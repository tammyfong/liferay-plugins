/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.vldap.server.handler;

import com.liferay.portal.NoSuchCompanyException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.Authenticator;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.vldap.util.DNUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.sasl.Sasl;
import javax.security.sasl.SaslException;
import javax.security.sasl.SaslServer;

import org.apache.directory.shared.ldap.message.ResultCodeEnum;
import org.apache.directory.shared.ldap.message.internal.InternalBindRequest;
import org.apache.directory.shared.ldap.message.internal.InternalBindResponse;
import org.apache.directory.shared.ldap.message.internal.InternalLdapResult;
import org.apache.directory.shared.ldap.message.internal.InternalRequest;
import org.apache.directory.shared.ldap.message.internal.InternalResponse;
import org.apache.directory.shared.ldap.name.DN;
import org.apache.directory.shared.ldap.util.StringTools;
import org.apache.mina.core.session.IoSession;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
public class BindLdapHandler implements LdapHandler {

	public List<InternalResponse> messageReceived(
			InternalRequest internalRequest, IoSession ioSession,
			LdapHandlerContext ldapHandlerContext)
		throws Exception {

		InternalResponse internalResponse = null;

		InternalBindRequest internalBindRequest =
			(InternalBindRequest)internalRequest;

		String saslMechanism = GetterUtil.getString(
			internalBindRequest.getSaslMechanism());

		if (saslMechanism.equals(_DIGEST_MD5)) {
			internalResponse = getSaslInternalResponse(
				internalBindRequest, ioSession, ldapHandlerContext);
		}
		else if (internalBindRequest.isSimple()) {
			internalResponse = getSimpleInternalResponse(
				internalBindRequest, ioSession, ldapHandlerContext);
		}
		else {
			internalResponse = getUnsupportedInternalResponse(
				internalBindRequest);
		}

		List<InternalResponse> internalResponses =
			new ArrayList<InternalResponse>();

		internalResponses.add(internalResponse);

		return internalResponses;
	}

	protected InternalResponse getInternalResponse(
		InternalBindRequest internalBindRequest, ResultCodeEnum resultCode) {

		InternalBindResponse internalBindResponse =
			(InternalBindResponse)internalBindRequest.getResultResponse();

		InternalLdapResult internalLdapResult =
			internalBindResponse.getLdapResult();

		internalLdapResult.setResultCode(resultCode);

		return internalBindResponse;
	}

	protected InternalResponse getSaslInternalResponse(
			InternalBindRequest internalBindRequest, IoSession ioSession,
			LdapHandlerContext ldapHandlerContext)
		throws Exception {

		if (internalBindRequest.getCredentials() == null) {
			internalBindRequest.setCredentials(StringTools.EMPTY_BYTES);
		}

		SaslServer saslServer = ldapHandlerContext.getSaslServer();

		try {
			if (saslServer == null) {
				synchronized (ldapHandlerContext) {
					saslServer = ldapHandlerContext.getSaslServer();

					if (saslServer == null) {
						SaslCallbackHandler saslCallbackHandler =
							new SaslCallbackHandler();

						ldapHandlerContext.setSaslCallbackHandler(
							saslCallbackHandler);

						saslServer = Sasl.createSaslServer(
							_DIGEST_MD5, "ldap", "localhost", null,
							saslCallbackHandler);

						ldapHandlerContext.setSaslServer(saslServer);
					}
				}
			}

			byte[] challenge = saslServer.evaluateResponse(
				internalBindRequest.getCredentials());

			InternalBindResponse internalBindResponse =
				(InternalBindResponse)internalBindRequest.getResultResponse();

			internalBindResponse.setServerSaslCreds(challenge);
		}
		catch (SaslException sasle) {
			_log.error(sasle, sasle);

			ldapHandlerContext.setSaslCallbackHandler(null);
			ldapHandlerContext.setSaslServer(null);

			return getInternalResponse(
				internalBindRequest, ResultCodeEnum.INVALID_CREDENTIALS);
		}

		if (saslServer.isComplete()) {
			SaslCallbackHandler saslCallbackHandler =
				ldapHandlerContext.getSaslCallbackHandler();

			ldapHandlerContext.setSaslCallbackHandler(null);
			ldapHandlerContext.setSaslServer(null);

			DN name = saslCallbackHandler.getName();

			setCompany(ldapHandlerContext, name);
			setUser(ldapHandlerContext, name);

			return getInternalResponse(
				internalBindRequest, ResultCodeEnum.SUCCESS);
		}
		else {
			return getInternalResponse(
				internalBindRequest, ResultCodeEnum.SASL_BIND_IN_PROGRESS);
		}
	}

	protected InternalResponse getSimpleInternalResponse(
			InternalBindRequest internalBindRequest, IoSession ioSession,
			LdapHandlerContext ldapHandlerContext)
		throws Exception {

		DN name = internalBindRequest.getName();

		Company company = setCompany(ldapHandlerContext, name);

		String screenName = GetterUtil.getString(
			DNUtil.getValue(name, "screenName"));

		if (Validator.isNull(screenName)) {
			return getInternalResponse(
				internalBindRequest, ResultCodeEnum.INVALID_CREDENTIALS);
		}

		String password = new String(internalBindRequest.getCredentials());
		Map<String, String[]> headerMap = new HashMap<String, String[]>();
		Map<String, String[]> parameterMap = new HashMap<String, String[]>();

		int authResult = UserLocalServiceUtil.authenticateByScreenName(
			company.getCompanyId(), screenName, password, headerMap,
			parameterMap);

		if (authResult != Authenticator.SUCCESS) {
			return getInternalResponse(
				internalBindRequest, ResultCodeEnum.AUTH_METHOD_NOT_SUPPORTED);
		}

		setUser(ldapHandlerContext, name);

		return getInternalResponse(internalBindRequest, ResultCodeEnum.SUCCESS);
	}

	protected InternalResponse getUnsupportedInternalResponse(
		InternalBindRequest internalBindRequest) {

		return getInternalResponse(
			internalBindRequest, ResultCodeEnum.AUTH_METHOD_NOT_SUPPORTED);
	}

	protected Company setCompany(
			LdapHandlerContext ldapHandlerContext, DN name)
		throws Exception {

		String webId = GetterUtil.getString(DNUtil.getValue(name, "webId"));

		Company company = null;

		try {
			company = CompanyLocalServiceUtil.getCompanyByWebId(webId);
		}
		catch (NoSuchCompanyException nsce) {
			long companyId = PortalUtil.getDefaultCompanyId();

			company = CompanyLocalServiceUtil.getCompany(companyId);
		}

		ldapHandlerContext.setCompany(company);

		return company;
	}

	protected User setUser(LdapHandlerContext ldapHandlerContext, DN name)
		throws Exception {

		String screenName = GetterUtil.getString(
			DNUtil.getValue(name, "screenName"));

		Company company = ldapHandlerContext.getCompany();

		User user = UserLocalServiceUtil.getUserByScreenName(
			company.getCompanyId(), screenName);

		ldapHandlerContext.setUser(user);

		return user;
	}

	private static final String _DIGEST_MD5 = "DIGEST-MD5";

	private static Log _log = LogFactoryUtil.getLog(BindLdapHandler.class);

}
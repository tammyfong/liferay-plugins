/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.hr.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.GroupedModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;

/**
 * The base model interface for the HRTimeSheetDay service. Represents a row in the &quot;HRTimeSheetDay&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.hr.model.impl.HRTimeSheetDayModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.hr.model.impl.HRTimeSheetDayImpl}.
 * </p>
 *
 * @author Wesley Gong
 * @see HRTimeSheetDay
 * @see com.liferay.hr.model.impl.HRTimeSheetDayImpl
 * @see com.liferay.hr.model.impl.HRTimeSheetDayModelImpl
 * @generated
 */
public interface HRTimeSheetDayModel extends BaseModel<HRTimeSheetDay>,
	GroupedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a h r time sheet day model instance should use the {@link HRTimeSheetDay} interface instead.
	 */

	/**
	 * Gets the primary key of this h r time sheet day.
	 *
	 * @return the primary key of this h r time sheet day
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this h r time sheet day
	 *
	 * @param pk the primary key of this h r time sheet day
	 */
	public void setPrimaryKey(long pk);

	/**
	 * Gets the hr time sheet day ID of this h r time sheet day.
	 *
	 * @return the hr time sheet day ID of this h r time sheet day
	 */
	public long getHrTimeSheetDayId();

	/**
	 * Sets the hr time sheet day ID of this h r time sheet day.
	 *
	 * @param hrTimeSheetDayId the hr time sheet day ID of this h r time sheet day
	 */
	public void setHrTimeSheetDayId(long hrTimeSheetDayId);

	/**
	 * Gets the group ID of this h r time sheet day.
	 *
	 * @return the group ID of this h r time sheet day
	 */
	public long getGroupId();

	/**
	 * Sets the group ID of this h r time sheet day.
	 *
	 * @param groupId the group ID of this h r time sheet day
	 */
	public void setGroupId(long groupId);

	/**
	 * Gets the company ID of this h r time sheet day.
	 *
	 * @return the company ID of this h r time sheet day
	 */
	public long getCompanyId();

	/**
	 * Sets the company ID of this h r time sheet day.
	 *
	 * @param companyId the company ID of this h r time sheet day
	 */
	public void setCompanyId(long companyId);

	/**
	 * Gets the user ID of this h r time sheet day.
	 *
	 * @return the user ID of this h r time sheet day
	 */
	public long getUserId();

	/**
	 * Sets the user ID of this h r time sheet day.
	 *
	 * @param userId the user ID of this h r time sheet day
	 */
	public void setUserId(long userId);

	/**
	 * Gets the user uuid of this h r time sheet day.
	 *
	 * @return the user uuid of this h r time sheet day
	 * @throws SystemException if a system exception occurred
	 */
	public String getUserUuid() throws SystemException;

	/**
	 * Sets the user uuid of this h r time sheet day.
	 *
	 * @param userUuid the user uuid of this h r time sheet day
	 */
	public void setUserUuid(String userUuid);

	/**
	 * Gets the user name of this h r time sheet day.
	 *
	 * @return the user name of this h r time sheet day
	 */
	@AutoEscape
	public String getUserName();

	/**
	 * Sets the user name of this h r time sheet day.
	 *
	 * @param userName the user name of this h r time sheet day
	 */
	public void setUserName(String userName);

	/**
	 * Gets the create date of this h r time sheet day.
	 *
	 * @return the create date of this h r time sheet day
	 */
	public Date getCreateDate();

	/**
	 * Sets the create date of this h r time sheet day.
	 *
	 * @param createDate the create date of this h r time sheet day
	 */
	public void setCreateDate(Date createDate);

	/**
	 * Gets the modified date of this h r time sheet day.
	 *
	 * @return the modified date of this h r time sheet day
	 */
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this h r time sheet day.
	 *
	 * @param modifiedDate the modified date of this h r time sheet day
	 */
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Gets the hr time sheet ID of this h r time sheet day.
	 *
	 * @return the hr time sheet ID of this h r time sheet day
	 */
	public long getHrTimeSheetId();

	/**
	 * Sets the hr time sheet ID of this h r time sheet day.
	 *
	 * @param hrTimeSheetId the hr time sheet ID of this h r time sheet day
	 */
	public void setHrTimeSheetId(long hrTimeSheetId);

	/**
	 * Gets the hr user ID of this h r time sheet day.
	 *
	 * @return the hr user ID of this h r time sheet day
	 */
	public long getHrUserId();

	/**
	 * Sets the hr user ID of this h r time sheet day.
	 *
	 * @param hrUserId the hr user ID of this h r time sheet day
	 */
	public void setHrUserId(long hrUserId);

	/**
	 * Gets the hr user uuid of this h r time sheet day.
	 *
	 * @return the hr user uuid of this h r time sheet day
	 * @throws SystemException if a system exception occurred
	 */
	public String getHrUserUuid() throws SystemException;

	/**
	 * Sets the hr user uuid of this h r time sheet day.
	 *
	 * @param hrUserUuid the hr user uuid of this h r time sheet day
	 */
	public void setHrUserUuid(String hrUserUuid);

	/**
	 * Gets the day of year of this h r time sheet day.
	 *
	 * @return the day of year of this h r time sheet day
	 */
	public int getDayOfYear();

	/**
	 * Sets the day of year of this h r time sheet day.
	 *
	 * @param dayOfYear the day of year of this h r time sheet day
	 */
	public void setDayOfYear(int dayOfYear);

	/**
	 * Gets the year of this h r time sheet day.
	 *
	 * @return the year of this h r time sheet day
	 */
	public int getYear();

	/**
	 * Sets the year of this h r time sheet day.
	 *
	 * @param year the year of this h r time sheet day
	 */
	public void setYear(int year);

	/**
	 * Gets the hours of this h r time sheet day.
	 *
	 * @return the hours of this h r time sheet day
	 */
	public double getHours();

	/**
	 * Sets the hours of this h r time sheet day.
	 *
	 * @param hours the hours of this h r time sheet day
	 */
	public void setHours(double hours);

	public boolean isNew();

	public void setNew(boolean n);

	public boolean isCachedModel();

	public void setCachedModel(boolean cachedModel);

	public boolean isEscapedModel();

	public void setEscapedModel(boolean escapedModel);

	public Serializable getPrimaryKeyObj();

	public ExpandoBridge getExpandoBridge();

	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	public Object clone();

	public int compareTo(HRTimeSheetDay hrTimeSheetDay);

	public int hashCode();

	public HRTimeSheetDay toEscapedModel();

	public String toString();

	public String toXmlString();
}
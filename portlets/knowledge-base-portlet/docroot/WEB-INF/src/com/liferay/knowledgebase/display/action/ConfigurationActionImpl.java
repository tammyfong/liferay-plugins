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

package com.liferay.knowledgebase.display.action;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.ActionRequest;
import javax.portlet.PortletPreferences;

/**
 * <a href="ConfigurationActionImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Peter Shin
 * @author Brian Wing Shun Chan
 */
public class ConfigurationActionImpl
	extends com.liferay.knowledgebase.admin.action.ConfigurationActionImpl {

	protected void postProcessPreferences(
			PortletPreferences preferences, ActionRequest actionRequest)
		throws Exception {

		String tabs2 = ParamUtil.getString(actionRequest, "tabs2");

		if (tabs2.equals("selection-method")) {
			updateSelectionMethod(actionRequest, preferences);
		}
	}

	protected void updateDisplaySettings(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		String childArticlesDisplayStyle = ParamUtil.getString(
			actionRequest, "childArticlesDisplayStyle");
		boolean enableArticleComments = ParamUtil.getBoolean(
			actionRequest, "enableArticleComments");
		boolean enableArticleCommentRatings = ParamUtil.getBoolean(
			actionRequest, "enableArticleCommentRatings");

		preferences.setValue(
			"child-articles-display-style", childArticlesDisplayStyle);
		preferences.setValue(
			"enable-article-comments", String.valueOf(enableArticleComments));
		preferences.setValue(
			"enable-article-comment-ratings",
			String.valueOf(enableArticleCommentRatings));
	}

	protected void updateSelectionMethod(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		String selectionMethod = ParamUtil.getString(
			actionRequest, "selectionMethod");
		long[] scopeGroupIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "scopeGroupIds"), 0L);
		long[] resourcePrimKeys = StringUtil.split(
			ParamUtil.getString(actionRequest, "resourcePrimKeys"), 0L);
		boolean allArticles = ParamUtil.getBoolean(
			actionRequest, "allArticles");
		String orderByColumn = ParamUtil.getString(
			actionRequest, "orderByColumn");
		boolean orderByAscending = ParamUtil.getBoolean(
			actionRequest, "orderByAscending");

		preferences.setValue("selection-method", selectionMethod);
		preferences.setValues(
			"scope-group-ids", ArrayUtil.toStringArray(scopeGroupIds));
		preferences.setValues(
			"resource-prim-keys", ArrayUtil.toStringArray(resourcePrimKeys));
		preferences.setValue("all-articles", String.valueOf(allArticles));
		preferences.setValue("order-by-column", orderByColumn);
		preferences.setValue(
			"order-by-ascending", String.valueOf(orderByAscending));
	}

}
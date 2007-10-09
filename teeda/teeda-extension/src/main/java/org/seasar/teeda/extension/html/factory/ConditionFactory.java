/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.teeda.extension.html.factory;

import java.util.Map;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.PageDesc;

/**
 * @author shot
 */
public class ConditionFactory extends AbstractElementProcessorFactory {

    //TODO write tld
    private static final String TAG_NAME = "condition";

    private static final String IS_PARAM_PREFIX = "is";

    private static final String ISNOT_PARAM_PREFIX = "isNot";

    public boolean isMatch(ElementNode elementNode, PageDesc pageDesc,
            ActionDesc actionDesc) {
        final String id = elementNode.getId();
        if (StringUtil.isEmpty(id)) {
            return false;
        }
        final String tagName = elementNode.getTagName();
        final boolean isId = StringUtil.startsWithIgnoreCase(id,
                IS_PARAM_PREFIX)
                && !StringUtil.startsWithIgnoreCase(id, ISNOT_PARAM_PREFIX);
        final boolean isNotId = StringUtil.startsWithIgnoreCase(id,
                ISNOT_PARAM_PREFIX);
        if (!isId && !isNotId) {
            return false;
        }
        String s = null;
        if (isId) {
            s = id.substring(IS_PARAM_PREFIX.length());
        } else {
            s = id.substring(ISNOT_PARAM_PREFIX.length());
        }
        final boolean hasProperty = (pageDesc != null) ? pageDesc.hasProperty(StringUtil
                .decapitalize(s)) : false;
        return (isDiv(tagName) || isSpan(tagName)) && hasProperty;
    }

    protected void customizeProperties(Map properties, ElementNode elementNode,
            PageDesc pageDesc, ActionDesc actionDesc) {
        super
                .customizeProperties(properties, elementNode, pageDesc,
                        actionDesc);
        if (pageDesc == null) {
            return;
        }
        String id = elementNode.getId();
        String pageName = pageDesc.getPageName();
        String s = null;
        String expression = null;
        if (StringUtil.startsWithIgnoreCase(id, IS_PARAM_PREFIX)
                && !StringUtil.startsWithIgnoreCase(id, ISNOT_PARAM_PREFIX)) {
            s = id.substring(IS_PARAM_PREFIX.length());
            s = StringUtil.decapitalize(s);
            s = s + " == true";
            expression = getBindingExpression(pageName, s);
        } else if (StringUtil.startsWithIgnoreCase(id, ISNOT_PARAM_PREFIX)) {
            s = id.substring(ISNOT_PARAM_PREFIX.length());
            s = StringUtil.decapitalize(s);
            s = s + " == false";
            expression = getBindingExpression(pageName, s);
        }
        properties.put("rendered", expression);
        String tagName = elementNode.getTagName();
        if (tagName != null && JsfConstants.SPAN_ELEM.equalsIgnoreCase(tagName)) {
            properties.put(ExtensionConstants.RENDERSPAN_ATTR, "true");
        }
    }

    protected String getTagName() {
        return TAG_NAME;
    }

    protected String getUri() {
        return ExtensionConstants.TEEDA_EXTENSION_URI;
    }

    private static final boolean isDiv(final String tagName) {
        return JsfConstants.DIV_ELEM.equalsIgnoreCase(tagName);
    }

    private static final boolean isSpan(final String tagName) {
        return JsfConstants.SPAN_ELEM.equalsIgnoreCase(tagName);
    }

}

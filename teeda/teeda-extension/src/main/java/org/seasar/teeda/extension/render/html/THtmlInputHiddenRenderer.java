/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.render.html;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.IgnoreAttribute;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.html.HtmlInputHiddenRenderer;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.core.util.ValueHolderUtil;

/**
 * @author shot
 */
public class THtmlInputHiddenRenderer extends HtmlInputHiddenRenderer {

    private final IgnoreAttribute ignoreComponent = new IgnoreAttribute();
    {
        ignoreComponent.addAttributeName(JsfConstants.ID_ATTR);
        ignoreComponent.addAttributeName(JsfConstants.VALUE_ATTR);
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlInputHiddenEnd(context, (HtmlInputHidden) component);
    }

    protected void encodeHtmlInputHiddenEnd(FacesContext context,
            HtmlInputHidden htmlInputHidden) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement(JsfConstants.INPUT_ELEM, htmlInputHidden);
        RendererUtil.renderAttribute(writer, JsfConstants.TYPE_ATTR,
                JsfConstants.HIDDEN_VALUE);
        RendererUtil.renderIdAttributeIfNecessary(writer, htmlInputHidden,
                getIdForRender(context, htmlInputHidden));
        final String clientId = htmlInputHidden.getClientId(context);
        if (clientId != null && clientId.lastIndexOf("-") > 0) {
            String s = clientId.substring(0, clientId.lastIndexOf("-"));
            RendererUtil.renderAttribute(writer, JsfConstants.NAME_ATTR, s);
        } else {
            RendererUtil.renderAttribute(writer, JsfConstants.NAME_ATTR,
                    clientId);
        }

        String value = ValueHolderUtil.getValueForRender(context,
                htmlInputHidden);
        RendererUtil.renderAttribute(writer, JsfConstants.VALUE_ATTR, value);
        renderRemainAttributes(htmlInputHidden, writer, ignoreComponent);
        writer.endElement(JsfConstants.INPUT_ELEM);
    }

}

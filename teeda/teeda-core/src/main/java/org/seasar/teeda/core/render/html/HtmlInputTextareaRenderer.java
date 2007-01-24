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
package org.seasar.teeda.core.render.html;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.IgnoreAttribute;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.AbstractInputRenderer;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.core.util.ValueHolderUtil;

/**
 * @author manhole
 */
public class HtmlInputTextareaRenderer extends AbstractInputRenderer {

    public static final String COMPONENT_FAMILY = "javax.faces.Input";

    public static final String RENDERER_TYPE = "javax.faces.Textarea";

    private final IgnoreAttribute ignoreAttribute = new IgnoreAttribute();
    {
        ignoreAttribute.addAttributeName(JsfConstants.ID_ATTR);
        ignoreAttribute.addAttributeName(JsfConstants.VALUE_ATTR);
        ignoreAttribute.addAttributeName(JsfConstants.NAME_ATTR);
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlInputTextareaEnd(context, (HtmlInputTextarea) component);
    }

    protected void encodeHtmlInputTextareaEnd(FacesContext context,
            HtmlInputTextarea htmlInputTextarea) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement(JsfConstants.TEXTAREA_ELEM, htmlInputTextarea);
        RendererUtil.renderIdAttributeIfNecessary(writer, htmlInputTextarea,
                getIdForRender(context, htmlInputTextarea));
        RendererUtil.renderAttribute(writer, JsfConstants.NAME_ATTR,
                htmlInputTextarea.getClientId(context));
        renderRemainAttributes(htmlInputTextarea, writer, ignoreAttribute);

        String value = ValueHolderUtil.getValueForRender(context,
                htmlInputTextarea);
        writer.writeText(value, null);

        writer.endElement(JsfConstants.TEXTAREA_ELEM);
    }

    public void decode(FacesContext context, UIComponent component) {
        assertNotNull(context, component);
        decodeHtmlInputText(context, (HtmlInputTextarea) component);
    }

    protected void decodeHtmlInputText(FacesContext context,
            HtmlInputTextarea htmlInputTextarea) {
        getDecoder().decode(context, htmlInputTextarea);
    }

}

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
package org.seasar.teeda.extension.render;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.IgnoreAttribute;

import org.seasar.framework.log.Logger;
import org.seasar.framework.message.MessageFormatter;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.AbstractRenderer;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.component.TCondition;

/**
 * @author shot
 */
public class TConditionRenderer extends AbstractRenderer {

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.Condition";

    public static final String RENDERER_TYPE = "org.seasar.teeda.extension.Condition";

    private static final Logger logger = Logger
            .getLogger(TConditionRenderer.class);

    private final IgnoreAttribute attribute = new IgnoreAttribute();
    {
        attribute.addAttributeName(JsfConstants.ID_ATTR);
        attribute.addAttributeName(ExtensionConstants.SUBMITTED);
    }

    public void decode(FacesContext context, UIComponent component) {
        assertNotNull(context, component);
        decodeTCondition(context, (TCondition) component);
    }

    protected void decodeTCondition(FacesContext context, TCondition condition) {
        Object submitted = null;
        for (Iterator itr = condition.getChildren().iterator(); itr.hasNext();) {
            final UIComponent c = (UIComponent) itr.next();
            if (c instanceof HtmlInputHidden) {
                final HtmlInputHidden hidden = (HtmlInputHidden) c;
                final String hiddenId = c.getId();
                if (hiddenId.equals(condition.getId()
                        + ExtensionConstants.TEEDA_HIDDEN_SUFFIX)) {
                    hidden.decode(context);
                    submitted = hidden.getSubmittedValue();
                    break;
                }
            }
        }
        if ("true".equals(submitted)) {
            condition.setSubmitted(true);
        } else {
            condition.setSubmitted(false);
        }
    }

    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        super.encodeBegin(context, component);
        final boolean rendered = component.isRendered();
        boolean renderHidden = false;
        for (Iterator itr = component.getChildren().iterator(); itr.hasNext();) {
            final UIComponent child = (UIComponent) itr.next();
            if (child instanceof HtmlInputHidden) {
                final String hiddenId = child.getId();
                if (hiddenId.equals(component.getId()
                        + ExtensionConstants.TEEDA_HIDDEN_SUFFIX)) {
                    ((HtmlInputHidden) child).setValue(new Boolean(rendered));
                    renderHidden = true;
                    break;
                }
            }
        }
        if (!rendered) {
            return;
        }
        if (!renderHidden) {
            final String message = MessageFormatter
                    .getMessage("WTDA0203", null);
            logger.debug(message);
        }
        TCondition condition = (TCondition) component;
        final ResponseWriter writer = context.getResponseWriter();
        writer.startElement(JsfConstants.DIV_ELEM, condition);
        RendererUtil.renderIdAttributeIfNecessary(writer, component,
                getIdForRender(context, condition));
        renderRemainAttributes(condition, writer, attribute);
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        super.encodeEnd(context, component);
        if (!component.isRendered()) {
            return;
        }
        final ResponseWriter writer = context.getResponseWriter();
        writer.endElement(JsfConstants.DIV_ELEM);
    }

    public boolean getRendersChildren() {
        return true;
    }

}

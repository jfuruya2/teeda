/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
import java.net.URLEncoder;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.IgnoreAttribute;
import javax.faces.internal.WindowIdUtil;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.AbstractRenderer;
import org.seasar.teeda.core.render.html.support.UrlBuilder;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.core.util.ValueHolderUtil;

/**
 * @author manhole
 */
public class HtmlOutputLinkRenderer extends AbstractRenderer {

    public static final String COMPONENT_FAMILY = "javax.faces.Output";

    public static final String RENDERER_TYPE = "javax.faces.Link";

    private final IgnoreAttribute ignoreComponent = new IgnoreAttribute();
    {
        ignoreComponent.addAttributeName(JsfConstants.ID_ATTR);
        ignoreComponent.addAttributeName(JsfConstants.VALUE_ATTR);
    }

    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlOutputLinkBegin(context, (HtmlOutputLink) component);
    }

    protected void encodeHtmlOutputLinkBegin(FacesContext context,
            HtmlOutputLink htmlOutputLink) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        final String encoding = writer.getCharacterEncoding();
        final String href = buildHref(context, htmlOutputLink, encoding);
        writer.startElement(JsfConstants.ANCHOR_ELEM, htmlOutputLink);
        RendererUtil.renderIdAttributeIfNecessary(writer, htmlOutputLink,
                getIdForRender(context, htmlOutputLink));
        renderHref(context, writer, href);
        renderRemainAttributes(htmlOutputLink, writer, ignoreComponent);
    }

    protected void renderHref(final FacesContext context,
            final ResponseWriter writer, final String href) throws IOException {
        writer.writeURIAttribute(JsfConstants.HREF_ATTR, href, null);
    }

    protected String buildHref(FacesContext context,
            HtmlOutputLink htmlOutputLink, String encoding) throws IOException {
        UrlBuilder urlBuilder = new UrlBuilder();
        urlBuilder.setBase(ValueHolderUtil.getValueForRender(context,
                htmlOutputLink));
        for (Iterator it = htmlOutputLink.getChildren().iterator(); it
                .hasNext();) {
            UIComponent child = (UIComponent) it.next();
            if (child instanceof UIParameter) {
                UIParameter parameter = (UIParameter) child;
                urlBuilder.add(
                        URLEncoder.encode(parameter.getName(), encoding),
                        URLEncoder.encode(toBlankIfNull(parameter.getValue()),
                                encoding));
            }
        }
        if (WindowIdUtil.isNewWindowTarget(htmlOutputLink.getTarget())) {
            urlBuilder.add(WindowIdUtil.NEWWINDOW, JsfConstants.TRUE);
        }
        return context.getExternalContext().encodeResourceURL(
                urlBuilder.build());
    }

    protected String toBlankIfNull(final Object value) {
        if (value == null) {
            return "";
        }
        return value.toString();
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlOutputLinkEnd(context, (HtmlOutputLink) component);
    }

    protected void encodeHtmlOutputLinkEnd(FacesContext context,
            HtmlOutputLink link) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.endElement(JsfConstants.ANCHOR_ELEM);
    }

    public boolean getRendersChildren() {
        return true;
    }

}

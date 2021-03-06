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
package org.seasar.teeda.extension.component.html;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.internal.FacesConfigOptions;
import javax.faces.internal.RenderPreparable;
import javax.faces.internal.RenderPreparableUtil;

import org.seasar.teeda.extension.component.TForEach;

/**
 * @author manhole
 */
//TODO if getter method which return type is String, use ComponentUtil_.getValueBindingAsString(this, bindingName);
public class THtmlGrid extends TForEach implements NamingContainer,
        RenderPreparable {

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.Grid";

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.HtmlGrid";

    public static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.Grid";

    public THtmlGrid() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    private String width;

    private Boolean scrollVertical;

    private Boolean scrollHorizontal;

    private Boolean async;

    private String height;

    private String style;

    private String styleClass;

    private String callback;

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String width) {
        this.height = width;
    }

    public void processValidators(final FacesContext context) {
        if (context == null) {
            throw new NullPointerException("context");
        }
        if (!isRendered()) {
            return;
        }
        for (final Iterator itr = getFacetsAndChildren(); itr.hasNext();) {
            final UIComponent component = (UIComponent) itr.next();
            if (component instanceof THtmlGridBody) {
                processValidatorsAllRows(context, component);
            } else {
                component.processValidators(context);
            }
        }
    }

    public void processUpdates(final FacesContext context) {
        if (context == null) {
            throw new NullPointerException("context");
        }
        if (!isRendered()) {
            return;
        }
        for (final Iterator itr = getFacetsAndChildren(); itr.hasNext();) {
            final UIComponent component = (UIComponent) itr.next();
            if (component instanceof THtmlGridBody) {
                processUpdatesAllRows(context, component);
            } else {
                component.processUpdates(context);
            }
        }
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[9];
        values[0] = super.saveState(context);
        values[1] = width;
        values[2] = height;
        values[3] = scrollHorizontal;
        values[4] = scrollVertical;
        values[5] = style;
        values[6] = styleClass;
        values[7] = async;
        values[8] = callback;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        width = (String) values[1];
        height = (String) values[2];
        scrollHorizontal = (Boolean) values[3];
        scrollVertical = (Boolean) values[4];
        style = (String) values[5];
        styleClass = (String) values[6];
        async = (Boolean) values[7];
        callback = (String) values[8];
    }

    // gridX, XY
    public boolean isScrollHorizontal() {
        if (scrollHorizontal != null) {
            return scrollHorizontal.booleanValue();
        }
        return false;
    }

    public void setScrollHorizontal(boolean scrollHorizontal) {
        this.scrollHorizontal = Boolean.valueOf(scrollHorizontal);
    }

    // gridY, XY
    public boolean isScrollVertical() {
        if (scrollVertical != null) {
            return scrollVertical.booleanValue();
        }
        return false;
    }

    public void setScrollVertical(boolean scrollVertical) {
        this.scrollVertical = Boolean.valueOf(scrollVertical);
    }

    public boolean isAsync() {
        if (async != null) {
            return async.booleanValue();
        }
        return FacesConfigOptions.isDefaultGridAsync();
    }

    public void setAsync(boolean async) {
        this.async = Boolean.valueOf(async);
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyle() {
        if (style != null) {
            return style;
        }
        ValueBinding vb = getValueBinding("style");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        if (styleClass != null) {
            return styleClass;
        }
        ValueBinding vb = getValueBinding("styleClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void preEncodeBegin(FacesContext context) throws IOException {
        RenderPreparableUtil.encodeBeforeForRenderer(context, this);
    }

    public void postEncodeEnd(FacesContext context) throws IOException {
    }

}

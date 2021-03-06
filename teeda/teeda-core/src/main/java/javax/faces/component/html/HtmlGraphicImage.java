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
package javax.faces.component.html;

import javax.faces.component.ComponentUtil_;
import javax.faces.component.UIGraphic;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.seasar.teeda.core.JsfConstants;

/**
 * @author shot
 */
public class HtmlGraphicImage extends UIGraphic {

    public static final String COMPONENT_TYPE = "javax.faces.HtmlGraphicImage";

    private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Image";

    private static final boolean DEFAULT_ISMAP = false;

    private static final int DEFAULTBORDER = Integer.MIN_VALUE;

    private String alt = null;

    private String dir = null;

    private String height = null;

    private Boolean ismap = null;

    private String lang = null;

    private String longdesc = null;

    private String onclick = null;

    private String ondblclick = null;

    private String onkeydown = null;

    private String onkeypress = null;

    private String onkeyup = null;

    private String onmousedown = null;

    private String onmousemove = null;

    private String onmouseout = null;

    private String onmouseover = null;

    private String onmouseup = null;

    private String style = null;

    private String styleClass = null;

    private String title = null;

    private String usemap = null;

    private String width = null;

    private Integer border;

    public HtmlGraphicImage() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getAlt() {
        if (alt != null) {
            return alt;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ALT_ATTR);
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getDir() {
        if (dir != null) {
            return dir;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.DIR_ATTR);
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getHeight() {
        if (height != null) {
            return height;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.HEIGHT_ATTR);
    }

    public void setIsmap(boolean ismap) {
        this.ismap = Boolean.valueOf(ismap);
    }

    public boolean isIsmap() {
        if (ismap != null) {
            return ismap.booleanValue();
        }
        ValueBinding vb = getValueBinding("ismap");
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext())
                : null;
        return v != null ? v.booleanValue() : DEFAULT_ISMAP;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLang() {
        if (lang != null) {
            return lang;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.LANG_ATTR);
    }

    public void setLongdesc(String longdesc) {
        this.longdesc = longdesc;
    }

    public String getLongdesc() {
        if (longdesc != null) {
            return longdesc;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.LONGDESC_ATTR);
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public String getOnclick() {
        if (onclick != null) {
            return onclick;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONCLICK_ATTR);
    }

    public void setOndblclick(String ondblclick) {
        this.ondblclick = ondblclick;
    }

    public String getOndblclick() {
        if (ondblclick != null) {
            return ondblclick;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONDBLCLICK_ATTR);
    }

    public void setOnkeydown(String onkeydown) {
        this.onkeydown = onkeydown;
    }

    public String getOnkeydown() {
        if (onkeydown != null) {
            return onkeydown;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONKEYDOWN_ATTR);
    }

    public void setOnkeypress(String onkeypress) {
        this.onkeypress = onkeypress;
    }

    public String getOnkeypress() {
        if (onkeypress != null) {
            return onkeypress;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONKEYPRESS_ATTR);
    }

    public void setOnkeyup(String onkeyup) {
        this.onkeyup = onkeyup;
    }

    public String getOnkeyup() {
        if (onkeyup != null) {
            return onkeyup;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONKEYUP_ATTR);
    }

    public void setOnmousedown(String onmousedown) {
        this.onmousedown = onmousedown;
    }

    public String getOnmousedown() {
        if (onmousedown != null) {
            return onmousedown;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONMOUSEDOWN_ATTR);
    }

    public void setOnmousemove(String onmousemove) {
        this.onmousemove = onmousemove;
    }

    public String getOnmousemove() {
        if (onmousemove != null) {
            return onmousemove;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONMOUSEMOVE_ATTR);
    }

    public void setOnmouseout(String onmouseout) {
        this.onmouseout = onmouseout;
    }

    public String getOnmouseout() {
        if (onmouseout != null) {
            return onmouseout;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONMOUSEOUT_ATTR);
    }

    public void setOnmouseover(String onmouseover) {
        this.onmouseover = onmouseover;
    }

    public String getOnmouseover() {
        if (onmouseover != null) {
            return onmouseover;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONMOUSEOVER_ATTR);
    }

    public void setOnmouseup(String onmouseup) {
        this.onmouseup = onmouseup;
    }

    public String getOnmouseup() {
        if (onmouseup != null) {
            return onmouseup;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONMOUSEUP_ATTR);
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyle() {
        if (style != null) {
            return style;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.STYLE_ATTR);
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        if (styleClass != null) {
            return styleClass;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.STYLE_CLASS_ATTR);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        if (title != null) {
            return title;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.TITLE_ATTR);
    }

    public void setUsemap(String usemap) {
        this.usemap = usemap;
    }

    public String getUsemap() {
        if (usemap != null) {
            return usemap;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.USEMAP_ATTR);
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getWidth() {
        if (width != null) {
            return width;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.WIDTH_ATTR);
    }

    public void setBorder(int border) {
        this.border = new Integer(border);
    }

    public int getBorder() {
        if (border != null) {
            return border.intValue();
        }
        ValueBinding vb = getValueBinding("border");
        Integer v = vb != null ? (Integer) vb.getValue(getFacesContext())
                : null;
        return v != null ? v.intValue() : DEFAULTBORDER;
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[23];
        values[0] = super.saveState(context);
        values[1] = alt;
        values[2] = dir;
        values[3] = height;
        values[4] = ismap;
        values[5] = lang;
        values[6] = longdesc;
        values[7] = onclick;
        values[8] = ondblclick;
        values[9] = onkeydown;
        values[10] = onkeypress;
        values[11] = onkeyup;
        values[12] = onmousedown;
        values[13] = onmousemove;
        values[14] = onmouseout;
        values[15] = onmouseover;
        values[16] = onmouseup;
        values[17] = style;
        values[18] = styleClass;
        values[19] = title;
        values[20] = usemap;
        values[21] = width;
        values[22] = border;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        alt = (String) values[1];
        dir = (String) values[2];
        height = (String) values[3];
        ismap = (Boolean) values[4];
        lang = (String) values[5];
        longdesc = (String) values[6];
        onclick = (String) values[7];
        ondblclick = (String) values[8];
        onkeydown = (String) values[9];
        onkeypress = (String) values[10];
        onkeyup = (String) values[11];
        onmousedown = (String) values[12];
        onmousemove = (String) values[13];
        onmouseout = (String) values[14];
        onmouseover = (String) values[15];
        onmouseup = (String) values[16];
        style = (String) values[17];
        styleClass = (String) values[18];
        title = (String) values[19];
        usemap = (String) values[20];
        width = (String) values[21];
        border = (Integer) values[22];
    }

}

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
package org.seasar.teeda.core.taglib.html;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlSelectManyMenu;

import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.mock.MockApplicationImpl;
import org.seasar.teeda.core.mock.MockConverter;
import org.seasar.teeda.core.mock.MockMethodBinding;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author yone
 */
public class SelectManyMenuTagTest extends TeedaTestCase {

    public void testGetComponentType() throws Exception {
        // # Arrange #
        SelectManyMenuTag tag = new SelectManyMenuTag();

        // # Act & Assert #
        assertEquals("javax.faces.HtmlSelectManyMenu", tag.getComponentType());
    }

    public void testGetRenderType() throws Exception {
        // # Arrange #
        SelectManyMenuTag tag = new SelectManyMenuTag();

        // # Act & Assert #
        assertEquals("javax.faces.Menu", tag.getRendererType());
    }

    public void testSetProperties_All() throws Exception {
        // # Arrange #
        HtmlSelectManyMenu component = createHtmlSelectManyMenu();
        SelectManyMenuTag tag = new SelectManyMenuTag();
        MockApplication app = new MockApplicationImpl();
        app.addConverter("mock.converter",
                "org.seasar.teeda.core.mock.MockConverter");
        setApplication(app);
        app.addValidator("mock.validator",
                "org.seasar.teeda.core.mock.MockValidator");

        tag.setConverter("mock.converter");
        tag.setImmediate("true");
        tag.setRequired("true");
        tag.setValidator("#{mock.validator}");
        tag.setValue("value");
        tag.setValueChangeListener("#{mock.listener}");
        tag.setAccesskey("accesskey");
        tag.setDir("dir");
        tag.setDisabled("true");
        tag.setDisabledClass("disabledClass");
        tag.setEnabledClass("enabledClass");
        tag.setLang("lang");
        tag.setOnblur("onblur");
        tag.setOnchange("onchange");
        tag.setOnclick("onclick");
        tag.setOndblclick("ondblclick");
        tag.setOnfocus("onfocus");
        tag.setOnkeydown("onkeydown");
        tag.setOnkeypress("onkeypress");
        tag.setOnkeyup("onkeyup");
        tag.setOnmousedown("onmousedown");
        tag.setOnmousemove("onmousemove");
        tag.setOnmouseout("onmouseout");
        tag.setOnmouseover("onmouseover");
        tag.setOnmouseup("onmouseup");
        tag.setOnselect("onselect");
        tag.setReadonly("true");
        tag.setStyle("style");
        tag.setStyleClass("styleclass");
        tag.setTabindex("13");
        tag.setTitle("title");

        // # Act #
        tag.setProperties(component);

        // # Assert #
        assertTrue(component.getConverter() instanceof MockConverter);
        assertTrue(component.isImmediate());
        assertTrue(component.isRequired());
        assertTrue(component.getValidator() instanceof MockMethodBinding);
        assertEquals("#{mock.validator}", component.getValidator()
                .getExpressionString());
        assertEquals("value", component.getValue());
        assertTrue(component.getValueChangeListener() instanceof MockMethodBinding);
        assertEquals("#{mock.listener}", component.getValueChangeListener()
                .getExpressionString());
        assertEquals("accesskey", component.getAccesskey());
        assertEquals("dir", component.getDir());
        assertTrue(component.isDisabled());
        assertEquals("disabledClass", component.getDisabledClass());
        assertEquals("enabledClass", component.getEnabledClass());
        assertEquals("lang", component.getLang());
        assertEquals("onblur", component.getOnblur());
        assertEquals("onchange", component.getOnchange());
        assertEquals("onclick", component.getOnclick());
        assertEquals("ondblclick", component.getOndblclick());
        assertEquals("onfocus", component.getOnfocus());
        assertEquals("onkeydown", component.getOnkeydown());
        assertEquals("onkeypress", component.getOnkeypress());
        assertEquals("onkeyup", component.getOnkeyup());
        assertEquals("onmousedown", component.getOnmousedown());
        assertEquals("onmousemove", component.getOnmousemove());
        assertEquals("onmouseout", component.getOnmouseout());
        assertEquals("onmouseover", component.getOnmouseover());
        assertEquals("onmouseup", component.getOnmouseup());
        assertEquals("onselect", component.getOnselect());
        assertTrue(component.isReadonly());
        assertEquals("style", component.getStyle());
        assertEquals("styleclass", component.getStyleClass());
        assertEquals("13", component.getTabindex());
        assertEquals("title", component.getTitle());
    }

    private HtmlSelectManyMenu createHtmlSelectManyMenu() {
        return (HtmlSelectManyMenu) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new HtmlSelectManyMenu();
    }
}

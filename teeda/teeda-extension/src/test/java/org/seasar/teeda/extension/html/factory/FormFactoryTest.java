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
package org.seasar.teeda.extension.html.factory;

import java.util.HashMap;
import java.util.Map;

import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.ElementProcessor;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.factory.sub.web.foo.FooAction;
import org.seasar.teeda.extension.html.factory.sub.web.foo.FooPage;
import org.seasar.teeda.extension.taglib.TFormTag;

/**
 * @author higa
 */
public class FormFactoryTest extends ElementProcessorFactoryTestCase {

    protected AbstractElementProcessorFactory createFactory() {
        return new FormFactory();
    }

    protected void registerTagElements() {
        registerTagElement(ExtensionConstants.TEEDA_EXTENSION_URI, "form",
                TFormTag.class);
    }

    public void testIsMatch1() throws Exception {
        Map properties = new HashMap();
        properties.put("id", "hogeForm");
        ElementNode elementNode = createElementNode("form", properties);
        assertTrue(factory.isMatch(elementNode, null, null));
        ElementNode elementNode2 = createElementNode("hoge", properties);
        assertFalse(factory.isMatch(elementNode2, null, null));
        ElementNode elementNode3 = createElementNode("form", new HashMap());
        assertFalse(factory.isMatch(elementNode3, null, null));
        properties.put("id", "form");
        ElementNode elementNode4 = createElementNode("form", properties);
        assertTrue(factory.isMatch(elementNode4, null, null));
    }

    public void testIsMatch2() throws Exception {
        Map properties = new HashMap();
        properties.put("id", "form");
        ElementNode elementNode = createElementNode("form", properties);
        assertTrue(factory.isMatch(elementNode, null, null));
    }

    public void testCreateFactory() throws Exception {
        // ## Arrange ##
        Map properties = new HashMap();
        properties.put("id", "fooForm");
        ElementNode elementNode = createElementNode("form", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");

        // ## Act ##
        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull("1", processor);
        assertEquals("2", TFormTag.class, processor.getTagClass());
    }
}

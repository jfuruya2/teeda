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
package javax.faces.component;

import junit.framework.TestCase;

import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.AssertUtil;

/**
 * @author shot
 */
public class ComponentAttributesMap_Test extends TestCase {

    public void testGetComponentProperty() {
        MockUIComponent component = new MockUIComponent();
        component.setFamily("mock");
        ComponentAttributesMap_ map = new ComponentAttributesMap_(component);
        assertNotNull(map.get("family"));
        assertEquals("mock", map.get("family"));
    }

    public void testPutComponentProperty() {
        MockUIComponent component = new MockUIComponent();
        component.setFamily("mock");
        ComponentAttributesMap_ map = new ComponentAttributesMap_(component);
        assertEquals("mock", map.get("family"));
        component.setFamily("mock2");
        map.put("family", component);
        assertEquals("mock2", map.get("family"));
    }

    public void testRemoveComponentProperty() throws Exception {
        // ## Arrange ##
        MockUIComponent component = new MockUIComponent();
        component.setFamily("mock");
        ComponentAttributesMap_ map = new ComponentAttributesMap_(component);

        // ## Act & Assert ##
        try {
            map.remove("family");
            fail();
        } catch (IllegalArgumentException iae) {
            AssertUtil.assertExceptionMessageExist(iae);
        }
    }

    public void testContainsKey_ComponentProperty() throws Exception {
        // ## Arrange ##
        MockUIComponent component = new MockUIComponent();
        component.setFamily("mock");
        ComponentAttributesMap_ map = new ComponentAttributesMap_(component);

        // ## Act & Assert ##
        assertEquals(false, map.containsKey("family"));
    }

    public void testContainsKey_NotComponentProperty() throws Exception {
        // ## Arrange ##
        MockUIComponent component = new MockUIComponent();
        component.setFamily("mock");
        ComponentAttributesMap_ map = new ComponentAttributesMap_(component);

        // ## Act & Assert ##
        assertEquals(false, map.containsKey("aaaa"));
        map.put("aaaa", "some value");
        assertEquals(true, map.containsKey("aaaa"));
    }

    public void pending_testGetComponentWriteOnlyProperty() {
        MyMockUIComponent component = new MyMockUIComponent();
        ComponentAttributesMap_ map = new ComponentAttributesMap_(component);
        try {
            map.get("barProperty");
            fail();
        } catch (IllegalArgumentException iae) {
            System.out.println(iae.getMessage());
            AssertUtil.assertExceptionMessageExist(iae);
        }
    }

    public void testPutComponentReadOnlyProperty() {
        MyMockUIComponent component = new MyMockUIComponent();
        ComponentAttributesMap_ map = new ComponentAttributesMap_(component);
        try {
            map.put("fooProperty", "some value");
            fail();
        } catch (IllegalArgumentException iae) {
            System.out.println(iae.getMessage());
            AssertUtil.assertExceptionMessageExist(iae);
        }
    }

    private static class MyMockUIComponent extends MockUIComponent {
        // read only
        public String getFooProperty() {
            return "fooValue";
        }

        // write only
        public void setBarProperty() {
        }
    }

    public void testPut_NotComponentProperty() throws Exception {
        // ## Arrange ##
        MockUIComponent component = new MockUIComponent();
        ComponentAttributesMap_ map = new ComponentAttributesMap_(component);

        // ## Act ##
        map.put("notUIComponentProperty", "some value");

        // ## Assert ##
        assertEquals("some value", map.get("notUIComponentProperty"));
    }

    public void testPut_KeyIsNull() throws Exception {
        MockUIComponent c = new MockUIComponent();
        ComponentAttributesMap_ map = new ComponentAttributesMap_(c);
        try {
            map.put(null, new Object());
            fail();
        } catch (NullPointerException npe) {
            AssertUtil.assertExceptionMessageExist(npe);
        }
    }

    public void testPut_ValueIsNull() throws Exception {
        MockUIComponent c = new MockUIComponent();
        ComponentAttributesMap_ map = new ComponentAttributesMap_(c);
        try {
            map.put("hoge", null);
            fail();
        } catch (NullPointerException npe) {
            AssertUtil.assertExceptionMessageExist(npe);
        }
    }

    public void testPut_KeyShouldBeString() throws Exception {
        MockUIComponent c = new MockUIComponent();
        ComponentAttributesMap_ map = new ComponentAttributesMap_(c);
        try {
            map.put(new Integer(1), "value");
            fail();
        } catch (ClassCastException cce) {
            AssertUtil.assertExceptionMessageExist(cce);
        }
    }

    // TODO more test

}

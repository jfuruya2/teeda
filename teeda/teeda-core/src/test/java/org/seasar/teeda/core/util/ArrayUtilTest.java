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
package org.seasar.teeda.core.util;

import java.lang.reflect.Array;

import junit.framework.TestCase;
import junitx.framework.ArrayAssert;

public class ArrayUtilTest extends TestCase {

    public void testEqualsIgnoreSequence() throws Exception {
        assertEquals(true, ArrayUtil.equalsIgnoreSequence(new Object[] { "1" },
                new Object[] { "1" }));
        assertEquals(true, ArrayUtil.equalsIgnoreSequence(new Object[] { "1",
                "2", "3" }, new Object[] { "2", "3", "1" }));
        assertEquals(false, ArrayUtil.equalsIgnoreSequence(
                new Object[] { "1" }, new Object[] { "2" }));
        assertEquals(false, ArrayUtil.equalsIgnoreSequence(
                new Object[] { "1" }, new Object[] {}));
        assertEquals(false, ArrayUtil.equalsIgnoreSequence(
                new Object[] { new Integer("1") }, new Object[] { "1" }));

        assertEquals(true, ArrayUtil.equalsIgnoreSequence(null, null));
        assertEquals(false, ArrayUtil.equalsIgnoreSequence(null,
                new Object[] {}));
    }

    public void testSetArrayValue() throws Exception {
        Object o = Array.newInstance(int.class, 3);
        ArrayUtil.setArrayValue(o, int.class, new Integer(1), 0);
        ArrayUtil.setArrayValue(o, int.class, new Integer(2), 1);
        ArrayUtil.setArrayValue(o, int.class, new Integer(3), 2);
        int[] num = (int[]) o;
        ArrayAssert.assertEquals(new int[] { 1, 2, 3 }, num);
    }

}

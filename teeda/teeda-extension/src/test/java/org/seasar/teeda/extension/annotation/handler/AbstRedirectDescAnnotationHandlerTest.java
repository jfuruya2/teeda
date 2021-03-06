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
package org.seasar.teeda.extension.annotation.handler;

import junit.framework.TestCase;

import org.seasar.teeda.extension.html.RedirectDesc;

/**
 * @author koichik
 */
public class AbstRedirectDescAnnotationHandlerTest extends TestCase {

    public void testCreateRedirectDesc() {
        RedirectDesc desc = AbstractRedirectDescAnnotationHandler
                .createRedirectDesc("http", 80);
        assertEquals("http", desc.getProtocol());
        assertEquals(80, desc.getPort());

        desc = AbstractRedirectDescAnnotationHandler.createRedirectDesc(
                "https", 443);
        assertEquals("https", desc.getProtocol());
        assertEquals(443, desc.getPort());
    }

}

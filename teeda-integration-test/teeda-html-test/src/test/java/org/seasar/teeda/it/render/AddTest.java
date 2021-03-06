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
package org.seasar.teeda.it.render;

import java.net.URL;

import junit.framework.Test;

import org.seasar.teeda.unit.web.TeedaWebTestCase;
import org.seasar.teeda.unit.web.TeedaWebTester;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author shot
 */
public class AddTest extends TeedaWebTestCase {

	public static Test suite() throws Exception {
		return setUpTest(AddTest.class);
	}

	public void testRender() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/add/add.html");
		tester.dumpHtml();

		tester.setTextByName("addForm:arg1", "123");
		tester.setTextByName("addForm:arg2", "123");

		tester.submitById("doCalculate");

		// ## Assert ##
		tester.dumpHtml();
		tester.assertTextEqualsById("result", "246");
	}

	public void testRender2() throws Exception {
		// ## Arrange ##
		URL url = getUrl("view/add/add.html");
		System.out.println(url);

		WebClient webClient = new WebClient();

		HtmlPage page1 = getHtmlPage(webClient, url);

		// ## Act ##
		HtmlElement element = page1.getHtmlElementById("arg3");
		HtmlInput input = (HtmlInput) element;

		// ## Assert ##
		assertEquals("readonly", input.getReadOnlyAttribute());
	}

	public void testSeasarUser7940() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/add/add.html");
		tester.dumpHtml();

		// ## Assert ##
		tester.assertTitleEquals("Simple Add sample");
		tester.assertAttributeEqualsById("doCalculate", "value", "計算実行");
	}

}

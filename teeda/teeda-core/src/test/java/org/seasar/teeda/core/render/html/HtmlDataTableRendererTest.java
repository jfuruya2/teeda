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
package org.seasar.teeda.core.render.html;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIColumn;
import javax.faces.el.ValueBinding;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;
import javax.xml.parsers.ParserConfigurationException;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.seasar.teeda.core.el.ELParser;
import org.seasar.teeda.core.el.impl.ValueBindingImpl;
import org.seasar.teeda.core.el.impl.commons.CommonsELParser;
import org.seasar.teeda.core.el.impl.commons.CommonsExpressionProcessorImpl;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.unit.TestUtil;
import org.seasar.teeda.core.unit.xmlunit.DifferenceListenerChain;
import org.seasar.teeda.core.unit.xmlunit.HtmlDomUtil;
import org.seasar.teeda.core.unit.xmlunit.IgnoreJsessionidDifferenceListener;
import org.seasar.teeda.core.unit.xmlunit.RegexpDifferenceListener;
import org.seasar.teeda.core.unit.xmlunit.TextTrimmingDifferenceListener;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * @author manhole
 */
public class HtmlDataTableRendererTest extends RendererTest {

    private HtmlDataTableRenderer renderer_;

    private MockHtmlDataTable htmlDataTable_;

    protected void setUp() throws Exception {
        super.setUp();
        renderer_ = createHtmlDataTableRenderer();
        htmlDataTable_ = new MockHtmlDataTable();
        htmlDataTable_.setRenderer(renderer_);
    }

    public void testEncodeBegin() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table", getResponseText());
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlDataTable_.setRendered(false);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlDataTable_);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncodeBegin_TableStyle() throws Exception {
        // ## Arrange ##
        htmlDataTable_.setStyle("s");
        htmlDataTable_.setStyleClass("t");
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table style=\"s\" class=\"t\"", getResponseText());
    }

    public void testEncodeBegin_Id() throws Exception {
        // ## Arrange ##
        htmlDataTable_.setId("aa");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table id=\"aa\"", getResponseText());
    }

    public void testEncode_NoValue() throws Exception {
        // ## Arrange ##

        // ## Act ##
        encodeByRenderer(renderer_, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table><tbody></tbody></table>", getResponseText());
    }

    public void testEncodeBegin_TableHeaderFacet() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        facet.setRenderer(new HtmlOutputTextRenderer());
        facet.setValue("a");
        htmlDataTable_.setHeader(facet);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals(
                "<table><thead><tr><th scope=\"colgroup\">a</th></tr></thead>",
                getResponseText());
    }

    public void testEncodeBegin_TableHeaderFacet2() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        final HtmlOutputTextRenderer textRenderer = new HtmlOutputTextRenderer();
        facet.setRenderer(textRenderer);
        {
            MockHtmlOutputText c = new MockHtmlOutputText();
            c.setValue("a");
            c.setRenderer(textRenderer);
            facet.getChildren().add(c);
        }
        {
            MockHtmlOutputText c = new MockHtmlOutputText();
            c.setValue("b");
            c.setRenderer(textRenderer);
            facet.getChildren().add(c);
        }
        htmlDataTable_.setHeader(facet);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals(
                "<table><thead><tr><th scope=\"colgroup\">ab</th></tr></thead>",
                getResponseText());
    }

    public void testEncodeBegin_TableHeaderStyle() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        facet.setRenderer(new HtmlOutputTextRenderer());
        facet.setValue("a");
        htmlDataTable_.setHeader(facet);
        htmlDataTable_.setHeaderClass("cc");
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals(
                "<table><thead><tr><th scope=\"colgroup\" class=\"cc\">a</th></tr>"
                        + "</thead>", getResponseText());
    }

    public void testEncodeBegin_TableHeaderFacetRenderFalse() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        facet.setRenderer(new HtmlOutputTextRenderer());
        facet.setValue("a");
        facet.setRendered(false);
        htmlDataTable_.setHeader(facet);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table", getResponseText());
    }

    public void testEncodeBegin_ColumnHeaderFacet() throws Exception {
        // ## Arrange ##
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c1");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setHeader(colFacet);
            htmlDataTable_.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c2");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setHeader(colFacet);
            htmlDataTable_.getChildren().add(col);
        }
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table><thead><tr>" + "<th colgroup=\"col\">c1</th>"
                + "<th colgroup=\"col\">c2</th>" + "</tr></thead>",
                getResponseText());
    }

    public void testEncodeBegin_ColumnHeaderStyle() throws Exception {
        // ## Arrange ##
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        htmlDataTable_.setHeaderClass("ccc");
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c1");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setHeader(colFacet);
            htmlDataTable_.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c2");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setHeader(colFacet);
            htmlDataTable_.getChildren().add(col);
        }
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table><thead><tr>"
                + "<th colgroup=\"col\" class=\"ccc\">c1</th>"
                + "<th colgroup=\"col\" class=\"ccc\">c2</th>"
                + "</tr></thead>", getResponseText());
    }

    public void testEncodeBegin_HeaderColumnRenderFalse() throws Exception {
        // ## Arrange ##
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        {
            UIColumn col = new UIColumn();
            col.setRendered(false);
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c1");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setHeader(colFacet);
            htmlDataTable_.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c2");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setHeader(colFacet);
            htmlDataTable_.getChildren().add(col);
        }
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals(
                "<table><thead><tr><th colgroup=\"col\">c2</th></tr></thead>",
                getResponseText());
    }

    public void testEncodeBegin_ColumnHeaderFacetRenderFalse() throws Exception {
        // ## Arrange ##
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c1");
            colFacet.setRenderer(htmlOutputTextRenderer);
            colFacet.setRendered(false);
            col.setHeader(colFacet);
            htmlDataTable_.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c2");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setHeader(colFacet);
            htmlDataTable_.getChildren().add(col);
        }
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table><thead><tr>" + "<th colgroup=\"col\"></th>"
                + "<th colgroup=\"col\">c2</th>" + "</tr></thead>",
                getResponseText());
    }

    public void testEncodeBegin_HeaderAndColumnsFacet() throws Exception {
        // ## Arrange ##
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        MockHtmlOutputText tableFacet = new MockHtmlOutputText();
        tableFacet.setRenderer(htmlOutputTextRenderer);
        tableFacet.setValue("a");
        htmlDataTable_.setHeader(tableFacet);

        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c1");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setHeader(colFacet);
            htmlDataTable_.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colHeaderFacet = new MockHtmlOutputText();
            colHeaderFacet.setValue("c2");
            colHeaderFacet.setRenderer(htmlOutputTextRenderer);
            col.setHeader(colHeaderFacet);
            htmlDataTable_.getChildren().add(col);
        }

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table><thead>"
                + "<tr><th colspan=\"2\" scope=\"colgroup\">a</th></tr>"
                + "<tr>" + "<th colgroup=\"col\">c1</th>"
                + "<th colgroup=\"col\">c2</th></tr>" + "</thead>",
                getResponseText());
    }

    public void testEncodeBegin_NotRenderHeaderColspanIfSingleColumn()
            throws Exception {
        // ## Arrange ##
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        MockHtmlOutputText tableHeaderFacet = new MockHtmlOutputText();
        tableHeaderFacet.setRenderer(htmlOutputTextRenderer);
        tableHeaderFacet.setValue("a");
        htmlDataTable_.setHeader(tableHeaderFacet);

        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("col1");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setHeader(colFacet);
            htmlDataTable_.getChildren().add(col);
        }

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table><thead>"
                + "<tr><th scope=\"colgroup\">a</th></tr>"
                + "<tr><th colgroup=\"col\">col1</th></tr>" + "</thead>",
                getResponseText());
    }

    public void testEncodeBegin_TableFooterFacet() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        facet.setRenderer(new HtmlOutputTextRenderer());
        facet.setValue("a");
        htmlDataTable_.setFooter(facet);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table><tfoot><tr><td>a</td></tr></tfoot>",
                getResponseText());
    }

    public void testEncodeBegin_TableFooterFacet2() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        final HtmlOutputTextRenderer textRenderer = new HtmlOutputTextRenderer();
        facet.setRenderer(textRenderer);
        {
            MockHtmlOutputText c = new MockHtmlOutputText();
            c.setValue("a");
            c.setRenderer(textRenderer);
            facet.getChildren().add(c);
        }
        {
            MockHtmlOutputText c = new MockHtmlOutputText();
            c.setValue("b");
            c.setRenderer(textRenderer);
            facet.getChildren().add(c);
        }
        htmlDataTable_.setFooter(facet);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table><tfoot><tr><td>ab</td></tr></tfoot>",
                getResponseText());
    }

    public void testEncodeBegin_TableFooterStyle() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        facet.setRenderer(new HtmlOutputTextRenderer());
        facet.setValue("a");
        htmlDataTable_.setFooter(facet);
        htmlDataTable_.setFooterClass("d");
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table><tfoot><tr><td class=\"d\">a</td></tr></tfoot>",
                getResponseText());
    }

    public void testEncodeBegin_TableFooterFacetRenderFalse() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        facet.setRenderer(new HtmlOutputTextRenderer());
        facet.setValue("a");
        facet.setRendered(false);
        htmlDataTable_.setFooter(facet);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table", getResponseText());
    }

    public void testEncodeBegin_ColumnFooterFacet() throws Exception {
        // ## Arrange ##
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c1");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setFooter(colFacet);
            htmlDataTable_.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c2");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setFooter(colFacet);
            htmlDataTable_.getChildren().add(col);
        }
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table><tfoot><tr><td>c1</td><td>c2</td></tr></tfoot>",
                getResponseText());
    }

    public void testEncodeBegin_ColumnFooterStyle() throws Exception {
        // ## Arrange ##
        htmlDataTable_.setFooterClass("ee");
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c1");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setFooter(colFacet);
            htmlDataTable_.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c2");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setFooter(colFacet);
            htmlDataTable_.getChildren().add(col);
        }
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table>" + "<tfoot>" + "<tr><td class=\"ee\">c1</td>"
                + "<td class=\"ee\">c2</td></tr>" + "</tfoot>",
                getResponseText());
    }

    public void testEncodeBegin_FooterColumnRenderFalse() throws Exception {
        // ## Arrange ##
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        {
            UIColumn col = new UIColumn();
            col.setRendered(false);
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c1");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setFooter(colFacet);
            htmlDataTable_.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c2");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setFooter(colFacet);
            htmlDataTable_.getChildren().add(col);
        }
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table><tfoot><tr><td>c2</td></tr></tfoot>",
                getResponseText());
    }

    public void testEncodeBegin_ColumnFooterFacetRenderFalse() throws Exception {
        // ## Arrange ##
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c1");
            colFacet.setRenderer(htmlOutputTextRenderer);
            colFacet.setRendered(false);
            col.setFooter(colFacet);
            htmlDataTable_.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c2");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setFooter(colFacet);
            htmlDataTable_.getChildren().add(col);
        }
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table><tfoot><tr><td></td><td>c2</td></tr></tfoot>",
                getResponseText());
    }

    public void testEncodeBegin_FooterAndColumnsFacet() throws Exception {
        // ## Arrange ##
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        MockHtmlOutputText tableHeaderFacet = new MockHtmlOutputText();
        tableHeaderFacet.setRenderer(htmlOutputTextRenderer);
        tableHeaderFacet.setValue("a");
        htmlDataTable_.setFooter(tableHeaderFacet);

        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("col1");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setFooter(colFacet);
            htmlDataTable_.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("col2");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setFooter(colFacet);
            htmlDataTable_.getChildren().add(col);
        }

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table><tfoot>" + "<tr><td colspan=\"2\">a</td></tr>"
                + "<tr><td>col1</td>" + "<td>col2</td></tr>" + "</tfoot>",
                getResponseText());
    }

    public void testEncodeBegin_NotRenderFooterColspanIfSingleColumn()
            throws Exception {
        // ## Arrange ##
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        MockHtmlOutputText tableHeaderFacet = new MockHtmlOutputText();
        tableHeaderFacet.setRenderer(htmlOutputTextRenderer);
        tableHeaderFacet.setValue("a");
        htmlDataTable_.setFooter(tableHeaderFacet);

        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("col1");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setFooter(colFacet);
            htmlDataTable_.getChildren().add(col);
        }

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table><tfoot>" + "<tr><td>a</td></tr>"
                + "<tr><td>col1</td></tr>" + "</tfoot>", getResponseText());
    }

    public void testSplitByComma() throws Exception {
        // ## Arrange ##
        // ## Act ##
        String[] result = renderer_.splitByComma("a, b  ,c , d");

        // ## Assert ##
        assertEquals(4, result.length);
        assertEquals("a", result[0]);
        assertEquals("b", result[1]);
        assertEquals("c", result[2]);
        assertEquals("d", result[3]);
    }

    public void testEncodeChildren1() throws Exception {
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        htmlDataTable_.setValue(new String[] { "a", "b", "c" });
        htmlDataTable_.setVar("fooVar");
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText htmlOutputText = new MockHtmlOutputText();
            htmlOutputText.setRenderer(htmlOutputTextRenderer);
            htmlOutputText.setValue("Z");
            col.getChildren().add(htmlOutputText);
            htmlDataTable_.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText htmlOutputText = new MockHtmlOutputText();
            htmlOutputText.setRenderer(htmlOutputTextRenderer);
            ELParser parser = new CommonsELParser();
            parser.setExpressionProcessor(new CommonsExpressionProcessorImpl());
            ValueBinding vb = new ValueBindingImpl(getFacesContext()
                    .getApplication(), "#{fooVar}", parser);
            htmlOutputText.setValueBinding("value", vb);
            col.getChildren().add(htmlOutputText);
            htmlDataTable_.getChildren().add(col);
        }

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeChildren(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<tbody>" + "<tr><td>Z</td><td>a</td></tr>"
                + "<tr><td>Z</td><td>b</td></tr>"
                + "<tr><td>Z</td><td>c</td></tr>" + "</tbody>",
                getResponseText());
    }

    public void testEncodeChildren_WithNestedChildren() throws Exception {
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        HtmlPanelGroupRenderer htmlPanelGroupRenderer = new HtmlPanelGroupRenderer();
        htmlDataTable_.setValue(new String[] { "a", "b", "c" });
        htmlDataTable_.setVar("fooVar");
        {
            UIColumn col = new UIColumn();
            {
                MockHtmlOutputText htmlOutputText = new MockHtmlOutputText();
                htmlOutputText.setRenderer(htmlOutputTextRenderer);
                ELParser parser = new CommonsELParser();
                parser
                        .setExpressionProcessor(new CommonsExpressionProcessorImpl());
                ValueBinding vb = new ValueBindingImpl(getFacesContext()
                        .getApplication(), "#{fooVar}", parser);
                htmlOutputText.setValueBinding("value", vb);
                col.getChildren().add(htmlOutputText);
            }
            {
                MockHtmlPanelGroup group = new MockHtmlPanelGroup();
                group.setRenderer(htmlPanelGroupRenderer);
                {
                    MockHtmlOutputText htmlOutputText = new MockHtmlOutputText();
                    htmlOutputText.setRenderer(htmlOutputTextRenderer);
                    htmlOutputText.setValue("y");
                    group.getChildren().add(htmlOutputText);
                }
                {
                    MockHtmlOutputText htmlOutputText = new MockHtmlOutputText();
                    htmlOutputText.setRenderer(htmlOutputTextRenderer);
                    htmlOutputText.setValue("z");
                    group.getChildren().add(htmlOutputText);
                }
                col.getChildren().add(group);
            }
            htmlDataTable_.getChildren().add(col);
        }

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeChildren(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<tbody>" + "<tr><td>ayz</td></tr>"
                + "<tr><td>byz</td></tr>" + "<tr><td>cyz</td></tr>"
                + "</tbody>", getResponseText());
    }

    public void testEncodeChildren_SetFirst() throws Exception {
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        htmlDataTable_.setValue(new String[] { "a", "b", "c" });
        htmlDataTable_.setVar("fooVar");
        htmlDataTable_.setFirst(1);
        {
            UIColumn col = new UIColumn();
            col.setRendered(false);
            MockHtmlOutputText htmlOutputText = new MockHtmlOutputText();
            htmlOutputText.setRenderer(htmlOutputTextRenderer);
            htmlOutputText.setValue("Z");
            col.getChildren().add(htmlOutputText);
            htmlDataTable_.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText htmlOutputText = new MockHtmlOutputText();
            htmlOutputText.setRenderer(htmlOutputTextRenderer);
            ELParser parser = new CommonsELParser();
            parser.setExpressionProcessor(new CommonsExpressionProcessorImpl());
            ValueBinding vb = new ValueBindingImpl(getFacesContext()
                    .getApplication(), "#{fooVar}", parser);
            htmlOutputText.setValueBinding("value", vb);
            col.getChildren().add(htmlOutputText);
            htmlDataTable_.getChildren().add(col);
        }

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeChildren(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<tbody>" + "<tr><td>b</td></tr>" + "<tr><td>c</td></tr>"
                + "</tbody>", getResponseText());
    }

    public void testEncodeChildren_SetRows() throws Exception {
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        htmlDataTable_.setValue(new String[] { "a", "b", "c", "d" });
        htmlDataTable_.setVar("fooVar");
        htmlDataTable_.setRows(2);
        {
            UIColumn col = new UIColumn();
            col.setRendered(false);
            MockHtmlOutputText htmlOutputText = new MockHtmlOutputText();
            htmlOutputText.setRenderer(htmlOutputTextRenderer);
            htmlOutputText.setValue("Z");
            col.getChildren().add(htmlOutputText);
            htmlDataTable_.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText htmlOutputText = new MockHtmlOutputText();
            htmlOutputText.setRenderer(htmlOutputTextRenderer);
            ELParser parser = new CommonsELParser();
            parser.setExpressionProcessor(new CommonsExpressionProcessorImpl());
            ValueBinding vb = new ValueBindingImpl(getFacesContext()
                    .getApplication(), "#{fooVar}", parser);
            htmlOutputText.setValueBinding("value", vb);
            col.getChildren().add(htmlOutputText);
            htmlDataTable_.getChildren().add(col);
        }

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeChildren(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<tbody>" + "<tr><td>a</td></tr>" + "<tr><td>b</td></tr>"
                + "</tbody>", getResponseText());
    }

    public void testEncodeChildren_RowAndColumnStyle() throws Exception {
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        htmlDataTable_.setValue(new String[] { "a", "b", "c", "d", "e" });
        htmlDataTable_.setVar("fooVar");
        htmlDataTable_.setColumnClasses("c1, c2, c3");
        htmlDataTable_.setRowClasses("r1, r2");
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText htmlOutputText = new MockHtmlOutputText();
            htmlOutputText.setRenderer(htmlOutputTextRenderer);
            htmlOutputText.setValue("Z");
            col.getChildren().add(htmlOutputText);
            htmlDataTable_.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText htmlOutputText = new MockHtmlOutputText();
            htmlOutputText.setRenderer(htmlOutputTextRenderer);
            htmlOutputText.setValue("Y");
            col.getChildren().add(htmlOutputText);
            htmlDataTable_.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText htmlOutputText = new MockHtmlOutputText();
            htmlOutputText.setRenderer(htmlOutputTextRenderer);
            htmlOutputText.setValue("X");
            col.getChildren().add(htmlOutputText);
            htmlDataTable_.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText htmlOutputText = new MockHtmlOutputText();
            htmlOutputText.setRenderer(htmlOutputTextRenderer);
            ELParser parser = new CommonsELParser();
            parser.setExpressionProcessor(new CommonsExpressionProcessorImpl());
            ValueBinding vb = new ValueBindingImpl(getFacesContext()
                    .getApplication(), "#{fooVar}", parser);
            htmlOutputText.setValueBinding("value", vb);
            col.getChildren().add(htmlOutputText);
            htmlDataTable_.getChildren().add(col);
        }

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeChildren(context, htmlDataTable_);

        // ## Assert ##
        final String readText = TestUtil.readText(getClass(),
                "testEncodeChildren_RowAndColumnStyle.html", "UTF-8");
        final String expected = extract(readText);
        Diff diff = diff(expected, getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        // attributes
        {
            htmlDataTable_.setBgcolor("gray");
            htmlDataTable_.setBorder(3);
            htmlDataTable_.setCellpadding("c");
            htmlDataTable_.setCellspacing("d");
            htmlDataTable_.setColumnClasses("e1, e2");
            htmlDataTable_.setDir("f");
            htmlDataTable_.setFooterClass("g");
            htmlDataTable_.setFrame("h");
            htmlDataTable_.setHeaderClass("i");
            htmlDataTable_.setLang("j");
            htmlDataTable_.setOnclick("k");
            htmlDataTable_.setOndblclick("l");
            htmlDataTable_.setOnkeydown("m");
            htmlDataTable_.setOnkeypress("n");
            htmlDataTable_.setOnkeyup("o");
            htmlDataTable_.setOnmousedown("p");
            htmlDataTable_.setOnmousemove("q");
            htmlDataTable_.setOnmouseout("r");
            htmlDataTable_.setOnmouseover("s");
            htmlDataTable_.setOnmouseup("t");
            htmlDataTable_.setRowClasses("u1, u2");
            htmlDataTable_.setRules("v");
            htmlDataTable_.setStyle("w");
            htmlDataTable_.setStyleClass("x");
            htmlDataTable_.setSummary("y");
            htmlDataTable_.setTitle("z");
            htmlDataTable_.setWidth("1");
        }
        // table header, footer
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        {
            MockHtmlOutputText header = new MockHtmlOutputText();
            header.setRenderer(htmlOutputTextRenderer);
            header.setValue("tableHeader");
            htmlDataTable_.setHeader(header);

            MockHtmlOutputText footer = new MockHtmlOutputText();
            footer.setRenderer(htmlOutputTextRenderer);
            footer.setValue("tableFooter");
            htmlDataTable_.setFooter(footer);
        }
        htmlDataTable_.setFirst(4);
        htmlDataTable_.setRows(3);
        htmlDataTable_.setVar("barVar");
        htmlDataTable_.setId("A");
        {
            Map m0 = new HashMap();
            Map m1 = new HashMap();
            Map m2 = new HashMap();
            Map m3 = new HashMap();
            Map m4 = new HashMap();
            Map m5 = new HashMap();
            Map m6 = new HashMap();
            Map m7 = new HashMap();
            Map m8 = new HashMap();
            Map m9 = new HashMap();
            m0.put("k1", "a0");
            m1.put("k1", "a1");
            m2.put("k1", "a2");
            m3.put("k1", "a3");
            m4.put("k1", "a4");
            m5.put("k1", "a5");
            m6.put("k1", "a6");
            m7.put("k1", "a7");
            m8.put("k1", "a8");
            m9.put("k1", "a9");
            m0.put("k2", "b0");
            m1.put("k2", "b1");
            m2.put("k2", "b2");
            m3.put("k2", "b3");
            m4.put("k2", "b4");
            m5.put("k2", "b5");
            m6.put("k2", "b6");
            m7.put("k2", "b7");
            m8.put("k2", "b8");
            m9.put("k2", "b9");
            htmlDataTable_.setValue(new Map[] { m0, m1, m2, m3, m4, m5, m6, m7,
                    m8, m9 });
        }
        // columns
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText htmlOutputText = new MockHtmlOutputText();
            htmlOutputText.setRenderer(htmlOutputTextRenderer);
            ELParser parser = new CommonsELParser();
            parser.setExpressionProcessor(new CommonsExpressionProcessorImpl());
            ValueBinding vb = new ValueBindingImpl(getFacesContext()
                    .getApplication(), "#{barVar.k1}", parser);
            htmlOutputText.setValueBinding("value", vb);
            col.getChildren().add(htmlOutputText);

            MockHtmlOutputText header = new MockHtmlOutputText();
            header.setRenderer(htmlOutputTextRenderer);
            header.setValue("col1Header");
            col.setHeader(header);

            MockHtmlOutputText footer = new MockHtmlOutputText();
            footer.setRenderer(htmlOutputTextRenderer);
            footer.setValue("col1Footer");
            col.setFooter(footer);

            htmlDataTable_.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText htmlOutputText = new MockHtmlOutputText();
            htmlOutputText.setRenderer(htmlOutputTextRenderer);
            ELParser parser = new CommonsELParser();
            parser.setExpressionProcessor(new CommonsExpressionProcessorImpl());
            ValueBinding vb = new ValueBindingImpl(getFacesContext()
                    .getApplication(), "#{barVar.k2}", parser);
            htmlOutputText.setValueBinding("value", vb);
            col.getChildren().add(htmlOutputText);

            MockHtmlOutputText header = new MockHtmlOutputText();
            header.setRenderer(htmlOutputTextRenderer);
            header.setValue("col2Header");
            col.setHeader(header);

            MockHtmlOutputText footer = new MockHtmlOutputText();
            footer.setRenderer(htmlOutputTextRenderer);
            footer.setValue("col2Footer");
            col.setFooter(footer);

            htmlDataTable_.getChildren().add(col);
        }

        MockFacesContext context = getFacesContext();
        encodeByRenderer(renderer_, context, htmlDataTable_);

        final String readText = TestUtil.readText(getClass(),
                "testEncode_WithAllAttributes.html", "UTF-8");
        final String expected = extract(readText);
        Diff diff = diff(expected, getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(true, renderer_.getRendersChildren());
    }

    private HtmlDataTableRenderer createHtmlDataTableRenderer() {
        return (HtmlDataTableRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        HtmlDataTableRenderer renderer = new HtmlDataTableRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
    }

    private String extract(final String s) {
        final String BEGIN = "<!-- BEGIN -->";
        final String END = "<!-- END -->";
        final int begin = s.indexOf(BEGIN);
        if (-1 < begin) {
            final int end = s.indexOf(END, begin + BEGIN.length());
            if (-1 < end) {
                return s.substring(begin + BEGIN.length(), end);
            }
        }
        return s;
    }

    private Diff diff(final String expected, final String actual)
            throws SAXException, IOException, ParserConfigurationException {
        Document cDoc = XMLUnit.buildDocument(XMLUnit.getControlParser(),
                new StringReader(expected));
        Document tDoc = XMLUnit.buildDocument(XMLUnit.getTestParser(),
                new StringReader(actual));
        HtmlDomUtil.removeBlankTextNode(cDoc.getChildNodes());
        HtmlDomUtil.removeBlankTextNode(tDoc.getChildNodes());
        Diff diff = new Diff(cDoc, tDoc);
        DifferenceListenerChain chain = new DifferenceListenerChain();
        chain.addDifferenceListener(new TextTrimmingDifferenceListener());
        chain.addDifferenceListener(new IgnoreJsessionidDifferenceListener());
        chain.addDifferenceListener(new RegexpDifferenceListener());
        diff.overrideDifferenceListener(chain);
        return diff;
    }

}

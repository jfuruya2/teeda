package javax.faces.internal;

import javax.faces.component.html.HtmlInputText;
import javax.faces.convert.IntegerConverter;

import junit.framework.TestCase;
import junitx.framework.ArrayAssert;

import org.seasar.teeda.core.mock.MockUIComponent;

public class ConvertUtilsTest extends TestCase {

    public void testCreateConversionMessage() throws Exception {
        IntegerConverter converter = new IntegerConverter();
        assertEquals(converter.getClass().getName() + ".CONVERSION",
                ConvertUtils.createConversionMessage(converter));
    }

    public void testCreateExceptionMessageArgs_withComponentId()
            throws Exception {
        MockUIComponent component = new MockUIComponent();
        component.setId("aaa");
        Object[] args = ConvertUtils.createExceptionMessageArgs(component,
                "bbb");
        ArrayAssert.assertEquals(new String[] { "aaa", "bbb" }, args);
    }

    public void testCreateExceptionMessageArgs_withLabel() throws Exception {
        HtmlInputText component = new HtmlInputText();
        component.setId("aaa");
        component.setLabel("ccc");
        Object[] args = ConvertUtils.createExceptionMessageArgs(component,
                "bbb");
        ArrayAssert.assertEquals(new String[] { "ccc", "bbb" }, args);
    }

}

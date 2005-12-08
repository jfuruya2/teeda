package javax.faces.component;

import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.unit.TeedaTestCase;


public class TestUIMessage extends TeedaTestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestUIMessage.class);
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Constructor for TestUIMessage.
	 * @param arg0
	 */
	public TestUIMessage(String arg0) {
		super(arg0);
	}

	public void testUIMessage(){
		UIMessage message = new UIMessage();
		assertEquals(message.getRendererType(), "javax.faces.Message");
	}

	public void testGetFamily(){
		UIMessage message = new UIMessage();
		assertEquals(UIMessage.COMPONENT_FAMILY, message.getFamily());
	}

	public void testGetFor(){
		UIMessage message = new UIMessage();
		MockValueBinding vb = new MockValueBinding();
		vb.setValue(getFacesContext(), "aaa");
		message.setValueBinding("for", vb);
		assertEquals("aaa", message.getFor());
	}
	
	public void testSetFor(){
		UIMessage message = new UIMessage();
		message.setFor("FOR");
		assertEquals("FOR", message.getFor());
	}
	
	public void testIsShowDetail(){
		UIMessage message = new UIMessage();
		Boolean value = Boolean.TRUE;
		MockValueBinding vb = new MockValueBinding();
		vb.setValue(getFacesContext(), value);
		message.setValueBinding("showDetail", vb);
		assertTrue(message.isShowDetail());
	}
	
	public void testSetShowDetail(){
		UIMessage message = new UIMessage();
		message.setShowDetail(true);
		assertTrue(message.isShowDetail());
	}

	public void testIsShowSummary(){
		UIMessage message = new UIMessage();
		Boolean value = Boolean.TRUE;
		MockValueBinding vb = new MockValueBinding();
		vb.setValue(getFacesContext(), value);
		message.setValueBinding("showSummary", vb);
		assertTrue(message.isShowSummary());
	}
	
	public void testSetShowSummary(){
		UIMessage message = new UIMessage();
		message.setShowSummary(true);
		assertTrue(message.isShowSummary());
	}

}

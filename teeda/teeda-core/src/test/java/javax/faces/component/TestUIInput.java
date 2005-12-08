package javax.faces.component;

import org.seasar.teeda.core.mock.MockFacesContextImpl;
import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class TestUIInput extends TeedaTestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestUIInput.class);
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
	 * Constructor for TestUIInput.
	 * 
	 * @param arg0
	 */
	public TestUIInput(String arg0) {
		super(arg0);
	}

	public void testGetFamily() {
		UIInput input = new UIInput();
		assertEquals(UIInput.COMPONENT_FAMILY, input.getFamily());
	}

	public void testSetSubmittedValue() {
		UIInput input = new UIInput();
		String value = "aaa";
		input.setSubmittedValue(value);
		assertEquals(value, input.getSubmittedValue());
	}

	public void testSetValue() {
		UIInput input = new UIInput();
		String value = "bbb";
		input.setValue(value);
		assertEquals(value, input.getValue());
	}

	public void testSetLocalValueSet() {
		UIInput input = new UIInput();
		input.setLocalValueSet(true);
		assertEquals(true, input.isLocalValueSet());
	}

	public void testSetRequired() {
		UIInput input = new UIInput();
		input.setRequired(true);
		assertEquals(true, input.isRequired());
	}

	public void testIsRequired() {
		UIInput input = new UIInput();
		MockValueBinding vb = new MockValueBinding();
		boolean value = true;
		vb.setValue(new MockFacesContextImpl(), new Boolean(value));
		input.setValueBinding("required", vb);
		assertEquals(value, input.isRequired());

	}

	public void testSetValid() {
		UIInput input = new UIInput();
		input.setValid(true);
		assertEquals(true, input.isValid());
	}

	public void testIsValid() {
		UIInput input = new UIInput();
		MockValueBinding vb = new MockValueBinding();
		boolean value = true;
		vb.setValue(getFacesContext(), new Boolean(value));
		input.setValueBinding("valid", vb);
		assertEquals(value, input.isValid());
	}

	public void testSetImmediate() {
		UIInput input = new UIInput();
		input.setImmediate(true);
		assertEquals(true, input.isImmediate());
	}

	public void testIsImmediate() {
		UIInput input = new UIInput();
		MockValueBinding vb = new MockValueBinding();
		boolean value = true;
		vb.setValue(getFacesContext(), new Boolean(value));
		input.setValueBinding("immediate", vb);
		assertEquals(value, input.isImmediate());
	}

	public void testGetValidator() {
		throw new RuntimeException("not impl yet.");
	}

	public void testSetValidator() {
		throw new RuntimeException("not impl yet.");
	}

}

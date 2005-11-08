package javax.faces.convert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.faces.component.UIViewRoot;
import javax.faces.mock.MockFacesContext;
import javax.faces.mock.MockUIComponent;

import junit.framework.TestCase;


public class TestDateTimeConverter extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestDateTimeConverter.class);
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
	 * Constructor for TestDateTimeConverter.
	 * @param arg0
	 */
	public TestDateTimeConverter(String arg0) {
		super(arg0);
	}

	public void testGetAsObject() {
		
		MockFacesContext context = new MockFacesContext();
		MockUIComponent component = new MockUIComponent();
		
		DateTimeConverter converter = new DateTimeConverter();
		
		try{
			converter.getAsObject(null, component, "");
			fail();
		}catch(NullPointerException e){
			assertTrue(true);
		}
		
		try{
			converter.getAsObject(context, null, "");
			fail();
		}catch(NullPointerException e){
			assertTrue(true);
		}

		Object o = converter.getAsObject(context, component, null);
		assertNull(o);
		
		o = converter.getAsObject(context, component, " ");
		assertNull(o);

		Locale locale = Locale.getDefault();
		converter.setLocale(locale);
		
		TimeZone timeZone = TimeZone.getDefault();
		converter.setTimeZone(timeZone);
		
		String pattern = "yyyy/MM/dd";
		converter.setPattern(pattern);
		
		String dateValue = "2005/08/01";
		o = converter.getAsObject(context, component, dateValue);
				
		Date dateTarget = createDateTarget(pattern, locale, dateValue);
		
		Date date = (Date)o;
		assertEquals(date, dateTarget);

		converter.setTimeZone(null);
		
		o = converter.getAsObject(context, component, dateValue);
		assertEquals((Date)o, dateTarget);
		
		
		DateTimeConverter converter2 = new DateTimeConverter();
		
		UIViewRoot viewRoot2 = new UIViewRoot();
		viewRoot2.setLocale(Locale.US);
		context.setViewRoot(viewRoot2);

		TimeZone timeZone2 = TimeZone.getDefault();		
		TimeZone.setDefault(converter2.getTimeZone());
		
		pattern = "M/d/yy";
		converter2.setPattern(pattern);
		
		dateValue = "8/3/05";
		Object o2 = converter2.getAsObject(context, component, dateValue);
		
		dateTarget = createDateTarget(pattern, Locale.US, dateValue);
		
		assertEquals((Date)o2, dateTarget);
		
		TimeZone.setDefault(timeZone2);

		
		DateTimeConverter converter3 = new DateTimeConverter();

		converter3.setTimeZone(TimeZone.getDefault());
		
		UIViewRoot viewRoot3 = new UIViewRoot();
		Locale locale3 = Locale.GERMAN;
		viewRoot3.setLocale(locale3);
		context.setViewRoot(viewRoot3);
		
		String style = DateTimeConverter.STYLE_MEDIUM;
		converter3.setDateStyle(style);
		
		String type = DateTimeConverter.TYPE_DATE;
		converter3.setType(type);
		
		dateValue = "04.08.2005";
		Object o3 = converter3.getAsObject(context, component, dateValue);
		
		SimpleDateFormat format = (SimpleDateFormat)DateFormat.getDateInstance(DateFormat.MEDIUM, locale3);

		try{
			dateTarget = format.parse(dateValue);
		}catch(ParseException e){
			fail();
		}
		
		assertEquals((Date)o3, dateTarget);
		
		
		DateTimeConverter converter4 = new DateTimeConverter();
		
		converter4.setTimeZone(TimeZone.getDefault());
		converter4.setPattern("yyyy/MM/dd");
		
		try{
			converter4.getAsObject(context, component, "200508/04");
			fail();
		}catch(Exception e){
			//TODO somehow need catching ConverterException 
			assertTrue(true);
		}
		
		
	}
	
	public void testGetAsString() {
		
		MockFacesContext context = new MockFacesContext();
		MockUIComponent component = new MockUIComponent();
		
		DateTimeConverter converter = new DateTimeConverter();
		String value = converter.getAsString(context, component, null);
		
		assertEquals(value, "");
		
		DateTimeConverter converter2 = new DateTimeConverter();
		String value2 = converter2.getAsString(context, component, "a");
		
		assertEquals(value2, "a");
		
		DateTimeConverter converter3 = new DateTimeConverter();
		Locale defaultLocale = Locale.getDefault();
		converter3.setLocale(defaultLocale);
		
		String pattern3 = "yy/MM/dd";
		converter3.setPattern(pattern3);
		
		converter3.setTimeZone(TimeZone.getDefault());
		
		Date date3 = new Date();
		String value3 = converter3.getAsString(context, component, date3);
		
		String val = createDateFormat(pattern3, defaultLocale, date3);
		assertEquals(value3, val);
		
		
		DateTimeConverter converter4 = new DateTimeConverter();
		Locale usLocale = Locale.US;
		converter4.setLocale(usLocale);
		converter4.setTimeZone(TimeZone.getDefault());
		
		Date date4 = new Date();
		String value4 = converter4.getAsString(context, component, date4);
		
		SimpleDateFormat formatter = (SimpleDateFormat)DateFormat.getDateInstance(3, usLocale);
		String val4 = formatter.format(date4);
		
		assertNotSame(value4, val4);
		System.out.println(value4);
		System.out.println(val4);
		
	}

	public void testIsTransient() {
		DateTimeConverter converter = new DateTimeConverter();
		converter.setTransient(true);
		assertTrue(converter.isTransient());
	}

	public void testSetTransient() {
		
		DateTimeConverter converter = new DateTimeConverter();
		converter.setTransient(true);
		assertTrue(converter.isTransient());
	}

	public void testSaveState() {
		DateTimeConverter converter = new DateTimeConverter();
		MockFacesContext context = new MockFacesContext();
		
		String dateStyle = DateTimeConverter.STYLE_FULL;
		Locale locale = Locale.getDefault();
		String pattern = "yy.MM/dd";
		String timeStyle = DateTimeConverter.STYLE_MEDIUM;
		TimeZone timeZone = TimeZone.getDefault();
		String type = DateTimeConverter.TYPE_DATE;
		
		converter.setDateStyle(dateStyle);
		converter.setLocale(locale);
		converter.setPattern(pattern);
		converter.setTimeStyle(timeStyle);
		converter.setTimeZone(timeZone);
		converter.setType(type);
		
		Object o = converter.saveState(context);
		assertTrue(o instanceof Object[]);
		
		Object[] values = (Object[])o;
		
		assertEquals(values[0], dateStyle);
		
	}

	public void testRestoreState() {
		DateTimeConverter converter = new DateTimeConverter();
		MockFacesContext context = new MockFacesContext();
		
		String dateStyle = DateTimeConverter.STYLE_FULL;
		Locale locale = Locale.getDefault();
		String pattern = "yy.MM/dd";
		String timeStyle = DateTimeConverter.STYLE_MEDIUM;
		TimeZone timeZone = TimeZone.getDefault();
		String type = DateTimeConverter.TYPE_DATE;

		Object[] values = new Object[]{dateStyle, locale, pattern, timeStyle, timeZone, type};
		
		converter.restoreState(context, values);
		
		assertEquals(converter.getLocale(), locale);
		
	}

	public void testGetDateStyle() {
		
		DateTimeConverter converter = new DateTimeConverter();
		assertEquals(DateTimeConverter.STYLE_DEFAULT, converter.getDateStyle());
		
		converter.setDateStyle(DateTimeConverter.STYLE_FULL);
		assertEquals(DateTimeConverter.STYLE_FULL, converter.getDateStyle());
	}

	public void testSetDateStyle() {

		DateTimeConverter converter = new DateTimeConverter();
		
		converter.setDateStyle(DateTimeConverter.STYLE_LONG);
		assertEquals(DateTimeConverter.STYLE_LONG, converter.getDateStyle());
	}

	public void testGetLocale(){
		DateTimeConverter converter = new DateTimeConverter();
		Locale locale = Locale.getDefault();
		
		converter.setLocale(locale);
		assertEquals(locale, converter.getLocale());
		
	}

	private static Date createDateTarget(String pattern, Locale locale, String date){
		DateFormat formatter = new SimpleDateFormat(pattern, locale);
		try{
			return formatter.parse(date);
		}catch(Exception e){
			fail();
		}
		return null;
	}
	
	private static String createDateFormat(String pattern, Locale locale, Object date){
		DateFormat formatter = new SimpleDateFormat(pattern, locale);
		try{
			return formatter.format(date);
		}catch(Exception e){
			fail();
		}
		return null;		
	}

}

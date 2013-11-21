package com.inthinc.pro.convert;

import static junit.framework.Assert.assertEquals;
import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;

import java.util.Map;
import java.util.TimeZone;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.junit.Before;
import org.junit.Test;

public class SQLDateToUtilDateConverterTest {
	private FacesContext context;

	@Before
	public void setup() {
		context = FacesContext.getCurrentInstance();
	}

	@Test
	public void testGetASString() {
		final UIComponent uiComponent = createMock(UIComponent.class);
		final TimeZone timeZone = TimeZone.getTimeZone("Europe/Sofia");
		final Map<String, Object> attributes = createMock(Map.class);
		expect(uiComponent.getAttributes()).andReturn(attributes).times(3);
		expect(attributes.get("timeZone")).andReturn(timeZone).anyTimes();
		expect(attributes.get("pattern")).andReturn("MMM d, yyyy").anyTimes();
		replay(attributes, uiComponent);
		final SQLDateToUtilDateConverter converter = new SQLDateToUtilDateConverter();
		final String date = converter.getAsString(context, uiComponent, new java.sql.Date(113, 9, 31));
		assertEquals("Oct 31, 2013", date);
	}

	@Test
	public void testGetASStringTimeZone() {
		final UIComponent uiComponent = createMock(UIComponent.class);
		final TimeZone timeZone = TimeZone.getTimeZone("UTC");
		final Map<String, Object> attributes = createMock(Map.class);
		expect(uiComponent.getAttributes()).andReturn(attributes).times(3);
		expect(attributes.get("timeZone")).andReturn(timeZone).anyTimes();
		expect(attributes.get("pattern")).andReturn("MMM d, yyyy").anyTimes();
		replay(attributes, uiComponent);
		final SQLDateToUtilDateConverter converter = new SQLDateToUtilDateConverter();
		final String date = converter.getAsString(context, uiComponent, new java.sql.Date(113, 9, 31));
		assertEquals("Oct 31, 2013", date);
	}
}

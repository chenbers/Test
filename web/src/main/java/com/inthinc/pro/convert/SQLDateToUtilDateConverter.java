package com.inthinc.pro.convert;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import org.apache.commons.lang.NotImplementedException;

import com.inthinc.pro.util.MessageUtil;

public class SQLDateToUtilDateConverter extends BaseConverter {

	@Override
	public Object getAsObject(final FacesContext context, final UIComponent component, final String value)
			throws ConverterException {
		if ((value != null) && (!value.isEmpty())) {
			throw new NotImplementedException();
		}
		return null;
	}

	@Override
	public String getAsString(final FacesContext facesContext, final UIComponent uiComponent, final Object value) {
		if (value instanceof Date) {
			final TimeZone timeZone = (TimeZone) uiComponent.getAttributes().get("timeZone");
			if (timeZone != null) {
				return getDateTimeZone(uiComponent, value, timeZone);
			} else {
				final TimeZone timeZoneUTC = TimeZone.getTimeZone("UTC");
				return getDateTimeZone(uiComponent, value, timeZoneUTC);
			}
		}
		return null;
	}

	/**
	 * @param uiComponent
	 * @param value
	 * @param timeZone
	 * @return date as string
	 */
	private String getDateTimeZone(final UIComponent uiComponent, final Object value, final TimeZone timeZone) {
		final Calendar instance = Calendar.getInstance(timeZone);
		instance.setTime((Date) value);
		final String pattern = (String) uiComponent.getAttributes().get("pattern");
		if ((pattern != null) && (!pattern.isEmpty())) {
			return formatDate(instance, pattern);
		} else {
			final String patternDefault = MessageUtil.getMessageString("dateTimeFormat");
			return formatDate(instance, patternDefault);
		}
	}

	/**
	 * @param instance
	 * @param pattern
	 * @return date as string using specified format
	 */
	private String formatDate(final Calendar instance, final String pattern) {
		final SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(instance.getTime());
	}
}

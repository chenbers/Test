package com.inthinc.pro.convert;



import java.text.SimpleDateFormat;
import java.util.TimeZone;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import com.inthinc.pro.util.DateUtil;


/**
 * Converts from integer time in seconds to a date in the logged on user's timezone.
 *
 */
public class SecondsDateConverter implements Converter
{
    // TODO: let user set in UI
    public static String MST_TZ = "US/Mountain";

    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        formatter.setTimeZone(TimeZone.getTimeZone(MST_TZ));
		if (value instanceof Integer)
		{
			// value is in seconds
			value = DateUtil.convertTimeInSecondsToDate((Integer)value);
		}
		else 
		{
			throw new ConverterException("Expected date to be an Integer number of seconds since epoch");
		}
    	return formatter.format(value);
    }

    public Object getAsObject(FacesContext context, UIComponent component, String value)
    {
        // TODO Auto-generated method stub
        return null;
    }

    
}

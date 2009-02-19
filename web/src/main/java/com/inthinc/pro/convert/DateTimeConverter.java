package com.inthinc.pro.convert;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * 
 * @author mstrong
 * 
 *         This dateTimeConverter is to assist when the f:convertDateTime does not work. This usually occurs when the f:converDateTime is used withing a table.
 * 
 */

public class DateTimeConverter implements Converter
{
    private Object timeZone;
    private String pattern;
    
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value)
    {
        return value;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value)
    {
        String returnString = null;
        if (value instanceof Date)
        {
            String pattern = "MMM dd, yyyy";
            TimeZone timeZone = TimeZone.getDefault();
            Date date = (Date) value;

            if (this.pattern != null)
            {
                pattern = this.pattern;

            }
         
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            sdf.setTimeZone(timeZone);
            if (this.timeZone != null)
            {
               
                if (this.timeZone instanceof String)
                {
                    sdf.setTimeZone(TimeZone.getTimeZone((String) this.timeZone));
                }
                else if (this.timeZone instanceof TimeZone)
                {
                    sdf.setTimeZone((TimeZone) this.timeZone);
                }
            }
           

            returnString = sdf.format(date);
        }

        return returnString;
    }

    public void setTimeZone(Object timeZone)
    {
        this.timeZone = timeZone;
    }

    public Object getTimeZone()
    {
        return timeZone;
    }

    public void setPattern(String pattern)
    {
        this.pattern = pattern;
    }

    public String getPattern()
    {
        return pattern;
    }
}

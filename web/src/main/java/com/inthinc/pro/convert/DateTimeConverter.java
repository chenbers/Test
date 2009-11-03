package com.inthinc.pro.convert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import com.inthinc.pro.util.MessageUtil;

/**
 * 
 * @author mstrong
 * 
 * This dateTimeConverter is to assist when the f:convertDateTime does not work. 
 * This usually occurs when the f:converDateTime is used withing a table.
 * 
 */

public class DateTimeConverter extends BaseConverter
{
    
//    private static final String DEFAULT_PATTERN = "MMM dd, yyyy";
//    private static final TimeZone DEFAULT_TIMEZONE = TimeZone.getDefault();
    
//    private Object timeZone;
//    private String pattern;
 
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value)
    {
//       String pattern = DEFAULT_PATTERN;
//    	TimeZone timeZone = DEFAULT_TIMEZONE;
        Date date = new Date();

//        if (this.pattern != null)
//        {
//            pattern = this.pattern;
//        }
        SimpleDateFormat sdf = new SimpleDateFormat(MessageUtil.getMessageString("dateTimeFormat"), getLocale());
        sdf.setTimeZone(getTimeZone());
//        if (getTimeZone() != null)
//        {
//            if ( getTimeZone() instanceof String)
//            {
//                sdf.setTimeZone(TimeZone.getTimeZone((String) getTimeZone()));
//            }
//            else if (getTimeZone() instanceof TimeZone)
//            {
//                sdf.setTimeZone((TimeZone) getTimeZone());
//            }
//        }
        try
        {
           date =  sdf.parse(value);
        }catch(ParseException ex){
           throw new ConverterException(ex);
        }
        return date;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value)
    {
        String returnString = null;
        if (value instanceof Date)
        {
//            String pattern = DEFAULT_PATTERN;
//            TimeZone timeZone = DEFAULT_TIMEZONE;
            
            Date date = (Date) value;

//            if (this.pattern != null)
//            {
//                pattern = this.pattern;
//            }
         
            SimpleDateFormat sdf = new SimpleDateFormat(MessageUtil.getMessageString("dateTimeFormat"), getLocale());
            sdf.setTimeZone(getTimeZone());
//            if (this.timeZone != null)
//            {
//                if (this.timeZone instanceof String)
//                {
//                    sdf.setTimeZone(TimeZone.getTimeZone((String) this.timeZone));
//                }
//                else if (this.timeZone instanceof TimeZone)
//                {
//                    sdf.setTimeZone((TimeZone) this.timeZone);
//                }
//            }
            returnString = sdf.format(date);
        }
        return returnString;
    }

//    public void setTimeZone(Object timeZone)
//    {
//        this.timeZone = timeZone;
//    }
//
//    public Object getTimeZone()
//    {
//        return timeZone;
//    }

//    public void setPattern(String pattern)
//    {
//        this.pattern = pattern;
//    }
//
//    public String getPattern()
//    {
//        return pattern;
//    }
}

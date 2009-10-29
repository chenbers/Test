package com.inthinc.pro.convert;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.util.MessageUtil;

/**
 * TimeAgoConverter
 * converts a time to "XX hrs XX min ago" or "XX minutes ago" or "XX seconds ago"
 */
public class TimeAgoConverter extends BaseConverter
{
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException
    {
        return new Integer(value);
    }
    
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException
    {
        Integer nowSeconds = (int)DateUtil.convertDateToSeconds(new Date());
        Integer seconds = 0;
        
        //Get logged in user's time zone.
        TimeZone tz = getTimeZone();

        if( value instanceof Date)
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(tz);
            calendar.setTime((Date)value);
            seconds = (int)DateUtil.convertDateToSeconds(calendar.getTime());
        }
        
        if(seconds != 0)        
            return getDiffString(seconds, nowSeconds, tz); // + " " + DateUtil.getDisplayDate(seconds);
        else
            return "Unknown Format";
    }
    
    private String getDiffString(Integer thenSecs, Integer nowSecs, TimeZone tz)
    {
        Integer diff = nowSecs - thenSecs;
        
        int hours = diff / 3600;
        int minutes = (diff % 3600) / 60;
        int seconds = (diff % 3600) % 60;
        
        if(hours > 48)
        {
            DateFormat dateFormatter = new SimpleDateFormat(MessageUtil.getMessageString("dateTimeFormat"),getLocale());
            dateFormatter.setTimeZone(tz);
            return dateFormatter.format(DateUtil.convertTimeInSecondsToDate(thenSecs));
        }
        else if(hours > 1)
            return hours + " "+MessageUtil.getMessageString("hrs")+" " + minutes + " "+MessageUtil.getMessageString("minago");
        else if (hours == 1)
            return hours + " "+MessageUtil.getMessageString("hr")+" " + minutes + " "+MessageUtil.getMessageString("minago");
        else if (minutes > 0)
            return minutes + " "+MessageUtil.getMessageString("minutesago");
        else if (seconds > 0)
            return seconds + " "+MessageUtil.getMessageString("secondsago");
        else
            return "";
    }
}

package com.inthinc.pro.convert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.swing.text.DateFormatter;

import com.inthinc.pro.dao.util.DateUtil;

/**
 * TimeAgoConverter
 * converts a time to "XX hrs XX min ago" or "XX minutes ago" or "XX seconds ago"
 */
public class TimeAgoConverter extends BaseConverter
{
    private DateFormat dateFormatter;
    private String dateFormat = "MMM dd h:mm a z";
    
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException
    {
        return new Integer(value);
    }
    
    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException
    {
        Integer nowSeconds = (int)DateUtil.convertDateToSeconds(new Date());
        Integer seconds = 0;
        
        //Get logged in user's time zone.
        TimeZone tz = super.getUser().getUser().getPerson().getTimeZone();

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
            DateFormat dateFormatter = new SimpleDateFormat(dateFormat);
            dateFormatter.setTimeZone(tz);
            return dateFormatter.format(DateUtil.convertTimeInSecondsToDate(thenSecs));
        }
        else if(hours > 1)
            return hours + " hrs " + minutes + " min ago";
        else if (hours == 1)
            return hours + " hr " + minutes + " min ago";
        else if (minutes > 0)
            return minutes + " minutes ago";
        else if (seconds > 0)
            return seconds + " seconds ago";
        else
            return "";
    }
}

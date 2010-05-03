package com.inthinc.pro.backing.dao;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.faces.model.SelectItem;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.pro.model.app.SupportedTimeZones;

public class DateFormatBean {
	private String pattern = "MM/dd/yy h:mm a (z)";
	private static String[] patternStrs = { 
      		"MM/dd/yy h:mm a (z)",
      		"d MMM, yyyy h:mm a (z)",
      		"MM/dd/yy h:mm a",
      		"d MMM, yyyy h:mm a",
      		"MM/dd/yy HH:mm  (z)",
      		"d MMM, yyyy HH:mm (z)",
      		"MM/dd/yy HH:mm",
      		"d MMM, yyyy HH:mm",
      		"MM/dd/yy HH:mm:ss",
      		"d MMM, yyyy HH:mm:ss",
	};
	
	private String timeZoneStr = TimeZone.getDefault().getID();

	private static Map<String, String> TIMEZONES = null;
    private static final int MILLIS_PER_MINUTE = 1000 * 60;
    private static final int MILLIS_PER_HOUR = MILLIS_PER_MINUTE * 60;


    static {
        final List<String> timeZones = SupportedTimeZones.getSupportedTimeZones();
        if (timeZones != null) {
	        
	    	// sort by offset from GMT
	    	Collections.sort(timeZones, new Comparator<String>() {
	            @Override
	            public int compare(String o1, String o2) {
	                final TimeZone t1 = TimeZone.getTimeZone(o1);
	                final TimeZone t2 = TimeZone.getTimeZone(o2);
	                return t1.getRawOffset() - t2.getRawOffset();
	            }
	        });
	        TIMEZONES = new LinkedHashMap<String, String>();
	        final NumberFormat format = NumberFormat.getIntegerInstance();
	        format.setMinimumIntegerDigits(2);
	        for (final String id : timeZones) {
	            final TimeZone timeZone = TimeZone.getTimeZone(id);
	            final int offsetHours = timeZone.getRawOffset() / MILLIS_PER_HOUR;
	            final int offsetMinutes = Math.abs((timeZone.getRawOffset() % MILLIS_PER_HOUR) / MILLIS_PER_MINUTE);
	            if (offsetHours < 0)
	                TIMEZONES.put(id, timeZone.getID() + " (GMT" + offsetHours + ':' + format.format(offsetMinutes) + ')');
	            else
	                TIMEZONES.put(id, timeZone.getID() + " (GMT+" + offsetHours + ':' + format.format(offsetMinutes) + ')');
	        }
        }
        
    }

    	
    public List<SelectItem> getTimeZones()
    {
    	
        List<SelectItem> selectItemList = new ArrayList<SelectItem>();
        
        for (String key : TIMEZONES.keySet())
        {
               selectItemList.add(new SelectItem(key, TIMEZONES.get(key)));
        }
        
        return selectItemList;
    }

    public List<SelectItem> getPatterns()
    {
    	
        List<SelectItem> selectItemList = new ArrayList<SelectItem>();
        
        for (String key : patternStrs)
        {
               selectItemList.add(new SelectItem(key, key));
        }
        
        return selectItemList;
    }


	public String formatDate(Date date)
	{
		DateTimeFormatter fmt = DateTimeFormat.forPattern(getPattern());
		DateTime dateTime = new DateTime(date, DateTimeZone.forTimeZone(getTimeZone() == null ? TimeZone.getDefault() : getTimeZone()));
 	    return fmt.print(dateTime);

	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public  TimeZone getTimeZone() {
		return TimeZone.getTimeZone(getTimeZoneStr());
	}


	public String getTimeZoneStr() {
		return timeZoneStr;
	}

	public void setTimeZoneStr(String timeZoneStr) {
		this.timeZoneStr = timeZoneStr;
	}


}

package com.inthinc.pro.reports.converter;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.inthinc.pro.dao.util.DateUtil;

public class TimeToStringConverter {
    
	public static String convertSeconds(Number seconds) {
    	
    	if (seconds == null)
    		seconds = 0;
    	
    	return DateUtil.getDurationFromSeconds(seconds.intValue());
    }
	
    
    public static String convertSeconds(Long seconds) {
        
        if (seconds == null)
            seconds = 0L;
        
        return DateUtil.getDurationFromSeconds(seconds.intValue());
    }	
    
    public static String convertSecondsToDate(Long seconds, Locale locale) {
                
        if (seconds != null && seconds == 0L) {
            return "";            
        }
        
        if (seconds == null) {
            seconds = 0L;
        }
        
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(seconds*1000L);
        
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm a (z)", locale);
        
        return sdf.format(gc.getTime());
    }
    
    public static String convertMillisToDate(Long milliSeconds, Locale locale) {
        if (milliSeconds != null && milliSeconds == 0L) {
            return "";            
        }
        
        if (milliSeconds == null) {
            milliSeconds = 0L;
        }
        
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(milliSeconds);
        
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm a (z)", locale);
        
        return sdf.format(gc.getTime());
    }

    public static String deltaSeconds(Long start, Long end) {
        if (start == null || start == 0l || end == null || end == 0l)
            return "  ----";
        Long delta = end-start;
        
        return DateUtil.getDurationFromSeconds(delta.intValue());
    }   

}

package com.inthinc.pro.reports.converter;

import com.inthinc.pro.dao.util.DateUtil;

public class TimeToStringConverter {
    
	public static String convertSeconds(Number seconds) {
    	
    	if (seconds == null)
    		seconds = 0;
    	
    	return DateUtil.getDurationFromSeconds(seconds.intValue());
    }

}

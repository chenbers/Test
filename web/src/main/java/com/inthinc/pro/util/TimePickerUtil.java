package com.inthinc.pro.util;

import org.joda.time.DateTime;

public class TimePickerUtil {
    
    public static DateTime addTimePickerSecondsToDateTime(int seconds, DateTime dateTime) {
        
        int hourOfDay = seconds/3600;
        int minuteOfHour = (seconds - (hourOfDay * 3600)) / 60;
        int secondOfMinute = seconds % 60;
        
        return dateTime.withTime(hourOfDay, minuteOfHour, secondOfMinute, 0);

    }

}

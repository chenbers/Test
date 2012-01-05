package com.inthinc.pro.reports.converter;

import java.util.Locale;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.reports.util.MessageUtil;

public class TimeFrameConverter {
    
    public static String convertTimeFrame(TimeFrame timeFrame, Locale locale) {
        
        if (TimeFrame.MONTH == timeFrame)
            return new DateTime().monthOfYear().getAsText(locale);

        
        return MessageUtil.getMessageString("timeFrame_" + timeFrame, locale);
        
    }
    public static String convertTimeFrameInterval(TimeFrame timeFrame, Locale locale, TimeZone timeZone) {
        
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(MessageUtil.getMessageString("simpleDateFormat", locale)).withLocale(locale);
        
        DateTimeZone dateTimeZone = timeZone == null ? DateTimeZone.UTC : DateTimeZone.forTimeZone(timeZone);
        
        DateTime start = timeFrame.getInterval(dateTimeZone).getStart();
        DateTime end = timeFrame.getInterval(dateTimeZone).getEnd().minusDays(1);
        
        if (start.isEqual(end)) 
            return dateTimeFormatter.print(start);
        return dateTimeFormatter.print(start) + " - " + dateTimeFormatter.print(end);

        
    }

}

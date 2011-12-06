package com.inthinc.pro.reports.converter;

import java.util.Locale;

import org.joda.time.DateTime;

import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.reports.util.MessageUtil;

public class TimeFrameConverter {
    
    public static String convertTimeFrame(TimeFrame timeFrame, Locale locale) {
        
        if (TimeFrame.MONTH == timeFrame)
            return new DateTime().monthOfYear().getAsText(locale);

        if (TimeFrame.LAST_MONTH == timeFrame)
            return new DateTime().minusMonths(1).monthOfYear().getAsText(locale);
        
        return MessageUtil.getMessageString("timeFrame_" + timeFrame, locale);
        
    }

}

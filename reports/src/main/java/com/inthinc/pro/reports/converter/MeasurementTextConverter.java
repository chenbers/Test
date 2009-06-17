package com.inthinc.pro.reports.converter;

import java.util.Locale;

import com.inthinc.pro.reports.util.MessageUtil;

public class MeasurementTextConverter
{
    public static final String METRIC_SUFFIX = ".metric";

    public static String getMessage(String key, Locale locale, Boolean convertToMetric)
    {
        if(convertToMetric)
            key += METRIC_SUFFIX;
        
        return MessageUtil.getMessageString(key, locale);
    }

}

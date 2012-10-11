package com.inthinc.pro.reports.hos.converter;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.hos.util.DateUtil;
import com.inthinc.pro.dao.util.MathUtil;
import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.MeasurementType;

public class Converter {
    
    public static String convertRemarksDate(Date date, TimeZone timeZone, Locale locale) {
        
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm").withLocale(locale);
        
        long minutes = DateUtil.milliSecondsToMinutes(date.getTime());
        DateTime dateTime = new DateTime(new Date(minutes * DateUtil.MILLISECONDS_IN_MINUTE), DateTimeZone.forTimeZone(timeZone == null ? TimeZone.getDefault() : timeZone));
        return fmt.print(dateTime).trim();
    }

    public static String convertRemarkDistance(Number distance, Boolean convertToMetric, Locale locale) {
        if (distance == null)
            distance = 0;
        distance = distance.doubleValue()/100d;
        NumberFormat nf = NumberFormat.getNumberInstance(locale);
        nf.setMaximumFractionDigits(0);
        nf.setMinimumFractionDigits(0);
        nf.setGroupingUsed(false);
        if (convertToMetric)
            return nf.format(MeasurementConversionUtil.convertMilesToKilometers(distance, MeasurementType.METRIC));
        else {
            return nf.format( MathUtil.round(distance, 2));
        }
    }
    public static String convertDistance(Number distance, Boolean convertToMetric, Locale locale) {
        if (distance == null)
            distance = 0;
        distance = distance.doubleValue()/100d;
        NumberFormat nf = NumberFormat.getNumberInstance(locale);
        nf.setMaximumFractionDigits(0);
        nf.setMinimumFractionDigits(0);
        nf.setGroupingUsed(false);
        if (convertToMetric)
            return nf.format(MeasurementConversionUtil.convertMilesToKilometers(distance, MeasurementType.METRIC));
        else {
            return nf.format( MathUtil.round(distance, 2));
        }
    }

    public static String convertMinutes(Number minutes) {
        
        if (minutes == null)
            minutes = 0L;
        
        Long hours = minutes.longValue() / 60;
        minutes = minutes.longValue() % 60;

        return  (hours < 10 ? "0" : "") + hours + ":" + (minutes.longValue() < 10 ? "0" : "") + minutes;

        
    }   
    
    public static String convertMinutesRound15(Number min) {
        
        Long minutes = min.longValue();
        if (minutes == null)
            minutes = 0L;
        
        if (minutes % 15 > 7)
            minutes = (minutes/15 + 1) * 15;
        else minutes = (minutes/15) * 15;
        
        Long hours = minutes / 60;
        minutes = minutes % 60;

        return  (hours < 10 ? "0" : "") + hours + ":" + (minutes < 10 ? "0" : "") + minutes;

        
    }   

    public static String convertMinutesRound15Excel(Long minutes) {
        
        if (minutes == null)
            minutes = 0L;
        
        if (minutes % 15 > 7)
            minutes = (minutes/15 + 1) * 15;
        else minutes = (minutes/15) * 15;
        
        Long hours = minutes / 60;
        minutes = minutes % 60;

        return  hours + ((minutes == 0) ? "" : ( "." + minutes));

        
    }   
}

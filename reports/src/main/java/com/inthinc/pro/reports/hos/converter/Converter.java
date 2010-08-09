package com.inthinc.pro.reports.hos.converter;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.dao.util.MathUtil;
import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.reports.util.MessageUtil;

public class Converter {
    
    public static String convertRemarksDate(Date date, TimeZone timeZone, Locale locale) {
        
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm").withLocale(locale);
        
        
        DateTime dateTime = new DateTime(date.getTime(), DateTimeZone.forTimeZone(timeZone == null ? TimeZone.getDefault() : timeZone));
        return fmt.print(dateTime);
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

    public static final String GALLONS = "Gallons:";
    // format is Vehicle Gallons: ## Trailer Gallons: ##  convert to
    //           Vehicle Liters: ## Trailer Liters: ##
    public static String convertRemarkDescription(String description, Locale locale) {
        int index = description.indexOf(GALLONS);
        String litersLabel = MessageUtil.getMessageString("report.ddl.liters", locale);
        if (index != -1)
        {
            StringBuffer newDescription = new StringBuffer();
            String[] strList = description.trim().split("\\s");
            for (int i = 0; i < strList.length; i++) {
                if (strList[i].compareTo(GALLONS) == 0) {
                    newDescription.append(" ");
                    newDescription.append(litersLabel);
                    if (i+1 < strList.length) {
                        try {
                            Number liters = MeasurementConversionUtil.fromGallonsToLiters(new Double(strList[i+1]));
                            newDescription.append(" ");
                            newDescription.append(liters.toString());
                        }
                        catch (NumberFormatException ex) {
                        }
                        i++;
                    }
                }
                else {
                    newDescription.append(" ");
                    newDescription.append(strList[i]);
                }
            }
            
            return newDescription.toString();
        }
        return description;
    }
    
    
    public static String convertMinutes(Long minutes) {
        
        if (minutes == null)
            minutes = 0L;
        
        Long hours = minutes / 60;
        minutes = minutes % 60;

        return  (hours < 10 ? "0" : "") + hours + ":" + (minutes < 10 ? "0" : "") + minutes;

        
    }   
    
    public static String convertMinutesRound15(Long minutes) {
        
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

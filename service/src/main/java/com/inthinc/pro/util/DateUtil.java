package com.inthinc.pro.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import com.inthinc.pro.service.exceptions.BadDateRangeException;

public class DateUtil {

    static final Integer DAYS_BACK = 7;
    static final String SIMPLE_DATE_FORMAT = "yyyyMMdd";

    
    public static String getFormattedDate(DateTime date){
    	
        SimpleDateFormat dateFormatter = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
        String formattedTime = dateFormatter.format(date.toDate());
        
        return formattedTime;
    }
    public static Date getBeginningOfDay(Date day) {
        return new DateMidnight(day).toDate();
    }

    public static Date getOneYearThresholdDate(Date day) {
        return new DateMidnight(day).minusYears(1).toDate();
    }
    
    /**
     * Returns the Date with time set to end of the day.
     * @param endDate
     * @return
     */
    public static Date getEndOfDay(Date endDate) {
        return new DateTime(new DateMidnight(endDate).plusDays(1)).minus(1).toDate();
    }
    
    public static Boolean validDateRange(Date normalizedStartDate, Date normalizedEndDate){
        
        return normalizedEndDate.after(normalizedStartDate);
    }
    
    /**
     * Create an Interval from the Date range.
     * If the Dates are null the default one is created.
     * @return the Date set to default days back
     */
    public static Interval getInterval(Date startDate, Date endDate) throws BadDateRangeException{
        if (startDate == null || endDate == null) {
            startDate = getMidnight(DAYS_BACK);
            endDate = getMidnight(0);
        }
        if (!DateUtil.validDateRange(startDate, endDate)) throw new BadDateRangeException(startDate,endDate);

        return new Interval(startDate.getTime(), getEndOfDay(endDate).getTime());
    }
    
    private static Date getMidnight(int daysBack) {
       DateMidnight date = new DateMidnight(); 
       date = date.minusDays(daysBack);
       return date.toDate();
    }
}

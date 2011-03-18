package com.inthinc.pro.util.dateUtil;

import java.util.Date;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;

public class DateUtil {

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
        DateMidnight midnight = new DateMidnight(endDate).plusDays(1);
        return new DateTime(midnight).minus(1).toDate();
    }
    
    public static Boolean validDateRange(Date normalizedStartDate, Date normalizedEndDate){
        
        return normalizedEndDate.after(normalizedStartDate);
    }
}

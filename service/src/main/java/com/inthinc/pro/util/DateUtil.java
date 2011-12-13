package com.inthinc.pro.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;

import com.inthinc.pro.service.exceptions.BadDateRangeException;

public class DateUtil {

    static final Integer DAYS_BACK = 7;
    static final String SIMPLE_DATE_FORMAT = "yyyyMMdd";
    static final String MONTH_FORMAT = "yyyyMMMZ";
    
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
    public static Interval getIntervalFromMonth(String month) throws ParseException{
    	if (month.isEmpty()){
    		DateTime dateTime = new DateTime();
    		return dateTime.monthOfYear().toInterval();
    	}
    	DateTime monthTime = getDateFromMonth(month);
    	Interval interval = 
    				new Interval(new DateMidnight(monthTime.monthOfYear().toInterval().getStart(), DateTimeZone.UTC), new DateMidnight(monthTime.monthOfYear().toInterval().getEnd(), DateTimeZone.UTC));
    	//adjust for future
    	if(interval.getEnd().isAfterNow()){
    		//take off a year
    		interval = new Interval(interval.getStart().minusYears(1), interval.getEnd().minusYears(1));
    	}
    	return interval;
    }
    private static DateTime getDateFromMonth(String month) throws ParseException{
        SimpleDateFormat dateFormatter = new SimpleDateFormat(MONTH_FORMAT);
        
    	DateTime now = new DateTime();
    	int year = now.getYear();
    	dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
    	calendar.setTime(dateFormatter.parse(year+month+"+0000"));
		DateTime selectedMonth = new DateTime(calendar.getTime()).toDateTime(DateTimeZone.UTC);
    	return selectedMonth;
    }
}

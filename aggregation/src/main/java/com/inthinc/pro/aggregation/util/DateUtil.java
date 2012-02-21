package com.inthinc.pro.aggregation.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.log4j.Logger;

public class DateUtil
{

    private static Logger         logger                  = Logger.getLogger(DateUtil.class);

    public static final long      MILLISECONDS_IN_ONE_DAY = 86400000l;                        // 24*60*60*1000;
    public static final long      MILLISECONDS_IN_MINUTE  = 60000l;
    public static final long      MILLISECONDS_IN_HOUR    = 3600000l;
    public static final int       MINUTES_IN_ONE_DAY      = 1440;                            // 24*60;

    public static final TimeZone UTC_TIMEZONE            = TimeZone.getTimeZone("UTC");
    private static final String   DB_DATE_FORMAT          = "yyyy-MM-dd HH:mm:ss";
    private static final String   DISPLAY_DATE_FORMAT     = "yyyy-MM-dd HH:mm:ss";
    
    public static SimpleDateFormat getDateFormat(TimeZone timeZone)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DB_DATE_FORMAT);
        dateFormat.setTimeZone(timeZone);
        return dateFormat;
    }
    public static SimpleDateFormat getDisplayDateFormat(TimeZone timeZone)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DISPLAY_DATE_FORMAT);
        dateFormat.setTimeZone(timeZone);
        return dateFormat;
    }
    
    public static Date getDateFromString(String strDate, TimeZone timeZone)
    {
        SimpleDateFormat dateFormat = getDateFormat(timeZone);
        try
        {
            Date startOfDay = dateFormat.parse(strDate);
            return startOfDay;
        }
        catch (ParseException e)
        {
            logger.error(e);
        }

        return null;
    }
    
	public static java.util.Date getDateFromString(String strDate)
	{
		return getDateFromString(strDate, UTC_TIMEZONE);
	}

	public static Date changeTimeZone(Date date, TimeZone currentTimeZone, TimeZone newTimeZone)
    {
    	String timeAsStr = "";
    	Date newDate = date;
    	try {
    		timeAsStr = DateUtil.getDateFormat(currentTimeZone).format(date);
    		newDate = DateUtil.getDateFormat(newTimeZone).parse(timeAsStr); 
    	}
	    catch (ParseException e)
	    {
	        logger.error(e);
	    }
        return newDate;
    }

    public static Date getStartOfDay(Date date)
    {
        return getStartOfDay(date, UTC_TIMEZONE);
    }


    public static Date getStartOfDay(Date date, TimeZone timeZone)
    {
        SimpleDateFormat dateFormat = getDateFormat(timeZone);
        String timeAsStr = dateFormat.format(date);
        timeAsStr = timeAsStr.substring(0, 10) + " 00:00:00";
        try
        {
            Date startOfDay = dateFormat.parse(timeAsStr);
            return startOfDay;
        }
        catch (ParseException e)
        {
            logger.error(e);
        }

        return null;
    }

    public static Date getEndOfDay(Date date)
    {
        return getEndOfDay(date, UTC_TIMEZONE);
    }

    public static Date getEndOfDay(Date date, TimeZone timeZone)
    {
        SimpleDateFormat dateFormat = getDateFormat(timeZone);
        String timeAsStr = dateFormat.format(date);
        timeAsStr = timeAsStr.substring(0, 10) + " 23:59:59";
        try
        {
            Date endOfDay = dateFormat.parse(timeAsStr);
            return endOfDay;
        }
        catch (ParseException e)
        {
            logger.error(e);
        }

        return null;
    }
    
    public static Date getStartOfYesterday(Date date)
    {
        return getStartOfYesterday(date, UTC_TIMEZONE);
    }


    public static Date getStartOfYesterday(Date date, TimeZone timeZone)
    {

        GregorianCalendar dayCalendar = new GregorianCalendar();
        dayCalendar.setTime(date);
        dayCalendar.add(Calendar.DATE, -1);

        return getStartOfDay(dayCalendar.getTime(), timeZone);
    }
    
    public static Date getStartOfTomorrow(Date date)
    {
        return getStartOfTomorrow(date, UTC_TIMEZONE);
    }
  
    public static Date getStartOfTomorrow(Date date, TimeZone timeZone)
    {

        GregorianCalendar dayCalendar = new GregorianCalendar();
        dayCalendar.setTime(date);
        dayCalendar.add(Calendar.DATE, 1);

        return getStartOfDay(dayCalendar.getTime(), timeZone);
    }
    public static Date getEndOfTomorrow(Date date)
    {
        return getEndOfTomorrow(date, UTC_TIMEZONE);
    }
    public static Date getEndOfTomorrow(Date date, TimeZone timeZone)
    {

        GregorianCalendar dayCalendar = new GregorianCalendar();
        dayCalendar.setTime(date);
        dayCalendar.add(Calendar.DATE, 1);

        return getEndOfDay(dayCalendar.getTime(), timeZone);
    }
    public static Date getStartOfDayForDaysBack(Date date, int daysBack)
    {
        return getStartOfDayForDaysBack(date, daysBack, UTC_TIMEZONE);
    }
    public static Date getStartOfDayForDaysBack(Date date, int daysBack, TimeZone timeZone)
    {

        GregorianCalendar dayCalendar = new GregorianCalendar();
        dayCalendar.setTime(date);
        dayCalendar.add(Calendar.DATE, -1 * daysBack);

        return getStartOfDay(dayCalendar.getTime(), timeZone);
    }
    
    public static Date getEndOfDayForDaysForward(Date date, int daysForward, TimeZone timeZone)
    {
        GregorianCalendar dayCalendar = new GregorianCalendar();
        dayCalendar.setTime(date);
        dayCalendar.add(Calendar.DATE, daysForward);

        return getEndOfDay(dayCalendar.getTime(), timeZone);
        
    }
    
    public static Date[] getDayList(Date startDate, Date endDate)
    {
        return getDayList(startDate, endDate, UTC_TIMEZONE);
    }

    public static Date[] getDayList(Date startDate, Date endDate, TimeZone timeZone)
    {

        int numDays = (int) ((endDate.getTime() - startDate.getTime()) / MILLISECONDS_IN_ONE_DAY) + 1;
        Date[] days = new Date[numDays];

        GregorianCalendar dayCalendar = new GregorianCalendar();
        dayCalendar.setTime(startDate);
        dayCalendar.setTimeZone(timeZone);
        for (int i = 0; i < numDays; i++)
        {
            if (i > 0)
                dayCalendar.add(Calendar.DATE, 1);
            days[i] = dayCalendar.getTime();
        }

        return days;
    }

    public static int hoursToMinutes(int hours)
    {
        return hours * 60;
    }

    public static long milliSecondsToMinutes(long ms)
    {
        long minutes = ms / MILLISECONDS_IN_MINUTE;
            
        if (ms % MILLISECONDS_IN_MINUTE > 30000l)
            minutes++;

         return minutes;
    }

    public static long minutesToMilliseconds(long min)
    {
        return min * MILLISECONDS_IN_MINUTE;
    }
    
    public static Date minutesToDate(long min)
    {
        return new Date(min * MILLISECONDS_IN_MINUTE);
    }

    public static long convertDaysToMillis(int days)
    {
        return (long) days * DateUtil.MILLISECONDS_IN_ONE_DAY;
    }

    public static String getDisplayDate(Date date)
    {
        return getDisplayDate(date, UTC_TIMEZONE);
    }
    public static String getDisplayDate(Date date, TimeZone timeZone)
    {
        return getDisplayDateFormat(timeZone).format(date);
    }

    public static boolean isDateBetween(Date date, Date start, Date end)
    {
        return (date.equals(start) || (date.after(start) && date.before(end)));

    }
    
    
    public static void dumpDate(Date date) {
        System.out.println(getDisplayDate(date, TimeZone.getDefault()));
    }


    public static void dumpDate(Date date, TimeZone tz) {
        System.out.println(getDisplayDate(date, tz));
    }

    
    public static long minutesDelta(Date date1, Date date2) {
        return  (date1.getTime()-date2.getTime()) / DateUtil.MILLISECONDS_IN_MINUTE;
    }
}

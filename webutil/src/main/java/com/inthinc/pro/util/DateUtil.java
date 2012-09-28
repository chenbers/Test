package com.inthinc.pro.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;




/**
 * Utility methods for date/time.
 *
 */
public class DateUtil
{
	public static final int SECONDS_IN_DAY = 86400;
	public static final int MILLISECONDS_IN_MINUTE = 60000;
	public static final int MINUTES_IN_DAY = 1440;
	
	public static final String[] dayOfWeek = {
		"TODAY",
		"SUN",
		"MON",
		"TUE",
		"WED",
		"THU",
		"FRI",
		"SAT",
	};
	
    private static final String DOB_SEPARATOR = "-";
    private static final NumberFormat twoDigitFormat = new DecimalFormat("00");
	
	public static int convertDateToSeconds(Date date)
	{
		return (int)(date.getTime()/1000l);
	}
	public static Date convertTimeInSecondsToDate(int seconds)
	{
		return new Date(convertSecondsToMilliseconds(seconds));
	}
	public static long convertSecondsToMilliseconds(int seconds)
	{
		return (long)((long)seconds * 1000l);
	}

	public static int convertMillisecondsToSeconds(long milliseconds)
	{
		return (int)(milliseconds/1000l);
	}

	public static int getTodaysDate()
	{
		Date today = new Date();
		
		return convertMillisecondsToSeconds(today.getTime());
	}


	public static int getDaysBackDate(int endDate, int daysBack)
	{
		return endDate-(daysBack * SECONDS_IN_DAY);
	}

	public static int getDaysBackDate(int nowUTCSec, int daysBack, String timeZone)
	{
		
		Date nowUTC = new Date(convertSecondsToMilliseconds(nowUTCSec));
		
		SimpleDateFormat tzFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		tzFormat.setTimeZone(TimeZone.getTimeZone(timeZone));

		// time now in timezone
		String nowTZstr = tzFormat.format(nowUTC);
		
		// start of today string in timezone
		String startOfDayTZ = nowTZstr.substring(0, 11) + "00:00:00";
		
		// start of today date in timezone
		Date startOfDayTZDate  = null;
		try {
			startOfDayTZDate = tzFormat.parse(startOfDayTZ);
		}
		catch (ParseException e) {
			// this should never happen since the conversions are all done within this method
		}
		
		// subtract one from daysBack because we count today as one of the days back
		// so if daysBack is 1, it means just today
		// if daysBack is 7, it means today + the 6 previous days
		long ms = startOfDayTZDate.getTime() - convertSecondsToMilliseconds((daysBack-1) * SECONDS_IN_DAY); 
		
		
		
		return convertMillisecondsToSeconds(ms);
	}
	
	// return day of week or 'today' 
	public static String getDayOfWeek(Date date, TimeZone timeZone)
	{
		
		Calendar todayCal = GregorianCalendar.getInstance(timeZone);
		todayCal.setTime(new Date());
		
		Calendar cal = GregorianCalendar.getInstance(timeZone);
		cal.setTime(date);
		
		if (todayCal.get(Calendar.YEAR) == cal.get(Calendar.YEAR) &&
			todayCal.get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR))
			return dayOfWeek[0];
			
		
		int day = cal.get(Calendar.DAY_OF_WEEK);
		return dayOfWeek[day];
	}
	
    
    public static int getCurrentYear()
    {
		GregorianCalendar cal = new GregorianCalendar(TimeZone.getDefault());
		cal.setTime(new Date());
		return cal.get(GregorianCalendar.YEAR);
    }
    public static int getCurrentMonth()
    {
        GregorianCalendar cal = new GregorianCalendar(TimeZone.getDefault());
        cal.setTime(new Date());
        return cal.get(GregorianCalendar.MONTH);
    }
    
    public static int getCurrentMonthofYear()
    {
        GregorianCalendar cal = new GregorianCalendar(TimeZone.getDefault());
        cal.setTime(new Date());
        return cal.get(GregorianCalendar.MONTH);
    }
    
    
    public static int getCurrentDayOfMonth()
    {
        GregorianCalendar cal = new GregorianCalendar(TimeZone.getDefault());
        cal.setTime(new Date());
        return cal.get(GregorianCalendar.DAY_OF_MONTH);
    }
    
    public static String getDOBString(Integer year, Integer month, Integer day)
    {
       // format yyyy-mm-dd
        return year + DOB_SEPARATOR + twoDigitFormat.format(month) + DOB_SEPARATOR + twoDigitFormat.format(day);

    }
    
    public static String getDurationFromSeconds(Integer secsIn)
    {
        int hours = secsIn / 3600,
        remainder = secsIn % 3600,
        minutes = remainder / 60,
        seconds = remainder % 60;

        return ( (hours < 10 ? "0" : "") + hours
        + ":" + (minutes < 10 ? "0" : "") + minutes
        + ":" + (seconds< 10 ? "0" : "") + seconds );
    }
    
    public static String getDurationFromMilliseconds(Long mSecsIn)
    {
        Long seconds = mSecsIn / 1000;
        return getDurationFromSeconds(seconds.intValue());
    }

}

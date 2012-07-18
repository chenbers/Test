package com.inthinc.pro.dao.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.LocalDate;

import com.inthinc.pro.model.TimeFrame;

public class DateUtil
{
	public static int SECONDS_IN_DAY = 86400;
    public static int MILLISECONDS_IN_MINUTE = 60000;
    public static int MINUTES_IN_DAY = 1440;
    public static int SECONDS_IN_MINUTE = 60;
    public static long MILLISECONDS_IN_DAY = 86400000l;

    public static final String[] dayOfWeek = { "TODAY", "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT", };


    public static long convertDateToSeconds(Date date)
    {
        return (date.getTime() / 1000l);
    }

    public static long convertDateToSeconds(DateTime dateTime)
    {
        return (dateTime.getMillis() / 1000l);
    }

    public static Date convertTimeInSecondsToDate(long sec)
    {
        return new Date(convertSecondsToMilliseconds(sec));
    }

    public static long convertSecondsToMilliseconds(long seconds)
    {
        return (seconds * 1000l);
    }

    public static float convertSecondsToHours(long seconds)
    {
        return seconds / 3600.0f;
    }
    public static double convertSecondsToDoubleHours(long seconds)
    {
        return seconds / 3600.0d;
    }

    public static int convertMillisecondsToSeconds(long milliseconds)
    {
        return (int) (milliseconds / 1000l);
    }

    public static int getTodaysDate()
    {
        Date today = new Date();

        return convertMillisecondsToSeconds(today.getTime());
    }

    public static int getDaysBackDate(int endDate, int daysBack)
    {
        return endDate - (daysBack * SECONDS_IN_DAY);
    }
    public static Date getDaysBackDate(Date endDate, int daysBack)
    {
        long sec = DateUtil.convertDateToSeconds(endDate) - (daysBack * SECONDS_IN_DAY);
        
        return DateUtil.convertTimeInSecondsToDate(sec);
    }

    public static int getDaysBackDate(long nowUTCSec, int daysBack, String timeZone)
    {

        Date nowUTC = new Date(convertSecondsToMilliseconds(nowUTCSec));

        SimpleDateFormat tzFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        tzFormat.setTimeZone(TimeZone.getTimeZone(timeZone));

        // time now in timezone
        String nowTZstr = tzFormat.format(nowUTC);

        // start of today string in timezone
        String startOfDayTZ = nowTZstr.substring(0, 11) + "00:00:00";

        // start of today date in timezone
        Date startOfDayTZDate = null;
        try
        {
            startOfDayTZDate = tzFormat.parse(startOfDayTZ);
        }
        catch (ParseException e)
        {
            // this should never happen since the conversions are all done
            // within this method
        }

        // subtract one from daysBack because we count today as one of the days
        // back
        // so if daysBack is 1, it means just today
        // if daysBack is 7, it means today + the 6 previous days
        long ms = startOfDayTZDate.getTime() - convertSecondsToMilliseconds((daysBack - 1) * SECONDS_IN_DAY);

        return convertMillisecondsToSeconds(ms);
    }

    // return day of week or 'today'
    public static String getDayOfWeek(Date date, TimeZone timeZone)
    {

        Calendar todayCal = Calendar.getInstance(timeZone);
        todayCal.setTime(new Date());

        Calendar cal = Calendar.getInstance(timeZone);
        cal.setTime(date);

        if (todayCal.get(Calendar.YEAR) == cal.get(Calendar.YEAR) && todayCal.get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR))
            return dayOfWeek[0];

        int day = cal.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek[day];
    }

    public static int getCurrentYear()
    {
        GregorianCalendar cal = new GregorianCalendar(TimeZone.getDefault());
        cal.setTime(new Date());
        return cal.get(Calendar.YEAR);
    }
    public static int getCurrentMonth()
    {
        GregorianCalendar cal = new GregorianCalendar(TimeZone.getDefault());
        cal.setTime(new Date());
        return cal.get(Calendar.MONTH);
    }
    public static int getCurrentDayOfMonth()
    {
        GregorianCalendar cal = new GregorianCalendar(TimeZone.getDefault());
        cal.setTime(new Date());
        return cal.get(Calendar.DAY_OF_MONTH);
    }
    
    public static String getDisplayDate(Integer dateSec)
    {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd h:mm a (z)");
        
        return format.format(convertTimeInSecondsToDate(dateSec));
    }
    
    public static String getDisplayDateShort(Integer dateSec)
    {
        DateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        
        return format.format(convertTimeInSecondsToDate(dateSec));
    	
    }
    
    public static String getDurationFromSeconds(Integer secsIn)
    {
        Integer hours = secsIn / 3600,
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
        
    public static Date getGregDate(Date in) 
    {
        GregorianCalendar gc = new GregorianCalendar(
                TimeZone.getTimeZone("GMT"));        
        
        // Date supplied, reset to...
        if ( in != null ) {
            gc.setTimeInMillis(in.getTime());
        }
            
        gc.clear(Calendar.HOUR_OF_DAY);
        gc.clear(Calendar.HOUR);
        gc.set(Calendar.HOUR_OF_DAY, 0);
        gc.clear(Calendar.MINUTE);
        gc.set(Calendar.MINUTE, 0);
        gc.clear(Calendar.SECOND);
        gc.set(Calendar.SECOND,0);
    
        return gc.getTime();
    }  
    
    public static Integer differenceInDays(Date startDate,Date endDate)
    {
        Integer difInDays = (int) ((endDate.getTime() - startDate.getTime())/(1000*60*60*24));
        return difInDays;
        
    }
    
    public static void resetTime(Calendar calendar){
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
    }
    
    
    public static long deltaMinutes(Date startTime, Date endDate) {
//        return ((endDate.getTime() - startTime.getTime()) + 30000)/60000l;
        return (endDate.getTime() - startTime.getTime())/60000l;
    }

    
    public static boolean timeInInterval(DateTime dateTime, DateTimeZone dateTimeZone, Interval utcInterval) {
        Interval tzInterval = getIntervalInTimeZone(utcInterval, dateTimeZone);
        return tzInterval.contains(dateTime);
    }
    
    public static Interval getIntervalInTimeZone(Interval interval, DateTimeZone dateTimeZone)
    {
        LocalDate localDate = new LocalDate(new DateMidnight(interval.getStart(), DateTimeZone.UTC));
        DateTime startDate = localDate.toDateTimeAtStartOfDay(dateTimeZone);
        localDate = new LocalDate(new DateMidnight(interval.getEnd(), DateTimeZone.UTC));
        DateTime endDate = localDate.toDateTimeAtStartOfDay(dateTimeZone);
        System.out.println("local Interval: " + startDate + " " + endDate);        

        return new Interval(startDate, endDate);

    }
    public static Integer differenceInDays(TimeFrame timeFrame, Interval interval){
        if(timeFrame != null)
            interval = timeFrame.getInterval();
        return differenceInDays(interval.getStart().toDate(), interval.getEnd().toDate());
    }
}



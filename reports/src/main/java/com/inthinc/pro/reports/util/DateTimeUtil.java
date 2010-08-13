package com.inthinc.pro.reports.util;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.hos.rules.RuleSetFactory;

public class DateTimeUtil {
    
    public static List<DateTime> getDayList(Interval interval, DateTimeZone dateTimeZone)
    {
        List<DateTime> dayList = new ArrayList<DateTime>();
        
        for (DateTime intervalDay = interval.getStart(); intervalDay.isBefore(interval.getEnd()); intervalDay = intervalDay.plusDays(1)) {
            LocalDate localDate = new LocalDate(intervalDay);
            DateTime day = localDate.toDateTimeAtStartOfDay(dateTimeZone);
            dayList.add(day);
        }
        return dayList;
    }
    
    public static Interval getStartEndInterval(String start, String end, String pattern)
    {
        // interval start date 00:00:00 to end date 23:59:59
        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        DateTime startDate = new DateMidnight(fmt.parseDateTime(start)).toDateTime();
        DateTime endDate = new DateMidnight(fmt.parseDateTime(end)).toDateTime().plusDays(1).minusSeconds(1);
        return new Interval(startDate, endDate);

    }

    public static Interval getExpandedInterval(Interval interval, DateTimeZone dateTimeZone, int daysBack, int daysForward)
    {
        return new Interval(new DateMidnight(interval.getStart(), dateTimeZone).minusDays(daysBack), new DateMidnight(interval.getEnd(), dateTimeZone).plusDays(daysForward)); 

    }

    public static Interval getInterval(DateTime currentTime, int daysBack, int daysForward)
    {
        DateTime start = currentTime;
        DateTime end = currentTime;
        if (daysBack != 0) 
            start = new DateMidnight(currentTime).minusDays(daysBack).toDateTime();
        if (daysForward != 0) 
            end = new DateMidnight(currentTime).plusDays(daysForward).toDateTime();
        return new Interval(start, end); 

    }

}

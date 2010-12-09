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

public class DateTimeUtil {
    
    public static List<DateTime> getDayList(Interval interval, DateTimeZone dateTimeZone)
    {
        List<DateTime> dayList = new ArrayList<DateTime>();
        
        
        for (DateTime intervalDay = interval.getStart(); intervalDay.isBefore(interval.getEnd()) || intervalDay.isEqual(interval.getEnd()); intervalDay = intervalDay.plusDays(1)) {
            LocalDate localDate = new LocalDate(intervalDay);
            DateTime day = localDate.toDateTimeAtStartOfDay(dateTimeZone);
            dayList.add(day);
        }
        
        return dayList;
    }
    
    public static Interval getStartEndInterval(String start, String end, String pattern, DateTimeZone dateTimeZone)
    {
        // interval start date 00:00:00 to end date 23:59:59
        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        LocalDate localDate = new LocalDate(new DateMidnight(fmt.parseDateTime(start), DateTimeZone.UTC));
        DateTime startDate = localDate.toDateTimeAtStartOfDay(dateTimeZone);
        localDate = new LocalDate(new DateMidnight(fmt.parseDateTime(end), DateTimeZone.UTC));
        DateTime endDate = localDate.toDateTimeAtStartOfDay(dateTimeZone).plusDays(1).minusSeconds(1);

        return new Interval(startDate, endDate);

    }

    public static Interval getExpandedInterval(Interval interval, DateTimeZone dateTimeZone, int daysBack, int daysForward)
    {
        LocalDate localDate = new LocalDate(new DateMidnight(interval.getStart(), dateTimeZone));
        DateTime startDate = localDate.toDateTimeAtStartOfDay(dateTimeZone).minusDays(daysBack);
        localDate = new LocalDate(new DateMidnight(interval.getEnd(), dateTimeZone));
        DateTime endDate = localDate.toDateTimeAtStartOfDay(dateTimeZone).plusDays(1+daysForward).minusSeconds(1);

        return new Interval(startDate, endDate);

    }

    public static Interval getDaysBackInterval(DateTime endDateTime, DateTimeZone dateTimeZone, int daysBack)
    {
        LocalDate localDate = new LocalDate(new DateMidnight(endDateTime, dateTimeZone));
        DateTime startDate = localDate.toDateTimeAtStartOfDay(dateTimeZone).minusDays(daysBack);
//        localDate = new LocalDate(endDateTime, dateTimeZone);
//        DateTime endDate = localDate.

        return new Interval(startDate, endDateTime);

    }
}

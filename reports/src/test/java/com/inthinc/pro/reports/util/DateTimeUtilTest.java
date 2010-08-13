package com.inthinc.pro.reports.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;


public class DateTimeUtilTest {
    
    DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("MM/dd/yyyy");
    DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
    DateTimeFormatter printFormatter = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss z");
    
    @Test
    public void dayListTest(){
        Interval interval = new Interval(dateFormatter.parseDateTime("02/01/2010"), dateFormatter.parseDateTime("02/05/2010"));
        Iterator iterator = DateTimeZone.getAvailableIDs().iterator();
        while (iterator.hasNext()) {
            DateTimeZone dateTimeZone = DateTimeZone.forID((String )iterator.next());
//            System.out.println(dateTimeZone.getID());
            List<DateTime> dayList = DateTimeUtil.getDayList(interval, dateTimeZone);
            assertEquals("num days ", 5, dayList.size());
            for (int i = 0, dcnt = 1 ; i < dayList.size(); i++, dcnt++) {
                assertEquals("day " + dcnt, "02/0" + dcnt + "/2010", dateFormatter.print(dayList.get(i)));
//                System.out.println(dateFormatter.print(dayList.get(i)));
            }
        }
    }
    
    
    @Test
    public void startEndIntervalTest()
    {
        Iterator iterator = DateTimeZone.getAvailableIDs().iterator();
        while (iterator.hasNext()) {
            DateTimeZone dateTimeZone = DateTimeZone.forID((String )iterator.next());
            Interval interval = DateTimeUtil.getStartEndInterval("02/01/2010", "02/05/2010", "MM/dd/yyyy", dateTimeZone);
            
//            System.out.println(dateTimeZone.getID() + " " + dateTimeZone.toTimeZone().getOffset(interval.getStartMillis()));
//            System.out.println(printFormatter.print(interval.getStart()));
//            System.out.println(printFormatter.print(interval.getEnd()));
            assertTrue("start", printFormatter.print(interval.getStart()).startsWith("02/01/2010 00:00:00"));
            assertTrue("end", printFormatter.print(interval.getEnd()).startsWith("02/05/2010 23:59:59"));
        }
    }

    @Test
    public void expandedIntervalTest(){
        Iterator iterator = DateTimeZone.getAvailableIDs().iterator();
        while (iterator.hasNext()) {
            DateTimeZone dateTimeZone = DateTimeZone.forID((String )iterator.next());
            Interval interval = DateTimeUtil.getStartEndInterval("02/01/2010", "02/05/2010", "MM/dd/yyyy", dateTimeZone);
            Interval expandedInterval = DateTimeUtil.getExpandedInterval(interval, dateTimeZone, 1, 1);
            
//            System.out.println(dateTimeZone.getID() + " " + dateTimeZone.toTimeZone().getOffset(expandedInterval.getStartMillis()));
//            System.out.println(printFormatter.print(expandedInterval.getStart()));
//            System.out.println(printFormatter.print(expandedInterval.getEnd()));
            assertTrue("start", printFormatter.print(expandedInterval.getStart()).startsWith("01/31/2010 00:00:00"));
            assertTrue("end", printFormatter.print(expandedInterval.getEnd()).startsWith("02/06/2010 23:59:59"));
        }
        
  
    
    
    }
    
    

    @Test
    public void daysBackIntervalTest()
    {
        Date currentTime = new Date();
        DateTime currentDateTime = new DateTime(currentTime, DateTimeZone.UTC); 
        int daysBack = 5;
//        System.out.println(printFormatter.print(currentDateTime));
        
        Iterator iterator = DateTimeZone.getAvailableIDs().iterator();
        while (iterator.hasNext()) {
            DateTimeZone dateTimeZone = DateTimeZone.forID((String )iterator.next());
            
            Interval interval = DateTimeUtil.getDaysBackInterval(currentDateTime, dateTimeZone, daysBack);
            
//            System.out.println(dateTimeZone.getID() + " " + dateTimeZone.toTimeZone().getOffset(interval.getStartMillis()));
//            System.out.println(printFormatter.print(interval.getStart()));
//            System.out.println(printFormatter.print(interval.getEnd()));

            int daysDelta = interval.getEnd().getDayOfYear() - interval.getStart().getDayOfYear();
            assertEquals("days back", daysBack, daysDelta);
            assertEquals("end", currentDateTime.getMillis(), interval.getEnd().getMillis());
        }
    }

/*
 *     public static List<DateTime> getDayList(Interval interval, DateTimeZone dateTimeZone)
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
    
 */

}

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

import com.inthinc.hos.util.DateUtil;


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
            List<DateTime> dayList = DateTimeUtil.getDayList(interval, dateTimeZone);
            assertEquals("num days ", 5, dayList.size());
            for (int i = 0, dcnt = 1 ; i < dayList.size(); i++, dcnt++) {
                assertEquals("day " + dcnt, "02/0" + dcnt + "/2010", dateFormatter.print(dayList.get(i)));
            }
        }
    }
    @Test
    public void dayIntervalListTest(){
        Interval interval = new Interval(dateFormatter.parseDateTime("02/01/2010"), dateFormatter.parseDateTime("02/05/2010"));
        Iterator iterator = DateTimeZone.getAvailableIDs().iterator();
        while (iterator.hasNext()) {
            DateTimeZone dateTimeZone = DateTimeZone.forID((String )iterator.next());
            List<Interval> dayIntervalList = DateTimeUtil.getDayIntervalList(interval, dateTimeZone);
            assertEquals("num days ", 5, dayIntervalList.size());
            for (int i = 0, dcnt = 1 ; i < dayIntervalList.size(); i++, dcnt++) {
               assertEquals("start day " + dcnt, "02/0" + dcnt + "/2010", dateFormatter.print(dayIntervalList.get(i).getStart()));
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
        
        Iterator iterator = DateTimeZone.getAvailableIDs().iterator();
        while (iterator.hasNext()) {
            DateTimeZone dateTimeZone = DateTimeZone.forID((String )iterator.next());
            
            // a bit of a kludge to get the test to work, but if the interval spans the dst change 
            // the days count can be one off
            Interval interval = DateTimeUtil.getDaysBackInterval(currentDateTime, dateTimeZone, daysBack);
            
            int daysDelta = (int)((interval.getEndMillis()-interval.getStartMillis()) / DateUtil.MILLISECONDS_IN_ONE_DAY);
            if (daysDelta != daysBack) {
                System.out.println("tz: " + dateTimeZone.getID() + " " + daysDelta);
                System.out.println(interval.getStart() + " " + interval.getEnd());
                if ((dateTimeZone.isStandardOffset(interval.getStartMillis())&& !dateTimeZone.isStandardOffset(interval.getEndMillis())) ||
                        (!dateTimeZone.isStandardOffset(interval.getStartMillis())&& dateTimeZone.isStandardOffset(interval.getEndMillis()))) {
                        System.out.println("interval spans dst change " + dateTimeZone.getID()); 
                        continue;
                    }
            }
            assertEquals("days back " + dateTimeZone.getID(), daysBack, daysDelta);
            assertEquals("end", currentDateTime.getMillis(), interval.getEnd().getMillis());
        }
    }


}

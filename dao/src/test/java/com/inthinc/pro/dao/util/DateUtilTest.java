package com.inthinc.pro.dao.util;

import static org.junit.Assert.assertTrue;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.junit.Test;



public class DateUtilTest {

    @Test
    public void testTimeInInterval() {
        
        int numDays = 7;
        DateTime end = new DateMidnight(DateTimeZone.UTC).toDateTime();
        DateTime start = new DateTime(end, DateTimeZone.UTC).minusDays(numDays-1);
        Interval interval  = new Interval(start, end);
    System.out.println(interval + " ");        

        DateTimeZone mountainTZ = DateTimeZone.forID("US/Mountain");
        DateTime loginStart = new DateTime(start, mountainTZ);
        assertTrue("time not in interval" , !DateUtil.timeInInterval(loginStart, mountainTZ, interval));
        
        
        loginStart = new DateTime(start.plusDays(1), mountainTZ);
        assertTrue("time in interval" , DateUtil.timeInInterval(loginStart, mountainTZ, interval));
        
        loginStart = new DateTime(start.plusDays(7), mountainTZ);
        assertTrue("time not in interval" , !DateUtil.timeInInterval(loginStart, mountainTZ, interval));
            
    }
}

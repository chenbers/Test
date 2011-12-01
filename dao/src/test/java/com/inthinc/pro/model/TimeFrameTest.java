package com.inthinc.pro.model;

import static org.junit.Assert.*;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.junit.Ignore;
import org.junit.Test;

public class TimeFrameTest {

	   @Test
	    public void timeIntervalTest(){
		   
		   //South Africa GMT+2.00
	    	DateTimeZone southAfricaTimeZone = DateTimeZone.forOffsetHours(2);
	    	Interval sainterval = TimeFrame.ONE_DAY_AGO.getInterval(southAfricaTimeZone);
	        //Original calculation
	    	DateTime saStart = sainterval.getStart().toDateTime(DateTimeZone.UTC);
	        DateTime saintervalToUse = sainterval.getStart().toDateTime(DateTimeZone.UTC).toDateMidnight().toDateTime();
	        //Revised calculation
	        DateTime saOffsetStart = sainterval.getStart().plusMillis(southAfricaTimeZone.getOffset(sainterval.getStart()));
	        DateTime sautcOffsetStart = sainterval.getStart().plusMillis(southAfricaTimeZone.getOffset(sainterval.getStart())).toDateTime(DateTimeZone.UTC);
	        DateTime sautcOffsetInterval = sainterval.getStart().plusMillis(southAfricaTimeZone.getOffset(sainterval.getStart())).toDateTime(DateTimeZone.UTC).toDateMidnight().toDateTime();

	        //Utah GMT-7
	    	DateTimeZone utahTimeZone = DateTimeZone.forOffsetHours(-7);
	    	Interval uinterval = TimeFrame.ONE_DAY_AGO.getInterval(utahTimeZone);
	        //Original calculation
	    	DateTime utStart = uinterval.getStart().toDateTime(DateTimeZone.UTC);
	        DateTime utintervalToUse = uinterval.getStart().toDateTime(DateTimeZone.UTC).toDateMidnight().toDateTime();
	        //Revised calculation
	        DateTime utOffsetStart = uinterval.getStart().plusMillis(utahTimeZone.getOffset(uinterval.getStart()));
	        DateTime ututcOffsetStart = uinterval.getStart().plusMillis(utahTimeZone.getOffset(uinterval.getStart())).toDateTime(DateTimeZone.UTC);
	        DateTime ututcOffsetInterval = uinterval.getStart().plusMillis(utahTimeZone.getOffset(uinterval.getStart())).toDateTime(DateTimeZone.UTC).toDateMidnight().toDateTime();
	        int j=1;
	   }

	   @Test
	   public void weekIntervalTest() {
	       
	       TimeFrame weekTimeFrame = TimeFrame.WEEK;
	       System.out.println("weekTimeFrame: " + weekTimeFrame.getInterval());
	       
	       List<Interval> intervalList = weekTimeFrame.getWeekEndIntervalList();
	       assertEquals("expect 1 entry in week interval list for timeFrame WEEK", 1, intervalList.size());
	       for (Interval interval : intervalList)
	           System.out.println("interval: " + interval);
	       
           TimeFrame monthTimeFrame = TimeFrame.MONTH;
           System.out.println("monthTimeFrame: " + monthTimeFrame.getInterval());
           
           intervalList = monthTimeFrame.getWeekEndIntervalList();
           assertEquals("expect 4 entries in week interval list for timeFrame MONTH", 4, intervalList.size());
           for (Interval interval : intervalList)
               System.out.println("interval: " + interval);
	       
	   }
}

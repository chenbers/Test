package com.inthinc.pro.model;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.AfterClass;
import org.junit.Test;

public class TimeFrameTest {
    
        @AfterClass
        public static void tearDownAfterClass() throws Exception {
            
            TimeFrame.setCurrentForTesting(null);            
        }
       

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
	   
	   public String[] expectedUTC = {
	           "2011-12-06 00:00:00 UTC", "2011-12-06 23:59:59 UTC",// TODAY
	           "2011-12-06 00:00:00 UTC", "2011-12-06 23:59:59 UTC",// DAY
	           "2011-12-05 00:00:00 UTC", "2011-12-06 00:00:00 UTC",// ONE_DAY_AGO
	           "2011-12-04 00:00:00 UTC", "2011-12-05 00:00:00 UTC",// TWO_DAYS_AGO
	           "2011-12-03 00:00:00 UTC", "2011-12-04 00:00:00 UTC",// THREE_DAYS_AGO
	           "2011-12-02 00:00:00 UTC", "2011-12-03 00:00:00 UTC",// FOUR_DAYS_AGO
	           "2011-12-01 00:00:00 UTC", "2011-12-02 00:00:00 UTC",// FIVE_DAYS_AGO
	           "2011-11-30 00:00:00 UTC", "2011-12-01 00:00:00 UTC",// SIX_DAYS_AGO
	           "2011-11-29 00:00:00 UTC", "2011-11-30 00:00:00 UTC",// SEVEN_DAYS_AGO
	           "2011-11-30 00:00:00 UTC", "2011-12-06 23:59:59 UTC",// WEEK
	           "2011-11-05 00:00:00 UTC", "2011-12-06 23:59:59 UTC",// LAST_THIRTY_DAYS
	           "2011-12-01 00:00:00 UTC", "2011-12-06 23:59:59 UTC",// MONTH
	           "2011-09-05 00:00:00 UTC", "2011-12-06 23:59:59 UTC",// THREE_MONTHS
	           "2011-06-05 00:00:00 UTC", "2011-12-06 23:59:59 UTC",// SIX_MONTHS
	           "2010-12-06 00:00:00 UTC", "2011-12-06 23:59:59 UTC",// YEAR
	           "2011-11-27 00:00:00 UTC", "2011-12-03 23:59:59 UTC",// SUN_SAT WEEK
	           "2011-11-01 00:00:00 UTC", "2011-11-30 23:59:59 UTC",// LAST_MONTH
               "2011-11-29 00:00:00 UTC", "2011-12-06 23:59:59 UTC",// PAST 7 DAYS
               "2011-12-06 00:00:00 UTC", "2011-12-06 23:59:59 UTC",//CUSTOM_RANGE
	   };
       public String[] expectedMountain = {
               "2011-12-05 00:00:00 MST", "2011-12-05 23:59:59 MST",// TODAY
               "2011-12-05 00:00:00 MST", "2011-12-05 23:59:59 MST",// DAY
               "2011-12-04 00:00:00 MST", "2011-12-05 00:00:00 MST",// ONE_DAY_AGO
               "2011-12-03 00:00:00 MST", "2011-12-04 00:00:00 MST",// TWO_DAYS_AGO
               "2011-12-02 00:00:00 MST", "2011-12-03 00:00:00 MST",// THREE_DAYS_AGO
               "2011-12-01 00:00:00 MST", "2011-12-02 00:00:00 MST",// FOUR_DAYS_AGO
               "2011-11-30 00:00:00 MST", "2011-12-01 00:00:00 MST",// FIVE_DAYS_AGO
               "2011-11-29 00:00:00 MST", "2011-11-30 00:00:00 MST",// SIX_DAYS_AGO
               "2011-11-28 00:00:00 MST", "2011-11-29 00:00:00 MST",// SEVEN_DAYS_AGO
               "2011-11-29 00:00:00 MST", "2011-12-05 23:59:59 MST",// WEEK
               "2011-11-05 00:00:00 MDT", "2011-12-05 23:59:59 MST",// LAST_THIRTY_DAYS
               "2011-12-01 00:00:00 MST", "2011-12-05 23:59:59 MST",// MONTH
               "2011-09-05 00:00:00 MDT", "2011-12-05 23:59:59 MST",// THREE_MONTHS
               "2011-06-05 00:00:00 MDT", "2011-12-05 23:59:59 MST",// SIX_MONTHS
               "2010-12-05 00:00:00 MST", "2011-12-05 23:59:59 MST",// YEAR
               "2011-11-27 00:00:00 MST", "2011-12-03 23:59:59 MST",// SUN_SAT WEEK
               "2011-11-01 00:00:00 MDT", "2011-11-30 23:59:59 MST",// LAST_MONTH
               "2011-11-28 00:00:00 MST", "2011-12-05 23:59:59 MST",// PAST 7 DAYS
               "2011-12-05 00:00:00 MST", "2011-12-05 23:59:59 MST",// CUSTOM RANGE
       };
       
       public static long TEST_TIME = 1323129600000l; // 2011-12-06 00:00:00 UTC
       
	   @Test
	   public void timeFrameTest()
	   {
	       DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss z");
	       TimeFrame.setCurrentForTesting(new DateTime(TEST_TIME));
	       
	       int cnt = 0;
	       for (TimeFrame timeFrame : TimeFrame.values()) {
	           System.out.println("\"" + formatter.print(timeFrame.getInterval().getStart()) + "\", \"" + formatter.print(timeFrame.getInterval().getEnd()) + "\"," + "// " + timeFrame);
	           StringBuffer startBuffer = new StringBuffer();
	           StringBuffer endBuffer = new StringBuffer();
	           formatter.printTo(startBuffer, timeFrame.getInterval().getStart());
               formatter.printTo(endBuffer, timeFrame.getInterval().getEnd());
               assertEquals(timeFrame + " UTC Start", expectedUTC[cnt++], startBuffer.toString());
               assertEquals(timeFrame + " UTC End", expectedUTC[cnt++], endBuffer.toString());
	       }

           cnt = 0;
           for (TimeFrame timeFrame : TimeFrame.values()) {
               DateTimeZone dateTimeZone = DateTimeZone.forID("US/Mountain");
             System.out.println("\"" + formatter.print(timeFrame.getInterval(dateTimeZone).getStart()) + "\", \"" + formatter.print(timeFrame.getInterval(dateTimeZone).getEnd()) + "\"," + "// " + timeFrame);
               StringBuffer startBuffer = new StringBuffer();
               StringBuffer endBuffer = new StringBuffer();
               formatter.printTo(startBuffer, timeFrame.getInterval(dateTimeZone).getStart());
               formatter.printTo(endBuffer, timeFrame.getInterval(dateTimeZone).getEnd());
               assertEquals(timeFrame + " Mountain TZ Start", expectedMountain[cnt++], startBuffer.toString());
               assertEquals(timeFrame + " Mountain TZ End", expectedMountain[cnt++], endBuffer.toString());
           }
	       
	       
	   }

}

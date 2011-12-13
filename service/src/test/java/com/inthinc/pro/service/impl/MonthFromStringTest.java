package com.inthinc.pro.service.impl;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Test;

import com.inthinc.pro.util.DateUtil;

public class MonthFromStringTest {

	@Test
	public void monthFromEmptyStringTest(){
		DateTime now = new DateTime();
		try {
			Interval interval = DateUtil.getIntervalFromMonth("");
			DateTime start = interval.getStart();
			int month = start.getMonthOfYear();
			int dayOfMonth = start.dayOfMonth().get();
			assertEquals(now.getMonthOfYear(),month);
			assertEquals(1,dayOfMonth);
			DateTime end = interval.getEnd();
			int endMonth = end.dayOfMonth().get();
			assertEquals((now.getMonthOfYear()+1)%12,endMonth);
			assertEquals(1,end.dayOfMonth().get());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void monthFromStringThisMonthTest(){
		DateTime now = new DateTime();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM");
        String formattedTime = dateFormatter.format(now.toDate());
        
		try {
			Interval interval = DateUtil.getIntervalFromMonth(formattedTime);
			DateTime start = interval.getStart();
			int month = start.getMonthOfYear();
			int dayOfMonth = start.dayOfMonth().get();
			assertEquals(now.getMonthOfYear(),month);
			assertEquals(1,dayOfMonth);
			assertEquals(now.getYear()-1, start.getYear());
			DateTime end = interval.getEnd();
			int endMonth = end.dayOfMonth().get();
			assertEquals((now.getMonthOfYear()+1)%12,endMonth);
			assertEquals(1,end.dayOfMonth().get());
			assertEquals(now.getYear(), end.getYear());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void monthFromStringOtherMonthTest(){
		DateTime now = new DateTime();

		try {
			Interval interval = DateUtil.getIntervalFromMonth("Jan");
			DateTime start = interval.getStart();
			int month = start.getMonthOfYear();
			int dayOfMonth = start.dayOfMonth().get();
			assertEquals(1,month);
			assertEquals(1,dayOfMonth);
			assertEquals(now.getYear(), start.getYear());
			DateTime end = interval.getEnd();
			int endMonth = end.dayOfMonth().get();
			assertEquals(1,endMonth);
			assertEquals(1,end.dayOfMonth().get());
			assertEquals(now.getYear(), end.getYear());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

package com.inthinc.pro.util;

import java.util.Locale;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;

import com.inthinc.pro.model.TimeFrame;

public class TimeFrameUtil {

	public static String getTimeFrameStr(TimeFrame timeFrame, Locale locale)
	{
		if (timeFrame.equals(TimeFrame.MONTH))
			return getMonthItem(locale);

		if (timeFrame.equals(TimeFrame.TWO_DAYS_AGO))
			return getDayItem(2, locale);
		if (timeFrame.equals(TimeFrame.THREE_DAYS_AGO))
			return getDayItem(3, locale);
		if (timeFrame.equals(TimeFrame.FOUR_DAYS_AGO))
			return getDayItem(4, locale);
		if (timeFrame.equals(TimeFrame.FIVE_DAYS_AGO))
			return getDayItem(5, locale);
		if (timeFrame.equals(TimeFrame.SIX_DAYS_AGO))
			return getDayItem(6, locale);

		return getTimeFrameItem(timeFrame, locale);
	}

	private static String getTimeFrameItem(TimeFrame selectTimeFrame, Locale locale)
	{
		return  MessageUtil.getMessageString("timeFrame_"+selectTimeFrame.name(), locale);
	}

	private static String getDayItem(int index, Locale locale)
	{
        Interval interval = new Interval(Days.SEVEN, new DateMidnight());
        return interval.getEnd().minusDays(index).dayOfWeek().getAsText(locale);
	}
	private static String getMonthItem(Locale locale)
	{
        return new DateTime().monthOfYear().getAsText(locale);
	}
    private static String getLastMonthItem(Locale locale)
    {
        return new DateTime().minusMonths(1).monthOfYear().getAsText(locale);
    }
}

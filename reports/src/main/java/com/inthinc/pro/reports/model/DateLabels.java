package com.inthinc.pro.reports.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.inthinc.pro.reports.util.MessageUtil;


public class DateLabels {

	
	private DateFormat dayFormatter;
	private DateFormat monthDayFormatter;
	private DateFormat monthFormatter;

	private static final String DAY_ONE = "01";
	
	public DateLabels(Locale locale)
	{
	    dayFormatter = new SimpleDateFormat(MessageUtil.getMessageString("justDay", locale), locale);
		dayFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
	    monthDayFormatter = new SimpleDateFormat(MessageUtil.getMessageString("monthDay", locale), locale);
		monthDayFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		monthFormatter = new SimpleDateFormat("MMM", locale);
		monthFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		
	}
	public DateLabels(Locale locale, String dayDateFormat)
	{
	    dayFormatter = new SimpleDateFormat(dayDateFormat, locale);
		dayFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
	    monthDayFormatter = new SimpleDateFormat(dayDateFormat, locale);
		monthDayFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		monthFormatter = new SimpleDateFormat("MMM", locale);
		monthFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		
	}
	
	
	public List<String> createDayLabelList(List<Date> dateList)
	{
	    List<String> labelList = new ArrayList<String>();
	    for (Date date : dateList)
	    {
       		if (date == null)
       		{
       			labelList.add(" ");
       			continue;
       		}
       		String label = dayFormatter.format(date);
            if (label.equals(DAY_ONE))
            {
            	label = monthDayFormatter.format(date);
            }
            labelList.add(label);
		}
		return labelList;
	}

	public List<String> createMonthLabelList(List<Date> dateList)
	{
		
	    List<String> labelList = new ArrayList<String>();
			
       	for (Date date : dateList) {
       		if (date == null)
       		{
       			labelList.add(" ");
       			continue;
       		}
       		labelList.add(monthFormatter.format(date));
       	}           
        return labelList;
	}


}

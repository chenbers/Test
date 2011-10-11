package com.inthinc.pro.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class DateFormatTest {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
	
    @Test
    public void testDateFormat(){
    	Date date = buildDateTimeFromString("2011-10-11T12:59:00-0600");
    	int i = 0;
    }
    private Date buildDateTimeFromString(String strDate) {
        if(strDate == null)
            return null;
        
        DateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT); 
        try
        {
            Date convertedDate = df.parse(strDate);           
            return convertedDate;
        } catch (ParseException e)
        {
            e.printStackTrace();
            return null;
        }
    }


}

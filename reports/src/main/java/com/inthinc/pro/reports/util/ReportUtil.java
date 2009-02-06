package com.inthinc.pro.reports.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.inthinc.pro.model.Duration;

public class ReportUtil
{
    
    private static List <String> monthLbls = new ArrayList<String>() {
        {
            add("JAN");add("FEB");add("MAR");add("APR");add("MAY");add("JUN");
            add("JUL");add("AUG");add("SEP");add("OCT");add("NOV");add("DEC");
            add("JAN");add("FEB");add("MAR");add("APR");add("MAY");add("JUN");
            add("JUL");add("AUG");add("SEP");add("OCT");add("NOV");add("DEC");
        }
    };
    
    public static List <String> lineChartEntityColorKey = new ArrayList<String>(){
        {
            add("#880000");add("#008800");
            add("#000088");add("#888800");
            add("#880088");add("#008888");
            add("#FF0000");add("#00FF00");
            add("#0000FF");add("#FF00FF");  
        }
    };
    
    public static List<String> pieChartEntityColorKey = new ArrayList<String>()
    {
        {
            add("FF0101");
            add("FF6601");
            add("F6B305");
            add("1E88C8");
            add("6B9D1B");
        }
    };
    
    public static List<String> createMonthList(Duration duration)
    {
        List<String> monthList = new ArrayList<String>();
        
        Calendar cal;
        DateFormat dateFormatter = new SimpleDateFormat("dd");
        
        if ( duration == Duration.DAYS ) {
            for ( int i = 0; i <= 29; i++ )
            {
                cal = Calendar.getInstance();
                cal.add(Calendar.DAY_OF_MONTH, -i);
           
                monthList.add(0, dateFormatter.format(cal.getTime()));
            }       
        } else {
            int num = convertToMonths(duration);             
            //What month is it? Remember, implemented with jan as 0. Also,
            //add 12 so the short date ranges work...
            GregorianCalendar calendar = new GregorianCalendar();       
            int currentMonth = calendar.get(GregorianCalendar.MONTH)+1;
            
            //Start there
            for ( int i = currentMonth + 12 - num; i < currentMonth + 12 ; i++ ) {
                monthList.add(monthLbls.get(i));
            }           
        }
        
        return monthList;
    }
    
    public static int convertToMonths(Duration duration) {
        int months = 0;
        
        if (           duration.equals(Duration.THREE) ) {
            return 3;
        } else if (    duration.equals(Duration.SIX) ) {
            return 6;
        } else if (    duration.equals(Duration.TWELVE) ) {
            return 12;
        }
        
        return months;
    }

}

package com.inthinc.pro.reports.util;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import com.inthinc.pro.model.Duration;

public class ReportUtil
{
    
//    private static List <String> monthLbls = new ArrayList<String>() {
//        {
//            add("JAN");add("FEB");add("MAR");add("APR");add("MAY");add("JUN");
//            add("JUL");add("AUG");add("SEP");add("OCT");add("NOV");add("DEC");
//            add("JAN");add("FEB");add("MAR");add("APR");add("MAY");add("JUN");
//            add("JUL");add("AUG");add("SEP");add("OCT");add("NOV");add("DEC");
//        }
//    };
    
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
    
//    public static List<String> createMonthList(Duration duration)
//    {
//        return createMonthList(duration, "dd");
//    }
    
	public static List<String> createMonthList(Duration duration, String dateFormat, Locale locale)
	{
	    List<String> monthList = new ArrayList<String>();
	    
	    Calendar cal= Calendar.getInstance();
	    DateFormat dateFormatter = new SimpleDateFormat(dateFormat, locale);
	    	    
	    if ( duration == Duration.DAYS ) {
		    cal.add(Calendar.DAY_OF_MONTH, -29);
            for ( int i = 0; i <= 29; i++ )
            {
                
                String day = dateFormatter.format(cal.getTime());
                           
                monthList.add(day);
                
                cal.add(Calendar.DAY_OF_MONTH, 1);

            }       
        } else {
        	int months = duration.getDvqCount();
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.add(Calendar.MONTH,-months+1);
            DateFormat df = new SimpleDateFormat("MMM",locale);
            //Start there
            for ( int i = 0; i < months; i++ ) {
                monthList.add(df.format(calendar.getTime()));
            	calendar.add(Calendar.MONTH, 1);
            }           
        }
	    
	    return monthList;
	}
    
//    public static int convertToMonths(Duration duration) {
//        int months = 0;
//        
//        if (           duration.equals(Duration.THREE) ) {
//            return 3;
//        } else if (    duration.equals(Duration.SIX) ) {
//            return 6;
//        } else if (    duration.equals(Duration.TWELVE) ) {
//            return 12;
//        }
//        
//        return months;
//    }

	public static String getScoreLegendString(Double start, Double end, Locale locale){
		
		NumberFormat nf = NumberFormat.getNumberInstance(locale);
		nf.setMinimumFractionDigits(1);
		nf.setMaximumFractionDigits(1);
		
		return nf.format(start)+" - "+nf.format(end);
	}
}

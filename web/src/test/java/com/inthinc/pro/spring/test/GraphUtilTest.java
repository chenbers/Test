package com.inthinc.pro.spring.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.Test;

import com.inthinc.pro.backing.BaseBeanTest;
import com.inthinc.pro.model.BaseScore;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.util.GraphicUtil;

public class GraphUtilTest extends BaseBeanTest{
	
	private static long NOV_15_2009 = 1258243200000L;  // UTC
	private static long JAN_01_2009 = 1230768000000L; 	// UTC
	
	private String[] expectedDayLabel = {
			"17","18","19","20","21","22","23","24","25","26","27","28","29","30","31",
			"Nov 01","02","03","04","05","06","07","08","09","10","11","12","13","14","15",
	};
	private String[] expectedRomanianDayLabel = {
			"17","18","19","20","21","22","23","24","25","26","27","28","29","30","31",
			"Nov 01","02","03","04","05","06","07","08","09","10","11","12","13","14","15",
	};
	private String[] expectedMonthLabel = {
			"Aug","Sep","Oct","Nov","Dec","Jan"
	};
	private String[] expectedRomanianMonthLabel = {
			"Aug","Sep","Oct","Nov","Dec","Ian"
	};
	
	@Test
	public void createMonthString() throws Exception{
		
		List<BaseScore> dayScoreList = new ArrayList<BaseScore>();
	    Calendar cal= new GregorianCalendar();
	    cal.setTimeZone(TimeZone.getTimeZone("GMT"));
	    cal.setTime(new Date (NOV_15_2009));
	    cal.add(Calendar.DAY_OF_MONTH, -29);
	    for ( int i = 0; i <= 29; i++ )
        {
	    	dayScoreList.add(new BaseScore(cal.getTime()));
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
		
		List<BaseScore> monthScoreList = new ArrayList<BaseScore>();
	    cal.setTime(new Date (JAN_01_2009));
	    cal.add(Calendar.MONTH, -5);
	    for ( int i = 0; i <= 5; i++ )
        {
    		monthScoreList.add(new BaseScore(cal.getTime()));
            cal.add(Calendar.MONTH, 1);

        }
		
		
		List<String> labelList = GraphicUtil.createDateLabelList(dayScoreList, Duration.DAYS, Locale.US);
		compareDayLabelList("US Locale", labelList);
		labelList = GraphicUtil.createDateLabelList(monthScoreList, Duration.SIX, Locale.US);
		compareMonthLabelList("US Locale", labelList);

		Locale romanian = null;
		Locale availableLocales[] = Locale.getAvailableLocales();
		for(int i=0; i<availableLocales.length; i++){
			if (availableLocales[i].getDisplayLanguage().equalsIgnoreCase("romanian"))
			{
				romanian = availableLocales[i];
				break;
			}
		}
		labelList = GraphicUtil.createDateLabelList(dayScoreList, Duration.DAYS, romanian);
		compareRomanianDayLabelList("romanian Locale", labelList);
		labelList = GraphicUtil.createDateLabelList(monthScoreList, Duration.SIX, romanian);
		compareRomanianMonthLabelList("romanian Locale", labelList);

	}

	private void compareMonthLabelList(String string, List<String> labelList) {
		assertEquals("Number of Months", expectedMonthLabel.length, labelList.size());
		int i = 0;
		for (String label : labelList)
		{
			assertEquals("Month #" + i, expectedMonthLabel[i++], label);
		}
		
	}

	private void compareDayLabelList(String string, List<String> labelList) {
		assertEquals("Number of Days", expectedDayLabel.length, labelList.size());
		int i = 0;
		for (String label : labelList)
		{
			assertEquals("Day #" + i, expectedDayLabel[i++], label);
		}
		
	}

	private void compareRomanianMonthLabelList(String string, List<String> labelList) {
		assertEquals("Number of Months", expectedRomanianMonthLabel.length, labelList.size());
		int i = 0;
		for (String label : labelList)
		{
			assertEquals("Month #" + i, expectedRomanianMonthLabel[i++], label);
		}
		
	}

	private void compareRomanianDayLabelList(String string, List<String> labelList) {
		assertEquals("Number of Days", expectedRomanianDayLabel.length, labelList.size());
		int i = 0;
		for (String label : labelList)
		{
			assertEquals("Day #" + i, expectedRomanianDayLabel[i++], label);
		}
		
	}
}

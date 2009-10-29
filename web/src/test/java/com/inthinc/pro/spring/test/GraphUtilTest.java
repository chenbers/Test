package com.inthinc.pro.spring.test;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Test;

import com.inthinc.pro.backing.BaseBeanTest;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.util.GraphicUtil;

public class GraphUtilTest extends BaseBeanTest{
	
	@Test
	public void createMonthString() throws Exception{
		
		String monthList = GraphicUtil.createMonthsString(Duration.DAYS, Locale.US);
		System.out.println(monthList);
		monthList = GraphicUtil.createMonthsString(Duration.TWELVE, Locale.US);
		System.out.println(monthList);
		monthList = GraphicUtil.createMonthsString(Duration.THREE, Locale.US);
		System.out.println(monthList);
		monthList = GraphicUtil.createMonthsString(Duration.SIX, Locale.US);
		System.out.println(monthList);
		List<String> months = GraphicUtil.createMonthList(Duration.DAYS, "M/dd", Locale.US);
		for(String month:months){
			System.out.println(month);
		}
		System.out.println("US DecimalSeparator is:"+GraphicUtil.getDecimalSeparator(Locale.US));
		System.out.println("US GroupingSeparator is:"+GraphicUtil.getGroupingSeparator(Locale.US));
		Locale availableLocales[] = Locale.getAvailableLocales();
		Map<String,Locale> localeMap = new HashMap<String,Locale>();
		for(int i=0; i<availableLocales.length; i++){
			localeMap.put(availableLocales[i].getDisplayLanguage(), availableLocales[i]);
		}
		Locale romanian = localeMap.get("Romanian");
		monthList = GraphicUtil.createMonthsString(Duration.DAYS, romanian);
		System.out.println(monthList);
		monthList = GraphicUtil.createMonthsString(Duration.TWELVE, romanian);
		System.out.println(monthList);
		monthList = GraphicUtil.createMonthsString(Duration.THREE, romanian);
		System.out.println(monthList);
		monthList = GraphicUtil.createMonthsString(Duration.SIX, romanian);
		System.out.println(monthList);
		months = GraphicUtil.createMonthList(Duration.DAYS, "d/M", romanian);
		for(String month:months){
			System.out.println(month);
		}
		System.out.println("Romanian DecimalSeparator is:"+GraphicUtil.getDecimalSeparator(romanian));
		System.out.println("Romanian GroupingSeparator is:"+GraphicUtil.getGroupingSeparator(romanian));

	}

}

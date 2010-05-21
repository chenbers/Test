package com.inthinc.pro.reports.util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.junit.Ignore;
import org.junit.Test;

public class MessagesTest {

	
	static Map<String, String> nonTranslatedMap = new HashMap<String, String>();
	static {
	}
	static Map<String, String> nonTranslatedMap_ro = new HashMap<String, String>();
	static {
		nonTranslatedMap_ro.put("timeFormat",  "not_translated"); 
		nonTranslatedMap_ro.put("justDay",  "not_translated"); 
		nonTranslatedMap_ro.put("monthDay",  "not_translated"); 
		nonTranslatedMap_ro.put("ScoreType.SCORE_SPEEDING_21_30",  "not_translated");  // 1-30 mph
		nonTranslatedMap_ro.put("ScoreType.SCORE_SPEEDING_21_30.metric",  "not_translated");  //1-48 kph
		nonTranslatedMap_ro.put("ScoreType.SCORE_SPEEDING_31_40",  "not_translated");  //31-40 mph
		nonTranslatedMap_ro.put("ScoreType.SCORE_SPEEDING_31_40.metric",  "not_translated");  //49-64 kph
		nonTranslatedMap_ro.put("ScoreType.SCORE_SPEEDING_41_54",  "not_translated");  //41-54 mph
		nonTranslatedMap_ro.put("ScoreType.SCORE_SPEEDING_41_54.metric",  "not_translated");  //65-88 kph
		nonTranslatedMap_ro.put("ScoreType.SCORE_SPEEDING_55_64",  "not_translated");  //55-64 mph
		nonTranslatedMap_ro.put("ScoreType.SCORE_SPEEDING_55_64.metric",  "not_translated");  //89-103 kph
		nonTranslatedMap_ro.put("ScoreType.SCORE_SPEEDING_65_80",  "not_translated");  //65-80 mph
		nonTranslatedMap_ro.put("ScoreType.SCORE_SPEEDING_65_80.metric",  "not_translated");  //104+ kph
		nonTranslatedMap_ro.put("no",  "not_translated");  //no
		nonTranslatedMap_ro.put("notApplicable",  "not_translated");  //N/A
		nonTranslatedMap_ro.put("report.dvspeed.21.30",  "not_translated");  //1-30 mph
		nonTranslatedMap_ro.put("report.dvspeed.21.30.metric",  "not_translated");  //1-48 kph
		nonTranslatedMap_ro.put("report.dvspeed.31.40",  "not_translated");  //31-40 mph
		nonTranslatedMap_ro.put("report.dvspeed.31.40.metric",  "not_translated");  //49-64 kph
		nonTranslatedMap_ro.put("report.dvspeed.41.54",  "not_translated");  //41-54 mph
		nonTranslatedMap_ro.put("report.dvspeed.41.54.metric",  "not_translated");  //65-88 kph
		nonTranslatedMap_ro.put("report.dvspeed.55.64",  "not_translated");  //55-64 mph
		nonTranslatedMap_ro.put("report.dvspeed.55.64.metric",  "not_translated");  //89-103 kph
		nonTranslatedMap_ro.put("report.dvspeed.65",  "not_translated");  //65+ mph
		nonTranslatedMap_ro.put("report.dvspeed.65.metric",  "not_translated");  //104+ kph
		nonTranslatedMap_ro.put("report.idling.percent",  "not_translated");  //%
		nonTranslatedMap_ro.put("report.kph",  "not_translated");  //kph
		nonTranslatedMap_ro.put("report.mi",  "not_translated");  //mi
		nonTranslatedMap_ro.put("report.mi.metric",  "not_translated");  //km
		nonTranslatedMap_ro.put("report.mph",  "not_translated");  //mph
		nonTranslatedMap_ro.put("report.mph.metric",  "not_translated");  //kph
		nonTranslatedMap_ro.put("NONE",  "not_translated");  //{}
		nonTranslatedMap_ro.put("RedFlagLevel.INFO",  "not_translated");  //info
		nonTranslatedMap_ro.put("report.crashhistory.status",  "not_translated");  //Status
		nonTranslatedMap_ro.put("report.crashhistory.status.EXCLUDE",  "not_translated");  //Exclude
		nonTranslatedMap_ro.put("report.device.imei",  "not_translated");  //IMEI
		nonTranslatedMap_ro.put("report.device.status",  "not_translated");  //Status
		nonTranslatedMap_ro.put("report.teamstats.idle_percentage", "not_translated"); // Idle %
		nonTranslatedMap_ro.put("report.vehicle.odometer", "not_translated");// Odometer
	}
	
	@Test
	@Ignore
	public void roTest(){
		
		Properties english = getProperties("com/inthinc/pro/ReportMessages.properties");
		Properties translated = getProperties("com/inthinc/pro/ReportMessages_ro.properties");

		int errorCount =  0;
			
		System.out.println("Checking Language: ro");
		Map<String, String> needTrans = new TreeMap<String, String>();

		Enumeration<?> propNames = english.propertyNames(); 
		while (propNames.hasMoreElements()) {
			String key = propNames.nextElement().toString();
// System.out.println(" " + key);				
			String value = english.getProperty(key);
			String langValue = translated.getProperty(key);
			
			
			if (langValue == null) {
//				System.out.println("MISSING: " + key + " en: " + value);
//				System.out.println(key + " = " + value);
				needTrans.put(key, value);

				errorCount++;
			}
			else if (langValue.isEmpty() && value.isEmpty())
				continue;
			else if (nonTranslatedMap.containsKey(key))
				continue;
			else if (nonTranslatedMap_ro.containsKey(key))
				continue;
			else if (langValue.trim().equalsIgnoreCase(value.trim()) || langValue.contains("(ro)")) { 
//				System.out.println("NOT TRANSLATED: " + key + " en: " + value + " ro: " + langValue);
//				System.out.println(key + " = " + value);
				needTrans.put(key, value);

				errorCount++;
			}
		}
		
		if (needTrans.size() > 0) {
			for (String key : needTrans.keySet()) 
				System.out.println(key + " = " + needTrans.get(key));
		}

				
		assertEquals("ro messages need translations", 0, errorCount);
		
		
	}

	private Properties getProperties(String propFile) {
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propFile);
        
        Properties properties = new Properties();
        try {
			properties.load(stream);
		} catch (IOException e) {
			System.out.println("ERROR loading: " + propFile);
			e.printStackTrace();
		}

		return properties;
	}
	
	
}

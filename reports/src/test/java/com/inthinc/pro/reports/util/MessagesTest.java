package com.inthinc.pro.reports.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
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
	
	String reportI18nPath[] = {
	        "com/inthinc/pro/reports/jasper/asset/i18n/",    
            "com/inthinc/pro/reports/jasper/hos/i18n/",    
            "com/inthinc/pro/reports/jasper/ifta/i18n/",    
            "com/inthinc/pro/reports/jasper/performance/i18n/"    
	};
	
    @Test
    @Ignore
    public void roLangTest() {
    
        int errorCount = langTest("com/inthinc/pro/", "ReportMessages", "ro", nonTranslatedMap_ro);
        
        // reports that have specific i18n files
        for (String path : reportI18nPath ) {
            List<String> bundleNames = getBundles(path);
            for (String bundle : bundleNames) {
                System.out.println(bundle);
                errorCount += langTest(path, bundle, "ro", null);
            }
        }

    
        assertEquals("total ro messages need translations", 0, errorCount);

    }

    
    private List<String> getBundles(String string) {
        List<String> bundles = new ArrayList<String>();
        try {
            Enumeration<URL> enumer = Thread.currentThread().getContextClassLoader().getResources(string);
            while (enumer.hasMoreElements()) {
                URL url = enumer.nextElement();
                File dir = new File(url.toURI());
                if (dir.isDirectory()) {
                    String[] propFiles = dir.list(new PropertiesFileFilter());
                    for (String propFile : propFiles)
                        bundles.add(propFile.substring(0, propFile.length()-11)); //.properties
                }
                
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bundles;
    }


    public int langTest(String path, String bundleName, String lang, Map<String, String> langNonTranslatedMap){
		
		Properties english = getProperties(path + bundleName + ".properties");
		Properties translated = getProperties(path + bundleName + "_" + lang + ".properties");

		int errorCount =  0;
			
		System.out.println("Checking Language: " + lang);
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
			else if (langNonTranslatedMap != null && langNonTranslatedMap.containsKey(key))
				continue;
			else if (langValue.trim().equalsIgnoreCase(value.trim()) || langValue.contains("(" + lang+ ")")) { 
//				System.out.println("NOT TRANSLATED: " + key + " en: " + value + " ro: " + langValue);
//				System.out.println(key + " = " + value);
				needTrans.put(key, value);

				errorCount++;
			}
		}
		
		if (needTrans.size() > 0) {
            System.out.println("*** " + path + bundleName + " ***");
			for (String key : needTrans.keySet()) 
				System.out.println(key + " = " + needTrans.get(key));
		}

				
//		assertEquals("ro messages need translations", 0, errorCount);
		return errorCount;
		
		
	}

	private Properties getProperties(String propFile) {
	    System.out.println("propFile: " + propFile);
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propFile);
        Properties properties = new Properties();
        if (stream != null) {
            try {
    			properties.load(stream);
    		} catch (IOException e) {
    			System.out.println("ERROR loading: " + propFile);
    			e.printStackTrace();
    		}
        }

		return properties;
	}
	
	
	class PropertiesFileFilter implements FilenameFilter {

        @Override
        public boolean accept(File dir, String name) {
System.out.println("filter " + name);            
            if (name.contains("_"))
                return false;
            if (name.contains(".properties"))
                return true;
            
            return false;
        }
	    
	}
	
}

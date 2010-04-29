package com.inthinc.pro.scheduler.util;


import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Ignore;
import org.junit.Test;

public class MessagesTest {

	
	static Map<String, String> nonTranslatedMap = new HashMap<String, String>();
	static {
	}
	static Map<String, String> nonTranslatedMap_ro = new HashMap<String, String>();
	static {
	}

	@Test
	public void roTest(){
		
		Properties english = getProperties("messages.properties");
		Properties translated = getProperties("messages_ro.properties");

		int errorCount =  0;
			
		System.out.println("Checking Language: ro");
		Enumeration<?> propNames = english.propertyNames(); 
		while (propNames.hasMoreElements()) {
			String key = propNames.nextElement().toString();
			String value = english.getProperty(key);
			String langValue = translated.getProperty(key);
			
			
			if (langValue == null) {
				System.out.println("MISSING: " + key + " en: " + value);
				errorCount++;
			}
			else if (langValue.isEmpty() && value.isEmpty())
				continue;
			else if (nonTranslatedMap.containsKey(key))
				continue;
			else if (nonTranslatedMap_ro.containsKey(key))
				continue;
			else if (langValue.trim().equalsIgnoreCase(value.trim()) || langValue.contains("(ro)")) { 
				System.out.println("NOT TRANSLATED: " + key + " en: " + value + " ro: " + langValue);
				errorCount++;
			}
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

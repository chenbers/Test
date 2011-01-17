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

import com.inthinc.pro.scheduler.dispatch.PhoneDispatcher;

@Ignore
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
	
	@Ignore
	public void testPhoneCall()
	{
	    PhoneDispatcher pd = new PhoneDispatcher();
	    pd.setCallerID("8018662255");
	    pd.setTokenID("724abefcafa4be428524109528252d18defe555564874cd59aed73e84cf16fc6817d99ce6fde0f5aef118e94");
	    pd.setPhoneServerURL("http://api.voxeo.net/VoiceXML.start");
	    pd.setMaxThreads(1);
        pd.init();
	    pd.send("8019383589", "hello Dave", 22, false);
        pd.send("8019383589", "hello Juliana", 22, false);
        pd.send("8019383589", "hello Judy", 22, false);
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

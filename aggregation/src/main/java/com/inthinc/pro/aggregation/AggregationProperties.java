package com.inthinc.pro.aggregation;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class AggregationProperties extends Properties {
	
	// global properties for gain
	// - to add a property - add it to the properties keys defines below
	//   and add to _gain.properties
	// - each instance of gain should make a copy of _gain.properties and name
	//   it gain.properties and modify for their environment
	// - to get value call GainProperties.getInstance().getProperty(<key>);
	
	
	private static Logger logger = Logger.getLogger(AggregationProperties.class);
	
	private static AggregationProperties aggregationProperties = null;
	
	
	// Properties Keys
	public final static String JDBC_CLASS = "jdbcClass";
	public final static String JDBC_URL = "jdbcURL";
	public final static String JDBC_USER = "jdbcUser";
	public final static String JDBC_PASSWORD = "jdbcPassword";
	
		
	private AggregationProperties(String filePath)
	{
		loadPropertiesFile(filePath);
	}
	
	public static void initProperties(String filePath)
	{
		aggregationProperties = new AggregationProperties(filePath);
	}
	
	
	public static AggregationProperties getInstance()
	{
		if (aggregationProperties == null)
		{
			logger.error("QueueProcessor Properties not properly initialized.");
		}
		
		return aggregationProperties;
			
	}

	public static boolean isInitialized()
	{
		return !(aggregationProperties == null);
			
	}

	private void loadPropertiesFile(String filePath) {
		
		File aggregationPropertiesFile = new File(filePath);
		
		try {
			FileInputStream inStream = new FileInputStream(aggregationPropertiesFile);
			load(inStream);
		} catch (Exception e) {
			logger.error("Properties file " + filePath + " cannot be loaded.", e);
		}
		
	}
	
	public boolean getBooleanProperty(String key)
	{
		String value = getProperty(key);
		
		return new Boolean(value).booleanValue();
	}

	public boolean getBooleanProperty(String key, boolean defaultValue)
	{
		String value = getProperty(key);
		if (value == null)
			return defaultValue;
		
		return new Boolean(value).booleanValue();
	}

	public int getIntegerProperty(String key)
	{
		String value = getProperty(key);
		
		return new Integer(value).intValue();
	}

	public int getIntegerProperty(String key, int defaultValue)
	{
		String value = getProperty(key);
		if (value == null)
			return defaultValue;
		
		return new Integer(value).intValue();
	}
}

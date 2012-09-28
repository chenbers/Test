package dataExtractionUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class DBUtilProperties extends Properties {

	String propertiesFileName = "dbUtil.properties";
	
	// property keys:
	public static final String DB_URL = "DB_JDBC_URL";
	public static final String DB_CLASS = "DB_JDBC_CLASS";
	public static final String DB_USER = "DB_JDBC_USER";
	public static final String DB_PASSWORD = "DB_JDBC_PASSWORD";
	
	public DBUtilProperties()
	{
		loadPropertiesFromFile();
	}

	private void loadPropertiesFromFile() {
		try {
//			FileInputStream is = new FileInputStream(propertiesFileName);
//			load(is);
		    InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesFileName);
            load(is);

			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getJDBCUrl()
	{
		return getProperty(DB_URL);
		
	}

	public String getJDBCClass()
	{
		return getProperty(DB_CLASS);
		
	}

	public String getDBUser()
	{
		return getProperty(DB_USER);
		
	}

	public String getDBPassword()
	{
		return getProperty(DB_PASSWORD);
		
	}

}

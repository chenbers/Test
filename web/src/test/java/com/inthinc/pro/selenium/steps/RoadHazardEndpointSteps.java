package com.inthinc.pro.selenium.steps;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import org.springframework.jdbc.core.JdbcTemplate;

import com.inthinc.pro.automation.models.Hazard;
import com.inthinc.pro.automation.models.Hazard.HazardUrls;
import com.inthinc.pro.automation.rest.RestCommands;
//import java.sql.Types;
//import java.util.Collections;
//import org.apache.log4j.Logger;

public class RoadHazardEndpointSteps {
	RestCommands restServices;
	List<Map<String, Object>> dbResults;
    List<Object> lo;
	String sHRt = System.getProperty("line.separator");
	int iAcct=-1;
	
    @Given("I verify endpoint data for MCMID_IMEA \"$mcmID\" and latitude \"$latitude\" and longitude \"$longitude\"")
    @When( "I verify endpoint data for MCMID_IMEA \"$mcmID\" and latitude \"$latitude\" and longitude \"$longitude\"")
    @Then( "I verify endpoint data for MCMID_IMEA \"$mcmID\" and latitude \"$latitude\" and longitude \"$longitude\"")
    public void iVerifyRHEndpointData(String mcmID, Double latitude, Double longitude){
        int counter=-1, hazardID=-1, iDbRows=-1, iRow=-1, iEpRows=-1, iHz=-1;
        String sQuery, sHazID;
        int[] iDbHazardID, iEpHazardID;
                
    	restServices = new RestCommands("QA", "password");
    	JdbcTemplate jt = new JdbcTemplate();
    	jt.setDataSource(new ITDataSource().getRealDataSource());

    	if(iAcct<1) {
	    	sQuery = "SELECT acctID FROM device WHERE mcmID='" + mcmID + "'";
	    	iAcct = jt.queryForInt(sQuery);
    	}

    	sQuery = "" + sHRt +
    	"SELECT haz AS hazardID, " + sHRt +
    	"		loc AS location, " + sHRt +
    	"		null AS status, " + sHRt +
    	"		typ AS type, " + sHRt + 
    	"		des AS description " + sHRt +
    	"  FROM " + sHRt +  
    	"      (SELECT hazardID AS haz, " + sHRt +
    	"              '' AS loc, " + sHRt +
    	"              status AS sta, " + sHRt +
    	"                CASE type " + sHRt + 
    	"			     WHEN  1 THEN 'WEATHER_SLIPPERY' " + sHRt +
    	"				 WHEN  2 THEN 'WEATHER_FLOODING' " + sHRt +
    	" 				 WHEN  3 THEN 'WEATHER_SNOWICE' " + sHRt +
    	" 				 WHEN  4 THEN 'WEATHER_WINDGUSTS' " + sHRt +
    	" 				 WHEN  5 THEN 'WEATHER_VISIBILITY' " + sHRt +
    	" 				 WHEN  6 THEN 'WEATHER_OTHER' " + sHRt +
    	" 				 WHEN  7 THEN 'ROADACTIVITY_CONSTRUCTION' " + sHRt +
    	" 				 WHEN  8 THEN 'ROADACTIVITY_ACCIDENT' " + sHRt +
    	" 				 WHEN  9 THEN 'ROADACTIVITY_DEBRIS' " + sHRt +
    	" 				 WHEN 10 THEN 'ROADACTIVITY_DISABLEDVEHICLE' " + sHRt +
    	" 				 WHEN 11 THEN 'ROADACTIVITY_OTHER' " + sHRt +
    	" 				 WHEN 12 THEN 'ROADRESTRICTIONS_WEIGHT' " + sHRt +
    	" 				 WHEN 13 THEN 'ROADRESTRICTIONS_HEIGHT' " + sHRt +
    	" 				 WHEN 14 THEN 'ROADRESTRICTIONS_BAN' " + sHRt +
    	" 				 WHEN 15 THEN 'ROADRESTRICTIONS_CLOSURE' " + sHRt +
    	" 				 WHEN 16 THEN 'ROADRESTRICTIONS_SHARPTURN' " + sHRt +
    	" 				 WHEN 17 THEN 'ROADRESTRICTIONS_STEEPGRADE' " + sHRt +
    	" 				 WHEN 18 THEN 'ROADRESTRICTIONS_OTHER' " + sHRt +
    	" 				 WHEN 19 THEN 'MICRO_WELLHEAD' " + sHRt +
    	" 				 WHEN 20 THEN 'ROADRESTRICTIONS_WIDTH' " + sHRt +
    	" 				 WHEN 21 THEN 'ROADACTIVITY_TRAFFIC' " + sHRt +
    	"				END AS typ, " + sHRt + 
    	"              description AS des, " + sHRt + 
    	"             ((ACOS(SIN(" + latitude + " * PI() / 180) * " + sHRt + 
    	"               SIN(latitude * PI() / 180) + " + sHRt + 
    	"               COS(" + latitude + " * PI() / 180) * " + sHRt + 
    	"               COS(latitude * PI() / 180) * " + sHRt + 
    	"               COS((" + longitude + " - longitude) * PI() / 180) " + sHRt + 
    	"              ) * 180 / PI() " + sHRt +
    	"             ) * 60 * 1.1515) AS distance " + sHRt + 
    	"         FROM hazard " + sHRt +
    	"        WHERE endTime > now() AND acctID=" + iAcct + " " + sHRt + 
    	"       HAVING distance<=200 " + sHRt + 
    	"     ORDER BY hazardID ASC" + sHRt +
    	") AS hazardStuff";
    	
        dbResults = jt.queryForList(sQuery);
        iDbRows = dbResults.size();
        Assert.assertTrue("Expected to see rows returned.", iDbRows>0);
        iDbHazardID = new int[iDbRows];

        for(Map dbRow : dbResults) {
        	iRow++;
        	lo = new ArrayList(dbRow.entrySet());
       		sHazID = lo.get(0).toString();
       		iHz = Integer.parseInt(sHazID.substring(9));
       		iDbHazardID[iRow] = iHz;
        }
        Arrays.sort(iDbHazardID);
        
    	HazardUrls custom = new HazardUrls(mcmID,latitude.toString(),longitude.toString());
    	Hazard hazard = new Hazard();
    	hazard.setCustomUrl(custom);
    	List<Hazard> list = restServices.getCustomUrl(Hazard.class, hazard);
    	iEpRows = list.size();
    	iEpHazardID = new int[iEpRows];

    	for (Hazard hazards : list) {
    		++counter;
    		hazardID = hazards.getHazardID();
    		iEpHazardID[counter] = hazardID;
    	}
    	Arrays.sort(iEpHazardID);
    	
    	Assert.assertTrue("Expected more than one row, but found " + counter,counter>1);
    	Assert.assertTrue("iDbHazardID==iEpHazardID should be true. ",iDbHazardID.length == iEpHazardID.length);
    	Assert.assertTrue("Lists should be equal: " + iDbHazardID + sHRt + iEpHazardID,Arrays.equals(iDbHazardID,iEpHazardID));
    }
    
    @Given("I verify endpoint data without specifics")
    @When( "I verify endpoint data without specifics")
    @Then( "I verify endpoint data without specifics")
    public void iVerifyRHEndpointData() {
    	String mcmID="", sQuery="";
    	Double latitude=-1d, longitude=-1d;
    	Map stuff = new HashMap();
    	
    	sQuery = 
    	"     SELECT " + sHRt +
    	"            hz.acctID AS ACCTID, " + sHRt +
    	"            TRUNCATE(hz.latitude,0) AS LAT, " + sHRt +
    	"            TRUNCATE(hz.longitude,0) AS LNG, " + sHRt +
    	"            'MCM073869' AS MCMID " + sHRt +
    	"       FROM hazard hz JOIN device dv ON dv.acctID=hz.acctID " + sHRt + 
    	"                       AND dv.status=hz.status " + sHRt +
    	"                       AND dv.mcmid IS NOT NULL " + sHRt +
    	"      WHERE hz.endTime > now() " + sHRt +
    	"        AND dv.status=1" + sHRt + 
    	"        AND hz.latitude<>hz.longitude " + sHRt +
    	"   GROUP BY hz.acctID, TRUNCATE(hz.latitude,0), TRUNCATE(hz.longitude,0) " + sHRt + 
    	"   ORDER BY count(*) desc " + sHRt +
    	"      LIMIT 1";

    	JdbcTemplate jt = new JdbcTemplate();
    	jt.setDataSource(new ITDataSource().getRealDataSource());
    	
    	stuff = jt.queryForMap(sQuery);
		try {
	    	iAcct = Integer.valueOf(stuff.get("ACCTID").toString());
			mcmID = (String)stuff.get("MCMID");
			latitude = (Double)stuff.get("LAT");
			longitude = (Double)stuff.get("LNG");
			
	    	iVerifyRHEndpointData(mcmID,latitude,longitude);
		} catch(Exception x) {
			System.out.println("*** I Verify RH Endpoint Data ***");
			x.printStackTrace();
			System.out.println("*********************************");
		}
    }
    
    public static double distanceBetween(String lat1, String lng1, String lat2, String lng2) {
    	return distanceBetween(Double.parseDouble(lat1), Double.parseDouble(lng1), Double.parseDouble(lat2), Double.parseDouble(lng2));
    }
    
    public static double distanceBetween(double lat1, double lng1, double lat2, double lng2) {
    	double earthRadius, dLat, dLng, sinLat, sinLng, a, c, dist;
        earthRadius	= 6371;										// radius in km
        dLat 		= Math.toRadians(lat2-lat1);
        dLng 		= Math.toRadians(lng2-lng1);
        sinLat 		= Math.sin(dLat / 2);
        sinLng 		= Math.sin(dLng / 2);
        a 			= Math.pow(sinLat, 2) + Math.pow(sinLng, 2) 
        				* Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        c 			= 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        dist 		= earthRadius * c;

        return dist;
    }
    
    //TODO: copied wholesale from dao/src/test/java/com/it... TEMPORARILY until the "right" way to share integration configuration is determined
    //OR until automation tests can be moved into the projects they are tests (i.e. webservice tests should be in service project, configurator test in configurator project ....)
    private class ITDataSource {
        org.apache.commons.dbcp.BasicDataSource datasource;
        
        public ITDataSource() {
            initDataSourceFromProperties(new IntegrationConfig());
        }

        private void initDataSourceFromProperties(IntegrationConfig config) {
            //datasource = new DriverManagerDataSource();
            datasource = new org.apache.commons.dbcp.BasicDataSource();
            datasource.setDriverClassName(config.getProperty(IntegrationConfig.JDBC_DRIVER_CLASS_NAME));
            datasource.setUrl(config.getProperty(IntegrationConfig.JDBC_MYSQL_URL));
            datasource.setConnectionProperties(IntegrationConfig.JDBC_MYSQL_CONNECTION_PROPERTIES);
            datasource.setUsername(config.getProperty(IntegrationConfig.JDBC_MYSQL_USERNAME));
            datasource.setPassword(config.getProperty(IntegrationConfig.JDBC_MYSQL_PASSWORD));
            datasource.setInitialSize(1);
            datasource.setMaxActive(10);
        }
        
        public Connection getConnection() {
            try {
                return datasource.getConnection();
            } catch (SQLException e) {
                System.out.println("Error: getConnection failed with: "+e);
                return null;
            }
        }

        public DataSource getRealDataSource() {
            return datasource;
        }
    }
    
    private class IntegrationConfig extends Properties {
        private final static String CONFIG_PROPERTIES_FILENAME = "it.properties";
        public final static String SILO_HOST = "siloDataAccessHost";
        public final static String SILO_PORT = "siloDataAccessPort";
        public final static String MCM_HOST = "mcmDataAccessHost";
        public final static String MCM_PORT = "mcmDataAccessPort";
        public final static String MAP_SERVER_URL = "mapserver.geonames.url";
        
        public final static String JDBC_DRIVER_CLASS_NAME = "jdbc.mysql.driverClassName";
        public final static String JDBC_MYSQL_URL = "jdbc.mysql.url";
        public final static String JDBC_MYSQL_USERNAME = "jdbc.mysql.username";
        public final static String JDBC_MYSQL_PASSWORD = "jdbc.mysql.password";
        public final static String JDBC_MYSQL_CONNECTION_PROPERTIES = "jdbc.mysql.connectionProperties";

        public final static String MINA_HOST = "minaHost";
        public final static String MINA_PORT = "minaPort";

        public IntegrationConfig() {
            this(CONFIG_PROPERTIES_FILENAME);
        }
        public IntegrationConfig(String propFileName) {
            try {
                
                load(Thread.currentThread().getContextClassLoader().getResourceAsStream(propFileName == null ? CONFIG_PROPERTIES_FILENAME : propFileName));
            } catch (Exception e) {
                System.out.println(CONFIG_PROPERTIES_FILENAME + " cannot be loaded.");
                e.printStackTrace();
            }
        }

        public int getIntegerProp(String key) {
            String value = getProperty(key);

            if (value == null) {
                System.out.println("Error getting value for property: " + key);
                return 0;
            }

            try {
                return new Integer(value).intValue();
            } catch (NumberFormatException e) {
                System.out.println("Error getting integer value for property: " + key);
            }
            return 0;
        }
    }
    //end wholesale copy paste //TODO: replace with a bestPractice/permanent solution
}

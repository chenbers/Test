package it.util;

import it.com.inthinc.pro.dao.model.GroupData;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.ReportTestConst;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import com.inthinc.hos.model.HOSStatus;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.dao.jdbc.HOSJDBCDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.notegen.MCMSimulator;

public class DataGenForIntegrationTesting extends DataGenForTesting {
	
	public String xmlPath;
	public boolean isNewDataSet;
	public Integer startDateInSec;
	public Integer numDays;
	
    public static Integer NUM_EVENT_DAYS = 1;
    
    
    @Override
    protected void createTestData() {
    	itData = new ITData();
        Date assignmentDate = DateUtil.convertTimeInSecondsToDate(DateUtil.getDaysBackDate(DateUtil.getTodaysDate(), NUM_EVENT_DAYS+2, ReportTestConst.TIMEZONE_STR));
        ((ITData)itData).createTestData(siloService, xml, assignmentDate, true, true, true, "IntegrationTest");
    }
    
    @Override
    protected boolean parseTestData(){
    	itData = new ITData();
        InputStream stream;
		try {
			stream = new FileInputStream(xmlPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
        	return false;
		}
        if (!((ITData)itData).parseTestData(stream, siloService, true, true, true))
        {
        	System.out.println("Parse of xml data file failed.  File: " + xmlPath);
        	return false;
        }

    	return true;
    }


	protected void parseArguments(String[] args) {
		// Arguments:
		//		required
		//			0:		NEW  or EVENTS
		//			1:		full path to xml file for storeage/retrieval of current data set
		//		optional:
		//			2: 		start day for event gen MM/dd/yyyy
		//			3:		number of days
		
		String usageErrorMsg = "Usage: DataGenForIntegrationTesting <NEW|EVENTS> <xml file path> [optional if EVENTS: <start date: MM/DD/YYYY> <num days>]";
		
        if (args.length < 2)
        {
        	System.out.println(usageErrorMsg);
        	System.exit(1);
        }
        
        if (args[0].equalsIgnoreCase("NEW"))
        {
        	isNewDataSet = true;
        }
        else if (args[0].equalsIgnoreCase("EVENTS"))
        {
        	isNewDataSet = false;
        }
        else
        {
        	System.out.println(usageErrorMsg);
        	System.exit(1);
        }
        
        xmlPath = args[1];
        
        if (args.length == 4)
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    		dateFormat.setTimeZone(ReportTestConst.timeZone);
            String dateStr = args[2];
            String numDaysStr = args[3];
    		try {
    			Date eventGenStartDate = dateFormat.parse(dateStr);
    			startDateInSec = DateUtil.getDaysBackDate((int)DateUtil.convertDateToSeconds(eventGenStartDate), 1, ReportTestConst.TIMEZONE_STR);
    			
    		} catch (ParseException e1) {
            	System.out.println(usageErrorMsg);
            	System.out.println("Command Line Args: expected day format mm/dd/yyyy");
            	System.exit(1);
    		}

    		try
    		{
    			numDays = Integer.valueOf(numDaysStr);
    			if (numDays < 1)
    				throw new NumberFormatException("");
    		}
    		catch (NumberFormatException ne)
    		{
            	System.out.println(usageErrorMsg);
            	System.out.println("Command Line Args: expected numDays greater than 0");
            	System.exit(1);
    		}
        	
        }
        else
        {
        	// start of day today (i.e. midnight today)
        	startDateInSec = DateUtil.getDaysBackDate(DateUtil.getTodaysDate(), 1, ReportTestConst.TIMEZONE_STR);
        	numDays = 1;
        }
        
	}
	
	EventGeneratorData eventGeneratorDataList[] = {
	        new EventGeneratorData(0,0,0,0,false,30,0),
	        new EventGeneratorData(1,1,1,1,false,25,50,true, false, true),
	        new EventGeneratorData(5,5,5,5,1, true, 20,100,true, false, true),
	        new EventGeneratorData(0,0,0,0,false,30,0, false, true, true)
	 };

	@Override
	protected void generateDayData(Date date, Integer driverType, List<GroupData> teamGroupData) throws Exception 
	{
		for (GroupData groupData : teamGroupData)
		{
			if (groupData.driverType.equals(driverType))
			{
                List<Event> eventList = null;
                EventGeneratorData eventGeneratorData = eventGeneratorDataList[driverType.intValue()];
				
				if (driverType.intValue() == 1 || driverType.intValue() == 2) 
                    eventGeneratorData.zoneID = itData.zone.getZoneID();

				eventGeneratorData.setEmpId(groupData.driver.getPerson().getEmpid());
				eventList = eventGenerator.generateTripEvents(date, eventGeneratorData);
                noteGenerator.genTrip(eventList, groupData.device);
			}		
		}
	}
//	@Override
	protected void generateUnknownDriverDayData(MCMSimulator mcmSim, Date date) throws Exception 
	{
		EventGeneratorData eventGeneratorData = new EventGeneratorData(1,1,1,1,false,25,50,true, false, true);
        eventGeneratorData.zoneID = itData.zone.getZoneID();
        List<Event> eventList = eventGenerator.generateTripEvents(date, eventGeneratorData);
        noteGenerator.genTrip(eventList, itData.noDriverDevice);
	}
	
    private void generateHOSDayData(GroupData groupData, Date startDate) {
        DateTime startDateTime = new DateTime(startDate.getTime());
        HOSStatus driverStatus[] = {
                HOSStatus.ON_DUTY,
                HOSStatus.DRIVING,
                HOSStatus.SLEEPER,
                HOSStatus.OFF_DUTY  // logs the driver out
        };
        HOSDAO hosDAO = new HOSJDBCDAO();
        ((HOSJDBCDAO)hosDAO).setDataSource(new ITDataSource().getRealDataSource());


        for (int i = 0; i < driverStatus.length; i++) {
            
            HOSRecord rec = constructHosRecord(groupData, driverStatus[i], startDateTime.plusMinutes(i*60).toDate());
            
            hosDAO.create(0l, rec);
        }

    }
	
    private HOSRecord constructHosRecord(GroupData groupData, HOSStatus hosStatus, Date logTime) {
        HOSRecord rec = new HOSRecord();
        rec.setDeviceID(groupData.device.getDeviceID());
        rec.setLat(0f);
        rec.setLng(0f);
        rec.setLocation("GENERATED - " + hosStatus.getName());
        rec.setLogTime(logTime);
        rec.setTimeZone(groupData.driver.getPerson().getTimeZone());
        rec.setDriverDotType(groupData.driver.getDot());
        rec.setDriverID(groupData.driver.getDriverID());
        rec.setServiceID("SERVICE - " + groupData.driver.getDriverID());
        rec.setStatus(hosStatus);
        rec.setTrailerGallons(0f);
        rec.setTrailerID("TRAILER - " + groupData.driver.getDriverID());
        rec.setTruckGallons(0f);
        rec.setVehicleID(groupData.vehicle.getVehicleID());
        rec.setVehicleOdometer(100l);
        rec.setEmployeeID(groupData.driver.getPerson().getEmpid());
        rec.setEditUserID(groupData.user.getUserID());
        rec.setStateID(5);
        return rec;
    }

    public static void main(String[] args)
    {
        
        DataGenForIntegrationTesting  testData = new DataGenForIntegrationTesting();
        testData.parseArguments(args);

        try {
            initServices();
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
            System.exit(1);
        }
        
        if (testData.isNewDataSet)
        {
	        try
	        {
	            System.out.println(" -- test data generation start -- ");
	            xml = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(testData.xmlPath)));
	            System.out.println(" saving output to " + testData.xmlPath);
	            testData.createTestData();
	            testData.itData.setDriverDOTType(((ITData)testData.itData).teamGroupData.get(ITData.BAD).driver, RuleSetType.NON_DOT);

	            // wait for imeis to hit central server
	            // generate data for today (midnight) and 30 previous days
	            int todayInSec = DateUtil.getTodaysDate();
	            testData.waitForIMEIs(DateUtil.getDaysBackDate(todayInSec, 1, ReportTestConst.TIMEZONE_STR) + 60, ((ITData)testData.itData).teamGroupData);
	            
	            int numDays = NUM_EVENT_DAYS;
	            for (int teamType = ITData.GOOD; teamType <= ITData.WS_GROUP; teamType++)
	            {
	            	for (int day = numDays; day > 0; day--)
	            	{
	                    int dateInSec = DateUtil.getDaysBackDate(todayInSec, day, ReportTestConst.TIMEZONE_STR) + 60;
	                    // startDate should be one minute after midnight in the selected time zone (TIMEZONE_STR) 
	                    Date startDate = new Date((long)dateInSec * 1000l);
	            		testData.generateDayData(startDate, teamType, ((ITData)testData.itData).teamGroupData);
	            		
	            		if (teamType == ITData.INTERMEDIATE) {
	            			 testData.generateUnknownDriverDayData(mcmSim, startDate);
	            		}
                        if (teamType == ITData.WS_GROUP) {
                            testData.generateHOSDayData(((ITData)testData.itData).teamGroupData.get(teamType), startDate);
                       }
	            	}
	            }
	            // save date of 1st event
	            xml.writeObject(new Integer(DateUtil.getDaysBackDate(todayInSec, numDays, ReportTestConst.TIMEZONE_STR)));
	            if (xml != null)
	            {
	                xml.close();
	            }
	            System.out.println(" -- test data generation complete -- ");
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	            System.exit(1);
	        }
        }
        else
        {
            try
            {
                if (!testData.parseTestData())
                {
                	System.exit(1);
                }

                for (int teamType = ITData.GOOD; teamType <= ITData.WS_GROUP; teamType++)
                {
    	        	for (int day = 0; day < testData.numDays; day++)
    	        	{
    	                int dateInSec = testData.startDateInSec + (day * DateUtil.SECONDS_IN_DAY) + 60;
    	                Date startDate = new Date((long)dateInSec * 1000l);
     	        		testData.generateDayData(startDate, teamType, ((ITData)testData.itData).teamGroupData);
	            		if (teamType == ITData.INTERMEDIATE) {
	            			 testData.generateUnknownDriverDayData(mcmSim, startDate);
	            		}
                        if (teamType == ITData.WS_GROUP) {
                            testData.generateHOSDayData(((ITData)testData.itData).teamGroupData.get(teamType), startDate);
                       }
    	        	}
                }
             
            }
            catch (Exception e)
            {
                e.printStackTrace();
                System.exit(1);
            }

        }

        System.exit(0);
    }




}

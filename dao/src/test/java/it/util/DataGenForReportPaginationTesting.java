package it.util;

import it.com.inthinc.pro.dao.model.GroupListData;
import it.com.inthinc.pro.dao.model.ITData;
import it.com.inthinc.pro.dao.model.ITDataExt;
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

import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.notegen.MCMSimulator;

public class DataGenForReportPaginationTesting extends DataGenForTesting {
	
	public String xmlPath;
	public boolean isNewDataSet;
	public Integer startDateInSec;
	public Integer numDays;
	
    public static Integer NUM_EVENT_DAYS = 7;
    public static Integer NUM_DRIVERS_VEHICLES_DEVICES = 40;//100;
    
    
    @Override
    protected void createTestData() {
    	itData = new ITDataExt();
        Date assignmentDate = DateUtil.convertTimeInSecondsToDate(DateUtil.getDaysBackDate(DateUtil.getTodaysDate(), NUM_EVENT_DAYS+2, ReportTestConst.TIMEZONE_STR));
        ((ITDataExt)itData).createTestDataExt(siloService, xml, assignmentDate, NUM_DRIVERS_VEHICLES_DEVICES);
    }
    
    @Override
    protected boolean parseTestData(){
    	itData = new ITDataExt();
        InputStream stream;
		try {
			stream = new FileInputStream(xmlPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
        	return false;
		}
        if (!((ITDataExt)itData).parseTestDataExt(stream, siloService, NUM_DRIVERS_VEHICLES_DEVICES))
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
		
		String usageErrorMsg = "Usage: DataGenForReportTesting <NEW|EVENTS> <xml file path> [optional if EVENTS: <start date: MM/DD/YYYY> <num days>]";
		
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
            new EventGeneratorData(1,1,1,1,false,25,50),
            new EventGeneratorData(5,5,5,5,true,20,100)
    };


	protected void generateDayDataExt(MCMSimulator mcmSim, Date date, Integer driverType, List<GroupListData> teamGroupData) throws Exception 
	{
		for (GroupListData groupData : teamGroupData)
		{
			if (groupData.driverType.equals(driverType))
			{
				
				for (Device device : groupData.deviceList) {
				    
	                EventGeneratorData eventGeneratorData = eventGeneratorDataList[driverType.intValue()];
	                List<Event> eventList = eventGenerator.generateTripEvents(date, eventGeneratorData);
	                noteGenerator.genTrip(eventList, device);
				}
			}		
		}
	}
//	@Override
	protected void generateUnknownDriverDayData(MCMSimulator mcmSim, Date date) throws Exception 
	{
        List<Event> eventList = eventGenerator.generateTripEvents(date, new EventGeneratorData(1,1,1,1,false,25,50));
        noteGenerator.genTrip(eventList, itData.noDriverDevice);
	}
	
	protected void waitForIMEIsExt(MCMSimulator mcmSim, int eventDateSec, List<GroupListData> teamGroupData) {
		
		for (GroupListData data : teamGroupData)
		{

			Event testEvent = new Event(0l, 0, NoteType.STRIPPED_ACKNOWLEDGE_ID_WITH_DATA,
                    new Date(eventDateSec * 1000l), 60, 0,  33.0089, -117.1100);
			for (Device device : data.deviceList) {
				if (!genTestEvent(testEvent, device))
				{
					System.out.println("Error: imei has not moved to central server");
					System.exit(1);
				}
			}
		}
	}

    public static void main(String[] args)
    {
        
        DataGenForReportPaginationTesting  testData = new DataGenForReportPaginationTesting();
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
	            
	            int todayInSec = DateUtil.getTodaysDate();
	            
	            int numDays = NUM_EVENT_DAYS;
//numDays = 5;	            
	            // save date of 1st event
	            xml.writeObject(new Integer(DateUtil.getDaysBackDate(todayInSec, numDays, ReportTestConst.TIMEZONE_STR)));
	            if (xml != null)
	            {
	                xml.close();
	            }
	            
	            testData.waitForIMEIsExt(mcmSim, DateUtil.getDaysBackDate(todayInSec, 1, ReportTestConst.TIMEZONE_STR) + 60, ((ITDataExt)testData.itData).teamGroupListData);
	            for (int teamType = ITData.GOOD; teamType <= ITData.BAD; teamType++)
	            {
	            	for (int day = numDays; day > 0; day--)
	            	{
	                    int dateInSec = DateUtil.getDaysBackDate(todayInSec, day, ReportTestConst.TIMEZONE_STR) + 60;
	                    // startDate should be one minute after midnight in the selected time zone (TIMEZONE_STR) 
	                    Date startDate = new Date((long)dateInSec * 1000l);
	            		testData.generateDayDataExt(mcmSim, startDate, teamType, ((ITDataExt)testData.itData).teamGroupListData);
	            		
	            		if (teamType == ITData.INTERMEDIATE) {
	            			 testData.generateUnknownDriverDayData(mcmSim, startDate);
	            		}
	            	}
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

                for (int teamType = ITData.GOOD; teamType <= ITData.BAD; teamType++)
                {
    	        	for (int day = 0; day < testData.numDays; day++)
    	        	{
    	                int dateInSec = testData.startDateInSec + (day * DateUtil.SECONDS_IN_DAY) + 60;
    	                Date startDate = new Date((long)dateInSec * 1000l);
    	        		testData.generateDayDataExt(mcmSim, startDate, teamType, ((ITDataExt)testData.itData).teamGroupListData);
	            		if (teamType == ITData.INTERMEDIATE) {
	            			 testData.generateUnknownDriverDayData(mcmSim, startDate);
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

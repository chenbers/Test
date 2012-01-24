package it.util;

import it.com.inthinc.pro.dao.model.GroupData;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.IntegrationConfig;
import it.config.ReportTestConst;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.inthinc.pro.dao.hessian.EventHessianDAO;
import com.inthinc.pro.dao.hessian.extension.HessianTCPProxyFactory;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventCategory;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.notegen.MCMSimulator;

public class DataGenForReportTesting extends DataGenForTesting {
	public String xmlPath;
	public boolean isNewDataSet;
	public Integer startDateInSec;
	public Integer numDays;

    public static Integer NUM_EVENT_DAYS = 31;
    
    
    @Override
    protected void generateDayData(MCMSimulator mcmSim, Date date, Integer driverType, List<GroupData> teamGroupData) throws Exception 
    {
        EventHessianDAO eventDAO = new EventHessianDAO();
        eventDAO.setSiloService(siloService);
        
        for (GroupData groupData : teamGroupData)
        {
            if (groupData.driverType.equals(driverType))
            {
                
                EventGenerator eventGenerator = new EventGenerator();
                switch (driverType.intValue()) {
                case 0:         // good
                    eventGenerator.generateTrip(groupData.device.getImei(), mcmSim, date, new EventGeneratorData(1,1,1,0,false,30,0), false);
                    // forgive all of the events
                    List<NoteType> noteTypes = EventCategory.VIOLATION.getNoteTypesInCategory();
                    Integer driverID = groupData.driver.getDriverID();
                    List<Event> eventList = eventDAO.getEventsForDriver(driverID, date, new Date(date.getTime() + DateUtil.MILLISECONDS_IN_DAY-1000l), noteTypes, 0);
System.out.println("found " + eventList.size() + " events to forgive");                    
                    for (Event event : eventList) {
                        eventDAO.forgive(driverID, event.getNoteID());
                    }
                    break;
                case 1:         // intermediate
                    eventGenerator.generateTrip(groupData.device.getImei(), mcmSim, date, new EventGeneratorData(1,1,1,1,false,25,50));
                break;
                case 2:         // bad
                    eventGenerator.generateTrip(groupData.device.getImei(), mcmSim, date, new EventGeneratorData(5,5,5,5,true,20,100));
                break;
                
                }
            }       
        }
            
        
    }


    
    @Override
    protected void createTestData() {
    	itData = new ITData();
        Date assignmentDate = DateUtil.convertTimeInSecondsToDate(DateUtil.getDaysBackDate(DateUtil.getTodaysDate(), NUM_EVENT_DAYS+2, ReportTestConst.TIMEZONE_STR));
        ((ITData)itData).createTestData(siloService, xml, assignmentDate, false, false);
    }
    @Override
    protected boolean parseTestData() {
    	itData = new ITData();
        InputStream stream;
		try {
			stream = new FileInputStream(xmlPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
        	return false;
		}
        if (!((ITData)itData).parseTestData(stream, siloService, false, false))
        {
        	System.out.println("Parse of xml data file failed.  File: " + xmlPath);
        	return false;
        }
        
        return true;
    }

	private void parseArguments(String[] args) {
		// Arguments:
		//		required
		//			0:		NEW  or EVENTS
		//			1:		full path to xml file for storeage/retrieval of current data set
		//		optional:
		//			2: 
		
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

    public static void main(String[] args)
    {
        
        DataGenForReportTesting  testData = new DataGenForReportTesting();
        testData.parseArguments(args);
    	


        IntegrationConfig config = new IntegrationConfig();
        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
        siloService = new SiloServiceCreator(host, port).getService();

        if (testData.isNewDataSet)
        {
	        try
	        {
	            System.out.println(" -- test data generation start -- ");
	            xml = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(testData.xmlPath)));
	            System.out.println(" saving output to " + testData.xmlPath);
	            testData.createTestData();
	            
	            // wait for imeis to hit central server
	            // generate data for today (midnight) and 30 previous days
	            HessianTCPProxyFactory factory = new HessianTCPProxyFactory();
	            MCMSimulator mcmSim = (MCMSimulator) factory.create(MCMSimulator.class, config.getProperty(IntegrationConfig.MCM_HOST), config.getIntegerProp(IntegrationConfig.MCM_PORT));

	            int todayInSec = DateUtil.getTodaysDate();
	            testData.waitForIMEIs(mcmSim, DateUtil.getDaysBackDate(todayInSec, 1, ReportTestConst.TIMEZONE_STR) + 60, ((ITData)testData.itData).teamGroupData);
	            
	            int numDays = NUM_EVENT_DAYS;
//numDays = 5;	            
	            for (int teamType = ITData.GOOD; teamType <= ITData.BAD; teamType++)
	            {
	            	for (int day = numDays; day > 0; day--)
	            	{
//	                    int dateInSec = DateUtil.getDaysBackDate(todayInSec, day, ReportTestConst.TIMEZONE_STR) + 60;
	                    int dateInSec = DateUtil.getDaysBackDate(todayInSec, day, ReportTestConst.TIMEZONE_STR) + 10800;
	                    // startDate should be one minute after midnight in the selected time zone (TIMEZONE_STR) 
	                    Date startDate = new Date((long)dateInSec * 1000l);
	            		testData.generateDayData(mcmSim, startDate, teamType, ((ITData)testData.itData).teamGroupData);
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
    	
                HessianTCPProxyFactory factory = new HessianTCPProxyFactory();
                MCMSimulator mcmSim = (MCMSimulator) factory.create(MCMSimulator.class, config.getProperty(IntegrationConfig.MCM_HOST), config.getIntegerProp(IntegrationConfig.MCM_PORT));
                for (int teamType = ITData.GOOD; teamType <= ITData.BAD; teamType++)
                {
    	        	for (int day = 0; day < testData.numDays; day++)
    	        	{
//    	                int dateInSec = testData.startDateInSec + (day * DateUtil.SECONDS_IN_DAY) + 60;
    	                int dateInSec = testData.startDateInSec + (day * DateUtil.SECONDS_IN_DAY) + 10800;
    	                Date startDate = new Date((long)dateInSec * 1000l);
    	        		testData.generateDayData(mcmSim, startDate, teamType, ((ITData)testData.itData).teamGroupData);
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

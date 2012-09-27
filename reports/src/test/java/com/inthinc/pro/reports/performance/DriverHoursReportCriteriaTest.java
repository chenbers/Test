package com.inthinc.pro.reports.performance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import mockit.Cascading;
import mockit.Expectations;
import mockit.Mocked;
import mockit.NonStrict;
import mockit.Verifications;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.joda.time.DateMidnight;
import org.joda.time.Interval;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.dao.DriveTimeDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.aggregation.DriveTimeRecord;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.performance.model.DriverHours;


//public class DriverHoursReportCriteriaTest extends BaseUnitTest {
public class DriverHoursReportCriteriaTest {
    
    // Constant values
    private final Integer GROUP_ID = new Integer(2);
    private final Integer DRIVER_ID = new Integer(1);
    private final String VEHICLE_NAME = "Stub Vehicle Name";
    private final Double HOURS_THIS_DAY = new Double(7);
    private final Long SECONDS_THIS_DAY = Long.valueOf(25200l);
    private final String GROUP_FULL_NAME = "Group Full Name";
    private final Locale LOCALE = Locale.US;
    private final Interval INTERVAL = new Interval(new Date().getTime() - 3600, new Date().getTime());
    
    // JMockit mocks
    @NonStrict @Cascading private Driver driverMock;
    @Mocked private DriverDAO driverDAOMock; 
    @Mocked private GroupHierarchy groupHierarchyMock;
    @Mocked private DriveTimeDAO driveTimeDAOMock; 
    
	// The System Under Test
	private DriverHoursReportCriteria reportCriteriaSUT = new DriverHoursReportCriteria(LOCALE); 

    
	/**
	 * Tests the init method of the DriverHoursReportCriteria class.
	 */
	@Ignore
    @Test
    public void testInit(){

		// General initializations
        reportCriteriaSUT.setDriverDAO(driverDAOMock);
        reportCriteriaSUT.setDriveTimeDAO(driveTimeDAOMock);
        

        // JMockit Documentation:
        // http://jmockit.googlecode.com/svn/trunk/www/tutorial/BehaviorBasedTesting.html
        //
        //------------------------------------------------------------------
        // 1. First declare what we expect from the mocks during the test 
        new Expectations(){

        	// This class includes Strict Expectations.
            // Strict expectations define the behavior of the mocks and automatically verify execution and order during the test run.
            
        	// Non-strict expectations define behavior only and are defined as a NonStrictExpectations() class. 
        	// In that case, execution and order must be verified in the Verifications() block.
        	// In this example we have one @NonStrict Mock object: driverMock
        	
           {

              List<Driver> driverList = new ArrayList<Driver>(); 
              driverList.add(driverMock);
              driverDAOMock.getAllDrivers(GROUP_ID); returns(driverList);
              Interval queryInterval = new Interval(INTERVAL.getStart().minusDays(1), new DateMidnight(INTERVAL.getEnd()).toDateTime().plusDays(2));

              driveTimeDAOMock.getDriveTimeRecordListForGroup(GROUP_ID, queryInterval); returns (getDriveTimeList());

              driverMock.getDriverID(); returns(DRIVER_ID); 
              driverDAOMock.findByID(DRIVER_ID);
              driverDAOMock.getTrips(DRIVER_ID, queryInterval);
              driverMock.getDriverID(); returns(DRIVER_ID); 
              //driverMock.getGroupID(); returns(GROUP_ID); 
              
                            
              groupHierarchyMock.getShortGroupName(GROUP_ID, ReportCriteria.SLASH_GROUP_SEPERATOR); returns(GROUP_FULL_NAME);
           }
           
           // Helper method
           private List<DriveTimeRecord> getDriveTimeList(){
               List<DriveTimeRecord> hoursList = new ArrayList<DriveTimeRecord>();
               DriveTimeRecord hours = new DriveTimeRecord(); 
               hours.setDateTime(new DateMidnight(INTERVAL.getStart()).toDateTime());
               hours.setDriveTimeSeconds(SECONDS_THIS_DAY);
               hours.setDriverID(DRIVER_ID);
               hours.setVehicleName(VEHICLE_NAME);
               hoursList.add(hours);
               
               return hoursList;
           }
        };
        
        //------------------------------------------------------------------
        // 2. Second, we run the actual method to be tested
        reportCriteriaSUT.init(groupHierarchyMock, GROUP_ID, INTERVAL);
    	
        //------------------------------------------------------------------
        // 3. Third we verify the results
        new Verifications()
        {
           {
        	   // All the strict expectations were already verified automatically
               //driverMock.getGroupID();
               driverMock.getDriverID();
               //driverMock.getPerson();

           }
         };

       // We can also perform regular JUnit assertions
       List<DriverHours> driverHoursList = reportCriteriaSUT.getMainDataset();
	   assertNotNull(driverHoursList);
	   System.out.println("driverHoursList.size(): "+driverHoursList.size());
	   assertTrue(driverHoursList.size() == 1);
	   DriverHours driverHours = driverHoursList.get(0);
	   assertEquals(driverHours.getGroupName(), GROUP_FULL_NAME);
	   assertEquals(driverHours.getHours(), HOURS_THIS_DAY);

    }    
    
	/**
	 * Tests that the sort order produced by the Comparator is correct.
	 */
	@Test
	public void testComparatorSort(){
		DriverHours[] hoursArray = new DriverHours[3];
		hoursArray[0] = getHours("GroupB", "DriverX"); 
		hoursArray[1] = getHours("GroupA", "DriverZ"); 
		hoursArray[2] = getHours("GroupA", "DriverY"); 

		List<DriverHours> hoursList = Arrays.asList(hoursArray.clone());
		Collections.sort(hoursList, reportCriteriaSUT.new DriverHoursComparator());

		// verify the correct order
		assertTrue(EqualsBuilder.reflectionEquals(hoursArray[2], hoursList.get(0))); //AY
		assertTrue(EqualsBuilder.reflectionEquals(hoursArray[1], hoursList.get(1))); //AZ
		assertTrue(EqualsBuilder.reflectionEquals(hoursArray[0], hoursList.get(2))); //BX
	}
	
	private DriverHours getHours(String groupName, String driverName){
		DriverHours hours = new DriverHours();
		hours.setGroupName(groupName);
		hours.setDriverName(driverName);
		return hours;
	}
    
}
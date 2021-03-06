package com.inthinc.pro.reports.performance;

import static junit.framework.Assert.fail;
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
import org.junit.Test;

import com.inthinc.pro.dao.DriveTimeDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.aggregation.DriveTimeRecord;
import com.inthinc.pro.model.performance.TenHoursViolationRecord;
import com.inthinc.pro.reports.BaseUnitTest;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.dao.WaysmartDAO;
import com.inthinc.pro.reports.performance.model.TenHoursViolation;


public class TenHoursViolationReportCriteriaTest extends BaseUnitTest {
    
    // Constant values
    private final Integer GROUP_ID = new Integer(2);
    private final Integer DRIVER_ID = new Integer(1);
    private final String VEHICLE_NAME = "Stub Vehicle Name";
    private final Double HOURS_THIS_DAY = new Double(15);
    private final Long SECONDS_THIS_DAY = Long.valueOf(54000l);
    private final String GROUP_FULL_NAME = "Group Full Name";
    private final Locale LOCALE = Locale.US;
    private final Interval INTERVAL = new Interval(new Date().getTime() - 3600, new Date().getTime());
    private List<Trip> tripList = new ArrayList<Trip>();
    
    // JMockit mocks
    @NonStrict @Cascading private Driver driverMock;
    @NonStrict @Cascading private Trip tripMock;
    @NonStrict @Mocked private DriverDAO driverDAOMock; 
    @Mocked private GroupHierarchy groupHierarchyMock;
    @Mocked private DriveTimeDAO driveTimeDAOMock; 
    
	// The System Under Test
	private TenHoursViolationReportCriteria reportCriteriaSUT = new TenHoursViolationReportCriteria(LOCALE); 

    
	/**
	 * Tests the init method of the TenHoursViolationReportCriteria class.
	 */
	@SuppressWarnings("unchecked")
    @Test
    public void testInit(){

		// General initializations
        reportCriteriaSUT.setDriverDAO(driverDAOMock);
        reportCriteriaSUT.setDriveTimeDAO(driveTimeDAOMock);
        
        tripList.add(tripMock);

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
        	// In this example we have TWO @NonStrict Mock objects: driverMock, and driverDAOMock
        	
        	 
        	  // Local mock, only used in the Expectations block
        	  // Some other mocks in this example could also be local
        
           {

              List<Driver> driverList = new ArrayList<Driver>(); driverList.add(driverMock);
              driverDAOMock.getAllDrivers(GROUP_ID); returns(driverList);

              Interval queryInterval = new Interval(INTERVAL.getStart().minusDays(1), new DateMidnight(INTERVAL.getEnd()).toDateTime().plusDays(2));

              driveTimeDAOMock.getDriveTimeRecordListForGroup(GROUP_ID, queryInterval); returns (getDriveTimeList());

              driverMock.getDriverID(); returns(DRIVER_ID); 
              driverMock.getGroupID(); returns(GROUP_ID); 
              
              driverDAOMock.findByID(DRIVER_ID); returns(driverMock); 
              driverDAOMock.getTrips(DRIVER_ID, INTERVAL); returns(tripList);
              tripMock.getMileage();returns(1000);

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
               driverMock.getGroupID();
               driverMock.getDriverID();
               driverMock.getPerson(); times = 2;
           }
         };

       // We can also perform regular JUnit assertions
       List<TenHoursViolation> tenHourViolations = reportCriteriaSUT.getMainDataset();
	   assertNotNull(tenHourViolations);
	   assertTrue(tenHourViolations.size() == 1);
	   TenHoursViolation violation = tenHourViolations.get(0);
	   assertEquals(violation.getGroupName(), GROUP_FULL_NAME);
	   assertEquals(violation.getVehicleName(), VEHICLE_NAME);
	   assertEquals(violation.getHoursThisDay(), HOURS_THIS_DAY);

    }    
    
	/**
	 * Tests that the sort order produced by the Comparator is correct.
	 */
	@Test
	public void testComparatorSort(){
		TenHoursViolation[] violation = new TenHoursViolation[3];
		violation[0] = getViolation("GroupB", "DriverX"); 
		violation[1] = getViolation("GroupA", "DriverZ"); 
		violation[2] = getViolation("GroupA", "DriverY"); 

		List<TenHoursViolation> violationsList = Arrays.asList(violation.clone());
		Collections.sort(violationsList, reportCriteriaSUT.new TenHoursViolationComparator());

		// verify the correct order
		assertTrue(EqualsBuilder.reflectionEquals(violation[2], violationsList.get(0))); //AY
		assertTrue(EqualsBuilder.reflectionEquals(violation[1], violationsList.get(1))); //AZ
		assertTrue(EqualsBuilder.reflectionEquals(violation[0], violationsList.get(2))); //BX
	}
	
	/**
     * Tests that the sort does not throw NPE.
     */
    @Test
    public void testComparatorSortHandlesNullProperties(){
        TenHoursViolation[] violation = new TenHoursViolation[3];
        violation[0] = new TenHoursViolation(); 
        violation[1] = new TenHoursViolation(); 
        violation[2] = new TenHoursViolation(); 

        List<TenHoursViolation> violationsList = Arrays.asList(violation);

        try {
            Collections.sort(violationsList, reportCriteriaSUT.new TenHoursViolationComparator());
        } catch (NullPointerException e) {
            fail(e.getClass() + " not expected.");
        }
    }
	
	private TenHoursViolation getViolation(String groupName, String driverName){
		TenHoursViolation violation = new TenHoursViolation();
		violation.setGroupName(groupName);
		violation.setDriverName(driverName);
		return violation;
	}
    
}
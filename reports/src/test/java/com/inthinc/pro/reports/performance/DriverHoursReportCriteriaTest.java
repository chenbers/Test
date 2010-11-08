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
import org.joda.time.Interval;
import org.junit.Test;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.performance.DriverHoursRecord;
import com.inthinc.pro.reports.BaseUnitTest;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.dao.WaysmartDAO;
import com.inthinc.pro.reports.performance.model.DriverHours;


public class DriverHoursReportCriteriaTest extends BaseUnitTest {
    
    // Constant values
    private final Integer GROUP_ID = new Integer(2);
    private final Double HOURS_THIS_DAY = new Double(7);
    private final String GROUP_FULL_NAME = "Group Full Name";
    private final Locale LOCALE = Locale.US;
    private final Interval INTERVAL = new Interval(new Date().getTime() - 3600, new Date().getTime());
    
    // JMockit mocks
    @NonStrict @Cascading private Driver driverMock;
    @Mocked private DriverDAO driverDAOMock; 
    @Mocked private WaysmartDAO waysmartDAOMock; 
    @Mocked private GroupHierarchy groupHierarchyMock;
    
	// The System Under Test
	private DriverHoursReportCriteria reportCriteriaSUT = new DriverHoursReportCriteria(LOCALE); 

    
	/**
	 * Tests the init method of the DriverHoursReportCriteria class.
	 */
	@SuppressWarnings("unchecked")
    @Test
    public void testInit(){

		// General initializations
        reportCriteriaSUT.setDriverDAO(driverDAOMock);
        reportCriteriaSUT.setWaysmartDAO(waysmartDAOMock);
        

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

              List<Driver> driverList = new ArrayList<Driver>(); driverList.add(driverMock);
              driverDAOMock.getAllDrivers(GROUP_ID); returns(driverList);

              waysmartDAOMock.getDriverHours(driverMock, INTERVAL); returns(getHoursList());

              driverMock.getGroupID(); returns(GROUP_ID); 
              groupHierarchyMock.getShortGroupName(GROUP_ID, ReportCriteria.SLASH_GROUP_SEPERATOR); returns(GROUP_FULL_NAME); 
           }
           
           // Helper method
           private List<DriverHoursRecord> getHoursList(){
        	   List<DriverHoursRecord> hoursList = new ArrayList<DriverHoursRecord>();
        	   DriverHoursRecord hours = new DriverHoursRecord(); 
        	   hours.setDate(new Date());
        	   hours.setHoursThisDay(HOURS_THIS_DAY); 
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
               driverMock.getPerson(); 
           }
         };

       // We can also perform regular JUnit assertions
       List<DriverHours> driverHoursList = reportCriteriaSUT.getMainDataset();
	   assertNotNull(driverHoursList);
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
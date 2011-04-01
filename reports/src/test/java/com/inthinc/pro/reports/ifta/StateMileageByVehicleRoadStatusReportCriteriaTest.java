package com.inthinc.pro.reports.ifta;

import static junit.framework.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import mockit.Expectations;
import mockit.Mocked;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.joda.time.Interval;
import org.junit.Test;

import com.inthinc.pro.dao.StateMileageDAO;
import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.StateMileage;
import com.inthinc.pro.reports.BaseUnitTest;
import com.inthinc.pro.reports.dao.mock.MockStateMileageDAO;
import com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus;

public class StateMileageByVehicleRoadStatusReportCriteriaTest { //extends BaseUnitTest {
    
    // Constant values
    private final Integer GROUP_ID = new Integer(177);
    private final Integer GROUP_ID2 = new Integer(178);
    private final List<Integer> GROUP_IDS = new ArrayList<Integer>();
    private final String VEHICLE = "123456";
    private final String GROUP_FULL_NAME_1 = "FLEET";
    private final String GROUP_FULL_NAME_2 = "FLEET - DIVISION - GOOD";
    private static final String STATE = "Ohio";
    private static final Long MILES = 5000L;
    
    private final Locale LOCALE = Locale.US;
    private final Interval INTERVAL = new Interval(new Date().getTime() - 3600, new Date().getTime());
    private Group[] groupList = {
            new Group(GROUP_ID, 1, GROUP_FULL_NAME_1, 0),
            new Group(GROUP_ID2, 1, GROUP_FULL_NAME_2, 0),
    };
    private GroupHierarchy groupHierarchy = new GroupHierarchy(Arrays.asList(groupList));

    // JMockit mocks
    
    // The System Under Test
    private StateMileageByVehicleRoadStatusReportCriteria reportCriteriaSUT = new StateMileageByVehicleRoadStatusReportCriteria(LOCALE); 

    
    /**
     * Tests the init method of the StateMileageByVehicleRoadStatusReportCriteria class.
     */
    @Test
    @SuppressWarnings({ "unchecked", "static-access" })
    public void testInit(final StateMileageDAO stateMileageDAOMock){

        // General initializations
        reportCriteriaSUT.setStateMileageDAO(stateMileageDAOMock);

        GROUP_IDS.add(GROUP_ID);
        GROUP_IDS.add(GROUP_ID2);
        // 1. First declare what we expect from the mocks during the test 
        new Expectations(){

            // This class includes Strict Expectations.
            // Strict expectations define the behavior of the mocks and automatically verify execution and order during the test run.
            
            // Non-strict expectations define behavior only and are defined as a NonStrictExpectations() class. 
            // In that case, execution and order must be verified in the Verifications() block.
            // In this example we have one @NonStrict Mock object: driverMock
            @Mocked({"convertMilesToKilometers"})
            MeasurementConversionUtil measurementUtilMock;
           {
              stateMileageDAOMock.getStateMileageByVehicleRoad(GROUP_ID, INTERVAL, false); returns(getData());
              stateMileageDAOMock.getStateMileageByVehicleRoad(GROUP_ID2, INTERVAL, false); returns(null);
              measurementUtilMock.convertMilesToKilometers(MILES, MeasurementType.ENGLISH); returns(MILES);
           }
           
           // Helper method
           private List<StateMileage> getData(){
               List<StateMileage> list = new ArrayList<StateMileage>();
               
               StateMileage bean = new StateMileage(); 
               bean.setVehicleName(VEHICLE); 
               bean.setStateName(STATE);
               bean.setGroupID(GROUP_ID);
               bean.setMiles(MILES);
               bean.setOnRoadFlag(true);
               
               list.add(bean);
               return list;
           }
           
        };
        

        
        //------------------------------------------------------------------
        // 2. Second, we run the actual method to be tested
        reportCriteriaSUT.init(groupHierarchy, GROUP_IDS, INTERVAL, false);
        
        //------------------------------------------------------------------
        // 3. Third we verify the results

       List<StateMileageByVehicleRoadStatus> dataSet = reportCriteriaSUT.getMainDataset();
       assertNotNull(dataSet);
       assertTrue(dataSet.size() == 1);
       StateMileageByVehicleRoadStatus bean = dataSet.get(0);
       assertEquals(bean.getGroupName(), GROUP_FULL_NAME_1);
       assertEquals(bean.getState(), STATE);
       assertEquals(bean.getVehicle(), VEHICLE);
       assertTrue(bean.getTotal().doubleValue() == MILES.doubleValue());

    }    
    
    /**
     * Tests the init method of the StateMileageByVehicleRoadStatusReportCriteria class.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testInitWithNonJMockData(){

        // General initializations
        MockStateMileageDAO stateMileageDAOMock = new MockStateMileageDAO();
        
        
        Date startDate = new Date();
        Date endDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2010, 9, 1);
        startDate = calendar.getTime();
        calendar.set(2011, 9, 1);
        endDate = calendar.getTime();
                
        Interval interval = new Interval(startDate.getTime(), endDate.getTime());

        reportCriteriaSUT.setStateMileageDAO(stateMileageDAOMock);

        GROUP_IDS.add(GROUP_ID);
        GROUP_IDS.add(GROUP_ID2);

        //------------------------------------------------------------------
        // 2. Second, we run the actual method to be tested
        reportCriteriaSUT.init(groupHierarchy, GROUP_IDS, interval, false);
        
        //------------------------------------------------------------------
        // 3. Third we verify the results

       List<StateMileageByVehicleRoadStatus> dataSet = reportCriteriaSUT.getMainDataset();
       assertNotNull(dataSet);
       assertTrue(dataSet.size() == 6);
       StateMileageByVehicleRoadStatus bean = dataSet.get(0);
       assertEquals(bean.getGroupName(), GROUP_FULL_NAME_1 );
       assertEquals(bean.getState(), "STUB Colorado");
       assertEquals(bean.getVehicle(), "STUB 10026217");
       assertTrue(bean.getTotal().doubleValue() == 3108d);
       assertEquals(bean.getRoadStatus(),"On-Road");
       
       bean = dataSet.get(1);
       assertEquals(bean.getGroupName(), GROUP_FULL_NAME_1);
       assertEquals(bean.getState(), "STUB Colorado");
       assertEquals(bean.getVehicle(), "STUB 11077461");
       assertTrue(bean.getTotal().doubleValue() == 4484d);
       assertEquals(bean.getRoadStatus(),"On-Road");
       
       bean = dataSet.get(2);
       assertEquals(bean.getGroupName(), GROUP_FULL_NAME_1);
       assertEquals(bean.getState(), "STUB Colorado");
       assertEquals(bean.getVehicle(), "STUB 11187740");
       assertTrue(bean.getTotal().doubleValue() == 1817d);
       assertEquals(bean.getRoadStatus(),"On-Road");
       
       bean = dataSet.get(3);
       assertEquals(bean.getGroupName(), GROUP_FULL_NAME_2 );
       assertEquals(bean.getState(), "STUB New Mexico");
       assertEquals(bean.getVehicle(), "STUB 10740909");
       assertTrue(bean.getTotal().doubleValue() == 827d);
       assertEquals(bean.getRoadStatus(),"Off-Road");

    }    
    
    /**
     * Tests that the sort order produced by the Comparator is correct.
     */
    @Test
    public void testComparatorSort(){
        StateMileageByVehicleRoadStatus[] beans = new StateMileageByVehicleRoadStatus[3];
        beans[0] = this.createBean("Group B", "124568", 3453D); 
        beans[1] = this.createBean("Group A", "845782", 3653D); 
        beans[2] = this.createBean("Group A", "104578", 2353D); 

        List<StateMileageByVehicleRoadStatus> dataList = Arrays.asList(beans.clone());
        Collections.sort(dataList, reportCriteriaSUT.new StateMileageByVehicleRoadStatusComparator());

        // verify the correct order
        assertTrue(EqualsBuilder.reflectionEquals(beans[2], dataList.get(0))); 
        assertTrue(EqualsBuilder.reflectionEquals(beans[1], dataList.get(1))); 
        assertTrue(EqualsBuilder.reflectionEquals(beans[0], dataList.get(2))); 
    }
    
    @Test
    public void testComparatorSortWorksOnNulls(){
        StateMileageByVehicleRoadStatus[] beans = new StateMileageByVehicleRoadStatus[3];
        beans[0] = new StateMileageByVehicleRoadStatus();
        beans[1] = new StateMileageByVehicleRoadStatus(); 
        beans[2] = new StateMileageByVehicleRoadStatus(); 

        List<StateMileageByVehicleRoadStatus> dataList = Arrays.asList(beans);
        
        try {
            Collections.sort(dataList, reportCriteriaSUT.new StateMileageByVehicleRoadStatusComparator());
        } catch (NullPointerException e) {
            fail(e.getClass() + " not expected.");
        }
    }
    
    /* Helper to create a StateMileageByVehicleRoadStatus bean */
    private StateMileageByVehicleRoadStatus createBean(String groupName, String vehicle, Double distance){
        StateMileageByVehicleRoadStatus bean = new StateMileageByVehicleRoadStatus();
        bean.setGroupName(groupName);
        bean.setVehicle(vehicle);
        bean.setTotal(distance);
        bean.setState(STATE);
        return bean;
    }

}

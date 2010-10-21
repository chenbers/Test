package com.inthinc.pro.reports.ifta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.StateMileage;
import com.inthinc.pro.reports.BaseUnitTest;
import com.inthinc.pro.reports.ifta.model.MileageByVehicle;

/**
 * Test for StateMileageByMonthReportCriteria.
 */
public class StateMileageByMonthReportCriteriaTest extends BaseUnitTest {
    
    // Constant values
    private final Integer GROUP_ID = new Integer(2);
    private final List<Integer> GROUP_IDS = new ArrayList<Integer>();
    private final String MONTH = "AUGUST";
    private final String GROUP_FULL_NAME = "Group Full Name";
    private static final String STATE = "Florida";
    private static final Long MILES = 5000L;
    
    private final Locale LOCALE = Locale.US;
    private final Interval INTERVAL = new Interval(new Date().getTime() - 3600, new Date().getTime());
    
    // JMockit mocks
    
    // The System Under Test
    private StateMileageByMonthReportCriteria reportCriteriaSUT = new StateMileageByMonthReportCriteria(LOCALE); 

    
    /**
     * Tests the init() method.
     */
    @Test
    @SuppressWarnings({ "unchecked", "static-access" })
    public void testInitWithMocks(final StateMileageDAO stateMileageDAOMock){

        // General initializations
        reportCriteriaSUT.setStateMileageDAO(stateMileageDAOMock);

        GROUP_IDS.add(GROUP_ID);
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
              stateMileageDAOMock.getStateMileageByGroupAndMonth(GROUP_ID, INTERVAL, false); returns(getData());
              measurementUtilMock.convertMilesToKilometers(MILES, MeasurementType.ENGLISH); returns(MILES);
           }
           
           // Helper method
           private List<StateMileage> getData(){
               List<StateMileage> list = new ArrayList<StateMileage>();
               
               StateMileage bean = new StateMileage(); 
               bean.setMonth(MONTH); 
               bean.setStateName(STATE);
               bean.setGroupName(GROUP_FULL_NAME);
               bean.setMiles(MILES);
               
               list.add(bean);
               return list;
           }
           
        };
        
        //------------------------------------------------------------------
        // 2. Second, we run the actual method to be tested
        reportCriteriaSUT.init(GROUP_IDS, INTERVAL, false);
        
        //------------------------------------------------------------------
        // 3. Third we verify the results

       List<MileageByVehicle> dataSet = reportCriteriaSUT.getMainDataset();
       assertNotNull(dataSet);
       assertTrue(dataSet.size() == 1);
       MileageByVehicle bean = dataSet.get(0);
       assertEquals(bean.getGroupName(), GROUP_FULL_NAME);
       assertEquals(bean.getState(), STATE);
       assertEquals(bean.getMonth(), MONTH);
       assertTrue(bean.getTotal().doubleValue() == MILES.doubleValue());

    }    
    
    /**
     * Tests that the sort order produced by the Comparator is correct.
     */
    @Test
    public void testComparatorSort(){
        MileageByVehicle[] beans = new MileageByVehicle[4];
        beans[0] = this.createBean("Group B", MONTH, STATE, 3453D); 
        beans[1] = this.createBean("Group A", "October", STATE, 3653D); 
        beans[2] = this.createBean("Group A", MONTH, STATE,2353D); 
        beans[3] = this.createBean("Group A", "October", "Utah", 3653D); 

        List<MileageByVehicle> dataList = Arrays.asList(beans.clone());
        Collections.sort(dataList, reportCriteriaSUT.new StateMileageByMonthComparator());

        // verify the correct order
        assertTrue(EqualsBuilder.reflectionEquals(beans[2], dataList.get(0))); 
        assertTrue(EqualsBuilder.reflectionEquals(beans[1], dataList.get(1))); 
        assertTrue(EqualsBuilder.reflectionEquals(beans[3], dataList.get(2))); 
        assertTrue(EqualsBuilder.reflectionEquals(beans[0], dataList.get(3))); 
    }
    
    /* Helper to create a MileageByVehicle bean */
    private MileageByVehicle createBean(String groupName, String month, String state,  Double total){
        MileageByVehicle bean = new MileageByVehicle();
        bean.setGroupName(groupName);
        bean.setMonth(month);
        bean.setTotal(total);
        bean.setState(state);
        return bean;
    }

}

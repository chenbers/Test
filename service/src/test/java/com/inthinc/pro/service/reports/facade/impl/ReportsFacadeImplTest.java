package com.inthinc.pro.service.reports.facade.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import mockit.Deencapsulation;
import mockit.Delegate;
import mockit.Expectations;
import mockit.Mocked;

import org.joda.time.Interval;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.service.ReportCriteriaService;
import com.inthinc.pro.service.impl.BaseUnitTest;

/**
 * Unit test for ReportsFacade.
 */
public class ReportsFacadeImplTest extends BaseUnitTest {
    private static final Integer GROUP_ID = 1;
    private static final List<Integer> groupIDList = new ArrayList<Integer>();
    private static final Interval INTERVAL = new Interval(0L, 1L);
    private static final Locale locale = Locale.US;
    private static final MeasurementType measureType = MeasurementType.ENGLISH;
    private static final boolean IFTA_ONLY = true;
    
	@Mocked 
	ReportCriteriaService reportServiceMock;
	
	// Partial mocking: mock only these three methods 
	@Mocked(methods = {"getAccountGroupHierarchy()", "getLocale()", "getMeasurementType()"}) 
	ReportsFacadeImpl reportsFacadeSUTMock;    
    
	@BeforeClass
	public static void setUp() {
	    groupIDList.add(GROUP_ID);
	}
	
	@Test
	public void testGetTenHourViolations() {
		
		Deencapsulation.setField(reportsFacadeSUTMock, reportServiceMock);
		
		new Expectations(){{
			reportServiceMock.getTenHoursDayViolationsCriteria(null, GROUP_ID, INTERVAL, Locale.US);
			result = new ReportCriteria();
		}};

		reportsFacadeSUTMock.getTenHourViolations(GROUP_ID, INTERVAL, Locale.US);
	}	
	
    @Test
    public void testGetDriverHours() {
        
        Deencapsulation.setField(reportsFacadeSUTMock, reportServiceMock);
        
        new Expectations(){{
            List<Integer> groupIdList = new ArrayList<Integer>();
            groupIdList.add(GROUP_ID);
            reportServiceMock.getDriverHoursReportCriteria(null, groupIdList, INTERVAL, Locale.US);
            result = new ReportCriteria();
        }};

        reportsFacadeSUTMock.getDriverHours(GROUP_ID, INTERVAL, Locale.US);
    }
    
	@SuppressWarnings("unchecked")
	@Test
	public void testGetStateMileageByVehicleRoadStatus() {
		
		Deencapsulation.setField(reportsFacadeSUTMock, reportServiceMock);
		
		new Expectations(){{
			reportServiceMock.getStateMileageByVehicleRoadStatusReportCriteria(null, 
			    (List<Integer>) any, INTERVAL, null, null, IFTA_ONLY);
			result = new ServiceDelegate();
		}};
		
		reportsFacadeSUTMock.getStateMileageByVehicleRoadStatus(groupIDList, INTERVAL, IFTA_ONLY, locale, measureType);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetMileageByVehicle() {
		
		Deencapsulation.setField(reportsFacadeSUTMock, reportServiceMock);
		
		new Expectations(){{
			reportServiceMock.getMileageByVehicleReportCriteria(null, (List<Integer>) any, INTERVAL, null, null, IFTA_ONLY);
			result = new ServiceDelegate();
		}};
		
		reportsFacadeSUTMock.getMileageByVehicle(groupIDList, INTERVAL, IFTA_ONLY, locale, measureType);
	}	
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetStateMileageByVehicleStateComparison() {
		
		Deencapsulation.setField(reportsFacadeSUTMock, reportServiceMock);
		
		new Expectations(){{
			reportServiceMock.getStateMileageCompareByGroupReportCriteria(null, (List<Integer>) any, INTERVAL, null, null, IFTA_ONLY);
			result = new ServiceDelegate();
		}};
		
		reportsFacadeSUTMock.getStateMileageGroupComparison(groupIDList, INTERVAL, IFTA_ONLY, locale, measureType);
	}	
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetStateMileageByVehicle() {
		
		Deencapsulation.setField(reportsFacadeSUTMock, reportServiceMock);
		
		new Expectations(){{
			reportServiceMock.getStateMileageByVehicleReportCriteria(null, (List<Integer>) any, INTERVAL, null, null, IFTA_ONLY);
			result = new ServiceDelegate();
		}};
		
		reportsFacadeSUTMock.getStateMileageByVehicle(groupIDList, INTERVAL, IFTA_ONLY, locale, measureType);
	}

    @SuppressWarnings("unchecked")
    @Test
    public void testGetStateMileageByMonth() {        
        Deencapsulation.setField(reportsFacadeSUTMock, reportServiceMock);
        
        new Expectations(){{
            reportServiceMock.getStateMileageByMonthReportCriteria(null, (List<Integer>) any, INTERVAL, null, null, IFTA_ONLY);
            result = new ServiceDelegate();
        }};
        
        reportsFacadeSUTMock.getStateMileageByMonth(groupIDList, INTERVAL, IFTA_ONLY, locale, measureType);
    }
	
	/**
	 * Delegate used to capture and verify arguments passed to the method being tested
	 * @author dcueva
	 */
	private class ServiceDelegate implements Delegate {
		@SuppressWarnings("unused")
		ReportCriteria getCriteria(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, Locale locale, MeasurementType measurementType, boolean dotOnly){
		
			assertNotNull(groupIDList);
			assertTrue(GROUP_ID.equals(groupIDList.get(0)));
			return new ReportCriteria();
		}		
	}
}

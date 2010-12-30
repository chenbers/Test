/**
 * 
 */
package com.inthinc.pro.reports.ifta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import mockit.Expectations;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.joda.time.Interval;
import org.junit.Test;

import com.inthinc.pro.dao.StateMileageDAO;
import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.StateMileage;
import com.inthinc.pro.reports.BaseUnitTest;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ifta.model.StateMileageFuelByVehicle;

/**
 * Unit Test for the StateMileageFuelByVehicle class.
 */
public class StateMileageFuelByVehicleReportCriteriaTest extends BaseUnitTest {

    private static final String GROUP_NAME_1 = "GN1";
	private static final String GROUP_NAME_2 = "GN2";
    private final List<Integer> GROUP_ID_LIST = new ArrayList<Integer>();
	private final Integer GROUP_ID = new Integer(2);
    private final Integer GROUP_ID2 = new Integer(3);	
    private final Locale LOCALE = Locale.US;	
    private final Interval INTERVAL = new Interval(new Date().getTime() - 3600, new Date().getTime());
    
    private Group[] groupList = {
            new Group(GROUP_ID, 1, GROUP_NAME_1, 0),
            new Group(GROUP_ID2, 1, GROUP_NAME_2, GROUP_ID)
    };
    private GroupHierarchy groupHierarchy = new GroupHierarchy(Arrays.asList(groupList));

	private StateMileageFuelByVehicleReportCriteria reportCriteriaSUT = new StateMileageFuelByVehicleReportCriteria(LOCALE);

	// General initializations
	{
	    GROUP_ID_LIST.add(GROUP_ID);
	    GROUP_ID_LIST.add(GROUP_ID2);
	}
	
	private Map<Integer, String> expectedGroupNames = new HashMap<Integer, String>() {
        private static final long serialVersionUID = 1L;

        {
            put(GROUP_ID, groupList[0].getName());
            put(GROUP_ID2, groupList[0].getName() + StateMileageFuelByVehicleReportCriteria.SLASH_GROUP_SEPERATOR  + groupList[1].getName());
        }
    };
	
	/**
	 * Test the init method.
	 */
	@Test
	public void testInit(final StateMileageDAO stateMileageDAOMock){
        
        reportCriteriaSUT.setStateMileageDAO(stateMileageDAOMock);
        
        // Strict JMockit expectations
        new Expectations() {
        	{
        		stateMileageDAOMock.getFuelStateMileageByVehicle(GROUP_ID, (Interval)any, false);
        		// returning null in purpose, so no processing is done in initDataSet
        	}
        };
        
        // Run the test
        assertNull(reportCriteriaSUT.getMainDataset());
        reportCriteriaSUT.init(groupHierarchy, GROUP_ID_LIST, INTERVAL, false);
        assertNotNull(reportCriteriaSUT.getMainDataset());
	}
	
	/**
	 * Test the initDatSet method
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testInitDataSet()
	{
		List<StateMileage> records = new ArrayList<StateMileage>();
		StateMileage record1 = getStateMileageItem(GROUP_ID);
		StateMileage record2 = getStateMileageItem(GROUP_ID2);
		records.add(record1);
		records.add(record2);

		reportCriteriaSUT.setMeasurementType(MeasurementType.ENGLISH);
		reportCriteriaSUT.setAccountGroupHierarchy(groupHierarchy);
		
		// Run the test
		reportCriteriaSUT.initDataSet(records);
		
		List<StateMileageFuelByVehicle> dataList = reportCriteriaSUT.getMainDataset();
		assertEquals(dataList.size(), 2);
		StateMileageFuelByVehicle bean1 = dataList.get(0);
		StateMileageFuelByVehicle bean2 = dataList.get(1);
		assertTrue(recordCorrectlyCopiedIntoBean(bean1, record1));
		assertTrue(recordCorrectlyCopiedIntoBean(bean2, record2));
	}

	/**
	 * Test the initDatSet method
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testInitDataSetWithMetric()
	{
		List<StateMileage> records = new ArrayList<StateMileage>();
		StateMileage record1 = getStateMileageItem(GROUP_ID);
		StateMileage record2 = getStateMileageItem(GROUP_ID2);
		records.add(record1);
		records.add(record2);

		reportCriteriaSUT.setMeasurementType(MeasurementType.METRIC);
        reportCriteriaSUT.setAccountGroupHierarchy(groupHierarchy);
		
		// Run the test
		reportCriteriaSUT.initDataSet(records);
		
		List<StateMileageFuelByVehicle> dataList = reportCriteriaSUT.getMainDataset();
		assertEquals(dataList.size(), 2);
		StateMileageFuelByVehicle bean1 = dataList.get(0);
		StateMileageFuelByVehicle bean2 = dataList.get(1);
		assertTrue(convertedToMetric(bean1, record1));
		assertTrue(convertedToMetric(bean2, record2));
	}
	
	/**
	 * Test the comparator.
	 */
	@Test
	public void testComparatorSort(){
	    reportCriteriaSUT.setAccountGroupHierarchy(groupHierarchy);

		List<StateMileageFuelByVehicle> listToSort = getListToSort();
		Collections.sort(listToSort, reportCriteriaSUT.new StateMileageFuelByVehicleComparator());
		isSorted(listToSort);
	}
	
	/**
	 * Test the mileage calculation.
	 */
	@Test
	public void testMileageCalculation(){
		final Double zero = new Double(0);
		final Double totalMiles = new Double(500);
		final Double totalTruckGas = new Double(123);
		
		// When Total truck Gas is zero, we should return zero
		assertEquals(reportCriteriaSUT.getMileage(totalMiles, zero), zero);
		
		// In other cases we return totalMiles / totalTruckGas rounded to 2 decimals
		Double mileage = reportCriteriaSUT.getMileage(totalMiles, totalTruckGas);
		assertEquals(mileage, new Double(Math.round((totalMiles / totalTruckGas) * 100.0) / 100.0));
	}
	
	/**
	 * Test the setter for the fuel efficiency type
	 */
	@Test
	public void testFuelEfficiencySetter(){
		reportCriteriaSUT.setFuelEfficiencyType(FuelEfficiencyType.LP100KM);
		
		assertEquals(FuelEfficiencyType.LP100KM, reportCriteriaSUT.getFuelEfficiencyType());
		String resourceKey = FuelEfficiencyType.LP100KM.toString();
		resourceKey = Character.toLowerCase(resourceKey.charAt(0)) + resourceKey.substring(1);
			
		assertEquals(resourceKey,reportCriteriaSUT.getParameter(ReportCriteria.FUEL_EFFICIENCY_TYPE));
	}
	
	/**
     * DEF5942: https://rally1.rallydev.com/slm/detail/df/2574711991
     * 
     * Adding tests to verify against NPE on comparators.
     */
    @Test
    public void testCompareWithNulls() {
        StateMileageFuelByVehicleReportCriteria.StateMileageFuelByVehicleComparator comparator = (new StateMileageFuelByVehicleReportCriteria(null).new StateMileageFuelByVehicleComparator());

        StateMileageFuelByVehicle o1 = new StateMileageFuelByVehicle();
        StateMileageFuelByVehicle o2 = new StateMileageFuelByVehicle();

        assertEquals(0, comparator.compare(o1, o2));
    }
	
	/**
	 * Verifies if the list was sorted correctly.
	 * 
	 * @param listToSort The list to verify
	 * @return T/F
	 */
	private void isSorted(List<StateMileageFuelByVehicle> list) {
		
		Iterator<StateMileageFuelByVehicle> it = list.iterator();

		StateMileageFuelByVehicle prev = it.next();
		assertNotNull(prev);
		while(it.hasNext()){
			StateMileageFuelByVehicle curr = it.next();
			assertTrue(before(prev,curr));
		}
	}

	/**
	 * Verifies that a bean should be ordered before another bean.
	 * 
	 * @param prev Previous value in the list. Must come before than curr.
	 * @param curr Current value int he list. Must come after before
	 * @return true if previous must in fact be before curr
	 */
	private boolean before(StateMileageFuelByVehicle prev, StateMileageFuelByVehicle curr) {
		boolean groupNameOK = prev.getGroupName().compareTo(curr.getGroupName()) <= 0;
		boolean vehicleOK = Integer.valueOf(prev.getVehicleName()) <= Integer.valueOf(curr.getVehicleName());
		boolean monthOK = Integer.valueOf(prev.getMonth()) <= Integer.valueOf(curr.getMonth());
		boolean stateOK = Integer.valueOf(prev.getState()) <= Integer.valueOf(curr.getState());
		
		return groupNameOK && vehicleOK && monthOK && stateOK;
	}

	/**
	 * Produces a list to sort.
	 * 
	 * @return A list with all the 16 possible combinations of GroupName, Vehicle, Month and State in a random order. 
	 */
	private List<StateMileageFuelByVehicle> getListToSort() {
		List<StateMileageFuelByVehicle> listToSort = new ArrayList<StateMileageFuelByVehicle>();
		
		// Generates the 16 combinations of the 4 attributes
		for (int i = 0; i <= 15; i++){
			StateMileageFuelByVehicle bean = new StateMileageFuelByVehicle();
			bean.setGroupName(Integer.toString(i / 8));
			bean.setVehicleName(String.valueOf(i / 4));
			bean.setMonth(String.valueOf(i / 2));
			bean.setState(String.valueOf(i / 1));
			listToSort.add(bean);	
			
			//System.out.println(groupName + " " + vehicle + " "+ month + " " + state);
			/*
			0 0 0 0
			0 0 0 1
			0 0 1 2
			0 0 1 3
			0 1 2 4
			0 1 2 5
			0 1 3 6
			0 1 3 7
			1 2 4 8
			1 2 4 9
			1 2 5 10
			1 2 5 11
			1 3 6 12
			1 3 6 13
			1 3 7 14
			1 3 7 15
			*/
		}
		Collections.shuffle(listToSort);		
		return listToSort;
	}

	/**
	 * Verifies that the Gallons and Miles values are converted to metric
	 * 
	 * @param bean The bean to be used in Jasper 
	 * @param record The record returned by the Back End.
	 * @return True if the values were converted to metric
	 */
	private boolean convertedToMetric(StateMileageFuelByVehicle bean, StateMileage record) {
		
		return new EqualsBuilder()
        .append(bean.getTotalMiles().doubleValue(), MeasurementConversionUtil.convertMilesToKilometers(record.getMiles(), MeasurementType.METRIC).doubleValue())
        .append(bean.getTotalTruckGas().doubleValue(), MeasurementConversionUtil.fromGallonsToLiters(record.getTruckGallons()).doubleValue())
        .append(bean.getTotalTrailerGas().doubleValue(), MeasurementConversionUtil.fromGallonsToLiters(record.getTrailerGallons()).doubleValue())
        .isEquals();
	}

	/**
	 * Verifies that the fields from a Back End record have been correctly copied into the bean fields.
	 * 
	 * @param bean The bean to be used in Jasper 
	 * @param record The record returned by the Back End.
	 * @return True if the data was correctly copied.
	 */
	private boolean recordCorrectlyCopiedIntoBean(StateMileageFuelByVehicle bean, StateMileage record) {
		return new EqualsBuilder()
	        .append(bean.getGroupName(), expectedGroupNames.get(record.getGroupID()))
	        .append(bean.getVehicleName(), record.getVehicleName())
	        .append(bean.getMonth(), record.getMonth())
	        .append(bean.getState(), record.getStateName())
	        .append(bean.getTotalMiles().doubleValue(), record.getMiles().doubleValue())
	        .append(bean.getTotalTruckGas().doubleValue(), record.getTruckGallons().doubleValue())
	        .append(bean.getTotalTrailerGas().doubleValue(), record.getTrailerGallons().doubleValue())
	        .isEquals();
	}

	private StateMileage getStateMileageItem(Integer groupID) {
		StateMileage sm = new StateMileage();
		sm.setGroupID(groupID);
		sm.setVehicleName("VN");
		sm.setMonth("October, 2010");
		sm.setStateName("UT");
		sm.setMiles(500L);
		sm.setTruckGallons(20F);
		sm.setTrailerGallons(30F);
		return sm;
	}
	
	
	
}

/**
 * 
 */
package com.inthinc.pro.reports.asset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import mockit.Expectations;
import mockit.Mocked;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.assets.AssetWarrantyRecord;
import com.inthinc.pro.reports.BaseUnitTest;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.asset.model.Warranty;
import com.inthinc.pro.reports.dao.WaysmartDAO;

/**
 * Unit Test for WarrantyListReportCriteria
 *
 */
public class WarrantyListReportCriteriaTest extends BaseUnitTest {
	
	private final Locale LOCALE = Locale.US;
	private final Integer PARENT_GROUP_ID = new Integer(1);
	private final Integer ACCOUNT_ID = new Integer(PARENT_GROUP_ID + 1000);
	private final String ACCOUNT_NAME = "Customer Name";
    private final String VEHICLE_NAME = "Vehicle Name";
	private final String IMEI = "IMEI";
	private final String GROUP_NAME = "Parent Group/Child 2 group";
	
	private final Integer CHILD1_GROUP_ID = new Integer(11);
	private final Integer CHILD2_GROUP_ID = new Integer(12);
	
	@Mocked private GroupHierarchy groupHierarchyMock;
	private final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(ReportCriteria.DATE_FORMAT).withLocale(LOCALE);


	
	private WarrantyListReportCriteria reportCriteriaSUT = new WarrantyListReportCriteria(LOCALE); 

	/**
	 * Test the init method.
	 * 
	 * @param groupDAOMock Mock for the Group DAO to be injected int he report criteria.
	 * @param waysmartDAOMock Mock for the Waysmart DAO to be injected int he report criteria.
	 */
	@SuppressWarnings("unchecked")
	@Ignore
	@Test
	public void testInit(final WaysmartDAO waysmartDAOMock){
		
		// inject the mocks
	    reportCriteriaSUT.setWaysmartDAO(waysmartDAOMock);
		
	    final List<AssetWarrantyRecord> warrantyList = getWarrantyList();
	    
		new Expectations(){
			{
			    groupHierarchyMock.getSubGroupList(PARENT_GROUP_ID); returns(getGroupList());

				// Test the two paths in the Group for loop
				waysmartDAOMock.getWarrantyList(CHILD1_GROUP_ID, true); returns(null);
				waysmartDAOMock.getWarrantyList(CHILD2_GROUP_ID, true); returns(warrantyList); 
				
				groupHierarchyMock.getShortGroupName(CHILD2_GROUP_ID, ReportCriteria.SLASH_GROUP_SEPERATOR);returns(GROUP_NAME);
			}
		};
		
		// Run the test
		reportCriteriaSUT.init(groupHierarchyMock, PARENT_GROUP_ID, ACCOUNT_ID, ACCOUNT_NAME, true);
		
		assertEquals(reportCriteriaSUT.getParameter(reportCriteriaSUT.CUSTOMER_NAME_KEY), ACCOUNT_NAME);
		assertEquals(reportCriteriaSUT.getParameter(reportCriteriaSUT.CUSTOMER_ID_KEY), ACCOUNT_ID.toString());
		
		List<Warranty> dataSet = reportCriteriaSUT.getMainDataset();
		assertNotNull(dataSet);
		assertEquals(dataSet.size(), 2);
		assertTrue(recordCorrectlyCopiedIntoBean(warrantyList.get(0), dataSet.get(0)));
		assertTrue(recordCorrectlyCopiedIntoBean(warrantyList.get(1), dataSet.get(1)));
	}
	
	private boolean recordCorrectlyCopiedIntoBean(
			AssetWarrantyRecord record, Warranty bean) {
		return new EqualsBuilder()
		.append(record.getVehicleName(), bean.getVehicleName())
		.append(record.getImei(), bean.getImei())
		.append(formatDate(record.getStartDate()), bean.getWarrantyStartDate())
		.append(formatDate(record.getEndDate()), bean.getWarrantyEndDate())
		.append(new Boolean(record.isExpired()), bean.getExpired())
        .isEquals();
	}

	private String formatDate(Date date) {
		return dateTimeFormatter.print(date.getTime());
	}

	private Group getGroup(Integer groupID){
		return new Group(groupID, ACCOUNT_ID, "Group_Name_" + groupID.toString(), null);
	}
	
	private List<Group> getGroupList(){
		List<Group> groupList = new ArrayList<Group>();
		groupList.add(getGroup(CHILD1_GROUP_ID));
		groupList.add(getGroup(CHILD2_GROUP_ID));
		return groupList;
	}
	
	private AssetWarrantyRecord getWarrantyRecord(int i){
		AssetWarrantyRecord assetWarranty = new AssetWarrantyRecord();
		assetWarranty.setVehicleName(VEHICLE_NAME + "_" + i);
		assetWarranty.setImei(IMEI);
		assetWarranty.setStartDate(new Date());
		assetWarranty.setEndDate(new Date());
		assetWarranty.setExpired(true);
		
		return assetWarranty;
	}
	
	private List<AssetWarrantyRecord> getWarrantyList(){
		List<AssetWarrantyRecord> warrantyList = new ArrayList<AssetWarrantyRecord>();
		warrantyList.add(getWarrantyRecord(1));
		warrantyList.add(getWarrantyRecord(2));
		return warrantyList;
		
	}	
}

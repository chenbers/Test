/**
 * 
 */
package com.inthinc.pro.backing;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Locale;

import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.junit.Test;

import com.inthinc.pro.reports.BaseUnitTest;
import com.inthinc.pro.reports.ReportCategory;
import com.inthinc.pro.reports.ReportGroup;

/**
 * Unit Test for WaysmartReportsBean
 */
public class WaysmartReportsBeanTest extends BaseUnitTest {

	// The SUT
	private WaysmartReportsBean waysmartReportsBeanSUT = new WaysmartReportsBean(); 
	
	/**
	 * Test obtaining the report items to be displayed in the UI.
	 */
	@Test
	public void testGetReportGroups(){
		List<SelectItemGroup> groupList = waysmartReportsBeanSUT.getReportGroups();
		
		// The first group must have a single blank item
		SelectItemGroup blankGroup = groupList.get(0);
		assertNotNull(blankGroup.getSelectItems());
		SelectItem[] blankItems = blankGroup.getSelectItems();
		assertTrue(blankItems.length == 1);
		assertTrue("".equals(blankItems[0].getLabel()));
		
		// The second group includes the Performance items
		checkGroupIncludesOnlyCategory(groupList.get(1), ReportCategory.Performance);

		// The third group includes the DOT/IFTA items
		checkGroupIncludesOnlyCategory(groupList.get(2), ReportCategory.IFTA);
	}
	
	/**
	 * Verify that a given group includes items only pertaining to a given category.
	 * 
	 * @param group The group whose items we will verify.
	 * @param category The category all items must belong to.
	 */
	private void checkGroupIncludesOnlyCategory(SelectItemGroup group, ReportCategory category){
		assertNotNull(group.getSelectItems());
		SelectItem[] items = group.getSelectItems();
		for (SelectItem i : items){
			
			// Get the key to the group enumeration
			Integer code = (Integer) i.getValue();
			
			// Use the map in the SUT to get the actual enum.
			ReportGroup reportGroupEnum = waysmartReportsBeanSUT.getReportGroupMap().get(code);
			assertTrue(reportGroupEnum.isCategory(category));
		}
	}
	
	@Test
	public void testCheckResourceBundlekeysForStateMileage(){
	    String[] keys = {
	            "report.title.metric",
	            "report.title.english",
	            "report.title.stateMileageByVehicleRoadStatus",
	            "description.reporttype.state.mileage.by.vehicle.road.status",
	            "description.reporttype.state.mileage.by.vehicle",
	            "description.reporttype.mileage.by.vehicle",
	            "description.reporttype.state.mileage.fuel.by.vehicle",
	            "description.reporttype.state.mileage.by.month",
	            "description.reporttype.state.mileage.compare.by.group",
	            "stateMileageByMonth.title.english",
	            "stateMileageByMonth.title.metric",
	            "stateMileageByVehicle.title.english",
	            "stateMileageByVehicle.title.metric",
	            "stateMileageCompareByGroup.title",
	            "group.label",
	            "column.1",
	            "column.group",
	            "column.vehicle",
	            "column.month",
	            "column.vehicle.raw",
	            "column.3",
	            "column.state",
	            "column.roadStatus",
	            "column.total",
	            "total",
	            "footer.confidential",
	            "footer.page",
	            "column.state.raw",
	            "column.roadStatus.raw",
	            "column.total.raw",
	            "column.month.raw",
	            "column.totalMiles.raw",
	            "column.totalTruckGas.raw",
	            "column.totalTrailerGas.raw",
	            "column.mileage.raw",
	            "column.group.raw"
	    };
	    String resourceBundleName = "com.inthinc.pro.reports.jasper.ifta.i18n.stateMileage";
	    List missingKeys = super.checkResourceBundleKeys(keys, resourceBundleName, Locale.US);
	    assertTrue(missingKeys.isEmpty());
	}
	
	@Test
    public void testCheckResourceBundlekeysForDriverHours(){
        String[] keys = {
                "report.title",
                "description.reporttype.driver.hours",
                "total.group",
                "total.driver",
                "total.date",
                "footer.confidential",
                "footer.page"
        };
        String resourceBundleName = "com.inthinc.pro.reports.jasper.performance.i18n.driverHoursReport";
        List missingKeys = super.checkResourceBundleKeys(keys, resourceBundleName, Locale.US);
        assertTrue(missingKeys.isEmpty());
    }
	
   @Test
    public void testCheckResourceBundlekeysForTenHourDayViolations(){
        String[] keys = {
                "report.title",
                "description.reporttype.ten.hour.day.violations",
                "column.1",
                "column.2",
                "column.3",
                "column.4",
                "column.5",
                "column.6",
                "footer.confidential",
                "footer.page",
                "column.1.raw",
                "column.2.raw",
                "column.3.raw",
                "column.4.raw",
                "column.5.raw",
                "column.6.raw"
        };
        String resourceBundleName = "com.inthinc.pro.reports.jasper.performance.i18n.tenHourDayViolations";
        List missingKeys = super.checkResourceBundleKeys(keys, resourceBundleName, Locale.US);
        assertTrue(missingKeys.isEmpty());
    }
   
   @Test
   public void testCheckResourceBundlekeysForVehicleUsage(){
       String[] keys = {
               "report.title",
               "column.1",
               "column.2",
               "column.3",
               "column.4",
               "column.5",
               "column.6",
               "column.7",
               "column.8",
               "column.9",
               "column.10",
               "column.11",
               "column.13",
               "footer.confidential",
               "footer.page",
               "column.1.raw",
               "column.2.raw",
               "column.3.raw",
               "column.4.raw",
               "column.5.raw",
               "column.6.raw",
               "column.7.raw",
               "column.8.raw",
               "column.9.raw",
               "column.10.raw",
               "column.11.raw",
               "column.12.raw",
               "footer.daily"
       };
       String resourceBundleName = "com.inthinc.pro.reports.jasper.performance.i18n.vehicleUsageReport";
       List missingKeys = super.checkResourceBundleKeys(keys, resourceBundleName, Locale.US);
       assertTrue(missingKeys.isEmpty());
   }

}

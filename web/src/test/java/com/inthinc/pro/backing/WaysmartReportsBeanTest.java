/**
 * 
 */
package com.inthinc.pro.backing;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.junit.Test;

import com.inthinc.pro.reports.ReportCategory;
import com.inthinc.pro.reports.ReportGroup;

/**
 * Unit Test for WaysmartReportsBean
 */
public class WaysmartReportsBeanTest {

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
	
}

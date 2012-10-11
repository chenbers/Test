package com.inthinc.pro.service.reports.facade.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import mockit.Cascading;
import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;

import org.junit.Assert;
import org.junit.Test;

import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.User;
import com.inthinc.pro.service.impl.BaseUnitTest;
import com.inthinc.pro.service.security.TiwiproPrincipal;

/**
 * Unit test for ReportsFacade.
 */
public class ReportsFacadeImplHelpersTest extends BaseUnitTest {
    private static final Integer GROUP_ID = 1505;
    private static final String GROUP_NAME = "Mock Group";
    
    private ReportsFacadeImpl reportsFacadeSUT = new ReportsFacadeImpl();

    @Test 
    public void testGetGroupHierarchy(@Mocked final TiwiproPrincipal principalMock) {

    	Deencapsulation.setField(reportsFacadeSUT, principalMock);

        new Expectations() {{
                principalMock.getAccountGroupHierarchy();  returns(getHierarchy());
        }};
        
        GroupHierarchy hierarchy = reportsFacadeSUT.getAccountGroupHierarchy();
        
        Assert.assertNotNull(hierarchy);
        Assert.assertEquals(hierarchy.getShortGroupName(GROUP_ID, "/"), GROUP_NAME);
    }
    
    protected GroupHierarchy getHierarchy() {
    	Group group = new Group(GROUP_ID, 1, GROUP_NAME, null);
        List<Group> groupList = new ArrayList<Group>();
        groupList.add(group);
        
        GroupHierarchy hierarchy = new GroupHierarchy();
        hierarchy.setGroupList(groupList);
        return hierarchy;
	}

	@Test 
	public void testGetMeasurementType(@Mocked final TiwiproPrincipal principalMock, 
									   @Cascading final User userMock) {

		Deencapsulation.setField(reportsFacadeSUT, principalMock);
        
    	new Expectations() {{
    	  // Cannot use cascading on TiwiproPrincipal
    	  principalMock.getUser(); returns(userMock);
    	  userMock.getPerson().getMeasurementType(); returns(MeasurementType.METRIC);
        }};
    
        assertEquals(reportsFacadeSUT.getMeasurementType(), MeasurementType.METRIC);
    }

	@Test 
	public void testGetLocale(@Mocked final TiwiproPrincipal principalMock, 
							  @Cascading final User userMock) {

		Deencapsulation.setField(reportsFacadeSUT, principalMock);
        
    	new Expectations() {{
    	  // Cannot use cascading on TiwiproPrincipal
    	  principalMock.getUser(); returns(userMock);
    	  userMock.getPerson().getLocale(); returns(Locale.CANADA_FRENCH);
        }};
    
        assertEquals(reportsFacadeSUT.getLocale(), Locale.CANADA_FRENCH);
    }	
	
}

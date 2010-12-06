package com.inthinc.pro.service.reports.facade;

import java.util.ArrayList;
import java.util.List;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;

import org.junit.Assert;
import org.junit.Test;

import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.service.impl.BaseUnitTest;
import com.inthinc.pro.service.security.TiwiproPrincipal;

/**
 * Unit test for ReportsFacade.
 */
public class ReportsFacadeTest extends BaseUnitTest {
    private static final Integer GROUP_ID = 1505;
    private static final String GROUP_NAME = "Mock Group";

    private ReportsFacade reportsFacadeSUT = new ReportsFacade();
    
    @Mocked private TiwiproPrincipal principalMock;

    
    @Test public void testGetGroupHierarchy() {

    	Deencapsulation.setField(reportsFacadeSUT, principalMock);

        new Expectations() {{
                principalMock.getAccountGroupHierarchy(); returns(getHierarchy());
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

	@Test public void testGetLocale() {
        Assert.assertNotNull(reportsFacadeSUT.getLocale());
    }
}

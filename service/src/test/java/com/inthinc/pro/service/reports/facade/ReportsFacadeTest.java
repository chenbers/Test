package com.inthinc.pro.service.reports.facade;

import java.util.ArrayList;
import java.util.List;

import mockit.Expectations;
import mockit.Mocked;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.reports.service.ReportCriteriaService;
import com.inthinc.pro.service.adapters.GroupDAOAdapter;
import com.inthinc.pro.service.impl.BaseUnitTest;

/**
 * Unit test for ReportsFacade.
 */
public class ReportsFacadeTest extends BaseUnitTest {
    private static final Integer GROUP_ID = 1505;

    private ReportsFacade reportsFacadeSUT = new ReportsFacade();
    
    @Mocked private ReportCriteriaService reportServiceMock;
    @Mocked private GroupDAOAdapter groupAdapterMock;
    
    @Test public void testGetGroupHierarchy() {
        reportsFacadeSUT.setGroupAdapter(groupAdapterMock);
        final Group group = new Group(GROUP_ID, 1, "Mock Group", null);
        
        new Expectations() {
            {
                List<Group> list = new ArrayList<Group>();
                list.add(group);
                groupAdapterMock.getAll(); returns(list);
            }
        };
        
        GroupHierarchy hierarchy = reportsFacadeSUT.getGroupHierarchy();
        
        Assert.assertNotNull(hierarchy);
        Assert.assertNotNull(hierarchy.getShortGroupName(GROUP_ID, "/"));
    }
    
    @Test public void testGetLocale() {
        Assert.assertNotNull(reportsFacadeSUT.getLocale());
    }
}

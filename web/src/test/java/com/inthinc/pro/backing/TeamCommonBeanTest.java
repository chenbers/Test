package com.inthinc.pro.backing;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.easymock.classextension.EasyMock;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.GroupTrendWrapper;
import com.inthinc.pro.model.aggregation.Trend;

public class TeamCommonBeanTest extends BaseBeanTest {
    @Test
    public void getOverallScoreUsingTrendScore_nullSubGroupsAggregateDriverTrends_noNPE() throws SecurityException, NoSuchMethodException {
        Group mockGroup = EasyMock.createNiceMock(Group.class);
        Integer fakeParentGroupID = 1;

        expect(mockGroup.getParentID()).andReturn(fakeParentGroupID);
        replay(mockGroup);

        GroupHierarchy mockGroupHierarchy = createMock(GroupHierarchy.class);
        Method mockGetGroupHierarchy = TeamCommonBean.class.getMethod("getGroupHierarchy", null);
        Method mockGetTimeFrame = TeamCommonBean.class.getMethod("getTimeFrame", null);

        TeamCommonBean partialMockTeamCommonBean = EasyMock.createNiceMock(TeamCommonBean.class, mockGetGroupHierarchy, mockGetTimeFrame);
        EasyMock.expect(partialMockTeamCommonBean.getGroupHierarchy()).andReturn(mockGroupHierarchy);
        TimeFrame mockTimeFrame = TimeFrame.MONTH; 

        EasyMock.expect(partialMockTeamCommonBean.getTimeFrame()).andReturn(mockTimeFrame);
        EasyMock.expectLastCall().anyTimes();

        Map<String, GroupTrendWrapper> fakeCachedTrendResults = new HashMap<String,GroupTrendWrapper>();
        partialMockTeamCommonBean.setCachedTrendResults(fakeCachedTrendResults);
        replay(partialMockTeamCommonBean);

        GroupReportDAO mockGroupReportDAO = createMock(GroupReportDAO.class);
        partialMockTeamCommonBean.setGroup(mockGroup);
        expect(mockGroupReportDAO.getSubGroupsAggregateDriverTrends(fakeParentGroupID, Duration.THREE, mockGroupHierarchy)).andReturn(null);
        replay(mockGroupReportDAO);
        try {
            partialMockTeamCommonBean.getOverallScoreUsingTrendScore(mockGroupReportDAO);
        } catch( NullPointerException npe) {
            npe.printStackTrace();
            fail();
        }
    }
	
    @Test
    public void testPopulateDateGaps() {
        TeamCommonBean teamCommonBean = (TeamCommonBean)applicationContext.getBean("teamCommonBean");

        teamCommonBean.setTimeFrame(TimeFrame.TODAY);
        
        DateTime trendEndingDate = new DateTime();
        trendEndingDate = trendEndingDate.minusDays(30);
        trendEndingDate = trendEndingDate.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
        trendEndingDate = trendEndingDate.withZone(DateTimeZone.UTC);
        
        List<Trend> trendList = new ArrayList<Trend>();
        Trend trend = new Trend();
        trend.setEndingDate(trendEndingDate.toDate());
        
        trendList.add(trend);
        
        teamCommonBean.populateDateGaps(trendList);
        
//        for(Trend t : trendList) {
//            System.out.println(t.getEndingDate());
//        }
        
        assertEquals(32, trendList.size());
        
        teamCommonBean.setTimeFrame(TimeFrame.MONTH);
        
        trendEndingDate = new DateTime();
        trendEndingDate = trendEndingDate.minusMonths(3);
        trendEndingDate = trendEndingDate.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
        trendEndingDate = trendEndingDate.dayOfMonth().withMinimumValue();
        trendEndingDate = trendEndingDate.withZone(DateTimeZone.UTC);
        
        trendList = new ArrayList<Trend>();
        trend = new Trend();
        trend.setEndingDate(trendEndingDate.toDate());
        trendList.add(trend);
        
        teamCommonBean.populateDateGaps(trendList);
        
//        for(Trend t : trendList) {
//            System.out.println(t.getEndingDate());
//        }
        
        assertEquals(4, trendList.size());
    }

}

package com.inthinc.pro.reports.performance;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.apache.log4j.Logger;
import org.joda.time.Interval;
import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventAggregationDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.DriverForgivenEventTotal;
import com.inthinc.pro.model.event.EventType;
import com.inthinc.pro.reports.BaseUnitTest;
import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.ReportCriteria;

public class DriverExcludedViolationsCriteriaTest extends BaseUnitTest{
    
    private static Logger logger = Logger.getLogger(DriverExcludedViolationsCriteriaTest.class);
    
    @Mocked
    private GroupDAO groupDAO;
    
    @Mocked
    private EventAggregationDAO eventAggregationDAO;
    
    @Mocked
    private DriverDAO driverDAO;
    
    private List<DriverForgivenEventTotal>  driverForgivenEventTotals;
    
    private GroupHierarchy groupHierarchy = new GroupHierarchy();
    
    private static final List<Integer> eventIDPickList;
    
    private static final List<Integer> groupIDPickList;
    
    static{
        eventIDPickList = new ArrayList<Integer>();
        eventIDPickList.add(1);
        eventIDPickList.add(2);
        eventIDPickList.add(3);
        eventIDPickList.add(4);
        eventIDPickList.add(5);
        eventIDPickList.add(6);
        
        groupIDPickList = new ArrayList<Integer>();
        groupIDPickList.add(1);
        groupIDPickList.add(2);
        groupIDPickList.add(3);
    }
    
    @Before
    public void setUpTests(){
        List<Group> groups = new ArrayList<Group>();
        Group rootGroup = new Group();
        rootGroup.setAccountID(1);
        rootGroup.setGroupID(1);
        rootGroup.setName("Root Group");
        rootGroup.setPath("1");
        groups.add(rootGroup);
        
        Group parentGroup = new Group();
        parentGroup.setAccountID(1);
        parentGroup.setGroupID(2);
        parentGroup.setName("Parent Group");
        parentGroup.setPath("1,2");
        parentGroup.setParentID(1);
        groups.add(parentGroup);
        
        Group childGroup = new Group();
        childGroup.setAccountID(1);
        childGroup.setGroupID(3);
        childGroup.setName("Team Group");
        childGroup.setPath("1,2,3");
        childGroup.setParentID(2);
        groups.add(childGroup);
        
        groupHierarchy.setGroupList(groups);
        
        driverForgivenEventTotals = new ArrayList<DriverForgivenEventTotal>();
        Random random = new Random();
        for(int i = 0;i < 12;i++){
            DriverForgivenEventTotal driverForgivenEventTotal = new DriverForgivenEventTotal();
            driverForgivenEventTotal.setDriverID(random.nextInt(3));
            driverForgivenEventTotal.setDriverName("Test Driver " + driverForgivenEventTotal.getDriverID());
            
            driverForgivenEventTotal.setEventType(EventType.getEventType(eventIDPickList.get(random.nextInt(6))));
            driverForgivenEventTotal.setEventCount(12);
            driverForgivenEventTotal.setEventCountForgiven(5);
            int listId = random.nextInt(3);
            driverForgivenEventTotal.setGroupID(groupIDPickList.get(listId));
            driverForgivenEventTotal.setGroupName(groupHierarchy.getGroup(driverForgivenEventTotal.getGroupID()).getName());
            double percent = 5D / 12D;
            driverForgivenEventTotal.setPercentForgiven(Double.valueOf(percent));
            driverForgivenEventTotals.add(driverForgivenEventTotal);
        }
    }
    
    
    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void testBuildDriverExcludedViolationsCriteriaTest(){
        new NonStrictExpectations() {{
            
            eventAggregationDAO.findDriverForgivenEventTotalsByGroups((List)any, (Interval)any);
            returns(driverForgivenEventTotals);
        }};
        
        List<Integer> groupIDs = new ArrayList<Integer>();
        groupIDs.add(12);
        
        DriverExcludedViolationsCriteria.Builder builder = new DriverExcludedViolationsCriteria.Builder(groupHierarchy,eventAggregationDAO, groupDAO, driverDAO, groupIDs, TimeFrame.DAY.getInterval());
        builder.setLocale(Locale.US);
        List<ReportCriteria> reportCriterias = new ArrayList<ReportCriteria>();
        reportCriterias.add(builder.build());

        dump("DriverExcludedViolations", 2, reportCriterias, FormatType.PDF);
    }

}

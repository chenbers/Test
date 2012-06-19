package com.inthinc.pro.reports.communication;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.dao.EventAggregationDAO;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.event.LastReportedEvent;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.reports.BaseUnitTest;
import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.performance.DriverExcludedViolationsCriteriaTest;

public class NonCommReportCriteriaTest extends BaseUnitTest{
    
    private static Logger logger = Logger.getLogger(DriverExcludedViolationsCriteriaTest.class);
    
    @Mocked
    private EventAggregationDAO eventAggregationDAO;
    
    private List<LastReportedEvent>  lastReportedEvents;
    
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
        rootGroup.setGroupID(0);
        rootGroup.setName("Root Group");
        rootGroup.setPath("0");
        groups.add(rootGroup);
        
        Group parentGroup = new Group();
        parentGroup.setAccountID(1);
        parentGroup.setGroupID(1);
        parentGroup.setName("Parent Group");
        parentGroup.setPath("0,1");
        parentGroup.setParentID(0);
        groups.add(parentGroup);
        
        Group childGroup = new Group();
        childGroup.setAccountID(1);
        childGroup.setGroupID(2);
        childGroup.setName("Team Group");
        childGroup.setPath("0,1,2");
        childGroup.setParentID(1);
        groups.add(childGroup);
        
        groupHierarchy.setGroupList(groups);
        
        lastReportedEvents = new ArrayList<LastReportedEvent>();
        Random random = new Random();
        for(int i = 0;i < 12;i++){
            LastReportedEvent lastReportedEvent = new LastReportedEvent();
            lastReportedEvent.setGroupID(random.nextInt(3));
            lastReportedEvent.setGroupName(groupHierarchy.getGroup(lastReportedEvent.getGroupID()).getName());
            lastReportedEvent.setType(NoteType.valueOf(random.nextInt(6)));
            lastReportedEvent.setVehicleID(i);
            lastReportedEvent.setVehicleName("Vehicle " + i);
            lastReportedEvent.setDeviceSerialNum("NNMMFF"+i);
            DateTime dateTime = new DateTime();
            dateTime.minusDays(i);
            lastReportedEvent.setTime(dateTime.toDate());
            lastReportedEvent.setDaysSince(i);
            
            lastReportedEvents.add(lastReportedEvent);
        }
    }
    
    
    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void testBuildNonCommCriteriaTest(){
        new NonStrictExpectations() {{
            eventAggregationDAO.findRecentEventByDevice((List)any, (Interval)any);
            returns(lastReportedEvents);
        }};
        
        List<Integer> groupIDs = new ArrayList<Integer>();
        groupIDs.add(12);
        
        NonCommReportCriteria.Builder builder = new NonCommReportCriteria.Builder(groupHierarchy, eventAggregationDAO, groupIDs, TimeFrame.DAY.getInterval());
        
        builder.setLocale(Locale.US);
        List<ReportCriteria> reportCriterias = new ArrayList<ReportCriteria>();
        reportCriterias.add(builder.build());

        dump("NonComm", 2, reportCriterias, FormatType.PDF);
    }

}

package com.inthinc.pro.reports.performance;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventAggregationDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.DriverForgivenEvent;
import com.inthinc.pro.model.event.EventType;
import com.inthinc.pro.reports.BaseUnitTest;
import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.ReportCriteria;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import org.apache.log4j.Logger;
import org.joda.time.Interval;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class DriverExcludedViolationsDetailCriteriaTest extends BaseUnitTest {

    private static Logger logger = Logger.getLogger(DriverExcludedViolationsDetailCriteriaTest.class);

    @Mocked
    private GroupDAO groupDAO;

    @Mocked
    private EventAggregationDAO eventAggregationDAO;

    @Mocked
    private DriverDAO driverDAO;

    private List<DriverForgivenEvent> driverForgivenEvent;

    private GroupHierarchy groupHierarchy = new GroupHierarchy();

    private static final List<Integer> eventIDPickList;

    private static final List<Integer> groupIDPickList;

    static {
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
    public void setUpTests() {
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

        driverForgivenEvent = new ArrayList<DriverForgivenEvent>();
        Random random = new Random();
        for (int i = 0; i < 12; i++) {
            DriverForgivenEvent driverForgivenEvent = new DriverForgivenEvent();
            driverForgivenEvent.setDriverID(random.nextInt(3));
            driverForgivenEvent.setDriverName("Test Driver " + driverForgivenEvent.getDriverID());

            driverForgivenEvent.setEventType(EventType.getEventType(eventIDPickList.get(random.nextInt(6))));
            driverForgivenEvent.setDateTime(new Date());
            driverForgivenEvent.setReason("reason" + i);
            int listId = random.nextInt(3);
            driverForgivenEvent.setGroupID(groupIDPickList.get(listId));
            driverForgivenEvent.setGroupName(groupHierarchy.getGroup(driverForgivenEvent.getGroupID()).getName());
            double percent = 1D / 189D;
            this.driverForgivenEvent.add(driverForgivenEvent);
        }
    }


    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void testBuildDriverExcludedViolationsDetailCriteriaTest() {
        new NonStrictExpectations() {{

            eventAggregationDAO.findDriverForgivenEventsByGroups((List) any, (Interval) any);
            returns(driverForgivenEvent);
        }};

        List<Integer> groupIDs = new ArrayList<Integer>();
        groupIDs.add(12);

        DriverExcludedViolationsDetailCriteria.Builder builder = new DriverExcludedViolationsDetailCriteria.Builder(groupHierarchy, eventAggregationDAO, groupDAO, driverDAO, groupIDs, TimeFrame.DAY.getInterval());
        builder.setLocale(Locale.US);
        List<ReportCriteria> reportCriterias = new ArrayList<ReportCriteria>();
        reportCriterias.add(builder.build());

        dump("DriverExcludedViolationsDetail", 2, reportCriterias, FormatType.PDF);
    }

}

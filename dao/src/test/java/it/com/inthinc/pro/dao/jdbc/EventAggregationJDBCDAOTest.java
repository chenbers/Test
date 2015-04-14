package it.com.inthinc.pro.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.inthinc.pro.model.aggregation.DriverForgivenEventTotal;
import it.config.ITDataSource;
import it.config.IntegrationConfig;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.dao.hessian.GroupHessianDAO;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.EventAggregationJDBCDAO;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.event.LastReportedEvent;

// set to Ignore for running on QA.
//@Ignore
public class EventAggregationJDBCDAOTest extends BaseJDBCTest {
    
    // In case the test date is destroyed, regen as follows:
    // Generated a canned account by running this java app:
    // it.util.DataGenForStressTesting NEW src/test/resources/NonCommTest.xml
    // this generates an account with a bunch of devices, vehicles, drivers and a trip for each device/vehicle
    // Set the interval day to the day after the generator is run (UTC).
    // TEST_ACCOUNT_ID and EXPECTED_VALID_COUNT will need to be adjusted to reflect the new data set.
    private static Integer TEST_ACCOUNT_ID = 979;
    private static Integer EXPECTED_VALID_COUNT = 366;
    
    
    private static List<Integer> testGroupList;

    private static DateTime SEP_12_2013 = new DateTime(1428969600000l);// 1375401600000l);
    private static Interval VALID_TEST_INTERVAL = new Interval(SEP_12_2013, SEP_12_2013.plusDays(1)); 
    
    
    private static SiloService siloService;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        IntegrationConfig config = new IntegrationConfig();

        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());

        siloService = new SiloServiceCreator(host, port).getService();

        GroupHessianDAO groupDAO = new GroupHessianDAO();
        groupDAO.setSiloService(siloService);
        
        List<Group> groupList = groupDAO.getGroupsByAcctID(TEST_ACCOUNT_ID);
        
        testGroupList = new ArrayList<Integer>();
        for (Group group : groupList) {
            if (group.getType() == GroupType.TEAM) {
                testGroupList.add(group.getGroupID());
            }
            
        }
    }




    @Ignore
    public void testOldVsNew() {
        EventAggregationJDBCDAO eventAggregationJDBCDAO = new EventAggregationJDBCDAO();
        eventAggregationJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());

        List<LastReportedEvent> lastEventListOld = eventAggregationJDBCDAO.findRecentEventByDevice(testGroupList, VALID_TEST_INTERVAL);
        List<LastReportedEvent> lastEventListNew = eventAggregationJDBCDAO.findLastEventForVehicles(testGroupList, VALID_TEST_INTERVAL);
        
        assertEquals("old method of fetching matches new", lastEventListOld.size(), lastEventListNew.size());
        
        for (int i = 0; i < lastEventListNew.size(); i++) {
            LastReportedEvent oldRec = lastEventListOld.get(i);
            LastReportedEvent newRec = lastEventListNew.get(i);
            assertEquals(i + " days Since", oldRec.getDaysSince(), newRec.getDaysSince());
            assertEquals(i + " device", oldRec.getDeviceSerialNum(), newRec.getDeviceSerialNum());
            assertEquals(i + " device", oldRec.getType(), newRec.getType());
            
        }

    }
    
    @Test
    public void testFindLastEventForVehicles() {
        EventAggregationJDBCDAO eventAggregationJDBCDAO = new EventAggregationJDBCDAO();
        eventAggregationJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
        
        List<LastReportedEvent> lastEventList= eventAggregationJDBCDAO.findLastEventForVehicles(testGroupList, VALID_TEST_INTERVAL);
        assertEquals("expected count", EXPECTED_VALID_COUNT.intValue(), lastEventList.size());
        
        for (LastReportedEvent event : lastEventList) {
            assertTrue("last date invalid for query interval", new DateTime(event.getTime()).isBefore(SEP_12_2013));
            
        }

    }

    @Test
    public void testReasonColumnInSelect() {
        // first, get the data
        EventAggregationJDBCDAO eventAggregationJDBCDAO = new EventAggregationJDBCDAO();
        eventAggregationJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
        List<DriverForgivenEventTotal> dfetList = eventAggregationJDBCDAO.findDriverForgivenEventTotalsByGroups(testGroupList, VALID_TEST_INTERVAL, true, true);

        // then count the number of reasons found
        int found = 0;
        if (dfetList != null && !dfetList.isEmpty()){
            for (DriverForgivenEventTotal df: dfetList){
                if (df.getReasons() != null && !df.getReasons().trim().isEmpty()){
                    found ++;
                }
            }
        }
    }
}

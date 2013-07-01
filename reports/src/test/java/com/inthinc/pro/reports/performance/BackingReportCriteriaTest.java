package com.inthinc.pro.reports.performance;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.joda.time.Interval;
import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.reports.BaseUnitTest;
import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.ReportCriteria;

public class BackingReportCriteriaTest extends BaseUnitTest {
    
    @Mocked
    GroupReportDAO groupReportDAO;
    
    private GroupHierarchy groupHierarchy;
    
    private static Integer FLEET_GROUP_ID = 0;
    private static Integer DIV_GROUP_ID = 1;
    private static Integer TEAM_GROUP_ID = 2;
    
    private static Integer ACTIVE_DRIVERS_PER_TEAM = 30;
    private static Integer INACTIVE_DRIVERS_PER_TEAM = 20;
    private static Integer ZERO_MILES_DRIVERS_PER_TEAM = 10;
        
    @Before
    public void setup() {
        List<Group> groups = new ArrayList<Group>();
        Group rootGroup = new Group();
        rootGroup.setAccountID(1);
        rootGroup.setGroupID(FLEET_GROUP_ID);
        rootGroup.setName("Root Group");
        rootGroup.setPath("0");
        groups.add(rootGroup);
        
        Group parentGroup = new Group();
        parentGroup.setAccountID(1);
        parentGroup.setGroupID(DIV_GROUP_ID);
        parentGroup.setName("Parent Group");
        parentGroup.setPath("0,1");
        parentGroup.setParentID(FLEET_GROUP_ID);
        groups.add(parentGroup);
        
        Group childGroup = new Group();
        childGroup.setAccountID(1);
        childGroup.setGroupID(TEAM_GROUP_ID);
        childGroup.setName("Team Group");
        childGroup.setPath("0,1,2");
        childGroup.setParentID(DIV_GROUP_ID);
        groups.add(childGroup);

        groupHierarchy = new GroupHierarchy();
        groupHierarchy.setGroupList(groups);
        mockGroupReportDao();
        
    }

    @Test
    public void testBackingReport() {
        Boolean includeInactiveDrivers = true;
        Boolean includeZeroMilesDrivers = true;
        runTest(includeInactiveDrivers, includeZeroMilesDrivers, (ACTIVE_DRIVERS_PER_TEAM+INACTIVE_DRIVERS_PER_TEAM+ZERO_MILES_DRIVERS_PER_TEAM), 1);
    }
    
    @Test
    public void testBackingReportNoInactive() {
        

        Boolean includeInactiveDrivers = false;
        Boolean includeZeroMilesDrivers = true;
        runTest(includeInactiveDrivers, includeZeroMilesDrivers, (ACTIVE_DRIVERS_PER_TEAM+ZERO_MILES_DRIVERS_PER_TEAM), 2);
    }
    
    @Test
    public void testBackingReportNoZero() {
        

        Boolean includeInactiveDrivers = true;
        Boolean includeZeroMilesDrivers = false;

        runTest(includeInactiveDrivers, includeZeroMilesDrivers, (ACTIVE_DRIVERS_PER_TEAM+INACTIVE_DRIVERS_PER_TEAM), 3);
    }
    
    private void runTest( Boolean includeInactiveDrivers, Boolean includeZeroMilesDrivers, int expectedDriverCount, int dumpID) {
        BackingReportCriteria.Builder builder = new BackingReportCriteria.Builder(groupHierarchy, groupReportDAO, 
                TEAM_GROUP_ID, TimeFrame.MONTH, MeasurementType.ENGLISH, includeInactiveDrivers, includeZeroMilesDrivers);
        ReportCriteria reportCriteria = builder.build();
        assertEquals("all drivers count" , expectedDriverCount, reportCriteria.getMainDataset().size());

        BackingReportCriteria.BackingWrapper reportRec = (BackingReportCriteria.BackingWrapper)reportCriteria.getMainDataset().get(0);
        assertEquals("driver Name", "Last1, First1", reportRec.getDriverName());
        assertEquals("group Path", "Parent Group - Team Group", reportRec.getGroupPath());
        assertEquals("driverMiles", 10000, reportRec.getDrivingMiles().intValue());
        assertEquals("trips", 2, reportRec.getTrips().intValue());
        assertEquals("backup events", 5, reportRec.getBackupEvents().intValue());
        assertEquals("backup seconds", 1000, reportRec.getBackupTime().intValue());
        
        
        List<ReportCriteria> reportCriterias = new ArrayList<ReportCriteria>();
        reportCriterias.add(reportCriteria);
        
        dump("BackingReport", dumpID, reportCriterias, FormatType.PDF);
        
    }
    private void mockGroupReportDao() {
        new NonStrictExpectations() {
            {
                groupReportDAO.getDriverScores(TEAM_GROUP_ID, (Interval)any, groupHierarchy);
                returns(creatReportTestData());
            }
        };
    }

    private List<DriverVehicleScoreWrapper> creatReportTestData() {
        List<DriverVehicleScoreWrapper> recList = new ArrayList<DriverVehicleScoreWrapper>();
        for (int i = 0; i < ACTIVE_DRIVERS_PER_TEAM; i++) {
            DriverVehicleScoreWrapper rec = makeDriverVehicleScoreWrapper(i+1, true, 10000);
            recList.add(rec);
        }
        for (int i = 0; i < INACTIVE_DRIVERS_PER_TEAM; i++) {
            DriverVehicleScoreWrapper rec = makeDriverVehicleScoreWrapper(i+1, false, 10000);
            recList.add(rec);
        }
        for (int i = 0; i < ZERO_MILES_DRIVERS_PER_TEAM; i++) {
            DriverVehicleScoreWrapper rec = makeDriverVehicleScoreWrapper(i+1, true, 0);
            recList.add(rec);
        }
        return recList;
    }
    
    private static DriverVehicleScoreWrapper makeDriverVehicleScoreWrapper(int id, boolean isActive, Integer mileage) {
        DriverVehicleScoreWrapper wrapper = new DriverVehicleScoreWrapper();
        Score score = new Score();
        
        score.setTrips(2);
        score.setOdometer6(mileage);
        score.setBackingTime(1000);
        score.setBackingEvents(5);
        
        wrapper.setScore(score);
        
        Driver driver = new Driver(id, id, Status.ACTIVE, "", 0l, 0l, "1wireID", "", null, "", null, "", null, TEAM_GROUP_ID);
        driver.setStatus(isActive ? Status.ACTIVE : Status.INACTIVE);
        Person person = new Person();
        person.setLast("Last" + id);
        person.setFirst("First" + id);
        person.setDriver(driver);
        driver.setPerson(person);
        wrapper.setDriver(driver);
        
        wrapper.setVehicle(null);
        
        return wrapper;
    }

}

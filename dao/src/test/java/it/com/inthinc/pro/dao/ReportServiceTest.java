package it.com.inthinc.pro.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.config.IntegrationConfig;
import it.config.ReportTestConst;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.dao.hessian.AccountHessianDAO;
import com.inthinc.pro.dao.hessian.DeviceHessianDAO;
import com.inthinc.pro.dao.hessian.GroupHessianDAO;
import com.inthinc.pro.dao.hessian.MpgHessianDAO;
import com.inthinc.pro.dao.hessian.RoleHessianDAO;
import com.inthinc.pro.dao.hessian.ScoreHessianDAO;
import com.inthinc.pro.dao.hessian.StateHessianDAO;
import com.inthinc.pro.dao.hessian.proserver.ReportService;
import com.inthinc.pro.dao.hessian.proserver.ReportServiceCreator;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.CrashSummary;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.model.DriverScore;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.IdlePercentItem;
import com.inthinc.pro.model.IdlingReportData;
import com.inthinc.pro.model.IdlingReportItem;
import com.inthinc.pro.model.MpgEntity;
import com.inthinc.pro.model.ScoreItem;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreTypeBreakdown;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.SpeedPercentItem;
import com.inthinc.pro.model.TrendItem;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleReportItem;
import com.inthinc.pro.model.app.DeviceSensitivityMapping;
import com.inthinc.pro.model.app.Roles;
import com.inthinc.pro.model.app.States;

//@Ignore
public class ReportServiceTest {
    private static ReportService reportService;
    private static SiloService siloService;

    private static final String REPORT_BASE_DATA_XML = "ReportTest.xml";
    // private static final String REPORT_BASE_DATA_XML = "ReportTest3.xml"; // test against 3/4 cluster on dev
    private static final int MAX_TOTAL_DAYS = 360;

    private static Account account;
    private static Group fleetGroup;
    private static Group districtGroup;
    private static List<GroupData> teamGroupData;
    private static Integer startDateInSec;
    private static int totalDays;

    static class GroupData {
        Integer driverType;
        Group group;
        User user;
        Device device;
        Vehicle vehicle;
        Driver driver;
    }

    public static double MILEAGE_BUFFER = 1d;

    public static int GOOD = 0;
    public static int INTERMEDIATE = 1;
    public static int BAD = 2;

    public static Integer expectedFleetOverall = 27;
    public static Integer expectedTeamOverall[] = { 50, // GOOD
            30, // INTERMEDIATE
            24, // BAD
    };
    public static Integer expectedDailyMPGLight[] = { 30, // GOOD
            25, // INTERMEDIATE
            20, // BAD
    };
    public static Integer expectedDailyMPGMedium[] = { 0, 0, 0 };
    public static Integer expectedDailyMPGHeavy[] = { 0, 0, 0 };
    public static Integer expectedDailyFleetMPGLight = 25;
    public static Integer expectedDailyFleetMPGMedium = 0;
    public static Integer expectedDailyFleetMPGHeavy = 0;

    Integer expectedTeamBreakdown[][] = { { 50, 50, 50, 50, 50 }, // GOOD (OVERALL, SPEEDING, SEATBELT, DRIVING STYLE, COACHING)
            { 23, 23, 23, 23, 23 }, // INTERMEDIATE
            { 17, 17, 17, 17, 17 }, // BAD
    };

    static long baseMilesPerDay = ReportTestConst.MILES_PER_EVENT * ReportTestConst.EVENTS_PER_DAY; // 1/100 mile units
    private static long expectedDailyMileagePerGroup[] = { baseMilesPerDay, // GOOD
            baseMilesPerDay + 2 * ReportTestConst.MILES_PER_EVENT, // Intermediate (2 coaching events)
            baseMilesPerDay + 10 * ReportTestConst.MILES_PER_EVENT, // BAD
    };

    private static final long expectedDailyDriveTime = ReportTestConst.ELAPSED_TIME_PER_EVENT * ReportTestConst.EVENTS_PER_DAY; // millisecond units

    // BAD Driver has 1 crash per day
    CrashSummary expectedCrashSummary[] = {
    // new CrashSummary(Integer crashesInTimePeriod, Integer totalCrashes, Integer daysSinceLastCrash, Number totalMiles, Number milesSinceLastCrash);
            new CrashSummary(0, 0, totalDays, expectedDailyMileagePerGroup[GOOD] * totalDays, expectedDailyMileagePerGroup[GOOD] * totalDays), // GOOD
            new CrashSummary(0, 0, totalDays, expectedDailyMileagePerGroup[INTERMEDIATE] * totalDays, expectedDailyMileagePerGroup[INTERMEDIATE] * totalDays), // Intermediate
            // new CrashSummary(totalDays, totalDays, 0, expectedDailyMileagePerGroup[BAD] * totalDays, (ReportTestConst.EVENTS_PER_DAY - ReportTestConst.CRASH_EVENT_IDX) *
            // ReportTestConst.MILES_PER_EVENT), // BAD
            new CrashSummary(totalDays, totalDays, 0, expectedDailyMileagePerGroup[BAD] * totalDays, 0l), // BAD
    };

    Integer expectedDriverCoaching[] = { 0, 2, // 1 seat belt and 1 speeding
            10, // 5 seat belt and 5 speeding
    };

    long expectedDailyLoIdle[] = { 0l, // GOOD
            ReportTestConst.LO_IDLE_TIME, // INTERMEDIATE
            ReportTestConst.LO_IDLE_TIME * 5l // BAD
    };
    long expectedDailyHiIdle[] = { 0l, // GOOD
            ReportTestConst.HI_IDLE_TIME, // INTERMEDIATE
            ReportTestConst.HI_IDLE_TIME * 5l // BAD
    };
    public static int expectedScoreBreakdown[][][] = {
    // GOOD
            { { 0, 0, 0, 0, 100 }, // speeding
                    { 0, 0, 0, 0, 100 }, // seat belt
                    { 0, 0, 0, 0, 100 }, // aggressive driving
                    { 0, 0, 0, 0, 100 }, // idling
            },
            // INTERMEDIATE
            { { 0, 0, 100, 0, 0 }, // speeding
                    { 0, 100, 0, 0, 0 }, // seat belt
                    { 0, 0, 100, 0, 0 }, // aggressive driving
                    { 0, 0, 0, 0, 100 }, // idling
            },
            // BAD
            { { 0, 0, 100, 0, 0 }, // speeding
                    { 100, 0, 0, 0, 0 }, // seat belt
                    { 0, 0, 100, 0, 0 }, // aggressive driving
                    { 0, 0, 0, 0, 100 }, // idling
            }, };

    static DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");

    private static boolean parseTestData(String xmlPath) {
        try {
            InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(xmlPath);
            // InputStream stream = new FileInputStream(xmlPath);
            XMLDecoder xml = new XMLDecoder(new BufferedInputStream(stream));
            account = getNext(xml, Account.class);
            getNext(xml, Address.class);
            fleetGroup = getNext(xml, Group.class);
            districtGroup = getNext(xml, Group.class);
            teamGroupData = new ArrayList<GroupData>();
            for (int i = GOOD; i <= BAD; i++) {
                Group group = getNext(xml, Group.class);
                GroupData groupData = new GroupData();
                groupData.group = group;
                groupData.driverType = i;
                teamGroupData.add(groupData);
//                System.out.println("Team " + i + " groupID: " + group.getGroupID());
            }
            getNext(xml, User.class);
            for (int i = GOOD; i <= BAD; i++) {
                GroupData groupData = teamGroupData.get(i);
                groupData.user = getNext(xml, User.class);
                groupData.device = getNext(xml, Device.class);
                groupData.driver = getNext(xml, Driver.class);
//                System.out.println("driverID: " + groupData.driver.getDriverID());
                groupData.vehicle = getNext(xml, Vehicle.class);
            }
            startDateInSec = getNext(xml, Integer.class);
            // dateFormat.setTimeZone(ReportTestConst.timeZone);
            // System.out.println("startDate: " + dateFormat.format(new Date(startDateInSec * 1000l)));
            Integer todayInSec = DateUtil.getDaysBackDate(DateUtil.getTodaysDate(), 0, ReportTestConst.TIMEZONE_STR);
            
          totalDays = (todayInSec - startDateInSec) / DateUtil.SECONDS_IN_DAY;
//System.out.println("totalDays: " + totalDays);          
            if (totalDays > MAX_TOTAL_DAYS)
                totalDays = MAX_TOTAL_DAYS;
//            System.out.println("Total Days: " + totalDays);
            xml.close();
            return dataExists();
        } catch (Exception ex) {
            System.out.println("error reading " + xmlPath);
            ex.printStackTrace();
            return false;
        }
    }

    private static <T> T getNext(XMLDecoder xml, Class<T> expectedType) throws Exception {
        Object result = xml.readObject();
        if (expectedType.isInstance(result)) {
            return (T) result;
        } else {
            throw new Exception("Expected " + expectedType.getName());
        }
    }

    private static boolean dataExists() {
        // just spot check that account and team exist (this could be more comprehensive)
        AccountHessianDAO accountDAO = new AccountHessianDAO();
        accountDAO.setSiloService(siloService);
        Account existingAccount = accountDAO.findByID(account.getAcctID());
        boolean dataExists = (existingAccount != null);
        if (dataExists) {
            GroupHessianDAO groupDAO = new GroupHessianDAO();
            groupDAO.setSiloService(siloService);
            Group existingTeam = groupDAO.findByID(teamGroupData.get(0).group.getGroupID());
            dataExists = (existingTeam != null && existingTeam.getType().equals(GroupType.TEAM));
        }
        if (!dataExists) {
            System.out.println("TEST DATA is missing: regenerate the base test data set");
        }
        return dataExists;
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

        IntegrationConfig config = new IntegrationConfig();

        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());

        reportService = new ReportServiceCreator(host, port).getService();
        siloService = new SiloServiceCreator(host, port).getService();
        // HessianDebug.debugIn = true;
        // HessianDebug.debugOut = true;
        // HessianDebug.debugRequest = true;

        initApp();

        if (!parseTestData(REPORT_BASE_DATA_XML)) {
            throw new Exception("Error parsing Test data xml file");
        }

    }

    private static void initApp() throws Exception {
        StateHessianDAO stateDAO = new StateHessianDAO();
        stateDAO.setSiloService(siloService);

        States states = new States();
        states.setStateDAO(stateDAO);
        states.init();

        RoleHessianDAO roleDAO = new RoleHessianDAO();
        roleDAO.setSiloService(siloService);

        Roles roles = new Roles();
        roles.setRoleDAO(roleDAO);
        roles.init();

        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);

        DeviceSensitivityMapping mapping = new DeviceSensitivityMapping();
        mapping.setDeviceDAO(deviceDAO);
        mapping.init();

    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {}

    private Integer getFleetGroupID() {
        return fleetGroup.getGroupID();
    }

    private Integer getDistrictGroupID() {
        return districtGroup.getGroupID();
    }

    private Integer getTeamGroupID(int teamType) {
        return teamGroupData.get(teamType).group.getGroupID();
    }

    private Integer getTeamDriverID(int teamType) {
        return teamGroupData.get(teamType).driver.getDriverID();
    }

    private Integer getTeamVehicleID(int teamType) {
        return teamGroupData.get(teamType).vehicle.getVehicleID();
    }

    @Test
    // @Ignore
    public void averageScoreByType() {
        // getGDScoreByGT

        ScoreHessianDAO scoreDAO = new ScoreHessianDAO();
        scoreDAO.setReportService(reportService);

        ScoreableEntity scoreableEntity = scoreDAO.getAverageScoreByType(getFleetGroupID(), Duration.DAYS, ScoreType.SCORE_OVERALL);
        assertNotNull(scoreableEntity);
        assertEquals("getAverageScoreByType for top level fleet group", expectedFleetOverall, scoreableEntity.getScore());

        for (int teamType = GOOD; teamType <= BAD; teamType++) {
            scoreableEntity = scoreDAO.getAverageScoreByType(getTeamGroupID(teamType), Duration.DAYS, ScoreType.SCORE_OVERALL);
            assertNotNull(scoreableEntity);
            assertNotNull(scoreableEntity.getScore());
            assertEquals("getAverageScoreByType for team groupID: " + scoreableEntity.getEntityID(), expectedTeamOverall[teamType], scoreableEntity.getScore());
        }

        scoreableEntity = scoreDAO.getTrendSummaryScore(getFleetGroupID(), Duration.DAYS, ScoreType.SCORE_OVERALL);
        assertNotNull(scoreableEntity);
        assertEquals("getAverageScoreByType for top level fleet group", expectedFleetOverall, scoreableEntity.getScore());

        for (int teamType = GOOD; teamType <= BAD; teamType++) {
            scoreableEntity = scoreDAO.getTrendSummaryScore(getTeamGroupID(teamType), Duration.DAYS, ScoreType.SCORE_OVERALL);
            assertNotNull(scoreableEntity);
            assertNotNull(scoreableEntity.getScore());
            assertEquals("getAverageScoreByType for team groupID: " + scoreableEntity.getEntityID(), expectedTeamOverall[teamType], scoreableEntity.getScore());
        }

    }

    @Test
    // @Ignore
    public void getScores() {
        // getSDScoresByGT

        ScoreHessianDAO scoreDAO = new ScoreHessianDAO();
        scoreDAO.setReportService(reportService);

        List<ScoreableEntity> scoreableEntityList = scoreDAO.getScores(getDistrictGroupID(), Duration.DAYS, ScoreType.SCORE_OVERALL);

        assertNotNull(scoreableEntityList);
        assertTrue("expected 3 subgroup scores", (scoreableEntityList.size() == 3));

        for (ScoreableEntity scoreableEntity : scoreableEntityList) {
            Boolean found = false;
            for (int teamType = GOOD; teamType <= BAD; teamType++) {
                if (scoreableEntity.getEntityID().equals(getTeamGroupID(teamType))) {
                    assertNotNull("Unexpected NULL Score for groupID " + getTeamGroupID(teamType), scoreableEntity.getScore());
                    assertEquals("Unexpected Score for groupID " + getTeamGroupID(teamType), expectedTeamOverall[teamType], scoreableEntity.getScore());
                    found = true;
                }
            }

            assertTrue("Unexpected Subgroup " + scoreableEntity.getEntityID(), found);
        }
    }

    @Test
    // @Ignore
    public void getScoreBreakdown() {
        // getDPctByGT
        // for pie charts

        ScoreHessianDAO scoreDAO = new ScoreHessianDAO();
        scoreDAO.setReportService(reportService);

        Integer expectedOverallPercentages[] = { 0, // 0.0 to 1.0
                0, // 1.1 to 2.0 -- BAD (24)
                67, // 2.1 to 3.0 -- INTERMEDIATE (30)
                0, // 3.1 to 4.0
                33, // 4.1 to 5.0 -- GOOD (50)
        };
        List<ScoreableEntity> scoreableEntityList = scoreDAO.getScoreBreakdown(getFleetGroupID(), Duration.DAYS, ScoreType.SCORE_OVERALL);

        assertNotNull(scoreableEntityList);
        assertTrue("expected 5 percentage scores", (scoreableEntityList.size() == 5));

        for (int i = 0; i < 5; i++) {
            assertEquals("unexpected percentage index: " + i, expectedOverallPercentages[i], scoreableEntityList.get(i).getScore());
        }

    }

    @Test
    // @Ignore
    public void getScoreTrend() {
        // getSDTrendsByGTC

        ScoreHessianDAO scoreDAO = new ScoreHessianDAO();
        scoreDAO.setReportService(reportService);

        Duration duration = Duration.DAYS;

        Map<Integer, List<ScoreableEntity>> scoreMap = scoreDAO.getTrendScores(getDistrictGroupID(), duration);

        assertNotNull(scoreMap);

        for (Integer groupID : scoreMap.keySet()) {
            // System.out.println("groupID: " + groupID);
            if (groupID.equals(getDistrictGroupID())) {
                List<ScoreableEntity> scoreList = scoreMap.get(getDistrictGroupID());
                assertNotNull("Unexpected NULL ScoreList for groupID " + getDistrictGroupID(), scoreList);
                assertEquals("Unexpected ScoreList size for groupID " + getDistrictGroupID(), duration.getDvqCount(), Integer.valueOf(scoreList.size()));

                continue;
            }

            Boolean found = false;
            for (int teamType = GOOD; teamType <= BAD; teamType++) {
                if (groupID.equals(getTeamGroupID(teamType))) {
                    List<ScoreableEntity> scoreList = scoreMap.get(groupID);
                    assertNotNull("Unexpected NULL ScoreList for groupID " + groupID, scoreList);
                    assertEquals("Unexpected ScoreList size for groupID " + groupID, duration.getDvqCount(), Integer.valueOf(scoreList.size()));
                    // skip 1st score on trending
                    for (int i = 1; i < duration.getDvqCount(); i++) {
                        int score = scoreList.get(i).getScore().intValue();
                        int expected = expectedTeamOverall[teamType].intValue();
                        // System.out.println("  #" + i + ": " + score);
                        assertTrue("#" + i + ": Unexpected Overall trend score " + score + " expected: " + expected + " Team: " + groupID, (score >= expected - 1 && score <= expected + 1));
                    }
                    found = true;
                    break;
                }
            }
            assertTrue("Unexpected Subgroup " + groupID, found);

        }

    }

    @Test
    // @Ignore
    public void speedPercent() {
        // getSDTrendsByGTC

        ScoreHessianDAO scoreDAO = new ScoreHessianDAO();
        scoreDAO.setReportService(reportService);

        Duration duration = Duration.DAYS;
        Integer groupID = getFleetGroupID();
        List<SpeedPercentItem> list = scoreDAO.getSpeedPercentItems(groupID, duration);
        assertNotNull("Unexpected NULL SpeedPercentList for groupID " + groupID, list);
        assertEquals("Unexpected SpeedPercentList size for groupID " + groupID, duration.getDvqCount(), Integer.valueOf(list.size()));

        long fleetExpectedDailyMileage = 0l;
        for (int teamType = GOOD; teamType <= BAD; teamType++)
            fleetExpectedDailyMileage += expectedDailyMileagePerGroup[teamType];
        long fleetExpectedDailySpeedingMileage = 6 * ReportTestConst.MILES_PER_EVENT; // 6 speeding events per day (1 - intermediate, 5 - bad)

        // System.out.println("EXPECTED distance: " + fleetExpectedDailyMileage + " speeding: " + fleetExpectedDailySpeedingMileage);

        int idx = 0;
        for (SpeedPercentItem item : list) {
            long distance = item.getMiles().longValue();
            long speedingDistance = item.getMilesSpeeding().longValue();
            // System.out.println("distance: " + distance + " speeding: " + speedingDistance);
            assertTrue("speeding distance should not exceed distance ", (speedingDistance <= distance));
            assertEquals(idx + ": Unexpected distance ", fleetExpectedDailyMileage, distance);
            assertEquals(idx + ": Unexpected speeding distance ", fleetExpectedDailySpeedingMileage, speedingDistance);

        }
    }

    // this is currently not working because of the way the back end is determining if a device has an emu that supports idle time 
    @Test
    //@Ignore
    public void idlePercent() {
        ScoreHessianDAO scoreDAO = new ScoreHessianDAO();
        scoreDAO.setReportService(reportService);

        Duration duration = Duration.DAYS;
        Integer groupID = getFleetGroupID();
        List<IdlePercentItem> list = scoreDAO.getIdlePercentItems(groupID, Duration.DAYS);
        assertNotNull("Unexpected NULL SpeedPercentList for groupID " + groupID, list);
        assertEquals("Unexpected SpeedPercentList size for groupID " + groupID, duration.getDvqCount(), Integer.valueOf(list.size()));

        long fleetExpectedDailyDriveTime = DateUtil.convertMillisecondsToSeconds(3l * expectedDailyDriveTime);
        long fleetExpectedDailyIdlingTime = 6 * (ReportTestConst.LO_IDLE_TIME + ReportTestConst.HI_IDLE_TIME); // 6 idling events per day (1 - intermediate, 5 - bad)
//        System.out.println("fleetExpectedDailyDriveTime: " + fleetExpectedDailyDriveTime + " fleetExpectedDailyIdlingTime: " + fleetExpectedDailyIdlingTime);

        for (IdlePercentItem item : list) {
            long driveTime = item.getDrivingTime();
            long idleTime = item.getIdlingTime();

//            System.out.println(item.getDate() + " driveTime: " + driveTime + " idleTime: " + idleTime);
            
            assertEquals("Fleet: Unexpected drive Time ", fleetExpectedDailyDriveTime, driveTime);
            assertEquals("Fleet: Unexpected idle Time ", fleetExpectedDailyIdlingTime, idleTime);
            assertEquals("Fleet: Unexpected vehicles ", 3, item.getNumVehicles().intValue());
            assertEquals("Fleet: Unexpected emu vehicles ", 3, item.getNumEMUVehicles().intValue());

        }
    }

    @Test
     @Ignore
    public void crashSummaryGroup() {
        ScoreHessianDAO scoreDAO = new ScoreHessianDAO();
        scoreDAO.setReportService(reportService);

        for (int teamType = GOOD; teamType <= BAD; teamType++) {
            Integer groupID = getTeamGroupID(teamType);

            // System.out.println("CrashSummary GROUP: " + groupID);
            CrashSummary crashSummary = scoreDAO.getGroupCrashSummaryData(groupID);
            assertEquals(teamType + " DaysSinceLastCrash: ", expectedCrashSummary[teamType].getDaysSinceLastCrash(), crashSummary.getDaysSinceLastCrash());
            assertEquals("TotalCrashes: ", expectedCrashSummary[teamType].getTotalCrashes(), crashSummary.getTotalCrashes());
            assertEquals("CrashesInTimePeriod: ", expectedCrashSummary[teamType].getCrashesInTimePeriod(), crashSummary.getCrashesInTimePeriod());
            assertEquals("TotalMiles: ", (expectedCrashSummary[teamType].getTotalMiles().floatValue() / 100f), crashSummary.getTotalMiles().floatValue(), 1.0);
            assertEquals("MilesSinceLastCrash: ", (expectedCrashSummary[teamType].getMilesSinceLastCrash().floatValue() / 100f), crashSummary.getMilesSinceLastCrash().floatValue(), 1.0);
        }
    }

    @Test
   // @Ignore
    public void crashSummaryDriver() {
        ScoreHessianDAO scoreDAO = new ScoreHessianDAO();
        scoreDAO.setReportService(reportService);

        for (int teamType = GOOD; teamType <= BAD; teamType++)
        {
            Integer driverID = getTeamDriverID(teamType);
            // ScoreableEntity avgScore = scoreDAO.getDriverAverageScoreByType(driverID, Duration.TWELVE, ScoreType.SCORE_OVERALL);
            CrashSummary crashSummary = scoreDAO.getDriverCrashSummaryData(driverID);
            assertEquals(teamType + " DaysSinceLastCrash: ", expectedCrashSummary[teamType].getDaysSinceLastCrash(), crashSummary.getDaysSinceLastCrash());
            assertEquals(teamType + " TotalCrashes: ", expectedCrashSummary[teamType].getTotalCrashes(), crashSummary.getTotalCrashes());
            assertEquals(teamType + " CrashesInTimePeriod: ", expectedCrashSummary[teamType].getCrashesInTimePeriod(), crashSummary.getCrashesInTimePeriod());
            assertEquals(teamType + " TotalMiles: ", expectedCrashSummary[teamType].getTotalMiles().floatValue() / 100f, crashSummary.getTotalMiles().floatValue(), 1.0);
            assertEquals(teamType + " MilesSinceLastCrash: ", (expectedCrashSummary[teamType].getMilesSinceLastCrash().floatValue() / 100f), crashSummary.getMilesSinceLastCrash().floatValue(), 1.0);
        }
    }

    @Test
    // @Ignore
    public void crashSummaryVehicle() {
        ScoreHessianDAO scoreDAO = new ScoreHessianDAO();
        scoreDAO.setReportService(reportService);
        for (int teamType = GOOD; teamType <= BAD; teamType++) {
            Integer vehicleID = getTeamVehicleID(teamType);
            CrashSummary crashSummary = scoreDAO.getVehicleCrashSummaryData(vehicleID);
            assertEquals("DaysSinceLastCrash: ", expectedCrashSummary[teamType].getDaysSinceLastCrash(), crashSummary.getDaysSinceLastCrash());
            assertEquals("TotalCrashes: ", expectedCrashSummary[teamType].getTotalCrashes(), crashSummary.getTotalCrashes());
            assertEquals("CrashesInTimePeriod: ", expectedCrashSummary[teamType].getCrashesInTimePeriod(), crashSummary.getCrashesInTimePeriod());
            assertEquals("TotalMiles: ", expectedCrashSummary[teamType].getTotalMiles().floatValue() / 100f, crashSummary.getTotalMiles().floatValue(), 1.0);
            assertEquals("MilesSinceLastCrash: ", (expectedCrashSummary[teamType].getMilesSinceLastCrash().floatValue() / 100f), crashSummary.getMilesSinceLastCrash().floatValue(), 1.0);
        }
    }

    @Test
    // @Ignore
    public void getSortedDriverScores() {
        // getDVScoresByGT

        ScoreHessianDAO scoreDAO = new ScoreHessianDAO();
        scoreDAO.setReportService(reportService);

        Duration duration = Duration.DAYS;
        for (int teamType = GOOD; teamType <= BAD; teamType++) {
            Integer groupID = getTeamGroupID(teamType);

            List<DriverScore> scoreList = scoreDAO.getSortedDriverScoreList(groupID, duration);
            assertNotNull(scoreList);

            assertEquals("expected one score in list (1 driver per group)", 1, scoreList.size());

            DriverScore driverScore = scoreList.get(0);
            assertEquals("driver score for driver ID: " + driverScore.getDriver().getDriverID(), expectedTeamOverall[teamType], driverScore.getScore());
        }
    }

    @Test
    // @Ignore
    public void driverScores() {

        // getDScoreByDT

        ScoreHessianDAO scoreDAO = new ScoreHessianDAO();
        scoreDAO.setReportService(reportService);

        Duration duration = Duration.DAYS;
        List<ScoreType> overallScoreSubTypes = ScoreType.SCORE_OVERALL.getSubTypes();
        for (int teamType = GOOD; teamType <= BAD; teamType++) {
            Integer driverID = getTeamDriverID(teamType);
            List<ScoreItem> avgItemList = scoreDAO.getAverageScores(driverID, EntityType.ENTITY_DRIVER, duration);
            ScoreItem avgScore = null;
            for (ScoreItem item : avgItemList) {
                if (item.getScoreType().equals(ScoreType.SCORE_OVERALL)) {
                    avgScore = item;
                    break;
                }
            }

            assertNotNull("getDriverAverageScoreByType ", avgScore);
            assertEquals("getDriverAverageScoreByType for driver ID: " + driverID, expectedTeamOverall[teamType], avgScore.getScore());
        }
    }

    @Test
    // @Ignore
    public void driverTrendScores() {

        // getDTrendByDTC

        ScoreHessianDAO scoreDAO = new ScoreHessianDAO();
        scoreDAO.setReportService(reportService);
        Duration duration = Duration.DAYS;
        for (int teamType = GOOD; teamType <= BAD; teamType++) {
            Integer driverID = getTeamDriverID(teamType);
            // List<ScoreableEntity> scoreList = scoreDAO.getDriverTrendCumulative(driverID, duration, ScoreType.SCORE_OVERALL);
            List<TrendItem> scoreList = scoreDAO.getTrendCumulative(driverID, EntityType.ENTITY_DRIVER, duration);

            assertNotNull("getDriverTrendCumulative", scoreList);
            assertEquals("getDriverTrendCumulative size", duration.getDvqCount() * ScoreType.values().length, scoreList.size());

            int idx = 0;
            for (TrendItem score : scoreList) {
                if (score.getScoreType().equals(ScoreType.SCORE_OVERALL)) {
                    int scoreVal = 0;
                    if (score.getScore() != null) {
                        scoreVal = score.getScore().intValue();
                    }
                    int expected = expectedTeamOverall[teamType].intValue();
                    // System.out.println("" + scoreVal);
                    assertTrue((idx++) + ": Unexpected Overall trend score " + scoreVal + " expected: " + expected + " DriverID: " + driverID, (scoreVal >= expected - 1 && scoreVal <= expected + 1));
                }
            }

        }

    }

    @Test
    // @Ignore
    public void driverCoachingTrendScores() {
        // getDTrendByDTC
        ScoreHessianDAO scoreDAO = new ScoreHessianDAO();
        scoreDAO.setReportService(reportService);
        Duration duration = Duration.DAYS;
        for (int teamType = GOOD; teamType <= BAD; teamType++) {
            Integer driverID = getTeamDriverID(teamType);
            List<TrendItem> scoreList = scoreDAO.getTrendScores(driverID, EntityType.ENTITY_DRIVER, duration);

            assertNotNull("getDriverTrendCumulative", scoreList);
            assertEquals("getDriverTrendCumulative size", duration.getDvqCount() * ScoreType.values().length, scoreList.size());

            int idx = 0;
            for (TrendItem score : scoreList) {
                if (score.getScoreType().equals(ScoreType.SCORE_COACHING_EVENTS)) {
                    int scoreVal = 0;
                    if (score.getScore() != null) {
                        scoreVal = score.getScore().intValue();
                    }
                    int expected = expectedDriverCoaching[teamType].intValue();
                    assertTrue((idx++) + ": Unexpected Coaching trend score " + scoreVal + " expected: " + expected + " DriverID: " + driverID, (scoreVal >= expected - 1 && scoreVal <= expected + 1));
                }
            }

        }

    }

    @Test
     // @Ignore
    public void driverMPGScores() {
        // getDTrendByDTC
        MpgHessianDAO mpgDAO = new MpgHessianDAO();
        mpgDAO.setReportService(reportService);

        Duration duration = Duration.DAYS;
        for (int teamType = GOOD; teamType <= BAD; teamType++) {
            Integer driverID = getTeamDriverID(teamType);
            List<MpgEntity> mpgEntityList = mpgDAO.getDriverEntities(driverID, duration, null);
            assertNotNull("getDriverEntities", mpgEntityList);
            assertEquals("getDriverEntities size", duration.getDvqCount(), Integer.valueOf(mpgEntityList.size()));

            for (MpgEntity entity : mpgEntityList) {
                assertEquals("getDriverEntities odometer", expectedDailyMileagePerGroup[teamType], entity.getOdometer());
                assertEquals("getDriverEntities mpgLight", expectedDailyMPGLight[teamType], ((entity.getLightValue() == null) ? Integer.valueOf(0) : entity.getLightValue()));
                assertEquals("getDriverEntities mpgMedium", expectedDailyMPGMedium[teamType], ((entity.getMediumValue() == null) ? Integer.valueOf(0) : entity.getMediumValue()));
                assertEquals("getDriverEntities mpgHeavy", expectedDailyMPGHeavy[teamType], ((entity.getHeavyValue() == null) ? Integer.valueOf(0) : entity.getHeavyValue()));
            }
        }
    }

    @Test
    // @Ignore
    public void vehicleScores() {

        // getVScoreByDT

        ScoreHessianDAO scoreDAO = new ScoreHessianDAO();
        scoreDAO.setReportService(reportService);

        Duration duration = Duration.DAYS;
        List<ScoreType> overallScoreSubTypes = ScoreType.SCORE_OVERALL.getSubTypes();
        for (int teamType = GOOD; teamType <= BAD; teamType++) {
            Integer vehicleID = getTeamVehicleID(teamType);

            List<ScoreItem> avgItemList = scoreDAO.getAverageScores(vehicleID, EntityType.ENTITY_VEHICLE, duration);
            ScoreItem avgScore = null;
            for (ScoreItem item : avgItemList) {
                if (item.getScoreType().equals(ScoreType.SCORE_OVERALL)) {
                    avgScore = item;
                    break;
                }
            }

            assertNotNull("getVehicleScoreBreakdownByType", avgScore);
            assertEquals("getVehicleAverageScoreByType for vehicle ID: " + vehicleID, expectedTeamOverall[teamType], avgScore.getScore());

        }

    }

    @Test
    // @Ignore
    public void vehicleTrendScores() {

        // getDTrendByDTC

        ScoreHessianDAO scoreDAO = new ScoreHessianDAO();
        scoreDAO.setReportService(reportService);
        Duration duration = Duration.DAYS;
        for (int teamType = GOOD; teamType <= BAD; teamType++) {
            Integer vehicleID = getTeamVehicleID(teamType);
            // List<ScoreableEntity> scoreList = scoreDAO.getVehicleTrendCumulative(vehicleID, duration, ScoreType.SCORE_OVERALL);
            List<TrendItem> scoreList = scoreDAO.getTrendCumulative(vehicleID, EntityType.ENTITY_VEHICLE, duration);

            assertNotNull("getVehicleTrendCumulative", scoreList);
            assertEquals("getVehicleTrendCumulative size", duration.getDvqCount() * ScoreType.values().length, scoreList.size());

            int idx = 0;
            for (TrendItem score : scoreList) {
                if (score.getScoreType().equals(ScoreType.SCORE_OVERALL)) {
                    int scoreVal = 0;
                    if (score.getScore() != null) {
                        scoreVal = score.getScore().intValue();
                    }
                    int expected = expectedTeamOverall[teamType].intValue();
                    assertTrue((idx++) + ": Unexpected Overall trend score " + scoreVal + " expected: " + expected + " VehicleID: " + vehicleID, (scoreVal >= expected - 1 && scoreVal <= expected + 1));
                }
            }
        }
    }

    @Test
     // @Ignore
    public void vehicleMPGScores() {
        // getVTrendByDTC
        MpgHessianDAO mpgDAO = new MpgHessianDAO();
        mpgDAO.setReportService(reportService);

        Duration duration = Duration.DAYS;
        for (int teamType = GOOD; teamType <= BAD; teamType++) {
            Integer vehicleID = getTeamVehicleID(teamType);
            List<MpgEntity> mpgEntityList = mpgDAO.getVehicleEntities(vehicleID, duration, null);
            assertNotNull("getVehicleEntities", mpgEntityList);
            assertEquals("getVehicleEntities size", duration.getDvqCount(), Integer.valueOf(mpgEntityList.size()));

            for (MpgEntity entity : mpgEntityList) {
                assertEquals("getVehicleEntities odometer", expectedDailyMileagePerGroup[teamType], entity.getOdometer());
                assertEquals("getVehicleEntities mpgLight", expectedDailyMPGLight[teamType], ((entity.getLightValue() == null) ? Integer.valueOf(0) : entity.getLightValue()));
                assertEquals("getVehicleEntities mpgMedium", expectedDailyMPGMedium[teamType], ((entity.getMediumValue() == null) ? Integer.valueOf(0) : entity.getMediumValue()));
                assertEquals("getVehicleEntities mpgHeavy", expectedDailyMPGHeavy[teamType], ((entity.getHeavyValue() == null) ? Integer.valueOf(0) : entity.getHeavyValue()));
            }
        }
    }

    @Test
    // @Ignore
    public void getVehicleReportData() {
        // getVDScoresByGT
        ScoreHessianDAO scoreDAO = new ScoreHessianDAO();
        scoreDAO.setReportService(reportService);
        Duration duration = Duration.TWELVE;
        for (int teamType = GOOD; teamType <= BAD; teamType++)
        // int teamType = 1;
        {
            Integer groupID = getTeamGroupID(teamType);
            List<VehicleReportItem> list = scoreDAO.getVehicleReportData(groupID, duration);

            assertNotNull("VehicleReportItem list", list);
            assertEquals("VehicleReportItem list size", 1, list.size());

            VehicleReportItem item = list.get(0);
            assertEquals(teamType + " VehicleReportItem miles driven", (expectedDailyMileagePerGroup[teamType] * totalDays) / 100, item.getMilesDriven());
            assertEquals("VehicleReportItem groupID", groupID, item.getGroupID());
            assertEquals("VehicleReportItem overallScore", expectedTeamOverall[teamType], item.getOverallScore());
        }
    }

    @Test
    // @Ignore
    public void getDriverReportData() {
        // getDVScoresByGT

        ScoreHessianDAO scoreDAO = new ScoreHessianDAO();
        scoreDAO.setReportService(reportService);
        Duration duration = Duration.TWELVE;
        for (int teamType = GOOD; teamType <= BAD; teamType++) {
            Integer groupID = getTeamGroupID(teamType);
            List<DriverReportItem> list = scoreDAO.getDriverReportData(groupID, duration);
            assertNotNull("DriverReportItem list", list);
            assertEquals("DriverReportItem list size", 1, list.size());
            DriverReportItem item = list.get(0);
            // assertEquals("DriverReportItem miles driven", Double.valueOf((expectedDailyMileagePerGroup[teamType] * totalDays)/100), item.getMilesDriven().doubleValue(),
            // MILEAGE_BUFFER);
            assertEquals("DriverReportItem miles driven", (expectedDailyMileagePerGroup[teamType] * totalDays) / 100, item.getMilesDriven());
            assertEquals("DriverReportItem groupID", groupID, item.getGroupID());
            assertEquals("DriverReportItem overallScore", expectedTeamOverall[teamType], item.getOverallScore());
        }
    }

    @Test
    // @Ignore
    public void getIdlingReportData() {
        // getDVScoresByGSE

        ScoreHessianDAO scoreDAO = new ScoreHessianDAO();
        scoreDAO.setReportService(reportService);

        int daysBack = 7;
        float expectDailyDriveTimeHrs = (float) (expectedDailyDriveTime * (daysBack - 1)) / 3600000f;
        int endDate = DateUtil.getTodaysDate();
        int startDate = DateUtil.getDaysBackDate(endDate, daysBack, ReportTestConst.TIMEZONE_STR);

        for (int teamType = GOOD; teamType <= BAD; teamType++) {
            Integer groupID = getTeamGroupID(teamType);

            IdlingReportData data = scoreDAO.getIdlingReportData(groupID, DateUtil.convertTimeInSecondsToDate(startDate), DateUtil.convertTimeInSecondsToDate(endDate));
            List<IdlingReportItem> list = data.getItemList();
            assertNotNull("IdlingReportItem list", list);
            assertEquals("IdlingReportItem list size", 1, list.size());
            IdlingReportItem item = list.get(0);
            // System.out.println(" " + item.getDriveTime() + " " + item.getHighHrs() + " " + item.getLowHrs());
            assertEquals("IdlingReportItem groupID", groupID, item.getGroupID());
            assertEquals("IdlingReportItem drive time", expectDailyDriveTimeHrs, item.getDriveTime(), 0.0003);
            float expectDailyLoIdleHrs = (float) (expectedDailyLoIdle[teamType] * (daysBack - 1)) / 3600f;
            float expectDailyHiIdleHrs = (float) (expectedDailyHiIdle[teamType] * (daysBack - 1)) / 3600f;
            assertEquals("IdlingReportItem drive time", expectDailyLoIdleHrs, item.getLowHrs(), 0.0003);
            assertEquals("IdlingReportItem drive time", expectDailyHiIdleHrs, item.getHighHrs(), 0.0003);
        }
    }

    @Test
    // @Ignore
    public void getVehicleTrendDaily() {
        ScoreHessianDAO scoreDAO = new ScoreHessianDAO();
        scoreDAO.setReportService(reportService);
        Duration duration = Duration.DAYS;
        for (int teamType = GOOD; teamType <= BAD; teamType++) {
            Integer vehicleID = getTeamVehicleID(teamType);
            // List<ScoreableEntity> list = scoreDAO.getVehicleTrendDaily(vehicleID, duration, ScoreType.SCORE_OVERALL);
            List<TrendItem> list = scoreDAO.getTrendScores(vehicleID, EntityType.ENTITY_VEHICLE, duration);

            assertNotNull("VehicleTrendDaily list", list);
            assertEquals("VehicleTrendDaily list size", Integer.valueOf(duration.getDvqCount() * ScoreType.values().length), Integer.valueOf(list.size()));
            int idx = 0;
            for (TrendItem item : list) {
                if (item.getScoreType().equals(ScoreType.SCORE_OVERALL)) {
                    int expected = expectedTeamOverall[teamType].intValue();
                    Integer scoreVal = item.getScore();
                    // System.out.println("" + scoreVal);
                    assertNotNull("Unexpected null overall trend score", scoreVal);
                    assertTrue((idx++) + ": Unexpected Overall trend score " + scoreVal + " expected: " + expected + " VehicleID: " + vehicleID, (scoreVal >= expected - 1 && scoreVal <= expected + 1));
                }
            }
        }
    }

    @Test
    // @Ignore
    public void getScoreBreakdownByType() {
        ScoreHessianDAO scoreDAO = new ScoreHessianDAO();
        scoreDAO.setReportService(reportService);
        Duration duration = Duration.DAYS;
        for (int teamType = GOOD; teamType <= BAD; teamType++) {
            Integer groupID = getTeamGroupID(teamType);

            List<ScoreTypeBreakdown> list = scoreDAO.getScoreBreakdownByType(groupID, duration, ScoreType.SCORE_OVERALL);

            assertNotNull("ScoreBreakdownByType list", list);
            assertEquals("ScoreBreakdownByType list size", Integer.valueOf(ScoreType.SCORE_OVERALL.getSubTypes().size()), Integer.valueOf(list.size()));

            for (int subtype = 0; subtype < list.size(); subtype++) {

                int category = 0;
                for (ScoreableEntity entity : list.get(subtype).getPercentageList()) {
                    int expected = expectedScoreBreakdown[teamType][subtype][category++];

                    assertEquals(teamType + " " + subtype + " " + category, Integer.valueOf(expected), entity.getScore());

                }
            }

        }
    }

    @Test
    // @Ignore
    public void teamMPG() {
        // getDVScoresByGT

        MpgHessianDAO mpgDAO = new MpgHessianDAO();
        mpgDAO.setReportService(reportService);

        Duration duration = Duration.DAYS;
        for (int teamType = GOOD; teamType <= BAD; teamType++) {
            Group group = teamGroupData.get(teamType).group;
            List<MpgEntity> list = mpgDAO.getEntities(group, duration);
            assertNotNull("getTeamMPG", list);
            assertEquals("getTeamMPG", Integer.valueOf(1), Integer.valueOf(list.size()));

            MpgEntity entity = list.get(0);
            assertEquals("getEntities entityID", getTeamDriverID(teamType), entity.getEntityID());
            assertEquals("getEntities mpgLight", expectedDailyMPGLight[teamType], ((entity.getLightValue() == null) ? Integer.valueOf(0) : entity.getLightValue()));
            assertEquals("getEntities mpgMedium", expectedDailyMPGMedium[teamType], ((entity.getMediumValue() == null) ? Integer.valueOf(0) : entity.getMediumValue()));
            assertEquals("getEntities mpgHeavy", expectedDailyMPGHeavy[teamType], ((entity.getHeavyValue() == null) ? Integer.valueOf(0) : entity.getHeavyValue()));
        }
    }

    @Test
    // @Ignore
    public void fleetMPG() {
        // getSDScoresByGT
        MpgHessianDAO mpgDAO = new MpgHessianDAO();
        mpgDAO.setReportService(reportService);

        Duration duration = Duration.DAYS;
        List<MpgEntity> list = mpgDAO.getEntities(fleetGroup, duration);
        assertNotNull("get MPG Entities", list);
        assertEquals("get fLEET MPG", Integer.valueOf(1), Integer.valueOf(list.size()));

        MpgEntity entity = list.get(0);
        assertEquals("getEntities entityID", districtGroup.getGroupID(), entity.getEntityID());
        assertEquals("getEntities mpgLight", expectedDailyFleetMPGLight, ((entity.getLightValue() == null) ? Integer.valueOf(0) : entity.getLightValue()), 1);
        assertEquals("getEntities mpgMedium", expectedDailyFleetMPGMedium, ((entity.getMediumValue() == null) ? Integer.valueOf(0) : entity.getMediumValue()));
        assertEquals("getEntities mpgHeavy", expectedDailyFleetMPGHeavy, ((entity.getHeavyValue() == null) ? Integer.valueOf(0) : entity.getHeavyValue()));
    }

    /*
     * 
     * private void dumpScoreableEntity(ScoreableEntity scoreableEntity) { System.out.println(scoreableEntity.getIdentifier() + " " + scoreableEntity.getEntityID() +" " +
     * scoreableEntity.getEntityType().toString() +" " + scoreableEntity.getScore() +" " + scoreableEntity.getScoreType().toString()); }
     */
}

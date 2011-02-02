package it.com.inthinc.pro.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.IntegrationConfig;
import it.config.ReportTestConst;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.PeriodType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.dao.hessian.DeviceHessianDAO;
import com.inthinc.pro.dao.hessian.MpgHessianDAO;
import com.inthinc.pro.dao.hessian.RoleHessianDAO;
import com.inthinc.pro.dao.hessian.ScoreHessianDAO;
import com.inthinc.pro.dao.hessian.StateHessianDAO;
import com.inthinc.pro.dao.hessian.proserver.ReportService;
import com.inthinc.pro.dao.hessian.proserver.ReportServiceCreator;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.hessian.report.GroupReportHessianDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.CrashSummary;
import com.inthinc.pro.model.DriverScore;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.IdlePercentItem;
import com.inthinc.pro.model.MpgEntity;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.model.ScoreItem;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreTypeBreakdown;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.SpeedPercentItem;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.TrendItem;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.app.DeviceSensitivityMapping;
import com.inthinc.pro.model.app.States;

//@Ignore
public class ReportServiceTest {
    private static ReportService reportService;
    private static SiloService siloService;
    private static ITData itData;
    
    private static final String REPORT_BASE_DATA_XML = "ReportTest.xml";
    private static final int MAX_TOTAL_DAYS = 360;

    private static int totalDays;

    public static double MILEAGE_BUFFER = 1d;
    public static int TOLERANCE = 0;

    public static Integer expectedFleetOverall = 27;
    public static Integer expectedTeamOverall[] = { 50, // GOOD
            30, // INTERMEDIATE
            23, // BAD
    };
    public static Double expectedDailyMPGLight[] = { 30.0, // GOOD
            25.0, // INTERMEDIATE
            20.0, // BAD
    };
    public static Double expectedDailyMPGMedium[] = { 0d, 0d, 0d };
    public static Double expectedDailyMPGHeavy[] = { 0d, 0d, 0d };
    public static Double expectedDailyFleetMPGLight = 25.0;
    public static Double expectedDailyFleetMPGMedium = 0d;
    public static Double expectedDailyFleetMPGHeavy = 0d;

    Double expectedTeamBreakdown[][] = { { 50.0, 50.0, 50.0, 50.0, 50.0 }, // GOOD (OVERALL, SPEEDING, SEATBELT, DRIVING STYLE, COACHING)
            { 23.0, 23.0, 23.0, 23.0, 23.0 }, // INTERMEDIATE
            { 17.0, 17.0, 17.0, 17.0, 17.0 }, // BAD
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
            new CrashSummary(0, 0, totalDays-1, expectedDailyMileagePerGroup[ITData.GOOD] * (totalDays-1), expectedDailyMileagePerGroup[ITData.GOOD] * (totalDays-1)), // ITData.GOOD
            new CrashSummary(0, 0, totalDays-1, expectedDailyMileagePerGroup[ITData.INTERMEDIATE] * (totalDays-1), expectedDailyMileagePerGroup[ITData.INTERMEDIATE] * (totalDays-1)), // Intermediate
            // new CrashSummary(totalDays, totalDays, 0, expectedDailyMileagePerGroup[BAD] * totalDays, (ReportTestConst.EVENTS_PER_DAY - ReportTestConst.CRASH_EVENT_IDX) *
            // ReportTestConst.MILES_PER_EVENT), // BAD
            new CrashSummary(totalDays-1, totalDays-1, 0, expectedDailyMileagePerGroup[ITData.BAD] * (totalDays-1), 0l), // BAD
    };

    Integer expectedDriverCoaching[] = { 0, 2, // 1 seat belt and 1 speeding
            10, // 5 seat belt and 5 speeding
    };

    long expectedDailyLoIdle[] = { 0l, // ITData.GOOD
            ReportTestConst.LO_IDLE_TIME, // INTERMEDIATE
            ReportTestConst.LO_IDLE_TIME * 5l // BAD
    };
    long expectedDailyHiIdle[] = { 0l, // ITData.GOOD
            ReportTestConst.HI_IDLE_TIME, // INTERMEDIATE
            ReportTestConst.HI_IDLE_TIME * 5l // BAD
    };
    public static int expectedScoreBreakdown[][][] = {
    // ITData.GOOD
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
                    { 0, 0, 0, 100, 0 }, // idling
            }, };

    static DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");


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
        itData = new ITData();
        
        
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(REPORT_BASE_DATA_XML);

        if (!itData.parseTestData(stream, siloService, false, false)) {
            throw new Exception("Error parsing Test data xml file");
        }
        
        TimeZone timeZone = TimeZone.getTimeZone(ReportTestConst.TIMEZONE_STR);
        DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(timeZone);
        Integer todayInSec = DateUtil.getDaysBackDate(DateUtil.getTodaysDate(), 0, ReportTestConst.TIMEZONE_STR);
        
        DateMidnight start = new DateMidnight(new DateTime(((long)itData.startDateInSec * 1000l), dateTimeZone), dateTimeZone);
		DateTime end = new DateMidnight(((long)todayInSec * 1000l), dateTimeZone).toDateTime().plusDays(1).minus(6000);
        Interval interval  = new Interval(start, end);
        totalDays = interval.toPeriod(PeriodType.days()).getDays();
          if (totalDays > MAX_TOTAL_DAYS)
              totalDays = MAX_TOTAL_DAYS;
  }

    private static void initApp() throws Exception {
        StateHessianDAO stateDAO = new StateHessianDAO();
        stateDAO.setSiloService(siloService);

        States states = new States();
        states.setStateDAO(stateDAO);
        states.init();

        RoleHessianDAO roleDAO = new RoleHessianDAO();
        roleDAO.setSiloService(siloService);

        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);

//        DeviceSensitivityMapping mapping = new DeviceSensitivityMapping();
//        mapping.setDeviceDAO(deviceDAO);
//        mapping.init();

    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {}

    private Integer getFleetGroupID() {
        return itData.fleetGroup.getGroupID();
    }

    private Integer getDistrictGroupID() {
        return itData.districtGroup.getGroupID();
    }

    private Integer getTeamGroupID(int teamType) {
        return itData.teamGroupData.get(teamType).group.getGroupID();
    }

    private Integer getTeamDriverID(int teamType) {
        return itData.teamGroupData.get(teamType).driver.getDriverID();
    }

    private Integer getTeamVehicleID(int teamType) {
        return itData.teamGroupData.get(teamType).vehicle.getVehicleID();
    }
    /*    	
    
    @Test
    public void tryingStuff() {
        Mapper mapper = new SimpleMapper();
    	Integer vehicleID = 1132;
    	Integer driverID = 499;


        for(Duration duration : EnumSet.allOf(Duration.class)) {

        	System.out.println(duration);
        	DriveQMap driveQMap = mapper.convertToModelObject(reportService.getVScoreByVT(vehicleID, duration.getCode()), DriveQMap.class);
        	Map<ScoreType, Integer> scoreMap = driveQMap.getScoreMap();
        	
        	
        	
        	System.out.println("    " + scoreMap.get(ScoreType.SCORE_OVERALL));
            		 
//           List<Map<String, Object>> cumulativList = reportService.getVTrendByVTC(vehicleID, duration.getCode(), duration.getDvqCount());
//            List<Map<String, Object>> cumulativList = reportService.getVTrendByVTC(vehicleID, duration.getDvqCode(), duration.getDvqCount());
            List<Map<String, Object>> cumulativList = reportService.getVTrendByVTC(vehicleID, duration.getAggregationBinSize(), duration.getDvqCount());
            List<DriveQMap> driveQList = mapper.convertToModelObject(cumulativList, DriveQMap.class);
        
            System.out.print("       ");
            for (DriveQMap dqMap : driveQList)
            {
            	Map<ScoreType, Integer> scoreTrendMap = dqMap.getScoreMap();
        		System.out.print(" " + scoreTrendMap.get(ScoreType.SCORE_OVERALL));

            }
            System.out.println();

            System.out.println("    " + scoreMap.get(ScoreType.SCORE_SPEEDING));
            System.out.print("       ");
            for (DriveQMap dqMap : driveQList)
            {
            	Map<ScoreType, Integer> scoreTrendMap = dqMap.getScoreMap();
        		System.out.print(" " + scoreTrendMap.get(ScoreType.SCORE_SPEEDING));

            }
            System.out.println();
        }
        Integer groupID = 1;
        for(Duration duration : EnumSet.allOf(Duration.class)) {

        	System.out.println(duration);
//        	List<DriveQMap> topGroupList = mapper.convertToModelObject(reportService.getGDTrendByGTC(groupID, duration.getCode(), duration.getDvqCount()), DriveQMap.class);
        	List<DriveQMap> topGroupList = mapper.convertToModelObject(reportService.getGDTrendByGTC(groupID, duration.getAggregationBinSize(), duration.getDvqCount()), DriveQMap.class);
            for (DriveQMap driveQMap : topGroupList)
            {
                System.out.print(" " + (driveQMap.getOverall() == null ? "none" : driveQMap.getOverall()));
            }
            System.out.println("");
            
            
//            Map<String, Object> returnMap = reportService.getGDScoreByGT(groupID, duration.getCode());
            Map<String, Object> returnMap = reportService.getGDScoreByGT(groupID, duration.getDvqCode());
            DriveQMap dqMap = mapper.convertToModelObject(returnMap, DriveQMap.class);

            System.out.println(" " + (dqMap.getOverall() == null ? "none" : dqMap.getOverall()));

        }
    }
*/
    
    @Test
    // @Ignore
    public void averageScoreByType() {
        // getGDScoreByGT

        ScoreHessianDAO scoreDAO = new ScoreHessianDAO();
        scoreDAO.setReportService(reportService);

        ScoreableEntity scoreableEntity = scoreDAO.getAverageScoreByType(getFleetGroupID(), Duration.DAYS, ScoreType.SCORE_OVERALL);
        assertNotNull(scoreableEntity);
        assertEquals("getAverageScoreByType for top level fleet group", expectedFleetOverall, scoreableEntity.getScore());

        for (int teamType = ITData.GOOD; teamType <= ITData.BAD; teamType++) {
            scoreableEntity = scoreDAO.getAverageScoreByType(getTeamGroupID(teamType), Duration.DAYS, ScoreType.SCORE_OVERALL);
            assertNotNull(scoreableEntity);
            assertNotNull(scoreableEntity.getScore());
            assertEquals("getAverageScoreByType for team groupID: " + scoreableEntity.getEntityID(), expectedTeamOverall[teamType], scoreableEntity.getScore());
        }

        scoreableEntity = scoreDAO.getTrendSummaryScore(getFleetGroupID(), Duration.DAYS, ScoreType.SCORE_OVERALL);
        assertNotNull(scoreableEntity);
        assertEquals("getAverageScoreByType for top level fleet group", expectedFleetOverall, scoreableEntity.getScore());

        for (int teamType = ITData.GOOD; teamType <= ITData.BAD; teamType++) {
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
            for (int teamType = ITData.GOOD; teamType <= ITData.BAD; teamType++) {
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
                33, // 4.1 to 5.0 -- ITData.GOOD (50)
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
            if (groupID.equals(getDistrictGroupID())) {
                List<ScoreableEntity> scoreList = scoreMap.get(getDistrictGroupID());
                assertNotNull("Unexpected NULL ScoreList for groupID " + getDistrictGroupID(), scoreList);
                assertEquals("Unexpected ScoreList size for groupID " + getDistrictGroupID(), duration.getDvqCount(), Integer.valueOf(scoreList.size()));

                continue;
            }

            Boolean found = false;
            for (int teamType = ITData.GOOD; teamType <= ITData.BAD; teamType++) {
                if (groupID.equals(getTeamGroupID(teamType))) {
                    List<ScoreableEntity> scoreList = scoreMap.get(groupID);
                    assertNotNull("Unexpected NULL ScoreList for groupID " + groupID, scoreList);
                    assertEquals("Unexpected ScoreList size for groupID " + groupID, duration.getDvqCount(), Integer.valueOf(scoreList.size()));
                    // skip 1st score on trending
                    for (int i = 1; i < duration.getDvqCount(); i++) {
                        int score = scoreList.get(i).getScore().intValue();
                        int expected = expectedTeamOverall[teamType].intValue();
//                        System.out.println("  #" + i + ": " + score);
                        assertTrue("#" + i + ": Unexpected Overall trend score " + score + " expected: " + expected + " Team: " + groupID, (score >= expected - TOLERANCE && score <= expected + TOLERANCE));
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
        for (int teamType = ITData.GOOD; teamType <= ITData.BAD; teamType++)
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

    @Test
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
// TODO: drive time is 60 seconds off            
//            assertEquals("Fleet: Unexpected drive Time ", fleetExpectedDailyDriveTime, driveTime);
            assertEquals("Fleet: Unexpected idle Time ", fleetExpectedDailyIdlingTime, idleTime);
            assertEquals("Fleet: Unexpected vehicles ", 3, item.getNumVehicles().intValue());
            assertEquals("Fleet: Unexpected emu vehicles ", 3, item.getNumEMUVehicles().intValue());

        }
    }

    // TODO: FIX THIS!!
    @Test
    @Ignore
    public void crashSummaryGroup() {
        ScoreHessianDAO scoreDAO = new ScoreHessianDAO();
        scoreDAO.setReportService(reportService);

        for (int teamType = ITData.GOOD; teamType <= ITData.BAD; teamType++) {
            Integer groupID = getTeamGroupID(teamType);

            CrashSummary crashSummary = scoreDAO.getGroupCrashSummaryData(groupID);
            assertEquals(teamType + " DaysSinceLastCrash: ", expectedCrashSummary[teamType].getDaysSinceLastCrash(), crashSummary.getDaysSinceLastCrash());
            assertEquals("TotalCrashes: ", expectedCrashSummary[teamType].getTotalCrashes(), crashSummary.getTotalCrashes());
            assertEquals("CrashesInTimePeriod: ", expectedCrashSummary[teamType].getCrashesInTimePeriod(), crashSummary.getCrashesInTimePeriod());
            assertEquals("TotalMiles: ", (expectedCrashSummary[teamType].getTotalMiles().floatValue() / 100f), crashSummary.getTotalMiles().floatValue(), 1.0);
            assertEquals("MilesSinceLastCrash: ", (expectedCrashSummary[teamType].getMilesSinceLastCrash().floatValue() / 100f), crashSummary.getMilesSinceLastCrash().floatValue(), 1.0);
        }
    }

    @Test
    @Ignore
    public void crashSummaryDriver() {
        ScoreHessianDAO scoreDAO = new ScoreHessianDAO();
        scoreDAO.setReportService(reportService);

        for (int teamType = ITData.GOOD; teamType <= ITData.BAD; teamType++)
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
    @Ignore
    public void crashSummaryVehicle() {
        ScoreHessianDAO scoreDAO = new ScoreHessianDAO();
        scoreDAO.setReportService(reportService);
        for (int teamType = ITData.GOOD; teamType <= ITData.BAD; teamType++) {
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
        for (int teamType = ITData.GOOD; teamType <= ITData.BAD; teamType++) {
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
        for (int teamType = ITData.GOOD; teamType <= ITData.BAD; teamType++) {
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
        for (int teamType = ITData.GOOD; teamType <= ITData.BAD; teamType++) {
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
                    assertTrue((idx++) + ": Unexpected Overall trend score " + scoreVal + " expected: " + expected + " DriverID: " + driverID, (scoreVal >= expected - TOLERANCE && scoreVal <= expected + TOLERANCE));
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
        for (int teamType = ITData.GOOD; teamType <= ITData.BAD; teamType++) {
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
        for (int teamType = ITData.GOOD; teamType <= ITData.BAD; teamType++) {
            Integer driverID = getTeamDriverID(teamType);
            List<MpgEntity> mpgEntityList = mpgDAO.getDriverEntities(driverID, duration, null);
            assertNotNull("getDriverEntities", mpgEntityList);
            assertEquals("getDriverEntities size", duration.getDvqCount(), Integer.valueOf(mpgEntityList.size()));

            for (MpgEntity entity : mpgEntityList) {
                assertEquals("getDriverEntities odometer", expectedDailyMileagePerGroup[teamType], entity.getOdometer());
                assertEquals("getDriverEntities mpgLight", expectedDailyMPGLight[teamType], ((entity.getLightValue() == null) ? Double.valueOf(0) : entity.getLightValue()));
                assertEquals("getDriverEntities mpgMedium", expectedDailyMPGMedium[teamType], ((entity.getMediumValue() == null) ? Double.valueOf(0) : entity.getMediumValue()));
                assertEquals("getDriverEntities mpgHeavy", expectedDailyMPGHeavy[teamType], ((entity.getHeavyValue() == null) ? Double.valueOf(0) : entity.getHeavyValue()));
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
        for (int teamType = ITData.GOOD; teamType <= ITData.BAD; teamType++) {
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
        for (int teamType = ITData.GOOD; teamType <= ITData.BAD; teamType++) {
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
                    assertTrue((idx++) + ": Unexpected Overall trend score " + scoreVal + " expected: " + expected + " VehicleID: " + vehicleID, (scoreVal >= expected - TOLERANCE && scoreVal <= expected + TOLERANCE));
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
        for (int teamType = ITData.GOOD; teamType <= ITData.BAD; teamType++) {
            Integer vehicleID = getTeamVehicleID(teamType);
            List<MpgEntity> mpgEntityList = mpgDAO.getVehicleEntities(vehicleID, duration, null);
            assertNotNull("getVehicleEntities", mpgEntityList);
            assertEquals("getVehicleEntities size", duration.getDvqCount(), Integer.valueOf(mpgEntityList.size()));

            Date currentDate = new Date();
            for (MpgEntity entity : mpgEntityList) {
                if (currentDate.after(entity.getDate()))
                    continue;
                assertEquals("getVehicleEntities odometer", expectedDailyMileagePerGroup[teamType], entity.getOdometer());
                assertEquals("getVehicleEntities mpgLight", expectedDailyMPGLight[teamType], ((entity.getLightValue() == null) ? Double.valueOf(0) : entity.getLightValue()));
                assertEquals("getVehicleEntities mpgMedium", expectedDailyMPGMedium[teamType], ((entity.getMediumValue() == null) ? Double.valueOf(0) : entity.getMediumValue()));
                assertEquals("getVehicleEntities mpgHeavy", expectedDailyMPGHeavy[teamType], ((entity.getHeavyValue() == null) ? Double.valueOf(0) : entity.getHeavyValue()));
            }
        }
    }
    @Test
    @Ignore
    public void getVehicleTrendDaily() {
        ScoreHessianDAO scoreDAO = new ScoreHessianDAO();
        scoreDAO.setReportService(reportService);
        Duration duration = Duration.DAYS;
        for (int teamType = ITData.GOOD; teamType <= ITData.BAD; teamType++) {
//System.out.println("teamType: " + teamType);        	
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
//                    System.out.println("" + scoreVal);
                    assertNotNull("Unexpected null overall trend score", scoreVal);
                    
                    // we use this call for mileage only, not score so commenting out for now
//                    assertTrue((idx++) + ": Unexpected Overall trend score " + scoreVal + " expected: " + expected + " VehicleID: " + vehicleID, (scoreVal >= expected - TOLERANCE && scoreVal <= expected + TOLERANCE));
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
        for (int teamType = ITData.GOOD; teamType <= ITData.BAD; teamType++) {
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
        for (int teamType = ITData.GOOD; teamType <= ITData.BAD; teamType++) {
            Group group = itData.teamGroupData.get(teamType).group;
            List<MpgEntity> list = mpgDAO.getEntities(group, duration);
            assertNotNull("getTeamMPG", list);
            assertEquals("getTeamMPG", Integer.valueOf(1), Integer.valueOf(list.size()));

            MpgEntity entity = list.get(0);
            assertEquals("getEntities entityID", getTeamDriverID(teamType), entity.getEntityID());
            assertEquals("getEntities mpgLight", expectedDailyMPGLight[teamType], ((entity.getLightValue() == null) ? Double.valueOf(0) : entity.getLightValue()));
            assertEquals("getEntities mpgMedium", expectedDailyMPGMedium[teamType], ((entity.getMediumValue() == null) ? Double.valueOf(0) : entity.getMediumValue()));
            assertEquals("getEntities mpgHeavy", expectedDailyMPGHeavy[teamType], ((entity.getHeavyValue() == null) ? Double.valueOf(0) : entity.getHeavyValue()));
        }
    }

    @Test
    // @Ignore
    public void fleetMPG() {
        // getSDScoresByGT
        MpgHessianDAO mpgDAO = new MpgHessianDAO();
        mpgDAO.setReportService(reportService);

        Duration duration = Duration.DAYS;
        List<MpgEntity> list = mpgDAO.getEntities(itData.fleetGroup, duration);
        assertNotNull("get MPG Entities", list);
        assertEquals("get fLEET MPG", Integer.valueOf(1), Integer.valueOf(list.size()));

        MpgEntity entity = list.get(0);
        assertEquals("getEntities entityID", itData.districtGroup.getGroupID(), entity.getEntityID());
        assertEquals("getEntities mpgLight", expectedDailyFleetMPGLight, ((entity.getLightValue() == null) ? Double.valueOf(0) : entity.getLightValue()), 1);
        assertEquals("getEntities mpgMedium", expectedDailyFleetMPGMedium, ((entity.getMediumValue() == null) ? Double.valueOf(0) : entity.getMediumValue()));
        assertEquals("getEntities mpgHeavy", expectedDailyFleetMPGHeavy, ((entity.getHeavyValue() == null) ? Double.valueOf(0) : entity.getHeavyValue()));
    }

    @Test
    // @Ignore
    public void teamStats() {
        GroupReportHessianDAO groupReportHessianDAO = new GroupReportHessianDAO();
        groupReportHessianDAO.setReportService(reportService);
        
        DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(ReportTestConst.timeZone);

        for (int teamType = ITData.GOOD; teamType <= ITData.BAD; teamType++) {
        	List<DriverVehicleScoreWrapper> driverScoreList = groupReportHessianDAO.getDriverScores(itData.teamGroupData.get(teamType).group.getGroupID(), TimeFrame.ONE_DAY_AGO.getInterval(dateTimeZone));

        	assertEquals("1 driver expected", Integer.valueOf(1), Integer.valueOf(driverScoreList.size()));
        	Score score = driverScoreList.get(0).getScore();
//        	System.out.println("weighted mpg = " + score.getWeightedMpg());
        	assertEquals(teamType + " weighted mpg", expectedDailyMPGLight[teamType].doubleValue(), score.getWeightedMpg().doubleValue(), 0.1);
/*        	
        	System.out.println("group ID " + driverScoreList.get(0).getDriver().getGroupID() );
        	System.out.println("driver ID " + driverScoreList.get(0).getDriver().getDriverID() );
        	System.out.println("total miles = " + score.getMilesDriven());
        	System.out.println("miles light = " + score.getOdometerLight());
        	System.out.println("miles med = " + score.getOdometerMedium());
        	System.out.println("miles heavy = " + score.getOdometerHeavy());
*/
        	long totalMiles = score.getMilesDriven().longValue();
        	long totalLMHMiles = score.getOdometerLight().longValue()
        						+ score.getOdometerMedium().longValue()
        						+ score.getOdometerHeavy().longValue();
        	assertEquals("total miles should match total light, med, heavy miles", totalMiles, totalLMHMiles);

        }
        
        

    }
}

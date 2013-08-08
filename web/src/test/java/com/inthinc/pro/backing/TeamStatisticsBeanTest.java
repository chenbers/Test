package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.mock.data.UnitTestStats;
import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.model.AggregationDuration;
import com.inthinc.pro.model.CrashSummary;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.model.DriverScore;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.IdlePercentItem;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.ScoreItem;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreTypeBreakdown;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.SpeedPercentItem;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.TrendItem;
import com.inthinc.pro.model.VehicleReportItem;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.GroupScoreWrapper;
import com.inthinc.pro.model.aggregation.GroupTrendWrapper;
import com.inthinc.pro.model.aggregation.Percentage;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.security.userdetails.ProUser;

public class TeamStatisticsBeanTest extends BaseBeanTest  {

    private static final Integer TEAM_GROUP_ID = 1;
    private static final Integer TEAM_PARENT_GROUP_ID = 1;
    private static final Integer DRIVER_ID = 1;
    private static final Integer ACCOUNT_ID = 1;
    private static final Integer SCORE_OVERALL = 45;
    
    private static final Integer SCOREABLE_ENTITY_SCORE_DEFAULT = 25;
    private static final Integer SCOREABLE_ENTITY_SCORE_TODAY = 28;
    private static final Integer SCOREABLE_ENTITY_SCORE_ONE_DAY_AGO = 29;
    private static final Integer SCOREABLE_ENTITY_SCORE_TWO_DAY_AGO = 30;
    private static final Integer SCOREABLE_ENTITY_SCORE_THREE_DAY_AGO = 31;
    private static final Integer SCOREABLE_ENTITY_SCORE_FOUR_DAY_AGO = 32;
    private static final Integer SCOREABLE_ENTITY_SCORE_FIVE_DAY_AGO = 33;
    
    private static final Integer SCOREABLE_ENTITY_SCORE_MONTH = 45;
    
    private static final String PERSON_FIRST_NAME = "PfirstName";
    private static final String PERSON_LAST_NAME = "PlastName";
    
    private static final String GROUP_NAME = "Test_Group";
    
    // Scorable Entity list switch
    private enum SEListSwitch {
        DAY_30, // 30 day list
        MONTH,  // Month List
        EMPTY,  // Empty List
        NULL    // Null ;
    }
    
    private SEListSwitch listSwitch;
    
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }

    @Test
    public void spring()
    {
        // fleet manager level
        ProUser pu = loginUser(UnitTestStats.UNIT_TEST_LOGIN);
        Integer grpID = pu.getUser().getGroupID();
     
        TeamStatisticsBean bean = (TeamStatisticsBean)applicationContext.getBean("teamStatisticsBean");
    
        // Check the injected beans
        assertNotNull(bean.getTeamCommonBean());
        assertNotNull(bean.getGroupReportDAO());
        assertNotNull(bean.getReportRenderer());
        assertNotNull(bean.getReportCriteriaService());
        assertNotNull(bean.getAccountDAO());
        assertNotNull(bean.getGroupReportDAO());
   
        // Timeframe initialized correctly?
        TeamCommonBean tcb = bean.getTeamCommonBean();
        tcb.setTimeFrame(TimeFrame.ONE_DAY_AGO);
        assertEquals(tcb.getTimeFrame().name(),"ONE_DAY_AGO");

        // Load "ONE_DAY_AGO" data?
        tcb.setGroupID(grpID);
        List<DriverVehicleScoreWrapper> ds = bean.getDriverStatistics();
        assertNotNull(ds);
        
        // Did it get cached?
        Map<String,List<DriverVehicleScoreWrapper>> cr = tcb.getCachedResults();
        assertEquals(cr.containsKey("ONE_DAY_AGO"),Boolean.TRUE.booleanValue());
            
        // Fake some driver data
        List<DriverVehicleScoreWrapper> lDvsw = new ArrayList<DriverVehicleScoreWrapper>();        
        
        Score s = new Score();
        s.setOverall(43);
        s.setTrips(4);
        s.setCrashEvents(7);
        s.setStartingOdometer(23);
        s.setEndingOdometer(142);
        s.setDriveTime(43);
        s.setIdleHi(63);
        s.setIdleLo(22);
        s.setAggressiveAccelEvents(4);
        s.setAggressiveBrakeEvents(2);
        s.setAggressiveBumpEvents(1);
        s.setAggressiveLeftEvents(7);
        s.setAggressiveRightEvents(9);
        s.setSeatbeltEvents(15);
        s.setSpeedEvents(11);
        s.setIdleHiEvents(4);
        s.setBackingTime(80);
        s.setBackingEvents(1);
        
        DriverVehicleScoreWrapper dvsw = new DriverVehicleScoreWrapper();        
        dvsw.setScore(s);
        lDvsw.add(dvsw);
        
        s = new Score();
        s.setOverall(32);
        s.setTrips(1);
        s.setCrashEvents(5);
        s.setStartingOdometer(4);
        s.setEndingOdometer(221);
        s.setDriveTime(111);
        s.setIdleHi(44);
        s.setIdleLo(42);
        s.setAggressiveAccelEvents(14);
        s.setAggressiveBrakeEvents(21);
        s.setAggressiveBumpEvents(11);
        s.setAggressiveLeftEvents(17);
        s.setAggressiveRightEvents(19);
        s.setSeatbeltEvents(5);
        s.setSpeedEvents(1);   
        s.setIdleHiEvents(4);
        s.setBackingTime(60);
        s.setBackingEvents(2);
        
        dvsw = new DriverVehicleScoreWrapper();
        dvsw.setScore(s);
        lDvsw.add(dvsw);
                
        // Set the fake data into the common bean cache
        bean.getTeamCommonBean().getCachedResults().put("TWO_DAYS_AGO", lDvsw);
        bean.getTeamCommonBean().setTimeFrame(TimeFrame.TWO_DAYS_AGO);
        
        // Acquire the totals
        List<DriverVehicleScoreWrapper> lTotals = bean.getDriverTotals();      
        
        // Load the data and see if the code gets the correct totals
        assertEquals(lTotals.get(0).getScore().getTrips(),5);   
        assertEquals(lTotals.get(0).getScore().getCrashEvents(),12);

//		removed bmiller 08/21/2012
//        assertEquals(lTotals.get(0).getScore().getEndingOdometer(),336);
  
  assertEquals(lTotals.get(0).getScore().getDriveTime(),154);
        assertEquals(lTotals.get(0).getScore().getIdleHi(),107);
        assertEquals(lTotals.get(0).getScore().getIdleLo(),64);
        assertEquals(lTotals.get(0).getScore().getSafetyTotal().intValue(),140);
        assertEquals(lTotals.get(0).getScore().getBackingTime(),140);
        assertEquals(lTotals.get(0).getScore().getBackingEvents(),3);
    }  
    
    @Test
    public void testGetDriverTotals() {
     
        // fleet manager level
        ProUser pu = loginUser(UnitTestStats.UNIT_TEST_LOGIN);
        
        //pacify compiler
        pu.getClass();
        
        TeamStatisticsBean bean = new TeamStatisticsBean();
        bean.setGroupReportDAO(new MockGroupReportDAO());
        
        MockScoreDAO mockScoreDAO = new MockScoreDAO();
        bean.setScoreDAO(mockScoreDAO);
        
        TeamCommonBean commonBean = new TeamCommonBean();
        
        commonBean.setGroupID(TEAM_GROUP_ID);
        commonBean.setGroup(getGroup());
       
        bean.setTeamCommonBean(commonBean);
        
        // Test Today
        commonBean.setTimeFrame(TimeFrame.TODAY);
        this.listSwitch = SEListSwitch.DAY_30;
        // clear the cache
        bean.getTeamCommonBean().getCachedTrendResults().clear();
        List<DriverVehicleScoreWrapper> dvswList = bean.getDriverTotals();
        assertNotNull(dvswList);
        assertEquals(1, dvswList.size());
        assertNotNull(dvswList.get(0).getScore());
        assertNotNull(dvswList.get(0).getScore().getOverall());
        assertEquals(SCOREABLE_ENTITY_SCORE_TODAY, dvswList.get(0).getScore().getOverall());
        
        //Test One day ago
        commonBean.setTimeFrame(TimeFrame.ONE_DAY_AGO);
        this.listSwitch = SEListSwitch.DAY_30;
        // clear the cache
        bean.getTeamCommonBean().getCachedTrendResults().clear();
        dvswList = bean.getDriverTotals();
        assertNotNull(dvswList);
        assertEquals(1, dvswList.size());
        assertNotNull(dvswList.get(0).getScore());
        assertNotNull(dvswList.get(0).getScore().getOverall());
        assertEquals(SCOREABLE_ENTITY_SCORE_ONE_DAY_AGO, dvswList.get(0).getScore().getOverall());
        
        //Test empty scorable entity list
        commonBean.setTimeFrame(TimeFrame.ONE_DAY_AGO);
        this.listSwitch = SEListSwitch.EMPTY;
        // clear the cache
        bean.getTeamCommonBean().getCachedTrendResults().clear();
        dvswList = bean.getDriverTotals();
        assertNotNull(dvswList);
        assertEquals(1, dvswList.size());
        assertNotNull(dvswList.get(0).getScore());
        assertNotNull(dvswList.get(0).getScore().getOverall());
        assertEquals(SCORE_OVERALL, dvswList.get(0).getScore().getOverall());
        
       //Test null scorable entity list
        commonBean.setTimeFrame(TimeFrame.ONE_DAY_AGO);
        this.listSwitch = SEListSwitch.NULL;
        // clear the cache
        bean.getTeamCommonBean().getCachedTrendResults().clear();
        dvswList = bean.getDriverTotals();
        assertNotNull(dvswList);
        assertEquals(1, dvswList.size());
        assertNotNull(dvswList.get(0).getScore());
        assertNotNull(dvswList.get(0).getScore().getOverall());
        assertEquals(SCORE_OVERALL, dvswList.get(0).getScore().getOverall());
        
        //Test Two day ago
        commonBean.setTimeFrame(TimeFrame.TWO_DAYS_AGO);
        this.listSwitch = SEListSwitch.DAY_30;
        // clear the cache
        bean.getTeamCommonBean().getCachedTrendResults().clear();
        dvswList = bean.getDriverTotals();
        assertNotNull(dvswList);
        assertEquals(1, dvswList.size());
        assertNotNull(dvswList.get(0).getScore());
        assertNotNull(dvswList.get(0).getScore().getOverall());
        assertEquals(SCOREABLE_ENTITY_SCORE_TWO_DAY_AGO, dvswList.get(0).getScore().getOverall());
        
        //Test Three day ago
        commonBean.setTimeFrame(TimeFrame.THREE_DAYS_AGO);
        this.listSwitch = SEListSwitch.DAY_30;
        // clear the cache
        bean.getTeamCommonBean().getCachedTrendResults().clear();
        dvswList = bean.getDriverTotals();
        assertNotNull(dvswList);
        assertEquals(1, dvswList.size());
        assertNotNull(dvswList.get(0).getScore());
        assertNotNull(dvswList.get(0).getScore().getOverall());
        assertEquals(SCOREABLE_ENTITY_SCORE_THREE_DAY_AGO, dvswList.get(0).getScore().getOverall());
        
        //Test Four day ago
        commonBean.setTimeFrame(TimeFrame.FOUR_DAYS_AGO);
        this.listSwitch = SEListSwitch.DAY_30;
        // clear the cache
        bean.getTeamCommonBean().getCachedTrendResults().clear();
        dvswList = bean.getDriverTotals();
        assertNotNull(dvswList);
        assertEquals(1, dvswList.size());
        assertNotNull(dvswList.get(0).getScore());
        assertNotNull(dvswList.get(0).getScore().getOverall());
        assertEquals(SCOREABLE_ENTITY_SCORE_FOUR_DAY_AGO, dvswList.get(0).getScore().getOverall());
       
        //Test Five day ago
        commonBean.setTimeFrame(TimeFrame.FIVE_DAYS_AGO);
        this.listSwitch = SEListSwitch.DAY_30;
        // clear the cache
        bean.getTeamCommonBean().getCachedTrendResults().clear();
        dvswList = bean.getDriverTotals();
        assertNotNull(dvswList);
        assertEquals(1, dvswList.size());
        assertNotNull(dvswList.get(0).getScore());
        assertNotNull(dvswList.get(0).getScore().getOverall());
        assertEquals(SCOREABLE_ENTITY_SCORE_FIVE_DAY_AGO, dvswList.get(0).getScore().getOverall());
        
        //Test Month
        commonBean.setTimeFrame(TimeFrame.MONTH);
        this.listSwitch = SEListSwitch.MONTH;
        // clear the cache
        bean.getTeamCommonBean().getCachedTrendResults().clear();
        dvswList = bean.getDriverTotals();
        assertNotNull(dvswList);
        assertEquals(1, dvswList.size());
        assertNotNull(dvswList.get(0).getScore());
        assertNotNull(dvswList.get(0).getScore().getOverall());
        assertEquals(SCOREABLE_ENTITY_SCORE_MONTH, dvswList.get(0).getScore().getOverall());
    }
    
    private class MockGroupReportDAO implements GroupReportDAO {

        @Override
        public Score getAggregateDriverScore(Integer groupID, AggregationDuration duration, GroupHierarchy gh) {
            return null;
        }

        @Override
        public Score getAggregateDriverScore(Integer groupID, Interval interval, GroupHierarchy gh) {
            return null;
        }

        @Override
        public Score getAggregateDriverScore(Integer groupID, DateTime startTime, DateTime endTime, GroupHierarchy gh) {
            return null;
        }

        @Override
        public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, int aggregationDurationCode, GroupHierarchy gh) {
            return getDriverVehicleScoreWrappers();
        }

        @Override
        public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, AggregationDuration aggregationDuration, GroupHierarchy gh) {
            return getDriverVehicleScoreWrappers();
        }

        @Override
        public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, Duration duration, GroupHierarchy gh) {
            return getDriverVehicleScoreWrappers();
        }

        @Override
        public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, DateTime startTime, DateTime endTime, GroupHierarchy gh) {
            return getDriverVehicleScoreWrappers();
        }

        @Override
        public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, Interval interval, GroupHierarchy gh) {
            return getDriverVehicleScoreWrappers();
        }

        @Override
        public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, DateTime day, GroupHierarchy gh) {
            return getDriverVehicleScoreWrappers();
        }

        @Override
        public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, Duration duration, GroupHierarchy gh) {
            return getDriverVehicleScoreWrappers();
        }

        @Override
        public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, int aggregationDurationCode, GroupHierarchy gh) {
            return getDriverVehicleScoreWrappers();
        }

        @Override
        public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, AggregationDuration aggregationDuration, GroupHierarchy gh) {
            return getDriverVehicleScoreWrappers();
        }

        @Override
        public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, Interval interval, GroupHierarchy gh) {
            return getDriverVehicleScoreWrappers();
        }

        @Override
        public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, DateTime day, GroupHierarchy gh) {
            return getDriverVehicleScoreWrappers();
        }

        @Override
        public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, DateTime startTime, DateTime endTime, GroupHierarchy gh) {
            return getDriverVehicleScoreWrappers();
        }

        @Override
        public List<GroupTrendWrapper> getSubGroupsAggregateDriverTrends(Integer groupID, Duration duration, GroupHierarchy gh) {
            return null;
        }

        @Override
        public Percentage getDriverPercentage(Integer groupID, Duration duration, GroupHierarchy gh) {
            return null;
        }

        @Override
        public List<GroupScoreWrapper> getSubGroupsAggregateDriverScores(Integer groupID, Duration duration, GroupHierarchy gh) {
            return null;
        }
        
    }
    
    private class MockScoreDAO implements ScoreDAO {

        @Override
        public ScoreableEntity findByID(Integer id) {
            return null;
        }

        @Override
        public Integer create(Integer id, ScoreableEntity entity) {
            return null;
        }

        @Override
        public Integer update(ScoreableEntity entity) {
            return null;
        }

        @Override
        public Integer deleteByID(Integer id) {
            return null;
        }

        @Override
        public ScoreableEntity getAverageScoreByType(Integer groupID, Duration duration, ScoreType st, GroupHierarchy gh) {
            return null;
        }

        @Override
        public ScoreableEntity getSummaryScore(Integer groupID, Duration duration, ScoreType st, GroupHierarchy gh) {
            return null;
        }

        @Override
        public List<ScoreableEntity> getScores(Integer groupID, Duration duration, ScoreType scoreType, GroupHierarchy gh) {
            return null;
        }

        @Override
        public ScoreableEntity getTrendSummaryScore(Integer groupID, Duration duration, ScoreType scoreType, GroupHierarchy gh) {
            return null;
        }

        @Override
        public Map<Integer, List<ScoreableEntity>> getTrendScores(Integer groupID, Duration duration, GroupHierarchy gh) {
            return getGroupTrendsMap();
        }

        @Override
        public List<DriverScore> getSortedDriverScoreList(Integer groupID, Duration duration, GroupHierarchy gh) {
            return null;
        }

        @Override
        public List<ScoreableEntity> getScoreBreakdown(Integer groupID, Duration duration, ScoreType scoreType, GroupHierarchy gh) {
            return null;
        }

        @Override
        public List<ScoreTypeBreakdown> getScoreBreakdownByType(Integer groupID, Duration duration, ScoreType scoreType, GroupHierarchy gh) {
            return null;
        }

        @Override
        public List<VehicleReportItem> getVehicleReportData(Integer groupID, Duration duration, Map<Integer, Group> groupMap) {
            return null;
        }

        @Override
        public List<DriverReportItem> getDriverReportData(Integer groupID, Duration duration, Map<Integer, Group> groupMap) {
            return null;
        }

        @Override
        public CrashSummary getGroupCrashSummaryData(Integer groupID, GroupHierarchy gh) {
            return null;
        }

        @Override
        public CrashSummary getDriverCrashSummaryData(Integer driverID) {
            return null;
        }

        @Override
        public CrashSummary getVehicleCrashSummaryData(Integer vehicleID) {
            return null;
        }

        @Override
        public List<SpeedPercentItem> getSpeedPercentItems(Integer groupID, Duration duration, GroupHierarchy gh) {
            return null;
        }

        @Override
        public List<IdlePercentItem> getIdlePercentItems(Integer groupID, Duration duration, GroupHierarchy gh) {
            return null;
        }

        @Override
        public List<TrendItem> getTrendCumulative(Integer id, EntityType entityType, Duration duration) {
            return null;
        }

        @Override
        public List<TrendItem> getTrendScores(Integer id, EntityType entityType, Duration duration) {
            return null;
        }

        @Override
        public List<ScoreItem> getAverageScores(Integer id, EntityType entityType, Duration duration) {
            return null;
        }
        
    }
    
    private List<DriverVehicleScoreWrapper> getDriverVehicleScoreWrappers() {
        List<DriverVehicleScoreWrapper> retVal = new ArrayList<DriverVehicleScoreWrapper>();
        
        Person person = new Person();
        person.setAcctID(ACCOUNT_ID);
        person.setFirst(PERSON_FIRST_NAME);
        person.setLast(PERSON_LAST_NAME);
        
        Driver driver = new Driver();
        driver.setGroupID(TEAM_GROUP_ID);
        driver.setDriverID(DRIVER_ID);
        driver.setPerson(person);
        
        Score score = new Score();
        score.setOverall(SCORE_OVERALL);
        
        DriverVehicleScoreWrapper dvsw = new DriverVehicleScoreWrapper();
        dvsw.setDriver(driver);
        dvsw.setScore(score);
        
        retVal.add(dvsw);
        
        return retVal;
    }
    
    private Map<Integer, List<ScoreableEntity>>getGroupTrendsMap() {
        Map<Integer, List<ScoreableEntity>> retVal = new HashMap<Integer, List<ScoreableEntity>>();
        if(this.listSwitch == SEListSwitch.DAY_30) {
            retVal.put(TEAM_GROUP_ID, getScoreableEntityListByDay());
        } else if (this.listSwitch == SEListSwitch.MONTH) {
            retVal.put(TEAM_GROUP_ID, getScoreableEntityListByMonth());
        } else if (this.listSwitch == SEListSwitch.EMPTY){
            retVal.put(TEAM_GROUP_ID, new ArrayList<ScoreableEntity>());
        } else if (this.listSwitch == SEListSwitch.EMPTY){
            retVal.put(TEAM_GROUP_ID, new ArrayList<ScoreableEntity>());
        } else if (this.listSwitch == SEListSwitch.NULL){
            retVal.put(TEAM_GROUP_ID, null);
        }
        
        return retVal;
    }
    
    private List<ScoreableEntity> getScoreableEntityListByDay() {
        List<ScoreableEntity> retVal = new ArrayList<ScoreableEntity>();
        
        DateTime dateTime = new DateTime();
        dateTime = dateTime.withZone(DateTimeZone.UTC);
        dateTime = dateTime.minusDays(30);
        
        for(int i = 0; i < 30; i ++) {
            ScoreableEntity se = new ScoreableEntity();
            if( i == 24 ){
                se.setScore(SCOREABLE_ENTITY_SCORE_FIVE_DAY_AGO);
            } else if ( i == 25 ){
                se.setScore(SCOREABLE_ENTITY_SCORE_FOUR_DAY_AGO);
            } else if ( i == 26 ){
                se.setScore(SCOREABLE_ENTITY_SCORE_THREE_DAY_AGO);
            } else if ( i == 27 ){
                se.setScore(SCOREABLE_ENTITY_SCORE_TWO_DAY_AGO);
            } else if ( i == 28 ){
                se.setScore(SCOREABLE_ENTITY_SCORE_ONE_DAY_AGO);
            }  else if ( i == 29 ){
                se.setScore(SCOREABLE_ENTITY_SCORE_TODAY);
            } else {
                se.setScore(SCOREABLE_ENTITY_SCORE_DEFAULT);
            }
            
            dateTime = dateTime.plusDays(1);
            
            se.setEntityID(TEAM_GROUP_ID);
            se.setEntityType(EntityType.ENTITY_GROUP);
            se.setScoreType(ScoreType.SCORE_OVERALL);
            se.setDate(dateTime.toDate());
            
            retVal.add(se);
        }
        
//        for( ScoreableEntity se : retVal){
//            System.out.println("Group ID: " + se.getEntityID() + " Date: " + se.getDate() + " Score: " + se.getScore());
//        }
        
        return retVal;
    }
    
    public List<ScoreableEntity> getScoreableEntityListByMonth() {
        List<ScoreableEntity> retVal = new ArrayList<ScoreableEntity>();
        
        DateTime dateTime = new DateTime();
        dateTime = dateTime.dayOfMonth().withMinimumValue();
        dateTime = dateTime.minusMonths(4);
        
        for(int i = 0; i < 3; i++) {
            ScoreableEntity se = new ScoreableEntity();
            if( i == 2 ){
                se.setScore(SCOREABLE_ENTITY_SCORE_MONTH);
            } else {
                se.setScore(SCOREABLE_ENTITY_SCORE_DEFAULT);
            }
            
            dateTime = dateTime.plusMonths(1);
            
            se.setEntityID(TEAM_GROUP_ID);
            se.setEntityType(EntityType.ENTITY_GROUP);
            se.setScoreType(ScoreType.SCORE_OVERALL);
            se.setDate(dateTime.toDate());
            
            retVal.add(se);
        }
        
//        for( ScoreableEntity se : retVal){
//            System.out.println("Group ID: " + se.getEntityID() + " Date: " + se.getDate() + " Score: " + se.getScore());
//        }
        
        return retVal;
    }
    
    private Group getGroup() {
        Group retVal = new Group(TEAM_GROUP_ID, ACCOUNT_ID, GROUP_NAME, TEAM_PARENT_GROUP_ID);
        
        return retVal;
    }
}

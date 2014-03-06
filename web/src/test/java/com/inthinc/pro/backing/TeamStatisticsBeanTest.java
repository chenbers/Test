package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.backing.TeamMockData.SEListSwitch;
import com.inthinc.pro.dao.mock.data.UnitTestStats;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.security.userdetails.ProUser;

public class TeamStatisticsBeanTest extends BaseBeanTest  {

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
        TeamMockData mockData = new TeamMockData();
        bean.setGroupReportDAO(mockData.getMockGroupReportDAO());
        
        TeamCommonBean commonBean = new TeamCommonBean();
        
        commonBean.setGroupID(TeamMockData.TEAM_GROUP_ID);
        commonBean.setGroup(mockData.getGroup());
       
        bean.setTeamCommonBean(commonBean);
        
        // Test Today
        commonBean.setTimeFrame(TimeFrame.TODAY);
        mockData.setListSwitch(SEListSwitch.DAY_30);
        // clear the cache
        bean.getTeamCommonBean().getCachedTrendResults().clear();
        List<DriverVehicleScoreWrapper> dvswList = bean.getDriverTotals();
        assertNotNull(dvswList);
        assertEquals(1, dvswList.size());
        assertNotNull(dvswList.get(0).getScore());
        assertNotNull(dvswList.get(0).getScore().getOverall());
        assertEquals(TeamMockData.TREND_SCORE_TODAY, dvswList.get(0).getScore().getOverall());
        
        //Test One day ago
        commonBean.setTimeFrame(TimeFrame.ONE_DAY_AGO);
        mockData.setListSwitch(SEListSwitch.DAY_30);
        // clear the cache
        bean.getTeamCommonBean().getCachedTrendResults().clear();
        dvswList = bean.getDriverTotals();
        assertNotNull(dvswList);
        assertEquals(1, dvswList.size());
        assertNotNull(dvswList.get(0).getScore());
        assertNotNull(dvswList.get(0).getScore().getOverall());
        assertEquals(TeamMockData.TREND_SCORE_ONE_DAY_AGO, dvswList.get(0).getScore().getOverall());
        
        //Test empty scorable entity list
        commonBean.setTimeFrame(TimeFrame.ONE_DAY_AGO);
        mockData.setListSwitch(SEListSwitch.EMPTY);
        // clear the cache
        bean.getTeamCommonBean().getCachedTrendResults().clear();
        dvswList = bean.getDriverTotals();
        assertNotNull(dvswList);
        assertEquals(1, dvswList.size());
        assertNotNull(dvswList.get(0).getScore());
        assertNotNull(dvswList.get(0).getScore().getOverall());
        assertEquals(TeamMockData.SCORE_NA, dvswList.get(0).getScore().getOverall());
        
       //Test null scorable entity list
        commonBean.setTimeFrame(TimeFrame.ONE_DAY_AGO);
        mockData.setListSwitch(SEListSwitch.NULL);
        // clear the cache
        bean.getTeamCommonBean().getCachedTrendResults().clear();
        dvswList = bean.getDriverTotals();
        assertNotNull(dvswList);
        assertEquals(1, dvswList.size());
        assertNotNull(dvswList.get(0).getScore());
        assertNotNull(dvswList.get(0).getScore().getOverall());
        assertEquals(TeamMockData.SCORE_NA, dvswList.get(0).getScore().getOverall());
        
        //Test Two day ago
        commonBean.setTimeFrame(TimeFrame.TWO_DAYS_AGO);
        mockData.setListSwitch(SEListSwitch.DAY_30);
        // clear the cache
        bean.getTeamCommonBean().getCachedTrendResults().clear();
        dvswList = bean.getDriverTotals();
        assertNotNull(dvswList);
        assertEquals(1, dvswList.size());
        assertNotNull(dvswList.get(0).getScore());
        assertNotNull(dvswList.get(0).getScore().getOverall());
        assertEquals(TeamMockData.TREND_SCORE_TWO_DAY_AGO, dvswList.get(0).getScore().getOverall());
        
        //Test Three day ago
        commonBean.setTimeFrame(TimeFrame.THREE_DAYS_AGO);
        mockData.setListSwitch(SEListSwitch.DAY_30);
        // clear the cache
        bean.getTeamCommonBean().getCachedTrendResults().clear();
        dvswList = bean.getDriverTotals();
        assertNotNull(dvswList);
        assertEquals(1, dvswList.size());
        assertNotNull(dvswList.get(0).getScore());
        assertNotNull(dvswList.get(0).getScore().getOverall());
        assertEquals(TeamMockData.TREND_SCORE_THREE_DAY_AGO, dvswList.get(0).getScore().getOverall());
        
        //Test Four day ago
        commonBean.setTimeFrame(TimeFrame.FOUR_DAYS_AGO);
        mockData.setListSwitch(SEListSwitch.DAY_30);
        // clear the cache
        bean.getTeamCommonBean().getCachedTrendResults().clear();
        dvswList = bean.getDriverTotals();
        assertNotNull(dvswList);
        assertEquals(1, dvswList.size());
        assertNotNull(dvswList.get(0).getScore());
        assertNotNull(dvswList.get(0).getScore().getOverall());
        assertEquals(TeamMockData.TREND_SCORE_FOUR_DAY_AGO, dvswList.get(0).getScore().getOverall());
       
        //Test Five day ago
        commonBean.setTimeFrame(TimeFrame.FIVE_DAYS_AGO);
        mockData.setListSwitch(SEListSwitch.DAY_30);
        // clear the cache
        bean.getTeamCommonBean().getCachedTrendResults().clear();
        dvswList = bean.getDriverTotals();
        assertNotNull(dvswList);
        assertEquals(1, dvswList.size());
        assertNotNull(dvswList.get(0).getScore());
        assertNotNull(dvswList.get(0).getScore().getOverall());
        assertEquals(TeamMockData.TREND_SCORE_FIVE_DAY_AGO, dvswList.get(0).getScore().getOverall());
        
        //Test Month
        commonBean.setTimeFrame(TimeFrame.MONTH);
        mockData.setListSwitch(SEListSwitch.MONTH);
        // clear the cache
        bean.getTeamCommonBean().getCachedTrendResults().clear();
        dvswList = bean.getDriverTotals();
        assertNotNull(dvswList);
        assertEquals(1, dvswList.size());
        assertNotNull(dvswList.get(0).getScore());
        assertNotNull(dvswList.get(0).getScore().getOverall());
        assertEquals(TeamMockData.TREND_SCORE_MONTH, dvswList.get(0).getScore().getOverall());
    }


    @Test
    public void testScoresWithUserTimeZone() {
        ProUser proUser = loginUser(UnitTestStats.UNIT_TEST_LOGIN);
        User user = proUser.getUser();
        Person person = user.getPerson();

        //set person time zone to gmt + 2
        person.setTimeZone(TimeZone.getTimeZone("GMT+2"));

        TeamStatisticsBean teamStatisticsBean = (TeamStatisticsBean) applicationContext.getBean("teamStatisticsBean");
        TeamCommonBean teamCommonBean = teamStatisticsBean.getTeamCommonBean();
        TeamMockData teamMockData = new TeamMockData();
        GroupReportDAO groupReportDAO = teamMockData.getMockGroupReportDAO();
        teamStatisticsBean.setGroupReportDAO(groupReportDAO);

        // set time frame for one day ago
        teamCommonBean.setTimeFrame(TimeFrame.ONE_DAY_AGO);
        assertEquals(teamCommonBean.getTimeFrame().name(), "ONE_DAY_AGO");

        List<DriverVehicleScoreWrapper> scoresWithUTCTimeZone = groupReportDAO.getDriverScores(teamCommonBean.getGroupID(), teamCommonBean.getTimeFrame().getInterval(teamStatisticsBean.getDateTimeZone()).getStart(),
                teamStatisticsBean.getGroupHierarchy());

        List<DriverVehicleScoreWrapper> scoresWithUserTimeZone = groupReportDAO.getDriverScoresWithUserTimeZone(teamCommonBean.getGroupID(), teamCommonBean.getTimeFrame().getInterval(teamStatisticsBean.getDateTimeZone()).getStart(),
                teamStatisticsBean.getGroupHierarchy());

        //there must be data
        assertTrue(scoresWithUTCTimeZone != null && scoresWithUTCTimeZone.size() > 0);
        assertTrue(scoresWithUserTimeZone != null && scoresWithUserTimeZone.size() > 0);

        // calculate both avg scores then compare
        int utcScore = 0;
        for (DriverVehicleScoreWrapper score : scoresWithUTCTimeZone) {
            utcScore += score.getScore().getOverall().intValue();
        }
        utcScore = utcScore / scoresWithUTCTimeZone.size();
        assertTrue(utcScore > 0);

        int userTimeZoneScore = 0;
        for (DriverVehicleScoreWrapper score : scoresWithUserTimeZone) {
            userTimeZoneScore += score.getScore().getOverall().intValue();
        }
        userTimeZoneScore = userTimeZoneScore / scoresWithUserTimeZone.size();
        assertTrue(utcScore > 0);

        // assert different scores
        assertTrue(utcScore != userTimeZoneScore);
    }
}

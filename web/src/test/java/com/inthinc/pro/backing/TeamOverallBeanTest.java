package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.backing.TeamMockData.SEListSwitch;
import com.inthinc.pro.dao.mock.data.UnitTestStats;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.security.userdetails.ProUser;

public class TeamOverallBeanTest extends BaseBeanTest {

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
        loginUser(UnitTestStats.UNIT_TEST_LOGIN);
     
        TeamOverallBean bean = (TeamOverallBean)applicationContext.getBean("teamOverallBean");
        
        // Check the injected beans
        assertNotNull(bean.getTeamCommonBean());
        
        // Fake some score data
        List<DriverVehicleScoreWrapper> lDvsw = new ArrayList<DriverVehicleScoreWrapper>();        
        
        Score s = new Score();
        s.setOverall(42); // 42 overall score   
        DriverVehicleScoreWrapper dvsw = new DriverVehicleScoreWrapper();        
        dvsw.setScore(s);
        lDvsw.add(dvsw);
        
        s = new Score();
        s.setOverall(17); // 17 overall score   
        dvsw = new DriverVehicleScoreWrapper();
        dvsw.setScore(s);
        lDvsw.add(dvsw);
                
        // Set the fake data into the common bean cache
        bean.getTeamCommonBean().getCachedResults().put("YEAR", lDvsw);
        bean.getTeamCommonBean().setTimeFrame(TimeFrame.YEAR);
        
        // Acquire the totals
        List<HashMap<String,String>> lTotals = bean.getOverallTotals();       
        
        // Load the data and see if the code gets the correct totals
        assertEquals(lTotals.get(0).get("zeroToOne"),"0");
        assertEquals(lTotals.get(0).get("oneToTwo"),"1");
        assertEquals(lTotals.get(0).get("twoToThree"),"0");
        assertEquals(lTotals.get(0).get("threeToFour"),"0");
        assertEquals(lTotals.get(0).get("fourToFive"),"1");
        
        // Check the chart
        assertNotNull("chart xml should be set", 
                bean.createPieChart(ScoreType.SCORE_OVERALL));   
        
        // Average overall score
        int score = bean.getSelectedOverallScore().intValue();
        assertEquals(29,score);
    } 
    
    @Test
    public void testGetSelectedOverallScore() {
     
        // fleet manager level
        ProUser pu = loginUser(UnitTestStats.UNIT_TEST_LOGIN);
        
        //pacify compiler
        pu.getClass();
        
        TeamOverallBean bean = (TeamOverallBean)applicationContext.getBean("teamOverallBean");
        TeamMockData mockData = new TeamMockData();
        bean.setGroupReportDAO(mockData.getMockGroupReportDAO());
        
        TeamCommonBean commonBean = (TeamCommonBean)applicationContext.getBean("teamCommonBean");
        TeamCommonBeanTimeFrame teamCommonBeanTimeFrame = (TeamCommonBeanTimeFrame)applicationContext.getBean("teamCommonBeanTimeFrameBean");
        commonBean.setTeamCommonBeanTimeFrame(teamCommonBeanTimeFrame);
        teamCommonBeanTimeFrame.setTimeFrame(TimeFrame.TODAY);
        commonBean.setGroupID(TeamMockData.TEAM_GROUP_ID);
        commonBean.setGroup(mockData.getGroup());
       
        bean.setTeamCommonBean(commonBean);
        
        // Test Today
//        commonBean.setTimeFrame(TimeFrame.TODAY);
        mockData.setListSwitch(SEListSwitch.DAY_30);
        // clear the cache
        bean.getTeamCommonBean().getCachedTrendResults().clear();
        bean.getOverallScoreMap().clear();
        Integer score  = bean.getSelectedOverallScore();
        assertNotNull(score);
        assertEquals(TeamMockData.TREND_SCORE_TODAY,score);
        
        //Test One day ago
        commonBean.setTimeFrame(TimeFrame.ONE_DAY_AGO);
        mockData.setListSwitch(SEListSwitch.DAY_30);
        // clear the cache
        bean.getTeamCommonBean().getCachedTrendResults().clear();
        bean.getOverallScoreMap().clear();
        score  = bean.getSelectedOverallScore();
        assertNotNull(score);
        assertEquals(TeamMockData.TREND_SCORE_ONE_DAY_AGO,score);
        
        //Test empty scorable entity list
        commonBean.setTimeFrame(TimeFrame.ONE_DAY_AGO);
        mockData.setListSwitch(SEListSwitch.EMPTY);
        // clear the cache
        bean.getTeamCommonBean().getCachedTrendResults().clear();
        bean.getOverallScoreMap().clear();
        score  = bean.getSelectedOverallScore();
        assertNotNull(score);
        assertEquals(TeamMockData.SCORE_NA,score);
        
       //Test null scorable entity list
        commonBean.setTimeFrame(TimeFrame.ONE_DAY_AGO);
        mockData.setListSwitch(SEListSwitch.NULL);
        // clear the cache
        bean.getTeamCommonBean().getCachedTrendResults().clear();
        score  = bean.getSelectedOverallScore();
        assertNotNull(score);
        assertEquals(TeamMockData.SCORE_NA,score);
        
        //Test Two day ago
        commonBean.setTimeFrame(TimeFrame.TWO_DAYS_AGO);
        mockData.setListSwitch(SEListSwitch.DAY_30);
        // clear the cache
        bean.getTeamCommonBean().getCachedTrendResults().clear();
        bean.getOverallScoreMap().clear();
        score  = bean.getSelectedOverallScore();
        assertNotNull(score);
        assertEquals(TeamMockData.TREND_SCORE_TWO_DAY_AGO,score);
        
        //Test Three day ago
        commonBean.setTimeFrame(TimeFrame.THREE_DAYS_AGO);
        mockData.setListSwitch(SEListSwitch.DAY_30);
        // clear the cache
        bean.getTeamCommonBean().getCachedTrendResults().clear();
        bean.getOverallScoreMap().clear();
        score  = bean.getSelectedOverallScore();
        assertNotNull(score);
        assertEquals(TeamMockData.TREND_SCORE_THREE_DAY_AGO,score);
        
        //Test Four day ago
        commonBean.setTimeFrame(TimeFrame.FOUR_DAYS_AGO);
        mockData.setListSwitch(SEListSwitch.DAY_30);
        // clear the cache
        bean.getTeamCommonBean().getCachedTrendResults().clear();
        bean.getOverallScoreMap().clear();
        score  = bean.getSelectedOverallScore();
        assertNotNull(score);
        assertEquals(TeamMockData.TREND_SCORE_FOUR_DAY_AGO,score);
       
        //Test Five day ago
        commonBean.setTimeFrame(TimeFrame.FIVE_DAYS_AGO);
        mockData.setListSwitch(SEListSwitch.DAY_30);
        // clear the cache
        bean.getTeamCommonBean().getCachedTrendResults().clear();
        bean.getOverallScoreMap().clear();
        score  = bean.getSelectedOverallScore();
        assertNotNull(score);
        assertEquals(TeamMockData.TREND_SCORE_FIVE_DAY_AGO,score);
        
        //Test Month
        commonBean.setTimeFrame(TimeFrame.MONTH);
        mockData.setListSwitch(SEListSwitch.MONTH);
        // clear the cache
        bean.getTeamCommonBean().getCachedTrendResults().clear();
        bean.getOverallScoreMap().clear();
        score  = bean.getSelectedOverallScore();
        assertNotNull(score);
        assertEquals(TeamMockData.TREND_SCORE_MONTH,score);
    }

}

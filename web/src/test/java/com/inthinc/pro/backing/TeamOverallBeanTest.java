package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.mock.data.UnitTestStats;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.Score;

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

}

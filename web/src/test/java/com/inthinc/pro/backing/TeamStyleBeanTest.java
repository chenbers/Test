package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.mock.data.UnitTestStats;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.Score;

public class TeamStyleBeanTest  extends BaseBeanTest {

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
     
        TeamStyleBean bean = (TeamStyleBean)applicationContext.getBean("teamStyleBean");
        
        // Check the injected beans
        assertNotNull(bean.getTeamCommonBean());
        
        // Fake some score data
        List<DriverVehicleScoreWrapper> lDvsw = new ArrayList<DriverVehicleScoreWrapper>();        
        
        Score s = new Score();  
        s.setAggressiveAccel(22);
        s.setAggressiveBrake(14);
        s.setAggressiveBump(47);
        s.setAggressiveTurn(35);        
        s.setDrivingStyle(42);
        DriverVehicleScoreWrapper dvsw = new DriverVehicleScoreWrapper();        
        dvsw.setScore(s);
        lDvsw.add(dvsw);
        
        s = new Score();
        s.setAggressiveAccel(24);
        s.setAggressiveBrake(15);
        s.setAggressiveBump(41);
        s.setAggressiveTurn(32);        
        s.setDrivingStyle(26);        
        dvsw = new DriverVehicleScoreWrapper();
        dvsw.setScore(s);
        lDvsw.add(dvsw);
                
        // Set the fake data into the common bean cache
        bean.getTeamCommonBean().getCachedResults().put("YEAR", lDvsw);
        bean.getTeamCommonBean().setTimeFrame(TimeFrame.YEAR);
        
        // Acquire the totals
//        List<HashMap<String,String>> lTotals = bean.getOverallTotals();       
      
//        // Load the data and see if the code gets the correct totals
//        assertEquals(lTotals.get(0).get("zeroToOne"),"0");
//        assertEquals(lTotals.get(0).get("oneToTwo"),"1");
//        assertEquals(lTotals.get(0).get("twoToThree"),"0");
//        assertEquals(lTotals.get(0).get("threeToFour"),"0");
//        assertEquals(lTotals.get(0).get("fourToFive"),"1");
//        
//        // Check the chart
        String chart = bean.createBar3DChart(ScoreType.SCORE_DRIVING_STYLE);
        assertNotNull("chart xml should be set", 
                bean.createBar3DChart(ScoreType.SCORE_DRIVING_STYLE));
//        
//        // Average overall style score
        int score = bean.getSelectedOverallScore().intValue();
        assertEquals(34,score);
    }   

}


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

public class TeamSpeedBeanTest extends BaseBeanTest {

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
     
        TeamSpeedBean bean = (TeamSpeedBean)applicationContext.getBean("teamSpeedBean");
        
        // Check the injected beans
        assertNotNull(bean.getTeamCommonBean());
        
        // Fake some score data
        List<DriverVehicleScoreWrapper> lDvsw = new ArrayList<DriverVehicleScoreWrapper>();        
        
        Score s = new Score();
        s.setSpeeding(23);
        s.setSpeedEvents1(22);
        s.setSpeedEvents2(10);
        s.setSpeedEvents3(33);
        s.setSpeedEvents4(5);
        s.setSpeedEvents5(4);
        s.setSpeedEvents(74);
        s.setSpeeding1(12);
        s.setSpeeding2(41);
        s.setSpeeding3(36);
        s.setSpeeding4(25);
        s.setSpeeding5(25);
        s.setOdometer1(110000);
        s.setOdometer2(310000);
        s.setOdometer3(210000);
        s.setOdometer4(510000);
        s.setOdometer5(610000);
        s.setOdometer(1750000);
        s.setSpeedOdometer1(1000);
        s.setSpeedOdometer2(3000);
        s.setSpeedOdometer3(2000);
        s.setSpeedOdometer4(5000);
        s.setSpeedOdometer5(6000);
        s.setSpeedOdometer(17000);
        DriverVehicleScoreWrapper dvsw = new DriverVehicleScoreWrapper();        
        dvsw.setScore(s);
        lDvsw.add(dvsw);
        
        s = new Score();
        s.setSpeeding(43);
        s.setSpeedEvents1(6);
        s.setSpeedEvents2(9);
        s.setSpeedEvents3(8);
        s.setSpeedEvents4(3);
        s.setSpeedEvents5(7);  
        s.setSpeedEvents(33);
        s.setSpeeding1(22);
        s.setSpeeding2(31);
        s.setSpeeding3(16);
        s.setSpeeding4(35);
        s.setSpeeding5(35); 
        s.setOdometer1(10000);
        s.setOdometer2(30000);
        s.setOdometer3(20000);
        s.setOdometer4(50000);
        s.setOdometer5(60000);   
        s.setOdometer(170000);
        s.setSpeedOdometer1(11000);
        s.setSpeedOdometer2(31000);
        s.setSpeedOdometer3(21000);
        s.setSpeedOdometer4(51000);
        s.setSpeedOdometer5(61000);
        s.setSpeedOdometer(175000);
        dvsw = new DriverVehicleScoreWrapper();
        dvsw.setScore(s);
        lDvsw.add(dvsw);
                
        // Set the fake data into the common bean cache
        bean.getTeamCommonBean().getCachedResults().put("YEAR", lDvsw);
        bean.getTeamCommonBean().setTimeFrame(TimeFrame.YEAR);
        
        // Acquire the totals
        List<HashMap<String,String>> lTotals = bean.getSpeedTotals();      
        
        // Load the data and see if the code gets the correct totals
        assertEquals(lTotals.get(0).get("zeroToThirty"),"28");
        assertEquals(lTotals.get(0).get("thirtyOneToFourty"),"19");
        assertEquals(lTotals.get(0).get("fourtyOneToFiftyFour"),"41");
        assertEquals(lTotals.get(0).get("fiftyFiveToSixtyFour"),"8");
        assertEquals(lTotals.get(0).get("sixtyFiveAndUp"),"11");
        
        // Check the chart
        assertNotNull("chart xml should be set", 
                bean.createPieChart(ScoreType.SCORE_SPEEDING));   
        
        // Average overall score
        int score = bean.getSelectedOverallScore().intValue();
        assertEquals(33,score);
        
        // Bar chart stuff
        List<Float> speed = bean.getMilesSpeeding();
        List<Float> miles = bean.getMilesDriven();
        
        assertEquals(speed.get(0),120.0,0.001);
        assertEquals(speed.get(1),340.0,0.001);
        assertEquals(speed.get(2),230.0,0.001);
        assertEquals(speed.get(3),560.0,0.001);
        assertEquals(speed.get(4),670.0,0.001);
        
        assertEquals(miles.get(0),1200.0,0.001);
        assertEquals(miles.get(1),3400.0,0.001);
        assertEquals(miles.get(2),2300.0,0.001);
        assertEquals(miles.get(3),5600.0,0.001);
        assertEquals(miles.get(4),6700.0,0.001);        
    }   

}


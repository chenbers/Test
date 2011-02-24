package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

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
   
        // Timeframe initialized correctly?
        TeamCommonBean tcb = bean.getTeamCommonBean();
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
        assertEquals(lTotals.get(0).getScore().getEndingOdometer(),336);
        assertEquals(lTotals.get(0).getScore().getDriveTime(),154);
        assertEquals(lTotals.get(0).getScore().getIdleHi(),107);
        assertEquals(lTotals.get(0).getScore().getIdleLo(),64);
        assertEquals(lTotals.get(0).getScore().getSafetyTotal().intValue(),137);
    }   
}

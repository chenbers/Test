package com.inthinc.pro.backing;

import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.mock.data.UnitTestStats;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
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
   
        // Timeframe initialized?
        TeamCommonBean tcb = bean.getTeamCommonBean();
        assertEquals(tcb.getTimeFrame().name(),"ONE_DAY_AGO");
        
        // Load "ONE_DAY_AGO"?
        tcb.setGroupID(grpID);
        List<DriverVehicleScoreWrapper> ds = bean.getDriverStatistics();
        assertNotNull(ds);
        
        // Did it get cached?
        Map<String,List<DriverVehicleScoreWrapper>> cr = tcb.getCachedResults();
        assertEquals(cr.containsKey("ONE_DAY_AGO"),Boolean.TRUE.booleanValue());
    }   
}

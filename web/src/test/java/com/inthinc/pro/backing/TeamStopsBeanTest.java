package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.mock.data.UnitTestStats;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverStopReport;
import com.inthinc.pro.model.DriverStops;

public class TeamStopsBeanTest extends BaseBeanTest  {
    
    // milliseconds in an hour
    public static final long millisInAnHour = 60 * 60 * 1000;

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

        TeamStopsBean bean = (TeamStopsBean)applicationContext.getBean("teamStopsBean");

        // Check the injected beans
        assertNotNull(bean.getTeamCommonBean());
        assertNotNull(bean.getDriverDAO());
        
        // Find the drivers and check on result
        List<Driver> d = bean.getDrivers();
        assertNotNull(d);
        
        // Set the driver id, get their summary data, and check on result
        bean.setSelectedDriverID(1);
        if ( d.size() > 0 ) {
            bean.setSelectedDriverID(d.get(0).getDriverID());
        }
        DriverStopReport ds = bean.getDriverStopReport();
        assertNotNull(ds);
    }
}

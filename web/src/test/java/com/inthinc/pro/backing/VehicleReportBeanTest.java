package com.inthinc.pro.backing;

import static org.junit.Assert.assertNotNull;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class VehicleReportBeanTest extends BaseBeanTest
{

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }
    
    @Test
    public void bean()
    {
        // just test the bean successfully creates all of the required pies
        
        // team level login
        loginUser("normal101");
        
        // get the bean from the applicationContext (initialized by Spring injection)
        VehicleReportBean vehicleReportBean = 
            (VehicleReportBean)applicationContext.getBean("vehicleReportBean");
        
        // make sure the spring injection worked
        assertNotNull(vehicleReportBean.getScoreDAO());
        assertNotNull(vehicleReportBean.getGroupDAO());
        assertNotNull(vehicleReportBean.getVehicleDAO());
    }
}

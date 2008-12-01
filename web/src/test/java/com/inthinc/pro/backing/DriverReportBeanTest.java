package com.inthinc.pro.backing;

import static org.junit.Assert.assertNotNull;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class DriverReportBeanTest extends BaseBeanTest
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
        DriverReportBean driverReportBean = 
            (DriverReportBean)applicationContext.getBean("driverReportBean");
        
        // make sure the spring injection worked
        assertNotNull(driverReportBean.getScoreDAO());
        assertNotNull(driverReportBean.getGroupDAO());
        assertNotNull(driverReportBean.getDriverDAO());
                
        // try grabbing some drivers based on above, should be 45 
        //  for normal101
        assertEquals(45,
                driverReportBean.getDriverData().size());        
        assertEquals( 1,
                (new Integer(driverReportBean.getStart())).intValue());
        assertEquals(25,
                (new Integer(driverReportBean.getEnd()).intValue()));
        assertEquals(45,
                (new Integer(driverReportBean.getMaxCount()).intValue()));        
    }
}


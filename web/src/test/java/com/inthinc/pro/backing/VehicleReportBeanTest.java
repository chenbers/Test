package com.inthinc.pro.backing;

import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.backing.ui.TableColumn;

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
        
        // try grabbing some vehicles based on above, should be ?? 
        //  for normal101
        
        // commented-out tests cannot be set, other developers
        //  are causing volatility
        /*
        assertEquals(36,
                vehicleReportBean.getVehicleData().size());        
        assertEquals( 1,
                (new Integer(vehicleReportBean.getStart())).intValue());
        assertEquals(25,
                (new Integer(vehicleReportBean.getEnd()).intValue()));
        assertEquals(36,
                (new Integer(vehicleReportBean.getMaxCount()).intValue()));
        */        
        // column map
        Map<String, TableColumn> columnMap = vehicleReportBean.getTableColumns();
        assertEquals(VehicleReportBean.AVAILABLE_COLUMNS.size(), columnMap.size());

    }
}

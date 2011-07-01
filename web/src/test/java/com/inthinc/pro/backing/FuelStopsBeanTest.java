package com.inthinc.pro.backing;

import java.util.Date;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Test;

import com.inthinc.pro.dao.mock.MockHOSDAO;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleType;

public class FuelStopsBeanTest extends BaseBeanTest {

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }
    
    @Test
    public void bean()
    {        
        // team level login
        loginUser("custom101");
        
        // get the bean from the applicationContext (initialized by Spring injection)
        FuelStopsBean fuelStopsBean = (FuelStopsBean)applicationContext.getBean("fuelStopsBean");
        NavigationBean nav = (NavigationBean)applicationContext.getBean("navigationBean");
        LocaleBean localeBean = new LocaleBean();
        localeBean.getLocale();
        
        fuelStopsBean.init();
        assertEquals(new Integer(15),fuelStopsBean.getPageData().getRowsPerPage());
        assertEquals(new Integer(0),fuelStopsBean.getPageData().getPageStartRow());
        
        fuelStopsBean.setVehicleID(130);
        fuelStopsBean.setVehicle(new Vehicle(130,0,Status.ACTIVE,"vehicle130","Subaru","Impreza",2011,"blue",VehicleType.LIGHT,"12345678901234567", null,"123abc",State.valueOf(45)));
        List<FuelStopsBean.FuelStopView> fuelStops = fuelStopsBean.getItems();
        assertTrue("Should be some fuel stops",fuelStops.size() > 0);
        
        String result = fuelStopsBean.add();
        assertEquals("pretty:fuelStopEdit",result);
        FuelStopsBean.FuelStopView item = fuelStopsBean.getItem();
        
        assertEquals(new Integer(130), item.getVehicleID());
        item.setTruckGallons(20.0f);
        item.setTrailerGallons(34.5f);
        item.setLocation("Sandy, UT");
        item.setLogTime(new Date());
        
        result = fuelStopsBean.save();
        assertEquals("pretty:fuelStops",result);
        assertEquals(((MockHOSDAO)fuelStopsBean.getHosDAO()).getLastID(),new Long(item.getId()));
        
    }
 
}

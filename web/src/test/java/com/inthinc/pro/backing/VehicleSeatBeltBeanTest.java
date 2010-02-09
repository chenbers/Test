package com.inthinc.pro.backing;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Vehicle;

public class VehicleSeatBeltBeanTest extends BaseBeanTest
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
        loginUser("custom101");
        
        // get the bean from the applicationContext (initialized by Spring injection)
        VehicleSeatBeltBean vehicleSeatBeltBean = (VehicleSeatBeltBean)applicationContext.getBean("vehicleSeatBeltBean");
        NavigationBean nav = (NavigationBean)applicationContext.getBean("navigationBean");
     
        
        Vehicle v = new Vehicle();
        v.setDriverID(101);
        v.setVehicleID(1111);
        v.setGroupID(102);

        
        vehicleSeatBeltBean.setVehicle(v);
        
        Person p = new Person();
        p.setFirst("John");
        p.setLast("Doe");
        
        Driver d = new Driver();
        d.setDriverID(101);
        d.setPersonID(45);
        d.setGroupID(101);
        d.setPerson(p);
        
        
        vehicleSeatBeltBean.setDriver(d);
        
        // make sure the spring injection worked
        assertNotNull(vehicleSeatBeltBean.getSeatBeltScore());
        assertNotNull(vehicleSeatBeltBean.getSeatBeltScoreStyle());
        assertNotNull(vehicleSeatBeltBean.getSeatBeltScoreHistoryOverall());
    }
}

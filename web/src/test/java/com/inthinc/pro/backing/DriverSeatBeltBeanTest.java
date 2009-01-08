package com.inthinc.pro.backing;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Person;

public class DriverSeatBeltBeanTest extends BaseBeanTest
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
        DriverSeatBeltBean driverSeatBeltBean = (DriverSeatBeltBean)applicationContext.getBean("driverSeatBeltBean");
        NavigationBean nav = (NavigationBean)applicationContext.getBean("navigationBean");
        
        driverSeatBeltBean.setNavigation(nav);
        
        Person p = new Person();
        p.setFirst("John");
        p.setLast("Doe");
        
        Driver d = new Driver();
        d.setDriverID(101);
        d.setPersonID(45);
        d.setGroupID(101);

        d.setPerson(p);
        
        driverSeatBeltBean.getNavigation().setDriver(d);
        
        // make sure the spring injection worked
        assertNotNull(driverSeatBeltBean.getSeatBeltScore());
        assertNotNull(driverSeatBeltBean.getSeatBeltScoreStyle());
        assertNotNull(driverSeatBeltBean.getSeatBeltScoreHistoryOverall());
        
    }
}

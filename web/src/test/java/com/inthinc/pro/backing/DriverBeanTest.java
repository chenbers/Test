package com.inthinc.pro.backing;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Person;

public class DriverBeanTest extends BaseBeanTest
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
        DriverBean driverBean = (DriverBean)applicationContext.getBean("driverBean");
        NavigationBean nav = (NavigationBean)applicationContext.getBean("navigationBean");
        
        driverBean.setNavigation(nav);
        
        Person p = new Person();
        p.setFirst("Kevin");
        p.setLast("Selman");
        
        Driver d = new Driver();
        d.setDriverID(101);
        d.setGroupID(201);
        d.setPersonID(45);

        d.setPerson(p);
        
        driverBean.getNavigation().setDriver(d);
        
        // make sure the spring injection worked
        assertNotNull(driverBean.getOverallScore());
        assertNotNull(driverBean.getOverallScoreStyle());
        assertNotNull(driverBean.getOverallScoreHistory());
        
        assertNotNull(driverBean.getMpgHistory());
        assertNotNull(driverBean.getCoachingHistory());
        
    }
}

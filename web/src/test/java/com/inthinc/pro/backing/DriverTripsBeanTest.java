package com.inthinc.pro.backing;


import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class DriverTripsBeanTest extends BaseBeanTest
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
    public void bean() throws IOException
    {
        // just test the bean successfully creates all of the required pies
        
        // team level login
//        loginUser("custom101");
//        
//        // get the bean from the applicationContext (initialized by Spring injection)
//        DriverTripsBean tripsBean = (DriverTripsBean)applicationContext.getBean("driverTripsBean");
//        tripsBean.setDriverDAO((DriverDAO)applicationContext.getBean("driverDAO"));
//        
//        Person p = new Person();
//        p.setFirst("John");
//        p.setLast("Doe");
//        p.setTimeZone(TimeZone.getTimeZone("GMT"));
//        
//        Driver d = new Driver();
//        d.setDriverID(101);
//        d.setPersonID(45);
//        d.setGroupID(101);
//
//        d.setPerson(p);
//        
//        tripsBean.setDriver(d);
        
//        // Get trips from DAO
//        tripsBean.initTrips();
//        assertNotNull(tripsBean.getSelectedTrip());
//        
//        // Test Stats
//        assertTrue( (tripsBean.getMilesDriven() > 0.0D) );
//        assertTrue( (tripsBean.getNumTrips() > 0) );
//        assertTrue( (tripsBean.getTotalDriveSeconds() > 0 ));
//        
//        // Test Events
//        tripsBean.initViolations(tripsBean.getStartDate(), tripsBean.getEndDate());
//        assertTrue( (tripsBean.getAllEvents().size() > 0) );
//        assertTrue( (tripsBean.getIdleEvents().size() > 0) );
//        assertTrue( (tripsBean.getViolationEvents().size() > 0) );
//        
//        // Test Event Sorting
//        Long firstEvent = DateUtil.convertDateToSeconds(tripsBean.getAllEvents().get(0).getTime());
//        Long secondEvent = DateUtil.convertDateToSeconds(tripsBean.getAllEvents().get(1).getTime());
//        assertTrue( (secondEvent > firstEvent));
//        
//        // Test ShowLastTenTrips
//        tripsBean.setShowLastTenTrips(true);
//        assertTrue( (tripsBean.getTrips().size() == 10) );
      
 
    }
}

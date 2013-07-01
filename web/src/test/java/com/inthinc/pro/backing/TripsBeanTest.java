package com.inthinc.pro.backing;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.model.LatLng;

public class TripsBeanTest extends BaseBeanTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {}

    @AfterClass
    public static void tearDownAfterClass() throws Exception {}

    @Test
    public void validLatLng() {
        
        // team level login
        loginUser("custom101");
        
        // get the bean from the applicationContext (initialized by Spring injection)
        TripsBean tripsBean  = (TripsBean)applicationContext.getBean("driverTripsBean");
        
        assertTrue("null LatLng is invalid", !tripsBean.isValidLatLng(null));
        assertTrue("0,0 LatLng is invalid", !tripsBean.isValidLatLng(new LatLng(0.0, 0.0)));
        assertTrue("close to 0,0 LatLng is invalid", !tripsBean.isValidLatLng(new LatLng(0.002, 0.002)));
        assertTrue("negative close to 0,0 LatLng is invalid", !tripsBean.isValidLatLng(new LatLng(-0.002, -0.002)));
        assertTrue("over maxLatLng is invalid", !tripsBean.isValidLatLng(new LatLng(LatLng.MAX_LAT+1.0, LatLng.MAX_LNG+1)));
        assertTrue("under minLatLng is invalid", !tripsBean.isValidLatLng(new LatLng(LatLng.MIN_LAT-1.0, LatLng.MIN_LNG-1)));
        
    }

}

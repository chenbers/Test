package com.inthinc.pro.service.it;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.Test;

import com.inthinc.pro.model.Trip;
import com.inthinc.pro.service.impl.DriverServiceImpl;



public class DriverServiceITCaseTest extends BaseEmbeddedServerITCase {
    private static Logger logger = Logger.getLogger(DriverServiceITCaseTest.class);
    private static int DRIVER_ID = 3;
    private static int DRIVER_ID_WITH_NO_DATA = 1;
    private static final SimpleDateFormat sdf = new SimpleDateFormat(DriverServiceImpl.getSimpleDateFormat());

  
    @Test
    public void getLastTripTest() throws Exception {
        
        // Getting driver last trip
        ClientResponse<Trip> trip = client.getLastTrip(DRIVER_ID);

        assertEquals( Response.Status.OK, trip.getResponseStatus() );
        assertNotNull(trip.getEntity());
        logger.info("Driver last trip retrieved successfully");
    }
    
    @Test
    public void getLastTripWithNoDataTest() throws Exception {
        
        // Getting driver last trip
        ClientResponse<Trip> trip = client.getLastTrip(DRIVER_ID_WITH_NO_DATA);

        assertEquals( Response.Status.NOT_FOUND, trip.getResponseStatus() );
    }
   
}

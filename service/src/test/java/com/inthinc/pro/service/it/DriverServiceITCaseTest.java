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

import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.service.impl.DriverServiceImpl;



public class DriverServiceITCaseTest extends BaseEmbeddedServerITCase {
    private static Logger logger = Logger.getLogger(DriverServiceITCaseTest.class);
    private static int DRIVER_ID = 3;
    private static int DRIVER_ID_WITH_NO_DATA = 777;
    private static int DRIVER_ID_WITH_NO_LOCATION = 1234;
    private static final SimpleDateFormat sdf = new SimpleDateFormat(DriverServiceImpl.getSimpleDateFormat());
    private static final String TOO_EARLY_DATE = "20090101";

  
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
    
    @Test
    public void getLastLocationTest() throws Exception {
        
        // Getting driver last location
        ClientResponse<LastLocation> location = client.getLastLocation(DRIVER_ID);

        assertEquals( Response.Status.OK, location.getResponseStatus() );
        assertNotNull(location.getEntity());
        logger.info("Driver last location retrieved successfully");
    }
    
    @Test
    public void getLastLocationWithNoDataTest() throws Exception {
        
        // Getting driver last location
        ClientResponse<LastLocation> location = client.getLastLocation(DRIVER_ID_WITH_NO_LOCATION);

        assertEquals( Response.Status.NOT_FOUND, location.getResponseStatus() );
    }
   
    
    @Test
    public void getLastTripsWithNoDataDefaultDateTest() throws Exception {
        
        // Getting driver last trips
        ClientResponse<List<Trip>> trips = client.getLastTrips(DRIVER_ID_WITH_NO_DATA);

        assertEquals( Response.Status.NOT_FOUND, trips.getResponseStatus() );
    }
    
    @Test
    public void getLastTripsWithTooEarlyDateTest() throws Exception {
        
        // Getting driver last trips
        ClientResponse<List<Trip>> trips = client.getLastTrips(DRIVER_ID, TOO_EARLY_DATE);

        assertEquals( Response.Status.BAD_REQUEST, trips.getResponseStatus() );
    }
   
}

package com.inthinc.pro.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import mockit.Expectations;

import org.junit.Test;

import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.service.adapters.DriverDAOAdapter;

/**
 * Unit tests for UserServiceImpl.
 */
public class DriverServiceImplTest extends BaseUnitTest {
    protected static final String TOO_EARLY_STRING_START_DATE = "20100102";
    private static final SimpleDateFormat sdf = new SimpleDateFormat(DriverServiceImpl.getSimpleDateFormat());
    
    DriverServiceImpl serviceSUT = new DriverServiceImpl();
    private Integer expectedDriver = 7999;
    
    @Test
    public void getLastTripWithDriverTripDataTest(final DriverDAOAdapter driverDaoMock) {
        serviceSUT.setDao(driverDaoMock);
        
        new Expectations() {
            {
                driverDaoMock.getLastTrip(expectedDriver);
                result = new Trip();
            }
        };
        
        Response response = serviceSUT.getLastTrip(expectedDriver);
        
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus() );
    }
    
    @Test
    public void getLastTripWithoutDriverTripDataTest(final DriverDAOAdapter driverDaoMock) {
        serviceSUT.setDao(driverDaoMock);
        
        new Expectations() {
            {
                driverDaoMock.getLastTrip(expectedDriver);
                result = null;
            }
        };
        
        Response response = serviceSUT.getLastTrip(expectedDriver);
        
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(),response.getStatus() );
    }
    
    @Test
    public void getLastTripWithNullDriverID() {        
        Response response = serviceSUT.getLastTrip(null);       
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(),response.getStatus() );
    }
    
    @Test
    public void getTripsFromDateWithDriverTripDataTest(final DriverDAOAdapter driverDaoMock) throws Exception {
        serviceSUT.setDao(driverDaoMock);
        
        final List<Trip> tripList = new ArrayList<Trip>();
        tripList.add(new Trip());
        
        final Date date = new SimpleDateFormat(DriverServiceImpl.getSimpleDateFormat()).parse(todayAsString());
        
        new Expectations() { 
            {      
                driverDaoMock.getLastTrips(expectedDriver, date );
                result = tripList ;
            }
        };
        
        Response response = serviceSUT.getLastTrips(expectedDriver, todayAsString());
        
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus() );
    }
    
    @Test
    public void getTripsFromDateWithDriverTripDataTestDefaultedDate(final DriverDAOAdapter driverDaoMock) throws Exception {
        serviceSUT.setDao(driverDaoMock);
        
        final List<Trip> tripList = new ArrayList<Trip>();
        tripList.add(new Trip());
        
        final Date date = new SimpleDateFormat(DriverServiceImpl.getSimpleDateFormat()).parse(limitGoodDateAsString());
        
        new Expectations() { 
            {      
                driverDaoMock.getLastTrips(expectedDriver, date );
                result = tripList ;
            }
        };
        
        Response response = serviceSUT.getLastTrips(expectedDriver);
        
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus() );
    }

    @Test
    public void getTripsFromDateWithoutDriverTripDataTest1(final DriverDAOAdapter driverDaoMock) throws Exception {
        serviceSUT.setDao(driverDaoMock);
        final Date date = new SimpleDateFormat(DriverServiceImpl.getSimpleDateFormat()).parse(todayAsString());
        
        new Expectations() {
            {      
                driverDaoMock.getLastTrips(expectedDriver, date );
                result = new ArrayList<Trip>();
            }
        };
        
        Response response = serviceSUT.getLastTrips(expectedDriver, todayAsString());
        
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(),response.getStatus() );
    }
    
    @Test
    public void getTripsFromDateWithoutDriverTripDataTest2(final DriverDAOAdapter driverDaoMock) throws Exception {
        serviceSUT.setDao(driverDaoMock);
        final Date date = new SimpleDateFormat(DriverServiceImpl.getSimpleDateFormat()).parse(todayAsString());
        
        new Expectations() {
            {      
                driverDaoMock.getLastTrips(expectedDriver, date );
                result = null;
            }
        };
        
        Response response = serviceSUT.getLastTrips(expectedDriver, todayAsString());
        
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(),response.getStatus() );
    }
    
    @Test
    public void getTripsFromDateWithLimitGoodDateTest(final DriverDAOAdapter driverDaoMock) throws Exception {
        serviceSUT.setDao(driverDaoMock);
        final Date date = new SimpleDateFormat(DriverServiceImpl.getSimpleDateFormat()).parse(limitGoodDateAsString());
        final List<Trip> tripList = new ArrayList<Trip>();
        tripList.add(new Trip());
        
        new Expectations() {
            {      
                driverDaoMock.getLastTrips(expectedDriver, date );
                result = tripList;
            }
        };
        
        Response response = serviceSUT.getLastTrips(expectedDriver, limitGoodDateAsString());
        
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus() );
    }
    
    @Test
    public void getTripsFromDateWithNullDriverTest(final DriverDAOAdapter driverDaoMock) throws Exception {
        serviceSUT.setDao(driverDaoMock);
         
        Response response = serviceSUT.getLastTrips(null, todayAsString());
        
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(),response.getStatus() );
    }
    
    @Test
    public void getTripsFromDateWithNullDateTest(final DriverDAOAdapter driverDaoMock) throws Exception {
        serviceSUT.setDao(driverDaoMock);
         
        Response response = serviceSUT.getLastTrips(expectedDriver, null);
        
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(),response.getStatus() );
    }
    
    @Test
    public void getTripsFromDateWithTooEarlyDateTest1(final DriverDAOAdapter driverDaoMock) throws Exception {
        serviceSUT.setDao(driverDaoMock);
         
        Response response = serviceSUT.getLastTrips(expectedDriver, TOO_EARLY_STRING_START_DATE);
        
        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),response.getStatus() );
    }
    
    @Test
    public void getTripsFromDateWithTooEarlyDateTest2(final DriverDAOAdapter driverDaoMock) throws Exception {
        serviceSUT.setDao(driverDaoMock);
         
        Response response = serviceSUT.getLastTrips(expectedDriver, tooEarlyDateAsString());
        
        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),response.getStatus() );
    }
    
    private String todayAsString() {   
        Calendar c1 = Calendar.getInstance(); 
        return sdf.format(c1.getTime());
    }

    private String tooEarlyDateAsString() {
        Calendar c1 = Calendar.getInstance(); 
        c1.add(Calendar.DATE, ( DriverServiceImpl.DEFAULT_PAST_TIMING - 1 ));   
        return sdf.format(c1.getTime());
    }
    
    private String limitGoodDateAsString() {
        Calendar c1 = Calendar.getInstance(); 
        c1.add(Calendar.DATE, DriverServiceImpl.DEFAULT_PAST_TIMING );   
        return sdf.format(c1.getTime());
    }
    
    @Test
    public void getLastLocationWithDriverTripDataTest(final DriverDAOAdapter driverDaoMock) {
        serviceSUT.setDao(driverDaoMock);
        
        new Expectations() {
            {
                driverDaoMock.getLastLocation(expectedDriver);
                result = new LastLocation();
            }
        };
        
        Response response = serviceSUT.getLastLocation(expectedDriver);
        
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus() );
    }
    
    @Test
    public void getLastLocationWithoutDriverLocationDataTest(final DriverDAOAdapter driverDaoMock) {
        serviceSUT.setDao(driverDaoMock);
        
        new Expectations() {
            {
                driverDaoMock.getLastLocation(expectedDriver);
                result = null;
            }
        };
        
        Response response = serviceSUT.getLastLocation(expectedDriver);
        
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(),response.getStatus() );
    }
    
    @Test
    public void getLastLocationWithNullDriverID() {        
        Response response = serviceSUT.getLastLocation(null);       
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(),response.getStatus() );
    }

    /**
     * Test if the right API is used when getGroupDriverLocations() is called.
     * Test if the Response returns what is expected.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetGroupDriverLocations(final DriverDAOAdapter driverDaoMock){
        final Integer groupID = 1;
        final DriverLocation location = new DriverLocation();
        serviceSUT.setDao(driverDaoMock);
        
        new Expectations() {
            {
                driverDaoMock.getDriverLocations(groupID); returns(new ArrayList<DriverLocation>());
                
                List<DriverLocation> list = new ArrayList<DriverLocation>();
                list.add(location);                
                driverDaoMock.getDriverLocations(groupID); returns(list);
            }
        };
        // check when empty list
        Response response = serviceSUT.getGroupDriverLocations(groupID);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        
        // check the content
        GenericEntity<List<DriverLocation>> entity = (GenericEntity<List<DriverLocation>>) response.getEntity();        
        assertNull(entity);

        // check when at least an element is returned
        response = serviceSUT.getGroupDriverLocations(groupID);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        
        // check the content
        entity = (GenericEntity<List<DriverLocation>>) response.getEntity();        
        assertNotNull(entity);
        assertEquals(1, entity.getEntity().size());
        assertEquals(location, entity.getEntity().get(0));
    }
}

package com.inthinc.pro.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import mockit.Expectations;

import org.junit.Test;

import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.service.adapters.DriverDAOAdapter;
import com.inthinc.pro.util.SecureDriverDAO;

/**
 * Unit tests for UserServiceImpl.
 */
public class DriverServiceImplTest extends BaseUnitTest {
    private static final String OK_RESPONSE = "Response Status: ";
    private static final String USERNAME_MOCK = "user";
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
}

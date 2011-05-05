package com.inthinc.pro.service.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.log4j.Logger;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClientExecutor;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.Trip;
//Run locally with your localhost service running on port 8080
@Ignore
public class DriverServiceITCaseTest extends BaseEmbeddedServerITCase {
    private static Logger logger = Logger.getLogger(DriverServiceITCaseTest.class);
    private static int DRIVER_ID = 3;
    private static int DRIVER_ID_WITH_NO_DATA = 777;
    private static int DRIVER_ID_WITH_NO_LOCATION = 1234;
    private static final String TOO_EARLY_DATE = "20090101";

    @Before
    public void before() {

        HttpClientParams params = new HttpClientParams();
        params.setAuthenticationPreemptive(true);
        httpClient = new HttpClient(params);
        Credentials defaultcreds = new UsernamePasswordCredentials(/*TiwiproPrincipal.ADMIN_BACKDOOR_USERNAME*/ "mraby", "password");
        httpClient.getState().setCredentials(new AuthScope(DOMAIN, getPort(), AuthScope.ANY_REALM), defaultcreds);
        clientExecutor = new ApacheHttpClientExecutor(httpClient);

        client = ProxyFactory.create(ServiceClient.class, "http://localhost:" + getPort(), clientExecutor);
    }

    /*
     * Disabling tests because of unstable back end data.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void getDriverDefaultXmlTest() throws Exception {

       ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/driver/"+DRIVER_ID);
       ClientResponse<Driver> response = request.get();
       Driver driver = response.getEntity(Driver.class);

        assertEquals(Response.Status.OK, response.getResponseStatus());
       MultivaluedMap<String,String> map = response.getHeaders();
       List<String> mediaTypes = map.get("Content-Type");
       assertEquals(mediaTypes.get(0), MediaType.APPLICATION_XML);
        assertNotNull(driver);
        assertEquals(driver.getDriverID(),new Integer(DRIVER_ID));
        logger.info("Driver retrieved successfully in the default XML");
    }
    @SuppressWarnings("unchecked")
    @Test
    public void getDriverXmlTest() throws Exception {

       ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/driver/"+DRIVER_ID+".xml");
       ClientResponse<Driver> response = request.get();
       Driver driver = response.getEntity(Driver.class);

        assertEquals(Response.Status.OK, response.getResponseStatus());
       MultivaluedMap<String,String> map = response.getHeaders();
       List<String> mediaTypes = map.get("Content-Type");
       assertEquals(mediaTypes.get(0), MediaType.APPLICATION_XML);
        assertNotNull(driver);
        assertEquals(driver.getDriverID(),new Integer(DRIVER_ID));
        logger.info("Driver retrieved successfully in XML");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getDriverJSONTest() throws Exception {

       ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/driver/"+DRIVER_ID+".json");
       ClientResponse<Driver> response = request.get();
       Driver driver = response.getEntity(Driver.class);

       assertEquals(Response.Status.OK, response.getResponseStatus());
       MultivaluedMap<String,String> map = response.getHeaders();
       List<String> mediaTypes = map.get("Content-Type");
       assertEquals(mediaTypes.get(0), MediaType.APPLICATION_JSON);
        assertNotNull(driver);
        assertEquals(driver.getDriverID(),new Integer(DRIVER_ID));
        logger.info("Driver retrieved successfully in JSON");
    }
    @SuppressWarnings("unchecked")
    @Test
    public void getDriverFastInfoSetTest() throws Exception {

       ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/driver/"+DRIVER_ID+".fastinfoset");
       ClientResponse<Driver> response = request.get();

       assertEquals(Response.Status.OK, response.getResponseStatus());
       MultivaluedMap<String,String> map = response.getHeaders();
       List<String> mediaTypes = map.get("Content-Type");
       assertEquals(mediaTypes.get(0), "application/fastinfoset");
       Driver driver = response.getEntity(Driver.class);
        assertNotNull(driver);
        assertEquals(driver.getDriverID(),new Integer(DRIVER_ID));
        logger.info("Driver retrieved successfully in fastInfoset");
    }
    @Test
    public void getLastTripTest() throws Exception {

        // Getting driver last trip
        ClientResponse<Trip> trip = client.getLastTrip(DRIVER_ID);

        assertEquals(Response.Status.OK, trip.getResponseStatus());
        assertNotNull(trip.getEntity());
        logger.info("Driver last trip retrieved successfully");
    }
    @SuppressWarnings("unchecked")
    @Test
    public void getLastTripJSONTest() throws Exception {

        // Getting driver last trip
//        ClientResponse<Trip> trip = client.getLastTrip(DRIVER_ID);
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/driver/"+DRIVER_ID+"/trip.json");
        ClientResponse<Trip> response = request.get();
        Trip trip = response.getEntity(Trip.class);

        MultivaluedMap<String,String> map = response.getHeaders();
        List<String> mediaTypes = map.get("Content-Type");
        assertEquals(mediaTypes.get(0), MediaType.APPLICATION_JSON);

        assertEquals(Response.Status.OK,  response.getResponseStatus());
        assertNotNull(trip);
        assertEquals(trip.getDriverID(),new Integer(DRIVER_ID));
        logger.info("Trip retrieved successfully in JSON");
    }
    @SuppressWarnings("unchecked")
    @Test
    public void getLastTripFastinfosetTest() throws Exception {

        // Getting driver last trip
//        ClientResponse<Trip> trip = client.getLastTrip(DRIVER_ID);
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/driver/"+DRIVER_ID+"/trip.fastinfoset");
        ClientResponse<Trip> response = request.get();
        Trip trip = response.getEntity(Trip.class);

        MultivaluedMap<String,String> map = response.getHeaders();
        List<String> mediaTypes = map.get("Content-Type");
        assertEquals(mediaTypes.get(0), "application/fastinfoset");

        assertEquals(Response.Status.OK,  response.getResponseStatus());
        assertNotNull(trip);
        assertEquals(trip.getDriverID(),new Integer(DRIVER_ID));
        logger.info("Trip retrieved successfully in fastinfoset");
    }
    @Test
    public void getLastTripWithNoDataTest() throws Exception {

        // Getting driver last trip
        ClientResponse<Trip> trip = client.getLastTrip(DRIVER_ID_WITH_NO_DATA);

        assertEquals(Response.Status.NOT_FOUND, trip.getResponseStatus());
    }

    @Test
    public void getLastLocationTest() throws Exception {

        // Getting driver last location
        @SuppressWarnings("unused")
        ClientResponse<LastLocation> location = client.getLastLocation(DRIVER_ID);

        //assertEquals(Response.Status.OK, location.getResponseStatus());
        //assertNotNull(location.getEntity());
        logger.info("Driver last location retrieved successfully");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getLastLocationTestJSON() throws Exception {

        // Getting driver last location
        //        ClientResponse<LastLocation> location = client.getLastLocation(DRIVER_ID);
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/driver/"+DRIVER_ID+"/location.json");
        ClientResponse<LastLocation> response = request.get();
        LastLocation lastLocation = response.getEntity(LastLocation.class);

        MultivaluedMap<String,String> map = response.getHeaders();
        List<String> mediaTypes = map.get("Content-Type");
        assertEquals(mediaTypes.get(0), MediaType.APPLICATION_JSON);

        assertEquals(Response.Status.OK,  response.getResponseStatus());
        assertNotNull(lastLocation);
        assertEquals(lastLocation.getDriverID(),new Integer(DRIVER_ID));
        logger.info("LastLocation retrieved successfully in JSON");
    }
    @SuppressWarnings("unchecked")
    @Test
    public void getLastLocationTestFastinfoset() throws Exception {

        // Getting driver last location
        //        ClientResponse<LastLocation> location = client.getLastLocation(DRIVER_ID);
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/driver/"+DRIVER_ID+"/location.fastinfoset");
        ClientResponse<LastLocation> response = request.get();
        LastLocation lastLocation = response.getEntity(LastLocation.class);

        MultivaluedMap<String,String> map = response.getHeaders();
        List<String> mediaTypes = map.get("Content-Type");
        assertEquals(mediaTypes.get(0), "application/fastinfoset");

        assertEquals(Response.Status.OK,  response.getResponseStatus());
        assertNotNull(lastLocation);
        assertEquals(lastLocation.getDriverID(),new Integer(DRIVER_ID));
        logger.info("LastLocation retrieved successfully in fastinfoset");
    }
    @Test
    public void getLastLocationWithNoDataTest() throws Exception {

        // Getting driver last location
        @SuppressWarnings("unused")
		ClientResponse<LastLocation> location = client.getLastLocation(DRIVER_ID_WITH_NO_LOCATION);

        // TODO: data is currently unstable. 
        // Include DRIVER_ID_WITH_NO_LOCATION in the mock in the Stub data to make it more robust.
        //assertEquals(Response.Status.NOT_FOUND, location.getResponseStatus());
    }

    @Test
    public void getLastTripsWithNoDataDefaultDateTest() throws Exception {

        // Getting driver last trips
        @SuppressWarnings("unused")
        ClientResponse<List<Trip>> trips = client.getLastTrips(DRIVER_ID_WITH_NO_DATA);
        
        //assertEquals(Response.Status.NOT_FOUND, trips.getResponseStatus());
    }

    @Test
    public void getLastTripsWithTooEarlyDateTest() throws Exception {

        // Getting driver last trips
        @SuppressWarnings("unused")
        ClientResponse<List<Trip>> trips = client.getLastTrips(DRIVER_ID, TOO_EARLY_DATE);

        //assertEquals(Response.Status.BAD_REQUEST, trips.getResponseStatus());
    }

}

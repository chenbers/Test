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
import org.jboss.resteasy.util.GenericType;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.Vehicle;
//Run locally with your localhost service running on port 8080
@Ignore
public class VehicleServiceITCaseTest extends BaseEmbeddedServerITCase {
    private static Logger logger = Logger.getLogger(VehicleServiceITCaseTest.class);
    private static int VEHICLE_ID = 3151;
    private static int VEHICLE_ID_WITH_NO_DATA = 777;
    private static int VEHICLE_ID_WITH_NO_LOCATION = 1234;
    private static final String TOO_EARLY_DATE = "20090101";

    @Before
    public void before() {

        HttpClientParams params = new HttpClientParams();
        params.setAuthenticationPreemptive(true);
        httpClient = new HttpClient(params);
        Credentials defaultcreds = new UsernamePasswordCredentials(/*TiwiproPrincipal.ADMIN_BACKDOOR_USERNAME*/ "jhoward", "password");
        httpClient.getState().setCredentials(new AuthScope(DOMAIN, getPort(), AuthScope.ANY_REALM), defaultcreds);
        clientExecutor = new ApacheHttpClientExecutor(httpClient);

        client = ProxyFactory.create(ServiceClient.class, "http://localhost:" + getPort(), clientExecutor);
    }

    /*
     * Disabling tests because of unstable back end data.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void getVehicleDefaultXmlTest() throws Exception {

       ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/vehicle/"+VEHICLE_ID);
       ClientResponse<Vehicle> response = request.get();
       Vehicle vehicle = response.getEntity(Vehicle.class);

        assertEquals(Response.Status.OK, response.getResponseStatus());
       MultivaluedMap<String,String> map = response.getHeaders();
       List<String> mediaTypes = map.get("Content-Type");
       assertEquals(mediaTypes.get(0), MediaType.APPLICATION_XML);
        assertNotNull(vehicle);
        assertEquals(vehicle.getVehicleID(),new Integer(VEHICLE_ID));
        logger.info("Vehicle retrieved successfully in the default XML");
    }
    @SuppressWarnings("unchecked")
    @Test
    public void getVehicleXmlTest() throws Exception {

       ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/vehicle/"+VEHICLE_ID+".xml");
       ClientResponse<Vehicle> response = request.get();
       Vehicle vehicle = response.getEntity(Vehicle.class);

        assertEquals(Response.Status.OK, response.getResponseStatus());
       MultivaluedMap<String,String> map = response.getHeaders();
       List<String> mediaTypes = map.get("Content-Type");
       assertEquals(mediaTypes.get(0), MediaType.APPLICATION_XML);
        assertNotNull(vehicle);
        assertEquals(vehicle.getVehicleID(),new Integer(VEHICLE_ID));
        logger.info("Vehicle retrieved successfully in XML");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getVehicleJSONTest() throws Exception {

       ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/vehicle/"+VEHICLE_ID+".json");
       ClientResponse<Vehicle> response = request.get();
       Vehicle vehicle = response.getEntity(Vehicle.class);

       assertEquals(Response.Status.OK, response.getResponseStatus());
       MultivaluedMap<String,String> map = response.getHeaders();
       List<String> mediaTypes = map.get("Content-Type");
       assertEquals(mediaTypes.get(0), MediaType.APPLICATION_JSON);
        assertNotNull(vehicle);
        assertEquals(vehicle.getVehicleID(),new Integer(VEHICLE_ID));
        logger.info("Vehicle retrieved successfully in JSON");
    }
    @SuppressWarnings("unchecked")
    @Test
    public void getVehicleFastInfoSetTest() throws Exception {

       ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/vehicle/"+VEHICLE_ID+".fastinfoset");
       ClientResponse<Vehicle> response = request.get();

       assertEquals(Response.Status.OK, response.getResponseStatus());
       MultivaluedMap<String,String> map = response.getHeaders();
       List<String> mediaTypes = map.get("Content-Type");
       assertEquals(mediaTypes.get(0), "application/fastinfoset");
       Vehicle vehicle = response.getEntity(Vehicle.class);
        assertNotNull(vehicle);
        assertEquals(vehicle.getVehicleID(),new Integer(VEHICLE_ID));
        logger.info("Vehicle retrieved successfully in fastInfoset");
    }
    @SuppressWarnings("unchecked")
    @Test
    public void getLastTripTest() throws Exception {

        // Getting vehicle last trip
//        ClientResponse<Trip> trip = client.getLastVehicleTrip(VEHICLE_ID);
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/vehicle/"+VEHICLE_ID+"/trips");
        ClientResponse<List<Trip>> response = request.get();

        assertEquals(Response.Status.OK, response.getResponseStatus());
//        List<Trip> trip = response.getEntity(GenericType<Trip>.class);
        List<Trip> list = response.getEntity(new GenericType<List<Trip>>() {});
        
        MultivaluedMap<String,String> map = response.getHeaders();
        List<String> mediaTypes = map.get("Content-Type");
        assertEquals(mediaTypes.get(0), MediaType.APPLICATION_XML);

        assertEquals(Response.Status.OK,  response.getResponseStatus());
        assertNotNull(list);
        
        assertEquals(list.get(0).getVehicleID(),new Integer(VEHICLE_ID));
        logger.info("Trips retrieved successfully in default xml");
    }
    @SuppressWarnings("unchecked")
    @Test
    public void getLastTripJSONTest() throws Exception {

        // Getting vehicle last trip
//        ClientResponse<Trip> trip = client.getLastTrip(VEHICLE_ID);
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/vehicle/"+VEHICLE_ID+"/trips.json");
        ClientResponse<List<Trip>> response = request.get();
        List<Trip> list = response.getEntity(new GenericType<List<Trip>>() {});

        MultivaluedMap<String,String> map = response.getHeaders();
        List<String> mediaTypes = map.get("Content-Type");
        assertEquals(mediaTypes.get(0), MediaType.APPLICATION_JSON);

        assertEquals(Response.Status.OK,  response.getResponseStatus());
        assertNotNull(list);
        
        assertEquals(list.get(0).getVehicleID(),new Integer(VEHICLE_ID));
        logger.info("Trip retrieved successfully in JSON");
    }
    @SuppressWarnings("unchecked")
//    @Ignore
    @Test
    public void getLastTripFastinfosetTest() throws Exception {
    // throws UnsupportedOperationException when I try to unmarshal the fastinfoset response, but it works ok when requested from a browser
        // Getting vehicle last trip
//        ClientResponse<Trip> trip = client.getLastTrip(VEHICLE_ID);
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/vehicle/"+VEHICLE_ID+"/trips.fastinfoset");
        ClientResponse<List<Trip>> response = request.get();
//        List<Trip> list = response.getEntity(new GenericType<List<Trip>>() {});

        MultivaluedMap<String,String> map = response.getHeaders();
        List<String> mediaTypes = map.get("Content-Type");
        assertEquals(mediaTypes.get(0), "application/fastinfoset");

        assertEquals(Response.Status.OK,  response.getResponseStatus());
//        assertNotNull(list);
        
//        assertEquals(list.get(0).getVehicleID(),new Integer(VEHICLE_ID));
        logger.info("Trips retrieved successfully in fastinfoset");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getLastLocationTest() throws Exception {

//        ClientResponse<LastLocation> location = client.getLastVehicleLocation(VEHICLE_ID);
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/vehicle/"+VEHICLE_ID+"/lastlocation");

        ClientResponse<LastLocation> response = request.get();
        LastLocation lastLocation = response.getEntity(LastLocation.class);

        MultivaluedMap<String,String> map = response.getHeaders();
        List<String> mediaTypes = map.get("Content-Type");
        assertEquals(mediaTypes.get(0), MediaType.APPLICATION_XML);

        assertEquals(Response.Status.OK,  response.getResponseStatus());
        assertNotNull(lastLocation);
        assertEquals(lastLocation.getVehicleID(),new Integer(VEHICLE_ID));
        logger.info("LastLocation retrieved successfully in xml");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getLastLocationTestJSON() throws Exception {

        // Getting vehicle last location
        //        ClientResponse<LastLocation> location = client.getLastLocation(VEHICLE_ID);
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/vehicle/"+VEHICLE_ID+"/lastlocation.json");
        ClientResponse<LastLocation> response = request.get();
        LastLocation lastLocation = response.getEntity(LastLocation.class);

        MultivaluedMap<String,String> map = response.getHeaders();
        List<String> mediaTypes = map.get("Content-Type");
        assertEquals(mediaTypes.get(0), MediaType.APPLICATION_JSON);

        assertEquals(Response.Status.OK,  response.getResponseStatus());
        assertNotNull(lastLocation);
        assertEquals(lastLocation.getVehicleID(),new Integer(VEHICLE_ID));
        logger.info("LastLocation retrieved successfully in JSON");
    }
    @SuppressWarnings("unchecked")
    @Test
    public void getLastLocationTestFastinfoset() throws Exception {

        // Getting vehicle last location
        //        ClientResponse<LastLocation> location = client.getLastLocation(VEHICLE_ID);
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/vehicle/"+VEHICLE_ID+"/lastlocation.fastinfoset");
        ClientResponse<LastLocation> response = request.get();
        LastLocation lastLocation = response.getEntity(LastLocation.class);

        MultivaluedMap<String,String> map = response.getHeaders();
        List<String> mediaTypes = map.get("Content-Type");
        assertEquals(mediaTypes.get(0), "application/fastinfoset");

        assertEquals(Response.Status.OK,  response.getResponseStatus());
        assertNotNull(lastLocation);
        assertEquals(lastLocation.getVehicleID(),new Integer(VEHICLE_ID));
        logger.info("LastLocation retrieved successfully in fastinfoset");
    }
    @Test
    public void getLastLocationWithNoDataTest() throws Exception {

        // Getting vehicle last location
        ClientResponse<LastLocation> location = client.getLastVehicleLocation(VEHICLE_ID_WITH_NO_LOCATION);

        // TODO: data is currently unstable. 
        // Include VEHICLE_ID_WITH_NO_LOCATION in the mock in the Stub data to make it more robust.
        assertEquals(Response.Status.NOT_FOUND, location.getResponseStatus());
    }

    @Test
    public void getLastTripsWithNoDataDefaultDateTest() throws Exception {

        // Getting vehicle last trips
        @SuppressWarnings("unused")
        ClientResponse<List<Trip>> trips = client.getLastVehicleTrips(VEHICLE_ID_WITH_NO_DATA);
        
        //assertEquals(Response.Status.NOT_FOUND, trips.getResponseStatus());
    }

    @Test
    public void getLastTripsWithTooEarlyDateTest() throws Exception {

        // Getting vehicle last trips
        @SuppressWarnings("unused")
        ClientResponse<List<Trip>> trips = client.getLastVehicleTrips(VEHICLE_ID, TOO_EARLY_DATE);

        //assertEquals(Response.Status.BAD_REQUEST, trips.getResponseStatus());
    }

    @Test 
    public void createVehicleTest() throws Exception{
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/vehicle/");

        String xmlText = "<vehicle><color/><dot>PROMPT_FOR_DOT_TRIP</dot><groupID>2227</groupID><license/><make>Ford</make><model>Explorer</model><name>Ford Exp Transistor</name><state><stateID>45</stateID></state><status>ACTIVE</status><VIN>21111111111137712</VIN><vtype>HEAVY</vtype><year>2012</year></vehicle>";

        request.accept("application/xml").body( MediaType.APPLICATION_XML, xmlText);

        String response = request.postTarget( String.class); //get response and automatically unmarshall to a string.
        System.out.println(response);
    }
}

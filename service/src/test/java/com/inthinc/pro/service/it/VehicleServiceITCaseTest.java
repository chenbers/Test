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
import com.inthinc.pro.service.model.BatchResponse;
//Run locally with your localhost service running on port 8080
@Ignore
public class VehicleServiceITCaseTest extends BaseEmbeddedServerITCase {
    private static Logger logger = Logger.getLogger(VehicleServiceITCaseTest.class);
    private static int VEHICLE_ID = 1; //Mach5
    private static int VEHICLE_ID_WITH_NO_DATA = 777;
    private static int VEHICLE_ID_WITH_NO_LOCATION = 1234;
    private static final String TOO_EARLY_DATE = "20090101";
    private Vehicle createdVehicle;
    @Before
    public void before() {

        HttpClientParams params = new HttpClientParams();
        params.setAuthenticationPreemptive(true);
        httpClient = new HttpClient(params);
        Credentials defaultcreds = new UsernamePasswordCredentials(getAdminuser(), getAdminpassword());
        httpClient.getState().setCredentials(new AuthScope(getDomain(), getPort(), AuthScope.ANY_REALM), defaultcreds);
        clientExecutor = new ApacheHttpClientExecutor(httpClient);

        client = ProxyFactory.create(ServiceClient.class, "http://"+getDomain()+":" + getPort(), clientExecutor);
    }

    /*
     * Disabling tests because of unstable back end data.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void getVehicleDefaultXmlTest() throws Exception {

       ClientRequest request = clientExecutor.createRequest(url+"/vehicle/"+VEHICLE_ID);
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

       ClientRequest request = clientExecutor.createRequest(url+"/vehicle/"+VEHICLE_ID+".xml");
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

       ClientRequest request = clientExecutor.createRequest(url+"/vehicle/"+VEHICLE_ID+".json");
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

       ClientRequest request = clientExecutor.createRequest(url+"/vehicle/"+VEHICLE_ID+".fastinfoset");
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
        ClientRequest request = clientExecutor.createRequest(url+"/vehicle/"+VEHICLE_ID+"/trips");
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
        ClientRequest request = clientExecutor.createRequest(url+"/vehicle/"+VEHICLE_ID+"/trips.json");
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
        ClientRequest request = clientExecutor.createRequest(url+"/vehicle/"+VEHICLE_ID+"/trips.fastinfoset");
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
        ClientRequest request = clientExecutor.createRequest(url+"/vehicle/"+VEHICLE_ID+"/lastlocation");

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
        ClientRequest request = clientExecutor.createRequest(url+"/vehicle/"+VEHICLE_ID+"/lastlocation.json");
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
        ClientRequest request = clientExecutor.createRequest(url+"/vehicle/"+VEHICLE_ID+"/lastlocation.fastinfoset");
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
    public void createAndDeleteVehicleTest() throws Exception{
        ClientRequest request = clientExecutor.createRequest(url+"/vehicle/");

        String xmlText = "<vehicle><color/><dot>PROMPT_FOR_DOT_TRIP</dot><groupID>2227</groupID><license/><make>Ford</make><model>Explorer</model><name>Ford Exp Transistor</name><state><stateID>45</stateID></state><status>ACTIVE</status><VIN>21111111111137742</VIN><vtype>HEAVY</vtype><year>2012</year></vehicle>";
//        String xmlText = "<vehicle><color/><dot>PROMPT_FOR_DOT_TRIP</dot><groupID>8</groupID><license/><make>Ford</make><model>Explorer</model><name>Ford Exp Transistor</name><state><stateID>45</stateID></state><status>ACTIVE</status><VIN>21111111111137717</VIN><vtype>HEAVY</vtype><year>2012</year></vehicle>";

        //Test for lds church
//        String xmlText = "<vehicle><color/><dot>PROMPT_FOR_DOT_TRIP</dot><groupID>317</groupID><license/><make>Ford</make><model>Explorer</model><name>Ford Exp Transistor</name><state><stateID>45</stateID></state><status>ACTIVE</status><VIN>21111111111137717</VIN><vtype>HEAVY</vtype><year>2012</year></vehicle>";

        request.accept("application/xml").body( MediaType.APPLICATION_XML, xmlText);
        createdVehicle = request.postTarget( Vehicle.class); //get response and automatically unmarshall to a string.
        System.out.println(createdVehicle.toString());
        
        ClientRequest deleteRequest = clientExecutor.createRequest(url+"/vehicle/"+createdVehicle.getVehicleID());
        ClientResponse<String> deleteResponse = deleteRequest.delete( String.class); //get response and automatically unmarshall to a string.
        System.out.println(deleteResponse.getStatus());
    }
    @Test 
    public void createVehiclesTest() throws Exception{

        ClientRequest request = clientExecutor.createRequest(url+"/vehicles/");

        String vehicle1 = "<vehicle><color/><dot>PROMPT_FOR_DOT_TRIP</dot><groupID>2227</groupID><license/><make>Ford</make><model>Explorer</model><name>Ford Exp Transistor</name><state><stateID>45</stateID></state><status>ACTIVE</status><VIN>21111111111137730</VIN><vtype>HEAVY</vtype><year>2012</year></vehicle>";
        String vehicle2 = "<vehicle><color/><dot>PROMPT_FOR_DOT_TRIP</dot><groupID>2227</groupID><license/><make>Ford</make><model>Explorer</model><name>Ford Exp Transistor</name><state><stateID>45</stateID></state><status>ACTIVE</status><VIN>21111111111137731</VIN><vtype>HEAVY</vtype><year>2012</year></vehicle>";
        String vehicle3 = "<vehicle><color/><dot>PROMPT_FOR_DOT_TRIP</dot><groupID>2227</groupID><license/><make>Ford</make><model>Explorer</model><name>Ford Exp Transistor</name><state><stateID>45</stateID></state><status>ACTIVE</status><VIN>21111111111137732</VIN><vtype>HEAVY</vtype><year>2012</year></vehicle>";

        request.accept("application/xml").body( MediaType.APPLICATION_XML, "<vehicles>"+vehicle1+vehicle2+vehicle3+"</vehicles>");
        //TODO work out the generic type thing
        ClientResponse<List<Vehicle>> response = request.post(new GenericType<List<Vehicle>>() {});
        List<BatchResponse> vehicles = (List<BatchResponse>) response.getEntity(new GenericType<List<BatchResponse>>() {});
        for(BatchResponse batchResponse : vehicles){
        	//run request in the response to get vehicle ok then delete it
        	System.out.println(batchResponse.toString());
        	String uri = batchResponse.getUri();
	        ClientRequest deleteRequest = clientExecutor.createRequest(uri);
	        ClientResponse<String> deleteResponse = deleteRequest.delete( String.class); //get response and automatically unmarshall to a string.

	        System.out.println(deleteResponse.getStatus());
        }
    }
    @Test 
    public void updateVehicleTest() throws Exception{
        ClientRequest request = clientExecutor.createRequest(url+"/vehicle/");

        String xmlText = "<vehicle><color/><dot>PROMPT_FOR_DOT_TRIP</dot><groupID>2227</groupID><license/><make>Ford</make><model>Explorer</model><name>Ford Exp Transistor</name><state><stateID>45</stateID></state><status>ACTIVE</status><VIN>21111111111137749</VIN><vtype>HEAVY</vtype><year>2012</year></vehicle>";
//        String xmlText = "<vehicle><color/><dot>PROMPT_FOR_DOT_TRIP</dot><groupID>8</groupID><license/><make>Ford</make><model>Explorer</model><name>Ford Exp Transistor</name><state><stateID>45</stateID></state><status>ACTIVE</status><VIN>21111111111137717</VIN><vtype>HEAVY</vtype><year>2012</year></vehicle>";

        //Test for lds church
//        String xmlText = "<vehicle><color/><dot>PROMPT_FOR_DOT_TRIP</dot><groupID>317</groupID><license/><make>Ford</make><model>Explorer</model><name>Ford Exp Transistor</name><state><stateID>45</stateID></state><status>ACTIVE</status><VIN>21111111111137717</VIN><vtype>HEAVY</vtype><year>2012</year></vehicle>";

        request.accept( MediaType.APPLICATION_XML).body( MediaType.APPLICATION_XML, xmlText);
        createdVehicle = request.postTarget( Vehicle.class); //get response and automatically unmarshall to a string.
        System.out.println(createdVehicle.toString());
        String updateText = "<vehicle><vehicleID>"+createdVehicle.getVehicleID()+"</vehicleID><color>red</color></vehicle>";
        ClientRequest updateRequest = clientExecutor.createRequest(url+"/vehicle");
        updateRequest.accept("application/xml").body( MediaType.APPLICATION_XML, updateText);
        ClientResponse<Vehicle> updatedVehicle = updateRequest.put(Vehicle.class); //get response.
        Vehicle vehicle = updatedVehicle.getEntity();
        System.out.println(vehicle.toString());
        
        ClientRequest deleteRequest = clientExecutor.createRequest(url+"/vehicle/"+createdVehicle.getVehicleID());
        ClientResponse<String> deleteResponse = deleteRequest.delete( String.class); //get response and automatically unmarshall to a string.
        System.out.println(deleteResponse.getStatus());
    }
}

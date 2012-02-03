package com.inthinc.pro.service.it;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.validation.constraints.AssertTrue;
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

import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.GroupScoreWrapper;
import com.inthinc.pro.model.aggregation.GroupTrendWrapper;
import com.inthinc.pro.service.exceptionMappers.BaseExceptionMapper;

/**
 * Integration test for Group Service Implementation.
 */
@Ignore
public class GroupServiceTest extends BaseEmbeddedServerITCase {
    private static Logger logger = Logger.getLogger(GroupServiceTest.class);
    private static int GROUP_ID = 3;
    private ServiceClient client;
    
    @Test
    public void testDummy() {}
    
    @Before
    public void before() {

        HttpClientParams params = new HttpClientParams();
        params.setAuthenticationPreemptive(true);
        httpClient = new HttpClient(params);
        Credentials defaultcreds = new UsernamePasswordCredentials(/*TiwiproPrincipal.ADMIN_BACKDOOR_USERNAME*/ "cjennings", "password");
        httpClient.getState().setCredentials(new AuthScope(getDomain(), getPort(), AuthScope.ANY_REALM), defaultcreds);
        clientExecutor = new ApacheHttpClientExecutor(httpClient);

        client = ProxyFactory.create(ServiceClient.class, "http://localhost:" + getPort(), clientExecutor);
    }
    
    /**
     * Test if Get DriverLocations service returns the correct content and response.
     */
    @Test
    //@Ignore
    public void testGetGroupDriverLocations() {
        logger.info("Testing Get DriverLocations service... ");
      
        ClientResponse<List<DriverLocation>> response = client.getGroupDriverLocations(GROUP_ID);
        logger.info("Get DriverLocations response: " + response.getStatus());
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        
        List<DriverLocation> list = response.getEntity(new GenericType<List<DriverLocation>>() {});
        assertNotNull(list);
        assertFalse(list.isEmpty());
    }

    /**
     * Test if Get DriverLocations service returns 404 response when empty/null list.
     */
    @Test
    public void testGetGroupDriverLocationsNoData() {
        logger.info("Testing Get DriverLocations service when no data... ");
        ClientResponse<List<DriverLocation>> response = client.getGroupDriverLocations(343);
        logger.info("Get DriverLocations response: " + response.getStatus());
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
    /**
     * Test if getDriverScores service.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetGroupDriverScores() {
        logger.info("Testing getDriverScores service");
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/"+GROUP_ID+"/scores/drivers/30");
        try {
            ClientResponse<List<DriverVehicleScoreWrapper>> response = request.get();
//        ClientResponse<List<DriverVehicleScoreWrapper>> response = client.getDriverScores(GROUP_ID, 30);
            logger.info("Get DriverVehicleScoreWrapper response: " + response.getStatus());
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            
            List<DriverVehicleScoreWrapper> list = response.getEntity();
            assertNotNull(list);
            assertFalse(list.isEmpty());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * Test if getDriverScores service.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetGroupVehicleScores() {
        logger.info("Testing getDriverScores service");
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/"+GROUP_ID+"/scores/vehicles/30");
        try {
            ClientResponse<List<DriverVehicleScoreWrapper>> response = request.get();
//        ClientResponse<List<DriverVehicleScoreWrapper>> response = client.getDriverScores(GROUP_ID, 30);
            logger.info("Get DriverVehicleScoreWrapper response: " + response.getStatus());
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            
            List<DriverVehicleScoreWrapper> list = response.getEntity(new GenericType<List<DriverVehicleScoreWrapper>>() {});
            assertNotNull(list);
            assertFalse(list.isEmpty());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * Test if getDriverScores service.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetGroupSubgroupsTrendsDriver() {
        logger.info("Testing getDriverScores service");
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/"+GROUP_ID+"/subgroups/trends/driver/30");
        try {
            ClientResponse<List<GroupTrendWrapper>> response = request.get();
//        ClientResponse<List<DriverVehicleScoreWrapper>> response = client.getDriverScores(GROUP_ID, 30);
            logger.info("Get DriverVehicleScoreWrapper response: " + response.getStatus());
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            
            List<GroupTrendWrapper> list = response.getEntity(new GenericType<List<GroupTrendWrapper>>() {});
            assertNotNull(list);
            assertFalse(list.isEmpty());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * Test if getDriverScores service.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetGroupSubgroupsScoresDriver() {
        logger.info("Testing getDriverScores service");
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/"+GROUP_ID+"/subgroups/scores/driver/30");
        try {
            ClientResponse<List<GroupScoreWrapper>> response = request.get();
//        ClientResponse<List<DriverVehicleScoreWrapper>> response = client.getDriverScores(GROUP_ID, 30);
            logger.info("Get DriverVehicleScoreWrapper response: " + response.getStatus());
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            
            List<GroupScoreWrapper> list = response.getEntity(new GenericType<List<GroupScoreWrapper>>() {});
            assertNotNull(list);
            assertFalse(list.isEmpty());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * Test if getDriverScores service.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetGroupDriverScoresJSON() {
        logger.info("Testing getDriverScores service");
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/"+GROUP_ID+"/scores/drivers/30.json");
        try {
            ClientResponse<List<DriverVehicleScoreWrapper>> response = request.get();
            logger.info("Get DriverVehicleScoreWrapper response: " + response.getStatus());
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            
            List<DriverVehicleScoreWrapper> list = response.getEntity(new GenericType<List<DriverVehicleScoreWrapper>>() {});
            assertNotNull(list);
            assertFalse(list.isEmpty());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * Test if getDriverScores service.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetGroupVehicleScoresJSON() {
        logger.info("Testing getDriverScores service");
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/"+GROUP_ID+"/scores/vehicles/30.json");
        try {
            ClientResponse<List<DriverVehicleScoreWrapper>> response = request.get();
//        ClientResponse<List<DriverVehicleScoreWrapper>> response = client.getDriverScores(GROUP_ID, 30);
            logger.info("Get DriverVehicleScoreWrapper response: " + response.getStatus());
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            
            List<DriverVehicleScoreWrapper> list = response.getEntity(new GenericType<List<DriverVehicleScoreWrapper>>() {});
            assertNotNull(list);
            assertFalse(list.isEmpty());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * Test if getDriverScores service.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetGroupSubgroupsTrendsDriverJSON() {
        logger.info("Testing getDriverScores service");
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/"+GROUP_ID+"/subgroups/trends/driver/30.json");
        try {
            ClientResponse<List<GroupTrendWrapper>> response = request.get();
//        ClientResponse<List<DriverVehicleScoreWrapper>> response = client.getDriverScores(GROUP_ID, 30);
            logger.info("Get DriverVehicleScoreWrapper response: " + response.getStatus());
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            
            List<GroupTrendWrapper> list = response.getEntity(new GenericType<List<GroupTrendWrapper>>() {});
            assertNotNull(list);
            assertFalse(list.isEmpty());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * Test if getDriverScores service.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetGroupSubgroupsScoresDriverJSON() {
        logger.info("Testing getDriverScores service");
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/"+GROUP_ID+"/subgroups/scores/driver/30.json");
        try {
            ClientResponse<List<GroupScoreWrapper>> response = request.get();
//        ClientResponse<List<DriverVehicleScoreWrapper>> response = client.getDriverScores(GROUP_ID, 30);
            logger.info("Get DriverVehicleScoreWrapper response: " + response.getStatus());
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            
            MultivaluedMap<String,String> map = response.getHeaders();
            List<String> mediaTypes = map.get("Content-Type");
            assertEquals(mediaTypes.get(0), MediaType.APPLICATION_JSON);

            List<GroupScoreWrapper> list = response.getEntity(new GenericType<List<GroupScoreWrapper>>() {});
            assertNotNull(list);
            assertFalse(list.isEmpty());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Test 
    public void createGroupTest() throws Exception{
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/");
        StringBuilder xmlString = new StringBuilder()
        .append("<group>")
        .append("<accountID>2</accountID>")
        .append("<name>service created 6 Jason Wimmer test</name>")
        .append("<parentID>5058</parentID>")
        .append("<status>GROUP_ACTIVE</status>")
        .append("<type>DIVISION</type>")
        //.append("<managerID>3</managerID>")
//        .append("<mapZoom>12</mapZoom>")
//        .append("<mapLat>40.711</mapLat>")
//        .append("<mapLng>-111.992</mapLng>")
//        .append("<zoneRev>120</zoneRev>")
//        .append("<addressID></addressID")
//        .append("<address></address")
        .append("</group>");
        
        request.accept("application/xml").body( MediaType.APPLICATION_XML, xmlString.toString());
        //request.se
        String response = request.postTarget( String.class); //get response and automatically unmarshall to a string.
        System.out.println(response);
    }
    
    @Test
    public void getGroupDriverScoresForMonthTest(){
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/"+GROUP_ID+"/scores/drivers/month/January");
        try {
            ClientResponse<List<DriverVehicleScoreWrapper>> response = request.get();
            logger.info("Get DriverVehicleScoreWrapper response: " + response.getStatus());
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            
            List<DriverVehicleScoreWrapper> list = response.getEntity(new GenericType<List<DriverVehicleScoreWrapper>>() {});
            assertNotNull(list);
            assertFalse(list.isEmpty());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    	
    }
    @Test
    public void getGroupDriverScoresForCurrentMonthTest(){
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/"+GROUP_ID+"/scores/drivers/month");
        try {
            ClientResponse<List<DriverVehicleScoreWrapper>> response = request.get();
            logger.info("Get DriverVehicleScoreWrapper response: " + response.getStatus());
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            
            List<DriverVehicleScoreWrapper> list = response.getEntity(new GenericType<List<DriverVehicleScoreWrapper>>() {});
            assertNotNull(list);
            assertFalse(list.isEmpty());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    	
    }
    @Test
    public void getSubGroupDriverScoresForMonthTest(){
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/"+GROUP_ID+"/subgroups/scores/driver/month/January");
        try {
            ClientResponse<List<GroupScoreWrapper>> response = request.get();
            logger.info("Get GroupScoreWrapper response: " + response.getStatus());
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            
            List<GroupScoreWrapper> list = response.getEntity(new GenericType<List<GroupScoreWrapper>>() {});
            assertNotNull(list);
            assertFalse(list.isEmpty());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    	
    }
    @Test
    public void getSubGroupDriverScoresForCurrentMonthTest(){
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/"+GROUP_ID+"/subgroups/scores/driver/month");
        try {
            ClientResponse<List<GroupScoreWrapper>> response = request.get();
            logger.info("Get GroupScoreWrapper response: " + response.getStatus());
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            
            List<GroupScoreWrapper> list = response.getEntity(new GenericType<List<GroupScoreWrapper>>() {});
            assertNotNull(list);
            assertFalse(list.isEmpty());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    	
    }
    
    /**
     * DE7336: Web services allows group to be moved from one account to another.
     * When updating a group via web services, you can move a group from one account to another even though that group 
     * shouldn't be able to be moved. 
     * 
     * @throws Exception
     */
    @Test 
    public void updateGroupTest() throws Exception{
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/");
        String expectedErrorMessage = "Changing the accountID on a group is not allowed";
        Integer accountID = 2; //this is a QA accountID, or a DEV accountID
        Integer createdGroupID = null;
        Integer newAccountID = 397;
        String description = "updateGroupTest description";
        String name = "updateGroupTest name";
        String type = "TEAM";
        //Integer parentID = 4917; //this is a QA parentID
        Integer parentID = 4; //this is a DEV parentID
        
        StringBuilder xmlString = new StringBuilder()
        .append("<group>")
        .append("<accountID>"+accountID+"</accountID>")
        .append("<description>"+description+"</description>")
        .append("<name>"+name+"</name>")
        .append("<type>"+type+"</type>")
        .append("<parentID>"+parentID+"</parentID>") 
        .append("</group>");
        
        try{
        
	        request.accept("application/xml").body( MediaType.APPLICATION_XML, xmlString.toString());
	//        String response = request.postTarget( String.class); //get response and automatically unmarshall to a string.
	//        assertFalse("response should not include 'Error's", response.contains("Error"));
	//        assertFalse("response should not include 'Exceptions's", response.contains("Exception"));
	//        assertTrue("response should be a group",response.contains("<group>"));
	        
	        
	        Group response = request.post(Group.class).getEntity();
	        System.out.println(response);
	        assertEquals(accountID, response.getAccountID());
	        assertEquals(parentID, response.getParentID());
	        createdGroupID = response.getGroupID();
	        
	        xmlString = new StringBuilder()
	        .append("<group>")
	        .append("<accountID>"+newAccountID+"</accountID>")
	        .append("<description>"+description+"</description>")
			.append("<groupID>"+createdGroupID+"</groupID>")
			.append("<name>"+name+"</name>")
			.append("<parentID>"+parentID+"</parentID>")
			.append("<type>"+type+"</type>")
			.append("</group>");
	        
	        request.accept("application/xml").body( MediaType.APPLICATION_XML, xmlString.toString());
	        //request.accept(MediaType.TEXT_HTML_TYPE).body(MediaType.TEXT_HTML_TYPE, xmlString.toString());
	        ClientResponse<Group> clientResponse = request.put(Group.class); 
	        
	        assertEquals(expectedErrorMessage, clientResponse.getHeaders().get(BaseExceptionMapper.HEADER_ERROR_MESSAGE).get(0));
	        assertEquals(accountID, response.getAccountID());
	        assertNotSame("AccountID should NOT change",newAccountID, response.getAccountID());
	
	        System.out.println(response);
        } finally {
	        if(createdGroupID != null){
	        	request = clientExecutor.createRequest("http://localhost:8080/service/api/group/"+createdGroupID);
		        request.accept("application/xml");
		        request.delete();
	        }
        }

    }
}

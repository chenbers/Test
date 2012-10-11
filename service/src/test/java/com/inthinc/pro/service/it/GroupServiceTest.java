package com.inthinc.pro.service.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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
import org.jboss.resteasy.plugins.providers.jaxb.JAXBUnmarshalException;
import org.jboss.resteasy.util.GenericType;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.dao.hessian.exceptions.GenericHessianException;
import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.User;
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

    private static Integer accountID = 2; // works for both dv and qa
    private static Integer nonExistentManagerID = 99999;

    // dev option
    // private static int GROUP_ID = 3;// dev
    // private static Integer newAccountID = 397;
    // private static Integer parentID = 4;
    // private static Integer nonExistentParentID = 4917;
    // private static Integer managerIDInOtherAccount = 1; //dev - this person is in account 1
    // private static Integer deletedManagerID = 18779; //DEV - this person is in account 3793
    // private static Integer parentIDOfDeletedManager = 5813; //this is a DEV parentID
    // private static Integer accountIDOfDeletedManager = 3793; //this is a DEV accountID
    // private static Integer driverID = 30;
    // private static Integer vehicleID = 30;

    // qa option
    private static int GROUP_ID = 2;
    private static Integer newAccountID = 397;
    private static Integer parentID = 4917;
    private static Integer nonExistentParentID = 4;
    private static Integer managerIDInOtherAccount = 1032; // QA - this person is in account 1
    private static Integer deletedManagerID = 81034; // QA - this person is in account 2
    private static Integer parentIDOfDeletedManager = 4917;
    private static Integer accountIDOfDeletedManager = 2;
    private static Integer managerToDelete = 3;
    private static Integer driverID = 10;
    private static Integer vehicleID = 305;
    
    private ServiceClient client;
    private static final String INVALID_MANAGER = "Manager ID not found:";
    private static final String INVALID_PARENT = "Parent group ID not found:";
    private static final String FORBIDDEN_ACCOUNT_CHANGE = "Changing the accountID on a group is not allowed:";
    @Test
    public void testDummy() {}

    @Before
    public void before() {

        HttpClientParams params = new HttpClientParams();
        params.setAuthenticationPreemptive(true);
        httpClient = new HttpClient(params);
        Credentials defaultcreds = new UsernamePasswordCredentials(/* TiwiproPrincipal.ADMIN_BACKDOOR_USERNAME */"cjennings", "password");
        httpClient.getState().setCredentials(new AuthScope(getDomain(), getPort(), AuthScope.ANY_REALM), defaultcreds);
        clientExecutor = new ApacheHttpClientExecutor(httpClient);

        client = ProxyFactory.create(ServiceClient.class, "http://localhost:" + getPort(), clientExecutor);
    }

    /**
     * Test if Get DriverLocations service returns the correct content and response.
     */
    @Test
    // @Ignore
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
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/" + GROUP_ID + "/scores/drivers/"+driverID);
        try {
            ClientResponse<List<DriverVehicleScoreWrapper>> response = request.get();
            // ClientResponse<List<DriverVehicleScoreWrapper>> response = client.getDriverScores(GROUP_ID, 30);
            logger.info("Get DriverVehicleScoreWrapper response: " + response.getStatus());
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

            List<DriverVehicleScoreWrapper> list = response.getEntity();
            assertNotNull(list);
            assertFalse(list.isEmpty());
        } catch (Exception e) {
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
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/" + GROUP_ID + "/scores/vehicles/"+vehicleID);
        try {
            ClientResponse<List<DriverVehicleScoreWrapper>> response = request.get();
            // ClientResponse<List<DriverVehicleScoreWrapper>> response = client.getDriverScores(GROUP_ID, 30);
            logger.info("Get DriverVehicleScoreWrapper response: " + response.getStatus());
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

            List<DriverVehicleScoreWrapper> list = response.getEntity(new GenericType<List<DriverVehicleScoreWrapper>>() {});
            assertNotNull(list);
            assertFalse(list.isEmpty());
        } catch (Exception e) {
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
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/" + GROUP_ID + "/subgroups/trends/driver/"+driverID);
        try {
            ClientResponse<List<GroupTrendWrapper>> response = request.get();
            // ClientResponse<List<DriverVehicleScoreWrapper>> response = client.getDriverScores(GROUP_ID, 30);
            logger.info("Get DriverVehicleScoreWrapper response: " + response.getStatus());
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

            List<GroupTrendWrapper> list = response.getEntity(new GenericType<List<GroupTrendWrapper>>() {});
            assertNotNull(list);
            assertFalse(list.isEmpty());
        } catch (Exception e) {
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
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/" + GROUP_ID + "/subgroups/scores/driver/"+driverID);
        try {
            ClientResponse<List<GroupScoreWrapper>> response = request.get();
            // ClientResponse<List<DriverVehicleScoreWrapper>> response = client.getDriverScores(GROUP_ID, 30);
            logger.info("Get DriverVehicleScoreWrapper response: " + response.getStatus());
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

            List<GroupScoreWrapper> list = response.getEntity(new GenericType<List<GroupScoreWrapper>>() {});
            assertNotNull(list);
            assertFalse(list.isEmpty());
        } catch (Exception e) {
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
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/" + GROUP_ID + "/scores/drivers/"+driverID+".json");
        try {
            ClientResponse<List<DriverVehicleScoreWrapper>> response = request.get();
            logger.info("Get DriverVehicleScoreWrapper response: " + response.getStatus());
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

            List<DriverVehicleScoreWrapper> list = response.getEntity(new GenericType<List<DriverVehicleScoreWrapper>>() {});
            assertNotNull(list);
            assertFalse(list.isEmpty());
        } catch (Exception e) {
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
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/" + GROUP_ID + "/scores/vehicles/"+vehicleID+".json");
        try {
            ClientResponse<List<DriverVehicleScoreWrapper>> response = request.get();
            // ClientResponse<List<DriverVehicleScoreWrapper>> response = client.getDriverScores(GROUP_ID, 30);
            logger.info("Get DriverVehicleScoreWrapper response: " + response.getStatus());
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

            List<DriverVehicleScoreWrapper> list = response.getEntity(new GenericType<List<DriverVehicleScoreWrapper>>() {});
            assertNotNull(list);
            assertFalse(list.isEmpty());
        } catch (Exception e) {
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
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/" + GROUP_ID + "/subgroups/trends/driver/"+driverID+".json");
        try {
            ClientResponse<List<GroupTrendWrapper>> response = request.get();
            // ClientResponse<List<DriverVehicleScoreWrapper>> response = client.getDriverScores(GROUP_ID, 30);
            logger.info("Get DriverVehicleScoreWrapper response: " + response.getStatus());
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

            List<GroupTrendWrapper> list = response.getEntity(new GenericType<List<GroupTrendWrapper>>() {});
            assertNotNull(list);
            assertFalse(list.isEmpty());
        } catch (Exception e) {
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
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/" + GROUP_ID + "/subgroups/scores/driver/"+driverID+".json");
        try {
            ClientResponse<List<GroupScoreWrapper>> response = request.get();
            // ClientResponse<List<DriverVehicleScoreWrapper>> response = client.getDriverScores(GROUP_ID, 30);
            logger.info("Get DriverVehicleScoreWrapper response: " + response.getStatus());
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

            MultivaluedMap<String, String> map = response.getHeaders();
            List<String> mediaTypes = map.get("Content-Type");
            assertEquals(mediaTypes.get(0), MediaType.APPLICATION_JSON);

            List<GroupScoreWrapper> list = response.getEntity(new GenericType<List<GroupScoreWrapper>>() {});
            assertNotNull(list);
            assertFalse(list.isEmpty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createGroupTest() throws Exception {
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/");
        StringBuilder xmlString = new StringBuilder().append("<group>").append("<accountID>" + accountID + "</accountID>").append("<name>service created 6 Jason Wimmer test</name>")
                .append("<parentID>5058</parentID>").append("<status>GROUP_ACTIVE</status>").append("<type>DIVISION</type>").append("<managerID>3</managerID>").append("<mapZoom>12</mapZoom>")
                .append("<mapLat>40.711</mapLat>").append("<mapLng>-111.992</mapLng>").append("<zoneRev>120</zoneRev>").append("<addressID></addressID").append("<address></address")
                .append("</group>");

        request.accept("application/xml").body(MediaType.APPLICATION_XML, xmlString.toString());
        // request.se
        String response = request.postTarget(String.class); // get response and automatically unmarshall to a string.
        System.out.println(response);
    }

    @Test
    public void getGroupDriverScoresForMonthTest() {
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/" + GROUP_ID + "/scores/drivers/month/January");
        try {
            ClientResponse<List<DriverVehicleScoreWrapper>> response = request.get();
            logger.info("Get DriverVehicleScoreWrapper response: " + response.getStatus());
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

            List<DriverVehicleScoreWrapper> list = response.getEntity(new GenericType<List<DriverVehicleScoreWrapper>>() {});
            assertNotNull(list);
            assertFalse(list.isEmpty());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getGroupDriverScoresForCurrentMonthTest() {
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/" + GROUP_ID + "/scores/drivers/month");
        try {
            ClientResponse<List<DriverVehicleScoreWrapper>> response = request.get();
            logger.info("Get DriverVehicleScoreWrapper response: " + response.getStatus());
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

            List<DriverVehicleScoreWrapper> list = response.getEntity(new GenericType<List<DriverVehicleScoreWrapper>>() {});
            assertNotNull(list);
            assertFalse(list.isEmpty());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getSubGroupDriverScoresForMonthTest() {
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/" + GROUP_ID + "/subgroups/scores/driver/month/January");
        try {
            ClientResponse<List<GroupScoreWrapper>> response = request.get();
            logger.info("Get GroupScoreWrapper response: " + response.getStatus());
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

            List<GroupScoreWrapper> list = response.getEntity(new GenericType<List<GroupScoreWrapper>>() {});
            assertNotNull(list);
            assertFalse(list.isEmpty());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getSubGroupDriverScoresForCurrentMonthTest() {
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/" + GROUP_ID + "/subgroups/scores/driver/month");
        try {
            ClientResponse<List<GroupScoreWrapper>> response = request.get();
            logger.info("Get GroupScoreWrapper response: " + response.getStatus());
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

            List<GroupScoreWrapper> list = response.getEntity(new GenericType<List<GroupScoreWrapper>>() {});
            assertNotNull(list);
            assertFalse(list.isEmpty());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * DE7336: Web services allows group to be moved from one account to another. When updating a group via web services, you can move a group from one account to another even
     * though that group shouldn't be able to be moved.
     * 
     * @throws Exception
     */
    @Test
    public void updateGroupTest_changeAccount() throws Exception {
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/");
        Integer createdGroupID = null;
        String description = "updateGroupTest description";
        String name = "updateGroupTest name";
        String type = "TEAM";

        StringBuilder xmlString = new StringBuilder().append("<group>").append("<accountID>" + accountID + "</accountID>").append("<description>" + description + "</description>")
                .append("<name>" + name + "</name>").append("<type>" + type + "</type>").append("<parentID>" + parentID + "</parentID>").append("</group>");

        try {

            request.accept("application/xml").body(MediaType.APPLICATION_XML, xmlString.toString());
            // String response = request.postTarget( String.class); //get response and automatically unmarshall to a string.
            // assertFalse("response should not include 'Error's", response.contains("Error"));
            // assertFalse("response should not include 'Exceptions's", response.contains("Exception"));
            // assertTrue("response should be a group",response.contains("<group>"));

            Group response = request.post(Group.class).getEntity();
            System.out.println(response);
            assertEquals(accountID, response.getAccountID());
            assertEquals(parentID, response.getParentID());
            createdGroupID = response.getGroupID();

            xmlString = new StringBuilder().append("<group>").append("<accountID>" + newAccountID + "</accountID>").append("<description>" + description + "</description>")
                    .append("<groupID>" + createdGroupID + "</groupID>").append("<name>" + name + "</name>").append("<parentID>" + parentID + "</parentID>").append("<type>" + type + "</type>")
                    .append("</group>");

            request.accept("application/xml").body(MediaType.APPLICATION_XML, xmlString.toString());
            // request.accept(MediaType.TEXT_HTML_TYPE).body(MediaType.TEXT_HTML_TYPE, xmlString.toString());
            ClientResponse<Group> clientResponse = request.put(Group.class);

            assertTrue(clientResponse.getHeaders().get(BaseExceptionMapper.HEADER_ERROR_MESSAGE).get(0).contains(FORBIDDEN_ACCOUNT_CHANGE));
            assertEquals(accountID, response.getAccountID());
            assertNotSame("AccountID should NOT change", newAccountID, response.getAccountID());

            System.out.println(response);
        } catch (GenericHessianException ghe) {
            System.out.println(ghe.getErrorCode());
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        } finally {
            if (createdGroupID != null) {
                request = clientExecutor.createRequest("http://localhost:8080/service/api/group/" + createdGroupID);
                request.accept("application/xml");
                request.delete();
            }
        }

    }

    /**
     * DE7336: Web services allows group to be moved from one account to another. When updating a group via web services, you can move a group from one account to another even
     * though that group shouldn't be able to be moved.
     * 
     * @throws Exception
     */
    @Test
    public void updateGroupTest_harmlessChange() throws Exception {
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/");
        Integer createdGroupID = null;
        String description = "updateGroupTest description";
        String name = "updateGroupTest name";
        String type = "TEAM";

        StringBuilder xmlString = new StringBuilder().append("<group>").append("<accountID>" + accountID + "</accountID>").append("<description>" + description + "</description>")
                .append("<name>" + name + "</name>").append("<type>" + type + "</type>").append("<parentID>" + parentID + "</parentID>").append("</group>");

        try {

            request.accept("application/xml").body(MediaType.APPLICATION_XML, xmlString.toString());

            Group response = request.post(Group.class).getEntity();
            System.out.println(response);
            assertEquals(accountID, response.getAccountID());
            assertEquals(parentID, response.getParentID());
            createdGroupID = response.getGroupID();

            xmlString = new StringBuilder().append("<group>").append("<accountID>" + accountID + "</accountID>").append("<description>" + description + "</description>")
                    .append("<groupID>" + createdGroupID + "</groupID>").append("<name>" + name + "</name>").append("<parentID>" + parentID + "</parentID>").append("<type>" + type + "</type>")
                    .append("</group>");

            request.accept("application/xml").body(MediaType.APPLICATION_XML, xmlString.toString());
            // request.accept(MediaType.TEXT_HTML_TYPE).body(MediaType.TEXT_HTML_TYPE, xmlString.toString());
            ClientResponse<Group> clientResponse = request.put(Group.class);

            assertEquals(accountID, clientResponse.getEntity().getAccountID());
            assertNotSame("AccountID should NOT change", newAccountID, response.getAccountID());

            System.out.println(response);
        } finally {
            if (createdGroupID != null) {
                request = clientExecutor.createRequest("http://localhost:8080/service/api/group/" + createdGroupID);
                request.accept("application/xml");
                request.delete();
            }
        }
    }

    /**
     * DE7277: Web service createGroup error 307 A Database execute call failed [dev: jWimmer] [test: ?]
     * 
     * web service deletions was leaving orphaned groups (with status = 0)) this test attempts to delete a group that has at least one group under it if things are working now, the
     * delete should NOT occur, there should be no orphans, and an appropriate error message should get sent back
     * 
     * @throws Exception
     */
    @Test
    public void deleteGroupTest_tryToCreateOrphanGroup() throws Exception {
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/");
        Integer accountID = 2; // this is a DEV accountID
        Integer parentGroupID = null;
        Integer childGroupID = null;
        String description = "de7277 parent group ";
        String name = "DE7277 parent group";
        String type = "DIVISION";

        StringBuilder xmlString = new StringBuilder().append("<group>").append("<accountID>" + accountID + "</accountID>").append("<description>" + description + "</description>")
                .append("<name>" + name + "</name>").append("<type>" + type + "</type>").append("<parentID>" + parentID + "</parentID>").append("</group>");

        try {

            request.accept("application/xml").body(MediaType.APPLICATION_XML, xmlString.toString());
            // String response = request.postTarget( String.class); //get response and automatically unmarshall to a string.
            // assertFalse("response should not include 'Error's", response.contains("Error"));
            // assertFalse("response should not include 'Exceptions's", response.contains("Exception"));
            // assertTrue("response should be a group",response.contains("<group>"));

            Group response = request.post(Group.class).getEntity();
            System.out.println(response);
            assertEquals(accountID, response.getAccountID());
            assertEquals(parentID, response.getParentID());
            parentGroupID = response.getGroupID();

            xmlString = new StringBuilder().append("<group>").append("<accountID>" + accountID + "</accountID>").append("<description>de7277 child group</description>")
                    .append("<name>de7277 child group</name>").append("<parentID>" + parentGroupID + "</parentID>").append("<type>" + type + "</type>").append("</group>");

            request.accept("application/xml").body(MediaType.APPLICATION_XML, xmlString.toString());
            // request.accept(MediaType.TEXT_HTML_TYPE).body(MediaType.TEXT_HTML_TYPE, xmlString.toString());
            response = request.post(Group.class).getEntity();
            childGroupID = response.getGroupID();

            request = clientExecutor.createRequest("http://localhost:8080/service/api/group/" + parentGroupID);
            request.accept("application/xml");
            ClientResponse deleteParentResponse = request.delete();
            assertTrue("deleting parent should fail and return status=400 (Bad Request)", Status.BAD_REQUEST.getStatusCode() == deleteParentResponse.getStatus());

            System.out.println(deleteParentResponse);
        } finally {
            if (childGroupID != null) {
                request = clientExecutor.createRequest("http://localhost:8080/service/api/group/" + childGroupID);
                request.accept("application/xml");
                ClientResponse deleteChildResponse = request.delete();
                assertTrue("warning test cleanup failed?", (Status.OK.getStatusCode() == deleteChildResponse.getStatus()));
            }
            if (parentGroupID != null) {
                request = clientExecutor.createRequest("http://localhost:8080/service/api/group/" + parentGroupID);
                request.accept("application/xml");
                ClientResponse deleteParentResponse = request.delete();
                assertTrue("warning test cleanup failed?", (Status.OK.getStatusCode() == deleteParentResponse.getStatus()));

            }
        }
    }

    @Test
    public void updateGroupTest_changeToNonExistentParent() throws Exception {
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/");
        String expectedErrorMessage = "parentID:" + nonExistentParentID + " doesn't exist.";
        Integer createdGroupID = null;
        String description = "updateGroupTest description";
        String name = "updateGroupTest name";
        String type = "TEAM";

        StringBuilder xmlString = new StringBuilder().append("<group>").append("<accountID>" + accountID + "</accountID>").append("<description>" + description + "</description>")
                .append("<name>" + name + "</name>").append("<type>" + type + "</type>").append("<parentID>" + parentID + "</parentID>").append("</group>");

        try {

            request.accept("application/xml").body(MediaType.APPLICATION_XML, xmlString.toString());
            // String response = request.postTarget( String.class); //get response and automatically unmarshall to a string.
            // assertFalse("response should not include 'Error's", response.contains("Error"));
            // assertFalse("response should not include 'Exceptions's", response.contains("Exception"));
            // assertTrue("response should be a group",response.contains("<group>"));

            Group response = request.post(Group.class).getEntity();
            System.out.println(response);
            assertEquals(accountID, response.getAccountID());
            assertEquals(parentID, response.getParentID());
            createdGroupID = response.getGroupID();

            xmlString = new StringBuilder().append("<group>").append("<accountID>" + accountID + "</accountID>").append("<description>" + description + "</description>")
                    .append("<groupID>" + createdGroupID + "</groupID>").append("<name>" + name + "</name>").append("<parentID>" + nonExistentParentID + "</parentID>")
                    .append("<type>" + type + "</type>").append("</group>");

            request.accept("application/xml").body(MediaType.APPLICATION_XML, xmlString.toString());
            // request.accept(MediaType.TEXT_HTML_TYPE).body(MediaType.TEXT_HTML_TYPE, xmlString.toString());
            ClientResponse<Group> clientResponse = request.put(Group.class);
            assertFalse(clientResponse.getStatus() == Status.OK.getStatusCode());

            assertTrue(clientResponse.getHeaders().get(BaseExceptionMapper.HEADER_ERROR_MESSAGE).get(0).contains(INVALID_PARENT));

            System.out.println(response);
        } finally {
            if (createdGroupID != null) {
                clientExecutor.createRequest("http://localhost:8080/service/api/group/" + createdGroupID).accept("application/xml").delete();
            }
        }

    }

    @Test
    public void createGroupTest_createToNonExistentParent() throws Exception {
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/");
        String description = "updateGroupTest description";
        String name = "updateGroupTest name";
        String type = "TEAM";

        StringBuilder xmlString = new StringBuilder().append("<group>").append("<accountID>" + accountID + "</accountID>").append("<description>" + description + "</description>")
                .append("<name>" + name + "</name>").append("<type>" + type + "</type>").append("<parentID>" + nonExistentParentID + "</parentID>").append("</group>");

        try {

            request.accept("application/xml").body(MediaType.APPLICATION_XML, xmlString.toString());

            ClientResponse<Group> createResponse = request.post(Group.class);
            assertFalse(createResponse.getStatus() == Status.OK.getStatusCode());
            assertTrue(createResponse.getHeaders().get(BaseExceptionMapper.HEADER_ERROR_MESSAGE).get(0).contains(INVALID_PARENT));

        } finally {}
    }

    @Test
    public void createGroupTest_createToNonExistentManager() throws Exception {
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/");
        String description = "updateGroupTest description";
        String name = "updateGroupTest name";
        String type = "TEAM";
        Group createdGroup = null;

        StringBuilder xmlString = new StringBuilder().append("<group>").append("<accountID>" + accountID + "</accountID>").append("<description>" + description + "</description>")
                .append("<managerID>" + nonExistentManagerID + "</managerID>").append("<name>" + name + "</name>").append("<type>" + type + "</type>").append("<parentID>" + parentID + "</parentID>")
                .append("</group>");

        try {

            request.accept("application/xml").body(MediaType.APPLICATION_XML, xmlString.toString());

            ClientResponse<Group> createResponse = request.post(Group.class);
            assertTrue(createResponse.getStatus() == Status.BAD_REQUEST.getStatusCode());
            assertTrue(createResponse.getHeaders().get(BaseExceptionMapper.HEADER_ERROR_MESSAGE) != null);
            assertTrue(createResponse.getHeaders().get(BaseExceptionMapper.HEADER_ERROR_MESSAGE).get(0).contains(INVALID_MANAGER));
            createdGroup = createResponse.getEntity();
        } catch (JAXBUnmarshalException jue) {
            assertTrue(jue instanceof JAXBUnmarshalException);
        } finally {
            if (createdGroup != null) {
                fail("No group should have been created");
                clientExecutor.createRequest("http://localhost:8080/service/api/group/" + createdGroup.getGroupID()).accept("application/xml").delete();
            }
        }
    }

    @Test
    public void createGroupTest_createToExistingManagerInWrongAccount() throws Exception {
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/");
        String description = "updateGroupTest description";
        String name = "updateGroupTest name";
        String type = "TEAM";
        Group createdGroup = null;

        StringBuilder xmlString = new StringBuilder().append("<group>").append("<accountID>" + accountID + "</accountID>").append("<description>" + description + "</description>")
                .append("<managerID>" + managerIDInOtherAccount + "</managerID>").append("<name>" + name + "</name>").append("<type>" + type + "</type>")
                .append("<parentID>" + parentID + "</parentID>").append("</group>");

        try {

            request.accept("application/xml").body(MediaType.APPLICATION_XML, xmlString.toString());

            ClientResponse<Group> createResponse = request.post(Group.class);
            assertTrue(createResponse.getStatus() == Status.BAD_REQUEST.getStatusCode());
            assertTrue(createResponse.getHeaders().get(BaseExceptionMapper.HEADER_ERROR_MESSAGE) != null);
            assertTrue(createResponse.getHeaders().get(BaseExceptionMapper.HEADER_ERROR_MESSAGE).get(0).contains(INVALID_MANAGER));
            createdGroup = createResponse.getEntity();
        } catch (JAXBUnmarshalException jue) {
            assertTrue(jue instanceof JAXBUnmarshalException);
        } finally {
            if (createdGroup != null) {
                fail("No group should have been created");
                clientExecutor.createRequest("http://localhost:8080/service/api/group/" + createdGroup.getGroupID()).accept("application/xml").delete();
            }
        }
    }

    @Test
    public void createGroupTest_createToDeletedManagerInRightAccount() throws Exception {
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/");
        String description = "updateGroupTest description";
        String name = "updateGroupTest name";
        String type = "TEAM";
        Group createdGroup = null;

        StringBuilder xmlString = new StringBuilder().append("<group>").append("<accountID>" + accountIDOfDeletedManager + "</accountID>").append("<description>" + description + "</description>")
                .append("<managerID>" + deletedManagerID + "</managerID>").append("<name>" + name + "</name>").append("<type>" + type + "</type>")
                .append("<parentID>" + parentIDOfDeletedManager + "</parentID>").append("</group>");

        try {

            request.accept("application/xml").body(MediaType.APPLICATION_XML, xmlString.toString());

            ClientResponse<Group> createResponse = request.post(Group.class);
            assertTrue(createResponse.getStatus() == Status.BAD_REQUEST.getStatusCode());
            assertTrue(createResponse.getHeaders().get(BaseExceptionMapper.HEADER_ERROR_MESSAGE) != null);
            assertTrue(createResponse.getHeaders().get(BaseExceptionMapper.HEADER_ERROR_MESSAGE).get(0).contains(INVALID_MANAGER));
            createdGroup = createResponse.getEntity();
        } catch (JAXBUnmarshalException jue) {
            assertTrue(jue instanceof JAXBUnmarshalException);
        } finally {
            if (createdGroup != null) {
                fail("No group should have been created");
                clientExecutor.createRequest("http://localhost:8080/service/api/group/" + createdGroup.getGroupID()).accept("application/xml").delete();
            }
        }
    }

    @Test
    public void getGroupTest_deletedManager() throws Exception {
        // Set a person who is a manager to deleted
        Person person = createTestPersonAndUser();
        // make them the manager of a group
        Group group = createAGroup(person.getPersonID());
        // delete the manager
        deleteTestPersonAndUser(person);
        // try to get the group they are the manager of
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/"+group.getGroupID());
        ClientResponse<Group> getResponse = request.get(Group.class);
        // check manager is null
        Group getGroup =  getResponse.getEntity();
        assertNull(getGroup.getManagerID());
        // delete the group
        clientExecutor.createRequest("http://localhost:8080/service/api/group/" + getGroup.getGroupID()).accept("application/xml").delete();
       
    }

    private Person createTestPersonAndUser() {
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/person/");
        // QA ClientRequest request = clientExecutor.createRequest("http://dev-pro.inthinc.com:8082/service/api/person/");
        // removed <dot>US_OIL</dot> from person
        // String xmlText =
        // "<person><acctID>1</acctID><addressID>3989</addressID><crit>0</crit><driver><certifications></certifications><driverID>10936</driverID><expiration>1969-12-31T17:00:00-07:00</expiration><groupID>2</groupID><personID>16491</personID><status>ACTIVE</status></driver><empid>ROMANIAN</empid><first>test</first><fuelEfficiencyType>KMPL</fuelEfficiencyType><height>0</height><info>0</info><last>Jennings</last><locale>ro</locale><measurementType>METRIC</measurementType><middle></middle><personID>16491</personID><priEmail>romanian@test.com</priEmail><priPhone></priPhone><priText></priText><reportsTo></reportsTo><secEmail></secEmail><secPhone></secPhone><secText></secText><status>ACTIVE</status><suffix></suffix><timeZone>US/Mountain</timeZone><title></title><user><groupID>1</groupID><password>8tRfU2ESaa4Ul0CJhE3lWpVwAbJKXTLLtN+GLsjrQ7HnIwx8nugxdhwPCUSIJ9SC</password><passwordDT>2011-07-29T10:39:55-06:00</passwordDT><personID>16491</personID><status>ACTIVE</status><userID>9169</userID><username>romanian_cj</username></user><warn>0</warn><weight>0</weight></person>";
        // QA
        // "<person><acctID>2</acctID><addressID>23293</addressID><crit>0</crit><driver><certifications></certifications><driverID>52992</driverID><expiration>1969-12-31T17:00:00-07:00</expiration><groupID>6</groupID><personID>55227</personID><status>ACTIVE</status></driver><first>Dr</first><fuelEfficiencyType>MPG_US</fuelEfficiencyType><height>0</height><info>0</info><last>Coke</last><locale>en_US</locale><measurementType>ENGLISH</measurementType><middle></middle><personID>55227</personID><priEmail>dp@test.com</priEmail><priPhone></priPhone><priText></priText><reportsTo></reportsTo><secEmail></secEmail><secPhone></secPhone><secText></secText><status>ACTIVE</status><suffix></suffix><timeZone>US/Mountain</timeZone><title></title><user><groupID>6</groupID><lastLogin>2011-07-11T02:45:49-06:00</lastLogin><passwordDT>2011-05-31T09:26:49-06:00</passwordDT><personID>55227</personID><status>ACTIVE</status><userID>31615</userID><username>DP</username></user><warn>0</warn><weight>0</weight></person>";
        String xmlText = "<person><acctID>2</acctID><addressID>76</addressID><crit>3</crit><driver><certifications></certifications><dot>TEXAS</dot><driverID>49</driverID><expiration>1969-12-31T17:00:00-07:00</expiration>"
                + "<groupID>3122</groupID><status>ACTIVE</status></driver><empid>99999996</empid><first>Vinh</first><fuelEfficiencyType>MPG_US</fuelEfficiencyType><gender>MALE</gender><height>0</height><info>1</info>"
                + "<last>Vo</last><locale>en_US</locale><measurementType>ENGLISH</measurementType><middle></middle><priEmail>vvo1003@inthinc.com</priEmail><priPhone>8015976467</priPhone><priText></priText><reportsTo></reportsTo>"
                + "<secEmail></secEmail><secPhone></secPhone><secText></secText><status>ACTIVE</status><suffix></suffix><timeZone>US/Mountain</timeZone><title></title>"
                + "<user><groupID>5</groupID><password>AjEsm1Z7+zu0rBc0DDEIMGBF2XSLek1RuabFpFtZ2xzjOrUrZNpsjHX1iMug5vX+</password><roles><role>3</role><role>4</role></roles><status>INACTIVE</status><username>vvo1003</username></user>"
                + "<warn>1</warn><weight>0</weight></person>";
        request.accept("application/xml").body(MediaType.APPLICATION_XML, xmlText);

        ClientResponse<Person> createdPerson;
        try {
            createdPerson = request.post(Person.class);
            Person person = createdPerson.getEntity();
            return person;
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // get response.
        return null;
    }

    private void deleteTestPersonAndUser(Person person) {
        try {
            ClientRequest deleteUserRequest = clientExecutor.createRequest("http://localhost:8080/service/api/user/" + person.getUserID());
            ClientResponse<User> response = deleteUserRequest.delete();
            ClientRequest deletePersonRequest = clientExecutor.createRequest("http://localhost:8080/service/api/person/" + person.getPersonID());
            response = deletePersonRequest.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private Group createAGroup(Integer managerID){
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/group/");
        String description = "updateGroupTest description";
        String name = "updateGroupTest name";
        String type = "TEAM";

        StringBuilder xmlString = new StringBuilder().append("<group>").append("<accountID>" + accountID + "</accountID>").append("<description>" + description + "</description>")
                .append("<managerID>" + managerID + "</managerID>").append("<name>" + name + "</name>").append("<type>" + type + "</type>").append("<parentID>" + parentID + "</parentID>")
                .append("</group>");

        try {

            request.accept("application/xml").body(MediaType.APPLICATION_XML, xmlString.toString());

            ClientResponse<Group> createResponse = request.post(Group.class);
            return createResponse.getEntity();
        }
        catch(Exception e){
            return null;
        }

    }
}

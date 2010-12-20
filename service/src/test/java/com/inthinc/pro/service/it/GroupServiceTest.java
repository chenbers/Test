package com.inthinc.pro.service.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.log4j.Logger;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClientExecutor;
import org.junit.Test;

import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.service.security.TiwiproPrincipal;

/**
 * Integration test for Group Service Implementation.
 */
public class GroupServiceTest extends BaseEmbeddedServerITCase {
    private static Logger logger = Logger.getLogger(GroupServiceTest.class);
    
    private ServiceClient client;
    
    @Test
    public void testDummy() {}
    
//    @Before
    public void before() {

        HttpClientParams params = new HttpClientParams();
        params.setAuthenticationPreemptive(true);
        httpClient = new HttpClient(params);
        Credentials defaultcreds = new UsernamePasswordCredentials(TiwiproPrincipal.ADMIN_BACKDOOR_USERNAME, "password");
        httpClient.getState().setCredentials(new AuthScope(DOMAIN, getPort(), AuthScope.ANY_REALM), defaultcreds);
        clientExecutor = new ApacheHttpClientExecutor(httpClient);

        client = ProxyFactory.create(ServiceClient.class, "http://localhost:" + getPort(), clientExecutor);
    }
    
    /**
     * Test if Get DriverLocations service returns the correct content and response.
     */
//    @Test
    public void testGetGroupDriverLocations() {
        logger.info("Testing Get DriverLocations service... ");
        ClientResponse<List<DriverLocation>> response = client.getGroupDriverLocations(1);
        logger.info("Get DriverLocations response: " + response.getStatus());
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        
        List<DriverLocation> list = response.getEntity();
        assertNotNull(list);
        assertFalse(list.isEmpty());
    }

    /**
     * Test if Get DriverLocations service returns 404 response when empty/null list.
     */
//    @Test
    public void testGetGroupDriverLocationsNoData() {
        logger.info("Testing Get DriverLocations service when no data... ");
        ClientResponse<List<DriverLocation>> response = client.getGroupDriverLocations(343);
        logger.info("Get DriverLocations response: " + response.getStatus());
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
}

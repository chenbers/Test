package com.inthinc.pro.service.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.Test;

import com.inthinc.pro.model.DriverLocation;

/**
 * Integration test for Group Service Implementation.
 */
public class GroupServiceTest extends BaseEmbeddedServerITCase {
    private static Logger logger = Logger.getLogger(GroupServiceTest.class);
    
    /**
     * Test if Get DriverLocations service returns the correct content and response.
     */
    @Test
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
    @Test
    public void testGetGroupDriverLocationsNoData() {
        logger.info("Testing Get DriverLocations service when no data... ");
        ClientResponse<List<DriverLocation>> response = client.getGroupDriverLocations(343);
        logger.info("Get DriverLocations response: " + response.getStatus());
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
}

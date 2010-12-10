package com.inthinc.pro.service.reports.impl.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.client.ClientResponse;
import org.junit.Test;

import com.inthinc.pro.reports.ifta.model.MileageByVehicle;
import com.inthinc.pro.service.it.BaseEmbeddedServerITCase;

/**
 * Integration tests for IFTA MileageByVehicle services.
 */
public class MileageByVehicleIFTAServiceTest extends BaseEmbeddedServerITCase {
    private static final String TEST_END_DATE = "20101001";
    private static final String TEST_START_DATE = "20100901";
    // Group ID
    private static final Integer GROUP_ID_WITH_DATA = 1;
    private static final Integer GROUP_ID_NOT_IN_HIERARCHY = 8;
    private static final Integer GROUP_ID_BAD = 99999;
    private static final Integer GROUP_ID_NEGATIVE = -5;

    /**
     * Integration test for getMileageByVehicleWithDates().
     */
    @Test public void testGetMileageByVehicleWithDates() {
        // test case when Group has data
        ClientResponse<List<MileageByVehicle>> response = 
            client.getMileageByVehicleWithDates(GROUP_ID_WITH_DATA, TEST_START_DATE, TEST_END_DATE);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());      
        List<MileageByVehicle> list = response.getEntity();
        assertTrue(list.size() > 0);

        // test case when wrong date format
        response = client.getMileageByVehicleWithIftaAndDates(GROUP_ID_WITH_DATA, "2010-10-01", "2010-10-30");
        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());        

        // test case when wrong date range
        response = client.getMileageByVehicleWithIftaAndDates(GROUP_ID_WITH_DATA, TEST_END_DATE, TEST_START_DATE);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());        
    }

    /**
     * Integration test for getMileageByVehicleWithIftaAndDates().
     */
    @Test public void testGetMileageByVehicleWithIftaAndDates() {
        
        // test case when Group has data
        ClientResponse<List<MileageByVehicle>> response = 
            client.getMileageByVehicleWithIftaAndDates(GROUP_ID_WITH_DATA, TEST_START_DATE, TEST_END_DATE);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());        
        List<MileageByVehicle> list = response.getEntity();
        assertTrue(list.size() > 0);
    }

    /**
     * Integration test for getMileageByVehicleWithIfta().
     */
    @Test public void testGetMileageByVehicleWithIfta() {
        
        // test case when Group has data
        ClientResponse<List<MileageByVehicle>> response = client.getMileageByVehicleWithIfta(GROUP_ID_WITH_DATA);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());        
        List<MileageByVehicle> list = response.getEntity();
        assertTrue(list.size() > 0);
    }

    /**
     * Integration test for getMileageByVehicleDefaults().
     */
    @Test public void testGetMileageByVehicleDefaults() {
        // test case when Group not in Hierarchy
        ClientResponse<List<MileageByVehicle>> response = 
            client.getMileageByVehicleDefaults(GROUP_ID_NOT_IN_HIERARCHY);
        // TODO fix it 
         assertEquals(Status.FORBIDDEN.getStatusCode(), response.getStatus());
        
        // test case when Group doesn't exist
        response = client.getMileageByVehicleDefaults(GROUP_ID_BAD);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

        // test case when Group is negative
        response = client.getMileageByVehicleDefaults(GROUP_ID_NEGATIVE);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        
        // test case when Group has data
        response = client.getMileageByVehicleDefaults(GROUP_ID_WITH_DATA);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        List<MileageByVehicle> list = response.getEntity();
        assertTrue(list.size() > 0);
    }
}

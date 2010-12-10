package com.inthinc.pro.service.reports.impl.it;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.ClientResponse;
import org.junit.Test;

import com.inthinc.pro.reports.ifta.model.MileageByVehicle;
import com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus;
import com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup;
import com.inthinc.pro.service.it.BaseEmbeddedServerITCase;

/**
 * Integration test for IFTA methods.
 */
public class IFTAServiceTest extends BaseEmbeddedServerITCase {

    private static final String TEST_END_DATE = "20101230";
    private static final String TEST_START_DATE = "20090101";
    private static final Integer TEST_GROUP_ID = 1;
    private static final Integer GROUP_ID_WITH_NO_DATA = 1;
    private static final Integer GROUP_ID = 3;
    private static final Integer GROUP_ID_NOT_IN_USER_HIERARCHY = 8;

    /**
     * Integration test for getStateMileageByVehicleRoadStatus().
     */
    @Test
    public void testGetStateMileageByVehicleRoadStatusWithGroupWithoutData() {

        ClientResponse<List<StateMileageByVehicleRoadStatus>> response = client.getStateMileageByVehicleRoadStatusWithDates(GROUP_ID_WITH_NO_DATA, TEST_START_DATE, TEST_END_DATE);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());

    }

    @Test
    public void testGetStateMileageByVehicleRoadStatusWithAccessDenied() {

        ClientResponse<List<StateMileageByVehicleRoadStatus>> response = client.getStateMileageByVehicleRoadStatusWithDates(GROUP_ID_NOT_IN_USER_HIERARCHY, TEST_START_DATE, TEST_END_DATE);

        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());

    }

    @Test
    public void testGetStateMileageByVehicleRoadStatusWithNegativeGroupID() {

        ClientResponse<List<StateMileageByVehicleRoadStatus>> response = client.getStateMileageByVehicleRoadStatusWithDates(-10, TEST_START_DATE, TEST_END_DATE);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());

    }

    @Test
    public void testGetStateMileageByVehicleRoadStatusWithStartDateBiggerThanEndDate() {

        ClientResponse<List<StateMileageByVehicleRoadStatus>> response = client.getStateMileageByVehicleRoadStatusWithDates(GROUP_ID, TEST_END_DATE, TEST_START_DATE);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());

    }

    @Test
    public void testGetStateMileageByVehicleRoadStatusWithUnknownGroupID() {

        ClientResponse<List<StateMileageByVehicleRoadStatus>> response = client.getStateMileageByVehicleRoadStatusWithDates(9999, TEST_START_DATE, TEST_END_DATE);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());

    }

    /**
     * Integration test for getStateMileageByVehicleStateComparison().
     */
    @Test
    public void testGetStateMileageByVehicleStateComparisonWithGroupNotInUserHierarchy() {

        ClientResponse<List<StateMileageCompareByGroup>> response = client.getStateMileageByVehicleStateComparisonWithDates(GROUP_ID_NOT_IN_USER_HIERARCHY, TEST_START_DATE, TEST_END_DATE);

        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());

    }

    @Test
    public void testGetStateMileageByVehicleStateComparisonWithGroupWithoutData() {

        ClientResponse<List<StateMileageCompareByGroup>> response = client.getStateMileageByVehicleStateComparisonWithDates(GROUP_ID_WITH_NO_DATA, TEST_START_DATE, TEST_END_DATE);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());

    }

    /**
     * Integration test for getStateMileageByVehicle().
     */

    @Test
    public void testGetStateMileageByVehicleDefaults() {

        @SuppressWarnings("unused")
        ClientResponse<List<MileageByVehicle>> response = client.getStateMileageByVehicleDefaults(TEST_GROUP_ID);

        // Assertions should not be run automatically, as it depends on data in the DB and it can change through time
        // assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        //                
        // List<MileageByVehicle> results = response.getEntity();
        //                
        // assertNotNull(results);
        // assertFalse(results.isEmpty());
    }

    @Test
    public void testGetStateMileageByVehicleWithDates() {

        @SuppressWarnings("unused")
        ClientResponse<List<MileageByVehicle>> response = client.getStateMileageByVehicleWithDates(TEST_GROUP_ID, "1700123111", "1700123122");

        // Assertions should not be run automatically, as it depends on data in the DB and it can change through time
        // assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        //        
        // List<MileageByVehicle> results = response.getEntity();
        //        
        // assertNotNull(results);
        // assertFalse(results.isEmpty());
    }

    @Test
    public void testGetStateMileageByVehicleIftaOnlyDefaults() {

        @SuppressWarnings("unused")
        ClientResponse<List<MileageByVehicle>> response = client.getStateMileageByVehicleWithIfta(TEST_GROUP_ID);

        // Assertions should not be run automatically, as it depends on data in the DB and it can change through time
        // assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        //                
        // List<MileageByVehicle> results = response.getEntity();
        //                
        // assertNotNull(results);
        // assertFalse(results.isEmpty());
    }

    @Test
    public void testGetStateMileageByVehicleIftaOnlyWithDates() {

        @SuppressWarnings("unused")
        ClientResponse<List<MileageByVehicle>> response = client.getStateMileageByVehicleWithIftaAndDates(TEST_GROUP_ID, "20101231", "20101231");

        // Assertions should not be run automatically, as it depends on data in the DB and it can change through time
        // assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        //        
        // List<MileageByVehicle> results = response.getEntity();
        //        
        // assertNotNull(results);
        // assertFalse(results.isEmpty());
    }
}

package com.inthinc.pro.service.reports.impl.it;

import java.util.ArrayList;
import java.util.List;

import org.jboss.resteasy.client.ClientResponse;
import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.reports.ifta.model.StateMileageFuelByVehicle;
import com.inthinc.pro.service.it.BaseEmbeddedServerITCase;
import com.inthinc.pro.util.GroupList;

/**
 * Integration test for IFTA methods.
 */
public class IFTAServiceStateMileageFuelByVehicleTest extends BaseEmbeddedServerITCase {

    private static final String TEST_END_DATE = "20101230";
    private static final String TEST_START_DATE = "20090101";
    private static final Integer SAMPLE_GROUP_ID = 1;
    private static final Integer SAMPLE_GROUP_ID_2 = 2;
    private GroupList groupList;

    @SuppressWarnings( { "serial", "unchecked" })
    @Before
    public void setUp() {
        groupList = new GroupList();
        groupList.setValue(new ArrayList() {
            {
                add(SAMPLE_GROUP_ID);
                add(SAMPLE_GROUP_ID_2);
            }
        });
    }

    @Test
    public void testGetStateMileageFuelByVehicleWithGroup() {

        @SuppressWarnings("unused")
        ClientResponse<List<StateMileageFuelByVehicle>> response = client.getStateMileageFuelByVehicleDefaults(SAMPLE_GROUP_ID);

        // No automatic assertions
        // assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetStateMileageFuelByVehicleWithGroupAndDates() {

        @SuppressWarnings("unused")
        ClientResponse<List<StateMileageFuelByVehicle>> response = client.getStateMileageFuelByVehicleWithDates(SAMPLE_GROUP_ID, TEST_START_DATE, TEST_END_DATE);

        // No automatic assertions
        // assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetStateMileageFuelByVehicleWithIfta() {

        @SuppressWarnings("unused")
        ClientResponse<List<StateMileageFuelByVehicle>> response = client.getStateMileageFuelByVehicleWithIfta(SAMPLE_GROUP_ID);

        // No automatic assertions
        // assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetStateMileageFuelByVehicleWithIftaAndDates() {

        @SuppressWarnings("unused")
        ClientResponse<List<StateMileageFuelByVehicle>> response = client.getStateMileageFuelByVehicleWithIftaAndDates(SAMPLE_GROUP_ID, TEST_START_DATE, TEST_END_DATE);

        // No automatic assertions
        // assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetStateMileageFuelByVehicleWithMultiGroup() {

        @SuppressWarnings("unused")
        ClientResponse<List<StateMileageFuelByVehicle>> response = client.getStateMileageFuelByVehicleDefaultsMultiGroup(groupList);

        // No automatic assertions
        // assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetStateMileageFuelByVehicleWithMultiGroupAndDates() {

        @SuppressWarnings("unused")
        ClientResponse<List<StateMileageFuelByVehicle>> response = client.getStateMileageFuelByVehicleWithDatesMultiGroup(groupList, TEST_START_DATE, TEST_END_DATE);

        // No automatic assertions
        // assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetStateMileageFuelByVehicleWithIftaMultiGroup() {

        @SuppressWarnings("unused")
        ClientResponse<List<StateMileageFuelByVehicle>> response = client.getStateMileageFuelByVehicleWithIftaMultiGroup(groupList);

        // No automatic assertions
        // assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetStateMileageFuelByVehicleWithIftaAndDatesMultiGroup() {

        @SuppressWarnings("unused")
        ClientResponse<List<StateMileageFuelByVehicle>> response = client.getStateMileageFuelByVehicleWithIftaAndDatesMultiGroup(groupList, TEST_START_DATE, TEST_END_DATE);

        // No automatic assertions
        // assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

}

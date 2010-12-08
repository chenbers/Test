package com.inthinc.pro.service.reports.impl.it;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.Test;

import com.inthinc.pro.reports.ifta.model.MileageByVehicle;
import com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus;
import com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup;
import com.inthinc.pro.service.it.BaseEmbeddedServerITCase;
import com.inthinc.pro.service.reports.impl.IFTAServiceImpl;

/**
 * Integration test for IFTA methods.
 */
public class IFTAServiceTest extends BaseEmbeddedServerITCase {

    private static final String TEST_END_DATE = "20101230";
    private static final String TEST_START_DATE = "20090101";
    private static final Integer TEST_GROUP_ID = 1;
    private static Logger logger = Logger.getLogger(IFTAServiceTest.class);
    private static final Integer GROUP_ID_WITH_NO_DATA = 1;
    private static final Integer GROUP_ID_NOT_IN_USER_HIERARCHY = 1504;

    /**
     * Integration test for getStateMileageByVehicleRoadStatus().
     */
    @Test
    public void testGetStateMileageByVehicleRoadStatusWithGroupNotInUserHierarchy() {

        ClientResponse<List<StateMileageByVehicleRoadStatus>> response = client.getStateMileageByVehicleRoadStatusWithDates(GROUP_ID_NOT_IN_USER_HIERARCHY, TEST_START_DATE, TEST_END_DATE);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());

    }

    @Test
    public void testGetStateMileageByVehicleRoadStatusWithGroupWithoutData() {

        ClientResponse<List<StateMileageByVehicleRoadStatus>> response = client.getStateMileageByVehicleRoadStatusWithDates(GROUP_ID_WITH_NO_DATA, TEST_START_DATE, TEST_END_DATE);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());

    }
    
    @Test
    public void testGetStateMileageByVehicleRoadStatusWithBadInput1() {

        ClientResponse<List<StateMileageByVehicleRoadStatus>> response = client.getStateMileageByVehicleRoadStatusWithDates(GROUP_ID_NOT_IN_USER_HIERARCHY, TEST_END_DATE, TEST_START_DATE);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());

    }
    
    @Test
    public void testGetStateMileageByVehicleRoadStatusWithBadInput2() {

        ClientResponse<List<StateMileageByVehicleRoadStatus>> response = client.getStateMileageByVehicleRoadStatusWithDates(-10, TEST_START_DATE, TEST_END_DATE);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());

    }
    
    @Test
    public void testGetStateMileageByVehicleRoadStatusWithBadInput3() {

//        ClientResponse<List<StateMileageByVehicleRoadStatus>> response = client.getStateMileageByVehicleRoadStatusWithDates(null, TEST_START_DATE, TEST_END_DATE);
//
//        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());

    }
    
    @Test
    public void testGetStateMileageByVehicleRoadStatusWithBadInput4() {

//        ClientResponse<List<StateMileageByVehicleRoadStatus>> response = client.getStateMileageByVehicleRoadStatusWithDates(GROUP_ID_NOT_IN_USER_HIERARCHY, null, TEST_END_DATE);
//
//        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());

    }
    
    /**
     * Integration test for getStateMileageByVehicleGroupComparaison().
     */
    @Test
    public void testGetStateMileageByVehicleGroupComparaisonWithGroupNotInUserHierarchy() {

        String expectedStrStartDate = TEST_START_DATE;
        String expectedStrEndDate = TEST_END_DATE;

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);

        ClientResponse<List<StateMileageCompareByGroup>> response = client.getStateMileageByVehicleGroupComparaisonWithDates(GROUP_ID_NOT_IN_USER_HIERARCHY, TEST_START_DATE, TEST_END_DATE);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());

    }

    @Test
    public void testGetStateMileageByVehicleGroupComparaisonWithGroupWithoutData() {

        String expectedStrStartDate = TEST_START_DATE;
        String expectedStrEndDate = TEST_END_DATE;

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);
        ClientResponse<List<StateMileageCompareByGroup>> response = client.getStateMileageByVehicleGroupComparaisonWithDates(GROUP_ID_WITH_NO_DATA, TEST_START_DATE, TEST_END_DATE);

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

    private Date buildDateFromString(String strDate) {
        DateFormat df = new SimpleDateFormat(IFTAServiceImpl.getSimpleDateFormat());
        try {
            Date convertedDate = df.parse(strDate);
            return convertedDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}

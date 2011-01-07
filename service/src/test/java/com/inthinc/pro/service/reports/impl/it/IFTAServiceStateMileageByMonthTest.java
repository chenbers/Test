package com.inthinc.pro.service.reports.impl.it;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.inthinc.pro.service.it.BaseEmbeddedServerITCase;
import com.inthinc.pro.util.GroupList;

/**
 * IFTAServiceStateMileageByMonth integration test.
 */
public class IFTAServiceStateMileageByMonthTest extends BaseEmbeddedServerITCase {

    private static final String TEST_END_DATE = "20101230";
    private static final String TEST_START_DATE = "20090101";
    private static final Integer TEST_GROUP_ID = 1;
    private static final String TEST_LOCALE = "FR";
    private static final String TEST_TYPE = "ENGLISH";
    private static final Integer GROUP_ID_NEGATIVE = -5;
    private List<Integer> expectedGroupIDList;

// TODO Uncomment only when run manually 
//    @Test
//    public void testGetStateMileageByMonthWithGroupNegative() {
//        ClientResponse<List<MileageByVehicle>> response = 
//            client.getStateMileageByMonthWithDates(GROUP_ID_NEGATIVE, TEST_START_DATE, TEST_END_DATE, TEST_LOCALE, TEST_TYPE);
//
//        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
//    }
//    
//    @Test
//    public void testGetStateMileageByMonth() {
//        ClientResponse<List<MileageByVehicle>> response = 
//            client.getStateMileageByMonthWithDates(TEST_GROUP_ID, TEST_START_DATE, TEST_END_DATE, TEST_LOCALE, TEST_TYPE);
//
//        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
//    }
    
    @Test
    public void testGetStateMileageByMonthWithMultiGroup() {
        expectedGroupIDList = new ArrayList<Integer>();
       // expectedGroupIDList.add(GROUP_ID_NEGATIVE);
        expectedGroupIDList.add(TEST_GROUP_ID);
        GroupList gl = new GroupList(expectedGroupIDList);
        
//        ClientResponse<List<MileageByVehicle>> response = 
//            client.getStateMileageByMonthWithDatesMultiGroup(gl, TEST_START_DATE, TEST_END_DATE, TEST_LOCALE, TEST_TYPE);
//
//        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

    }
}

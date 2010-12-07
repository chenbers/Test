package com.inthinc.pro.service.reports.impl.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.Assert;
import org.junit.Test;

import com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus;
import com.inthinc.pro.service.it.BaseEmbeddedServerITCase;
import com.inthinc.pro.service.reports.impl.IFTAServiceImpl;

/**
 * Integration test for IFTA methods.
 */
public class IFTAServiceTest extends BaseEmbeddedServerITCase {

    private static Logger logger = Logger.getLogger(IFTAServiceTest.class);
    private static final Integer GROUP_ID_WITH_NO_DATA = 1;
    private static final Integer GROUP_ID_NOT_IN_USER_HIERARCHY = 1504;
    
    /**
     * Integration test for getStateMileageByVehicleRoadStatus(). 
     */
    @Test
    public void testGetStateMileageByVehicleRoadStatusWithGroupNotInUserHierarchy() {
        
        String expectedStrStartDate = "20090101";
        String expectedStrEndDate = "20101230";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);
        
        ClientResponse<List<StateMileageByVehicleRoadStatus>> response = client.getStateMileageByVehicleRoadStatusOnlyDates(GROUP_ID_NOT_IN_USER_HIERARCHY, "20090101", "20101230");
               
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());         

    }
    
    @Test
    public void testGetStateMileageByVehicleRoadStatusWithGroupWithoutData() {
        
        String expectedStrStartDate = "20090101";
        String expectedStrEndDate = "20101230";

        Date startDate = buildDateFromString(expectedStrStartDate);
        Date endDate = buildDateFromString(expectedStrEndDate);
        ClientResponse<List<StateMileageByVehicleRoadStatus>> response = client.getStateMileageByVehicleRoadStatusOnlyDates(GROUP_ID_WITH_NO_DATA, "20090101", "20101230");
          
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());         

    }
    
    private Date buildDateFromString(String strDate) {
        DateFormat df = new SimpleDateFormat(IFTAServiceImpl.getSimpleDateFormat()); 
        try
        {
            Date convertedDate = df.parse(strDate);           
            return convertedDate;
        } catch (ParseException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}

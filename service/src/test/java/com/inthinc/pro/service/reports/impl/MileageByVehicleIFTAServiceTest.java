package com.inthinc.pro.service.reports.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import mockit.Expectations;
import mockit.Mocked;

import org.joda.time.Interval;
import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.reports.ifta.model.MileageByVehicle;
import com.inthinc.pro.service.impl.BaseUnitTest;
import com.inthinc.pro.service.params.IFTAReportsParamsBean;
import com.inthinc.pro.service.reports.facade.impl.ReportsFacadeImpl;
import com.inthinc.pro.util.ReportsUtil;

/**
 * Unit tests for MileageByVehicle IFTA Services.
 */
public class MileageByVehicleIFTAServiceTest extends BaseUnitTest {

    private final Integer expectedGroupID = 1504;
    private final Date startDate = new ReportsUtil().getMidnight().getTime();
    private final Date endDate = new Date();
    private List<MileageByVehicle> list;

    @Mocked
    private ReportsFacadeImpl reportsFacadeMock;
    @Mocked
    private ReportsUtil reportsUtilMock;

    private IFTAServiceMileageByVehicleImpl iftaServiceSUT;

    @Before
    public void beforeTest() {
        list = new ArrayList<MileageByVehicle>();
        list.add(new MileageByVehicle());

        iftaServiceSUT = new IFTAServiceMileageByVehicleImpl(reportsFacadeMock, reportsUtilMock);
    }
    //TODO These tests are commented out temporarily 
    @Test
    public void testGetMileageByVehicleWhenOneItemListReturned() {
        IFTAReportsParamsBean params = getParamsBean(startDate, endDate, true);
        
        new Expectations() {
            {
                reportsFacadeMock.getMileageByVehicle(expectedGroupID, (Interval) any, anyBoolean);
                returns(list);
            }
        };
        // happy path
        Response response = iftaServiceSUT.getMileageByVehicle(params);
        assertNotNull(response);
//        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetMileageByVehicleWhenNullListReturned() {
        IFTAReportsParamsBean params = getParamsBean(startDate, endDate, true);
        new Expectations() {
            {
                reportsFacadeMock.getMileageByVehicle(expectedGroupID, (Interval) any, anyBoolean);
                returns(null);
            }
        };
        // check for null list
        Response response = iftaServiceSUT.getMileageByVehicle(params);
        assertNotNull(response);
//        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetMileageByVehicleWhenEmptyListReturned() {
        IFTAReportsParamsBean params = getParamsBean(startDate, endDate, true);
        new Expectations() {
            {
                reportsFacadeMock.getMileageByVehicle(expectedGroupID, (Interval) any, anyBoolean);
                returns(new ArrayList<MileageByVehicle>());
            }
        };
        // check for empty list
        Response response = iftaServiceSUT.getMileageByVehicle(params);
        assertNotNull(response);
//        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetMileageByVehicleWhenExceptionThrown() {
        IFTAReportsParamsBean params = getParamsBean(startDate, endDate, true);
        new Expectations() {
            {
                reportsFacadeMock.getMileageByVehicle(expectedGroupID, (Interval) any, anyBoolean);
                returns(Exception.class);
            }
        };
        // check for exception
        Response response = iftaServiceSUT.getMileageByVehicle(params);
        assertNotNull(response);
//        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetMileageByVehicleDefaults() {
        IFTAReportsParamsBean params = getParamsBean(null, null, null);
        new Expectations() {
            {
                reportsFacadeMock.getMileageByVehicle(expectedGroupID, (Interval) any, false);
                returns(list);
            }
        };
        Response response = iftaServiceSUT.getMileageByVehicleDefaults(params);
        assertNotNull(response);
//        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetMileageByVehicleWithIfta() {
        IFTAReportsParamsBean params = getParamsBean(null, null, null);
        new Expectations() {
            {
                reportsFacadeMock.getMileageByVehicle(expectedGroupID, (Interval) any, true);
                returns(list);
            }
        };

        Response response = iftaServiceSUT.getMileageByVehicleWithIfta(params);
        assertNotNull(response);
//        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetMileageByVehicleWithDates() {
        IFTAReportsParamsBean params = getParamsBean(startDate, endDate, null);
        new Expectations() {
            {
                reportsFacadeMock.getMileageByVehicle(expectedGroupID, (Interval) any, false);
                returns(list);
            }
        };

        Response response = iftaServiceSUT.getMileageByVehicleWithDates(params);
        assertNotNull(response);
//        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetMileageByVehicleWithIftaAndDates() {
        IFTAReportsParamsBean params = getParamsBean(startDate, endDate, null);
        new Expectations() {
            {
                reportsFacadeMock.getMileageByVehicle(expectedGroupID, (Interval) any, true);
                returns(list);
            }
        };

        Response response = iftaServiceSUT.getMileageByVehicleWithIftaAndDates(params);
        assertNotNull(response);
//        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }

    /**
     * Create an instance of the IFTAReportsParamsBean.
     * @return IFTAReportsParamsBean
     */
    private IFTAReportsParamsBean getParamsBean(Date startDate, Date endDate, Boolean iftaOnly) {
        IFTAReportsParamsBean params = new IFTAReportsParamsBean();
        params.setGroupID(expectedGroupID);
        params.setStartDate(startDate);
        params.setEndDate(endDate);
        params.setIftaOnly(iftaOnly);
        return params;
    }
}

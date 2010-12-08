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
import com.inthinc.pro.service.reports.facade.impl.ReportsFacadeImpl;
import com.inthinc.pro.util.ReportsUtil;

/**
 * Unit tests for MileageByVehicle IFTA Services. 
 */
public class MileageByVehicleIFTAServiceTest extends BaseUnitTest {
    
    private final Integer expectedGroupID = 1504;
    private final Date startDate = IFTAServiceImpl.getMidnight().getTime();
    private final Date endDate = new Date();
    private List<MileageByVehicle> list;
    
    @Mocked private ReportsFacadeImpl reportsFacadeMock;
    @Mocked private ReportsUtil reportsUtilMock;

    private IFTAServiceImpl iftaServiceSUT = new IFTAServiceImpl(reportsFacadeMock, reportsUtilMock);

    @Before
    public void beforeTest() {
        list = new ArrayList<MileageByVehicle>();
        list.add(new MileageByVehicle());
        iftaServiceSUT.setFacade(reportsFacadeMock);
        iftaServiceSUT.setReportsUtil(reportsUtilMock);
    }
    
    @Test public void testGetMileageByVehicleWhenOneItemListReturned() {
        new Expectations() {
        {
            reportsUtilMock.checkParameters(expectedGroupID, (Date)any, (Date)any);returns(null);
            reportsFacadeMock.getMileageByVehicle(expectedGroupID, (Interval)any, anyBoolean);returns(list);
        }};        
        // happy path
        Response response = iftaServiceSUT.getMileageByVehicle(expectedGroupID, startDate, endDate, true);
        assertNotNull(response);
        assertEquals(Status.OK.getStatusCode(),response.getStatus() );
    }

    @Test public void testGetMileageByVehicleWhenNullListReturned() {        
        new Expectations() {
        {
            reportsUtilMock.checkParameters(expectedGroupID, (Date)any, (Date)any);returns(null);
            reportsFacadeMock.getMileageByVehicle(expectedGroupID, (Interval)any, anyBoolean);returns(null);
        }};        
        // check for null list
        Response response = iftaServiceSUT.getMileageByVehicle(expectedGroupID, startDate, endDate, true);
        assertNotNull(response);
        assertEquals(Status.NOT_FOUND.getStatusCode(),response.getStatus() );
    }
    
    @Test public void testGetMileageByVehicleWhenEmptyListReturned() {        
        new Expectations() {
        {
            reportsUtilMock.checkParameters(expectedGroupID, (Date)any, (Date)any);returns(null);
            reportsFacadeMock.getMileageByVehicle(expectedGroupID, (Interval)any, anyBoolean);returns(new ArrayList<MileageByVehicle>());
        }};        
        // check for empty list
        Response response = iftaServiceSUT.getMileageByVehicle(expectedGroupID, startDate, endDate, true);
        assertNotNull(response);
        assertEquals(Status.NOT_FOUND.getStatusCode(),response.getStatus() );
    }
    
    @Test public void testGetMileageByVehicleWhenExceptionThrown() {        
        new Expectations() {
        {
            reportsUtilMock.checkParameters(expectedGroupID, (Date)any, (Date)any);returns(null);
            reportsFacadeMock.getMileageByVehicle(expectedGroupID, (Interval)any, anyBoolean);returns(Exception.class);
        }};        
        // check for exception
        Response response = iftaServiceSUT.getMileageByVehicle(expectedGroupID, startDate, endDate, true);
        assertNotNull(response);
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(),response.getStatus() );
    }
 
    @Test public void testGetMileageByVehicleDefaults() {
        new Expectations() {
        {
            reportsFacadeMock.getMileageByVehicle(expectedGroupID, (Interval)any, false);returns(list);
        }};        
        Response response = iftaServiceSUT.getMileageByVehicleDefaults(expectedGroupID);
        assertNotNull(response);
        assertEquals(Status.OK.getStatusCode(),response.getStatus() );
    }

    @Test public void testGetMileageByVehicleWithIfta() {
        new Expectations() {
        {
            reportsFacadeMock.getMileageByVehicle(expectedGroupID, (Interval)any, true);returns(list);
        }};        

        Response response = iftaServiceSUT.getMileageByVehicleWithIfta(expectedGroupID);
        assertNotNull(response);
        assertEquals(Status.OK.getStatusCode(),response.getStatus() );
    }

    @Test public void testGetMileageByVehicleWithDates() {
        new Expectations() {
        {
            reportsFacadeMock.getMileageByVehicle(expectedGroupID, (Interval)any, false);returns(list);
        }};
        
        Response response = iftaServiceSUT.getMileageByVehicleWithDates(expectedGroupID, startDate, endDate);
        assertNotNull(response);
        assertEquals(Status.OK.getStatusCode(),response.getStatus() );
    }

    @Test public void testGetMileageByVehicleWithIftaAndDates() {
        new Expectations() {
        {
            reportsFacadeMock.getMileageByVehicle(expectedGroupID, (Interval)any, true);returns(list);
        }};        

        Response response = iftaServiceSUT.getMileageByVehicleWithIftaAndDates(expectedGroupID, startDate, endDate);
        assertNotNull(response);
        assertEquals(Status.OK.getStatusCode(),response.getStatus() );
    }
}

package com.inthinc.pro.service.reports.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import mockit.Expectations;
import mockit.Mocked;

import org.joda.time.Interval;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.reports.performance.model.DriverHours;
import com.inthinc.pro.reports.performance.model.TenHoursViolation;
import com.inthinc.pro.service.impl.BaseUnitTest;
import com.inthinc.pro.service.reports.facade.ReportsFacade;

/**
 * Unit test for PerformanceServiceImpl class.
 */
public class PerformanceServiceImplTest extends BaseUnitTest {
    private static final Integer GROUP_ID = 1505;
    
    private PerformanceServiceImpl serviceSUT;
    
    @Mocked private ReportsFacade facadeMock;
    
    @Before
    public void setUp() {
        serviceSUT = new PerformanceServiceImpl(facadeMock, null);
    }
    
    /**
     * Test the getTenHourViolations without an Interval.
     */
    @SuppressWarnings("unchecked") @Test
    public void testGetTenHourViolations() {
        
        final TenHoursViolation violation = new TenHoursViolation();
        // set the mock facade
        serviceSUT.setReportsFacade(facadeMock);
        
        new Expectations () {
            {
                List<TenHoursViolation> list = new ArrayList<TenHoursViolation>();
                list.add(violation);
                facadeMock.getTenHourViolations(GROUP_ID, (Interval)any, Locale.US); returns(list);
            }
        };
        
        // check the response
        Response response = serviceSUT.getTenHourViolations(GROUP_ID, Locale.US);
        Assert.assertNotNull(response);
        Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
                
        // check the content
        GenericEntity<List<TenHoursViolation>> entity = 
            (GenericEntity<List<TenHoursViolation>>) response.getEntity();
        
        Assert.assertNotNull(entity);
        Assert.assertEquals(1, entity.getEntity().size());
        Assert.assertEquals(violation, entity.getEntity().get(0));
    }
    
    /**
     * Test getTenHourViolations with the interval.
     */
    @SuppressWarnings("unchecked")
    @Test(expected=IllegalArgumentException.class)
    public void testGetTenHourViolationsWithInterval() {
        // create interval dates
        Calendar endDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.DAY_OF_MONTH, -6);
        
        final TenHoursViolation violation = new TenHoursViolation();
        
        // set the mock facade
        serviceSUT.setReportsFacade(facadeMock);
        
        new Expectations () {
            {
                List<TenHoursViolation> list = new ArrayList<TenHoursViolation>();
                list.add(violation);
                facadeMock.getTenHourViolations(GROUP_ID, (Interval)any, Locale.US); returns(list);
            }
        };
        
        // check the response
        Response response = serviceSUT.getTenHourViolations(GROUP_ID, startDate.getTime(), endDate.getTime(), Locale.US);
        
        Assert.assertNotNull(response);
        Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
        
        // check the content
        GenericEntity<List<TenHoursViolation>> entity = 
            (GenericEntity<List<TenHoursViolation>>) response.getEntity();
        
        Assert.assertNotNull(entity);
        Assert.assertEquals(1, entity.getEntity().size());
        Assert.assertEquals(violation, entity.getEntity().get(0));
        
        // check for wrong interval
        response = serviceSUT.getTenHourViolations(GROUP_ID, endDate.getTime(), startDate.getTime(), Locale.US);
        Assert.fail("IllegalArgumentException expected to throw");
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testGetDriverHoursDefaults() {
        final DriverHours driverHoursMock = new DriverHours("Mock group", "10/10/10", "Mock Driver", 2.5);
        // set the mock facade
        serviceSUT.setReportsFacade(facadeMock);
        
        new Expectations () {
            {
                List<DriverHours> list = new ArrayList<DriverHours>();
                list.add(driverHoursMock);
                facadeMock.getDriverHours(GROUP_ID, (Interval)any, Locale.US); returns(list);
                facadeMock.getDriverHours(GROUP_ID, (Interval)any, null); returns(null);
            }
        };
        
        // check the response
        Response response = serviceSUT.getDriverHours(GROUP_ID, Locale.US);
        Assert.assertNotNull(response);
        Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
                
        // check the content
        GenericEntity<List<DriverHours>> entity = 
            (GenericEntity<List<DriverHours>>) response.getEntity();
        
        Assert.assertNotNull(entity);
        Assert.assertEquals(1, entity.getEntity().size());
        Assert.assertEquals(driverHoursMock, entity.getEntity().get(0));
        
        // check the 404 response
        response = serviceSUT.getDriverHours(GROUP_ID, null);
        Assert.assertNotNull(response);
        Assert.assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetDriverHoursWithInterval() {
        final DriverHours driverHoursMock = new DriverHours("Mock group", "10/10/10", "Mock Driver", 2.5);
        // set the mock facade
        serviceSUT.setReportsFacade(facadeMock);
        
        new Expectations () {
            {
                List<DriverHours> list = new ArrayList<DriverHours>();
                list.add(driverHoursMock);
                facadeMock.getDriverHours(GROUP_ID, (Interval)any, Locale.US); returns(list);
            }
        };
        
        // check the response
        Response response = serviceSUT.getDriverHours(GROUP_ID, 
                buildDateFromString("20101001"), buildDateFromString("20101030"), Locale.US);
        Assert.assertNotNull(response);
        Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
                
        // check the content
        GenericEntity<List<DriverHours>> entity = 
            (GenericEntity<List<DriverHours>>) response.getEntity();
        
        Assert.assertNotNull(entity);
        Assert.assertEquals(1, entity.getEntity().size());
        Assert.assertEquals(driverHoursMock, entity.getEntity().get(0));
    }
}

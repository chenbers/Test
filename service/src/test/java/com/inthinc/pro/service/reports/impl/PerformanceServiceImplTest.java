package com.inthinc.pro.service.reports.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import mockit.Expectations;
import mockit.Mocked;

import org.joda.time.Interval;
import org.junit.Assert;
import org.junit.Test;

import com.inthinc.pro.reports.performance.model.TenHoursViolation;
import com.inthinc.pro.service.impl.BaseUnitTest;
import com.inthinc.pro.service.reports.facade.ReportsFacade;

/**
 * Unit test for PerformanceServiceImpl class.
 */
public class PerformanceServiceImplTest extends BaseUnitTest {
    private static final Integer GROUP_ID = 1505;
    
    private PerformanceServiceImpl serviceSUT = new PerformanceServiceImpl();
    
    @Mocked private ReportsFacade facadeMock;
    
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
                facadeMock.getTenHourViolations(GROUP_ID, (Interval)any); returns(list);
            }
        };
        
        // check the response
        Response response = serviceSUT.getTenHourViolations(GROUP_ID);
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
                facadeMock.getTenHourViolations(GROUP_ID, (Interval)any); returns(list);
            }
        };
        
        // check the response
        Response response = serviceSUT.getTenHourViolations(GROUP_ID, startDate.getTime(), endDate.getTime());
        
        Assert.assertNotNull(response);
        Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
        
        // check the content
        GenericEntity<List<TenHoursViolation>> entity = 
            (GenericEntity<List<TenHoursViolation>>) response.getEntity();
        
        Assert.assertNotNull(entity);
        Assert.assertEquals(1, entity.getEntity().size());
        Assert.assertEquals(violation, entity.getEntity().get(0));
        
        // check for wrong interval
        response = serviceSUT.getTenHourViolations(GROUP_ID, endDate.getTime(), startDate.getTime());
        Assert.fail("IllegalArgumentException expected to throw");
    }
}

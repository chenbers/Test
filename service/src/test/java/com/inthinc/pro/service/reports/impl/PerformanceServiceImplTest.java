package com.inthinc.pro.service.reports.impl;

import java.util.ArrayList;
import java.util.Date;
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
    
    @SuppressWarnings("unchecked")
    @Test
    public void testGetTenHourViolations() {
        
        final TenHoursViolation violation = new TenHoursViolation();
        serviceSUT.setReportsFacade(facadeMock);
        
        new Expectations () {
            {
                List<TenHoursViolation> list = new ArrayList<TenHoursViolation>();
                list.add(violation);
                facadeMock.getTenHourViolations(GROUP_ID, (Interval)any); returns(list);
            }
        };
        
        Response response = serviceSUT.getTenHourViolations(GROUP_ID);
        Assert.assertNotNull(response);
        Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
                
        GenericEntity<List<TenHoursViolation>> entity = 
            (GenericEntity<List<TenHoursViolation>>) response.getEntity();
        
        Assert.assertNotNull(entity);
        Assert.assertEquals(1, entity.getEntity().size());
        Assert.assertEquals(violation, entity.getEntity().get(0));

    }
    
    @Test
    public void testGetTenHourViolationsWithInterval() {
        Date today = new Date();
        serviceSUT.getTenHourViolations(GROUP_ID, today, today);
    }
}

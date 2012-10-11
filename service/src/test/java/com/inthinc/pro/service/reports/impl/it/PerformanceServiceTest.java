package com.inthinc.pro.service.reports.impl.it;

import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.Assert;
import org.junit.Test;

import com.inthinc.pro.reports.performance.model.TenHoursViolation;
import com.inthinc.pro.service.it.BaseEmbeddedServerITCase;

/**
 * Integration test for PerformanceService methods.
 */
public class PerformanceServiceTest extends BaseEmbeddedServerITCase {

    private static Logger logger = Logger.getLogger(PerformanceServiceTest.class);
    private static final Integer GROUP_ID = 1505;
    
    @Test
    public void testDummy() {}
    
    /**
     * Integration test for getTenHourViolations(). 
     */
//    @Test
    public void testGetTenHourViolations() {
        logger.info("Testing Get TenHourViolations service... ");
        ClientResponse<List<TenHoursViolation>> response = client.getTenHourViolations(GROUP_ID);
        logger.info("Get TenHourViolations response: " + response.getStatus());
        
        if (Response.Status.OK.getStatusCode() == response.getStatus()) {
            List<TenHoursViolation> content = response.getEntity();
            Assert.assertTrue(content.size() > 0);
        }
    }
}

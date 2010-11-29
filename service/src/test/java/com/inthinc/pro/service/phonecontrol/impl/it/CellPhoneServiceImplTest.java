package com.inthinc.pro.service.phonecontrol.impl.it;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.inthinc.pro.service.it.BaseEmbeddedServerITCase;

/**
 * Tests the integration for CellPhoneServiceImpl class. 
 */
public class CellPhoneServiceImplTest extends BaseEmbeddedServerITCase {
    private static Logger logger = Logger.getLogger(CellPhoneServiceImplTest.class);
    private static final String PHONE_ID = "1234567890";
    /**
     * Integration test for setStatusEnabled(). 
     */
    @Test
    public void testEnableStatus() {
        logger.info("Testing Enable Status service... ");
        Response response = client.setStatusEnabled(PHONE_ID);
        logger.info("Enable Status response: " + response.getStatus());
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    /**
     * Integration test for setStatusDisabled(). 
     */
    @Test
    public void testDisableStatus() {
        logger.info("Testing Disable Status service... ");
        Response response = client.setStatusDisabled(PHONE_ID);
        logger.info("Disable Status response: " + response.getStatus());
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
}

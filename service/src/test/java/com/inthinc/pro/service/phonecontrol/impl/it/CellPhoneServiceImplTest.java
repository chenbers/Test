package com.inthinc.pro.service.phonecontrol.impl.it;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.inthinc.pro.model.phone.CellStatusType;
import com.inthinc.pro.service.it.BaseEmbeddedServerITCase;

/**
 * Tests the integration for CellPhoneServiceImpl class. 
 */
public class CellPhoneServiceImplTest extends BaseEmbeddedServerITCase {
    private static Logger logger = Logger.getLogger(CellPhoneServiceImplTest.class);
    /**
     * Integration test for updateStatus(). 
     */
    @Test
    public void testUpdateStatus() {
        logger.info("Testing Update Status service: " + CellStatusType.ENABLED);
        Response response = client.updateStatus("1234567890", CellStatusType.ENABLED);
        logger.info("Update Status response: " + response.getStatus());

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        logger.info("Update Status test succeeded.");
    }
}

package com.inthinc.pro.service.phonecontrol.impl.it;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.Test;

import com.inthinc.pro.service.it.BaseEmbeddedServerITCase;
import com.inthinc.pro.service.it.WebServiceITCase;

public class IncomingPhoneControlIntegrationTest extends BaseEmbeddedServerITCase {
    private static Logger logger = Logger.getLogger(IncomingPhoneControlIntegrationTest.class);

    private static final Integer expectedDriverID = 777;
    
    @Test
    public void testNoteStartedRequest(){
       ClientResponse response = client.processStartMotionEvent(expectedDriverID);
       
       assertEquals(response.getResponseStatus(), Response.Status.OK );
       logger.info("Note start request received successfully");
    }
    
    @Test
    public void testNoteStoppedRequest(){
       ClientResponse response = client.processStopMotionEvent(expectedDriverID);
       
       assertEquals(response.getResponseStatus(), Response.Status.OK );
       logger.info("Note start request received successfully");
    }
}

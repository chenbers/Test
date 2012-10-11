package com.inthinc.pro.service.phonecontrol.impl.it;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.inthinc.pro.service.it.BaseEmbeddedServerITCase;

public class IncomingPhoneControlIntegrationTest extends BaseEmbeddedServerITCase {
    private static Logger logger = Logger.getLogger(IncomingPhoneControlIntegrationTest.class);

    private static final Integer expectedDriverID = 777;
    
    @Test
    public void testDummy() {}
    
//    @Test
    public void testNoteStartedRequest(){
       Response response = client.processStartMotionEvent(expectedDriverID);
       
       assertEquals(Status.OK.getStatusCode(), response.getStatus());
       logger.info("Note Server start motion request processed successfully");
    }
    
//    @Test
    public void testNoteStoppedRequest(){
       Response response = client.processStopMotionEvent(expectedDriverID);
       
       assertEquals(Status.OK.getStatusCode(), response.getStatus());
       logger.info("Note Server stop motion request processed successfully");
    }
}

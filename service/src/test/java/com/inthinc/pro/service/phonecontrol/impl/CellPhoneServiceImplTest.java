package com.inthinc.pro.service.phonecontrol.impl;

import javax.ws.rs.core.Response;

import mockit.Expectations;
import mockit.Verifications;

import org.junit.Assert;
import org.junit.Test;

import com.inthinc.pro.service.impl.BaseUnitTest;
import com.inthinc.pro.service.phonecontrol.MovementEventHandler;

public class CellPhoneServiceImplTest extends BaseUnitTest {
    
    private Integer expecteDriverID = 777;
    
    @Test 
    public void testProcessStartMotionEvent(final MovementEventHandler movementEventHandler){
        CellPhoneServiceImpl serviceSUT = new CellPhoneServiceImpl();
        serviceSUT.setMovementEventHandler(movementEventHandler);
         
        //System under test
        Response response = serviceSUT.processStartMotionEvent(expecteDriverID);
        
        
        Assert.assertNotNull(response); 
        Assert.assertEquals("Error processing start motion event", Response.Status.OK.getStatusCode(), response.getStatus());

    }

}

package com.inthinc.pro.service.phonecontrol.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import mockit.Expectations;
import mockit.Mocked;

import org.junit.Test;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.phone.CellStatusType;
import com.inthinc.pro.service.impl.BaseUnitTest;
import com.inthinc.pro.service.phonecontrol.MovementEventHandler;

/**
 * Unit test for CellPhoneServiceImpl.
 */
public class CellPhoneServiceImplTest extends BaseUnitTest {
    private final String phoneID = "1234567890";
    private Integer expecteDriverID = 777;    
    
    private CellPhoneServiceImpl serviceSUT = new CellPhoneServiceImpl();
    
    //Mock
    @Mocked private DriverDAO driverDaoMock;
    
    /**
     * Test updateStatus() with CellStatusType.ENABLED acknowledgment.
     */
    @Test
    public void testUpdate() {
        serviceSUT.setDriverDAO(driverDaoMock);
               
        Response response = serviceSUT.updateStatus(phoneID, CellStatusType.ENABLED);
        // check if the response is OK
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        
    }

    @Test 
    public void testProcessStartMotionEvent(final MovementEventHandler movementEventHandler){
        CellPhoneServiceImpl serviceSUT = new CellPhoneServiceImpl();
        serviceSUT.setMovementEventHandler(movementEventHandler);
         
        //System under test
        Response response = serviceSUT.processStartMotionEvent(expecteDriverID);
        
        
        assertNotNull(response); 
        assertEquals("Error processing start motion event", Response.Status.OK.getStatusCode(), response.getStatus());

    }
    
    @Test 
    public void testThreadStart(final MovementEventHandler movementEventHandlerMock){
        MovementStartHandlerThread serviceSUT = new MovementStartHandlerThread(movementEventHandlerMock, expecteDriverID);
        
        new Expectations(){                     
             {
                 movementEventHandlerMock.handleDriverStartedMoving(expecteDriverID);
             }
         };
         
        //System under test
        serviceSUT.run();       

    }
    
    @Test 
    public void testThreadStop(final MovementEventHandler movementEventHandlerMock){
        MovementStopHandlerThread serviceSUT = new MovementStopHandlerThread(movementEventHandlerMock, expecteDriverID);
        
        new Expectations(){                     
             {
                 movementEventHandlerMock.handleDriverStoppedMoving(expecteDriverID);
             }
         };
         
        //System under test
        serviceSUT.run();       

    }
}

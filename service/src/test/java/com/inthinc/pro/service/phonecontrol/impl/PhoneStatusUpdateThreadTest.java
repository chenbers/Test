package com.inthinc.pro.service.phonecontrol.impl;

import mockit.Cascading;
import mockit.Expectations;
import mockit.Mocked;

import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.model.Cellblock;
import com.inthinc.pro.model.phone.CellStatusType;
import com.inthinc.pro.service.adapters.PhoneControlDAOAdapter;
import com.inthinc.pro.service.impl.BaseUnitTest;
import com.inthinc.pro.service.phonecontrol.PhoneStatusController;
@Ignore
/**
 * Test for PhoneStatusUpdateThread.
 */
public class PhoneStatusUpdateThreadTest extends BaseUnitTest {
    private final String phoneID = "1234567890";
    private PhoneStatusUpdateThread threadSUT;

    // Mock
//    @Mocked
//    private DriverDAOAdapter driverAdapterMock;
    
    @Mocked
    private PhoneControlDAOAdapter phoneControlDAOMock;
    @Mocked
    private PhoneStatusController phoneStatusControllerMock;
    @Cascading
    private Cellblock driverMock;

    @Test
    public void testEnablePhone() {
        threadSUT = new PhoneStatusUpdateThread(phoneID, CellStatusType.ENABLED);
        threadSUT.setPhoneControlDAOAdapter(phoneControlDAOMock);
//        threadSUT.setDriverDAOAdapter(driverAdapterMock);
        threadSUT.setPhoneStatusController(phoneStatusControllerMock);

        new Expectations() {
            {
                phoneControlDAOMock.findByPhoneNumber(phoneID);
                returns(driverMock);
                phoneStatusControllerMock.setPhoneStatusEnabled(driverMock);
                times = 1;
            }
        };

        threadSUT.run();
    }

    @Test
    public void testDisablePhone() {
        threadSUT = new PhoneStatusUpdateThread(phoneID, CellStatusType.DISABLED);
        threadSUT.setPhoneControlDAOAdapter(phoneControlDAOMock);
        threadSUT.setPhoneStatusController(phoneStatusControllerMock);
        
        new Expectations() {
            {
                phoneControlDAOMock.findByPhoneNumber(phoneID);
                returns(driverMock);
                phoneStatusControllerMock.setPhoneStatusDisabled(driverMock);
                times = 1;
            }
        };
        
        threadSUT.run();
    }

    /**
     * Test for run() method when backend returns null.
     */
    @Test
    public void testRunWhenNullDriver() {
        threadSUT = new PhoneStatusUpdateThread(phoneID, CellStatusType.ENABLED);
        threadSUT.setPhoneControlDAOAdapter(phoneControlDAOMock);
        threadSUT.setPhoneStatusController(phoneStatusControllerMock);

        new Expectations() {
            {
                phoneControlDAOMock.findByPhoneNumber(phoneID);
                returns(null);
            }
        };

        threadSUT.run();
    }

}

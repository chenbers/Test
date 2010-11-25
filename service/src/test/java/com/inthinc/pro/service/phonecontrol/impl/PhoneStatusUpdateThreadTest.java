package com.inthinc.pro.service.phonecontrol.impl;

import mockit.Cascading;
import mockit.Expectations;
import mockit.Mocked;

import org.junit.Test;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.phone.CellStatusType;
import com.inthinc.pro.service.impl.BaseUnitTest;

/**
 * 
 */
public class PhoneStatusUpdateThreadTest extends BaseUnitTest {
    private final String phoneID = "1234567890";
    
    private PhoneStatusUpdateThread threadSUT;
    
    //Mock
    @Mocked private DriverDAO driverDaoMock;
    @Cascading private Driver driverMock;
    
    /**
     * Test for run() method.
     */
    @Test
    public void testRun() {
        threadSUT = new PhoneStatusUpdateThread(driverDaoMock, phoneID, CellStatusType.ENABLED);
        
        new Expectations() {
            {
                driverDaoMock.findByPhoneID(phoneID); returns(driverMock);
                driverMock.setCellStatus(CellStatusType.ENABLED); returns(null);
                driverDaoMock.update(driverMock); returns(0);
            }
        };
        
        threadSUT.run();
    }

    /**
     * Test for run() method when backend returns null.
     */
    @Test
    public void testRunWhenNullDriver() {
        threadSUT = new PhoneStatusUpdateThread(driverDaoMock, phoneID, CellStatusType.ENABLED);
        
        new Expectations() {
            {
                driverDaoMock.findByPhoneID(phoneID); returns(null);
            }
        };
        
        threadSUT.run();
    }

}

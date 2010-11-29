package com.inthinc.pro.service.phonecontrol.impl;

import mockit.Cascading;
import mockit.Expectations;
import mockit.Mocked;

import org.junit.Test;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.phone.CellStatusType;
import com.inthinc.pro.service.impl.BaseUnitTest;
import com.inthinc.pro.service.phonecontrol.dao.DriverPhoneDAO;

/**
 * Test for PhoneStatusUpdateThread.
 */
public class PhoneStatusUpdateThreadTest extends BaseUnitTest {
    private final String phoneID = "1234567890";
    private final Integer driverID = 10;
    
    private PhoneStatusUpdateThread threadSUT;
    
    //Mock
    @Mocked private DriverDAO driverDaoMock;
    @Mocked private DriverPhoneDAO phoneDaoMock;
    @Cascading private Driver driverMock;
    
    /**
     * Test for run() method.
     */
    @Test
    public void testEnablePhone() {
        threadSUT = new PhoneStatusUpdateThread(phoneID, CellStatusType.ENABLED);
        threadSUT.setDriverDAO(driverDaoMock);
        threadSUT.setDriverPhoneDAO(phoneDaoMock);
        
        new Expectations() {
            {
                driverDaoMock.findByPhoneID(phoneID); returns(driverMock);
                driverMock.setCellStatus(CellStatusType.ENABLED);
                driverDaoMock.update(driverMock); returns(0);
                driverMock.getDriverID(); returns(driverID); 
                phoneDaoMock.removeDriverFromDisabledPhoneList(driverID); returns(null);
                driverMock.getDriverID(); returns(driverID); 
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
        threadSUT.setDriverDAO(driverDaoMock);
        threadSUT.setDriverPhoneDAO(phoneDaoMock);
        
        new Expectations() {
            {
                driverDaoMock.findByPhoneID(phoneID); returns(null);
            }
        };
        
        threadSUT.run();
    }

}

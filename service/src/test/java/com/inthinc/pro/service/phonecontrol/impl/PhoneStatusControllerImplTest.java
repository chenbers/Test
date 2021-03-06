package com.inthinc.pro.service.phonecontrol.impl;

import static junit.framework.Assert.assertEquals;
import mockit.Verifications;

import org.junit.Test;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.PhoneControlDAO;
import com.inthinc.pro.model.Cellblock;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.phone.CellStatusType;
import com.inthinc.pro.service.phonecontrol.PhoneStatusController;
import com.inthinc.pro.service.phonecontrol.dao.DriverPhoneDAO;

public class PhoneStatusControllerImplTest {
    @Test
    public void testSetStausToEnabled(final PhoneControlDAO phoneControlDAOMock, final DriverPhoneDAO phoneDaoMock) {
        final Cellblock driver = new Cellblock();
//        driver.setCellblock(new Cellblock());
        driver.setDriverID(1);

        PhoneStatusController controller = new PhoneStatusControllerImpl(phoneControlDAOMock, phoneDaoMock);

        controller.setPhoneStatusEnabled(driver);

        assertEquals(CellStatusType.ENABLED, driver.getCellStatus());

        new Verifications() {
            {
                phoneControlDAOMock.update(driver);
                times = 1;

                phoneDaoMock.removeDriverFromDisabledPhoneList(driver.getDriverID());
                times = 1;
            }
        };
    }

    @Test
    public void testSetStausToDisabled(final PhoneControlDAO phoneControlDAOMock, final DriverPhoneDAO phoneDaoMock) {
        final Cellblock driver = new Cellblock();
//        driver.setCellblock(new Cellblock());
        driver.setDriverID(1);

        PhoneStatusController controller = new PhoneStatusControllerImpl(phoneControlDAOMock, phoneDaoMock);

        controller.setPhoneStatusDisabled(driver);

        assertEquals(CellStatusType.DISABLED, driver.getCellStatus());

        new Verifications() {
            {
                phoneControlDAOMock.update(driver);
                times = 1;

                phoneDaoMock.addDriverToDisabledPhoneList(driver.getDriverID());
                times = 1;
            }
        };
    }
}

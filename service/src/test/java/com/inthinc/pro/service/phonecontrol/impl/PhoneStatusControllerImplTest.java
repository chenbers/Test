package com.inthinc.pro.service.phonecontrol.impl;

import static junit.framework.Assert.assertEquals;
import mockit.Verifications;

import org.junit.Test;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.phone.CellStatusType;
import com.inthinc.pro.service.phonecontrol.PhoneStatusController;
import com.inthinc.pro.service.phonecontrol.dao.DriverPhoneDAO;

public class PhoneStatusControllerImplTest {
    @Test
    public void testSetStausToEnabled(final DriverDAO driverDaoMock, final DriverPhoneDAO phoneDaoMock) {
        final Driver driver = new Driver();
        driver.setCellProviderInfo(new Driver.CellProviderInfo());
        driver.setDriverID(1);

        PhoneStatusController controller = new PhoneStatusControllerImpl(driverDaoMock, phoneDaoMock);

        controller.setPhoneStatusEnabled(driver);

        assertEquals(CellStatusType.ENABLED, driver.getCellProviderInfo().getCellStatus());

        new Verifications() {
            {
                driverDaoMock.update(driver);
                times = 1;

                phoneDaoMock.removeDriverFromDisabledPhoneList(driver.getDriverID());
                times = 1;
            }
        };
    }

    @Test
    public void testSetStausToDisabled(final DriverDAO driverDaoMock, final DriverPhoneDAO phoneDaoMock) {
        final Driver driver = new Driver();
        driver.setCellProviderInfo(new Driver.CellProviderInfo());
        driver.setDriverID(1);

        PhoneStatusController controller = new PhoneStatusControllerImpl(driverDaoMock, phoneDaoMock);

        controller.setPhoneStatusDisabled(driver);

        assertEquals(CellStatusType.DISABLED, driver.getCellProviderInfo().getCellStatus());

        new Verifications() {
            {
                driverDaoMock.update(driver);
                times = 1;

                phoneDaoMock.addDriverToDisabledPhoneList(driver.getDriverID());
                times = 1;
            }
        };
    }
}

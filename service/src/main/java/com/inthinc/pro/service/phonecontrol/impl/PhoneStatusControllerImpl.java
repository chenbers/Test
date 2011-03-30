package com.inthinc.pro.service.phonecontrol.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.phone.CellStatusType;
import com.inthinc.pro.service.phonecontrol.PhoneStatusController;
import com.inthinc.pro.service.phonecontrol.dao.DriverPhoneDAO;

/**
 * Default implementation of {@link PhoneStatusController}.
 */
@Component
public class PhoneStatusControllerImpl implements PhoneStatusController {

    private static final Logger logger = Logger.getLogger(PhoneStatusControllerImpl.class);

    private DriverDAO driverDao;
    private DriverPhoneDAO phoneDao;

    @Autowired
    public PhoneStatusControllerImpl(DriverDAO driverDao, DriverPhoneDAO phoneDao) {
        this.driverDao = driverDao;
        this.phoneDao = phoneDao;
    }

    /**
     * @see com.inthinc.pro.service.phonecontrol.PhoneStatusController#setPhoneStatusDisabled(com.inthinc.pro.model.Driver)
     */
    @Override
    public void setPhoneStatusDisabled(Driver driver) {
        if (driver.getCellblock() != null) {
            logger.debug("Updating driver phone status to " + CellStatusType.DISABLED);
            driver.getCellblock().setCellStatus(CellStatusType.DISABLED);
            driverDao.update(driver);
            phoneDao.addDriverToDisabledPhoneList(driver.getDriverID());
            logger.debug("Phone status has been updated successfully. Driver has been added to disabled phone list.");
        } else {
            logger.debug("Provider info is not set for the driver. Ignoring request to update phone status.");
        }
    }

    /**
     * @see com.inthinc.pro.service.phonecontrol.PhoneStatusController#setPhoneStatusEnabled(com.inthinc.pro.model.Driver)
     */
    @Override
    public void setPhoneStatusEnabled(Driver driver) {
        if (driver.getCellblock() != null) {
            logger.debug("Updating driver phone status to " + CellStatusType.ENABLED);
            driver.getCellblock().setCellStatus(CellStatusType.ENABLED);
            driverDao.update(driver);
            phoneDao.removeDriverFromDisabledPhoneList(driver.getDriverID());
            logger.debug("Phone status has been updated successfully. Driver has been removed from disabled phone list.");
        } else {
            logger.debug("Provider info is not set for the driver. Ignoring request to update phone status.");
        }
    }

}

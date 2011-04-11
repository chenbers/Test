package com.inthinc.pro.service.phonecontrol.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.PhoneControlDAO;
import com.inthinc.pro.model.Cellblock;
import com.inthinc.pro.model.phone.CellStatusType;
import com.inthinc.pro.service.phonecontrol.PhoneStatusController;
import com.inthinc.pro.service.phonecontrol.dao.DriverPhoneDAO;

/**
 * Default implementation of {@link PhoneStatusController}.
 */
@Component
public class PhoneStatusControllerImpl implements PhoneStatusController {

    private static final Logger logger = Logger.getLogger(PhoneStatusControllerImpl.class);

    private PhoneControlDAO phoneControlDAO;
    private DriverPhoneDAO phoneDao;

    @Autowired
    public PhoneStatusControllerImpl(PhoneControlDAO phoneControlDAO, DriverPhoneDAO phoneDao) {
        this.phoneControlDAO = phoneControlDAO;
        this.phoneDao = phoneDao;
    }

    /**
     * @see com.inthinc.pro.service.phonecontrol.PhoneStatusController#setPhoneStatusDisabled(com.inthinc.pro.model.Cellblock)
     */
    @Override
    public void setPhoneStatusDisabled(Cellblock driver) {
        if (driver != null) {
            logger.debug("Updating driver phone status to " + CellStatusType.DISABLED);
            driver.setCellStatus(CellStatusType.DISABLED);
            phoneControlDAO.update(driver);
            phoneDao.addDriverToDisabledPhoneList(driver.getDriverID());
            logger.debug("Phone status has been updated successfully. Cellblock has been added to disabled phone list.");
        } else {
            logger.debug("Provider info is not set for the driver. Ignoring request to update phone status.");
        }
    }

    /**
     * @see com.inthinc.pro.service.phonecontrol.PhoneStatusController#setPhoneStatusEnabled(com.inthinc.pro.model.Cellblock)
     */
    @Override
    public void setPhoneStatusEnabled(Cellblock driver) {
        if (driver != null) {
            logger.debug("Updating driver phone status to " + CellStatusType.ENABLED);
            driver.setCellStatus(CellStatusType.ENABLED);
            phoneControlDAO.update(driver);
            phoneDao.removeDriverFromDisabledPhoneList(driver.getDriverID());
            logger.debug("Phone status has been updated successfully. Cellblock has been removed from disabled phone list.");
        } else {
            logger.debug("Provider info is not set for the driver. Ignoring request to update phone status.");
        }
    }

}

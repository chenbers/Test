package com.inthinc.pro.service.phonecontrol.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.phone.CellStatusType;
import com.inthinc.pro.service.phonecontrol.dao.DriverPhoneDAO;

/**
 * Thread class to have an asynchronous update.
 */
public class PhoneStatusUpdateThread extends Thread {
    private static Logger logger = Logger.getLogger(PhoneStatusUpdateThread.class);
    private static final String LOG_PREFIX = "UpdateStatus Request ";
    
    private String phoneId; 
    private CellStatusType status;
    @Autowired
    private DriverDAO driverDAO;
    @Autowired
    private DriverPhoneDAO phoneDAO;
    
    /**
     * Default constructor.
     * @param driverDAO the DAO to get/update the driver.
     * @param phoneId the driver's phone number
     * @param status the cell status: ENABLED/DISABLED
     */
    PhoneStatusUpdateThread(String phoneId, CellStatusType status) {
        this.phoneId = phoneId;
        this.status = status;
    }
    
    /**
     * {@inheritDoc}
     * @see java.lang.Thread#run()
     */
    @Override
    public void run() {
        logger.debug(LOG_PREFIX + "started for phoneID: " + phoneId + ", status: " + status);
        Driver driver = driverDAO.findByPhoneID(phoneId);
        logger.debug(LOG_PREFIX + "called driverDAO.findByPhoneID(), returned: " + driver);
        if (driver != null) {
            if (status == CellStatusType.DISABLED) {
                phoneDAO.addDriverToDisabledPhoneList(driver.getDriverID());
                logger.debug(LOG_PREFIX + "added " + driver.getDriverID() + " to DisabledPhoneList");
            } else {
                phoneDAO.removeDriverFromDisabledPhoneList(driver.getDriverID());
                logger.debug(LOG_PREFIX + "removed " + driver.getDriverID() + " from DisabledPhoneList");
            }
            
            logger.debug(LOG_PREFIX + " is updating driver.. ");
            driver.setCellStatus(status);
            driverDAO.update(driver);
        }
        
        logger.debug(LOG_PREFIX + "done.");
    }
    
    /**
     * The driverDAO setter.
     * @param driverDAO the driverDAO to set
     */
    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    /**
     * The driverPhoneDAO setter.
     * @param driverPhoneDAO the driverPhoneDAO to set
     */
    public void setDriverPhoneDAO(DriverPhoneDAO driverPhoneDAO) {
        this.phoneDAO = driverPhoneDAO;
    }
}
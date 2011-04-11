package com.inthinc.pro.service.phonecontrol.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.inthinc.pro.model.Cellblock;
import com.inthinc.pro.model.phone.CellStatusType;
import com.inthinc.pro.service.adapters.PhoneControlDAOAdapter;
import com.inthinc.pro.service.phonecontrol.PhoneStatusController;

/**
 * Thread class to have an asynchronous update.
 */
public class PhoneStatusUpdateThread extends Thread {
    private static Logger logger = Logger.getLogger(PhoneStatusUpdateThread.class);
    private static final String LOG_PREFIX = "Change Status Request ";
    private static final String PHONE_PATTERN = "^\\d{7,12}$";

    private String phoneId;
    private CellStatusType status;

//    @Autowired
//    private DriverDAOAdapter driverDAOAdapter;

    @Autowired
    private PhoneStatusController phoneStatusController;

    @Autowired
    private PhoneControlDAOAdapter phoneControlDAOAdapter;
    /**
     * Default constructor.
     * 
     * @param driverDAO
     *            the DAO to get/update the driver.
     * @param phoneId
     *            the driver's phone number
     * @param status
     *            the cell status: ENABLED/DISABLED
     */
    PhoneStatusUpdateThread(String phoneId, CellStatusType status) {
        this.phoneId = phoneId;
        this.status = status;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Thread#run()
     */
    @Override
    public void run() {
        if (phoneId == null || !phoneId.matches(PHONE_PATTERN)) {
            logger.error(LOG_PREFIX + "validation: PhoneID doesn't match " + PHONE_PATTERN);
            return;
        }

        logger.debug(LOG_PREFIX + "started for PH#-" + phoneId + " Status-" + status);
        try {
//            Driver driver = driverDAOAdapter.findByPhoneNumber(phoneId);
//            logger.debug(LOG_PREFIX + "called driverDAO.findByPhoneID(), returned: " + driver);
//            
//            if (driver != null) {
//                logger.debug(LOG_PREFIX + " is updating driver.. ");
                Cellblock cellblock = phoneControlDAOAdapter.findByPhoneNumber(phoneId);
                if (status == CellStatusType.DISABLED) {
                    phoneStatusController.setPhoneStatusDisabled(cellblock);
//                    logger.debug(LOG_PREFIX + "added " + cellblock.getDriverID() + " to DisabledPhoneList");
                } else {
                    phoneStatusController.setPhoneStatusEnabled(cellblock);
//                    logger.debug(LOG_PREFIX + "removed " + cellblock.getDriverID() + " from DisabledPhoneList");
                }

//                logger.debug(LOG_PREFIX + status + " for PH#-" + phoneId); // should we log as info?
//            }

        } catch (Exception e) {
            logger.error(LOG_PREFIX + " to " + status + " failed!", e);
        }
    }

//    /**
//     * The driverDAO setter.
//     * 
//     * @param driverDAO
//     *            the driverDAO to set
//     */
//    public void setDriverDAOAdapter(DriverDAOAdapter driverDAOAdapter) {
//        this.driverDAOAdapter = driverDAOAdapter;
//    }

    /**
     * The driverPhoneDAO setter.
     * 
     * @param driverPhoneDAO
     *            the driverPhoneDAO to set
     */
    public void setPhoneStatusController(PhoneStatusController phoneStatusController) {
        this.phoneStatusController = phoneStatusController;
    }

    public void setPhoneControlDAOAdapter(PhoneControlDAOAdapter phoneControlDAOAdapter) {
        this.phoneControlDAOAdapter = phoneControlDAOAdapter;
    }
}
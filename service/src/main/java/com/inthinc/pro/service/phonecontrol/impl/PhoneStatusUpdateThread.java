package com.inthinc.pro.service.phonecontrol.impl;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.phone.CellStatusType;

/**
 * Thread class to have an asynchronous update.
 */
public class PhoneStatusUpdateThread extends Thread {
    private String phoneId; 
    private CellStatusType status;
    private DriverDAO driverDAO;
    
    PhoneStatusUpdateThread(DriverDAO driverDAO, String phoneId, CellStatusType status) {
        this.driverDAO = driverDAO;
        this.phoneId = phoneId;
        this.status = status;
    }
    
    @Override
    public void run() {
        Driver driver = driverDAO.findByPhoneID(phoneId);
        if (driver != null) {
            driver.setCellStatus(status);
            driverDAO.update(driver);
        }
    }
    
    /**
     * The driverDAO setter.
     * @param driverDAO the driverDAO to set
     */
    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

}
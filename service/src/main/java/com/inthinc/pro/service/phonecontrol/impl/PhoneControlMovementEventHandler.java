/**
 * 
 */
package com.inthinc.pro.service.phonecontrol.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.service.phonecontrol.MovementEventHandler;
import com.inthinc.pro.service.phonecontrol.PhoneControlAdapter;
import com.inthinc.pro.service.phonecontrol.PhoneControlAdapterFactory;

/**
 * {@link MovementEventHandler} which requests phone control service provider to disable/enable driver's cell phone once it starts/stops driving.
 */
@Component
public class PhoneControlMovementEventHandler implements MovementEventHandler {

    private static final Logger logger = Logger.getLogger(PhoneControlMovementEventHandler.class);
    
    private final DriverDAO driverDao;
    private PhoneControlAdapterFactory serviceFactory;

    /**
     * Creates an instance of {@link PhoneControlMovementEventHandler}.
     * 
     * @param driverDao
     *            The {@link DriverDAO} instance to use to obtain information about the driver.
     * @param serviceFactory
     *            An instance of the {@link PhoneControlAdapterFactory} to be used to create {@link PhoneControlAdapter} client endpoints.
     */
    @Autowired
    public PhoneControlMovementEventHandler(DriverDAO driverDao, PhoneControlAdapterFactory serviceFactory) {
        this.driverDao = driverDao;
        this.serviceFactory = serviceFactory;
    }

    /**
     * Handles the 'driver started moving' event and signals the phone control service provider to disable the driver's cell phone, if applicable (i.e. driver has the service
     * enabled).
     * 
     * @see com.inthinc.pro.service.phonecontrol.MovementEventHandler#handleDriverStartedMoving(java.lang.Integer)
     */
    // TODO Add logic to handle error once retry user story is implemented.
    @Override
    public void handleDriverStartedMoving(Integer driverId) {
        logger.debug("Querying backend for driver information. Driver ID = " + driverId);
        Driver driver = driverDao.findByID(driverId);
        String cellPhoneNumber = driver.getCellPhone();
        logger.debug("Obtained driver cellphone from the back end. Cell phone # is '" + cellPhoneNumber + "'");

        PhoneControlAdapter phoneControlAdapter = serviceFactory.createAdapter(driver.getProvider());
        phoneControlAdapter.disablePhone(cellPhoneNumber);
    }

    /**
     * @see com.inthinc.pro.service.phonecontrol.MovementEventHandler#handleDriverStoppedMoving(java.lang.Integer)
     */
    // TODO Add logic to handle error once retry user story is implemented.
    @Override
    public void handleDriverStoppedMoving(Integer driverId) {
        logger.debug("Querying backend for driver information. Driver ID = " + driverId);
        Driver driver = driverDao.findByID(driverId);
        String cellPhoneNumber = driver.getCellPhone();
        logger.debug("Obtained driver cellphone from the back end. Cell phone # is '" + cellPhoneNumber + "'");

        PhoneControlAdapter phoneControlService = serviceFactory.createAdapter(driver.getProvider());
        phoneControlService.enablePhone(cellPhoneNumber);
    }
}

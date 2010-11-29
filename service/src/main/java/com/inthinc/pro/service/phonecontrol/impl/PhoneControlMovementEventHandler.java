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
        Driver driver = getDriverInfo(driverId);

        if (driver != null) {
            disableDriverPhone(driver);
        }
    }

    /**
     * @see com.inthinc.pro.service.phonecontrol.MovementEventHandler#handleDriverStoppedMoving(java.lang.Integer)
     */
    // TODO Add logic to handle error once retry user story is implemented.
    @Override
    public void handleDriverStoppedMoving(Integer driverId) {
        Driver driver = getDriverInfo(driverId);

        if (driver != null) {
            enableDriverPhone(driver);
        }
    }

    private Driver getDriverInfo(Integer driverId) {
        Driver driver = null;

        try {
            driver = driverDao.findByID(driverId);

            if (driver == null) {
                logger.warn("No information is available for driver DID-" + driverId + ".");
            } else {
                logger.debug("Obtained driver info from the back end. DID-" + driverId + ", PH#-" + driver.getCellPhone() + ", provider: " + driver.getProvider());
            }
        } catch (Throwable e) {
            logger.warn("Unable to query driver DID-" + driverId + " information due to an internal error:", e);
        }

        return driver;
    }

    private void disableDriverPhone(Driver driver) {
        if (driver.getProvider() != null) {

            if (driver.getCellPhone() != null) {
                PhoneControlAdapter phoneControlAdapter = serviceFactory.createAdapter(driver.getProvider());
                phoneControlAdapter.disablePhone(driver.getCellPhone());
            } else {
                logger.warn("Driver DID-" + driver.getDriverID() + " has no associated cell phone number.");
            }

        } else {
            logger.warn("No provider information is available for the driver DID-" + driver.getDriverID() + ".");
        }
    }

    private void enableDriverPhone(Driver driver) {
        if (driver.getProvider() != null) {

            if (driver.getCellPhone() != null) {
                PhoneControlAdapter phoneControlAdapter = serviceFactory.createAdapter(driver.getProvider());
                phoneControlAdapter.enablePhone(driver.getCellPhone());
            } else {
                logger.warn("Driver DID-" + driver.getDriverID() + " has no cell phone number associated with him.");
            }

        } else {
            logger.warn("No provider information is available for the driver DID-" + driver.getDriverID() + ".");
        }
    }
}

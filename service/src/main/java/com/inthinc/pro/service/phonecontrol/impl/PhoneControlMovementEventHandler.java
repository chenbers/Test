/**
 * 
 */
package com.inthinc.pro.service.phonecontrol.impl;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.service.phonecontrol.MovementEventHandler;
import com.inthinc.pro.service.phonecontrol.PhoneControlAdapter;
import com.inthinc.pro.service.phonecontrol.PhoneControlAdapterFactory;

/**
 * {@link MovementEventHandler} which requests phone control service provider to disable/enable driver's cell phone once it starts/stops driving.
 */
public class PhoneControlMovementEventHandler implements MovementEventHandler {

    private final DriverDAO driverDao;
    private PhoneControlAdapterFactory serviceFactory;

    /**
     * Creates an instance of {@link PhoneControlMovementEventHandler}.
     * 
     * @param driverDao
     *            THe {@link DriverDAO} instance to use to obtain information about the driver.
     * @param serviceFactory
     *            An instance of the {@link PhoneControlAdapterFactory} to be used to create {@link PhoneControlAdapter} client endpoints.
     */
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
        Driver driver = driverDao.findByID(driverId);
        String cellPhoneNumber = driver.getCellPhone();

        PhoneControlAdapter phoneControlService = serviceFactory.createServiceEndpoint(driver.getProvider());
        phoneControlService.disablePhone(cellPhoneNumber);
    }

    /**
     * @see com.inthinc.pro.service.phonecontrol.MovementEventHandler#handleDriverStoppedMoving(java.lang.Integer)
     */
    // TODO Add logic to handle error once retry user story is implemented.
    @Override
    public void handleDriverStoppedMoving(Integer driverId) {
        Driver driver = driverDao.findByID(driverId);
        String cellPhoneNumber = driver.getCellPhone();

        PhoneControlAdapter phoneControlService = serviceFactory.createServiceEndpoint(driver.getProvider());
        phoneControlService.enablePhone(cellPhoneNumber);
    }
}

/**
 * 
 */
package com.inthinc.pro.service.phonecontrol.impl;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.service.phonecontrol.MovementEventHandler;

/**
 * {@link MovementEventHandler} which requests phone control service provider to disable/enable driver's cell phone once it starts/stops driving.
 */
public class PhoneControlMovementEventHandler implements MovementEventHandler {

    private final DriverDAO driverDao;

    /**
     * Creates an instance of {@link PhoneControlMovementEventHandler}.
     * 
     * @param driverDao
     *            THe {@link DriverDAO} instance to use to obtain information about the driver.
     */
    public PhoneControlMovementEventHandler(DriverDAO driverDao) {
        this.driverDao = driverDao;
    }

    /**
     * Handles the 'driver started moving' event and signals the phone control service provider to disable the driver's cell phone, if applicable (i.e. driver has the service
     * enabled).
     * 
     * @see com.inthinc.pro.service.phonecontrol.MovementEventHandler#handleDriverStartedMoving(java.lang.Integer)
     */
    @Override
    public void handleDriverStartedMoving(Integer driverId) {
        Driver driver = driverDao.findByID(driverId);
        driver.getPerson().getPriPhone();
        // TODO Check if driver has a Cellcontrol/Zoomsafer cell phone account and
        // fire the request to the appropriate service.
    }
}

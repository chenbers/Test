package com.inthinc.pro.service.phonecontrol;

/**
 * Handles events fired by the Notes server about drivers starting/stopping to drive.
 */
public interface MovementEventHandler {

    /**
     * Handles the 'driver start moving' event.
     * 
     * @param driverID
     *            The id of the driver who started to drive.
     */
    void handleDriverStartedMoving(Integer driverID);

    /**
     * Handles the 'driver stopped moving' event.
     * 
     * @param driverId
     *            The id of the driver who stopped driving.
     */
    void handleDriverStoppedMoving(Integer driverID);
}

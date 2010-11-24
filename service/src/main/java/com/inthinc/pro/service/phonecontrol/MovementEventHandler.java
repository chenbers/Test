package com.inthinc.pro.service.phonecontrol;

/**
 * Handles events fired by the Notes server about drivers starting/stopping to drive.
 */
public interface MovementEventHandler {

    /**
     * Handles the 'driver start moving' event.
     * 
     * @param driverId
     *            The id of the driver which started to drive.
     */
    void handleDriverStartedMoving(Integer driverId);
}

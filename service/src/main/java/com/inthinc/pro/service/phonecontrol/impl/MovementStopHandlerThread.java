package com.inthinc.pro.service.phonecontrol.impl;

import com.inthinc.pro.service.phonecontrol.MovementEventHandler;

public class MovementStopHandlerThread extends MovementEventHandlerThread implements Runnable {

    public MovementStopHandlerThread(MovementEventHandler movementEventHandler, Integer driverID) {
        super(movementEventHandler, driverID);
    }

    @Override
    public void execute() {
        movementEventHandler.handleDriverStoppedMoving(driverID);
    }
}

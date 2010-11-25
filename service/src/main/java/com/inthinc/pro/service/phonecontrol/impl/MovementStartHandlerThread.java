package com.inthinc.pro.service.phonecontrol.impl;

import com.inthinc.pro.service.phonecontrol.MovementEventHandler;

public class MovementStartHandlerThread extends MovementEventHandlerThread implements Runnable {

    public MovementStartHandlerThread(MovementEventHandler movementEventHandler, Integer driverID) {
        super(movementEventHandler, driverID);
    }

    @Override
    public void run() {
        movementEventHandler.handleDriverStartedMoving(driverID);
    }

}

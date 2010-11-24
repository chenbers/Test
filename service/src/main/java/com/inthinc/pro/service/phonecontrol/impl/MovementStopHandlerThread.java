package com.inthinc.pro.service.phonecontrol.impl;

import com.inthinc.pro.service.phonecontrol.MovementEventHandler;

public class MovementStopHandlerThread extends MovementEventHandlerThread implements Runnable {

    public MovementStopHandlerThread(Integer driverID) {
        super(driverID);
    }

    @Override
    public void run() {
        movementEventHandler.handleDriverStoppedMoving(driverID);
    }

}

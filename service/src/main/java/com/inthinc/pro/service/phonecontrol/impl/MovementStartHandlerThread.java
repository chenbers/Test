package com.inthinc.pro.service.phonecontrol.impl;

public class MovementStartHandlerThread extends MovementEventHandlerThread implements Runnable {

    public MovementStartHandlerThread(Integer driverID) {
        super(driverID);
    }

    @Override
    public void run() {
        movementEventHandler.handleDriverStartedMoving(driverID);
    }

}

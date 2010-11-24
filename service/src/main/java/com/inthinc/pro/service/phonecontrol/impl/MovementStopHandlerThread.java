package com.inthinc.pro.service.phonecontrol.impl;


public class MovementStopHandlerThread extends MovementEventHandlerThread implements Runnable {

    public MovementStopHandlerThread(Integer driverID) {
        super(driverID);
    }

    @Override
    public void run() {
        movementEventHandler.handleDriverStoppedMoving(driverID);
    }

}

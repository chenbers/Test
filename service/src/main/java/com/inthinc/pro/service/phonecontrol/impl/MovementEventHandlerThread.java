package com.inthinc.pro.service.phonecontrol.impl;

import com.inthinc.pro.service.phonecontrol.MovementEventHandler;

public abstract class MovementEventHandlerThread implements Runnable {
    
    MovementEventHandler movementEventHandler;
    Integer driverID;
    
    public MovementEventHandlerThread(Integer driverID) {
        this.driverID = driverID;
    }

    public abstract void run();

}

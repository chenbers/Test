package com.inthinc.pro.service.phonecontrol.impl;

import com.inthinc.pro.service.phonecontrol.MovementEventHandler;

public abstract class MovementEventHandlerThread implements Runnable {
    
    MovementEventHandler movementEventHandler;
    Integer driverID;
    
    public MovementEventHandlerThread(MovementEventHandler movementEventHandler, Integer driverID) {
        this.driverID = driverID;
        this.movementEventHandler = movementEventHandler;
    }
    
    public abstract void run();

    public MovementEventHandler getMovementEventHandler() {
        return movementEventHandler;
    }

    public void setMovementEventHandler(MovementEventHandler movementEventHandler) {
        this.movementEventHandler = movementEventHandler;
    }

    public Integer getDriverID() {
        return driverID;
    }

    public void setDriverID(Integer driverID) {
        this.driverID = driverID;
    }

}

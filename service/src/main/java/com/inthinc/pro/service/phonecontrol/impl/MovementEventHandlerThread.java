package com.inthinc.pro.service.phonecontrol.impl;

import org.apache.log4j.Logger;

import com.inthinc.pro.service.phonecontrol.MovementEventHandler;

/**
 * Template method class for asynchronously handling driver start/stop driving events.
 * <p/>
 * This class implements {@link Runnable}, making it ready to run inside a Thread. The {@link Runnable#run()} method implementation will invoke the template method
 * {@link MovementEventHandlerThread#execute()}.
 */
public abstract class MovementEventHandlerThread implements Runnable {

    private static final Logger logger = Logger.getLogger(MovementEventHandlerThread.class);

    MovementEventHandler movementEventHandler;
    Integer driverID;

    public MovementEventHandlerThread(MovementEventHandler movementEventHandler, Integer driverID) {
        this.driverID = driverID;
        this.movementEventHandler = movementEventHandler;
    }

    public void run() {
        try {
            execute();
        } catch (Throwable e) {
            logger.warn("An error was detected while handling the request.", e);
        }
    }

    /**
     * Executes this handler to handle the event fired by the notes server.
     * <p/>
     * This is a template method to be implemented by children classes.
     */
    public abstract void execute();

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

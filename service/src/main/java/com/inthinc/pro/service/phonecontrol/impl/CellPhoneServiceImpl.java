package com.inthinc.pro.service.phonecontrol.impl;

import javax.ws.rs.core.Response;

import com.inthinc.pro.model.phone.CellStatusType;
import com.inthinc.pro.service.phonecontrol.CellPhoneService;
import com.inthinc.pro.service.phonecontrol.MovementEventHandler;

public class CellPhoneServiceImpl implements CellPhoneService {
    
    private MovementEventHandler movementEventHandler;

    @Override
    public Response processStartMotionEvent(Integer driverID) {   
        new Thread(new MovementStartHandlerThread(movementEventHandler, driverID)).start();
        return Response.ok().build();

    }
    
    @Override
    public Response processStopMotionEvent(Integer driverID) {   
        new Thread(new MovementStopHandlerThread(movementEventHandler, driverID)).start();
        return Response.ok().build();

    }

    @Override
    public Response updateStatus(String phoneId, CellStatusType status) {
        // TODO Auto-generated method stub
        return null;
    }

    public MovementEventHandler getMovementEventHandler() {
        return movementEventHandler;
    }

    public void setMovementEventHandler(MovementEventHandler movementEventHandler) {
        this.movementEventHandler = movementEventHandler;
    }

}

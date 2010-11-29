package com.inthinc.pro.service.phonecontrol.impl;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.model.phone.CellStatusType;
import com.inthinc.pro.service.phonecontrol.CellPhoneService;
import com.inthinc.pro.service.phonecontrol.MovementEventHandler;

/**
 * CellPhoneService implementation class.
 */
@Component
public class CellPhoneServiceImpl implements CellPhoneService {
    private static Logger logger = Logger.getLogger(CellPhoneServiceImpl.class);
    @Autowired
    private MovementEventHandler movementEventHandler;

    @Override
    public Response processStartMotionEvent(Integer driverID) {   
        logger.info("Start motion request received from Note Server for driver " + driverID);
        new Thread(new MovementStartHandlerThread(movementEventHandler, driverID)).start();
        return Response.ok().build();

    }

    @Override
    public Response processStopMotionEvent(Integer driverID) {   
        logger.info("Stop motion request received from Note Server for driver " + driverID);
        new Thread(new MovementStopHandlerThread(movementEventHandler, driverID)).start();
        return Response.ok().build();

    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.phonecontrol.CellPhoneService#update(java.lang.String, com.inthinc.pro.model.phone.CellStatusType)
     */
    @Override
    public Response updateStatus(String phoneId, CellStatusType status) {
        logger.debug("UpdateStatus Request received for phoneID: " + phoneId + ", status: " + status);
        new PhoneStatusUpdateThread(phoneId, status).start();
        
        return Response.ok().build();
    }

    public MovementEventHandler getMovementEventHandler() {
        return movementEventHandler;
    }

    public void setMovementEventHandler(MovementEventHandler movementEventHandler) {
        this.movementEventHandler = movementEventHandler;
    }
}

package com.inthinc.pro.service.phonecontrol.impl;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.phone.CellStatusType;
import com.inthinc.pro.service.phonecontrol.CellPhoneService;
import com.inthinc.pro.service.phonecontrol.MovementEventHandler;
import com.inthinc.pro.service.phonecontrol.dao.DriverPhoneDAO;

/**
 * CellPhoneService implementation class.
 */
@Component
public class CellPhoneServiceImpl implements CellPhoneService {
    private static Logger logger = Logger.getLogger(CellPhoneServiceImpl.class);
    
    @Autowired
    private MovementEventHandler movementEventHandler;

    @Autowired private DriverDAO driverDAO;    
    @Autowired private DriverPhoneDAO phoneDAO;


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
     * @see com.inthinc.pro.service.phonecontrol.CellPhoneService#setStatusEnabled(java.lang.String)
     */
    @Override
    public Response setStatusEnabled(String phoneId) {
        logger.debug("setStatus enabled Request received for phoneID: " + phoneId);
        PhoneStatusUpdateThread update = new PhoneStatusUpdateThread(phoneId, CellStatusType.ENABLED);
        update.setDriverDAO(this.driverDAO);
        update.setDriverPhoneDAO(this.phoneDAO);
        update.start();
        
        return Response.ok().build();
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.phonecontrol.CellPhoneService#setStatusDisabled(java.lang.String)
     */
    @Override
    public Response setStatusDisabled(String phoneId) {
        logger.debug("setStatus disabled Request received for phoneID: " + phoneId);
        PhoneStatusUpdateThread update = new PhoneStatusUpdateThread(phoneId, CellStatusType.DISABLED);
        update.setDriverDAO(this.driverDAO);
        update.setDriverPhoneDAO(this.phoneDAO);
        update.start();
        
        return Response.ok().build();
    }

    /**
     * The driverDAO setter.
     * @param driverDAO the driverDAO to set
     */
    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    /**
     * The phoneDAO setter.
     * @param phoneDAO the phoneDAO to set
     */
    public void setPhoneDAO(DriverPhoneDAO phoneDAO) {
        this.phoneDAO = phoneDAO;
    }

    public MovementEventHandler getMovementEventHandler() {
        return movementEventHandler;
    }

    public void setMovementEventHandler(MovementEventHandler movementEventHandler) {
        this.movementEventHandler = movementEventHandler;
    }
}

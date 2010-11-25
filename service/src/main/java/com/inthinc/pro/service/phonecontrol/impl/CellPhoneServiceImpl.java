package com.inthinc.pro.service.phonecontrol.impl;

import javax.ws.rs.core.Response;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.phone.CellStatusType;
import com.inthinc.pro.service.phonecontrol.CellPhoneService;
import com.inthinc.pro.service.phonecontrol.MovementEventHandler;

/**
 * CellPhoneService implementation class.
 */
public class CellPhoneServiceImpl implements CellPhoneService {
    private DriverDAO driverDAO;
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

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.phonecontrol.CellPhoneService#update(java.lang.String, com.inthinc.pro.model.phone.CellStatusType)
     */
    @Override
    public Response updateStatus(String phoneId, CellStatusType status) {
        
        new PhoneStatusUpdateThread(driverDAO, phoneId, status).start();
        
        return Response.ok().build();
    }

    /**
     * The driverDAO setter.
     * @param driverDAO the driverDAO to set
     */
    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    public MovementEventHandler getMovementEventHandler() {
        return movementEventHandler;
    }

    public void setMovementEventHandler(MovementEventHandler movementEventHandler) {
        this.movementEventHandler = movementEventHandler;
    }
}

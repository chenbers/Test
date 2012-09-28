package com.inthinc.pro.service.phonecontrol.impl;

import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.inthinc.pro.model.Cellblock;
import com.inthinc.pro.model.phone.CellStatusType;
import com.inthinc.pro.service.adapters.PhoneControlDAOAdapter;
import com.inthinc.pro.service.impl.AbstractService;
import com.inthinc.pro.service.phonecontrol.CellPhoneService;
import com.inthinc.pro.service.phonecontrol.MovementEventHandler;
import com.inthinc.pro.service.phonecontrol.PhoneStatusController;

/**
 * CellPhoneService implementation class.
 */

public class CellPhoneServiceImpl extends AbstractService<Cellblock, PhoneControlDAOAdapter>implements CellPhoneService {
    private static Logger logger = Logger.getLogger(CellPhoneServiceImpl.class);

    @Autowired
    private MovementEventHandler movementEventHandler;

//    @Autowired
//    private DriverDAOAdapter driverAdapter;

    @Autowired
    private PhoneControlDAOAdapter phoneControlDAOAdapter;
    
    @Autowired
    private PhoneStatusController phoneStatusController;

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
     * 
     * @see com.inthinc.pro.service.phonecontrol.CellPhoneService#setStatusEnabled(java.lang.String)
     */
    @Override
    public Response setStatusEnabled(String phoneId) {
        logger.debug("setStatus enabled Request received for phoneID: " + phoneId);
        PhoneStatusUpdateThread update = createPhoneStatusUpdateThread(phoneId, CellStatusType.ENABLED);
        update.start();

        return Response.ok().build();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inthinc.pro.service.phonecontrol.CellPhoneService#setStatusDisabled(java.lang.String)
     */
    @Override
    public Response setStatusDisabled(String phoneId) {
        logger.debug("setStatus disabled Request received for phoneID: " + phoneId);
        PhoneStatusUpdateThread update = createPhoneStatusUpdateThread(phoneId, CellStatusType.DISABLED);
        update.start();

        return Response.ok().build();
    }
    
    private PhoneStatusUpdateThread createPhoneStatusUpdateThread(String phoneId, CellStatusType status ){
     
        PhoneStatusUpdateThread update = new PhoneStatusUpdateThread(phoneId, status);
        update.setPhoneControlDAOAdapter(this.phoneControlDAOAdapter);
        update.setPhoneStatusController(this.phoneStatusController);
        return update;
    }
    /**
     * The driverDAOAdapter setter.
     * 
     * @param driverDAOAdapter
     *            the driverDAOAdapter to set
     */
//    public void setDriverDAOAdapter(DriverDAOAdapter driverDAOAdapter) {
//        this.driverAdapter = driverDAOAdapter;
//    }

    public MovementEventHandler getMovementEventHandler() {
        return movementEventHandler;
    }

    public void setMovementEventHandler(MovementEventHandler movementEventHandler) {
        this.movementEventHandler = movementEventHandler;
    }

    public void setPhoneStatusController(PhoneStatusController phoneStatusController) {
        this.phoneStatusController = phoneStatusController;
    }

    public void setPhoneControlDAOAdapter(PhoneControlDAOAdapter phoneControlDAOAdapter) {
        this.phoneControlDAOAdapter = phoneControlDAOAdapter;
    }

    @Override
    public Response create(List<Cellblock> list, UriInfo uriInfo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response getAll() {
        // TODO Auto-generated method stub
        return null;
    }
}

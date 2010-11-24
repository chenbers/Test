package com.inthinc.pro.service.phonecontrol.impl;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.inthinc.pro.model.phone.CellStatusType;
import com.inthinc.pro.service.phonecontrol.CellPhoneService;
import com.iwi.teenserver.dao.hessian.mcm.IWINotificationType;

public class CellPhoneServiceImpl implements CellPhoneService {

    @Override
    public Response processStartMotionEvent(Integer driverID) {   
        new Thread(new MovementStartHandlerThread(driverID)).start();
        return Response.ok().build();

    }
    
    @Override
    public Response processStopMotionEvent(Integer driverID) {   
        new Thread(new MovementStopHandlerThread(driverID)).start();
        return Response.ok().build();

    }

    @Override
    public Response updateStatus(String phoneId, CellStatusType status) {
        // TODO Auto-generated method stub
        return null;
    }

}

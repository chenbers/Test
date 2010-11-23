package com.inthinc.pro.service.phonecontrol.impl;

import javax.ws.rs.core.Response;

import com.inthinc.pro.model.phone.CellStatusType;
import com.inthinc.pro.service.phonecontrol.CellPhoneService;
import com.iwi.teenserver.dao.hessian.mcm.IWINotificationType;

public class CellPhoneServiceImpl implements CellPhoneService {

    @Override
    public Response createEvent(Integer driverID, IWINotificationType event) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response update(String phoneId, CellStatusType status) {
        // TODO Auto-generated method stub
        return null;
    }
    
    

}

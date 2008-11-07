package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.proserver.CentralService;
import com.inthinc.pro.model.Device;

public class DeviceHessianDAO extends GenericHessianDAO<Device, Integer, CentralService> implements DeviceDAO
{
    @Override
    public List<Device> getDevicesByAcctID(Integer accountID)
    {
        try
        {
            return convertToModelObject(getSiloService().getDevicesByAcctID(accountID));
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }
}

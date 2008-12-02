package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.proserver.CentralService;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.ForwardCommand;
import com.inthinc.pro.model.ForwardCommandStatus;

public class DeviceHessianDAO extends GenericHessianDAO<Device, Integer> implements DeviceDAO
{
    @Override
    public List<Device> getDevicesByAcctID(Integer accountID)
    {
        try
        {
            return getMapper().convertToModelObject(getSiloService().getDevicesByAcctID(accountID), Device.class);
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }

    @Override
    public List<ForwardCommand> getForwardCommands(Integer deviceID, ForwardCommandStatus status)
    {
        try
        {
            return getMapper().convertToModelObject(getSiloService().getFwdCmds(deviceID, status.getCode()), ForwardCommand.class);
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }

    @Override
    public Integer queueForwardCommand(Integer deviceID, ForwardCommand forwardCommand)
    {
        return getChangedCount(getSiloService().queueFwdCmd(deviceID, getMapper().convertToMap(forwardCommand)));
    }
}

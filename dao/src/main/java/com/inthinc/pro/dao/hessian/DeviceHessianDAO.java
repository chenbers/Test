package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.ForwardCommand;
import com.inthinc.pro.model.ForwardCommandStatus;

public class DeviceHessianDAO extends GenericHessianDAO<Device, Integer> implements DeviceDAO
{
    private static final String CENTRAL_ID_KEY = "mcmid";

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
    public Device findByIMEI(String imei)
    {
        
        // TODO: it can take up to 5 minutes from when a user record is added until
        // it can be accessed via getID().   Should this method account for that?
        try
        {
            Map<String, Object> returnMap = getSiloService().getID(CENTRAL_ID_KEY, imei);
            Integer deviceId = getCentralId(returnMap);
            return findByID(deviceId);
        }
        catch (EmptyResultSetException e)
        {
            return null;
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

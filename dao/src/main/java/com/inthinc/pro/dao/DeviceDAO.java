package com.inthinc.pro.dao;

import java.util.List;
import java.util.Map;

import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.ForwardCommand;
import com.inthinc.pro.model.ForwardCommandStatus;

public interface DeviceDAO extends GenericDAO<Device, Integer>
{
    List<Device> getDevicesByAcctID(Integer accountID);
    
    List<ForwardCommand> getForwardCommands(Integer deviceID, ForwardCommandStatus status);
    
    Integer queueForwardCommand(Integer deviceID, ForwardCommand forwardCommand);

}

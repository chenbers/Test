package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.ForwardCommand;
import com.inthinc.pro.model.ForwardCommandStatus;
import com.inthinc.pro.model.SensitivityForwardCommandMapping;

public interface DeviceDAO extends GenericDAO<Device, Integer>
{
    List<Device> getDevicesByAcctID(Integer accountID);
    
    Device findByIMEI(String imei);
    
    List<ForwardCommand> getForwardCommands(Integer deviceID, ForwardCommandStatus status);
    
    Integer queueForwardCommand(Integer deviceID, ForwardCommand forwardCommand);
    
    List<SensitivityForwardCommandMapping> getSensitivityForwardCommandMapping();

}

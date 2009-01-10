package com.inthinc.pro.dao;

import java.util.List;
import java.util.Map;

import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.ForwardCommand;
import com.inthinc.pro.model.ForwardCommandStatus;
import com.inthinc.pro.model.SensitivityForwardCommandMapping;
import com.inthinc.pro.model.SensitivityType;

public interface DeviceDAO extends GenericDAO<Device, Integer>
{
    List<Device> getDevicesByAcctID(Integer accountID);
    
    Device findByIMEI(String imei);
    
    List<ForwardCommand> getForwardCommands(Integer deviceID, ForwardCommandStatus status);
    
    Integer queueForwardCommand(Integer deviceID, ForwardCommand forwardCommand);
    
    Map<SensitivityType, SensitivityForwardCommandMapping> getSensitivityForwardCommandMapping();

}

package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.configurator.DeviceSettingDefinition;
import com.inthinc.pro.model.configurator.VehicleSetting;

public interface ConfiguratorDAO extends GenericDAO<DeviceSettingDefinition, Integer>{
    
    List<DeviceSettingDefinition> getDeviceSettingDefinitions();
    List<VehicleSetting> getVehicleSettingsByGroupIDDeep(Integer groupID);
}

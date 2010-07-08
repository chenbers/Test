package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.configurator.DeviceSettingDefinition;

public interface ConfiguratorDAO extends GenericDAO<DeviceSettingDefinition, Integer>{
    
    List<DeviceSettingDefinition> getDeviceSettingDefinitions();
    
}

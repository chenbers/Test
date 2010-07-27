package com.inthinc.pro.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.model.configurator.DeviceSettingDefinition;
import com.inthinc.pro.model.configurator.VehicleSetting;
import com.inthinc.pro.model.configurator.VehicleSettingHistory;

public interface ConfiguratorDAO extends GenericDAO<DeviceSettingDefinition, Integer>{
    
    public List<DeviceSettingDefinition> getDeviceSettingDefinitions();
    public VehicleSetting getVehicleSettings(Integer vehicleID);
    public List<VehicleSetting> getVehicleSettingsByGroupIDDeep(Integer groupID);
    public void setVehicleSettings(Integer vehicleID, Map<Integer,String> setMap, Integer userID, String reason);
    public void updateVehicleSettings(Integer vehicleID, Map<Integer,String> setMap, Integer userID, String reason);
    public List<VehicleSettingHistory> getVehicleSettingsHistory(Integer vehicleID, Date startTime, Date endTime);
}

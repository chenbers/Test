package com.inthinc.pro.model.configurator;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.model.app.BaseAppEntity;

public class DeviceSettingDefinitions implements BaseAppEntity {

    private List<DeviceSettingDefinition> deviceSettings;
    
    private ConfiguratorDAO deviceSettingDAO;

    public void init(){
        
 //      deviceSettings =  deviceSettingDAO.getDeviceSettingDefinitions();
//       Collections.sort(deviceSettings);
       
    }
    public List<DeviceSettingDefinition> getDeviceSettings() {
        return deviceSettings;
    }

    public void setDeviceSettings(List<DeviceSettingDefinition> deviceSettings) {
        this.deviceSettings = deviceSettings;
    }
    
    public List<DeviceSettingDefinition> getOrderedDeviceSettings(){
        
        return deviceSettings;
    }
}

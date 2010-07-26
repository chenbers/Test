package com.inthinc.pro.model.configurator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.model.app.BaseAppEntity;

public class DeviceSettingDefinitions implements BaseAppEntity {

    private Map<Integer, DeviceSettingDefinition> allDeviceSettingDefinitions;
    private ConfiguratorDAO configuratorDAO;

    public void init(){

        List<DeviceSettingDefinition> deviceSettingDefinitions = configuratorDAO.getDeviceSettingDefinitions();  
        allDeviceSettingDefinitions = new HashMap<Integer,DeviceSettingDefinition>();
        for(DeviceSettingDefinition dsd : deviceSettingDefinitions){
            
            allDeviceSettingDefinitions.put(dsd.getSettingID(), dsd);
        }
    }

    public void setConfiguratorDAO(ConfiguratorDAO configuratorDAO) {
        this.configuratorDAO = configuratorDAO;
    }

    public Map<Integer,DeviceSettingDefinition> getAllDeviceSettingDefinitions() {
        return allDeviceSettingDefinitions;
    }
    
    public List<DeviceSettingDefinition> getDeviceSettingDefinitions(){
        
        return new ArrayList<DeviceSettingDefinition>(allDeviceSettingDefinitions.values());
    }
}

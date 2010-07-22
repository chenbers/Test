package com.inthinc.pro.model.configurator;

import java.util.List;

import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.model.app.BaseAppEntity;

public class DeviceSettingDefinitions implements BaseAppEntity {

    private List<DeviceSettingDefinition> allDeviceSettingDefinitions;
    private ConfiguratorDAO configuratorDAO;

    public void init(){

        allDeviceSettingDefinitions = configuratorDAO.getDeviceSettingDefinitions();        
    }

    public void setConfiguratorDAO(ConfiguratorDAO configuratorDAO) {
        this.configuratorDAO = configuratorDAO;
    }

    public List<DeviceSettingDefinition> getAllDeviceSettingDefinitions() {
        return allDeviceSettingDefinitions;
    }
}

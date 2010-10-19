package com.inthinc.pro.configurator.model;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.configurator.ui.ConfigurationSelectionBean;

public class ComparedConfigurations {

    private DeviceSettingDefinitionsByProductType deviceSettingDefinitionsByProductType;
    private ConfigurationSelectionBean configurationSelectionBean;
    private List<DeviceSettingDefinitionBean> differentDeviceSettings;
    private ConfigurationSet compareConfigurationSet;

    
    public ComparedConfigurations() {
        super();
        differentDeviceSettings = new ArrayList<DeviceSettingDefinitionBean>();
    }

    public void init(ConfigurationSet compareConfigurationSet) {

        this.compareConfigurationSet = compareConfigurationSet;
        differentDeviceSettings = deviceSettingDefinitionsByProductType.deriveReducedSettings(compareConfigurationSet.getSettingIDsWithMoreThanOneValue(),
                configurationSelectionBean.getProductType());
    }
    
    public List<DeviceSettingDefinitionBean> getDifferentDeviceSettings() {
        return differentDeviceSettings;
    }

    public ConfigurationSet getCompareConfigurationSet() {
        return compareConfigurationSet;
    }

    public void setDeviceSettingDefinitionsByProductType(DeviceSettingDefinitionsByProductType deviceSettingDefinitionsByProductType) {
        this.deviceSettingDefinitionsByProductType = deviceSettingDefinitionsByProductType;
    }

    public void setConfigurationSelectionBean(ConfigurationSelectionBean configurationSelectionBean) {
        this.configurationSelectionBean = configurationSelectionBean;
    }

    
    public Boolean getPopulated(){
        return compareConfigurationSet != null && compareConfigurationSet.getHasConfigurations();
    }
    public void clear(){
        
        compareConfigurationSet = null;
        differentDeviceSettings = null;
    }
}

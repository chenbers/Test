package com.inthinc.pro.configurator.model;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.model.configurator.ProductType;


public class ComparedConfigurations {

    private List<DeviceSettingDefinitionBean> differentDeviceSettings;
    private ConfigurationSet compareConfigurationSet;

    
    public ComparedConfigurations() {
        super();
        differentDeviceSettings = new ArrayList<DeviceSettingDefinitionBean>();
    }

    public void getDifferences(ConfigurationSet compareConfigurationSet, 
                     DeviceSettingDefinitionsByProductType deviceSettingDefinitionsByProductType,
                     ProductType productType) {

        this.compareConfigurationSet = compareConfigurationSet;
        differentDeviceSettings = deviceSettingDefinitionsByProductType.deriveReducedSettings(compareConfigurationSet.getSettingIDsWithMoreThanOneValue(),
                productType);
    }
    
    public List<DeviceSettingDefinitionBean> getDifferentDeviceSettings() {
        return differentDeviceSettings;
    }

    public ConfigurationSet getCompareConfigurationSet() {
        return compareConfigurationSet;
    }
    
    public Boolean getPopulated(){
        return compareConfigurationSet != null && compareConfigurationSet.getHasConfigurations();
    }
    public void clear(){
        
        compareConfigurationSet = null;
        differentDeviceSettings = null;
        differentDeviceSettings = new ArrayList<DeviceSettingDefinitionBean>();
    }
}

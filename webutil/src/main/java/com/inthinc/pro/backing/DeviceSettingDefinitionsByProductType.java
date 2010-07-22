package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.model.configurator.DeviceSettingDefinition;
import com.inthinc.pro.model.configurator.DeviceSettingDefinitions;
import com.inthinc.pro.model.configurator.DeviceSettingDefinition.ProductType;

public class DeviceSettingDefinitionsByProductType {

    private DeviceSettingDefinitions deviceSettingDefinitions;
    private Map<ProductType, List<DeviceSettingDefinition>> deviceSettings;
    private Map<ProductType, List<DeviceSettingDefinition>> ignoredSettings;
    
    public void init(){
        
        initProductSettings();
        distributeSettings();
    }
    private void initProductSettings(){
        
       deviceSettings = new HashMap<ProductType, List<DeviceSettingDefinition>>();
       ignoredSettings = new HashMap<ProductType, List<DeviceSettingDefinition>>();
       
       for (ProductType productType : ProductType.getSet()){
            
            deviceSettings.put(productType,new ArrayList<DeviceSettingDefinition>()); 
            ignoredSettings.put(productType,new ArrayList<DeviceSettingDefinition>()); 
       }
    }
    private void distributeSettings(){
        
        for(DeviceSettingDefinition dsd:deviceSettingDefinitions.getAllDeviceSettingDefinitions()){
            
            distributeSetting(dsd);
        }
    }
    private void distributeSetting(DeviceSettingDefinition dsd){

        for (ProductType productType :ProductType.getSet()){
            
            if (productType.maskBitSet(dsd.getProductMask())){
                
                deviceSettings.get(productType).add(dsd);
            
                if(!dsd.isInclude()){
                    
                    ignoredSettings.get(productType).add(dsd);
                }
            }
        }
    }

    public List<DeviceSettingDefinition> getDeviceSettings(Object key) {
        
        return deviceSettings.get(key);
    }

    public void setDeviceSettingDefinitions(DeviceSettingDefinitions deviceSettingDefinitions) {
        this.deviceSettingDefinitions = deviceSettingDefinitions;
    }
    
    public List<DeviceSettingDefinition> getDeviceSettingDefinitions(String key){
        
        return deviceSettings.get(key);
    }
    public Map<ProductType, List<DeviceSettingDefinition>> getIgnoredSettings() {
        return ignoredSettings;
    }
}

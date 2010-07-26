package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
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
        
        for(DeviceSettingDefinition dsd:deviceSettingDefinitions.getDeviceSettingDefinitions()){
            
            distributeSetting(dsd);
        }
    }
    private void distributeSetting(DeviceSettingDefinition dsd){

        for (ProductType productType :ProductType.getSet()){
            
            if (productType.maskBitSet(dsd.getProductMask())){
                
                if(dsd.isInclude()){
                    
                    deviceSettings.get(productType).add(dsd);
                }
                else{
                    
                    ignoredSettings.get(productType).add(dsd);
                }
            }
        }
    }
    public List<DeviceSettingDefinition> deriveReducedSettings(List<Integer> settingIDsWithMoreThanOneValue,ProductType productType){
        
        List<DeviceSettingDefinition> reducedSettings = new ArrayList<DeviceSettingDefinition>();
        
        if (settingIDsWithMoreThanOneValue.isEmpty()) return reducedSettings;
        
        Collections.sort(settingIDsWithMoreThanOneValue);
        for (DeviceSettingDefinition dsd:deviceSettings.get(productType)){
            
            if(settingIDsWithMoreThanOneValue.contains(dsd.getSettingID())){
            
                reducedSettings.add(dsd);
            }
        }
        return reducedSettings;
    }
    public List<DeviceSettingDefinition> getDeviceSettings(ProductType key) {
        
        return deviceSettings.get(key);
    }

    public void setDeviceSettingDefinitions(DeviceSettingDefinitions deviceSettingDefinitions) {
        this.deviceSettingDefinitions = deviceSettingDefinitions;
    }
    
    public List<DeviceSettingDefinition> getIgnoredSettings(ProductType key) {
        return ignoredSettings.get(key);
    }
    public boolean validValue(Integer settingID, String value){
        
        return deviceSettingDefinitions.getAllDeviceSettingDefinitions().get(settingID).validateValue(value);
    }   
       
}

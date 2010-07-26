package com.inthinc.pro.backing.model;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.model.configurator.VehicleSetting;

public class ConfigurationExtractor {

    public static ConfigurationSet getConfigurations(List<VehicleSetting> vehicleSettings){
        
        
        ConfigurationSet configurationSet = new ConfigurationSet();
        
       if(vehicleSettings == null) return configurationSet;
       
       configurationSet.setConfigurations(createConfigurations(vehicleSettings));
        
        return configurationSet;
    }
    
    private static List<SettingOptions> createConfigurations(List<VehicleSetting> vehicleSettings){
        
        List<SettingOptions> configurations = new ArrayList<SettingOptions>();
        
        for(VehicleSetting vehicleSetting : vehicleSettings){
            
            if (vehicleSetting.getActual()==null) continue;
            
            SettingOptions matchingConfiguration = getMatchingConfiguration(vehicleSetting,configurations);
            
            if(matchingConfiguration != null){
                
                matchingConfiguration.addVehicleID(vehicleSetting.getVehicleID());
            }
            else{
                
                configurations.add(createNewConfiguration(vehicleSetting));
            }
        }
        return configurations;
    }
    private static SettingOptions getMatchingConfiguration(VehicleSetting vehicleSetting,List<SettingOptions> configurations){
        
        for(SettingOptions configuration : configurations){
            
            if(vehicleSetting.getActual().equals(configuration.getValues())){
                
                return configuration;
            }
        }
        return null;
    }
    private static SettingOptions createNewConfiguration(VehicleSetting vehicleSetting){
        
        SettingOptions settingOptions = new SettingOptions();
        settingOptions.addVehicleID(vehicleSetting.getVehicleID());
        settingOptions.setValues(vehicleSetting.getActual());

        return settingOptions;
    }
}

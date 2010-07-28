package com.inthinc.pro.backing.configurator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            
            if ((vehicleSetting.getActual()==null) && (vehicleSetting.getDesired()==null)) continue;
            
            Map<Integer, String> combinedSettings = combineSettings(vehicleSetting);
            
            SettingOptions matchingConfiguration = getMatchingConfiguration(combinedSettings,configurations);
            
            if(matchingConfiguration != null){
                
                matchingConfiguration.addVehicleID(vehicleSetting.getVehicleID());
            }
            else{
                
                configurations.add(createNewConfiguration(vehicleSetting.getVehicleID(),combinedSettings));
            }
        }
        return configurations;
    }
    private static Map<Integer,String> combineSettings(VehicleSetting vehicleSetting){
    	
    	Map<Integer,String> combinedSettings = new HashMap<Integer,String>();
    	combinedSettings.putAll(vehicleSetting.getActual());
    	combinedSettings.putAll(vehicleSetting.getDesired());
    	
    	return combinedSettings;
    }
    private static SettingOptions getMatchingConfiguration(Map<Integer, String> combinedSettings,List<SettingOptions> configurations){
        
        for(SettingOptions configuration : configurations){
            
            if(combinedSettings.equals(configuration.getValues())){
                
                return configuration;
            }
        }
        return null;
    }
    private static SettingOptions createNewConfiguration(Integer vehicleID,Map<Integer, String> combinedSettings){
        
        SettingOptions settingOptions = new SettingOptions();
        settingOptions.addVehicleID(vehicleID);
        settingOptions.setValues(combinedSettings);

        return settingOptions;
    }
}

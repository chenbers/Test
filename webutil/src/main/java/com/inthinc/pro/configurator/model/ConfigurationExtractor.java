package com.inthinc.pro.configurator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.configurator.VehicleSetting;

public class ConfigurationExtractor {

	private static final Logger logger = Logger.getLogger(ConfigurationExtractor.class);

    public static ConfigurationSet getConfigurations(List<VehicleSetting> vehicleSettings){

    	logger.info("ConfigurationExtractor: start");
        return new ConfigurationSet(createConfigurations(vehicleSettings));
    }
    
    private static List<Configuration> createConfigurations(List<VehicleSetting> vehicleSettings){
 
    	logger.info("ConfigurationExtractor: createConfigurations");
        if((vehicleSettings == null) || vehicleSettings.isEmpty()) return Collections.emptyList();
        
        List<Configuration> configurations = new ArrayList<Configuration>();
        
        for(VehicleSetting vehicleSetting : vehicleSettings){
            
        	logger.info("ConfigurationExtractor: vehicleSetting, vehicleID="+vehicleSetting.getVehicleID());
            configurations = addToMatchingConfiguration(vehicleSetting,configurations);
        }
        return configurations;
     }
    
    private static List<Configuration> addToMatchingConfiguration(VehicleSetting vehicleSetting,List<Configuration> configurations){
        
        for(Configuration configuration : configurations){
            
            if(vehicleSetting.getCombinedSettings().equals(configuration.getValues())){
                
            	logger.info("ConfigurationExtractor: addToMatchingConfiguration - matching found");
                configuration.addVehicleID(vehicleSetting.getVehicleID());
                return configurations;
            }
        }
        configurations.add(createNewConfiguration(vehicleSetting));
        return configurations;
    }
    private static Configuration createNewConfiguration(VehicleSetting vehicleSetting){
        
       	logger.info("ConfigurationExtractor: addToMatchingConfiguration - adding new");
       	
        Configuration configuration = new Configuration();
        configuration.addVehicleID(vehicleSetting.getVehicleID());
        configuration.setValues(vehicleSetting.getCombinedSettings());

        return configuration;
    }
}

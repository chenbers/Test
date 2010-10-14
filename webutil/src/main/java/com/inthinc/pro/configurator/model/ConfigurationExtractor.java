package com.inthinc.pro.configurator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.configurator.VehicleSetting;

public class ConfigurationExtractor {

	private static final Logger logger = Logger.getLogger(ConfigurationExtractor.class);

    public static ConfigurationSet getConfigurations(List<VehicleSetting> vehicleSettings,  List<Integer> productSettings){

        return new ConfigurationSet(createConfigurations(vehicleSettings, productSettings));
    }
    /*
     * Destroys the vehicleSettings list as it iterates through to save space
     */
    private static List<Configuration> createConfigurations(List<VehicleSetting> vehicleSettings, List<Integer> productSettings){
 
    	logger.debug("ConfigurationExtractor: createConfigurations");
        if((vehicleSettings == null) || vehicleSettings.isEmpty()) return Collections.emptyList();
        
        List<Configuration> configurations = new ArrayList<Configuration>();
        
        Iterator<VehicleSetting> vehicleSettingIterator = vehicleSettings.iterator();
        while(vehicleSettingIterator.hasNext()){
            
            VehicleSetting vehicleSetting = vehicleSettingIterator.next(); 
            logger.debug("ConfigurationExtractor: vehicleSetting, vehicleID="+vehicleSetting.getVehicleID());
            addToMatchingConfiguration(vehicleSetting,productSettings,configurations);
            
            vehicleSettingIterator.remove();
        }
        return configurations;
     }
    
    private static void addToMatchingConfiguration(VehicleSetting vehicleSetting, List<Integer> productSettings,List<Configuration> configurations){
        
  	  	EditableMap<Integer, String> editedDesiredValues = new EditableMap<Integer,String>(productSettings, vehicleSetting.getCombinedSettings());
        for(Configuration configuration : configurations){
        	
            if(editedDesiredValues.equals(configuration.getEditedDesiredValues())){
                
            	logger.debug("ConfigurationExtractor: addToMatchingConfiguration - matching found");
                configuration.addVehicleID(vehicleSetting.getVehicleID());
            }
        }
        configurations.add(createNewConfiguration(vehicleSetting.getVehicleID(),configurations.size()+1, editedDesiredValues));
    }
    private static Configuration createNewConfiguration(Integer vehicleID, Integer configurationID, EditableMap<Integer, String> editedDesiredValues){
        
       	logger.debug("ConfigurationExtractor: addToMatchingConfiguration - adding new");
       	
        Configuration configuration = new Configuration(configurationID);
        configuration.addVehicleID(vehicleID);
        configuration.setEditedDesiredValues(editedDesiredValues);

        return configuration;
    }
//    private static EditableMap<Integer, String> initializeEditedDesiredSettings(VehicleSetting vehicleSetting,List<DeviceSettingDefinitionBean> productSettings){
//
//    	Map<Integer, String> copyMap = new HashMap<Integer,String>();
//    	Map<Integer, String> mapToCopy = vehicleSetting.getCombinedSettings();
//    	
//    	for(DeviceSettingDefinitionBean dsdb: productSettings){
//    		
//    		if (mapToCopy.get(dsdb.getSettingID()) == null){
//    			
//    			copyMap.put(dsdb.getSettingID(),null);
//    		}
//    		else {
//    			copyMap.put(dsdb.getSettingID() ,mapToCopy.get(dsdb.getSettingID()));
//    		}
//    	}
//    	return new EditableMap<Integer,String>(copyMap);
//    }

}

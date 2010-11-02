package com.inthinc.pro.configurator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.VehicleSetting;

public class VehicleSettings {

    private Map<Integer, VehicleSetting> vehicleSettingsMap;
    private List<VehicleSetting> vehicleSettings;
    private List<Integer> vehicleIDs;
    
    public void filterSettings(List<VehicleSetting> allVehicleSettings, ProductType productType){
        
        vehicleSettingsMap = new HashMap<Integer,VehicleSetting>();
    	vehicleSettings = new ArrayList<VehicleSetting>();
    	vehicleIDs = new ArrayList<Integer>();
    	
        for(VehicleSetting vehicleSetting:allVehicleSettings){
            
        	if (vehicleSetting.getProductType().equals(productType)){
        		
        	    vehicleSettingsMap.put(vehicleSetting.getVehicleID(),vehicleSetting);
	            vehicleSettings.add(vehicleSetting);
	            vehicleIDs.add(vehicleSetting.getVehicleID());
        	}
        }
    }
    public Map<Integer, VehicleSetting> getVehicleSettingsMap() {
        return vehicleSettingsMap;
    }
    public List<VehicleSetting> getVehicleSettings() {
        
    	if (vehicleSettings == null) return Collections.emptyList();
        return vehicleSettings;
    }
    
    public List<Integer> getVehicleIDs(){
    	
    	if (vehicleIDs == null) return Collections.emptyList();
    	return vehicleIDs;
    }
}

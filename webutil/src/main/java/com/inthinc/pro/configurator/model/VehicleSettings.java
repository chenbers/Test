package com.inthinc.pro.configurator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.VehicleSetting;

public class VehicleSettings {

    private List<VehicleSetting> vehicleSettings;
    private List<Integer> vehicleIDs;
    
    public void filterSettings(List<VehicleSetting> allVehicleSettings, ProductType productType){
        
    	vehicleSettings = new ArrayList<VehicleSetting>();
    	vehicleIDs = new ArrayList<Integer>();
    	
        for(VehicleSetting vehicleSetting:allVehicleSettings){
            
        	if (vehicleSetting.getProductType().equals(productType)){
        		
	            vehicleSettings.add(vehicleSetting);
	            vehicleIDs.add(vehicleSetting.getVehicleID());
        	}
        }
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

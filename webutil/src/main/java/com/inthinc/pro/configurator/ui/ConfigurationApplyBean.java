package com.inthinc.pro.configurator.ui;

import java.util.Map;

import com.inthinc.pro.configurator.model.Configuration;

public class ConfigurationApplyBean extends VehicleFilterBean{
	
	private Integer applyToConfigurationID;
	private Integer selectedVehicleID;
    
	public Integer getSelectedVehicleID() {
		return selectedVehicleID;
	}
	public void setSelectedVehicleID(Integer selectedVehicleID) {
		this.selectedVehicleID = selectedVehicleID;
	}
	public Integer getApplyToConfigurationID() {
		return applyToConfigurationID;
	}
	public void setApplyToConfigurationID(Integer applyToConfigurationID) {
		this.applyToConfigurationID = applyToConfigurationID;
	}
	public Object applySettingsToTargetVehicles(Configuration selectedConfiguration, String reason){
    	
       	for(Integer vehicleID : getVehicleIDsForMakeModelYear()){
    		
			vehicleSettingsDAO.updateVehicleSettings(vehicleID, selectedConfiguration.getLatestDesiredValues(), 
													 getBaseBean().getProUser().getUser().getUserID(), 
													 reason);
    	}
       	return null;
    }
    public Object updateVehicle(Map<Integer, String> differenceMap, String reason){
    	
       return  updateVehicle(selectedVehicleID,differenceMap, reason);
//		vehicleSettingsDAO.updateVehicleSettings(selectedVehicleID, selectedConfiguration.getLatestDesiredValues(), 
//				 getBaseBean().getProUser().getUser().getUserID(), 
//				 selectedConfiguration.getReason());

//		return null;
    }
    public Object updateVehicle(Integer vehicleID, Map<Integer, String> differenceMap, String reason){
    	
		vehicleSettingsDAO.updateVehicleSettings(vehicleID, differenceMap, 
				 getBaseBean().getProUser().getUser().getUserID(), 
				 reason);

		return null;
    }
}

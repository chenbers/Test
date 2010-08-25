package com.inthinc.pro.configurator.model;

import java.util.List;
import java.util.Map;

import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.configurator.VehicleSetting;

public class VehicleSettingsDAO {
    
    private ConfiguratorDAO configuratorDAO;
    private VehicleDAO vehicleDAO;
    
    public void setVehicleDAO(VehicleDAO vehicleDAO) {
		this.vehicleDAO = vehicleDAO;
	}

	public void setConfiguratorDAO(ConfiguratorDAO configuratorDAO) {
        this.configuratorDAO = configuratorDAO;
    }

    public List<VehicleSetting> getVehicleSettings(Integer selectedGroup){
        
        return configuratorDAO.getVehicleSettingsByGroupIDDeep(selectedGroup);
    }

    public void setVehicleSettings(Integer vehicleID,Map<Integer, String> vehicleSettings, Integer userID, String reason){
    	
       	if(reason == null|| reason.isEmpty() || vehicleSettings.isEmpty() || vehicleID==null) return;
        
       	configuratorDAO.setVehicleSettings(vehicleID, vehicleSettings, userID, reason);
    }
    public void updateVehicleSettings(Integer vehicleID,Map<Integer, String> vehicleSettings, Integer userID, String reason){
    	
    	if(reason == null|| reason.isEmpty() || vehicleSettings.isEmpty() || vehicleID==null) return;
    	
    	configuratorDAO.updateVehicleSettings(vehicleID, vehicleSettings, userID, reason);
    }
    
    public VehicleSetting getVehicleSetting(Integer vehicleID){
    	
    	return configuratorDAO.getVehicleSettings(vehicleID);
    }
    
    public List<Vehicle> getVehicles(Integer groupID){
    	
    	return vehicleDAO.getVehiclesInGroupHierarchy(groupID);
    }
}

package com.inthinc.pro.configurator.model;

import java.util.List;
import java.util.Map;

import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.model.configurator.VehicleSetting;

public class VehicleSettings {
    
    private ConfiguratorDAO configuratorDAO;
    
    public void setConfiguratorDAO(ConfiguratorDAO configuratorDAO) {
        this.configuratorDAO = configuratorDAO;
    }

    public List<VehicleSetting> getVehicleSettings(Integer selectedGroup){
        
        return configuratorDAO.getVehicleSettingsByGroupIDDeep(selectedGroup);
    }

    public void setVehicleSettings(Integer vehicleID,Map<Integer, String> vehicleSettings, Integer userID, String reason){
    	configuratorDAO.setVehicleSettings(vehicleID, vehicleSettings, userID, reason);
    }
    public void updateVehicleSettings(Integer vehicleID,Map<Integer, String> vehicleSettings, Integer userID, String reason){
    	configuratorDAO.updateVehicleSettings(vehicleID, vehicleSettings, userID, reason);
    }
    
    public VehicleSetting getVehicleSetting(Integer vehicleID){
    	
    	return configuratorDAO.getVehicleSettings(vehicleID);
    }
}

package com.inthinc.pro.backing.model;

import java.util.Map;

import com.inthinc.pro.backing.VehicleSettingAdapter;
import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.model.configurator.VehicleSetting;

public abstract class VehicleSettingManager {

    protected ConfiguratorDAO configuratorDAO;
    protected VehicleSetting  vehicleSetting;
    protected Map<String,Object> properties;
        
    protected VehicleSettingManager(ConfiguratorDAO configuratorDAO, VehicleSetting vehicleSetting) {

        this.configuratorDAO = configuratorDAO;
        this.vehicleSetting = vehicleSetting;
    }

    public abstract void init();

    public abstract VehicleSettingAdapter associateSettings(Integer vehicleID);

    public abstract Map<Integer, String> evaluateSettings(Integer vehicleID, VehicleSettingAdapter vehicleSettingAdapter);

    public abstract Map<Integer, String> evaluateChangedSettings(Boolean batchEdit, Map<String, Boolean> updateField, Integer vehicleID, VehicleSettingAdapter vehicleSettingAdapter);

    public abstract void setVehicleSettings(Integer vehicleID, VehicleSettingAdapter vehicleSettingAdapter, Integer userID, String reason);

    public abstract void updateVehicleSettings(boolean batchEdit, Map<String, Boolean> updateField, Integer vehicleID, VehicleSettingAdapter vehicleSettingAdapter, Integer userID, String reason);

    public void setConfiguratorDAO(ConfiguratorDAO configuratorDAO) {
        this.configuratorDAO = configuratorDAO;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public Integer convertString(String integerValue) {
            
            if (integerValue == null) return 0;
            
            try {
                
                return Integer.parseInt(integerValue);
            }
            catch(NumberFormatException nfe){
                
                return 0;
            }
       }

}

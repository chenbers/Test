package com.inthinc.pro.backing.model;

import java.util.Map;

import com.inthinc.pro.backing.VehicleSettingAdapter;
import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.model.configurator.VehicleSetting;
//Has no special settings
public class UnknownSettingManager extends VehicleSettingManager {

    protected UnknownSettingManager(ConfiguratorDAO configuratorDAO, VehicleSetting vehicleSetting) {
        super(configuratorDAO, vehicleSetting);

    }

    @Override
    public VehicleSettingAdapter associateSettings(Integer vehicleID) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<Integer, String> evaluateChangedSettings(Boolean batchEdit, Map<String, Boolean> updateField, Integer vehicleID, VehicleSettingAdapter vehicleSettingAdapter) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<Integer, String> evaluateSettings(Integer vehicleID, VehicleSettingAdapter vehicleSettingAdapter) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void init() {
    // TODO Auto-generated method stub

    }

    @Override
    public void setVehicleSettings(Integer vehicleID, VehicleSettingAdapter vehicleSettingAdapter, Integer userID, String reason) {
    // TODO Auto-generated method stub

    }

    @Override
    public void updateVehicleSettings(boolean batchEdit, Map<String, Boolean> updateField, Integer vehicleID, VehicleSettingAdapter vehicleSettingAdapter, Integer userID, String reason) {
    // TODO Auto-generated method stub

    }

}

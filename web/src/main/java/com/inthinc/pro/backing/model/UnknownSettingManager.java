package com.inthinc.pro.backing.model;

import java.util.Map;

import com.inthinc.pro.backing.EditableVehicleSettings;
import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.model.configurator.VehicleSetting;
//Has no special settings
public class UnknownSettingManager extends VehicleSettingManager {

	protected UnknownSettingManager(ConfiguratorDAO configuratorDAO, VehicleSetting vehicleSetting) {
        super(configuratorDAO, vehicleSetting);

    }

    @Override
	protected EditableVehicleSettings createDefaultValues(Integer vehicleID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected EditableVehicleSettings createFromExistingValues(VehicleSetting vs) {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public Map<Integer, String> evaluateChangedSettings(Boolean batchEdit, Map<String, Boolean> updateField, Integer vehicleID, EditableVehicleSettings editableVehicleSettings) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<Integer, String> evaluateSettings(Integer vehicleID, EditableVehicleSettings editableVehicleSettings) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void init() {
    // TODO Auto-generated method stub

    }

    @Override
    public void setVehicleSettings(Integer vehicleID, EditableVehicleSettings editableVehicleSettings, Integer userID, String reason) {
    // TODO Auto-generated method stub

    }

    @Override
    public void updateVehicleSettings(boolean batchEdit, Map<String, Boolean> updateField, Integer vehicleID, EditableVehicleSettings editableVehicleSettings, Integer userID, String reason) {
    // TODO Auto-generated method stub

    }

}

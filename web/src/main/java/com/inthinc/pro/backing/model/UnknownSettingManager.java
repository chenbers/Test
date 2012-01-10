package com.inthinc.pro.backing.model;

import java.util.Map;

import com.inthinc.pro.backing.EditableVehicleSettings;
import com.inthinc.pro.backing.UnknownEditableVehicleSettings;
import com.inthinc.pro.model.configurator.VehicleSetting;

public class UnknownSettingManager extends VehicleSettingManager {



    @Override
    public EditableVehicleSettings associateSettings(Integer vehicleID) {
        return new UnknownEditableVehicleSettings();
    }


    @Override
    protected Map<Integer, String> getVehicleSettingsForSliderSettingIDs(VehicleSetting vehicleSetting, VehicleSensitivitySlider slider) {
        return null;
    }

 
    @Override
	public EditableVehicleSettings createDefaultValues(Integer vehicleID) {
		return null;
	}

	@Override
	protected EditableVehicleSettings createFromExistingValues(Integer vehicleID,VehicleSetting vs) {
		return null;
	}

    @Override
    public Map<Integer, String> evaluateSettings(Integer vehicleID, EditableVehicleSettings editableVehicleSettings,
				 Map<String, Boolean> updateField) {
        return null;
    }


    @Override
    public void init() {

    }

    @Override
    public void setVehicleSettings(Integer vehicleID, EditableVehicleSettings editableVehicleSettings, Integer userID, String reason,
				 Map<String, Boolean> updateField) {
        //No settings - so can't create the record 
    }

    @Override
    public void updateVehicleSettings(Integer vehicleID, EditableVehicleSettings editableVehicleSettings, Integer userID, String reason,
				 Map<String, Boolean> updateField) {
        //Can't edit unknown settings
    }
    
}

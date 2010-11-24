package com.inthinc.pro.backing.model;

import java.util.Map;

import com.inthinc.pro.backing.EditableVehicleSettings;
import com.inthinc.pro.backing.UnknownEditableVehicleSettings;
import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.model.configurator.Slider;
import com.inthinc.pro.model.configurator.VehicleSetting;
//Has no special settings
public class UnknownSettingManager extends VehicleSettingManager {

	protected UnknownSettingManager(ConfiguratorDAO configuratorDAO, VehicleSetting vehicleSetting) {
	    
        super();
    }


    @Override
    public EditableVehicleSettings associateSettings(Integer vehicleID) {
        return new UnknownEditableVehicleSettings(vehicleID);
    }


    @Override
    protected Map<Integer, String> getVehiclSettingsForSliderSettingIDs(VehicleSetting vehicleSetting, Slider slider) {
        return super.getVehiclSettingsForSliderSettingIDs(vehicleSetting, slider);
    }

 
    @Override
	protected EditableVehicleSettings createDefaultValues(Integer vehicleID) {
		return null;
	}

	@Override
	protected EditableVehicleSettings createFromExistingValues(Integer vehicleID,VehicleSetting vs) {
		return null;
	}

    @Override
    public Map<Integer, String> evaluateChangedSettings(Integer vehicleID, EditableVehicleSettings editableVehicleSettings) {

         return null;
    }

    @Override
    public Map<Integer, String> evaluateSettings(Integer vehicleID, EditableVehicleSettings editableVehicleSettings) {
        return null;
    }


    @Override
    public void init() {

    }

    @Override
    public void setVehicleSettings(Integer vehicleID, EditableVehicleSettings editableVehicleSettings, Integer userID, String reason) {
        //No settings - so can't create the record 
    }

    @Override
    public void updateVehicleSettings(Integer vehicleID, EditableVehicleSettings editableVehicleSettings, Integer userID, String reason) {
        //Can't edit unknown settings
    }
    
}

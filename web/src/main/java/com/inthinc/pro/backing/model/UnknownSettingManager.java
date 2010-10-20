package com.inthinc.pro.backing.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.backing.EditableVehicleSettings;
import com.inthinc.pro.backing.UnknownEditableVehicleSettings;
import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.Slider;
import com.inthinc.pro.model.configurator.SliderType;
import com.inthinc.pro.model.configurator.VehicleSetting;
//Has no special settings
public class UnknownSettingManager extends VehicleSettingManager {

	protected UnknownSettingManager(ConfiguratorDAO configuratorDAO, VehicleSetting vehicleSetting) {
	    
        super();
//        this.configuratorDAO = configuratorDAO;
//        this.vehicleSetting = new VehicleSetting();
//        vehicleSetting.setProductType(ProductType.UNKNOWN);
//
//        vehicleSensitivitySliders = new NullSensitivitySliders(vehicleSetting.getProductType(), 0, 1000000);
//        
//        adjustedSettingCounts = new HashMap<SliderType,Integer>();

    }

    @Override
    protected Map<SliderType, Integer> adjustedSettingCountsToAllowForCustomValues(Integer hardVertical, Integer hardTurn, Integer hardAcceleration, Integer hardBrake) {
        // TODO Auto-generated method stub
//        return super.adjustedSettingCountsToAllowForCustomValues(hardVertical, hardTurn, hardAcceleration, hardBrake);
        return adjustedSettingCounts;
    }

    @Override
    public EditableVehicleSettings associateSettings(Integer vehicleID) {
        // TODO Auto-generated method stub
//        return super.associateSettings(vehicleID);
        return new UnknownEditableVehicleSettings(vehicleID);
    }

    @Override
    protected Integer extractSliderValue(SliderType sliderType, Map<Integer, String> settings) {
        // TODO Auto-generated method stub
//        return super.extractSliderValue(sliderType, settings);
        return 0;
    }

    @Override
    public Map<SliderType, Integer> getAdjustedSettingCounts() {
        // TODO Auto-generated method stub
//        return super.getAdjustedSettingCounts();
        return null;
    }

    @Override
    public Map<SliderType, Integer> getDefaultSettings() {
        // TODO Auto-generated method stub
//        return super.getDefaultSettings();
        return null;
    }

    @Override
    public List<SliderType> getSensitivities() {
        // TODO Auto-generated method stub
//        return super.getSensitivities();
        return null;

    }

    @Override
    protected Map<Integer, String> getSensitivityValue(SliderType sliderType, Integer sliderValue) {
        // TODO Auto-generated method stub
//        return super.getSensitivityValue(sliderType, sliderValue);
        return null;

    }

    @Override
    public Map<SliderType, Integer> getSettingCounts() {
        // TODO Auto-generated method stub
        return super.getSettingCounts();
    }

    @Override
    protected Map<Integer, String> getVehiclSettingsForSliderSettingIDs(VehicleSetting vehicleSetting, Slider slider) {
        // TODO Auto-generated method stub
        return super.getVehiclSettingsForSliderSettingIDs(vehicleSetting, slider);
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
        //No settings - so can't create the record 
    }

    @Override
    public void updateVehicleSettings(boolean batchEdit, Map<String, Boolean> updateField, Integer vehicleID, EditableVehicleSettings editableVehicleSettings, Integer userID, String reason) {
        //Can't edit unknown settings
    }

}

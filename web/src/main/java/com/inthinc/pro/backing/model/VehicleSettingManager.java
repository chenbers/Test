package com.inthinc.pro.backing.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.inthinc.pro.backing.EditableVehicleSettings;
import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.model.configurator.SettingType;
import com.inthinc.pro.model.configurator.Slider;
import com.inthinc.pro.model.configurator.SliderType;
import com.inthinc.pro.model.configurator.VehicleSetting;

public abstract class VehicleSettingManager {

    protected static final Integer CUSTOM_SLIDER_VALUE = 99;
	protected ConfiguratorDAO configuratorDAO;
    protected VehicleSetting  vehicleSetting;
    protected VehicleSensitivitySliders vehicleSensitivitySliders;
    protected Map<SliderType,Integer> adjustedSettingCounts;
        
    protected VehicleSettingManager(ConfiguratorDAO configuratorDAO, VehicleSetting vehicleSetting) {

        this.configuratorDAO = configuratorDAO;
        this.vehicleSetting = vehicleSetting;
        vehicleSensitivitySliders = new VehicleSensitivitySliders(vehicleSetting.getProductType(), 0, 1000000);
        
        adjustedSettingCounts = new HashMap<SliderType,Integer>();
    }
    
    public VehicleSettingManager() {
        super();
        // TODO Auto-generated constructor stub
    }

    public abstract void init();
    public abstract Map<Integer, String> evaluateSettings(Integer vehicleID, EditableVehicleSettings editableVehicleSettings);
    public abstract Map<Integer, String> evaluateChangedSettings(Boolean batchEdit, Map<String, Boolean> updateField, Integer vehicleID, EditableVehicleSettings editableVehicleSettings);
    public abstract void setVehicleSettings(Integer vehicleID, EditableVehicleSettings editableVehicleSettings, Integer userID, String reason);
    public abstract void updateVehicleSettings(boolean batchEdit, Map<String, Boolean> updateField, Integer vehicleID, EditableVehicleSettings editableVehicleSettings, Integer userID, String reason);


    public void setConfiguratorDAO(ConfiguratorDAO configuratorDAO) {
        this.configuratorDAO = configuratorDAO;
    }
    

	public Map<SliderType, Integer> getDefaultSettings() {
		return vehicleSensitivitySliders.getDefaultSettings();
	}

	public List<SliderType> getSensitivities() {
		return SliderType.getSensitivities();
	}

	public Map<SliderType, Integer> getSettingCounts() {
		return vehicleSensitivitySliders.getSettingCounts();
	}

	public EditableVehicleSettings associateSettings(Integer vehicleID) {
	    
	    if (vehicleSetting == null){
	        
	        return createDefaultValues(vehicleID); 
	    }
	    else {
	        return createFromExistingValues(vehicleSetting);
	    }
	}
	
    protected abstract EditableVehicleSettings createDefaultValues(Integer vehicleID);
    protected abstract EditableVehicleSettings createFromExistingValues(VehicleSetting vs);
    
    protected Map<SliderType,Integer> adjustedSettingCountsToAllowForCustomValues(Integer hardVertical, Integer hardTurn, Integer hardAcceleration, Integer hardBrake) {
    	   
	   	Map<SliderType,Integer> settingCount = new HashMap<SliderType,Integer>();
        
        settingCount.put(SliderType.HARD_ACCEL_SLIDER, vehicleSensitivitySliders.getSettingsCount(SliderType.HARD_ACCEL_SLIDER)+(hardAcceleration==CUSTOM_SLIDER_VALUE?1:0));
        settingCount.put(SliderType.HARD_BRAKE_SLIDER, vehicleSensitivitySliders.getSettingsCount(SliderType.HARD_BRAKE_SLIDER)+(hardBrake==CUSTOM_SLIDER_VALUE?1:0));
        settingCount.put(SliderType.HARD_TURN_SLIDER,  vehicleSensitivitySliders.getSettingsCount(SliderType.HARD_TURN_SLIDER)+(hardTurn==CUSTOM_SLIDER_VALUE?1:0));
        settingCount.put(SliderType.HARD_BUMP_SLIDER,  vehicleSensitivitySliders.getSettingsCount(SliderType.HARD_BUMP_SLIDER)+(hardVertical==CUSTOM_SLIDER_VALUE?1:0));

        return settingCount;
    }

    protected Map<Integer,String> getSensitivityValue(SliderType sliderType, Integer sliderValue) {
           
           return vehicleSensitivitySliders.getSensitivitySliderSettings(sliderType).getSettingValuesFromSliderValue(sliderValue);
       }

    protected Map<Integer, String> getVehiclSettingsForSliderSettingIDs(VehicleSetting vehicleSetting,Slider slider){
        
        Map<Integer, String> vehicleSettings = new HashMap<Integer, String>();
        
        Set<Integer> settingIDs = slider.getSettingIDsForThisSlider();
        
        for(Integer settingID :settingIDs){
            
            vehicleSettings.put(settingID, vehicleSetting.getCombined(settingID));
        }
        return vehicleSettings;
    }

    protected Integer extractSliderValue(SliderType sliderType, Map<Integer, String> settings) {
        
        if (settings == null) {
    
            return getDefaultSettings().get(sliderType);
        }
        return vehicleSensitivitySliders.getSensitivitySliderSettings(sliderType).getSliderValueFromSettings(settings);
    }

    public abstract class DesiredSettings{

        protected Map<Integer, String> desiredSettings;

        public DesiredSettings() {
            
            desiredSettings = new HashMap<Integer,String>();
        }

        public Map<Integer, String> getDesiredSettings() {
            return desiredSettings;
        }

        public boolean isDifferent(String newValue, String oldValue) {
        
           if(((oldValue != null) && (newValue == null)) ||
                ((oldValue == null) && (newValue != null)) || 
                (!oldValue.equals(newValue))) return true;
           return false;
        }
        public abstract void addSettingIfNeeded(SettingType setting,String newValue, String oldValue); 
      }
      public class ChangedSettings extends DesiredSettings{
          
          private Boolean batchEdit;
          private Map<String, Boolean> updateField;
          
          public ChangedSettings(Boolean batchEdit, Map<String, Boolean> updateField) {

            super();
                
            this.batchEdit = batchEdit;
            this.updateField = updateField;
          }

          @Override
          public void addSettingIfNeeded(SettingType setting,String newValue, String oldValue){
              
           if(isRequested(setting) && isDifferent(newValue,oldValue)){
               
               desiredSettings.put(setting.getSettingID(), newValue);
           }
        }
        private boolean isRequested(SettingType setting){
            
            if (!batchEdit) return true;
            if (updateField.get("editableVehicleSettings."+setting.getPropertyName())){
                return true;
            }
            return false;
        }
      }
      public class NewSettings extends DesiredSettings{

        @Override
        public void addSettingIfNeeded(SettingType setting,String newValue, String oldValue){
              
            if(isDifferent(newValue,oldValue)){
               
               desiredSettings.put(setting.getSettingID(), newValue);
            }
         }
      }
    public Map<SliderType, Integer> getAdjustedSettingCounts() {
        return adjustedSettingCounts;
    }

}

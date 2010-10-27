package com.inthinc.pro.backing.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.inthinc.pro.backing.EditableVehicleSettings;
import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.SettingType;
import com.inthinc.pro.model.configurator.Slider;
import com.inthinc.pro.model.configurator.SliderType;
import com.inthinc.pro.model.configurator.VehicleSetting;

public abstract class VehicleSettingManager {

	protected ConfiguratorDAO configuratorDAO;
    protected VehicleSetting  vehicleSetting;
    protected VehicleSensitivitySliders vehicleSensitivitySliders;
    protected Map<SliderType,Integer> adjustedSettingCounts;
        
    protected VehicleSettingManager(ConfiguratorDAO configuratorDAO, ProductType productType, VehicleSetting vehicleSetting) {

        this.configuratorDAO = configuratorDAO;
        this.vehicleSetting = vehicleSetting;
        vehicleSensitivitySliders = new VehicleSensitivitySliders(productType, 0, 1000000);
        
        adjustedSettingCounts = new HashMap<SliderType,Integer>();
    }
    
    public VehicleSettingManager() {

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
    public Map<SliderType, Integer> getAdjustedSettingCounts() {
        return adjustedSettingCounts;
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
        public abstract void addSliderIfNeeded(SettingType setting,String newValue, String oldValue);
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
         @Override
         public void addSliderIfNeeded(SettingType setting, String newValue, String oldValue) {

             if(isRequested(setting) && newValue!=null && isDifferent(newValue,oldValue)){
                 
                 desiredSettings.put(setting.getSettingID(), newValue);
             }
              
         }
         private boolean isRequested(SettingType setting){
            
            if (!batchEdit) return true;
            
            return updateField.get("editableVehicleSettings."+setting.getPropertyName());

         }
       }
      public class NewSettings extends DesiredSettings{

        @Override
        public void addSettingIfNeeded(SettingType setting,String newValue, String oldValue){
              
            if(isDifferent(newValue,oldValue)){
               
               desiredSettings.put(setting.getSettingID(), newValue);
            }
         }
      @Override
      public void addSliderIfNeeded(SettingType setting, String newValue, String oldValue) {

          if(newValue!=null && isDifferent(newValue,oldValue)){
              
              desiredSettings.put(setting.getSettingID(), newValue);
          }
           
      }
   }
}

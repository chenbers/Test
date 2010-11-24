package com.inthinc.pro.backing.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.inthinc.pro.backing.EditableVehicleSettings;
import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.SettingType;
import com.inthinc.pro.model.configurator.Slider;
import com.inthinc.pro.model.configurator.VehicleSetting;

public abstract class VehicleSettingManager {

	protected ConfiguratorDAO configuratorDAO;
    protected VehicleSetting  vehicleSetting;
    protected VehicleSensitivitySliders vehicleSensitivitySliders;
    
    private Integer adjustedHardAccelerationSetting;
    private Integer adjustedHardBrakeSetting;
    private Integer adjustedHardVerticalSetting;
    private Integer adjustedHardTurnSetting;
    
    protected VehicleSettingManager(ConfiguratorDAO configuratorDAO, ProductType productType, VehicleSetting vehicleSetting) {

        this.configuratorDAO = configuratorDAO;
        this.vehicleSetting = vehicleSetting;
        vehicleSensitivitySliders = new VehicleSensitivitySliders(productType, 0, 1000000);
    }
    
    public VehicleSettingManager() {

    }

    public abstract void init();
    public abstract Map<Integer, String> evaluateSettings(Integer vehicleID, EditableVehicleSettings editableVehicleSettings);
    public abstract Map<Integer, String> evaluateChangedSettings(Integer vehicleID, EditableVehicleSettings editableVehicleSettings);
    public abstract void setVehicleSettings(Integer vehicleID, EditableVehicleSettings editableVehicleSettings, Integer userID, String reason);
    public abstract void updateVehicleSettings(Integer vehicleID, EditableVehicleSettings editableVehicleSettings, Integer userID, String reason);


    public void setConfiguratorDAO(ConfiguratorDAO configuratorDAO) {
        this.configuratorDAO = configuratorDAO;
    }
    
	public EditableVehicleSettings associateSettings(Integer vehicleID) {
	    
	    if (vehicleSetting == null){
	        
	        return createDefaultValues(vehicleID); 
	    }
	    else {
	        return createFromExistingValues(vehicleID, vehicleSetting);
	    }
	}
	
    protected abstract EditableVehicleSettings createDefaultValues(Integer vehicleID);
    protected abstract EditableVehicleSettings createFromExistingValues(Integer vehicleID,VehicleSetting vs);
    
    protected Map<Integer, String> getHardAccelerationValue(Integer sliderValue){
        
        return vehicleSensitivitySliders.getHardAccelerationSlider().getSettingValuesFromSliderValue(sliderValue);
    }

    protected Map<Integer, String> getHardBrakeValue(Integer sliderValue){
        
        return vehicleSensitivitySliders.getHardBrakeSlider().getSettingValuesFromSliderValue(sliderValue);
    }
    protected Map<Integer, String> getHardTurnValue(Integer sliderValue){
        
        return vehicleSensitivitySliders.getHardTurnSlider().getSettingValuesFromSliderValue(sliderValue);
    }
    protected Map<Integer, String> getHardVerticalValue(Integer sliderValue){
        
        return vehicleSensitivitySliders.getHardVerticalSlider().getSettingValuesFromSliderValue(sliderValue);
    }

    public Slider getHardAccelerationSlider() {
        return vehicleSensitivitySliders.getHardAccelerationSlider();
    }

    public Slider getHardBrakeSlider() {
        return vehicleSensitivitySliders.getHardBrakeSlider();
    }

    public Slider getHardTurnSlider() {
        return vehicleSensitivitySliders.getHardTurnSlider();
    }

    public Slider getHardVerticalSlider() {
        return vehicleSensitivitySliders.getHardVerticalSlider();
    }

    protected Map<Integer, String> getVehiclSettingsForSliderSettingIDs(VehicleSetting vehicleSetting,Slider slider){
        
        Map<Integer, String> vehicleSettings = new HashMap<Integer, String>();
        
        Set<Integer> settingIDs = slider.getSettingIDsForThisSlider();
        
        for(Integer settingID :settingIDs){
            
            vehicleSettings.put(settingID, vehicleSetting.getCombined(settingID));
        }
        return vehicleSettings;
    }

    protected Integer extractHardAccelerationValue( Map<Integer, String> settings){
        
        if (settings == null) {
            
            return vehicleSensitivitySliders.getHardAccelerationSlider().getDefaultValueIndex();
        }
        return vehicleSensitivitySliders.getHardAccelerationSlider().getSliderValueFromSettings(settings);
    }
    protected Integer extractHardBrakeValue( Map<Integer, String> settings){
        
        if (settings == null) {
            
            return vehicleSensitivitySliders.getHardBrakeSlider().getDefaultValueIndex();
        }
        return vehicleSensitivitySliders.getHardBrakeSlider().getSliderValueFromSettings(settings);
    }
    protected Integer extractHardTurnValue( Map<Integer, String> settings){
        
        if (settings == null) {
            
            return vehicleSensitivitySliders.getHardTurnSlider().getDefaultValueIndex();
        }
        return vehicleSensitivitySliders.getHardTurnSlider().getSliderValueFromSettings(settings);
    }
    protected Integer extractHardVerticalValue( Map<Integer, String> settings){
        
        if (settings == null) {
            
            return vehicleSensitivitySliders.getHardVerticalSlider().getDefaultValueIndex();
        }
        return vehicleSensitivitySliders.getHardVerticalSlider().getSliderValueFromSettings(settings);
    }
    protected void adjustCountsForCustomValues(Integer hardAcceleration, Integer hardBrake, Integer hardTurn, Integer hardVertical){
        
        adjustedHardAccelerationSetting = vehicleSensitivitySliders.adjustHardAccelerationSettingCountToAllowForCustomValues(hardAcceleration);
        adjustedHardBrakeSetting = vehicleSensitivitySliders.adjustHardBrakeSettingCountToAllowForCustomValues(hardBrake);
        adjustedHardTurnSetting = vehicleSensitivitySliders.adjustHardTurnSettingCountToAllowForCustomValues(hardTurn);
        adjustedHardVerticalSetting = vehicleSensitivitySliders.adjustHardVerticalSettingCountToAllowForCustomValues(hardVertical);
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
          
         public ChangedSettings() {
    
            super();
         }
    
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
      public Integer getAdjustedHardAccelerationSetting() {
          return adjustedHardAccelerationSetting;
      }

      public Integer getAdjustedHardBrakeSetting() {
          return adjustedHardBrakeSetting;
      }

      public Integer getAdjustedHardVerticalSetting() {
          return adjustedHardVerticalSetting;
      }

      public Integer getAdjustedHardTurnSetting() {
          return adjustedHardTurnSetting;
      }
}

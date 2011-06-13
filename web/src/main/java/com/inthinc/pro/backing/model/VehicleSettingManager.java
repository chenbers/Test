package com.inthinc.pro.backing.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.inthinc.pro.backing.BaseBean;
import com.inthinc.pro.backing.EditableVehicleSettings;
import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.model.app.SensitivitySliders;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.SettingType;
import com.inthinc.pro.model.configurator.SensitivitySlider;
import com.inthinc.pro.model.configurator.SliderType;
import com.inthinc.pro.model.configurator.VehicleSetting;

@SuppressWarnings("serial")
public abstract class VehicleSettingManager extends BaseBean {

	protected ConfiguratorDAO configuratorDAO;
    protected VehicleSetting  vehicleSetting;

    protected VehicleSensitivitySlider hardAccelerationSlider;
    protected VehicleSensitivitySlider hardBrakeSlider;
    protected VehicleSensitivitySlider hardVerticalSlider;
    protected VehicleSensitivitySlider hardTurnSlider;
    
    public VehicleSettingManager() {

    }
    protected VehicleSettingManager(ConfiguratorDAO configuratorDAO, SensitivitySliders sensitivitySliders,
                                    ProductType productType, VehicleSetting vehicleSetting) {

        this.configuratorDAO = configuratorDAO;
        this.vehicleSetting = vehicleSetting;
        createVehicleSensitivitySliders(sensitivitySliders,productType, 0, 1000000);
    }
    
    protected VehicleSettingManager(ConfiguratorDAO configuratorDAO,SensitivitySliders sensitivitySliders,
                                    ProductType productType, Integer vehicleID, Integer deviceID) {

        this.configuratorDAO = configuratorDAO;
        this.vehicleSetting = new VehicleSetting(vehicleID,deviceID,productType);
        createVehicleSensitivitySliders(sensitivitySliders,productType, 0, 1000000);
    }
    private void createVehicleSensitivitySliders(SensitivitySliders sensitivitySliders,
                                    ProductType productType, int minFirmwareVersion, int maxFirmwareVersion){
        if (productType == null) return; 
        
        hardAccelerationSlider =  new VehicleSensitivitySlider(sensitivitySliders,SliderType.HARD_ACCEL_SLIDER,productType,minFirmwareVersion, maxFirmwareVersion);
        hardBrakeSlider = new VehicleSensitivitySlider(sensitivitySliders,SliderType.HARD_BRAKE_SLIDER,productType,minFirmwareVersion, maxFirmwareVersion);
        hardVerticalSlider = new VehicleSensitivitySlider(sensitivitySliders,SliderType.HARD_BUMP_SLIDER,productType,minFirmwareVersion, maxFirmwareVersion);
        hardTurnSlider = new VehicleSensitivitySlider(sensitivitySliders,SliderType.HARD_TURN_SLIDER,productType,minFirmwareVersion, maxFirmwareVersion);
       
    }
    public abstract void init();
    public abstract Map<Integer, String> evaluateSettings(Integer vehicleID, EditableVehicleSettings editableVehicleSettings);
    public abstract Map<Integer, String> evaluateChangedSettings(Integer vehicleID, EditableVehicleSettings editableVehicleSettings);
    public abstract void setVehicleSettings(Integer vehicleID, EditableVehicleSettings editableVehicleSettings, Integer userID, String reason);
//    public abstract void updateVehicleSettings(Integer vehicleID, EditableVehicleSettings editableVehicleSettings, Integer userID, String reason);

    public void updateVehicleSettings(Integer vehicleID, EditableVehicleSettings editableVehicleSettings, Integer userID, String reason) {
        
        Map<Integer, String> setMap = evaluateChangedSettings(vehicleID,editableVehicleSettings);
        configuratorDAO.updateVehicleSettings(vehicleID, setMap, userID, reason);
    }

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
	
    public abstract EditableVehicleSettings createDefaultValues(Integer vehicleID);
    protected abstract EditableVehicleSettings createFromExistingValues(Integer vehicleID,VehicleSetting vs);
    
    protected Map<Integer, String> getHardAccelerationValue(Integer sliderValue){
        
        return hardAccelerationSlider.getSettingValuesFromSliderValue(sliderValue);
    }

    protected Map<Integer, String> getHardBrakeValue(Integer sliderValue){
        
        return hardBrakeSlider.getSettingValuesFromSliderValue(sliderValue);
    }
    protected Map<Integer, String> getHardTurnValue(Integer sliderValue){
        
        return hardTurnSlider.getSettingValuesFromSliderValue(sliderValue);
    }
    protected Map<Integer, String> getHardVerticalValue(Integer sliderValue){
        
        return hardVerticalSlider.getSettingValuesFromSliderValue(sliderValue);
    }

    public SensitivitySlider getHardAccelerationSlider() {
        return hardAccelerationSlider.getSensitivitySlider();
    }

    public SensitivitySlider getHardBrakeSlider() {
        return hardBrakeSlider.getSensitivitySlider();
    }

    public SensitivitySlider getHardTurnSlider() {
        return hardTurnSlider.getSensitivitySlider();
    }

    public SensitivitySlider getHardVerticalSlider() {
        return hardVerticalSlider.getSensitivitySlider();
    }

    protected Map<Integer, String> getVehicleSettingsForSliderSettingIDs(VehicleSetting vehicleSetting,VehicleSensitivitySlider slider){
        
        Map<Integer, String> vehicleSettings = new HashMap<Integer, String>();
        
        Set<Integer> settingIDs = slider.getSettingIDsForThisSlider();
        
        for(Integer settingID :settingIDs){
            
            vehicleSettings.put(settingID, vehicleSetting.getCombined(settingID));
        }
        return vehicleSettings;
    }

    protected void adjustCountsForCustomValues(Integer hardAcceleration, Integer hardBrake, Integer hardTurn, Integer hardVertical){
        
        hardAccelerationSlider.adjustSettingCountToAllowForCustomValues(hardAcceleration);
        hardBrakeSlider.adjustSettingCountToAllowForCustomValues(hardBrake);
        hardTurnSlider.adjustSettingCountToAllowForCustomValues(hardTurn);
        hardVerticalSlider.adjustSettingCountToAllowForCustomValues(hardVertical);
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
        
           if((oldValue == null) && (newValue==null)) return false;
           if((oldValue == null)||(newValue == null)) return true;
           return !oldValue.equals(newValue);
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
          return hardAccelerationSlider.getAdjustedSetting();
      }

      public Integer getAdjustedHardBrakeSetting() {
          return hardBrakeSlider.getAdjustedSetting();
      }

      public Integer getAdjustedHardVerticalSetting() {
          return hardVerticalSlider.getAdjustedSetting();
      }

      public Integer getAdjustedHardTurnSetting() {
          return hardTurnSlider.getAdjustedSetting();
      }
}

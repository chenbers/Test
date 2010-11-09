package com.inthinc.pro.backing.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.inthinc.pro.model.app.SensitivitySliderValuesMapping;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.Slider;
import com.inthinc.pro.model.configurator.SliderType;
import com.inthinc.pro.model.configurator.VehicleSetting;

public class VehicleSensitivitySliders {

    protected static final Integer CUSTOM_SLIDER_VALUE = 99;
    
    private Slider hardAccelerationSlider;
    private Slider hardBrakeSlider;
    private Slider hardVerticalSlider;
    private Slider hardTurnSlider;

    public Slider getHardAccelerationSlider() {
        return hardAccelerationSlider;
    }
    public Slider getHardBrakeSlider() {
        return hardBrakeSlider;
    }
    public Slider getHardVerticalSlider() {
        return hardVerticalSlider;
    }
    public Slider getHardTurnSlider() {
        return hardTurnSlider;
    }
    
    public VehicleSensitivitySliders(ProductType productType, int minFirmwareVersion, int maxFirmwareVersion){
       
        createSliders(productType,minFirmwareVersion, maxFirmwareVersion);
    }

    private void createSliders(ProductType productType, int minFirmwareVersion, int maxFirmwareVersion){
        
        
        if (productType == null) return; 
        
        hardAccelerationSlider =  SensitivitySliderValuesMapping.getSlider(SliderType.HARD_ACCEL_SLIDER,productType,minFirmwareVersion, maxFirmwareVersion);
        hardBrakeSlider = SensitivitySliderValuesMapping.getSlider(SliderType.HARD_BRAKE_SLIDER,productType,minFirmwareVersion, maxFirmwareVersion);
        hardVerticalSlider = SensitivitySliderValuesMapping.getSlider(SliderType.HARD_BUMP_SLIDER,productType,minFirmwareVersion, maxFirmwareVersion);
        hardTurnSlider = SensitivitySliderValuesMapping.getSlider(SliderType.HARD_TURN_SLIDER,productType,minFirmwareVersion, maxFirmwareVersion);
    }
    
    public Integer adjustHardAccelerationSettingCountToAllowForCustomValues(Integer hardAcceleration) {
        
        return hardAccelerationSlider.getSliderPositionCount()+(hardAcceleration > hardAccelerationSlider.getSliderPositionCount()?1:0);
    }
    public Integer adjustHardBrakeSettingCountToAllowForCustomValues(Integer hardBrake) {
        
        return hardBrakeSlider.getSliderPositionCount()+(hardBrake > hardBrakeSlider.getSliderPositionCount()?1:0);
    }
    public Integer adjustHardVerticalSettingCountToAllowForCustomValues(Integer hardVertical) {
        
        return hardVerticalSlider.getSliderPositionCount()+(hardVertical > hardVerticalSlider.getSliderPositionCount()?1:0);
    }
    public Integer adjustHardTurnSettingCountToAllowForCustomValues(Integer hardTurn) {
        
        return hardTurnSlider.getSliderPositionCount()+(hardTurn > hardTurnSlider.getSliderPositionCount()?1:0);
    }

   protected Map<Integer, String> getVehiclSettingsForSliderSettingIDs(VehicleSetting vehicleSetting,Slider slider){
       
       Map<Integer, String> vehicleSettings = new HashMap<Integer, String>();
       
       Set<Integer> settingIDs = slider.getSettingIDsForThisSlider();
       
       for(Integer settingID :settingIDs){
           
           vehicleSettings.put(settingID, vehicleSetting.getCombined(settingID));
       }
       return vehicleSettings;
   }
}

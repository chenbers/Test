package com.inthinc.pro.backing.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.inthinc.pro.model.app.SensitivitySliderValuesMapping;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.Slider;
import com.inthinc.pro.model.configurator.SliderType;
import com.inthinc.pro.model.configurator.VehicleSetting;

public class VehicleSensitivitySliders {
    
    private Map<SliderType, Slider> settingsAndSlidersMap;
    private Map<SliderType, Integer> defaultSettings;
    private Map<SliderType, Integer> settingCounts;
    
    protected VehicleSensitivitySliders() {
        super();
        // TODO Auto-generated constructor stub
    }
    public VehicleSensitivitySliders(ProductType productType, int minFirmwareVersion, int maxFirmwareVersion){
       
        createSliders(productType,minFirmwareVersion, maxFirmwareVersion);
        if (!settingsAndSlidersMap.isEmpty()){
            
            initSettingCounts();
            initDefaultSettings();
            
        }
    }
    private void initSettingCounts() {
        
        settingCounts = new HashMap<SliderType,Integer>();
        
        settingCounts.put(SliderType.HARD_ACCEL_SLIDER, getSettingsCount(SliderType.HARD_ACCEL_SLIDER));
        settingCounts.put(SliderType.HARD_BRAKE_SLIDER, getSettingsCount(SliderType.HARD_BRAKE_SLIDER));
        settingCounts.put(SliderType.HARD_TURN_SLIDER,  getSettingsCount(SliderType.HARD_TURN_SLIDER));
        settingCounts.put(SliderType.HARD_BUMP_SLIDER,  getSettingsCount(SliderType.HARD_BUMP_SLIDER));
    }

    private void createSliders(ProductType productType, int minFirmwareVersion, int maxFirmwareVersion){
        
        settingsAndSlidersMap = new HashMap<SliderType, Slider>();
        
        if (productType == null) return; 
        
        settingsAndSlidersMap.put(SliderType.HARD_ACCEL_SLIDER, 
                                  SensitivitySliderValuesMapping.getSlider(SliderType.HARD_ACCEL_SLIDER,productType,minFirmwareVersion, maxFirmwareVersion));
        settingsAndSlidersMap.put(SliderType.HARD_BRAKE_SLIDER, 
                                  SensitivitySliderValuesMapping.getSlider(SliderType.HARD_BRAKE_SLIDER,productType,minFirmwareVersion, maxFirmwareVersion));
        settingsAndSlidersMap.put(SliderType.HARD_TURN_SLIDER, 
                                  SensitivitySliderValuesMapping.getSlider(SliderType.HARD_TURN_SLIDER,productType,minFirmwareVersion, maxFirmwareVersion));
        settingsAndSlidersMap.put(SliderType.HARD_BUMP_SLIDER, 
                                  SensitivitySliderValuesMapping.getSlider(SliderType.HARD_BUMP_SLIDER,productType,minFirmwareVersion, maxFirmwareVersion));
        
    }

    private void initDefaultSettings(){
        
        defaultSettings = new HashMap<SliderType,Integer>();
        
        defaultSettings.put(SliderType.HARD_ACCEL_SLIDER, settingsAndSlidersMap.get(SliderType.HARD_ACCEL_SLIDER).getDefaultValueIndex());
        defaultSettings.put(SliderType.HARD_BRAKE_SLIDER, settingsAndSlidersMap.get(SliderType.HARD_BRAKE_SLIDER).getDefaultValueIndex());
        defaultSettings.put(SliderType.HARD_TURN_SLIDER, settingsAndSlidersMap.get(SliderType.HARD_TURN_SLIDER).getDefaultValueIndex());
        defaultSettings.put(SliderType.HARD_BUMP_SLIDER, settingsAndSlidersMap.get(SliderType.HARD_BUMP_SLIDER).getDefaultValueIndex());
    }
    public Slider getSensitivitySliderSettings(SliderType sliderType){
       
       return settingsAndSlidersMap.get(sliderType);
   }
   
   public Integer getSettingsCount(SliderType sliderType){
       if(settingsAndSlidersMap == null ||settingsAndSlidersMap.get(sliderType) == null) {
           return 0;
       }
       Iterator<Integer> it = settingsAndSlidersMap.get(sliderType).getSettingsForThisSlider().keySet().iterator();
       if (it.hasNext()){
           
          return settingsAndSlidersMap.get(sliderType).getSettingsForThisSlider().get(it.next()).getValues().size();
       }
       return 0;
   }
   protected Map<Integer, String> getVehiclSettingsForSliderSettingIDs(VehicleSetting vehicleSetting,Slider slider){
       
       Map<Integer, String> vehicleSettings = new HashMap<Integer, String>();
       
       Set<Integer> settingIDs = slider.getSettingIDsForThisSlider();
       
       for(Integer settingID :settingIDs){
           
           vehicleSettings.put(settingID, vehicleSetting.getCombined(settingID));
       }
       return vehicleSettings;
   }
   public Map<SliderType, Integer> getDefaultSettings() {
       return defaultSettings;
   }
   public Map<SliderType, Integer> getSettingCounts() {
       return settingCounts;
   }
}

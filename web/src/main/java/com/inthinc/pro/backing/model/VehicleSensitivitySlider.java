package com.inthinc.pro.backing.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.inthinc.pro.model.app.SensitivitySliders;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.SensitivitySlider;
import com.inthinc.pro.model.configurator.SliderType;
import com.inthinc.pro.model.configurator.VehicleSetting;

public class VehicleSensitivitySlider {
    
    private SensitivitySlider sensitivitySlider;
    private Integer adjustedSetting;

    public VehicleSensitivitySlider(SensitivitySliders sensitivitySliders, 
                                    SliderType sliderType,
                                    ProductType productType, 
                                    int minFirmwareVersion, int maxFirmwareVersion) {
        sensitivitySlider = sensitivitySliders.getSensitivitySlider(sliderType,productType,minFirmwareVersion, maxFirmwareVersion);
    }
    public void adjustSettingCountToAllowForCustomValues(Integer setting) {
        
        adjustedSetting = sensitivitySlider.getSliderPositionCount()+(setting > sensitivitySlider.getSliderPositionCount()?1:0);
    }

    public Integer getAdjustedSetting() {
        return adjustedSetting;
    }
    public SensitivitySlider getSensitivitySlider() {
        return sensitivitySlider;
    }
    public int getDefaultValueIndex() {
        return sensitivitySlider.getDefaultValueIndex();
    }
    public Map<Integer, String> getSettingValuesFromSliderValue(Integer sliderValue) {
        return sensitivitySlider.getSettingValuesFromSliderValue(sliderValue);
    }
    public Integer getSliderValueFromSettings(VehicleSetting vehicleSetting) {
        Map<Integer, String> settings = getVehicleSettingsForSliderSettingIDs(vehicleSetting);
        return sensitivitySlider.getSliderValueFromSettings(settings);
    }
    private Map<Integer, String> getVehicleSettingsForSliderSettingIDs(VehicleSetting vehicleSetting){
        
        Map<Integer, String> vehicleSettings = new HashMap<Integer, String>();
        Set<Integer> settingIDs = sensitivitySlider.getSettingIDsForThisSlider();
        
        for(Integer settingID :settingIDs){
            vehicleSettings.put(settingID, vehicleSetting.getBestOption(settingID));
        }
        return vehicleSettings;
    }
    public Set<Integer> getSettingIDsForThisSlider() {
        return sensitivitySlider.getSettingIDsForThisSlider();
    }

}

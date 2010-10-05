package com.inthinc.pro.backing.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.model.SensitivitySliderValues;
import com.inthinc.pro.model.app.SensitivitySliderValuesMapping;

public class WaySmartSettingsAndSliders {
    public enum SliderType {
        
        HARD_ACCELERATION_SETTING,HARD_BRAKE_SETTING,HARD_TURN_SETTING,HARD_VERT_SETTING
    }
    public enum Setting {
        
        RMS_LEVEL(1224),
        HARDVERT_DMM_PEAKTOPEAK(1225),
        SEVERE_HARDVERT_LEVEL(1165),
        
        //Hard Turns
        Y_LEVEL(1226),
        DVY(1228),
        
        //Hard brakes
        X_ACCEL(1229),
        DVX(1231),
        
        //Hard Acceleration
        HARD_ACCEL_LEVEL(1232),
        HARD_ACCEL_DELTAV(1234);
        
        private Integer settingID;
        
        Setting(Integer settingID){
            
            this.settingID = settingID;
        }
        public Integer getSettingID(){
            return settingID;
        }
    }
    private static Map<SliderType, SettingsAndSliders> settingsAndSlidersMap;
    
    public void init(){
        
        Map<Integer, SensitivitySliderValues> sensitivitySliderValues = SensitivitySliderValuesMapping.getSensitivitySliderValues();

        settingsAndSlidersMap = new HashMap<SliderType, SettingsAndSliders>();
        
        settingsAndSlidersMap.put(SliderType.HARD_ACCELERATION_SETTING, getHardAccelSettingsAndSliders(sensitivitySliderValues));
        settingsAndSlidersMap.put(SliderType.HARD_BRAKE_SETTING, getHardBrakeSettingsAndSliders(sensitivitySliderValues));
        settingsAndSlidersMap.put(SliderType.HARD_TURN_SETTING, getHardTurnSettingsAndSliders(sensitivitySliderValues));
        settingsAndSlidersMap.put(SliderType.HARD_VERT_SETTING, getHardVertSettingsAndSliders(sensitivitySliderValues));
    }

    private SettingsAndSliders getHardAccelSettingsAndSliders(Map<Integer, SensitivitySliderValues> settingIDMap){
        
        SettingsAndSliders settingsAndSliders = new SettingsAndSliders();
        
        List<SensitivitySliderValues> sensitivitySliderValues = new ArrayList<SensitivitySliderValues>();
        
        sensitivitySliderValues.add(settingIDMap.get(Setting.HARD_ACCEL_LEVEL.getSettingID()));
        sensitivitySliderValues.add(settingIDMap.get(Setting.HARD_ACCEL_DELTAV.getSettingID()));
        
        settingsAndSliders.setSensitivitySliderValues(sensitivitySliderValues);
        
        return settingsAndSliders;
    }

   private SettingsAndSliders getHardBrakeSettingsAndSliders(Map<Integer, SensitivitySliderValues> settingIDMap){
       
       SettingsAndSliders settingsAndSliders = new SettingsAndSliders();
       
       List<SensitivitySliderValues> sensitivitySliderValues = new ArrayList<SensitivitySliderValues>();
       
       sensitivitySliderValues.add(settingIDMap.get(Setting.X_ACCEL.getSettingID()));
       sensitivitySliderValues.add(settingIDMap.get(Setting.DVX.getSettingID()));
       
       settingsAndSliders.setSensitivitySliderValues(sensitivitySliderValues);
       
       return settingsAndSliders;
   }
   private SettingsAndSliders getHardTurnSettingsAndSliders(Map<Integer, SensitivitySliderValues> settingIDMap){
       
       SettingsAndSliders settingsAndSliders = new SettingsAndSliders();
       
       List<SensitivitySliderValues> sensitivitySliderValues = new ArrayList<SensitivitySliderValues>();
       
       sensitivitySliderValues.add(settingIDMap.get(Setting.Y_LEVEL.getSettingID()));
       sensitivitySliderValues.add(settingIDMap.get(Setting.DVY.getSettingID()));
       
       settingsAndSliders.setSensitivitySliderValues(sensitivitySliderValues);
       
       return settingsAndSliders;
   }
   private SettingsAndSliders getHardVertSettingsAndSliders(Map<Integer, SensitivitySliderValues> settingIDMap){
       
       SettingsAndSliders settingsAndSliders = new SettingsAndSliders();
       
       List<SensitivitySliderValues> sensitivitySliderValues = new ArrayList<SensitivitySliderValues>();
       
       sensitivitySliderValues.add(settingIDMap.get(Setting.RMS_LEVEL.getSettingID()));
       sensitivitySliderValues.add(settingIDMap.get(Setting.SEVERE_HARDVERT_LEVEL.getSettingID()));
       sensitivitySliderValues.add(settingIDMap.get(Setting.HARDVERT_DMM_PEAKTOPEAK.getSettingID()));
       
       settingsAndSliders.setSensitivitySliderValues(sensitivitySliderValues);
       
       return settingsAndSliders;
   }
   public static SettingsAndSliders getSettingsAndSliders(SliderType sliderType){
       
       return settingsAndSlidersMap.get(sliderType);
   }
}
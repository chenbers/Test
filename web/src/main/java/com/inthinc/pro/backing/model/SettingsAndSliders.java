package com.inthinc.pro.backing.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.inthinc.pro.model.SensitivitySliderValues;
import com.inthinc.pro.model.app.SensitivitySliderValuesMapping;

public class SettingsAndSliders {
    
    private static final Integer CUSTOM_SLIDER = 99;
    protected List<SensitivitySliderValues> sensitivitySliderValues;
    
    public Integer getSlider(Map<Integer,String> settings){
        
        if (settings == null || settings.isEmpty()) return CUSTOM_SLIDER;
        
        Integer slider = CUSTOM_SLIDER;
        Set<Entry<Integer,String>> settingEntries = settings.entrySet();
        Integer firstSetting = settings.keySet().iterator().next();
        Integer sliderCount = SensitivitySliderValuesMapping.getSensitivitySliderValues().get(firstSetting).getValues().size();

        for(int i=0;i<sliderCount;i++){
             
            boolean match = true;
            slider = i+1;
            for (Entry<Integer,String> settingEntry : settingEntries){
                
                Integer settingID = settingEntry.getKey(); //settingID
                String settingValue = settingEntry.getValue();//setting value
                
                match = match && SensitivitySliderValuesMapping.getSensitivitySliderValues().get(settingID).getValues().get(i).equals(settingValue);
                
                if (!match){
                     
                    break;
                }
            }
            if (match) return slider;
        }
        return CUSTOM_SLIDER;
    }
     
    public SettingsAndSliders() {
        super();
        sensitivitySliderValues = new ArrayList<SensitivitySliderValues>();
    }
    public void setSensitivitySliderValues(List<SensitivitySliderValues> sensitivityTypes) {
        this.sensitivitySliderValues = sensitivityTypes;
    }

    public List<SensitivitySliderValues> getSensitivitySliderValues() {
        return sensitivitySliderValues;
    }

    public Map<Integer, String> getSettings(Integer slider) {
        
        Map<Integer, String> settings = new HashMap<Integer, String>();
        for(SensitivitySliderValues ssv : sensitivitySliderValues){
            
            settings.put(ssv.getSettingID(), SensitivitySliderValuesMapping.getSensitivitySliderValues().get(ssv.getSettingID()).getValues().get(slider));
        }
        return settings;
    }

}

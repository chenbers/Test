package com.inthinc.pro.backing.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.inthinc.pro.model.app.SensitivityMapping;
import com.inthinc.pro.model.configurator.SensitivityType;

public class SettingsAndSliders {
    
    private static final Integer CUSTOM_SLIDER = 99;
    protected List<SensitivityType> sensitivityTypes;
    
    public Integer getSlider(Map<SensitivityType,String> settings){
        
        if (settings == null || settings.isEmpty()) return CUSTOM_SLIDER;
        
        Integer slider = CUSTOM_SLIDER;
        Set<Entry<SensitivityType,String>> settingEntries = settings.entrySet();
        SensitivityType firstSetting = settings.keySet().iterator().next();
        Integer sliderCount = SensitivityMapping.getSensitivityForwardCommandMapping(firstSetting).getValues().size();

        for(int i=0;i<sliderCount;i++){
             
            boolean match = true;
            slider = i+1;
            for (Entry<SensitivityType,String> settingEntry : settingEntries){
                
                SensitivityType sensitivityType = settingEntry.getKey(); //settingID
                String settingValue = settingEntry.getValue();//setting value
                
                match = match && SensitivityMapping.getSensitivityForwardCommandMapping(sensitivityType).getValues().get(i).equals(settingValue);
                
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
        sensitivityTypes = new ArrayList<SensitivityType>();
    }
    public void setSensitivityTypes(List<SensitivityType> sensitivityTypes) {
        this.sensitivityTypes = sensitivityTypes;
    }

    public List<SensitivityType> getSensitivityTypes() {
        return sensitivityTypes;
    }

    public Map<SensitivityType, String> getSettings(Integer slider) {
        
        Map<SensitivityType, String> settings = new HashMap<SensitivityType, String>();
        for(SensitivityType sensitivityType : sensitivityTypes){
            
            settings.put(sensitivityType, SensitivityMapping.getSensitivityForwardCommandMapping(sensitivityType).getValues().get(slider));
        }
        return settings;
    }

}

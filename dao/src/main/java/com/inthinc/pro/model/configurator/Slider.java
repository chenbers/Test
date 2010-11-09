package com.inthinc.pro.model.configurator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.inthinc.pro.model.SensitivitySliderValues;

public class Slider {
    
    private Map<Integer,SensitivitySliderValues> settingsForThisSlider;
    private int sliderPositionCount;
    private int defaultValueIndex;
    private SliderType sliderType;
    private SliderKey sliderKey;
    
    public Slider(SliderKey sliderKey){
        this.sliderKey = sliderKey;
        sliderType = SliderType.valueOf(sliderKey.getSensitivityType());
        settingsForThisSlider = new HashMap<Integer,SensitivitySliderValues>();
    }
    public SliderType getSliderType() {
        return sliderType;
    }
    public void addSetting(SensitivitySliderValues sensitivitySliderValues){
        settingsForThisSlider.put(sensitivitySliderValues.getSettingID(),sensitivitySliderValues);
        if(sensitivitySliderValues.getDefaultValueIndex() != null){
            defaultValueIndex =  sensitivitySliderValues.getDefaultValueIndex();
        }
    }
    public int getDefaultValueIndex() {
        return defaultValueIndex;
    }
    public void setDefaultValueIndex(int defaultValueIndex) {
        this.defaultValueIndex = defaultValueIndex;
    }
    public Integer getSliderValueFromSettings(Map<Integer,String> settings){
        
        if (settings == null || settings.isEmpty()) return sliderPositionCount+1;
        
        Integer sliderValue = sliderPositionCount+1;
        Set<Entry<Integer,String>> settingEntries = settings.entrySet();

        for(int i=0;i<sliderPositionCount;i++){
             
            boolean match = true;
            sliderValue = i;
            for (Entry<Integer,String> settingEntry : settingEntries){
                
                Integer settingID = settingEntry.getKey(); //settingID
                String settingValue = settingEntry.getValue();//setting value
                
                match = match && settingsForThisSlider.get(settingID).getValues().get(i).equals(settingValue);
                
                if (!match){
                     
                    break;
                }
            }
            if (match) return sliderValue;
        }
        return sliderPositionCount+1;
    }
     
    public SliderKey getSliderKey() {
        return sliderKey;
    }

    public Map<Integer,SensitivitySliderValues> getSettingsForThisSlider() {
        return settingsForThisSlider;
    }

    public void setSettingsForThisSlider(Map<Integer,SensitivitySliderValues> settingsForThisSlider) {
        this.settingsForThisSlider = settingsForThisSlider;
    }

    public Map<Integer, String> getSettingValuesFromSliderValue(Integer sliderValue) {
        
        Map<Integer, String> settings = new HashMap<Integer, String>();
        
        if (sliderValue > settingsForThisSlider.entrySet().size()) return settings;
        
        Iterator<Entry<Integer,SensitivitySliderValues>> settingsEntrySetIterator = settingsForThisSlider.entrySet().iterator();
        
        while(settingsEntrySetIterator.hasNext()){
            
            Entry<Integer,SensitivitySliderValues> settingsEntry= settingsEntrySetIterator.next();
            
            settings.put(settingsEntry.getValue().getSettingID(), settingsEntry.getValue().getValues().get(sliderValue-1));
        }
        return settings;
    }
    public void setSliderPositionCount(){
        
        Iterator<SensitivitySliderValues> sliderValuesIterator = settingsForThisSlider.values().iterator();
        if(sliderValuesIterator.hasNext()){
            sliderPositionCount = sliderValuesIterator.next().getValues().size();
        }
        else sliderPositionCount = 0;
    }
    
    public int getSliderPositionCount(){
        return sliderPositionCount;
    }
    public Set<Integer> getSettingIDsForThisSlider(){
        
        return settingsForThisSlider.keySet();
    }
}

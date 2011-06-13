package com.inthinc.pro.model.configurator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.inthinc.pro.model.SensitivitySliderValues;

public class SensitivitySlider {
    
    private SliderKey sliderKey;
    private Map<Integer,SensitivitySliderValues> settingsForThisSlider;
    private int sliderPositionCount;
    private int defaultValueIndex;
    
    public SensitivitySlider(SliderKey sliderKey){
        this.sliderKey = sliderKey;
        settingsForThisSlider = new HashMap<Integer,SensitivitySliderValues>();
    }
    public void addSetting(SensitivitySliderValues sensitivitySliderValues){
        settingsForThisSlider.put(sensitivitySliderValues.getSettingID(),sensitivitySliderValues);
        if(sensitivitySliderValues.getSensitivitySubtype() == 0){
            defaultValueIndex =  sensitivitySliderValues.getDefaultValueIndex();
            sliderPositionCount = sensitivitySliderValues.getValues().size();
        }
    }
    public Integer getSliderValueFromSettings(Map<Integer,String> settings){
        //If you have a set of values for a slider this returns the corresponding slider position.  
        //If it doesn't match it returns the highest slider position + 1 which is the "custom" value
        //This may happen if the settings have been set in the configurator
        //Where there is a value in the setting that represents the slider position it is ignored
        // in the matching because it won't get set correctly in the configurator.  This value should
        // eventually be dropped
        if (settings == null || settings.isEmpty() || settingsAllNull(settings)) return defaultValueIndex;
        
        Set<Entry<Integer,String>> settingEntries = settings.entrySet();

        for(int sliderValue=0;sliderValue<sliderPositionCount;sliderValue++){
            
            if (settingsMatch(sliderValue,settingEntries)) return sliderValue+1;
        }
        return sliderPositionCount+1;
    }
    private boolean settingsAllNull(Map<Integer,String> settings){
        Set<Entry<Integer,String>> settingEntries = settings.entrySet();
        
        for (Entry<Integer,String> settingEntry : settingEntries){
            
            if (settingEntry.getValue() != null) return false;
        }
        return true;
    }
    private boolean settingsMatch(int sliderValue,Set<Entry<Integer,String>> settingEntries){
        //Where there are several settings for a slider value all must match
        boolean match = true;
        for (Entry<Integer,String> settingEntry : settingEntries){
            
            SensitivitySliderValues sensitvitySliderValues = settingsForThisSlider.get(settingEntry.getKey());
            
            match = subValuesMatch(settingEntry.getValue(),sensitvitySliderValues.getValues().get(sliderValue));
            
            if(!match) return false;
        }
        return true;
        
    }
    private Boolean subValuesMatch(String settingValue, String thisSliderValue){
        //Where one setting has several values within it all must match except for the 
        // one representing the slider position (hardcoded to value 2)
        if (null == settingValue) return false;
        
        List<String> settingValues = Arrays.asList(settingValue.split(" "));
        List<String> sliderValues = Arrays.asList(thisSliderValue.split(" "));
        if (sliderValues.size()!= settingValues.size()) return false;
        for(int i=0; i<sliderValues.size();i++){
            
            if(i==2) continue; //slider position value shouldn't be counted
            if (!settingValues.get(i).equals(sliderValues.get(i))){
                return false;
            }
        }
        return true;
    }
    public SliderKey getSliderKey() {
        return sliderKey;
    }

    public Map<Integer,SensitivitySliderValues> getSettingsForThisSlider() {
        return settingsForThisSlider;
    }

    public Map<Integer, String> getSettingValuesFromSliderValue(Integer sliderValue) {
        //If you have a slider value, this returns a map of setting values that match it   
        if (!sliderValueInRange(sliderValue)) return new HashMap<Integer, String>();
        
        Map<Integer, String> sliderValues = getValuesForSliderValue(sliderValue);
        
        return sliderValues;
    }
    private boolean sliderValueInRange(Integer sliderValue){
        
        Iterator<SensitivitySliderValues> it = settingsForThisSlider.values().iterator();
        List<String> values = null;
        if (it.hasNext()){
            values = it.next().getValues();
        }    
         
        return (values != null) && (sliderValue <= values.size());
    }
    private Map<Integer, String> getValuesForSliderValue(Integer sliderValue){
        
        Map<Integer, String> settings = new HashMap<Integer, String>();
        Iterator<Entry<Integer,SensitivitySliderValues>> settingsEntrySetIterator = settingsForThisSlider.entrySet().iterator();
        
        while(settingsEntrySetIterator.hasNext()){
            
            Entry<Integer,SensitivitySliderValues> settingsEntry= settingsEntrySetIterator.next();
            
            settings.put(settingsEntry.getValue().getSettingID(), settingsEntry.getValue().getValues().get(sliderValue-1));
        }
        return settings;
    }
    
    public int getSliderPositionCount(){
        return sliderPositionCount;
    }
    public Set<Integer> getSettingIDsForThisSlider(){
        
        return settingsForThisSlider.keySet();
    }
    public int getDefaultValueIndex() {
        return defaultValueIndex;
    }
}

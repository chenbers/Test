package com.inthinc.pro.model.configurator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
        
        Set<Entry<Integer,String>> settingEntries = settings.entrySet();

        for(int sliderValue=0;sliderValue<sliderPositionCount;sliderValue++){
            
            if (settingsMatch(sliderValue,settingEntries)) return sliderValue+1;
        }
        return sliderPositionCount+1;
    }
    private boolean settingsMatch(int sliderValue,Set<Entry<Integer,String>> settingEntries){
        boolean match = true;
        for (Entry<Integer,String> settingEntry : settingEntries){
            
            Integer settingID = settingEntry.getKey(); //settingID
            String settingValue = settingEntry.getValue();//setting value
            SensitivitySliderValues sensitvitySliderValues = settingsForThisSlider.get(settingID);
            
            match = subValuesMatch(settingValue,sensitvitySliderValues.getValues().get(sliderValue));
            
            if (match) return true;
        }
        return false;
        
    }
    private Boolean subValuesMatch(String settingValue, String thisSliderValue){

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

    public void setSettingsForThisSlider(Map<Integer,SensitivitySliderValues> settingsForThisSlider) {
        this.settingsForThisSlider = settingsForThisSlider;
    }

    public Map<Integer, String> getSettingValuesFromSliderValue(Integer sliderValue) {
           
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

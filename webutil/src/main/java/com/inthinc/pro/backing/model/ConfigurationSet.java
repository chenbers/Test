package com.inthinc.pro.backing.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class ConfigurationSet {

    public ConfigurationSet() {
        super();
        configurations = new ArrayList<SettingOptions>();
        settingIDsWithMoreThanOneValue = new ArrayList<Integer>();
    }
    List<SettingOptions> configurations;
    List<Integer> settingIDsWithMoreThanOneValue;
    
    
    
    public List<SettingOptions> getConfigurations() {
        return configurations;
    }
    public void setConfigurations(List<SettingOptions> configurations) {
        this.configurations = configurations;
        deriveSettingIDsWithMoreThanOneValue();
    }
    private void deriveSettingIDsWithMoreThanOneValue(){
        
        //Build  a set for each setting 
        Map<Integer,Set<String>> differences = new HashMap<Integer,Set<String>>();
        
        for(SettingOptions settingOptions : configurations){
            
            for (Entry<Integer,String> setting : settingOptions.getEntries()){
                
                if (!differences.containsKey(setting.getKey())){
                    
                    differences.put(setting.getKey(), new HashSet<String>());
                }
                differences.get(setting.getKey()).add(setting.getValue());
            }
        }
 
        settingIDsWithMoreThanOneValue = new ArrayList<Integer>();
        for(Entry<Integer,Set<String>> entry : differences.entrySet()){
            
            if (entry.getValue().size() > 1){
                
                settingIDsWithMoreThanOneValue.add(entry.getKey());
            }
        }
    }
    public List<Integer> getSettingIDsWithMoreThanOneValue() {
        return settingIDsWithMoreThanOneValue;
    }
    public boolean getHasConfigurations(){
        
        return !configurations.isEmpty();
    }
    public Integer getConfigurationsSize(){
        
        return configurations.size();
    }
}

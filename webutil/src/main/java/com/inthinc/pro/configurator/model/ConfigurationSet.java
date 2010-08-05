package com.inthinc.pro.configurator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class ConfigurationSet {

    List<Configuration> configurations;
    List<Integer> settingIDsWithMoreThanOneValue;
    
//    public ConfigurationSet() {
//        super();
//        configurations = new ArrayList<Configuration>();
//        settingIDsWithMoreThanOneValue = new ArrayList<Integer>();
//    }    
    public ConfigurationSet(List<Configuration> configurations){

        this.configurations = configurations;
        deriveSettingIDsWithMoreThanOneValue();
    }
    public List<Configuration> getConfigurations() {
        return configurations;
    }
//    public void setConfigurations(List<Configuration> configurations) {
//        this.configurations = configurations;
//        deriveSettingIDsWithMoreThanOneValue();
//    }
    private void deriveSettingIDsWithMoreThanOneValue(){
        
        //Build  a set for each setting 
        Map<Integer,Set<String>> differences = new HashMap<Integer,Set<String>>();
        
        for(Configuration configuration : configurations){
            
            for (Entry<Integer,String> setting : configuration.getEntries()){
                
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

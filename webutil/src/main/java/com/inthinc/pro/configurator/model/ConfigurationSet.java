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
    
    public ConfigurationSet(List<Configuration> configurations){

        this.configurations = configurations;
        deriveSettingIDsWithMoreThanOneValue();
    }
    
    public Configuration getConfiguration(Integer configurationID){
    	
    	for(Configuration c:configurations){
    		
    		if(c.getConfigurationID() == configurationID) return c;
    	}
    	return null;
    }
    public List<Configuration> getConfigurations() {
        return configurations;
    }

    private void deriveSettingIDsWithMoreThanOneValue(){
        
        //Build  a set for each setting 
        Map<Integer,Set<String>> differences = new HashMap<Integer,Set<String>>();
        
        for(Configuration configuration : configurations){
            
            for (Entry<Integer,String> setting : configuration.getSettingsEntries()){
                
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
        
        return configurations != null && !configurations.isEmpty();
    }
    public Integer getConfigurationsSize(){
        
        return configurations == null?0:configurations.size();
    }
    public ConfigurationSet getSelectedConfigurations(){
    	List<Configuration> selectedConfigurations = new ArrayList<Configuration>();
    	
    	for (Configuration c : configurations){
    		
    		if (c.isSelect()){
    			selectedConfigurations.add(c);
    		}
    	}
    	return new ConfigurationSet(selectedConfigurations);
    }
}

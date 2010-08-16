package com.inthinc.pro.configurator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Configuration{
    
	private Integer configurationID;
	
    private List<Integer> vehicleIDs;
    private Map<Integer,String> settingValues;
    private Map<Integer,String> desiredValues;
    private Map<Integer,String> newDesiredValues;
	private String reason;
    private List<Entry<Integer,String>> entries;
    
    public Configuration(Integer configurationID) {
    	
    	this.configurationID = configurationID;
    	
        vehicleIDs = new ArrayList<Integer>();
        settingValues = new HashMap<Integer,String>();
        desiredValues = new HashMap<Integer,String>();
        newDesiredValues = new HashMap<Integer,String>();
    }
    
    public Integer getConfigurationID() {
		return configurationID;
	}

	public void setNewDesiredValue(Integer settingID, String value){
    	
    	if (desiredValues.get(settingID) != null && desiredValues.get(settingID).equals(value)) {
    		
    		newDesiredValues.remove(settingID);
    		return;
    	}
    	newDesiredValues.put(settingID, value);
    }
    public Map<Integer, String> getNewDesiredValues() {
		return newDesiredValues;
	}
	public Configuration(Configuration configuration){
        
        vehicleIDs = new ArrayList<Integer>(configuration.vehicleIDs);
        settingValues = new HashMap<Integer, String>(configuration.settingValues);
    }
    public Map<Integer, String> getDesiredValues() {
		return desiredValues;
	}
   public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
    public void addValue(Integer settingID, String value){
        
        settingValues.put(settingID,value);
    }
    public List<Integer> getVehicleIDs() {
        return vehicleIDs;
    }
    public void setVehicleIDs(List<Integer> vehicleIDs) {
        this.vehicleIDs = vehicleIDs;
    }
    public void addVehicleID(Integer vehicleID){
        vehicleIDs.add(vehicleID);
    }

    public Integer getCount(){
        return vehicleIDs.size();
    }
    public List<String> getValuesList(){

        return new ArrayList<String>(settingValues.values());
    }
    public List<Entry<Integer,String>> getEntries(){
        
       if(entries == null){
            
            entries = new ArrayList<Entry<Integer,String>>(settingValues.entrySet());
        }
        return entries;
    }
    public Map<Integer, String> getSettingValues() {
		return settingValues;
	}

	public void setSettingValues(Map<Integer, String> settingValues) {
		this.settingValues = settingValues;
	}
}

package com.inthinc.pro.configurator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Configuration{
    
    private List<Integer> vehicleIDs;
    private Map<Integer,String> values;
    private Map<Integer,String> desiredValues;
	private String reason;
	private Map<Integer, String> colors;
	private Map<Integer, String> messages;
	//    private List<Integer> keys;
    private List<Entry<Integer,String>> entries;
    private Map<Integer, String> newDesiredValues;
    
    public Configuration() {
        super();
        vehicleIDs = new ArrayList<Integer>();
        values = new HashMap<Integer,String>();
        desiredValues = new HashMap<Integer,String>();
        newDesiredValues = new HashMap<Integer,String>();
    }
    
    public void setNewDesiredValue(Integer settingID, String value){
    	
    	if (desiredValues.get(settingID).equals(value)) {
    		
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
        values = new HashMap<Integer, String>(configuration.values);
        setColors(new HashMap<Integer, String>());
        setMessages(new HashMap<Integer, String>());
    }
	public Map<Integer, String> getMessages() {
		return messages;
	}
	public void setMessages(Map<Integer, String> messages) {
		this.messages = messages;
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
        
        values.put(settingID,value);
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

        return new ArrayList<String>(values.values());
    }
    public List<Entry<Integer,String>> getEntries(){
        
       if(entries == null){
            
            entries = new ArrayList<Entry<Integer,String>>(values.entrySet());
        }
        return entries;
    }
    public Map<Integer, String> getValues() {
        return values;
    }
    public void setValues(Map<Integer, String> values) {
        this.values = values;
    }
    public void setColors(Map<Integer, String> colors) {
        this.colors = colors;
    }
    public Map<Integer, String> getColors() {
        return colors;
    }
}

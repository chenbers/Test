package com.inthinc.pro.configurator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class Configuration{
    
	private boolean select;

	private Integer configurationID;
	
    private List<Integer> vehicleIDs;
    private String vehicleIDsString="";
    
    private EditableMap<Integer,String> editedDesiredValues;
	private String reason;

	public boolean isSelect() {
		return select;
	}
	
	public void setSelect(boolean select) {
		this.select = select;
	}
	public Object selectAction(){
		
		select = !select;
		return null;
	}
	public Map<Integer, String> getOriginalValues() {
		return editedDesiredValues.getOriginalValues();
	}

	public Map<Integer, String> getDifferencesMap() {
		return editedDesiredValues.getDifferencesMap();
	}
    public Set<Entry<Integer, String>> getSettingsEntries(){
    	
    	return editedDesiredValues.getOriginalValues().entrySet();
    	
    }
    public Configuration(Integer configurationID) {
    	
    	this.configurationID = configurationID;
    	
        vehicleIDs = new ArrayList<Integer>();
    }
    
    public Integer getConfigurationID() {
		return configurationID;
	}
    public void setEditedDesiredValues(EditableMap<Integer, String> editedDesiredValues){
    	
    	this.editedDesiredValues = editedDesiredValues;
    }
    public Map<Integer, String> getLatestDesiredValues() {
    	
		return editedDesiredValues.getLatestValues();
	}
	public EditableMap<Integer, String> getEditedDesiredValues() {
		return editedDesiredValues;
	}
	public void resetDesiredValues(){
		
		editedDesiredValues.reset();
	}
	public Configuration(Configuration configuration){
        
        vehicleIDs = new ArrayList<Integer>(configuration.vehicleIDs);
        editedDesiredValues = configuration.getEditedDesiredValues();
    }
   public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
    public List<Integer> getVehicleIDs() {
        return vehicleIDs;
    }
    public void setVehicleIDs(List<Integer> vehicleIDs) {
        this.vehicleIDs = vehicleIDs;
    }
    public void addVehicleID(Integer vehicleID){
        vehicleIDs.add(vehicleID);
        vehicleIDsString+=vehicleID+",";
    }

    public Integer getCount(){
        return vehicleIDs.size();
    }
    
    public String getVehicleIDsString(){
        
        if (vehicleIDsString.isEmpty()) return vehicleIDsString;
        StringBuilder vehicleIDsStringBuilder = new StringBuilder(vehicleIDsString);
        
        vehicleIDsStringBuilder.setLength(vehicleIDsStringBuilder.length()-1);
        return vehicleIDsStringBuilder.toString();
    }
}

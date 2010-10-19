package com.inthinc.pro.configurator.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class Configuration{
    
    private boolean select;
	private String messageDigest5;

    private Integer configurationID;
	
    private List<Integer> vehicleIDs;
    private String vehicleIDsString="";
    
    private EditableMap<Integer,String> editedDesiredValues;
	private String reason;
	
    public Configuration (Integer vehicleID, Integer configurationID, 
            EditableMap<Integer, String> editedDesiredValues,
            String editedDesiredValuesMD5){

        this.configurationID = configurationID;
        vehicleIDs = new ArrayList<Integer>();
        addVehicleID(vehicleID);
        this.editedDesiredValues = editedDesiredValues;
        this.messageDigest5 = editedDesiredValuesMD5;
    }

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
    
    public String getMessageDigest5() {
        return messageDigest5;
    }

    public void setMessageDigest5(String messageDigest5) {
        this.messageDigest5 = messageDigest5;
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
        messageDigest5 = configuration.getMessageDigest5();
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

package com.inthinc.pro.model.configurator;

import java.util.HashMap;
import java.util.Map;

public class VehicleSetting {
    
    private Integer vehicleID;
    private Integer deviceID;
    private ProductType productType;
    private Map<Integer, String> desired;
    private Map<Integer, String> actual;
    private Map<Integer, String> combinedSettings;
       
    public Integer getVehicleID() {
        return vehicleID;
    }
    public void setVehicleID(Integer vehicleID) {
        this.vehicleID = vehicleID;
    }
    public Integer getDeviceID() {
        return deviceID;
    }
    public void setDeviceID(Integer deviceID) {
        this.deviceID = deviceID;
    }
    public ProductType getProductType() {
		return productType;
	}
	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
	public Map<Integer, String> getDesired() {
        return desired;
    }
    public void setDesired(Map<Integer, String> desired) {
        this.desired = desired;
    }
    public Map<Integer, String> getActual() {
        return actual;
    }
    public void setActual(Map<Integer, String> actual) {
        this.actual = actual;
    }
    public boolean hasSettings(){
    	
    	return (actual != null) || (desired != null);
    }
    public void combineSettings(){
    	
		combinedSettings = new HashMap<Integer,String>();

		if (actual != null){
    		
    		combinedSettings.putAll(actual);
    	}
    	if (desired != null){
    		
    		combinedSettings.putAll(desired);   		
    	}
    }
	public Map<Integer,String> getCombinedSettings(){
		
    	combineSettings();
    	
		return combinedSettings;
    }
	public void clearCombinedSettings() {
		this.combinedSettings = null;
	}

}

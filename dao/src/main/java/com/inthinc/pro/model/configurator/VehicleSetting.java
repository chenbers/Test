package com.inthinc.pro.model.configurator;

import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.dao.annotations.Column;

public class VehicleSetting {
    
    private Integer vehicleID;
    private Integer deviceID;
    @Column (name="productVer")
    private ProductType productType;
    private Map<Integer, String> desired;
    private Map<Integer, String> actual;
    @Column(updateable=false)
    private Map<Integer, String> combinedSettings;
    
	public VehicleSetting() {
	}

    public VehicleSetting(Integer vehicleID, Integer deviceID, ProductType productType) {
        this.vehicleID = vehicleID;
        this.deviceID = deviceID;
        this.productType = productType;

        desired = new HashMap<Integer,String>();
        actual  = new HashMap<Integer,String>();
//        combinedSettings = new HashMap<Integer,String>();
    }
	public VehicleSetting(Integer vehicleID,ProductType productType) {
		this(vehicleID,null,productType);
    }
	
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
    
    public String getBestOption(Integer settingID){
        if ((desired != null) && desired.containsKey(settingID)) return desired.get(settingID);
        if (actual != null) return actual.get(settingID);
        return null;
    }
    public void combineSettings(){
    	
		combinedSettings = new HashMap<Integer,String>();

		if (actual != null){
    		
    		combinedSettings.putAll(actual);
    	}
    	if (desired != null){
    		//Desired will overwrite actuals
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
	public boolean settingsAreEmpty(){
	    return actual.isEmpty() && desired.isEmpty();
	}
}

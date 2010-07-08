package com.inthinc.pro.model.configurator;

import java.util.List;

public class VehicleSetting {
    
    private Integer vehicleID;
    private Integer deviceID;
    private List<Setting> settings;
    
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
    public List<Setting> getSettings() {
        return settings;
    }
    public void setSettings(List<Setting> settings) {
        this.settings = settings;
    }

}

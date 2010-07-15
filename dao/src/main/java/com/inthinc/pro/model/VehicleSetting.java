package com.inthinc.pro.model;

import java.util.Map;

public class VehicleSetting {

    private long                vehicleID;
    private Map<Integer,String> settings;
    
    public long getVehicleID() {
        return vehicleID;
    }
    public void setVehicleID(long vehicleID) {
        this.vehicleID = vehicleID;
    }
    public Map<Integer, String> getSettings() {
        return settings;
    }
    public void setSettings(Map<Integer, String> settings) {
        this.settings = settings;
    }
}

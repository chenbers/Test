package com.inthinc.pro.reports.hos.model;

public class VehicleInfo {
    
    private Number startOdometer;
    private String name;
    private Number driverMiles;
    private Integer vehicleID;
    private Number vehicleMiles;

    public Number getVehicleMiles() {
        return vehicleMiles;
    }
    public void setVehicleMiles(Number vehicleMiles) {
        this.vehicleMiles = vehicleMiles;
    }
    public Integer getVehicleID() {
        return vehicleID;
    }
    public void setVehicleID(Integer vehicleID) {
        this.vehicleID = vehicleID;
    }
    public Number getStartOdometer() {
        return startOdometer;
    }
    public void setStartOdometer(Number startOdometer) {
        this.startOdometer = startOdometer;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Number getDriverMiles() {
        return driverMiles;
    }
    public void setDriverMiles(Number driverMiles) {
        this.driverMiles = driverMiles;
    }
}

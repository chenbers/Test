package com.inthinc.pro.reports.hos.model;

public class VehicleInfo {
    
    private Number startOdometer;
    private String name;
    private Number milesDriven;

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
    public Number getMilesDriven() {
        return milesDriven;
    }
    public void setMilesDriven(Number milesDriven) {
        this.milesDriven = milesDriven;
    }
}

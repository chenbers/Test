package com.inthinc.pro.model.hos;

import java.util.Date;

import com.inthinc.pro.model.BaseEntity;

public class HOSVehicleDayData extends BaseEntity {
    
    Integer     vehicleID;
    String      vehicleName;
    Date        day;
    Long        startOdometer;
    Long        milesDriven;
    
    

    public Integer getVehicleID() {
        return vehicleID;
    }
    public void setVehicleID(Integer vehicleID) {
        this.vehicleID = vehicleID;
    }
    public String getVehicleName() {
        return vehicleName;
    }
    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }
    public Date getDay() {
        return day;
    }
    public void setDay(Date day) {
        this.day = day;
    }
    public Long getStartOdometer() {
        return startOdometer;
    }
    public void setStartOdometer(Long startOdometer) {
        this.startOdometer = startOdometer;
    }
    public Long getMilesDriven() {
        return milesDriven;
    }
    public void setMilesDriven(Long milesDriven) {
        this.milesDriven = milesDriven;
    }
}

package com.inthinc.pro.model.aggregation;

import org.joda.time.DateTime;

public class DriveTimeRecord {
    

    private DateTime dateTime;
    private Integer driverID;
    private String vehicleName;
    private Integer vehicleID;
    private Long driveTimeSeconds;

    public DriveTimeRecord() {
        super();
    }
    public DriveTimeRecord(DateTime dateTime, Integer driverID, String vehicleName, Integer vehicleID, Long driveTimeSeconds) {
        super();
        this.dateTime = dateTime;
        this.driverID = driverID;
        this.vehicleName = vehicleName;
        this.vehicleID = vehicleID;
        this.driveTimeSeconds = driveTimeSeconds;
    }
    
    public DateTime getDateTime() {
        return dateTime;
    }
    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }
    public Integer getDriverID() {
        return driverID;
    }
    public void setDriverID(Integer driverID) {
        this.driverID = driverID;
    }
    public String getVehicleName() {
        return vehicleName;
    }
    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }
    public Integer getVehicleID() {
        return vehicleID;
    }
    public void setVehicleID(Integer vehicleID) {
        this.vehicleID = vehicleID;
    }
    public Long getDriveTimeSeconds() {
        return driveTimeSeconds;
    }
    public void setDriveTimeSeconds(Long driveTimeSeconds) {
        this.driveTimeSeconds = driveTimeSeconds;
    }

    public void dump() {
        System.out.println("" + dateTime.getMillis() + " " + dateTime + " " + driverID + " " + vehicleID + " " + driveTimeSeconds);
    }
}

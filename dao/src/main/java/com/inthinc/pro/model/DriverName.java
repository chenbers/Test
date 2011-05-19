package com.inthinc.pro.model;

public class DriverName{
    
    private Integer driverID;
    private String driverName;
    
    public DriverName() {
    }
    public DriverName(Integer driverID, String driverName) {
        super();
        this.driverID = driverID;
        this.driverName = driverName;
    }
    public Integer getDriverID() {
        return driverID;
    }
    public void setDriverID(Integer driverID) {
        this.driverID = driverID;
    }
    public String getDriverName() {
        return driverName;
    }
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
    
}

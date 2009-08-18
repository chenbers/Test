package com.inthinc.pro.model;

import java.util.Date;
import java.util.List;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;

public class CrashReport extends BaseEntity {

    /**
     * 
     */
    @Column(updateable = false)
    private static final long serialVersionUID = -2232308206099636851L;
    @ID
    private Integer crashReportID;
    @Column(name = "acctID")
    private Integer accountID;
    private CrashReportStatus crashReportStatus;
    private Date date;
    private LatLng latLng;
    private String address;
    private String damage;
    private Integer vehicleID;
    private Integer driverID;
    @Column(updateable = false)
    private Vehicle vehicle;
    @Column(updateable = false)
    private Driver driver;
    private String weather;
    private String description;
    private Long noteID;
    @Column(updateable = false)
    private FullEvent fullEvent;
    @Column(updateable = false)
    private List<VehicleOccupant> vehicleOccupants;
    @Column(updateable = false)
    private List<CrashDataPoint> crashDataPoints; //Detailed Crash Data
    
    public CrashReport(){
        
    }

    public CrashReport(Integer accountID, CrashReportStatus crashReportStatus, String damage, Vehicle vehicle, Driver driver, String weather) {
        super();
        this.accountID = accountID;
        this.setCrashReportStatus(crashReportStatus);
        this.damage = damage;
        this.vehicleID = vehicle != null ? vehicle.getVehicleID():null;
        this.vehicle = vehicle;
        this.weather = weather;
        this.driverID = driver != null ? driver.getDriverID():null;
        this.driver = driver;
    }

    
    public Integer getCrashReportID() {
        return crashReportID;
    }

    
    public void setCrashReportID(Integer crashReportID) {
        this.crashReportID = crashReportID;
    }

    
    public Integer getAccountID() {
        return accountID;
    }

    
    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    
    public CrashReportStatus getCrashReportStatus() {
        return crashReportStatus;
    }

    
    public void setCrashReportStatus(CrashReportStatus crashReportStatus) {
        this.crashReportStatus = crashReportStatus;
    }

    
    public Date getDate() {
        return date;
    }

    
    public void setDate(Date date) {
        this.date = date;
    }

    
    public LatLng getLatLng() {
        return latLng;
    }

    
    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    
    public String getAddress() {
        return address;
    }

    
    public void setAddress(String address) {
        this.address = address;
    }

    
    public String getDamage() {
        return damage;
    }

    
    public void setDamage(String damage) {
        this.damage = damage;
    }

    
    public Integer getVehicleID() {
        return vehicleID;
    }

    
    public void setVehicleID(Integer vehicleID) {
        this.vehicleID = vehicleID;
    }

    
    public Vehicle getVehicle() {
        return vehicle;
    }

    
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    
    public String getWeather() {
        return weather;
    }

    
    public void setWeather(String weather) {
        this.weather = weather;
    }

    
    public String getDescription() {
        return description;
    }

    
    public void setDescription(String description) {
        this.description = description;
    }

    
    public Long getNoteID() {
        return noteID;
    }

    
    public void setNoteID(Long noteID) {
        this.noteID = noteID;
    }

    
    public FullEvent getFullEvent() {
        return fullEvent;
    }

    
    public void setFullEvent(FullEvent fullEvent) {
        this.fullEvent = fullEvent;
    }

    
    public List<VehicleOccupant> getVehicleOccupants() {
        return vehicleOccupants;
    }

    
    public void setVehicleOccupants(List<VehicleOccupant> vehicleOccupants) {
        this.vehicleOccupants = vehicleOccupants;
    }

    
    public List<CrashDataPoint> getCrashDataPoints() {
        return crashDataPoints;
    }

    
    public void setCrashDataPoints(List<CrashDataPoint> crashDataPoints) {
        this.crashDataPoints = crashDataPoints;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((crashReportID == null) ? 0 : crashReportID.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof CrashReport)) {
            return false;
        }
        CrashReport other = (CrashReport) obj;
        if (crashReportID == null) {
            if (other.crashReportID != null) {
                return false;
            }
        }
        else if (!crashReportID.equals(other.crashReportID)) {
            return false;
        }
        return true;
    }

    public void setDriverID(Integer driverID) {
        this.driverID = driverID;
    }

    public Integer getDriverID() {
        return driverID;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Driver getDriver() {
        return driver;
    }
    
    
}
    
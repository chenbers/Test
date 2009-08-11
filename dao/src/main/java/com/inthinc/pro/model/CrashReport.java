package com.inthinc.pro.model;

import java.util.Date;
import java.util.List;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;

public class CrashReport extends BaseEntity {

    /**
     * 
     */
    @Column(updateable=false)
    private static final long serialVersionUID = -2232308206099636851L;

    @ID
    private Integer crashReportID;
    
    @Column(name = "acctID")
    private Integer accountID;
    private Status status;
    private Date date;
    private LatLng latLng;
    private String location;
    private DamageType damageType;
    private Integer vehicleID;
    
    @Column(updateable=false)
    private Vehicle vehicle;
    private String weather;
    private FullEvent event;
    private String description;
    
    @Column(updateable = false)
    private List<VehicleOccupant> vehicleOccupants;

    
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

    
    public Status getStatus() {
        return status;
    }

    
    public void setStatus(Status status) {
        this.status = status;
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

    
    public String getLocation() {
        return location;
    }

    
    public void setLocation(String location) {
        this.location = location;
    }

    
    public DamageType getDamageType() {
        return damageType;
    }

    
    public void setDamageType(DamageType damageType) {
        this.damageType = damageType;
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

    
    public FullEvent getEvent() {
        return event;
    }

    
    public void setEvent(FullEvent event) {
        this.event = event;
    }

    
    public String getDescription() {
        return description;
    }

    
    public void setDescription(String description) {
        this.description = description;
    }

    
    public List<VehicleOccupant> getVehicleOccupants() {
        return vehicleOccupants;
    }

    
    public void setVehicleOccupants(List<VehicleOccupant> vehicleOccupants) {
        this.vehicleOccupants = vehicleOccupants;
    }


    @Override
    public String toString() {
        return "CrashReport [accountID=" + accountID + ", crashReportID=" + crashReportID + ", damageType=" + damageType + ", date=" + date + ", description=" + description
                + ", event=" + event + ", latLng=" + latLng + ", location=" + location + ", status=" + status + ", vehicle=" + vehicle + ", vehicleID=" + vehicleID
                + ", vehicleOccupants=" + vehicleOccupants + ", weather=" + weather + "]";
    }
    
    
    
    
}

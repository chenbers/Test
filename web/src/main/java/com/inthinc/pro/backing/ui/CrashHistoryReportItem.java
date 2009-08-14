package com.inthinc.pro.backing.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.util.MessageUtil;

public class CrashHistoryReportItem implements Comparable<CrashHistoryReportItem> {
    private static final Logger logger = Logger.getLogger(CrashHistoryReportItem.class);
    private String group;
    private String driverName;
    private String vehicleName;
    private String date;
    private String nbrOccupants;
    private String status;
    
    private Long time;    
    private Integer forgiven;
    private String detail;
    
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    private Double latitude;
    private Double longitude;
    private LatLng latlng;
    
    private Driver driver;    
    private Vehicle vehicle;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNbrOccupants() {
        return nbrOccupants;
    }

    public void setNbrOccupants(String nbrOccupants) {
        this.nbrOccupants = nbrOccupants;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public LatLng getLatLng()
    {
        return new LatLng(latitude, longitude);
    }    

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getForgiven() {
        return forgiven;
    }

    public void setForgiven(Integer forgiven) {
        this.forgiven = forgiven;
    }

    @Override
    public int compareTo(CrashHistoryReportItem o) {
        return 0;
        // return this.getEvent().getTime().compareTo(o.getEvent().getTime());
    }
}

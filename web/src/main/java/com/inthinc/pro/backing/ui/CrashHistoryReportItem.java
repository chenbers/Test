package com.inthinc.pro.backing.ui;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Vehicle;

/**
 * @author pwehan
 *
 */
public class CrashHistoryReportItem extends NotificationReportItem<CrashHistoryReportItem>  {
    private static final Logger logger = Logger.getLogger(CrashHistoryReportItem.class);
    
    private Integer crashReportID;
    
    private String nbrOccupants;
    private String status;
    
    private Long time;    
    private Integer forgiven;
    private Double latitude;
    private Double longitude;
//    private LatLng latlng;
    
    private Driver driver;    
    private Vehicle vehicle;
    
    private String weather;

    public Integer getCrashReportID() {
        return crashReportID;
    }

    public void setCrashReportID(Integer crashReportID) {
        this.crashReportID = crashReportID;
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

    public Integer getForgiven() {
        return forgiven;
    }

    public void setForgiven(Integer forgiven) {
        this.forgiven = forgiven;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    @Override
    public int compareTo(CrashHistoryReportItem o) {
        return this.getTime().compareTo(o.getTime());
    }
}

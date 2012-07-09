package com.inthinc.device.models;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inthinc.device.emulation.notes.DeviceNote;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.pro.automation.models.BaseEntity;
import com.inthinc.pro.automation.objects.AutomationCalendar;

@XmlRootElement
public class Trip extends BaseEntity {
    /**
     * 
     */
    private static final long serialVersionUID = -6030725471494521455L;
    private Long tripID;
    private Integer vehicleID;
    private Integer driverID;
    private final AutomationCalendar startTime = new AutomationCalendar();
    private final AutomationCalendar endTime = new AutomationCalendar();
    private Integer mileage;
    private List<DeviceNote> route;

    private String startAddressStr;
    private String endAddressStr;

    private List<DeviceNote> events;

    private TripStatus status;

    private TripQuality quality;

    public Trip() {
        super();
    }

    @JsonProperty("endTime")
    public String getEndTimeString(){
        return endTime.toString();
    }
    
    public AutomationCalendar getEndTime() {
        return endTime;
    }
    
    @JsonProperty("endTime")
    public void setEndTime(String endTime){
        this.endTime.setDate(endTime);
    }

    public void setEndTime(AutomationCalendar endTime) {
        this.endTime.setDate(endTime);
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public List<DeviceNote> getRoute() {
        return route;
    }

    public void setRoute(List<DeviceNote> route) {
        this.route = route;
    }

    @JsonProperty("startTime")
    public String getStartTimeString(){
        return startTime.toString();
    }
    
    public AutomationCalendar getStartTime() {
        return startTime;
    }

    @JsonProperty("startTime")
    public void setStartTime(String startTime){
        this.startTime.setDate(startTime);
    }
    
    
    public void setStartTime(AutomationCalendar startTime) {
        this.startTime.setDate(startTime);
    }

    public Integer getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(Integer vehicleID) {
        this.vehicleID = vehicleID;
    }

    public String getEndAddressStr() {
        return endAddressStr;
    }

    public void setEndAddressStr(String endAddressStr) {
        this.endAddressStr = endAddressStr;
    }

    public String getStartAddressStr() {
        return startAddressStr;
    }

    public void setStartAddressStr(String startAddressStr) {
        this.startAddressStr = startAddressStr;
    }

    public GeoPoint getStartLoc() {
        if (hasGoodRoute()) {
            return route.get(0).getLocation();
        }
        return null;
    }

    public void setStartLoc(GeoPoint startLoc) {
        // Just to keep Jackson marshallling happy
    }

    public GeoPoint getEndLoc() {
        if (hasGoodRoute()) {
            return route.get(route.size() - 1).getLocation();
        }
        return null;
    }

    public void setEndLoc(GeoPoint endLoc) {
        // Just to keep Jackson marshallling happy
    }

    public boolean hasGoodRoute() {
        return route != null && route.size() > 1;
    }

    public int compareTo(Trip trip) {
        return trip.getStartTime().compareTo(getStartTime());
    }

    public List<DeviceNote> getEvents() {
        return events;
    }

    public void setEvents(List<DeviceNote> events) {
        this.events = events;
    }

    public Long getTripID() {
        if (startTime != null) {
            return startTime.toInt() * 1l;
        }
        return tripID;
    }

    public void setTripID(Long tripID) {
        this.tripID = tripID;
    }

    public Integer getDriverID() {
        return driverID;
    }

    public void setDriverID(Integer driverID) {
        this.driverID = driverID;
    }

    public TripStatus getStatus() {
        return status;
    }

    public void setStatus(TripStatus status) {
        this.status = status;
    }

    public TripQuality getQuality() {
        if (quality == null)
            return TripQuality.UNKNOWN;
        return quality;
    }

    public void setQuality(TripQuality quality) {
        this.quality = quality;
    }

    public String toString() {
        return "Trip [driverID=" + driverID + ", vehicleID=" + vehicleID + ", startTime=" + startTime + ", endTime=" + endTime + ", mileage=" + mileage + ", locations Count=" + route.size()
                + "route=" + route + "]";
    }

}

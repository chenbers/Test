package com.inthinc.pro.model;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;
import com.inthinc.pro.model.event.Event;

@XmlRootElement
public class Trip extends BaseEntity {
    @ID
    private Long tripID;
    private Integer vehicleID;
    private Integer driverID;
    private Date startTime;
    private Date endTime;
    private Integer mileage;
    @Column(name = "route", type = com.inthinc.pro.model.LatLng.class, updateable = false)
    private List<LatLng> route;

    private String startAddressStr;
    private String endAddressStr;

    @Column(name = "events", type = com.inthinc.pro.model.event.Event.class, updateable = false)
    private List<Event> events;

    private TripStatus status;

    @Column(updateable = false)
    private TripQuality quality;

    public Trip() {
        super();
    }

    public Trip(Long tripID, Integer vehicleID, Date startTime, Date endTime, Integer mileage, List<LatLng> route, String startAddressStr, String endAddressStr) {
        super();
        this.tripID = tripID;
        this.vehicleID = vehicleID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.mileage = mileage;
        this.route = route;
        this.startAddressStr = startAddressStr;
        this.endAddressStr = endAddressStr;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public List<LatLng> getRoute() {
        return route;
    }

    public void setRoute(List<LatLng> route) {
        this.route = route;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
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

    public LatLng getStartLoc() {
        if (hasGoodRoute()) {
            return route.get(0);
        }
        return null;
    }
    public void setStartLoc(LatLng startLoc){
        //Just to keep Jackson marshallling happy
    }
    public LatLng getEndLoc() {
        if (hasGoodRoute()) {
            return route.get(route.size() - 1);
        }
        return null;
    }
    public void setEndLoc(LatLng endLoc){
        //Just to keep Jackson marshallling happy
    }
    public boolean hasGoodRoute(){
        return route != null && route.size() > 1;
    }
    public int compareTo(Trip trip) {
        return trip.getStartTime().compareTo(getStartTime());
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

//    public boolean isEventsExist() {
//        return events != null && events.size() > 0;
//    }

    public Long getTripID() {
        if (startTime != null) {
            return startTime.getTime();
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
}

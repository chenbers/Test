package com.inthinc.pro.model;

import java.util.List;

import com.inthinc.pro.dao.annotations.ID;

public class Trip extends BaseEntity
{
    @ID
    private Integer tripID;
    private Integer vehicleID;
    private Integer startTime;
    private Integer endTime;
    private Integer mileage;
    private List<LatLng> route;
    
    private String startAddressStr;
    private String endAddressStr;
    private List<Event> events;

    public Trip()
    {
        super();
    }
    public Trip(Integer tripID, Integer vehicleID, Integer startTime, Integer endTime, Integer mileage, List<LatLng> route, String startAddressStr, String endAddressStr)
    {
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
    public Integer getEndTime()
    {
        return endTime;
    }
    public void setEndTime(Integer endTime)
    {
        this.endTime = endTime;
    }
    public Integer getMileage()
    {
        return mileage;
    }
    public void setMileage(Integer mileage)
    {
        this.mileage = mileage;
    }
    public List<LatLng> getRoute()
    {
        return route;
    }
    public void setRoute(List<LatLng> route)
    {
        this.route = route;
    }
    public Integer getStartTime()
    {
        return startTime;
    }
    public void setStartTime(Integer startTime)
    {
        this.startTime = startTime;
    }
    public Integer getVehicleID()
    {
        return vehicleID;
    }
    public void setVehicleID(Integer vehicleID)
    {
        this.vehicleID = vehicleID;
    }
    
    public String getEndAddressStr()
    {
        return endAddressStr;
    }
    public void setEndAddressStr(String endAddressStr)
    {
        this.endAddressStr = endAddressStr;
    }
    public String getStartAddressStr()
    {
        return startAddressStr;
    }
    public void setStartAddressStr(String startAddressStr)
    {
        this.startAddressStr = startAddressStr;
    }
    public LatLng getStartLoc()
    {
        if (route != null && route.size() > 0)
        {
            return route.get(0);
        }
        return null;
    }
    public LatLng getEndLoc()
    {
        if (route != null && route.size() > 0)
        {
            return route.get(route.size()-1);
        }
        return null;
    }
    public int compareTo(Trip trip)
    {
        return trip.getStartTime()-getStartTime();
    }
    public List<Event> getEvents()
    {
        return events;
    }
    public void setEvents(List<Event> events)
    {
        this.events = events;
    }
    
    public boolean isEventsExist()
    {
        return events != null && events.size() > 0;
    }
    public Integer getTripID()
    {
        return tripID;
    }
    public void setTripID(Integer tripID)
    {
        this.tripID = tripID;
    }

}

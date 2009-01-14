package com.inthinc.pro.model;

import java.util.Date;

import com.inthinc.pro.dao.annotations.Column;

public class ZoneArrivalEvent extends Event
{

    private static final long serialVersionUID = 1L;
    
    private Integer zoneID;

    @Column(updateable=false)
    private transient String zoneName;
        
    public ZoneArrivalEvent()
    {
        super();

    }

    public ZoneArrivalEvent(Long noteID, Integer vehicleID, Integer type, Date time, Integer speed,
            Integer odometer, Double latitude, Double longitude, Integer zoneID)
    {
        super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
        this.zoneID = zoneID;
   
    }
    
    public EventType getEventType()
    {
        return EventType.ZONES_ARRIVAL;
    }

    public Integer getZoneID()
    {
        return zoneID;
    }

    public void setZoneID(Integer zoneID)
    {
        this.zoneID = zoneID;
    }

    public String getZoneName()
    {
        return zoneName;
    }

    public void setZoneName(String zoneName)
    {
        this.zoneName = zoneName;
    }
    
    public EventCategory getEventCategory()
    {
        return EventCategory.DRIVER;
    }
}

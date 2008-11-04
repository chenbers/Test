package com.inthinc.pro.model;

public class ZoneDepartureEvent extends Event
{

    private static final long serialVersionUID = 1L;
    
    private Integer zoneID;
    private transient String zoneName;
    

    public ZoneDepartureEvent()
    {
        super();
    }

    public ZoneDepartureEvent(Long noteID, Integer vehicleID, Integer type, Integer time, Integer speed,
            Integer odometer, Double latitude, Double longitude, Integer zoneID)
    {
        super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
        this.zoneID = zoneID;


    }

    public EventType getEventType()
    {
        return EventType.ZONES_DEPARTURE;
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
}

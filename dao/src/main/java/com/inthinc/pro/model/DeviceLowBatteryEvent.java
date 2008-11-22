package com.inthinc.pro.model;

import java.util.Date;

public class DeviceLowBatteryEvent extends Event
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public DeviceLowBatteryEvent()
    {
        super();
    }
    
    public DeviceLowBatteryEvent(Long noteID, Integer vehicleID, Integer type, Date time, Integer speed, Integer odometer, Double latitude, Double longitude)
    {
        super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
    }
    
    public EventType getEventType()
    {
        return EventType.DEVICE_LOW_BATTERY;
    }   
    
    public EventCategory getEventCategory()
    {
        return EventCategory.WARNING;
    }

}


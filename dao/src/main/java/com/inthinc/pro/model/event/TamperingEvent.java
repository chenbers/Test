package com.inthinc.pro.model.event;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TamperingEvent extends Event{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public TamperingEvent()
    {
        super();
    }
    
    public TamperingEvent(Long noteID, Integer vehicleID, Integer type, Date time, Integer speed, Integer odometer, Double latitude, Double longitude)
    {
        super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
    }
    
    public EventType getEventType()
    {
        return EventType.TAMPERING;
    }   
    
    public EventCategory getEventCategory()
    {
        return EventCategory.WARNING;
    }
    
}

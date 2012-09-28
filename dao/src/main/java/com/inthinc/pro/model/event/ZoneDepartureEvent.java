package com.inthinc.pro.model.event;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ZoneDepartureEvent extends ZoneEvent
{

    private static final long serialVersionUID = 1L;
    
    public ZoneDepartureEvent()
    {
        super();
    }

    public ZoneDepartureEvent(Long noteID, Integer vehicleID, NoteType type, Date time, Integer speed,
            Integer odometer, Double latitude, Double longitude, Integer zoneID)
    {
        super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude, zoneID);


    }

    public EventType getEventType()
    {
        return EventType.ZONES_DEPARTURE;
    }
    
}

package com.inthinc.pro.model.event;

import java.util.Date;

public class ZonesVersionEvent extends VersionEvent {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ZonesVersionEvent()
    {
        super();
    }
    public ZonesVersionEvent(Long noteID, Integer vehicleID, NoteType type, Date time, Integer speed,
            Integer odometer, Double latitude, Double longitude, VersionState status)
    {
        super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude, status);
    }

    
    @Override
    public EventType getEventType()
    {
        return EventType.ZONES_CURRENT;
        
    }

}

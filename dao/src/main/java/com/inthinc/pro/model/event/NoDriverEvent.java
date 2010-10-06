package com.inthinc.pro.model.event;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NoDriverEvent extends Event{

	private static final long serialVersionUID = 1L;

	public NoDriverEvent() {
		super();
	}
	
    public NoDriverEvent(Long noteID, Integer vehicleID, NoteType type, Date time, Integer speed, Integer odometer, Double latitude, Double longitude)
    {
        super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
    }

    public EventType getEventType()
    {
        return EventType.NO_DRIVER;
    }
    public EventCategory getEventCategory()
    {
        return EventCategory.NO_DRIVER;
    }

}

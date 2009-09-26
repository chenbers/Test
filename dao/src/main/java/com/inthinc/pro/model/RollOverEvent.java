package com.inthinc.pro.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RollOverEvent extends Event
{
    private static final long serialVersionUID = 1L;
    	
	public RollOverEvent()
	{
		super();
	}
	public RollOverEvent(Long noteID, Integer vehicleID, Integer type, Date time, Integer speed, Integer odometer, Double latitude, Double longitude)
	{
		super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
	}
	
    public EventType getEventType()
	{
		return EventType.ROLLOVER;
	}
    public EventCategory getEventCategory()
    {
        return EventCategory.EMERGENCY;
    }

}

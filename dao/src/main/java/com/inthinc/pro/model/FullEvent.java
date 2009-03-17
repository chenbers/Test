package com.inthinc.pro.model;

import java.util.Date;

public class FullEvent extends Event
{
    private static final long serialVersionUID = 1L;
    	
	public FullEvent()
	{
		super();
	}
	public FullEvent(Long noteID, Integer vehicleID, Integer type, Date time, Integer speed, Integer odometer, Double latitude, Double longitude)
	{
		super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
	}
	
    public EventType getEventType()
	{
		return EventType.CRASH;
	}
    public EventCategory getEventCategory()
    {
        return EventCategory.DRIVER;
    }
    public String getDetails(String formatStr)
    {
        return formatStr;
    }

}

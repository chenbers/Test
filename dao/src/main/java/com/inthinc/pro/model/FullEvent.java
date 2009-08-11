package com.inthinc.pro.model;

import java.util.Date;

public class FullEvent extends Event
{
    private static final long serialVersionUID = 1L;
    
    private Integer deltaX; // deltas store as Integer, divide by 10 for float value
    private Integer deltaY;
    private Integer deltaZ;
    
    private Integer speedLimit;
    	
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
        return EventCategory.EMERGENCY;
    }
    public String getDetails(String formatStr)
    {
        return formatStr;
    }
    
    public Integer getDeltaX() {
        return deltaX;
    }
    
    public void setDeltaX(Integer deltaX) {
        this.deltaX = deltaX;
    }
    
    public Integer getDeltaY() {
        return deltaY;
    }
    
    public void setDeltaY(Integer deltaY) {
        this.deltaY = deltaY;
    }
    
    public Integer getDeltaZ() {
        return deltaZ;
    }
    
    public void setDeltaZ(Integer deltaZ) {
        this.deltaZ = deltaZ;
    }
    
    public Integer getSpeedLimit() {
        return speedLimit;
    }
    
    public void setSpeedLimit(Integer speedLimit) {
        this.speedLimit = speedLimit;
    }
    
    

}

package com.inthinc.pro.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.inthinc.pro.dao.util.DateUtil;

public class IdleEvent extends Event
{
    private static final long serialVersionUID = 1L;
    
    /**
     * lowIdle - The duration in seconds the vehicle was idling with RPM < 1000
     * highIdle - The duration in seconds the vehicle was idling with RPM > 1000
     */
    private Integer lowIdle;
    private Integer highIdle;
	
	public IdleEvent()
	{
		super();
	}
	public IdleEvent(Long noteID, Integer vehicleID, Integer type, Date time, Integer speed, Integer odometer, Double latitude, Double longitude,
						Integer lowIdle, Integer highIdle)
	{
		super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
		this.lowIdle = lowIdle;
		this.highIdle = highIdle;
		
	}
	
	public String getHighIdleDuration()
	{
	   return DateUtil.getDurationFromSeconds(this.highIdle);
	}
	
	public Integer getLowIdle()
    {
        return lowIdle;
    }
    public void setLowIdle(Integer lowIdle)
    {
        this.lowIdle = lowIdle;
    }
    public Integer getHighIdle()
    {
        return highIdle;
    }
    public void setHighIdle(Integer highIdle)
    {
        this.highIdle = highIdle;
    }
    public EventType getEventType()
	{
		return EventType.IDLE;
	}
    public EventCategory getEventCategory()
    {
        return EventCategory.VIOLATION;
    }

}

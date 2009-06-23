package com.inthinc.pro.model;

import java.text.DateFormat;
import java.text.MessageFormat;
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
    private Integer lowIdle = 0;
    private Integer highIdle = 0;
	
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
	   if(this.highIdle != null)
	       return DateUtil.getDurationFromSeconds(this.highIdle);
	   else
	       return "";
	}
	
   public String getLowIdleDuration()
    {
       if(this.lowIdle != null)
           return DateUtil.getDurationFromSeconds(this.lowIdle);
       else
           return "";
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
		return EventType.IDLING;
	}
    public EventCategory getEventCategory()
    {
        return EventCategory.WARNING;
    }
    
    @Override
    public String getDetails(String formatStr,MeasurementType measurementType,String mphString)
    {
        return MessageFormat.format(formatStr, new Object[] {getLowIdle() == null ? 0 : getLowIdle(), getHighIdle() == null ? 0 : getHighIdle()});
    }

}

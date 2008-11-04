package com.inthinc.pro.model;

import java.util.Date;

public class SpeedingEvent extends Event
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer topSpeed;
	private Integer avgSpeed;
	private Integer speedLimit;
	private Integer distance;
    private Integer avgRPM;
	
	public SpeedingEvent()
	{
		super();
	}
	public SpeedingEvent(Long noteID, Integer vehicleID, Integer type, Date time, Integer speed, Integer odometer, Double latitude, Double longitude,
						Integer topSpeed, Integer avgSpeed, Integer speedLimit, Integer distance, Integer avgRPM)
	{
		super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
		this.topSpeed = topSpeed;
		this.avgSpeed = avgSpeed;
		this.speedLimit = speedLimit;
		this.distance = distance;
        this.avgRPM = avgRPM;
	}
	public Integer getAvgSpeed()
	{
		return avgSpeed;
	}
	public void setAvgSpeed(Integer avgSpeed)
	{
		this.avgSpeed = avgSpeed;
	}
	public Integer getDistance()
	{
		return distance;
	}
	public void setDistance(Integer distance)
	{
		this.distance = distance;
	}
	public Integer getSpeedLimit()
	{
		return speedLimit;
	}
	public void setSpeedLimit(Integer speedLimit)
	{
		this.speedLimit = speedLimit;
	}
	public Integer getTopSpeed()
	{
		return topSpeed;
	}
	public void setTopSpeed(Integer topSpeed)
	{
		this.topSpeed = topSpeed;
	}
	public EventType getEventType()
	{
		return EventType.SPEEDING;
	}
    public Integer getAvgRPM()
    {
        return avgRPM;
    }
    public void setAvgRPM(Integer avgRPM)
    {
        this.avgRPM = avgRPM;
    }

}

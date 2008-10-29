package com.inthinc.pro.model;

public class SeatBeltEvent extends Event
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer avgSpeed;
	private Integer topSpeed;
	private Integer distance;
	public SeatBeltEvent()
	{
		super();
	}
	public SeatBeltEvent(Long noteID, Integer vehicleID, Integer type, Integer time, Integer speed, Integer odometer, Double latitude, Double longitude,
						Integer avgSpeed, Integer topSpeed, Integer distance)
	{
		super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
		this.avgSpeed = avgSpeed;
		this.topSpeed = topSpeed;
		this.distance = distance;
		
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
		return EventType.SEATBELT;
	}

}

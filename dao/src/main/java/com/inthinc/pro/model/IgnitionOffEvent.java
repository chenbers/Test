package com.inthinc.pro.model;

import java.util.Date;

public class IgnitionOffEvent extends Event
{
    private static final long serialVersionUID = 1L;
    private Integer mpg = 0;		// units of 1/100 of a mile per gal
    
	public IgnitionOffEvent()
	{
		super();
	}
	public IgnitionOffEvent(Long noteID, Integer vehicleID, Integer type, Date time, Integer speed, Integer odometer, Double latitude, Double longitude,
						Integer mpg)
	{
		super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
		this.mpg = mpg;
		
	}
	public Integer getMpg() {
		return mpg;
	}
	public void setMpg(Integer mpg) {
		this.mpg = mpg;
	}

    
    

}

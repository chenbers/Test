package com.inthinc.pro.model.event;

import java.util.Date;

public class IgnitionOffEvent extends Event
{
    private static final long serialVersionUID = 1L;
    private Integer mpg = 0;		// units of 1/100 of a mile per gal
    private Integer mpgDistance;		// units of 1/100 of a mile per gal
	private Integer driveTime;			// units are 1/100 of a mile
    
	public IgnitionOffEvent()
	{
		super();
	}
	public IgnitionOffEvent(Long noteID, Integer vehicleID, NoteType type, Date time, Integer speed, Integer odometer, Double latitude, Double longitude,
						Integer mpg, Integer mpgDistance, Integer driveTime)
	{
		super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
		this.mpg = mpg;
		this.mpgDistance = mpgDistance;
		this.driveTime = driveTime;
		
	}
	public Integer getMpg() {
		return mpg;
	}
	public void setMpg(Integer mpg) {
		this.mpg = mpg;
	}
    public Integer getMpgDistance() {
		return mpgDistance;
	}
	public void setMpgDistance(Integer mpgDistance) {
		this.mpgDistance = mpgDistance;
	}

	public Integer getDriveTime() {
		return driveTime;
	}
	public void setDriveTime(Integer driveTime) {
		this.driveTime = driveTime;
	}
    
    

}

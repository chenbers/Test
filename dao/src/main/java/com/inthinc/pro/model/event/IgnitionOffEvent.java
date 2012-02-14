package com.inthinc.pro.model.event;

import java.util.Date;

import com.inthinc.pro.dao.annotations.event.EventAttrID;

public class IgnitionOffEvent extends Event
{
    private static final long serialVersionUID = 1L;
    @EventAttrID(name="MPG")
    private Integer mpg = 0;		// units of 1/100 of a mile per gal
    @EventAttrID(name="MPG_DISTANCE")
    private Integer mpgDistance;		// units of 1/100 of a mile per gal
    @EventAttrID(name="TRIP_DURATION")
	private Integer driveTime;			// units are 1/100 of a mile
    @EventAttrID(name="PERCENTAGE_OF_POINTS_THAT_PASSED_THE_FILTER_")
    private Integer gpsQuality; 
    
    //   * Attributes [ATTR_MPG], [ATTR_MPG_DISTANCE], [ATTR_TRIP_DURATION], ATTR_PERCENTAGE_GPS_FILTERED, [ATTR_SPEEDING_SQUELCHED], ATTR_CURRENT_IGN, [ATTR_NUM_GPS_REBOOTS], [ATTR_OBD_PCT], [ATTR_GPS_PCT], [ATTR_AGPS_DOWNLOADED], [ATTR_VIOLATION_FLAGS]
    private static EventAttr[] eventAttrList = {
        EventAttr.MPG,
        EventAttr.MPG_DISTANCE,
        EventAttr.TRIP_DURATION,
        EventAttr.PERCENTAGE_OF_POINTS_THAT_PASSED_THE_FILTER_,
        EventAttr.SPEED_COLLECTED, // WRONG ?
        EventAttr.CURRENT_IGN
    };
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
    public Integer getGpsQuality() {
        return gpsQuality;
    }
    public void setGpsQuality(Integer gpsQuality) {
        this.gpsQuality = gpsQuality;
    }
    @Override
    public EventAttr[] getEventAttrList() {
        return eventAttrList;
    }

    

}

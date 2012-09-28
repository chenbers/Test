package com.inthinc.pro.model.event;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.dao.annotations.event.EventAttrID;

@XmlRootElement
public class FullEvent extends Event
{
    private static final long serialVersionUID = 1L;
    
    @EventAttrID(name="DELTAV_X")
    private Integer deltaX; // deltas store as Integer, divide by 10 for float value
    @EventAttrID(name="DELTAV_Y")
    private Integer deltaY;
    @EventAttrID(name="DELTAV_Z")
    private Integer deltaZ;
    	
    private static EventAttr[] eventAttrList = {
        EventAttr.DELTA_VS
    };
    
	public FullEvent()
	{
		super();
	}
	public FullEvent(Long noteID, Integer vehicleID, NoteType type, Date time, Integer speed, Integer odometer, Double latitude, Double longitude)
	{
		super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
	}
	public FullEvent(Long noteID, Integer vehicleID, NoteType type, Date time, Integer speed, Integer odometer, Double latitude, Double longitude,
		    	Integer deltaX, Integer deltaY, Integer deltaZ, Integer speedLimit)
	{
		super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
		this.deltaX = deltaX;
		this.deltaY = deltaY;
		this.deltaZ = deltaZ;
		setSpeedLimit(speedLimit);
	}
	
    public EventType getEventType()
	{
		return EventType.CRASH;
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
    
    @Override
    public EventAttr[] getEventAttrList() {
        return eventAttrList;
    }

}

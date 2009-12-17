package com.inthinc.pro.model;

import java.text.MessageFormat;
import java.util.Date;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.util.MeasurementConversionUtil;

public abstract class ZoneEvent extends Event {

	/**
	 * 
	 */
	private static final long serialVersionUID = -221662750228535423L;
	
	protected Integer zoneID;
	@Column(updateable = false)
	private transient String zoneName;

	public ZoneEvent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ZoneEvent(Long noteID, Integer vehicleID, Integer type, Date time,
			Integer speed, Integer odometer, Double latitude, Double longitude, Integer zoneID) {
		super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
        this.zoneID = zoneID;
	}

	public Integer getZoneID() {
	    return zoneID;
	}

	public void setZoneID(Integer zoneID) {
	    this.zoneID = zoneID;
	}

	public String getZoneName() {
	    return zoneName;
	}

	public void setZoneName(String zoneName) {
	    this.zoneName = zoneName;
	}

	public EventCategory getEventCategory() {
	    return EventCategory.DRIVER;
	}

	@Override
    public String getDetails(String formatStr,MeasurementType measurementType,String mphString)
    {
        return MessageFormat.format(formatStr, new Object[] {zoneName == null ? "" : zoneName});
    }

}

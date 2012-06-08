package com.inthinc.pro.model.event;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.event.EventAttrID;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.MeasurementType;

public abstract class ZoneEvent extends Event {

	/**
	 * 
	 */
	private static final long serialVersionUID = -221662750228535423L;
	
    @EventAttrID(name="ZONE_ID")
	protected Integer zoneID;
	
	@Column(updateable = false)
	private String zoneName;
	
	private List<LatLng> zonePoints;
	
    private static EventAttr[] eventAttrList = {
        EventAttr.ZONE_ID
    };
    
    @Override
    public EventAttr[] getEventAttrList() {
        return eventAttrList;
    }

	

	public ZoneEvent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ZoneEvent(Long noteID, Integer vehicleID, NoteType type, Date time,
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

	@Override
	public String getZoneName() {
	    return zoneName;
	}

	public void setZoneName(String zoneName) {
	    this.zoneName = zoneName;
	}

	@Override
    public String getDetails(String formatStr,MeasurementType measurementType,String... mphString)
    {
        return MessageFormat.format(formatStr, new Object[] {zoneName == null ? "" : zoneName});
    }

	public List<LatLng> getZonePoints() {
		return zonePoints;
	}

	public void setZonePoints(List<LatLng> zonePoints) {
		this.zonePoints = zonePoints;
	}

	
	@Override
	public String getZonePointsStr() {
        StringBuilder sb = new StringBuilder();
        if (zonePoints != null)
            for (final LatLng point : zonePoints)
            {
                if (sb.length() > 0)
                    sb.append(';');
                sb.append(point.latLngStr());
            }
        return sb.toString();

	}

}

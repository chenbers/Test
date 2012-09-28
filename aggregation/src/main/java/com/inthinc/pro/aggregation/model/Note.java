package com.inthinc.pro.aggregation.model;

import java.util.Date;
import java.util.Properties;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Note
{
	
	private static Logger logger = LoggerFactory.getLogger(Note.class.getName());
	public final static int TYPE_NEWDRIVER = 7;
	public final static int TYPE_NEWDRIVER_HOSRULE = 116;
	public final static int TYPE_IGNITION_ON = 19;
	public final static int TYPE_CLEAR_DRIVER = 66;
	public final static int TYPE_IGNITION_OFF = 20;
	public final static int TYPE_LOW_BATTERY = 22;
	public final static int TYPE_HOS_CHANGE_STATE_EX = 96;
	public final static int TYPE_HOS_CHANGE_STATE_NO_GPS_LOCK = 113;
	public final static int TYPE_FUEL_STOP = 73; 
	public final static int TYPE_FUEL_STOP_EX = 166;
	public final static int TYPE_IDLING = 140;
	public final static int TYPE_IDLING2 = 208;
	public final static int TYPE_RFKILL = 219;
	
    private Long noteID;
    private Long driverID;
    private Long vehicleID;
    private Long groupID;
    private Long deviceID;

	/* data from the note */
    private Integer type;
	private Date time;
	private Integer flags;
	private Integer mapRev;
	private float longitude;
	private float latitude;
	private Integer speed;
	private Long odometer;
	private Integer state;
	private Integer topSpeed;
	private Integer avgSpeed;
	private Integer speedLimit;
	private Integer distance;
	private Integer deltaX;
	private Integer deltaY;
	private Integer deltaZ;
	private Properties attributes;
    
    public Note()
    {
        super();
        this.attributes = new Properties();
    }

	public Long getNoteID() {
		return noteID;
	}

	public void setNoteID(Long noteID) {
		this.noteID = noteID;
	}

	public Long getDriverID() {
		return driverID;
	}

	public void setDriverID(Long driverID) {
		this.driverID = driverID;
	}

	public Long getVehicleID() {
		return vehicleID;
	}

	public void setVehicleID(Long vehicleID) {
		this.vehicleID = vehicleID;
	}

	public Long getGroupID() {
		return groupID;
	}

	public void setGroupID(Long groupID) {
		this.groupID = groupID;
	}

	public Long getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(Long deviceID) {
		this.deviceID = deviceID;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Integer getFlags() {
		return flags;
	}

	public void setFlags(Integer flags) {
		this.flags = flags;
	}

	public Integer getMapRev() {
		return mapRev;
	}

	public void setMapRev(Integer mapRev) {
		this.mapRev = mapRev;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public Long getOdometer() {
		return odometer;
	}

	public void setOdometer(Long odometer) {
		this.odometer = odometer;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getTopSpeed() {
		return topSpeed;
	}

	public void setTopSpeed(Integer topSpeed) {
		this.topSpeed = topSpeed;
	}

	public Integer getAvgSpeed() {
		return avgSpeed;
	}

	public void setAvgSpeed(Integer avgSpeed) {
		this.avgSpeed = avgSpeed;
	}

	public Integer getSpeedLimit() {
		return speedLimit;
	}

	public void setSpeedLimit(Integer speedLimit) {
		this.speedLimit = speedLimit;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
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

	public Properties getAttributes() {
		return attributes;
	}

	public void setAttributes(Properties attributes) {
		this.attributes = attributes;
	}

	public void setAttributes(String strAttribs) {
		strAttribs = strAttribs.replace(';', '\n');
		
		this.attributes = new Properties();
		
		try {
			this.attributes.load(new ByteArrayInputStream(strAttribs.getBytes()));
		} catch (IOException e) {}
	}

	public String getAttribute(String key) {
		return (String) attributes.getProperty(key);
	}

	public int getAttributeAsInt(String key) {
		int intVal = 0;
		String strVal =  (String) attributes.getProperty(key);
		if (strVal != null)
		{
			try {
				intVal = Integer.parseInt(strVal);
			} catch (NumberFormatException e)
			{}
			
		}
		return intVal;
	}

	public boolean isStartNote()
	{
		return (type == TYPE_NEWDRIVER || type == TYPE_NEWDRIVER_HOSRULE || type == TYPE_IGNITION_ON);
	}
	
	public boolean isEndNote()
	{
		return (type == TYPE_CLEAR_DRIVER || type == TYPE_IGNITION_OFF 
				|| type == TYPE_LOW_BATTERY || type == TYPE_HOS_CHANGE_STATE_EX 
				|| type == TYPE_HOS_CHANGE_STATE_NO_GPS_LOCK);

	}

	public static boolean isStartNote(int type)
	{
		return (type == TYPE_NEWDRIVER || type == TYPE_NEWDRIVER_HOSRULE || type == TYPE_IGNITION_ON);
	}
	
	public static boolean isEndNote(int type)
	{
		return (type == TYPE_CLEAR_DRIVER || type == TYPE_IGNITION_OFF 
				|| type == TYPE_LOW_BATTERY || type == TYPE_HOS_CHANGE_STATE_EX 
				|| type == TYPE_HOS_CHANGE_STATE_NO_GPS_LOCK);

	}

}

package com.inthinc.pro.automation.models;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inthinc.pro.automation.enums.WebDateFormat;
import com.inthinc.pro.automation.objects.AutomationCalendar;

@XmlRootElement
public class Event extends BaseEntity implements Comparable<Event>, Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long noteID;

    private Integer forgiven = 0;
    private Integer flags;
    private Double latitude;

    private Double longitude;

    private Integer maprev;
    private Integer odometer;
    private Integer speed;
    private final AutomationCalendar time = new AutomationCalendar(WebDateFormat.RALLY_DATE_FORMAT);
    private String type;
    private String eventType;
    private String eventCategory;

    private Vehicle vehicle;

    private Integer vehicleID;

    private Driver driver;

    private Integer driverID;
    
    //The driverID that the device has and sent back in the attrMap as {227 = {deviceDriverID}}
    private Integer deviceDriverID;

    private String addressStr;

    private Integer groupID;
    private Integer state;
    private Integer heading;
    private Integer sats;

    private String formattedTime;

    // new fields added for pagination
    private String driverName;
    private String vehicleName;
    private String groupName;
    private TimeZone driverTimeZone;

    private Integer deviceID;
    private Integer speedLimit;
    private AttrMap attrMap;
     
    private String eventTypeString;    
    
    public Event() {
        super();
    }

    public Event(Long noteID, Integer vehicleID, String type, Date time, Integer speed, Integer odometer, Double latitude, Double longitude) {
        super();
        this.noteID = noteID;
        this.vehicleID = vehicleID;
        this.type = type;
        this.time.setDate(time.getTime());
        this.speed = speed;
        this.odometer = odometer;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * The comparable interface is used to order objects in their natural order. The natural order of Events is the order in which they occured.
     */
    public int compareTo(Event o) {
        return getTime().compareTo(o.getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Event))
            return false;

        Event e = (Event) o;
        // should test any significant fields for equality. I believe noteID is the most significant
        // since it is the primary key of the record. I've also added time but if noteID is equal, then
        // it should be implicit that time is equal. I'm considering removing time from this test.
        return noteID.equals(e.noteID) && time.equals(e.time);
    }

    public String getAddressStr() {
        return addressStr;
    }


    public Integer getForgiven() {
        return forgiven;
    }

    public Double getLatitude() {
        return latitude;
    }

    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }

    public Double getLongitude() {
        return longitude;
    }

    public Integer getMaprev() {
        return maprev;
    }

    public Long getNoteID() {
        return noteID;
    }

    public Integer getOdometer() {
        return odometer;
    }

    public Integer getSpeed() {
        return speed;
    }

    @JsonProperty("time")
    public String getTimeString(){
        return time.toString();
    }
    
    public AutomationCalendar getTime() {
        return time;
    }

    public Object getType() {
        return type;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Integer getVehicleID() {
        return vehicleID;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (int) (noteID ^ (noteID >>> 32));
        result = 31 * result + (int) (time.toInt() / 1000);
        return result;
    }

    public void setAddressStr(String addressStr) {
        this.addressStr = addressStr;
    }

    public void setForgiven(Integer forgiven) {
        this.forgiven = forgiven;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setMaprev(Integer maprev) {
        this.maprev = maprev;
    }

    public void setNoteID(Long noteID) {
        this.noteID = noteID;
    }

    public void setOdometer(Integer odometer) {
        this.odometer = odometer;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public void setTime(String time) {
        this.time.setDate(time);
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void setVehicleID(Integer vehicleID) {
        this.vehicleID = vehicleID;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Integer getDriverID() {
        return driverID;
    }

    public void setDriverID(Integer driverID) {
        this.driverID = driverID;
    }
        
    public Integer getDeviceDriverID() {
		return deviceDriverID;
	}

	public void setDeviceDriverID(Integer deviceDriverID) {
		this.deviceDriverID = deviceDriverID;
	}


    /**
     * This method will typically be overridden by a child class. The child class will pass in the appropriate parameters needed for the formatStr.
     * 
     * 
     * @param formatStr
     *            String that will be formatted by one of the child classes. T
     * @param measurementType
     * @return Formatted Message String
     */
    public String getDetails(String formatStr, MeasurementType measurementType, String... strings) {
        return formatStr;
    }

    public Integer getFlags() {
        return flags;
    }

    public void setFlags(Integer flags) {
        this.flags = flags;
    }

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getHeading() {
        return heading;
    }

    public void setHeading(Integer heading) {
        this.heading = heading;
    }

    public Integer getSats() {
        return sats;
    }

    public void setSats(Integer sats) {
        this.sats = sats;
    }

    @Override
    public String toString() {
        return "Event [driverID=" + driverID + ", latitude=" + latitude + ", longitude=" + longitude + ", noteID=" + noteID + ", type=" + type + ", vehicleID=" + vehicleID + "]";
    }

    public boolean isValidEvent() {
        return true;
    }

    public static List<Event> cleanEvents(List<Event> events) {

        Iterator<Event> it = events.iterator();
        while (it.hasNext()) {

            if (!it.next().isValidEvent()) {

                it.remove();
            }
        }
        return events;
    }

    public String getFormattedTime() {
        return formattedTime;
    }

    public void setFormattedTime(String formattedTime) {
        this.formattedTime = formattedTime;
    }

    public String getDriverName() {
        if (driverName == null || driverName.isEmpty())
            return null;
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public TimeZone getDriverTimeZone() {
        return driverTimeZone;
    }

    public void setDriverTimeZone(TimeZone driverTimeZone) {
        this.driverTimeZone = driverTimeZone;
    }

    public String getZoneName() {
        return null;
    }

    public String getZonePointsStr() {
        return null;
    }

    public Integer getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(Integer deviceID) {
        this.deviceID = deviceID;
    }

    public Integer getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(Integer speedLimit) {
        this.speedLimit = speedLimit;
    }

    public AttrMap getAttrMap() {
        return attrMap;
    }

    public void setAttrMap(AttrMap attrMap) {
        this.attrMap = attrMap;
    }

    public boolean isWaysmartOnly() {
        return false;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public String getEventTypeString() {
        return eventTypeString;
    }

    public void setEventTypeString(String eventTypeString) {
        this.eventTypeString = eventTypeString;
    }

}

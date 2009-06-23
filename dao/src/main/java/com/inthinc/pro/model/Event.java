package com.inthinc.pro.model;

import java.io.Serializable;
import java.util.Date;

import com.inthinc.pro.dao.annotations.Column;

public class Event implements Comparable<Event>, Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long noteID;

    private Integer forgiven = 0;
    private Integer flags;
    @Column(name="lat")
    private Double latitude;
    
    @Column(name="lng")
    private Double longitude;
    
    private Integer maprev;
    private Integer odometer;
    private Integer speed;
    private Date time;
    private Integer type;
    
    @Column(updateable = false)
    private Vehicle vehicle;
    
    private Integer vehicleID;

    @Column(updateable = false)
    private Driver driver;
    
    private Integer driverID;

    @Column(updateable = false)
    private String addressStr;
    
    @Column(updateable = false)
    private LatLng latLng;
   
    private Integer groupID;
    private Integer state;
    private Integer heading;
    private Integer sats;
    @Column(updateable=false)
    private Date modified;
    @Column(updateable=false)
    private Date created;

    public Event()
    {
        super();
    }

    public Event(Long noteID, Integer vehicleID, Integer type, Date time, Integer speed, Integer odometer, Double latitude, Double longitude)
    {
        super();
        this.noteID = noteID;
        this.vehicleID = vehicleID;
        this.type = type;
        this.time = time;
        this.speed = speed;
        this.odometer = odometer;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * The comparable interface is used to order objects in their natural order. The natural order of Events is the order in which they occured.
     */
    public int compareTo(Event o)
    {
        return getTime().compareTo(o.getTime());
    }

    @Override
    public boolean equals(Object o)
    {
        if(o == this)
            return true;
        if (!(o instanceof Event))
            return false;

        Event e = (Event) o;
        // should test any significant fields for equality. I believe noteID is the most significant
        // since it is the primary key of the record. I've also added time but if noteID is equal, then
        // it should be implicit that time is equal. I'm considering removing time from this test.
        return noteID.equals(e.noteID) && time.equals(e.time);
    }

    public String getAddressStr()
    {
        return addressStr;
    }

    public EventType getEventType()
    {
        return EventType.UNKNOWN;
    }

    public Integer getForgiven()
    {
        return forgiven;
    }


    public Double getLatitude()
    {
        return latitude;
    }

    public LatLng getLatLng()
    {
        return new LatLng(latitude, longitude);
    }

    public Double getLongitude()
    {
        return longitude;
    }

    public Integer getMaprev()
    {
        return maprev;
    }

    public Long getNoteID()
    {
        return noteID;
    }

    public Integer getOdometer()
    {
        return odometer;
    }


    public Integer getSpeed()
    {
        return speed;
    }

    public Date getTime()
    {
        return time;
    }

    public Integer getType()
    {
        return type;
    }

    public Vehicle getVehicle()
    {
        return vehicle;
    }

    public Integer getVehicleID()
    {
        return vehicleID;
    }

    @Override
    public int hashCode()
    {
        int result = 17;
        result = 31 * result + (int) (noteID ^ (noteID>>>32));
        result = 31 * result + (int)(time.getTime()/1000);
        return result;
    }

    public void setAddressStr(String addressStr)
    {
        this.addressStr = addressStr;
    }


    public void setForgiven(Integer forgiven)
    {
        this.forgiven = forgiven;
    }


    public void setLatitude(Double latitude)
    {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude)
    {
        this.longitude = longitude;
    }

    public void setMaprev(Integer maprev)
    {
        this.maprev = maprev;
    }

    public void setNoteID(Long noteID)
    {
        this.noteID = noteID;
    }

    public void setOdometer(Integer odometer)
    {
        this.odometer = odometer;
    }


    public void setSpeed(Integer speed)
    {
        this.speed = speed;
    }

    public void setTime(Date time)
    {
        this.time = time;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    public void setVehicle(Vehicle vehicle)
    {
        this.vehicle = vehicle;
    }

    public void setVehicleID(Integer vehicleID)
    {
        this.vehicleID = vehicleID;
    }

    public Driver getDriver()
    {
        return driver;
    }

    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }

    public Integer getDriverID()
    {
        return driverID;
    }

    public void setDriverID(Integer driverID)
    {
        this.driverID = driverID;
    }

    public EventCategory getEventCategory()
    {
        return EventCategory.NONE;
    }
    
    /**
     * This method will typically be overridden by a child class. The child class will pass in the appropriate parameters
     * needed for the formatStr. 
     * 
     * 
     * @param formatStr String that will be formatted by one of the child classes. T
     * @param measurementType
     * @return Formatted Message String
     */
    public String getDetails(String formatStr, MeasurementType measurementType,String mphString)
    {
        return formatStr;
    }

    public Integer getFlags()
    {
        return flags;
    }

    public void setFlags(Integer flags)
    {
        this.flags = flags;
    }

    public Integer getGroupID()
    {
        return groupID;
    }

    public void setGroupID(Integer groupID)
    {
        this.groupID = groupID;
    }

    public Integer getState()
    {
        return state;
    }

    public void setState(Integer state)
    {
        this.state = state;
    }

    public Integer getHeading()
    {
        return heading;
    }

    public void setHeading(Integer heading)
    {
        this.heading = heading;
    }

    public Integer getSats()
    {
        return sats;
    }

    public void setSats(Integer sats)
    {
        this.sats = sats;
    }

    public Date getModified()
    {
        return modified;
    }

    public void setModified(Date modified)
    {
        this.modified = modified;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }

}

package com.inthinc.pro.model;

import java.io.Serializable;

public class Event implements Comparable<Event>, Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private transient String addressStr;
    private transient Integer created;
    private Integer forgiven = 0;
    private Integer heading;
    private Double latitude;
    private Double longitude;
    private Integer maprev;
    private Long noteID;
    private Integer odometer;
    private Integer sats;
    private Integer speed;
    private Integer time;
    private Integer type;
    private transient Vehicle vehicle;
    private Integer vehicleID;
    private transient Driver driver;
    private Integer driverID;

    public Event()
    {
        super();
    }

    public Event(Long noteID, Integer vehicleID, Integer type, Integer time, Integer speed, Integer odometer, Double latitude, Double longitude)
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

    public Integer getCreated()
    {
        return created;
    }

    public EventType getEventType()
    {
        return EventType.UNKNOWN;
    }

    public Integer getForgiven()
    {
        return forgiven;
    }

    public Integer getHeading()
    {
        return heading;
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

    public Integer getSats()
    {
        return sats;
    }

    public Integer getSpeed()
    {
        return speed;
    }

    public Integer getTime()
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
        result = 31 * result + time;
        return result;
    }

    public void setAddressStr(String addressStr)
    {
        this.addressStr = addressStr;
    }

    public void setCreated(Integer created)
    {
        this.created = created;
    }

    public void setForgiven(Integer forgiven)
    {
        this.forgiven = forgiven;
    }

    public void setHeading(Integer heading)
    {
        this.heading = heading;
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

    public void setSats(Integer sats)
    {
        this.sats = sats;
    }

    public void setSpeed(Integer speed)
    {
        this.speed = speed;
    }

    public void setTime(Integer time)
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

}

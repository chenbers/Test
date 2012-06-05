package com.inthinc.pro.model;

import java.util.Date;

import com.inthinc.pro.dao.annotations.Column;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DriverLocation
{
    private Driver driver;
    private Vehicle vehicle;
    private LatLng loc;                  // last location of driver
    private Date time;
    private String addressStr;
    private Double dist;
    private Device device;
    private Integer speed;
    private Integer head;
    
    @Column(updateable = false)
    private Integer position;
    
    
    @Column(updateable = false)
    private Group group;


    public DriverLocation()
    {
    }

    public DriverLocation(LastLocation lastLocation, Driver driver, Vehicle vehicle)
    {
    	setHead(lastLocation.getHead());
    	setLoc(lastLocation.getLoc());
    	setSpeed(lastLocation.getSpeed());
    	setTime(lastLocation.getTime());
    	setDriver(driver);
    	setVehicle(vehicle);
//    	setGroup(driver.)
    }
    
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public LatLng getLoc()
    {
        return loc;
    }
    public Driver getDriver()
    {
        return driver;
    }
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }
    public Vehicle getVehicle()
    {
        return vehicle;
    }
    public void setVehicle(Vehicle vehicle)
    {
        this.vehicle = vehicle;
    }
    public void setLoc(LatLng loc)
    {
        this.loc = loc;
    }
    public Date getTime()
    {
        return time;
    }
    public void setTime(Date time)
    {
        this.time = time;
    }
    public String getAddressStr()
    {
        return addressStr;
    }
    public void setAddressStr(String addressStr)
    {
        this.addressStr = addressStr;
    }
    public Double getDist()
    {
        return dist;
    }
    public void setDist(Double dist)
    {
        this.dist = dist;
    }
    public Integer getPosition()
    {
        return position;
    }
    public void setPosition(Integer position)
    {
        this.position = position;
    }
    public Device getDevice()
    {
        return device;
    }
    public void setDevice(Device device)
    {
        this.device = device;
    }
	public Integer getSpeed() {
		return speed;
	}
	public void setSpeed(Integer speed) {
		this.speed = speed;
	}
	public Integer getHead() {
		return head;
	}
	public void setHead(Integer head) {
		this.head = head;
	}
	public void setHeading(Integer head) {
		this.head = head;
	}

}

package com.inthinc.pro.backing;

import java.util.Date;

import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Vehicle;

public class DriverLastLocationBean extends BaseBean {
	
    private LatLng      lastLocation;
    private String      driverName;
    private Driver 		driver;
    private Vehicle     vehicle;
    private int			iconKey;
    private String      address;
    private Date        time;
    
	public LatLng getLastLocation() {
		return lastLocation;
	}
	public void setLastLocation(LatLng lastLocation) {
		this.lastLocation = lastLocation;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public Driver getDriver() {
		return driver;
	}
	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	public int getIconKey() {
		return iconKey;
	}
	public void setIconKey(int iconKey) {
		this.iconKey = iconKey;
	}
    public String getAddress()
    {
        return address;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }
    public Vehicle getVehicle()
    {
        return vehicle;
    }
    public void setVehicle(Vehicle vehicle)
    {
        this.vehicle = vehicle;
    }
    public Date getTime()
    {
        return time;
    }
    public void setTime(Date time)
    {
        this.time = time;
    }
}

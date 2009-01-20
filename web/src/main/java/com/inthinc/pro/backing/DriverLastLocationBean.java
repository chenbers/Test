package com.inthinc.pro.backing;

import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.LatLng;

public class DriverLastLocationBean extends BaseBean {
	
    private LatLng      lastLocation;
    private String      driverName;
    private Driver 		driver;
    private int			iconKey;
    
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

}

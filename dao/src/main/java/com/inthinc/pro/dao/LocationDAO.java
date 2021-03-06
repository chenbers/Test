package com.inthinc.pro.dao;

import java.util.Date;
import java.util.List;

import org.joda.time.Interval;

import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.DriverStops;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Trip;

public interface LocationDAO 
{
    public LastLocation getLastLocationForDriver(Integer driverID);
	public List<DriverLocation> getDriverLocations(Integer groupID);
    public LastLocation getLastLocationForVehicle(Integer vehicleID);
    public Trip getLastTripForVehicle(Integer vehicleID);
    public List<Trip> getTripsForVehicle(Integer vehicleID, Date startDate, Date endDate);
    public Trip getLastTripForDriver(Integer driverID);
    public List<Trip> getTripsForDriver(Integer driverID, Date startDate, Date endDate);
    public List<Trip> getTripsForDriver(Integer driverID, Date startDate, Date endDate, Boolean includeRoute);
    public List<DriverStops> getStops(Integer driverID, String driverName, Interval interval);
    public List<LatLng> getLocationsForDriverTrip(Integer driverID, Date startTime, Date endTime);
    public List<LatLng> getLocationsForVehicleTrip(Integer vehicleID, Date startTime, Date endTime);

    
    public VehicleDAO getVehicleDAO();
	public void setVehicleDAO(VehicleDAO vehicleDAO);
	public DriverDAO getDriverDAO();
	public void setDriverDAO(DriverDAO deviceDAO);
    public DeviceDAO getDeviceDAO();
    public void setDeviceDAO(DeviceDAO deviceDAO);
    public EventDAO getEventDAO();
    public void setEventDAO(EventDAO eventDAO);
}

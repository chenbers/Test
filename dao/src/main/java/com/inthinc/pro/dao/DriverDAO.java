package com.inthinc.pro.dao;

import java.util.Date;
import java.util.List;

import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.Trip;

public interface DriverDAO extends GenericDAO<Driver, Integer>
{
    
    /**
     * Gets all drivers in the specified group.
     * 
     * @param groupID
     * @return
     */
    List<Driver> getAllDrivers(Integer groupID);
    
    /**
     * Gets a list of DriverLocation's near the specified LatLng.
     * 
     * @param groupID
     * @param numof
     * @param lat
     * @param lng
     * @return
     */
    List<DriverLocation> getDriversNearLoc(Integer groupID, Integer numof, Double lat, Double lng);
    
    /**
     * Gets the specified drivers LastLocation.
     * 
     * @param driverID
     * @return
     */
    LastLocation getLastLocation(Integer driverID);
    List<Trip> getTrips(Integer driverID, Date startDate, Date endDate);
    
    Trip getLastTrip(Integer driverID);


}

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
     * Gets all drivers in the specified group complete hierarchy
     * 
     * @param groupID
     * @return List of Drivers
     */
    List<Driver> getAllDrivers(Integer groupID);

    /**
     * Gets the drivers that are directly under the specified group
     * 
     * @param groupID
     * @return List of Drivers
     */
    List<Driver> getDrivers(Integer groupID);

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

    /**
     * Get all driver trips between start and end dates.
     * 
     * @param driverID
     * @param startDate
     * @param endDate
     * @return
     */
    List<Trip> getTrips(Integer driverID, Date startDate, Date endDate);

    /**
     * Get driver last trip by DriverID
     * 
     * @param driverID
     * @return
     */
    Trip getLastTrip(Integer driverID);

    /**
     * Get Driver object by PersonID
     * 
     * @param personID
     * @return
     */
    Driver getDriverByPersonID(Integer personID);

    /**
     * Find a driver ID by RFID.
     * 
     * @param rfid
     *            The RFID to find by.
     * @return The driver ID or <code>null</code> if not found.
     */
    Integer getDriverIDForRFID(Long rfid);
}

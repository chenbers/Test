package com.inthinc.pro.dao;

import java.util.Date;
import java.util.List;

import com.inthinc.pro.model.Vehicle;
import org.joda.time.Interval;

import com.inthinc.pro.model.DriverName;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.DriverStops;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Trip;

public interface DriverDAO extends GenericDAO<Driver, Integer> {

    /**
     * Gets all drivers in the specified group complete hierarchy
     * 
     * @param groupID
     * @return List of Drivers
     */
    List<Driver> getAllDrivers(Integer groupID);

    /**
     * Get all drivers in a specified list of group ids.
     *
     * @param groupIDList list of group ids
     * @return List of Drivers
     */
    List<Driver> getDriversInGroupIDList(List<Integer> groupIDList);

    /**
     * Gets the drivers that are directly under the specified group
     * 
     * @param groupID
     * @return List of Drivers
     */
    List<Driver> getDrivers(Integer groupID);

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
    List<Trip> getTrips(Integer driverID, Date startDate, Date enate);
    List<Trip> getTrips(Integer driverID, Date startDate, Date enate, Boolean includeRoute);

    /**
     * Get all driver trips between start and end dates.
     * 
     * @param driverID
     * @param interval
     * @return
     */
    List<Trip> getTrips(Integer driverID, Interval interval);
    List<Trip> getTrips(Integer driverID, Interval interval, Boolean includeRoute);

    /**
     * Get driver last trip by DriverID
     * 
     * @param driverID
     * @return
     */
    Trip getLastTrip(Integer driverID);


    public List<LatLng> getLocationsForTrip(Integer driverID, Date startTime, Date endTime);
    public List<LatLng> getLocationsForTrip(Integer driverID, Interval interval);

    /**
     * Get Driver object by PersonID
     * 
     * @param personID
     * @return
     */
    Driver findByPersonID(Integer personID);

    /**
     * Find a RFIDs for a barcode.
     * 
     * @param barcode
     *            The barcode to find by.
     * @return List of RFIDs or <code>null</code> if not found.
     */
    List<Long> getRfidsByBarcode(String barcode);

    /**
     * Find a driver for a barcode.
     * 
     * @param barcode
     *            The barcode to find by.
     * @return List of RFIDs or <code>null</code> if not found.
     */
    Integer getDriverIDByBarcode(String barcode);

    /**
     * Gets a list of all drivers that have a last location.
     * 
     * @param groupID
     *            The groupID (deep) to retrieve.
     * @return List of DriverLocations or empty map if none found.
     */
    List<DriverLocation> getDriverLocations(Integer groupID);

    /**
     * Gets a list of all a drivers stops for a timeframe.
     * 
     * @param driverID
     *            The driverID to retrieve.
     * @param interval
     *            The time interval to consider.              
     * @return List of DriverStopsReportItem or empty map if none found.
     */
    List<DriverStops> getStops(Integer driverID, String driverName, Interval interval);

    List<DriverName> getDriverNames(Integer groupID);
}

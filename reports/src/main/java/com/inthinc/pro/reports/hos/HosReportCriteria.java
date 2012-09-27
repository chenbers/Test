package com.inthinc.pro.reports.hos;

import java.util.List;

import org.joda.time.Interval;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Trip;

public abstract class HosReportCriteria {
    private Boolean includeInactiveDrivers;
    private Boolean includeZeroMilesDrivers;

    /**
     * Determines whether this driver should be included in a report based on params. Used when driver and totalMiles are already KNOWN.
     * 
     * @param driver
     *            the Driver object in question
     * @param totalMiles
     *            the total number of miles for the timeframe of the report
     * @return true if the driver should be included
     */
    public boolean includeDriver(Driver driver, Integer totalMiles) {
        boolean includeThisInactiveDriver = (includeInactiveDrivers && totalMiles != 0);
        boolean includeThisZeroMilesDriver = (includeZeroMilesDrivers && driver.getStatus().equals(Status.ACTIVE));
        return ((driver.getStatus().equals(Status.ACTIVE) && totalMiles != 0) || (includeInactiveDrivers && includeZeroMilesDrivers) || includeThisInactiveDriver || includeThisZeroMilesDriver);
    }

    /**
     * Determines whether this driver should be included in a report based on params including:
     * 
     * @param driverDAO
     *            necessary to find Driver
     * @param driverID
     *            Integer id of the Driver in question
     * @param interval
     *            the time interval being queried
     * @return true if the driver should be included
     */
    public boolean includeDriver(DriverDAO driverDAO, Integer driverID, Interval interval) {
        Driver driver = driverDAO.findByID(driverID);
        List<Trip> trips = driverDAO.getTrips(driver.getDriverID(), interval);
        Integer totalMiles = 0;
        for (Trip trip : trips) {
            totalMiles += trip.getMileage();
        }
        return includeDriver(driver, totalMiles);
    }

    public Boolean getIncludeInactiveDrivers() {
        return includeInactiveDrivers;
    }

    public void setIncludeInactiveDrivers(Boolean includeInactiveDrivers) {
        this.includeInactiveDrivers = includeInactiveDrivers;
    }

    public Boolean getIncludeZeroMilesDrivers() {
        return includeZeroMilesDrivers;
    }

    public void setIncludeZeroMilesDrivers(Boolean includeZeroMilesDrivers) {
        this.includeZeroMilesDrivers = includeZeroMilesDrivers;
    }

}

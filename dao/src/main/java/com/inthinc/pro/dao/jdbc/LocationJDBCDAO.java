package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.LocationDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.DriverStops;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Trip;
import org.joda.time.Interval;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Location jdbc dao.
 */
public class LocationJDBCDAO  extends SimpleJdbcDaoSupport implements LocationDAO{
    private static final String TRIP_COUNT_FOR_DRIVER_ID = "" +
            "select sum(coalesce(mileage,0)) mileageSum from trip " +
            "where driverID = :driverID " +
            " and startTime >= :startDate and endTime <= :endDate";

    @Override
    public Integer getTripMileageCountForDriver(Integer driverID, Date startDate, Date endDate) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("driverID", driverID);
        params.put("startDate", startDate);
        params.put("endDate", endDate);

        return getSimpleJdbcTemplate().queryForInt(TRIP_COUNT_FOR_DRIVER_ID, params);
    }

    @Override
    public LastLocation getLastLocationForDriver(Integer driverID) {
        throw new NotImplementedException();
    }

    @Override
    public List<DriverLocation> getDriverLocations(Integer groupID) {
        throw new NotImplementedException();
    }

    @Override
    public LastLocation getLastLocationForVehicle(Integer vehicleID) {
        throw new NotImplementedException();
    }

    @Override
    public Trip getLastTripForVehicle(Integer vehicleID) {
        throw new NotImplementedException();
    }

    @Override
    public List<Trip> getTripsForVehicle(Integer vehicleID, Date startDate, Date endDate) {
        throw new NotImplementedException();
    }

    @Override
    public Trip getLastTripForDriver(Integer driverID) {
        throw new NotImplementedException();
    }

    @Override
    public List<Trip> getTripsForDriver(Integer driverID, Date startDate, Date endDate) {
        throw new NotImplementedException();
    }

    @Override
    public List<Trip> getTripsForDriver(Integer driverID, Date startDate, Date endDate, Boolean includeRoute) {
        throw new NotImplementedException();
    }

    @Override
    public List<DriverStops> getStops(Integer driverID, String driverName, Interval interval) {
        throw new NotImplementedException();
    }

    @Override
    public List<LatLng> getLocationsForDriverTrip(Integer driverID, Date startTime, Date endTime) {
        throw new NotImplementedException();
    }

    @Override
    public List<LatLng> getLocationsForVehicleTrip(Integer vehicleID, Date startTime, Date endTime) {
        throw new NotImplementedException();
    }

    @Override
    public VehicleDAO getVehicleDAO() {
        throw new NotImplementedException();
    }

    @Override
    public void setVehicleDAO(VehicleDAO vehicleDAO) {

    }

    @Override
    public DriverDAO getDriverDAO() {
        throw new NotImplementedException();
    }

    @Override
    public void setDriverDAO(DriverDAO deviceDAO) {

    }

    @Override
    public DeviceDAO getDeviceDAO() {
        throw new NotImplementedException();
    }

    @Override
    public void setDeviceDAO(DeviceDAO deviceDAO) {

    }

    @Override
    public EventDAO getEventDAO() {
        throw new NotImplementedException();
    }

    @Override
    public void setEventDAO(EventDAO eventDAO) {

    }
}

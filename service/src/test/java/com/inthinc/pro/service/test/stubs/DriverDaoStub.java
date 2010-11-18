/**
 * 
 */
package com.inthinc.pro.service.test.stubs;

import java.util.Date;
import java.util.List;

import org.joda.time.Interval;
import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.DriverStops;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.Trip;

/**
 * @author dfreitas
 * 
 */
@Component
public class DriverDaoStub implements DriverDAO {

    @Override
    public Integer update(Driver entity) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Driver findByID(Integer id) {
        // TODO Auto-generated method stub
        return new Driver();
    }

    @Override
    public Integer deleteByID(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer create(Integer id, Driver entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Trip> getTrips(Integer driverID, Interval interval) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Trip> getTrips(Integer driverID, Date startDate, Date endDate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DriverStops> getStops(Integer driverID, Interval interval) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Long> getRfidsByBarcode(String barcode) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Trip getLastTrip(Integer driverID) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LastLocation getLastLocation(Integer driverID) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Driver> getDrivers(Integer groupID) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DriverLocation> getDriverLocations(Integer groupID) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer getDriverIDByBarcode(String barcode) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Driver> getAllDrivers(Integer groupID) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Driver findByPersonID(Integer personID) {
        // TODO Auto-generated method stub
        return null;
    }
}

package com.inthinc.pro.service.test.mock;

import java.util.ArrayList;
import java.util.Collections;
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
    private Driver driver;
    
    @Override
    public Integer update(Driver entity) {
        this.driver = entity;
        return entity.getDriverID();
    }

    @Override
    public Driver findByID(Integer id) {
        return this.driver;
    }

    @Override
    public Integer deleteByID(Integer id) {
        return driver.getDriverID();
    }

    @Override
    public Integer create(Integer id, Driver entity) {
        this.driver = entity;
        return id;
    }

    @Override
    public List<Trip> getTrips(Integer driverID, Interval interval) {
        return Collections.emptyList();
    }

    @Override
    public List<Trip> getTrips(Integer driverID, Date startDate, Date endDate) {
        return Collections.emptyList();
    }

    @Override
    public List<DriverStops> getStops(Integer driverID, Interval interval) {
        return Collections.emptyList();
    }

    @Override
    public List<Long> getRfidsByBarcode(String barcode) {
        return Collections.emptyList();
    }

    @Override
    public Trip getLastTrip(Integer driverID) {
        return null;
    }

    @Override
    public LastLocation getLastLocation(Integer driverID) {
        return null;
    }

    @Override
    public List<Driver> getDrivers(Integer groupID) {
        return Collections.emptyList();
    }

    @Override
    public List<DriverLocation> getDriverLocations(Integer groupID) {
        return Collections.emptyList();
    }

    @Override
    public Integer getDriverIDByBarcode(String barcode) {
        return driver.getDriverID();
    }

    @Override
    public List<Driver> getAllDrivers(Integer groupID) {
        return Collections.emptyList();
    }

    @Override
    public Driver findByPersonID(Integer personID) {
        return this.driver;
    }

    @Override
    public Driver findByPhoneNumber(String phoneID) {
        this.driver.getCellProviderInfo().setCellPhone(phoneID);
        return this.driver;
    }

    public void setExpectedDriver(Driver driver) {
        this.driver = driver;
    }

	@Override
	public List<Driver> getDriversWithDisabledPhones() {
		return new ArrayList<Driver>();
	}
}

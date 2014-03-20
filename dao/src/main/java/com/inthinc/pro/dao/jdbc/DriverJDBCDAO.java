package com.inthinc.pro.dao.jdbc;

import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.DriverName;
import com.inthinc.pro.model.DriverStops;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.app.States;
import org.joda.time.Interval;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * Driver jdbc dao.
 */
public class DriverJDBCDAO extends SimpleJdbcDaoSupport implements DriverDAO{
    private final Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));

    private ParameterizedRowMapper<Driver> driverMapper = new ParameterizedRowMapper<Driver>(){
        @Override
        public Driver mapRow(ResultSet rs, int rowNum) throws SQLException {
            Driver driver = new Driver();
            driver.setDriverID(rs.getInt("d.driverID"));
            driver.setGroupID(rs.getInt("d.groupID"));
            driver.setStatus(Status.valueOf(rs.getInt("d.status")));
            driver.setLicense(rs.getString("d.license"));
            driver.setLicenseClass(rs.getString("d.class"));
            driver.setState(States.getStateById(rs.getInt("d.stateID")));
            driver.setExpiration(rs.getDate("d.expiration", calendar));
            driver.setCertifications(rs.getString("d.certs"));
            driver.setDot(RuleSetType.valueOf(rs.getInt("d.dot")));
            driver.setBarcode(rs.getString("d.barcode"));
            driver.setRfid1(rs.getObject("d.rfid1") == null ? null : rs.getLong("d.rfid1"));
            driver.setRfid2(rs.getObject("d.rfid2")== null ? null : rs.getLong("d.rfid2"));
            driver.setFobID(rs.getString("d.fobID"));

            Person person = new Person();
            person.setPersonID(rs.getInt("d.personID"));
            person.setFirst(rs.getString("p.first"));
            person.setLast(rs.getString("p.last"));
            person.setDriver(driver);
            driver.setPerson(person);
            return driver;
        }
    };

    @Override
    public List<Driver> getAllDrivers(Integer groupID) {
        throw new NotImplementedException();
    }

    @Override
    public List<Driver> getDriversInGroupIDList(List<Integer> groupIDList) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("group_list", groupIDList);
        String select = "select d.*, p.first,p.last from driver d left outer join person p on d.personID = p.personID where d.status != 3 and d.groupID in (:group_list)";
        List<Driver> driverList = getSimpleJdbcTemplate().query(select, driverMapper, params);
        return driverList;
    }

    @Override
    public List<Driver> getDrivers(Integer groupID) {
        throw new NotImplementedException();
    }

    @Override
    public LastLocation getLastLocation(Integer driverID) {
        throw new NotImplementedException();
    }

    @Override
    public List<Trip> getTrips(Integer driverID, Date startDate, Date enate) {
        throw new NotImplementedException();
    }

    @Override
    public List<Trip> getTrips(Integer driverID, Date startDate, Date enate, Boolean includeRoute) {
        throw new NotImplementedException();
    }

    @Override
    public List<Trip> getTrips(Integer driverID, Interval interval) {
        throw new NotImplementedException();
    }

    @Override
    public List<Trip> getTrips(Integer driverID, Interval interval, Boolean includeRoute) {
        throw new NotImplementedException();
    }

    @Override
    public Trip getLastTrip(Integer driverID) {
        throw new NotImplementedException();
    }

    @Override
    public List<LatLng> getLocationsForTrip(Integer driverID, Date startTime, Date endTime) {
        throw new NotImplementedException();
    }

    @Override
    public List<LatLng> getLocationsForTrip(Integer driverID, Interval interval) {
        throw new NotImplementedException();
    }

    @Override
    public Driver findByPersonID(Integer personID) {
        throw new NotImplementedException();
    }

    @Override
    public List<Long> getRfidsByBarcode(String barcode) {
        throw new NotImplementedException();
    }

    @Override
    public Integer getDriverIDByBarcode(String barcode) {
        throw new NotImplementedException();
    }

    @Override
    public List<DriverLocation> getDriverLocations(Integer groupID) {
        throw new NotImplementedException();
    }

    @Override
    public List<DriverStops> getStops(Integer driverID, String driverName, Interval interval) {
        throw new NotImplementedException();
    }

    @Override
    public List<DriverName> getDriverNames(Integer groupID) {
        throw new NotImplementedException();
    }

    @Override
    public Driver findByID(Integer integer) {
        throw new NotImplementedException();
    }

    @Override
    public Integer create(Integer integer, Driver entity) {
        throw new NotImplementedException();
    }

    @Override
    public Integer update(Driver entity) {
        throw new NotImplementedException();
    }

    @Override
    public Integer deleteByID(Integer integer) {
        throw new NotImplementedException();
    }
}

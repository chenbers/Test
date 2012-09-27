package com.inthinc.pro.reports.performance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.PhoneControlDAO;
import com.inthinc.pro.model.Cellblock;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.DriverName;
import com.inthinc.pro.model.DriverStops;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.reports.BaseUnitTest;

public class BasePerformanceUnitTest extends BaseUnitTest {
    
    protected static final Integer GROUP_ID = Integer.valueOf(1);
    protected static final Integer TEAM_GROUP_ID = Integer.valueOf(2);
    protected static final String GROUP_NAME = "TEST GROUP";


    protected Interval initInterval()
    {
        DateTime end = new DateMidnight(DateTimeZone.UTC).toDateTime();
        DateTime start = new DateTime(end, DateTimeZone.UTC).minusDays(1);
        return new Interval(start, end);

    }
    
    protected GroupHierarchy getMockGroupHierarchy() {
        List<Group> groupList = new ArrayList<Group>();
        Group group = new Group(GROUP_ID, Integer.valueOf(0), GROUP_NAME, Integer.valueOf(0));
        group.setType(GroupType.FLEET);
        Group teamGroup = new Group(TEAM_GROUP_ID, Integer.valueOf(0), GROUP_NAME + " TEAM", GROUP_ID);
        teamGroup.setType(GroupType.TEAM);
        groupList.add(group);
        groupList.add(teamGroup);
        GroupHierarchy groupHierarchy = new GroupHierarchy();
        groupHierarchy.setGroupList(groupList);
        return groupHierarchy;
    }
    
    class MockDriverDAO implements DriverDAO {

        @Override
        public Driver findByPersonID(Integer personID) {
            return null;
        }


        @Override
        public List<Driver> getAllDrivers(Integer groupID) {
            List<Driver> driverList = new ArrayList<Driver>();

            for (int i = 1; i < 5; i++) {
                Driver driver = new Driver(i, i, Status.ACTIVE, "", 0l, 0l, "", null, null, null, null, null, GROUP_ID);
                Person person = new Person();
                person.setPersonID(i);
                person.setFirst("F" + i);
                person.setLast("L"+i);
                driver.setPerson(person);
                driverList.add(driver);
            }
            return driverList;
        }

        @Override
        public Integer getDriverIDByBarcode(String barcode) {
            return null;
        }

        @Override
        public List<DriverLocation> getDriverLocations(Integer groupID) {
            return null;
        }

        @Override
        public List<Driver> getDrivers(Integer groupID) {
            return null;
        }

        @Override
        public LastLocation getLastLocation(Integer driverID) {
            return null;
        }

        @Override
        public Trip getLastTrip(Integer driverID) {
            return null;
        }

        @Override
        public List<Long> getRfidsByBarcode(String barcode) {
            return null;
        }

        @Override
        public List<DriverStops> getStops(Integer driverID, String driverName, Interval interval) {
            return null;
        }

        @Override
        public List<Trip> getTrips(Integer driverID, Date startDate, Date endDate) {
            //return a mock trip with minimal data
            List<Trip> trips = new ArrayList<Trip>();
            Trip trip = new Trip();
            trip.setMileage(1);
            trips.add(trip);
            return trips;
        }

        @Override
        public List<Trip> getTrips(Integer driverID, Interval interval) {
            //return a mock trip with minimal data
            List<Trip> trips = new ArrayList<Trip>();
            Trip trip = new Trip();
            trip.setMileage(1);
            trips.add(trip);
            return trips;
        }

        @Override
        public Integer create(Integer id, Driver entity) {
            return null;
        }

        @Override
        public Integer deleteByID(Integer id) {
            return null;
        }

        @Override
        public Driver findByID(Integer id) {
            Driver driver = new Driver();
            driver.setStatus(Status.ACTIVE);
            return driver;
        }

        @Override
        public Integer update(Driver entity) {
            return null;
        }


        @Override
        public List<DriverName> getDriverNames(Integer groupID) {
            // TODO Auto-generated method stub
            return null;
        }
        
    }
    
    class MockPhoneControlDAO implements PhoneControlDAO{
        @Override
        public Cellblock findByPhoneNumber(String phoneNumber) {
            return null;
        }
        @Override
        public List<Cellblock> getDriversWithDisabledPhones() {
            return null;
        }
        @Override
        public Integer create(Integer id, Cellblock entity) {
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public Integer deleteByID(Integer id) {
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public Cellblock findByID(Integer id) {
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public Integer update(Cellblock entity) {
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public List<Cellblock> getCellblocksByAcctID(Integer acctID) {
            // TODO Auto-generated method stub
            return null;
        }

        
    }
}

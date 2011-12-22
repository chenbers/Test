package com.inthinc.pro.reports;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.map.GoogleAddressLookup;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.DriverName;
import com.inthinc.pro.model.DriverStops;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.reports.service.impl.ReportCriteriaServiceImpl;


public class TeamStopsReportCriteriaTest extends BaseUnitTest {
    
    AddressLookup addressLookup;
    
    @Before
    public void setup() {
        addressLookup = new GoogleAddressLookup();
        ((GoogleAddressLookup)addressLookup).setGoogleMapGeoUrl("https://maps-api-ssl.google.com/maps/geo?client=gme-inthinc&sensor=false&q=");

    }
    
    @Test
    public void testDriverNoData() {
        
        ReportCriteriaServiceImpl  reportCriteriaService = new ReportCriteriaServiceImpl();
       
        reportCriteriaService.setDriverDAO(new MockDriverDAO());
        ReportCriteria reportCriteria = reportCriteriaService.getTeamStopsReportCriteria(1, TimeFrame.TODAY,DateTimeZone.getDefault(), Locale.ENGLISH, addressLookup, null );

        dump("teamStops1", 0, reportCriteria, FormatType.PDF);

    }

    @Test
    public void testDriverData() {
        
        ReportCriteriaServiceImpl  reportCriteriaService = new ReportCriteriaServiceImpl();
       
        reportCriteriaService.setDriverDAO(new MockDriverDAO());
        ReportCriteria reportCriteria = reportCriteriaService.getTeamStopsReportCriteria(11771, TimeFrame.TODAY,DateTimeZone.getDefault(), Locale.ENGLISH, addressLookup, null);

        dump("teamStops2", 0, reportCriteria, FormatType.PDF);

    }

    class MockDriverDAO implements DriverDAO {
        
        DriverStops[] driverStops = {
                new DriverStops(11771, 12750, "Test Vehicle", 34.66848d, -92.340576d, 0l, null, 1314706917l, 0, 0, null),
                new DriverStops(11771, 12750, "Test Vehicle", 34.66848d, -92.340576d, 0l, 1314706917l, 1314706980l, 68, 18, null),
                new DriverStops(11771, 12750, "Test Vehicle", 34.670361d, -92.377899d, 328l, 1314707308l, 1314707640l, 332, 0, null),
                new DriverStops(11771, 12750, "Test Vehicle", 34.62093d, -92.49765d, 536l, 1314708176l, 1314708240l, 64, 0, null),
                new DriverStops(11771, 12750, "Test Vehicle", 34.623398d, -92.499512d, 347l, 1314708587l, null, 73, 0, "Zone With Alerts")
            };


        @Override
        public Driver findByPersonID(Integer personID) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<Driver> getAllDrivers(Integer groupID) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Integer getDriverIDByBarcode(String barcode) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<DriverLocation> getDriverLocations(Integer groupID) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<DriverName> getDriverNames(Integer groupID) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<Driver> getDrivers(Integer groupID) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public LastLocation getLastLocation(Integer driverID) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Trip getLastTrip(Integer driverID) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<Long> getRfidsByBarcode(String barcode) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<DriverStops> getStops(Integer driverID, Interval interval) {
            if (driverID == 1) {
                return new ArrayList<DriverStops>();
            }
            if (driverID == 11771) {
                List<DriverStops> list = new ArrayList<DriverStops>();
                for (DriverStops driverStop : driverStops)
                    list.add(driverStop);
                return list;
            }
            
            return null;
        }

        @Override
        public List<Trip> getTrips(Integer driverID, Date startDate, Date endDate) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<Trip> getTrips(Integer driverID, Interval interval) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Integer create(Integer id, Driver entity) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Integer deleteByID(Integer id) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Driver findByID(Integer id) {
            if (id == 1) {
                Person person = new Person();
                person.setPersonID(1);
                person.setFirst("First1");
                person.setLast("Last");
                
                Driver driver = new Driver(1, 1, Status.ACTIVE, null,null,null,null, null, null, null, null, null, 1);
                driver.setPerson(person);
                return driver;
            }
            if (id == 11771) {
                Person person = new Person();
                person.setPersonID(1);
                person.setFirst("John");
                person.setLast("Smith");
                
                Driver driver = new Driver(11771, 11771, Status.ACTIVE, null,null,null,null, null, null, null, null, null, 1);
                driver.setPerson(person);
                return driver;
            }
            return null;
        }

        @Override
        public Integer update(Driver entity) {
            // TODO Auto-generated method stub
            return null;
        }
        
    }
     
}

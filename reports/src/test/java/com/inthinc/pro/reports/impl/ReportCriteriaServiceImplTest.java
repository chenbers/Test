package com.inthinc.pro.reports.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.joda.time.Interval;
import org.junit.Test;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.DriverStops;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.reports.BaseUnitTest;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.dao.mock.MockWaysmartDAO;
import com.inthinc.pro.reports.hos.testData.MockData;
import com.inthinc.pro.reports.service.impl.ReportCriteriaServiceImpl;

/**
 * Unit test for ReportCriteriaServiceImpl.
 */
public class ReportCriteriaServiceImplTest extends BaseUnitTest {
    private ReportCriteriaServiceImpl serviceSUT;
    
    // Mocks
    private Integer mockGroupId = 1;
    private List<Integer> listGroupID = new ArrayList<Integer>();
    
    /**
     * Constructor to initialize the test fields.
     */
    public ReportCriteriaServiceImplTest() {
        // TODO Move it to application context for tests
        serviceSUT = new ReportCriteriaServiceImpl();
        serviceSUT.setGroupDAO(new MockGroupDAO(mockGroupId));
        serviceSUT.setDriverDAO(new MockDriverDAO(mockGroupId));
        serviceSUT.setWaysmartDAO(new MockWaysmartDAO());
    }

    /**
     * Test for getDriverHoursReportCriteria method.
     */
    @Test
    public void testGetDriverHoursReportCriteria() {
        ReportCriteria criteria = serviceSUT.getDriverHoursReportCriteria(mockGroupId,
                new Interval(new Date().getTime() - 3000, new Date().getTime()), Locale.US);
        
        assertNotNull(criteria);
        assertEquals(Locale.US,criteria.getLocale());
        assertNotNull(criteria.getMainDataset());
    }
    
    /**
     * Test for getStateMileageByVehicleRoadStatusReportCriteria method.
     */
    @Test
    public void testGetStateMileageByVehicleRoadStatusReportCriteria() {
        boolean isIfta = true;
        ReportCriteria criteria = serviceSUT.getStateMileageByVehicleRoadStatusReportCriteria(listGroupID,
                new Interval(new Date().getTime() - 3000, new Date().getTime()), Locale.US, isIfta);
        
        assertNotNull(criteria);
        assertEquals(Locale.US,criteria.getLocale());
        assertNotNull(criteria.getMainDataset());
    }

    // TODO Move these classes to mock package
    class MockDriverDAO implements DriverDAO {
        Driver driver;
        
        MockDriverDAO(Integer groupID) {
            driver = MockData.createMockDriver(groupID, 2, "Driver", "2");
            driver.setGroupID(groupID);
        }
        
        @Override
        public Driver findByPersonID(Integer personID) {
            return null;
        }

        @Override
        public List<Driver> getAllDrivers(Integer groupID) {
            List<Driver> list = new ArrayList<Driver>();
            list.add(driver);
            return list;
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
            List<Driver> list = new ArrayList<Driver>();
            list.add(driver);
            return list;
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
        public List<DriverStops> getStops(Integer driverID, Interval interval) {
            return null;
        }

        @Override
        public List<Trip> getTrips(Integer driverID, Date startDate, Date endDate) {
            return null;
        }

        @Override
        public List<Trip> getTrips(Integer driverID, Interval interval) {
            return null;
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
            return null;
        }

        @Override
        public Integer update(Driver entity) {
            return null;
        }        
    }

    class MockGroupDAO implements GroupDAO {
        Group group = null;
        
        MockGroupDAO(Integer id){
            group = MockData.createMockGroup(id);
        }

        @Override
        public List<Group> getGroupHierarchy(Integer acctID, Integer groupID) {
            List<Group> list = new ArrayList<Group>();
            list.add(MockData.createMockGroup(1));
            return list;
        }

        @Override
        public List<Group> getGroupsByAcctID(Integer acctID) {
            return null;
        }

        @Override
        public Integer create(Integer id, Group entity) {
            return null;
        }

        @Override
        public Integer deleteByID(Integer id) {
            return null;
        }

        @Override
        public Group findByID(Integer id) {
            group.setGroupID(id);
            return group;
        }

        @Override
        public Integer update(Group entity) {
            return null;
        }
        
    }
}

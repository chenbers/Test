package com.inthinc.pro.reports.performance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.joda.time.Interval;
import org.junit.Test;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.mock.MockWaysmartDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.DriverStops;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.reports.BaseUnitTest;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.testData.MockData;

public class TenHoursViolationReportCriteriaTest extends BaseUnitTest {
    private TenHoursViolationReportCriteria reportCriteria; 
    
    private Integer mockGroupID = 1;
    
    @Test
    public void testCasesForTenHoursViolationReport(){
        reportCriteria = new TenHoursViolationReportCriteria(Locale.US);

        assertTrue(reportCriteria instanceof ReportCriteria);
        
        reportCriteria.setAccountDAO(new MockAccountDAO());
        reportCriteria.setDriverDAO(new MockDriverDAO(mockGroupID));
        reportCriteria.setGroupDAO(new MockGroupDAO(mockGroupID));
        reportCriteria.setMockWaysmartDao(new MockWaysmartDAO());
        
        Interval interval = new Interval(new Date().getTime() - 3600, new Date().getTime());
        reportCriteria.init(mockGroupID, interval);
        
        assertEquals(ReportType.TEN_HOUR_DAY_VIOLATIONS, reportCriteria.getReport());
        assertNotNull(reportCriteria.getMainDataset());

        assertTrue(reportCriteria.getMainDataset().size() > 0);
    }
    
    class MockAccountDAO implements AccountDAO {

        private static final long serialVersionUID = 1L;
        private Account account = MockData.createMockAccount();

        @Override
        public Integer create(Account entity) {
            return entity.getAcctID();
        }

        @Override
        public List<Account> getAllAcctIDs() {
            List<Account> list = new ArrayList<Account>();
            list.add(account);
            return list;
        }

        @Override
        public Integer create(Integer id, Account entity) {
            account.setAcctID(id);
            return id;
        }

        @Override
        public Integer deleteByID(Integer id) {
            return id;
        }

        @Override
        public Account findByID(Integer id) {
            account.setAcctID(id);
            return account;
        }

        @Override
        public Integer update(Account entity) {
            return null;
        }
        
    }
    
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
            return null;
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
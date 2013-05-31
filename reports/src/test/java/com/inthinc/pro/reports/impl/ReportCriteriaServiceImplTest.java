package com.inthinc.pro.reports.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import mockit.VerificationsInOrder;

import org.joda.time.Interval;
import org.junit.Test;

import com.inthinc.pro.dao.DriveTimeDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.StateMileageDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.DriverName;
import com.inthinc.pro.model.DriverStops;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.reports.BaseUnitTest;
import com.inthinc.pro.reports.dao.mock.MockWaysmartDAO;
import com.inthinc.pro.reports.hos.testData.MockData;
import com.inthinc.pro.reports.ifta.StateMileageByMonthReportCriteria;
import com.inthinc.pro.reports.ifta.StateMileageByVehicleReportCriteria;
import com.inthinc.pro.reports.ifta.StateMileageByVehicleRoadStatusReportCriteria;
import com.inthinc.pro.reports.ifta.StateMileageCompareByGroupReportCriteria;
import com.inthinc.pro.reports.performance.DriverHoursReportCriteria;
import com.inthinc.pro.reports.service.impl.ReportCriteriaServiceImpl;

/**
 * Unit test for ReportCriteriaServiceImpl.
 */
public class ReportCriteriaServiceImplTest extends BaseUnitTest {
    private ReportCriteriaServiceImpl serviceSUT;

    // Mocks
    private Integer mockGroupId = 1;
    private List<Integer> listGroupID = new ArrayList<Integer>();
    private Group[] groupList = {
            new Group(mockGroupId, 1, "Mock Group", 0)
    };
    private GroupHierarchy groupHierarchy = new GroupHierarchy(Arrays.asList(groupList));

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
    public void testGetDriverHoursReportCriteria(final DriverHoursReportCriteria criteriaMock) {

        final Interval interval = new Interval(new Date().getTime() - 3000, new Date().getTime());

        serviceSUT.getDriverHoursReportCriteria(groupHierarchy, mockGroupId, interval, Locale.US);

        new VerificationsInOrder() {
            {
                new DriverHoursReportCriteria(Locale.US);
                criteriaMock.setDriverDAO((DriverDAO) any);
                criteriaMock.setDriveTimeDAO((DriveTimeDAO) any);
                criteriaMock.init(groupHierarchy, mockGroupId, interval);

            }
        };

    }

    /**
     * Test for getStateMileageByVehicleRoadStatusReportCriteria method.
     */
    @Test
    public void testGetStateMileageByVehicleRoadStatusReportCriteria(final StateMileageByVehicleRoadStatusReportCriteria criteriaMock) {
        final Boolean isIfta = true;
        final Interval interval = new Interval(new Date().getTime() - 3000, new Date().getTime());
        final MeasurementType type = MeasurementType.ENGLISH;

        listGroupID.add(1);
        serviceSUT.getStateMileageByVehicleRoadStatusReportCriteria(groupHierarchy, listGroupID, interval, Locale.US, type, isIfta);

        new VerificationsInOrder() {
            {
                new StateMileageByVehicleRoadStatusReportCriteria(Locale.US);
                criteriaMock.setGroupDAO((GroupDAO) any);
                criteriaMock.setStateMileageDAO((StateMileageDAO) any);
                criteriaMock.setMeasurementType(type);
                criteriaMock.init(groupHierarchy, listGroupID, interval, isIfta);

            }
        };

    }

    /**
     * Test for getStateMileageByVehicleReportCriteria method.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetStateMileageByVehicleReportCriteria(final StateMileageByVehicleReportCriteria criteriaMock) {
        final Boolean isIfta = true;
        final Interval interval = new Interval(new Date().getTime() - 16000, new Date().getTime());

        List<Integer> groupIDs = new ArrayList<Integer>();
        groupIDs.add(1);
        serviceSUT.getStateMileageByVehicleReportCriteria(groupHierarchy, groupIDs, interval, Locale.US, MeasurementType.ENGLISH, isIfta);

        new VerificationsInOrder() {
            {
                new StateMileageByVehicleReportCriteria(Locale.US);
                criteriaMock.setGroupDAO((GroupDAO) any);
                criteriaMock.setStateMileageDAO((StateMileageDAO) any);
                criteriaMock.init(groupHierarchy, (List<Integer>) any, interval, isIfta);
            }
        };
    }

    /**
     * Test for getStateMileageByMonthReportCriteria method.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetStateMileageByMonthReportCriteria(final StateMileageByMonthReportCriteria criteriaMock) {
        final Boolean isIfta = true;
        final Interval interval = new Interval(new Date().getTime() - 16000, new Date().getTime());

        List<Integer> groupIDsMock = new ArrayList<Integer>();
        groupIDsMock.add(1);
        serviceSUT.getStateMileageByMonthReportCriteria(groupHierarchy, groupIDsMock, interval, Locale.US, MeasurementType.ENGLISH, isIfta);

        new VerificationsInOrder() {
            {
                new StateMileageByMonthReportCriteria(Locale.US);
                criteriaMock.setGroupDAO((GroupDAO) any);
                criteriaMock.setStateMileageDAO((StateMileageDAO) any);
                criteriaMock.init(groupHierarchy, (List<Integer>) any, interval, isIfta);
            }
        };
    }

    /**
     * Test for getStateMileageByMonthReportCriteria method.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetStateMileageCompareByGroupReportCriteria(final StateMileageCompareByGroupReportCriteria criteriaMock) {
        final Boolean isIfta = true;
        final Interval interval = new Interval(new Date().getTime() - 16000, new Date().getTime());

        List<Integer> groupIDsMock = new ArrayList<Integer>();
        groupIDsMock.add(1);
        serviceSUT.getStateMileageCompareByGroupReportCriteria(groupHierarchy, groupIDsMock, interval, Locale.US, MeasurementType.ENGLISH, isIfta);

        new VerificationsInOrder() {
            {
                new StateMileageCompareByGroupReportCriteria(Locale.US);
                criteriaMock.setGroupDAO((GroupDAO) any);
                criteriaMock.setStateMileageDAO((StateMileageDAO) any);
                criteriaMock.setMeasurementType(MeasurementType.ENGLISH);
                criteriaMock.init(groupHierarchy, (List<Integer>) any, interval, isIfta);
            }
        };
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
            List<Driver> list = Collections.emptyList();
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
        public List<LatLng> getLocationsForTrip(Integer driverID, Date startTime, Date endTime) {
            return null;
        }

        @Override
        public List<LatLng> getLocationsForTrip(Integer driverID, Interval interval){
            return null;
        }

        @Override
        public List<Long> getRfidsByBarcode(String barcode) {
            return null;
        }

        @Override
        public List<DriverStops> getStops(Integer driverID,  String driverName, Interval interval) {
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
        public List<Trip> getTrips(Integer driverID, Date startDate, Date endDate, Boolean includeRoute) {
            return null;
        }

        @Override
        public List<Trip> getTrips(Integer driverID, Interval interval, Boolean includeRoute) {
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

        @Override
        public List<DriverName> getDriverNames(Integer groupID) {
            // TODO Auto-generated method stub
            return null;
        }

//        @Override
//        public Driver findByPhoneNumber(String phoneID) {
//            return null;
//        }
//
//		@Override
//		public List<Driver> getDriversWithDisabledPhones() {
//			return null;
//		}
    }

    class MockGroupDAO implements GroupDAO {
        Group group = null;

        MockGroupDAO(Integer id) {
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

		@Override
		public Integer delete(Group group) {
			return null;
		}

    }
}

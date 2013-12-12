package com.inthinc.pro.reports.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import mockit.VerificationsInOrder;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Test;

import com.inthinc.hos.ddl.HOSOccupantLog;
import com.inthinc.hos.model.HOSStatus;
import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.dao.StateMileageDAO;
import com.inthinc.pro.dao.report.TrailerReportDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.DriverName;
import com.inthinc.pro.model.DriverStops;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.TrailerReportItem;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.hos.HOSDriverLogin;
import com.inthinc.pro.model.hos.HOSGroupMileage;
import com.inthinc.pro.model.hos.HOSOccupantHistory;
import com.inthinc.pro.model.hos.HOSOccupantInfo;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.model.hos.HOSVehicleMileage;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;
import com.inthinc.pro.reports.BaseUnitTest;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
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
        serviceSUT.setAccountDAO(new MockAccountDAO());
        serviceSUT.setHosDAO(new MockHosDAO());
    }

    /**
     * Test for getDriverHoursReportCriteria method.
     */
  
    @Test
    public void testGetDriverHoursReportCriteria(final DriverHoursReportCriteria criteriaMock) {

        final Interval interval = new Interval(new Date().getTime() - 3000, new Date().getTime());
        List<Integer> mockGroupIdList = new ArrayList<Integer>();
        mockGroupIdList.add(mockGroupId);
        serviceSUT.getDriverHoursReportCriteria(groupHierarchy, mockGroupIdList, interval, Locale.US);

        new VerificationsInOrder() {
            {
                new DriverHoursReportCriteria(Locale.US);
                criteriaMock.setAccountDAO((AccountDAO) any);
                criteriaMock.setGroupDAO((GroupDAO) any);
                criteriaMock.setHosDAO((HOSDAO) any);
                criteriaMock.init(groupHierarchy, (List<Integer>) any, interval);

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
    
    @Test
    public void testGetTrailerReportCriteria() {
        String mockGroupName = "TrailerGroupName";
        
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.TRAILER_REPORT, mockGroupName, Locale.US);
        Duration duration = Duration.TWELVE;
        
        assertEquals(ReportType.TRAILER_REPORT, reportCriteria.getReport());
        
        MockTrailerReportDAO dao = new MockTrailerReportDAO();
        List<Integer> groupIDs = new ArrayList<Integer>();
        groupIDs.add(dao.getGroupID());
        Integer rowCount = dao.getTrailerReportCount(dao.getAcctID(), groupIDs, null);
        
        assertEquals(dao.numOfResults, rowCount.intValue());
        
        PageParams pageParams = new PageParams(0, rowCount, null, null);
        reportCriteria.setMainDataset(dao.getTrailerReportItemByGroupPaging(dao.getAcctID(), groupIDs, pageParams));
        reportCriteria.setDuration(duration);
        
        assertEquals(dao.numOfResults, reportCriteria.getMainDataset().size());
        assertEquals(duration, reportCriteria.getDuration());
    }

    // TODO Move these classes to mock package
    class MockTrailerReportDAO implements TrailerReportDAO {
        private int groupID = 1;
        private int acctID = 1;
        private String groupName = "MockGroupName";
        private int numOfResults = 10;
        
        public int getAcctID() {
            return acctID;
        }

        public int getGroupID() {
            return groupID;
        }

        public String getGroupName() {
            return groupName;
        }

        public int getNumOfResults() {
            return numOfResults;
        }

        @Override
        public List<TrailerReportItem> getTrailerReportItemByGroupPaging(Integer acctID, List<Integer> groupIDs, PageParams pageParams) {
           List<TrailerReportItem> retVal = new ArrayList<TrailerReportItem>();
           for(int i = 0; i < numOfResults; i++){
               TrailerReportItem item = new TrailerReportItem();
               item.setTrailerID(i);
               item.setTrailerName("tName_" + i);
               item.setVehicleID(i);
               item.setVehicleName("vName_" + i);
               item.setDriverID(i);
               item.setDriverName("dName_"+i);
               item.setGroupID(groupID);
               item.setGroupName(groupName);
               retVal.add(item);
           }
           
           return retVal;
        }

        @Override
        public Integer getTrailerReportCount(Integer acctID, List<Integer> groupIDs, List<TableFilterField> tableFilterFieldList) {
            return numOfResults;
        }

        @Override
        public Boolean isValidTrailer(Integer acctID, String trailerName) {
            // TODO Auto-generated method stub
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
    class MockHosDAO implements HOSDAO {
        
        public MockHosDAO() {
            // TODO Auto-generated constructor stub
        }

        @Override
        public HOSRecord findByID(Long id) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Long create(Long id, HOSRecord entity) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Integer update(HOSRecord entity) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Integer deleteByID(Long id) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<HOSRecord> getHOSRecords(Integer driverID, Interval interval, Boolean driverStatusOnly) {
            List<HOSRecord> hosRecordList = new ArrayList<HOSRecord>();
            HOSRecord record = new HOSRecord();
            record.setStatus(HOSStatus.DRIVING);
            record.setLogTime(new Date());
            hosRecordList.add(record);
            return hosRecordList;
        }

        @Override
        public List<HOSRecord> getHOSRecordsFilteredByInterval(Integer driverID, Interval interval, Boolean driverStatusOnly) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<HOSOccupantLog> getHOSOccupantLogs(Integer driverID, Interval interval) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<HOSGroupMileage> getHOSMileage(Integer groupID, Interval interval, Boolean noDriver) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<HOSVehicleMileage> getHOSVehicleMileage(Integer groupID, Interval interval, Boolean noDriver) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public HOSDriverLogin getDriverForEmpidLastName(String employeeId, String lastName) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Map<Integer, Long> fetchMileageForDayVehicle(DateTime day, Integer vehicleID) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public HOSOccupantInfo getOccupantInfo(Integer driverID) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<HOSRecord> getHOSRecordsForCommAddress(String address, List<HOSRecord> paramList) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public HOSDriverLogin getDriverForEmpid(String commAddress, String employeeId) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public HOSDriverLogin isValidLogin(String commAddress, String employeeId, long loginTime, boolean occupantFlag, int odometer) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public String fetchIMEIForOccupant(Integer driverID, Integer startTime) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<HOSOccupantHistory> getHOSOccupantHistory(HOSDriverLogin driverLogin) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<HOSOccupantHistory> getHOSOccupantHistory(String commAddress, String employeeId) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void logoutDriverFromDevice(String commAddress, String employeeId, long logoutTime, int odometer) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public List<HOSRecord> getRecordsForVehicle(Integer vehicleID, Interval interval, Boolean driverStatusOnly) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<HOSRecord> getFuelStopRecordsForVehicle(Integer vehicleID, Interval interval) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Long createFromNote(HOSRecord hosRecord) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public LatLng getVehicleHomeOfficeLocation(Integer vehicleID) {
            // TODO Auto-generated method stub
            return null;
        }
        
    }
    
    class MockAccountDAO implements AccountDAO {

        MockAccountDAO() {
            MockData.createMockAccount();
        }
        
        @Override
        public Account findByID(Integer id) {
            return null;
        }

        @Override
        public Integer create(Integer id, Account entity) {
            return null;
        }

        @Override
        public Integer update(Account entity) {
            return null;
        }

        @Override
        public Integer deleteByID(Integer id) {
            return null;
        }

        @Override
        public Integer create(Account entity) {
            return null;
        }

        @Override
        public List<Account> getAllAcctIDs() {
            return null;
        }
        
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

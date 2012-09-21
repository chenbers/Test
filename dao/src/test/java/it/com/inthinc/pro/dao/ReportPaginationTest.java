package it.com.inthinc.pro.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import it.com.inthinc.pro.dao.model.GroupListData;
import it.com.inthinc.pro.dao.model.ITData;
import it.com.inthinc.pro.dao.model.ITDataExt;
import it.config.IntegrationConfig;
import it.config.ReportTestConst;
import it.util.DataGenForReportPaginationTesting;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.dao.hessian.AccountHessianDAO;
import com.inthinc.pro.dao.hessian.DeviceHessianDAO;
import com.inthinc.pro.dao.hessian.DriverHessianDAO;
import com.inthinc.pro.dao.hessian.ReportHessianDAO;
import com.inthinc.pro.dao.hessian.StateHessianDAO;
import com.inthinc.pro.dao.hessian.mapper.DriverPerformanceMapper;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.DeviceReportItem;
import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.model.IdlingReportItem;
import com.inthinc.pro.model.VehicleReportItem;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.Range;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableFilterField;
import com.inthinc.pro.model.pagination.TableSortField;

public class ReportPaginationTest {
	
    private static final Logger logger = Logger.getLogger(ReportPaginationTest.class);
    private static SiloService siloService;
    private static ITDataExt itData;
    
    private static final String PAGINATION_BASE_DATA_XML = "ReportPageTest.xml";
//    private static final String PAGINATION_BASE_DATA_XML = "ReportPageTest34.xml";
    private static Integer countPerGroup;
    private static Integer goodGroupID;
    private static String goodGroupName;
    private static String badGroupName;
    
    TestFilterParams[] driverFilterTestList = {
    		new TestFilterParams("groupID", "XXX", 0),
    		new TestFilterParams("groupID", goodGroupID.toString(), countPerGroup),
    		new TestFilterParams("employeeID", "XXX", 0),
    		new TestFilterParams("employeeID", "emp", countPerGroup),
    		new TestFilterParams("groupName", "XXX", 0),
    		new TestFilterParams("groupName", goodGroupName, countPerGroup),
    		new TestFilterParams("driverName", "XXX", 0),
    		new TestFilterParams("driverName", goodGroupName, countPerGroup),
    		new TestFilterParams("vehicleName", "XXX", 0),
    		new TestFilterParams("vehicleName", goodGroupName, countPerGroup),
    		new TestFilterParams("overallScore", new Range(100,200), 0),
    		new TestFilterParams("overallScore", new Range(0,51), countPerGroup),
    		new TestFilterParams("speedScore", new Range(100,200), 0),
    		new TestFilterParams("speedScore", new Range(0,51), countPerGroup),
    		new TestFilterParams("styleScore", new Range(100,200), 0),
    		new TestFilterParams("styleScore", new Range(0,51), countPerGroup),
    		new TestFilterParams("seatbeltScore", new Range(100,200), 0),
    		new TestFilterParams("seatbeltScore", new Range(0,51), countPerGroup),
    		new TestFilterParams("milesDriven", new Range(0,0), 0),
    		new TestFilterParams("milesDriven", new Range(0,Long.MAX_VALUE), countPerGroup),
    };

    TestFilterParams[] vehicleFilterTestList = {
    		new TestFilterParams("groupID", "XXX", 0),
    		new TestFilterParams("groupID", goodGroupID.toString(), countPerGroup+1),
    		new TestFilterParams("groupName", "XXX", 0),
    		new TestFilterParams("groupName", goodGroupName, countPerGroup+1),
    		new TestFilterParams("driverName", "XXX", 0),
    		new TestFilterParams("driverName", goodGroupName, countPerGroup),
    		new TestFilterParams("vehicleName", "XXX", 0),
    		new TestFilterParams("vehicleName", goodGroupName, countPerGroup),
    		new TestFilterParams("vehicleYMM", "XXX", 0),
    		new TestFilterParams("vehicleYMM", "Make", countPerGroup+1),
    		new TestFilterParams("overallScore", new Range(100,200), 0),
    		new TestFilterParams("overallScore", new Range(0,51), countPerGroup+1),
    		new TestFilterParams("speedScore", new Range(100,200), 0),
    		new TestFilterParams("speedScore", new Range(0,51), countPerGroup+1),
    		new TestFilterParams("styleScore", new Range(100,200), 0),
    		new TestFilterParams("styleScore", new Range(0,51), countPerGroup+1),
    		new TestFilterParams("milesDriven", new Range(0,0), 0),
    		new TestFilterParams("milesDriven", new Range(0,Long.MAX_VALUE), countPerGroup+1),
    		new TestFilterParams("odometer", new Range(100,200), 0),
    		new TestFilterParams("odometer", new Range(0,Long.MAX_VALUE), countPerGroup+1),
    };

    TestFilterParams[] deviceFilterTestList = {
    		
    		new TestFilterParams("deviceName", "XXX", 0),
    		new TestFilterParams("deviceName", goodGroupName, countPerGroup+1),
    		new TestFilterParams("vehicleName", "XXX", 0),
    		new TestFilterParams("vehicleName", goodGroupName, countPerGroup),
    		new TestFilterParams("deviceIMEI", "XXX", 0),
    		new TestFilterParams("deviceIMEI", ""+goodGroupID, countPerGroup+1),
    		new TestFilterParams("devicePhone", "XXX", 0),
    		new TestFilterParams("devicePhone", ""+goodGroupID, countPerGroup+1),
// TODO: Did ephone go away on device?  If so, it should also be removed from the devices report    		
//    		new TestFilterParams("deviceEPhone", "XXX", 0),
//    		new TestFilterParams("deviceEPhone", "9876", countPerGroup+1),
    		new TestFilterParams("deviceStatus", DeviceStatus.INACTIVE.getCode(), 0),
    		new TestFilterParams("deviceStatus", DeviceStatus.ACTIVE.getCode(), countPerGroup+1),
    };

    TestFilterParams[] idlingFilterTestList = {
    		new TestFilterParams("groupName", "XXX", 0),
    		new TestFilterParams("groupName", badGroupName, countPerGroup),
    		new TestFilterParams("driverName", "XXX", 0),
    		new TestFilterParams("driverName", badGroupName, countPerGroup),
    		new TestFilterParams("driveTime", new Range(0,0), 0),
    		new TestFilterParams("driveTime", new Range(0,Long.MAX_VALUE), countPerGroup),
    		new TestFilterParams("lowIdleTime", new Range(0,0), 0),
    		new TestFilterParams("lowIdleTime", new Range(0,Long.MAX_VALUE), countPerGroup),
    		new TestFilterParams("highIdleTime", new Range(0,0), 0),
    		new TestFilterParams("highIdleTime", new Range(0,Long.MAX_VALUE), countPerGroup),
    };
    

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        IntegrationConfig config = new IntegrationConfig();

        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());

        siloService = new SiloServiceCreator(host, port).getService();

        initApp();
        itData = new ITDataExt();

        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(PAGINATION_BASE_DATA_XML);
        countPerGroup = DataGenForReportPaginationTesting.NUM_DRIVERS_VEHICLES_DEVICES;
        if (!itData.parseTestDataExt(stream, siloService, countPerGroup)) {
            throw new Exception("Error parsing Test data xml file");
        }

        AccountHessianDAO accountDAO = new AccountHessianDAO();
        accountDAO.setSiloService(siloService);
        Account account = accountDAO.findByID(itData.account.getAccountID());
        itData.account.setUnkDriverID(account.getUnkDriverID());


        goodGroupID = itData.teamGroupListData.get(ITData.GOOD).group.getGroupID();
        goodGroupName = itData.teamGroupListData.get(ITData.GOOD).group.getName();
        badGroupName = itData.teamGroupListData.get(ITData.BAD).group.getName();
        
  }

    private static void initApp() throws Exception {
        StateHessianDAO stateDAO = new StateHessianDAO();
        stateDAO.setSiloService(siloService);

        States states = new States();
        states.setStateDAO(stateDAO);
        states.init();

        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);

//        DeviceSensitivityMapping mapping = new DeviceSensitivityMapping();
//        mapping.setDeviceDAO(deviceDAO);
//        mapping.init();

    }

    @Test
    public void drivers() {
    	ReportHessianDAO reportDAO = new ReportHessianDAO();
    	reportDAO.setSiloService(siloService);
        DriverPerformanceMapper driverPerformanceMapper = new DriverPerformanceMapper();
        DriverHessianDAO driverDAO = new DriverHessianDAO();
        driverDAO.setSiloService(siloService);
        driverPerformanceMapper.setDriverDAO(driverDAO);
        reportDAO.setDriverPerformanceMapper(driverPerformanceMapper);
    	
    	// no filters
    	for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {

    		GroupListData team = itData.teamGroupListData.get(teamIdx);
    		Integer driverCount = reportDAO.getDriverReportCount(team.group.getGroupID(), null);
    		assertEquals("Unexpected driver count for team " + teamIdx, countPerGroup, driverCount);

    		// get all
    		PageParams pageParams = new PageParams();
    		pageParams.setStartRow(0);
    		pageParams.setEndRow(driverCount-1);
    		List<DriverReportItem> driverList = reportDAO.getDriverReportPage(team.group.getGroupID(), pageParams);
    		assertEquals("Unexpected driver count for team " + teamIdx, countPerGroup, Integer.valueOf(driverList.size()));
    		// check some of the field values
    		for (DriverReportItem item : driverList) {
    			String expectedDriverName = "Driver"+team.group.getName() + " m Last" +team.group.getGroupID()+ " jr";
    			assertEquals("driver Name", expectedDriverName, getItemBaseName(item.getDriverName(), "Driver"));
    			String expectedGroupName = team.group.getName();
    			assertEquals("group Name", expectedGroupName, item.getGroupName());
    			String expectedVehicleName = "Vehicle"+ team.group.getName();
    			assertEquals("vehicle Name", expectedVehicleName, getItemBaseName(item.getVehicleName(), "Vehicle"));
    			
    			// scores
    			assertTrue("overall score", (item.getOverallScore() >= 0 && item.getOverallScore() <= 50));
    			assertTrue("speed score", (item.getSpeedScore() >= 0 && item.getSpeedScore() <= 50));
    			assertTrue("style score", (item.getStyleScore() >= 0 && item.getStyleScore() <= 50));
    			assertTrue("seatbelt score", (item.getSeatbeltScore() >= 0 && item.getSeatbeltScore() <= 50));
    			
    			
    		}
    		// get some
    		if (driverCount > 1) {
	    		pageParams.setStartRow(1);
	    		pageParams.setEndRow(1);
	    		driverList = reportDAO.getDriverReportPage(team.group.getGroupID(), pageParams);
	    		assertEquals("Unexpected partial driver list count for team " + teamIdx, 1, driverList.size());
    		}
    		
    		
    		
    	}
		// filter
		GroupListData team = itData.teamGroupListData.get(ITData.GOOD);
		Integer allDriverCount = reportDAO.getDriverReportCount(team.group.getGroupID(), null);
		for (TestFilterParams testFilterParams : driverFilterTestList) {
			PageParams pageParams = new PageParams();
			pageParams.setStartRow(0);
			pageParams.setEndRow(allDriverCount-1);
			List<TableFilterField> filterList = new ArrayList<TableFilterField>();
			filterList.add(new TableFilterField(testFilterParams.field, testFilterParams.value));
			pageParams.setFilterList(filterList);
			Integer driverCount = reportDAO.getDriverReportCount(team.group.getGroupID(), filterList);
			assertEquals("Unexpected driver count for " +  testFilterParams.field + "=" + testFilterParams.value + " filter", testFilterParams.expectedCount, driverCount);
			pageParams.setFilterList(filterList);
			List<DriverReportItem> list = reportDAO.getDriverReportPage(team.group.getGroupID(), pageParams);
			assertEquals("Unexpected driver count for " +  testFilterParams.field + "=" + testFilterParams.value + " filter", testFilterParams.expectedCount, Integer.valueOf(list.size()));
		}

    	// sort
		Integer driverCount = reportDAO.getDriverReportCount(itData.fleetGroup.getGroupID(), null);
		PageParams pageParams = new PageParams();
		pageParams.setStartRow(0);
		pageParams.setEndRow(driverCount-1);
		pageParams.setSort(new TableSortField(SortOrder.ASCENDING, "groupName"));
		
		List<DriverReportItem> driverList = reportDAO.getDriverReportPage(itData.fleetGroup.getGroupID(), pageParams);
		String lastGroupName = null;
		for (DriverReportItem item : driverList) {
			if (lastGroupName == null) {
				lastGroupName = item.getGroupName();
				continue;
			}
			assertTrue("group Name", (lastGroupName.compareToIgnoreCase(item.getGroupName()) <= 0) );
		}
		
    	
	}
    
    private Object getItemBaseName(String itemName, String search) {
		int beginIndex = itemName.indexOf(search);
		return itemName.substring(beginIndex);
	}

	@Test
    public void vehicles() {
    	ReportHessianDAO reportDAO = new ReportHessianDAO();
    	reportDAO.setSiloService(siloService);
    	
    	// no filters
    	for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {

    		GroupListData team = itData.teamGroupListData.get(teamIdx);
    		Integer vehicleCount = reportDAO.getVehicleReportCount(team.group.getGroupID(), null);
    		Integer expectedCount = countPerGroup + (teamIdx == ITData.GOOD ? 1 : 0);  // add one for unknown driver vehicle
    		assertEquals("Unexpected vehicle count for team " + teamIdx, expectedCount, vehicleCount);

    		// get all
    		PageParams pageParams = new PageParams();
    		pageParams.setStartRow(0);
    		pageParams.setEndRow(vehicleCount-1);
    		List<VehicleReportItem> vehicleList = reportDAO.getVehicleReportPage(team.group.getGroupID(), pageParams);
    		assertEquals("Unexpected vehicle count for team " + teamIdx, expectedCount, Integer.valueOf(vehicleList.size()));
    		// check some of the field values
    		for (VehicleReportItem item : vehicleList) {
    			if (item.getDriverID().equals(itData.account.getUnkDriverID())) {
    				// skip unknown driver
    				continue;
    			}
    			String expectedDriverName = "Driver"+team.group.getName() + " m Last" +team.group.getGroupID()+ " jr";
    			
    			assertEquals("driver Name", expectedDriverName, getItemBaseName(item.getDriverName(), "Driver"));
    			String expectedGroupName = team.group.getName();
    			assertEquals("group Name", expectedGroupName, item.getGroupName());
    			String expectedVehicleName = "Vehicle"+ team.group.getName();
    			assertEquals("vehicle Name", expectedVehicleName, getItemBaseName(item.getVehicleName(), "Vehicle"));
    			
    			// scores
    			assertTrue("overall score", (item.getOverallScore() >= 0 && item.getOverallScore() <= 50));
    			assertTrue("speed score", (item.getSpeedScore() >= 0 && item.getSpeedScore() <= 50));
    			assertTrue("style score", (item.getStyleScore() >= 0 && item.getStyleScore() <= 50));
    			
    			
    		}
    		// get some
    		if (vehicleCount > 1) {
	    		pageParams.setStartRow(1);
	    		pageParams.setEndRow(1);
	    		vehicleList = reportDAO.getVehicleReportPage(team.group.getGroupID(), pageParams);
	    		assertEquals("Unexpected partial vehicle list count for team " + teamIdx, 1, vehicleList.size());
    		}
    		
    		
    		
    		
    	}
		// filter
		GroupListData team = itData.teamGroupListData.get(ITData.GOOD);
		Integer allVehicleCount = reportDAO.getVehicleReportCount(team.group.getGroupID(), null);
		for (TestFilterParams testFilterParams : vehicleFilterTestList) {
			PageParams pageParams = new PageParams();
			pageParams.setStartRow(0);
			pageParams.setEndRow(allVehicleCount-1);
			List<TableFilterField> filterList = new ArrayList<TableFilterField>();
			filterList.add(new TableFilterField(testFilterParams.field, testFilterParams.value));
			pageParams.setFilterList(filterList);
			Integer vehicleCount = reportDAO.getVehicleReportCount(team.group.getGroupID(), filterList);
			assertEquals("Unexpected vehicle count for " +  testFilterParams.field + "=" + testFilterParams.value + " filter", testFilterParams.expectedCount, vehicleCount);
			pageParams.setFilterList(filterList);
			List<VehicleReportItem> list = reportDAO.getVehicleReportPage(team.group.getGroupID(), pageParams);
			assertEquals("Unexpected vehicle count for " +  testFilterParams.field + "=" + testFilterParams.value + " filter", testFilterParams.expectedCount, Integer.valueOf(list.size()));
		}

    	// sort
		Integer vehicleCount = reportDAO.getVehicleReportCount(itData.fleetGroup.getGroupID(), null);
		PageParams pageParams = new PageParams();
		pageParams.setStartRow(0);
		pageParams.setEndRow(vehicleCount-1);
		pageParams.setSort(new TableSortField(SortOrder.ASCENDING, "groupName"));
		
		List<VehicleReportItem> vehicleList = reportDAO.getVehicleReportPage(itData.fleetGroup.getGroupID(), pageParams);
		String lastGroupName = null;
		for (VehicleReportItem item : vehicleList) {
			if (lastGroupName == null) {
				lastGroupName = item.getGroupName();
				continue;
			}
			assertTrue("group Name", (lastGroupName.compareToIgnoreCase(item.getGroupName()) <= 0) );
		}
		
    	
	}

    @Test
    public void devices() {
    	ReportHessianDAO reportDAO = new ReportHessianDAO();
    	reportDAO.setSiloService(siloService);
    	
    	// no filters
    	for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {

    		GroupListData team = itData.teamGroupListData.get(teamIdx);
    		Integer deviceCount = reportDAO.getDeviceReportCount(team.group.getGroupID(), null);
    		// +1 because of device assigned to vehicle for unknown driver
    		
    		Integer expectedDeviceCount = countPerGroup;
    		if (teamIdx == ITData.GOOD)
    			expectedDeviceCount++;
    		
    		assertEquals("Unexpected device count for team " + teamIdx, expectedDeviceCount, deviceCount);

    		// get all
    		PageParams pageParams = new PageParams();
    		pageParams.setStartRow(0);
    		pageParams.setEndRow(deviceCount-1);
    		List<DeviceReportItem> deviceList = reportDAO.getDeviceReportPage(team.group.getGroupID(), pageParams);
    		assertEquals("Unexpected device count for team " + teamIdx, expectedDeviceCount, Integer.valueOf(deviceList.size()));
    		// check some of the field values
    		for (DeviceReportItem item : deviceList) {
    			String expectedVehicleName = "Vehicle"+ team.group.getName();
    			if (item.getVehicleName().contains("NO_DRIVER"))
    				continue;
    			assertEquals("vehicle Name", expectedVehicleName, getItemBaseName(item.getVehicleName(), "Vehicle"));
    			
    			String expectedDeviceName = team.group.getName() + "Device";
    			assertEquals("device Name", expectedDeviceName, item.getDeviceName());
    			
    		}
    		// get some
    		if (deviceCount > 1) {
	    		pageParams.setStartRow(1);
	    		pageParams.setEndRow(1);
	    		deviceList = reportDAO.getDeviceReportPage(team.group.getGroupID(), pageParams);
	    		assertEquals("Unexpected partial device list count for team " + teamIdx, 1, deviceList.size());
    		}
    		
    		
    	}
		// filter
		GroupListData team = itData.teamGroupListData.get(ITData.GOOD);
		Integer allDeviceCount = reportDAO.getDeviceReportCount(team.group.getGroupID(), null);
		for (TestFilterParams testFilterParams : deviceFilterTestList) {
			PageParams pageParams = new PageParams();
			pageParams.setStartRow(0);
			pageParams.setEndRow(allDeviceCount-1);
			List<TableFilterField> filterList = new ArrayList<TableFilterField>();
			filterList.add(new TableFilterField(testFilterParams.field, testFilterParams.value));
			pageParams.setFilterList(filterList);
			Integer deviceCount = reportDAO.getDeviceReportCount(team.group.getGroupID(), filterList);
			assertEquals("Unexpected device count for " +  testFilterParams.field + "=" + testFilterParams.value + " filter", testFilterParams.expectedCount, deviceCount);
			List<DeviceReportItem> list = reportDAO.getDeviceReportPage(team.group.getGroupID(), pageParams);
			assertEquals("Unexpected device count for " +  testFilterParams.field + "=" + testFilterParams.value + " filter", testFilterParams.expectedCount, Integer.valueOf(list.size()));
		}

    	// sort
		Integer deviceCount = reportDAO.getDeviceReportCount(itData.fleetGroup.getGroupID(), null);
		PageParams pageParams = new PageParams();
		pageParams.setStartRow(0);
		pageParams.setEndRow(deviceCount-1);
		pageParams.setSort(new TableSortField(SortOrder.ASCENDING, "deviceName"));
		
		List<DeviceReportItem> deviceList = reportDAO.getDeviceReportPage(itData.fleetGroup.getGroupID(), pageParams);
		String lastDeviceName = null;
		for (DeviceReportItem item : deviceList) {
			if (lastDeviceName == null) {
				lastDeviceName = item.getDeviceName();
				continue;
			}
			assertTrue("Device Name", (lastDeviceName.compareToIgnoreCase(item.getDeviceName()) <= 0) );
		}
		
    	
	}
    
    @Test
    @Ignore //TODO: jwimmer: test ignored until it is clear how to handle the idling time for "unknown Driver" on the reports page.
    public void idling() {
    	ReportHessianDAO reportDAO = new ReportHessianDAO();
    	reportDAO.setSiloService(siloService);
    	
	    DateTimeZone dateTimeZone = DateTimeZone.forID(ReportTestConst.TIMEZONE_STR);
        Interval interval = new Interval(new DateMidnight(new DateTime().minusDays(7), dateTimeZone), new DateMidnight(new DateTime(), dateTimeZone));
        

    	// no filters
    	for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {

    		GroupListData team = itData.teamGroupListData.get(teamIdx);
    		Integer idlingCount = reportDAO.getIdlingReportCount(team.group.getGroupID(), interval, null);
    		assertEquals("Unexpected idling count for team " + teamIdx, countPerGroup, idlingCount);

    		Integer idlingSupportCount = reportDAO.getIdlingReportSupportsIdleStatsCount(team.group.getGroupID(), interval, null);
    		assertEquals("Unexpected idling support stats count for team " + teamIdx, countPerGroup, idlingSupportCount);

    		// get all
    		PageParams pageParams = new PageParams();
    		pageParams.setStartRow(0);
    		pageParams.setEndRow(idlingCount-1);
    		List<IdlingReportItem> idlingList = reportDAO.getIdlingReportPage(team.group.getGroupID(), interval, pageParams);
    		assertEquals("Unexpected idling count for team " + teamIdx, countPerGroup, Integer.valueOf(idlingList.size()));
    		// check some of the field values
    		for (IdlingReportItem item : idlingList) {
    			String expectedDriverName = "Driver"+team.group.getName() + " m Last" +team.group.getGroupID()+ " jr";
    			assertEquals("driver Name", expectedDriverName, getItemBaseName(item.getDriverName(), "Driver"));
    			
    		}
    		// get some
    		if (idlingCount > 1) {
	    		pageParams.setStartRow(1);
	    		pageParams.setEndRow(1);
	    		idlingList = reportDAO.getIdlingReportPage(team.group.getGroupID(), interval, pageParams);
	    		assertEquals("Unexpected partial idling list count for team " + teamIdx, 1, idlingList.size());
    		}
    	}
		// filter
		GroupListData team = itData.teamGroupListData.get(ITData.BAD);
		Integer allIdleCount = reportDAO.getIdlingReportCount(team.group.getGroupID(), interval, null);
		for (TestFilterParams testFilterParams : idlingFilterTestList) {
			PageParams pageParams = new PageParams();
			pageParams.setStartRow(0);
			pageParams.setEndRow(allIdleCount-1);
			List<TableFilterField> filterList = new ArrayList<TableFilterField>();
			filterList.add(new TableFilterField(testFilterParams.field, testFilterParams.value));
			pageParams.setFilterList(filterList);
			Integer idlingCount = reportDAO.getIdlingReportCount(team.group.getGroupID(), interval, filterList);
			assertEquals("Unexpected idling count for " +  testFilterParams.field + "=" + testFilterParams.value + " filter", testFilterParams.expectedCount, idlingCount);
			List<IdlingReportItem> list = reportDAO.getIdlingReportPage(team.group.getGroupID(), interval, pageParams);
			assertEquals("Unexpected vehicle count for " +  testFilterParams.field + "=" + testFilterParams.value + " filter", testFilterParams.expectedCount, Integer.valueOf(list.size()));
		}

    	// sort
		Integer idlingCount = reportDAO.getIdlingReportCount(itData.fleetGroup.getGroupID(), interval, null);
		PageParams pageParams = new PageParams();
		pageParams.setStartRow(0);
		pageParams.setEndRow(idlingCount-1);
		pageParams.setSort(new TableSortField(SortOrder.ASCENDING, "driverName"));
		
		List<IdlingReportItem> idlingList = reportDAO.getIdlingReportPage(itData.fleetGroup.getGroupID(), interval, pageParams);
		String lastDriverName = null;
		for (IdlingReportItem item : idlingList) {
			if (lastDriverName == null) {
				lastDriverName = item.getDriverName();
				continue;
			}
			assertTrue("Driver Name", (lastDriverName.compareToIgnoreCase(item.getDriverName()) <= 0) );
		}
		
    	
	}
    class TestFilterParams {
		String field;
    	Object value;
    	Integer expectedCount;
    	
    	public TestFilterParams(String field, Object value, Integer expectedCount) {
			super();
			this.field = field;
			this.value = value;
			this.expectedCount = expectedCount;
		}
    }
}

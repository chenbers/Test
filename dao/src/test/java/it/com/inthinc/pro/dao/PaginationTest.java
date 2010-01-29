package it.com.inthinc.pro.dao;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import it.com.inthinc.pro.dao.model.GroupData;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.IntegrationConfig;
import it.config.ReportTestConst;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.hessian.DeviceHessianDAO;
import com.inthinc.pro.dao.hessian.EventHessianDAO;
import com.inthinc.pro.dao.hessian.RedFlagHessianDAO;
import com.inthinc.pro.dao.hessian.RoleHessianDAO;
import com.inthinc.pro.dao.hessian.StateHessianDAO;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventCategory;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.RedFlag;
import com.inthinc.pro.model.app.DeviceSensitivityMapping;
import com.inthinc.pro.model.app.Roles;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;
import com.inthinc.pro.model.pagination.TableSortField;
import com.inthinc.pro.model.pagination.TableSortField.SortOrder;


public class PaginationTest {
	
    private static final Logger logger = Logger.getLogger(PaginationTest.class);
    private static SiloService siloService;
    private static ITData itData;
    
    private static final String PAGINATION_BASE_DATA_XML = "PageTest.xml";
    private static final int FLEET_IDX = 3;    
    // the expected counts come from the events generated daily by DataGenForPaginationTesting
    // that process is run via a cron job in Hudson daily
    private static Map<EventCategory, Integer[]> EXPECTED_EVENT_COUNTS;
    static {
    	EXPECTED_EVENT_COUNTS = new HashMap<EventCategory, Integer[]> ();
    	// events in team 0 (GOOD) are from the unknown driver
    	EXPECTED_EVENT_COUNTS.put(EventCategory.VIOLATION, new Integer[] {Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(15), Integer.valueOf(21)});
    	EXPECTED_EVENT_COUNTS.put(EventCategory.WARNING, new Integer[] {Integer.valueOf(4), Integer.valueOf(4), Integer.valueOf(8), Integer.valueOf(16)});
    	EXPECTED_EVENT_COUNTS.put(EventCategory.EMERGENCY, new Integer[] {Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(1)});
    	
    }

    private static Integer[] EXPECTED_RED_FLAG_COUNTS = {
    	Integer.valueOf(7),
    	Integer.valueOf(7),
    	Integer.valueOf(16),
    	Integer.valueOf(30),

    };

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        IntegrationConfig config = new IntegrationConfig();

        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());

        siloService = new SiloServiceCreator(host, port).getService();

        initApp();
        itData = new ITData();

        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(PAGINATION_BASE_DATA_XML);
        if (!itData.parseTestData(stream, siloService, true, true)) {
            throw new Exception("Error parsing Test data xml file");
        }

        Integer todayInSec = DateUtil.getDaysBackDate(DateUtil.getTodaysDate(), 0, ReportTestConst.TIMEZONE_STR);
        
  }

    private static void initApp() throws Exception {
        StateHessianDAO stateDAO = new StateHessianDAO();
        stateDAO.setSiloService(siloService);

        States states = new States();
        states.setStateDAO(stateDAO);
        states.init();

        RoleHessianDAO roleDAO = new RoleHessianDAO();
        roleDAO.setSiloService(siloService);

        Roles roles = new Roles();
        roles.setRoleDAO(roleDAO);
        roles.init();

        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);

        DeviceSensitivityMapping mapping = new DeviceSensitivityMapping();
        mapping.setDeviceDAO(deviceDAO);
        mapping.init();

    }
    
    @Ignore
    @Test
    public void events() {
    	EventHessianDAO eventDAO = new EventHessianDAO();
    	eventDAO.setSiloService(siloService);
    	
    	// no filters
    	for (EventCategory category : EXPECTED_EVENT_COUNTS.keySet()) {
	    	for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {
	    		int daysBack = 1;
	    		GroupData team = itData.teamGroupData.get(teamIdx);
	    		Integer eventCount = eventDAO.getEventCount(team.group.getGroupID(), daysBack, EventDAO.INCLUDE_FORGIVEN, EventMapper.getEventTypesInCategory(category), null);
	    		assertEquals("Unexpected event count for team " + teamIdx + " category " + category, EXPECTED_EVENT_COUNTS.get(category)[teamIdx], eventCount);

	    		// get all
	    		PageParams pageParams = new PageParams();
	    		pageParams.setStartRow(0);
	    		pageParams.setEndRow(eventCount);
	    		List<Event> eventList = eventDAO.getEventPage(team.group.getGroupID(), daysBack, EventDAO.INCLUDE_FORGIVEN, EventMapper.getEventTypesInCategory(category), pageParams);
	    		assertEquals("Unexpected event list count for team " + teamIdx + " category " + category, EXPECTED_EVENT_COUNTS.get(category)[teamIdx], Integer.valueOf(eventList.size()));
	    		
	    		// check some of the field values
	    		for (Event event : eventList) {
	    			if (teamIdx == ITData.GOOD) {
		    			// all events are UNKNOWN DRIVER
	    				assertNull("unknown driver timezone", event.getDriverTimeZone());
	    				assertNull("unknown driver name", event.getDriverFullName());
		    			assertNull("unknown driver group Name", event.getGroupName());
	    			}
	    			else {
		    			assertEquals("driver timezone", ReportTestConst.timeZone, event.getDriverTimeZone());
		    			String expectedDriverName = "Driver"+team.group.getName();
		    			assertEquals("driver fullName", expectedDriverName, event.getDriverFullName());
		    			String expectedGroupName = team.group.getName();
		    			assertEquals("group Name", expectedGroupName, event.getGroupName());
	    			}
	    			String expectedVehicleName = "Vehicle"+team.group.getName();
	    			assertEquals("vehicle Name", expectedVehicleName, event.getVehicleName());
	    		}
	    		
	    		// get some
	    		if (eventCount > 3) {
		    		pageParams.setStartRow(1);
		    		pageParams.setEndRow(3);
		    		eventList = eventDAO.getEventPage(team.group.getGroupID(), daysBack, EventDAO.INCLUDE_FORGIVEN, EventMapper.getEventTypesInCategory(category), pageParams);
		    		assertEquals("Unexpected partial event list count for team " + teamIdx + " category " + category, 2, eventList.size());
	    		}
	    	}
    	}
    }
    
    @Ignore
    @Test
    public void eventsSorts() {
    	EventHessianDAO eventDAO = new EventHessianDAO();
    	eventDAO.setSiloService(siloService);
    	
    	// no filters
    	for (EventCategory category : EXPECTED_EVENT_COUNTS.keySet()) {
	    	for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {
	    		int daysBack = 1;
	    		GroupData team = itData.teamGroupData.get(teamIdx);
	    		Integer eventCount = eventDAO.getEventCount(team.group.getGroupID(), daysBack, EventDAO.INCLUDE_FORGIVEN, EventMapper.getEventTypesInCategory(category), null);
	    		assertEquals("Unexpected event count for team " + teamIdx + " category " + category, EXPECTED_EVENT_COUNTS.get(category)[teamIdx], eventCount);
	
	    		// get all
	    		PageParams pageParams = new PageParams();
	    		pageParams.setStartRow(0);
	    		pageParams.setEndRow(eventCount);
	    		
	    		for (SortOrder sortOrder : SortOrder.values()) {
	        		// sort by time
		    		pageParams.setSort(new TableSortField(sortOrder, "time"));
		    		List<Event> eventList = eventDAO.getEventPage(team.group.getGroupID(), daysBack, EventDAO.INCLUDE_FORGIVEN, EventMapper.getEventTypesInCategory(category), pageParams);
		    		assertEquals("Unexpected event list count for team " + teamIdx + " category " + category, EXPECTED_EVENT_COUNTS.get(category)[teamIdx], Integer.valueOf(eventList.size()));
		    		
		    		Date lastTime = null;
		    		for (Event event : eventList) {
		    			if (lastTime == null) {
		    				lastTime = event.getTime();
		    				continue;
		    			}
		    			assertTrue("sort " + sortOrder + " time failed ", 
		    					((sortOrder.equals(SortOrder.ASCENDING) && lastTime.compareTo(event.getTime()) <= 0) ||
		    					 (sortOrder.equals(SortOrder.DESCENDING) && lastTime.compareTo(event.getTime()) >= 0)) );
		    			lastTime = event.getTime();
		    		}
	
		    		// sort by groupName
		    		pageParams.setSort(new TableSortField(sortOrder, "groupName"));
		    		eventList = eventDAO.getEventPage(team.group.getGroupID(), daysBack, EventDAO.INCLUDE_FORGIVEN, EventMapper.getEventTypesInCategory(category), pageParams);
		    		assertEquals("Unexpected event list count category " + category, EXPECTED_EVENT_COUNTS.get(category)[teamIdx], Integer.valueOf(eventList.size()));
		    		
		    		String lastGroup = null;
		    		for (Event event : eventList) {
		    			if (lastGroup == null) {
		    				lastGroup = event.getGroupName();
		    				continue;
		    			}
		    			assertTrue("sort " + sortOrder + " group failed ", 
		    					((sortOrder.equals(SortOrder.ASCENDING) && lastGroup.compareToIgnoreCase(event.getGroupName()) <= 0) ||
		    					 (sortOrder.equals(SortOrder.DESCENDING) && lastGroup.compareToIgnoreCase(event.getGroupName()) >= 0)) );
	    				lastGroup = event.getGroupName();
		    		}

		    		// sort by vehicleName
		    		pageParams.setSort(new TableSortField(sortOrder, "vehicleName"));
		    		eventList = eventDAO.getEventPage(team.group.getGroupID(), daysBack, EventDAO.INCLUDE_FORGIVEN, EventMapper.getEventTypesInCategory(category), pageParams);
		    		assertEquals("Unexpected event list count category " + category, EXPECTED_EVENT_COUNTS.get(category)[teamIdx], Integer.valueOf(eventList.size()));
		    		
		    		String lastVehicle = null;
		    		for (Event event : eventList) {
		    			if (lastVehicle == null) {
		    				lastVehicle = event.getVehicleName();
		    				continue;
		    			}
		    			assertTrue("sort " + sortOrder + " vehicle failed ", 
		    					((sortOrder.equals(SortOrder.ASCENDING) && lastVehicle.compareToIgnoreCase(event.getVehicleName()) <= 0) ||
		    					 (sortOrder.equals(SortOrder.DESCENDING) && lastVehicle.compareToIgnoreCase(event.getVehicleName()) >= 0)) );
	    				lastVehicle = event.getVehicleName();
		    		}

		    		// sort by driverName
		    		pageParams.setSort(new TableSortField(sortOrder, "driverName"));
		    		eventList = eventDAO.getEventPage(team.group.getGroupID(), daysBack, EventDAO.INCLUDE_FORGIVEN, EventMapper.getEventTypesInCategory(category), pageParams);
		    		assertEquals("Unexpected event list count category " + category, EXPECTED_EVENT_COUNTS.get(category)[teamIdx], Integer.valueOf(eventList.size()));
		    		
		    		String lastDriver = null;
		    		for (Event event : eventList) {
		    			if (lastDriver == null) {
		    				lastDriver = event.getDriverFullName();
		    				continue;
		    			}
		    			assertTrue("sort " + sortOrder + " driver failed ", 
		    					((sortOrder.equals(SortOrder.ASCENDING) && lastDriver.compareToIgnoreCase(event.getDriverFullName()) <= 0) ||
		    					 (sortOrder.equals(SortOrder.DESCENDING) && lastDriver.compareToIgnoreCase(event.getDriverFullName()) >= 0)) );
	    				lastDriver = event.getDriverFullName();
		    		}
	    		}
		    }
		}
    }

    @Ignore
    @Test
    public void eventsFilters() {
    	EventHessianDAO eventDAO = new EventHessianDAO();
    	eventDAO.setSiloService(siloService);

    	for (EventCategory category : EXPECTED_EVENT_COUNTS.keySet()) {
    		
    		int daysBack = 1;
    		Integer allEventCount = eventDAO.getEventCount(itData.fleetGroup.getGroupID(), daysBack, EventDAO.INCLUDE_FORGIVEN, EventMapper.getEventTypesInCategory(category), null);
    		assertEquals("Unexpected event count for fleet  category " + category, EXPECTED_EVENT_COUNTS.get(category)[FLEET_IDX], allEventCount);
    		
	    	for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {
	    		
	    		// filter by groupName
	    		GroupData team = itData.teamGroupData.get(teamIdx);
	    		List<TableFilterField> filterList = new ArrayList<TableFilterField>();
	    		filterList.add(new TableFilterField("groupName", ITData.TEAM_GROUP_NAME[teamIdx]));
	    		
	    		Integer eventCount = eventDAO.getEventCount(itData.fleetGroup.getGroupID(), daysBack, EventDAO.INCLUDE_FORGIVEN, EventMapper.getEventTypesInCategory(category), filterList);
	    		assertEquals("Unexpected event count for fleet category " + category + " group filter " + ITData.TEAM_GROUP_NAME[teamIdx], EXPECTED_EVENT_COUNTS.get(category)[teamIdx], eventCount);
	    		
	    		PageParams pageParams = new PageParams();
	    		pageParams.setStartRow(0);
	    		pageParams.setEndRow(eventCount);
	    		pageParams.setSort(null);

	    		pageParams.setFilterList(filterList);
	    		List<Event> eventList = eventDAO.getEventPage(team.group.getGroupID(), daysBack, EventDAO.INCLUDE_FORGIVEN, EventMapper.getEventTypesInCategory(category), pageParams);
	    		assertEquals("Unexpected event list count category " + category, EXPECTED_EVENT_COUNTS.get(category)[teamIdx], Integer.valueOf(eventList.size()));
	    		for (Event event : eventList) {
	    			assertTrue("filter group failed ", event.getGroupName().contains(ITData.TEAM_GROUP_NAME[teamIdx])); 
	    		}

	    		// in generated data driver/vehicle name has group name in it
	    		filterList.clear();
	    		filterList.add(new TableFilterField("vehicleName", ITData.TEAM_GROUP_NAME[teamIdx]));
	    		pageParams.setFilterList(filterList);
	    		eventList = eventDAO.getEventPage(team.group.getGroupID(), daysBack, EventDAO.INCLUDE_FORGIVEN, EventMapper.getEventTypesInCategory(category), pageParams);
	    		assertEquals("Unexpected event list count category " + category, EXPECTED_EVENT_COUNTS.get(category)[teamIdx], Integer.valueOf(eventList.size()));
	    		for (Event event : eventList) {
	    			assertTrue("filter group failed ", event.getVehicleName().contains(ITData.TEAM_GROUP_NAME[teamIdx])); 
	    		}

	    		filterList.clear();
	    		filterList.add(new TableFilterField("driverFullName", ITData.TEAM_GROUP_NAME[teamIdx]));
	    		pageParams.setFilterList(filterList);
	    		eventList = eventDAO.getEventPage(team.group.getGroupID(), daysBack, EventDAO.INCLUDE_FORGIVEN, EventMapper.getEventTypesInCategory(category), pageParams);
	    		assertEquals("Unexpected event list count category " + category, EXPECTED_EVENT_COUNTS.get(category)[teamIdx], Integer.valueOf(eventList.size()));
	    		for (Event event : eventList) {
	    			assertTrue("filter group failed ", event.getDriverFullName().contains(ITData.TEAM_GROUP_NAME[teamIdx])); 
	    		}

	    	}
		}
    }

    @Ignore
    @Test
    public void redFlags() {
    	RedFlagHessianDAO redFlagDAO = new RedFlagHessianDAO();
    	redFlagDAO.setSiloService(siloService);
    	
    	// no filters
    	for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {
    		int daysBack = 1;
    		GroupData team = itData.teamGroupData.get(teamIdx);
    		Integer redFlagCount = redFlagDAO.getRedFlagCount(team.group.getGroupID(), daysBack, EventDAO.INCLUDE_FORGIVEN, null);
    		assertEquals("Unexpected redFlag count for team " + teamIdx, EXPECTED_RED_FLAG_COUNTS[teamIdx], redFlagCount);

    		// get all
    		PageParams pageParams = new PageParams();
    		pageParams.setStartRow(0);
    		pageParams.setEndRow(redFlagCount);
    		List<RedFlag> redFlagList = redFlagDAO.getRedFlagPage(team.group.getGroupID(), daysBack, EventDAO.INCLUDE_FORGIVEN, pageParams);
    		assertEquals("Unexpected redFlag list count for team " + teamIdx, EXPECTED_RED_FLAG_COUNTS[teamIdx], Integer.valueOf(redFlagList.size()));
    		
    		// check some of the field values
    		for (RedFlag redFlag : redFlagList) {
    			if (teamIdx == ITData.GOOD) {
	    			// all events are UNKNOWN DRIVER
    				assertNull("unknown driver timezone", redFlag.getEvent().getDriverTimeZone());
    				assertNull("unknown driver name", redFlag.getEvent().getDriverFullName());
	    			assertNull("unknown driver group Name", redFlag.getEvent().getGroupName());
    			}
    			else {
	    			assertEquals("driver timezone", ReportTestConst.timeZone, redFlag.getEvent().getDriverTimeZone());
	    			String expectedDriverName = "Driver"+team.group.getName();
	    			assertEquals("driver fullName", expectedDriverName, redFlag.getEvent().getDriverFullName());
	    			String expectedGroupName = team.group.getName();
	    			assertEquals("group Name", expectedGroupName, redFlag.getEvent().getGroupName());
    			}
    			String expectedVehicleName = "Vehicle"+team.group.getName();
    			assertEquals("vehicle Name", expectedVehicleName, redFlag.getEvent().getVehicleName());
    		}
    		
    		// get some
    		if (redFlagCount > 3) {
	    		pageParams.setStartRow(1);
	    		pageParams.setEndRow(3);
	    		redFlagList = redFlagDAO.getRedFlagPage(team.group.getGroupID(), daysBack, EventDAO.INCLUDE_FORGIVEN, pageParams);
	    		assertEquals("Unexpected partial event list count for team " + teamIdx, 2, redFlagList.size());
    		}
    	}
	}


    @Ignore
    @Test
    public void redFlagSorts() {
    	RedFlagHessianDAO redFlagDAO = new RedFlagHessianDAO();
    	redFlagDAO.setSiloService(siloService);
    	
    	
    	// no filters
    	for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {
    		int daysBack = 1;
    		GroupData team = itData.teamGroupData.get(teamIdx);
    		Integer redFlagCount = redFlagDAO.getRedFlagCount(team.group.getGroupID(), daysBack, EventDAO.INCLUDE_FORGIVEN, null);
    		assertEquals("Unexpected redFlag count for team " + teamIdx, EXPECTED_RED_FLAG_COUNTS[teamIdx], redFlagCount);

    		// get all
    		PageParams pageParams = new PageParams();
    		pageParams.setStartRow(0);
    		pageParams.setEndRow(redFlagCount);
    		
    		for (SortOrder sortOrder : SortOrder.values()) {
        		// sort by time
	    		pageParams.setSort(new TableSortField(sortOrder, "time"));
	    		List<RedFlag> redFlagList = redFlagDAO.getRedFlagPage(team.group.getGroupID(), daysBack, EventDAO.INCLUDE_FORGIVEN, pageParams);
	    		
	    		Date lastTime = null;
	    		for (RedFlag redFlag : redFlagList) {
	    			if (lastTime == null) {
	    				lastTime = redFlag.getEvent().getTime();
	    				continue;
	    			}
	    			assertTrue("sort " + sortOrder + " time failed ", 
	    					((sortOrder.equals(SortOrder.ASCENDING) && lastTime.compareTo(redFlag.getEvent().getTime()) <= 0) ||
	    					 (sortOrder.equals(SortOrder.DESCENDING) && lastTime.compareTo(redFlag.getEvent().getTime()) >= 0)) );
	    			lastTime = redFlag.getEvent().getTime();
	    		}

	    		// sort by groupName
	    		pageParams.setSort(new TableSortField(sortOrder, "groupName"));
	    		redFlagList = redFlagDAO.getRedFlagPage(team.group.getGroupID(), daysBack, EventDAO.INCLUDE_FORGIVEN, pageParams);
	    		
	    		String lastGroup = null;
	    		for (RedFlag redFlag : redFlagList) {
	    			if (lastGroup == null) {
	    				lastGroup = redFlag.getEvent().getGroupName();
	    				continue;
	    			}
	    			assertTrue("sort " + sortOrder + " group failed ", 
	    					((sortOrder.equals(SortOrder.ASCENDING) && lastGroup.compareToIgnoreCase(redFlag.getEvent().getGroupName()) <= 0) ||
	    					 (sortOrder.equals(SortOrder.DESCENDING) && lastGroup.compareToIgnoreCase(redFlag.getEvent().getGroupName()) >= 0)) );
    				lastGroup = redFlag.getEvent().getGroupName();
	    		}

	    		// sort by vehicleName
	    		pageParams.setSort(new TableSortField(sortOrder, "vehicleName"));
	    		redFlagList = redFlagDAO.getRedFlagPage(team.group.getGroupID(), daysBack, EventDAO.INCLUDE_FORGIVEN, pageParams);
	    		
	    		String lastVehicle = null;
	    		for (RedFlag redFlag : redFlagList) {
	    			if (lastVehicle == null) {
	    				lastVehicle = redFlag.getEvent().getVehicleName();
	    				continue;
	    			}
	    			assertTrue("sort " + sortOrder + " vehicle failed ", 
	    					((sortOrder.equals(SortOrder.ASCENDING) && lastVehicle.compareToIgnoreCase(redFlag.getEvent().getVehicleName()) <= 0) ||
	    					 (sortOrder.equals(SortOrder.DESCENDING) && lastVehicle.compareToIgnoreCase(redFlag.getEvent().getVehicleName()) >= 0)) );
    				lastVehicle = redFlag.getEvent().getVehicleName();
	    		}

	    		// sort by driverName
	    		pageParams.setSort(new TableSortField(sortOrder, "driverName"));
	    		redFlagList = redFlagDAO.getRedFlagPage(team.group.getGroupID(), daysBack, EventDAO.INCLUDE_FORGIVEN, pageParams);
	    		
	    		String lastDriver = null;
	    		for (RedFlag redFlag : redFlagList) {
	    			if (lastDriver == null) {
	    				lastDriver = redFlag.getEvent().getDriverFullName();
	    				continue;
	    			}
	    			assertTrue("sort " + sortOrder + " driver failed ", 
	    					((sortOrder.equals(SortOrder.ASCENDING) && lastDriver.compareToIgnoreCase(redFlag.getEvent().getDriverFullName()) <= 0) ||
	    					 (sortOrder.equals(SortOrder.DESCENDING) && lastDriver.compareToIgnoreCase(redFlag.getEvent().getDriverFullName()) >= 0)) );
    				lastDriver = redFlag.getEvent().getDriverFullName();
	    		}
    		}
	    }
    }

    @Ignore
    @Test
    public void redFlagFilters() {

    	RedFlagHessianDAO redFlagDAO = new RedFlagHessianDAO();
    	redFlagDAO.setSiloService(siloService);
    		
		int daysBack = 1;
		Integer allRedFlagCount = redFlagDAO.getRedFlagCount(itData.fleetGroup.getGroupID(), daysBack, EventDAO.INCLUDE_FORGIVEN, null);
		assertEquals("Unexpected redFlag count for fleet", EXPECTED_RED_FLAG_COUNTS[FLEET_IDX], allRedFlagCount);
		
    	for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {
    		
    		// filter by groupName
    		GroupData team = itData.teamGroupData.get(teamIdx);
    		List<TableFilterField> filterList = new ArrayList<TableFilterField>();
    		filterList.add(new TableFilterField("groupName", ITData.TEAM_GROUP_NAME[teamIdx]));
    		
    		Integer redFlagCount = redFlagDAO.getRedFlagCount(team.group.getGroupID(), daysBack, EventDAO.INCLUDE_FORGIVEN, filterList);
    		assertEquals("Unexpected event count for fleet group filter " + ITData.TEAM_GROUP_NAME[teamIdx], EXPECTED_RED_FLAG_COUNTS[teamIdx], redFlagCount);
    		
    		PageParams pageParams = new PageParams();
    		pageParams.setStartRow(0);
    		pageParams.setEndRow(redFlagCount);
    		pageParams.setSort(null);

    		pageParams.setFilterList(filterList);
    		List<RedFlag> redFlagList = redFlagDAO.getRedFlagPage(team.group.getGroupID(), daysBack, EventDAO.INCLUDE_FORGIVEN, pageParams);
    		assertEquals("Unexpected redFlag list count", EXPECTED_RED_FLAG_COUNTS[teamIdx], Integer.valueOf(redFlagList.size()));
    		for (RedFlag redFlag : redFlagList) {
    			assertTrue("filter group failed ", redFlag.getEvent().getGroupName().contains(ITData.TEAM_GROUP_NAME[teamIdx])); 
    		}

    		// in generated data driver/vehicle name has group name in it
    		filterList.clear();
    		filterList.add(new TableFilterField("vehicleName", ITData.TEAM_GROUP_NAME[teamIdx]));
    		pageParams.setFilterList(filterList);
    		redFlagList = redFlagDAO.getRedFlagPage(team.group.getGroupID(), daysBack, EventDAO.INCLUDE_FORGIVEN, pageParams);
    		assertEquals("Unexpected redFlag list count", EXPECTED_RED_FLAG_COUNTS[teamIdx], Integer.valueOf(redFlagList.size()));
    		for (RedFlag redFlag : redFlagList) {
    			assertTrue("filter group failed ", redFlag.getEvent().getVehicleName().contains(ITData.TEAM_GROUP_NAME[teamIdx])); 
    		}

    		filterList.clear();
    		filterList.add(new TableFilterField("driverFullName", ITData.TEAM_GROUP_NAME[teamIdx]));
    		pageParams.setFilterList(filterList);
    		redFlagList = redFlagDAO.getRedFlagPage(team.group.getGroupID(), daysBack, EventDAO.INCLUDE_FORGIVEN, pageParams);
    		assertEquals("Unexpected redFlag list count", EXPECTED_RED_FLAG_COUNTS[teamIdx], Integer.valueOf(redFlagList.size()));
    		for (RedFlag redFlag : redFlagList) {
    			assertTrue("filter group failed ", redFlag.getEvent().getDriverFullName().contains(ITData.TEAM_GROUP_NAME[teamIdx])); 
    		}

    	}
    }

}

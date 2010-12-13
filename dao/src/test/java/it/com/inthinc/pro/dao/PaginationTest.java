package it.com.inthinc.pro.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.com.inthinc.pro.dao.model.GroupData;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.IntegrationConfig;
import it.config.ReportTestConst;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.hessian.DeviceHessianDAO;
import com.inthinc.pro.dao.hessian.EventHessianDAO;
import com.inthinc.pro.dao.hessian.RedFlagHessianDAO;
import com.inthinc.pro.dao.hessian.StateHessianDAO;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.RedFlag;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventCategory;
import com.inthinc.pro.model.event.EventType;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.model.event.ZoneEvent;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableFilterField;
import com.inthinc.pro.model.pagination.TableSortField;

public class PaginationTest {
	
    private static final Logger logger = Logger.getLogger(PaginationTest.class);
    private static SiloService siloService;
    private static ITData itData;
    
    private static final String PAGINATION_BASE_DATA_XML = "PageTest.xml";
//    private static final String PAGINATION_BASE_DATA_XML = "PageTest34.xml";
    private static final int FLEET_IDX = 3;    
    // the expected counts come from the events generated daily by DataGenForPaginationTesting
    // that process is run via a cron job in Hudson daily
    private static Map<EventCategory, Integer[]> EXPECTED_EVENT_COUNTS;
    static {
    	EXPECTED_EVENT_COUNTS = new HashMap<EventCategory, Integer[]> ();
    	// events in team 0 (GOOD) are from the unknown driver
    	EXPECTED_EVENT_COUNTS.put(EventCategory.VIOLATION, new Integer[] {Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(15), Integer.valueOf(28)});
    	EXPECTED_EVENT_COUNTS.put(EventCategory.WARNING, new Integer[] {Integer.valueOf(6), Integer.valueOf(5), Integer.valueOf(9), Integer.valueOf(31)});
    	EXPECTED_EVENT_COUNTS.put(EventCategory.EMERGENCY, new Integer[] {Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(10)});
    	
    }
    private static Map<EventCategory, Integer> EXPECTED_WS_EVENT_COUNTS;
    static {
        EXPECTED_WS_EVENT_COUNTS = new HashMap<EventCategory, Integer> ();
        // events in team 0 (GOOD) are from the unknown driver
        EXPECTED_WS_EVENT_COUNTS.put(EventCategory.VIOLATION, Integer.valueOf(7));
        EXPECTED_WS_EVENT_COUNTS.put(EventCategory.WARNING, Integer.valueOf(11));
        EXPECTED_WS_EVENT_COUNTS.put(EventCategory.EMERGENCY, Integer.valueOf(9));
        
    }

    private static Integer[] EXPECTED_RED_FLAG_COUNTS = {
    	Integer.valueOf(8),
    	Integer.valueOf(8),
        Integer.valueOf(21),
        Integer.valueOf(38),        // 1 from WS Group (no driver)

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
        if (!itData.parseTestData(stream, siloService, true, true, true)) {
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

        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);
    }

    @Test
    //@Ignore
    public void events() {
    	EventHessianDAO eventDAO = new EventHessianDAO();
    	eventDAO.setSiloService(siloService);
    	
    	// no filters
    	for (EventCategory category : EXPECTED_EVENT_COUNTS.keySet()) {
	    	for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {
	    		int daysBack = 1;
	    	    Date endDate = new Date();
	    	    Date startDate = DateUtil.getDaysBackDate(endDate, daysBack);

	    		GroupData team = itData.teamGroupData.get(teamIdx);
	    		Integer eventCount = eventDAO.getEventCount(team.group.getGroupID(), startDate, endDate, EventDAO.INCLUDE_FORGIVEN, category.getNoteTypesInCategory(), null);
	    		assertEquals("Unexpected event count for team " + team.group.getName() + " category " + category, EXPECTED_EVENT_COUNTS.get(category)[teamIdx], eventCount);

	    		// get all
	    		PageParams pageParams = new PageParams();
	    		pageParams.setStartRow(0);
	    		pageParams.setEndRow(eventCount-1);
	    		List<Event> eventList = eventDAO.getEventPage(team.group.getGroupID(), startDate, endDate, EventDAO.INCLUDE_FORGIVEN, category.getNoteTypesInCategory(), pageParams);
	    		assertEquals("Unexpected event list count for team " + team.group.getName() + " " + " category " + category, EXPECTED_EVENT_COUNTS.get(category)[teamIdx], Integer.valueOf(eventList.size()));
	    		
	    		// check some of the field values
	    		for (Event event : eventList) {
	    			if (teamIdx == ITData.GOOD) {
		    			// all events other than Ignition on are UNKNOWN DRIVER
	    			    // 1 ignition on is for the unknown and 1 is for the good driver
	    			    if (event.getEventType() == EventType.IGNITION_ON) {
	    			        continue;
	    			    }
    			        assertNotNull("unknown driver timezone", event.getDriverTimeZone());
    			        assertEquals("unknown driver name", "Unknown Driver", event.getDriverName());
    			        assertEquals("unknown driver group Name should match vehicles group", team.group.getName(), event.getGroupName());
    			        String expectedVehicleName = "VehicleNO_DRIVER";
    			        assertEquals("vehicle Name", expectedVehicleName, event.getVehicleName());
	    			}
	    			else {
		    			assertEquals("driver timezone", ReportTestConst.timeZone, event.getDriverTimeZone());
		    			String expectedDriverName = "Driver"+team.group.getName() + " m Last" +team.group.getGroupID()+ " jr";
		    			assertEquals("driver Name", expectedDriverName, event.getDriverName());
		    			String expectedGroupName = team.group.getName();
		    			assertEquals("group Name", expectedGroupName, event.getGroupName());
		    			String expectedVehicleName = "Vehicle"+ team.group.getName();
		    			assertEquals("vehicle Name", expectedVehicleName, event.getVehicleName());
	    			}
	    		}
	    		
	    		// get some
	    		if (eventCount > 3) {
		    		pageParams.setStartRow(1);
		    		pageParams.setEndRow(3);
		    		eventList = eventDAO.getEventPage(team.group.getGroupID(), startDate, endDate, EventDAO.INCLUDE_FORGIVEN, category.getNoteTypesInCategory(), pageParams);
		    		assertEquals("Unexpected partial event list count for team " + teamIdx + " category " + category,3, eventList.size());
	    		}
	    	}
    	}
    }
    
    @Test
    //@Ignore
    public void eventsSorts() {
    	EventHessianDAO eventDAO = new EventHessianDAO();
    	eventDAO.setSiloService(siloService);
    	
    	// no filters
    	for (EventCategory category : EXPECTED_EVENT_COUNTS.keySet()) {
	    	for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {
	    		int daysBack = 1;
	    	    Date endDate = new Date();
	    	    Date startDate = DateUtil.getDaysBackDate(endDate, daysBack);
	    		GroupData team = itData.teamGroupData.get(teamIdx);
	    		Integer eventCount = eventDAO.getEventCount(team.group.getGroupID(), startDate, endDate, EventDAO.INCLUDE_FORGIVEN, category.getNoteTypesInCategory(), null);
	    		assertEquals("Unexpected event count for team " + teamIdx + " category " + category, EXPECTED_EVENT_COUNTS.get(category)[teamIdx], eventCount);
	
	    		// get all
	    		PageParams pageParams = new PageParams();
	    		pageParams.setStartRow(0);
	    		pageParams.setEndRow(eventCount-1);
	    		
	    		for (SortOrder sortOrder : SortOrder.values()) {
	        		// sort by time
		    		pageParams.setSort(new TableSortField(sortOrder, "time"));
		    		List<Event> eventList = eventDAO.getEventPage(team.group.getGroupID(), startDate, endDate, EventDAO.INCLUDE_FORGIVEN, category.getNoteTypesInCategory(), pageParams);
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
		    		eventList = eventDAO.getEventPage(team.group.getGroupID(), startDate, endDate, EventDAO.INCLUDE_FORGIVEN, category.getNoteTypesInCategory(), pageParams);
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
		    		eventList = eventDAO.getEventPage(team.group.getGroupID(), startDate, endDate,  EventDAO.INCLUDE_FORGIVEN, category.getNoteTypesInCategory(), pageParams);
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
		    		eventList = eventDAO.getEventPage(team.group.getGroupID(), startDate, endDate,  EventDAO.INCLUDE_FORGIVEN, category.getNoteTypesInCategory(), pageParams);
		    		assertEquals("Unexpected event list count category " + category, EXPECTED_EVENT_COUNTS.get(category)[teamIdx], Integer.valueOf(eventList.size()));
		    		
		    		String lastDriver = null;
		    		for (Event event : eventList) {
		    			if (lastDriver == null) {
		    				lastDriver = event.getDriverName();
		    				continue;
		    			}
		    			assertTrue("sort " + sortOrder + " driver failed ", 
		    					((sortOrder.equals(SortOrder.ASCENDING) && lastDriver.compareToIgnoreCase(event.getDriverName()) <= 0) ||
		    					 (sortOrder.equals(SortOrder.DESCENDING) && lastDriver.compareToIgnoreCase(event.getDriverName()) >= 0)) );
	    				lastDriver = event.getDriverName();
		    		}
	    		}
		    }
		}
    }

    @Test
    //@Ignore
    public void eventsFilters() {
    	EventHessianDAO eventDAO = new EventHessianDAO();
    	eventDAO.setSiloService(siloService);

    	for (EventCategory category : EXPECTED_EVENT_COUNTS.keySet()) {
    		
    		int daysBack = 1;
    	    Date endDate = new Date();
    	    Date startDate = DateUtil.getDaysBackDate(endDate, daysBack);
    		Integer allEventCount = eventDAO.getEventCount(itData.fleetGroup.getGroupID(), startDate, endDate,  EventDAO.INCLUDE_FORGIVEN, category.getNoteTypesInCategory(), null);
    		assertEquals("Unexpected event count for fleet  category " + category, EXPECTED_EVENT_COUNTS.get(category)[FLEET_IDX], allEventCount);
    		
	    	for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {
	    		
	    		// filter by groupName
	    		GroupData team = itData.teamGroupData.get(teamIdx);
	    		List<TableFilterField> filterList = new ArrayList<TableFilterField>();
	    		filterList.add(new TableFilterField("groupName", ITData.TEAM_GROUP_NAME[teamIdx]));
	    		
	    		Integer eventCount = eventDAO.getEventCount(itData.fleetGroup.getGroupID(), startDate, endDate,  EventDAO.INCLUDE_FORGIVEN, category.getNoteTypesInCategory(), filterList);
	    		assertEquals("Unexpected event count for fleet category " + category + " group filter " + ITData.TEAM_GROUP_NAME[teamIdx], EXPECTED_EVENT_COUNTS.get(category)[teamIdx], eventCount);
	    		
	    		PageParams pageParams = new PageParams();
	    		pageParams.setStartRow(0);
	    		pageParams.setEndRow(eventCount-1);
	    		pageParams.setSort(null);

	    		pageParams.setFilterList(filterList);
	    		List<Event> eventList = eventDAO.getEventPage(team.group.getGroupID(), startDate, endDate,  EventDAO.INCLUDE_FORGIVEN, category.getNoteTypesInCategory(), pageParams);
	    		assertEquals("Unexpected event list count category " + category, EXPECTED_EVENT_COUNTS.get(category)[teamIdx], Integer.valueOf(eventList.size()));
	    		for (Event event : eventList) {
	    			assertTrue("filter group failed ", event.getGroupName().contains(ITData.TEAM_GROUP_NAME[teamIdx])); 
	    		}

	    		// in generated data driver/vehicle name has group name in it
	    		filterList.clear();
	    		String vehicleNameFilter = ITData.TEAM_GROUP_NAME[teamIdx];
	    		if (teamIdx == ITData.GOOD) {
	    		//	vehicleNameFilter = "NO_DRIVER";
	    			vehicleNameFilter = "";
	    		}
	    		filterList.add(new TableFilterField("vehicleName", vehicleNameFilter));
	    		pageParams.setFilterList(filterList);
	    		eventList = eventDAO.getEventPage(team.group.getGroupID(), startDate, endDate,  EventDAO.INCLUDE_FORGIVEN, category.getNoteTypesInCategory(), pageParams);
	    		assertEquals("Unexpected event list count category " + category, EXPECTED_EVENT_COUNTS.get(category)[teamIdx], Integer.valueOf(eventList.size()));
	    		for (Event event : eventList) {
	    			assertTrue("filter vehicle failed ", event.getVehicleName().contains(vehicleNameFilter)); 
	    		}

	    		// unknown driver
	    		if (teamIdx == ITData.GOOD)
	    			continue;
	    		filterList.clear();
	    		filterList.add(new TableFilterField("driverName", ITData.TEAM_GROUP_NAME[teamIdx]));
	    		pageParams.setFilterList(filterList);
	    		eventList = eventDAO.getEventPage(team.group.getGroupID(), startDate, endDate,  EventDAO.INCLUDE_FORGIVEN, category.getNoteTypesInCategory(), pageParams);
	    		assertEquals("Unexpected event list count category " + category, EXPECTED_EVENT_COUNTS.get(category)[teamIdx], Integer.valueOf(eventList.size()));
	    		for (Event event : eventList) {
	    			assertTrue("filter group failed ", event.getDriverName().contains(ITData.TEAM_GROUP_NAME[teamIdx])); 
	    		}

	    	}
		}
    }    	
    
    @Test
    public void eventTypeFilters() {
    	EventHessianDAO eventDAO = new EventHessianDAO();
    	eventDAO.setSiloService(siloService);

    	for (EventCategory category : EXPECTED_EVENT_COUNTS.keySet()) {
    		
    		int daysBack = 1;
    	    Date endDate = new Date();
    	    Date startDate = DateUtil.getDaysBackDate(endDate, daysBack);
    		
	    	for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {
	    		
	    		// filter by groupName
	    		GroupData team = itData.teamGroupData.get(teamIdx);
	    		List<TableFilterField> filterList = new ArrayList<TableFilterField>();
	    		filterList.add(new TableFilterField("type", category.getNoteTypesInCategory()));  
	    		
	    		Integer eventCount = eventDAO.getEventCount(team.group.getGroupID(), startDate, endDate,  EventDAO.INCLUDE_FORGIVEN, category.getNoteTypesInCategory(), filterList);
	    		assertEquals("Unexpected event count for fleet category " + category + " group filter " + ITData.TEAM_GROUP_NAME[teamIdx], EXPECTED_EVENT_COUNTS.get(category)[teamIdx], eventCount);
	    		
	    		PageParams pageParams = new PageParams();
	    		pageParams.setStartRow(0);
	    		pageParams.setEndRow(eventCount-1);
	    		pageParams.setSort(null);

	    		pageParams.setFilterList(filterList);
	    		List<Event> eventList = eventDAO.getEventPage(team.group.getGroupID(), startDate, endDate,  EventDAO.INCLUDE_FORGIVEN, category.getNoteTypesInCategory(), pageParams);
	    		assertEquals("Unexpected event list count category " + category, EXPECTED_EVENT_COUNTS.get(category)[teamIdx], Integer.valueOf(eventList.size()));

	    	}
		}
    }    	

    @Test
    //@Ignore
    public void eventsFilterByNoteID() {
       	EventHessianDAO eventDAO = new EventHessianDAO();
       	eventDAO.setSiloService(siloService);
    	
    	// filter by noteID
   		int daysBack = 7;
   	    Date endDate = new Date();
   	    Date startDate = DateUtil.getDaysBackDate(endDate, daysBack);
   		Integer allEventCount = eventDAO.getEventCount(itData.fleetGroup.getGroupID(), startDate, endDate,  EventDAO.INCLUDE_FORGIVEN, EventCategory.VIOLATION.getNoteTypesInCategory(), null);
		PageParams pageParams = new PageParams();
		pageParams.setStartRow(0);
		pageParams.setEndRow(allEventCount-1);
		pageParams.setSort(null);
		pageParams.setFilterList(null);
   		List<Event> eventList = eventDAO.getEventPage(itData.fleetGroup.getGroupID(), startDate, endDate,  EventDAO.INCLUDE_FORGIVEN, EventCategory.VIOLATION.getNoteTypesInCategory(), pageParams);
   		
    	Long filterNoteID = eventList.get(0).getNoteID();
		List<TableFilterField> filterList = new ArrayList<TableFilterField>();
		filterList.add(new TableFilterField("noteID", filterNoteID));
		pageParams.setFilterList(filterList);
		

   		Integer filteredEventCount = eventDAO.getEventCount(itData.fleetGroup.getGroupID(), startDate, endDate,  EventDAO.INCLUDE_FORGIVEN, EventCategory.VIOLATION.getNoteTypesInCategory(), filterList);
   		assertEquals("expected 1 note back on filter by noteID", 1, filteredEventCount.intValue());
		pageParams.setEndRow(filteredEventCount-1);

		List<Event> filteredEventList = eventDAO.getEventPage(itData.fleetGroup.getGroupID(), startDate, endDate,  EventDAO.INCLUDE_FORGIVEN, EventCategory.VIOLATION.getNoteTypesInCategory(), pageParams);
   		assertEquals("expected 1 note back on filter by noteID", 1, filteredEventList.size());
		
   		assertEquals("filter by noteID", eventList.get(0), filteredEventList.get(0));
		
    	
   		
    }

    @Test
    //@Ignore
    public void redFlags() {
    	RedFlagHessianDAO redFlagDAO = new RedFlagHessianDAO();
    	redFlagDAO.setSiloService(siloService);
    	
    	// no filters
    	for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {
    		int daysBack = 1;
    	    Date endDate = new Date();
    	    Date startDate = DateUtil.getDaysBackDate(endDate, daysBack);
    		GroupData team = itData.teamGroupData.get(teamIdx);
    		Integer redFlagCount = redFlagDAO.getRedFlagCount(team.group.getGroupID(), startDate, endDate,  EventDAO.INCLUDE_FORGIVEN, null);
    		assertEquals("Unexpected redFlag count for team " + teamIdx, EXPECTED_RED_FLAG_COUNTS[teamIdx], redFlagCount);

    		// get all
    		PageParams pageParams = new PageParams();
    		pageParams.setStartRow(0);
    		pageParams.setEndRow(redFlagCount-1);
    		List<RedFlag> redFlagList = redFlagDAO.getRedFlagPage(team.group.getGroupID(), startDate, endDate,  EventDAO.INCLUDE_FORGIVEN, pageParams);
    		assertEquals("Unexpected redFlag list count for team " + teamIdx, EXPECTED_RED_FLAG_COUNTS[teamIdx], Integer.valueOf(redFlagList.size()));
    		
    		// check some of the field values
    		for (RedFlag redFlag : redFlagList) {
    			if (teamIdx == ITData.GOOD) {
	    			// all events are UNKNOWN DRIVER
    				assertNotNull("unknown driver timezone", redFlag.getEvent().getDriverTimeZone());
    				assertEquals("unknown driver name", "Unknown Driver", redFlag.getEvent().getDriverName());

	    			assertEquals("unknown driver group Name should match vehicles group", team.group.getName(), redFlag.getEvent().getGroupName());
	    			String expectedVehicleName = "VehicleNO_DRIVER";
	    			assertEquals("vehicle Name", expectedVehicleName, redFlag.getEvent().getVehicleName());
    			}
    			else {
	    			assertEquals("driver timezone", ReportTestConst.timeZone, redFlag.getEvent().getDriverTimeZone());
	    			String expectedDriverName = "Driver"+team.group.getName() + " m Last" +team.group.getGroupID()+ " jr";
	    			assertEquals("driver Name", expectedDriverName, redFlag.getEvent().getDriverName());
	    			String expectedGroupName = team.group.getName();
	    			assertEquals("group Name", expectedGroupName, redFlag.getEvent().getGroupName());
	    			String expectedVehicleName = "Vehicle"+team.group.getName();
	    			assertEquals("vehicle Name", expectedVehicleName, redFlag.getEvent().getVehicleName());
    			}
    			if (redFlag.getLevel() == null) {
    				assertNotNull("Unexpected null Level for red flag with note ID " + redFlag.getEvent().getNoteID(), redFlag.getLevel());
    			}
    		}
    		
    		// get some
    		if (redFlagCount > 3) {
	    		pageParams.setStartRow(1);
	    		pageParams.setEndRow(3);
	    		redFlagList = redFlagDAO.getRedFlagPage(team.group.getGroupID(), startDate, endDate,  EventDAO.INCLUDE_FORGIVEN, pageParams);
	    		assertEquals("Unexpected partial event list count for team " + teamIdx, 3, redFlagList.size());
    		}
    	}
	}

    @Test
    //@Ignore
    public void redFlagSorts() {
    	RedFlagHessianDAO redFlagDAO = new RedFlagHessianDAO();
    	redFlagDAO.setSiloService(siloService);
    	
    	
    	// no filters
    	for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {
    		int daysBack = 1;
    	    Date endDate = new Date();
    	    Date startDate = DateUtil.getDaysBackDate(endDate, daysBack);
    		GroupData team = itData.teamGroupData.get(teamIdx);
    		Integer redFlagCount = redFlagDAO.getRedFlagCount(team.group.getGroupID(), startDate, endDate,  EventDAO.INCLUDE_FORGIVEN, null);
    		assertEquals("Unexpected redFlag count for team " + teamIdx, EXPECTED_RED_FLAG_COUNTS[teamIdx], redFlagCount);

    		// get all
    		PageParams pageParams = new PageParams();
    		pageParams.setStartRow(0);
    		pageParams.setEndRow(redFlagCount-1);
    		
    		for (SortOrder sortOrder : SortOrder.values()) {
        		// sort by time
	    		pageParams.setSort(new TableSortField(sortOrder, "time"));
	    		List<RedFlag> redFlagList = redFlagDAO.getRedFlagPage(team.group.getGroupID(), startDate, endDate,  EventDAO.INCLUDE_FORGIVEN, pageParams);
	    		
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
	    		redFlagList = redFlagDAO.getRedFlagPage(team.group.getGroupID(), startDate, endDate,  EventDAO.INCLUDE_FORGIVEN, pageParams);
	    		
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
	    		redFlagList = redFlagDAO.getRedFlagPage(team.group.getGroupID(), startDate, endDate,  EventDAO.INCLUDE_FORGIVEN, pageParams);
	    		
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
	    		redFlagList = redFlagDAO.getRedFlagPage(team.group.getGroupID(), startDate, endDate,  EventDAO.INCLUDE_FORGIVEN, pageParams);
	    		
	    		String lastDriver = null;
	    		for (RedFlag redFlag : redFlagList) {
	    			if (lastDriver == null) {
	    				lastDriver = redFlag.getEvent().getDriverName();
	    				continue;
	    			}
	    			assertTrue("sort " + sortOrder + " driver failed ", 
	    					((sortOrder.equals(SortOrder.ASCENDING) && lastDriver.compareToIgnoreCase(redFlag.getEvent().getDriverName()) <= 0) ||
	    					 (sortOrder.equals(SortOrder.DESCENDING) && lastDriver.compareToIgnoreCase(redFlag.getEvent().getDriverName()) >= 0)) );
    				lastDriver = redFlag.getEvent().getDriverName();
	    		}
    		}
	    }
    }

    @Test
    //@Ignore
    public void redFlagFilters() {

    	RedFlagHessianDAO redFlagDAO = new RedFlagHessianDAO();
    	redFlagDAO.setSiloService(siloService);
    		
		int daysBack = 1;
	    Date endDate = new Date();
	    Date startDate = DateUtil.getDaysBackDate(endDate, daysBack);
		Integer allRedFlagCount = redFlagDAO.getRedFlagCount(itData.fleetGroup.getGroupID(), startDate, endDate,  EventDAO.INCLUDE_FORGIVEN, null);
		assertEquals("Unexpected redFlag count for fleet", EXPECTED_RED_FLAG_COUNTS[FLEET_IDX], allRedFlagCount);
		
    	for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {
    		
    		// filter by groupName
    		GroupData team = itData.teamGroupData.get(teamIdx);
    		List<TableFilterField> filterList = new ArrayList<TableFilterField>();
    		filterList.add(new TableFilterField("groupName", ITData.TEAM_GROUP_NAME[teamIdx]));
    		
    		Integer redFlagCount = redFlagDAO.getRedFlagCount(team.group.getGroupID(), startDate, endDate,  EventDAO.INCLUDE_FORGIVEN, filterList);
    		assertEquals("Unexpected event count for fleet group filter " + ITData.TEAM_GROUP_NAME[teamIdx], EXPECTED_RED_FLAG_COUNTS[teamIdx], redFlagCount);
    		
    		PageParams pageParams = new PageParams();
    		pageParams.setStartRow(0);
    		pageParams.setEndRow(redFlagCount-1);
    		pageParams.setSort(null);

    		pageParams.setFilterList(filterList);
    		List<RedFlag> redFlagList = redFlagDAO.getRedFlagPage(team.group.getGroupID(), startDate, endDate,  EventDAO.INCLUDE_FORGIVEN, pageParams);
    		assertEquals("Unexpected redFlag list count", EXPECTED_RED_FLAG_COUNTS[teamIdx], Integer.valueOf(redFlagList.size()));
    		for (RedFlag redFlag : redFlagList) {
    			assertTrue("filter group failed ", redFlag.getEvent().getGroupName().contains(ITData.TEAM_GROUP_NAME[teamIdx])); 
    		}

    		// in generated data driver/vehicle name has group name in it
    		filterList.clear();
    		String vehicleNameFilter = ITData.TEAM_GROUP_NAME[teamIdx];
    		if (teamIdx == ITData.GOOD)
    			vehicleNameFilter = "NO_DRIVER";
    		filterList.add(new TableFilterField("vehicleName", vehicleNameFilter));
    		pageParams.setFilterList(filterList);
    		redFlagList = redFlagDAO.getRedFlagPage(team.group.getGroupID(), startDate, endDate,  EventDAO.INCLUDE_FORGIVEN, pageParams);
    		assertEquals("Unexpected redFlag list count", EXPECTED_RED_FLAG_COUNTS[teamIdx], Integer.valueOf(redFlagList.size()));
    		for (RedFlag redFlag : redFlagList) {
    			assertTrue("filter group failed ", redFlag.getEvent().getVehicleName().contains(vehicleNameFilter)); 
    		}

    		if (teamIdx == ITData.GOOD)
    			continue;
    		filterList.clear();
    		filterList.add(new TableFilterField("driverName", ITData.TEAM_GROUP_NAME[teamIdx]));
    		pageParams.setFilterList(filterList);
    		redFlagList = redFlagDAO.getRedFlagPage(team.group.getGroupID(), startDate, endDate,  EventDAO.INCLUDE_FORGIVEN, pageParams);
    		assertEquals("Unexpected redFlag list count", EXPECTED_RED_FLAG_COUNTS[teamIdx], Integer.valueOf(redFlagList.size()));
    		for (RedFlag redFlag : redFlagList) {
    			assertTrue("filter group failed ", redFlag.getEvent().getDriverName().contains(ITData.TEAM_GROUP_NAME[teamIdx])); 
    		}

    	}
    }

    @Test
    //@Ignore
    public void redFlagZones() {
    	RedFlagHessianDAO redFlagDAO = new RedFlagHessianDAO();
    	redFlagDAO.setSiloService(siloService);
    	
    	// no filters
    	for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {
    		int daysBack = 1;
    	    Date endDate = new Date();
    	    Date startDate = DateUtil.getDaysBackDate(endDate, daysBack);
    		GroupData team = itData.teamGroupData.get(teamIdx);
    		Integer redFlagCount = redFlagDAO.getRedFlagCount(team.group.getGroupID(), startDate, endDate,  EventDAO.INCLUDE_FORGIVEN, null);
    		//assertEquals("Unexpected redFlag count for team " + teamIdx, EXPECTED_RED_FLAG_COUNTS[teamIdx], redFlagCount);

    		// get all
    		PageParams pageParams = new PageParams();
    		pageParams.setStartRow(0);
    		pageParams.setEndRow(redFlagCount-1);
    		List<RedFlag> redFlagList = redFlagDAO.getRedFlagPage(team.group.getGroupID(), startDate, endDate,  EventDAO.INCLUDE_FORGIVEN, pageParams);
    		assertEquals("Unexpected redFlag list count for team " + teamIdx, EXPECTED_RED_FLAG_COUNTS[teamIdx], Integer.valueOf(redFlagList.size()));
    		
    		// check some of the field values
    		for (RedFlag redFlag : redFlagList) {
    			if (redFlag.getEvent() instanceof ZoneEvent) {
    				ZoneEvent zoneEvent = (ZoneEvent)redFlag.getEvent();
    				assertNotNull("zone name for zoneEvent " + zoneEvent.getNoteID() + " is null", zoneEvent.getZoneName());
    				assertNotNull("zone points for zoneEvent " + zoneEvent.getNoteID() + " is null", zoneEvent.getZonePoints());
    			}
    		}
    		
    	}
	}
    @Test
    //@Ignore
    public void wsevents() {
        EventHessianDAO eventDAO = new EventHessianDAO();
        eventDAO.setSiloService(siloService);
        
        // no filters
        for (EventCategory category : EXPECTED_WS_EVENT_COUNTS.keySet()) {
            int teamIdx = ITData.WS_GROUP;
            int daysBack = 1;
            Date endDate = new Date();
            Date startDate = DateUtil.getDaysBackDate(endDate, daysBack);

            GroupData team = itData.teamGroupData.get(teamIdx);
            Integer eventCount = eventDAO.getEventCount(team.group.getGroupID(), startDate, endDate, EventDAO.INCLUDE_FORGIVEN, category.getNoteTypesInCategory(), null);
            assertEquals("Unexpected event count for team " + team.group.getName() + " category " + category, EXPECTED_WS_EVENT_COUNTS.get(category), eventCount);

        }
    }

}

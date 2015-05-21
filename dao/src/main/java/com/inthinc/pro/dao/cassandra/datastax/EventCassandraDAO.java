package com.inthinc.pro.dao.cassandra.datastax;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.utils.Bytes;
import com.inthinc.pro.comm.parser.attrib.Attrib;
import com.inthinc.pro.comm.parser.note.NoteParser;
import com.inthinc.pro.comm.parser.note.NoteParserFactory;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.hessian.mapper.EventHessianMapper;
import com.inthinc.pro.dao.hessian.mapper.Mapper;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventCategory;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;

@SuppressWarnings("serial")
public class EventCassandraDAO extends BaseCassandraDAO implements EventDAO {
	private static Map<String, String> excludeAttribs = new HashMap<String, String>();
	static {
		excludeAttribs.put(Attrib.NOTETYPE.getFieldName(), "");
		excludeAttribs.put(Attrib.NOTETIME.getFieldName(), "");
		excludeAttribs.put(Attrib.NOTESPEED.getFieldName(), "");
		excludeAttribs.put(Attrib.NOTESPEEDLIMIT.getFieldName(), "");
		excludeAttribs.put(Attrib.NOTEODOMETER.getFieldName(), "");
	}

	public static void main(String[] args) {
		CassandraDB cassandraDB = new CassandraDB(true, "Inthinc Production", "note_prod", "cache_qa", "schlumberger-node-b-1.tiwipro.com:9160", 1, false, false);
		EventCassandraDAO eventDAO = new EventCassandraDAO(cassandraDB);
		List<NoteType> noteTypes = EventCategory.WARNING.getNoteTypesInCategory();
		noteTypes.add(NoteType.WSZONES_DEPARTURE_EX);
		// noteTypes.add(NoteType.LOCATION);
		List<Event> events = eventDAO.getEventsForVehicle(17493, new Date(1418148082000L), new Date(), noteTypes, 1);
		for (Event event : events) {
			logger.info(event + " " + event.getAttribs());
		}
	}

	SiloService siloService = null;
	private Mapper mapper = new EventHessianMapper();
	private VehicleDAO vehicleDAO;
	private DriverDAO driverDAO;

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(EventCassandraDAO.class);

	private final static String SELECT_VEHICLE_NOTES = "SELECT writeTime(value), vehicleid, time, type, method, driverid, noteid, satcomm, value FROM \"vehicleNoteTimeTypeIndex60\" WHERE vehicleid=? AND time>=? AND time<=?";
	private final static String SELECT_DRIVER_NOTES = "SELECT writeTime(value), driverid, time, type, method, vehicleid, noteid, satcomm, value FROM \"driverNoteTimeTypeIndex60\" WHERE driverid=? AND time>=? AND time<=?";
	private final static String SELECT_VEHICLE_BREADCRUMBS = "SELECT writeTime(value), vehicleid, time, method, driverid, value FROM \"vehicleBreadCrumb60\" WHERE vehicleid=? AND time>=? AND time<=?";
	private final static String SELECT_DRIVER_BREADCRUMBS = "SELECT writeTime(value), driverid, time, method, vehicleid, value FROM \"driverBreadCrumb60\" WHERE driverid=? AND time>=? AND time<=?";
	private static PreparedStatement selectVehicleNotesStatement = null;
	private static PreparedStatement selectDriverNotesStatement = null;
	private static PreparedStatement selectVehicleBCStatement = null;
	private static PreparedStatement selectDriverBCStatement = null;

	public EventCassandraDAO(CassandraDB cassandraDB) {
		setCassandraDB(cassandraDB);
		selectVehicleNotesStatement = getSession().prepare(SELECT_VEHICLE_NOTES);
		selectVehicleNotesStatement.setConsistencyLevel(getCL());
		selectDriverNotesStatement = getSession().prepare(SELECT_DRIVER_NOTES);
		selectDriverNotesStatement.setConsistencyLevel(getCL());
		selectVehicleBCStatement = getSession().prepare(SELECT_VEHICLE_BREADCRUMBS);
		selectVehicleBCStatement.setConsistencyLevel(getCL());
		selectDriverBCStatement = getSession().prepare(SELECT_DRIVER_BREADCRUMBS);
		selectDriverBCStatement.setConsistencyLevel(getCL());
	}

	public SiloService getSiloService() {
		return siloService;
	}

	public void setSiloService(SiloService siloService) {
		this.siloService = siloService;
	}

	public void setVehicleDAO(VehicleDAO vehicleDAO) {
		this.vehicleDAO = vehicleDAO;
	}

	public VehicleDAO getVehicleDAO() {
		return vehicleDAO;
	}

	public DriverDAO getDriverDAO() {
		return driverDAO;
	}

	public void setDriverDAO(DriverDAO driverDAO) {
		this.driverDAO = driverDAO;
	}

	@Override
	public List<Event> getMostRecentEvents(Integer groupID, Integer eventCount) {
		logger.debug("EventCassandraDAO getMostRecentEvents() groupID = " + groupID);
		throw new UnsupportedOperationException("This method is not supported in this DAO");
	}

	@Override
	public List<Event> getMostRecentWarnings(Integer groupID, Integer eventCount) {
		logger.debug("EventCassandraDAO getMostRecentWarnings() groupID = " + groupID);
		throw new UnsupportedOperationException("This method is not supported in this DAO");
	}

	@Override
	public List<Event> getMostRecentEmergencies(Integer groupID, Integer eventCount) {
		logger.debug("EventCassandraDAO getMostRecentEmergencies() groupID = " + groupID);
		throw new UnsupportedOperationException("This method is not supported in this DAO");
	}

	@Override
	public List<Event> getEventsForDriver(Integer driverID, Date startDate, Date endDate, List<NoteType> types, Integer includeForgiven) {
		logger.debug("EventCassandraDAO getEventsForDriver() driverID = " + driverID);
		Integer[] eventTypesArray = getMapper().convertToArray(types, Integer.class);

		List<Event> returnList = null;
		returnList = fetchEventsForAsset(true, driverID, startDate, endDate, eventTypesArray, includeForgiven);

		if (types.contains(NoteType.LOCATION))
			returnList.addAll(fetchLocations(driverID, (int) DateUtil.convertDateToSeconds(startDate), (int) DateUtil.convertDateToSeconds(endDate), true));

		Collections.sort(returnList);
		Collections.reverse(returnList);

		return returnList;
	}

	@Override
	public List<Event> getEventsForVehicle(Integer vehicleID, Date startDate, Date endDate, List<NoteType> types, Integer includeForgiven) {
		logger.debug("EventCassandraDAO getEventsForvehicle() vehicleID = " + vehicleID);
		Integer[] eventTypesArray = getMapper().convertToArray(types, Integer.class);
		List<Event> returnList = null;
		returnList = fetchEventsForAsset(false, vehicleID, startDate, endDate, eventTypesArray, includeForgiven);
		if (types.contains(NoteType.LOCATION))
			returnList.addAll(fetchLocations(vehicleID, (int) DateUtil.convertDateToSeconds(startDate), (int) DateUtil.convertDateToSeconds(endDate), false));

		Collections.sort(returnList);
		Collections.reverse(returnList);

		return returnList;
	}

	@Override
	public List<Event> getViolationEventsForDriver(Integer driverID, Date startDate, Date endDate, Integer includeForgiven) {
		logger.debug("EventCassandraDAO getViolationEventsForDriver() driverID = " + driverID);
		return getEventsForDriver(driverID, startDate, endDate, EventCategory.VIOLATION.getNoteTypesInCategory(), includeForgiven);
	}

	@Override
	public List<Event> getWarningEventsForDriver(Integer driverID, Date startDate, Date endDate, Integer includeForgiven) {
		logger.debug("EventCassandraDAO getWarningEventsForDriver() driverID = " + driverID);
		return getEventsForDriver(driverID, startDate, endDate, EventCategory.WARNING.getNoteTypesInCategory(), includeForgiven);
	}

	@Override
	public List<Event> getEmergencyEventsForDriver(Integer driverID, Date startDate, Date endDate, Integer includeForgiven) {
		logger.debug("EventCassandraDAO getEmergencyEventsForDriver() driverID = " + driverID);
		return getEventsForDriver(driverID, startDate, endDate, EventCategory.EMERGENCY.getNoteTypesInCategory(), includeForgiven);
	}

	@Override
	public Integer forgive(Integer driverID, Long noteID, Long forgivenByUserID, String reason) {
		throw new UnsupportedOperationException("This method is not supported in this DAO");
	}

	@Override
	public Integer unforgive(Integer driverID, Long noteID) {
		throw new UnsupportedOperationException("This method is not supported in this DAO");
	}

	@Override
	public List<Event> getEventsForGroupFromVehicles(Integer groupID, List<NoteType> eventTypes, Date startDate, Date endDate) {

		logger.debug("EventCassandraDAO getEventsForGroupFromVehicles() groupID = " + groupID);
		// List<Vehicle> vehicleList =
		// getMapper().convertToModelObject(this.getSiloService().getVehiclesByGroupIDDeep(groupID),
		// Vehicle.class);
		List<Vehicle> vehicleList = vehicleDAO.getVehiclesInGroupHierarchy(groupID);
		List<Event> eventList = new ArrayList<Event>();
		for (Vehicle vehicle : vehicleList) {
			List<Event> vehicleEvents = getEventsForVehicle(vehicle.getVehicleID(), startDate, endDate, eventTypes, 1);
			for (Event event : vehicleEvents) {
				event.setVehicle(vehicle);
				event.setVehicleName(vehicle.getName());
				eventList.add(event);
			}
		}
		return eventList;
	}

	@Override
	public List<Event> getEventsForGroupFromVehicles(Integer groupID, List<NoteType> eventTypes, Integer daysBack) {
		logger.debug("EventCassandraDAO getEventsForGroupFromVehicles() groupID = " + groupID);

		throw new UnsupportedOperationException("This method is not supported in this DAO");
	}

	@Override
	public Event getEventNearLocation(Integer driverID, Double latitude, Double longitude, Date startDate, Date endDate) {
		throw new UnsupportedOperationException("This method is not supported in this DAO");
	}

	@Override
	public <T> T getEventByType(Long noteID, Class<T> clazz) {
		throw new UnsupportedOperationException("This method is not supported in this DAO");
	}

	@Override
	public Event findByID(Integer id) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("This method is not supported in this DAO");
	}

	@Override
	public Event findByID(Long id) {
		throw new UnsupportedOperationException("This method is not supported in this DAO");
	}

	public Integer create(Integer id, Event event) {
		throw new UnsupportedOperationException("This method is not supported in this DAO");
	}

	public Integer update(Event event) {
		throw new UnsupportedOperationException("This method is not supported in this DAO");
	}

	public Integer deleteByID(Integer id) {
		throw new UnsupportedOperationException("This method is not supported in this DAO");
	}

	@Override
	public Integer getEventCount(Integer groupID, Date startDate, Date endDate, Integer includeForgiven, List<NoteType> noteTypes, List<TableFilterField> filters) {

		throw new UnsupportedOperationException("This method is not supported in this DAO");
	}

	@Override
	public List<Event> getEventPage(Integer groupID, Date startDate, Date endDate, Integer includeForgiven, List<NoteType> noteTypes, PageParams pageParams) {
		throw new UnsupportedOperationException("This method is not supported in this DAO");
	}

	private List<TableFilterField> fixFilters(List<TableFilterField> filters) {
		if (filters == null)
			return new ArrayList<TableFilterField>();

		for (TableFilterField tableFilterField : filters)
			if (tableFilterField.getFilter() instanceof List<?>) {
				tableFilterField.setFilter(getMapper().convertToArray((List<?>) tableFilterField.getFilter(), Integer.class));
			}

		return filters;
	}

	private class LatestTimeEventComparator implements Comparator<Event> {
		@Override
		public int compare(Event e1, Event e2) {
			return e2.getTime().compareTo(e1.getTime());
		}
	}

	private Mapper getMapper() {
		return mapper;
	}


	private List<Event> fetchEventsForAsset(boolean isDriver, Integer ID, Date startDate, Date endDate, Integer[] eventTypes, Integer includeForgiven) {
		List eventTypesList = Arrays.asList(eventTypes);
		List<Event> eventList = new ArrayList<Event>();
		BoundStatement boundStatement = new BoundStatement(isDriver ? selectDriverNotesStatement : selectVehicleNotesStatement);

		ResultSet results = getSession().execute(boundStatement.bind(ID, BigInteger.valueOf(DateUtil.convertDateToSeconds(startDate)), BigInteger.valueOf(DateUtil.convertDateToSeconds(endDate))));
		for (Row row : results) {
			Integer type = row.getVarint("type").intValue();
			if (eventTypesList.contains(type)) {
				long createdTS = row.getLong(0);
				int time = row.getVarint("time").intValue();
				String method = row.getString("method");
				Integer assetid = row.getVarint((isDriver) ? "vehicleid" : "driverid").intValue();
				long noteid = row.getLong("noteid");
				byte[] data = Bytes.getArray(row.getBytes("value"));
				boolean satcomm = row.getBool("satcomm");
				NoteParser parser = NoteParserFactory.getParserForMethod(method);
				Map<String, Object> fieldMap = parser.parseNote(data);

				fieldMap.put("driverID", (isDriver) ? ID : assetid);
				fieldMap.put("vehicleID", (!isDriver) ? ID : assetid);
				fieldMap.put("8282", new Integer(satcomm ? 1 : 0));

				logger.debug("fieldMap: " + fieldMap);
				addNoteAttribsMap(fieldMap);

				Event event = getMapper().convertToModelObject(fieldMap, Event.class);
				event.setNoteID(noteid);

				if (event.getTime() == null)
					event.setTime(DateUtil.convertTimeInSecondsToDate(time));

				event.setCreated(new Date(createdTS));

				if (event.isValidEvent())
					eventList.add(event);

			}
		}
		return eventList;
	}


	private List<Event> fetchLocations(Integer id, Integer startTime, Integer endTime, boolean isDriver) {
		logger.debug("fetchLocations ID: " + id + " startTime: " + startTime + " endTime: " + endTime);
		List<Event> locationList = new ArrayList<Event>();

		BoundStatement boundStatement = new BoundStatement(isDriver ? selectDriverBCStatement : selectVehicleBCStatement);
		ResultSet results = getSession().execute(boundStatement.bind(id, BigInteger.valueOf(startTime), BigInteger.valueOf(endTime)));
		for (Row row : results) {
			long createdTS = row.getLong(0);
			int time = row.getVarint("time").intValue();
			String method = row.getString("method");
			Integer assetid = row.getVarint((isDriver) ? "vehicleid" : "driverid").intValue();
			byte[] data = Bytes.getArray(row.getBytes("value"));
			
			NoteParser parser = NoteParserFactory.getParserForMethod(method);
			Map<String, Object> fieldMap = parser.parseNote(data);
			addNoteAttribsMap(fieldMap);
			Event event = mapper.convertToModelObject(fieldMap, Event.class);
			if (isDriver) {
				event.setDriverID(id);
				event.setVehicleID(assetid);
			} else {
				event.setVehicleID(id);
				event.setDriverID(assetid);
			}
			event.setCreated(new Date(createdTS));
			event.setNoteID(0L);
			locationList.add(event);
		}
		return locationList;
	}


	protected Integer getChangedCount(Map<String, Object> map) {
		return (Integer) map.get("count");
	}

	private static void addNoteAttribsMap(Map<String, Object> attribMap) {
		Map<Integer, Object> attribs = new HashMap<Integer, Object>();

		try {
			for (Map.Entry<String, Object> attrib : attribMap.entrySet()) {
				String key = attrib.getKey();
				Object value = attrib.getValue();

				if (excludeAttribs.get(key) == null) {
					if (!isCode(key)) {
						Attrib noteAttrib = Attrib.getForName(key);
						key = (noteAttrib == null) ? "" : String.valueOf(noteAttrib.getCode());
					}
					if (!key.isEmpty()) {
						Integer iKey = Integer.parseInt(key);
						Object oVal = null;
						if (iKey == 221)
							oVal = Long.parseLong(value.toString());
						else if (iKey < 24576 || (iKey > 32000 && iKey < 33000))
							oVal = Integer.parseInt(value.toString());
						else if ((iKey >= 24576 && iKey < 32000) || (iKey > 33000 && iKey <= 49166))
							oVal = value.toString().replace('=', '&');

						if (oVal != null && iKey != null)
							attribs.put(iKey, oVal);
					}
				}
			}
		} catch (Exception e) {
			logger.error("addNoteAttribsMap exception: " + e);
		}
		if (attribs.size() > 0)
			attribMap.put("attrMap", attribs);
	}

	private static boolean isCode(String str) {
		return Character.isDigit(str.charAt(0));
	}

}

package com.inthinc.pro.dao.cassandra;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

/////Used for Main test only///////////////
import com.inthinc.pro.dao.hessian.VehicleHessianDAO;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
///////////////////////////////////////

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.mapper.EventHessianMapper;
import com.inthinc.pro.dao.hessian.mapper.Mapper;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventCategory;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.model.event.SeatBeltEvent;
import com.inthinc.pro.model.event.SpeedingEvent;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;

import com.inthinc.pro.comm.parser.note.NoteParser;
import com.inthinc.pro.comm.parser.note.NoteParserFactory;

import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.Composite;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.beans.Rows;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.MultigetSliceQuery;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.SliceQuery;


@SuppressWarnings("serial")
public class EventCassandraDAO extends AggregationCassandraDAO implements EventDAO
{
	private final static int MAX_EVENTS = 1000;
	
	SiloService siloService = null;
	private Mapper mapper = new EventHessianMapper();
    private VehicleDAO vehicleDAO;
    private DriverDAO driverDAO;


    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(EventCassandraDAO.class);

    
    public static void main(String[] args)
    {
/*
		if(args.length == 0)
		{
			System.out.println("Properties File was not passed In");
			System.exit(0);
		}
	
		
		String filePath = (String) args[0];
		String startTime = (String) args[1];
		String endTime = (String) args[2];
*/		
        SiloService siloService = new SiloServiceCreator("tp-web10.tiwipro.com", 8099).getService();
//        SiloService siloService = new SiloServiceCreator("dev-pro.inthinc.com", 8199).getService();
//        SiloService siloService = new SiloServiceCreator("localhost", 8199).getService();
        VehicleHessianDAO vehicleDAO = new VehicleHessianDAO();
        vehicleDAO.setSiloService(siloService);

//    	CassandraDB cassandraDB = new CassandraDB(true, "Iridium Archive", "note", "localhost:9160", 10, false);
        CassandraDB cassandraDB = new CassandraDB(true, "Inthinc Production", "note_prod", "note_prod", "chevron-node4.tiwipro.com:9160", 10, false, false);
    	EventCassandraDAO dao = new EventCassandraDAO();
    	dao.setCassandraDB(cassandraDB);
    	dao.setVehicleDAO(vehicleDAO);
  /*  	
    	List<Event> events = dao.getViolationEventsForDriver(66462, new Date(1334078768000L), new Date(1334081277000L), 1);
    	dao.logEvents(events);

    	events = dao.getEmergencyEventsForDriver(66462, new Date(1334078768000L), new Date(1334081277000L), 1);
    	dao.logEvents(events);
    	
    	events = dao.getWarningEventsForDriver(66462, new Date(1334078768000L), new Date(1334081277000L), 1);
    	dao.logEvents(events);
    	
    	events = dao.getMostRecentEvents(0, 100);
    	dao.logEvents(events);
    	
    	events = dao.getMostRecentWarnings(0, 100);
    	dao.logEvents(events);

    	events = dao.getMostRecentEmergencies(0, 100);
    	dao.logEvents(events);
*/
//    	List<Event> events = dao.getEventsForGroupFromVehicles(5260, EventCategory.VIOLATION.getNoteTypesInCategory(), new Date(1334078768000L), new Date(1334081277000L));
    	
//    	List<Event> events = dao.fetchAllEventsForAsset(false, 20079, 0, 1365256141);
//    	dao.logEvents2(events);
    	
    	dao.shutdown();
    }

    /*    
    private List<Event> fetchAllEventsForAsset(boolean isDriver, Integer ID, Integer startTS, Integer endTS)
    {
        List<Long> keyList = new ArrayList<Long>();
        Composite startRange = new Composite();
        startRange.add(0, 0);
        startRange.add(1, startTS);
        
        Composite endRange = new Composite();
        endRange.add(0, Integer.MAX_VALUE);
        endRange.add(1, endTS);

        keyList.addAll(fetchRowKeysFromIndex(isDriver, ID.intValue(), startRange, endRange, MAX_EVENTS));
        
        return fetchNotes(keyList, true);
    }

    public List<Event> fetchAllEventsForAsset(boolean isDriver, Integer ID, Integer startTS, Integer endTS)
    {
        List<Event> eventList = new ArrayList<Event>();
        Composite startRange = new Composite();
        startRange.add(0, 0);
        startRange.add(1, 0);
        
        Composite endRange = new Composite();
        endRange.add(0, Integer.MAX_VALUE);
        endRange.add(1, endTS);

            eventList.addAll(fetchNotesFromIndex(isDriver, ID, startRange, endRange, MAX_EVENTS, true));
        return eventList;
    }
    private void logEvents2(List<Event> events)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        for (Event event : events)
        {
//            logger.info(event.getTime().getTime()/1000 + " " + event.getOdometer());

            System.out.println("UPDATE note20 set odometer=" + event.getOdometer() + " WHERE deviceID=43316 AND time='" + dateFormat.format(event.getTime()) + "';");
        }
        System.out.println("**************************************************");
    }
*/

    private void logEvents(List<Event> events)
    {
    	for (Event event : events)
    	{
    		logger.info("Event: " + event);
    		System.out.println("Event: " + event);
    	}
    	System.out.println("**************************************************");
    }

    public EventCassandraDAO()
    {
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
    public List<Event> getMostRecentEvents(Integer groupID, Integer eventCount)
    {
        logger.debug("EventCassandraDAO getMostRecentEvents() groupID = " + groupID);

    	Integer[] eventTypes = getMapper().convertToArray(EventCategory.VIOLATION.getNoteTypesInCategory(), Integer.class);
        return fetchMostRecentForGroup(groupID, eventTypes, eventCount, true);
    }

    
    
    @Override
    public List<Event> getMostRecentWarnings(Integer groupID, Integer eventCount)
    {
        logger.debug("EventCassandraDAO getMostRecentWarnings() groupID = " + groupID);
        Integer[] eventTypes = getMapper().convertToArray(EventCategory.WARNING.getNoteTypesInCategory(), Integer.class);
        return fetchMostRecentForGroup(groupID, eventTypes, eventCount, true);
    }
    
    @Override
    public List<Event> getMostRecentEmergencies(Integer groupID, Integer eventCount)
    {
        logger.debug("EventCassandraDAO getMostRecentEmergencies() groupID = " + groupID);
        Integer[] eventTypes = getMapper().convertToArray(EventCategory.EMERGENCY.getNoteTypesInCategory(), Integer.class);
        return fetchMostRecentForGroup(groupID, eventTypes, eventCount, true);
    }
    
    
    

    @Override
    public List<Event> getEventsForDriver(Integer driverID, Date startDate, Date endDate, List<NoteType> types, Integer includeForgiven)
    {
        logger.debug("EventCassandraDAO getEventsForDriver() driverID = " + driverID);
        Integer[] eventTypesArray = getMapper().convertToArray(types, Integer.class);
        
        if (DateUtil.differenceInDays(startDate, new Date()) > 60)
            return fetchEventsForAssetOver60(true, driverID, startDate, endDate, eventTypesArray, includeForgiven);
        else
            return fetchEventsForAsset(true, driverID, startDate, endDate, eventTypesArray, includeForgiven);
    }

    @Override
    public List<Event> getEventsForVehicle(Integer vehicleID, Date startDate, Date endDate, List<NoteType> types, Integer includeForgiven)
    {
        logger.debug("EventCassandraDAO getEventsForvehicle() vehicleID = " + vehicleID);
        Integer[] eventTypesArray = getMapper().convertToArray(types, Integer.class);
        if (DateUtil.differenceInDays(startDate, new Date()) > 60)
            return fetchEventsForAssetOver60(false, vehicleID, startDate, endDate, eventTypesArray, includeForgiven);
        else
            return fetchEventsForAsset(false, vehicleID, startDate, endDate, eventTypesArray, includeForgiven);
    }

    
    @Override
    public List<Event> getViolationEventsForDriver(Integer driverID, Date startDate, Date endDate, Integer includeForgiven)
    {
        logger.debug("EventCassandraDAO getViolationEventsForDriver() driverID = " + driverID);
        return getEventsForDriver(driverID, startDate, endDate, EventCategory.VIOLATION.getNoteTypesInCategory(), includeForgiven);
    }

    @Override
    public List<Event> getWarningEventsForDriver(Integer driverID, Date startDate, Date endDate, Integer includeForgiven)
    {
        logger.debug("EventCassandraDAO getWarningEventsForDriver() driverID = " + driverID);
        return getEventsForDriver(driverID, startDate, endDate, EventCategory.WARNING.getNoteTypesInCategory(), includeForgiven);
    }
    
    @Override
    public List<Event> getEmergencyEventsForDriver(Integer driverID, Date startDate, Date endDate, Integer includeForgiven)
    {
        logger.debug("EventCassandraDAO getEmergencyEventsForDriver() driverID = " + driverID);
        return getEventsForDriver(driverID, startDate, endDate, EventCategory.EMERGENCY.getNoteTypesInCategory(), includeForgiven);
    }    

    @Override
    public Integer forgive(Integer driverID, Long noteID)
    {
    	Event event = findByID(noteID);    	
    	
    	if (event != null)
    		updateAggregates(event, -1);
/*
    	try{
    		return getChangedCount(getSiloService().forgive(driverID, noteID));
    	}
    	catch (ProDAOException pDAOe){
    		
    		return 1; //It as already been changed either here or elsewhere
    	}
*/
    	return 1;
    }

    @Override
    public Integer unforgive(Integer driverID, Long noteID)
    {
//        return getChangedCount(getSiloService().unforgive(driverID, noteID));
    	Event event = findByID(noteID);    	
    	
    	if (event != null)
    		updateAggregates(event, 1);
    	return 1;
    }

    @Override
    public List<Event> getEventsForGroupFromVehicles(Integer groupID, List<NoteType> eventTypes, Date startDate, Date endDate) {

        logger.debug("EventCassandraDAO getEventsForGroupFromVehicles() groupID = " + groupID);
//        List<Vehicle> vehicleList = getMapper().convertToModelObject(this.getSiloService().getVehiclesByGroupIDDeep(groupID), Vehicle.class);
        List<Vehicle> vehicleList = vehicleDAO.getVehiclesInGroupHierarchy(groupID);
        List<Event> eventList = new ArrayList<Event>();
        for (Vehicle vehicle : vehicleList)
        {
            List<Event>vehicleEvents = getEventsForVehicle(vehicle.getVehicleID(), startDate, endDate, eventTypes, 1);
            for (Event event : vehicleEvents)
            {
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

        Date endDate = new Date();
        Date startDate = DateUtil.getDaysBackDate(endDate, daysBack);
        return getEventsForGroupFromVehicles(groupID,eventTypes, startDate,endDate);
	}

	@Override
    public Event getEventNearLocation(Integer driverID, Double latitude, Double longitude, Date startDate, Date endDate) {
        logger.debug("EventCassandraDAO getEventNearLocation() driverID = " + driverID);
//        return getMapper().convertToModelObject(getSiloService().getNoteNearLoc(driverID, latitude, longitude, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(endDate)), Event.class);
		return null;
    }
    
    @Override
    public <T> T getEventByType(Long noteID, Class<T> clazz)
    {
        logger.debug("EventCassandraDAO getEventByType() noteID = " + noteID);
//        return getMapper().convertToModelObject(this.getSiloService().getNote(noteID), clazz);
    	return null;
    }
    
    @Override
    public Event findByID(Integer id) throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException("This method is not supported in this DAO");
    }
    
    @Override
    public Event findByID(Long id)
    {
        logger.debug("EventCassandraDAO getEventByType() findByID = " + id);
        List<Long> keyList = new ArrayList<Long>();
        keyList.add(id);
        List<Event> eventList = fetchNotes(keyList, true);
    	if (eventList.size() > 0)
    		return eventList.get(0);
    	
    	return null;
    }
    
    
    public Integer create(Integer id, Event event)
    {
        throw new UnsupportedOperationException("This method is not supported in this DAO");
    }

    public Integer update(Event event)
    {
        throw new UnsupportedOperationException("This method is not supported in this DAO");
    }

    public Integer deleteByID(Integer id)
    {
        throw new UnsupportedOperationException("This method is not supported in this DAO");
    }

    
    
	@Override
	public Integer getEventCount(Integer groupID, Date startDate, Date endDate,
			Integer includeForgiven, List<NoteType> noteTypes,
			List<TableFilterField> filters) {
		
	    filters = fixFilters(filters);
			try {
			return getChangedCount(getSiloService().getDriverEventCount(groupID, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(endDate), 
									includeForgiven, getMapper().convertList(filters), 
									getMapper().convertToArray(noteTypes, Integer.class)));
		}
        catch (EmptyResultSetException e)
        {
            return 0;
        }
//		return 0;
	}


	@Override
	public List<Event> getEventPage(Integer groupID, Date startDate, Date endDate,
			Integer includeForgiven, List<NoteType> noteTypes,
			PageParams pageParams) {
	    pageParams.setFilterList(fixFilters(pageParams.getFilterList()));
		try {
			return getMapper().convertToModelObject(getSiloService().getDriverEventPage(groupID, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(endDate), 
									includeForgiven, getMapper().convertToMap(pageParams), 
									getMapper().convertToArray(noteTypes, Integer.class)), Event.class);
		}
		catch (EmptyResultSetException e)
		{
			return Collections.emptyList();
		}
//		return Collections.emptyList();
	}

	private List<TableFilterField> fixFilters(List<TableFilterField> filters)
	{
	    if (filters == null)
            return new ArrayList<TableFilterField>();
	    
	    for (TableFilterField tableFilterField : filters)
	        if (tableFilterField.getFilter() instanceof List<?>) {
	            tableFilterField.setFilter(getMapper().convertToArray((List<?>)tableFilterField.getFilter(), Integer.class));
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

    private List<Event> fetchMostRecentForGroup(Integer groupID, Integer[] eventTypes, Integer eventCount, boolean includeForgiven)
    {
        List<Long> keyList = new ArrayList<Long>();
    	for(Integer eventType : eventTypes)
    	{
    		Composite startRange = new Composite();
    		startRange.add(0, eventType);
    		startRange.add(1, 0);
    		
    		
    		Composite endRange = new Composite();
    		endRange.add(0, eventType);
    		endRange.add(1, (int)DateUtil.convertDateToSeconds(new Date()));

//FIX    		
//    		keyList.addAll(fetchRowKeysFromIndex(driverGroupNoteTypeTimeIndex30_CF, groupID.intValue(), startRange, endRange, eventCount));
    	}
    	
        int max = eventCount;
        if (keyList.size() < max)
        	max = keyList.size();

    	keyList = keyList.subList(0, max);
    	return fetchNotes(keyList, includeForgiven);
    }

    private List<Event> fetchEventsForAsset(boolean isDriver, Integer ID, Date startDate, Date endDate, Integer[] eventTypes, Integer includeForgiven)
    {
        List<Event> eventList = new ArrayList<Event>();
        for(Integer eventType : eventTypes)
        {
            
        
            Composite startRange = new Composite();
            startRange.add(0, eventType);
            startRange.add(1, (int)DateUtil.convertDateToSeconds(startDate));
            
            Composite endRange = new Composite();
            endRange.add(0, eventType);
            endRange.add(1, (int)DateUtil.convertDateToSeconds(endDate));

            eventList.addAll(fetchNotesFromIndex(isDriver, ID, startRange, endRange, MAX_EVENTS, (includeForgiven == 1)));
        }
        return eventList;
    }
    
    private List<Event> fetchEventsForAssetOver60(boolean isDriver, Integer ID, Date startDate, Date endDate, Integer[] eventTypes, Integer includeForgiven)
    {
        List<Long> keyList = new ArrayList<Long>();
    	for(Integer eventType : eventTypes)
    	{
    		
   		
    		Composite startRange = new Composite();
    		startRange.add(0, eventType);
    		startRange.add(1, (int)DateUtil.convertDateToSeconds(startDate));
    		
    		Composite endRange = new Composite();
    		endRange.add(0, eventType);
    		endRange.add(1, (int)DateUtil.convertDateToSeconds(endDate));

    		keyList.addAll(fetchRowKeysFromIndex(isDriver, ID.intValue(), startRange, endRange, MAX_EVENTS));
    	}
    	
    	
    	return fetchNotes(keyList, (includeForgiven == 1));
    }

    private List<Event> fetchNotesFromIndex(boolean isDriver, Integer rowKey, Composite startRange, Composite endRange, Integer count, boolean includeForgiven)
    {
        String INDEX_CF = isDriver ? driverNoteTypeTimeIndex60_CF : vehicleNoteTypeTimeIndex60_CF;
        
        SliceQuery<Integer, Composite, byte[]> sliceQuery = HFactory.createSliceQuery(getKeyspace(), integerSerializer, compositeSerializer, bytesArraySerializer);
        sliceQuery.setRange(startRange, endRange, false, count);
        sliceQuery.setColumnFamily(INDEX_CF);
        sliceQuery.setKey(rowKey);
        //rangeSlicesQuery.setReturnKeysOnly();
        
        QueryResult<ColumnSlice<Composite, byte[]>> result = sliceQuery.execute();
        ColumnSlice<Composite, byte[]> columnSlice = result.get();            
        List<HColumn<Composite, byte[]>> columnList = columnSlice.getColumns();
        
        Composite colName = null;
        byte[] raw = null;
        String method = "";
        boolean forgiven = false;
        Integer assetId = 0;
        Long noteId = 0L;
        List<Event> eventList = new ArrayList<Event>();
        for (HColumn<Composite, byte[]> column : columnList)
        {
            colName = column.getName();
            forgiven = booleanSerializer.fromByteBuffer((ByteBuffer) colName.get(5));
            if ((includeForgiven || (!includeForgiven && forgiven == false))) {
                method = stringSerializer.fromByteBuffer((ByteBuffer) colName.get(2));
                assetId = bigIntegerSerializer.fromByteBuffer((ByteBuffer) colName.get(3)).intValue();
                noteId = longSerializer.fromByteBuffer((ByteBuffer) colName.get(4));
                raw = column.getValue();

				// 5/18/2013 We had a bug where new notes based on End of Trip attribs had method set to null
				// in noteservice. Fixed in noteservice but this code added to deal with old/bad data. Can eventually be removed
				if (method == null)
                    method = "notebc";
				///////////////	

                NoteParser parser = NoteParserFactory.getParserForMethod(method);
                Map<String, Object> fieldMap = parser.parseNote(raw);
                fieldMap.put("driverID", (isDriver) ? rowKey  : assetId);
                fieldMap.put("vehicleID", (!isDriver) ? rowKey  : assetId);

                logger.debug("fieldMap: " + fieldMap);    
                
                Event event = getMapper().convertToModelObject(fieldMap, Event.class);
                event.setNoteID(noteId);
                
                if (event.isValidEvent())
                    eventList.add(event);
            }
        }
            
        
        
        return eventList;
     }

    private List<Long> fetchRowKeysFromIndex(boolean isDriver, Integer rowKey, Composite startRange, Composite endRange, Integer count)
    {
        String INDEX_CF = isDriver ? driverNoteTypeTimeIndex_CF : vehicleNoteTypeTimeIndex_CF;
        SliceQuery<Composite, Composite, Long> sliceQuery = HFactory.createSliceQuery(getKeyspace(), compositeSerializer, compositeSerializer, longSerializer);
        sliceQuery.setRange(startRange, endRange, false, count);
        sliceQuery.setColumnFamily(INDEX_CF);
        
		Composite rowKeyComp = new Composite();
		rowKeyComp.add(0, rowKey);
        sliceQuery.setKey(rowKeyComp);
        //rangeSlicesQuery.setReturnKeysOnly();
        
        QueryResult<ColumnSlice<Composite, Long>> result = sliceQuery.execute();
        ColumnSlice<Composite, Long> columnSlice = result.get();            
        
        List<Long> keyList = new ArrayList<Long>();
    	List<HColumn<Composite, Long>> columnList = columnSlice.getColumns();
        for (HColumn<Composite, Long> column : columnList)
        {
        	keyList.add(column.getValue());
        }
    	return keyList;
     }

    private List<Event> fetchNotes(List<Long> keys, boolean includeForgiven)
    {
    	List <Event> eventList = new ArrayList<Event>();
    	MultigetSliceQuery<Long, String, byte[]> sliceQuery = HFactory.createMultigetSliceQuery(getKeyspace(), longSerializer, stringSerializer, bytesArraySerializer);
        
        sliceQuery.setColumnFamily(note_CF);            
        sliceQuery.setRange(ATTRIBS_COL + "!", "~", false, 1000);  //get all the columns
        sliceQuery.setKeys(keys);
        
        QueryResult<Rows<Long, String, byte[]>> result = sliceQuery.execute();
        
        Rows<Long, String, byte[]> rows = result.get();     
        for (Row<Long, String, byte[]> row : rows)
        {
        	ColumnSlice<String, byte[]> columnSlice = row.getColumnSlice();
        	List<HColumn<String, byte[]>> columnList = columnSlice.getColumns();
        	
        	long id  = row.getKey();

    		byte[] raw = null;
    		String method = "";
    		boolean forgiven = false;
    		int driverId = 0;
            int vehicleId = 0;
    		for (HColumn<String, byte[]> column : columnList)
        	{
        		if (column.getName().equalsIgnoreCase(RAW_COL))
        			raw = column.getValue();
        		else if (column.getName().equalsIgnoreCase(METHOD_COL))
        			method = new String(column.getValue());
        		else if (column.getName().equalsIgnoreCase(DRIVERID_COL))
        			driverId = bigIntegerSerializer.fromBytes(column.getValue()).intValue();
                else if (column.getName().equalsIgnoreCase(VEHICLEID_COL))
                    vehicleId = bigIntegerSerializer.fromBytes(column.getValue()).intValue();
            	else if (column.getName().equalsIgnoreCase(FORGIVEN_COL))
            		forgiven = true;
        	}
        		
			NoteParser parser = NoteParserFactory.getParserForMethod(method);
		    Map<String, Object> fieldMap = parser.parseNote(raw);
		    fieldMap.put("driverID", new Integer(driverId));
		    fieldMap.put("vehicleID", new Integer(vehicleId));
            
            Event event = getMapper().convertToModelObject(fieldMap, Event.class);
            event.setNoteID(id);
            
            if (event.isValidEvent() && (includeForgiven || (!includeForgiven && forgiven == false)))
            	eventList.add(event);
        }
    		
        logger.debug("EventList size: " + eventList.size());
    	Collections.sort(eventList, new LatestTimeEventComparator());

		return eventList;
    }

    protected Integer getChangedCount(Map<String, Object> map) {
        return (Integer) map.get("count");
    }

}

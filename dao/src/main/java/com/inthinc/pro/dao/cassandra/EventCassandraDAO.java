package com.inthinc.pro.dao.cassandra;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.Composite;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.beans.Rows;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.MultigetSliceQuery;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.SliceQuery;

import org.apache.log4j.Logger;

import com.inthinc.pro.comm.parser.attrib.Attrib;
import com.inthinc.pro.comm.parser.note.NoteParser;
import com.inthinc.pro.comm.parser.note.NoteParserFactory;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
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
public class EventCassandraDAO extends AggregationCassandraDAO implements EventDAO
{
	private final static int MAX_EVENTS = 1000;
    private static Map<String, String> excludeAttribs = new HashMap<String, String>();
    static {
        excludeAttribs.put(Attrib.NOTETYPE.getFieldName(), "");
        excludeAttribs.put(Attrib.NOTETIME.getFieldName(), "");
        excludeAttribs.put(Attrib.NOTEFLAGS.getFieldName(), "");
        excludeAttribs.put(Attrib.NOTESPEED.getFieldName(), "");
        excludeAttribs.put(Attrib.NOTESPEEDLIMIT.getFieldName(), "");
        excludeAttribs.put(Attrib.NOTEODOMETER.getFieldName(), "");
        excludeAttribs.put(Attrib.BOUNDARYID.getFieldName(), "");
        excludeAttribs.put(Attrib.MAXLATITUDE.getFieldName(), "");
        excludeAttribs.put(Attrib.MAXLONGITUDE.getFieldName(), "");
        excludeAttribs.put(Attrib.NOTEMAPREV.getFieldName(), "");
        excludeAttribs.put(Attrib.TOPSPEED.getFieldName(), "");
        excludeAttribs.put(Attrib.AVGSPEED.getFieldName(), "");
        excludeAttribs.put(Attrib.DISTANCE.getFieldName(), "");
        excludeAttribs.put(Attrib.NOTELATLONG.getFieldName(), "");
        excludeAttribs.put("head", "");
        excludeAttribs.put("heading", "");
    }
 
    public static void main(String[] args) {
        CassandraDB cassandraDB = new CassandraDB(true, "Inthinc Production", "note_qa", "cache_qa","schlumberger-node-b-1.tiwipro.com:9160", 1, false, false);
        EventCassandraDAO eventDAO = new EventCassandraDAO();
        List noteTypes = EventCategory.WARNING.getNoteTypesInCategory();
        noteTypes.add(NoteType.LOCATION);
        List<Event> events = eventDAO.getEventsForVehicle(58672, new Date(1413830104000L), new Date(), noteTypes, 1);
        for (Event event : events){
        	logger.info(event + " " + event.getAttribs());
        }
    }
	
	SiloService siloService = null;
	private Mapper mapper = new EventHessianMapper();
    private VehicleDAO vehicleDAO;
    private DriverDAO driverDAO;

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(EventCassandraDAO.class);
    
 
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
        
        List<Event> returnList = null;
        returnList =  fetchEventsForAsset(true, driverID, startDate, endDate, eventTypesArray, includeForgiven);
        
        if (types.contains(NoteType.LOCATION))
        	returnList.addAll(fetchLocations(driverID, (int) DateUtil.convertDateToSeconds(startDate), (int) DateUtil.convertDateToSeconds(endDate), true));
        
        Collections.sort(returnList);
        Collections.reverse(returnList);
       
        return returnList;
    }

    @Override
    public List<Event> getEventsForVehicle(Integer vehicleID, Date startDate, Date endDate, List<NoteType> types, Integer includeForgiven)
    {
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
    		endRange.add(1, (int)DateUtil.convertDateToSeconds(new Date())+1);

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
            endRange.add(1, (int)DateUtil.convertDateToSeconds(endDate)+1);
            
            eventList.addAll(fetchNotesFromIndex(isDriver, ID, startRange, endRange, MAX_EVENTS, (includeForgiven == 1)));
        }
        return eventList;
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
                addNoteAttribsMap(fieldMap);
                
                Event event = getMapper().convertToModelObject(fieldMap, Event.class);
                event.setNoteID(noteId);
                
                if (event.isValidEvent())
                    eventList.add(event);
            }
        }
            
        
        
        return eventList;
     }

    private List<Event> fetchLocations(Integer id, Integer startTime, Integer endTime, boolean isDriver) {
        logger.debug("fetchLocations ID: " + id + " startTime: " + startTime + " endTime: " + endTime);
        List<Event> locationList = new ArrayList<Event>();

        SliceQuery<Integer, Composite, byte[]> sliceQuery = HFactory.createSliceQuery(getKeyspace(), integerSerializer, compositeSerializer, bytesArraySerializer);

        Composite startRange = new Composite();
        startRange.add(0, startTime);
        startRange.add(1, Integer.MIN_VALUE);

        Composite endRange = new Composite();
        endRange.add(0, endTime); // Current Timestamp
        endRange.add(1, Integer.MAX_VALUE);

        sliceQuery.setRange(startRange, endRange, true, 10000);
        sliceQuery.setColumnFamily((isDriver) ? driverBreadCrumb60_CF : vehicleBreadCrumb60_CF);
        sliceQuery.setKey(id);

        QueryResult<ColumnSlice<Composite, byte[]>> result = sliceQuery.execute();
        ColumnSlice<Composite, byte[]> columnSlice = result.get();

        Composite columnKey = null;
        byte[] raw = null;
        List<HColumn<Composite, byte[]>> columnList = columnSlice.getColumns();
        for (HColumn<Composite, byte[]> column : columnList) {
            columnKey = column.getName();
            Integer assetID = bigIntegerSerializer.fromByteBuffer((ByteBuffer) columnKey.get(1)).intValue();
            String method = stringSerializer.fromByteBuffer((ByteBuffer) columnKey.get(2));
            raw = column.getValue();

            NoteParser parser = NoteParserFactory.getParserForMethod(method);
            Map<String, Object> fieldMap = parser.parseNote(raw);
            addNoteAttribsMap(fieldMap);
            Event event = mapper.convertToModelObject(fieldMap, Event.class);
            if (isDriver) {
            	event.setDriverID(id);
            	event.setVehicleID(assetID);
            }	
            else {
            	event.setVehicleID(id);
            	event.setDriverID(assetID);
            }	
            locationList.add(event);
        }

        return locationList;
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

    private static void addNoteAttribsMap(Map<String, Object> attribMap) {
    	Map<Integer, Object> attribs = new HashMap<Integer, Object>();
        
        for (Map.Entry<String, Object> attrib : attribMap.entrySet()) {
            String key = attrib.getKey();
            Object value = attrib.getValue();
            
            if (excludeAttribs.get(key) == null) {
                if (!isCode(key)) {
                	Attrib noteAttrib = Attrib.getForName(key);
                    key = (noteAttrib==null) ? "" : String.valueOf(noteAttrib.getCode());
                }
                if (!key.isEmpty()) {
                	Integer iKey = Integer.parseInt(key);
                	Object oVal = null;
                	if (iKey < 24576 || (iKey > 32000 && iKey < 33000))
                		oVal = Integer.parseInt(value.toString());
                	if ((iKey >= 24576 && iKey < 32000) || (iKey > 33000 && iKey <= 49166))
                		oVal = value.toString().replace('=','&');
                	
                	if (oVal!=null && iKey!=null)
                		attribs.put(iKey, oVal);
                }    
            }
        }
        if (attribs.size() > 0)
        	attribMap.put("attrMap", attribs);
    }

    private static boolean isCode(String str)
    {
        return Character.isDigit(str.charAt(0));
    }


}

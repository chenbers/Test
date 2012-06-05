package com.inthinc.pro.dao.cassandra;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/////Used for Main test only///////////////
import com.inthinc.pro.dao.hessian.VehicleHessianDAO;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
///////////////////////////////////////

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.mapper.EventHessianMapper;
import com.inthinc.pro.dao.hessian.mapper.Mapper;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventCategory;
import com.inthinc.pro.model.event.LoginEvent;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;

import com.inthinc.pro.comm.parser.note.NoteParser;
import com.inthinc.pro.comm.parser.note.NoteParserFactory;
import com.inthinc.pro.comm.parser.util.ReadUtil;

import me.prettyprint.cassandra.serializers.IntegerSerializer;
import me.prettyprint.hector.api.beans.AbstractComposite.Component;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.Composite;
import me.prettyprint.hector.api.beans.CounterRow;
import me.prettyprint.hector.api.beans.CounterRows;
import me.prettyprint.hector.api.beans.CounterSlice;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.HCounterColumn;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.beans.Rows;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.MutationResult;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.MultigetSliceCounterQuery;
import me.prettyprint.hector.api.query.MultigetSliceQuery;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.SliceQuery;


@SuppressWarnings("serial")
public class EventCassandraDAO extends GenericCassandraDAO implements EventDAO
{
	private final static int MAX_EVENTS = 1000;
	
    private Mapper mapper = new EventHessianMapper();
    private VehicleDAO vehicleDAO;

	
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
        SiloService siloService = new SiloServiceCreator("192.168.11.115", 8091).getService();
        VehicleHessianDAO vehicleDAO = new VehicleHessianDAO();
        vehicleDAO.setSiloService(siloService);

    	CassandraDB cassandraDB = new CassandraDB("Iridium Archive", "note", "localhost:9160", 10, false);
    	EventCassandraDAO dao = new EventCassandraDAO();
    	dao.setCassandraDB(cassandraDB);
    	dao.setVehicleDAO(vehicleDAO);
    	
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

//    	List<Event> events = dao.getEventsForGroupFromVehicles(5260, EventCategory.VIOLATION.getNoteTypesInCategory(), new Date(1334078768000L), new Date(1334081277000L));
//    	dao.logEvents(events);
    	
    	dao.shutdown();
    }
    
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

    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    public VehicleDAO getVehicleDAO() {
        return vehicleDAO;
    }
    
    @Override
    public List<Event> getMostRecentEvents(Integer groupID, Integer eventCount)
    {
        Integer[] eventTypes = getMapper().convertToArray(EventCategory.VIOLATION.getNoteTypesInCategory(), Integer.class);
        return fetchMostRecentForGroup(groupID, eventTypes, eventCount, true);
    }

    
    
    @Override
    public List<Event> getMostRecentWarnings(Integer groupID, Integer eventCount)
    {
        Integer[] eventTypes = getMapper().convertToArray(EventCategory.WARNING.getNoteTypesInCategory(), Integer.class);
        return fetchMostRecentForGroup(groupID, eventTypes, eventCount, true);
    }
    
    @Override
    public List<Event> getMostRecentEmergencies(Integer groupID, Integer eventCount)
    {
        Integer[] eventTypes = getMapper().convertToArray(EventCategory.EMERGENCY.getNoteTypesInCategory(), Integer.class);
        return fetchMostRecentForGroup(groupID, eventTypes, eventCount, true);
    }
    
    
    

    @Override
    public List<Event> getEventsForDriver(Integer driverID, Date startDate, Date endDate, List<NoteType> types, Integer includeForgiven)
    {
        Integer[] eventTypesArray = getMapper().convertToArray(types, Integer.class);
        return fetchEventsForAsset(driverNoteTypeTimeIndex_CF, driverID, startDate, endDate, eventTypesArray, includeForgiven);
    }

    @Override
    public List<Event> getEventsForVehicle(Integer vehicleID, Date startDate, Date endDate, List<NoteType> types, Integer includeForgiven)
    {
        Integer[] eventTypesArray = getMapper().convertToArray(types, Integer.class);
        return fetchEventsForAsset(vehicleNoteTypeTimeIndex_CF, vehicleID, startDate, endDate, eventTypesArray, includeForgiven);
    }

    
    @Override
    public List<Event> getViolationEventsForDriver(Integer driverID, Date startDate, Date endDate, Integer includeForgiven)
    {
        return getEventsForDriver(driverID, startDate, endDate, EventCategory.VIOLATION.getNoteTypesInCategory(), includeForgiven);
    }

    @Override
    public List<Event> getWarningEventsForDriver(Integer driverID, Date startDate, Date endDate, Integer includeForgiven)
    {
        return getEventsForDriver(driverID, startDate, endDate, EventCategory.WARNING.getNoteTypesInCategory(), includeForgiven);
    }
    
    @Override
    public List<Event> getEmergencyEventsForDriver(Integer driverID, Date startDate, Date endDate, Integer includeForgiven)
    {
        return getEventsForDriver(driverID, startDate, endDate, EventCategory.EMERGENCY.getNoteTypesInCategory(), includeForgiven);
    }    

    @Override
    public Integer forgive(Integer driverID, Long noteID)
    {
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
    	return 1;
    }

    @Override
    public List<Event> getEventsForGroupFromVehicles(Integer groupID, List<NoteType> eventTypes, Date startDate, Date endDate) {

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

        Date endDate = new Date();
        Date startDate = DateUtil.getDaysBackDate(endDate, daysBack);
        return getEventsForGroupFromVehicles(groupID,eventTypes, startDate,endDate);
	}

	@Override
    public Event getEventNearLocation(Integer driverID, Double latitude, Double longitude, Date startDate, Date endDate) {
//        return getMapper().convertToModelObject(getSiloService().getNoteNearLoc(driverID, latitude, longitude, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(endDate)), Event.class);
		return null;
    }
    
    @Override
    public <T> T getEventByType(Long noteID, Class<T> clazz)
    {
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
/*    	
        try
        {
            Map<String, Object> returnMap = getSiloService().getNote(id);
            return getMapper().convertToModelObject(returnMap, Event.class);
        }
        catch (EmptyResultSetException e)
        {
            return null;
        }
*/
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
		
/*
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
 */
		return 0;
	}

	@Override
	public List<Event> getEventPage(Integer groupID, Date startDate, Date endDate,
			Integer includeForgiven, List<NoteType> noteTypes,
			PageParams pageParams) {
/*
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
*/		
		return Collections.emptyList();
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
        List<Composite> keyList = new ArrayList<Composite>();
    	for(Integer eventType : eventTypes)
    	{
    		Composite startRange = new Composite();
    		startRange.add(0, eventType);
    		startRange.add(1, 0);
    		
    		
    		Composite endRange = new Composite();
    		endRange.add(0, eventType);
    		endRange.add(1, (int)DateUtil.convertDateToSeconds(new Date()));
    		
    		keyList.addAll(fetchRowKeysFromIndex(driverGroupNoteTypeTimeIndex30_CF, groupID.intValue(), startRange, endRange, eventCount));
    	}
    	
        int max = eventCount;
        if (keyList.size() < max)
        	max = keyList.size();

    	keyList = keyList.subList(0, max);
    	return fetchNotes(keyList, includeForgiven);
    }

    
    private List<Event> fetchEventsForAsset(String index_cf, Integer ID, Date startDate, Date endDate, Integer[] eventTypes, Integer includeForgiven)
    {
        List<Composite> keyList = new ArrayList<Composite>();
    	for(Integer eventType : eventTypes)
    	{
    		
   		
    		Composite startRange = new Composite();
    		startRange.add(0, eventType);
    		startRange.add(1, (int)DateUtil.convertDateToSeconds(startDate));
    		
    		Composite endRange = new Composite();
    		endRange.add(0, eventType);
    		endRange.add(1, (int)DateUtil.convertDateToSeconds(endDate));

    		keyList.addAll(fetchRowKeysFromIndex(index_cf, ID.intValue(), startRange, endRange, MAX_EVENTS));
    	}
    	
    	
    	return fetchNotes(keyList, (includeForgiven == 1));
    }

    private List<Composite> fetchRowKeysFromIndex(String INDEX_CF, Integer rowKey, Composite startRange, Composite endRange, Integer count)
    {
        SliceQuery<Composite, Composite, Composite> sliceQuery = HFactory.createSliceQuery(getKeyspace(), compositeSerializer, compositeSerializer, compositeSerializer);

        sliceQuery.setRange(startRange, endRange, false, count);
        
        sliceQuery.setColumnFamily(INDEX_CF);
        
		Composite rowKeyComp = new Composite();
		rowKeyComp.add(0, rowKey);
        sliceQuery.setKey(rowKeyComp);
        //rangeSlicesQuery.setReturnKeysOnly();
        
        QueryResult<ColumnSlice<Composite, Composite>> result = sliceQuery.execute();
        ColumnSlice<Composite, Composite> columnSlice = result.get();            
        
        List<Composite> keyList = new ArrayList<Composite>();
    	List<HColumn<Composite, Composite>> columnList = columnSlice.getColumns();
        for (HColumn<Composite, Composite> column : columnList)
        {
        	keyList.add(column.getValue());
        }
    	return keyList;
     }

    private List<Event> fetchNotes(List<Composite> keys, boolean includeForgiven)
    {
    	List <Event> eventList = new ArrayList<Event>();
    	MultigetSliceQuery<Composite, String, byte[]> sliceQuery = HFactory.createMultigetSliceQuery(getKeyspace(), compositeSerializer, stringSerializer, bytesArraySerializer);
        
        sliceQuery.setColumnFamily(note_CF);            
        sliceQuery.setRange(ATTRIBS_COL + "!", "~", false, 1000);  //get all the columns
        sliceQuery.setKeys(keys);
        
        QueryResult<Rows<Composite, String, byte[]>> result = sliceQuery.execute();
        
        Rows<Composite, String, byte[]> rows = result.get();     
        for (Row<Composite, String, byte[]> row : rows)
        {
        	ColumnSlice<String, byte[]> columnSlice = row.getColumnSlice();
        	List<HColumn<String, byte[]>> columnList = columnSlice.getColumns();

    		int vehicleId = bigIntegerSerializer.fromByteBuffer((ByteBuffer) row.getKey().get(0)).intValue();
    		
    		
    		byte[] raw = null;
    		String method = "";
    		boolean forgiven = false;
    		int driverId = 0;
        	for (HColumn<String, byte[]> column : columnList)
        	{
        		if (column.getName().equalsIgnoreCase(RAW_COL))
        			raw = column.getValue();
        		else if (column.getName().equalsIgnoreCase(METHOD_COL))
        			method = new String(column.getValue());
        		else if (column.getName().equalsIgnoreCase(DRIVERID_COL))
        			driverId = bigIntegerSerializer.fromBytes(column.getValue()).intValue();
            	else if (column.getName().equalsIgnoreCase(FORGIVEN_COL))
            		forgiven = true;
        	}
        		
			NoteParser parser = NoteParserFactory.getParserForMethod(method);
		    Map<String, Object> fieldMap = parser.parseNote(raw);
		    fieldMap.put("driverID", new Integer(driverId));
		    fieldMap.put("vehicleID", new Integer(vehicleId));
            
            Event event = getMapper().convertToModelObject(fieldMap, Event.class);
            
            if (event.isValidEvent() && (includeForgiven || (!includeForgiven && forgiven == false)))
            	eventList.add(event);
        }
    		
    	Collections.sort(eventList, new LatestTimeEventComparator());

		return eventList;
    }
}

package com.inthinc.pro.dao.hessian;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.mapper.EventHessianMapper;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventCategory;
import com.inthinc.pro.model.event.LoginEvent;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;

@SuppressWarnings("serial")
public class EventHessianDAO extends GenericHessianDAO<Event, Integer> implements EventDAO
{

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(EventHessianDAO.class);

    public EventHessianDAO()
    {
        super();
        super.setMapper(new EventHessianMapper());
    }

    @Override
    public List<Event> getMostRecentEvents(Integer groupID, Integer eventCount)
    {
        try
        {
            Integer[] eventTypes = getMapper().convertToArray(EventCategory.VIOLATION.getNoteTypesInCategory(), Integer.class);

            //TODO Temporarily added arbitrary 10 to hopefully be able to get the eventCount of valid events back after the clean
            List <Event> eventList = Event.cleanEvents(getMapper().convertToModelObject(getSiloService().getRecentNotes(groupID, eventCount+10, eventTypes), Event.class));
            int max = eventCount;
            if (eventList.size() < max)
            	max = eventList.size();
            return eventList.subList(0, max);
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Event> getMostRecentWarnings(Integer groupID, Integer eventCount)
    {
        try
        {
            Integer[] eventTypes = getMapper().convertToArray(EventCategory.WARNING.getNoteTypesInCategory(), Integer.class);
            return getMapper().convertToModelObject(getSiloService().getRecentNotes(groupID, eventCount, eventTypes), Event.class);
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }
    
    @Override
    public List<Event> getMostRecentEmergencies(Integer groupID, Integer eventCount)
    {
        try
        {
            Integer[] eventTypes = getMapper().convertToArray(EventCategory.EMERGENCY.getNoteTypesInCategory(), Integer.class);
            return getMapper().convertToModelObject(getSiloService().getRecentNotes(groupID, eventCount, eventTypes), Event.class);
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Event> getEventsForDriver(Integer driverID, Date startDate, Date endDate, List<NoteType> noteTypes, Integer includeForgiven)
    {
        try
        {
            Integer[] eventTypesArray = getMapper().convertToArray(noteTypes, Integer.class);

            return Event.cleanEvents(getMapper().convertToModelObject(
                    getSiloService().getDriverNote(driverID, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(endDate), includeForgiven, eventTypesArray),
                    Event.class));
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Event> getEventsForVehicle(Integer vehicleID, Date startDate, Date endDate, List<NoteType> types, Integer includeForgiven)
    {
        try
        {
            Integer[] eventTypesArray = getMapper().convertToArray(types, Integer.class);

            return Event.cleanEvents(getMapper()
                    .convertToModelObject(
                            getSiloService().getVehicleNote(vehicleID, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(endDate), includeForgiven,
                                    eventTypesArray), Event.class));
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }

    
    @Override
    public List<Event> getViolationEventsForDriver(Integer driverID, Date startDate, Date endDate, Integer includeForgiven)
    {
        return Event.cleanEvents(getEventsForDriver(driverID, startDate, endDate, EventCategory.VIOLATION.getNoteTypesInCategory(), includeForgiven));
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
    	try{
    		return getChangedCount(getSiloService().forgive(driverID, noteID));
    	}
    	catch (ProDAOException pDAOe){
    		
    		return 1; //It as already been changed either here or elsewhere
    	}
    }

    @Override
    public Integer unforgive(Integer driverID, Long noteID)
    {
        return getChangedCount(getSiloService().unforgive(driverID, noteID));
    }
    @Override
    public List<Event> getEventsForGroupFromVehicles(Integer groupID, List<NoteType> eventTypes, Date startDate, Date endDate) {

        List<Vehicle> vehicleList = getMapper().convertToModelObject(this.getSiloService().getVehiclesByGroupIDDeep(groupID), Vehicle.class);
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
        return Event.cleanEvents(eventList);
    }

	@Override
	public List<Event> getEventsForGroupFromVehicles(Integer groupID, List<NoteType> eventTypes, Integer daysBack) {

        Date endDate = new Date();
        Date startDate = DateUtil.getDaysBackDate(endDate, daysBack);
        return getEventsForGroupFromVehicles(groupID,eventTypes, startDate,endDate);
	}

	@Override
    public Event getEventNearLocation(Integer driverID, Double latitude, Double longitude, Date startDate, Date endDate) {
        return getMapper().convertToModelObject(getSiloService().getNoteNearLoc(driverID, latitude, longitude, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(endDate)), Event.class);
    }
    
    @Override
    public <T> T getEventByType(Long noteID, Class<T> clazz)
    {
        return getMapper().convertToModelObject(this.getSiloService().getNote(noteID), clazz);
    }
    
    @Override
    public Event findByID(Integer id) throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException("This method is not supported in this DAO");
    }
    
    @Override
    public Event findByID(Long id)
    {
        try
        {
            Map<String, Object> returnMap = getSiloService().getNote(id);
            return getMapper().convertToModelObject(returnMap, Event.class);
        }
        catch (EmptyResultSetException e)
        {
            return null;
        }
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
}

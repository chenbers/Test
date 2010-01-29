package com.inthinc.pro.dao.hessian;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.exceptions.ProxyException;
import com.inthinc.pro.dao.hessian.mapper.EventHessianMapper;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventCategory;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;

public class EventHessianDAO extends GenericHessianDAO<Event, Integer> implements EventDAO
{

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
            Integer[] eventTypes = EventMapper.getEventTypesInCategory(EventCategory.VIOLATION).toArray(new Integer[0]);

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
            Integer[] eventTypes = EventMapper.getEventTypesInCategory(EventCategory.WARNING).toArray(new Integer[0]);
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
            Integer[] eventTypes = EventMapper.getEventTypesInCategory(EventCategory.EMERGENCY).toArray(new Integer[0]);
            return getMapper().convertToModelObject(getSiloService().getRecentNotes(groupID, eventCount, eventTypes), Event.class);
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Event> getEventsForDriver(Integer driverID, Date startDate, Date endDate, List<Integer> eventTypes, Integer includeForgiven)
    {
        try
        {
            Integer[] eventTypesArray = eventTypes.toArray(new Integer[0]);

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
    public List<Event> getEventsForVehicle(Integer vehicleID, Date startDate, Date endDate, List<Integer> eventTypes, Integer includeForgiven)
    {
        try
        {
            Integer[] eventTypesArray = eventTypes.toArray(new Integer[0]);

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
    public List<Event> getEventsForDriverByMiles(Integer driverID, Integer milesBack, List<Integer> eventTypes)
    {
        try
        {
            Integer[] eventTypesArray = eventTypes.toArray(new Integer[0]);

            return Event.cleanEvents(getMapper().convertToModelObject(getSiloService().getNoteByMiles(driverID, milesBack, eventTypesArray), Event.class));
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
        // TODO: Remove when method is impl on back end
        catch (ProxyException ex)
        {
            if (ex.getErrorCode() == 422)
            {
                return new ArrayList<Event>();
            }
            throw ex;
        }
    }

    @Override
    public List<Event> getEventsForVehicleByMiles(Integer vehicleID, Integer milesBack, List<Integer> eventTypes)
    {
        try
        {
            Integer[] eventTypesArray = eventTypes.toArray(new Integer[0]);

            return Event.cleanEvents(getMapper().convertToModelObject(getSiloService().getNoteByMiles(vehicleID, milesBack, eventTypesArray), Event.class));
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Event> getViolationEventsForDriver(Integer driverID, Date startDate, Date endDate, Integer includeForgiven)
    {
        return Event.cleanEvents(getEventsForDriver(driverID, startDate, endDate, EventMapper.getEventTypesInCategory(EventCategory.VIOLATION), includeForgiven));
    }

    @Override
    public List<Event> getWarningEventsForDriver(Integer driverID, Date startDate, Date endDate, Integer includeForgiven)
    {
        return getEventsForDriver(driverID, startDate, endDate, EventMapper.getEventTypesInCategory(EventCategory.WARNING), includeForgiven);
    }
    
    @Override
    public List<Event> getEmergencyEventsForDriver(Integer driverID, Date startDate, Date endDate, Integer includeForgiven)
    {
        return getEventsForDriver(driverID, startDate, endDate, EventMapper.getEventTypesInCategory(EventCategory.EMERGENCY), includeForgiven);
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
    public List<Event> getViolationEventsForGroup(Integer groupID, Integer daysBack, Integer includeForgiven)
    {
        return Event.cleanEvents(getEventsForGroup(groupID, daysBack, EventMapper.getEventTypesInCategory(EventCategory.VIOLATION), includeForgiven));
    }

    @Override
    public List<Event> getWarningEventsForGroup(Integer groupID, Integer daysBack, Integer includeForgiven)
    {
        return getEventsForGroup(groupID, daysBack, EventMapper.getEventTypesInCategory(EventCategory.WARNING), includeForgiven);
    }

    @Override
    public List<Event> getEmergencyEventsForGroup(Integer groupID, Integer daysBack, Integer includeForgiven)
    {
        return getEventsForGroup(groupID, daysBack, EventMapper.getEventTypesInCategory(EventCategory.EMERGENCY), includeForgiven);
    }

    @Override
    public List<Event> getViolationEventsForGroup(Integer groupID, Date startDate, Date endDate, Integer includeForgiven)
    {
        return Event.cleanEvents(getEventsForGroup(groupID, startDate, endDate, EventMapper.getEventTypesInCategory(EventCategory.VIOLATION), includeForgiven));
    }
    
    @Override
    public List<Event> getZoneAlertsForGroup(Integer groupID, Integer daysBack, Integer includeForgiven)
    {
        return getEventsForGroup(groupID, daysBack, EventMapper.getEventTypesInCategory(EventCategory.DRIVER), includeForgiven);
    }

    @Override
    public List<Event> getWarningEventsForGroup(Integer groupID, Date startDate, Date endDate, Integer includeForgiven)
    {
        return getEventsForGroup(groupID, startDate, endDate, EventMapper.getEventTypesInCategory(EventCategory.WARNING), includeForgiven);
    }

    @Override
    public List<Event> getEmergencyEventsForGroup(Integer groupID, Date startDate, Date endDate, Integer includeForgiven)
    {
        return getEventsForGroup(groupID, startDate, endDate, EventMapper.getEventTypesInCategory(EventCategory.EMERGENCY), includeForgiven);
    }

    public List<Event> getEventsForGroup(Integer groupID, Integer daysBack, List<Integer> eventTypes, Integer includeForgiven)
    {
        Date endDate = new Date();
        Date startDate = DateUtil.getDaysBackDate(endDate, daysBack);
        return Event.cleanEvents(getEventsForGroup(groupID, startDate, endDate, eventTypes, includeForgiven));
    }
    public List<Event> getEventsForGroup(Integer groupID, Date startDate, Date endDate, List<Integer> eventTypes, int includeForgiven)
    {
//        logger.info("getDriverNoteByGroupIDDeep for groupID: " + groupID + "  startDate: " + DateUtil.convertDateToSeconds(startDate) + " endDate: " + DateUtil.convertDateToSeconds(endDate) +
//        			"includeForgiven: " + includeForgiven + " eventTypes: " + eventTypes);            
        
        List<Map<String, Object>> returnList = this.getSiloService().getDriverNoteByGroupIDDeep(groupID, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(endDate), includeForgiven, eventTypes.toArray(new Integer[0]));
        List<Event> eventList = getMapper().convertToModelObject(returnList, Event.class);
        eventList =  Event.cleanEvents(eventList);
        return eventList;
    }

	@Override
	public List<Event> getEventsForGroupFromVehicles(Integer groupID, List<Integer> eventTypes, Integer daysBack) {

        Date endDate = new Date();
        Date startDate = DateUtil.getDaysBackDate(endDate, daysBack);

        List<Vehicle> vehicleList = getMapper().convertToModelObject(this.getSiloService().getVehiclesByGroupIDDeep(groupID), Vehicle.class);
        List<Event> eventList = new ArrayList<Event>();
        for (Vehicle vehicle : vehicleList)
        {
            List<Event>vehicleEvents = getEventsForVehicle(vehicle.getVehicleID(), startDate, endDate, eventTypes, 1);
            for (Event event : vehicleEvents)
            {
                event.setVehicle(vehicle);
                eventList.add(event);
            }
        }
        return Event.cleanEvents(eventList);
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
        return getMapper().convertToModelObject(this.getSiloService().getNote(id), Event.class);
    }

	@Override
	public Integer getEventCount(Integer groupID, Integer daysBack,
			Integer includeForgiven, List<Integer> eventTypes,
			List<TableFilterField> filters) {
		
/*		
 * real implementation
        Date endDate = new Date();
        Date startDate = DateUtil.getDaysBackDate(endDate, daysBack);

        try
        {
        	//EventMapper.getEventTypesInCategory(eventCategory)
			if (filters == null)
            	filters = new ArrayList<TableFilterField>();

        	return getChangedCount(getSiloService().getDriverEventCount(groupID, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(endDate), 
									includeForgiven, getMapper().convertList(filters), 
									eventTypes.toArray(new Integer[0])));
        }
        catch (ProxyException ex) {
        	
        	if (ex.getErrorCode() == 422)
        	{
        		logger.error("getDriverEventTotalCount not implemented on backend ");
        		return 0;
        	}
        	
        }
        return 0;
*/        
/*
 * mock implementation
		return 100;
*/		
// implementation using existing methods
		
		List<Event> eventList = getEventsForGroup(groupID, daysBack, eventTypes, includeForgiven);
		return eventList.size();
		
	}

	@Override
	public List<Event> getEventPage(Integer groupID, Integer daysBack,
			Integer includeForgiven, List<Integer> eventTypes,
			PageParams pageParams) {
/*	
 * real implementation	
        Date endDate = new Date();
        Date startDate = DateUtil.getDaysBackDate(endDate, daysBack);
        
        try
        {
        	return getMapper().convertToModelObject(getSiloService().getDriverEventPage(groupID, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(endDate), 
									includeForgiven, getMapper().convertToMap(pageParams), 
									eventTypes.toArray(new Integer[0])), Event.class);
        }
        catch (ProxyException ex) {
        	
        	if (ex.getErrorCode() == 422)
        	{
        		logger.error("getDriverEventPage not implemented on backend ");
        		return new ArrayList<Event>();
        	}
        	
        }
		return new ArrayList<Event>();
*/
		
/*	
 * mock implementation	
		TimeZone timeZone[] = {
				TimeZone.getTimeZone("US/Mountain"),
				TimeZone.getTimeZone("US/Pacific"),
		};
		List<Event> eventList = new ArrayList<Event>();
		int typeCnt = 0;
		for (int i = 0; i < getEventCount(groupID, daysBack, includeForgiven, eventTypes, pageParams.getFilterList()); i++)
		{
			
			if (typeCnt >= eventTypes.size())
				typeCnt=0;
			
			Event event = null;
			Class<?> eventClass = EventMapper.getEventType(eventTypes.get(typeCnt));
            if (eventClass != null) {
                try {
					event = (Event) eventClass.newInstance();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            else {
            	event = new Event();
            }
			
			event.setNoteID(i+1000l);
			event.setDriverFullName(i + " driver Name");
			event.setDriverID(i);
			event.setVehicleName(i + " vehicle Name");
			event.setVehicleID(i);
			event.setGroupName((i/10) + " group Name");
			event.setGroupID((i/10));
			event.setTime(new Date());
			event.setDriverTimeZone(timeZone[i % 2]);
			event.setLatitude(32.96453094482422d);
			event.setLongitude(-117.12944793701172d);
			event.setType(eventTypes.get(typeCnt));
			eventList.add(event);
			
			typeCnt++;
		}
		
		return eventList.subList(pageParams.getStartRow(), pageParams.getEndRow());
*/
		
// implementation using existing methods
		
		List<Event> eventList = getEventsForGroup(groupID, daysBack, eventTypes, includeForgiven);
		for (Event event : eventList) {
			
			// unknown driver id for test account (temporary)
			if (!event.getDriverID().equals(Integer.valueOf(5690))) {
					event.setDriverFullName("Driver" + event.getDriverID());
					event.setGroupName("Group" + event.getGroupID());
					event.setDriverTimeZone(TimeZone.getTimeZone("US/Mountain"));
			}
			event.setVehicleName("Vehicle" + event.getVehicleID());
			
		}
		return eventList.subList(pageParams.getStartRow(), pageParams.getEndRow());
		
	}
}

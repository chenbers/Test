package com.inthinc.pro.dao.hessian;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.exceptions.ProxyException;
import com.inthinc.pro.dao.hessian.mapper.EventHessianMapper;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventCategory;
import com.inthinc.pro.model.EventMapper;

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
            return getMapper().convertToModelObject(getSiloService().getRecentNotes(groupID, eventCount, eventTypes), Event.class);
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

            return getMapper().convertToModelObject(
                    getSiloService().getDriverNote(driverID, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(endDate), includeForgiven, eventTypesArray),
                    Event.class);
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

            return getMapper()
                    .convertToModelObject(
                            getSiloService().getVehicleNote(vehicleID, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(endDate), includeForgiven,
                                    eventTypesArray), Event.class);
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

            return getMapper().convertToModelObject(getSiloService().getNoteByMiles(driverID, milesBack, eventTypesArray), Event.class);
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

            return getMapper().convertToModelObject(getSiloService().getNoteByMiles(vehicleID, milesBack, eventTypesArray), Event.class);
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Event> getViolationEventsForDriver(Integer driverID, Date startDate, Date endDate, Integer includeForgiven)
    {
        return getEventsForDriver(driverID, startDate, endDate, EventMapper.getEventTypesInCategory(EventCategory.VIOLATION), includeForgiven);
    }

    @Override
    public List<Event> getWarningEventsForDriver(Integer driverID, Date startDate, Date endDate, Integer includeForgiven)
    {
        return getEventsForDriver(driverID, startDate, endDate, EventMapper.getEventTypesInCategory(EventCategory.WARNING), includeForgiven);
    }

    @Override
    public Integer forgive(Integer driverID, Long noteID)
    {
        return getChangedCount(getSiloService().forgive(driverID, noteID));
    }

    @Override
    public Integer unforgive(Integer driverID, Long noteID)
    {
        return getChangedCount(getSiloService().unforgive(driverID, noteID));
    }

    @Override
    public List<Event> getViolationEventsForGroup(Integer groupID, Integer daysBack, Integer includeForgiven)
    {
        return getEventsForGroup(groupID, daysBack, EventMapper.getEventTypesInCategory(EventCategory.VIOLATION), includeForgiven);
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
        return getEventsForGroup(groupID, startDate, endDate, EventMapper.getEventTypesInCategory(EventCategory.VIOLATION), includeForgiven);
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
        return getEventsForGroup(groupID, startDate, endDate, eventTypes, includeForgiven);
    }

    public List<Event> getEventsForGroup(Integer groupID, Date startDate, Date endDate, List<Integer> eventTypes, int includeForgiven)
    {
        List<Driver> driverList = getMapper().convertToModelObject(this.getSiloService().getDriversByGroupIDDeep(groupID), Driver.class);
        List<Event> eventList = new ArrayList<Event>();
        for (Driver driver : driverList)
        {
            List<Event> driverEvents = getEventsForDriver(driver.getDriverID(), startDate, endDate, eventTypes, includeForgiven);
            for (Event event : driverEvents)
            {
                event.setDriver(driver);
                eventList.add(event);
            }
        }
        return eventList;
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
    
    
 

}

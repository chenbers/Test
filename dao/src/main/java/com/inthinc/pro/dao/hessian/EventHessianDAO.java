package com.inthinc.pro.dao.hessian;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.exceptions.ProxyException;
import com.inthinc.pro.dao.hessian.mapper.EventHessianMapper;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventCategory;
import com.inthinc.pro.model.EventMapper;

public class EventHessianDAO extends GenericHessianDAO<Event, Integer> implements EventDAO
{
    

    public EventHessianDAO()
    {
        super();
        super.setMapper(new EventHessianMapper());
    }

    @Override
    public List<Event> getMostRecentEvents(Integer groupID, Integer eventCnt)
    {
        try
        {
            Integer[] eventTypes = EventMapper.getEventTypesInCategory(EventCategory.VIOLATION).toArray(new Integer[0]);

            return getMapper().convertToModelObject(getSiloService().getRecentNotes(groupID, eventCnt, eventTypes), Event.class);
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Event> getMostRecentWarnings(Integer groupID, Integer eventCnt)
    {
        try
        {
            Integer[] eventTypes = EventMapper.getEventTypesInCategory(EventCategory.WARNING).toArray(new Integer[0]);

            return getMapper().convertToModelObject(getSiloService().getRecentNotes(groupID, eventCnt, eventTypes), Event.class);
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Event> getEventsForDriver(Integer driverID, Date startDate, Date endDate, List<Integer> eventTypes)
    {
        try
        {
            Integer[] eventTypesArray = eventTypes.toArray(new Integer[0]);

            return getMapper().convertToModelObject(getSiloService().getNote(driverID, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(endDate), eventTypesArray), Event.class);
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
    public List<Event> getViolationEventsForDriver(Integer driverID, Date startDate, Date endDate)
    {
        return getEventsForDriver(driverID, startDate, endDate, EventMapper.getEventTypesInCategory(EventCategory.VIOLATION));
    }

    @Override
    public List<Event> getWarningEventsForDriver(Integer driverID, Date startDate, Date endDate)
    {
        return getEventsForDriver(driverID, startDate, endDate, EventMapper.getEventTypesInCategory(EventCategory.WARNING));
    }
}

package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.proserver.CentralService;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventCategory;
import com.inthinc.pro.model.EventMapper;

public class EventHessianDAO extends GenericHessianDAO<Event, Integer> implements EventDAO
{
    

    @Override
    public List<Event> getMostRecentEvents(Integer groupID, Integer eventCnt)
    {
        try
        {
            Integer[] eventTypes = EventMapper.getEventTypesInCategory(EventCategory.VIOLATION).toArray(new Integer[0]);

            return convertToModelObject(getSiloService().getMostRecentEvents(groupID, eventCnt, eventTypes));
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

            return convertToModelObject(getSiloService().getMostRecentEvents(groupID, eventCnt, eventTypes));
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }
}

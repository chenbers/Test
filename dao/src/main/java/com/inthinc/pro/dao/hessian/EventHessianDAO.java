package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.proserver.CentralService;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventMapper;

public class EventHessianDAO extends GenericHessianDAO<Event, Integer, CentralService> implements EventDAO
{
    

    @Override
    public List<Event> getMostRecentEvents(Integer groupID, Integer eventCnt)
    {
        try
        {
            Integer[] recentEventTypes = {
                    EventMapper.TIWIPRO_EVENT_NOTEEVENT,
                    EventMapper.TIWIPRO_EVENT_SEATBELT,
                    EventMapper.TIWIPRO_EVENT_SPEEDING,
                    EventMapper.TIWIPRO_EVENT_SPEEDING_EX3,
                    EventMapper.TIWIPRO_EVENT_WSZONES_ARRIVAL,
                    EventMapper.TIWIPRO_EVENT_WSZONES_ARRIVAL_EX,
                    EventMapper.TIWIPRO_EVENT_WSZONES_DEPARTURE,
                    EventMapper.TIWIPRO_EVENT_WSZONES_DEPARTURE_EX,
                    EventMapper.TIWIPRO_EVENT_ZONE_ENTER_ALERTED,
                    EventMapper.TIWIPRO_EVENT_ZONE_EXIT_ALERTED
            };

            return convertToModelObject(getSiloService().getMostRecentEvents(groupID, eventCnt, recentEventTypes));
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }

}

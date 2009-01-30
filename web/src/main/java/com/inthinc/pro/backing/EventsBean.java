package com.inthinc.pro.backing;

import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.TableType;

public class EventsBean extends BaseEventsBean
{
    private static final Logger     logger                  = Logger.getLogger(EventsBean.class);
    
    @Override
    protected List<Event> getEventsForGroup(Integer groupID)
    {
        return getEventDAO().getViolationEventsForGroup(groupID, 7);
    }

    // TablePrefOptions interface

    @Override
    public TableType getTableType()
    {
        return TableType.EVENTS;
    }
    
    public String showAllFromRecentAction()
    {
        setSearchText(null);
        setCategoryFilter(null);
        setEventFilter(null);
        
        refreshAction();
        return "go_events";
    }
}

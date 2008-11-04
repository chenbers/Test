package com.inthinc.pro.backing;

import java.util.List;

import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.model.Event;

public class TeamRecentEventsBean extends BaseBean
{
    private List<Event> recentEvents;
    
    private NavigationBean navigation;
    private EventDAO eventDAO;
    
    private Integer groupID;

    


    public List<Event> getRecentEvents()
    {
        if (recentEvents == null)
        {
            recentEvents = eventDAO.getMostRecentEvents(getGroupID(), 5);
        }
        return recentEvents;
    }

    public void setRecentEvents(List<Event> recentEvents)
    {
        this.recentEvents = recentEvents;
    }

    public NavigationBean getNavigation()
    {
        return navigation;
    }

    public void setNavigation(NavigationBean navigation)
    {
        this.navigation = navigation;
    }

    public EventDAO getEventDAO()
    {
        return eventDAO;
    }

    public void setEventDAO(EventDAO eventDAO)
    {
        this.eventDAO = eventDAO;
    }
    public Integer getGroupID()
    {
        setGroupID(navigation.getGroupID());
        if (groupID == null)
        {
            setGroupID(getUser().getPerson().getGroupID());
        }
        return groupID;
    }

    public void setGroupID(Integer groupID)
    {
        this.groupID = groupID;
    }

}

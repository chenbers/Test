package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;

import com.inthinc.pro.backing.ui.EventDisplay;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.model.Event;

public class TeamRecentEventsBean extends BaseBean
{
    private List<EventDisplay> recentEvents;
    
    private NavigationBean navigation;
    private EventDAO eventDAO;
    
    private Integer groupID;

    public List<EventDisplay> getRecentEvents()
    {
        if (recentEvents == null)
        {
            List<Event> events = eventDAO.getMostRecentEvents(getGroupID(), 5);
            
            recentEvents = new ArrayList<EventDisplay>();
            for (Event event : events)
            {
                recentEvents.add(new EventDisplay(event));
            }
            
        }
        return recentEvents;
    }

    public void setRecentEvents(List<EventDisplay> recentEvents)
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

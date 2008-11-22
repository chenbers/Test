package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.EventDisplay;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventType;

public class TeamRecentEventsBean extends BaseBean
{
    
    private static final Logger logger = Logger.getLogger(TeamRecentEventsBean.class);
    private List<EventDisplay> recentEvents;
    
    private NavigationBean navigation;
    private EventDAO eventDAO;
    private Integer groupID;
    private EventDisplay selectedEvent;

    public EventDisplay getSelectedEvent()
    {
        return selectedEvent;
    }

    public void setSelectedEvent(EventDisplay selectedEvent)
    {
        this.selectedEvent = selectedEvent;
    }

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
    
    public String detailsAction()
    {
        if (selectedEvent != null)
        {
            EventType eventType = selectedEvent.getEvent().getEventType();
            if (eventType.equals(EventType.SEATBELT))
            {
                return "go_reportDriverSeatBelt";
            }
            else if (eventType.equals(EventType.SPEEDING))
            {
                return "go_reportDriverSpeed";
            }
            return "go_reportDriverStyle";
        }
        return "";
    }

}

package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.backing.ui.EventDisplay;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.model.Event;

public class TeamWarningsBean extends BaseBean
{
    private List<EventDisplay> warnings;
    
    private NavigationBean navigation;
    private EventDAO eventDAO;
    
    private Integer groupID;

    public List<EventDisplay> getWarnings()
    {
        if (warnings == null)
        {
            // TODO: get warnings
            List<Event> events = eventDAO.getMostRecentEvents(getGroupID(), 5);
            
            warnings = new ArrayList<EventDisplay>();
            for (Event event : events)
            {
                warnings.add(new EventDisplay(event));
            }
            
        }
        return warnings;
    }

    public void setWarnings(List<EventDisplay> warnings)
    {
        this.warnings = warnings;
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

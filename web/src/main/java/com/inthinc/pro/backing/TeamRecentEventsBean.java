package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.EventDisplay;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventCategory;
import com.inthinc.pro.model.event.EventType;
import com.inthinc.pro.model.TimeFrame;

public class TeamRecentEventsBean extends BaseBean
{
    
    private static final Logger logger = Logger.getLogger(TeamRecentEventsBean.class);
    private List<EventDisplay> recentEvents;
    
    private NavigationBean navigation;
    private EventDAO eventDAO;
    private DriverDAO driverDAO;
    private Integer groupID;
    private EventDisplay selectedEvent;
    private static final EventCategory category = EventCategory.VIOLATION;
    private String groupName;

    static final Comparator<EventDisplay> EVENT_ORDER =  new Comparator<EventDisplay>()  {
                public int compare(EventDisplay e1, EventDisplay e2) 
                {       if( e2.getTime().compareTo(e1.getTime()) == 0 )
                            return e2.getDriverName().compareTo(e1.getDriverName());       
                        else
                            return e2.getTime().compareTo(e1.getTime());  } };

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
            populateDriver(events);
            
            recentEvents = new ArrayList<EventDisplay>();
            for (Event event : events)
            {
                recentEvents.add(new EventDisplay(event));
            }
            Collections.sort(recentEvents, EVENT_ORDER);
            
        }
        return recentEvents;
    }

    // TODO: if this becomes a performance issue -- ask back end to populate this in getRecentNotes() method
    private void populateDriver(List<Event> events)
    {
        Map<Integer, Driver> driverMap  = new HashMap<Integer, Driver>();
        
        for (Event event : events)
        {
            if (event.getDriver() == null && event.getDriverID() != null)
            {
                Driver driver = driverMap.get(event.getDriverID());
                if (driver == null)
                {
                    driver = driverDAO.findByID(event.getDriverID());
                    driverMap.put(event.getDriverID(), driver);
                }
                event.setDriver(driver);
            }
        }
    }

    public String getGroupName()
    {        
        return groupName = (getGroupHierarchy().getGroup(getGroupID())).getName();
    }

    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
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
            setGroupID(getUser().getGroupID());
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
            else if (eventType.equals(EventType.IDLING))
            {
                return "go_safety";
            }
            return "go_reportDriverStyle";
        }
        return "";
    }

    public EventCategory getCategory()
    {
        return category;
    }

    public DriverDAO getDriverDAO()
    {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO)
    {
        this.driverDAO = driverDAO;
    }
    public TimeFrame getTimeFrame() {
    	return TimeFrame.YEAR;
    }


}

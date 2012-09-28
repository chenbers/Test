package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.backing.ui.EventDisplay;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventCategory;
import com.inthinc.pro.model.Vehicle;

public class TeamWarningsBean extends BaseBean
{
    private NavigationBean navigation;
    private EventDAO eventDAO;
    private DriverDAO driverDAO;
    private VehicleDAO vehicleDAO;
    private List<EventDisplay> warnings;
    private List<EventDisplay> emergencies;
    private Integer groupID;
    private static final EventCategory category = EventCategory.WARNING;
    private String groupName;
    
    static final Comparator<EventDisplay> EVENT_ORDER =  new Comparator<EventDisplay>()  {
        public int compare(EventDisplay e1, EventDisplay e2) 
        {       if( e2.getTime().compareTo(e1.getTime()) == 0 )
                    return e2.getDriverName().compareTo(e1.getDriverName());       
                else
                    return e2.getTime().compareTo(e1.getTime());  } };

    public List<EventDisplay> getWarnings()
    {
        if (warnings == null)
        {
            List<Event> events = eventDAO.getMostRecentWarnings(getGroupID(), 5);
            populateDriverVehicle(events);
            
            warnings = new ArrayList<EventDisplay>();
            
            for (Event event : events)
            {
                warnings.add(new EventDisplay(event));
            }
            Collections.sort(warnings, EVENT_ORDER);

        }
        return warnings;
    }

    public List<EventDisplay> getEmergencies()
    {
        if(emergencies == null)
        {
            List<Event> events = eventDAO.getMostRecentEmergencies(getGroupID(), 5);
            populateDriverVehicle(events);
            
            emergencies = new ArrayList<EventDisplay>();
            
            for (Event event : events)
            {
                emergencies.add(new EventDisplay(event));
            }
            Collections.sort(emergencies, EVENT_ORDER);
        }
        return emergencies;
    }

    // TODO: if this becomes a performance issue -- ask back end to populate this in getRecentNotes() method
    private void populateDriverVehicle(List<Event> events)
    {
        Map<Integer, Driver> driverMap  = new HashMap<Integer, Driver>();
        Map<Integer, Vehicle> vehicleMap  = new HashMap<Integer, Vehicle>();
        
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
                
                Vehicle vehicle = vehicleMap.get(event.getVehicleID());
                if (vehicle == null)
                {
                    vehicle = vehicleDAO.findByID(event.getVehicleID());
                    vehicleMap.put(event.getVehicleID(), vehicle);
                }
                event.setVehicle(vehicle);
            }
        }
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

    public VehicleDAO getVehicleDAO()
    {
        return vehicleDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO)
    {
        this.vehicleDAO = vehicleDAO;
    }

    public String getGroupName()
    {
        return groupName = (getGroupHierarchy().getGroup(getGroupID())).getName();
    }

    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }


}

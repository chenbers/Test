package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.TripDisplay;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.DriverDAO;

public class DriverTripsBean extends BaseBean
{
    private static final Logger logger = Logger.getLogger(DriverTripsBean.class);

    private NavigationBean navigation;
    private DriverDAO driverDAO;
    private EventDAO eventDAO;
    
    private Date startDate = new Date();
    private Date endDate = new Date();
    
    private Integer milesDriven = new Integer(0);
    private String idleTime = "0:45"; //
    private Integer numTrips = 0;
    private Integer totalDriveTime;
    
    private boolean showLastTenTrips = false;
    private boolean showIdleMarkers = true;
    private boolean showWarnings = true;
    
    private List<TripDisplay> trips;
    private List<TripDisplay> selectedTrips;
    private TripDisplay selectedTrip;
	private List<Event> violationEvents;

	private Integer tripsPager = 0;
    private Integer tripPager = 0;

    
	public void init()
    {
    	// Set start date to 7 days ago.
	    Calendar calendar = Calendar.getInstance();
    	calendar.add(Calendar.DAY_OF_MONTH, -7);
    	startDate = calendar.getTime();
    	
    	initTrips();
    }
    
    public void initTrips()
    {  
        List<Trip> tempTrips = new ArrayList<Trip>();
        tempTrips = driverDAO.getTrips(navigation.getDriver().getDriverID(), startDate, endDate);

        trips = new ArrayList<TripDisplay>();
        selectedTrips = new ArrayList<TripDisplay>();
        
        for (Trip trip : tempTrips)
        {
            if(trip.getStartTime().before(startDate))
                continue;
            
            trips.add( 0, new TripDisplay(trip) );
            
            milesDriven += trip.getMileage();
            //totalDriveTime += ( trip.getEndTime() - trip.getStartTime() );
        }

        numTrips = trips.size();
        
        if(numTrips > 0)
        {
            selectedTrips.add(trips.get(0));
            
            Date vStart = selectedTrips.get(0).getTrip().getStartTime();
            Date vEnd = selectedTrips.get(0).getTrip().getEndTime();
            
            initViolations(vStart, vEnd);
        }     
    }
 
    public void initViolations(Date start, Date end)
    {
        List<Integer> types = new ArrayList<Integer>();
        types.add(93); // EventMapper.TIWIPRO_EVENT_SPEEDING_EX3
        types.add(3);  // EventMapper.TIWIPRO_EVENT_SEATBELT
        types.add(2);  // EventMapper.TIWIPRO_EVENT_NOTEEVENT
        
        violationEvents = eventDAO.getEventsForDriver(navigation.getDriver().getDriverID(), start, end, types);  
    }
    
    //DATE PROPERTIES
    public Date getStartDate()
    {
        return startDate;
    }   
    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }
    public Date getEndDate()
    {
        return endDate;
    }
    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    //TRIP DAO PROPERTIES
    public DriverDAO getDriverDAO() {
        return driverDAO;
    }
    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }
	
	//MILES PROPERTIES
	public Integer getMilesDriven() {
		return milesDriven / 100;
	}
	public void setMilesDriven(Integer milesDriven) {
		this.milesDriven = milesDriven;
	}
	
	//IDLE TIME PROPERTIES
	public String getIdleTime() {
		return idleTime;
	}
	public void setIdleTime(String idleTime) {
		this.idleTime = idleTime;
	}
	
	//NUMBER OF TRIPS PROPERTIES
	public Integer getNumTrips() {
		return numTrips;
	}
	public void setNumTrips(Integer numTrips) {
		this.numTrips = numTrips;
	}
	
	//TOTAL DRIVE TIME PROPERTIES
	public Integer getTotalDriveTime() {
		return totalDriveTime;
	}
	public void setTotalDriveTime(Integer totalDriveTime) {
		this.totalDriveTime = totalDriveTime;
	}
	
	//SHOW ALL TRIPS SETTING PROPERTIES
	public boolean isShowLastTenTrips() {
		return showLastTenTrips;
	}
	public void setShowLastTenTrips(boolean showLastTenTrips)
	{
		this.showLastTenTrips = showLastTenTrips; 
		selectedTrips.clear();

		if (showLastTenTrips)
		{
			int count = 0;
			for (TripDisplay trip : trips)
			{
				if (count == 10)
					break;

				selectedTrips.add(trip);  //Trips are already in reverse order. (Most recent first)
				count++;
			}
			
			//TODO RESET START END DATES TO MATCH THE TRIPS
		} 
		else 
		{
			if (selectedTrip == null) {
				selectedTrips.add(trips.get(0));
			} else
				selectedTrips.add(selectedTrip);
		}	
		
		if(selectedTrips.size() > 0)
		    initViolations(selectedTrips.get(selectedTrips.size()-1).getTrip().getStartTime(), selectedTrips.get(0).getTrip().getEndTime());
        
	}
	
	//SHOW IDLE MARKERS SETTING PROPERTIES
	public boolean isShowIdleMarkers() {
		return showIdleMarkers;
	}
	public void setShowIdleMarkers(boolean showIdleMarkers) {
		this.showIdleMarkers = showIdleMarkers;
	}
	
	//SHOW WARNINGS SETTING PROPERTIES
	public boolean isShowWarnings() {
		return showWarnings;
	}
	public void setShowWarnings(boolean showWarnings) {
		this.showWarnings = showWarnings;
	}
	
	//TRIP PROPERTIES
    public List<TripDisplay> getTrips()
    {
        return trips;
    }
    public void setTrips(List<TripDisplay> trips)
    {
        this.trips = trips;
    }

    //TRIPS PAGER COUNTER
    public Integer getTripsPager()
    {
        return tripsPager;
    }
    public void setTripsPager(Integer tripsPager)
    {
        this.tripsPager = tripsPager;
    }

    //TRIP PAGER COUNTER
    public Integer getTripPager()
    {
        return tripPager;
    }
    public void setTripPager(Integer tripPager)
    {
        this.tripPager = tripPager;
    }

    //SELECTD TRIP PROPERTIES
	public TripDisplay getSelectedTrip() 
	{
		return selectedTrip;
	}
    public void setSelectedTrip(TripDisplay selectedTrip)
    {
        selectedTrips.clear();
        selectedTrips.add(selectedTrip);
        this.showLastTenTrips = false;
        initViolations(selectedTrip.getTrip().getStartTime(), selectedTrip.getTrip().getEndTime());
    }

    //NAVIGATION PROPERTIES
    public NavigationBean getNavigation()
    {
        return navigation;
    }
    public void setNavigation(NavigationBean navigation)
    {
        this.navigation = navigation;
    }
    
    //VIOLATIONS PROPERTIES
    public List<Event> getViolationEvents() {
		return violationEvents;
	}

	public void setViolationEvents(List<Event> violationEvents) {
		this.violationEvents = violationEvents;
	}
	//EVENT DAO PROPERTIES
    public EventDAO getEventDAO() {
		return eventDAO;
	}

	public void setEventDAO(EventDAO eventDAO) {
		this.eventDAO = eventDAO;
	}
	
	//SELECTED TRIPS PROPERTIES
    public List<TripDisplay> getSelectedTrips() {
		return selectedTrips;
	}

	public void setSelectedTrips(List<TripDisplay> selectedTrips) {
		this.selectedTrips = selectedTrips;
	}
}

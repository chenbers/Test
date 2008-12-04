package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.management.timer.Timer;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.TripDisplay;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.dao.TripDAO;

public class DriverTripsBean extends BaseBean
{
    private static final Logger logger = Logger.getLogger(DriverTripsBean.class);
    
    private Date startDate = new Date();
    private Date endDate = new Date();
    
    private NavigationBean navigation;
    
    private Integer milesDriven = new Integer(0);
    private Integer numStops = 0;
    private String idleTime = "0:45";
    private Integer numTrips = 0;
    private Integer totalDriveTime;
    
    private boolean showAllTrips = false;
    private boolean showIdleMarkers = false;
    private boolean showWarnings = true;
    
    private List<TripDisplay> trips;
    private TripDisplay lastTrip;
    private TripDisplay selectedTrip;
    
    private Integer tripsPager = 0;
    private Integer tripPager = 0;
    private TripDAO tripDAO;
    
    public void init()
    {
    	Calendar calendar = Calendar.getInstance();
    	calendar.add(Calendar.DAY_OF_MONTH, -7);
    	
    	startDate = calendar.getTime();
    	
    	initTrips();

    }
    
    public void initTrips()
    {
        logger.debug("## initTrips()");
        
        List<Trip> tempTrips = new ArrayList<Trip>();
        tempTrips = tripDAO.getTrips(navigation.getDriver().getDriverID(), DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(endDate));

        trips = new ArrayList<TripDisplay>();
        for (Trip trip : tempTrips)
        {
            trips.add(new TripDisplay(trip));
            
            milesDriven += trip.getMileage();
            //totalDriveTime += ( trip.getEndTime() - trip.getStartTime() );
            
            //numStops =  ? 
            //idleTime =  ?
        }
        
        numTrips = trips.size();
        
        if(trips.size() > 0)
        {
            lastTrip = trips.get( numTrips-1 );
            setSelectedTrip( trips.get(numTrips-1) );
        }
        
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
    public TripDAO getTripDAO() {
        return tripDAO;
    }
    public void setTripDAO(TripDAO tripDAO) {
        this.tripDAO = tripDAO;
    }
	
	//MILES PROPERTIES
	public Integer getMilesDriven() {
		return milesDriven / 100;
	}
	public void setMilesDriven(Integer milesDriven) {
		this.milesDriven = milesDriven;
	}
	
	//STOPS PROPERTIES
	public Integer getNumStops() {
		return numStops;
	}
	public void setNumStops(Integer numStops) {
		this.numStops = numStops;
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
	public boolean isShowAllTrips() {
		return showAllTrips;
	}
	public void setShowAllTrips(boolean showAllTrips) {
		this.showAllTrips = showAllTrips;
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

    //LAST TRIP PROPERTIES
    public TripDisplay getLastTrip()
    {
        return lastTrip;
    }
    public void setLastTrip(TripDisplay lastTrip)
    {
        this.lastTrip = lastTrip;
    }

    //SELECTED TRIP PROPERTIES
    public TripDisplay getSelectedTrip()
    {
        return selectedTrip;
    }
    public void setSelectedTrip(TripDisplay selectedTrip)
    {
        this.selectedTrip = selectedTrip;
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
}

package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Trip;


import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.TripDAO;


public class DriverTripsBean extends BaseBean
{
    private Date startDate = new Date();
    private Date endDate = new Date();
    
    private Integer milesDriven;
    private Integer numStops;
    private String idleTime;
    private Integer numTrips;
    private String totalDriveTime;
    
    private boolean showAllTrips = false;
    private boolean showIdleMarkers = false;
    private boolean showWarnings = true;
    
    private List<Trip> trips = new ArrayList<Trip>();
    private TripDAO tripDAO;
    
    
    //DATE PROPERTIES
    public Date getStartDate()
    {
        return startDate;
    }   
    public String getStartDateAsShortString()
    {
    	return DateUtil.getDisplayDateShort(DateUtil.convertDateToSeconds(startDate));
    }
    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }
    public Date getEndDate()
    {
        return endDate;
    }
    public String getEndDateAsShortString()
    {
    	return DateUtil.getDisplayDateShort(DateUtil.convertDateToSeconds(endDate));
    }
    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    //TRIP DAO PROPERTIES
    public TripDAO getTripDAO()
    {
        return tripDAO;
    }
    public void setTripDAO(TripDAO tripDAO)
    {
        this.tripDAO = tripDAO;
    }
    
    //TRIP PROPERTIES
	public List<Trip> getTrips() {
		// TODO: update numTrips of trips.count.
		// TODO: update totalDriveTime of trips.
		
		//		trips = tripDAO.getTrips(1, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(endDate)-5000);
		
		Trip t = new Trip();
		t = new Trip();
		t.setStartTime(DateUtil.convertDateToSeconds(startDate));
		t.setMileage(123);
		t.setEndAddressStr("123 Street");
		t.setStartAddressStr("456 Street");
		trips.add(t);

		Trip t2 = new Trip();
		t2.setStartTime(DateUtil.convertDateToSeconds(startDate) - 3000);
		t2.setMileage(123);
		t2.setEndAddressStr("123 Street");
		t2.setStartAddressStr("456 Street");
		trips.add(t2);
		
		return trips;
	}
	public void setTrips(List<Trip> trips) {
		this.trips = trips;
	}
	
	//MILES PROPERTIES
	public Integer getMilesDriven() {
		return milesDriven;
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
	public String getTotalDriveTime() {
		return totalDriveTime;
	}
	public void setTotalDriveTime(String totalDriveTime) {
		this.totalDriveTime = totalDriveTime;
	}
	
	//SHOW ALL TRIPS SETTING
	public boolean isShowAllTrips() {
		return showAllTrips;
	}
	public void setShowAllTrips(boolean showAllTrips) {
		this.showAllTrips = showAllTrips;
	}
	
	//SHOW IDLE MARKERS SETTING
	public boolean isShowIdleMarkers() {
		return showIdleMarkers;
	}
	public void setShowIdleMarkers(boolean showIdleMarkers) {
		this.showIdleMarkers = showIdleMarkers;
	}
	
	//SHOW WARNINGS SETTING
	public boolean isShowWarnings() {
		return showWarnings;
	}
	public void setShowWarnings(boolean showWarnings) {
		this.showWarnings = showWarnings;
	}
}

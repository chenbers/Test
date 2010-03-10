package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.backing.listener.TimeFrameChangeListener;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.map.MapIcon;
import com.inthinc.pro.map.MapIconFactory;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.TripStatus;

public class TeamTripsBean extends BaseBean implements TimeFrameChangeListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<DriverTrips> driversTrips;
    private DriverDAO driverDAO;
    private EventDAO eventDAO;
    private List<MapIcon> icons;
    private TeamCommonBean teamCommonBean;
    
	private boolean timeFrameChanged;
	
    public void init(){
    	
		icons = MapIconFactory.IconType.TEAM_LEGEND.getIconList(15);
		teamCommonBean.addTimeFrameChangeListener(this);
		timeFrameChanged = false;
		
		initDrivers();
		
    }
    private void initDrivers(){
    	
		List<Driver> driversList = driverDAO.getDrivers(teamCommonBean.getGroupID());
		driversTrips = new ArrayList<DriverTrips>();
		for (Driver driver:driversList){
			
			DriverTrips dw = new DriverTrips(driver.getDriverID(),driver.getPerson().getFullName());
			driversTrips.add(dw);
		}
    }
    public void reset(){
    	
    	for(DriverTrips dw:driversTrips){
    		
    		dw.setSelected(false);
    	}
    }
 	public List<DriverTrips> getDrivers() {
		
		return driversTrips;
	}
 	public List<DriverTrips> getSelectedDrivers(){
 		
 		List<DriverTrips> selectedDrivers = new ArrayList<DriverTrips>();
 		
 		if (timeFrameChanged){
 			
	 	   	for(DriverTrips dw:driversTrips){
	 	   		
	 	   		if ( dw.isSelected()){
	 	   			
	 	   			selectedDrivers.add(dw);
	 	   		}
	 	   	}
 		}
 	   	return selectedDrivers;
 	}
	public void setDrivers(List<DriverTrips> drivers) {
		this.driversTrips = drivers;
	}
	public void reloadTrips(){
		
    	for(DriverTrips dt:driversTrips){
    		
    		dt.reloadTrips(timeFrameChanged);
    	}
	}
	public void clearTrips(){
		
    	for(DriverTrips dt:driversTrips){
    		
    		dt.clearTrips();
    	}		
	}
	public DriverDAO getDriverDAO() {
		return driverDAO;
	}
	public void setDriverDAO(DriverDAO driverDAO) {
		this.driverDAO = driverDAO;
	}
	public List<MapIcon> getIcons() {
		return icons;
	}
	public void setIcons(List<MapIcon> icons) {
		this.icons = icons;
	}
	public Integer getGroupID() {
		return teamCommonBean.getGroupID();
	}
	public TeamCommonBean getTeamCommonBean() {
		return teamCommonBean;
	}
	public void setTeamCommonBean(TeamCommonBean teamCommonBean) {
		this.teamCommonBean = teamCommonBean;
	}
	public EventDAO getEventDAO() {
		return eventDAO;
	}
	public void setEventDAO(EventDAO eventDAO) {
		this.eventDAO = eventDAO;
	}
	
	@Override
	public void onTimeFrameChange() {
		
		clearTrips();
		timeFrameChanged = true;
	}
	public boolean isTimeFrameChanged() {
		
		return timeFrameChanged;
	}

/*
 * 
 * TeamTrip inner class
 * 
 */
	public class TeamTrip {

	    List<LatLng> route;
	    LatLng routeLastStep;
	    LatLng beginningPoint;
	    boolean inProgress;
	    
	    public TeamTrip(Trip trip)
	    {
	         
	        route = trip.getRoute();
	        inProgress = trip.getStatus().equals(TripStatus.TRIP_IN_PROGRESS);        
	        if(route.size() > 0)
	        {
	            routeLastStep = route.get(route.size()-1);
	            routeLastStep.setLat(routeLastStep.getLat() + 0.00001);
	            
	            beginningPoint = route.get(0);
	            beginningPoint.setLat(beginningPoint.getLat() - 0.00001);
	        }
	    }
	 
	    public List<LatLng> getRoute()
	    {
	        return route;
	    }

	    public void setRoute(List<LatLng> route)
	    {
	        this.route = route;
	    }

	    public LatLng getRouteLastStep()
	    {
	        return routeLastStep;
	    }

	    public void setRouteLastStep(LatLng routeLastStep)
	    {
	        this.routeLastStep = routeLastStep;
	    }
	        
	    public LatLng getBeginningPoint()
	    {
	        return beginningPoint;
	    }
	    
	    public void setBeginningPoint(LatLng beginningPoint)
	    {
	        this.beginningPoint = beginningPoint;
	    }
	    
	    public boolean isInProgress() {
	    	return inProgress;
		}
		public void setInProgress(boolean inProgress) {
			this.inProgress = inProgress;
		}
	}
/*
 * 
 * Driver Trips inner class
 * 
 */
	public class DriverTrips {
		
		private Integer driverID;
		private String fullName;
		private List<TeamTrip> trips;
	    private List<LatLng> violations = new ArrayList<LatLng>();
	    private List<LatLng> idles = new ArrayList<LatLng>();
	    private List<LatLng> tampers = new ArrayList<LatLng>();

		boolean selected;
		
		public DriverTrips(Integer driverID, String fullName) {
			super();
			this.driverID = driverID;
			this.fullName = fullName;
			this.selected = false;
		}
		public boolean isSelected() {
			return selected;
		}
		public void setSelected(boolean selected) {
			
			this.selected = selected;
			
			if (selected) {
				
				loadTripsAndEvents();
			}
			else {
				
				clearTrips();
			}
		}
		public List<TeamTrip> getTrips() {
			return trips;
		}
		public void setTrips(List<TeamTrip> trips) {
			this.trips = trips;
		}
		public void reloadTrips(boolean timeFrameChanged){
			
			if (selected && timeFrameChanged) loadTripsAndEvents();
		}
		public void clearTrips(){
			if (trips != null) {
				
				trips.clear();
				trips = null;
				violations.clear();
				idles.clear();
				tampers.clear();
			}
		}
		private void loadTripsAndEvents(){
			
			if(trips == null){
				
				loadTrips();
		        loadViolations();
			}
		}
		public List<LatLng> getViolations() {
			return violations;
		}
		public void setViolations(List<LatLng> violationEvents) {
			this.violations = violationEvents;
		}
		public List<LatLng> getIdles() {
			return idles;
		}
		public void setIdles(List<LatLng> idles) {
			this.idles = idles;
		}
		public List<LatLng> getTampers() {
			return tampers;
		}
		public void setTampers(List<LatLng> tampers) {
			this.tampers = tampers;
		}
		
		private void loadTrips(){
			
		   List<Trip> tripsList = driverDAO.getTrips(driverID, teamCommonBean.getTimeFrame().getInterval());
	       trips = new ArrayList<TeamTrip>();

	       for (Trip trip : tripsList) {
	        	
	    	   TeamTrip td = new TeamTrip(trip);
	    	   trips.add(td);
	 
	        }
		}
		private void loadViolations( ) {
	        if (violations.isEmpty()) {
	        	
	            List<Integer> violationEventTypeList = new ArrayList<Integer>();
	            violationEventTypeList.add(EventMapper.TIWIPRO_EVENT_SPEEDING_EX3);
	            violationEventTypeList.add(EventMapper.TIWIPRO_EVENT_SEATBELT);
	            violationEventTypeList.add(EventMapper.TIWIPRO_EVENT_NOTEEVENT);
	            List<Integer> idleTypes = new ArrayList<Integer>();
	            idleTypes.add(EventMapper.TIWIPRO_EVENT_IDLE);
	            List<Integer> tamperEventTypeList = new ArrayList<Integer>();
	            tamperEventTypeList.add(EventMapper.TIWIPRO_EVENT_UNPLUGGED);
	            tamperEventTypeList.add(EventMapper.TIWIPRO_EVENT_UNPLUGGED_ASLEEP);

                List<Event>violationEvents = eventDAO.getEventsForDriver(driverID, teamCommonBean.getStartTime().toDate(), teamCommonBean.getEndTime().toDate(), violationEventTypeList, showExcludedEvents);
                List<Event>idleEvents = eventDAO.getEventsForDriver(driverID, teamCommonBean.getStartTime().toDate(), teamCommonBean.getEndTime().toDate(), idleTypes, showExcludedEvents);
                List<Event>tamperEvents = eventDAO.getEventsForDriver(driverID, teamCommonBean.getStartTime().toDate(), teamCommonBean.getEndTime().toDate(), tamperEventTypeList, showExcludedEvents);
	            
                for (Event e:violationEvents){
                	violations.add(e.getLatLng());
                }
                for (Event e:idleEvents){
                	idles.add(e.getLatLng());
                }
                for (Event e:tamperEvents){
                	tampers.add(e.getLatLng());
                }
	        }
	    }
		public Integer getDriverID() {
			return driverID;
		}
		public void setDriverID(Integer driverID) {
			this.driverID = driverID;
		}
		public String getFullName() {
			return fullName;
		}
		public void setFullName(String driverFullName) {
			this.fullName = driverFullName;
		}
	}

}

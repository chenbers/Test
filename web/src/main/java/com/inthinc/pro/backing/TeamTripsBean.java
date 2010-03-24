package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ajax4jsf.model.KeepAlive;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.map.MapIcon;
import com.inthinc.pro.map.MapIconFactory;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.TripStatus;

@KeepAlive
public class TeamTripsBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<DriverTrips> driversTrips;
    private DriverDAO driverDAO;
    private EventDAO eventDAO;
    private List<MapIcon> icons;
    private TeamCommonBean teamCommonBean;
    
	private TimeFrame myTimeFrame;
	
    public void init(){
    	
		icons = MapIconFactory.IconType.TEAM_LEGEND.getIconList(15);
		myTimeFrame =teamCommonBean.getTimeFrame();
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
 	public List<DriverTrips> getSelectedDriversTrips(){
 		
 		List<DriverTrips> selectedDrivers = new ArrayList<DriverTrips>();
 		
 		if (!myTimeFrame.equals(teamCommonBean.getTimeFrame())){
 			
	 	   	for(DriverTrips dt:driversTrips){
	 	   		
	 	   		if ( dt.isSelected()){
	 	   			
	 	   			dt.reloadTrips();
	 	   			selectedDrivers.add(dt);
	 	   		}
	 	   	}
	 	   	myTimeFrame = teamCommonBean.getTimeFrame();
 		}
 	   	return selectedDrivers;
 	}
	public void setDrivers(List<DriverTrips> drivers) {
		this.driversTrips = drivers;
	}
	public void reloadTrips(){
		
    	for(DriverTrips dt:driversTrips){
    		
    		dt.reloadTrips();
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
	    private List<LatLng> violations;
	    private List<LatLng> idles;
	    private List<LatLng> tampers;
	    
	    public TeamTrip(Trip trip, 
	    				List<LatLng> violations,
	    				List<LatLng> idles,
	    				List<LatLng> tampers,
	    				int compressionFactor)
	    {
	         
	        route = compressRoute(trip.getRoute(), compressionFactor);
	        inProgress = trip.getStatus().equals(TripStatus.TRIP_IN_PROGRESS);        
	        if(route.size() > 0)
	        {
	            routeLastStep = route.get(route.size()-1);
	            routeLastStep.setLat(routeLastStep.getLat() + 0.00001);
	            
	            beginningPoint = route.get(0);
	            beginningPoint.setLat(beginningPoint.getLat() - 0.00001);
	        }
	        this.violations=violations;
	        this.idles = idles;
	        this.tampers = tampers;
	    }
	    private List<LatLng> compressRoute(List<LatLng> route, int compressionFactor){
	    	
	    	List<LatLng> compressedRoute = new ArrayList<LatLng>();
	    	compressedRoute.add(route.get(0));
	    	for (int i=compressionFactor; i< route.size();i+=compressionFactor){
	    		
	    		compressedRoute.add(route.get(i));
	    	}
	    	compressedRoute.add(route.get(route.size()-1));
	    	return compressedRoute;
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
		public List<LatLng> getViolations() {
			return violations;
		}
		public void setViolations(List<LatLng> violations) {
			this.violations = violations;
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
		public void reloadTrips(){
			
			if (selected) reloadTripsAndEvents();
		}
		public void clearTrips(){
			if (trips != null) {
				
				trips.clear();
				trips = null;
			}
		}
		private void reloadTripsAndEvents(){
			
				clearTrips();
				loadTrips();
		}
		private void loadTripsAndEvents(){
			
			if(trips == null){
				
				loadTrips();
			}
		}
		
		private void loadTrips(){
			//Load all the violations for the time interval and then calculate which belong to which trips
	       List<Event> violations = loadViolations();
	       List<Event> idles = loadIdles();
	       List<Event> tampers = loadTampers();
	       
		   List<Trip> tripsList = driverDAO.getTrips(driverID, teamCommonBean.getTimeFrame().getInterval());
	       trips = new ArrayList<TeamTrip>();
	
	       for (Trip trip : tripsList) {
	    	   
	    	   TeamTrip td = new TeamTrip(trip,
			   						getTripViolations(violations,trip.getStartTime(),trip.getEndTime()),
			   						getTripIdles(idles,trip.getStartTime(),trip.getEndTime()),
			   						getTripTampers(tampers,trip.getStartTime(),trip.getEndTime()),
			   						1);
	    	   trips.add(td);
	 
	        }
		}
		private List<LatLng> getTripViolations(List<Event> violations,Date startTime, Date endTime){
			
			List<LatLng> tripViolations = new ArrayList<LatLng>();
			
			for (Event event:violations){
				
				if ((event.getTime().after(startTime) && event.getTime().before(endTime))||
						event.getTime().equals(startTime) || event.getTime().equals(endTime)){

					tripViolations.add(event.getLatLng());
				}
			}
			return tripViolations;
		}
		private List<LatLng> getTripIdles(List<Event> idles, Date startTime, Date endTime){
			
			List<LatLng> tripIdles = new ArrayList<LatLng>();
			
			for (Event event:idles){
				
				if ((event.getTime().after(startTime) && event.getTime().before(endTime))||
						event.getTime().equals(startTime) || event.getTime().equals(endTime)){

					tripIdles.add(event.getLatLng());
				}
			}
			return tripIdles;
		}
		private List<LatLng> getTripTampers(List<Event> tampers, Date startTime, Date endTime){
			
			List<LatLng> tripTampers = new ArrayList<LatLng>();
			
			for (Event event:tampers){
				
				if ((event.getTime().after(startTime) && event.getTime().before(endTime))||
						event.getTime().equals(startTime) || event.getTime().equals(endTime)){

					tripTampers.add(event.getLatLng());
				}
			}
			return tripTampers;
		}
		private List<Event> loadViolations( ) {
	        	
            List<Integer> violationEventTypeList = new ArrayList<Integer>();
            violationEventTypeList.add(EventMapper.TIWIPRO_EVENT_SPEEDING_EX3);
            violationEventTypeList.add(EventMapper.TIWIPRO_EVENT_SEATBELT);
            violationEventTypeList.add(EventMapper.TIWIPRO_EVENT_NOTEEVENT);

            return eventDAO.getEventsForDriver(driverID, teamCommonBean.getStartTime().toDate(), teamCommonBean.getEndTime().toDate(), violationEventTypeList, showExcludedEvents);
		}
		private List<Event> loadIdles( ) {

            List<Integer> idleTypes = new ArrayList<Integer>();
            idleTypes.add(EventMapper.TIWIPRO_EVENT_IDLE);
            
            return eventDAO.getEventsForDriver(driverID, teamCommonBean.getStartTime().toDate(), teamCommonBean.getEndTime().toDate(), idleTypes, showExcludedEvents);

		}
		private List<Event> loadTampers( ) {
			
            List<Integer> tamperEventTypeList = new ArrayList<Integer>();
            tamperEventTypeList.add(EventMapper.TIWIPRO_EVENT_UNPLUGGED);
            tamperEventTypeList.add(EventMapper.TIWIPRO_EVENT_UNPLUGGED_ASLEEP);

            return eventDAO.getEventsForDriver(driverID, teamCommonBean.getStartTime().toDate(), teamCommonBean.getEndTime().toDate(), tamperEventTypeList, showExcludedEvents);
            
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

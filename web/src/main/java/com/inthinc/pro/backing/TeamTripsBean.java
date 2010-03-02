package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.map.MapIcon;
import com.inthinc.pro.map.MapIconFactory;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.Trip;

public class TeamTripsBean extends BaseBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<DriverTripWrapper> driversTrips;
    private DriverDAO driverDAO;
    private EventDAO eventDAO;
    private List<MapIcon> icons;
    private TeamCommonBean teamCommonBean;
	
    public void init(){
    	
		icons = MapIconFactory.IconType.TEAM_LEGEND.getIconList(15);
		initDrivers();
    }
    private void initDrivers(){
    	
		List<Driver> driversList = driverDAO.getDrivers(teamCommonBean.getGroupID());
		driversTrips = new ArrayList<DriverTripWrapper>();
		for (Driver driver:driversList){
			
			DriverTripWrapper dw = new DriverTripWrapper();
			dw.setDriverID(driver.getDriverID());
			dw.setFullName(driver.getPerson().getFullName());
			dw.setSelected(false);
			driversTrips.add(dw);
		}
    }
    public void reset(){
    	
    	for(DriverTripWrapper dw:driversTrips){
    		
    		dw.setSelected(false);
 //   		dw.clearTrips();
    	}
    }
 	public List<DriverTripWrapper> getDrivers() {
		
		return driversTrips;
	}
	public void setDrivers(List<DriverTripWrapper> drivers) {
		this.driversTrips = drivers;
	}
	public void clearTrips(){
		
    	for(DriverTripWrapper dw:driversTrips){
    		
    		dw.clearTrips();
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

	public class DriverTripWrapper {
		
		private Integer driverID;
		private String fullName;
		private List<TeamTrip> trips;
	    private List<Event> violationEvents = new ArrayList<Event>();
	    private List<Event> idleEvents = new ArrayList<Event>();
	    private List<Event> tamperEvents = new ArrayList<Event>();

		boolean selected;
		
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
		public void clearTrips(){
			if (trips != null) {
				
				trips.clear();
				violationEvents.clear();
				idleEvents.clear();
				tamperEvents.clear();
			}
		}
		private void loadTripsAndEvents(){
			
			if(trips == null){
				
				loadTrips();
		        loadViolations();
			}
		}
		public List<Event> getViolationEvents() {
			return violationEvents;
		}
		public void setViolationEvents(List<Event> violationEvents) {
			this.violationEvents = violationEvents;
		}
		public List<Event> getIdleEvents() {
			return idleEvents;
		}
		public void setIdleEvents(List<Event> idleEvents) {
			this.idleEvents = idleEvents;
		}
		public List<Event> getTamperEvents() {
			return tamperEvents;
		}
		public void setTamperEvents(List<Event> tamperEvents) {
			this.tamperEvents = tamperEvents;
		}
		
		private void loadTrips(){
			
		   List<Trip> tripsList = driverDAO.getTrips(driverID, teamCommonBean.getStartTime().toDate(), teamCommonBean.getEndTime().toDate());
	       trips = new ArrayList<TeamTrip>();

	       for (Trip trip : tripsList) {
	        	
	    	   TeamTrip td = new TeamTrip(trip, addressLookup);
	    	   trips.add(td);
	 
	        }
	        Collections.sort(trips);
	        Collections.reverse(trips);
			
		}
		private void loadViolations( ) {
	        if (violationEvents.isEmpty()) {
	        	
	            List<Integer> violationEventTypeList = new ArrayList<Integer>();
	            violationEventTypeList.add(EventMapper.TIWIPRO_EVENT_SPEEDING_EX3);
	            violationEventTypeList.add(EventMapper.TIWIPRO_EVENT_SEATBELT);
	            violationEventTypeList.add(EventMapper.TIWIPRO_EVENT_NOTEEVENT);
	            List<Integer> idleTypes = new ArrayList<Integer>();
	            idleTypes.add(EventMapper.TIWIPRO_EVENT_IDLE);
	            List<Integer> tamperEventTypeList = new ArrayList<Integer>();
	            tamperEventTypeList.add(EventMapper.TIWIPRO_EVENT_UNPLUGGED);
	            tamperEventTypeList.add(EventMapper.TIWIPRO_EVENT_UNPLUGGED_ASLEEP);

                violationEvents = eventDAO.getEventsForDriver(driverID, teamCommonBean.getStartTime().toDate(), teamCommonBean.getEndTime().toDate(), violationEventTypeList, showExcludedEvents);
                idleEvents = eventDAO.getEventsForDriver(driverID, teamCommonBean.getStartTime().toDate(), teamCommonBean.getEndTime().toDate(), idleTypes, showExcludedEvents);
                tamperEvents = eventDAO.getEventsForDriver(driverID, teamCommonBean.getStartTime().toDate(), teamCommonBean.getEndTime().toDate(), tamperEventTypeList, showExcludedEvents);
	            
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

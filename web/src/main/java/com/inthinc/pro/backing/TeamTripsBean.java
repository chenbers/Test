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
	
	private List<DriverWrapper> drivers;
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
		drivers = new ArrayList<DriverWrapper>();
		for (Driver d:driversList){
			
			DriverWrapper dw = new DriverWrapper();
			dw.setDriver(d);
			dw.setSelected(false);
			drivers.add(dw);
		}
    }
    public void reset(){
    	
    	for(DriverWrapper dw:drivers){
    		
    		dw.setSelected(false);
    		dw.clearTrips();
    	}
    }
 	public List<DriverWrapper> getDrivers() {
		
		return drivers;
	}
	public void setDrivers(List<DriverWrapper> drivers) {
		this.drivers = drivers;
	}
	public void clearTrips(){
		
    	for(DriverWrapper dw:drivers){
    		
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

	public class DriverWrapper {
		
		private Driver driver;
		private List<TeamTrip> trips;
	    private List<Event> violationEvents = new ArrayList<Event>();
	    private List<Event> idleEvents = new ArrayList<Event>();
	    private List<Event> allEvents = new ArrayList<Event>();
	    private List<Event> tamperEvents = new ArrayList<Event>();

		boolean selected;
		
		public Driver getDriver() {
			return driver;
		}
		public void setDriver(Driver driver) {
			this.driver = driver;
			driver.getPerson().setTimeZone(null);
		}
		public boolean isSelected() {
			return selected;
		}
		public void setSelected(boolean selected) {
			this.selected = selected;
			if (selected) {
				loadTrips();
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
			if (trips != null) trips.clear();
		}
		public void loadTrips(){
			
			if((trips == null)||trips.isEmpty()){
				
			   List<Trip> tripsList = driverDAO.getTrips(driver.getDriverID(), teamCommonBean.getStartTime(), teamCommonBean.getEndTime());
		       trips = new ArrayList<TeamTrip>();

		       for (Trip trip : tripsList) {
		        	
		    	   TeamTrip td = new TeamTrip(trip, addressLookup);
		    	   trips.add(td);
		 
		        }
		        Collections.sort(trips);
		        Collections.reverse(trips);
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
		public List<Event> getAllEvents() {
			return allEvents;
		}
		public void setAllEvents(List<Event> allEvents) {
			this.allEvents = allEvents;
		}
		public List<Event> getTamperEvents() {
			return tamperEvents;
		}
		public void setTamperEvents(List<Event> tamperEvents) {
			this.tamperEvents = tamperEvents;
		}

	   public void loadViolations( ) {
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

                violationEvents = eventDAO.getEventsForDriver(driver.getDriverID(), teamCommonBean.getStartTime(), teamCommonBean.getEndTime(), violationEventTypeList, showExcludedEvents);
                idleEvents = eventDAO.getEventsForDriver(driver.getDriverID(), teamCommonBean.getStartTime(), teamCommonBean.getEndTime(), idleTypes, showExcludedEvents);
                tamperEvents = eventDAO.getEventsForDriver(driver.getDriverID(), teamCommonBean.getStartTime(), teamCommonBean.getEndTime(), tamperEventTypeList, showExcludedEvents);
	            
	        }
	    }
	}

}

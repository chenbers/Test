package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.backing.ui.TripDisplay;
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
	
	private List<Driver> drivers;
	private Map<Integer,List<TripDisplay>> selectedDriversTrips;
	private List<Driver> selectedDrivers;
    private DriverDAO driverDAO;
    private EventDAO eventDAO;
    private List<MapIcon> icons;
    private TeamCommonBean teamCommonBean;
    private List<Event> violationEvents = new ArrayList<Event>();
    private List<Event> idleEvents = new ArrayList<Event>();
    private List<Event> allEvents = new ArrayList<Event>();
    private List<Event> tamperEvents = new ArrayList<Event>();
	
    public void init(){
    	
		icons = MapIconFactory.IconType.TEAM_LEGEND.getIconList(15);
		drivers = driverDAO.getDrivers(teamCommonBean.getGroupID());
		selectedDrivers = new ArrayList<Driver>();
		selectedDriversTrips = new LinkedHashMap<Integer,List<TripDisplay>>();
    }
	public List<Driver> getDrivers() {
		
		return drivers;
	}
	public void setDrivers(List<Driver> drivers) {
		this.drivers = drivers;
	}
	public void clearTrips(){
		
		selectedDriversTrips.clear();
		selectedDrivers.clear();
	}
	public void unloadTrips(Driver driver){
		
		selectedDriversTrips.remove(driver.getDriverID());
	}
	public void loadTrips(Driver driver){
		
		if(selectedDriversTrips.get(driver.getDriverID()) == null){
			
		   List<Trip> trips = driverDAO.getTrips(driver.getDriverID(), teamCommonBean.getStartTime(), teamCommonBean.getEndTime());
	       List<TripDisplay> selectedDriverTrips = new ArrayList<TripDisplay>();
	       for (Trip trip : trips) {
	        	
	            TripDisplay td = new TripDisplay(trip, driver.getPerson().getTimeZone(), addressLookup);
	            // If starting or ending address is null, try to set a zone name
	//            if ( td.getStartAddress() == null ) {
	//                LatLng latLng = new LatLng(td.getRoute().get(0).getLat(),td.getRoute().get(0).getLng());
	//                td.setStartAddress(MiscUtil.findZoneName(getProUser().getZones(), latLng));
	//            }                
	//            if ( td.getEndAddress() == null ) {
	//                LatLng latLng = new LatLng(td.getEndPointLat(),td.getEndPointLng());
	//                td.setEndAddress(MiscUtil.findZoneName(getProUser().getZones(), latLng));
	//            }
	            selectedDriverTrips.add(td);
	 
	        }
	        Collections.sort(selectedDriverTrips);
	        Collections.reverse(selectedDriverTrips);
	        loadViolations(driver.getDriverID() );
	        
	        selectedDriversTrips.put(driver.getDriverID(),selectedDriverTrips);
		}
	}
	public void loadTrips(){
		
		selectedDriversTrips.clear();
        allEvents.clear();

       for(Driver selectedDriver:selectedDrivers){
    	   
	       loadTrips(selectedDriver) ;
       }
	}
	   public void loadViolations( Integer driverID) {
	        if (violationEvents.isEmpty()) {
	        	
	            //Add 1 second to end time to get events, eg tampering events that occur at the end of a trip 
	            // - method uses < end time, not <= end time.  This isn't true anymore!

	            List<Integer> violationEventTypeList = new ArrayList<Integer>();
	            violationEventTypeList.add(EventMapper.TIWIPRO_EVENT_SPEEDING_EX3);
	            violationEventTypeList.add(EventMapper.TIWIPRO_EVENT_SEATBELT);
	            violationEventTypeList.add(EventMapper.TIWIPRO_EVENT_NOTEEVENT);
	            List<Integer> idleTypes = new ArrayList<Integer>();
	            idleTypes.add(EventMapper.TIWIPRO_EVENT_IDLE);
	            List<Integer> tamperEventTypeList = new ArrayList<Integer>();
	            tamperEventTypeList.add(EventMapper.TIWIPRO_EVENT_UNPLUGGED);
	            tamperEventTypeList.add(EventMapper.TIWIPRO_EVENT_UNPLUGGED_ASLEEP);

	                violationEvents = eventDAO.getEventsForDriver(driverID, teamCommonBean.getStartTime(), teamCommonBean.getEndTime(), violationEventTypeList, showExcludedEvents);
	                idleEvents = eventDAO.getEventsForDriver(driverID, teamCommonBean.getStartTime(), teamCommonBean.getEndTime(), idleTypes, showExcludedEvents);
	                tamperEvents = eventDAO.getEventsForDriver(driverID, teamCommonBean.getStartTime(), teamCommonBean.getEndTime(), tamperEventTypeList, showExcludedEvents);

//	                // Lookup Addresses for events
//	            populateAddresses(violationEvents);
//	            populateAddresses(idleEvents);
//	            populateAddresses(tamperEvents);
//	            
//	            // Get the correct timestamp for display (vehicle page only)
//	            populateFormattedDate(violationEvents);
//	            populateFormattedDate(idleEvents);
//	            populateFormattedDate(tamperEvents);
	            
	            allEvents.addAll(violationEvents);
	            allEvents.addAll(idleEvents);
	            allEvents.addAll(tamperEvents);
	            Collections.sort(allEvents);
	            Collections.reverse(allEvents);
//	            allEventsMap = new LinkedHashMap<Long,Event>();
//	            for(Event event:allEvents){
//	            	allEventsMap.put(event.getNoteID(),event);
//	            }
//	            selectedViolationID = allEvents.size()>0?allEvents.get(0).getNoteID():null;
	        }
	    }

	public List<List<TripDisplay>> getSelectedDriversTrips() {

		return new ArrayList<List<TripDisplay>>(selectedDriversTrips.values());
	}
//	public void setSelectedDriverTrips(List<List<TripDisplay>> selectedDriversTrips) {
//		this.selectedDriversTrips = selectedDriversTrips;
//	}
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
	public List<Driver> getSelectedDrivers() {
		return selectedDrivers;
	}
	public void setSelectedDrivers(List<Driver> selectedDriver) {
		this.selectedDrivers = selectedDrivers;
	}
	public EventDAO getEventDAO() {
		return eventDAO;
	}
	public void setEventDAO(EventDAO eventDAO) {
		this.eventDAO = eventDAO;
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
	public void setSelectedDriver(Driver driver){
		
		if (selectedDrivers.remove(driver)){
			
			unloadTrips(driver);
		}
		else{
			
			selectedDrivers.add(driver);
			loadTrips(driver);
		}
	}
}

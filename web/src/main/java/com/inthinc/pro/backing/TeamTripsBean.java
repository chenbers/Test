package com.inthinc.pro.backing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ajax4jsf.model.KeepAlive;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.EventType;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.TripStatus;
import com.inthinc.pro.util.MessageUtil;

@KeepAlive
public class TeamTripsBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<String> colors;
	private List<String> labels;
	
    private DriverDAO driverDAO;
    private EventDAO eventDAO;
    
    private TeamCommonBean teamCommonBean;

//	private List<DriverTrips> driversTrips;
	private Map<Integer, DriverTrips> driversTripsMap;
	
    private EventData eventData;
    
	
    public void init(){
    	
		initDrivers();
		colors = Arrays.asList(	"#C7BBBF","#F2CBD1","#DE9ED4","#B0C0F5","#BCA6BF","#F28392","#A5B0D6","#C6F5DF","#F5D0EF","#C6E9F5",
								"#AACC66","#EFDAF2","#C0BBED","#D4BBED","#BFF5F1","#86DBD6","#78D6F5","#80F2BD","#D7F7CB","#BAE8A5",
	              				"#45BACC","#CCB345","#CCCA45","#E8C687","#F5B869","#E89289");
		labels = Arrays.asList("A","B", "C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z");
		eventData = new EventData();
    }
    private void initDrivers(){
    	
		List<Driver> driversList = driverDAO.getDrivers(teamCommonBean.getGroupID());
		Collections.sort(driversList);
		driversTripsMap = new LinkedHashMap<Integer,DriverTrips>();
		for (Driver driver:driversList){
			
			DriverTrips dw = new DriverTrips(driver.getDriverID(),driver.getPerson().getFullName());
			driversTripsMap.put(dw.driverID, dw);
		}
    }
    public void reset(){
    	
    	for(DriverTrips dw:driversTripsMap.values()){
    		
    		dw.setDriverSelected(false);
    	}
    }
 	public List<DriverTrips> getDriversTrips() {
 		
		return Collections.list(Collections.enumeration(driversTripsMap.values()));
	}
 	public List<DriverTrips> getSelectedDriversTrips(){
 		
 		List<DriverTrips> selectedDrivers = new ArrayList<DriverTrips>();
 		
 	   	for(DriverTrips dt:driversTripsMap.values()){
 	   		
 	   		if ( dt.isDriverSelected()){
 	   			
 	   			dt.reloadTrips();
 	   			selectedDrivers.add(dt);
 	   		}
 	   	}
 	   	return selectedDrivers;
 	}
//	public void setDrivers(List<DriverTrips> drivers) {
//		this.driversTrips = drivers;
//	}
	public void reloadTrips(){
		
    	for(DriverTrips dt:driversTripsMap.values()){
    		
    		dt.reloadTrips();
    	}
	}
	public void clearTrips(){
		
    	for(DriverTrips dt:driversTripsMap.values()){
    		
    		dt.clearTrips();
    	}		
	}
	public void setupEventData(){
		
		DriverTrips selectedDriverTrips = driversTripsMap.get(eventData.getDriverID());
		eventData.setDriverName(selectedDriverTrips.getDriverName());
		Date eventTime = null;
		String eventDescription = "";
		if (eventData.getEventType().equals("start")){
			
			eventTime = selectedDriverTrips.getTrips().get(eventData.tripNumber).getStartTime();
			eventData.setEventName(MessageUtil.getMessageString("TRIP_START"));
		}
		else if (eventData.getEventType().equals("end")){
			
			eventTime = selectedDriverTrips.getTrips().get(eventData.tripNumber).getEndTime();
			eventData.setEventName(MessageUtil.getMessageString("TRIP_END"));
		}
		else if (eventData.getEventType().equals("progress")){
			
			eventTime = selectedDriverTrips.getTrips().get(eventData.tripNumber).getEndTime();
			eventData.setEventName(MessageUtil.getMessageString("TRIP_PROGRESS"));
		}
		else if(eventData.getEventType().equals("violation")){
			Event event = selectedDriverTrips.getTrips().get(eventData.getTripNumber()).getViolationEvents().get(eventData.getEventIndex());
			eventTime = event.getTime();
			
			eventData.setEventName(MessageUtil.getMessageString(selectedDriverTrips.getTrips().get(eventData.getTripNumber()).getViolationEvents().get(eventData.getEventIndex()).getEventType().toString()));
			if (selectedDriverTrips.getTrips().get(eventData.getTripNumber()).getViolationEvents().get(eventData.getEventIndex()).getEventType().equals(EventType.SPEEDING)){
				
				eventDescription = event.getDetails(MessageUtil.getMessageString("redflags_details" +event.getEventType().name(),LocaleBean.getCurrentLocale()),
													getMeasurementType(),
													MessageUtil.getMessageString(getMeasurementType().toString()+"_mph"));
			}
		}
		else if(eventData.getEventType().equals("idle")){
			
			eventTime = selectedDriverTrips.getTrips().get(eventData.getTripNumber()).getIdleEvents().get(eventData.getEventIndex()).getTime();
			eventData.setEventName(MessageUtil.getMessageString(selectedDriverTrips.getTrips().get(eventData.getTripNumber()).getIdleEvents().get(eventData.getEventIndex()).getEventType().toString()));
			eventDescription = selectedDriverTrips.getTrips().get(eventData.getTripNumber()).getIdleEvents().get(eventData.getEventIndex()).
											getDetails(MessageUtil.getMessageString("redflags_detailsIDLING"), null,null);
			
		}
		else if(eventData.getEventType().equals("tamper")){
			
			eventTime = selectedDriverTrips.getTrips().get(eventData.getTripNumber()).getTamperEvents().get(eventData.getEventIndex()).getTime();
			eventData.setEventName(MessageUtil.getMessageString(selectedDriverTrips.getTrips().get(eventData.getTripNumber()).getTamperEvents().get(eventData.getEventIndex()).getEventType().toString()));

		}
		
        SimpleDateFormat sdf = new SimpleDateFormat(MessageUtil.getMessageString("dateTimeFormat"), getLocale());
        sdf.setTimeZone(this.getPerson().getTimeZone());
        eventData.setTimeString(sdf.format(eventTime));
        eventData.setEventDescription(eventDescription);
	}
	public DriverDAO getDriverDAO() {
		return driverDAO;
	}
	public void setDriverDAO(DriverDAO driverDAO) {
		this.driverDAO = driverDAO;
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
	public List<String> getColors() {
		return colors;
	}
	public void setColors(List<String> colors) {
		this.colors = colors;
	}
	public List<String> getLabels() {
		return labels;
	}
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
	public EventData getEventData() {
		return eventData;
	}
	public void setEventData(EventData eventData) {
		this.eventData = eventData;
	}
	
/*
 * 
 * Driver Trips inner class
 * 
 */
	public class DriverTrips {
		
		private Integer driverID;
		private String driverName;
		private List<TeamTrip> trips;

		boolean driverSelected;
		
		public DriverTrips(Integer driverID, String driverName) {
			super();
			this.driverID = driverID;
			this.driverName = driverName;
			this.driverSelected = false;
		}
		public boolean isDriverSelected() {
			return driverSelected;
		}
		public void setDriverSelected(boolean driverSelected) {
			
			this.driverSelected = driverSelected;
			
		}
		public void selectivelyLoadTrips(){
			
			if (driverSelected && (trips == null)) {
				
				loadTrips();
			}
		}
		public List<TeamTrip> getTrips() {
			
			return trips;
		}
		public void setTrips(List<TeamTrip> trips) {
			this.trips = trips;
		}
		public void reloadTrips(){
			
			if (driverSelected) {
				
				clearTrips();
				loadTrips();
			}
		}
		public void clearTrips(){
			if (trips != null) {
				
				trips.clear();
				trips = null;
			}
		}
		
		private void loadTrips(){
			//Load all the violations for the time interval and then calculate which belong to which trips
	       List<Event> violations = loadViolations();
	       List<Event> idles = loadIdles();
	       List<Event> tampers = loadTampers();
	       
		   List<Trip> tripsList = driverDAO.getTrips(driverID, teamCommonBean.getTimeFrame().getInterval(getDateTimeZone()));
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
		private List<Event> getTripViolations(List<Event> violations,Date startTime, Date endTime){
			
			List<Event> tripViolations = new ArrayList<Event>();
			
			for (Event event:violations){
				
				if ((event.getTime().after(startTime) && event.getTime().before(endTime))||
						event.getTime().equals(startTime) || event.getTime().equals(endTime)){

					tripViolations.add(event);
				}
			}
			return tripViolations;
		}
		private List<Event> getTripIdles(List<Event> idles, Date startTime, Date endTime){
			
			List<Event> tripIdles = new ArrayList<Event>();
			
			for (Event event:idles){
				
				if ((event.getTime().after(startTime) && event.getTime().before(endTime))||
						event.getTime().equals(startTime) || event.getTime().equals(endTime)){

					tripIdles.add(event);
				}
			}
			return tripIdles;
		}
		private List<Event> getTripTampers(List<Event> tampers, Date startTime, Date endTime){
			
			List<Event> tripTampers = new ArrayList<Event>();
			
			for (Event event:tampers){
				
				if ((event.getTime().after(startTime) && event.getTime().before(endTime))||
						event.getTime().equals(startTime) || event.getTime().equals(endTime)){

					tripTampers.add(event);
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
		public String getDriverName() {
			return driverName;
		}
		public void setDriverName(String driverName) {
			this.driverName = driverName;
		}
	}
	/*
	 * 
	 * TeamTrip inner class
	 * 
	 */
		public class TeamTrip {
			private List<LatLng> route;
		    private LatLng routeLastStep;
		    private LatLng beginningPoint;
		    private boolean inProgress;
		    private Date startTime;
		    private Date endTime;
		    private List<LatLng> violations;
		    private List<LatLng> idles;
		    private List<LatLng> tampers;
		    private List<Event> violationEvents;
		    private List<Event> idleEvents;
		    private List<Event> tamperEvents;
		    
		    public TeamTrip(Trip trip, 
		    			    List<Event> violationEvents,
		    			    List<Event> idleEvents,
		    			    List<Event> tamperEvents,
		    				int compressionFactor)
		    {
		        startTime = trip.getStartTime();
		        endTime = trip.getEndTime();
		        
		        route = compressRoute(trip.getRoute(), compressionFactor);
		        inProgress = trip.getStatus().equals(TripStatus.TRIP_IN_PROGRESS);        
		        if(route.size() > 0)
		        {
		            routeLastStep = route.get(route.size()-1);
		            routeLastStep.setLat(routeLastStep.getLat() + 0.00001);
		            
		            beginningPoint = route.get(0);
		            beginningPoint.setLat(beginningPoint.getLat() - 0.00001);
		        }
		        this.violationEvents = violationEvents;
		        violations =  getTripViolations(violationEvents);
		        this.idleEvents = idleEvents;
		        idles = getTripIdles(idleEvents);
		        this.tamperEvents = tamperEvents;
		        tampers = getTripTampers(tamperEvents);
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
			private List<LatLng> getTripViolations(List<Event> violations){
				
				List<LatLng> tripViolations = new ArrayList<LatLng>();
				
				for (Event event:violations){
					
					tripViolations.add(event.getLatLng());
				}
				return tripViolations;
			}
			private List<LatLng> getTripIdles(List<Event> idles){
				
				List<LatLng> tripIdles = new ArrayList<LatLng>();
				
				for (Event event:idles){
					
					tripIdles.add(event.getLatLng());
				}
				return tripIdles;
			}
			private List<LatLng> getTripTampers(List<Event> tampers){
				
				List<LatLng> tripTampers = new ArrayList<LatLng>();
				
				for (Event event:tampers){
					
					tripTampers.add(event.getLatLng());
				}
				return tripTampers;
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
			public Date getStartTime() {
				return startTime;
			}
			public void setStartTime(Date startTime) {
				this.startTime = startTime;
			}
			public Date getEndTime() {
				return endTime;
			}
			public void setEndTime(Date endTime) {
				this.endTime = endTime;
			}
		}

	/**
	 * 
	 * Event data inner class
	 * 
	 */
	public class EventData{
		
		private Integer driverID;
		private String  eventType;
		private Integer eventIndex;
		private Integer tripNumber;

		private String driverName;
		private String timeString;
		private String eventName;
		private String eventDescription;
		
		public EventData() {

			eventName = "";
			eventDescription = "";
		}
		public Integer getDriverID() {
			return driverID;
		}
		public void setDriverID(Integer driverID) {
			this.driverID = driverID;
		}
		public String getDriverName() {
			return driverName;
		}
		public void setDriverName(String driverName) {
			this.driverName = driverName;
		}
		public Integer getEventIndex() {
			return eventIndex;
		}
		public void setEventIndex(Integer eventIndex) {
			this.eventIndex = eventIndex;
		}
		public String getEventType() {
			return eventType;
		}
		public void setEventType(String eventType) {
			this.eventType = eventType;
		}
		public Integer getTripNumber() {
			return tripNumber;
		}
		public void setTripNumber(Integer tripNumber) {
			this.tripNumber = tripNumber;
		}
		public String getTimeString() {
			return timeString;
		}
		public void setTimeString(String timeString) {
			this.timeString = timeString;
		}
		public String getEventName() {
			return eventName;
		}
		public void setEventName(String eventName) {
			this.eventName = eventName;
		}
		public String getEventDescription() {
			return eventDescription;
		}
		public void setEventDescription(String eventDescription) {
			this.eventDescription = eventDescription;
		}
	}
}

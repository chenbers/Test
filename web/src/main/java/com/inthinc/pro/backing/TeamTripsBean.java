package com.inthinc.pro.backing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.ajax4jsf.model.KeepAlive;
import org.richfaces.json.JSONArray;
import org.richfaces.json.JSONException;
import org.richfaces.json.JSONObject;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.TripStatus;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventSubCategory;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.util.MessageUtil;

@KeepAlive
public class TeamTripsBean extends BaseBean {

	/**
	 * Backing bean for the TeamTrips tab of the new team page
	 */
	private static final long serialVersionUID = 1L;
	private final int driversPerPage = 25;
	
	private List<String> colors;
	private List<String> textColors;
	private List<String> labels;
	
    private DriverDAO driverDAO;
    private EventDAO eventDAO;
    
    private TeamCommonBean teamCommonBean;

	private Map<Integer, DriverTrips> driversTripsMap;
	private Integer driversPage;
	
	private Map<String,Event> eventsMap;
    private EventData eventData;
    private List<EventData> clusterBubbleEvents;
	
    private List<String> eventIDs;
    private LatLng clusterLatLng;
    
    private List<DriverTrips> selectedDrivers;
     
    private JSONObject jsonEventData;
    private static final EnumSet<TimeFrame> validTimeFrames = EnumSet.range(TimeFrame.TODAY,TimeFrame.WEEK);
    
    public void init(){
    	
		initDrivers();

		labels = new ArrayList<String>(Arrays.asList( Pattern.compile("\",\"|\"").split(MessageUtil.getMessageString("teamLabels"))));
		labels.remove(0);

		eventsMap = new HashMap<String,Event>();
		eventData = new EventData();
		clusterBubbleEvents = new ArrayList<EventData>();
		eventIDs = new ArrayList<String>();
		driversPage = 1;
    }
    private void initDrivers(){
    	
		List<Driver> driversList = driverDAO.getDrivers(teamCommonBean.getGroupID());
		Collections.sort(driversList);
		driversTripsMap = new LinkedHashMap<Integer,DriverTrips>();
		int index = 0;
		for (Driver driver:driversList){
			
			DriverTrips dw = new DriverTrips(driver.getDriverID(),driver.getPerson().getFullName(), index);
			driversTripsMap.put(dw.driverID, dw);
			index++;
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
 	
 	public boolean isValidTimeFrame(){
 		
 		return validTimeFrames.contains(teamCommonBean.getTimeFrame());
		
 	}
 	public void loadSelectedDriversTrips(){
 	
 		if (isValidTimeFrame()){
 			
	 		selectedDrivers = new ArrayList<DriverTrips>();
	 		DriverTrips driversTripsList[] = driversTripsMap.values().toArray(new DriverTrips[0]);
	 		
	 		for (int i=(driversPage-1)*driversPerPage; i< (driversTripsMap.size()<driversPage*driversPerPage?driversTripsMap.size():driversPage*driversPerPage); i++){
	 	   		
	 			DriverTrips dt = driversTripsList[i];
	 	   		if ( dt.isDriverSelected()){
	 	   			
	 	   			dt.reloadTrips();
	 	   			selectedDrivers.add(dt);
	 	   		}
	 	   	}
 		}
 		else{
 			
			addInfoMessage("Please choose a valid time frame for the Team Trips tab");
 		}

 	}
 	public List<DriverTrips> getSelectedDriversTrips(){
 		
 		if (isValidTimeFrame()){
 			
	 	   	return selectedDrivers;
 		}
 		else {
 			
 			return null;
 		}
 	}

	public void clearTrips(){
		
    	for(DriverTrips dt:driversTripsMap.values()){
    		
    		dt.clearTrips();
    	}		
	}
	public void createEventBubbleData(){
		
		//Assumes eventID has been set
		//get event from map
		
		Event event = eventsMap.get(eventData.eventID);
		if(event != null){
			
			eventData.createEventBubbleData(event);
		}
	}
	public EventData setUpClusterBubbleData(String eventID){
		
		//Assumes eventID has been set
		//get event from map

		Event event = eventsMap.get(eventID);
		if(event != null){
			
			EventData eventData = new EventData(eventID);
			eventData.setEventID(eventID);
			eventData.createEventBubbleData(event);
			
			return eventData;
		}
		return null;
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
	
	public JSONArray getColorsJSON(){
		
		return new JSONArray(colors);
	}
	public List<String> getTextColors() {
		return textColors;
	}
	public void setTextColors(List<String> textColors) {
		this.textColors = textColors;
	}
	public JSONArray getTextColorsJSON(){
		
		return new JSONArray(textColors);
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
	public List<EventData> getClusterBubbleEvents() {
		return clusterBubbleEvents;
	}
	public void setClusterBubbleEvents(List<EventData> clusterBubbleEvents) {
		this.clusterBubbleEvents = clusterBubbleEvents;
	}

	public void setClusterEventID(String eventID){
		
		EventData eventData = new EventData();
		eventData.setEventID(eventID);
	}
	public List<String> getEventIDList() {
		return eventIDs;
	}
	public String getClusterEventData(){
		
		return jsonEventData.toString();
	}
	public void setClusterEventData(String eventData) {
		
		try {
			
			jsonEventData = new JSONObject(eventData);
		}
		catch (JSONException JSONe){
			
			jsonEventData = null;
		}
		
	}
	public LatLng getClusterLatLng(){
		
		return clusterLatLng;		
	}
	public void createClusterBubbleData(){
		
		if (jsonEventData == null) return;
		
		try {
			
			clusterLatLng = new LatLng( (Double) jsonEventData.get("lat"),
										(Double) jsonEventData.get("lng"));
			JSONArray eventIDs = jsonEventData.getJSONArray("events");
			
			clusterBubbleEvents = new ArrayList<EventData>();
			for(int i = 0; i<eventIDs.length(); i++){
				
				String eventID = eventIDs.getString(i);

				EventData eventData = setUpClusterBubbleData(eventID);
				if(eventData != null){
					
					clusterBubbleEvents.add(eventData);
				}
			}
		}
		catch (JSONException JSONe){
			
		}
	}
	public Integer getDriversPage() {
		return driversPage;
	}
	public void setDriversPage(Integer driversPage) {
		this.driversPage = driversPage;
	}
	public int getDriversPerPage() {
		return driversPerPage;
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

		private boolean driverSelected;
		private Integer driverIndex; //so you know which row it is
		
		public DriverTrips(Integer driverID, String driverName, Integer index) {
			super();
			this.driverID = driverID;
			this.driverName = driverName;
			this.driverSelected = false;
			this.driverIndex = index;
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
	       
		   List<Trip> tripsList = driverDAO.getTrips(driverID, teamCommonBean.getTimeFrame().getInterval(getDateTimeZone()), true);
           Collections.sort(tripsList, new Comparator<Trip>() {
               @Override
               public int compare(Trip t1, Trip t2) {
                   return (int)(t1.getStartTime().getTime() - t2.getStartTime().getTime());
               }
           });

	       trips = new ArrayList<TeamTrip>();
	
	       for (Trip trip : tripsList) {
	    	   
	           if (trip.hasGoodRoute()){
    	    	   TeamTrip td = new TeamTrip(trip,
    			   						getTripViolations(violations,trip.getStartTime(),trip.getEndTime()),
    			   						getTripIdles(idles,trip.getStartTime(),trip.getEndTime()),
    			   						getTripTampers(tampers,trip.getStartTime(),trip.getEndTime()),
    			   						1);
    	    	   trips.add(td);
	           }
	    	   
	        }
	       //add events to the event map
	       addEventsToMap(violations);
	       addEventsToMap(idles);
	       addEventsToMap(tampers);
		}
		private void addEventsToMap(List<Event>events){
			
			for(Event event: events){
				
				eventsMap.put(event.getNoteID().toString(), event);
			}
		}
		private boolean eventInInterval(Date eventTime, Date startTime, Date endTime){
			
			return (eventTime.after(startTime) && eventTime.before(endTime))||
			eventTime.equals(startTime) || eventTime.equals(endTime);
		}
		private List<Event> getTripViolations(List<Event> violations,Date startTime, Date endTime){
			
			List<Event> tripViolations = new ArrayList<Event>();
			
			for (Event event:violations){
				
				if (eventInInterval(event.getTime(), startTime, endTime)){

					tripViolations.add(event);
				}
			}
			return tripViolations;
		}
		private List<Event> getTripIdles(List<Event> idles, Date startTime, Date endTime){
			
			List<Event> tripIdles = new ArrayList<Event>();
			
			for (Event event:idles){
				
				if (eventInInterval(event.getTime(), startTime, endTime)){

					tripIdles.add(event);
				}
			}
			return tripIdles;
		}
		private List<Event> getTripTampers(List<Event> tampers, Date startTime, Date endTime){
			
			List<Event> tripTampers = new ArrayList<Event>();
			
			for (Event event:tampers){
				
				if (eventInInterval(event.getTime(), startTime, endTime)){

					tripTampers.add(event);
				}
			}
			return tripTampers;
		}
		private List<Event> loadViolations( ) {
	        	
            List<NoteType> violationEventTypeList = new ArrayList<NoteType>();
            violationEventTypeList.addAll(EventSubCategory.SPEED.getNoteTypesInSubCategory());
            violationEventTypeList.add(NoteType.SEATBELT);
            violationEventTypeList.addAll(EventSubCategory.DRIVING_STYLE.getNoteTypesInSubCategory());
            return eventDAO.getEventsForDriver(driverID, 
            		teamCommonBean.getTimeFrame().getInterval(getDateTimeZone()).getStart().toDateTime().toDate(), 
            		teamCommonBean.getTimeFrame().getInterval(getDateTimeZone()).getEnd().toDateTime().toDate(), 
            		violationEventTypeList, getShowExcludedEvents());
		}
		private List<Event> loadIdles( ) {

            List<NoteType> idleTypes = new ArrayList<NoteType>();
            idleTypes.add(NoteType.IDLE);
            
            return eventDAO.getEventsForDriver(driverID,
            		teamCommonBean.getTimeFrame().getInterval(getDateTimeZone()).getStart().toDateTime().toDate(), 
            		teamCommonBean.getTimeFrame().getInterval(getDateTimeZone()).getEnd().toDateTime().toDate(), 
            		idleTypes, getShowExcludedEvents());

		}
		private List<Event> loadTampers( ) {
			
            List<NoteType> tamperEventTypeList = new ArrayList<NoteType>();
            tamperEventTypeList.add(NoteType.UNPLUGGED);
            tamperEventTypeList.add(NoteType.UNPLUGGED_ASLEEP);

            return eventDAO.getEventsForDriver(driverID,
            		teamCommonBean.getTimeFrame().getInterval(getDateTimeZone()).getStart().toDateTime().toDate(), 
            		teamCommonBean.getTimeFrame().getInterval(getDateTimeZone()).getEnd().toDateTime().toDate(), 
            		tamperEventTypeList, getShowExcludedEvents());
            
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
		public Integer getDriverIndex() {
			return driverIndex;
		}
		public void setDriverIndex(Integer driverIndex) {
			this.driverIndex = driverIndex;
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
		    private EventItem startEventItem;
		    private EventItem endEventItem;
		    private List<EventItem> violations;
		    private List<EventItem> idles;
		    private List<EventItem> tampers;
		    
		    public TeamTrip(Trip trip, 
		    			    List<Event> violationEvents,
		    			    List<Event> idleEvents,
		    			    List<Event> tamperEvents,
		    				int compressionFactor)
		    {

		        route = compressRoute(trip, compressionFactor);
		        //Need to fake start and end/progress events for trips
		        createStartEvent(trip);
		    	
		    	createEndEvent(trip);
		        
		        startTime = trip.getStartTime();
		        endTime = trip.getEndTime();
		        
		        inProgress = trip.getStatus().equals(TripStatus.TRIP_IN_PROGRESS);
		        
		        violations =  getTripViolations(violationEvents);
		        idles = getTripIdles(idleEvents);
		        tampers = getTripTampers(tamperEvents);
		    }
		    private List<LatLng> compressRoute(Trip trip, int compressionFactor){
		    	
		    	List<LatLng> compressedRoute = new ArrayList<LatLng>();
		    	if (trip.hasGoodRoute()){
		    	    List<LatLng> tripRoute = trip.getRoute();
    		    	compressedRoute.add(tripRoute.get(0));
    		    	for (int i=compressionFactor; i< tripRoute.size();i+=compressionFactor){
    		    		
    		    		compressedRoute.add(tripRoute.get(i));
    		    	}
    		    	compressedRoute.add(tripRoute.get(tripRoute.size()-1));
		        }
		    	return compressedRoute;
		    }
		    private void createStartEvent(Trip trip){
		    	
		    	Event startEvent = new Event();
		    	startEvent.setDriverID(trip.getDriverID());
		    	startEvent.setTime(trip.getStartTime());
		    	startEvent.setNoteID(trip.getStartTime().getTime());
		    	startEvent.setType(NoteType.TRIP_START);
		    	
		    	startEventItem = new EventItem();
		    	
		        if(trip.hasGoodRoute()){

		            beginningPoint = route.get(0);
		            beginningPoint.setLat(beginningPoint.getLat() - 0.00001);
			    	startEvent.setLatitude(beginningPoint.getLat());
			    	startEvent.setLongitude(beginningPoint.getLng());
			    	startEventItem.latLng = new LatLng(startEvent.getLatitude(), startEvent.getLongitude());
		        }
		    	eventsMap.put(startEvent.getNoteID().toString(), startEvent);
		    	
		    	startEventItem.eventID = startEvent.getNoteID().toString();

		    }
		    private void createEndEvent(Trip trip){
		    	Event endEvent = new Event();
		    	endEvent.setDriverID(trip.getDriverID());
		    	
		        endEvent.setTime(trip.getEndTime());
		        endEvent.setNoteID(trip.getEndTime().getTime());
		        if (trip.getStatus().equals(TripStatus.TRIP_IN_PROGRESS)){
		        	
		        	endEvent.setType(NoteType.TRIP_INPROGRESS);
		        }
		        else {
		        	
		        	endEvent.setType(NoteType.TRIP_END);
		        }

		        endEventItem = new EventItem();

		        if(trip.hasGoodRoute())
		        {
		            routeLastStep = route.get(route.size()-1);
		            routeLastStep.setLat(routeLastStep.getLat() + 0.00001);
		            endEvent.setLatitude(routeLastStep.getLat());
		            endEvent.setLongitude(routeLastStep.getLng());
		            
		            endEventItem.latLng = new LatLng(endEvent.getLatitude(), endEvent.getLongitude());
		        }
		        
		    	endEventItem.eventID = endEvent.getNoteID().toString();

		        eventsMap.put(endEvent.getNoteID().toString(), endEvent);

		    }
			private List<EventItem> getTripViolations(List<Event> violations){
				
				List<EventItem> tripViolations = new ArrayList<EventItem>();
				
				for (Event event:violations){
					
					EventItem eventItem = new EventItem();
					eventItem.setEventID(event.getNoteID().toString());
					eventItem.setLatLng(event.getLatLng());
					tripViolations.add(eventItem);
				}
				return tripViolations;
			}
			private List<EventItem> getTripIdles(List<Event> idles){
				
				List<EventItem> tripIdles = new ArrayList<EventItem>();
				
				for (Event event:idles){
					
					EventItem eventItem = new EventItem();
					eventItem.setEventID(event.getNoteID().toString());
					eventItem.setLatLng(event.getLatLng());
					tripIdles.add(eventItem);
				}
				return tripIdles;
			}
			private List<EventItem> getTripTampers(List<Event> tampers){
				
				List<EventItem> tripTampers = new ArrayList<EventItem>();
				
				for (Event event:tampers){
					
					EventItem eventItem = new EventItem();
					eventItem.setEventID(event.getNoteID().toString());
					eventItem.setLatLng(event.getLatLng());
					tripTampers.add(eventItem);
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
			public List<EventItem> getViolations() {
				return violations;
			}
			public void setViolations(List<EventItem> violations) {
				this.violations = violations;
			}
			public List<EventItem> getIdles() {
				return idles;
			}
			public void setIdles(List<EventItem> idles) {
				this.idles = idles;
			}
			public List<EventItem> getTampers() {
				return tampers;
			}
			public void setTampers(List<EventItem> tampers) {
				this.tampers = tampers;
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
			public EventItem getStartEventItem() {
				return startEventItem;
			}
			public void setStartEventItem(EventItem startEventItem) {
				this.startEventItem = startEventItem;
			}
			public EventItem getEndEventItem() {
				return endEventItem;
			}
			public void setEndEventItem(EventItem endEventItem) {
				this.endEventItem = endEventItem;
			}
		}
/**
 * 
 * Inner class EventItem to pass id and latlng to the client side in JSON
 * 
 * @author jacquiehoward
 *
 */
	public class EventItem{
		
		private String eventID;
		private LatLng latLng;
		
		public String getEventID() {
			return eventID;
		}
		public void setEventID(String eventID) {
			this.eventID = eventID;
		}
		public LatLng getLatLng() {
			return latLng;
		}
		public void setLatLng(LatLng latLng) {
			this.latLng = latLng;
		}
	}
	/**
	 * 
	 * Event data inner class
	 * 
	 */
	public class EventData{
		
		private String eventID;
		
		private Integer driverID;
		private Double lat;
		private Double lng;

		private String driverName;
		private Date eventTime;
		private String timeString;
		private String eventName;
		private String eventDescription;
		
		public EventData(String eventID){
			
			this();
			this.eventID = eventID;
		}
		public EventData() {
			
			driverName = "";
			driverID = 0;
			eventName = "";
			eventDescription = "";
		}
		public void reset(){
			
			driverName = "";
			driverID = 0;
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
		public Double getLat() {
			return lat;
		}
		public void setLat(Double lat) {
			this.lat = lat;
		}
		public Double getLng() {
			return lng;
		}
		public void setLng(Double lng) {
			this.lng = lng;
		}
		public String getEventID() {
			return eventID.toString();
		}
		public void setEventID(String eventID) {
			this.eventID = eventID;
		}
		public void createEventBubbleData(Event event){
			
			//Assumes eventID has been set
			reset();
			
			//set driver's name
			driverID = event.getDriverID();
			setDriverName(driversTripsMap.get(driverID).getDriverName());
			if(event.getType() == NoteType.TRIP_START
			   || event.getType() == NoteType.TRIP_INPROGRESS
			   || event.getType() == NoteType.TRIP_END){
				
				setEventName(MessageUtil.getMessageString(MessageUtil.getMessageString(event.getType().toString())));
			}
			else {
				
			    setEventName(MessageUtil.getMessageString(event.getEventType().toString()));
                setEventDescription(
                        event.getDetails(
                                MessageUtil.getMessageString(
                                        "redflags_details" +event.getEventType().name(),
                                        LocaleBean.getCurrentLocale()),
                                        getMeasurementType(),
                                        MessageUtil.getMessageString(getMeasurementType().toString()+"_mph"),
                                        MessageUtil.getMessageString(getMeasurementType().toString()+"_miles")));				
			}
	        SimpleDateFormat sdf = new SimpleDateFormat(MessageUtil.getMessageString("dateTimeFormat"), getLocale());
	        sdf.setTimeZone(getPerson().getTimeZone());
	        setTimeString(sdf.format(event.getTime()));
	        
	        eventTime = event.getTime();
	        lat = event.getLatitude();
	        lng = event.getLongitude();
		}
		public Date getEventTime() {
			return eventTime;
		}
		public void setEventTime(Date eventTime) {
			this.eventTime = eventTime;
		}
	}
}

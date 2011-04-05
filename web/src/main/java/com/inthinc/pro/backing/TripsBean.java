package com.inthinc.pro.backing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.ajax4jsf.model.KeepAlive;
import org.apache.log4j.Logger;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.inthinc.pro.backing.model.GroupTreeNodeImpl;
import com.inthinc.pro.backing.ui.TripDisplay;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventSubCategory;
import com.inthinc.pro.model.event.IdleEvent;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.MiscUtil;

@KeepAlive
public class TripsBean extends BaseBean {

    private static final long serialVersionUID = 2409167667876030280L;
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(TripsBean.class);
    private static final long THIRTY_DAYS = 30L * 24L * 60L * 60L * 1000L;
    private static final long ONE_SECOND = 1000L;
    
    private DriverDAO driverDAO;
    private VehicleDAO vehicleDAO;
    private GroupDAO groupDAO;
    private EventDAO eventDAO;
    private Date startDate;
    private Date startDatePrev;
    private Date endDate;
    private Date endDatePrev;
    private Integer milesDriven = 0;
    private Integer idleSeconds = 0;
    private Integer numTrips = 0;
    private Integer totalDriveSeconds = 0;
    private Integer eventsPage = 0;
    private boolean showLastTenTrips = false;
    private boolean showIdleMarkers = true;
    private boolean showWarnings = true;
    private boolean showTampering = true;
    private List<TripDisplay> trips = new ArrayList<TripDisplay>();
    private List<TripDisplay> selectedTrips = new ArrayList<TripDisplay>();
    private TripDisplay selectedTrip;
    private Vehicle selectedVehicle;
    private Driver  selectedDriver;
    private List<Event> violationEvents = new ArrayList<Event>();
    private List<Event> idleEvents = new ArrayList<Event>();
    private List<Event> allEvents = new ArrayList<Event>();
    private List<Event> tamperEvents = new ArrayList<Event>();
    private IdentifiableEntityBean identifiableEntityBean;
    private GroupTreeNodeImpl groupTreeNodeImpl;
    private Map<Integer, Driver> tripsDrivers = new HashMap<Integer, Driver>();
    private String dateStatus = MessageUtil.getMessageString("trip_valid_date_range",getLocale());
    private LatLng lastLocation;
    

    

    //Changes for clientside reverse geocoding
    private Map<Long,TripDisplay> tripsMap;
    private Long selectedTripID;
    private Map<Long,Event> allEventsMap;
    private Long selectedViolationID;

    public void initTrips() {
        if (trips.isEmpty()) {
            List<Trip> tempTrips = new ArrayList<Trip>();
            if(identifiableEntityBean.getEntityType().equals(EntityType.ENTITY_DRIVER)){
                tempTrips = driverDAO.getTrips(identifiableEntityBean.getId(), getStartDate(), getEndDate());
                
            }else{
                tempTrips = vehicleDAO.getTrips(identifiableEntityBean.getId(), getStartDate(), getEndDate());
            }
            
            for (Trip trip : tempTrips) {
            	            	
                TripDisplay td = new TripDisplay(trip, getTimeZoneFromDriver(trip.getDriverID()), getAddressLookup());
                
                if ((td.getRoute() != null) && (td.getRoute().size() > 0)){
	                // If starting or ending address is null, try to set a zone name
	                if ( td.getStartAddress() == null ) {
	                    LatLng latLng = new LatLng(td.getRoute().get(0).getLat(),td.getRoute().get(0).getLng());
	                    td.setStartAddress(MiscUtil.findZoneName(this.getProUser().getZones(), latLng));
	                }                
	                if ( td.getEndAddress() == null ) {
	                    LatLng latLng = new LatLng(td.getEndPointLat(),td.getEndPointLng());
	                    td.setEndAddress(MiscUtil.findZoneName(this.getProUser().getZones(), latLng));
	                }
                }
                trips.add(td);
            }
            Collections.sort(trips);
            Collections.reverse(trips);
            numTrips = trips.size();
            if (numTrips > 0) {
                // Set selected trip and get associated events.
                // Generate Stats on selected
                setSelectedTrip(trips.get(0));
                generateStats();
            } else {
                milesDriven = 0;
                totalDriveSeconds = 0;
                idleSeconds = 0;
                setSelectedTrip(null);
                selectedTrips.clear();
                tamperEvents.clear();
                violationEvents.clear();
                idleEvents.clear();
                allEvents.clear();
            }
            makeTripMap(trips);
        }
    }
    private void makeTripMap(List<TripDisplay> trips){
        tripsMap = new LinkedHashMap<Long,TripDisplay>();
        
        for(TripDisplay trip : trips){
       	
          tripsMap.put(trip.getTrip().getTripID(), trip);
       }
    	
    }
    private TimeZone getTimeZoneFromDriver(Integer driverID) {
        // Return TimeZone if this driver is known.
        if (tripsDrivers.containsKey(driverID))
            return tripsDrivers.get(driverID).getPerson().getTimeZone();
        // Lookup driver, save Driver for repeat requests.
        TimeZone timeZone;
        Driver driver = driverDAO.findByID(driverID);
        if (driver != null && driver.getPerson() != null && driver.getPerson().getTimeZone() != null) {
            tripsDrivers.put(driver.getDriverID(), driver);
            timeZone = driver.getPerson().getTimeZone();
        }
        else {
            // Use GMT for default if no driver associated.
            timeZone = TimeZone.getTimeZone("GMT");
        }
        return timeZone;
    }

    public void initViolations(Date start, Date end) {
        if (violationEvents.isEmpty()) {
            List<NoteType> violationEventTypeList = new ArrayList<NoteType>();
            violationEventTypeList.addAll(EventSubCategory.SPEED.getNoteTypesInSubCategory());
            violationEventTypeList.add(NoteType.SEATBELT);
            violationEventTypeList.addAll(EventSubCategory.DRIVING_STYLE.getNoteTypesInSubCategory());
            List<NoteType> idleTypes = new ArrayList<NoteType>();
            idleTypes.add(NoteType.IDLE);
            List<NoteType> tamperEventTypeList = new ArrayList<NoteType>();
            tamperEventTypeList.add(NoteType.UNPLUGGED);
            tamperEventTypeList.add(NoteType.UNPLUGGED_ASLEEP);
            if (identifiableEntityBean.getEntityType().equals(EntityType.ENTITY_DRIVER)) {
            	
                violationEvents = eventDAO.getEventsForDriver(identifiableEntityBean.getId(), start, end, violationEventTypeList, getShowExcludedEvents());
                idleEvents = eventDAO.getEventsForDriver(identifiableEntityBean.getId(), start, end, idleTypes, getShowExcludedEvents());
                tamperEvents = eventDAO.getEventsForDriver(identifiableEntityBean.getId(), start, end, tamperEventTypeList, getShowExcludedEvents());
            }
            else {
            	
                violationEvents = eventDAO.getEventsForVehicle(identifiableEntityBean.getId(), start, end, violationEventTypeList, getShowExcludedEvents());
                idleEvents = eventDAO.getEventsForVehicle(identifiableEntityBean.getId(), start, end, idleTypes, getShowExcludedEvents());
                tamperEvents = eventDAO.getEventsForVehicle(identifiableEntityBean.getId(), start, end, tamperEventTypeList, getShowExcludedEvents());
            }
            // Lookup Addresses for events
            if(selectedTrip.getBeginningPoint()!= null){
	            populateAddresses(violationEvents);
	            populateAddresses(idleEvents);
	            populateAddresses(tamperEvents);
            }
            
            // Get the correct timestamp for display (vehicle page only)
            populateFormattedDate(violationEvents);
            populateFormattedDate(idleEvents);
            populateFormattedDate(tamperEvents);
            
            allEvents.clear();
            allEvents.addAll(violationEvents);
            allEvents.addAll(idleEvents);
            allEvents.addAll(tamperEvents);
            Collections.sort(allEvents);
            Collections.reverse(allEvents);
            allEventsMap = new LinkedHashMap<Long,Event>();
            for(Event event:allEvents){
            	allEventsMap.put(event.getNoteID(),event);
            }
            selectedViolationID = allEvents.size()>0?allEvents.get(0).getNoteID():null;
        }
    }

    private void populateAddresses(List<Event> eventList) {
        LatLng lastValidLatLng = selectedTrip.getBeginningPoint();
        if ((lastValidLatLng == null) && !eventList.isEmpty()){
        	lastValidLatLng = eventList.get(0).getLatLng();
        }
        for (Event event : eventList) {
        	
            if ((event.getLatitude() < 0.005 && event.getLatitude() > -0.005) || (event.getLongitude() < 0.005 && event.getLongitude() > -0.005)) {
                event.setLatitude(lastValidLatLng.getLat() + 0.00001);
                event.setLongitude(lastValidLatLng.getLng());
            }
              event.setAddressStr(getAddress(event.getLatLng()));
            // TODO: Refactor. Will set to a zone name if null back from retrieval, or could not find
            if ( event.getAddressStr() == null ) {
                event.setAddressStr(MiscUtil.findZoneName(this.getProUser().getZones(), event.getLatLng()));
            }
            lastValidLatLng = event.getLatLng();
        }
    }
    

    private void populateFormattedDate(List<Event> eventList) {
       
        for (Event event : eventList) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(MessageUtil.getMessageString("dateTimeFormat"));
            dateFormatter.setTimeZone(getTimeZone());
            event.setFormattedTime(dateFormatter.format(event.getTime()));                       
        }
    }    

    public void generateStats() {
        milesDriven = 0;
        totalDriveSeconds = 0;
        idleSeconds = 0;
        for (TripDisplay trip : trips) {
            milesDriven += trip.getTrip().getMileage();
            Long tempLong = (trip.getDurationMiliSeconds() / 1000L);
            totalDriveSeconds += tempLong.intValue();
        }
        // Get events over date picker date range
        List<NoteType> idleTypes = new ArrayList<NoteType>();
        idleTypes.add(NoteType.IDLE);
        List<Event> tmpIdleEvents = new ArrayList<Event>();
        if (identifiableEntityBean.getEntityType().equals(EntityType.ENTITY_DRIVER)) {
            tmpIdleEvents = eventDAO.getEventsForDriver(identifiableEntityBean.getId(), getStartDate(), getEndDate(), idleTypes, getShowExcludedEvents());
        }
        else {
            tmpIdleEvents = eventDAO.getEventsForVehicle(identifiableEntityBean.getId(), getStartDate(), getEndDate(), idleTypes, getShowExcludedEvents());
        }
        for (Event event : tmpIdleEvents) {
            idleSeconds += ((IdleEvent) event).getHighIdle();
            idleSeconds += ((IdleEvent) event).getLowIdle();
        }
    }

    // DATE PROPERTIES
    public Date getStartDate() {
        if (startDate == null) {
            // Set start date to 7 days ago, apply driver's time zone..
            startDate = new DateMidnight(new DateTime().minusWeeks(1), DateTimeZone.forTimeZone(getTimeZoneFromEntity())).toDate();
            startDatePrev = startDate;
        }        
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartDatePrev() {
        return startDatePrev;
    }

    public void setStartDatePrev(Date startDatePrev) {
        this.startDatePrev = startDatePrev;
    }

    public Date getEndDate() {
        if (endDate == null) {
            setEndDate(new Date());
            endDatePrev = endDate;
        }
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = setTimeToEndOfDay(endDate, getTimeZoneFromEntity());
    }
    
    private TimeZone getTimeZoneFromEntity()
    {
        if (identifiableEntityBean == null)
            return TimeZone.getTimeZone("GMT");
        
        if (identifiableEntityBean.getEntityType().equals(EntityType.ENTITY_DRIVER)) {
            return getTimeZoneFromDriver(identifiableEntityBean.getId());
        }
       return getTimeZoneFromDriver(((Vehicle)identifiableEntityBean.getEntity()).getDriverID());
        
    }
    
    public Date getEndDatePrev() {
        return endDatePrev;
    }

    public void setEndDatePrev(Date endDatePrev) {
        this.endDatePrev = endDatePrev;
    }

    public TimeZone getTimeZone(){
        if (identifiableEntityBean.getEntityType().equals(EntityType.ENTITY_DRIVER)) {
            // Set end date to now using driver's time zone.
            return getTimeZoneFromDriver(identifiableEntityBean.getId());
        }
        else {
            // The vehicle could currently NOT be associated with a driver, so,
            //  grab the trips now 
//            initTrips();

            // Could possibly have selected a date range with no trips,
            //  and therefore, no driver, set default
            if ( selectedTrip == null ) {
                return TimeZone.getTimeZone("GMT");
            } 
            
            return getTimeZoneFromDriver(selectedTrip.getTrip().getDriverID());
        }
    }
    
    public String getTimeZoneDisplay(){
        return getTimeZone().getDisplayName(false,TimeZone.LONG,getLocale());
    }

    private Date setTimeToEndOfDay(Date date, TimeZone tz) {
        return new DateMidnight(date, DateTimeZone.forTimeZone(tz)).toDateTime().plusDays(1).minus(ONE_SECOND).toDate();

    }

    // TRIP DAO PROPERTIES
    public DriverDAO getDriverDAO() {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    // MILES PROPERTIES
    public Integer getMilesDriven() {
        return milesDriven;
    }

    public void setMilesDriven(Integer milesDriven) {
        this.milesDriven = milesDriven;
    }

    // IDLE SECONDS PROPERTIES
    public Integer getIdleSeconds() {
        return idleSeconds;
    }

    public void setIdleSeconds(Integer IdleSeconds) {
        this.idleSeconds = IdleSeconds;
    }

    public String getIdleTime() {
        return DateUtil.getDurationFromSeconds(this.getIdleSeconds());
    }

    // NUMBER OF TRIPS PROPERTIES
    public Integer getNumTrips() {
        initTrips();
        return numTrips;
    }

    public void setNumTrips(Integer numTrips) {
        this.numTrips = numTrips;
    }

    // TOTAL DRIVE Seconds PROPERTIES
    public Integer getTotalDriveSeconds() {
        return totalDriveSeconds;
    }

    public void setTotalDriveSeconds(Integer totalDriveSeconds) {
        this.totalDriveSeconds = totalDriveSeconds;
    }

    public String getTotalDriveTime() {
        return DateUtil.getDurationFromSeconds(this.getTotalDriveSeconds());
    }

    // SHOW ALL TRIPS SETTING PROPERTIES
    public boolean isShowLastTenTrips() {
        return showLastTenTrips;
    }

    public void setShowLastTenTrips(boolean showLastTenTrips) {
        this.showLastTenTrips = showLastTenTrips;
        selectedTrips.clear();
        // Check box is checked
        if (showLastTenTrips == true) {
            int count = 0;
            for (TripDisplay trip : trips) {
                // Add 10 trips to list then stop.
                if (count == 10)
                    break;
                selectedTrips.add(trip);
                count++;
            }
            // Reverse list, oldest is first
            Collections.reverse(selectedTrips);
            
            // Load events for given list.
            this.violationEvents.clear();
            initViolations(selectedTrips.get(selectedTrips.size() - 1).getTrip().getStartTime(), selectedTrips.get(0).getTrip().getEndTime());
        }
        else {
            if (selectedTrip == null) {
                setSelectedTrip(trips.get(0));
            }
            else {
                setSelectedTrip(selectedTrip);
             }
        }
    }

    // SHOW IDLE MARKERS SETTING PROPERTIES
    public boolean isShowIdleMarkers() {
        return showIdleMarkers;
    }

    public void setShowIdleMarkers(boolean showIdleMarkers) {
        this.showIdleMarkers = showIdleMarkers;
    }

    // SHOW WARNINGS SETTING PROPERTIES
    public boolean isShowWarnings() {
        return showWarnings;
    }

    public void setShowWarnings(boolean showWarnings) {
        this.showWarnings = showWarnings;
    }

    // TRIP PROPERTIES
    public List<TripDisplay> getTrips() {
        return trips;
    }

    public void setTrips(List<TripDisplay> trips) {
        this.trips = trips;
    }

    // SELECTD TRIP PROPERTIES
    public TripDisplay getSelectedTrip() {
        return selectedTrip;
    }

    public void setSelectedTrip(TripDisplay selectedTrip) {
    	
    	// TODO: refactor
    	if (selectedTrip != null && this.selectedTrip != null) {
    		if (selectedTrip.equals(this.selectedTrip)) {
    			return;
    		}
    	}
        this.selectedTrip = selectedTrip;
        // Add this trip to the list of selected trips.
        selectedTrips.clear();
        violationEvents.clear();
        idleEvents.clear();
        
        if (selectedTrip != null){
        	
	        selectedTrips.add(selectedTrip);
	        this.showLastTenTrips = false;
	        if(identifiableEntityBean.getEntityType().equals(EntityType.ENTITY_DRIVER)){
	            setSelectedVehicle(vehicleDAO.findByID(selectedTrip.getTrip().getVehicleID()));
	        }else{
	         // Get Driver for this Trip
	            setSelectedDriver(driverDAO.findByID(selectedTrip.getTrip().getDriverID()));
	        }
	        // Get Violations for this Trip
	        initViolations(selectedTrip.getTrip().getStartTime(), selectedTrip.getTrip().getEndTime());
	        selectedTripID = selectedTrips.get(0).getTrip().getTripID();
        }
    }

    // VIOLATIONS PROPERTIES
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

    // EVENT DAO PROPERTIES
    public EventDAO getEventDAO() {
        return eventDAO;
    }

    public void setEventDAO(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    public VehicleDAO getVehicleDAO() {
        return vehicleDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    // SELECTED TRIPS PROPERTIES
    public List<TripDisplay> getSelectedTrips() {
        initTrips();
        return selectedTrips;
    }

    public void setSelectedTrips(List<TripDisplay> selectedTrips) {
        this.selectedTrips = selectedTrips;
    }

    public void DateChangedAction() {
                
        if ( checkDates() ) {        
            // Save the good dates
            startDatePrev = startDate;
            endDatePrev = endDate;
            trips.clear();
            tripsMap = null;
            initTrips();
                    
        } else {
            // Bad dates, reload old
            startDate = startDatePrev;
            endDate = endDatePrev;
        }
        
    }
    private boolean checkDates() {     
        // Code implemented to make sure users don't go past 30 days when
        //  searching for trips  
        
        //  Start before end date
        if (        (this.endDate.getTime() - this.startDate.getTime()) < 0L ) {            
            setDateStatus(MessageUtil.getMessageString("trip_end_before_start",getLocale()));            
            return false;
            
        // Start date more than 30 days in the past from today    
        } else if ( ((new Date()).getTime()-startDate.getTime()) > THIRTY_DAYS ) {
            setDateStatus(MessageUtil.getMessageString("trip_start_more_than_thirty",getLocale()));            
            return false;
            
        // Winner!
        } else {
            setDateStatus(MessageUtil.getMessageString("trip_valid_date_range",getLocale()));            
            return true;
        }        
    }

    public Integer getEventsPage() {
        return eventsPage;
    }

    public void setEventsPage(Integer eventsPage) {
        this.eventsPage = eventsPage;
    }

    public Vehicle getSelectedVehicle() {
        return selectedVehicle;
    }

    public void setSelectedVehicle(Vehicle selectedVehicle) {
        this.selectedVehicle = selectedVehicle;
    }

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }

    public void setGroupTreeNodeImpl(GroupTreeNodeImpl groupTreeNodeImpl) {
        this.groupTreeNodeImpl = groupTreeNodeImpl;
    }

    public GroupTreeNodeImpl getGroupTreeNodeImpl() {
        return groupTreeNodeImpl;
    }

    public List<Event> getTamperEvents() {
        return tamperEvents;
    }

    public void setTamperEvents(List<Event> tamperEvents) {
        this.tamperEvents = tamperEvents;
    }

    public void setShowTampering(boolean showTampering) {
        this.showTampering = showTampering;
    }

    public boolean isShowTampering() {
        return showTampering;
    }

    public void setIdentifiableEntityBean(IdentifiableEntityBean identifiableEntityBean) {
        this.identifiableEntityBean = identifiableEntityBean;
    }

    public IdentifiableEntityBean getIdentifiableEntityBean() {
        return identifiableEntityBean;
    }

    public void setSelectedDriver(Driver selectedDriver) {
        this.selectedDriver = selectedDriver;
    }

    public Driver getSelectedDriver() {
        return selectedDriver;
    }

    public String getDateStatus() {
        return dateStatus;
    }

    public void setDateStatus(String dateStatus) {
        this.dateStatus = dateStatus;
    }

	public Map<Long, TripDisplay> getTripsMap() {
		return tripsMap;
	}

	public void setTripsMap(Map<Long, TripDisplay> tripsMap) {
		this.tripsMap = tripsMap;
	}

	public Long getSelectedTripID() {
		return selectedTripID;
	}

	public void setSelectedTripID(Long selectedTripID) {
		this.selectedTripID = selectedTripID;
	}

	public Map<Long, Event> getAllEventsMap() {
		return allEventsMap;
	}

	public void setAllEventsMap(Map<Long, Event> allEventsMap) {
		this.allEventsMap = allEventsMap;
	}

	public Long getSelectedViolationID() {
		return selectedViolationID;
	}

	public void setSelectedViolationID(Long selectedViolationID) {
		this.selectedViolationID = selectedViolationID;
	}
	public Double getAnchorLat(){
		if(lastLocation==null) setLastLocation();
		return lastLocation.getLat();
	}
	public Double getAnchorLng(){
		if(lastLocation==null) setLastLocation();
		return lastLocation.getLng();
	}
	private void setLastLocation(){
		for(TripDisplay trip : selectedTrips){
			if(trip.getRouteLastStep()!=null){
				lastLocation= trip.getRouteLastStep();
				break;
			}
		}
		if(lastLocation==null){
			lastLocation = getEntityLastLocation();
		}
	}
	private LatLng  getEntityLastLocation(){
		if(identifiableEntityBean.getEntityType().equals(EntityType.ENTITY_DRIVER)){
	        LastLocation ll = driverDAO.getLastLocation(identifiableEntityBean.getId());
	        return ll.getLoc();
	    }
		else{
	    	LastLocation ll = vehicleDAO.getLastLocation(identifiableEntityBean.getId());
	        return ll.getLoc();
	    }
	}
}

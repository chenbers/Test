package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.model.GroupTreeNodeImpl;
import com.inthinc.pro.backing.ui.TripDisplay;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.IdleEvent;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.Vehicle;

public class TripsBean extends BaseBean {

    /**
     * 
     */
    private static final long serialVersionUID = 2409167667876030280L;
    private static final Logger logger = Logger.getLogger(TripsBean.class);
    private DriverDAO driverDAO;
    private VehicleDAO vehicleDAO;
    private GroupDAO groupDAO;
    private EventDAO eventDAO;
    private AddressLookup addressLookup;
    private Date startDate;
    private Date endDate;
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

    public void initTrips() {
        if (trips.isEmpty()) {
            List<Trip> tempTrips = new ArrayList<Trip>();
            if(identifiableEntityBean.getEntityType().equals(EntityType.ENTITY_DRIVER)){
                tempTrips = driverDAO.getTrips(identifiableEntityBean.getId(), getStartDate(), getEndDate());
            }else{
                tempTrips = vehicleDAO.getTrips(identifiableEntityBean.getId(), getStartDate(), getEndDate());
            }
            
            for (Trip trip : tempTrips) {
                trips.add(new TripDisplay(trip, getTimeZoneFromDriver(trip.getDriverID()), addressLookup.getMapServerURLString()));
            }
            Collections.sort(trips);
            Collections.reverse(trips);
            numTrips = trips.size();
            if (numTrips > 0) {
                // Set selected trip and get associated events.
                // Generate Stats on selected
                setSelectedTrip(trips.get(0));
                generateStats();
            }
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
            List<Integer> violationEventTypeList = new ArrayList<Integer>();
            violationEventTypeList.add(EventMapper.TIWIPRO_EVENT_SPEEDING_EX3);
            violationEventTypeList.add(EventMapper.TIWIPRO_EVENT_SEATBELT);
            violationEventTypeList.add(EventMapper.TIWIPRO_EVENT_NOTEEVENT);
            List<Integer> idleTypes = new ArrayList<Integer>();
            idleTypes.add(EventMapper.TIWIPRO_EVENT_IDLE);
            List<Integer> tamperEventTypeList = new ArrayList<Integer>();
            tamperEventTypeList.add(EventMapper.TIWIPRO_EVENT_UNPLUGGED);
            if (identifiableEntityBean.getEntityType().equals(EntityType.ENTITY_DRIVER)) {
                violationEvents = eventDAO.getEventsForDriver(identifiableEntityBean.getId(), start, end, violationEventTypeList, showExcludedEvents);
                idleEvents = eventDAO.getEventsForDriver(identifiableEntityBean.getId(), start, end, idleTypes, showExcludedEvents);
                tamperEvents = eventDAO.getEventsForDriver(identifiableEntityBean.getId(), start, end, tamperEventTypeList, showExcludedEvents);
            }
            else {
                violationEvents = eventDAO.getEventsForVehicle(identifiableEntityBean.getId(), start, end, violationEventTypeList, showExcludedEvents);
                idleEvents = eventDAO.getEventsForVehicle(identifiableEntityBean.getId(), start, end, idleTypes, showExcludedEvents);
                tamperEvents = eventDAO.getEventsForVehicle(identifiableEntityBean.getId(), start, end, tamperEventTypeList, showExcludedEvents);
            }
            // Lookup Addresses for events
            populateAddresses(violationEvents);
            populateAddresses(idleEvents);
            populateAddresses(tamperEvents);
            allEvents.clear();
            allEvents.addAll(violationEvents);
            allEvents.addAll(idleEvents);
            allEvents.addAll(tamperEvents);
            Collections.sort(allEvents);
            Collections.reverse(allEvents);
        }
    }

    private void populateAddresses(List<Event> eventList) {
        LatLng lastValidLatLng = selectedTrip.getBeginningPoint();
        for (Event event : eventList) {
            if ((event.getLatitude() < 0.005 && event.getLatitude() > -0.005) || (event.getLongitude() < 0.005 && event.getLongitude() > -0.005)) {
                event.setLatitude(lastValidLatLng.getLat() + 0.00001);
                event.setLongitude(lastValidLatLng.getLng());
            }
            event.setAddressStr(addressLookup.getAddress(event.getLatitude(), event.getLongitude()));
            lastValidLatLng = event.getLatLng();
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
        List<Integer> idleTypes = new ArrayList<Integer>();
        idleTypes.add(EventMapper.TIWIPRO_EVENT_IDLE);
        List<Event> tmpIdleEvents = new ArrayList<Event>();
        if (identifiableEntityBean.getEntityType().equals(EntityType.ENTITY_DRIVER)) {
            tmpIdleEvents = eventDAO.getEventsForDriver(identifiableEntityBean.getId(), getStartDate(), getEndDate(), idleTypes, showExcludedEvents);
        }
        else {
            tmpIdleEvents = eventDAO.getEventsForVehicle(identifiableEntityBean.getId(), getStartDate(), getEndDate(), idleTypes, showExcludedEvents);
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
            Calendar calendar = Calendar.getInstance();
            // calendar.setTimeZone(navigation.getDriver().getPerson().getTimeZone());
            calendar.add(Calendar.DAY_OF_MONTH, -7);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            startDate = calendar.getTime();
        }
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        if (endDate == null) {
            if (identifiableEntityBean.getEntityType().equals(EntityType.ENTITY_DRIVER)) {
                // Set end date to now using driver's time zone.
                endDate = SetTimeToEndOfDay(new Date(), getTimeZoneFromDriver(identifiableEntityBean.getId()));
            }
            else {
                endDate = SetTimeToEndOfDay(new Date(), getTimeZoneFromDriver(((Vehicle)identifiableEntityBean.getEntity()).getDriverID()));
            }
        }
        return endDate;
    }

    public void setEndDate(Date endDate) {
        Date dateOut = null;
        
        // Set Time to 11:59:99 PM Always
        if (identifiableEntityBean.getEntityType().equals(EntityType.ENTITY_DRIVER)) {
            // Set end date to now using driver's time zone.
            dateOut = SetTimeToEndOfDay(endDate, getTimeZoneFromDriver(identifiableEntityBean.getId()));
        }
        else {
            dateOut = SetTimeToEndOfDay(endDate, getTimeZoneFromDriver(((Vehicle)identifiableEntityBean.getEntity()).getDriverID()));
        }
        
        this.endDate = dateOut;
    }
    
    public TimeZone getTimeZone(){
        if (identifiableEntityBean.getEntityType().equals(EntityType.ENTITY_DRIVER)) {
            // Set end date to now using driver's time zone.
            return getTimeZoneFromDriver(identifiableEntityBean.getId());
        }
        else {
            return getTimeZoneFromDriver(((Vehicle)identifiableEntityBean.getEntity()).getDriverID());
        }
    }
    
    public String getTimeZoneDisplay(){
        return getTimeZone().getDisplayName(false,TimeZone.LONG,getLocale());
    }

    public Date SetTimeToEndOfDay(Date date, TimeZone tz) {
        // Adjust time using TimeZome and set to 11:59:59
        Calendar calendar = Calendar.getInstance();
        // calendar.setTimeZone(tz);
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
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

    public AddressLookup getAddressLookup() {
        return addressLookup;
    }

    public void setAddressLookup(AddressLookup addressLookup) {
        this.addressLookup = addressLookup;
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
        trips.clear();
        initTrips();
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
}

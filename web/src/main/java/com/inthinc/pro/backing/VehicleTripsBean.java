package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.springframework.security.AccessDeniedException;

import com.inthinc.pro.backing.model.GroupTreeNodeImpl;
import com.inthinc.pro.backing.ui.EventReportItem;
import com.inthinc.pro.backing.ui.TripDisplay;
import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.IdleEvent;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.util.DateUtil;

public class VehicleTripsBean extends BaseBean
{
    private static final Logger   logger            = Logger.getLogger(VehicleTripsBean.class);

    private VehicleDAO            vehicleDAO;
    private DriverDAO             driverDAO;
    private EventDAO              eventDAO;
    private GroupDAO              groupDAO;
    private AddressLookup         addressLookup;

    private Date                  startDate;
    private Date                  endDate;

    private Integer               milesDriven       = 0;
    private Integer               idleSeconds       = 0;
    private Integer               numTrips          = 0;
    private Integer               totalDriveSeconds = 0;
    private Integer               eventsPage        = 0;

    private boolean               showLastTenTrips  = false;
    private boolean               showIdleMarkers   = true;
    private boolean               showWarnings      = true;

    private Map<Integer, Driver>  tripsDrivers      = new HashMap<Integer, Driver>();
    private List<TripDisplay>     trips             = new ArrayList<TripDisplay>();
    private List<TripDisplay>     selectedTrips     = new ArrayList<TripDisplay>();
    private TripDisplay           selectedTrip;
    private Driver                selectedDriver;
    private List<EventReportItem> violationEvents   = new ArrayList<EventReportItem>();
    private List<EventReportItem> idleEvents        = new ArrayList<EventReportItem>();
    private List<EventReportItem> allEvents         = new ArrayList<EventReportItem>();
    private TimeZone              timeZone;
    
    private Vehicle               vehicle;
    private Integer               vehicleID;
    
    private GroupTreeNodeImpl     groupTreeNodeImpl;

    public void initTrips()
    {
        if (trips.isEmpty())
        {
            List<Trip> tempTrips = new ArrayList<Trip>();
            tempTrips = vehicleDAO.getTrips(getVehicle().getVehicleID(), getStartDate(), getEndDate());

            for (Trip trip : tempTrips)
            {
                trips.add(new TripDisplay(trip, getTimeZoneFromDriver(trip.getDriverID()), addressLookup.getMapServerURLString()));
            }
            Collections.sort(trips);
            Collections.reverse(trips);

            numTrips = trips.size();
            if (numTrips > 0)
            {
                // Generate Stats on selected
                setSelectedTrip(trips.get(0));
                generateStats();
            }
        }
    }

    public void initViolations(Date start, Date end)
    {
        if (violationEvents.isEmpty())
        {
            List<Integer> vioTypes = new ArrayList<Integer>();
            vioTypes.add(EventMapper.TIWIPRO_EVENT_SPEEDING_EX3);
            vioTypes.add(EventMapper.TIWIPRO_EVENT_SEATBELT);
            vioTypes.add(EventMapper.TIWIPRO_EVENT_NOTEEVENT);

            List<Integer> idleTypes = new ArrayList<Integer>();
            idleTypes.add(EventMapper.TIWIPRO_EVENT_IDLE);

            List<Event> tempViolations;
            List<Event> tempIdle;

            tempViolations = eventDAO.getEventsForVehicle(getVehicle().getVehicleID(), start, end, vioTypes,showExcludedEvents);
            tempIdle = eventDAO.getEventsForVehicle(getVehicle().getVehicleID(), start, end, idleTypes,showExcludedEvents);

            // Lookup Addresses for events
            violationEvents = populateAddresses(tempViolations);
            idleEvents = populateAddresses(tempIdle);

            allEvents.clear();
            allEvents.addAll(violationEvents);
            allEvents.addAll(idleEvents);
            Collections.sort(allEvents);
            Collections.reverse(allEvents);
        }
    }

    private List<EventReportItem> populateAddresses(List<Event> eventList)
    {
        List<EventReportItem> returnList = new ArrayList<EventReportItem>();
        LatLng lastValidLatLng = selectedTrip.getBeginningPoint();
        for (Event event : eventList)
        {
            if((event.getLatitude() < 0.005 && event.getLatitude() > -0.005) || (event.getLongitude() < 0.005 && event.getLongitude() > -0.005))
            {                
                event.setLatitude(lastValidLatLng.getLat() + 0.00001);
                event.setLongitude(lastValidLatLng.getLng());
            }
            event.setAddressStr(addressLookup.getAddress(event.getLatitude(), event.getLongitude()));
            lastValidLatLng = event.getLatLng();
            returnList.add(new EventReportItem(event, getTimeZoneFromDriver(event.getDriverID()),getMeasurementType()));
        }
        return returnList;
    }
    
    public void generateStats()
    {
        milesDriven = 0;
        totalDriveSeconds = 0;
        idleSeconds = 0;

        for (TripDisplay trip : trips)
        {
            milesDriven += trip.getTrip().getMileage();

            Long tempLong = (trip.getDurationMiliSeconds() / 1000L);
            totalDriveSeconds += tempLong.intValue();
        }

        // Get events over date picker date range
        List<Integer> idleTypes = new ArrayList<Integer>();
        idleTypes.add(EventMapper.TIWIPRO_EVENT_IDLE);

        List<Event> tmpIdleEvents = new ArrayList<Event>();
        tmpIdleEvents = eventDAO.getEventsForVehicle(getVehicle().getVehicleID(), startDate, endDate, idleTypes,showExcludedEvents);

        for (Event event : tmpIdleEvents)
        {
            idleSeconds += ((IdleEvent) event).getHighIdle();
            idleSeconds += ((IdleEvent) event).getLowIdle();
        }
    }

    private TimeZone getTimeZoneFromDriver(Integer driverID)
    {
        // Return TimeZone if this driver is known.
        if (tripsDrivers.containsKey(driverID))
            return tripsDrivers.get(driverID).getPerson().getTimeZone();

        // Lookup driver, save Driver for repeat requests.
        TimeZone tz;
        Driver driver = driverDAO.findByID(driverID);
        if (driver != null && driver.getPerson() != null && driver.getPerson().getTimeZone() != null)
        {
            tripsDrivers.put(driver.getDriverID(), driver);
            tz = driver.getPerson().getTimeZone();
        }
        else
        {
            // Use GMT for default if no driver associated.
            tz = TimeZone.getTimeZone("GMT");
        }

        return tz;
    }

    // DATE PROPERTIES
    public Date getStartDate()
    {
        if (startDate == null)
        {
            // Set start date to 7 days ago, apply driver's time zone..
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(getTimeZone());
            calendar.add(Calendar.DAY_OF_MONTH, -7);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            startDate = calendar.getTime();
        }
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getEndDate()
    {
        if (endDate == null)
        {
            // Set end date to now using driver's time zone.
            endDate = SetTimeToEndOfDay(new Date(), getTimeZone());
        }
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        // Set Time to 11:59:99 PM Always
        endDate = SetTimeToEndOfDay(endDate, getTimeZone());
        this.endDate = endDate;
    }

    public Date SetTimeToEndOfDay(Date date, TimeZone tz)
    {
        // Adjust time using TimeZome and set to 11:59:59
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(tz);
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        return calendar.getTime();
    }

    // TRIP DAO PROPERTIES
    public VehicleDAO getVehicleDAO()
    {
        return vehicleDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO)
    {
        this.vehicleDAO = vehicleDAO;
    }

    // MILES PROPERTIES
    public Integer getMilesDriven()
    {
        return milesDriven;
    }

    public void setMilesDriven(Integer milesDriven)
    {
        this.milesDriven = milesDriven;
    }

    // IDLE SECONDS PROPERTIES
    public Integer getIdleSeconds()
    {
        return idleSeconds;
    }

    public void setIdleSeconds(Integer IdleSeconds)
    {
        this.idleSeconds = IdleSeconds;
    }

    public String getIdleTime()
    {
        return DateUtil.getDurationFromSeconds(this.getIdleSeconds());
    }

    // NUMBER OF TRIPS PROPERTIES
    public Integer getNumTrips()
    {
        initTrips();
        return numTrips;
    }

    public void setNumTrips(Integer numTrips)
    {
        this.numTrips = numTrips;
    }

    // TOTAL DRIVE Seconds PROPERTIES
    public Integer getTotalDriveSeconds()
    {
        return totalDriveSeconds;
    }

    public void setTotalDriveSeconds(Integer totalDriveSeconds)
    {
        this.totalDriveSeconds = totalDriveSeconds;
    }

    public String getTotalDriveTime()
    {
        return DateUtil.getDurationFromSeconds(this.getTotalDriveSeconds());
    }

    // SHOW ALL TRIPS SETTING PROPERTIES
    public boolean isShowLastTenTrips()
    {
        return showLastTenTrips;
    }

    public void setShowLastTenTrips(boolean showLastTenTrips)
    {
        this.showLastTenTrips = showLastTenTrips;
        selectedTrips.clear();

        // Check box is checked
        if (showLastTenTrips == true)
        {
            int count = 0;
            for (TripDisplay trip : trips)
            {
                // Add 10 trips to list then stop.
                if (count == 10)
                    break;

                selectedTrips.add(trip);
                count++;
            }
            Collections.reverse(selectedTrips);
            
            // Load events for given list.
            this.violationEvents.clear();
            initViolations(selectedTrips.get(selectedTrips.size() - 1).getTrip().getStartTime(), selectedTrips.get(0).getTrip().getEndTime());
        }
        else
        {
            if (selectedTrip == null)
            {
                setSelectedTrip(trips.get(0));
            }
            else
            {
                setSelectedTrip(selectedTrip);
            }
        }
    }

    // SHOW IDLE MARKERS SETTING PROPERTIES
    public boolean isShowIdleMarkers()
    {
        return showIdleMarkers;
    }

    public void setShowIdleMarkers(boolean showIdleMarkers)
    {
        this.showIdleMarkers = showIdleMarkers;
    }

    // SHOW WARNINGS SETTING PROPERTIES
    public boolean isShowWarnings()
    {
        return showWarnings;
    }

    public void setShowWarnings(boolean showWarnings)
    {
        this.showWarnings = showWarnings;
    }

    // TRIP PROPERTIES
    public List<TripDisplay> getTrips()
    {
        return trips;
    }

    public void setTrips(List<TripDisplay> trips)
    {
        this.trips = trips;
    }

    // SELECTD TRIP PROPERTIES
    public TripDisplay getSelectedTrip()
    {
        return selectedTrip;
    }

    public void setSelectedTrip(TripDisplay selectedTrip)
    {
        this.selectedTrip = selectedTrip;
        // Add this trip to the list of selected trips.
        selectedTrips.clear();
        selectedTrips.add(selectedTrip);
        this.showLastTenTrips = false;
        this.eventsPage = 1;

        // Get Driver for this Trip
        setSelectedDriver(driverDAO.findByID(selectedTrip.getTrip().getDriverID()));

        // Get Violations for this Trip
        violationEvents.clear();
        idleEvents.clear();
        initViolations(selectedTrip.getTrip().getStartTime(), selectedTrip.getTrip().getEndTime());
    }

    // VIOLATIONS PROPERTIES
    public List<EventReportItem> getViolationEvents()
    {
        return violationEvents;
    }

    public void setViolationEvents(List<EventReportItem> violationEvents)
    {
        this.violationEvents = violationEvents;
    }

    public List<EventReportItem> getIdleEvents()
    {
        return idleEvents;
    }

    public void setIdleEvents(List<EventReportItem> idleEvents)
    {
        this.idleEvents = idleEvents;
    }

    public List<EventReportItem> getAllEvents()
    {
        return allEvents;
    }

    public void setAllEvents(List<EventReportItem> allEvents)
    {
        this.allEvents = allEvents;
    }

    // EVENT DAO PROPERTIES
    public EventDAO getEventDAO()
    {
        return eventDAO;
    }

    public void setEventDAO(EventDAO eventDAO)
    {
        this.eventDAO = eventDAO;
    }

    public AddressLookup getAddressLookup()
    {
        return addressLookup;
    }

    public void setAddressLookup(AddressLookup addressLookup)
    {
        this.addressLookup = addressLookup;
    }

    // SELECTED TRIPS PROPERTIES
    public List<TripDisplay> getSelectedTrips()
    {
        initTrips();
        return selectedTrips;
    }

    public void setSelectedTrips(List<TripDisplay> selectedTrips)
    {
        this.selectedTrips = selectedTrips;
    }

    public void DateChangedAction()
    {
        trips.clear();
        initTrips();
    }

    public DriverDAO getDriverDAO()
    {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO)
    {
        this.driverDAO = driverDAO;
    }

    public Driver getSelectedDriver()
    {
        return selectedDriver;
    }

    public void setSelectedDriver(Driver selectedDriver)
    {
        this.selectedDriver = selectedDriver;
    }

    public String getDisplayTimeZone()
    {

        return getTimeZone().getDisplayName(timeZone.inDaylightTime(new GregorianCalendar(timeZone).getTime()), TimeZone.LONG);
    }

    public TimeZone getTimeZone()
    {
        // Get Time Zone from driver
        if (timeZone == null)
        {
            timeZone = getTimeZoneFromDriver(getVehicle().getDriverID());
        }

        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone)
    {
        this.timeZone = timeZone;
    }

    public Map<Integer, Driver> getTripsDrivers()
    {
        return tripsDrivers;
    }

    public void setTripsDrivers(Map<Integer, Driver> tripsDrivers)
    {
        this.tripsDrivers = tripsDrivers;
    }

    public Integer getEventsPage()
    {
        return eventsPage;
    }

    public void setEventsPage(Integer eventsPage)
    {
        this.eventsPage = eventsPage;
    }

    public void setVehicle(Vehicle vehicle)
    {
        this.vehicle = vehicle;
    }

    public Vehicle getVehicle()
    {
        return vehicle;
    }

    public void setVehicleID(Integer vehicleID)
    {
        this.vehicle = vehicleDAO.findByID(vehicleID);
        if (vehicle == null || getGroupHierarchy().getGroup(vehicle.getGroupID()) == null)
            throw new AccessDeniedException(MessageUtil.getMessageString("exception_accessDenied", getUser().getLocale()));
        groupTreeNodeImpl = new GroupTreeNodeImpl(groupDAO.findByID(vehicle.getGroupID()),getGroupHierarchy());
        this.vehicleID = vehicleID;
    }

    public Integer getVehicleID()
    {
        
        return vehicleID;
    }

    public void setGroupDAO(GroupDAO groupDAO)
    {
        this.groupDAO = groupDAO;
    }

    public GroupDAO getGroupDAO()
    {
        return groupDAO;
    }

    public void setGroupTreeNodeImpl(GroupTreeNodeImpl groupTreeNodeImpl)
    {
        this.groupTreeNodeImpl = groupTreeNodeImpl;
    }

    public GroupTreeNodeImpl getGroupTreeNodeImpl()
    {
        return groupTreeNodeImpl;
    }

}

package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.TripDisplay;
import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.IdleEvent;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.util.DateUtil;

public class DriverTripsBean extends BaseBean
{
    private static final Logger logger            = Logger.getLogger(DriverTripsBean.class);

    private NavigationBean      navigation;
    private DriverDAO           driverDAO;
    private EventDAO            eventDAO;
    private AddressLookup       addressLookup;

    private Date                startDate;
    private Date                endDate;

    private Double              milesDriven       = 0.0D;
    private Integer             idleSeconds       = 0;
    private Integer             numTrips          = 0;
    private Integer             totalDriveSeconds = 0;

    private boolean             showLastTenTrips  = false;
    private boolean             showIdleMarkers   = true;
    private boolean             showWarnings      = true;

    private List<TripDisplay>   trips             = new ArrayList<TripDisplay>();
    private List<TripDisplay>   selectedTrips     = new ArrayList<TripDisplay>();
    private TripDisplay         selectedTrip;
    private List<Event>         violationEvents   = new ArrayList<Event>();
    private List<Event>         idleEvents        = new ArrayList<Event>();
    private List<Event>         allEvents         = new ArrayList<Event>();

    public void initTrips()
    {
        if(trips.isEmpty())
        {
            List<Trip> tempTrips = new ArrayList<Trip>();
            tempTrips = driverDAO.getTrips(navigation.getDriver().getDriverID(), getStartDate(), getEndDate());
            
            for (Trip trip : tempTrips)
            {
                trips.add(new TripDisplay(trip, navigation.getDriver().getPerson().getTimeZone(), addressLookup.getMapServerURLString()));
            }
            Collections.sort(trips);
            Collections.reverse(trips);
    
            numTrips = trips.size();
            if (numTrips > 0)
            {
                // Set selected trip and get associated events.
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

            violationEvents = eventDAO.getEventsForDriver(navigation.getDriver().getDriverID(), start, end, vioTypes);
            idleEvents = eventDAO.getEventsForDriver(navigation.getDriver().getDriverID(), start, end, idleTypes);

            // Lookup Addresses for events
            for (Event event : violationEvents)
            {
                event.setAddressStr(addressLookup.getAddress(event.getLatitude(), event.getLongitude()));
            }
            for (Event event : idleEvents)
            {
                event.setAddressStr(addressLookup.getAddress(event.getLatitude(), event.getLongitude()));
            }

            allEvents.clear();
            allEvents.addAll(violationEvents);
            allEvents.addAll(idleEvents);
            Collections.sort(allEvents);
            Collections.reverse(allEvents);
        }
    }

    public void generateStats()
    {
        milesDriven = 0D;
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
        tmpIdleEvents = eventDAO.getEventsForDriver(
                                 navigation.getDriver().getDriverID(), getStartDate(), getEndDate(), idleTypes);        

        logger.debug("generateStats() - " + DateUtil.convertDateToSeconds(getStartDate()) + " - " + DateUtil.convertDateToSeconds(getStartDate()));
                
        for (Event event : tmpIdleEvents)
        {
            idleSeconds += ((IdleEvent) event).getHighIdle();
            idleSeconds += ((IdleEvent) event).getLowIdle();
        }
    }

    // DATE PROPERTIES
    public Date getStartDate()
    {
        if(startDate == null)
        {
            // Set start date to 7 days ago, apply driver's time zone..
            Calendar calendar = Calendar.getInstance();
            //calendar.setTimeZone(navigation.getDriver().getPerson().getTimeZone());
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
        if(endDate == null)
        {
            // Set end date to now using driver's time zone.
            endDate = SetTimeToEndOfDay(new Date(), navigation.getDriver().getPerson().getTimeZone());
        }
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        //Set Time to 11:59:99 PM Always
        endDate = SetTimeToEndOfDay(endDate, navigation.getDriver().getPerson().getTimeZone());
        this.endDate = endDate;
    }

    public Date SetTimeToEndOfDay(Date date, TimeZone tz)
    {
        // Adjust time using TimeZome and set to 11:59:59
        Calendar calendar = Calendar.getInstance();
        //calendar.setTimeZone(tz);
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND,59);
        
        return calendar.getTime();
    }
    // TRIP DAO PROPERTIES
    public DriverDAO getDriverDAO()
    {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO)
    {
        this.driverDAO = driverDAO;
    }

    // MILES PROPERTIES
    public Double getMilesDriven()
    {
        return milesDriven / 100;
    }

    public void setMilesDriven(Double milesDriven)
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

                selectedTrips.add(trip); // Trips are already in reverse order. (Most recent first)
                count++;
            }
            
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
        //Add this trip to the list of selected trips.
        selectedTrips.clear();
        selectedTrips.add(selectedTrip);
        this.showLastTenTrips = false;
        
        //Get Violations for this Trip
        violationEvents.clear();
        idleEvents.clear();
        initViolations(selectedTrip.getTrip().getStartTime(), selectedTrip.getTrip().getEndTime());
    }

    // NAVIGATION PROPERTIES
    public NavigationBean getNavigation()
    {
        return navigation;
    }

    public void setNavigation(NavigationBean navigation)
    {
        this.navigation = navigation;
    }

    // VIOLATIONS PROPERTIES
    public List<Event> getViolationEvents()
    {
        return violationEvents;
    }

    public void setViolationEvents(List<Event> violationEvents)
    {
        this.violationEvents = violationEvents;
    }

    public List<Event> getIdleEvents()
    {
        return idleEvents;
    }

    public void setIdleEvents(List<Event> idleEvents)
    {
        this.idleEvents = idleEvents;
    }

    public List<Event> getAllEvents()
    {
        return allEvents;
    }

    public void setAllEvents(List<Event> allEvents)
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
}

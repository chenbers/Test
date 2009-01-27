package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    private Date                startDate         = new Date();
    private Date                endDate           = new Date();

    private Double              milesDriven       = 0.0D;
    private Integer             idleSeconds       = 0;
    private Integer             numTrips          = 0;
    private Integer             totalDriveSeconds = 0;

    private boolean             showLastTenTrips  = false;
    private boolean             showIdleMarkers   = true;
    private boolean             showWarnings      = true;

    private List<TripDisplay>   trips             = new ArrayList<TripDisplay>();
    private List<TripDisplay>   selectedTrips;
    private TripDisplay         selectedTrip;
    private List<Event>         violationEvents   = new ArrayList<Event>();
    private List<Event>         idleEvents        = new ArrayList<Event>();

    public DriverTripsBean()
    {
        // TODO set startDate to 7 days ago 12:00:00AM.
        
        // Set start date to 7 days ago.
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        startDate = calendar.getTime();
    }

    public void initTrips()
    {
        if(trips.size() < 1)
        {
            List<Trip> tempTrips = new ArrayList<Trip>();
            tempTrips = driverDAO.getTrips(navigation.getDriver().getDriverID(), startDate, endDate);
     
            selectedTrips = new ArrayList<TripDisplay>();
    
            for (Trip trip : tempTrips)
            {
                trips.add(0, new TripDisplay(trip, navigation.getDriver().getPerson().getTimeZone()));
            }
    
            numTrips = trips.size();
    
            if (numTrips > 0)
            {
                logger.debug(numTrips.toString() + "Trips Found.");
                
                setSelectedTrip(trips.get(0));
       
                generateStats();
            }
        }
    }

    public void initViolations(Date start, Date end)
    {
        if(violationEvents.size() > 1) return;
        
        logger.debug("Get Events");
        List<Integer> vioTypes = new ArrayList<Integer>();
        vioTypes.add(EventMapper.TIWIPRO_EVENT_SPEEDING_EX3);
        vioTypes.add(EventMapper.TIWIPRO_EVENT_SEATBELT);
        vioTypes.add(EventMapper.TIWIPRO_EVENT_NOTEEVENT);

        List<Integer> idleTypes = new ArrayList<Integer>();
        idleTypes.add(EventMapper.TIWIPRO_EVENT_IDLE);

        // TODO use one list. add idle events and sort by time.  JSF to check event type for which image to use.
        violationEvents = eventDAO.getEventsForDriver(navigation.getDriver().getDriverID(), start, end, vioTypes);

        //Lookup Addresses for events
        AddressLookup lookup = new AddressLookup();
        for (Event event: violationEvents)
        {
            event.setAddressStr(lookup.getAddress(event.getLatitude(), event.getLongitude()));
        }
        
        idleEvents = new ArrayList<Event>();
        idleEvents = eventDAO.getEventsForDriver(navigation.getDriver().getDriverID(), start, end, idleTypes);
    }

    public void generateStats()
    {
        logger.debug("Generate Stats");
        milesDriven = 0D;
        totalDriveSeconds = 0;
        idleSeconds = 0;
        
        for (TripDisplay trip : trips)
        {
            milesDriven += trip.getTrip().getMileage();
       
            Long tempLong = (trip.getDurationMiliSeconds() / 1000L);
            totalDriveSeconds += tempLong.intValue();
        }
        
        // Grabbing events over date picker date range
        List<Integer> idleTypes = new ArrayList<Integer>();
        idleTypes.add(EventMapper.TIWIPRO_EVENT_IDLE);
        List<Event> tmpIdleEvents = new ArrayList<Event>();
        tmpIdleEvents = eventDAO.getEventsForDriver(
                navigation.getDriver().getDriverID(), startDate, endDate, idleTypes);        

        for (Event event : tmpIdleEvents)
        {
            idleSeconds += ((IdleEvent) event).getHighIdle();
            idleSeconds += ((IdleEvent) event).getLowIdle();
        }
    }

    // DATE PROPERTIES
    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
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

        if (showLastTenTrips == true)
        {
            int count = 0;
            for (TripDisplay trip : trips)
            {
                if (count == 10)
                    break;

                selectedTrips.add(trip); // Trips are already in reverse order. (Most recent first)
                count++;
            }

            // TODO RESET START END DATES TO MATCH THE TRIPS
        }
        else
        {
            if (selectedTrip == null)
            {
                selectedTrips.add(trips.get(0));
            }
            else
                selectedTrips.add(selectedTrip);
        }

        if (selectedTrips.size() > 0)
            initViolations(selectedTrips.get(selectedTrips.size() - 1).getTrip().getStartTime(), selectedTrips.get(0).getTrip().getEndTime());

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

    // EVENT DAO PROPERTIES
    public EventDAO getEventDAO()
    {
        return eventDAO;
    }

    public void setEventDAO(EventDAO eventDAO)
    {
        this.eventDAO = eventDAO;
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

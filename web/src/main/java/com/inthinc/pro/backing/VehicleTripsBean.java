package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.TripDisplay;
import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.IdleEvent;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.util.DateUtil;

public class VehicleTripsBean extends BaseBean
{
    private static final Logger logger            = Logger.getLogger(VehicleTripsBean.class);

    private NavigationBean      navigation;
    private VehicleDAO           vehicleDAO;
    private EventDAO            eventDAO;
    private DriverDAO           driverDAO;

    private Date                startDate         = new Date();
    private Date                endDate           = new Date();

    private Double              milesDriven       = 0.0D;
    private Integer             idleSeconds       = 0;
    private Integer             numTrips          = 0;
    private Integer             totalDriveSeconds = 0;

    private boolean             showLastTenTrips  = false;
    private boolean             showIdleMarkers   = true;
    private boolean             showWarnings      = true;

    private List<TripDisplay>   trips;
    private List<TripDisplay>   selectedTrips;
    private TripDisplay         selectedTrip;
    private Driver              selectedDriver;
    private List<Event>         violationEvents;
    private List<Event>         idleEvents;
    private TimeZone            timeZone;

    public void init()
    {
        // TODO set startDate to 7 days ago 12:00:00AM and endDate to Today 11:59:59PM and when dates change.
        
        // Set start date to 7 days ago.
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        startDate = calendar.getTime();
        
        if(navigation.getVehicle() == null)
            return;

        initTrips();
    }

    public void initTrips()
    {
        logger.debug("InitTrips");
        
        //Get Time Zone from driver
        if(navigation.getVehicle().getDriverID() != null)
        {
            Driver d = driverDAO.findByID(navigation.getVehicle().getDriverID());
            timeZone = d.getPerson().getTimeZone();
        }
        else
        {
            //Use GMT for default if no driver associated.
            timeZone = TimeZone.getTimeZone("GMT");
        }
        
        //Get Trips from back end.
        List<Trip> tempTrips = new ArrayList<Trip>();
        tempTrips = vehicleDAO.getTrips(navigation.getVehicle().getVehicleID(), startDate, endDate);

        trips = new ArrayList<TripDisplay>();
        for (Trip trip : tempTrips)
        {
            trips.add(0, new TripDisplay(trip, getTimeZone()));
        }

        numTrips = trips.size();

        selectedTrips = new ArrayList<TripDisplay>();
        if (numTrips > 0)
        {
            logger.debug(numTrips.toString() + "Trips Found.");
            
            setSelectedTrip(trips.get(0));
            
            Date vStart = selectedTrips.get(0).getTrip().getStartTime();
            Date vEnd = selectedTrips.get(0).getTrip().getEndTime();

            initViolations(vStart, vEnd);

            generateStats();
        }
    }

    public void initViolations(Date start, Date end)
    {
        logger.debug("Get Events");
        List<Integer> vioTypes = new ArrayList<Integer>();
        vioTypes.add(EventMapper.TIWIPRO_EVENT_SPEEDING_EX3);
        vioTypes.add(EventMapper.TIWIPRO_EVENT_SEATBELT);
        vioTypes.add(EventMapper.TIWIPRO_EVENT_NOTEEVENT);

        List<Integer> idleTypes = new ArrayList<Integer>();
        idleTypes.add(EventMapper.TIWIPRO_EVENT_IDLE);

        // TODO use one list. add idle events and sort by time.  JSF to check event type for which image to use.
        
        violationEvents = new ArrayList<Event>();
        violationEvents = eventDAO.getEventsForVehicle(navigation.getVehicle().getVehicleID(), start, end, vioTypes);

        //Lookup Addresses for events
        AddressLookup lookup = new AddressLookup();
        for (Event event: violationEvents)
        {
            event.setAddressStr(lookup.getAddress(event.getLatitude(), event.getLongitude()));
        }
        
        idleEvents = new ArrayList<Event>();
        idleEvents = eventDAO.getEventsForVehicle(navigation.getVehicle().getVehicleID(), start, end, idleTypes);
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

        for (Event event : idleEvents)
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
    public VehicleDAO getVehicleDAO()
    {
        return vehicleDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO)
    {
        this.vehicleDAO = vehicleDAO;
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
        selectedTrips.clear();
        selectedTrips.add(selectedTrip);
        setSelectedDriver(driverDAO.findByID(selectedTrip.getTrip().getDriverID()));
        this.showLastTenTrips = false;
        initViolations(selectedTrip.getTrip().getStartTime(), selectedTrip.getTrip().getEndTime());
    }

    public Driver getSelectedDriver()
    {
        return selectedDriver;
    }

    public void setSelectedDriver(Driver selectedDriver)
    {
        this.selectedDriver = selectedDriver;
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
        return selectedTrips;
    }

    public void setSelectedTrips(List<TripDisplay> selectedTrips)
    {
        this.selectedTrips = selectedTrips;
    }

    public TimeZone getTimeZone()
    {


        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone)
    {
        this.timeZone = timeZone;
    }

    public DriverDAO getDriverDAO()
    {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO)
    {
        this.driverDAO = driverDAO;
    }
    
    
}

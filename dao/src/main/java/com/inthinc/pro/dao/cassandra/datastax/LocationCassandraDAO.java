package com.inthinc.pro.dao.cassandra.datastax;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.Interval;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.utils.Bytes;
import com.inthinc.pro.comm.parser.attrib.Attrib;
import com.inthinc.pro.comm.parser.note.NoteParser;
import com.inthinc.pro.comm.parser.note.NoteParserFactory;
import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.LocationDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.hessian.DeviceHessianDAO;
import com.inthinc.pro.dao.hessian.DriverHessianDAO;
import com.inthinc.pro.dao.hessian.VehicleHessianDAO;
import com.inthinc.pro.dao.hessian.mapper.Mapper;
import com.inthinc.pro.dao.hessian.mapper.SimpleMapper;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.DriverStops;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.TripQuality;
import com.inthinc.pro.model.TripStatus;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.IdleEvent;

@SuppressWarnings("serial")
public class LocationCassandraDAO extends BaseCassandraDAO implements LocationDAO {
    private Mapper mapper;
    private VehicleDAO vehicleDAO;
    private DriverDAO driverDAO;
    private DeviceDAO deviceDAO;
    private EventDAO eventDAO;

	private final static String VEHICLEID = "vehicleID";
	private final static String DEVICEID = "deviceID";
	private final static String DRIVERID = "driverID";
	private final static String START_NOTE_TYPE = "startNoteType";
	private final static String END_NOTE_TYPE = "endNoteType";
	private final static String START_TIME = "startTime";
	private final static String END_TIME = "endTime";
	private final static String START_ODOMETER = "startOdometer";
	private final static String END_ODOMETER = "endOdometer";
	private final static String START_LAT = "startLat";
	private final static String START_LNG = "startLng";
	private final static String END_LAT = "endLat";
	private final static String END_LNG = "endLng";
	private final static String QUALITY = "quality";
	private final static String IDLE = "Idle";
	private final static String MILEAGE1 = "mileage1";
	private final static String MILEAGE2 = "mileage2";
	private final static String MILEAGE3 = "mileage3";
	private final static String MILEAGE4 = "mileage4";
	private final static String MILEAGE5 = "mileage5";
	private final static String MILEAGE = "mileage";
	private final static String DRIVETIME = "driveTime";
	private final static String TS_TIME = "tsTime";
	private final static String TS_DRIVETIME = "tsDriveTime";
	private final static String TS_MILEAGE = "tsMileage";
	private final static String TS_MILEAGE1 = "tsMileage1";
	private final static String TS_MILEAGE2 = "tsMileage2";
	private final static String TS_MILEAGE3 = "tsMileage3";
	private final static String TS_MILEAGE4 = "tsMileage4";
	private final static String TS_MILEAGE5 = "tsMileage5";


    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(LocationCassandraDAO.class);

    public static void main(String[] args) {
//      SiloService siloService = new SiloServiceCreator("localhost", 8092).getService();
      SiloService siloService = new SiloServiceCreator("weatherford-dbnode0.tiwii.com", 8099).getService();
      VehicleHessianDAO vehicleDAO = new VehicleHessianDAO();
      vehicleDAO.setSiloService(siloService);
      DriverHessianDAO driverDAO = new DriverHessianDAO();
      driverDAO.setSiloService(siloService);
      DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
      deviceDAO.setSiloService(siloService);
//      CassandraDB cassandraDB = new CassandraDB(true, "Inthinc Production", "note_prod", "cache_prod","schlumberger-node-b-1.tiwipro.com:9160", 1, false, false);
 
     CassandraDB cassandraDB = new CassandraDB(true, "Inthinc Production", "note_prod", "cache_prod","schlumberger-node-b-1.tiwipro.com:9160", 1, false, false);
               
      LocationCassandraDAO dao = new LocationCassandraDAO(cassandraDB);
      dao.setCassandraDB(cassandraDB);
      dao.setVehicleDAO(vehicleDAO);
      dao.setDriverDAO(driverDAO);
      dao.setDeviceDAO(deviceDAO);
      
      //      dao.eventDAO = new EventCassandraDAO();
      //Interval interval = new Interval(1399226693000L, new Date().getTime());
      //List<DriverStops> stops = dao.getStops(36315, "Nick Ison", interval);
     
//      Vehicle vehicle = vehicleDAO.findByID(59061);
   
      List<Trip> trips = dao.getTripsForVehicle(184551428, new Date(1416347634000L), new Date(1508660678000L)); 
//      LastLocation ll = dao.getLastLocationForDriver(272);
//      System.out.println("LL: " + ll);
//      dao.logTrips(trips);
      for(Trip dTrip : trips)
      {
          System.out.println("Trip: " + dTrip);
      }
      cassandraDB.close();
  }

	private final static String FETCH_VEHICLE_TRIPINDEX = "SELECT * FROM \"vehicleTripIndex\" where vehicleid=? AND startts >= ? and startts <= ?";
	private final static String FETCH_DRIVER_TRIPINDEX = "SELECT * FROM \"driverTripIndex\" where driverid=? AND startts >= ? and startts <= ?";
	private final static String FETCH_VEHICLE_LASTTRIPINDEX = "SELECT * FROM \"vehicleTripIndex\" where vehicleid=? ORDER BY startts DESC LIMIT 1";
	private final static String FETCH_DRIVER_LASTTRIPINDEX = "SELECT * FROM \"driverTripIndex\" where driverid=? ORDER BY startts DESC LIMIT 1";
	private final static String FETCH_TRIP = "SELECT * FROM \"trip\" where tripid=?";
	private final static String SELECT_VEHICLE_BREADCRUMBS = "SELECT vehicleid, time, method, driverid, value FROM \"vehicleBreadCrumb60\" WHERE vehicleid=? AND time>=? AND time<=? ORDER BY time";
	private final static String SELECT_DRIVER_BREADCRUMBS = "SELECT driverid, time, method, vehicleid, value FROM \"driverBreadCrumb60\" WHERE driverid=? AND time>=? AND time<=? ORDER BY time";
	private final static String SELECT_VEHICLE_NOTES = "SELECT vehicleid, time, type, method, driverid, noteid, satcomm, value FROM \"vehicleNoteTimeTypeIndex60\" WHERE vehicleid=? AND time>=? AND time<=? ORDER BY time";

	private final static String SELECT_VEHICLE_LAST_BREADCRUMBS = "SELECT vehicleid, time, method, driverid, value FROM \"vehicleBreadCrumb60\" WHERE vehicleid=? AND time <= ? ORDER BY time DESC LIMIT 20";
	private final static String SELECT_DRIVER_LAST_BREADCRUMBS = "SELECT driverid, time, method, vehicleid, value FROM \"driverBreadCrumb60\" WHERE driverid=? AND time <= ? ORDER BY time DESC LIMIT 20";
	private final static String SELECT_VEHICLE_LAST_NOTES = "SELECT vehicleid, time, method, driverid, value FROM \"vehicleNoteTimeTypeIndex60\" WHERE vehicleid=? ORDER BY time DESC LIMIT 20";
	private final static String SELECT_DRIVER_LAST_NOTES = "SELECT driverid, time, method, vehicleid, value FROM \"driverNoteTimeTypeIndex60\" WHERE driverid=? ORDER BY time DESC LIMIT 20";

	private final static String SELECT_START_CURRENT_TRIP_NOTE = "SELECT column1, column2, column3, column4, value FROM \"startTripCache\" WHERE key=1 AND column1=? LIMIT 1";

	private static PreparedStatement fetchVehicleTripStatement = null; 
	private static PreparedStatement fetchDriverTripStatement = null; 
	private static PreparedStatement fetchVehicleLastTripStatement = null; 
	private static PreparedStatement fetchDriverLastTripStatement = null; 
	private static PreparedStatement fetchTripStatement = null; 
	private static PreparedStatement selectVehicleBCStatement = null; 
	private static PreparedStatement selectDriverBCStatement = null; 
	private static PreparedStatement selectVehicleBCLastStatement = null; 
	private static PreparedStatement selectDriverBCLastStatement = null; 
	private static PreparedStatement selectVehicleNoteLastStatement = null; 
	private static PreparedStatement selectDriverNoteLastStatement = null; 
	private static PreparedStatement selectStartTripCurrentCacheStatement = null;
	private static PreparedStatement selectVehicleNotesStatement = null; 
	



    public LocationCassandraDAO(CassandraDB cassandraDB) {
    	setCassandraDB(cassandraDB);
        mapper = new SimpleMapper();
        
		fetchVehicleTripStatement = getSession().prepare(FETCH_VEHICLE_TRIPINDEX);
		fetchVehicleTripStatement.setConsistencyLevel(getCL());

		fetchVehicleLastTripStatement = getSession().prepare(FETCH_VEHICLE_LASTTRIPINDEX);
		fetchVehicleLastTripStatement.setConsistencyLevel(getCL());

		fetchDriverTripStatement = getSession().prepare(FETCH_DRIVER_TRIPINDEX);
		fetchDriverTripStatement.setConsistencyLevel(getCL());

		fetchDriverLastTripStatement = getSession().prepare(FETCH_DRIVER_LASTTRIPINDEX);
		fetchDriverLastTripStatement.setConsistencyLevel(getCL());

		fetchTripStatement = getSession().prepare(FETCH_TRIP);
		fetchTripStatement.setConsistencyLevel(getCL());
		
		selectVehicleBCStatement = getSession().prepare(SELECT_VEHICLE_BREADCRUMBS);
		selectVehicleBCStatement.setConsistencyLevel(getCL());
		selectDriverBCStatement = getSession().prepare(SELECT_DRIVER_BREADCRUMBS);
		selectDriverBCStatement.setConsistencyLevel(getCL());
		selectVehicleNotesStatement = getSession().prepare(SELECT_VEHICLE_NOTES);
		selectVehicleNotesStatement.setConsistencyLevel(getCL());

		selectVehicleBCLastStatement = getSession().prepare(SELECT_VEHICLE_LAST_BREADCRUMBS);
		selectVehicleBCLastStatement.setConsistencyLevel(getCL());
		selectDriverBCLastStatement = getSession().prepare(SELECT_DRIVER_LAST_BREADCRUMBS);
		selectDriverBCLastStatement.setConsistencyLevel(getCL());
		
		selectVehicleNoteLastStatement = getSession().prepare(SELECT_VEHICLE_LAST_NOTES);
		selectVehicleNoteLastStatement.setConsistencyLevel(getCL());
		selectDriverNoteLastStatement = getSession().prepare(SELECT_DRIVER_LAST_NOTES);
		selectDriverNoteLastStatement.setConsistencyLevel(getCL());

		selectStartTripCurrentCacheStatement = getCacheSession().prepare(SELECT_START_CURRENT_TRIP_NOTE);
		selectStartTripCurrentCacheStatement.setConsistencyLevel(getCL());
}

    @Override
    public VehicleDAO getVehicleDAO() {
        return vehicleDAO;
    }

    @Override
    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    @Override
    public DriverDAO getDriverDAO() {
        return driverDAO;
    }

    @Override
    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    @Override
    public DeviceDAO getDeviceDAO() {
        return deviceDAO; 
    }

    @Override
    public void setDeviceDAO(DeviceDAO deviceDAO) {
        this.deviceDAO = deviceDAO;
    }

    @Override
    public EventDAO getEventDAO() {
        return eventDAO;
    }

    @Override
    public void setEventDAO(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }


    /**
     * Optimal get last trips for vehicles.
     *
     * @param vehicleList vehicle list
     * @return vehicle trips
     */
    public List<Trip> getLastTripsForVehicles(List<Vehicle> vehicleList) {
        if (vehicleList == null)
            return null;

        List<Trip> tripList = new ArrayList<Trip>();
        for (Vehicle vehicle : vehicleList) {
        	Trip trip = getLastTripForVehicle(vehicle.getVehicleID());
        	
        }
        
        tripList = cleanAndRemoveVehicleIdDuplicates(tripList);
        Collections.sort(tripList, new LatestTimeTripComparator());
        return tripList;
    }

    /**
     * Removes duplicate trips per vehicle id.
     * Removes trips with no vehicle id.
     *
     * @param tripList trip list
     * @return cleaned list
     */
    private List<Trip> cleanAndRemoveVehicleIdDuplicates(List<Trip> tripList){
        List<Trip> returnList = new ArrayList<Trip>(tripList.size());
        Map<Integer, Trip> map = new HashMap<Integer, Trip>();

        for (Trip trip: tripList){
            if (trip.getTripID()!=null) {
                if (map.containsKey(trip.getVehicleID())) {
                    Trip existing = map.get(trip.getVehicleID());
                    if (existing.getStartTime().before(trip.getStartTime()))
                        map.put(trip.getVehicleID(), trip);
                }else{
                    map.put(trip.getVehicleID(), trip);
                }
            }
        }
        returnList.addAll(map.values());
        return returnList;
    }


    @Override
    public Trip getLastTripForVehicle(Integer vehicleID) {
        Trip trip = fetchCurrentTripForAsset(vehicleID, false);
        if (trip == null) {
            trip = fetchLastCompletedTripForAsset(vehicleID, false);
        }
        return trip;
    }

    @Override
    public List<Trip> getTripsForVehicle(Integer vehicleID, Date startDate, Date endDate) {
        logger.debug("LocationCassandraDAO getTripsForVehicle() vehicleID = " + vehicleID + " startDate: " + startDate + " endDate: " + endDate);
        return fetchTripsForAsset(vehicleID, (int) DateUtil.convertDateToSeconds(startDate), (int) DateUtil.convertDateToSeconds(endDate), false, false);
    }

    @Override
    public Trip getLastTripForDriver(Integer driverID) {
        Trip trip = null;
        try {
            logger.debug("LocationCassandraDAO getLastTripForDriver() driverID2 = " + driverID);
            trip = fetchCurrentTripForAsset(driverID, true);
            if (trip == null) {
                trip = fetchLastCompletedTripForAsset(driverID, true);
            }
        } catch (Throwable e) {
            System.out.println("Exception: " + e);
            logger.error("Exception: " + e);
        }
        logger.debug("LocationCassandraDAO getLastTripForDriver() trip = " + trip);
        return trip;
    }

    @Override
    public List<Trip> getTripsForDriver(Integer driverID, Date startDate, Date endDate) {
        logger.debug("LocationCassandraDAO getTripsForDriver() driverID = " + driverID);
        return fetchTripsForAsset(driverID, (int) DateUtil.convertDateToSeconds(startDate), (int) DateUtil.convertDateToSeconds(endDate), true, false);
    }

    @Override
    public List<Trip> getTripsForDriver(Integer driverID, Date startDate, Date endDate, Boolean includeRoute) {
        logger.debug("LocationCassandraDAO getTripsForDriver() driverID = " + driverID);
        return fetchTripsForAsset(driverID, (int) DateUtil.convertDateToSeconds(startDate), (int) DateUtil.convertDateToSeconds(endDate), true, includeRoute);
    }

    @Override
    public LastLocation getLastLocationForDriver(Integer driverID) {
        logger.debug("LocationCassandraDAO getLastLocationForDriver() driverID = " + driverID);
        return fetchLastLocationForAsset(driverID, true);
    }

    @Override
    public List<DriverLocation> getDriverLocations(Integer groupID) {
        logger.debug("LocationCassandraDAO getDriverLocations() groupID = " + groupID);
        List<Driver> driverList = driverDAO.getAllDrivers(groupID);
        List<DriverLocation> driverLocationList = new ArrayList<DriverLocation>();
        for (Driver driver : driverList) {
            LastLocation lastLocation = getLastLocationForDriver(driver.getDriverID());
            // Vehicle vehicle = vehicleDAO.findByID(lastLocation.getDriverID());
            Vehicle vehicle = vehicleDAO.findByDriverID(driver.getDriverID());

            DriverLocation driverLocation = new DriverLocation(lastLocation, driver, vehicle);
            driverLocationList.add(driverLocation);
        }
        return driverLocationList;
    }

    @Override
    public LastLocation getLastLocationForVehicle(Integer vehicleID) {
        logger.debug("LocationCassandraDAO getLastLocationForVehicle() vehicleID = " + vehicleID);
        return fetchLastLocationForAsset(vehicleID, false);
    }

    @Override
    public List<DriverStops> getStops(Integer driverID, String driverName, Interval interval) {
        // return locationDAO.getStops(driverID, driverName, interval);
        logger.debug("LocationCassandraDAO getStops() driverID = " + driverID);
        final int TIME_BUFFER_SECONDS = 60;
        Date start = interval.getStart().toDateTime().toDate();
        Date end = interval.getEnd().toDateTime().toDate();

        List<DriverStops> stopsList = new ArrayList<DriverStops>();
        List<Trip> tripList = fetchTripsForAsset(driverID, (int) DateUtil.convertDateToSeconds(start), (int) DateUtil.convertDateToSeconds(end), true, false);
        Collections.reverse(tripList);

        DriverStops stop = null;
        Map<Integer, String> vehicleNameMap = new HashMap<Integer, String>();
        for (Trip trip : tripList) {

            String vehicleName = vehicleNameMap.get(trip.getVehicleID());
            if (vehicleName == null) {
                Vehicle vehicle = vehicleDAO.findByID(trip.getVehicleID());
                vehicleName = vehicle.getName();
                vehicleNameMap.put(trip.getVehicleID(), vehicleName);
            }

            if (trip.getEndTime() != null) {
                // Only looking at trips that ended.
                if (stop == null) {
                    //Initial departure for day
                    stop = new DriverStops();
                    stop.setDriverID(trip.getDriverID());
                    stop.setDriverName(driverName);
                    stop.setVehicleID(trip.getVehicleID());
                    stop.setVehicleName(vehicleName);
                    stop.setDepartTime(DateUtil.convertDateToSeconds(trip.getStartTime()));
                    stop.setIdleHi(0);
                    stop.setIdleLo(0);
                    stop.setLat(trip.getStartLat());
                    stop.setLng(trip.getStartLng());
                    stopsList.add(stop);

                    stop = createStop(driverName, vehicleName, trip, true);
                } else {
                    if (trip.getVehicleID().intValue() == stop.getVehicleID().intValue()) {
                        // We've got two trips in a row for this driver with same vehicle,
                        // so a stop
                        stop.setVehicleName(vehicleName);
                        stop.setDepartTime(DateUtil.convertDateToSeconds(trip.getStartTime()));
                        stopsList.add(stop);

                        if (trip.getStatus() != TripStatus.TRIP_IN_PROGRESS)
                            stop = createStop(driverName, vehicleName, trip, true);
                        else
                        {
                            stop = null;
                            break;
                        }
                    } else {
                        // Vehicle for driver changes between trips. Throw out current stop and start new one.
                        stop = createStop(driverName, vehicleName, trip, false);
                    }
                }
            }
        }

        //If current stop, add
        if (stop != null && stop.getArriveTime() != null && stop.getDepartTime() == null)
            stopsList.add(stop);

        // We are grabbing the idle notes and checking to see if they border an existing stop. If so we
        // add its time to that stop, otherwise we create a new stop for the idle event.
        List<DriverStops> idleStopsList = new ArrayList<DriverStops>();
        List<com.inthinc.pro.model.event.NoteType> noteTypeList = new ArrayList<com.inthinc.pro.model.event.NoteType>();
        noteTypeList.add(com.inthinc.pro.model.event.NoteType.IDLE);
        noteTypeList.add(com.inthinc.pro.model.event.NoteType.IDLE_STATS);
        List<Event> idleEventsList = eventDAO.getEventsForDriver(driverID, start, end, noteTypeList, 1);
        for (Event event : idleEventsList) {
            if (event instanceof IdleEvent) {
                Integer idleHi = ((IdleEvent) event).getHighIdle();
                if (idleHi == null)
                    idleHi=0;
                Integer idleLo = ((IdleEvent) event).getLowIdle();
                if (idleLo == null)
                    idleLo=0;

                boolean idleIsStop = true;
                for (DriverStops s : stopsList) {
                    if (s.getArriveTime() != null) {
                        if (DateUtil.convertDateToSeconds(event.getTime()) < s.getArriveTime() && (s.getArriveTime() - DateUtil.convertDateToSeconds(event.getTime())) <= TIME_BUFFER_SECONDS
                                && isSamePosition(event.getLatitude(), event.getLongitude(), s.getLat(), s.getLng())) {
                            idleIsStop = false;
                            // We have an idle event right before a start trip. Make it the some stop
                            s.setIdleHi(s.getIdleHi() + idleHi);
                            s.setIdleLo(s.getIdleLo() + idleLo);

                            // Set the arrivalTime to the idle noteTime - its idle time
                            s.setArriveTime(DateUtil.convertDateToSeconds(event.getTime()) - (s.getIdleHi() + s.getIdleLo()));
                            break;
                        } else if (s.getDepartTime() != null && DateUtil.convertDateToSeconds(event.getTime()) > s.getDepartTime()
                                && (DateUtil.convertDateToSeconds(event.getTime()) - ((idleHi + idleLo)) - s.getDepartTime() <= TIME_BUFFER_SECONDS)
                                && isSamePosition(event.getLatitude(), event.getLongitude(), s.getLat(), s.getLng())) {
                            idleIsStop = false;
                            // We have an idle event right after the stop. make it the same stop,
                            s.setIdleHi(s.getIdleHi() + idleHi);
                            s.setIdleLo(s.getIdleLo() + idleLo);
                            s.setDepartTime(DateUtil.convertDateToSeconds(event.getTime()));
                            break;
                        }
                    }
                }

                //We have idles that don't back up against a stop.
                if (idleIsStop) {
                    DriverStops s = new DriverStops();

                    Vehicle vehicle = vehicleDAO.findByID(event.getVehicleID());
                    s.setVehicleID(event.getVehicleID());
                    s.setVehicleName(vehicle.getName());
                    // Idle time note is its own stop
                    // Set the arrivalTime to the idle noteTime - its idle time
                    s.setArriveTime(DateUtil.convertDateToSeconds(event.getTime()) - (((IdleEvent) event).getHighIdle() + ((IdleEvent) event).getLowIdle()));
                    s.setDepartTime(DateUtil.convertDateToSeconds(event.getTime()));
                    s.setIdleHi(((IdleEvent) event).getHighIdle());
                    s.setIdleLo(((IdleEvent) event).getLowIdle());
                    s.setDriverID(driverID);
                    s.setDriverName(driverName);
                    s.setLat(event.getLatitude());
                    s.setLng(event.getLongitude());
                    idleStopsList.add(s);
                }
            }
        }
        stopsList.addAll(idleStopsList);

        //If we had some idle time events, the stop arrive/depart times
        //have changed, or new stops added.  Need to adjust drive time
        if (idleEventsList.size() > 0) {
            adjustStopDriveTimes(stopsList);
        }

        logger.debug("stopsList.size(): " + stopsList.size());

        try {
            for (DriverStops s : stopsList) {
                s.dump();
            }
        } catch (Throwable e) {
            logger.debug("Exception: " + e);

        }

        return stopsList;
    }

    private DriverStops createStop(String driverName, String vehicleName, Trip trip, boolean setDriveTime) {
        DriverStops stop = new DriverStops();
        stop.setDriveTime(0L);
        stop.setDriverID(trip.getDriverID());
        stop.setDriverName(driverName);
        stop.setVehicleName(vehicleName);
        stop.setVehicleID(trip.getVehicleID());
        stop.setLat(trip.getEndLat());
        stop.setLng(trip.getEndLng());
        stop.setArriveTime(DateUtil.convertDateToSeconds(trip.getEndTime()));
        stop.setIdleHi(0);
        stop.setIdleLo(0);
        if (setDriveTime) {
            long driveTime = DateUtil.convertDateToSeconds(trip.getEndTime())-DateUtil.convertDateToSeconds(trip.getStartTime());
            if (driveTime > 0)
                stop.setDriveTime(driveTime);
        }
        return stop;
    }

    private void adjustStopDriveTimes(List<DriverStops> stopsList) {
        Collections.sort(stopsList, new EarliestTimeStopComparator());

        Long departTime = null;
        for (DriverStops stop : stopsList) {
            if (departTime != null) {
                long driveTime = stop.getArriveTime() - departTime;
                stop.setDriveTime(driveTime > 0 ? driveTime : 0L);
            }

            departTime = stop.getDepartTime();
        }
    }


    private boolean isSamePosition(Double lat1, Double lng1, Double lat2, Double lng2) {
        boolean isSamePos = true;
        Double deltaLat = Math.abs(lat1 - lat2);
        Double deltaLng = Math.abs(lng1 - lng2);
        if (Math.max(deltaLat, deltaLng) > 0.001)
            isSamePos = false;

        return isSamePos;
    }

    private Trip fetchLastCompletedTripForAsset(Integer assetId, boolean isDriver) {
        Trip trip = null;

		List<Long> rowKeysList = new ArrayList<Long>();

		BoundStatement boundStatement = new BoundStatement((isDriver) ? fetchDriverLastTripStatement : fetchVehicleLastTripStatement);

		ResultSet results = getSession().execute(boundStatement.bind(assetId));
		for (Row row : results) {
			rowKeysList.add(row.getLong("tripid"));
		}

        List<Trip> tripList = fetchTrips(rowKeysList, assetId, isDriver, true);

        if (tripList != null && tripList.size() > 0)
            trip = tripList.get(0);

        return trip;
    }

    private List<Trip> fetchTripsForAsset(Integer assetId, Integer startTime, Integer endTime, boolean isDriver, boolean includeRoute) {
        List<Long> rowKeysList = fetchTripsForAssetFromIndex(assetId, startTime, endTime, isDriver);
        List<Trip> tripList = fetchTrips(rowKeysList, assetId, isDriver, includeRoute);
        Trip currentTrip = fetchCurrentTripForAsset(assetId, isDriver);
       if (currentTrip != null && DateUtil.convertDateToSeconds(currentTrip.getStartTime()) <= endTime)
            tripList.add(0, currentTrip);

        return tripList;
    }

    private List<Long> fetchTripsForAssetFromIndex(Integer id, Integer startTS, Integer endTS, boolean isDriver) {
        final int SECONDS_IN_DAY = 86400;

		List<Long> rowKeysList = new ArrayList<Long>();

		BoundStatement boundStatement = new BoundStatement((isDriver) ? fetchDriverTripStatement : fetchVehicleTripStatement);

		ResultSet results = getSession().execute(boundStatement.bind(id, BigInteger.valueOf(startTS.intValue() - SECONDS_IN_DAY), BigInteger.valueOf(endTS.intValue() + SECONDS_IN_DAY)));
		for (Row row : results) {
			rowKeysList.add(row.getLong("tripid"));
		}

		return rowKeysList;
       }



    private List<Trip> fetchTrips(List<Long> tripidList, Integer assetID, boolean isDriver, boolean includeRoute) {
        List<Trip> tripList = new ArrayList<Trip>();
 
		BoundStatement boundStatement = new BoundStatement(fetchTripStatement);

		for (Long tripid : tripidList) {
			ResultSet results = getSession().execute(boundStatement.bind(tripid));
			Map<String, Object> fieldMap = new HashMap<String, Object>();
			for (Row row : results) {
				fieldMap.put(row.getString("column1"), new Integer(row.getVarint("value").intValue()));
			}
			Trip trip = new Trip();
			trip.setTripID(tripid);
			trip.setDriverID(getFieldAsInteger(fieldMap, DRIVERID));
			trip.setVehicleID(getFieldAsInteger(fieldMap, VEHICLEID));
			trip.setStartTime(DateUtil.convertTimeInSecondsToDate(getFieldAsInteger(fieldMap, START_TIME).intValue()));
			trip.setStartLat(convertInt2Double(getFieldAsInteger(fieldMap, START_LAT)));
			trip.setStartLng(convertInt2Double(getFieldAsInteger(fieldMap, START_LNG)));
			trip.setEndTime(DateUtil.convertTimeInSecondsToDate(getFieldAsInteger(fieldMap, END_TIME).intValue()));
			trip.setEndLat(convertInt2Double(getFieldAsInteger(fieldMap, END_LAT)));
			trip.setEndLng(convertInt2Double(getFieldAsInteger(fieldMap, END_LNG)));
			trip.setQuality(TripQuality.valueOf(getFieldAsInteger(fieldMap, QUALITY)));
			trip.setMileage(getFieldAsInteger(fieldMap, MILEAGE));
            if (trip.getMileage() == null)
                trip.setMileage(0);

            trip.setStatus(TripStatus.TRIP_COMPLETED);

			logger.info("trip: " + trip);
			if (trip.getMileage() == null)
				trip.setMileage(0);

            List<LatLng> routeList = new ArrayList<LatLng>();

            LatLng startLoc = new LatLng(trip.getStartLat(), trip.getStartLng());
            startLoc.setTime(trip.getStartTime());
            LatLng endLoc = new LatLng(trip.getEndLat(), trip.getEndLng());
            endLoc.setTime(trip.getEndTime());

			if (isValidLocation(startLoc))
				routeList.add(startLoc);
            if (includeRoute || !isValidLocation(startLoc) || !isValidLocation(endLoc)) {
                routeList.addAll(fetchRouteForTrip(assetID, (int) DateUtil.convertDateToSeconds(trip.getStartTime()), (int) DateUtil.convertDateToSeconds(trip.getEndTime()), isDriver));
            }
			if (isValidLocation(endLoc))
				routeList.add(endLoc);

            trip.setRoute(routeList);
            trip.setFullRouteLoaded(includeRoute || !isValidLocation(startLoc) || !isValidLocation(endLoc));

            tripList.add(trip);
        }
        Collections.sort(tripList, new LatestTimeTripComparator());

        return tripList;
    }


    private Trip fetchCurrentTripForAsset(Integer id, boolean isDriver) {
        Trip trip = null;
        Vehicle vehicle = null;

        if (vehicleDAO != null) {
            if (isDriver)
                vehicle = vehicleDAO.findByDriverID(id);
            else
                vehicle = vehicleDAO.findByID(id);
        }

        logger.debug("fetchCurrentTripForAsset vehicle: " + vehicle);

        if (vehicle != null) {
            trip = getTripStart(vehicle.getVehicleID());
            if (trip != null) {
                LastLocation lastLocation = getLastLocationForVehicle(vehicle.getVehicleID());
                if (lastLocation == null) {
                	LatLng latLng = new LatLng(trip.getStartLat(), trip.getStartLng());
                	lastLocation = new LastLocation();
                	lastLocation.setLoc(latLng);
                	lastLocation.setOdometer(trip.getMileage());
                }
                if (isDriver)
                    trip.setDriverID(id);
                else
                    trip.setDriverID(vehicle.getDriverID());

                trip.setStatus(TripStatus.TRIP_IN_PROGRESS);
                trip.setQuality(TripQuality.UNKNOWN);
                Device device = deviceDAO.findByID(vehicle.getDeviceID());

                if (device.isAbsoluteOdometer()){
                    int startOdometer = trip.getMileage();
                    int endOdometer = lastLocation.getOdometer();
                    trip.setMileage((endOdometer-startOdometer) > 0 ? (endOdometer-startOdometer) : 0);
                }
                else  {
                    int mileage = sumMiles(vehicle.getVehicleID(), trip.getStartTime(), new Date());
                    trip.setMileage(mileage);
                }

                trip.setEndLat(lastLocation.getLoc().getLat());
                trip.setEndLng(lastLocation.getLoc().getLng());
                trip.setEndTime(new Date());
                trip.setRoute(fetchRouteForTrip(id, (int) DateUtil.convertDateToSeconds(trip.getStartTime()), (int) DateUtil.convertDateToSeconds(trip.getEndTime()), isDriver));
            }
        }

        logger.debug("fetchCurrentTripForAsset trip: " + ((trip == null) ? "null" : trip));

        return trip;
    }

    public List<LatLng> getLocationsForDriverTrip(Integer driverID, Date startTime, Date endTime) {
        return fetchRouteForTrip(driverID, (int) DateUtil.convertDateToSeconds(startTime), (int) DateUtil.convertDateToSeconds(endTime), true);
    }

    public List<LatLng> getLocationsForVehicleTrip(Integer vehicleID, Date startTime, Date endTime) {
        return fetchRouteForTrip(vehicleID, (int) DateUtil.convertDateToSeconds(startTime), (int) DateUtil.convertDateToSeconds(endTime), false);
    }


    private List<LatLng> fetchRouteForTrip(Integer id, Integer startTS, Integer endTS, boolean isDriver) {
        logger.debug("fetchRouteForTrip ID: " + id + " startTime: " + startTS + " endTime: " + endTS);
        List<LatLng> locationList = new ArrayList<LatLng>();

		BoundStatement boundStatement = new BoundStatement((isDriver) ? selectDriverBCStatement : selectVehicleBCStatement);
		ResultSet results = getSession().execute(boundStatement.bind(id, BigInteger.valueOf(startTS), BigInteger.valueOf(endTS)));
		for (Row row : results) {
			String method = row.getString("method");
			byte[] data = Bytes.getArray(row.getBytes("value"));
			NoteParser parser = NoteParserFactory.getParserForMethod(method);
			Map<String, Object> fieldMap = parser.parseNote(data);
            LatLng location = mapper.convertToModelObject(fieldMap, LatLng.class);
            location.setHeading(0);
			if (isValidLocation(location))
				locationList.add(location);
		}
	
        return locationList;
    }


    private LastLocation fetchLastLocationForAsset(Integer id, boolean isDriver) {

        // First grab the last note for the asset that has a valid lat/lng (not stripped)
        LastLocation lastNoteLocation = fetchLastLocationNoteFromIndex(id, isDriver);
        Integer latestNoteTimestamp = (int) ((lastNoteLocation != null) ? DateUtil.convertDateToSeconds(lastNoteLocation.getTime()) : DateUtil.convertDateToSeconds(new Date()));

        LastLocation lastBCLocation = fetchLastBreadcrumb(id, latestNoteTimestamp, isDriver);

        return (lastBCLocation != null) ? lastBCLocation : lastNoteLocation;
    }

    private LastLocation fetchLastLocationNoteFromIndex(Integer id, boolean isDriver) {
        LastLocation lastLocation = null;

        logger.debug("fetchLastLocationNoteFromIndex ID: " + id  + " isDriver: " + isDriver);

		BoundStatement boundStatement = new BoundStatement((isDriver) ? selectDriverNoteLastStatement : selectVehicleNoteLastStatement);
		ResultSet results = getSession().execute(boundStatement.bind(id));
		for (Row row : results) {
			String method = row.getString("method");
			byte[] data = Bytes.getArray(row.getBytes("value"));
			NoteParser parser = NoteParserFactory.getParserForMethod(method);
			Map<String, Object> fieldMap = parser.parseNote(data);
            LatLng location = mapper.convertToModelObject(fieldMap, LatLng.class);
    		if (isValidLocation(location)) {
                lastLocation = mapper.convertToModelObject(fieldMap, LastLocation.class);
                if (isDriver) {
        			int driverid = row.getVarint("driverid").intValue();
                    lastLocation.setDriverID(driverid);
                    lastLocation.setVehicleID(id);
                } else {
        			int vehicleid = row.getVarint("vehicleid").intValue();
                    lastLocation.setVehicleID(vehicleid);
                    lastLocation.setDriverID(id);
                }
                break;
    		}	
		}
        return lastLocation;
    }

    private LastLocation fetchLastBreadcrumb(Integer id, Integer lastNoteTime, boolean isDriver) {
        LastLocation lastLocation = null;

        logger.debug("fetchLastBreadcrumb ID: " + id + " lastNoteTime: " + lastNoteTime + " isDriver: " + isDriver);

		BoundStatement boundStatement = new BoundStatement((isDriver) ? selectDriverBCLastStatement : selectVehicleBCLastStatement);
		ResultSet results = getSession().execute(boundStatement.bind(id, BigInteger.valueOf(lastNoteTime)));
		for (Row row : results) {
			String method = row.getString("method");
			byte[] data = Bytes.getArray(row.getBytes("value"));
			NoteParser parser = NoteParserFactory.getParserForMethod(method);
			Map<String, Object> fieldMap = parser.parseNote(data);
            LatLng location = mapper.convertToModelObject(fieldMap, LatLng.class);
    		if (isValidLocation(location)) {
                lastLocation = mapper.convertToModelObject(fieldMap, LastLocation.class);
                if (isDriver) {
        			int driverid = row.getVarint("driverid").intValue();
                    lastLocation.setDriverID(driverid);
                    lastLocation.setVehicleID(id);
                } else {
        			int vehicleid = row.getVarint("vehicleid").intValue();
                    lastLocation.setVehicleID(vehicleid);
                    lastLocation.setDriverID(id);
                }
                break;
    		}	
		}
        return lastLocation;
    }    

 
    private Trip getTripStart(Integer vehicleID)
    {
        Trip trip = null;
        
		BoundStatement boundStatement = new BoundStatement(selectStartTripCurrentCacheStatement);
		ResultSet results = getSession().execute(boundStatement.bind(BigInteger.valueOf(vehicleID)));
		for (Row row : results) {
			int vehicle = row.getVarint("column1").intValue();
			int time = row.getVarint("column2").intValue();
			int type = row.getVarint("column3").intValue();
			String method = row.getString("column4");
			byte[] data = Bytes.getArray(row.getBytes("value"));
			NoteParser parser = NoteParserFactory.getParserForMethod(method);
			Map<String, Object> fieldMap = parser.parseNote(data);
            Long startTS = ((Integer)fieldMap.get(Attrib.NOTETIME.getFieldName())) * 1000L;
            trip.setStartTime(new Date(startTS));
            trip.setStartLat(((Number) fieldMap.get(Attrib.MAXLATITUDE.getFieldName())).doubleValue());
            trip.setStartLng(((Number) fieldMap.get(Attrib.MAXLONGITUDE.getFieldName())).doubleValue());
            trip.setMileage(((Number) fieldMap.get(Attrib.NOTEODOMETER.getFieldName())).intValue());
            trip.setVehicleID(vehicleID);
		}
		return trip;
    }


    private int sumMiles(Integer vehicleID, Date startTime, Date endTime)
    {
    	Integer startTS = (int) DateUtil.convertDateToSeconds(startTime);
    	Integer endTS = (int) DateUtil.convertDateToSeconds(endTime);
    	
		BoundStatement boundStatement = new BoundStatement(selectVehicleNotesStatement);
		ResultSet results = getSession().execute(boundStatement.bind(vehicleID, BigInteger.valueOf(startTS), BigInteger.valueOf(endTS)));
        int miles = 0;
		for (Row row : results) {
			String method = row.getString("method");
			byte[] data = Bytes.getArray(row.getBytes("value"));
			NoteParser parser = NoteParserFactory.getParserForMethod(method);
			Map<String, Object> fieldMap = parser.parseNote(data);
            miles += ((Number) fieldMap.get(Attrib.NOTEODOMETER.getFieldName())).intValue();
		}


		boundStatement = new BoundStatement(selectVehicleBCStatement);
		results = getSession().execute(boundStatement.bind(vehicleID, BigInteger.valueOf(startTS), BigInteger.valueOf(endTS)));
		for (Row row : results) {
			String method = row.getString("method");
			byte[] data = Bytes.getArray(row.getBytes("value"));
			NoteParser parser = NoteParserFactory.getParserForMethod(method);
			Map<String, Object> fieldMap = parser.parseNote(data);
            miles += ((Number) fieldMap.get(Attrib.NOTEODOMETER.getFieldName())).intValue();
		}

        return miles;
    }


    private class LatestTimeTripComparator implements Comparator<Trip> {
        @Override
        public int compare(Trip t1, Trip t2) {
            return t2.getStartTime().compareTo(t1.getStartTime());
        }
    }

    private class EarliestTimeStopComparator implements Comparator<DriverStops> {
        @Override
        public int compare(DriverStops s1, DriverStops s2) {
            Long arrive1 = (s1.getArriveTime() == null) ? 0 : s1.getArriveTime();
            Long arrive2 = (s2.getArriveTime() == null) ? 0 : s2.getArriveTime();
            return arrive1.compareTo(arrive2);
        }
    }

    private Double convertInt2Double(int intVal) {
        return intVal / 10000D;
    }

	private boolean isValidLocation(LatLng latLng) {
		return !(Math.floor(Math.abs(latLng.getLatitude())) + Math.floor(Math.abs(latLng.getLongitude())) == 0);
	}
	
	private int convertDouble2Int(double dVal) {
		return (int) (Math.round(dVal * 10000));
	}


	private Integer getFieldAsInteger(Map<String, Object> fieldMap, String name) {
		Integer val = (Integer) fieldMap.get(name);
		if (val == null)
			val = new Integer(0);

		return val;
	}

}

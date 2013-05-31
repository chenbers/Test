package com.inthinc.pro.dao.cassandra;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.*;

import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.Composite;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.beans.Rows;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.MultigetSliceQuery;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.SliceQuery;

import org.apache.log4j.Logger;
import org.joda.time.Interval;

import com.inthinc.pro.comm.parser.attrib.Attrib;
import com.inthinc.pro.comm.parser.note.NoteParser;
import com.inthinc.pro.comm.parser.note.NoteParserFactory;
import com.inthinc.pro.comm.parser.note.NoteType;
import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.LocationDAO;
import com.inthinc.pro.dao.VehicleDAO;
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
public class LocationCassandraDAO extends GenericCassandraDAO implements LocationDAO {
    private Mapper mapper;
    private VehicleDAO vehicleDAO;
    private DriverDAO driverDAO;
    private DeviceDAO deviceDAO;
    private EventDAO eventDAO;

    private final static String VEHICLEID = "vehicleId";
    private final static String DEVICEID = "deviceId";
    private final static String DRIVERID = "driverId";
    private final static String START_NOTE_TYPE = "startNoteType";
    private final static String END_NOTE_TYPE = "endNoteType";
    private final static String START_TIME = "startTime";
    private final static String END_TIME = "endTime";
    private final static String START_ODOMETER = "startOdometer";
    private final static String END_ODOMETER = "endOdometer";
    private final static String MILEAGE = "mileage";
    private final static String MILEAGE1 = "mileage1";
    private final static String MILEAGE2 = "mileage2";
    private final static String MILEAGE3 = "mileage3";
    private final static String MILEAGE4 = "mileage4";
    private final static String MILEAGE5 = "mileage5";
    private final static String HIGH_IDLE = "highIdle";
    private final static String LOW_IDLE = "lowIdle";
    private final static String START_LAT = "startLat";
    private final static String START_LNG = "startLng";
    private final static String END_LAT = "endLat";
    private final static String END_LNG = "endLng";

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(LocationCassandraDAO.class);

    public static void main(String[] args) {
//        SiloService siloService = new SiloServiceCreator("localhost", 8092).getService();
        SiloService siloService = new SiloServiceCreator("tp-web10.tiwipro.com", 8099).getService();
        VehicleHessianDAO vehicleDAO = new VehicleHessianDAO();
        vehicleDAO.setSiloService(siloService);
        DriverHessianDAO driverDAO = new DriverHessianDAO();
        driverDAO.setSiloService(siloService);
        CassandraDB cassandraDB = new CassandraDB(true, "Inthinc Production", "note_prod", "cache_prod","chevron-node4.tiwipro.com:9160", 10, false);
        LocationCassandraDAO dao = new LocationCassandraDAO();
        dao.setCassandraDB(cassandraDB);
        dao.setVehicleDAO(vehicleDAO);
        dao.setDriverDAO(driverDAO);

/*        LastLocation ll = dao.getLastLocationForVehicle(19417);
        System.out.println("Location: " + ll);
        ll = dao.getLastLocationForDriver(37505);
        System.out.println("Location: " + ll);
*/
//        List<Trip> trips = dao.getTripsForDriver(14372, new Date(1362355200000L), new Date()); // List<DriverLocation> driverLocations = getDriverLocations(Integer groupID)

/*        List<Trip> trips = dao.getTripsForVehicle(8452, new Date(0), new Date()); 
        dao.logTrips(trips);
        for(Trip dTrip : trips)
        {
            System.out.println("Trip: " + dTrip);
        }
*/        

        List<LatLng> route = dao.fetchRouteForTrip(20079, 0,1363957581, false);        
//        System.out.println("route: " + route.size());
//            Trip dTrip = dao.getLastTripForVehicle(21130);
//        System.out.println("Trip: " + dTrip);

        /*
        Trip trip = dao.getLastTripForDriver(37505); 
        System.out.println("Trip: " + trip);
         * 53422 Trip trip = dao.getLastTripForDriver(20821); System.out.println("Trip: " + trip);
         * 
         * LastLocation ll = dao.getLastLocationForDriver(20821); System.out.println("Location: " + ll);
         * 
         * 
         * ll = dao.getLastLocationForDriver(66462); System.out.println("Location: " + ll);
         * 
         * Trip trip = dao.getLastTripForVehicle(52721); System.out.println("Trip: " + trip);
         * 
         * List<Trip> trips = dao.getTripsForVehicle(52721, new Date(0), new Date()); dao.logTrips(trips);
         * 
         */

        dao.shutdown();
    }

    private void logTrips(List<Trip> trips) {
        for (Trip t : trips) {
            logger.info("Trip: " + t);
            System.out.println("Trip: " + t);
        }
        System.out.println("**************************************************");
    }

    public LocationCassandraDAO() {
        mapper = new SimpleMapper();
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
        logger.debug("LocationCassandraDAO getTripsForVehicle() vehicleID = " + vehicleID);
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
        return fetchLastLocationForAsset(driverNoteTimeTypeIndex_CF, driverBreadCrumb60_CF, driverID, true);
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
        return fetchLastLocationForAsset(vehicleNoteTimeTypeIndex_CF, vehicleBreadCrumb60_CF, vehicleID, false);
    }

    @Override
    public List<DriverStops> getStops(Integer driverID, String driverName, Interval interval) {
        // return locationDAO.getStops(driverID, driverName, interval);
        logger.debug("LocationCassandraDAO getStops() driverID = " + driverID);
        final int TIME_BUFFER_SECONDS = 60;
        Date start = interval.getStart().toDateTime().toDate();
        Date end = interval.getEnd().toDateTime().toDate();

        List<DriverStops> stopsList = new ArrayList<DriverStops>();
        // List<Trip> tripList = getTripsForDriver(driverID, start, end);
        List<Trip> tripList = fetchTripsForAsset(driverID, (int) DateUtil.convertDateToSeconds(start), (int) DateUtil.convertDateToSeconds(end), true, false);
        Collections.reverse(tripList);

        boolean beginStop = true;
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

                    beginStop = false;
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
                        
                        
                        if (DateUtil.convertDateToSeconds(event.getTime()) < s.getArriveTime() && (s.getArriveTime() - DateUtil.convertDateToSeconds(event.getTime())) < TIME_BUFFER_SECONDS
                                && isSamePosition(event.getLatitude(), event.getLongitude(), s.getLat(), s.getLng())) {
                            idleIsStop = false;
                            // We have an idle event right before a start trip. Make it the some stop
                            s.setIdleHi(s.getIdleHi() + idleHi);
                            s.setIdleLo(s.getIdleLo() + idleLo);
    
                            // Set the arrivalTime to the idle noteTime - its idle time
                            s.setArriveTime(DateUtil.convertDateToSeconds(event.getTime()) - (s.getIdleHi() + s.getIdleLo()));
                            break;
                        } else if (s.getDepartTime() != null && DateUtil.convertDateToSeconds(event.getTime()) > s.getDepartTime()
//                                && (DateUtil.convertDateToSeconds(event.getTime()) - ((((IdleEvent) event).getHighIdle() + ((IdleEvent) event).getLowIdle())) - s.getDepartTime() < TIME_BUFFER_SECONDS)
                                && (DateUtil.convertDateToSeconds(event.getTime()) - ((idleHi + idleLo)) - s.getDepartTime() < TIME_BUFFER_SECONDS)
                                && isSamePosition(event.getLatitude(), event.getLongitude(), s.getLat(), s.getLng())) {
                            idleIsStop = false;
                            // We have an idle event right after the stop. make it the same stop,
                            s.setIdleHi(s.getIdleHi() + idleHi);
                            s.setIdleLo(s.getIdleLo() + idleLo);
                            s.setDepartTime(DateUtil.convertDateToSeconds(event.getTime()));
                            break;
                        }
                    }
                    // Math.abs(event.getLatitude() - trip.getEndLat());
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
        stop.setDriverID(trip.getDriverID());
        stop.setDriverName(driverName);
        stop.setVehicleName(vehicleName);
        stop.setVehicleID(trip.getVehicleID());
        stop.setLat(trip.getEndLat());
        stop.setLng(trip.getEndLng());
        stop.setArriveTime(DateUtil.convertDateToSeconds(trip.getEndTime()));
        stop.setIdleHi(0);
        stop.setIdleLo(0);
        if (setDriveTime)
            stop.setDriveTime(DateUtil.convertDateToSeconds(trip.getEndTime())-DateUtil.convertDateToSeconds(trip.getStartTime()));
        return stop;
    }
    
    private void adjustStopDriveTimes(List<DriverStops> stopsList) {
        Collections.sort(stopsList, new EarliestTimeStopComparator());
        
        Long departTime = null;
        for (DriverStops stop : stopsList) {
            if (departTime != null) {
                stop.setDriveTime(stop.getArriveTime() - departTime);
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
        SliceQuery<Integer, Composite, Long> sliceQuery = HFactory.createSliceQuery(getKeyspace(), integerSerializer, compositeSerializer, longSerializer);
        sliceQuery.setColumnFamily((isDriver) ? driverTripIndex_CF : vehicleTripIndex_CF);
        sliceQuery.setRange(null, null, true, 1);
        sliceQuery.setKey(assetId);

        QueryResult<ColumnSlice<Composite, Long>> result = sliceQuery.execute();
        ColumnSlice<Composite, Long> columnSlice = result.get();

        List<Long> rowKeysList = new ArrayList<Long>();
        List<HColumn<Composite, Long>> columnList = columnSlice.getColumns();
        for (HColumn<Composite, Long> column : columnList) {
            rowKeysList.add(column.getValue());
        }

        
        List<Trip> tripList = fetchTrips(rowKeysList, assetId, isDriver, true);
        if (tripList != null && tripList.size() > 0)
            trip = tripList.get(0);

        return trip;
    }

    private List<Trip> fetchTripsForAsset(Integer assetId, Integer startTime, Integer endTime, boolean isDriver, boolean includeRoute) {
        List<Long> rowKeysList = fetchTripsForAssetFromIndex(assetId, startTime, endTime, isDriver);
        List<Trip> tripList = fetchTrips(rowKeysList, assetId, isDriver, includeRoute);
        Trip trip = fetchCurrentTripForAsset(assetId, isDriver);
        if (trip != null)
            tripList.add(0, trip);

        return tripList;
    }

    private List<Long> fetchTripsForAssetFromIndex(Integer id, Integer startTime, Integer endTime, boolean isDriver) {
        final int SECONDS_IN_DAY = 86400;

        Composite startRange = new Composite();
        startRange.add(0, startTime - SECONDS_IN_DAY); // Grab from 1 day earlier so we can see if ends in requested period
        startRange.add(1, startTime);

        Composite endRange = new Composite();
        endRange.add(0, endTime);
        endRange.add(1, endTime + SECONDS_IN_DAY);

        SliceQuery<Integer, Composite, Long> sliceQuery = HFactory.createSliceQuery(getKeyspace(), integerSerializer, compositeSerializer, longSerializer);
        sliceQuery.setColumnFamily((isDriver) ? driverTripIndex_CF : vehicleTripIndex_CF);
        sliceQuery.setRange(startRange, endRange, false, 1000);
        sliceQuery.setKey(id);

        QueryResult<ColumnSlice<Composite, Long>> result = sliceQuery.execute();
        ColumnSlice<Composite, Long> columnSlice = result.get();

        List<Long> rowKeysList = new ArrayList<Long>();
        List<HColumn<Composite, Long>> columnList = columnSlice.getColumns();
        for (HColumn<Composite, Long> column : columnList) {
            Integer colStartTime = bigIntegerSerializer.fromByteBuffer((ByteBuffer) column.getName().get(0)).intValue();
            Integer colEndTime = bigIntegerSerializer.fromByteBuffer((ByteBuffer) column.getName().get(1)).intValue();

            // Make sure trip within date range
            if ((colStartTime >= startTime && colStartTime <= endTime) || (colEndTime >= startTime && colEndTime <= endTime))
                rowKeysList.add(column.getValue());
        }
        return rowKeysList;
    }

    
    
    private List<Trip> fetchTrips(List<Long> rowKeyList, Integer assetID, boolean isDriver, boolean includeRoute) {
        List<Trip> tripList = new ArrayList<Trip>();
        MultigetSliceQuery<Long, String, Integer> sliceQuery = HFactory.createMultigetSliceQuery(getKeyspace(), longSerializer, stringSerializer, integerSerializer);

        sliceQuery.setColumnFamily(trip_CF);
        sliceQuery.setRange("!", "~", false, 1000); // get all the columns
        sliceQuery.setKeys(rowKeyList);

        QueryResult<Rows<Long, String, Integer>> result = sliceQuery.execute();

        Rows<Long, String, Integer> rows = result.get();
        for (Row<Long, String, Integer> row : rows) {
            ColumnSlice<String, Integer> columnSlice = row.getColumnSlice();
            List<HColumn<String, Integer>> columnList = columnSlice.getColumns();

            Map<String, Object> fieldMap = new HashMap<String, Object>();
            int mileage = 0;
            if (columnList.size() > 0) {
                for (HColumn<String, Integer> column : columnList) {
  //                  if (column.getName().startsWith(MILEAGE))
  //                      mileage += column.getValue();

                    if (column.getName().startsWith(START_LAT) || column.getName().startsWith(START_LNG) || column.getName().startsWith(END_LAT) || column.getName().startsWith(END_LNG))
                        fieldMap.put(column.getName(), convertInt2Double(column.getValue()));
                    else
                        fieldMap.put(column.getName(), column.getValue());
                }
//                fieldMap.put(MILEAGE, mileage);

                logger.debug("tripFieldMap = " + fieldMap);
                Trip trip = mapper.convertToModelObject(fieldMap, Trip.class);
                logger.debug("trip: " + trip);
                if (trip.getMileage() == null)
                    trip.setMileage(0);
                
                trip.setStatus(TripStatus.TRIP_COMPLETED);

                // logger.debug("LocationCassandraDAO fetchTrips() trip = " + trip);

                List<LatLng> routeList = new ArrayList<LatLng>();  
                
                LatLng startLoc = new LatLng(trip.getStartLat(), trip.getStartLng());
                startLoc.setTime(trip.getStartTime());
                routeList.add(startLoc);
                LatLng endLoc = new LatLng(trip.getEndLat(), trip.getEndLng());
                endLoc.setTime(trip.getEndTime());
                if (includeRoute) {
                    routeList.addAll(fetchRouteForTrip(assetID, (int) DateUtil.convertDateToSeconds(trip.getStartTime()), (int) DateUtil.convertDateToSeconds(trip.getEndTime()), isDriver));
                }    
                routeList.add(endLoc);

                //attempt to remove any points from the route that are near 0,0
                Iterator<LatLng> iter = routeList.iterator();
                while(iter.hasNext()){
                    LatLng latLng = iter.next();
                    if (Math.floor(Math.abs(latLng.getLatitude())) + Math.floor(Math.abs(latLng.getLongitude())) == 0) {
                        iter.remove();
                    }
                }

                trip.setRoute(routeList);
                trip.setFullRouteLoaded(includeRoute);
                
                tripList.add(trip);
            }
        }

        Collections.sort(tripList, new LatestTimeTripComparator());

        return tripList;
    }


    private Trip fetchCurrentTripForAsset(Integer id, boolean isDriver) {
        Trip trip = null;
        Vehicle vehicle = null;
        if (isDriver)
            vehicle = vehicleDAO.findByDriverID(id);
        else
            vehicle = vehicleDAO.findByID(id);

        logger.debug("fetchCurrentTripForAsset vehicle: " + vehicle);

        if (vehicle != null) {
            trip = getTripStart(vehicle.getVehicleID());
            if (trip != null) {
                LastLocation lastLocation = getLastLocationForVehicle(vehicle.getVehicleID());
                if (isDriver)
                    trip.setDriverID(id);
                else
                    trip.setDriverID(vehicle.getDriverID());
                
                trip.setStatus(TripStatus.TRIP_IN_PROGRESS);
                trip.setQuality(TripQuality.UNKNOWN);
                Device device = deviceDAO.findByID(vehicle.getDeviceID());
                
                if (device.isWaySmart()){
                    int startOdometer = trip.getMileage();
                    int endOdometer = lastLocation.getOdometer();
                    trip.setMileage(endOdometer-startOdometer);
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

        
    private List<LatLng> fetchRouteForTrip(Integer id, Integer startTime, Integer endTime, boolean isDriver) {
        logger.debug("fetchRouteForTrip ID: " + id + " startTime: " + startTime + " endTime: " + endTime);
        List<LatLng> locationList = new ArrayList<LatLng>();

        SliceQuery<Integer, Composite, byte[]> sliceQuery = HFactory.createSliceQuery(getKeyspace(), integerSerializer, compositeSerializer, bytesArraySerializer);

        Composite startRange = new Composite();
        // startRange.add(0, startTime);
        startRange.add(0, startTime);

        startRange.add(1, Integer.MIN_VALUE);
        Composite endRange = new Composite();
        // endRange.add(0, endTime); //Current Timestamp
        endRange.add(0, endTime); // Current Timestamp

        endRange.add(1, Integer.MAX_VALUE);

        sliceQuery.setRange(startRange, endRange, true, 10000);

        sliceQuery.setColumnFamily((isDriver) ? driverBreadCrumb60_CF : vehicleBreadCrumb60_CF);

        sliceQuery.setKey(id);
        // rangeSlicesQuery.setReturnKeysOnly();

        QueryResult<ColumnSlice<Composite, byte[]>> result = sliceQuery.execute();
        ColumnSlice<Composite, byte[]> columnSlice = result.get();

        Composite columnKey = null;
        byte[] raw = null;
        List<HColumn<Composite, byte[]>> columnList = columnSlice.getColumns();
        for (HColumn<Composite, byte[]> column : columnList) {
            columnKey = column.getName();
            String method = stringSerializer.fromByteBuffer((ByteBuffer) columnKey.get(2));
            raw = column.getValue();

            NoteParser parser = NoteParserFactory.getParserForMethod(method);
            Map<String, Object> fieldMap = parser.parseNote(raw);
            LatLng location = mapper.convertToModelObject(fieldMap, LatLng.class);
            location.setHeading(0);
//            logger.info("fieldMap: " + fieldMap);
//            logger.info("Trip LatLng: " + location);
            
//            log(fieldMap);
            locationList.add(location);
        }

        return locationList;
    }

    private void log(Map<String, Object> fieldMap){
        
//        System.out.println(fieldMap);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Integer timeDS = ((Number)fieldMap.get(Attrib.NOTETIME.getFieldName())).intValue();
        Integer odometer = ((Number)fieldMap.get(Attrib.NOTEODOMETER.getFieldName())).intValue();
        Date time = new Date(timeDS*1000L);
        
        System.out.println("UPDATE note20 set odometer=" + odometer + " WHERE deviceID=43316 AND time='" + dateFormat.format(time) + "';");
    } 
    
    private LastLocation fetchLastLocationForAsset(String index_cf, String breadcrumb_cf, Integer id, boolean isDriver) {

        LastLocation lastLocation = null;
        Integer latestLocationTimestamp = 0;
        Integer latestNoteTypeCode = 0;
        Long noteId = null;
        Integer assetId = null;

        // First grab the last note for the asset that has a valid lat/lng (not stripped)
        Map<Composite, Long> noteMap = fetchLastLocationNoteFromIndex(index_cf, id);

        for (Map.Entry<Composite, Long> entry : noteMap.entrySet()) {
            Composite noteTimeType = entry.getKey();

            latestLocationTimestamp = bigIntegerSerializer.fromByteBuffer((ByteBuffer) noteTimeType.get(0)).intValue();
            latestNoteTypeCode = bigIntegerSerializer.fromByteBuffer((ByteBuffer) noteTimeType.get(1)).intValue();
//            assetId = bigIntegerSerializer.fromByteBuffer((ByteBuffer) noteTimeType.get(3)).intValue();
            noteId = entry.getValue();
            break;
        }

        // if the last note for this asset isn't an endtrip, let's see if we more recent breadcrumbs
        if (!NoteType.isTripEndNoteType(NoteType.get(latestNoteTypeCode))) {
            lastLocation = fetchLastBreadcrumb(breadcrumb_cf, id, latestLocationTimestamp, isDriver);
        }

        if (lastLocation == null) {
            // we still don't have a location, so grab it from the note CF.
            if (noteId != null) {
                lastLocation = fetchLastLocationFromNote(noteId);
//                lastLocation.setDriverID(isDriver ? id : assetId);
//                lastLocation.setVehicleID((!isDriver) ? id : assetId);
            }    
        }
        return lastLocation;
    }

/*    private Map<Composite, Composite> fetchLastLocationNoteFromIndex(String INDEX_CF, Integer id) {
        final int MAX_COLS = 10;

        SliceQuery<Composite, Composite, Composite> sliceQuery = HFactory.createSliceQuery(getKeyspace(), compositeSerializer, compositeSerializer, compositeSerializer);

        sliceQuery.setRange(null, null, false, MAX_COLS);

        sliceQuery.setColumnFamily(INDEX_CF);

        Composite rowKeyComp = new Composite();
        rowKeyComp.add(0, id);
        sliceQuery.setKey(rowKeyComp);
        // rangeSlicesQuery.setReturnKeysOnly();

        QueryResult<ColumnSlice<Composite, Composite>> result = sliceQuery.execute();
        ColumnSlice<Composite, Composite> columnSlice = result.get();

        Map<Composite, Composite> keyMap = new HashMap<Composite, Composite>();
        List<HColumn<Composite, Composite>> columnList = columnSlice.getColumns();
        for (HColumn<Composite, Composite> column : columnList) {
            Composite columnName = column.getName();
            // Integer noteTime = (Integer) columnName.get(0);
            // Integer noteType = (Integer) columnName.get(1);

            int noteType = bigIntegerSerializer.fromByteBuffer((ByteBuffer) columnName.get(1)).intValue();

            if (!NoteType.isStrippedNote(noteType)) {
                keyMap.put(column.getName(), column.getValue());
                break;
            }
        }
        return keyMap;
    }
*/
    
    private Map<Composite, Long> fetchLastLocationNoteFromIndex(String INDEX_CF, Integer id) {
        final int MAX_COLS = 10;

        SliceQuery<Integer, Composite, Long> sliceQuery = HFactory.createSliceQuery(getKeyspace(), integerSerializer, compositeSerializer, longSerializer);

        sliceQuery.setRange(null, null, false, MAX_COLS);

        sliceQuery.setColumnFamily(INDEX_CF);

//        Composite rowKeyComp = new Composite();
 //       rowKeyComp.add(0, id);
        sliceQuery.setKey(id);
        // rangeSlicesQuery.setReturnKeysOnly();

        QueryResult<ColumnSlice<Composite, Long>> result = sliceQuery.execute();
        ColumnSlice<Composite, Long> columnSlice = result.get();

        Map<Composite, Long> keyMap = new HashMap<Composite, Long>();
        List<HColumn<Composite, Long>> columnList = columnSlice.getColumns();
        for (HColumn<Composite, Long> column : columnList) {
            Composite columnName = column.getName();
            // Integer noteTime = (Integer) columnName.get(0);
            // Integer noteType = (Integer) columnName.get(1);

            int noteType = bigIntegerSerializer.fromByteBuffer((ByteBuffer) columnName.get(1)).intValue();

            if (!NoteType.isStrippedNote(noteType)) {
                keyMap.put(column.getName(), column.getValue());
                break;
            }
        }
        return keyMap;
    }

    private LastLocation fetchLastBreadcrumb(String BREADCRUMB_CF, Integer id, Integer lastNoteTime, boolean isDriver) {
        LastLocation lastLocation = null;
        SliceQuery<Integer, Composite, byte[]> sliceQuery = HFactory.createSliceQuery(getKeyspace(), integerSerializer, compositeSerializer, bytesArraySerializer);

        int currrentTimeStamp = (int) DateUtil.convertDateToSeconds(new Date());

        if (lastNoteTime > currrentTimeStamp)
            currrentTimeStamp = lastNoteTime;

        logger.debug("ID: " + id + " isDriver: " + isDriver + " lastNoteTime: " + lastNoteTime + " currrentTimeStamp: " + currrentTimeStamp);

        Composite startRange = new Composite();
        startRange.add(0, new Integer(currrentTimeStamp)); // Current Timestamp
        startRange.add(1, Integer.MIN_VALUE);
        Composite endRange = new Composite();
        endRange.add(0, lastNoteTime);
        endRange.add(1, Integer.MAX_VALUE);

        sliceQuery.setRange(startRange, endRange, false, 1);

        sliceQuery.setColumnFamily(BREADCRUMB_CF);

        sliceQuery.setKey(id);
        // rangeSlicesQuery.setReturnKeysOnly();

        QueryResult<ColumnSlice<Composite, byte[]>> result = sliceQuery.execute();
        ColumnSlice<Composite, byte[]> columnSlice = result.get();

        Composite columnKey = null;
        byte[] raw = null;
        List<HColumn<Composite, byte[]>> columnList = columnSlice.getColumns();
        for (HColumn<Composite, byte[]> column : columnList) {
            columnKey = column.getName();
            raw = column.getValue();
            break;
        }

        if (columnKey != null) {
            String method = stringSerializer.fromByteBuffer((ByteBuffer) columnKey.get(2));
            NoteParser parser = NoteParserFactory.getParserForMethod(method);
            Map<String, Object> fieldMap = parser.parseNote(raw);
            lastLocation = mapper.convertToModelObject(fieldMap, LastLocation.class);

            if (isDriver) {
                lastLocation.setDriverID(id);
                int vehicleId = bigIntegerSerializer.fromByteBuffer((ByteBuffer) columnKey.get(1)).intValue();
                lastLocation.setVehicleID(vehicleId);
            } else {
                lastLocation.setVehicleID(id);
                int driverId = bigIntegerSerializer.fromByteBuffer((ByteBuffer) columnKey.get(1)).intValue();

                lastLocation.setDriverID(driverId);
            }
        }
        return lastLocation;
    }

    private LastLocation fetchLastLocationFromNote(Long key) {
        SliceQuery<Long, String, byte[]> sliceQuery = HFactory.createSliceQuery(getKeyspace(), longSerializer, stringSerializer, bytesArraySerializer);

        sliceQuery.setColumnFamily(note_CF);
        sliceQuery.setRange("", "", false, 10); // get all the columns

        sliceQuery.setKey(key);

        QueryResult<ColumnSlice<String, byte[]>> result = sliceQuery.execute();

        ColumnSlice<String, byte[]> cs = result.get();
        List<HColumn<String, byte[]>> columnList = cs.getColumns();

        byte[] raw = null;
        String method = "";
        int driverId = 0;
        int vehicleId = 0;
        for (HColumn<String, byte[]> column : columnList) {
            if (column.getName().equalsIgnoreCase(RAW_COL))
                raw = column.getValue();
            else if (column.getName().equalsIgnoreCase(METHOD_COL))
                method = new String(column.getValue());
            else if (column.getName().equalsIgnoreCase(DRIVERID_COL))
                driverId = bigIntegerSerializer.fromBytes(column.getValue()).intValue();
            else if (column.getName().equalsIgnoreCase(VEHICLEID_COL))
                vehicleId = bigIntegerSerializer.fromBytes(column.getValue()).intValue();
        }

        NoteParser parser = NoteParserFactory.getParserForMethod(method);
        Map<String, Object> fieldMap = parser.parseNote(raw);

        LastLocation lastLocation = mapper.convertToModelObject(fieldMap, LastLocation.class);
        lastLocation.setDriverID(driverId);
        lastLocation.setVehicleID(vehicleId);

        return lastLocation;
    }

    private Trip getTripStart(Integer vehicleID)
    {
        Trip trip = null;
        Date currentTime = new Date();
        int startTime = 0;
        int endTime = (int) (currentTime.getTime()/1000);
        Composite startColName = new Composite();
        startColName.add(0, vehicleID);
        startColName.add(1, endTime);
        startColName.add(2, Integer.MIN_VALUE);
        startColName.add(3, MIN_STRING);

        Composite endColName = new Composite();
        endColName.add(0, vehicleID);
        endColName.add(1, startTime);
        endColName.add(2, Integer.MAX_VALUE);
        endColName.add(3, MAX_STRING);

        SliceQuery<Integer, Composite, byte[]> sliceQuery = HFactory.createSliceQuery(getCacheKeyspace(), integerSerializer, compositeSerializer, bytesArraySerializer);

        sliceQuery.setColumnFamily(startTripCache_CF);
        sliceQuery.setRange(startColName, endColName, false, 1); 
        
        sliceQuery.setKey(1);

        QueryResult<ColumnSlice<Composite, byte[]>> result = sliceQuery.execute();
        ColumnSlice<Composite, byte[]> columnSlice = result.get();            

        if (columnSlice.getColumns().size() > 0) {
            trip = new Trip();
            List<HColumn<Composite, byte[]>> columnList = columnSlice.getColumns();
            Composite colName = null;
            HColumn<Composite, byte[]> column = columnList.get(0);
            colName = column.getName();
            startTime = bigIntegerSerializer.fromByteBuffer((ByteBuffer) colName.get(1)).intValue();
            String method = stringSerializer.fromByteBuffer((ByteBuffer) colName.get(3));
            NoteParser parser = NoteParserFactory.getParserForMethod(method);
            Map<String, Object> fieldMap = parser.parseNote(column.getValue());
            Long startTS = ((Integer)fieldMap.get(Attrib.NOTETIME.getFieldName())) * 1000L;
            trip.setStartTime(new Date(startTS));
            trip.setStartLat(((Number) fieldMap.get(Attrib.MAXLATITUDE.getFieldName())).doubleValue());
            trip.setStartLng(((Number) fieldMap.get(Attrib.MAXLONGITUDE.getFieldName())).doubleValue());
            trip.setMileage(((Number) fieldMap.get(Attrib.NOTEODOMETER.getFieldName())).intValue());
            trip.setVehicleID(vehicleID);
        }
        return trip;
    }    

    
    private int sumMiles(Integer vehicleID, Date startTime, Date endTime) {
        return sumMiles(vehicleID, (int)(startTime.getTime()/1000), (int)(endTime.getTime()/1000));
    }

    private int sumMiles(Integer vehicleID, Integer startTime, Integer endTime){
        int miles = 0;
        miles += sumMiles(vehicleID, startTime, endTime, vehicleNoteTimeTypeIndex60_CF);
        miles += sumMiles(vehicleID, startTime, endTime, vehicleBreadCrumb60_CF);
        return miles;
    }

    
    private int sumMiles(Integer vehicleID, Integer startTime, Integer endTime, String CF)
    {
        SliceQuery<Integer, Composite, byte[]> sliceQuery = HFactory.createSliceQuery(getKeyspace(), integerSerializer, compositeSerializer, bytesArraySerializer);
        Composite startRange = new Composite();
        startRange.add(0, endTime);
        startRange.add(1, Integer.MIN_VALUE);
        startRange.add(2, MIN_STRING);

        Composite endRange = new Composite();
        endRange.add(0, startTime); // Current Timestamp
        endRange.add(1, Integer.MAX_VALUE);
        endRange.add(2, MAX_STRING);

        sliceQuery.setRange(startRange, endRange, false, 10000);
        sliceQuery.setColumnFamily(CF);
        sliceQuery.setKey(vehicleID);

        QueryResult<ColumnSlice<Composite, byte[]>> result = sliceQuery.execute();
        ColumnSlice<Composite, byte[]> columnSlice = result.get();

        Composite columnKey = null;
        byte[] raw = null;
        List<HColumn<Composite, byte[]>> columnList = columnSlice.getColumns();
        int miles = 0;
        for (HColumn<Composite, byte[]> column : columnList) {
            columnKey = column.getName();
            String method = stringSerializer.fromByteBuffer((ByteBuffer) columnKey.get(2));
            raw = column.getValue();
            NoteParser parser = NoteParserFactory.getParserForMethod(method);
            Map<String, Object> fieldMap = parser.parseNote(raw);
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

}

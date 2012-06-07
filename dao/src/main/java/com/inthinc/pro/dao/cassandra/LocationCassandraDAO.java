package com.inthinc.pro.dao.cassandra;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/////Used for Main test only///////////////
//import com.inthinc.pro.dao.cassandra.EventCassandraDAO.LatestTimeEventComparator;
import com.inthinc.pro.dao.hessian.DriverHessianDAO;
import com.inthinc.pro.dao.hessian.VehicleHessianDAO;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
///////////////////////////////////////

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.LocationDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.mapper.Mapper;
import com.inthinc.pro.dao.hessian.mapper.SimpleMapper;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.event.Event;

//import com.inthinc.pro.comm.parser.note.NoteType;
//import com.inthinc.pro.comm.parser.note.NoteParser;
//import com.inthinc.pro.comm.parser.note.NoteParserFactory;
//import com.inthinc.pro.comm.parser.util.ReadUtil;
//
import me.prettyprint.cassandra.serializers.IntegerSerializer;
import me.prettyprint.hector.api.beans.AbstractComposite.Component;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.Composite;
import me.prettyprint.hector.api.beans.CounterRow;
import me.prettyprint.hector.api.beans.CounterRows;
import me.prettyprint.hector.api.beans.CounterSlice;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.HCounterColumn;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.beans.Rows;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.MutationResult;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.MultigetSliceCounterQuery;
import me.prettyprint.hector.api.query.MultigetSliceQuery;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.SliceCounterQuery;
import me.prettyprint.hector.api.query.SliceQuery;


@SuppressWarnings("serial")
public class LocationCassandraDAO extends GenericCassandraDAO implements LocationDAO
{
    private Mapper mapper;
    private VehicleDAO vehicleDAO;
    private DriverDAO driverDAO;

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
	
    
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(LocationCassandraDAO.class);

    
    public static void main(String[] args)
    {
/*
		if(args.length == 0)
		{
			System.out.println("Properties File was not passed In");
			System.exit(0);
		}
	
		
		String filePath = (String) args[0];
		String startTime = (String) args[1];
		String endTime = (String) args[2];
*/		
/*    	
        SiloService siloService = new SiloServiceCreator("localhost", 8092).getService();
        VehicleHessianDAO vehicleDAO = new VehicleHessianDAO();
        vehicleDAO.setSiloService(siloService);
        DriverHessianDAO driverDAO = new DriverHessianDAO();
        driverDAO.setSiloService(siloService);
    	CassandraDB cassandraDB = new CassandraDB("Iridium Archive", "note", "localhost:9160", 10, false);
    	LocationCassandraDAO dao = new LocationCassandraDAO();
    	dao.setCassandraDB(cassandraDB);
    	dao.setVehicleDAO(vehicleDAO);
    	dao.setDriverDAO(driverDAO);
    	
    	LastLocation ll = dao.getLastLocationForVehicle(52721);
		System.out.println("Location: " + ll);
    	
    	ll = dao.getLastLocationForDriver(66462);
		System.out.println("Location: " + ll);

		Trip trip =  dao.getLastTripForVehicle(52721);
		System.out.println("Trip: " + trip);
		
	    trip = dao.getLastTripForDriver(66462);
		System.out.println("Trip: " + trip);
    	
    	dao.shutdown();
*/    	
    }
  /*  
    private void logLocation(List<LastLocation> locations)
    {
    	for (LastLocation location : locations)
    	{
    		logger.info("Location: " + location);
    		System.out.println("Location: " + location);
    	}
    	System.out.println("**************************************************");
    }

    public LocationCassandraDAO()
    {
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
    public Trip getLastTripForVehicle(Integer vehicleID) {
    	Trip trip = fetchCurrentTripForAsset(vehicleID, false);
    	if (trip == null)
    	{
    		trip = fetchLastCompletedTripForAsset(vehicleID, false);
    	}
    	return trip;
    }

    @Override
    public List<Trip> getTripsForVehicle(Integer vehicleID, Date startDate, Date endDate) {
        logger.debug("getTrips() vehicleID = " + vehicleID);
        return fetchTripsForAsset(vehicleID, (int)DateUtil.convertDateToSeconds(startDate), (int)DateUtil.convertDateToSeconds(endDate), false);
    }
	
    @Override
    public Trip getLastTripForDriver(Integer driverID) {
    	Trip trip = fetchCurrentTripForAsset(driverID, true);
    	if (trip == null)
    	{
    		trip = fetchLastCompletedTripForAsset(driverID, true);
    	}
    	return trip;
    }

    @Override
    public List<Trip> getTripsForDriver(Integer driverID, Date startDate, Date endDate) {
        logger.debug("getTrips() driverID = " + driverID);
        return fetchTripsForAsset(driverID, (int)DateUtil.convertDateToSeconds(startDate), (int)DateUtil.convertDateToSeconds(endDate), true);
    }
	
	@Override
    public LastLocation getLastLocationForDriver(Integer driverID)
    {
    	return fetchLastLocationForAsset(driverNoteTimeTypeIndex_CF, driverBreadCrumb60_CF, driverID, true);
    }

	@Override
	public List<DriverLocation> getDriverLocations(Integer groupID) {		
		List<Driver> driverList = driverDAO.getAllDrivers(groupID);
        List<DriverLocation> driverLocationList = new ArrayList<DriverLocation>();
        for (Driver driver : driverList)
        {
        	LastLocation lastLocation = getLastLocationForDriver(driver.getDriverID());
            Vehicle vehicle = vehicleDAO.findByID(lastLocation.getDriverID());
        			
        	DriverLocation driverLocation = new DriverLocation(lastLocation, driver, vehicle);		
        	driverLocationList.add(driverLocation);		
        }
        return driverLocationList;
	}

    @Override
    public LastLocation getLastLocationForVehicle(Integer vehicleID) {
    	return fetchLastLocationForAsset(vehicleNoteTimeTypeIndex_CF, vehicleBreadCrumb60_CF, vehicleID, false);
    }


    private Trip fetchLastCompletedTripForAsset(Integer assetId, boolean isDriver)
    {
    	Trip trip = null;
    	SliceQuery<Integer, Composite, Composite> sliceQuery = HFactory.createSliceQuery(getKeyspace(), integerSerializer, compositeSerializer, compositeSerializer);
        sliceQuery.setColumnFamily((isDriver) ? driverTripIndex_CF : vehicleTripIndex_CF );
        sliceQuery.setRange(null, null, false, 1);
        sliceQuery.setKey(assetId);
		
        QueryResult<ColumnSlice<Composite, Composite>> result = sliceQuery.execute();
        ColumnSlice<Composite, Composite> columnSlice = result.get();            
        
        List<Composite> rowKeysList = new ArrayList<Composite>();
    	List<HColumn<Composite, Composite>> columnList = columnSlice.getColumns();
    	for (HColumn<Composite, Composite> column : columnList)
        {
			 rowKeysList.add(column.getValue());
        }
    	List<Trip> tripList = fetchTrips(rowKeysList, assetId, isDriver);
    	if (tripList != null && tripList.size() > 0)
    		trip = tripList.get(0);
    	
    	return trip;
    }

    private List<Trip> fetchTripsForAsset(Integer assetId, Integer startTime, Integer endTime, boolean isDriver)
    {
        List<Composite> rowKeysList = fetchTripsForAssetFromIndex(assetId, startTime, endTime, isDriver);
        List<Trip> tripList = fetchTrips(rowKeysList, assetId, isDriver);
        Trip trip = fetchCurrentTripForAsset(assetId, isDriver, startTime, endTime);
        if (trip != null)
        	tripList.add(0, trip);
        	
        return tripList;
    }

    private List<Composite> fetchTripsForAssetFromIndex(Integer id, Integer startTime, Integer endTime, boolean isDriver)
    {
    	final int SECONDS_IN_DAY = 86400;
    	
    	Composite startRange = new Composite();
    	startRange.add(0, startTime-SECONDS_IN_DAY);  //Grab from 1 day earlier so we can see if ends in requested period
    	startRange.add(1, startTime);
    	
    	Composite endRange = new Composite();
    	endRange.add(0, endTime);  
    	endRange.add(1, endTime+SECONDS_IN_DAY);

    	SliceQuery<Integer, Composite, Composite> sliceQuery = HFactory.createSliceQuery(getKeyspace(), integerSerializer, compositeSerializer, compositeSerializer);
        sliceQuery.setColumnFamily((isDriver) ? driverTripIndex_CF : vehicleTripIndex_CF );
        sliceQuery.setRange(startRange, endRange, false, 1000);
        sliceQuery.setKey(id);
		
        QueryResult<ColumnSlice<Composite, Composite>> result = sliceQuery.execute();
        ColumnSlice<Composite, Composite> columnSlice = result.get();            
        
        List<Composite> rowKeysList = new ArrayList<Composite>();
    	List<HColumn<Composite, Composite>> columnList = columnSlice.getColumns();
    	for (HColumn<Composite, Composite> column : columnList)
        {
    		//bigIntegerSerializer.fromByteBuffer((ByteBuffer) row.getKey().get(0)).intValue();
    		Integer colStartTime = (Integer) column.getName().get(0);
    		Integer colEndTime = (Integer) column.getName().get(0);
    		
    		if ((colStartTime >= startTime && colStartTime <= endTime) || (colEndTime >= startTime && colEndTime <= endTime))
    			 rowKeysList.add(column.getValue());
        }
    	return rowKeysList;
    }
    
    private List<Trip> fetchTrips(List<Composite> rowKeyList, Integer assetID, boolean isDriver)
    {
    	List <Trip> tripList = new ArrayList<Trip>();
    	MultigetSliceQuery<Composite, String, Integer> sliceQuery = HFactory.createMultigetSliceQuery(getKeyspace(), compositeSerializer, stringSerializer, integerSerializer);
        
        sliceQuery.setColumnFamily(trip_CF);            
        sliceQuery.setRange("!", "~", false, 1000);  //get all the columns
        sliceQuery.setKeys(rowKeyList);
        
        QueryResult<Rows<Composite, String, Integer>> result = sliceQuery.execute();
        
        Rows<Composite, String, Integer> rows = result.get();     
        for (Row<Composite, String, Integer> row : rows)
        {
        	ColumnSlice<String, Integer> columnSlice = row.getColumnSlice();
        	List<HColumn<String,Integer>> columnList = columnSlice.getColumns();

        	Map<String, Object> fieldMap = new HashMap<String, Object>();
        	int mileage = 0;
        	for (HColumn<String, Integer> column : columnList)
        	{
        		if (column.getName().startsWith(MILEAGE))
        			mileage += column.getValue();
        		fieldMap.put(column.getName(), column.getValue());
        	}
    		fieldMap.put(MILEAGE, mileage);
        		
            Trip trip = mapper.convertToModelObject(fieldMap, Trip.class);
        	trip.setRoute(fetchRouteForTrip(assetID, (int)DateUtil.convertDateToSeconds(trip.getStartTime()), (int)DateUtil.convertDateToSeconds(trip.getEndTime()), isDriver));

        	tripList.add(trip);
        }
    		
    	Collections.sort(tripList, new LatestTimeTripComparator());

		return tripList;
    }
    

    private Trip fetchCurrentTripForAsset(Integer id, boolean isDriver)
    {
    	return fetchCurrentTripForAsset(id, isDriver, null, null);
    }
    			
    private Trip fetchCurrentTripForAsset(Integer id, boolean isDriver, Integer startTime, Integer endTime)
    {
    	Trip trip = null;
    	Vehicle vehicle = null;
    	if (isDriver)
    		vehicle = vehicleDAO.findByDriverID(id);
    	else
    		vehicle = vehicleDAO.findByID(id);
    
    	if (vehicle != null && vehicle.getDeviceID() != null)
    	{
            SliceQuery<Integer, String, Integer> sliceQuery = HFactory.createSliceQuery(getKeyspace(), integerSerializer, stringSerializer, integerSerializer);
            sliceQuery.setColumnFamily(currentTripDescription_CF);
            sliceQuery.setRange(null, null, false, 20);
            sliceQuery.setKey(vehicle.getDeviceID());
    		
            QueryResult<ColumnSlice<String, Integer>> result = sliceQuery.execute();
            ColumnSlice<String, Integer> columnSlice = result.get();            
            
            Map<String, Object> columnMap = new HashMap<String, Object>();
        	List<HColumn<String, Integer>> columnList = columnSlice.getColumns();
        	boolean isWaysmart = false;
        	int mileage = 0;
        	int startOdometer = 0;
        	for (HColumn<String, Integer> column : columnList)
            {
            	if (START_ODOMETER.equalsIgnoreCase(column.getName()))
            	{
            		isWaysmart = true;
            		startOdometer = column.getValue();
            	}
        		columnMap.put(column.getName(), column.getValue());
            }

            if (!isWaysmart)
            {
        		SliceCounterQuery<Integer, String> cq = HFactory.createCounterSliceQuery(getKeyspace(), integerSerializer, stringSerializer);
        		cq.setColumnFamily(currentTripDescription_CF);
        		cq.setKey(vehicle.getDeviceID());
        		cq.setRange(MILEAGE, MILEAGE + "~", false, 10);
        		QueryResult<CounterSlice<String>> counterResult = cq.execute();
                CounterSlice<String> cs = counterResult.get();
                for ( HCounterColumn<String> col: cs.getColumns() ) {
                	mileage += col.getValue();
                }        
            }
            else
            {
            	LastLocation lastLocation = null;
            	if (isDriver)
            		lastLocation = getLastLocationForDriver(id);
            	else
            		lastLocation = getLastLocationForVehicle(id);
            	
            	mileage = lastLocation.getOdometer() - startOdometer;
            }
    		columnMap.put(MILEAGE, mileage);
    	    trip = mapper.convertToModelObject(columnMap, Trip.class);
    	    
    	    if (startTime != null && endTime != null)
    	    {
	    		if ((DateUtil.convertDateToSeconds(trip.getStartTime()) >= startTime && DateUtil.convertDateToSeconds(trip.getStartTime()) <= endTime) || (DateUtil.convertDateToSeconds(trip.getEndTime()) >= startTime && DateUtil.convertDateToSeconds(trip.getEndTime()) <= endTime))
	    			trip.setRoute(fetchRouteForTrip(id, (int)DateUtil.convertDateToSeconds(trip.getStartTime()), (int)DateUtil.convertDateToSeconds(trip.getEndTime()), isDriver));
	    		else
	    			trip = null;
	    	
    	    }
    	}	
    
    	return trip;	
    }
    
    private List<LatLng> fetchRouteForTrip(Integer id, Integer startTime, Integer endTime, boolean isDriver)
    {
    	List<LatLng> locationList = new ArrayList<LatLng>();
    	
    	SliceQuery<Composite, Composite, byte[]> sliceQuery = HFactory.createSliceQuery(getKeyspace(), compositeSerializer, compositeSerializer, bytesArraySerializer);
        
        Composite startRange = new Composite();
//        startRange.add(0, startTime);
        startRange.add(0, endTime);
        
        startRange.add(1, Integer.MIN_VALUE);
        Composite endRange = new Composite();
//        endRange.add(0, endTime); //Current Timestamp
        endRange.add(0, startTime); //Current Timestamp
        
        endRange.add(1, Integer.MAX_VALUE);
        
        sliceQuery.setRange(startRange, endRange, false, 10000);
        
        sliceQuery.setColumnFamily((isDriver) ? driverBreadCrumb60_CF : vehicleBreadCrumb60_CF);
        
		Composite rowKeyComp = new Composite();
		rowKeyComp.add(0, id);
        sliceQuery.setKey(rowKeyComp);
        //rangeSlicesQuery.setReturnKeysOnly();
        
        QueryResult<ColumnSlice<Composite, byte[]>> result = sliceQuery.execute();
        ColumnSlice<Composite, byte[]> columnSlice = result.get();            
        
        Composite columnKey = null;
        byte[] raw = null;
    	List<HColumn<Composite, byte[]>> columnList = columnSlice.getColumns();
        for (HColumn<Composite, byte[]> column : columnList)
        {
        	columnKey = column.getName();
        	raw = column.getValue();

			NoteParser parser = NoteParserFactory.getParserForMethod(NoteParserFactory.NOTEBC_METHOD);
		    Map<String, Object> fieldMap = parser.parseNote(raw);
		    LatLng location = mapper.convertToModelObject(fieldMap, LatLng.class);
	        
	        locationList.add(location);
        }

		return locationList;
     }

    private LastLocation fetchLastLocationForAsset(String index_cf, String breadcrumb_cf, Integer id, boolean isDriver)    {
    	
    	LastLocation lastLocation = null;
    	Integer latestLocationTimestamp = 0;
    	Integer latestNoteType = 0;
    	Composite noteId = null;
    	
    	//First grab the last note for the asset that has a valid lat/lng (not stripped)
    	Map<Composite, Composite> noteMap = fetchLastLocationNoteFromIndex(index_cf, id);
    	
    	for (Map.Entry<Composite, Composite> entry : noteMap.entrySet())
    	{
    		Composite noteTimeType = entry.getKey();

    		latestLocationTimestamp = bigIntegerSerializer.fromByteBuffer((ByteBuffer) noteTimeType.get(0)).intValue();
    		latestNoteType = bigIntegerSerializer.fromByteBuffer((ByteBuffer) noteTimeType.get(1)).intValue();
    		noteId = entry.getValue();
    		break;
    	}
    	
    	//if the last note for this asset isn't an endtrip, let's see if we more recent breadcrumbs
    	if (!NoteType.isTripEndNoteType(latestNoteType))
    	{
    		lastLocation = fetchLastBreadcrumb(breadcrumb_cf, id, latestLocationTimestamp, isDriver);
    	}
    		
    	if (lastLocation == null)
    	{
    		//we still don't have a location, so grab it from the note CF.
    		if (noteId != null)    			
    			lastLocation = fetchLastLocationFromNote(noteId);
    	}
    	return lastLocation;
    }

    
    
    private Map<Composite, Composite> fetchLastLocationNoteFromIndex(String INDEX_CF, Integer id)
    {
    	final int MAX_COLS = 10;
    	
        SliceQuery<Composite, Composite, Composite> sliceQuery = HFactory.createSliceQuery(getKeyspace(), compositeSerializer, compositeSerializer, compositeSerializer);

        sliceQuery.setRange(null, null, false, MAX_COLS);
        
        sliceQuery.setColumnFamily(INDEX_CF);
        
		Composite rowKeyComp = new Composite();
		rowKeyComp.add(0, id);
        sliceQuery.setKey(rowKeyComp);
        //rangeSlicesQuery.setReturnKeysOnly();
        
        QueryResult<ColumnSlice<Composite, Composite>> result = sliceQuery.execute();
        ColumnSlice<Composite, Composite> columnSlice = result.get();            
        
        Map<Composite, Composite> keyMap = new HashMap<Composite, Composite>();
    	List<HColumn<Composite, Composite>> columnList = columnSlice.getColumns();
        for (HColumn<Composite, Composite> column : columnList)
        {
        	Composite columnName = column.getName();
//        	Integer noteTime = (Integer) columnName.get(0);
//        	Integer noteType = (Integer) columnName.get(1);

    		int noteType = bigIntegerSerializer.fromByteBuffer((ByteBuffer) columnName.get(1)).intValue();
        	
        	if (!NoteType.isStrippedNote(noteType))
        	{
        		keyMap.put(column.getName(), column.getValue());
        		break;
        	}
        }
    	return keyMap;
     }
    
    private LastLocation fetchLastBreadcrumb(String BREADCRUMB_CF, Integer id, Integer lastNoteTime, boolean isDriver)
    {
    	LastLocation lastLocation = null;
    	SliceQuery<Composite, Composite, byte[]> sliceQuery = HFactory.createSliceQuery(getKeyspace(), compositeSerializer, compositeSerializer, bytesArraySerializer);
        
        Composite startRange = new Composite();
        startRange.add(0, lastNoteTime);
        startRange.add(1, Integer.MIN_VALUE);
        Composite endRange = new Composite();
        endRange.add(0, new Integer((int)DateUtil.convertDateToSeconds(new Date()))); //Current Timestamp
        endRange.add(1, Integer.MAX_VALUE);
        
        sliceQuery.setRange(startRange, endRange, false, 1);
        
        sliceQuery.setColumnFamily(BREADCRUMB_CF);
        
		Composite rowKeyComp = new Composite();
		rowKeyComp.add(0, id);
        sliceQuery.setKey(rowKeyComp);
        //rangeSlicesQuery.setReturnKeysOnly();
        
        QueryResult<ColumnSlice<Composite, byte[]>> result = sliceQuery.execute();
        ColumnSlice<Composite, byte[]> columnSlice = result.get();            
        
        Composite columnKey = null;
        byte[] raw = null;
    	List<HColumn<Composite, byte[]>> columnList = columnSlice.getColumns();
        for (HColumn<Composite, byte[]> column : columnList)
        {
        	columnKey = column.getName();
        	raw = column.getValue();
        	break;
        }

        if (columnKey != null)
        {
        	String method = stringSerializer.fromByteBuffer((ByteBuffer) columnKey.get(2));
   			NoteParser parser = NoteParserFactory.getParserForMethod(method);
		    Map<String, Object> fieldMap = parser.parseNote(raw);
	        lastLocation = mapper.convertToModelObject(fieldMap, LastLocation.class);
	        
	        if (isDriver)
	        {
	        	lastLocation.setDriverID(id);
	    		int vehicleId = bigIntegerSerializer.fromByteBuffer((ByteBuffer) columnKey.get(1)).intValue();
	        	lastLocation.setVehicleID(vehicleId);
	        }
	        else
	        {
	        	lastLocation.setVehicleID(id);
	    		int driverId = bigIntegerSerializer.fromByteBuffer((ByteBuffer) columnKey.get(1)).intValue();

	        	lastLocation.setDriverID(driverId);
	        }
        }
		return lastLocation;
     }

    
    private LastLocation fetchLastLocationFromNote(Composite key)
    {
    	SliceQuery<Composite, String, byte[]> sliceQuery = HFactory.createSliceQuery(getKeyspace(), compositeSerializer, stringSerializer, bytesArraySerializer);
        
        sliceQuery.setColumnFamily(note_CF);            
        sliceQuery.setRange("", "", false, 10);  //get all the columns
        
        sliceQuery.setKey(key);
        
        QueryResult<ColumnSlice<String, byte[]>> result = sliceQuery.execute();
        
        ColumnSlice<String, byte[]> cs = result.get();     
       	List<HColumn<String, byte[]>> columnList = cs.getColumns();

		int vehicleId = bigIntegerSerializer.fromByteBuffer((ByteBuffer) key.get(0)).intValue();
		
		
		byte[] raw = null;
		String method = "";
		boolean forgiven = false;
		int driverId = 0;
    	for (HColumn<String, byte[]> column : columnList)
    	{
    		if (column.getName().equalsIgnoreCase(RAW_COL))
    			raw = column.getValue();
    		else if (column.getName().equalsIgnoreCase(METHOD_COL))
    			method = new String(column.getValue());
    		else if (column.getName().equalsIgnoreCase(DRIVERID_COL))
    			driverId = bigIntegerSerializer.fromBytes(column.getValue()).intValue();
    	}
    		
		NoteParser parser = NoteParserFactory.getParserForMethod(method);
	    Map<String, Object> fieldMap = parser.parseNote(raw);
//	    fieldMap.put("driverID", new Integer(driverId));
//	    fieldMap.put("vehicleID", new Integer(vehicleId));
            
	    LastLocation lastLocation = mapper.convertToModelObject(fieldMap, LastLocation.class);
	    lastLocation.setDriverID(driverId);
	    lastLocation.setVehicleID(vehicleId);
	    
		return lastLocation;
    }
    
	private class LatestTimeTripComparator implements Comparator<Trip> {
	    @Override
	    public int compare(Trip t1, Trip t2) {
	    	return t2.getStartTime().compareTo(t1.getStartTime());
	    }
	}

*/


	@Override
	public LastLocation getLastLocationForDriver(Integer driverID) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<DriverLocation> getDriverLocations(Integer groupID) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public LastLocation getLastLocationForVehicle(Integer vehicleID) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Trip getLastTripForVehicle(Integer vehicleID) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Trip> getTripsForVehicle(Integer vehicleID, Date startDate,
			Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Trip getLastTripForDriver(Integer driverID) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Trip> getTripsForDriver(Integer driverID, Date startDate,
			Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public VehicleDAO getVehicleDAO() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setVehicleDAO(VehicleDAO vehicleDAO) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public DriverDAO getDriverDAO() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setDriverDAO(DriverDAO driverDAO) {
		// TODO Auto-generated method stub
		
	}
}



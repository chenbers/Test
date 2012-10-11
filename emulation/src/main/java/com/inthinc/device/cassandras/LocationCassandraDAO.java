package com.inthinc.device.cassandras;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.Composite;
import me.prettyprint.hector.api.beans.CounterSlice;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.HCounterColumn;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.beans.Rows;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.MultigetSliceQuery;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.SliceCounterQuery;
import me.prettyprint.hector.api.query.SliceQuery;

import com.inthinc.device.emulation.enums.ColumnAttributes;
import com.inthinc.device.emulation.enums.DeviceNoteTypes;
import com.inthinc.device.emulation.enums.NoteColumnFamily;
import com.inthinc.device.emulation.enums.UnitType;
import com.inthinc.device.emulation.interfaces.SiloService;
import com.inthinc.device.emulation.notes.DeviceNote;
import com.inthinc.device.hessian.tcp.AutomationHessianFactory;
import com.inthinc.device.models.Trip;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.models.Vehicle;
import com.inthinc.pro.automation.objects.AutomationCalendar;
import com.inthinc.pro.automation.utils.AutoServers;
import com.inthinc.pro.automation.utils.ObjectConverter;

public class LocationCassandraDAO extends GenericCassandraDAO {

    /**
     * 
     */
    private static final long serialVersionUID = 950567180207962961L;
    private final static String START_ODOMETER = "startOdometer";
    private final static String MILEAGE = "mileage";


    public static void main(String[] args) {

        CassandraDB cassandraDB = new CassandraDB("Iridium Archive", "note", "10.0.35.40:9160", 10, false);
        LocationCassandraDAO dao = new LocationCassandraDAO(new AutoServers());
        dao.setCassandraDB(cassandraDB);

        AutomationCalendar startTime = new AutomationCalendar();
        AutomationCalendar endTime = startTime.copy();
        startTime.addToDay(-1);
        

        List<Trip> trips = dao.fetchTripsForAsset(53412, startTime, endTime, UnitType.VEHICLE);
        Log.info(trips);
        trips = dao.fetchTripsForAsset(69186, startTime, endTime, UnitType.DRIVER);
        Log.info(trips);
        
        dao.shutdown();
    }

    private SiloService proxy;
    
    public LocationCassandraDAO(AutoServers server){
        this.proxy = new AutomationHessianFactory(server).getPortalProxy();
    }



    public Trip fetchLastCompletedTripForAsset(Integer assetId, UnitType type) {
        Trip trip = null;
        SliceQuery<Integer, Composite, Composite> sliceQuery = HFactory.createSliceQuery(getKeyspace(), integerSerializer, compositeSerializer, compositeSerializer);
        sliceQuery.setColumnFamily(NoteColumnFamily.TRIP_INDEX.getColumnDef(type));
        sliceQuery.setRange(null, null, false, 1);
        sliceQuery.setKey(assetId);

        QueryResult<ColumnSlice<Composite, Composite>> result = sliceQuery.execute();
        ColumnSlice<Composite, Composite> columnSlice = result.get();

        List<Composite> rowKeysList = new ArrayList<Composite>();
        List<HColumn<Composite, Composite>> columnList = columnSlice.getColumns();
        for (HColumn<Composite, Composite> column : columnList) {
            rowKeysList.add(column.getValue());
        }
        List<Trip> tripList = fetchTrips(rowKeysList, assetId, type);
        if (tripList != null && tripList.size() > 0)
            trip = tripList.get(0);

        return trip;
    }

    public List<Trip> fetchTripsForAsset(Integer assetId, AutomationCalendar startTime, AutomationCalendar endTime, UnitType type) {
        List<Composite> rowKeysList = fetchTripsForAssetFromIndex(assetId, startTime, endTime, type);
        List<Trip> tripList = fetchTrips(rowKeysList, assetId, type);
        Trip trip = fetchCurrentTripForAsset(assetId, type, startTime, endTime);
        if (trip != null && trip.getRoute().size() > 0)
            tripList.add(0, trip);

        return tripList;
    }

    private List<Composite> fetchTripsForAssetFromIndex(Integer id, AutomationCalendar startTime, AutomationCalendar endTime, UnitType type) {

        Composite startRange = new Composite();
        startRange.add(0, startTime.addToDay(-1).toInt()); // Grab from 1 day earlier so we can see if ends in requested period
        startRange.add(1, startTime.toInt());

        Composite endRange = new Composite();
        endRange.add(0, endTime.toInt());
        endRange.add(1, endTime.addToDay(1).toInt());

        SliceQuery<Integer, Composite, Composite> sliceQuery = HFactory.createSliceQuery(getKeyspace(), integerSerializer, compositeSerializer, compositeSerializer);
        String cf = NoteColumnFamily.TRIP_INDEX.getColumnDef(type);
        sliceQuery.setColumnFamily(cf);
        sliceQuery.setRange(startRange, endRange, false, 1000);
        sliceQuery.setKey(id);

        QueryResult<ColumnSlice<Composite, Composite>> result = sliceQuery.execute();
        ColumnSlice<Composite, Composite> columnSlice = result.get();

        List<Composite> rowKeysList = new ArrayList<Composite>();
        List<HColumn<Composite, Composite>> columnList = columnSlice.getColumns();
        for (HColumn<Composite, Composite> column : columnList) {
            Integer colStartTime = bigIntegerSerializer.fromByteBuffer((ByteBuffer) column.getName().get(0)).intValue();
            Integer colEndTime = bigIntegerSerializer.fromByteBuffer((ByteBuffer) column.getName().get(1)).intValue();

            // Make sure trip within date range
            if ((colStartTime >= startTime.toInt() && colStartTime <= endTime.toInt()) || 
                    (colEndTime >= startTime.toInt() && colEndTime <= endTime.toInt()))
                rowKeysList.add(column.getValue());
        }
        return rowKeysList;
    }

    private List<Trip> fetchTrips(List<Composite> rowKeyList, Integer assetID, UnitType type) {
        List<Trip> tripList = new ArrayList<Trip>();
        MultigetSliceQuery<Composite, String, Integer> sliceQuery = HFactory.createMultigetSliceQuery(getKeyspace(), compositeSerializer, stringSerializer, integerSerializer);

        sliceQuery.setColumnFamily(NoteColumnFamily.TRIP.getColumnDef(type));
        sliceQuery.setRange("!", "~", false, 1000); // get all the columns
        sliceQuery.setKeys(rowKeyList);

        QueryResult<Rows<Composite, String, Integer>> result = sliceQuery.execute();

        Rows<Composite, String, Integer> rows = result.get();
        for (Row<Composite, String, Integer> row : rows) {
            ColumnSlice<String, Integer> columnSlice = row.getColumnSlice();
            List<HColumn<String, Integer>> columnList = columnSlice.getColumns();

            Map<String, Object> fieldMap = new HashMap<String, Object>();
            int mileage = 0;
            for (HColumn<String, Integer> column : columnList) {
                if (column.getName().startsWith(MILEAGE))
                    mileage += column.getValue();
                fieldMap.put(column.getName(), column.getValue());
            }
            fieldMap.put(MILEAGE, mileage);
            String json = ObjectConverter.convertToJSONObject(fieldMap, "trip").toString();
            Trip trip = ObjectConverter.convertJSONToObject(json, "trip", Trip.class);
            trip.setRoute(fetchLocationNotes(assetID, trip.getStartTime(), trip.getEndTime(), type));
            if (trip.getRoute().size() > 0) {
                tripList.add(trip);
            }
        }

        return tripList;
    }

    private Trip fetchCurrentTripForAsset(Integer id, UnitType type, AutomationCalendar startTime, AutomationCalendar endTime) {
        Trip trip = null;
        Vehicle vehicle = null;
        Map<String, Object> temp;
        if (type.equals(UnitType.DRIVER))
            temp = proxy.getVehicleByDriverID(id);
        else
            temp = proxy.getVehicle(id);
        String json = ObjectConverter.convertToJSONObject(temp,"vehicle").toString();
        vehicle = ObjectConverter.convertJSONToObject(json, "vehicle", Vehicle.class);

        if (vehicle != null && vehicle.getDeviceID() != null) {
            SliceQuery<Integer, String, Integer> sliceQuery = HFactory.createSliceQuery(getKeyspace(), integerSerializer, stringSerializer, integerSerializer);
            sliceQuery.setColumnFamily(NoteColumnFamily.CURRENT_TRIP_DESCRIPTION.getColumnDef(type));
            sliceQuery.setRange(null, null, false, 20);
            sliceQuery.setKey(vehicle.getDeviceID());

            QueryResult<ColumnSlice<String, Integer>> result = sliceQuery.execute();
            ColumnSlice<String, Integer> columnSlice = result.get();

            Map<String, Object> columnMap = new HashMap<String, Object>();
            List<HColumn<String, Integer>> columnList = columnSlice.getColumns();
            boolean isWaysmart = false;
            int mileage = 0;
            int startOdometer = 0;
            for (HColumn<String, Integer> column : columnList) {
                if (START_ODOMETER.equalsIgnoreCase(column.getName())) {
                    isWaysmart = true;
                    startOdometer = column.getValue();
                }
                columnMap.put(column.getName(), column.getValue());
            }

            if (!isWaysmart) {
                SliceCounterQuery<Integer, String> cq = HFactory.createCounterSliceQuery(getKeyspace(), integerSerializer, stringSerializer);
                cq.setColumnFamily(NoteColumnFamily.CURRENT_TRIP_DESCRIPTION.getColumnDef(type));
                cq.setKey(vehicle.getDeviceID());
                cq.setRange(MILEAGE, MILEAGE + "~", false, 10);
                QueryResult<CounterSlice<String>> counterResult = cq.execute();
                CounterSlice<String> cs = counterResult.get();
                for (HCounterColumn<String> col : cs.getColumns()) {
                    mileage += col.getValue();
                }
            } else {
                DeviceNote lastLocation = null;
                lastLocation = getLastLocationNote(type, id);

                mileage = lastLocation.getOdometer() - startOdometer;
            }
            columnMap.put(MILEAGE, mileage);
            json = ObjectConverter.convertToJSONObject(columnMap, "trip").toString();
            trip = ObjectConverter.convertJSONToObject(json, "trip", Trip.class);

            if (startTime != null && endTime != null) {
                // Make sure trip really falls within date range.
                int start = trip.getEndTime().toInt();
                int stop = trip.getStartTime().toInt();
                if (( start >= startTime.toInt() && start <= endTime.toInt()) || 
                        (stop >= startTime.toInt() && stop <= endTime.toInt()))
                    trip.setRoute(fetchLocationNotes(id, trip.getStartTime(), trip.getEndTime(), type));
                else
                    trip = null;

            }
        }

        return trip;
    }
    
    public List<DeviceNote> fetchLocationNotes(Integer id, AutomationCalendar startTime, AutomationCalendar endTime, UnitType type) {
        List<DeviceNote> locationList = new ArrayList<DeviceNote>();

        SliceQuery<Composite, Composite, byte[]> sliceQuery = HFactory.createSliceQuery(getKeyspace(), compositeSerializer, compositeSerializer, bytesArraySerializer);

        Composite startRange = new Composite();
        // startRange.add(0, startTime);
        startRange.add(0, endTime.toInt());

        startRange.add(1, Integer.MIN_VALUE);
        Composite endRange = new Composite();
        endRange.add(0, startTime.toInt()); // Current Timestamp

        endRange.add(1, Integer.MAX_VALUE);

        sliceQuery.setRange(startRange, endRange, false, 10000);

        sliceQuery.setColumnFamily(NoteColumnFamily.BREADCRUMB_60.getColumnDef(type));

        Composite rowKeyComp = new Composite();
        rowKeyComp.add(0, id);
        sliceQuery.setKey(rowKeyComp);
        // rangeSlicesQuery.setReturnKeysOnly();

        QueryResult<ColumnSlice<Composite, byte[]>> result = sliceQuery.execute();
        ColumnSlice<Composite, byte[]> columnSlice = result.get();

        byte[] raw = null;
        List<HColumn<Composite, byte[]>> columnList = columnSlice.getColumns();
        for (HColumn<Composite, byte[]> column : columnList) {
            raw = column.getValue();

            locationList.add(DeviceNote.unPackageS(raw));
        }

        return locationList;
    }

    public DeviceNote getLastLocationNote(UnitType type, Integer id) {

        DeviceNote lastLocation = null;
        AutomationCalendar latestLocationTimestamp = new AutomationCalendar();
        Integer latestNoteType = 0;
        Composite noteId = null;

        // First grab the last note for the asset that has a valid lat/lng (not stripped)
        Map<Composite, Composite> noteMap = fetchLastLocationNoteFromIndex(NoteColumnFamily.NOTE_TIME_TYPE.getColumnDef(type), id);
        for (Map.Entry<Composite, Composite> entry : noteMap.entrySet()) {
            Composite noteTimeType = entry.getKey();

            latestLocationTimestamp.setDate(bigIntegerSerializer.fromByteBuffer((ByteBuffer) noteTimeType.get(0)).intValue());
            latestNoteType = bigIntegerSerializer.fromByteBuffer((ByteBuffer) noteTimeType.get(1)).intValue();
            noteId = entry.getValue();
            break;
        }

        // if the last note for this asset isn't an endtrip, let's see if we more recent breadcrumbs
        if (!DeviceNoteTypes.valueOf(latestNoteType).isEndOfTrip()) { 
            lastLocation = fetchLastLocationNote(id, latestLocationTimestamp, type);
        }

        if (lastLocation == null) {
            // we still don't have a location, so grab it from the note CF.
            if (noteId != null)
                lastLocation = fetchLastLocationFromNote(noteId);
        }
        return lastLocation;
    }

    private Map<Composite, Composite> fetchLastLocationNoteFromIndex(String INDEX_CF, Integer id) {
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

            int noteType = bigIntegerSerializer.fromByteBuffer((ByteBuffer) columnName.get(1)).intValue();

            if (!DeviceNoteTypes.valueOf(noteType).isStrippedNote()) {
                keyMap.put(column.getName(), column.getValue());
                break;
            }
        }
        return keyMap;
    }
    
    private DeviceNote fetchLastLocationNote(Integer id, AutomationCalendar lastNoteTime, UnitType type) {
        DeviceNote lastLocation = null;
        SliceQuery<Composite, Composite, byte[]> sliceQuery = HFactory.createSliceQuery(getKeyspace(), compositeSerializer, compositeSerializer, bytesArraySerializer);

        Composite startRange = new Composite();
        startRange.add(0, lastNoteTime.toInt());
        startRange.add(1, Integer.MIN_VALUE);
        Composite endRange = new Composite();
        endRange.add(0, new AutomationCalendar().toInt()); // Current Timestamp
        endRange.add(1, Integer.MAX_VALUE);

        sliceQuery.setRange(startRange, endRange, false, 1);

        sliceQuery.setColumnFamily(NoteColumnFamily.BREADCRUMB_60.getColumnDef(type));

        Composite rowKeyComp = new Composite();
        rowKeyComp.add(0, id);
        sliceQuery.setKey(rowKeyComp);
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
            lastLocation = DeviceNote.unPackageS(raw);
        }
        return lastLocation;
    }

    private DeviceNote fetchLastLocationFromNote(Composite key) {
        SliceQuery<Composite, String, byte[]> sliceQuery = HFactory.createSliceQuery(getKeyspace(), compositeSerializer, stringSerializer, bytesArraySerializer);

        sliceQuery.setColumnFamily(NoteColumnFamily.NOTE.getColumnDef(null));
        sliceQuery.setRange("", "", false, 10); // get all the columns

        sliceQuery.setKey(key);

        QueryResult<ColumnSlice<String, byte[]>> result = sliceQuery.execute();

        ColumnSlice<String, byte[]> cs = result.get();
        List<HColumn<String, byte[]>> columnList = cs.getColumns();


        byte[] raw = null;
        for (HColumn<String, byte[]> column : columnList) {
            if (column.getName().equalsIgnoreCase(ColumnAttributes.RAW_COL.getValue()))
                raw = column.getValue();
        }
        DeviceNote lastLocation = DeviceNote.unPackageS(raw);

        return lastLocation;
    }
}

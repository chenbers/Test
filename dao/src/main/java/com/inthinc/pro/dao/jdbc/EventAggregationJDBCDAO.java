package com.inthinc.pro.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.model.aggregation.DriverEventIndex;
import com.inthinc.pro.model.aggregation.DriverForgivenData;
import org.joda.time.Interval;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventAggregationDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.aggregation.DriverForgivenEventTotal;
import com.inthinc.pro.model.event.EventType;
import com.inthinc.pro.model.event.LastReportedEvent;
import com.inthinc.pro.model.event.NoteType;

/**
 * Modified for de9040 for queries for never assigned vehicles and last note for vehicle By adding relation between groupVehicleFlat and vehicle by groupId.
 */
public class EventAggregationJDBCDAO extends SimpleJdbcDaoSupport implements EventAggregationDAO {
    protected static final boolean INACTIVE_DRIVERS_DEFAULT = false;
    protected static final boolean ZERO_MILES_DRIVERS_DEFAULT = false;
    private DriverDAO driverDAO;
    
    /* Query to return the total number of forgiven events for a single driver by event type */
    private static final String SELECT_FORGIVEN_EVENT_TOTALS = "SELECT cnv.driverID AS 'driverId', cnv.driverName AS 'driverName', cnv.type AS 'type',cnv.aggType as 'aggType',g.groupID as 'groupID', g.name AS 'groupName', count(noteID) AS 'eventCount', "
                    +
                    // "(SELECT count(*) FROM cachedNoteView cnv1 WHERE cnv1.driverID = cnv.driverID AND cnv1.type = cnv.type AND (cnv1.aggType = cnv.aggType OR cnv1.aggType is null) AND forgiven = 1 AND cnv1.time BETWEEN :startDate AND :endDate) AS 'eventCountForgiven' "
                    // +
                    "SUM(cnv.forgiven=1)  AS 'eventCountForgiven' "
                    + // Another way of getting a filtered count cvn.forgiven=1 returns 1 which means true and we can count that.
                    "FROM cachedNoteView cnv  INNER JOIN groups g ON g.groupID = cnv.driverGroupID "
                    + "WHERE cnv.driverGroupID IN (:groupList) AND cnv.time BETWEEN :startDate AND :endDate GROUP BY cnv.driverID,cnv.type,cnv.aggType";
    
    public List<DriverForgivenEventTotal> findDriverForgivenEventTotalsByGroups(List<Integer> groupIDs, Interval interval) {
        return findDriverForgivenEventTotalsByGroups(groupIDs, interval, INACTIVE_DRIVERS_DEFAULT, ZERO_MILES_DRIVERS_DEFAULT);
    }

    @Override
    public Map<DriverEventIndex,List<DriverForgivenData>> findDriverForgivenDataByGroups(List<Integer> groupIDs, final Interval interval, final boolean includeInactiveDrivers,
                                                                                 final boolean includeZeroMilesDrivers) {
        String allEventForgiven = SELECT_ALL_EVENT_FORGIVEN;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupList", groupIDs);
        params.put("endDate", interval.getEnd().toDate());
        params.put("startDate", interval.getStart().toDate());

        final Map<DriverEventIndex, List<DriverForgivenData>> retMap = new HashMap<DriverEventIndex, List<DriverForgivenData>>();

        getSimpleJdbcTemplate().query(allEventForgiven, new ParameterizedRowMapper<DriverForgivenData>() {
            @Override
            public DriverForgivenData mapRow(ResultSet rs, int rowNum) throws SQLException {
                EventType eventType = EventType.UNKNOWN;

                if (NoteType.valueOf(rs.getInt("type")) != null) {
                    eventType = NoteType.valueOf(rs.getInt("type")).getEventType(rs.getInt("aggType"));
                }
                // Create a key to group the aggregation amounts by
                DriverEventIndex mapId = new DriverEventIndex(rs.getInt("driverID"),eventType);

                List<DriverForgivenData> drList = retMap.get(mapId);
                if (drList == null){
                    drList = new ArrayList<DriverForgivenData>();
                }

                DriverForgivenData driverForgivenData = new DriverForgivenData();

                // this should never be false
                if (rs.getObject("noteID") != null){
                    driverForgivenData.setNoteID(rs.getLong("noteID"));

                    if (rs.getObject("forgivenByUserID") != null){
                        driverForgivenData.setForgivenByUserID(rs.getInt("forgivenByUserID"));
                    }
                    if (rs.getObject("driverID") != null){
                        driverForgivenData.setDriverID(rs.getInt("driverID"));
                    }

                    String reason  = rs.getString("reason");
                    if (reason != null){
                        if (reason.trim().isEmpty())
                            reason = null;
                        else
                            reason = reason.trim();
                    }

                    driverForgivenData.setReason(reason);

                    drList.add(driverForgivenData);
                }
                retMap.put(mapId, drList);

                return  driverForgivenData;
            }
        }, params);

        return retMap;
    }

    /*
         * (non-Javadoc)
         *
         * @see com.inthinc.pro.dao.EventAggregationDAO#findDriverForgivenEventTotalsByGroups(java.util.List, org.joda.time.Interval)
         */
    @Override
    public List<DriverForgivenEventTotal> findDriverForgivenEventTotalsByGroups(List<Integer> groupIDs, final Interval interval, final boolean includeInactiveDrivers,
                    final boolean includeZeroMilesDrivers) {

        String allEventTotals = SELECT_ALL_EVENT_TOTALS;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupList", groupIDs);
        params.put("endDate", interval.getEnd().toDate());
        params.put("startDate", interval.getStart().toDate());
        // if(logger.isTraceEnabled()){
        // logger.trace("Executing query for findDriverForgivenEventTotalsByGroups()");
        // logger.trace(String.format("Executing query: %s", forgivenEventTotals));
        // logger.trace(String.format("Query parameters: %s", params));
        // }
        /*
         * Create a map to allow us to aggregate the totals by grouping by EventType.java which is unknown to the database. Otherwise, we would allow the database to group by.
         */
        final Map<DriverEventIndex, DriverForgivenEventTotal> driverForgivenEventTotalMap = new LinkedHashMap<DriverEventIndex, DriverForgivenEventTotal>();
        getSimpleJdbcTemplate().query(allEventTotals, new ParameterizedRowMapper<DriverForgivenEventTotal>() {
            @Override
            public DriverForgivenEventTotal mapRow(ResultSet rs, int rowNum) throws SQLException {
                               EventType eventType = EventType.UNKNOWN;

                if (NoteType.valueOf(rs.getInt("type")) != null) {
                    eventType = NoteType.valueOf(rs.getInt("type")).getEventType(rs.getInt("aggType"));
                }
                // Create a key to group the aggregation amounts by
                DriverEventIndex mapId = new DriverEventIndex(rs.getInt("driverID"), eventType);

                DriverForgivenEventTotal driverForgivenEventTotal = null;
                if (driverForgivenEventTotalMap.get(mapId) != null) {
                    driverForgivenEventTotal = driverForgivenEventTotalMap.get(mapId);
                    driverForgivenEventTotal.setEventCount(driverForgivenEventTotal.getEventCount() + rs.getInt("eventCount"));
                } else {
                    Driver driver = driverDAO.findByID(rs.getInt("driverID"));

                    List<Trip> trips = driverDAO.getTrips(driver.getDriverID(), interval);
                    Integer totalMiles = 0;
                    for (Trip trip : trips) {
                        totalMiles += trip.getMileage();
                    }

                    boolean includeThisInactiveDriver = (includeInactiveDrivers && totalMiles != 0);
                    boolean includeThisZeroMilesDriver = (includeZeroMilesDrivers && driver.getStatus().equals(Status.ACTIVE));
                    if ((driver.getStatus().equals(Status.ACTIVE) && totalMiles != 0) || (includeInactiveDrivers && includeZeroMilesDrivers) || includeThisInactiveDriver
                                    || includeThisZeroMilesDriver) {
                        System.out.println("INCLUDING: fullName: " + driver.getPerson().getFullName());
                        System.out.println("status: " + driver.getStatus());
                        System.out.println("totalMiles: " + totalMiles);
                        driverForgivenEventTotal = new DriverForgivenEventTotal();
                        driverForgivenEventTotal.setDriverID(rs.getInt("driverID"));
                        driverForgivenEventTotal.setDriverName(rs.getString("driverName"));
                        driverForgivenEventTotal.setGroupID(rs.getInt("groupID"));
                        driverForgivenEventTotal.setGroupName(rs.getString("groupName"));
                        driverForgivenEventTotal.setEventCount(rs.getInt("eventCount"));
                        driverForgivenEventTotal.setEventType(eventType);
                        driverForgivenEventTotalMap.put(mapId, driverForgivenEventTotal);
                    } else {
                        System.out.println(rs.getString("driverName") + " this record was returned via SQL, but filtered out in java");
                        return null;
                    }
                }
                return driverForgivenEventTotal;
            }
        }, params);



        // if the initial dataset is not empty
        if (!driverForgivenEventTotalMap.isEmpty()) {

            // find forgiven data
            Map<DriverEventIndex,List<DriverForgivenData>> forgivenDataMap = findDriverForgivenDataByGroups(groupIDs, interval, includeInactiveDrivers, includeZeroMilesDrivers);

            // for each main dataset
            for (Map.Entry<DriverEventIndex, DriverForgivenEventTotal> dftEntry : driverForgivenEventTotalMap.entrySet()){
                DriverForgivenEventTotal dft = dftEntry.getValue();
                Integer driverID = dft.getDriverID();
                DriverEventIndex mapId = dftEntry.getKey();

                dft.setEventCountForgiven(0);
                dft.setReasons("");

                // if it's possible to have forgiven data
                if (mapId != null && driverID != null) {
                    List<DriverForgivenData> driverForgivenData = forgivenDataMap.get(mapId);

                    // if we found forgiven data
                    if (driverForgivenData != null) {
                        for (DriverForgivenData df : driverForgivenData) {
                            dft.setEventCountForgiven(dft.getEventCountForgiven() + 1);
                            if (df.getReason() != null && !df.getReason().trim().isEmpty()) {
                                if (dft.getReasons() != null && !dft.getReasons().isEmpty()) {
                                    dft.setReasons(dft.getReasons() + "; " + df.getReason());
                                } else {
                                    dft.setReasons(df.getReason());
                                }
                            }
                        }

                        // calculate percent
                        double percentForgiven = 0.0D;
                        double totalEvents = dft.getEventCount();
                        double totalEventsForgiven = dft.getEventCountForgiven();
                        percentForgiven = totalEventsForgiven / totalEvents;
                        dft.setPercentForgiven(percentForgiven);
                    }else{
                        dft.setPercentForgiven(0d);
                    }
                }
            }
        }

        // remove events without forgiven from main datamap
        List<DriverEventIndex> removedKeys = new ArrayList<DriverEventIndex>();
        for (Map.Entry<DriverEventIndex, DriverForgivenEventTotal> dftEntry : driverForgivenEventTotalMap.entrySet()){
            DriverForgivenEventTotal value = dftEntry.getValue();
            if (value.getEventCountForgiven()<=0){
                removedKeys.add(dftEntry.getKey());
            }
        }

        for (DriverEventIndex key: removedKeys)
            driverForgivenEventTotalMap.remove(key);


        return Arrays.asList(driverForgivenEventTotalMap.values().toArray(new DriverForgivenEventTotal[0]));
    }
    
    private static final String SELECT_LAST_NOTE_TEMPLATE = "SELECT * from (%s) a GROUP by vehicleID ORDER by vehicleName ASC;";
    private static final String SELECT_LAST_NOTE_TEMPLATE_INNER = "SELECT v.vehicleID,v.name as 'vehicleName',l.deviceID,d.name as 'deviceName',d.serialNum,"
                    + "l.start AS 'deviceAssignedDate',l.stop AS 'deviceUnassignedDate',n.noteID,n.time AS 'noteTime',n.type AS 'noteType',g.groupID,g.name as 'groupName',(DATEDIFF(n.time,CURDATE()) * -1) as 'daysSince' FROM vehicle v "
                    + "INNER JOIN vddlog l ON l.vehicleID = v.vehicleID INNER JOIN device d ON d.deviceID = l.deviceID "
                    + "INNER JOIN note%s n ON n.deviceID = l.deviceID AND n.vehicleID = v.vehicleID "
                    + "INNER JOIN groupVehicleFlat gv ON gv.vehicleID = v.vehicleID INNER JOIN groups g ON g.groupID = gv.groupID "
                    + "INNER JOIN (SELECT _n.vehicleID,_n.deviceID,MAX(_n.time) as 'maxTime' FROM note%s _n GROUP BY _n.deviceID,_n.vehicleID) groupedNote "
                    + "ON groupedNote.maxtime = n.time AND groupedNote.deviceID = l.deviceID AND groupedNote.vehicleID = l.vehicleID "
                    + "WHERE n.time < :startDate AND (l.stop iS NULL OR l.stop = (select max(stop) from vddlog where vehicleID = v.vehicleID)) " + "AND g.groupID IN (:groupList)";
    
    private static final String SELECT_NEVER_ASSIGNED_VEHICLE = "SELECT v.vehicleID,v.name AS 'vehicleName',g.groupID,g.name AS 'groupName' FROM vehicle v "
                    + "INNER JOIN groupVehicleFlat gv ON gv.vehicleID = v.vehicleID INNER JOIN groups g ON g.groupID = gv.groupID " + "LEFT OUTER JOIN vddlog l ON l.vehicleID = v.vehicleID WHERE "
                    + "NOT EXISTS (SELECT  * FROM vddlog _l WHERE _l.vehicleID = v.vehicleID) " + "AND g.groupID IN (:groupList) ";
    
    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.dao.EventAggregationDAO#findRecentEventByDevice(java.util.List, org.joda.time.Interval)
     */
    @Override
    public List<LastReportedEvent> findRecentEventByDevice(List<Integer> groupIDs, Interval interval) {
        
        /*
         * First load all last reported events which fall before the start time of the interval. If a vehicle doesn't have a device currently, still load it and the last note received for that vehicle
         * from the last device which was assigned to it.
         */
        StringBuilder lastNoteQueryStringBuilder = new StringBuilder();
        // Build a query for each note table
        for (int i = 0; i < 32; i++) {
            String tableNum = Integer.toString(i);
            if (tableNum.length() == 1) {
                tableNum = String.format("0%s", tableNum);
            }
            lastNoteQueryStringBuilder.append(String.format(SELECT_LAST_NOTE_TEMPLATE_INNER, tableNum, tableNum));
            if (i < 31) {
                lastNoteQueryStringBuilder.append(" UNION ");
            }
        }
        
        String lastNotQuery = String.format(SELECT_LAST_NOTE_TEMPLATE, lastNoteQueryStringBuilder.toString());
        System.out.println(lastNotQuery);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupList", groupIDs);
        params.put("startDate", interval.getStart().toDate());
        
        if (logger.isDebugEnabled()) {
            logger.debug("Executing query for findRecentEventByDevice()");
            logger.debug(String.format("Executing query: %s", lastNotQuery));
            logger.debug(String.format("Query parameters: %s", params));
        }
        List<LastReportedEvent> lastReportedEvents = getSimpleJdbcTemplate().query(lastNotQuery, new ParameterizedRowMapper<LastReportedEvent>() {
            @Override
            public LastReportedEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
                LastReportedEvent event = new LastReportedEvent();
                event.setType(NoteType.valueOf(rs.getInt("noteType")));
                event.setVehicleID(rs.getInt("vehicleID"));
                event.setVehicleName(rs.getString("vehicleName"));
                event.setTime(rs.getTimestamp("noteTime"));
                event.setDeviceID(rs.getInt("deviceID"));
                event.setDeviceSerialNum(rs.getString("serialNum"));
                event.setDaysSince(rs.getInt("daysSince"));
                event.setGroupID(rs.getInt("groupID"));
                event.setGroupName(rs.getString("groupName"));
                event.setLastUnassignedDate(rs.getDate("deviceUnassignedDate"));
                event.setLastAssignedDate(rs.getDate("deviceAssignedDate"));
                return event;
            }
        }, params);
        
        /* Now we need to load all vehicles which have never been assigned */
        Map<String, Object> params2 = new HashMap<String, Object>();
        params2.put("groupList", groupIDs);
        List<LastReportedEvent> vehiclesWithNoNotes = getSimpleJdbcTemplate().query(SELECT_NEVER_ASSIGNED_VEHICLE, new ParameterizedRowMapper<LastReportedEvent>() {
            @Override
            public LastReportedEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
                LastReportedEvent event = new LastReportedEvent();
                event.setVehicleID(rs.getInt("vehicleID"));
                event.setVehicleName(rs.getString("vehicleName"));
                event.setGroupID(rs.getInt("groupID"));
                event.setGroupName(rs.getString("groupName"));
                return event;
            }
        }, params2);
        
        lastReportedEvents.addAll(vehiclesWithNoNotes);
        return lastReportedEvents;
    }
    
    private static final String SELECT_LAST_NOTE_FOR_VEHICLE = "SELECT v.vehicleID,v.name as 'vehicleName',d.deviceID,d.name as 'deviceName',d.serialNum,max(l.time) AS 'noteTime',l.noteType AS 'noteType',g.groupID,g.name as 'groupName',(DATEDIFF(l.time,CURDATE()) * -1) as 'daysSince' "
                    + "FROM vehicle v "
                    + "INNER JOIN groupVehicleFlat gv ON gv.vehicleID = v.vehicleID AND gv.groupID=v.groupID INNER JOIN groups g ON g.groupID = gv.groupID "
                    + "INNER JOIN (select max(time) maxTime  from lastLocVehicle _l group by deviceID) dmax "
                    + "INNER JOIN lastLocVehicle l on l.vehicleID = v.vehicleID and dmax.maxTime = l.time "
                    + "INNER JOIN device d ON d.deviceID = l.deviceID "
                    + "where v.vehicleID in (select vehicleID from vehicle where groupID in (:groupList)) ";
    private static final String SELECT_BETWEEN_TWO_DATES = " and l.time between :startDate and :endDate group by vehicleID order by vehicleName";
    private static final String SELECT_ONE_DATE = "and l.time < :startDate group by vehicleID order by vehicleName";
    
    private static final String SELECT_NEVER_ASSIGNED_VEHICLES = "SELECT v.vehicleID,v.name AS 'vehicleName',g.groupID,g.name AS 'groupName' FROM vehicle v "
                    + "INNER JOIN groupVehicleFlat gv ON gv.vehicleID = v.vehicleID AND gv.groupID=v.groupID INNER JOIN groups g ON g.groupID = gv.groupID "
                    + "LEFT OUTER JOIN vddlog l ON l.vehicleID = v.vehicleID WHERE " + "NOT EXISTS (SELECT  * FROM vddlog _l WHERE _l.vehicleID = v.vehicleID) " + "AND g.groupID IN (:groupList) ";
    
    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.dao.EventAggregationDAO#findLastEventForVehicles(java.util.List, org.joda.time.Interval)
     */
    @Override
    public List<LastReportedEvent> findLastEventForVehicles(List<Integer> groupIDs, Interval interval,boolean dontIncludeUnassignedDevice,boolean activeInterval) {
        
        /*
         * First load all last reported events which fall before the start time of the interval. If a vehicle doesn't have a device currently, still load it and the last note received for that vehicle
         * from the last device which was assigned to it.
         */
        String lastNotQuery = SELECT_LAST_NOTE_FOR_VEHICLE;

        Map<String, Object> params = new HashMap<String, Object>();

        if (activeInterval){
            params.put("groupList", groupIDs);
            params.put("startDate", interval.getStart().toDate());
            params.put("endDate",interval.getEnd().toDate());
            lastNotQuery=lastNotQuery+SELECT_BETWEEN_TWO_DATES;
        }
        else{
            params.put("groupList", groupIDs);
            params.put("startDate", interval.getStart().toDate());
            lastNotQuery=lastNotQuery+SELECT_ONE_DATE;
        }

        
        if (logger.isDebugEnabled()) {
            logger.debug("Executing query for findLastEventForVehicles()");
            logger.debug(String.format("Executing query: %s", lastNotQuery));
            logger.debug(String.format("Query parameters: %s", params));
        }
        List<LastReportedEvent> lastReportedEvents = getSimpleJdbcTemplate().query(lastNotQuery, new ParameterizedRowMapper<LastReportedEvent>() {
            @Override
            public LastReportedEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
                LastReportedEvent event = new LastReportedEvent();
                event.setType(NoteType.valueOf(rs.getInt("noteType")));
                event.setVehicleID(rs.getInt("vehicleID"));
                event.setVehicleName(rs.getString("vehicleName"));
                event.setTime(rs.getTimestamp("noteTime"));
                event.setDeviceID(rs.getInt("deviceID"));
                event.setDeviceSerialNum(rs.getString("serialNum"));
                event.setDaysSince(rs.getInt("daysSince"));
                event.setGroupID(rs.getInt("groupID"));
                event.setGroupName(rs.getString("groupName"));
                return event;
            }
        }, params);

        if(!dontIncludeUnassignedDevice) {
        /* Now we need to load all vehicles which have never been assigned */
        Map<String, Object> params2 = new HashMap<String, Object>();
        params2.put("groupList", groupIDs);
        List<LastReportedEvent> vehiclesWithNoNotes = getSimpleJdbcTemplate().query(SELECT_NEVER_ASSIGNED_VEHICLES, new ParameterizedRowMapper<LastReportedEvent>() {
            @Override
            public LastReportedEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
                LastReportedEvent event = new LastReportedEvent();
                event.setVehicleID(rs.getInt("vehicleID"));
                event.setVehicleName(rs.getString("vehicleName"));
                event.setGroupID(rs.getInt("groupID"));
                event.setGroupName(rs.getString("groupName"));
                return event;
            }
        }, params2);

        lastReportedEvents.addAll(vehiclesWithNoNotes);
        }
        return lastReportedEvents;
    }
    
    public DriverDAO getDriverDAO() {
        return driverDAO;
    }
    
    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }
}

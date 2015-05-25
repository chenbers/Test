package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventAggregationDAO;
import com.inthinc.pro.dao.EventStatisticsDAO;
import com.inthinc.pro.dao.LocationDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.aggregation.DriverForgivenEvent;
import com.inthinc.pro.model.aggregation.DriverForgivenEventTotal;
import com.inthinc.pro.model.event.EventType;
import com.inthinc.pro.model.event.LastReportedEvent;
import com.inthinc.pro.model.event.NoteType;
import org.apache.log4j.Logger;
import org.joda.time.Interval;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Modified for de9040 for queries for never assigned vehicles and last note for vehicle By adding relation between groupVehicleFlat and vehicle by groupId.
 */
public class EventAggregationJDBCDAO extends SimpleJdbcDaoSupport implements EventAggregationDAO {
    private static final Logger logger = Logger.getLogger(EventAggregationJDBCDAO.class);
    protected static final boolean INACTIVE_DRIVERS_DEFAULT = false;
    protected static final boolean ZERO_MILES_DRIVERS_DEFAULT = false;
    private DriverDAO driverDAO;
    private EventStatisticsDAO eventStatisticsDAO;
    private UserDAO userDAO;
    
    /* Query to return the total number of forgiven events for a single driver by event type */
    private static final String SELECT_FORGIVEN_EVENT_TOTALS = ""
            +"SELECT cnv.driverID AS 'driverId' "
            +"  ,i.driverName AS 'driverName' "
            +"  ,cnv.type AS 'type' "
            +"  ,getAggType(`cnv`.`type`,`cnv`.`attribs`,`cnv`.`deltaX`,`cnv`.`deltaY`,`cnv`.`deltaZ`) as 'aggType' "
            +"  ,g.groupID as 'groupID' "
            +"  ,g.name AS 'groupName' "
            +"  ,count(cnv.noteID) AS 'eventCount' "
            +"  ,SUM(coalesce(cnv.forgiven,0)) AS 'eventCountForgiven' "
            +"FROM groups g  "
            +"  join driver d on d.groupID = g.groupID and d.status != 3 "
            +"  join cachedNoteInfo i on i.driverID = d.driverID "
            +"  join cachedNote cnv on cnv.driverID = d.driverID "
            +"WHERE g.groupID IN (:groupList)  "
            +"  AND cnv.time BETWEEN :startDate AND :endDate "
            +"GROUP BY cnv.driverID ,cnv.type ,getAggType(`cnv`.`type`,`cnv`.`attribs`,`cnv`.`deltaX`,`cnv`.`deltaY`,`cnv`.`deltaZ`)";


    /* Query to return forgiven events by event type for a groupID list */
    private static final String SELECT_FORGIVEN_EVENTS = ""
            +"SELECT cnv.driverID AS 'driverId' "
            +"  ,i.driverName AS 'driverName' "
            +"  ,cnv.type AS 'type' "
            +"  ,getAggType(`cnv`.`type`,`cnv`.`attribs`,`cnv`.`deltaX`,`cnv`.`deltaY`,`cnv`.`deltaZ`) as 'aggType' "
            +"  ,g.groupID as 'groupID' "
            +"  ,g.name AS 'groupName' "
            +"  ,f.reason AS 'reason' "
            +"  ,cnv.time AS 'dateTime' "
            +"  ,f.forgivenByUserId AS 'forgivenByUserId' "
            +" FROM cachedNote cnv "
            +"  JOIN driver d "
            +"  ON cnv.driverID = d.driverID "
            +"  AND d.status != 3 "
            +"  JOIN groups g "
            +"  ON d.groupID = g.groupID "
            +"  JOIN cachedNoteInfo i "
            +"  ON cnv.driverID = i.driverID "
            +"  AND cnv.vehicleID = i.vehicleID "
            +"  AND g.groupID = i.groupID "
            +"  JOIN forgiven f "
            +"  ON cnv.noteID = f.noteID "
            +" WHERE g.groupID IN (:groupList)  "
            +"  AND cnv.time BETWEEN :startDate AND :endDate "
            +"  AND cnv.forgiven = 1 ";


    public List<DriverForgivenEventTotal> findDriverForgivenEventTotalsByGroups(List<Integer> groupIDs, Interval interval) {
        return findDriverForgivenEventTotalsByGroups(groupIDs, interval, INACTIVE_DRIVERS_DEFAULT, ZERO_MILES_DRIVERS_DEFAULT);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.dao.EventAggregationDAO#findDriverForgivenEventTotalsByGroups(java.util.List, org.joda.time.Interval)
     */
    @Override
    public List<DriverForgivenEventTotal> findDriverForgivenEventTotalsByGroups(List<Integer> groupIDs, final Interval interval, final boolean includeInactiveDrivers,
                    final boolean includeZeroMilesDrivers) {
        String forgivenEventTotals = SELECT_FORGIVEN_EVENT_TOTALS;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupList", groupIDs);
        params.put("endDate", interval.getEnd().toDate());
        params.put("startDate", interval.getStart().toDate());

        /*
         * Create a map to allow us to aggregate the totals by grouping by EventType.java which is unknown to the database. Otherwise, we would allow the database to group by.
         */
        final Map<Object[], DriverForgivenEventTotal> driverForgivenEventTotalMap = new LinkedHashMap<Object[], DriverForgivenEventTotal>();
        getSimpleJdbcTemplate().query(forgivenEventTotals, new ParameterizedRowMapper<DriverForgivenEventTotal>() {
            @Override
            public DriverForgivenEventTotal mapRow(ResultSet rs, int rowNum) throws SQLException {
                /* Check to ensure that the forgiven count is greater than 0 */
                if (rs.getInt("eventCountForgiven") > 0) {
                    EventType eventType = EventType.UNKNOWN;
                    if (NoteType.valueOf(rs.getInt("type")) != null) {
                        eventType = NoteType.valueOf(rs.getInt("type")).getEventType(rs.getInt("aggType"));
                    }

                    // holds already found driver info from driverDao
                    final Map<Integer, Driver> driverInfoCache = new HashMap<Integer, Driver>();
                    final Map<Integer, Integer> driverTripMileageCache = new HashMap<Integer, Integer>();

                    // Create a key to group the aggregation amounts by
                    Object[] mapId = new Object[3];
                    mapId[0] = rs.getInt("driverID");
                    mapId[1] = eventType;

                    DriverForgivenEventTotal driverForgivenEventTotal = null;
                    if (driverForgivenEventTotalMap.get(mapId) != null) {
                        driverForgivenEventTotal = driverForgivenEventTotalMap.get(mapId);
                        driverForgivenEventTotal.setEventCount(driverForgivenEventTotal.getEventCount() + rs.getInt("eventCount"));
                        driverForgivenEventTotal.setEventCountForgiven(driverForgivenEventTotal.getEventCountForgiven() + rs.getInt("eventCountForgiven"));
                    } else {

                        Driver driver = driverInfoCache.get(rs.getInt("driverID"));
                        if (driver == null){
                            driver = driverDAO.findByID(rs.getInt("driverID"));
                            if (driver != null)
                                driverInfoCache.put(driver.getDriverID(), driver);
                        }

                        Integer driverTripMileage = driverTripMileageCache.get(rs.getInt("driverID"));
                        if (driverTripMileage == null){
                            driverTripMileage = eventStatisticsDAO.getTripMileageCountForDriver(rs.getInt("driverID"), interval.getStart().toDate(), interval.getEnd().toDate());
                            driverTripMileageCache.put(rs.getInt("driverID"),driverTripMileage);
                        }

                        Integer totalMiles = driverTripMileage;
                        if (totalMiles == null)
                            totalMiles = 0;
                        
                        boolean includeThisInactiveDriver = (includeInactiveDrivers && totalMiles != 0);
                        boolean includeThisZeroMilesDriver = (includeZeroMilesDrivers && driver.getStatus().equals(Status.ACTIVE));
                        if ((driver.getStatus().equals(Status.ACTIVE) && totalMiles != 0) || (includeInactiveDrivers && includeZeroMilesDrivers) || includeThisInactiveDriver
                                        || includeThisZeroMilesDriver) {
                            logger.info("INCLUDING: fullName: " + driver.getPerson().getFullName());
                            logger.info("status: " + driver.getStatus());
                            logger.info("totalMiles: " + totalMiles);
                            driverForgivenEventTotal = new DriverForgivenEventTotal();
                            driverForgivenEventTotal.setDriverID(rs.getInt("driverID"));
                            driverForgivenEventTotal.setDriverName(rs.getString("driverName"));
                            driverForgivenEventTotal.setGroupID(rs.getInt("groupID"));
                            driverForgivenEventTotal.setGroupName(rs.getString("groupName"));
                            driverForgivenEventTotal.setEventCount(rs.getInt("eventCount"));
                            driverForgivenEventTotal.setEventCountForgiven(rs.getInt("eventCountForgiven"));
                            driverForgivenEventTotal.setEventType(eventType);
                            driverForgivenEventTotalMap.put(mapId, driverForgivenEventTotal);

                        } else {
                            logger.info(rs.getString("driverName") + " this record was returned via SQL, but filtered out in java");
                            return null;
                        }
                    }
                    double percentForgiven = 0.0D;
                    double totalEvents = driverForgivenEventTotal.getEventCount();
                    double totalEventsForgiven = driverForgivenEventTotal.getEventCountForgiven();
                    percentForgiven = totalEventsForgiven / totalEvents;
                    driverForgivenEventTotal.setPercentForgiven(percentForgiven);
                    return driverForgivenEventTotal;
                } else {
                    return null;
                }
            }
        }, params);
        return Arrays.asList(driverForgivenEventTotalMap.values().toArray(new DriverForgivenEventTotal[0]));
    }

    public List<DriverForgivenEvent> findDriverForgivenEventsByGroups(List<Integer> groupIDs, Interval interval) {
        return findDriverForgivenEventsByGroups(groupIDs, interval, INACTIVE_DRIVERS_DEFAULT, ZERO_MILES_DRIVERS_DEFAULT);
    }

    @Override
    public List<DriverForgivenEvent> findDriverForgivenEventsByGroups(List<Integer> groupIDs, final Interval interval, final boolean includeInactiveDrivers,
                                                                      final boolean includeZeroMilesDrivers) {
        String forgivenEvents = SELECT_FORGIVEN_EVENTS;
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm a");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupList", groupIDs);
        params.put("endDate", interval.getEnd().toDate());
        params.put("startDate", interval.getStart().toDate());

        // holds already found driver info from driverDao
        final Map<Integer, Driver> driverInfoCache = new HashMap<Integer, Driver>();
        final Map<Integer, User> userInfoCache = new HashMap<Integer, User>();
        final Map<Integer, Integer> driverTripMileageCache = new HashMap<Integer, Integer>();

        return getSimpleJdbcTemplate().query(forgivenEvents, new ParameterizedRowMapper<DriverForgivenEvent>() {
            @Override
            public DriverForgivenEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
                DriverForgivenEvent dfe = new DriverForgivenEvent();
                EventType eventType = EventType.UNKNOWN;
                if (NoteType.valueOf(rs.getInt("type")) != null) {
                    eventType = NoteType.valueOf(rs.getInt("type")).getEventType(rs.getInt("aggType"));
                }

                Driver driver = driverInfoCache.get(rs.getInt("driverID"));
                if (driver == null){
                    driver = driverDAO.findByID(rs.getInt("driverID"));
                    if (driver != null)
                        driverInfoCache.put(driver.getDriverID(), driver);
                }

                Integer driverTripMileage = driverTripMileageCache.get(rs.getInt("driverID"));
                if (driverTripMileage == null){
                    driverTripMileage = eventStatisticsDAO.getTripMileageCountForDriver(rs.getInt("driverID"), interval.getStart().toDate(), interval.getEnd().toDate());
                    driverTripMileageCache.put(rs.getInt("driverID"),driverTripMileage);
                }

                if (driverTripMileage == null)
                    driverTripMileage = 0;

                boolean includeThisInactiveDriver = (includeInactiveDrivers && driverTripMileage != 0);
                boolean includeThisZeroMilesDriver = (includeZeroMilesDrivers && driver != null && driver.getStatus().equals(Status.ACTIVE));
                if ((driver != null && driver.getStatus().equals(Status.ACTIVE) && driverTripMileage != 0) || (includeInactiveDrivers && includeZeroMilesDrivers) || includeThisInactiveDriver
                        || includeThisZeroMilesDriver) {
                    Person person = driver.getPerson();
                    String personName = "";
                    if (person != null)
                        personName = person.getFullName();
                    logger.info("INCLUDING: fullName: " + personName);
                    logger.info("status: " + driver.getStatus());
                    logger.info("totalMiles: " + driverTripMileage);

                    dfe.setDriverID(rs.getInt("driverID"));
                    dfe.setDriverName(rs.getString("driverName"));
                    dfe.setGroupID(rs.getInt("groupID"));
                    dfe.setGroupName(rs.getString("groupName"));
                    dfe.setDateTime(rs.getTimestamp("dateTime"));
                    dfe.setReason(rs.getString("reason"));
                    dfe.setEventType(eventType);

                    // get excluded by employee id
                    Long forgivenByUserIdL = rs.getLong("forgivenByUserId");
                    if (forgivenByUserIdL != 0){
                        Integer forgivenByUserId = forgivenByUserIdL.intValue();
                        User user = userInfoCache.get(forgivenByUserId);
                        if (user == null){
                            user = userDAO.findByID(forgivenByUserId);
                            if (user != null) {
                                userInfoCache.put(forgivenByUserId, user);
                            }
                        }

                        if (user != null && user.getPerson() != null){
                            dfe.setExcludedByUser(user.getPerson().getEmpid());
                        }
                    }

                    if (dfe.getDateTime() != null) {
                        dfe.setDateTimeStr(sdf.format(dfe.getDateTime()));
                    }

                    return dfe;

                } else {
                    logger.info(rs.getString("driverName") + " this record was returned via SQL, but filtered out in java");
                    return null;
                }
            }
        }, params);
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
        logger.info(lastNotQuery);
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

    public EventStatisticsDAO getEventStatisticsDAO() {
        return eventStatisticsDAO;
    }

    public void setEventStatisticsDAO(EventStatisticsDAO eventStatisticsDAO) {
        this.eventStatisticsDAO = eventStatisticsDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}

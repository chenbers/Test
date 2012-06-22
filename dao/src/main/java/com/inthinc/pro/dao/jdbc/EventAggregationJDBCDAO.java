package com.inthinc.pro.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.Interval;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.inthinc.pro.dao.EventAggregationDAO;
import com.inthinc.pro.model.aggregation.DriverForgivenEventTotal;
import com.inthinc.pro.model.event.EventType;
import com.inthinc.pro.model.event.LastReportedEvent;
import com.inthinc.pro.model.event.NoteType;

public class EventAggregationJDBCDAO extends SimpleJdbcDaoSupport implements EventAggregationDAO {
    
    /* Query to return the total number of forgiven events for a single driver by event type */
    private static final String SELECT_FORGIVEN_EVENT_TOTALS = "SELECT cnv.driverID AS 'driverId', cnv.driverName AS 'driverName', cnv.type AS 'type',cnv.aggType as 'aggType',g.groupID as 'groupID', g.name AS 'groupName', count(noteID) AS 'eventCount', " + 
                    "(SELECT count(*) FROM cachedNoteView cnv1 WHERE cnv1.driverID = cnv.driverID AND cnv1.type = cnv.type AND (cnv1.aggType = cnv.aggType OR cnv1.aggType is null) AND forgiven = 1) AS 'eventCountForgiven' " + 
                    "FROM cachedNoteView cnv  INNER JOIN groups g ON g.groupID = cnv.driverGroupID " + 
                    "WHERE cnv.driverGroupID IN (:groupList) AND cnv.time BETWEEN :startDate AND :endDate GROUP BY cnv.driverID,cnv.type,cnv.aggType";
   
    
    /*
     * (non-Javadoc)
     * @see com.inthinc.pro.dao.EventAggregationDAO#findDriverForgivenEventTotalsByGroups(java.util.List, org.joda.time.Interval)
     */
    @Override
    public List<DriverForgivenEventTotal> findDriverForgivenEventTotalsByGroups(List<Integer> groupIDs, Interval interval) {
        String forgivenEventTotals = SELECT_FORGIVEN_EVENT_TOTALS;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupList", groupIDs);
        params.put("endDate", interval.getEnd().toDate());
        params.put("startDate", interval.getStart().toDate());
//        if(logger.isTraceEnabled()){
//            logger.trace("Executing query for findDriverForgivenEventTotalsByGroups()");
//            logger.trace(String.format("Executing query: %s", forgivenEventTotals));
//            logger.trace(String.format("Query parameters: %s", params));
//        }
        /*
         * Create a map to allow us to aggregate the totals by grouping by EventType.java which is unknown to the database. Otherwise,
         * we would allow the database to group by.
         */
        final Map<Object[], DriverForgivenEventTotal> driverForgivenEventTotalMap = new LinkedHashMap<Object[], DriverForgivenEventTotal>();
        getSimpleJdbcTemplate().query(forgivenEventTotals, new ParameterizedRowMapper<DriverForgivenEventTotal>() {
            @Override
            public DriverForgivenEventTotal mapRow(ResultSet rs, int rowNum) throws SQLException {
                /* Check to ensure that the forgiven count is greater than 0 */
                if(rs.getInt("eventCountForgiven") > 0){
                    EventType eventType = EventType.UNKNOWN;
                    if(NoteType.valueOf(rs.getInt("type")) != null){
                        eventType = NoteType.valueOf(rs.getInt("type")).getEventType(rs.getInt("aggType"));
                    }
                    //Create a key to group the aggregation amounts by
                    Object[] mapId = new Object[3];
                    mapId[0] = rs.getInt("driverID");
                    mapId[1] = eventType;
                    
                    DriverForgivenEventTotal driverForgivenEventTotal = null;
                    if(driverForgivenEventTotalMap.get(mapId) != null){
                        driverForgivenEventTotal = driverForgivenEventTotalMap.get(mapId);
                        driverForgivenEventTotal.setEventCount(driverForgivenEventTotal.getEventCount() + rs.getInt("eventCount"));
                        driverForgivenEventTotal.setEventCountForgiven(driverForgivenEventTotal.getEventCountForgiven() + rs.getInt("eventCountForgiven"));
                    }else{
                        driverForgivenEventTotal = new DriverForgivenEventTotal();
                        driverForgivenEventTotal.setDriverID(rs.getInt("driverID"));
                        driverForgivenEventTotal.setDriverName(rs.getString("driverName"));
                        driverForgivenEventTotal.setGroupID(rs.getInt("groupID"));
                        driverForgivenEventTotal.setGroupName(rs.getString("groupName"));
                        driverForgivenEventTotal.setEventCount(rs.getInt("eventCount"));
                        driverForgivenEventTotal.setEventCountForgiven(rs.getInt("eventCountForgiven"));
                        driverForgivenEventTotal.setEventType(eventType);
                        driverForgivenEventTotalMap.put(mapId, driverForgivenEventTotal);
                    }
                    double percentForgiven = 0.0D;
                    double totalEvents = driverForgivenEventTotal.getEventCount();
                    double totalEventsForgiven = driverForgivenEventTotal.getEventCountForgiven();
                    percentForgiven = totalEventsForgiven/totalEvents;
                    driverForgivenEventTotal.setPercentForgiven(percentForgiven);
                    return driverForgivenEventTotal;
                }else{
                    return null;
                }
            }           
        } , params);
        return Arrays.asList(driverForgivenEventTotalMap.values().toArray(new DriverForgivenEventTotal[0]));
    }
    
    private static final String SELECT_LAST_NOTE_TEMPLATE = "SELECT * from (%s) a GROUP by vehicleID ORDER by vehicleName ASC;";
    private static final String SELECT_LAST_NOTE_TEMPLATE_INNER = "SELECT v.vehicleID,v.name as 'vehicleName',l.deviceID,d.name as 'deviceName',d.serialNum," + 
                                "l.start AS 'deviceAssignedDate',l.stop AS 'deviceUnassignedDate',n.noteID,n.time AS 'noteTime',n.type AS 'noteType',g.groupID,g.name as 'groupName',(DATEDIFF(n.time,CURDATE()) * -1) as 'daysSince' FROM vehicle v " + 
                                "INNER JOIN vddlog l ON l.vehicleID = v.vehicleID INNER JOIN device d ON d.deviceID = l.deviceID " +  
                                "INNER JOIN note%s n ON n.deviceID = l.deviceID AND n.vehicleID = v.vehicleID " +  
                                "INNER JOIN groupVehicleFlat gv ON gv.vehicleID = v.vehicleID INNER JOIN groups g ON g.groupID = gv.groupID " + 
                                "INNER JOIN (SELECT _n.vehicleID,_n.deviceID,MAX(_n.time) as 'maxTime' FROM note%s _n GROUP BY _n.deviceID,_n.vehicleID) groupedNote " +  
                                "ON groupedNote.maxtime = n.time AND groupedNote.deviceID = l.deviceID AND groupedNote.vehicleID = l.vehicleID " + 
                                "WHERE n.time < :startDate AND (l.stop iS NULL OR l.stop = (SELECT max(l.stop) FROM vehicle _v WHERE _v.vehicleID = v.vehicleID)) " + 
                                "AND g.groupID IN (:groupList)";
    private static final String SELECT_NEVER_ASSIGNED_VEHICLE = "SELECT v.vehicleID,v.name as 'vehicleName',g.groupID,g.name as 'groupName' FROM vehicle v " + 
                                "INNER JOIN groupVehicleFlat gv ON gv.vehicleID = v.vehicleID INNER JOIN groups g ON g.groupID = gv.groupID " + 
                                "LEFT OUTER JOIN vddlog l ON l.vehicleID = v.vehicleID WHERE " + 
                                "NOT EXISTS (SELECT  * FROM vddlog _l WHERE _l.vehicleID = v.vehicleID) " +
                                "AND g.groupID IN (:groupList) ";
                            
    
    /*
     * (non-Javadoc)
     * @see com.inthinc.pro.dao.EventAggregationDAO#findRecentEventByDevice(java.util.List, org.joda.time.Interval)
     */
    @Override
    public List<LastReportedEvent> findRecentEventByDevice(List<Integer> groupIDs, Interval interval) {
        
        /* 
         * First load all last reported events which fall before the start time of the interval. 
         * If a vehicle doesn't have a device currently, still load it and the last note received for that vehicle from the 
         * last device which was assigned to it.
         */
        StringBuilder lastNoteQueryStringBuilder = new StringBuilder();
        //Build a query for each note table
        for(int i = 0;i < 32;i++){
            String tableNum = Integer.toString(i);
            if(tableNum.length() == 1){
                tableNum = String.format("0%s", tableNum);
            }
            lastNoteQueryStringBuilder.append(String.format(SELECT_LAST_NOTE_TEMPLATE_INNER, tableNum,tableNum));
            if(i < 31){
                lastNoteQueryStringBuilder.append(" UNION ");
            }
        }
        
        String lastNotQuery = String.format(SELECT_LAST_NOTE_TEMPLATE, lastNoteQueryStringBuilder.toString());
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupList", groupIDs);
        params.put("startDate", interval.getStart().toDate());
        
        if(logger.isDebugEnabled()){
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
                event.setTime( rs.getTimestamp("noteTime"));
                event.setDeviceID(rs.getInt("deviceID"));
                event.setDeviceSerialNum(rs.getString("serialNum"));
                event.setDaysSince(rs.getInt("daysSince"));
                event.setGroupID(rs.getInt("groupID"));
                event.setGroupName(rs.getString("groupName"));
                event.setLastUnassignedDate(rs.getDate("deviceUnassignedDate"));
                event.setLastAssignedDate(rs.getDate("deviceAssignedDate"));
                return event;
            }
        },params);
        
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

}

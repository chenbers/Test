package com.inthinc.pro.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
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
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventType;
import com.inthinc.pro.model.event.NoteType;

public class EventAggregationJDBCDAO extends SimpleJdbcDaoSupport implements EventAggregationDAO {
    
    /* Query to return the total number of forgiven events for a single driver by event type */
    private static final String SELECT_FORGIVEN_EVENT_TOTALS = "SELECT cnv.driverID AS 'driverId', cnv.driverName AS 'driverName', cnv.type AS 'type',cnv.aggType as 'aggType',g.groupID as 'groupID', g.name AS 'groupName', count(noteID) AS 'eventCount', " + 
                    "(SELECT count(*) FROM cachedNoteView cnv1 WHERE cnv1.driverID = cnv.driverID AND cnv1.type = cnv.type AND forgiven = 1) AS 'eventCountForgiven' " + 
                    "FROM cachedNoteView cnv  INNER JOIN groups g ON g.groupID = cnv.driverGroupID " + 
                    "WHERE cnv.driverGroupID IN (:groupList) AND cnv.time BETWEEN :startDate AND :endDate GROUP BY cnv.driverID,cnv.type";
    
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
    
    private static final String SELECT_LAST_NOTE_TEMPLATE = "SELECT * from (%s) a GROUP by deviceID ORDER by serialNum ASC;";
    private static final String SELECT_LAST_NOTE_TEMPLATE_INNER = "select n.noteID,n.time,n.type,n.deviceID,d.serialNum,n.vehicleID,v.name AS 'vehicleName',g.groupID,g.name AS 'groupName' from note%s n " + 
                                "INNNER JOIN (select _n.deviceID,_n.vehicleID,max(_n.time) AS 'maxTime' from note%s _n group by _n.deviceID,_n.vehicleID) groupedNote " + 
                                "ON groupedNote.maxtime = n.time AND groupedNote.deviceID = n.deviceID " + 
                                "INNER JOIN device d on d.deviceID = n.deviceID INNER JOIN vddlog l ON n.deviceID = l.deviceID INNER JOIN vehicle v on v.vehicleID = l.vehicleID" + 
                                "INNER JOIN groupVehicleFlat gv ON gv.vehicleID = v.vehicleID INNER JOIN groups g ON g.groupID = gv.groupID " + 
                                "WHERE n.time BETWEEN :startDate AND :endDate AND g.groupID IN (:groupList) AND l.stop IS NULL";
    
    /*
     * (non-Javadoc)
     * @see com.inthinc.pro.dao.EventAggregationDAO#findRecentEventByDevice(java.util.List, org.joda.time.Interval)
     */
    @Override
    public List<Event> findRecentEventByDevice(List<Integer> groupIDs, Interval interval) {
        
        StringBuilder lastNoteQueryStringBuilder = new StringBuilder();
        //Build a query for each note table
        for(int i = 0;i < 32;i++){
            String tableNum = Integer.toString(i);
            if(tableNum.length() == 1){
                tableNum = String.format("0%s", tableNum);
            }
            String.format(SELECT_LAST_NOTE_TEMPLATE_INNER, tableNum,tableNum);
            if(i < 31){
                lastNoteQueryStringBuilder.append(" UNION ");
            }
        }
        
        String lastNotQuery = String.format(SELECT_LAST_NOTE_TEMPLATE, lastNoteQueryStringBuilder.toString());
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupList", groupIDs);
        params.put("endDate", interval.getEnd().toDate());
        params.put("startDate", interval.getStart().toDate());
        getSimpleJdbcTemplate().query(lastNotQuery, new ParameterizedRowMapper<DriverForgivenEventTotal>() {
            @Override
            public DriverForgivenEventTotal mapRow(ResultSet rs, int rowNum) throws SQLException {
                
                return null;
            }
        },params);
        return null;
    }

}

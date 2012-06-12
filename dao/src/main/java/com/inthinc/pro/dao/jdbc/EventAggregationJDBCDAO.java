package com.inthinc.pro.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.Interval;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.inthinc.pro.dao.EventAggregationDAO;
import com.inthinc.pro.model.aggregation.DriverForgivenEventTotal;
import com.inthinc.pro.model.event.EventType;

public class EventAggregationJDBCDAO extends SimpleJdbcDaoSupport implements EventAggregationDAO {
    
    /* Query to return the total number of forgiven events for a single driver by event type */
    private static final String SELECT_FORGIVEN_EVENT_TOTALS = "SELECT cnv.driverID AS 'driverId', cnv.driverName AS 'driverName', cnv.type AS 'type', g.name AS 'groupName', count(noteID) AS 'eventCount', " + 
                    "(SELECT count(*) FROM cachedNoteView cnv1 WHERE cnv1.driverID = cnv.driverID AND cnv1.type = cnv.type AND forgiven = 1) AS 'eventCountForgiven' " + 
                    "FROM cachedNoteView cnv  INNER JOIN groups g ON g.groupID = cnv.driverGroupID " + 
                    "WHERE cnv.driverGroupID IN (:groupList) AND cnv.time >= :startDate AND cnv.time < :endDate GROUP BY cnv.driverID,cnv.type";
    
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
        return getSimpleJdbcTemplate().query(forgivenEventTotals, new ParameterizedRowMapper<DriverForgivenEventTotal>() {
            @Override
            public DriverForgivenEventTotal mapRow(ResultSet rs, int rowNum) throws SQLException {
                DriverForgivenEventTotal driverForgivenEventTotal = new DriverForgivenEventTotal();
                driverForgivenEventTotal.setDriverID(rs.getInt("driverID"));
                driverForgivenEventTotal.setDriverName(rs.getString("driverName"));
                driverForgivenEventTotal.setGroupID(rs.getInt("groupID"));
                driverForgivenEventTotal.setGroupName(rs.getString("groupName"));
                driverForgivenEventTotal.setEventCount(rs.getInt("eventCount"));
                driverForgivenEventTotal.setEventType(EventType.getEventType(rs.getInt("type")));
                driverForgivenEventTotal.setEventCountForgiven(rs.getInt("eventCountForgiven"));
                Double percentForgiven = Double.valueOf(driverForgivenEventTotal.getEventCount()/driverForgivenEventTotal.getEventCountForgiven());
                driverForgivenEventTotal.setPercentForgiven(percentForgiven);
                return driverForgivenEventTotal;
            }           
        } , params);
    }

}

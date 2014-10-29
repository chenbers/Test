package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventAggregationDAO;
import com.inthinc.pro.dao.EventStatisticsDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.aggregation.DriverForgivenEventTotal;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventType;
import com.inthinc.pro.model.event.LastReportedEvent;
import com.inthinc.pro.model.event.NoteType;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.security.InvalidParameterException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * DAO for statistical information relating to events.
 */
public class EventStatisticsJDBCDAO extends SimpleJdbcDaoSupport implements EventStatisticsDAO {
    private static SimpleDateFormat dfUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private static final String MAX_SPEEDING_HEAD = "COALESCE(MAX(c.topSpeed),0) maxSpeed";
    private static final String SPEEDING_TIME_HEAD = "SUM(COALESCE(duration,0)) speedingTime";
    private static final List<NoteType> SPEEDING_NOTE_TYPES = Arrays.asList(NoteType.SPEEDING, NoteType.SPEEDING_AV, NoteType.SPEEDING_EX, NoteType.SPEEDING_EX2, NoteType.SPEEDING_EX3, NoteType.SPEEDING_EX4, NoteType.SPEEDING_LOG4);


    @Override
    public Integer getMaxSpeedForPastDays(Integer driverID, Integer numDays, Integer includeForgiven, Date endDate) {
        // verify
        if (driverID == null)
            throw new InvalidParameterException("DriverId is null.");

        if (numDays == null)
            throw new InvalidParameterException("NumDays is null.");

        if (includeForgiven == null)
            includeForgiven = 1;

        if (endDate == null)
            endDate = new Date();

        // now and now minus numDays
        DateTime now = new DateTime(endDate);
        DateTime nowMinusDays = now.minusDays(numDays);

        // get the query
        String query = getQueryString(MAX_SPEEDING_HEAD, driverID, nowMinusDays.toDate(), now.toDate(), SPEEDING_NOTE_TYPES, EventStatisticsDAO.INCLUDE_FORGIVEN, dfUTC);

        // execute and return
        return getJdbcTemplate().queryForInt(query);
    }

    @Override
    public Integer getSpeedingTimeInSecondsForPastDays(Integer driverID, Integer numDays, Integer includeForgiven, Date endDate) {
        // verify
        if (driverID == null)
            throw new InvalidParameterException("DriverId is null.");

        if (numDays == null)
            throw new InvalidParameterException("NumDays is null.");

        if (endDate == null)
            endDate = new Date();

        if (includeForgiven == null)
            includeForgiven = 1;

        // now and now minus numDays
        DateTime now = new DateTime(endDate);
        DateTime nowMinusDays = now.minusDays(numDays);

        // get the query
        String query = getQueryString(SPEEDING_TIME_HEAD, driverID, nowMinusDays.toDate(), now.toDate(), SPEEDING_NOTE_TYPES, EventStatisticsDAO.INCLUDE_FORGIVEN, dfUTC);

        // execute and return
        return getJdbcTemplate().queryForInt(query);
    }

    /**
     * Helps build the query string.
     *
     * @param selectData what to select
     * @param driverID driver id
     * @param startDate start date to search from
     * @param endDate end date to search to
     * @param noteTypes note types to include in search
     * @param includeForgiven if it includes forgiven or not
     * @param df date formatter to use on date parameters
     * @return built query
     */
    private String getQueryString(String selectData, Integer driverID, Date startDate, Date endDate, List<NoteType> noteTypes, Integer includeForgiven, final SimpleDateFormat df) {
        StringBuilder builder = new StringBuilder("SELECT ").append(selectData).append(" FROM  cachedNote c")
                .append(" WHERE c.driverID  = ").append(driverID).append("  AND (c.time BETWEEN '").append(df.format(startDate)).append("' AND '").append(df.format(endDate)).append("') ").append(" AND c.type IN (");
        for (int i = 0; i < noteTypes.size(); i++) {
            builder.append(noteTypes.get(i).getCode());
            if (i < noteTypes.size() - 1) {
                builder.append(",");
            }
        }
        builder.append(") ");
        if (includeForgiven == 0) {
            builder.append("AND forgiven<>1 ");
        }
        builder.append(" ORDER BY time DESC");
        return builder.toString();
    }

    @Override
    public Event findByID(Integer integer) {
        throw new NotImplementedException();
    }

    @Override
    public Integer create(Integer integer, Event entity) {
        throw new NotImplementedException();
    }

    @Override
    public Integer update(Event entity) {
        throw new NotImplementedException();
    }

    @Override
    public Integer deleteByID(Integer integer) {
        throw new NotImplementedException();
    }
}

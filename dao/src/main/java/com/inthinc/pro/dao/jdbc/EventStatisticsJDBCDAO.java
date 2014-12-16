package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.EventStatisticsDAO;
import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.aggregation.Speed;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.NoteType;
import org.joda.time.DateTime;
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

    @Override
    public Speed getSpeedInfoForPastDays(Integer driverID, MeasurementType measurementType, Integer numDays, Integer includeForgiven, Date endDate) {
        Speed speed = new Speed();
        if (numDays != 0) {
            speed.setMaxSpeed(getMaxSpeedForPastDays(driverID, numDays, includeForgiven, endDate));
            speed.setSpeedTime(convertToKmIfNeeded(measurementType, getSpeedingTimeInSecondsForPastDays(driverID, numDays, includeForgiven, endDate)));
        }
        return speed;
    }

    @Override
    public Map<Integer, Speed> getSpeedInfoForPersons(List<Person> persons, Integer numberOfDays) {
        // verify
        if (persons == null)
            throw new InvalidParameterException("No persons given.");

        if (numberOfDays == null)
            throw new InvalidParameterException("NumDays is null.");

        Date endDate = new Date();
        Integer includeForgiven = 1;

        // now and now minus numDays
        DateTime now = new DateTime(endDate);
        DateTime nowMinusDays = now.minusDays(numberOfDays);

        // Driver id list
        List<Integer> driverIDList = new ArrayList<Integer>();
        for (Person person: persons){
            driverIDList.add(person.getDriverID());
        }

        List<Integer> noteTypes = new ArrayList<Integer>();
        for (NoteType noteType: SPEEDING_NOTE_TYPES){
            noteTypes.add(noteType.getCode());
        }

        // get the query
        String query = "select COALESCE(MAX(topSpeed), 0) maxSpeed, " +
                "       SUM(COALESCE(duration, 0)) speedingTime, " +
                "       driverID " +
                " from " +
                "(select topSpeed, duration, type, driverID, time from cachedNote " +
                " where driverID in (:driverIDList) and type in (:noteTypes) " +
                " and (time between '" + dfUTC.format(nowMinusDays.toDate()) + "' and '" + dfUTC.format(now.toDate()) + "') " +
                " ORDER BY time DESC) a " +
                " group by driverID ";

        // request
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("driverIDList", driverIDList);
        params.put("noteTypes", noteTypes);

        List<Speed> speedList = getSimpleJdbcTemplate().query(query, new ParameterizedRowMapper<Speed>() {
            @Override
            public Speed mapRow(ResultSet rs, int rowNum) throws SQLException {
                Speed speed = new Speed();
                speed.setMaxSpeed(rs.getInt("maxSpeed"));
                speed.setSpeedTime(rs.getInt("speedingTime"));
                speed.setDriverID(rs.getInt("driverID"));
                return speed;
            }
        }, params);

        // transform to map
        Map<Integer, Speed> speedMap = new HashMap<Integer, Speed>(speedList.size());
        for (Speed speed: speedList){
            speedMap.put(speed.getDriverID(), speed);
        }
        return speedMap;
    }

    /**
     * Converts a value from miles (db) to km if needed based on the given measurement type.
     *
     * @param measurementType measurement type
     * @param value           value
     * @return converted value (if needed)
     */
    private Integer convertToKmIfNeeded(MeasurementType measurementType, Integer value) {
        if (measurementType == null)
            return value;

        if (value == null)
            return null;

        if (measurementType.equals(MeasurementType.ENGLISH))
            return value;

        return MeasurementConversionUtil.fromMilesToKilometers(value).intValue();
    }

    /**
     * Helps build the query string.
     *
     * @param selectData      what to select
     * @param driverID        driver id
     * @param startDate       start date to search from
     * @param endDate         end date to search to
     * @param noteTypes       note types to include in search
     * @param includeForgiven if it includes forgiven or not
     * @param df              date formatter to use on date parameters
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

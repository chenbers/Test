package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.CrashReportDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.CrashReport;
import com.inthinc.pro.model.CrashReportStatus;
import com.inthinc.pro.model.ForgivenType;
import com.inthinc.pro.model.Trip;
import com.mysql.jdbc.Statement;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class CrashReportJDBCDAO extends SimpleJdbcDaoSupport implements CrashReportDAO {
    private VehicleDAO vehicleDAO;
    private DriverDAO driverDAO;


    private static final String SELECT_CRASH = "select * from crash ";
    private static final String FIND_BY_ID = SELECT_CRASH + " where crashID =:crashID";
    private static final String DELETE_CRASH_REPORT_BY_ID = "DELETE FROM crash WHERE crashID = ?";
    private static final String UPDATE_CRASH_REPORT = "UPDATE crash set driverID=?, vehicleID=?, count=?, status=?, hasTrace=?, time=?, lat=?, lng=?, noteID=?, weather=?, description=? where crashID =?";
    private static final String INSERT_CRASH_REPORT = "INSERT INTO crash (driverID, vehicleID, count, status, hasTrace, time, lat, lng, noteID, weather, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String FIND_CRASH_REPORT_BY_GROUP_ID = "SELECT c.crashID, c.driverID, c.vehicleID, c.count, c.status,c.hasTrace,c.time, c.lat,c.lng,c.deviceID,c.noteID,c.address,c.weather,c.description FROM crash c, driver d WHERE c.driverID=d.driverID AND d.groupID=:groupID AND c.time<=:stopDate AND c.time>=:startDate";

    private ParameterizedRowMapper<CrashReport> crashReportParameterizedRowMapper = new ParameterizedRowMapper<CrashReport>() {
        @Override
        public CrashReport mapRow(ResultSet rs, int rowNum) throws SQLException {
            CrashReport crashReport = new CrashReport();
            crashReport.setCrashReportID(rs.getInt("crashID"));
            crashReport.setDriverID(getIntOrNullFromRS(rs, "driverID"));
            crashReport.setVehicleID(getIntOrNullFromRS(rs, "vehicleID"));
            crashReport.setOccupantCount(getIntOrNullFromRS(rs, "count"));
            crashReport.setCrashReportStatus(rs.getObject("status") == null ? null : CrashReportStatus.valueOf(rs.getInt("status")));
            crashReport.setHasTrace(getIntOrNullFromRS(rs, "hasTrace"));
            crashReport.setDate(rs.getTimestamp("time"));
            crashReport.setLat(getDoubleOrNullFromRS(rs, "lat"));
            crashReport.setLng(getDoubleOrNullFromRS(rs, "lng"));
            crashReport.setNoteID(getLongOrNullFromRS(rs,"noteID"));
            crashReport.setWeather(getStringOrNullFromRS(rs, "weather"));
            crashReport.setDescription(getStringOrNullFromRS(rs, "description"));

            return crashReport;
        }
    };

    @Override
    public List<CrashReport> findByGroupID(Integer groupID) {
        return findByGroupID(groupID, ForgivenType.INCLUDE);
    }

    @Override
    public List<CrashReport> findByGroupID(Integer groupID, ForgivenType forgivenType) {
        DateTime startTime = new DateTime(1960, 1, 1, 0, 0, 0, 0);
        DateTime stopTime = new DateTime();
        return findByGroupID(groupID, startTime.toDate(), stopTime.plusDays(3).toDate(), forgivenType);
    }


    @Override
    public List<CrashReport> findByGroupID(Integer groupID, Date startDate, Date stopDate, ForgivenType forgivenType) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("groupID", groupID);
            params.put("startDate", startDate);
            params.put("stopDate", stopDate);
            params.put("forgiventType", forgivenType);

            StringBuilder crashReportByGroupID = new StringBuilder();
            crashReportByGroupID.append(FIND_CRASH_REPORT_BY_GROUP_ID);
            List<CrashReport> crashReports = getSimpleJdbcTemplate().query(crashReportByGroupID.toString(), crashReportParameterizedRowMapper, params);
            return crashReports;


        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }

    }

    @Override
    public Trip getTrip(CrashReport crashReport) {
        if (crashReport != null && crashReport.getVehicleID() != null) {
            DateTime crashTime = new DateTime(crashReport.getDate());
            List<Trip> tripList = vehicleDAO.getTrips(crashReport.getVehicleID(), crashTime.minusDays(1).toDate(), crashTime.plusDays(1).toDate());

            if (tripList != null && tripList.size() > 0) {
                for (Trip trip : tripList) {
                    DateTime startTime = new DateTime(trip.getStartTime());
                    DateTime endTime = new DateTime(trip.getEndTime());
                    if (crashTime.isAfter(startTime.minusSeconds(1).getMillis()) && crashTime.isBefore(endTime.plusSeconds(1).getMillis())) {
                        //It seems that if the crash time occurred between a trips start and end time, that would indicate we have the trip that the crash is associated with.
                        //If for some reason it turns out that a driver can some how have trips that overlap, then we need to loop through the trips LatLngs and determine the correct trip that crash was a
                        //part of. The method below (locateTripInList) only looks for exact latlng matches. Trip event data contains LatLngs that are not as precise as the Crash LatLng.
                        //                    for (LatLng latlng : trip.getRoute()) {
                        //
                        //                    }
                        return trip;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Integer forgiveCrash(Integer groupID) {
        throw new NotImplementedException();
    }

    @Override
    public Integer unforgiveCrash(Integer groupID) {
        throw new NotImplementedException();
    }

    @Override
    public CrashReport findByID(Integer crashID) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("crashID", crashID);

        StringBuilder crashReportSelect = new StringBuilder();
        crashReportSelect.append(FIND_BY_ID);
        CrashReport crashReport = null;
        try {
            crashReport = getSimpleJdbcTemplate().queryForObject(crashReportSelect.toString(), crashReportParameterizedRowMapper, params);
        } catch (EmptyResultSetException e) {
            return null;
        }
        if (crashReport.getVehicleID() != null) {
            crashReport.setVehicle(vehicleDAO.findByID(crashReport.getVehicleID()));
        }
        if (crashReport.getDriverID() != null) {
            crashReport.setDriver(driverDAO.findByID(crashReport.getDriverID()));
        }
        return crashReport;
    }

    @Override
    public Integer create(Integer integer, final CrashReport crash) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_CRASH_REPORT, Statement.RETURN_GENERATED_KEYS);

                if (crash.getDriverID() == null) {
                    ps.setNull(1, Types.NULL);
                } else {
                    ps.setInt(1, crash.getDriverID());
                }

                if (crash.getVehicleID() == null) {
                    ps.setNull(2, Types.NULL);
                } else {
                    ps.setInt(2, crash.getVehicleID());
                }

                if (crash.getOccupantCount() == null) {
                    ps.setNull(3, Types.NULL);
                } else {
                    ps.setInt(3, crash.getOccupantCount());
                }

                if (crash.getCrashReportStatus() == null) {
                    ps.setNull(4, Types.NULL);
                } else {
                    ps.setInt(4, crash.getCrashReportStatus().getCode());
                }

                if (crash.getHasTrace() == null) {
                    ps.setNull(5, Types.NULL);
                } else {
                    ps.setInt(5, crash.getHasTrace());
                }

                if (crash.getDate() == null) {
                    ps.setNull(6, Types.NULL);
                } else {
                    ps.setTimestamp(6, new java.sql.Timestamp(new DateTime(crash.getDate()).getMillis()));
                }

                if (crash.getLat() == null) {
                    ps.setNull(7, Types.NULL);
                } else {
                    ps.setDouble(7, crash.getLat());
                }
                if (crash.getLng() == null) {
                    ps.setNull(8, Types.NULL);
                } else {
                    ps.setDouble(8, crash.getLng());
                }
                if (crash.getNoteID() == null) {
                    ps.setNull(9, Types.NULL);
                } else {
                    ps.setLong(9, crash.getNoteID());
                }
                if (crash.getWeather() == null) {
                    ps.setNull(10, Types.NULL);
                } else {
                    ps.setString(10, crash.getWeather());
                }
                if (crash.getDescription() == null) {
                    ps.setNull(11, Types.NULL);
                } else {
                    ps.setString(11, crash.getDescription());
                }
                logger.debug(ps.toString());
                return ps;
            }

        };
        jdbcTemplate.update(psc, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public Integer update(final CrashReport crash) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(UPDATE_CRASH_REPORT);

                 if (crash.getDriverID() == null) {
                    ps.setNull(1, Types.NULL);
                } else {
                    ps.setInt(1, crash.getDriverID());
                }

                if (crash.getVehicleID() == null) {
                    ps.setNull(2, Types.NULL);
                } else {
                    ps.setInt(2, crash.getVehicleID());
                }

                if (crash.getOccupantCount() == null) {
                    ps.setNull(3, Types.NULL);
                } else {
                    ps.setInt(3, crash.getOccupantCount());
                }

                if (crash.getCrashReportStatus() == null) {
                    ps.setNull(4, Types.NULL);
                } else {
                    ps.setInt(4, crash.getCrashReportStatus().getCode());
                }

                if (crash.getHasTrace() == null) {
                    ps.setNull(5, Types.NULL);
                } else {
                    ps.setInt(5, crash.getHasTrace());
                }

                if (crash.getDate() == null) {
                    ps.setNull(6, Types.NULL);
                } else {
                    ps.setTimestamp(6, new java.sql.Timestamp(new DateTime(crash.getDate()).getMillis()));
                }

                if (crash.getLat() == null) {
                    ps.setNull(7, Types.NULL);
                } else {
                    ps.setDouble(7, crash.getLat());
                }
                if (crash.getLng() == null) {
                    ps.setNull(8, Types.NULL);
                } else {
                    ps.setDouble(8, crash.getLng());
                }
                if (crash.getNoteID() == null) {
                    ps.setNull(9, Types.NULL);
                } else {
                    ps.setLong(9, crash.getNoteID());
                }
                if (crash.getWeather() == null) {
                    ps.setNull(10, Types.NULL);
                } else {
                    ps.setString(10, crash.getWeather());
                }
                if (crash.getDescription() == null) {
                    ps.setNull(11, Types.NULL);
                } else {
                    ps.setString(11, crash.getDescription());
                }

                ps.setInt(12, crash.getCrashReportID());

                logger.debug(ps.toString());
                return ps;
            }
        };
        jdbcTemplate.update(psc);
        return crash.getCrashReportID();
    }

    @Override
    public Integer deleteByID(Integer crashID) {
        return getJdbcTemplate().update(DELETE_CRASH_REPORT_BY_ID, new Object[]{crashID});
    }

    private String getStringOrNullFromRS(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) == null ? null : rs.getString(columnName);
    }

    private Integer getIntOrNullFromRS(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) == null ? null : (int) rs.getLong(columnName);
    }
    private Double getDoubleOrNullFromRS(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) == null ? null : (double) rs.getDouble(columnName);
    }

    private Date getDateOrNullFromRS(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) == null ? null : rs.getDate(columnName);
    }
    private Long getLongOrNullFromRS(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) == null ? null : (long) rs.getLong(columnName);
    }

    public VehicleDAO getVehicleDAO() {
        return vehicleDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    public DriverDAO getDriverDAO() {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

}

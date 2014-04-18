package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.CrashReportDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.CrashReport;
import com.inthinc.pro.model.CrashReportStatus;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.ForgivenType;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.Vehicle;
import com.mysql.jdbc.Statement;
import org.joda.time.DateTime;
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
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrashReportJDBCDAO extends SimpleJdbcDaoSupport implements CrashReportDAO {
    private VehicleDAO vehicleDAO;
    private DriverDAO driverDAO;


    private static final String SELECT_CRASH = "select * from crash";
    private static final String SELECT_FIND_BY_ID =SELECT_CRASH + "where crashID =:crashID";
    private static final String DEL_ADDRESS_BY_ID = "DELETE FROM crash WHERE crashID =:crashID";
    private static final String UPDATE_ADDRESS = "UPDATE crash set crashID=?, driverID=?, vehicleID=?, count=?, status=?, hasTrace=?, time=?, lat=?, lng=?, noteID=?, weather=?, description=? where crashID =:crashID";
    private static final String INSERT_ADDRESS = "INSERT INTO crash (crashID, driverID, vehicleID, count, status, hasTrace, time, lat, lng, noteID, weather, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    private ParameterizedRowMapper<CrashReport> crashReportParameterizedRowMapper = new ParameterizedRowMapper<CrashReport>() {
        @Override
        public CrashReport mapRow(ResultSet rs, int rowNum) throws SQLException {
            CrashReport crashReport = new CrashReport();
            crashReport.setCrashReportID(rs.getInt("crashID"));
            crashReport.setDriverID(rs.getInt("driverID"));
            crashReport.setVehicleID(rs.getInt("vehicleID"));
            crashReport.setOccupantCount(rs.getInt("count"));
            crashReport.setCrashReportStatus(rs.getObject("status") == null ? null : CrashReportStatus.valueOf(rs.getInt("status")));
            crashReport.setHasTrace(rs.getInt("hasTrace"));
            crashReport.setDate(rs.getDate("time"));
            crashReport.setLat(rs.getDouble("lat"));
            crashReport.setLng(rs.getDouble("lng"));
            crashReport.setNoteID((rs.getLong("noteID")));
            crashReport.setWeather(rs.getString("weather"));
            crashReport.setDescription(rs.getString("desc"));

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
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("groupID", groupID);
//        params.put("startDate", startDate);
//        params.put("stopDate", stopDate);
//        vehicleDAO.getVehiclesInGroupHierarchy(groupID)
//
////        StringBuilder crashReportSelect = new StringBuilder();
////        crashReportSelect.append(SELECT_FIND_BY_GROUP);
////        List<CrashReport> crashReports = getSimpleJdbcTemplate().query(crashReportSelect.toString(), crashReportParameterizedRowMapper, params);
////         return crashReports;
//
//        try {
//            List<Map<String, Object>> crashReportList = getSiloService().getCrashes(groupID, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(stopDate),
//                    forgivenType.getCode());
//            List<CrashReport> crashReports = getMapper().convertToModelObject(crashReportList, CrashReport.class);
//            Map<Integer, Vehicle> vehicleMap = new HashMap<Integer, Vehicle>();
//            Map<Integer, Driver> driverMap = new HashMap<Integer, Driver>();
//            Map<Integer, Group> groupMap = new HashMap<Integer, Group>();
//            for (CrashReport crashReport : crashReports) {
//                if (!vehicleMap.containsKey(crashReport.getVehicleID()))
//                    vehicleMap.put(crashReport.getVehicleID(), vehicleDAO.findByID(crashReport.getVehicleID()));
//                if (!driverMap.containsKey(crashReport.getDriverID()))
//                    driverMap.put(crashReport.getDriverID(), driverDAO.findByID(crashReport.getDriverID()));
//                crashReport.setVehicle(vehicleMap.get(crashReport.getVehicleID()));
//                crashReport.setDriver(driverMap.get(crashReport.getDriverID()));
//            }
//            return crashReports;
//        } catch (EmptyResultSetException e) {
//            return Collections.emptyList();
//        }
        throw new NotImplementedException();
    }

    @Override
    public Trip getTrip(CrashReport crashReport) {
        if (crashReport != null && crashReport.getVehicleID() != null)
        {
            DateTime crashTime = new DateTime(crashReport.getDate());
            List<Trip> tripList = vehicleDAO.getTrips(crashReport.getVehicleID(), crashTime.minusDays(1).toDate(), crashTime.plusDays(1).toDate());

            if(tripList != null && tripList.size() > 0) {
                for (Trip trip : tripList) {
                    DateTime startTime = new DateTime(trip.getStartTime());
                    DateTime endTime = new DateTime(trip.getEndTime());
                    if(crashTime.isAfter(startTime.minusSeconds(1).getMillis()) && crashTime.isBefore(endTime.plusSeconds(1).getMillis())) {
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
        crashReportSelect.append(SELECT_FIND_BY_ID);
        CrashReport crashReport = null;
           try{
        crashReport = getSimpleJdbcTemplate().queryForObject(crashReportSelect.toString(), crashReportParameterizedRowMapper, params);
           }catch (EmptyResultSetException e){
               return null;
           }
        if(crashReport.getVehicleID() != null) {
            crashReport.setVehicle(vehicleDAO.findByID(crashReport.getVehicleID()));
        }
        if(crashReport.getDriverID() != null) {
            crashReport.setDriver(driverDAO.findByID(crashReport.getDriverID()));
        }
        return crashReport;
    }

    @Override
    public Integer create(Integer integer,  final CrashReport crashID) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_ADDRESS, Statement.RETURN_GENERATED_KEYS);

                ps.setInt(1, crashID.getAccountID());

                if (crashID.getDriverID() == null) {
                    ps.setNull(2, Types.NULL);
                } else {
                    ps.setInt(2, crashID.getDriverID());
                }

                if (crashID.getVehicleID() == null) {
                    ps.setNull(3, Types.NULL);
                } else {
                    ps.setInt(3, crashID.getVehicleID());
                }

                if (crashID.getOccupantCount() == null) {
                    ps.setNull(4, Types.NULL);
                } else {
                    ps.setInt(4, crashID.getOccupantCount());
                }

                if (crashID.getCrashReportStatus() == null) {
                    ps.setNull(5, Types.NULL);
                } else {
                    ps.setInt(5, crashID.getCrashReportStatus().getCode());
                }

                if (crashID.getHasTrace() == null) {
                    ps.setNull(6, Types.NULL);
                } else {
                    ps.setInt(6, crashID.getHasTrace());
                }

                if (crashID.getDate() == null) {
                    ps.setNull(7, Types.NULL);
                } else {
                    ps.setDate(7, new java.sql.Date(crashID.getDate().getTime()));
                 }

                if (crashID.getLat() == null) {
                    ps.setNull(8, Types.NULL);
                } else {
                    ps.setDouble(8, crashID.getLat());
                }
                if (crashID.getLng() == null) {
                    ps.setNull(9, Types.NULL);
                } else {
                    ps.setDouble(9, crashID.getLng());
                }
                if (crashID.getNoteID() == null) {
                    ps.setNull(10, Types.NULL);
                } else {
                    ps.setLong(10, crashID.getNoteID());
                }
                if (crashID.getWeather() == null) {
                    ps.setNull(11, Types.NULL);
                } else {
                    ps.setString(11, crashID.getWeather());
                }
                if (crashID.getDescription() == null) {
                    ps.setNull(12, Types.NULL);
                } else {
                    ps.setString(12, crashID.getDescription());
                }
                logger.debug(ps.toString());
                return ps;
            }

        };
        jdbcTemplate.update(psc, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public Integer update(final CrashReport crashID) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(UPDATE_ADDRESS);

                ps.setInt(1, crashID.getAccountID());

                if (crashID.getDriverID() == null) {
                    ps.setNull(2, Types.NULL);
                } else {
                    ps.setInt(2, crashID.getDriverID());
                }

                if (crashID.getVehicleID() == null) {
                    ps.setNull(3, Types.NULL);
                } else {
                    ps.setInt(3, crashID.getVehicleID());
                }

                if (crashID.getOccupantCount() == null) {
                    ps.setNull(4, Types.NULL);
                } else {
                    ps.setInt(4, crashID.getOccupantCount());
                }

                if (crashID.getCrashReportStatus() == null) {
                    ps.setNull(5, Types.NULL);
                } else {
                    ps.setInt(5, crashID.getCrashReportStatus().getCode());
                }

                if (crashID.getHasTrace() == null) {
                    ps.setNull(6, Types.NULL);
                } else {
                    ps.setInt(6, crashID.getHasTrace());
                }

                if (crashID.getDate() == null) {
                    ps.setNull(7, Types.NULL);
                } else {
                    ps.setDate(7, new java.sql.Date(crashID.getDate().getTime()));
                }

                if (crashID.getLat() == null) {
                    ps.setNull(8, Types.NULL);
                } else {
                    ps.setDouble(8, crashID.getLat());
                }
                if (crashID.getLng() == null) {
                    ps.setNull(9, Types.NULL);
                } else {
                    ps.setDouble(9, crashID.getLng());
                }
                if (crashID.getNoteID() == null) {
                    ps.setNull(10, Types.NULL);
                } else {
                    ps.setLong(10, crashID.getNoteID());
                }
                if (crashID.getWeather() == null) {
                    ps.setNull(11, Types.NULL);
                } else {
                    ps.setString(11, crashID.getWeather());
                }
                if (crashID.getDescription() == null) {
                    ps.setNull(12, Types.NULL);
                } else {
                    ps.setString(12, crashID.getDescription());
                }

//                 ps.setInt(13, crashID.getCrashReportID());

                logger.debug(ps.toString());
                return ps;
            }
        };
        jdbcTemplate.update(psc);
        return crashID.getCrashReportID();
    }

    @Override
    public Integer deleteByID(Integer crashID) {
        return getJdbcTemplate().update(DEL_ADDRESS_BY_ID, new Object[]{crashID});
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

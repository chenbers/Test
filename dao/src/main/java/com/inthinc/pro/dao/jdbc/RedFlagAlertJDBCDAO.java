package com.inthinc.pro.dao.jdbc;


import com.inthinc.pro.dao.RedFlagAlertDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.*;
import com.mysql.jdbc.Statement;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class RedFlagAlertJDBCDAO extends SimpleJdbcDaoSupport implements RedFlagAlertDAO {

    private static final String RED_FLAG_ALERT = "select * from alert";
    private static final String FIND_ALERT_BY_ID = RED_FLAG_ALERT + " where alertID=:alertID and status <> 3";
    private static final String GET_BY_ACCTID = RED_FLAG_ALERT + " where acctID=:acctID and status <> 3";
    private static final String GET_BY_USERID = RED_FLAG_ALERT + " where userID=:userID and status <> 3";
    private static final String GET_BY_GROUPID = RED_FLAG_ALERT + " where fwd_teamGroupID=:fwd_teamGroupID and status <> 3";
    private static final String DEL_BY_ID = "DELETE FROM alert WHERE alertID = ?";

    private static final String ALERT_GROUP = "select * from alertGroup";
    private static final String FIND_ALERT_GROUP_BY_ALERT_ID = ALERT_GROUP + " where alertID = :alertID";
    private static final String UPDATE_ALERT_GROUP_BY_ID = "update alertGroup set alertID = ?, groupID = ? where alertGroupID = :alertGroupID";
    private static final String DELETE_ALERT_GROUP_BY_ID = "delete from alertGroup where alertGroupID = ?";
    private static final String INSERT_ALERT_GROUP = "insert into alertGroup (alertID, groupID) values (?, ?)";

    private static  final String INSERT_INTO = "INSERT INTO alert (alertTypeMask, alertType, type,  status,  modified,  acctID, userID,  name,  description,  startTOD,  stopTOD, dayOfWeekMask, vtypeMask,  speedSettings, " +
                    "accel, brake, turn,  vert,  severityLevel,  zoneID, escalationTryLimit, escalationTryTimeLimit, escalationCallDelay, idlingThreshold, notifyManagers) VALUES " +
                    "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private ParameterizedRowMapper<RedFlagAlertAssignItem> redFlagAlertGroupRowMapper = new ParameterizedRowMapper<RedFlagAlertAssignItem>() {
        @Override
        public RedFlagAlertAssignItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            RedFlagAlertAssignItem item = new RedFlagAlertAssignItem();
            item.setId(rs.getInt("alertGroupID"));
            item.setRedFlagId(rs.getInt("alertId"));
            item.setItemId(rs.getInt("groupID"));

            return item;
        }
    };

    private ParameterizedRowMapper<RedFlagAlert> redFlagAlertParameterizedRowMapper = new ParameterizedRowMapper<RedFlagAlert>() {
        @Override
        public RedFlagAlert mapRow(ResultSet rs, int rowNum) throws SQLException {
            RedFlagAlert redFlagAlert = new RedFlagAlert();
            redFlagAlert.setAlertID(rs.getInt("alertID"));

            // special mask for alert message types
            redFlagAlert.setTypes(AlertMessageType.getAlertMessageTypes(getLongOrNullFromRS(rs, "alertTypeMask")));

            redFlagAlert.setStatus(Status.valueOf(rs.getInt("status")));
            redFlagAlert.setModified(getDateOrNullFromRS(rs, "modified"));
            redFlagAlert.setAccountID(rs.getInt("acctID"));
            redFlagAlert.setUserID(rs.getInt("userID"));
            redFlagAlert.setName(rs.getString("name"));
            redFlagAlert.setDescription(rs.getString("description"));
            redFlagAlert.setStartTOD(rs.getInt("startTOD"));
            redFlagAlert.setStopTOD(rs.getInt("stopTOD"));

            redFlagAlert.setZoneID(getIntOrNullFromRS(rs,"zoneID"));
            redFlagAlert.setSeverityLevel(RedFlagLevel.valueOf(rs.getInt("severityLevel")));
            redFlagAlert.setHardAcceleration(getIntOrNullFromRS(rs,"accel"));
            redFlagAlert.setHardBrake(getIntOrNullFromRS(rs,"brake"));
            redFlagAlert.setHardTurn(getIntOrNullFromRS(rs,"turn"));
            redFlagAlert.setHardVertical(getIntOrNullFromRS(rs,"vert"));
            redFlagAlert.setMaxEscalationTries(getIntOrNullFromRS(rs,"escalationTryLimit"));
            redFlagAlert.setMaxEscalationTryTime(getIntOrNullFromRS(rs,"escalationTryTimeLimit"));

            // special mask for day of week
            redFlagAlert.setDayOfWeek(getDaysOfWeek(getLongOrNullFromRS(rs, "dayOfWeekMask")));

            redFlagAlert.setIdlingThreshold(getIntOrNullFromRS(rs,"idlingThreshold"));
            redFlagAlert.setNotifyManagers(rs.getBoolean("notifyManagers"));

            // special mask for vehicle types
            redFlagAlert.setVehicleTypes(VehicleType.getVehicleTypes(getLongOrNullFromRS(rs, "vtypeMask")));


            List<Integer> groupIds = new ArrayList<Integer>();
//            groupIds.add(rs.getInt("fwd_teamgroupId"))  ;
            redFlagAlert.setGroupIDs(groupIds);

            redFlagAlert.setEscalationTimeBetweenRetries(getIntOrNullFromRS(rs,"escalationCallDelay"));
//
//            List<AlertEscalationItem> escalation = new ArrayList<AlertEscalationItem>();
//            if (getIntOrNullFromRS(rs,"vtypeMask")!=null) {
//                escalation.add(rs.getString(""));
//            }

//            redFlagAlert.setEscalationList(rs.getObject(""));
//            List<Integer> driverIDs = new ArrayList<Integer>();
//            driverIDs.add(rs.getInt("fwd_teamgroupId"))  ;
//            redFlagAlert.setDriverIDs(driverIDs);

            return redFlagAlert;
        }
    };

    @Override
    public List<RedFlagAlert> getRedFlagAlerts(Integer accountID) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("accountID", accountID);
            StringBuilder redFlagAlerts = new StringBuilder(GET_BY_ACCTID);

            List<RedFlagAlert> redFlagAlertList = getSimpleJdbcTemplate().query(redFlagAlerts.toString(), redFlagAlertParameterizedRowMapper, params);

            return redFlagAlertList;

        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<RedFlagAlert> getRedFlagAlertsByUserID(Integer userID) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("userID", userID);
            StringBuilder redFlagAlerts = new StringBuilder(GET_BY_USERID);

            List<RedFlagAlert> redFlagAlerByUser = getSimpleJdbcTemplate().query(redFlagAlerts.toString(), redFlagAlertParameterizedRowMapper, params);

            return redFlagAlerByUser;

        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }


    @Override
    public List<RedFlagAlert> getRedFlagAlertsByUserIDDeep(Integer userID) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("userID", userID);
            StringBuilder redFlagAlerts = new StringBuilder(GET_BY_USERID);

            List<RedFlagAlert> redFlagAlerByUser = getSimpleJdbcTemplate().query(redFlagAlerts.toString(), redFlagAlertParameterizedRowMapper, params);

            return redFlagAlerByUser;

        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<RedFlagAlert> getAlertsByTeamGroupID(Integer groupID) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("groupID", groupID);
            StringBuilder redFlagAlerts = new StringBuilder(GET_BY_GROUPID);

            List<RedFlagAlert> redFlagByGroup = getSimpleJdbcTemplate().query(redFlagAlerts.toString(), redFlagAlertParameterizedRowMapper, params);

            return redFlagByGroup;

        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }


    @Override
    public Integer deleteAlertsByZoneID(Integer zoneID) {
        return null;
    }

    @Override
    public RedFlagAlert findByID(Integer alertID) {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("alertID", alertID);
        StringBuilder findAddress = new StringBuilder(FIND_ALERT_BY_ID);

        return getSimpleJdbcTemplate().queryForObject(findAddress.toString(), redFlagAlertParameterizedRowMapper, args);
    }

    @Override
    public Integer create(Integer integer, final RedFlagAlert entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_INTO, Statement.RETURN_GENERATED_KEYS);

                ps.setObject(1, entity.getTypes());

                ps.setInt(2, entity.getAlertID());

                ps.setInt(3, 0);

                ps.setInt(4, entity.getStatus().getCode());

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                df.setTimeZone(TimeZone.getTimeZone("UTC"));
                String modified = df.format(toUTC(new Date()));

                ps.setString(5, modified);

                ps.setInt(6, entity.getAccountID());
                ps.setInt(7, entity.getUserID());

                if (entity.getName() == null) {
                    ps.setNull(8, Types.NULL);
                } else {
                    ps.setString(8, entity.getName());
                }

                if (entity.getDescription() == null) {
                    ps.setNull(9, Types.NULL);
                } else {
                    ps.setString(9, entity.getDescription());
                }


                if (entity.getStartTOD() == null) {
                    ps.setNull(10, Types.NULL);
                } else {
                    ps.setInt(10, entity.getStartTOD());
                }


                if (entity.getStopTOD() == null) {
                    ps.setNull(11, Types.NULL);
                } else {
                    ps.setInt(11, entity.getStopTOD());
                }

                ps.setObject(12, entity.getDayOfWeek());

                if (entity.getVehicleTypes() == null) {
                    ps.setNull(13, Types.NULL);
                } else {
                    ps.setObject(13, entity.getVehicleTypes());
                }

                if (entity.getSpeedSettings() == null) {
                    ps.setNull(14, Types.NULL);
                } else {
                    ps.setObject(14, entity.getSpeedSettings());
                }

                if (entity.getHardAcceleration() == null) {
                    ps.setNull(15, Types.NULL);
                } else {
                    ps.setInt(15, entity.getHardAcceleration());
                }

                if (entity.getHardBrake() == null) {
                    ps.setNull(16, Types.NULL);
                } else {
                    ps.setInt(16, entity.getHardBrake());
                }

                if (entity.getHardTurn() == null) {
                    ps.setNull(17, Types.NULL);
                } else {
                    ps.setInt(17, entity.getHardTurn());
                }

                if (entity.getHardVertical() == null) {
                    ps.setNull(18, Types.NULL);
                } else {
                    ps.setInt(18, entity.getHardVertical());
                }

                if (entity.getSeverityLevel() == null) {
                    ps.setNull(19, Types.NULL);
                } else {
                    ps.setObject(19, entity.getSeverityLevel());
                }

                if (entity.getZoneID() == null) {
                    ps.setNull(20, Types.NULL);
                } else {
                    ps.setInt(20, entity.getZoneID());
                }

                if (entity.getMaxEscalationTries() == null) {
                    ps.setNull(21, Types.NULL);
                } else {
                    ps.setInt(21, entity.getMaxEscalationTries());
                }

                if (entity.getMaxEscalationTryTime() == null) {
                    ps.setNull(22, Types.NULL);
                } else {
                    ps.setInt(22, entity.getMaxEscalationTryTime());
                }

                if (entity.getEscalationTimeBetweenRetries() == null) {
                    ps.setNull(23, Types.NULL);
                } else {
                    ps.setInt(23, entity.getEscalationTimeBetweenRetries());
                }

                if (entity.getIdlingThreshold() == null) {
                    ps.setNull(24, Types.NULL);
                } else {
                    ps.setInt(24, entity.getIdlingThreshold());
                }

                if (entity.getNotifyManagers() == null) {
                    ps.setNull(25, Types.NULL);
                } else {
                    ps.setBoolean(25, entity.getNotifyManagers());
                }


                logger.debug(ps.toString());
                return ps;
            }
        };
        jdbcTemplate.update(psc, keyHolder);
        return keyHolder.getKey().intValue();
    }


    @Override
    public Integer update(final RedFlagAlert entity) {
    return null;
    }



    @Override
    public Integer deleteByID(Integer alertID) {
        return getJdbcTemplate().update(DEL_BY_ID, new Object[]{alertID});
    }


    private List<RedFlagAlertAssignItem> getRedFlagAlertGroupsByID(Integer alertId) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("alertID", alertId);

            return  getSimpleJdbcTemplate().query(FIND_ALERT_GROUP_BY_ALERT_ID, redFlagAlertGroupRowMapper, params);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

    private Integer deleteRedFlagAlertGroupById(Integer alertGroupID) {
        return getJdbcTemplate().update(DELETE_ALERT_GROUP_BY_ID, new Object[]{alertGroupID});
    }

    public Integer createRedFlagAlertGroup(Integer id, final RedFlagAlertAssignItem entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_ALERT_GROUP, Statement.RETURN_GENERATED_KEYS);

                ps.setInt(1, entity.getRedFlagId());
                ps.setInt(2, entity.getItemId());

                logger.debug(ps.toString());
                return ps;
            }
        };
        jdbcTemplate.update(psc, keyHolder);
        return keyHolder.getKey().intValue();
    }

    public Integer updateRedFlagAlertGroup(final RedFlagAlertAssignItem entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(UPDATE_ALERT_GROUP_BY_ID);

                ps.setInt(1, entity.getRedFlagId());
                ps.setInt(2, entity.getItemId());

                //where
                ps.setInt(3, entity.getId());

                logger.debug(ps.toString());
                return ps;
            }
        };
        jdbcTemplate.update(psc);
        return entity.getId();
    }


    private String getStringOrNullFromRS(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) == null ? null : rs.getString(columnName);
    }

    private Integer getIntOrNullFromRS(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) == null ? null : (int) rs.getInt(columnName);
    }

    private Long getLongOrNullFromRS(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) == null ? null : (long) rs.getLong(columnName);
    }

    private Boolean getBoolOrNullFromRs(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) == null ? null : (boolean) rs.getBoolean(columnName);
    }

    private Date getDateOrNullFromRS(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) == null ? null : rs.getDate(columnName);
    }

    private Date toUTC(Date date){
        DateTime dt = new DateTime(date.getTime()).toDateTime(DateTimeZone.UTC);
        return dt.toDate();
    }

    private List<Boolean> getDaysOfWeek(Long dayOfWeekMask){
        List<Boolean> daysOfWeek = new LinkedList<Boolean>();

        if (dayOfWeekMask != null) {
            // for sunday to saturday (sunday = 0, saturday = 6)
            for (int i = 0; i <= 6; i++) {
                daysOfWeek.add(dayOfWeekMatch(i, dayOfWeekMask));
            }
        }

        return daysOfWeek;
    }

    private boolean dayOfWeekMatch(int dayOfWeek, long dayOfWeekMask) {
        return ((dayOfWeekMask & (1 << dayOfWeek)) != 0);
    }
}

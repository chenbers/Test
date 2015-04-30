package com.inthinc.pro.dao.jdbc;


import com.inthinc.pro.dao.RedFlagAlertDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.*;
import com.inthinc.pro.model.configurator.SpeedingConstants;
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
    private static final String FIND_ALERT_BY_ID = RED_FLAG_ALERT + " where alertID=:alertID";
    private static final String GET_BY_ACCTID = RED_FLAG_ALERT + " where acctID=:acctID  and status <> 3";
    private static final String GET_BY_USERID = RED_FLAG_ALERT + " where userID=:userID  and status <> 3";
    private static final String GET_BY_USERID_DEEP = "SELECT * FROM alert a JOIN user u WHERE a.userID=u.userID AND u.userID= :userID  and a.status <> 3";
    private static final String GET_BY_GROUPID = "SELECT * FROM alert a JOIN alertGroup g WHERE a.alertID=g.alertID AND g.groupID= :groupID  and a.status <> 3";
    private static final String DEL_BY_ID = "UPDATE alert SET modified=UTC_TIMESTAMP(), status=3 WHERE alertID = ?";
    private static final String DELETE_BY_ZONE_ID = "UPDATE alert SET modified=UTC_TIMESTAMP(), status=3 WHERE zoneID=:zoneID and (alertTypeMask & 0x18) != 1";

    //AlertGroup
    private static final String ALERT_GROUP = "select * from alertGroup";
    private static final String FIND_ALERT_GROUP_BY_ALERT_ID = ALERT_GROUP + " where alertID = :alertID";
    private static final String UPDATE_ALERT_GROUP_BY_ID = "update alertGroup set alertID = ?, groupID = ? where alertGroupID = ?";
    private static final String DELETE_ALERT_GROUP_BY_ID = "delete from alertGroup where alertGroupID = ?";
    private static final String DELETE_ALERT_GROUP_BY_ALERT_ID = "delete from alertGroup where alertID = ?";
    private static final String INSERT_ALERT_GROUP = "insert into alertGroup (alertID, groupID) values (?, ?)";

    //AlertDriver
    private static final String ALERT_DRIVER = "select * from alertDriver";
    private static final String FIND_ALERT_DRIVER_BY_ALERT_ID = ALERT_DRIVER + " where alertID = :alertID";
    private static final String UPDATE_ALERT_DRIVER_BY_ID = "update alertDriver set alertID = ?, driverID = ? where alertDriverID = ?";
    private static final String DELETE_ALERT_DRIVER_BY_ID = "delete from alertDriver where alertDriverID = ?";
    private static final String DELETE_ALERT_DRIVER_BY_ALERT_ID = "delete from alertDriver where alertID = ?";
    private static final String INSERT_ALERT_DRIVER = "insert into alertDriver (alertID, driverID) values (?, ?)";

    //AlertVehicle
    private static final String ALERT_VEHICLE = "select * from alertVehicle";
    private static final String FIND_ALERT_VEHICLE_BY_ALERT_ID = ALERT_VEHICLE + " where alertID = :alertID";
    private static final String UPDATE_ALERT_VEHICLE_BY_ID = "update alertVehicle set alertID = ?, driverID = ? where alertVehicleID = ?";
    private static final String DELETE_ALERT_VEHICLE_BY_ID = "delete from alertVehicle where alertVehicleID = ?";
    private static final String DELETE_ALERT_VEHICLE_BY_ALERT_ID = "delete from alertVehicle where alertID = ?";
    private static final String INSERT_ALERT_VEHICLE = "insert into alertVehicle (alertID, vehicleID) values (?, ?)";

    //AlertPerson
    private static final String ALERT_PERSON = "select * from alertPerson";
    private static final String FIND_ALERT_PERSON_BY_ALERT_ID = ALERT_PERSON + " where alertID = :alertID";
    private static final String UPDATE_ALERT_PERSON_BY_ID = "update alertPerson set alertID = ?, personID = ? where alertPersonID = ?";
    private static final String DELETE_ALERT_PERSON_BY_ID = "delete from alertPerson where alertPersonID = ?";
    private static final String DELETE_ALERT_PERSON_BY_ALERT_ID = "delete from alertPerson where alertID = ?";
    private static final String INSERT_ALERT_PERSON = "insert into alertPerson (alertID, personID) values (?, ?)";

    //AlertEmail
    private static final String ALERT_EMAIL = "select * from alertEmail";
    private static final String FIND_ALERT_EMAIL_BY_ALERT_ID = ALERT_EMAIL + " where alertID = :alertID";
    private static final String UPDATE_ALERT_EMAIL_BY_ID = "update alertEmail set alertID = ?, email = ? where alertEmailID = ?";
    private static final String DELETE_ALERT_EMAIL_BY_ID = "delete from alertEmail where alertEmailID = ?";
    private static final String DELETE_ALERT_EMAIL_BY_ALERT_ID = "delete from alertEmail where alertID = ?";
    private static final String INSERT_ALERT_EMAIL = "insert into alertEmail (alertID, email) values (?, ?)";

    //AlertEscPerson
    private static final String ALERT_ESCPERS = "select * from alertEscalationPersons";
    private static final String FIND_ALERT_ESCPERS_BY_ALERT_ID = ALERT_ESCPERS + " where alertID = :alertID";
    private static final String UPDATE_ALERT_ESCPERS_BY_ID = "update alertEscalationPersons set alertID = ?, personID = ?, escalationOrder=?, contactType=? where alertPersonID = ?";
    private static final String DELETE_ALERT_ESCPERS_BY_ID = "delete from alertEscalationPersons where alertPersonID = ?";
    private static final String DELETE_ALERT_ESCPERS_BY_ALERT_ID = "delete from alertEscalationPersons where alertID = ?";
    private static final String INSERT_ALERT_ESCPERS = "insert into alertEscalationPersons (alertID, personID, escalationOrder, contactType) values (?, ?, ?, ?)";
    
    //Person auxiliary DAO
    private static final String AUX_PERSON = "select * from person";
    private static final String FIND_AUX_PERSON_BY_ID = AUX_PERSON + " where personID = :personID";


    private static final String INSERT_INTO = "INSERT INTO alert (alertTypeMask, alertType, type,  status,  modified,  acctID, userID,  name,  description,  startTOD,  stopTOD, dayOfWeekMask, vtypeMask, " +
                    "speedSettings, accel, brake, turn,  vert,  severityLevel,  zoneID, escalationTryLimit, escalationTryTimeLimit, escalationCallDelay, idlingThreshold, notifyManagers) VALUES " +
                    "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


    private static final String UPDATE_ALERT = "UPDATE alert set alertTypeMask=?, alertType=?, type=?,  status=?,  modified=?,  acctID=?, userID=?,  name=?,  description=?,  startTOD=?,  stopTOD=?, dayOfWeekMask=?, vtypeMask=?,  speedSettings=?, " +
                    "accel=?, brake=?, turn=?,  vert=?,  severityLevel=?,  zoneID=?, escalationTryLimit=?, escalationTryTimeLimit=?, escalationCallDelay=?, idlingThreshold=?, notifyManagers=? where alertID=?";

    private static final String MAX_SPEED_MARKER = "~";

    private ParameterizedRowMapper<RedFlagAlertAssignItem> redFlagAlertGroupRowMapper = new ParameterizedRowMapper<RedFlagAlertAssignItem>() {
        @Override
        public RedFlagAlertAssignItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            RedFlagAlertAssignItem item = new RedFlagAlertAssignItem();
            item.setId(rs.getInt("alertGroupID"));
            item.setRedFlagId(rs.getInt("alertID"));
            item.setItemId(rs.getInt("groupID"));

            return item;
        }
    };

    private ParameterizedRowMapper<RedFlagAlertAssignItem> redFlagAlertDriverRowMapper = new ParameterizedRowMapper<RedFlagAlertAssignItem>() {
        @Override
        public RedFlagAlertAssignItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            RedFlagAlertAssignItem item = new RedFlagAlertAssignItem();
            item.setId(rs.getInt("alertDriverID"));
            item.setRedFlagId(rs.getInt("alertID"));
            item.setItemId(rs.getInt("driverID"));

            return item;
        }
    };


    private ParameterizedRowMapper<RedFlagAlertAssignItem> redFlagAlertVehicleRowMapper = new ParameterizedRowMapper<RedFlagAlertAssignItem>() {
        @Override
        public RedFlagAlertAssignItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            RedFlagAlertAssignItem item = new RedFlagAlertAssignItem();
            item.setId(rs.getInt("alertVehicleID"));
            item.setRedFlagId(rs.getInt("alertID"));
            item.setItemId(rs.getInt("vehicleID"));

            return item;
        }
    };

    private ParameterizedRowMapper<RedFlagAlertAssignItem> redFlagAlertEmailRowMapper = new ParameterizedRowMapper<RedFlagAlertAssignItem>() {
        @Override
        public RedFlagAlertAssignItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            RedFlagAlertAssignItem item = new RedFlagAlertAssignItem();
            item.setId(rs.getInt("alertEmailID"));
            item.setRedFlagId(rs.getInt("alertID"));
            item.setItem(rs.getString("email"));

            return item;
        }
    };

    private ParameterizedRowMapper<RedFlagAlertAssignItem> redFlagAlertPersonRowMapper = new ParameterizedRowMapper<RedFlagAlertAssignItem>() {
        @Override
        public RedFlagAlertAssignItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            RedFlagAlertAssignItem item = new RedFlagAlertAssignItem();
            item.setId(rs.getInt("alertPersonID"));
            item.setRedFlagId(rs.getInt("alertID"));
            item.setItemId(rs.getInt("personID"));

            return item;
        }
    };

    private ParameterizedRowMapper<AlertEscalationItem> redFlagAlertEscPersRowMapper = new ParameterizedRowMapper<AlertEscalationItem>() {
        @Override
        public AlertEscalationItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            AlertEscalationItem item = new AlertEscalationItem();
            item.setAlertID(rs.getInt("alertID"));
            item.setPersonID(rs.getInt("personID"));
            item.setEscalationOrder(rs.getInt("escalationOrder"));
            item.setContactType(rs.getInt("contactType"));
            item.setAlertPersonID(rs.getInt("alertPersonID"));

            return item;

        }
    };
    
    private ParameterizedRowMapper<String> redFlagAuxPersonRowMapper = new ParameterizedRowMapper<String>() {
        @Override
        public String mapRow(ResultSet rs, int rowNum) throws SQLException {
            String result = "";
            result = rs.getString("first") + " " + rs.getString("last");
            return result;
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
            
            Date modified = getDateTimeOrNullFromRS(rs, "modified");
            redFlagAlert.setModified(modified != null ? new DateTime(modified).toDate() : null);
            redFlagAlert.setAccountID(rs.getInt("acctID"));
            redFlagAlert.setUserID(rs.getInt("userID"));
            redFlagAlert.setName(rs.getString("name"));
            
            List<String> names = getPersonFullNameByAlertID(redFlagAlert.getAlertID());
            redFlagAlert.setFullName((names.isEmpty() || names.size() > 1) ? null : names.get(0));
            redFlagAlert.setDescription(rs.getString("description"));
            redFlagAlert.setStartTOD(convertTimeToNumberOfMins(getTimeOrNullFromRS(rs, "startTOD")));
            redFlagAlert.setStopTOD(convertTimeToNumberOfMins(getTimeOrNullFromRS(rs, "stopTOD")));
            redFlagAlert.setZoneID(getIntOrNullFromRS(rs, "zoneID"));
            redFlagAlert.setSeverityLevel(RedFlagLevel.valueOf(rs.getInt("severityLevel")));
            redFlagAlert.setHardAcceleration(getIntOrNullFromRS(rs, "accel"));
            redFlagAlert.setHardBrake(getIntOrNullFromRS(rs, "brake"));
            redFlagAlert.setHardTurn(getIntOrNullFromRS(rs, "turn"));
            redFlagAlert.setHardVertical(getIntOrNullFromRS(rs, "vert"));
            redFlagAlert.setMaxEscalationTries(getIntOrNullFromRS(rs, "escalationTryLimit"));
            redFlagAlert.setMaxEscalationTryTime(getIntOrNullFromRS(rs, "escalationTryTimeLimit"));

            String ss = getStringOrNullFromRS(rs, "speedSettings");
            if (ss == null || ss.trim().isEmpty()) {
                redFlagAlert.setSpeedSettings(new Integer[SpeedingConstants.INSTANCE.NUM_SPEEDS]);
            } else if (!ss.contains(MAX_SPEED_MARKER)) {
                String[] sss = ss.split(" ");
                Integer[] speedSettings = new Integer[SpeedingConstants.INSTANCE.NUM_SPEEDS];
                for (int i = 0; i < sss.length; i++) {
                    if (!sss[i].trim().isEmpty()) {
                        speedSettings[i] = Integer.parseInt(sss[i]);
                    }
                }
                redFlagAlert.setSpeedSettings(speedSettings);
            } else {
                redFlagAlert.setSpeedSettings(null);
                redFlagAlert.setUseMaxSpeed(true);
                String ssfin = ss.replace("~", "");
                try {
                    redFlagAlert.setMaxSpeed(Integer.valueOf(ssfin));
                } catch (NumberFormatException ex) {
                    redFlagAlert.setUseMaxSpeed(false);
                    redFlagAlert.setMaxSpeed(null);
                }
            }


            // special mask for day of week
            redFlagAlert.setDayOfWeek(getDaysOfWeek(getLongOrNullFromRS(rs, "dayOfWeekMask")));

            redFlagAlert.setIdlingThreshold(getIntOrNullFromRS(rs, "idlingThreshold"));
            redFlagAlert.setNotifyManagers(getBoolOrNullFromRs(rs, "notifyManagers"));

            // special mask for vehicle types
            redFlagAlert.setVehicleTypes(VehicleType.getVehicleTypes(getLongOrNullFromRS(rs, "vtypeMask")));

            // special values for secondary tables
            redFlagAlert.setGroupIDs(getGroupIdsForRedFlag(redFlagAlert.getAlertID()));
            redFlagAlert.setDriverIDs(getDriverIdsForRedFlag(redFlagAlert.getAlertID()));
            redFlagAlert.setVehicleIDs(getVehicleIdsForRedFlag(redFlagAlert.getAlertID()));
            redFlagAlert.setNotifyPersonIDs(getPersonIdsForRedFlag(redFlagAlert.getAlertID()));
            redFlagAlert.setEscalationList(getRedFlagAlertEscPByAlertID(redFlagAlert.getAlertID()));

            redFlagAlert.setEscalationTimeBetweenRetries(getIntOrNullFromRS(rs, "escalationCallDelay"));

            redFlagAlert.setMaxEscalationTryTime(getIntOrNullFromRS(rs, "escalationTryTimeLimit"));

            redFlagAlert.setMaxEscalationTries(getIntOrNullFromRS(rs, "escalationTryLimit"));

            return redFlagAlert;
        }
    };

    private List<Integer> getGroupIdsForRedFlag(Integer redFlagId) {
        List<Integer> groupIds = new LinkedList<Integer>();
        List<RedFlagAlertAssignItem> items = getRedFlagAlertGroupsByAlertID(redFlagId);
        for (RedFlagAlertAssignItem item : items) {
            groupIds.add(item.getItemId());
        }
        return groupIds;
    }


    private List<Integer> getDriverIdsForRedFlag(Integer redFlagId) {
        List<Integer> driverIds = new LinkedList<Integer>();
        List<RedFlagAlertAssignItem> items = getRedFlagAlertDriverByAlertID(redFlagId);
        for (RedFlagAlertAssignItem item : items) {
            driverIds.add(item.getItemId());
        }
        return driverIds;
    }


    private List<Integer> getVehicleIdsForRedFlag(Integer redFlagId) {
        List<Integer> vehicleIDs = new LinkedList<Integer>();
        List<RedFlagAlertAssignItem> items = getRedFlagAlertVehicleByAlertID(redFlagId);
        for (RedFlagAlertAssignItem item : items) {
            vehicleIDs.add(item.getItemId());
        }
        return vehicleIDs;
    }


    private List<Integer> getPersonIdsForRedFlag(Integer redFlagId) {
        List<Integer> notifyPersonIDs = new LinkedList<Integer>();
        List<RedFlagAlertAssignItem> items = getRedFlagAlertPersonByAlertID(redFlagId);
        for (RedFlagAlertAssignItem item : items) {
            notifyPersonIDs.add(item.getItemId());
        }
        return notifyPersonIDs;
    }


    @Override
    public List<RedFlagAlert> getRedFlagAlerts(Integer acctID) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("acctID", acctID);
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
            StringBuilder redFlagAlerts = new StringBuilder(GET_BY_USERID_DEEP);

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
        return getJdbcTemplate().update(DELETE_BY_ZONE_ID, new Object[]{zoneID});
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

                ps.setLong(1, AlertMessageType.convertTypes(entity.getTypes()));

                ps.setInt(2, 0);

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

                ps.setLong(12, convertDaysOfWeek(entity.getDayOfWeek()));

                ps.setLong(13, VehicleType.convertTypes(entity.getVehicleTypes()));

                if (entity.getSpeedSettings() == null) {
                    ps.setString(14, SpeedingConstants.INSTANCE.DEFAULT_SPEED_SET);
                } else {
                    ps.setString(14, Arrays.toString(entity.getSpeedSettings()));
                }

                    ps.setInt(15, entity.getHardAcceleration());

                    ps.setInt(16, entity.getHardBrake());

                    ps.setInt(17, entity.getHardTurn());

                    ps.setInt(18, entity.getHardVertical());

                if (entity.getSeverityLevel() == null) {
                    ps.setObject(19, RedFlagLevel.NONE.getCode());
                } else {
                    ps.setInt(19, entity.getSeverityLevel().getCode());
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

        // create other items for this red flag
        createGroupsForRedFlagAlert(keyHolder.getKey().intValue(), entity.getGroupIDs());
        createDriverForRedFlagAlert(keyHolder.getKey().intValue(), entity.getDriverIDs());
        createVehicleForRedFlagAlert(keyHolder.getKey().intValue(), entity.getVehicleIDs());
        createPersonForRedFlagAlert(keyHolder.getKey().intValue(), entity.getNotifyPersonIDs());
        createEscForRedFlagAlert(keyHolder.getKey().intValue(), entity.getEscalationList());


        return keyHolder.getKey().intValue();
    }

    //create groups for AlertGroups
    private void createGroupsForRedFlagAlert(Integer redFlagId, List<Integer> groupIds) {
        for (Integer groupId : groupIds) {
            RedFlagAlertAssignItem item = new RedFlagAlertAssignItem(redFlagId, groupId);
            createRedFlagAlertGroup(null, item);
        }
    }


    //create for AlertDriver
    private void createDriverForRedFlagAlert(Integer redFlagId, List<Integer> driverIds) {
        for (Integer driverId : driverIds) {
            RedFlagAlertAssignItem item = new RedFlagAlertAssignItem(redFlagId, driverId);
            createRedFlagAlertDriver(null, item);
        }
    }


    //create for AlertVehicle
    private void createVehicleForRedFlagAlert(Integer redFlagId, List<Integer> vehicleIds) {
        for (Integer vehicleId : vehicleIds) {
            RedFlagAlertAssignItem item = new RedFlagAlertAssignItem(redFlagId, vehicleId);
            createRedFlagAlertVehicle(null, item);
        }
    }


    //create for AlertPerson
    private void createPersonForRedFlagAlert(Integer redFlagId, List<Integer> notifyPersonIDs) {
        for (Integer personID : notifyPersonIDs) {
            RedFlagAlertAssignItem item = new RedFlagAlertAssignItem(redFlagId, personID);
            createRedFlagAlertPerson(null, item);
        }
    }


    //create for AlertEmail
    private void createEmailForRedFlagAlert(Integer redFlagId, List<String> emails) {
        for (String email : emails) {
            RedFlagAlertAssignItem item = new RedFlagAlertAssignItem(redFlagId, email);
            createRedFlagAlertEmail(null, item);
        }
    }

//    //create for AlertEsc
    private void createEscForRedFlagAlert(Integer alertID, List<AlertEscalationItem> escalation) {
        for (AlertEscalationItem esc : escalation) {
            AlertEscalationItem item = new AlertEscalationItem(alertID, esc);

            createRedFlagAlertEscP(null, item);
        }
    }


    @Override
    public Integer update(final RedFlagAlert entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(UPDATE_ALERT);

                ps.setLong(1, AlertMessageType.convertTypes(entity.getTypes()));

                ps.setInt(2, 0);
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

                ps.setLong(12, convertDaysOfWeek(entity.getDayOfWeek()));
                ps.setLong(13, VehicleType.convertTypes(entity.getVehicleTypes()));

                if (entity.getSpeedSettings() == null) {
                    ps.setString(14, SpeedingConstants.INSTANCE.DEFAULT_SPEED_SET);
                } else {
                    ps.setInt(14, entity.getSpeedSettings().length);
                }

                    ps.setInt(15, entity.getHardAcceleration());

                    ps.setInt(16, entity.getHardBrake());

                    ps.setInt(17, entity.getHardTurn());

                    ps.setInt(18, entity.getHardVertical());


                if (entity.getSeverityLevel() == null) {
                    ps.setInt(19, RedFlagLevel.NONE.getCode());
                } else {
                    ps.setInt(19, entity.getSeverityLevel().getCode());
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

                ps.setInt(26, entity.getAlertID());

                logger.debug(ps.toString());
                return ps;
            }
        };
        jdbcTemplate.update(psc);

        // update other items for this red flag
        updateGroupsForRedFlagAlert(entity.getAlertID(), entity.getGroupIDs());
        setUpdateAlertDriverForRedFlagAlert(entity.getAlertID(), entity.getDriverIDs());
        setUpdateAlertVehicleForRedFlagAlert(entity.getAlertID(), entity.getVehicleIDs());
        setUpdateAlertPersonForRedFlagAlert(entity.getAlertID(), entity.getNotifyPersonIDs());
        setUpdateAlertEscForRedFlagAlert(entity.getAlertID(), entity.getEscalationList());

        return entity.getAlertID();
    }


        private void updateGroupsForRedFlagAlert(Integer redFlagId, List<Integer> groupIds) {
            List<RedFlagAlertAssignItem> existing = getRedFlagAlertGroupsByAlertID(redFlagId);
            for (RedFlagAlertAssignItem item : existing) {
                    // delete it
                    deleteRedFlagAlertGroupById(item.getId());
            }

            for (Integer id : groupIds) {
                RedFlagAlertAssignItem item = new RedFlagAlertAssignItem(id);
                    // insert it
                    item.setRedFlagId(redFlagId);
                    item.setItemId(id);
                    createRedFlagAlertGroup(null,item);

            }
        }


    @Override
    public Integer deleteByID(Integer alertID) {
        int result = getJdbcTemplate().update(DEL_BY_ID, new Object[] { alertID });

        //delete other items for this red flag.
        deleteGroupsForRedFlagsAlert(alertID);
        deleteDriverForRedFlagsAlert(alertID);
        deleteVehiclerForRedFlagsAlert(alertID);
        deleteEmailForRedFlagsAlert(alertID);
        deletePersonForRedFlagsAlert(alertID);
        deletePersonForRedFlagsEscPers(alertID);

        return result;
    }


    private void deleteGroupsForRedFlagsAlert(Integer redFlagId) {
        deleteRedFlagAlertGroupByAlertId(redFlagId);
    }


    private void deleteDriverForRedFlagsAlert(Integer redFlagId) {
        deleteRedFlagAlertDriverByAlertId(redFlagId);
    }


    private void deleteVehiclerForRedFlagsAlert(Integer redFlagId) {
        deleteRedFlagAlertVehicleByAlertId(redFlagId);
    }


    private void deleteEmailForRedFlagsAlert(Integer redFlagId) {
        deleteRedFlagAlertEmailByAlertId(redFlagId);
    }


    private void deletePersonForRedFlagsAlert(Integer redFlagId) {
        deleteRedFlagAlertPersonByAlertId(redFlagId);
    }

    private void deletePersonForRedFlagsEscPers(Integer redFlagId) {
        deleteRedFlagAlertEscpByAlertId(redFlagId);
    }


    //AlertGroup
    public List<RedFlagAlertAssignItem> getRedFlagAlertGroupsByAlertID(Integer alertId) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("alertID", alertId);

            return getSimpleJdbcTemplate().query(FIND_ALERT_GROUP_BY_ALERT_ID, redFlagAlertGroupRowMapper, params);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }


    public  Integer deleteRedFlagAlertGroupById(Integer alertGroupID) {
        return getJdbcTemplate().update(DELETE_ALERT_GROUP_BY_ID, new Object[] { alertGroupID });
    }


    private Integer deleteRedFlagAlertGroupByAlertId(Integer alertID) {
        return getJdbcTemplate().update(DELETE_ALERT_GROUP_BY_ALERT_ID, new Object[] { alertID });
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


    //AlertDriver
    private List<RedFlagAlertAssignItem> getRedFlagAlertDriverByAlertID(Integer alertID) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("alertID", alertID);

            return getSimpleJdbcTemplate().query(FIND_ALERT_DRIVER_BY_ALERT_ID, redFlagAlertDriverRowMapper, params);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }


    private Integer deleteRedFlagAlertDriverById(Integer alertDriverID) {
        return getJdbcTemplate().update(DELETE_ALERT_DRIVER_BY_ID, new Object[] { alertDriverID });
    }


    private Integer deleteRedFlagAlertDriverByAlertId(Integer alertID) {
        return getJdbcTemplate().update(DELETE_ALERT_DRIVER_BY_ALERT_ID, new Object[] { alertID });
    }


    public Integer createRedFlagAlertDriver(Integer id, final RedFlagAlertAssignItem entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_ALERT_DRIVER, Statement.RETURN_GENERATED_KEYS);

                ps.setInt(1, entity.getRedFlagId());
                ps.setInt(2, entity.getItemId());

                logger.debug(ps.toString());
                return ps;
            }
        };
        jdbcTemplate.update(psc, keyHolder);
        return keyHolder.getKey().intValue();
    }


    public Integer updateRedFlagAlertDriver(final RedFlagAlertAssignItem entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(UPDATE_ALERT_DRIVER_BY_ID);

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


    private void setUpdateAlertDriverForRedFlagAlert(Integer redFlagId, List<Integer> driverIDs) {
        List<RedFlagAlertAssignItem> existing = getRedFlagAlertDriverByAlertID(redFlagId);

        for (RedFlagAlertAssignItem item : existing) {
                // delete it
                deleteRedFlagAlertDriverById(item.getId());

        }

        for (Integer id : driverIDs) {
            RedFlagAlertAssignItem item = new RedFlagAlertAssignItem(id);

                // insert it
                item.setRedFlagId(redFlagId);
                item.setItemId(id);
                createRedFlagAlertDriver(null, item);

        }
    }


    //AlertVehicle
    private List<RedFlagAlertAssignItem> getRedFlagAlertVehicleByAlertID(Integer alertID) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("alertID", alertID);

            return getSimpleJdbcTemplate().query(FIND_ALERT_VEHICLE_BY_ALERT_ID, redFlagAlertVehicleRowMapper, params);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

    private Integer deleteRedFlagAlertVehicleById(Integer alertVehicleID) {
        return getJdbcTemplate().update(DELETE_ALERT_VEHICLE_BY_ID, new Object[] { alertVehicleID });
    }

    private Integer deleteRedFlagAlertVehicleByAlertId(Integer alertID) {
        return getJdbcTemplate().update(DELETE_ALERT_VEHICLE_BY_ALERT_ID, new Object[] { alertID });
    }

    public Integer createRedFlagAlertVehicle(Integer id, final RedFlagAlertAssignItem entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_ALERT_VEHICLE, Statement.RETURN_GENERATED_KEYS);

                ps.setInt(1, entity.getRedFlagId());
                ps.setInt(2, entity.getItemId());

                logger.debug(ps.toString());
                return ps;
            }
        };
        jdbcTemplate.update(psc, keyHolder);
        return keyHolder.getKey().intValue();
    }

    public Integer updateRedFlagAlertVehicle(final RedFlagAlertAssignItem entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(UPDATE_ALERT_VEHICLE_BY_ID);

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


    private void setUpdateAlertVehicleForRedFlagAlert(Integer redFlagId, List<Integer> vehicleIDs) {
        // first, find all the existing items for this red flag
        List<RedFlagAlertAssignItem> existing = getRedFlagAlertDriverByAlertID(redFlagId);

        for (RedFlagAlertAssignItem item : existing) {
                // delete it
                deleteRedFlagAlertVehicleById(item.getId());

        }

        for (Integer id : vehicleIDs) {
            RedFlagAlertAssignItem item = new RedFlagAlertAssignItem(id);

                // insert it
                item.setRedFlagId(redFlagId);
                item.setItemId(id);
            createRedFlagAlertVehicle(null, item);

        }
    }


    //AlertPerson
    private List<RedFlagAlertAssignItem> getRedFlagAlertPersonByAlertID(Integer alertID) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("alertID", alertID);

            return getSimpleJdbcTemplate().query(FIND_ALERT_PERSON_BY_ALERT_ID, redFlagAlertPersonRowMapper, params);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

    private Integer deleteRedFlagAlertPersonById(Integer alertPersonID) {
        return getJdbcTemplate().update(DELETE_ALERT_PERSON_BY_ID, new Object[] { alertPersonID });
    }


    private Integer deleteRedFlagAlertPersonByAlertId(Integer alertID) {
        return getJdbcTemplate().update(DELETE_ALERT_PERSON_BY_ALERT_ID, new Object[] { alertID });
    }


    public Integer createRedFlagAlertPerson(Integer id, final RedFlagAlertAssignItem entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_ALERT_PERSON, Statement.RETURN_GENERATED_KEYS);

                ps.setInt(1, entity.getRedFlagId());
                ps.setInt(2, entity.getItemId());

                logger.debug(ps.toString());
                return ps;
            }
        };
        jdbcTemplate.update(psc, keyHolder);
        return keyHolder.getKey().intValue();
    }


    public Integer updateRedFlagAlertPerson(final RedFlagAlertAssignItem entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(UPDATE_ALERT_PERSON_BY_ID);

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


    private void setUpdateAlertPersonForRedFlagAlert(Integer redFlagId, List<Integer> notifyPersonIDs) {
        // first, find all the existing items for this red flag
        List<RedFlagAlertAssignItem> existing = getRedFlagAlertPersonByAlertID(redFlagId);

        for (RedFlagAlertAssignItem item : existing) {
                // delete it
                deleteRedFlagAlertPersonById(item.getId());

        }

        for (Integer id : notifyPersonIDs) {
            RedFlagAlertAssignItem item = new RedFlagAlertAssignItem(id);

                // insert it
                item.setRedFlagId(redFlagId);
                item.setItemId(id);
            createRedFlagAlertPerson(null, item);

        }
    }


    //AlertEscPerson
    private List<AlertEscalationItem> getRedFlagAlertEscPByAlertID(Integer alertID) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("alertID", alertID);

            return getSimpleJdbcTemplate().query(FIND_ALERT_ESCPERS_BY_ALERT_ID, redFlagAlertEscPersRowMapper, params);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }
    
    //Person fullName auxiliary DAO
    private List<String> getPersonFullNameByAlertID(Integer alertID) {
        List<RedFlagAlertAssignItem> items = getRedFlagAlertPersonByAlertID(alertID);
        if (items.isEmpty() || items.size() > 1) {
            return Collections.emptyList();
        }
        Integer personID = items.get(0).getItemId();
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("personID", personID);

            return getSimpleJdbcTemplate().query(FIND_AUX_PERSON_BY_ID, redFlagAuxPersonRowMapper, params);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

    private Integer deleteRedFlagAlertEscpById(Integer alertPersonID) {
        return getJdbcTemplate().update(DELETE_ALERT_ESCPERS_BY_ID, new Object[] { alertPersonID });
    }

    private Integer deleteRedFlagAlertEscpByAlertId(Integer alertID) {
        return getJdbcTemplate().update(DELETE_ALERT_ESCPERS_BY_ALERT_ID, new Object[] { alertID });
    }

    public Integer createRedFlagAlertEscP(Integer id, final AlertEscalationItem entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_ALERT_ESCPERS, Statement.RETURN_GENERATED_KEYS);

                ps.setInt(1, entity.getAlertID());
                ps.setInt(2, entity.getPersonID());
                ps.setInt(3, entity.getEscalationOrder());
                ps.setInt(4, entity.getContactType());

                logger.debug(ps.toString());
                return ps;
            }
        };
        jdbcTemplate.update(psc, keyHolder);
        return keyHolder.getKey().intValue();
    }


    public Integer updateRedFlagAlertEscP(final AlertEscalationItem entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(UPDATE_ALERT_ESCPERS_BY_ID);
                                                             //alertID = ?, personID = ?, escalationOrder=?, contactType=?
                ps.setInt(1, entity.getAlertID());
                ps.setInt(2, entity.getPersonID());
                ps.setInt(3, entity.getEscalationOrder());
                ps.setInt(4, entity.getContactType());

                //where
                ps.setInt(3, entity.getAlertPersonID());

                logger.debug(ps.toString());
                return ps;
            }
        };
        jdbcTemplate.update(psc);
        return entity.getAlertPersonID();
    }

    private void setUpdateAlertEscForRedFlagAlert(Integer redFlagId, List<AlertEscalationItem> escIDs) {
        // first, find all the existing items for this red flag
        List<AlertEscalationItem> existing = getRedFlagAlertEscPByAlertID(redFlagId);

        //for each existing, if it's not in the given list, delete it from bd
        for (AlertEscalationItem item : existing) {
                // delete it
                deleteRedFlagAlertEscpById(item.getAlertPersonID());

        }

        // for each given, if it's not in the existing list, insert it into bd
        for (AlertEscalationItem id : escIDs) {
            AlertEscalationItem item = new AlertEscalationItem();

                // insert it
                item.setAlertID(redFlagId);
                item.setPersonID(id.getPersonID());
                item.setEscalationOrder(id.getEscalationOrder());
                item.setContactType(id.getContactType());
            createRedFlagAlertEscP(null, item);

        }
    }


    //AlertEmail
    private List<RedFlagAlertAssignItem> getRedFlagAlertEmailByAlertID(Integer alertID) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("alertID", alertID);

            return getSimpleJdbcTemplate().query(FIND_ALERT_EMAIL_BY_ALERT_ID, redFlagAlertEmailRowMapper, params);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

    private Integer deleteRedFlagAlertEmailById(Integer alertEmailID) {
        return getJdbcTemplate().update(DELETE_ALERT_EMAIL_BY_ID, new Object[] { alertEmailID });
    }

    private Integer deleteRedFlagAlertEmailByAlertId(Integer alertID) {
        return getJdbcTemplate().update(DELETE_ALERT_EMAIL_BY_ALERT_ID, new Object[] { alertID });
    }

    public Integer createRedFlagAlertEmail(Integer id, final RedFlagAlertAssignItem entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_ALERT_EMAIL, Statement.RETURN_GENERATED_KEYS);

                ps.setInt(1, entity.getRedFlagId());
                ps.setString(2, entity.getItem());

                logger.debug(ps.toString());
                return ps;
            }
        };
        jdbcTemplate.update(psc, keyHolder);
        return keyHolder.getKey().intValue();
    }


    public Integer updateRedFlagAlertEmail(final RedFlagAlertAssignItem entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(UPDATE_ALERT_EMAIL_BY_ID);

                ps.setInt(1, entity.getRedFlagId());
                ps.setString(2, entity.getItem());

                //where
                ps.setInt(3, entity.getId());

                logger.debug(ps.toString());
                return ps;
            }
        };
        jdbcTemplate.update(psc);
        return entity.getId();
    }

    private void setUpdateAlertEmailForRedFlagAlert(Integer redFlagId, List<Integer> emails) {
        // first, find all the existing items for this red flag
        List<RedFlagAlertAssignItem> existing = getRedFlagAlertEmailByAlertID(redFlagId);

        for (RedFlagAlertAssignItem item : existing) {
                // delete it
                deleteRedFlagAlertEmailById(item.getId());

        }

        for (Integer id : emails) {
            RedFlagAlertAssignItem item = new RedFlagAlertAssignItem(id);

                // insert it
                item.setRedFlagId(redFlagId);
                item.setItemId(id);
                 createRedFlagAlertEmail(null, item);

        }
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
    
    private Date getTimeOrNullFromRS(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) == null ? null : rs.getTime(columnName);
    }
    
    private Date getDateTimeOrNullFromRS(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) == null ? null : rs.getTimestamp(columnName);
    }

    private Date toUTC(Date date) {
        DateTime dt = new DateTime(date.getTime()).toDateTime(DateTimeZone.UTC);
        return dt.toDate();
    }

    private List<Boolean> getDaysOfWeek(Long dayOfWeekMask) {
        List<Boolean> daysOfWeek = new LinkedList<Boolean>();

        if (dayOfWeekMask != null) {
            // for sunday to saturday (sunday = 0, saturday = 6)
            for (int i = 0; i <= 6; i++) {
                daysOfWeek.add(dayOfWeekMatch(i, dayOfWeekMask));
            }
        }

        return daysOfWeek;
    }

    public static Long convertDaysOfWeek(List<Boolean> daysOfWeek) {
        Long daysOfWeekMask = new Long(0);
        if (daysOfWeek != null) {
            for (Boolean day : daysOfWeek) {

                long bitValue = 1l << (day ? 1 : 0);
                daysOfWeekMask = daysOfWeekMask.longValue() | bitValue;
            }
        }
        return daysOfWeekMask;
    }

    private boolean dayOfWeekMatch(int dayOfWeek, long dayOfWeekMask) {
        return ((dayOfWeekMask & (1 << dayOfWeek)) != 0);
    }
    
    private Integer convertTimeToNumberOfMins(Date time) {
        if (time == null) {        
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        return cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(Calendar.MINUTE);
    }
}

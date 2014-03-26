package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.Gender;
import com.inthinc.pro.model.GoogleMapType;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.security.AccessPoint;
import com.mysql.jdbc.Statement;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class UserJDBCDAO extends SimpleJdbcDaoSupport implements UserDAO {

    //    private static final String SELECT_USER_STRING ="Select t.tzName,p.personID, p.acctID, p.tzID, p.modified, p.status, p.measureType, p.fuelEffType, p.addrID, p.locale, p.reportsTo," +
//            "    p.title,p.dept,p.empid, p.first, p.middle, p.last,p.suffix, p.gender, p.height, p.weight, p.dob, p.info, p.warn, p.crit, p.priEmail, p.secEmail, p.priPhone," +
//            "    p.secPhone, p.priText, p.secText, u.userID, u.groupID, u.personID, u.status, u.modified, u.lastLogin, u.passwordDT, u.password, u.username, u.mapType " +
//            "    from user u, person p,timezone t  where d.driverID = :driverID and p.personID = d.personID and p.tzID=t.tzID; ";
    //    private static final String SELECT_USER_STRING = "select u.userID, u.groupID, u.personID, u.status, u.modified, u.lastLogin, u.passwordDT, u.password, u.username, u.mapType from user u where 1=1 ";

    private static final String ROLE_ACCESS_SELECT = "SELECT DISTINCT ra.mode, ra.accessPtID, mode FROM roleAccess ra WHERE roleID IN (:roleID)";
    private static final String ROLE_SELECT = "SELECT u.userID, u.roleID FROM role as r JOIN userRole as u USING (roleID) WHERE u.userID IN (:userID)";
    private static final String MAP_LAYERS_SELECT = "SELECT uml.customMapID FROM userMapLayer uml where uml.userID = :userID";
    private static final String SELECT_USER_STRING = "Select t.tzName,p.personID, p.acctID, p.tzID, p.modified, p.status, p.measureType, p.fuelEffType, p.addrID, p.locale," +
            " p.reportsTo, p.title,p.dept,p.empid, p.first, p.middle, p.last,p.suffix, p.gender, p.height, p.weight, p.dob, p.info, p.warn, p.crit, p.priEmail, p.secEmail, p.priPhone," +
            " p.secPhone, p.priText, p.secText, u.userID, u.groupID, u.personID, u.status, u.modified, u.lastLogin, u.passwordDT, u.password, u.username, u.mapType from user u, person p," +
            " timezone t where p.personID = u.personID and p.tzID=t.tzID ";
    private static final String SELECT_USER_STRING_BY_NAME = " and u.username like :username and u.status <> 3";
    private static final String SELECT_USER_STRING_BY_PERSONID = " and u.personID like :personID and u.status <> 3";
    private static final String SELECT_USER_STRING_BY_ID = " and u.userID like :userID and u.status <> 3";
    private static final String GROUP_ID_DEEP = SELECT_USER_STRING + " and u.groupID in (select groupID from groups where groupPath like :groupID) and u.status <> 3";
    private static final String INSERT_USER = "insert into user (groupID, personID, status, passwordDT, username, password, mapType)" +
                                              "VALUES (?, ?, ?, ?, ?, ?, ?)";


    private ParameterizedRowMapper<User> userParameterizedRow = new ParameterizedRowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {

            Person person = new Person();
            person.setPersonID(rs.getInt("p.personID"));
            person.setAcctID(rs.getInt("p.acctID"));
            person.setStatus(Status.valueOf(rs.getInt("p.status")));
            person.setMeasurementType(MeasurementType.valueOf(rs.getInt("p.measureType")));
            person.setFuelEfficiencyType(FuelEfficiencyType.valueOf(rs.getInt("p.fuelEffType")));
            person.setAddressID(rs.getInt("p.addrID"));
            person.setReportsTo(rs.getString("p.reportsTo"));
            person.setTitle(rs.getString("p.title"));
            person.setDept(rs.getString("p.dept"));
            person.setEmpid(rs.getString("p.empid"));
            person.setFirst(rs.getString("p.first"));
            person.setMiddle(rs.getString("p.middle"));
            person.setLast(rs.getString("p.last"));
            person.setSuffix(rs.getString("p.suffix"));
            person.setGender(Gender.valueOf(rs.getInt("p.gender")));
            person.setHeight(rs.getInt("p.height"));
            person.setDob(rs.getDate("p.dob"));
            person.setInfo(rs.getInt("p.info"));
            person.setWarn(rs.getInt("p.warn"));
            person.setCrit(rs.getInt("p.crit"));
            person.setPriEmail(rs.getString("p.priEmail"));
            person.setSecEmail(rs.getString("p.secEmail"));
            person.setPriPhone(rs.getString("p.priPhone"));
            person.setSecPhone(rs.getString("p.secPhone"));
            person.setPriText(rs.getString("p.priText"));
            person.setSecText(rs.getString("p.secText"));
            person.setTimeZone(TimeZone.getTimeZone(rs.getString("t.tzName")));

            User userItem = new User();
            userItem.setGroupID(getIntOrNullFromRS(rs, "u.groupID"));
            userItem.setLastLogin(getDateOrNullFromRS(rs, "u.lastLogin"));
            userItem.setPassword(getStringOrNullFromRS(rs, "u.password"));
            userItem.setPasswordDT(getDateOrNullFromRS(rs, "u.passwordDT"));
            userItem.setPersonID(getIntOrNullFromRS(rs, "u.personID"));
            userItem.setUsername(getStringOrNullFromRS(rs, "u.username"));
            userItem.setUserID(getIntOrNullFromRS(rs, "u.userID"));
            userItem.setStatus(rs.getObject("u.status") == null ? null : Status.valueOf(rs.getInt("u.status")));

            userItem.setMapType(rs.getObject("u.status") == null ? null : GoogleMapType.valueOf(rs.getInt("u.mapType")));
            userItem.setPerson(person);

            userItem.setRoles(getUserRoles(rs.getInt("u.userID")));
            userItem.setSelectedMapLayerIDs(getUserMapLayers(rs.getInt("u.userID")));

            userItem.setAccessPoints(getUserAccessPoint(getUserRoles(rs.getInt("u.userID"))));

            return userItem;
        }
    };

    private ParameterizedRowMapper<Integer> roleParameterizedRow = new ParameterizedRowMapper<Integer>() {
        @Override
        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
            Integer roleId = getIntOrNullFromRS(rs, "u.roleID");
            return roleId;
        }
    };

    private ParameterizedRowMapper<Integer> mapLayersParameterizedRow = new ParameterizedRowMapper<Integer>() {
        @Override
        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
            Integer mapLayers = getIntOrNullFromRS(rs, "uml.customMapID");
            return mapLayers;
        }
    };

    private ParameterizedRowMapper<AccessPoint> accessPointParameterizedRow = new ParameterizedRowMapper<AccessPoint>() {
        @Override
        public AccessPoint mapRow(ResultSet rs, int rowNum) throws SQLException {

            AccessPoint accessPoint = new AccessPoint();
            accessPoint.setAccessPtID(getIntOrNullFromRS(rs, "ra.accessPtID"));
            accessPoint.setMode(getIntOrNullFromRS(rs, "ra.mode"));


            return accessPoint;
        }

    };


    @Override
    public User findByUserName(String username) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", username);

        StringBuilder userSelect = new StringBuilder(SELECT_USER_STRING);
        userSelect.append(SELECT_USER_STRING_BY_NAME);

        User userRet = getSimpleJdbcTemplate().queryForObject(userSelect.toString(), userParameterizedRow, params);
        return userRet;

    }

    @Override
    public List<User> getUsersInGroupHierarchy(Integer groupID) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("groupID", groupID);

            List<User> userList = getUsersByGroupIDDeep(groupID);

            return userList;

        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public User getUserByPersonID(Integer personID) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("personID", personID);

        StringBuilder userSelect = new StringBuilder(SELECT_USER_STRING);
        userSelect.append(SELECT_USER_STRING_BY_PERSONID);

        User userRet = getSimpleJdbcTemplate().queryForObject(userSelect.toString(), userParameterizedRow, params);
        return userRet;
    }

    @Override
    public User findByID(Integer integer) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userID", integer);

        StringBuilder userSelect = new StringBuilder(SELECT_USER_STRING);
        userSelect.append(SELECT_USER_STRING_BY_ID);

        User userRet = getSimpleJdbcTemplate().queryForObject(userSelect.toString(), userParameterizedRow, params);
        return userRet;
    }

    @Override
    public Integer create(Integer integer, final User entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
//                groupID, personID, status, passwordDT, username, password, mapType
                ps.setInt(1, entity.getGroupID());
                ps.setInt(2, entity.getPersonID());
                ps.setInt(3, entity.getStatus().getCode());
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                df.setTimeZone(TimeZone.getTimeZone("UTC"));
                String passwordDT = df.format(toUTC(new Date()));
                ps.setString(4, passwordDT);
                ps.setString(5, entity.getUsername());
                ps.setString(6, entity.getPassword());
                ps.setInt(7, entity.getMapType().getCode());

                return ps;
            }
            };
        jdbcTemplate.update(psc, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public Integer update(User entity) {
        return null;
    }

    @Override
    public Integer deleteByID(Integer integer) {
        return null;
    }

    private String getStringOrNullFromRS(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) == null ? null : rs.getString(columnName);
    }

    private Integer getIntOrNullFromRS(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) == null ? null : (int) rs.getLong(columnName);
    }

    private Date getDateOrNullFromRS(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) == null ? null : rs.getDate(columnName);
    }

    private Date toUTC(Date date){
        DateTime dt = new DateTime(date.getTime()).toDateTime(DateTimeZone.UTC);
        return dt.toDate();
    }

    public List<Integer> getUserRoles(Integer userID) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userID", userID);

        StringBuilder roleSelect = new StringBuilder(ROLE_SELECT);

        List<Integer> roleRet = getSimpleJdbcTemplate().query(roleSelect.toString(), roleParameterizedRow, params);
        return roleRet;
    }

    public List<Integer> getUserMapLayers(Integer userID) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userID", userID);

        StringBuilder mapLayersSelect = new StringBuilder(MAP_LAYERS_SELECT);

        List<Integer> mapLayersRet = getSimpleJdbcTemplate().query(mapLayersSelect.toString(), mapLayersParameterizedRow, params);
        return mapLayersRet;
    }

    public List<AccessPoint> getUserAccessPoint(List<Integer> roleID) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("roleID", roleID);

        StringBuilder userAccessPointSelect = new StringBuilder(ROLE_ACCESS_SELECT);

        List<AccessPoint> mapLayersRet = getSimpleJdbcTemplate().query(userAccessPointSelect.toString(), accessPointParameterizedRow, params);
        return mapLayersRet;
    }

    private List<User> getUsersByGroupIDDeep(Integer groupID) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID", "%/" + groupID + "/%");
        StringBuilder groupIdDeep = new StringBuilder(GROUP_ID_DEEP);
        List<User> vehiclesIn = getSimpleJdbcTemplate().query(groupIdDeep.toString(), userParameterizedRow, params);

        return vehiclesIn;
    }

}

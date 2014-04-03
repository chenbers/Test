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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class UserJDBCDAO extends SimpleJdbcDaoSupport implements UserDAO {

    private static final String ROLE_ACCESS_SELECT = "SELECT DISTINCT ra.mode, ra.accessPtID, mode FROM roleAccess ra WHERE roleID IN (:roleID)";
    private static final String ROLE_SELECT = "SELECT u.userID, u.roleID FROM role as r JOIN userRole as u USING (roleID) WHERE u.userID = :userID";
    private static final String MAP_LAYERS_SELECT = "SELECT uml.customMapID FROM userMapLayer uml where uml.userID = :userID";
    private static final String SELECT_USER_STRING = "Select t.tzName,p.personID, p.acctID, p.tzID, p.modified, p.status, p.measureType, p.fuelEffType, p.addrID, p.locale," +
            " p.reportsTo, p.title,p.dept,p.empid, p.first, p.middle, p.last,p.suffix, p.gender, p.height, p.weight, p.dob, p.info, p.warn, p.crit, p.priEmail, p.secEmail, p.priPhone," +
            " p.secPhone, p.priText, p.secText, u.userID, u.groupID, u.personID, u.status, u.modified, u.lastLogin, u.passwordDT, u.password, u.username, u.mapType from user u, person p," +
            " timezone t where p.personID = u.personID and p.tzID=t.tzID ";
    private static final String SELECT_USER_STRING_BY_NAME = " and u.username like :username and u.status <> 3";
    private static final String SELECT_USER_STRING_BY_PERSONID = " and u.personID like :personID and u.status <> 3";
    private static final String SELECT_USER_STRING_BY_ID = " and u.userID = :userID";
    private static final String GROUP_ID_DEEP = SELECT_USER_STRING + " and u.groupID in (select groupID from groups where groupPath like :groupID) and u.status <> 3";
    private static final String INSERT_USER = "INSERT into user (groupID, personID, status, passwordDT, username, password, mapType, modified)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_USER = "UPDATE user SET groupID=?, personID=?, status=?, passwordDT=?, username=?, password=?, mapType=?, modified=? WHERE userID=?";
    private static final String DEL_USER_BY_ID = "DELETE FROM user where userID=?";


    private ParameterizedRowMapper<User> userParameterizedRow = new ParameterizedRowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {

            Person person = new Person();

            person.setPersonID(rs.getInt("p.personID"));
            person.setAcctID(getIntOrNullFromRS(rs, "p.acctID"));
            person.setStatus(rs.getObject("p.status") == null ? null : Status.valueOf(rs.getInt("u.status")));
            person.setMeasurementType(rs.getObject("p.measureType") == null ? null : MeasurementType.valueOf(rs.getInt("p.measureType")));
            person.setFuelEfficiencyType(rs.getObject("p.fuelEffType") == null ? null : FuelEfficiencyType.valueOf(rs.getInt("p.fuelEffType")));
            person.setAddressID(getIntOrNullFromRS(rs, "p.addrID"));
            person.setLocale(rs.getObject("p.locale") == null ? null : getLocale(rs.getString("p.locale")));
            person.setReportsTo(getStringOrNullFromRS(rs, "p.reportsTo"));
            person.setTitle(getStringOrNullFromRS(rs, "p.title"));
            person.setDept(getStringOrNullFromRS(rs, "p.dept"));
            person.setEmpid(getStringOrNullFromRS(rs, "p.empid"));
            person.setFirst(getStringOrNullFromRS(rs, "p.first"));
            person.setMiddle(getStringOrNullFromRS(rs, "p.middle"));
            person.setLast(getStringOrNullFromRS(rs, "p.last"));
            person.setSuffix(getStringOrNullFromRS(rs, "p.suffix"));
            person.setGender(rs.getObject("p.gender") == null ? null : Gender.valueOf(rs.getInt("p.gender")));
            person.setHeight(getIntOrNullFromRS(rs, "p.height"));
            person.setDob(rs.getObject("p.dob") == null ? null : rs.getDate("p.dob"));
            person.setInfo(getIntOrNullFromRS(rs, "p.info"));
            person.setWarn(getIntOrNullFromRS(rs, "p.warn"));
            person.setCrit(getIntOrNullFromRS(rs, "p.crit"));
            person.setPriEmail(getStringOrNullFromRS(rs, "p.priEmail"));
            person.setSecEmail(getStringOrNullFromRS(rs, "p.secEmail"));
            person.setPriPhone(getStringOrNullFromRS(rs, "p.priPhone"));
            person.setSecPhone(getStringOrNullFromRS(rs, "p.secPhone"));
            person.setPriText(getStringOrNullFromRS(rs, "p.priText"));
            person.setSecText(getStringOrNullFromRS(rs, "p.secText"));
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
            List<Integer> roleIds=getUserRoles(rs.getInt("u.userID"));
            userItem.setRoles(roleIds);
            userItem.setSelectedMapLayerIDs(getUserMapLayers(rs.getInt("u.userID")));
            if(roleIds.isEmpty()){
                List<AccessPoint> accessPoints = new ArrayList<AccessPoint>();
                userItem.setAccessPoints(accessPoints);
            }else
            {
            userItem.setAccessPoints(getUserAccessPoint(roleIds));
            }
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
        try {
            User userRet = getSimpleJdbcTemplate().queryForObject(userSelect.toString(), userParameterizedRow, params);
            return userRet;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }


    }

    @Override
    public List<User> getUsersInGroupHierarchy(Integer groupID) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("groupID", groupID);

            List<User> userList = getUsersByGroupIDDeep(groupID);

            return userList;

        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public User getUserByPersonID(Integer personID) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("personID", personID);

        StringBuilder userSelect = new StringBuilder(SELECT_USER_STRING);
        userSelect.append(SELECT_USER_STRING_BY_PERSONID);
        try{
        User userRet = getSimpleJdbcTemplate().queryForObject(userSelect.toString(), userParameterizedRow, params);
            return userRet;
        }catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public User findByID(Integer userID) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userID", userID);

        StringBuilder userSelect = new StringBuilder(SELECT_USER_STRING);
        userSelect.append(SELECT_USER_STRING_BY_ID);

        return getSimpleJdbcTemplate().queryForObject(userSelect.toString(), userParameterizedRow, params);
    }

    @Override
    public Integer create(Integer integer, final User entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, entity.getGroupID());
                ps.setInt(2, entity.getPersonID());
                ps.setInt(3, entity.getStatus().getCode());
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                df.setTimeZone(TimeZone.getTimeZone("UTC"));
                String passwordDT = df.format(toUTC(new Date()));
                ps.setString(4, passwordDT);
                ps.setString(5, entity.getUsername());
                ps.setString(6, entity.getPassword());
                if(entity.getMapType()==null){
                    ps.setInt(7, Types.NULL);
                }else{
                ps.setInt(7, entity.getMapType().getCode());
                }

                String modified = df.format(toUTC(new Date()));
                ps.setString(8, modified);

                logger.debug(ps.toString());
                return ps;
            }
        };
        jdbcTemplate.update(psc, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public Integer update(final User entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                if (entity.getUserID() == null)
                    throw new SQLException("Cannot update user with null id.");
                PreparedStatement ps = con.prepareStatement(UPDATE_USER);

                ps.setInt(1, entity.getGroupID());
                ps.setInt(2, entity.getPersonID());
                ps.setInt(3, entity.getStatus().getCode());
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                df.setTimeZone(TimeZone.getTimeZone("UTC"));
                String passwordDT = df.format(toUTC(new Date()));
                ps.setString(4, passwordDT);
                ps.setString(5, entity.getUsername());
                ps.setString(6, entity.getPassword());
                if(entity.getMapType()==null){
                    ps.setInt(7, Types.NULL);
                }else{
                    ps.setInt(7, entity.getMapType().getCode());
                }
                String modified = df.format(toUTC(new Date()));
                ps.setString(8, modified);

                ps.setInt(9, entity.getUserID());
                logger.debug(ps.toString());
                return ps;
            };

        };
        jdbcTemplate.update(psc);
        return entity.getUserID();
        }

    @Override
    public Integer deleteByID(Integer userID) {
        return getJdbcTemplate().update(DEL_USER_BY_ID, new Object[]{userID});
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

    private Date toUTC(Date date) {
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
        List<User> userIn = getSimpleJdbcTemplate().query(groupIdDeep.toString(), userParameterizedRow, params);

        return userIn;
    }

    private Locale getLocale(String strLocale) {
        if (strLocale == null || strLocale.trim().isEmpty())
            return null;
        return new Locale(strLocale.trim());
    }

}

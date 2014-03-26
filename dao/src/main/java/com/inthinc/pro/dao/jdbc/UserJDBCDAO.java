package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.Gender;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class UserJDBCDAO extends SimpleJdbcDaoSupport implements UserDAO {

    //    private static final String SELECT_USER_STRING = "select u.userID, u.groupID, u.personID, u.status, u.modified, u.lastLogin, u.passwordDT, u.password, u.username, u.mapType from user u where 1=1 ";
    private static final String ROLE_SELECT = "SELECT u.userID, u.roleID FROM role as r JOIN userRole as u USING (roleID) WHERE u.userID IN (:userID)";
//    private static final String SELECT_USER_STRING ="Select t.tzName,p.personID, p.acctID, p.tzID, p.modified, p.status, p.measureType, p.fuelEffType, p.addrID, p.locale, p.reportsTo," +
//            "    p.title,p.dept,p.empid, p.first, p.middle, p.last,p.suffix, p.gender, p.height, p.weight, p.dob, p.info, p.warn, p.crit, p.priEmail, p.secEmail, p.priPhone," +
//            "    p.secPhone, p.priText, p.secText, u.userID, u.groupID, u.personID, u.status, u.modified, u.lastLogin, u.passwordDT, u.password, u.username, u.mapType " +
//            "    from user u, person p,timezone t  where d.driverID = :driverID and p.personID = d.personID and p.tzID=t.tzID; ";
    private static final String SELECT_USER_STRING = "Select t.tzName,p.personID, p.acctID, p.tzID, p.modified, p.status, p.measureType, p.fuelEffType, p.addrID, p.locale," +
            " p.reportsTo, p.title,p.dept,p.empid, p.first, p.middle, p.last,p.suffix, p.gender, p.height, p.weight, p.dob, p.info, p.warn, p.crit, p.priEmail, p.secEmail, p.priPhone," +
            " p.secPhone, p.priText, p.secText, u.userID, u.groupID, u.personID, u.status, u.modified, u.lastLogin, u.passwordDT, u.password, u.username, u.mapType from user u, person p," +
            " timezone t where p.personID = u.personID and p.tzID=t.tzID and u.userID=ur.userID ";
    private static final String SELECT_USER_STRING_BY_NAME = " and u.username like :username";

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
            userItem.setLastLogin(getDateOrNullFromRS(rs, "u.activated"));
            userItem.setPassword(getStringOrNullFromRS(rs, "u.password"));
            userItem.setPasswordDT(getDateOrNullFromRS(rs, "u.passwordDT"));
            userItem.setPersonID(getIntOrNullFromRS(rs, "u.presonID"));
            userItem.setUsername(getStringOrNullFromRS(rs, "u.username"));
            userItem.setUserID(getIntOrNullFromRS(rs, "u.userID"));
            userItem.setStatus(rs.getObject("u.status") == null ? null : Status.valueOf(rs.getInt("u.status")));
            userItem.setPerson(person);

            userItem.setRoles(getUserRoles(rs.getInt("u.userID")));
//            userItem.setSelectedMapLayerIDs();
//            userItem.setAccessPoints();
//            userItem.setMapType();

            return userItem;
        }
    };

    private ParameterizedRowMapper<Integer> roleParameterizedRow = new ParameterizedRowMapper<Integer>() {
        @Override
        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
            Integer roleId=getIntOrNullFromRS(rs, "u.roleID");
            return roleId;
        }
    };


    @Override
    public User findByUserName(String username) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("u.username", username);

        StringBuilder userSelect = new StringBuilder(SELECT_USER_STRING);
        userSelect.append(SELECT_USER_STRING_BY_NAME);

        User userRet = getSimpleJdbcTemplate().queryForObject(userSelect.toString(), userParameterizedRow, params);
        return userRet;

    }

    @Override
    public List<User> getUsersInGroupHierarchy(Integer groupID) {
        return null;
    }

    @Override
    public User getUserByPersonID(Integer personID) {
        return null;
    }

    @Override
    public User findByID(Integer integer) {
        return null;
    }

    @Override
    public Integer create(Integer integer, User entity) {
        return null;
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

    public List<Integer> getUserRoles(Integer userID){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("u.userID", userID);

        StringBuilder roleSelect = new StringBuilder(ROLE_SELECT);

        List<Integer> roleRet = getSimpleJdbcTemplate().query(roleSelect.toString(), roleParameterizedRow, params);
        return roleRet;
    }

}

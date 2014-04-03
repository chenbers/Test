package com.inthinc.pro.dao.jdbc;

import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.Gender;
import com.inthinc.pro.model.GoogleMapType;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.security.AccessPoint;
import com.mysql.jdbc.Statement;
import org.springframework.dao.EmptyResultDataAccessException;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * Person DAO.
 */
public class PersonJDBCDAO extends SimpleJdbcDaoSupport implements PersonDAO {
    private final Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
    UserJDBCDAO userDAO = new UserJDBCDAO();
    DriverJDBCDAO driverDAO = new DriverJDBCDAO();
    GroupJDBCDAO groupDAO = new GroupJDBCDAO();

    private static final String SELECT_PERSON = "Select t.tzName,p.personID, p.acctID, p.tzID, p.modified, p.status, p.measureType, p.fuelEffType, p.addrID, p.locale, p.reportsTo," +
                    "    p.title,p.dept,p.empid, p.first, p.middle, p.last,p.suffix, p.gender, p.height, p.weight, p.dob, p.info, p.warn, p.crit, p.priEmail, p.secEmail, p.priPhone," +
                    "    p.secPhone, p.priText, p.secText, d.personID, d.driverid, d.groupid, d.barcode, d.rfid1, d.rfid2, d.fobID, d.license, d.stateid, d.expiration,  d.certs,d.dot," +
                    "    d.grouppath, d.class, u.userID, u.groupID, u.personID, u.status, u.modified, u.lastLogin, u.passwordDT, u.password, u.username, u.mapType " +
                    " from user u, driver d, person p,timezone t where p.driverID = d.driverID and p.userID = u.userID and p.personID = d.personID and p.tzID=t.tzID; ";

    private static final String GROUP_ID_DEEP = SELECT_PERSON + " and u.groupID in (select groupID from groups where groupPath like :groupID) OR d.groupid in (select groupID from groups where groupPath like :groupID) and u.status <> 3";
    private static final String FIND_BY_EMAIL = SELECT_PERSON + " and p.email = :email";
    private static final String FIND_BY_ACCOUNT = SELECT_PERSON + " and p.acctID = :accountID";
    private static final String FIND_BY_EMPID = SELECT_PERSON + " and p.acctID = :accountID and p.empid = :empID";
    private static final String FIND_BY_ID = SELECT_PERSON + " and p.personID = :personID";
    private static final String FIND_REPORT_PREF_BY_USER="select reportPrefID reportPref where userID = :userID";
    private static final String FIND_ALERT_BY_USER="select alertID from alert where userID = :userID";

    private static final String DEL_PERSON_BY_ID = "DELETE FROM person WHERE personID=?";
    private static final String DEL_REPORT_PREF_BY_ID = "DELETE FROM reportPref WHERE reportPrefID=?";
    private static final String DEL_ALERT_BY_ID="DELETE FROM alert WHERE alertID=?";

    private static final String INSERT_PERSON = "INSERT into person (acctID, tzID, status, measureType, fuelEffType, " +
                    "addrID, locale, reportsTo, title, dept, empid, first, middle, last, suffix, gender, height, weight, dob, info, warn, " +
                    "crit, priEmail, secEmail, priPhone, secPhone, priText, secText )" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private ParameterizedRowMapper<Person> personRowMapper = new ParameterizedRowMapper<Person>() {
        @Override
        public Person mapRow(ResultSet rs, int rowNum) throws SQLException {

            Person person = new Person();

            person.setPersonID(getIntOrNullFromRS(rs, "p.personID"));
            person.setAcctID(getIntOrNullFromRS(rs, "p.acctID"));
            person.setAddressID(getIntOrNullFromRS(rs, "p.addrID"));
            person.setCrit(getIntOrNullFromRS(rs, "p.crit"));
            person.setDept(getStringOrNullFromRS(rs, "p.dept"));
            person.setEmpid(getStringOrNullFromRS(rs, "p.empid"));
            person.setDob(getDateOrNullFromRS(rs, "p.dob"));
            person.setFirst(getStringOrNullFromRS(rs, "p.first"));
            person.setFuelEfficiencyType(rs.getObject("p.fuelEffType") == null ? null : FuelEfficiencyType.valueOf(rs.getInt("p.fuelEffType")));
            person.setGender(rs.getObject("p.gender") == null ? null : Gender.valueOf(rs.getInt("gender")));
            person.setHeight(getIntOrNullFromRS(rs, "p.height"));
            person.setInfo(getIntOrNullFromRS(rs, "p.info"));
            person.setLast(getStringOrNullFromRS(rs, "p.last"));
            person.setLocale(rs.getObject("p.locale") == null ? null : getLocale(rs.getString("p.locale")));
            person.setWarn(getIntOrNullFromRS(rs, "p.warn"));
            person.setPriEmail(getStringOrNullFromRS(rs, "p.priEmail"));
            person.setPriPhone(getStringOrNullFromRS(rs, "p.priPhone"));
            person.setPriText(getStringOrNullFromRS(rs, "p.priText"));
            person.setReportsTo(getStringOrNullFromRS(rs, "p.reportsTo"));
            person.setStatus(rs.getObject("status") == null ? null : Status.valueOf(rs.getInt("status")));
            person.setSecEmail(getStringOrNullFromRS(rs, "p.secEmail"));
            person.setSecPhone(getStringOrNullFromRS(rs, "p.secEmail"));
            person.setSecText(getStringOrNullFromRS(rs, "p.secEmail"));
            person.setMeasurementType(rs.getObject("p.measureType") == null ? null : MeasurementType.valueOf(rs.getString("p.measureType")));
            person.setTitle(getStringOrNullFromRS(rs, "p.title"));
            person.setMiddle(getStringOrNullFromRS(rs, "p.middle"));
            person.setSuffix(getStringOrNullFromRS(rs, "p.suffix"));
            person.setWeight(getIntOrNullFromRS(rs, "p.weight"));
            person.setTimeZone(TimeZone.getTimeZone(rs.getString("t.tzName")));

            Address address = new Address();
            address.setAddrID(getIntOrNullFromRS(rs, "addrID"));
            person.setAddress(address);

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
            List<Integer> roleIds = userDAO.getUserRoles(rs.getInt("u.userID"));
            userItem.setRoles(roleIds);
            userItem.setSelectedMapLayerIDs(userDAO.getUserMapLayers(rs.getInt("u.userID")));
            if (roleIds.isEmpty()) {
                List<AccessPoint> accessPoints = new ArrayList<AccessPoint>();
                userItem.setAccessPoints(accessPoints);
            } else {
                userItem.setAccessPoints(userDAO.getUserAccessPoint(roleIds));
            }

            person.setUser(userItem);

            Driver driverItem = new Driver();

            driverItem.setDriverID(rs.getInt("d.driverID"));
            driverItem.setGroupID(rs.getInt("d.groupID"));
            //            driver.setStatus(status);
            driverItem.setLicense(rs.getString("d.license"));
            driverItem.setLicenseClass(rs.getString("d.class"));
            driverItem.setState(States.getStateById(rs.getInt("d.stateID")));         //?
            driverItem.setExpiration(rs.getDate("d.expiration", calendar));
            driverItem.setCertifications(rs.getString("d.certs"));
            driverItem.setDot(RuleSetType.valueOf(rs.getInt("d.dot")));         //?
            driverItem.setBarcode(rs.getString("d.barcode"));
            driverItem.setRfid1(rs.getObject("d.rfid1") == null ? null : rs.getLong("d.rfid1"));
            driverItem.setRfid2(rs.getObject("d.rfid2") == null ? null : rs.getLong("d.rfid2"));
            driverItem.setFobID(rs.getString("d.fobID"));
            driverItem.setStatus(Status.valueOf(rs.getInt("d.status")));
            //            driver.setPersonID(rs.getInt("d.personId"));
            driverItem.setPerson(person);

            person.setDriver(driverItem);

            return person;
        }
    };


    private ParameterizedRowMapper<Integer> reportPrefIDRowMapper = new ParameterizedRowMapper<Integer>() {
        @Override
        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
            Integer reportPrefID = getIntOrNullFromRS(rs, "reportPrefID");
            return reportPrefID;
        }
    };

    private ParameterizedRowMapper<Integer> alertIDRowMapper = new ParameterizedRowMapper<Integer>() {
        @Override
        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
            Integer alertID = getIntOrNullFromRS(rs, "alertID");
            return alertID;
        }
    };


    @Override
    public Person findByEmail(String email) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("p.email", email);

        StringBuilder personByEmailSelect = new StringBuilder();
        personByEmailSelect.append(FIND_BY_EMAIL);
        try {
            Person person = getSimpleJdbcTemplate().queryForObject(personByEmailSelect.toString(), personRowMapper, params);
            return person;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Person> getPeopleInGroupHierarchy(Integer groupID) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("groupID", groupID);

            List<Person> personList = getPersonsByGroupIDDeep(groupID);

            return personList;

        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Person> getPeopleInGroupHierarchy(Integer userGroupID, Integer driverGroupID) {
        throw new NotImplementedException();
    }

    @Override
    public Integer delete(Person person) {
        if ((person.getUser() != null) && (person.getUser().getUserID() != null)) {
            userDAO.deleteByID(person.getUser().getUserID());
            deleteAlertsByUserId(person.getUser().getUserID());
            deleteReportsByUserId(person.getUser());
        }
        if ((person.getDriver() != null) && (person.getDriver().getDriverID() != null)) {
            driverDAO.deleteByID(person.getDriver().getDriverID());
        }

        return deleteByID(person.getPersonID());
//        getJdbcTemplate().update(DEL_PERSON_BY_ID, new Object[] { person.getPersonID() });
    }

    @Override
    public List<Person> getPeopleInAccount(Integer acctID) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("accountID", acctID);

        StringBuilder personByAcctSelect = new StringBuilder();
        personByAcctSelect.append(FIND_BY_ACCOUNT);

        List<Person> personList = getSimpleJdbcTemplate().query(personByAcctSelect.toString(), personRowMapper, params);
        return personList;
    }

    @Override
    public Person findByEmpID(Integer acctID, String empID) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("accountID", acctID);
        params.put("p.empID", empID);

        StringBuilder personByEmpSelect = new StringBuilder();
        personByEmpSelect.append(FIND_BY_EMPID);
        try {
            Person person = getSimpleJdbcTemplate().queryForObject(personByEmpSelect.toString(), personRowMapper, params);
            return person;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Person findByID(Integer integer) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("personID", integer);

        StringBuilder personByIDSelect = new StringBuilder();
        personByIDSelect.append(FIND_BY_ID);
        try {
            Person person = getSimpleJdbcTemplate().queryForObject(personByIDSelect.toString(), personRowMapper, params);
            return person;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Integer create(Integer integer,final Person entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        entity.setAddress(groupDAO.createAddressIfNeeded(entity.getAccountID(), entity.getAddress()));

        Integer personID;


        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_PERSON, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, entity.getAccountID());
                ps.setString(2, entity.getTimeZone().getID());

                if (entity.getStatus() == null || entity.getStatus().getCode() == null) {
                    ps.setNull(3, Types.NULL);
                } else {
                    ps.setInt(3, entity.getStatus().getCode());
                }

                if (entity.getMeasurementType() == null || entity.getMeasurementType().getCode() == null) {
                    ps.setNull(4, Types.NULL);
                } else {
                    ps.setInt(4, entity.getMeasurementType().getCode());
                }

                if (entity.getFuelEfficiencyType() == null || entity.getFuelEfficiencyType().getCode() == null) {
                    ps.setNull(5, Types.NULL);
                } else {
                    ps.setInt(5, entity.getFuelEfficiencyType().getCode());
                }

                if (entity.getAddress() == null) {
                    ps.setNull(6, Types.NULL);
                } else {
                    ps.setInt(6, entity.getAddress().getAddrID());
                }

                if (entity.getLocale() == null) {
                    ps.setNull(7, Types.NULL);
                } else {
                    ps.setString(7, entity.getLocale().toString());
                }

                //                acctID, tzID, status, measureType, fuelEffType, addrID, locale, reportsTo, title, dept, empid, first, middle, last, suffix, gender, height, weight, dob, info, warn, crit, priEmail, secEmail, priPhone, secPhone, priText, secText


                ps.setString(8, entity.getReportsTo());
                ps.setString(9, entity.getTitle());
                ps.setString(10, entity.getDept());

//                if (entity.getDotOfficeType() == null || entity.getDotOfficeType().getCode() == null) {
//                    ps.setNull(7, Types.NULL);
//                } else {
//                    ps.setInt(7, entity.getDotOfficeType().getCode());
//                }
//
//                if (entity.getType() == null || entity.getType().getCode() == null) {
//                    ps.setNull(8, Types.NULL);
//                } else {
//                    ps.setInt(8, entity.getType().getCode());
//                }
//
//                if (entity.getManagerID() == null) {
//                    ps.setNull(9, Types.NULL);
//                } else {
//                    ps.setInt(9, entity.getManagerID());
//                }
//                ps.setInt(10, entity.getMapZoom());
//                ps.setDouble(11, entity.getMapLat());
//                ps.setDouble(12, entity.getMapLng());
//
//                if (entity.getZoneRev() == null) {
//                    ps.setInt(13, Types.NULL);
//                } else {
//                    ps.setInt(13, entity.getZoneRev());
//                }
//
//                if (entity.getAggDate() == null) {
//                    ps.setNull(14, Types.NULL);
//                } else {
//                    ps.setDate(14, new java.sql.Date(dateFormatter.parseDateTime(entity.getAggDate()).getMillis()));
//                }


                logger.debug(ps.toString());
                return ps;
            }
        };

        jdbcTemplate.update(psc, keyHolder);
        personID = keyHolder.getKey().intValue();








        if (entity.getUser() != null && (entity.getUser().getUserID() == null || entity.getUser().getUserID().intValue() == 0))
        {
            entity.getUser().setPersonID(personID);
            Integer userID = userDAO.create(personID, entity.getUser());
            entity.getUser().setUserID(userID);
        }

        if (entity.getDriver() != null && (entity.getDriver().getDriverID() == null || entity.getDriver().getDriverID().intValue() == 0))
        {
            entity.getDriver().setPersonID(personID);
            Integer driverID =driverDAO.create(personID, entity.getDriver());
            entity.getDriver().setDriverID(driverID);
        }

        return personID;
    }

    @Override
    public Integer update(Person entity) {
        return null;
    }

    @Override
    public Integer deleteByID(Integer integer) {
        return getJdbcTemplate().update(DEL_PERSON_BY_ID, new Object[]{integer});
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

    private Locale getLocale(String strLocale) {
        if (strLocale == null || strLocale.trim().isEmpty())
            return null;
        return new Locale(strLocale.trim());
    }

    private List<Person> getPersonsByGroupIDDeep(Integer groupID) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID", "%/" + groupID + "/%");
        StringBuilder groupIdDeep = new StringBuilder(GROUP_ID_DEEP);
        List<Person> personIn = getSimpleJdbcTemplate().query(groupIdDeep.toString(), personRowMapper, params);

        return personIn;
    }


    /**
     * @param user
     */
    private void deleteReportsByUserId(final User user) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userID", user.getUserID());


        StringBuilder reportPrefByIDSelect = new StringBuilder();
        reportPrefByIDSelect.append(FIND_REPORT_PREF_BY_USER);
           List<Integer> reportIDList = getSimpleJdbcTemplate().query(reportPrefByIDSelect.toString(), reportPrefIDRowMapper, params);
        for (int i=0;i<reportIDList.size();i++){
            Integer reportPrefId = reportIDList.get(i);
            getJdbcTemplate().update(DEL_REPORT_PREF_BY_ID, new Object[] { reportPrefId });
        }
    }

    /**
     * @param userID
     */
    private void deleteAlertsByUserId(final Integer userID) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userID", userID);

        StringBuilder alertByIDSelect = new StringBuilder();
        alertByIDSelect.append(FIND_ALERT_BY_USER);
        List<Integer> alertIDList = getSimpleJdbcTemplate().query(alertByIDSelect.toString(), alertIDRowMapper, params);
        for (int i=0;i<alertIDList.size();i++){
            Integer alertId = alertIDList.get(i);
            getJdbcTemplate().update(DEL_ALERT_BY_ID, new Object[] { alertId });
        }

    }


    public UserJDBCDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserJDBCDAO userDAO) {
        this.userDAO = userDAO;
    }

    public DriverJDBCDAO getDriverDAO() {
        return driverDAO;
    }

    public void setDriverDAO(DriverJDBCDAO driverDAO) {
        this.driverDAO = driverDAO;
    }


}

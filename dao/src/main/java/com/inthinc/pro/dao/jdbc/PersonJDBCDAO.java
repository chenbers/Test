package com.inthinc.pro.dao.jdbc;

import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.comm.parser.attrib.IntegerParser;
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
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    AddressJDBCDAO addressDAO = new AddressJDBCDAO();

    private static final DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");

    private static final String SELECT_PERSON = "select * from person p LEFT JOIN user u ON (p.personID=u.personID) LEFT JOIN driver d ON (p.personID=d.personID) LEFT JOIN timezone t ON (p.tzID=t.tzID) where 1=1 ";

    private static final String GROUP_ID_DEEP = SELECT_PERSON + " and u.groupID in (select groupID from groups where groupPath like :groupID) OR d.groupid in (select groupID from groups where groupPath like :groupID) and u.status <> 3";
    private static final String FIND_BY_EMAIL = SELECT_PERSON + " and p.priEmail = :email";
    private static final String FIND_BY_ACCOUNT = SELECT_PERSON + " and p.acctID = :accountID";
    private static final String FIND_BY_EMPID = SELECT_PERSON + " and p.acctID = :accountID and p.empid = :empID";
    private static final String FIND_BY_ID = SELECT_PERSON + " and p.personID = :personID";
    private static final String FIND_REPORT_PREF_BY_USER = "select reportPrefID from reportPref where userID = :userID";
    private static final String FIND_ALERT_BY_USER = "select alertID from alert where userID = :userID";
    private static final String TIME_ZONE_SELECT = "select tzID from timezone where tzName = :tzName";

    private static final String DEL_PERSON_BY_ID = "DELETE FROM person WHERE personID=?";
    private static final String DEL_REPORT_PREF_BY_ID = "DELETE FROM reportPref WHERE reportPrefID=?";
    private static final String DEL_ALERT_BY_ID = "DELETE FROM alert WHERE alertID=?";

    private static final String INSERT_PERSON = "INSERT into person (acctID, tzID, status, measureType, fuelEffType, " +
                    "addrID, locale, reportsTo, title, dept, empid, first, middle, last, suffix, gender, height, weight, dob, info, warn, " +
                    "crit, priEmail, secEmail, priPhone, secPhone, priText, secText, modified )" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_PERSON = "UPDATE person set acctID = ?, tzID = ?, status = ?, measureType = ?, fuelEffType = ?, " +
                    "addrID = ?, locale = ?, reportsTo = ?, title = ?, dept = ?, empid = ?, first = ?, middle = ?, last = ?, suffix = ?, gender = ?, height = ?, weight = ?, dob = ?, info = ?, warn = ?, " +
                    "crit = ?, priEmail = ?, secEmail = ?, priPhone = ?, secPhone = ?, priText = ?, secText = ?, modified = ? where personID = ?";

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
            person.setMeasurementType(rs.getObject("p.measureType") == null ? null : MeasurementType.valueOf(rs.getInt("p.measureType")));
            person.setTitle(getStringOrNullFromRS(rs, "p.title"));
            person.setMiddle(getStringOrNullFromRS(rs, "p.middle"));
            person.setSuffix(getStringOrNullFromRS(rs, "p.suffix"));
            person.setWeight(getIntOrNullFromRS(rs, "p.weight"));
            person.setTimeZone(TimeZone.getTimeZone(rs.getString("t.tzName")));

            Address address = new Address();
            address.setAddrID(getIntOrNullFromRS(rs, "addrID"));
            person.setAddress(address);

            if (getIntOrNullFromRS(rs, "u.userID") != null) {
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
            }

            if (getIntOrNullFromRS(rs, "d.driverID") != null) {
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
            }

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
        params.put("email", email);

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
        params.put("empID", empID);

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
    public Integer create(Integer acctID, final Person entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();

        if (entity.getAddress() != null && entity.getAddressID() == null)
        {
            Integer addressID = addressDAO.create(acctID, entity.getAddress());
            entity.getAddress().setAddrID(addressID);
            entity.setAddressID(addressID);
        }

        Integer personID;

        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_PERSON, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, entity.getAccountID());
                ps.setInt(2, getTimezoneID(entity.getTimeZone().getID()));

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

                ps.setString(8, entity.getReportsTo());
                ps.setString(9, entity.getTitle());
                ps.setString(10, entity.getDept());
                ps.setString(11, entity.getEmpid());
                ps.setString(12, entity.getFirst());
                ps.setString(13, entity.getMiddle());
                ps.setString(14, entity.getLast());
                ps.setString(15, entity.getSuffix());

                if (entity.getGender() == null || entity.getGender().getCode() == null) {
                    ps.setNull(16, Types.NULL);
                } else {
                    ps.setInt(16, entity.getGender().getCode());
                }

                if (entity.getHeight() == null) {
                    ps.setNull(17, Types.NULL);
                } else {
                    ps.setInt(17, entity.getHeight());
                }
                if (entity.getWeight() == null) {
                    ps.setNull(18, Types.NULL);
                } else {
                    ps.setInt(18, entity.getWeight());
                }

                if (entity.getDob() == null) {
                    ps.setNull(19, Types.NULL);
                } else {
                    ps.setDate(19, new java.sql.Date(entity.getDob().getTime()));
                }

                if (entity.getInfo() == null) {
                    ps.setNull(20, Types.NULL);
                } else {
                    ps.setInt(20, entity.getInfo());
                }

                if (entity.getWarn() == null) {
                    ps.setNull(21, Types.NULL);
                } else {
                    ps.setInt(21, entity.getWarn());
                }

                if (entity.getCrit() == null) {
                    ps.setNull(22, Types.NULL);
                } else {
                    ps.setInt(22, entity.getCrit());
                }
                ps.setString(23, entity.getPriEmail());
                ps.setString(24, entity.getSecEmail());
                ps.setString(25, entity.getPriPhone());
                ps.setString(26, entity.getSecPhone());
                ps.setString(27, entity.getPriText());
                ps.setString(28, entity.getSecText());

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String modified = df.format(toUTC(new Date()));
                ps.setString(29, modified);

                logger.debug(ps.toString());
                return ps;
            }
        };

        jdbcTemplate.update(psc, keyHolder);
        personID = keyHolder.getKey().intValue();

        if (entity.getUser() != null && (entity.getUser().getUserID() == null || entity.getUser().getUserID().intValue() == 0)) {
            entity.getUser().setPersonID(personID);
            Integer userID = userDAO.create(personID, entity.getUser());
            entity.getUser().setUserID(userID);
        }

        if (entity.getDriver() != null && (entity.getDriver().getDriverID() == null || entity.getDriver().getDriverID().intValue() == 0)) {
            entity.getDriver().setPersonID(personID);
            Integer driverID = driverDAO.create(personID, entity.getDriver());
            entity.getDriver().setDriverID(driverID);
        }

        return personID;
    }

    @Override
    public Integer update(final Person entity) {


        if ((entity.getAddress() != null) && !entity.getAddress().isEmpty() && entity.getAddress().getAddrID() != null)
        {
           addressDAO.update(entity.getAddress());
        }
        else if (entity.getAddress() != null && (entity.getAddressID() == null || entity.getAddressID().intValue() == 0))
        {
            Integer addressID = addressDAO.create(entity.getAccountID(), entity.getAddress());
            entity.getAddress().setAddrID(addressID);
            entity.setAddressID(addressID);
        }

        Integer changedID; 
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(UPDATE_PERSON, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, entity.getAccountID());
                ps.setInt(2, getTimezoneID(entity.getTimeZone().getID()));

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

                ps.setString(8, entity.getReportsTo());
                ps.setString(9, entity.getTitle());
                ps.setString(10, entity.getDept());
                ps.setString(11, entity.getEmpid());
                ps.setString(12, entity.getFirst());
                ps.setString(13, entity.getMiddle());
                ps.setString(14, entity.getLast());
                ps.setString(15, entity.getSuffix());

                if (entity.getGender() == null || entity.getGender().getCode() == null) {
                    ps.setNull(16, Types.NULL);
                } else {
                    ps.setInt(16, entity.getGender().getCode());
                }

                if (entity.getHeight() == null) {
                    ps.setNull(17, Types.NULL);
                } else {
                    ps.setInt(17, entity.getHeight());
                }
                if (entity.getWeight() == null) {
                    ps.setNull(18, Types.NULL);
                } else {
                    ps.setInt(18, entity.getWeight());
                }

                if (entity.getDob() == null) {
                    ps.setNull(19, Types.NULL);
                } else {
                    ps.setDate(19, new java.sql.Date(entity.getDob().getTime()));
                }

                if (entity.getInfo() == null) {
                    ps.setNull(20, Types.NULL);
                } else {
                    ps.setInt(20, entity.getInfo());
                }

                if (entity.getWarn() == null) {
                    ps.setNull(21, Types.NULL);
                } else {
                    ps.setInt(21, entity.getWarn());
                }

                if (entity.getCrit() == null) {
                    ps.setNull(22, Types.NULL);
                } else {
                    ps.setInt(22, entity.getCrit());
                }
                ps.setString(23, entity.getPriEmail());
                ps.setString(24, entity.getSecEmail());
                ps.setString(25, entity.getPriPhone());
                ps.setString(26, entity.getSecPhone());
                ps.setString(27, entity.getPriText());
                ps.setString(28, entity.getSecText());

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String modified = df.format(toUTC(new Date()));
                ps.setString(29, modified);
                ps.setInt(30, entity.getPersonID());

                logger.debug(ps.toString());
                return ps;
            }
        };

        jdbcTemplate.update(psc);
        changedID = entity.getPersonID();

        if (entity.getDriver() != null)
        {
            if (entity.getDriver().getDriverID() == null || entity.getDriver().getDriverID().intValue() == 0)
            {
                entity.getDriver().setPersonID(entity.getPersonID());
                Integer driverID = driverDAO.create(entity.getPersonID(),entity.getDriver());
                entity.getDriver().setDriverID(driverID);
            }
            else
            {
                driverDAO.update(entity.getDriver());
            }
        }

        if (entity.getUser() != null)
        {
            if (entity.getUser().getUserID() == null || entity.getUser().getUserID().intValue() == 0)
            {
                entity.getUser().setPersonID(entity.getPersonID());
                Integer userID = userDAO.create(entity.getPersonID(),entity.getUser());
                entity.getUser().setUserID(userID);
            }
            else
            {
                userDAO.update(entity.getUser());
            }
        }

        return changedID;

    }

    public Integer getTimezoneID(String tzName){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tzName", tzName );

        StringBuilder timezoneIDSelect = new StringBuilder(TIME_ZONE_SELECT);
        Integer timezoneID = getSimpleJdbcTemplate().queryForInt(timezoneIDSelect.toString(), params);

        return timezoneID;
    }

    @Override
    public Integer deleteByID(Integer integer) {
        return getJdbcTemplate().update(DEL_PERSON_BY_ID, new Object[] { integer });
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
        for (int i = 0; i < reportIDList.size(); i++) {
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
        for (int i = 0; i < alertIDList.size(); i++) {
            Integer alertId = alertIDList.get(i);
            getJdbcTemplate().update(DEL_ALERT_BY_ID, new Object[] { alertId });
        }

    }

    private Date toUTC(Date date) {
        DateTime dt = new DateTime(date.getTime()).toDateTime(DateTimeZone.UTC);
        return dt.toDate();
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

    public AddressJDBCDAO getAddressDAO() {
        return addressDAO;
    }

    public void setAddressDAO(AddressJDBCDAO addressDAO) {
        this.addressDAO = addressDAO;
    }
}

package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.Gender;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.configurator.ProductType;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by infrasoft05 on 4/2/14.
 */
public class PersonJDBCDAO extends SimpleJdbcDaoSupport implements PersonDAO{

    UserJDBCDAO userDAO = new UserJDBCDAO();
    private static final String SELECT_PERSON= "Select t.tzName,p.personID, p.acctID, p.tzID, p.modified, p.status, p.measureType, p.fuelEffType, p.addrID, p.locale, p.reportsTo," +
                    "    p.title,p.dept,p.empid, p.first, p.middle, p.last,p.suffix, p.gender, p.height, p.weight, p.dob, p.info, p.warn, p.crit, p.priEmail, p.secEmail, p.priPhone," +
                    "    p.secPhone, p.priText, p.secText, d.personID, d.driverid, d.groupid, d.barcode, d.rfid1, d.rfid2, d.fobID, d.license, d.stateid, d.expiration,  d.certs,d.dot," +
                    "    d.grouppath, d.class from driver d, person p,timezone t where d.driverID = :driverID and p.personID = d.personID and p.tzID=t.tzID; ";


    private ParameterizedRowMapper<Person> pagedPersonRowMapper = new ParameterizedRowMapper<Person>() {
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


//            person.setUser();
//
//            person.setDriver();



            return person;
        }
    };
    @Override
    public Person findByEmail(String email) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("p.email", email);

        StringBuilder personByEmailSelect = new StringBuilder();
        personByEmailSelect.append(email);
        try {
            Person person = getSimpleJdbcTemplate().queryForObject(personByEmailSelect.toString(), pagedPersonRowMapper, params);
            return person;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Person> getPeopleInGroupHierarchy(Integer groupID) {
        return null;
    }

    @Override
    public List<Person> getPeopleInGroupHierarchy(Integer userGroupID, Integer driverGroupID) {
        return null;
    }

    @Override
    public Integer delete(Person person) {
        return null;
    }

    @Override
    public List<Person> getPeopleInAccount(Integer acctID) {
        return null;
    }

    @Override
    public Person findByEmpID(Integer acctID, String empID) {
        return null;
    }

    @Override
    public Person findByID(Integer integer) {
        return null;
    }

    @Override
    public Integer create(Integer integer, Person entity) {
        return null;
    }

    @Override
    public Integer update(Person entity) {
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

    private Locale getLocale(String strLocale) {
        if (strLocale == null || strLocale.trim().isEmpty())
            return null;
        return new Locale(strLocale.trim());
    }

}

package com.inthinc.pro.dao.jdbc;

import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.LocationDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.DriverName;
import com.inthinc.pro.model.DriverStops;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.Gender;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.app.States;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
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
import java.sql.Statement;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class DriverJDBCDAO extends SimpleJdbcDaoSupport implements DriverDAO {
    private final Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));

    private ParameterizedRowMapper<Driver> driverMapper = new ParameterizedRowMapper<Driver>(){
        @Override
        public Driver mapRow(ResultSet rs, int rowNum) throws SQLException {
            Driver driver = new Driver();
            driver.setDriverID(rs.getInt("d.driverID"));
            driver.setGroupID(rs.getInt("d.groupID"));
            driver.setStatus(Status.valueOf(rs.getInt("d.status")));
            driver.setLicense(rs.getString("d.license"));
            driver.setLicenseClass(rs.getString("d.class"));
            driver.setState(States.getStateById(rs.getInt("d.stateID")));
            driver.setExpiration(rs.getDate("d.expiration", calendar));
            driver.setCertifications(rs.getString("d.certs"));
            driver.setDot(RuleSetType.valueOf(rs.getInt("d.dot")));
            driver.setBarcode(rs.getString("d.barcode"));
            driver.setRfid1(rs.getObject("d.rfid1") == null ? null : rs.getLong("d.rfid1"));
            driver.setRfid2(rs.getObject("d.rfid2")== null ? null : rs.getLong("d.rfid2"));
            driver.setFobID(rs.getString("d.fobID"));

            Person person = new Person();
            person.setPersonID(rs.getInt("d.personID"));
            person.setFirst(rs.getString("p.first"));
            person.setLast(rs.getString("p.last"));
            person.setDriver(driver);
            driver.setPerson(person);
            return driver;
        }
    };

    @Override
    public List<Driver> getAllDrivers(Integer groupID) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", groupID);
        List<Driver> driverList = new ArrayList<Driver>();
        List<Driver> driverFinal= new ArrayList<Driver>();

        List<Integer> allGroupId =getGroupIdDeep(groupID);
        System.out.print(allGroupId);

        for (Integer item :allGroupId){
            driverList =   getDrivers(item);
            for ( Driver driverItem :driverList) {
                driverFinal.add(driverItem);
            }
        }
        return driverFinal;

    }

    @Override
    public List<Driver> getDriversInGroupIDList(List<Integer> groupIDList) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("group_list", groupIDList);
        String select = "select d.*, p.first,p.last from driver d left outer join person p on d.personID = p.personID where d.status != 3 and d.groupID in (:group_list)";
        List<Driver> driverList = getSimpleJdbcTemplate().query(select, driverMapper, params);
        return driverList;
    }

    private LocationDAO locationDAO;
    private VehicleDAO vehicleDAO;

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    DateFormat dfNew = new SimpleDateFormat("yyyy-MM-dd");


    private static final String FIND_DRIVER_BY_ID="Select d.status, t.tzName,p.personID, p.acctID, p.tzID, p.modified, p.status, p.measureType, p.fuelEffType, p.addrID, p.locale, p.reportsTo," +
            "    p.title,p.dept,p.empid, p.first, p.middle, p.last,p.suffix, p.gender, p.height, p.weight, p.dob, p.info, p.warn, p.crit, p.priEmail, p.secEmail, p.priPhone," +
            "    p.secPhone, p.priText, p.secText, d.personID, d.driverid, d.groupid, d.barcode, d.rfid1, d.rfid2, d.fobID, d.license, d.stateid, d.expiration,  d.certs,d.dot," +
            "    d.grouppath, d.class from driver d, person p,timezone t where d.driverId = :driverId and p.personID = d.personID and p.tzID=t.tzID; ";
    private static final String SELECT_DRIVERS_BY_GROUPID ="Select d.status, t.tzName,d.driverid,d.groupid,d.barcode, d.rfid1, d.rfid2, d.fobID, d.license, d.stateid, d.expiration," +
            "    d.certs,d.dot,d.grouppath, d.class, p.personid,p.acctid, p.tzid,p.modified, p.status,p.measuretype,p.fuelefftype,p.addrid,p.locale,p.reportsto," +
            "    p.title,p.dept,p.empid,p.first,p.middle, p.last, p.suffix, p.gender, p.height, p.weight,  p.dob, p.info,p.warn, p.crit, p.priemail, p.secemail," +
            "    p.priphone, p.secphone,p.pritext,p.sectext from driver d, person p,timezone t" +
            "    where  d.personID = p.personID and d.status <>3 and p.tzID=t.tzID and d.groupID like :groupId ;";
    private static final String SELECT_FIND_BY_PERSONID ="select d.driverid,d.groupid,d.barcode, d.rfid1, d.rfid2, d.fobID, d.license, d.stateid, d.expiration," +
            "     d.certs,d.dot,d.grouppath, d.class from driver d where d.personId = :personID and d.status <>3";
    private static final String SELECT_RFID="select rfid  from rfid where barcode like :barcode order by rfid";
    private static final String SELECT_DRIVERS_BY_BARCODE = "Select driverId from driver where barcode like :barcode";
    private static final String SELECT_DRIVER_NAMES = "SELECT d.driverID,concat_ws(' ', IF(p.first='',NULL,p.first),IF(p.middle='',NULL,p.middle)," +
            "IF(p.last='',NULL,p.last),IF(p.suffix='',NULL,p.suffix)) as driverName FROM driver d, person p WHERE d.status!=3 " +
            "AND  d.groupPath IN (SELECT groupPath from groups where groupId like :groupId ) AND d.personID=p.personID";
    private static final String INSERT_DRIVER = "INSERT INTO driver(groupID, certs, status, rfid1, rfid2, class, barcode, license, fobID, dot,personID," +
            "groupPath,stateId,expiration,modified,aggDate) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_DRIVER_ACCOUNT = "UPDATE driver " +
            " set " +
            " groupID = ?," +
            " personID = ?," +
            " status = ?," +
            " license = ?," +
            " stateID = ?," +
            " class = ?," +
            " expiration = ?," +
            " dot = ?," +
            " groupPath = ?, " +     //de aici
            " certs = ?, "+
            " rfid1 = ?, "+
            " rfid2 = ?, "+
            " barcode = ?, "+
            " fobID = ?, "+
            " newaggDate = ? "+
            " where driverId = ?";
    private static final String DEL_DRIVER_BY_ID = "DELETE FROM driver WHERE driverID = ?";
    private static final String GET_GROUP_PATH = "select g.groupPath from groups g where g.groupID= :groupID";

    private static final DateTimeFormatter dateFormatter = DateTimeFormat.forPattern(" yyyy-MM-dd HH:mm:ss.SZ");



    public List<Integer> getGroupIdDeep(Integer groupId){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", groupId);

        List<String> groupPath = getSimpleJdbcTemplate().query("select groupPath from groups where groupID like :groupId ",pagedDriverPathMapper, params);

        Map<String, Object> params1 = new HashMap<String, Object>();
        params1.put("groupPath", groupPath.get(0)+"%");
        return getSimpleJdbcTemplate().query("select groupId from groups where status<>3 and groupPath like :groupPath",pagedDriverIDMapper,params1);
    }


    @Override
    public List<Driver> getDrivers(Integer groupID) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", groupID);

        StringBuilder driverSelect = new StringBuilder();
        driverSelect.append(SELECT_DRIVERS_BY_GROUPID);

        List<Driver> driverList = getSimpleJdbcTemplate().query(driverSelect.toString(), pagedDriverRowMapper, params);
        return driverList;
    }

    @Override
    public LastLocation getLastLocation(Integer driverID) {
        return locationDAO.getLastLocationForDriver(driverID);
    }

    @Override
    public List<Trip> getTrips(Integer driverID, Date startDate, Date enate) {
        return locationDAO.getTripsForDriver(driverID, startDate, enate, false);
    }

    @Override
    public List<Trip> getTrips(Integer driverID, Date startDate, Date enate, Boolean includeRoute) {
        return locationDAO.getTripsForDriver(driverID, startDate, enate, includeRoute);
    }

    @Override
    public List<Trip> getTrips(Integer driverID, Interval interval) {
        return getTrips(driverID, interval.getStart().toDateTime().toDate(), interval.getEnd().toDateTime().toDate());
    }

    @Override
    public List<Trip> getTrips(Integer driverID, Interval interval, Boolean includeRoute) {
        return getTrips(driverID, interval.getStart().toDateTime().toDate(), interval.getEnd().toDateTime().toDate(), includeRoute);
    }

    @Override
    public Trip getLastTrip(Integer driverID) {
        return locationDAO.getLastTripForDriver(driverID);
    }

    @Override
    public List<LatLng> getLocationsForTrip(Integer driverID, Date startTime, Date endTime) {
        return locationDAO.getLocationsForDriverTrip(driverID, startTime, endTime);
    }

    @Override
    public List<LatLng> getLocationsForTrip(Integer driverID, Interval interval) {
        return getLocationsForTrip(driverID, interval.getStart().toDateTime().toDate(), interval.getEnd().toDateTime().toDate());
    }

    @Override
    public Driver findByPersonID(Integer personID) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("personID", personID);

        StringBuilder driverSelectByPersonId = new StringBuilder();
        driverSelectByPersonId.append(SELECT_FIND_BY_PERSONID);

        Driver driver = getSimpleJdbcTemplate().queryForObject(driverSelectByPersonId.toString(), pagedDriverRowMapper, params);

        return driver;
    }

    @Override
    public List<Long> getRfidsByBarcode(String barcode) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("barcode", barcode);

        StringBuilder rfidSelect = new StringBuilder();
        rfidSelect.append(SELECT_RFID);

        List<Long> rfidList = getSimpleJdbcTemplate().query(rfidSelect.toString(),pagedRfidsMapper,params);

        return rfidList;
    }

    @Override
    public Integer getDriverIDByBarcode(String barcode) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("barcode", barcode);

        StringBuilder driverByBarcodeSelect = new StringBuilder();
        driverByBarcodeSelect.append(SELECT_DRIVERS_BY_BARCODE);

        Integer driverIdByBarcoder = getSimpleJdbcTemplate().queryForInt(driverByBarcodeSelect.toString(),params);

        return driverIdByBarcoder;
    }

    @Override
    public List<DriverLocation> getDriverLocations(Integer groupID) {
        return locationDAO.getDriverLocations(groupID);
    }

    @Override
    public List<DriverStops> getStops(Integer driverID, String driverName, Interval interval) {
        return locationDAO.getStops(driverID, driverName, interval);
    }

    @Override
    public List<DriverName> getDriverNames(Integer groupID) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", groupID);

        StringBuilder driverNameSelect = new StringBuilder();
        driverNameSelect.append(SELECT_DRIVER_NAMES);

        List<DriverName> driveNameList = getSimpleJdbcTemplate().query(driverNameSelect.toString(), pagedDriverNameRowMapper, params);
        return driveNameList;
    }

    @Override
    public Driver findByID(Integer driverId) {
        try{
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("driverId", driverId);
        StringBuilder driverIdSelect = new StringBuilder(FIND_DRIVER_BY_ID);
        return getSimpleJdbcTemplate().queryForObject(driverIdSelect.toString(), pagedDriverRowMapper, args);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Integer create(final Integer integer, final Driver entity) {
       JdbcTemplate jdbcTemplate = getJdbcTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_DRIVER, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, entity.getGroupID());

                if (entity.getCertifications() == null ) {
                    ps.setNull(2, Types.NULL);
                } else {
                    ps.setString(2, entity.getCertifications());
                }

                if (entity.getStatus() == null || entity.getStatus().getCode() == null) {
                    ps.setNull(3, Types.NULL);
                } else {
                    ps.setInt(3, entity.getStatus().getCode());
                }
                if (entity.getRfid1() == null ) {
                    ps.setNull(4, Types.NULL);
                } else {
                    ps.setLong(4, entity.getRfid1());
                }

                if (entity.getRfid2() == null ) {
                    ps.setNull(5, Types.NULL);
                } else {
                    ps.setLong(5, entity.getRfid2());
                }

                if (entity.getLicenseClass() == null ) {
                    ps.setNull(6, Types.NULL);
                } else {
                    ps.setString(6, entity.getLicenseClass());
                }

                if (entity.getBarcode() == null ) {
                    ps.setNull(7, Types.NULL);
                } else {
                    ps.setString(7,entity.getBarcode());
                }
                if (entity.getLicense() == null ) {
                    ps.setNull(8, Types.NULL);
                } else {
                    ps.setString(8,entity.getLicense());
                }

                if (entity.getFobID() == null ) {
                    ps.setNull(9, Types.NULL);
                } else {
                    ps.setString(9, entity.getFobID());
                }

                if (entity.getDot() == null || entity.getDot().getCode() == null) {
                    ps.setNull(10, Types.NULL);
                } else {
                    ps.setInt(10, entity.getDot().getCode());
                }

                ps.setInt(11,integer);
                ps.setString(12, getPathByGroupId(entity.getGroupID()));
                if(entity.getState()==null) {
                    ps.setNull(13, Types.NULL);
                }else{
                ps.setInt(13,entity.getState().getStateID());
                }
                if (entity.getExpiration() == null) {
                    ps.setNull(14, Types.NULL);
                } else {
                    ps.setDate(14, new java.sql.Date(new DateTime(entity.getExpiration()).getMillis()));
                }

                df.setTimeZone(TimeZone.getTimeZone("UTC"));
                String modified = df.format(toUTC(new Date()));
                ps.setString(15, modified);

                dfNew.setTimeZone(TimeZone.getTimeZone("UTC"));
                String aggDate = dfNew.format(toUTC(new Date()));
                 ps.setString(16,aggDate);

                logger.debug(ps.toString());
                return ps;
            }
        };
        jdbcTemplate.update(psc, keyHolder);
        return keyHolder.getKey().intValue();

    }

    @Override
    public Integer update(final Driver entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                if (entity.getDriverID() == null)
                    throw new SQLException("Cannot update group with null id.");
                PreparedStatement ps = con.prepareStatement(UPDATE_DRIVER_ACCOUNT);
                ps.setInt(1, entity.getGroupID());
                ps.setInt(2,entity.getPersonID());
                if (entity.getStatus() == null || entity.getStatus().getCode() == null) {
                    ps.setNull(3, Types.NULL);
                } else {
                    ps.setInt(3, entity.getStatus().getCode());
                }

                if (entity.getLicense() == null ) {
                    ps.setNull(4, Types.NULL);
                } else {
                    ps.setString(4, entity.getLicense());
                }

                if (entity.getState() == null || entity.getState().getStateID() == null ) {
                    ps.setNull(5, Types.NULL);
                } else {
                    ps.setInt(5, entity.getState().getStateID());
                }
                if (entity.getLicenseClass() == null ) {
                    ps.setNull(6, Types.NULL);
                } else {
                    ps.setString(6, entity.getLicenseClass());
                }

                if (entity.getExpiration() == null) {
                    ps.setNull(7, Types.NULL);
                } else {
                    ps.setDate(7, new java.sql.Date(new DateTime (entity.getExpiration()).getMillis()));
                }
                if (entity.getDot() == null || entity.getDot().getCode() == null) {
                    ps.setNull(8, Types.NULL);
                } else {
                    ps.setInt(8, entity.getDot().getCode());
                } 
                ps.setString(9, getPathByGroupId(entity.getGroupID()));

                if (entity.getCertifications() == null) {
                    ps.setNull(10, Types.NULL);
                } else {
                    ps.setString(10, entity.getCertifications());
                }

                if (entity.getRfid1() == null) {
                    ps.setNull(11, Types.NULL);
                } else {
                    ps.setLong(11, entity.getRfid1());
                }

                if (entity.getRfid2() == null) {
                    ps.setNull(12, Types.NULL);
                } else {
                    ps.setLong(12, entity.getRfid2());
                }

                if (entity.getBarcode() == null) {
                    ps.setNull(13, Types.NULL);
                } else {
                    ps.setString(13, entity.getBarcode());
                }

                ps.setString(14, entity.getFobID());

                dfNew.setTimeZone(TimeZone.getTimeZone("UTC"));
                String aggDate = dfNew.format(toUTC(new Date()));
                ps.setString(15,aggDate);
                ps.setInt(16, entity.getDriverID());

                logger.debug(ps.toString());
                return ps;
            }
        };
        jdbcTemplate.update(psc);
        return entity.getDriverID();

    }

    @Override
    public Integer deleteByID(Integer integer) {
        return getJdbcTemplate().update(DEL_DRIVER_BY_ID, new Object[]{integer});

    }
    private ParameterizedRowMapper<Driver> pagedDriverRowMapper = new ParameterizedRowMapper<Driver>() {
        @Override
        public Driver mapRow(ResultSet rs, int rowNum) throws SQLException {
            Driver driver = new Driver();
            Person person = new Person();

            person.setPersonID(rs.getInt("p.personID"));
            person.setAcctID(rs.getInt("p.acctID"));
            person.setStatus(Status.valueOf(rs.getInt("p.status")));
            person.setMeasurementType(MeasurementType.valueOf(rs.getInt("p.measureType")));
            person.setFuelEfficiencyType(FuelEfficiencyType.valueOf(rs.getInt("p.fuelEffType")));
            person.setAddressID(rs.getInt("p.addrID"));
            person.setLocale(getLocale(rs.getString("p.locale")));
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
            driver.setDriverID(rs.getInt("d.driverID"));
            driver.setGroupID(rs.getInt("d.groupID"));
//            driver.setStatus(status);
            driver.setLicense(rs.getString("d.license"));
            driver.setLicenseClass(rs.getString("d.class"));
            driver.setState(States.getStateById(rs.getInt("d.stateID")));         //?
            driver.setExpiration(rs.getDate("d.expiration", calendar));
            driver.setCertifications(rs.getString("d.certs"));
            driver.setDot(RuleSetType.valueOf(rs.getInt("d.dot")));         //?
            driver.setBarcode(rs.getString("d.barcode"));
            driver.setRfid1(rs.getObject("d.rfid1") == null ? null : rs.getLong("d.rfid1"));
            driver.setRfid2(rs.getObject("d.rfid2")== null ? null : rs.getLong("d.rfid2"));
            driver.setFobID(rs.getString("d.fobID"));
            driver.setStatus(Status.valueOf(rs.getInt("d.status")));
//            driver.setPersonID(rs.getInt("d.personId"));
            driver.setPerson(person);

            return driver;
        }
    };

    private Locale getLocale(String strLocale){
        if (strLocale == null  || strLocale.trim().isEmpty())
            return null;
        return new Locale(strLocale.trim());
    }

    private ParameterizedRowMapper<Long> pagedRfidsMapper = new ParameterizedRowMapper<Long>() {

        @Override
        public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
            //addd
            return null;
        }
    };

    private ParameterizedRowMapper<String> pagedDriverPathMapper = new ParameterizedRowMapper<String>() {

        @Override
        public String mapRow(ResultSet rs, int rowNum) throws SQLException {
            String groupPath = "";
            groupPath = groupPath+ rs.getString("groupPath");
                return groupPath;
        }
    };
    private ParameterizedRowMapper<Integer> pagedDriverIDMapper = new ParameterizedRowMapper<Integer>() {

        @Override
        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
           return getIntOrNullFromRS(rs, "groupId");
        }
    };
    private ParameterizedRowMapper<DriverName> pagedDriverNameRowMapper = new ParameterizedRowMapper<DriverName>() {

        @Override
        public DriverName mapRow(ResultSet rs, int rowNum) throws SQLException {
            DriverName driverName= new DriverName();
            driverName.setDriverID(rs.getInt("d.driverID"));
            driverName.setDriverName(rs.getString("driverName"));
            return null;
        }
    };
    public VehicleDAO getVehicleDAO() {
        return vehicleDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    public LocationDAO getLocationDAO() {
        return locationDAO;
    }

    public void setLocationDAO(LocationDAO locationDAO) {
        this.locationDAO = locationDAO;
    }

    private Integer getIntOrNullFromRS(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) == null ? null : (int) rs.getLong(columnName);
    }
    private String getPathByGroupId(Integer groupID){
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("groupID", groupID);
        String groupPath = new String (GET_GROUP_PATH);
        String grPath = getSimpleJdbcTemplate().queryForObject(groupPath, String.class, args);

        return grPath;
    }
    private Date toUTC(Date date){
        DateTime dt = new DateTime(date.getTime()).toDateTime(DateTimeZone.UTC);
        return dt.toDate();
    }
}

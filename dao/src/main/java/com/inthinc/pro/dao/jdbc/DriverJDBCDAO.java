package com.inthinc.pro.dao.jdbc;

import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.LocationDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.DeviceStatus;
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
import com.inthinc.pro.model.pagination.SortOrder;
import com.mysql.jdbc.Statement;
import org.apache.commons.lang.NotImplementedException;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import sun.util.locale.LocaleUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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
    private LocationDAO locationDAO;
    private VehicleDAO vehicleDAO;
    final Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
    private static final String FIND_DRIVER_BY_ID="Select t.tzName,p.personID, p.acctID, p.tzID, p.modified, p.status, p.measureType, p.fuelEffType, p.addrID, p.locale, p.reportsTo," +
            "    p.title,p.dept,p.empid, p.first, p.middle, p.last,p.suffix, p.gender, p.height, p.weight, p.dob, p.info, p.warn, p.crit, p.priEmail, p.secEmail, p.priPhone," +
            "    p.secPhone, p.priText, p.secText, d.personID, d.driverid, d.groupid, d.barcode, d.rfid1, d.rfid2, d.fobID, d.license, d.stateid, d.expiration,  d.certs,d.dot," +
            "    d.grouppath, d.class from driver d, person p,timezone t where d.driverId = :driverId and p.personID = d.personID and p.tzID=t.tzID; ";
    private static final String SELECT_DRIVERS_BY_GROUPID ="Select  d.driverid,d.groupid,d.barcode, d.rfid1, d.rfid2, d.fobID, d.license, d.stateid, d.expiration," +
            "    d.certs,d.dot,d.grouppath, d.class, p.personid,p.acctid, p.tzid,p.modified, p.status,p.measuretype,p.fuelefftype,p.addrid,p.locale,p.reportsto," +
            "    p.title,p.dept,p.empid,p.first,p.middle, p.last, p.suffix, p.gender, p.height, p.weight,  p.dob, p.info,p.warn, p.crit, p.priemail, p.secemail," +
            "    p.priphone, p.secphone,p.pritext,p.sectext from driver d, person p" +
            "    where  d.personID = p.personID and d.status <>3 and d.groupID like :groupId ;";
    private static final String SELECT_FIND_BY_PERSONID ="select d.driverid,d.groupid,d.barcode, d.rfid1, d.rfid2, d.fobID, d.license, d.stateid, d.expiration," +
            "     d.certs,d.dot,d.grouppath, d.class from driver d where d.personId = :personID and d.status <>3";
    private static final String SELECT_RFID="select rfid  from rfid where barcode like :barcode order by rfid";
    private static final String SELECT_DRIVERS_BY_BARCODE = "Select driverId from driver where barcode like :barcode";
    private static final String SELECT_DRIVER_NAMES = "SELECT d.driverID,concat_ws(' ', IF(p.first='',NULL,p.first),IF(p.middle='',NULL,p.middle)," +
            "IF(p.last='',NULL,p.last),IF(p.suffix='',NULL,p.suffix)) FROM driver d, person p WHERE d.status!=3 " +
            "AND  d.groupPath IN (SELECT groupPath from groups where groupId like :groupId ) AND d.personID=p.personID";
    private static final String INSERT_DRIVER = "INSERT INTO driver(groupID, certs, status, rfid1, rfid2, class, barcode, license, fobID, dot,personID))" +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_DRIVER_ACCOUNT = "UPDATE driver " +
            " set " +
            " groupID = ?," +
            " personID = ?," +
            " status = ?," +
            " modified = ?," +
            " license = ?," +
            " stateID = ?," +
            " class = ?," +
            " expiration = ?," +
            " dot = ?," +
            " groupPath = ?," +
            " where driverId = ?";
    private static final String DEL_DRIVER_BY_ID = "DELETE FROM driver WHERE driverID = ?";

    private static final DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");

    @Override
    public List<Driver> getAllDrivers(Integer groupID) {
        //getDriversByGroupIDDeep - din portal_rep + transformate groupId in lista de groupId
        return null;
    }

    @Override
    public List<Driver> getDrivers(Integer groupID) {
        //getDriversByGroupID
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", groupID);

        StringBuilder driverSelect = new StringBuilder();
        driverSelect.append(SELECT_DRIVERS_BY_GROUPID);

        List<Driver> driverList = getSimpleJdbcTemplate().query(driverSelect.toString(), pagedDriverRowMapper, params);
        return driverList;
    }

    @Override
    public LastLocation getLastLocation(Integer driverID) {
        //locationDAO.getLastLocationForDriver(driverID);
        return locationDAO.getLastLocationForDriver(driverID);
    }

    @Override
    public List<Trip> getTrips(Integer driverID, Date startDate, Date enate) {
        //return locationDAO.getTripsForDriver(driverID, startDate, endDate, false);
        return locationDAO.getTripsForDriver(driverID, startDate, enate, false);
    }

    @Override
    public List<Trip> getTrips(Integer driverID, Date startDate, Date enate, Boolean includeRoute) {
        //return locationDAO.getTripsForDriver(driverID, startDate, endDate, includeRoute);
        return locationDAO.getTripsForDriver(driverID, startDate, enate, includeRoute);
    }

    @Override
    public List<Trip> getTrips(Integer driverID, Interval interval) {
        // return getTrips(driverID, interval.getStart().toDateTime().toDate(), interval.getEnd().toDateTime().toDate());
        return getTrips(driverID, interval.getStart().toDateTime().toDate(), interval.getEnd().toDateTime().toDate());
    }

    @Override
    public List<Trip> getTrips(Integer driverID, Interval interval, Boolean includeRoute) {
        //		return getTrips(driverID, interval.getStart().toDateTime().toDate(), interval.getEnd().toDateTime().toDate(), includeRoute);
        return getTrips(driverID, interval.getStart().toDateTime().toDate(), interval.getEnd().toDateTime().toDate(), includeRoute);
    }

    @Override
    public Trip getLastTrip(Integer driverID) {
        //return locationDAO.getLastTripForDriver(driverID);
        return locationDAO.getLastTripForDriver(driverID);
    }

    @Override
    public List<LatLng> getLocationsForTrip(Integer driverID, Date startTime, Date endTime) {
        //        return locationDAO.getLocationsForDriverTrip(driverID, startTime, endTime);
        return locationDAO.getLocationsForDriverTrip(driverID, startTime, endTime);
    }

    @Override
    public List<LatLng> getLocationsForTrip(Integer driverID, Interval interval) {
        //        return getLocationsForTrip(driverID, interval.getStart().toDateTime().toDate(), interval.getEnd().toDateTime().toDate());
        return getLocationsForTrip(driverID, interval.getStart().toDateTime().toDate(), interval.getEnd().toDateTime().toDate());
    }

    @Override
    public Driver findByPersonID(Integer personID) {
        //getDriverByPersonID
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("personID", personID);

        StringBuilder driverSelectByPersonId = new StringBuilder();
        driverSelectByPersonId.append(SELECT_FIND_BY_PERSONID);

        Driver driver = getSimpleJdbcTemplate().queryForObject(driverSelectByPersonId.toString(), pagedDriverRowMapper, params);

        return driver;
    }

    @Override
    public List<Long> getRfidsByBarcode(String barcode) {

//        List<Long> rfids = getSiloService().getRfidsForBarcode(barcode);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("barcode", barcode);

        StringBuilder rfidSelect = new StringBuilder();
        rfidSelect.append(SELECT_RFID);

        List<Long> rfidList = getSimpleJdbcTemplate().query(rfidSelect.toString(),pagedRfidsMapper,params);

        return rfidList;
    }

    @Override
    public Integer getDriverIDByBarcode(String barcode) {

        //            Map<String, Object> returnMap = getSiloService().getID(BARCODE_KEY, barcode);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("barcode", barcode);

        StringBuilder driverByBarcodeSelect = new StringBuilder();
        driverByBarcodeSelect.append(SELECT_DRIVERS_BY_BARCODE);

        Integer driverIdByBarcoder = getSimpleJdbcTemplate().queryForInt(driverByBarcodeSelect.toString(),params);

        return driverIdByBarcoder;
    }

    @Override
    public List<DriverLocation> getDriverLocations(Integer groupID) {
        //		return locationDAO.getDriverLocations(groupID);
        return locationDAO.getDriverLocations(groupID);
    }

    @Override
    public List<DriverStops> getStops(Integer driverID, String driverName, Interval interval) {
        //		return locationDAO.getStops(driverID, driverName, interval);
        return locationDAO.getStops(driverID, driverName, interval);
    }

    @Override
    public List<DriverName> getDriverNames(Integer groupID) {
        /*  from python

        * def getDriverNamesByGroupIDDeep(params):
	"fetch driver names by groupID deep"
	(result,errmsg) = checkParams('getDriverNamesByGroupIDDeep', (int,), params)
	if result != TiwiErr.TSCodeSuccess:
		syslog(LOG_ERR, errmsg)
	else:
		groupID = params[0]
		try:
			from siloAPI.tiwigroup import TiwiGroup
			grp = TiwiGroup.objects.get(pk=groupID)
			from django.db import connection
			query = "SELECT d.driverID,concat_ws(' ', IF(p.first='',NULL,p.first),IF(p.middle='',NULL,p.middle),IF(p.last='',NULL,p.last),IF(p.suffix='',NULL,p.suffix)) FROM driver d, person p WHERE d.status!=3 AND d.groupPath LIKE '%s%%' AND d.personID=p.personID" % (grp.grouppath,)
			cur = connection.cursor()
			cur.execute(query)
			rows = cur.fetchall()
			result = [iConvertDriverNameRow(row)  for row in rows]      -- driverId si DriverName
			cur.close()
		except Exception, e:
			result = TiwiErr.TSCodeExecute
			syslog(LOG_ERR, 'getDriverNamesByGroupIDDeep: failed, groupID=%d, exception=%s' % (groupID, str(e)))
	return result
        * */
        //            List<DriverName> driverList = getMapper().convertToModelObject(this.getSiloService().getDriverNamesByGroupIDDeep(groupID), DriverName.class);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", groupID);

        StringBuilder driverNameSelect = new StringBuilder();
        driverNameSelect.append(SELECT_DRIVER_NAMES);

        List<DriverName> driveNameList = getSimpleJdbcTemplate().query(driverNameSelect.toString(), pagedDriverNameRowMapper, params);
        return driveNameList;
    }
    //genericdao
    @Override
    public Driver findByID(Integer driverId) {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("driverId", driverId);
        StringBuilder driverIdSelect = new StringBuilder(FIND_DRIVER_BY_ID);
        return getSimpleJdbcTemplate().queryForObject(driverIdSelect.toString(), pagedDriverRowMapper, args);

        // throw new NotImplementedException();
    }

    @Override
    public Integer create(Integer integer, final Driver entity) {
       /* JdbcTemplate jdbcTemplate = getJdbcTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_DRIVER, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, entity.getGroupID());
                ps.setString(2, entity.getCertifications());
                if (entity.getStatus() == null || entity.getStatus().getCode() == null) {
                    ps.setNull(3, Types.NULL);
                } else {
                    ps.setInt(3, entity.getStatus().getCode());
                }
                ps.setLong(4, entity.getRfid1());
                ps.setLong(5,entity.getRfid2());
                ps.setString(6, entity.getLicenseClass());
                ps.setString(7,entity.getLicense());
                ps.setString(8,entity.getBarcode());
                ps.setString(9,entity.getLicense());
                ps.setInt(10,entity.getState().getStateID());
                ps.setInt(11,entity.getPersonID());

                logger.debug(ps.toString());
                return ps;
            }
        };
        jdbcTemplate.update(psc, keyHolder);
        return keyHolder.getKey().intValue();
        // update ? */
        throw new NotImplementedException();
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
                //? modified
                ps.setString(5,entity.getLicense());
                //? stateId
                if (entity.getExpiration() == null) {
                    ps.setNull(7, Types.NULL);
                } else {
                    ps.setDate(7, new java.sql.Date(dateFormatter.parseDateTime(entity.getExpiration().toString()).getMillis()));
                }
                if (entity.getDot() == null || entity.getDot().getCode() == null) {
                    ps.setNull(8, Types.NULL);
                } else {
                    ps.setInt(8, entity.getDot().getCode());
                }
                ps.setInt(11, entity.getDriverID());
                logger.debug(ps.toString());
                return ps;
            }
        };
        jdbcTemplate.update(psc);
        return entity.getDriverID();

        //  throw new NotImplementedException();
    }

    @Override
    public Integer deleteByID(Integer integer) {
        return getJdbcTemplate().update(DEL_DRIVER_BY_ID, new Object[]{integer});

        // throw new NotImplementedException();
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
//            driver.setStatus(rs.getObject("status") == null ? null : Devi.valueOf(rs.getInt("status")));
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
    private ParameterizedRowMapper<DriverName> pagedDriverNameRowMapper = new ParameterizedRowMapper<DriverName>() {

        @Override
        public DriverName mapRow(ResultSet rs, int rowNum) throws SQLException {
            //addd
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
}

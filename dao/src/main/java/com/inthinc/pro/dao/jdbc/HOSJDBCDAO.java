package com.inthinc.pro.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.inthinc.hos.ddl.HOSOccupantLog;
import com.inthinc.hos.model.HOSOrigin;
import com.inthinc.hos.model.HOSStatus;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.InspectionType;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.configurator.SettingType;
import com.inthinc.pro.model.hos.HOSDriverLogin;
import com.inthinc.pro.model.hos.HOSGroupMileage;
import com.inthinc.pro.model.hos.HOSOccupantHistory;
import com.inthinc.pro.model.hos.HOSOccupantInfo;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.model.hos.HOSVehicleMileage;



@SuppressWarnings("unchecked")
public class HOSJDBCDAO extends NamedParameterJdbcDaoSupport implements HOSDAO {
    
    private final static String SELECT_SQL = "SELECT h.hosLogID, h.driverID, h.vehicleID, h.logTime, "+ 
            "tz.tzName, h.status, h.driverDOTType, h.vehicleIsDOTFlag, h.vehicleOdometer, h.origin, coalesce(h.trailerID, '') AS trailerID, coalesce(h.serviceID, '') AS serviceID, "+
            "h.latitude, h.longitude, coalesce(h.location, '') AS location, coalesce(cl.location, '') AS originalLocation, " +
            "h.deletedFlag, h.editedFlag, h.editCount, h.editUserName, " +
            "h.truckGallons, h.trailerGallons, coalesce(h.tripReportFlag, 0) AS tripReportFlag, coalesce(h.tripInspectionFlag, 0) tripInspectionFlag, " + 
            "coalesce(v.name, '') AS vehicleName, cl.logTime AS originalLogTime, coalesce(v.license, '') as vehicleLicense, p.empid, h.editUserID, " +
            "IF((SELECT count(*) FROM hosvehiclelogin WHERE vehicleID = h.vehicleID AND driverID != h.driverID AND h.logTime BETWEEN loginTime AND coalesce(logoutTime, now())) > 0, 0, 1) 'singleDriver', " +
            "cl.status as originalStatus, h.mobileUnitId, h.inspectionType, h.reason, h.approvedBy, h.editor, h.timeStamp " +
            "FROM hoslog h LEFT JOIN vehicle v ON (h.vehicleID = v.vehicleID) LEFT JOIN hoslog_changelog cl ON  (h.hosLogID = cl.hosLogID), person p, driver d, timezone tz " +
            "WHERE h.tzID = tz.tzID AND d.driverID = h.driverID AND p.personID = d.personID ";

    private final static String SELECT_SIMPLE_SQL = "SELECT h.hosLogID, h.driverID, h.vehicleID, h.logTime, "+ 
            "tz.tzName, h.status, h.driverDOTType, h.vehicleIsDOTFlag, h.vehicleOdometer, h.origin, coalesce(h.trailerID, '') AS trailerID, coalesce(h.serviceID, '') AS serviceID, "+
            "h.latitude, h.longitude, coalesce(h.location, '') AS location, '' AS originalLocation, " +
            "h.deletedFlag, h.editedFlag, h.editCount, h.editUserName, " +
            "h.truckGallons, h.trailerGallons, coalesce(h.tripReportFlag, 0) AS tripReportFlag, coalesce(h.tripInspectionFlag, 0) tripInspectionFlag, " + 
            "coalesce(v.name, '') AS vehicleName, '' AS originalLogTime, coalesce(v.license, '') as vehicleLicense, p.empid, h.editUserID, " +
            "1 as 'singleDriver', " +
            "-1 as originalStatus, h.mobileUnitId, h.inspectionType, h.reason, h.approvedBy, h.editor, h.timeStamp " +
            "FROM hoslog h LEFT JOIN vehicle v ON (h.vehicleID = v.vehicleID), person p, driver d, timezone tz " +
            "WHERE h.tzID = tz.tzID AND d.driverID = h.driverID AND p.personID = d.personID ";

    
    
//    private final static String DRIVER_FOR_EMPID_ADDRESS_SQL = "SELECT d.driverID, coalesce(d.dot, 0) AS dot, tz.tzName, coalesce(d.fobID, '') AS fobId " +
//            "FROM driver d left join person p on p.personID = d.personID left join timezone tz on p.tzID = tz.tzID " +
//            "where p.empid=:employeeId and p.last=:last";
    



    private final static String FIND_BY_ID_SQL = SELECT_SQL + " AND h.hosLogID = :id ";

    @Override
    public HOSRecord findByID(Long id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        return (HOSRecord) getNamedParameterJdbcTemplate().queryForObject(FIND_BY_ID_SQL, params, new HOSRecordRowMapper());
    }

    private final static String UPDATE_SQL = "UPDATE hoslog "+
            "SET driverID=:driverID, vehicleID=:vehicleID, logTime=:logTime,tzID =:tzID, status=:status, driverDOTType=:driverDOTType, "+
            "employeeId=coalesce(:employeeId, ''), vehicleIsDOTFlag=:vehicleIsDOTFlag, trailerID=coalesce(:trailerID, ''),serviceID=coalesce(:serviceID, ''), "+
            "location=coalesce(:location, ''), vehicleLicense = coalesce(:vehicleLicense, ''), editedFlag=:editedFlag, editCount=:editCount,editUserID=:editUserID, " +
            "truckGallons=:truckGallons, trailerGallons=:trailerGallons, editUserName=:editUserName, timeLastUpdated=UTC_TIMESTAMP(), mobileUnitId=:mobileUnitId, "+
            "reason=:reason, approvedBy=:approvedBy, timeStamp=:timeStamp, editor=:editor "+
            "WHERE hosLogID = :id";

    @Override
    public Integer update(HOSRecord hosRecord) {

        populateVehicle(hosRecord);
        populateEmployeeIDTimezone(hosRecord);
        if (hosRecord.getEditUserID() != null && hosRecord.getEditUserID() > 0) {
            populateEditUser(hosRecord);
        }

        if (hosLogExistsForDriver(hosRecord.getHosLogID(), hosRecord.getDriverID())) {
            if (!changeLogExists(hosRecord.getHosLogID())) {
                createChangeLog(hosRecord.getHosLogID());
            }
            hosRecord.setChangedCnt(hosRecord.getChangedCnt() + 1);
            hosRecord.setEdited(true);
            Map<String, Object> params = paramMapFromHOSRecord(hosRecord);
            params.put("id", hosRecord.getHosLogID());
            return getNamedParameterJdbcTemplate().update(UPDATE_SQL, params);

        } else {

            deleteByID(hosRecord.getHosLogID());
            hosRecord.setChangedCnt(1);
            hosRecord.setOrigin(HOSOrigin.PORTAL);
            create(0l, hosRecord);
        }

        return 1;
    }

    private final static String DELETE_BY_ID_SQL = "UPDATE hoslog set deletedFlag = 1, editedFlag=1, editCount=editCount+1, timeLastUpdated=UTC_TIMESTAMP() WHERE hosLogID = :id"; 

    @Override
    public Integer deleteByID(Long id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        return getNamedParameterJdbcTemplate().update(DELETE_BY_ID_SQL, params);
    }

    private final static String DATE_DRIVER_FILTERED_SQL = SELECT_SQL + 
            "AND h.deletedFlag = 0 AND h.driverID = :driverID " +
            "AND h.logTime BETWEEN :startTime AND :endTime " + 
            "AND h.status NOT IN (31,39,47,48) ";
    private final static String DRIVER_STATUS_ONLY_FILTERED_SQL = DATE_DRIVER_FILTERED_SQL + 
            "AND h.status IN (0,1,2,3,4,7,8,24,29,30,32) ";
    @Override
    public List<HOSRecord> getHOSRecords(Integer driverID, Interval interval, Boolean driverStatusOnly) {
        interval = getAdjustedInterval(driverID, interval);

        String sql = driverStatusOnly ? DRIVER_STATUS_ONLY_FILTERED_SQL : DATE_DRIVER_FILTERED_SQL;
        sql += "ORDER BY h.logTime DESC";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("driverID", driverID);
        params.put("startTime", getDateString(interval.getStart().toDate()));
        params.put("endTime", getDateString(interval.getEnd().toDate()));

        return getNamedParameterJdbcTemplate().query(sql, params, new HOSRecordRowMapper());
    }

    @Override
    public List<HOSRecord> getHOSRecordsFilteredByInterval(Integer driverID, Interval interval, Boolean driverStatusOnly) {
        List<HOSRecord> hosRecordList = getHOSRecords(driverID, interval, driverStatusOnly);
        if (hosRecordList == null)
            return null;
        if (logger.isDebugEnabled())
            logger.debug("original list size: " + hosRecordList.size());

        List<HOSRecord> hosFilteredRecordList = new ArrayList<HOSRecord>();
        for (HOSRecord rec : hosRecordList)
            if (interval.contains(rec.getLogTime().getTime()))
                hosFilteredRecordList.add(rec);

        return hosFilteredRecordList;

    }
    
    private final static String SELECT_OCCUPANT_LOGS = "SELECT  ol.driverID, CONCAT(p.first, ' ', p.last) AS driverName, ol.vehicleID, " +
            "ol.loginTime, COALESCE(ol.logoutTime, UTC_TIMESTAMP()) AS logoutTime," +
            "ol.serviceId, ol.trailerId " +
            "FROM hosvehiclelogin ol, person p, driver d, " +
            /*Grab vehicle, interval records for driver*/
            "(SELECT hvl.vehicleID, loginTime, COALESCE(logoutTime, UTC_TIMESTAMP()) AS logoutTime FROM hosvehiclelogin hvl, vehicle v WHERE driverId = :driverID AND v.vehicleID=hvl.vehicleID AND ((loginTime BETWEEN :startTime AND :endTime) OR (COALESCE(logoutTime, UTC_TIMESTAMP())  BETWEEN :startTime AND :endTime) OR (loginTime <= :startTime AND COALESCE(logoutTime, UTC_TIMESTAMP()) >= :endTime))) dl " + 
            "WHERE ol.driverId != :driverID " +
            /*Grab records that fall into time interval of driver records*/
            "AND ol.vehicleId = dl.vehicleID " +  
            "AND ((ol.loginTime > dl.loginTime AND ol.loginTime < dl.logoutTime) OR (COALESCE(ol.logoutTime, UTC_TIMESTAMP()) > dl.loginTime AND COALESCE(ol.logoutTime, UTC_TIMESTAMP()) < dl.logoutTime) OR (ol.loginTime < dl.loginTime AND COALESCE(ol.logoutTime, UTC_TIMESTAMP()) > dl.logoutTime)) " +
            "AND d.personID = p.personID AND d.driverId = ol.driverId and ol.occupantFlag > 0 " +
            "ORDER BY ol.loginTime";


    @Override
    public List<HOSOccupantLog> getHOSOccupantLogs(Integer driverID, Interval interval) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("driverID", driverID);
        params.put("startTime", getDateString(interval.getStart().toDate()));
        params.put("endTime", getDateString(interval.getEnd().toDate()));
        return getNamedParameterJdbcTemplate().query(SELECT_OCCUPANT_LOGS, params, new ParameterizedRowMapper<HOSOccupantLog>() {

            @Override
            public HOSOccupantLog mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                HOSOccupantLog hosOccupantLog = new HOSOccupantLog();
                hosOccupantLog.setDriverID(resultSet.getInt("driverID"));
                hosOccupantLog.setDriverName(resultSet.getString("driverName"));
                hosOccupantLog.setVehicleID(resultSet.getInt("vehicleID"));
                hosOccupantLog.setLogTime(getDateFromString(resultSet.getString("loginTime")));
                hosOccupantLog.setEndTime(getDateFromString(resultSet.getString("logoutTime")));
                hosOccupantLog.setServiceID(resultSet.getString("serviceId"));
                hosOccupantLog.setTrailerID(resultSet.getString("trailerId"));
                return hosOccupantLog;
            }

        });
    }
    
    private final static String GROUP_MILEAGE_SQL = "SELECT v.groupID, sum(odometer6) AS distance FROM agg a, vehicle v, groupVehicleFlat g " +
            "WHERE a.vehicleID = v.vehicleID  AND g.vehicleID = v.vehicleID AND g.groupID = :groupID AND a.aggDate between :startTime and :endTime "+
            "AND ((not :noDriver AND a.driverID != :unknownDriverID) OR (:noDriver AND a.driverID = :unknownDriverID)) " +
            "GROUP BY groupID";

    @Override
    public List<HOSGroupMileage> getHOSMileage(Integer groupID, Interval interval, Boolean noDriver) {
        Integer unknownDriverID = fetchUnknownDriverID(groupID);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID", groupID);
        params.put("startTime", getDateString(interval.getStart().toDate()));
        params.put("endTime", getDateString(interval.getEnd().toDate()));
        params.put("unknownDriverID", unknownDriverID);
        params.put("noDriver", noDriver == null ? false : noDriver);

        return getNamedParameterJdbcTemplate().query(GROUP_MILEAGE_SQL, params, new ParameterizedRowMapper<HOSGroupMileage>() {

            @Override
            public HOSGroupMileage mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                HOSGroupMileage hosGroupMileage = new HOSGroupMileage();
                hosGroupMileage.setGroupID(resultSet.getInt("groupID"));
                hosGroupMileage.setDistance(resultSet.getLong("distance"));
                return hosGroupMileage;
            }

        });
    }

    private final static String GROUP_VEHICLE_MILEAGE_SQL = "SELECT v.groupID, v.name AS vehicleName, sum(odometer6) AS distance FROM agg a, vehicle v " +
            "WHERE a.vehicleID = v.vehicleID AND v.groupID = :groupID AND a.aggDate between :startTime and :endTime " +
            "AND ((not :noDriver AND a.driverID != :unknownDriverID) OR (:noDriver AND a.driverID = :unknownDriverID)) " + 
            "GROUP BY v.groupID, v.name";


    @Override
    public List<HOSVehicleMileage> getHOSVehicleMileage(Integer groupID, Interval interval, Boolean noDriver) {
        Integer unknownDriverID = fetchUnknownDriverID(groupID);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID", groupID);
        params.put("startTime", getDateString(interval.getStart().toDate()));
        params.put("endTime", getDateString(interval.getEnd().toDate()));
        params.put("unknownDriverID", unknownDriverID);
        params.put("noDriver", noDriver == null ? false : noDriver);

        return getNamedParameterJdbcTemplate().query(GROUP_VEHICLE_MILEAGE_SQL, params, new ParameterizedRowMapper<HOSVehicleMileage>() {

            @Override
            public HOSVehicleMileage mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                HOSVehicleMileage hosVehicleMileage = new HOSVehicleMileage();
                hosVehicleMileage.setGroupID(resultSet.getInt("groupID"));
                hosVehicleMileage.setVehicleName(resultSet.getString("vehicleName"));
                hosVehicleMileage.setDistance(resultSet.getLong("distance"));
                return hosVehicleMileage;
            }

        });
    }

    private final static String DRIVER_FOR_EMPID_LASTNAME_SQL = "SELECT d.driverID, coalesce(d.dot, 0) AS dot, tz.tzName, coalesce(d.fobID, '') AS fobId " +
            "FROM driver d left join person p on p.personID = d.personID left join timezone tz on p.tzID = tz.tzID " +
            "where p.empid=:employeeId and p.last=:last";

    @Override
    public HOSDriverLogin getDriverForEmpidLastName(String employeeId, String lastName) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("employeeId", employeeId);
        params.put("last", lastName);

        return (HOSDriverLogin) getNamedParameterJdbcTemplate().queryForObject(DRIVER_FOR_EMPID_LASTNAME_SQL, params, new ParameterizedRowMapper<HOSDriverLogin>() {

            @Override
            public HOSDriverLogin mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                HOSDriverLogin hosDriverLogin = new HOSDriverLogin();
                hosDriverLogin.setDriverID(resultSet.getInt("driverID"));
                hosDriverLogin.setDriverDotType(RuleSetType.valueOf(resultSet.getInt("dot")));
                hosDriverLogin.setTimezoneID(resultSet.getString("tzName"));
                hosDriverLogin.setFobId(resultSet.getString("fobId"));
                return hosDriverLogin;
            }

        });
    }

    private static final String FETCH_MILEAGE_VEHICLE_DAY = "select driverID, odometer6 from agg where vehicleID = :vehicleID and aggDate = :aggDate";

    @Override
    public Map<Integer, Long> fetchMileageForDayVehicle(DateTime day, Integer vehicleID) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("vehicleID", vehicleID);
        params.put("aggDate", getDayDateString(day.toDate()));

        List<VehicleDriverMileage> vehicleDriverMileageList = getNamedParameterJdbcTemplate().query(FETCH_MILEAGE_VEHICLE_DAY, params, new ParameterizedRowMapper<VehicleDriverMileage>() {

            @Override
            public VehicleDriverMileage mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                VehicleDriverMileage vehicleDriverMileage = new VehicleDriverMileage();
                vehicleDriverMileage.setDriverID(resultSet.getInt("driverID"));
                vehicleDriverMileage.setMileage(resultSet.getLong("odometer6"));
                return vehicleDriverMileage;
            }

        });

        Map<Integer, Long> mileageMap = new HashMap<Integer, Long>();
        for (VehicleDriverMileage vehicleDriverMileage : vehicleDriverMileageList) {
            mileageMap.put(vehicleDriverMileage.getDriverID(), vehicleDriverMileage.getMileage());
        }
        return mileageMap;
    }

    
    private static final String FETCH_OCCUPANT_INFO = "SELECT concat(coalesce(p.first, ''), ' ', coalesce(p.last, '')) AS name, coalesce(p.empid, '') AS empid, coalesce(d.fobID, '') AS fobID FROM driver d, person p WHERE d.personID = p.personID AND d.driverID = :driverID";

    @Override
    public HOSOccupantInfo getOccupantInfo(Integer driverID) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("driverID", driverID);

        try {
            return (HOSOccupantInfo) getNamedParameterJdbcTemplate().queryForObject(FETCH_OCCUPANT_INFO, params, new ParameterizedRowMapper<HOSOccupantInfo>() {
                @Override
                public HOSOccupantInfo mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                    HOSOccupantInfo hosOccupantInfo = new HOSOccupantInfo();
                    hosOccupantInfo.setFullName(resultSet.getString("name"));
                    hosOccupantInfo.setFobId(resultSet.getString("fobID"));
                    hosOccupantInfo.setEmpId(resultSet.getString("empid"));
                    return hosOccupantInfo;
                }
    
            });
        } catch (EmptyResultDataAccessException ex) {
            logger.info("occupant info not found for driverID: " + driverID);
            return null;
        }
    }

    @Override
    public List<HOSRecord> getHOSRecordsForCommAddress(String address, List<HOSRecord> paramList) {

        List<HOSRecord> finalRecordList = new ArrayList<HOSRecord>();

        Integer driverID = getDriverIDForCommAddress(address);
        if (driverID == null) {
            return finalRecordList;
        }

        Interval interval = getMinMaxInterval(paramList);
        List<HOSRecord> recordList = getHOSRecords(driverID, interval, true);
        // filter out the records that were contained in the date interval for the
        // driver but weren't in params list.
        for (HOSRecord record : recordList) {
            for (HOSRecord param : paramList) {
                if (record.getLogTime().equals(param.getLogTime()) && record.getStatus().equals(param.getStatus())) {
                    finalRecordList.add(record);
                }
            }
        }

        return finalRecordList;
    }


    private static final String FETCH_DRIVER_INFO_FOR_ADDRESS_EMPID = "SELECT vdd.acctID, vdd.deviceID, vdd.vehicleID, p.personID, p.tzID, p.measureType, p.fuelEffType, d.driverID, coalesce(d.dot, 0) AS dot, coalesce(d.fobID, '') as fobID, tz.tzName " +
            "FROM vddlog vdd, person p left join driver d on d.personID=p.personID left join timezone tz on p.tzID=tz.tzID " +
            "WHERE p.acctID = vdd.acctID AND p.empid = :employeeID AND p.status = 1 " +
            "and vdd.deviceID in (SELECT deviceID FROM device WHERE (imei = :address OR mcmid = :address) AND status = 1) AND vdd.stop IS NULL order by vdd.start desc limit 1";

    @Override
    public HOSDriverLogin getDriverForEmpid(String commAddress, String employeeId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("employeeID", employeeId);
        params.put("address", commAddress);
        
        try {

            return (HOSDriverLogin) getNamedParameterJdbcTemplate().queryForObject(FETCH_DRIVER_INFO_FOR_ADDRESS_EMPID, params, new ParameterizedRowMapper<HOSDriverLogin>() {
    
                @Override
                public HOSDriverLogin mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                    HOSDriverLogin hosDriverLogin = new HOSDriverLogin();
                    hosDriverLogin.setAcctID(resultSet.getInt("acctID"));
                    hosDriverLogin.setDeviceID(resultSet.getInt("deviceID"));
                    hosDriverLogin.setDriverID(resultSet.getInt("driverID"));
                    hosDriverLogin.setVehicleID(resultSet.getInt("vehicleID"));
                    hosDriverLogin.setDriverDotType(RuleSetType.valueOf(resultSet.getInt("dot")));
                    hosDriverLogin.setTimezoneID(resultSet.getString("tzName"));
                    hosDriverLogin.setMeasurementType(MeasurementType.valueOf(resultSet.getInt("measureType")));
                    hosDriverLogin.setFuelEfficiencyType(FuelEfficiencyType.valueOf(resultSet.getInt("fuelEffType")));
                    hosDriverLogin.setFobId(resultSet.getString("fobID"));
                    return hosDriverLogin;
                }
    
            });
        } catch (EmptyResultDataAccessException ex) {
            logger.info("No driverLogin found for device: " + commAddress + " employeeId: " + employeeId);
            return new HOSDriverLogin(0, 0, 0);
        }
    }

    // NOTE: This one is still using stored proc.
    private static final String IS_VALID_LOGIN_SP = "{call hos_isValidLogin(:address, :employeeID, :loginTime, :occupantFlag, :odometer)}";
    @Override
    public HOSDriverLogin isValidLogin(String commAddress, String employeeId, long loginTime, boolean occupantFlag, int odometer) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("address", commAddress);
        params.put("employeeID", employeeId);
        params.put("loginTime", loginTime);
        params.put("occupantFlag", occupantFlag);
        params.put("odometer", odometer);
        return (HOSDriverLogin)getNamedParameterJdbcTemplate().queryForObject(IS_VALID_LOGIN_SP, params,new ParameterizedRowMapper<HOSDriverLogin>() {

            @Override
            public HOSDriverLogin mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                HOSDriverLogin driverLogin = new HOSDriverLogin();
                driverLogin.setAcctID(resultSet.getInt(1));
                driverLogin.setDeviceID(resultSet.getInt(2));
                driverLogin.setDriverID(resultSet.getInt(3));
                driverLogin.setDriverDotType(RuleSetType.valueOf(resultSet.getInt(4)));
                driverLogin.setTimezoneID(resultSet.getString(5));
                driverLogin.setCurrentVehicleID(resultSet.getInt(6));
                driverLogin.setCurrentOcupantFlag(resultSet.getBoolean(7));
                driverLogin.setCurrentDeviceID(resultSet.getInt(8));
                driverLogin.setCurrentAddress(resultSet.getString(9));
                driverLogin.setVehicleDotType(RuleSetType.valueOf(resultSet.getInt(10)));
                driverLogin.setMeasurementType(MeasurementType.valueOf(resultSet.getInt(11)));
                driverLogin.setFuelEfficiencyType(FuelEfficiencyType.valueOf(resultSet.getInt(12)));
                driverLogin.setFobId(resultSet.getString(13));
                
                return driverLogin;
            }

        });

    }

    private final static String FETCH_IMEI_FOR_OCCUPANT = "SELECT imei FROM hosvehiclelogin l, device d WHERE l.deviceID=d.deviceID AND l.logoutTime IS NULL and l.occupantFlag=1 AND l.driverID = :driverID AND loginTime < :startTime";

    @Override
    public String fetchIMEIForOccupant(Integer driverID, Integer startTime) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("startTime", startTime);
        params.put("driverID", driverID);
        return queryForNullableObject(FETCH_IMEI_FOR_OCCUPANT, String.class, params);
    }

    @Override
    public List<HOSOccupantHistory> getHOSOccupantHistory(HOSDriverLogin driverLogin) {
        final RuleSetType dotType = driverLogin.getDriverDotType();

        final int daysback = (dotType == RuleSetType.CANADA_2007_OIL || dotType == RuleSetType.CANADA_2007_60_DEGREES_OIL) ? -24 : -14;

        TimeZone driverTz = TimeZone.getTimeZone("MST7MDT"); // default
        if (!driverLogin.getTimezoneID().equals(""))
            driverTz = TimeZone.getTimeZone(driverLogin.getTimezoneID());

        GregorianCalendar startDayCalendar = new GregorianCalendar();
        startDayCalendar.setTimeZone(driverTz);
        startDayCalendar.setTime(new Date());
        startDayCalendar.add(Calendar.DATE, daysback);
        int startDayOfYear = startDayCalendar.get(Calendar.DAY_OF_YEAR);

        Interval interval = new Interval(startDayCalendar.getTime().getTime(), new Date().getTime());
        final List<HOSOccupantLog> recordList = getHOSOccupantLogs(driverLogin.getDriverID(), interval);

        startDayCalendar.set(startDayCalendar.get(Calendar.YEAR), startDayCalendar.get(Calendar.MONTH), startDayCalendar.get(Calendar.DATE), 0, 0, 0);
        GregorianCalendar endDayCalendar = new GregorianCalendar();
        endDayCalendar.setTimeZone(driverTz);
        endDayCalendar.setTime(startDayCalendar.getTime());
        endDayCalendar.add(Calendar.DATE, 1);
        GregorianCalendar startLogTimeCalendar = new GregorianCalendar();
        startLogTimeCalendar.setTimeZone(driverTz);
        GregorianCalendar endLogTimeCalendar = new GregorianCalendar();
        endLogTimeCalendar.setTimeZone(driverTz);
        List<Integer> occupantDuplicateCheckList = new ArrayList<Integer>();
        List<HOSOccupantHistory> occupantHistoryList = new ArrayList<HOSOccupantHistory>();

        for (int i = 0; i >= daysback; i--) {
            for (HOSOccupantLog occupantLog : recordList) {
                startLogTimeCalendar.setTime(occupantLog.getLogTime());
                endLogTimeCalendar.setTime(occupantLog.getEndTime());

                // if the dates for the log fit in the given day.
                if ((startLogTimeCalendar.after(startDayCalendar) && startLogTimeCalendar.before(endDayCalendar))
                        || (endLogTimeCalendar.after(startDayCalendar) && endLogTimeCalendar.before(endDayCalendar))
                        || (startLogTimeCalendar.before(startDayCalendar) && endLogTimeCalendar.after(endDayCalendar))) {
                    Integer driverID = (Integer) (occupantLog.getDriverID());
                    if (occupantDuplicateCheckList.indexOf(driverID) == -1) {
                        occupantDuplicateCheckList.add(driverID);
                        occupantHistoryList.add(new HOSOccupantHistory((i * -1) + 1, driverID, startDayOfYear));
                    }
                }
            }
            occupantDuplicateCheckList.clear();
            startDayCalendar.add(Calendar.DATE, 1);
            endDayCalendar.add(Calendar.DATE, 1);
        }

        return occupantHistoryList;

    }

    @Override
    public List<HOSOccupantHistory> getHOSOccupantHistory(String commAddress, String employeeId) {
        HOSDriverLogin driverLogin = getDriverForEmpid(commAddress, employeeId);
        return getHOSOccupantHistory(driverLogin);
    }

    // NOTE: Leaving stored proc call for this one.
    
    private static final String LOGOUT_DRIVER_SP = "{call hos_logoutDriverFromDevice(:address, :employeeID, :logoutTime, :odometer)}";
    @Override
    public void logoutDriverFromDevice(String commAddress, String employeeId, long logoutTime, int odometer) {
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("address", commAddress);
        params.put("employeeID", employeeId);
        params.put("logoutTime", logoutTime);
        params.put("odometer", odometer);
        getNamedParameterJdbcTemplate().update(LOGOUT_DRIVER_SP, params);
    }
    
    private final static String DATE_VEHICLE_FILTERED_SQL = SELECT_SIMPLE_SQL + 
            "AND h.deletedFlag = 0 AND h.vehicleID = :vehicleID " +
            "AND h.logTime BETWEEN :startTime AND :endTime " + 
            "AND h.status NOT IN (31,39,47,48) ";
    
    private final static String VEHICLE_DRIVER_STATUS_ONLY_FILTERED_SQL = DATE_VEHICLE_FILTERED_SQL + 
            "AND h.status IN (0,1,2,3,4,7,8,24,29,30,32) ";

    @Override
    public List<HOSRecord> getRecordsForVehicle(Integer vehicleID, Interval interval, Boolean driverStatusOnly) {
        String sql = driverStatusOnly ? VEHICLE_DRIVER_STATUS_ONLY_FILTERED_SQL : DATE_VEHICLE_FILTERED_SQL;
        sql += "ORDER BY h.logTime DESC";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("vehicleID", vehicleID);
        params.put("startTime", getDateString(interval.getStart().toDate()));
        params.put("endTime", getDateString(interval.getEnd().toDate()));

        return getNamedParameterJdbcTemplate().query(sql, params, new HOSRecordRowMapper());
    }
    
    private final static String VEHICLE_FUEL_STOPS_SQL = SELECT_SIMPLE_SQL + 
            "AND h.deletedFlag = 0 AND h.vehicleID = :vehicleID " +
            "AND h.logTime BETWEEN :startTime AND :endTime " + 
            "AND h.status = "+ HOSStatus.FUEL_STOP.getCode() + " " +
            "ORDER BY h.logTime DESC";


    @Override
    public List<HOSRecord> getFuelStopRecordsForVehicle(Integer vehicleID, Interval interval) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("vehicleID", vehicleID);
        params.put("startTime", getDateString(interval.getStart().toDate()));
        params.put("endTime", getDateString(interval.getEnd().toDate()));

        return getNamedParameterJdbcTemplate().query(VEHICLE_FUEL_STOPS_SQL, params, new HOSRecordRowMapper());
    }

    @Override
    public Long create(Long id, HOSRecord hosRecord) {

        // hos_createFromAdminPortal
        hosRecord.setVehicleID(hosRecord.getVehicleID() == null ? 0 : hosRecord.getVehicleID());
        populateEmployeeIDTimezone(hosRecord);

        if (hosRecord.getEditUserID() != null && hosRecord.getEditUserID() > 0) {
            /* We have have a valid user ID, so admin adding records in portal */
            populateEditUser(hosRecord);
            hosRecord.setOrigin(HOSOrigin.PORTAL);
            hosRecord.setEdited(true);
            hosRecord.setChangedCnt(1);
            populateVehicle(hosRecord);
        } else {
            hosRecord.setOrigin(HOSOrigin.KIOSK);
        }

        return create(hosRecord);
    }

    @Override
    public Long createFromNote(HOSRecord hosRecord) {

        if (hosRecord.getOrigin() == null) {
            hosRecord.setOrigin(HOSOrigin.DEVICE);
        }

        hosRecord.setNoteID(0l);
        populateTrailerService(hosRecord);
        populateDriver(hosRecord);
        populateVehicle(hosRecord);

        return create(hosRecord);
    }


    // home office location for vehicle is in the actual settings 
    private final static String FETCH_VEHICLE_HOME_LOCATION = "select settingID, value from actualVSet where settingID in (1048, 1049) and vehicleID=:vehicleID"; 

    @Override
    public LatLng getVehicleHomeOfficeLocation(Integer vehicleID) {
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("vehicleID", vehicleID);
        List<FloatSetting> settings = null;
        try {
            settings = getNamedParameterJdbcTemplate().query(FETCH_VEHICLE_HOME_LOCATION, params, new ParameterizedRowMapper<FloatSetting>() {
                @Override
                public FloatSetting mapRow(ResultSet rs, int rowNum) throws SQLException {
                    FloatSetting floatSetting = new FloatSetting();
                    floatSetting.setId(rs.getInt("settingID"));
                    floatSetting.setValue(rs.getFloat("value"));
                    return floatSetting;
                }
            });
        }
        catch (EmptyResultDataAccessException ex) {
        }
        if (settings == null || settings.isEmpty()) {
            return null;
        }

        LatLng homeLocation = new LatLng();
        for (FloatSetting setting : settings) {
            if (setting.getId() == 1048) {
                homeLocation.setLat(setting.getValue());
            } else {
                homeLocation.setLng(setting.getValue());
            }
        }
        return homeLocation;
        
    }
    class FloatSetting {
        Integer id;
        Float value;
        public FloatSetting(){}
        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }
        public Float getValue() {
            return value;
        }
        public void setValue(Float value) {
            this.value = value;
        }
    }

    private final static String FETCH_HOS_DELTA_RECORDS = "SELECT h.hosLogID, h.logTime, cl.logTime as originalLogTime, "
            + "h.status, cl.status as originalStatus, h.driverDOTType, h.origin, h.deletedFlag, h.editedFlag, h.vehicleID, coalesce(v.deviceID, 0) as deviceID "
            + "FROM hoslog h "
            + "left JOIN vddlog v on (h.vehicleID != 0 and h.vehicleID = v.vehicleID and h.logTime BETWEEN v.start AND coalesce(v.stop, UTC_TIMESTAMP())) "
            + "LEFT JOIN hoslog_changelog cl ON  (h.hosLogID = cl.hosLogID) "
            + "WHERE h.driverID = :driverID AND (h.origin IN (2,3) OR h.editedFlag = true OR deviceID != :deviceID) AND (h.timeLastUpdated > :deltaTime) AND h.status NOT IN (31,39,47,48) ORDER BY h.logTime DESC";

    @Override
    public List<HOSRecord> getHOSDeltaRecords(Integer driverID, Integer deviceID, Date deltaTime) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("driverID", driverID);
        params.put("deviceID", deviceID);
        params.put("deltaTime", getDateString(deltaTime));

        return getNamedParameterJdbcTemplate().query(FETCH_HOS_DELTA_RECORDS, params, new ParameterizedRowMapper<HOSRecord>() {
            @Override
            public HOSRecord mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                HOSRecord hosRecord = new HOSRecord();
                hosRecord.setHosLogID(resultSet.getLong("hosLogID"));
                hosRecord.setLogTime(getDateFromString(resultSet.getString("logTime")));
                hosRecord.setOriginalLogTime(getDateFromString(resultSet.getString("originalLogTime")));
                hosRecord.setStatus(HOSStatus.valueOf(resultSet.getInt("status")));
                hosRecord.setOriginalStatus(resultSet.getObject("originalStatus") == null ? null : HOSStatus.valueOf(resultSet.getInt(5)));
                hosRecord.setDriverDotType(RuleSetType.valueOf(resultSet.getInt("driverDOTType")));
                hosRecord.setOrigin(HOSOrigin.valueOf(resultSet.getInt("origin")));
                hosRecord.setDeleted(resultSet.getBoolean("deletedFlag"));
                hosRecord.setEdited(resultSet.getBoolean("editedFlag"));
                hosRecord.setVehicleID(resultSet.getInt("vehicleID"));
                hosRecord.setDeviceID(resultSet.getInt("deviceID"));
                return hosRecord;
            }
        });
    }

    private static final String COUNT_HOS_RECORDS_AT_TIMESTAMP = "select count(*) from hoslog where deletedFlag = false and driverID = :driverID and logTime = :logTime and hosLogID != :id";

    @Override
    public boolean otherHosRecordExistsForDriverTimestamp(Integer driverID, Date dateTime, Long hosLogID) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("driverID", driverID);
        params.put("logTime", getDateString(dateTime));
        params.put("id", hosLogID);
        return getNamedParameterJdbcTemplate().queryForInt(COUNT_HOS_RECORDS_AT_TIMESTAMP, params) > 0;
    }

    private final static String FETCH_HOS_RECORDS_AT_SUMMARY_TIME = "SELECT h.hosLogID, h.timeLastUpdated, h.timeAdded, h.logTime, cl.logTime as originalLogTime, "
            + "h.status, cl.status as originalStatus, h.driverDOTType, coalesce(h.vehicleID,0) AS vehicleID, h.deletedFlag, h.editedFlag " + "FROM hoslog h "
            + "LEFT JOIN hoslog_changelog cl ON  (h.hosLogID = cl.hosLogID) " + "WHERE h.driverID = :driverID AND h.status NOT IN (31,39,47,48) "
            + "AND (h.logTime between :startTime and :endTime or cl.logTime between :startTime and :endTime)";

    @Override
    public List<HOSRecord> getHOSRecordAtSummaryTime(Integer driverID, final Integer vehicleID, final Date summaryTime, Date startTime, Date endTime) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("driverID", driverID);
        params.put("startTime", getDateString(startTime));
        params.put("endTime", getDateString(endTime));

        List<HOSRecord> recordList = getNamedParameterJdbcTemplate().query(FETCH_HOS_RECORDS_AT_SUMMARY_TIME, params, new ParameterizedRowMapper<HOSRecord>() {
            @Override
            public HOSRecord mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                HOSRecord hosRecord = new HOSRecord();
                hosRecord.setHosLogID(resultSet.getLong("hosLogID"));
                Date lastUpdateTime = getDateFromString(resultSet.getString("timeLastUpdated"));
                Date addedTime = getDateFromString(resultSet.getString("timeAdded"));
                Date logTime = getDateFromString(resultSet.getString("logTime"));
                Date originalLogTime = getDateFromString(resultSet.getString("originalLogTime"));
                HOSStatus status = HOSStatus.valueOf(resultSet.getInt("status"));
                HOSStatus originalStatus = resultSet.getObject("originalStatus") == null ? null : HOSStatus.valueOf(resultSet.getInt(7));
                hosRecord.setDriverDotType(RuleSetType.valueOf(resultSet.getInt("driverDOTType")));
                hosRecord.setVehicleID(resultSet.getInt("vehicleID"));
                hosRecord.setDeleted(resultSet.getBoolean("deletedFlag"));
                hosRecord.setEdited(resultSet.getBoolean("editedFlag"));
                if (addedTime.after(summaryTime) && (!hosRecord.getVehicleID().equals(vehicleID) || hosRecord.getDeleted() || hosRecord.getEdited())) {
                    return null;
                }

                if (lastUpdateTime.after(summaryTime)) {
                    hosRecord.setLogTime(originalLogTime == null ? logTime : originalLogTime);
                    hosRecord.setStatus(originalStatus == null ? status : originalStatus);
                } else {
                    hosRecord.setLogTime(logTime);
                    hosRecord.setStatus(status);
                }

                return hosRecord;
            }
        });

        List<HOSRecord> returnList = new ArrayList<HOSRecord>();
        for (HOSRecord rec : recordList) {
            if (rec != null) {
                returnList.add(rec);
            }
        }

        Collections.sort(returnList);
        return returnList;
    }

    class VehicleDriverMileage {
        private Integer driverID;
        private Long mileage;

        public VehicleDriverMileage() {}

        public Integer getDriverID() {
            return driverID;
        }

        public void setDriverID(Integer driverID) {
            this.driverID = driverID;
        }

        public Long getMileage() {
            return mileage;
        }

        public void setMileage(Long mileage) {
            this.mileage = mileage;
        }

    }

    private Map<String, Object> paramMapFromHOSRecord(HOSRecord hosRecord) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("driverID", hosRecord.getDriverID());
        params.put("vehicleID", hosRecord.getVehicleID());
        params.put("noteID", hosRecord.getNoteID());
        params.put("logTime", getDateString(hosRecord.getLogTime()));
        params.put("tzID", hosRecord.getTzID());
        params.put("status", hosRecord.getStatus().getCode());
        params.put("driverDOTType", hosRecord.getDriverDotType().getCode());
        params.put("employeeId", hosRecord.getEmployeeID());
        params.put("editUserID", hosRecord.getEditUserID());
        params.put("editUserName", hosRecord.getEditUserName());
        params.put("editCount", hosRecord.getChangedCnt());
        params.put("editedFlag", nonNullValue(hosRecord.getEdited()));
        params.put("vehicleIsDOTFlag", nonNullValue(hosRecord.getVehicleIsDOT()));
        params.put("vehicleLicense", hosRecord.getVehicleLicense() == null ? "" : hosRecord.getVehicleLicense());
        params.put("vehicleOdometer", hosRecord.getVehicleOdometer());
        params.put("origin", hosRecord.getOrigin().getCode());
        params.put("latitude", hosRecord.getLat());
        params.put("longitude", hosRecord.getLng());
        params.put("location", hosRecord.getLocation());
        params.put("userEnteredLocationFlag", nonNullValue(hosRecord.getUserEnteredLocationFlag()));
        params.put("state", hosRecord.getStateID() == null ? 0 : hosRecord.getStateID());
        params.put("truckGallons", nonNullValue(hosRecord.getTruckGallons()));
        params.put("trailerGallons", nonNullValue(hosRecord.getTrailerGallons()));
        params.put("serviceID", hosRecord.getServiceID());
        params.put("trailerID", hosRecord.getTrailerID());
        params.put("tripReportFlag", hosRecord.getTripReportFlag());
        params.put("tripInspectionFlag", nonNullValue(hosRecord.getTripInspectionFlag()));
        Date currentTime = new Date();
        params.put("timeAdded", getDateString(currentTime));
        params.put("timeLastUpdated", getDateString(currentTime));
        params.put("mobileUnitId", hosRecord.getMobileUnitID());
        params.put("inspectionType", hosRecord.getInspectionType() == null ? InspectionType.NONE.getCode() : hosRecord.getInspectionType().getCode());
        params.put("reason",hosRecord.getReason());
        params.put("approvedBy", hosRecord.getApprovedBy());
        params.put("editor", hosRecord.getEditor());
        params.put("timeStamp", hosRecord.getTimeStamp());
        return params;
    }

    class HOSRecordRowMapper implements ParameterizedRowMapper<HOSRecord> {

        @Override
        public HOSRecord mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            HOSRecord hosRecord = new HOSRecord();
            hosRecord.setHosLogID(resultSet.getLong("hosLogID"));
            hosRecord.setDriverID(resultSet.getInt("driverID"));
            hosRecord.setVehicleID(resultSet.getInt("vehicleID"));
            hosRecord.setLogTime(getDateFromString(resultSet.getString("logTime")));
            hosRecord.setTimeZone(TimeZone.getTimeZone(resultSet.getString("tzName")));
            hosRecord.setStatus(HOSStatus.valueOf(resultSet.getInt("status")));
            hosRecord.setDriverDotType(RuleSetType.valueOf(resultSet.getInt("driverDOTType")));
            hosRecord.setVehicleIsDOT(resultSet.getBoolean("vehicleIsDOTFlag"));
            hosRecord.setVehicleOdometer(Long.valueOf(resultSet.getInt("vehicleOdometer")));
            hosRecord.setOrigin(HOSOrigin.valueOf(resultSet.getInt("origin")));
            hosRecord.setTrailerID(resultSet.getString("trailerID"));
            hosRecord.setServiceID(resultSet.getString("serviceID"));
            hosRecord.setLat(resultSet.getFloat("latitude"));
            hosRecord.setLng(resultSet.getFloat("longitude"));
            hosRecord.setLocation(resultSet.getString("location"));
            hosRecord.setOriginalLocation(resultSet.getString("originalLocation"));
            hosRecord.setDeleted(resultSet.getBoolean("deletedFlag"));
            hosRecord.setEdited(resultSet.getBoolean("editedFlag"));
            hosRecord.setChangedCnt(resultSet.getInt("editCount"));
            hosRecord.setEditUserName(resultSet.getString("editUserName"));
            hosRecord.setTruckGallons(resultSet.getFloat("truckGallons"));
            hosRecord.setTrailerGallons(resultSet.getFloat("trailerGallons"));
            hosRecord.setTripReportFlag(resultSet.getBoolean("tripReportFlag"));
            hosRecord.setTripInspectionFlag(resultSet.getBoolean("tripInspectionFlag"));
            hosRecord.setVehicleName(resultSet.getString("vehicleName"));
            hosRecord.setOriginalLogTime(getDateFromString(resultSet.getString("originalLogTime")));
            hosRecord.setVehicleLicense(resultSet.getString("vehicleLicense"));
            hosRecord.setEmployeeID(resultSet.getString("empid"));
            hosRecord.setEditUserID(resultSet.getInt("editUserID"));
            hosRecord.setSingleDriver(resultSet.getBoolean("singleDriver"));
            hosRecord.setOriginalStatus(resultSet.getObject("originalStatus") == null ? null : HOSStatus.valueOf(resultSet.getInt("originalStatus")));
            hosRecord.setMobileUnitID(resultSet.getString("mobileUnitID"));
            hosRecord.setInspectionType(InspectionType.valueOf(resultSet.getInt("inspectionType")));
            hosRecord.setReason(resultSet.getString("reason"));
            hosRecord.setApprovedBy(resultSet.getString("approvedBy"));
            hosRecord.setEditor(resultSet.getObject("editor") == null ? null : resultSet.getInt("editor"));
            hosRecord.setTimeStamp(resultSet.getTimestamp("timeStamp"));
            return hosRecord;
        }

    }

    // --- DB Helper Methods ==
    private <T> T queryForNullableObject(String sql, Class<T> requiredType, Map<String, Object> params) {

        try {
            return (T) getNamedParameterJdbcTemplate().queryForObject(sql, params, requiredType);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    private final static String CHANGE_LOG_EXISTS_SQL = "SELECT count(hosLogID) FROM hoslog_changelog WHERE hoslogID = :id";

    private boolean changeLogExists(Long hosLogID) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", hosLogID);
        return getNamedParameterJdbcTemplate().queryForInt(CHANGE_LOG_EXISTS_SQL, params) > 0;
    }

    private final static String LOG_EXISTS_FOR_DRIVER_SQL = "SELECT count(hosLogID) FROM hoslog WHERE hosLogID = :id AND driverID = :driverID";

    private boolean hosLogExistsForDriver(Long hosLogID, Integer driverID) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", hosLogID);
        params.put("driverID", driverID);
        return getNamedParameterJdbcTemplate().queryForInt(LOG_EXISTS_FOR_DRIVER_SQL, params) > 0;
    }

    private final static String TRAILER_SERVICE_SELECT = "SELECT trailerID, serviceID FROM hoslog WHERE vehicleID = :vehicleID ORDER BY logTime DESC LIMIT 1";

    private void populateTrailerService(HOSRecord hosRecord) {
        if (hosRecord.getStatus() == HOSStatus.INTERNAL_TIMESTAMP || hosRecord.getVehicleID() == null) {
            return;
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("vehicleID", hosRecord.getVehicleID());
        HOSRecord trailerService = null;

        try {
            trailerService = (HOSRecord) getNamedParameterJdbcTemplate().queryForObject(TRAILER_SERVICE_SELECT, params, new ParameterizedRowMapper<HOSRecord>() {
                @Override
                public HOSRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
                    HOSRecord hosRecord = new HOSRecord();
                    hosRecord.setTrailerID(rs.getString("trailerID"));
                    hosRecord.setServiceID(rs.getString("serviceID"));
                    return hosRecord;
                }
            });
        } catch (EmptyResultDataAccessException ex) {
            // no result (this is OK)
        }

        if (trailerService != null) {
            hosRecord.setTrailerID(trailerService.getTrailerID() == null ? "" : trailerService.getTrailerID());
            hosRecord.setServiceID(trailerService.getServiceID() == null ? "" : trailerService.getServiceID());
        }
    }

    private final static String UNKNOWN_DRIVERID_SELECT = "SELECT unkDriverID FROM account, groups where account.acctID=groups.acctID and groups.groupID=:groupID";

    private Integer fetchUnknownDriverID(Integer groupID) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID", groupID);
        return (Integer) getNamedParameterJdbcTemplate().queryForObject(UNKNOWN_DRIVERID_SELECT, params, Integer.class);
    }

    private final static String DRIVER_INFO_FROM_DRIVER_ID_SELECT = "SELECT p.empid, p.tzID, d.driverID, coalesce(d.dot, 0) AS dot FROM driver d, person p WHERE d.personID = p.personID AND d.driverID = :driverID";

    private void populateDriver(HOSRecord hosRecord) {
        HOSRecord driverInfo = null;
        Map<String, Object> params = new HashMap<String, Object>();
        
        params.put("driverID", hosRecord.getDriverID());
        driverInfo = (HOSRecord) getNamedParameterJdbcTemplate().queryForObject(DRIVER_INFO_FROM_DRIVER_ID_SELECT, params, new ParameterizedRowMapper<HOSRecord>() {
            @Override
            public HOSRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
                HOSRecord hosRecord = new HOSRecord();
                hosRecord.setDriverID(rs.getInt("driverID"));
                hosRecord.setEmployeeID(rs.getString("empid"));
                hosRecord.setDriverDotType(RuleSetType.valueOf(rs.getInt("dot")));
                hosRecord.setTzID(rs.getInt("tzID"));
                return hosRecord;
            }
        });

        if (driverInfo != null) {
            hosRecord.setDriverDotType(driverInfo.getDriverDotType());
            hosRecord.setTzID(driverInfo.getTzID());
            hosRecord.setDriverID(driverInfo.getDriverID());
            hosRecord.setEmployeeID(driverInfo.getEmployeeID());
        }
    }


    private final static String EMPID_INFO_FROM_DRIVER_ID_SELECT = "SELECT p.empid, p.tzID tzID FROM person p inner join driver d on d.personID = p.personID WHERE d.driverID = :driverID";

    private void populateEmployeeIDTimezone(HOSRecord hosRecord) {
        HOSRecord driverInfo = null;
        if (hosRecord.getDriverID() != null) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("driverID", hosRecord.getDriverID());
            driverInfo = (HOSRecord) getNamedParameterJdbcTemplate().queryForObject(EMPID_INFO_FROM_DRIVER_ID_SELECT, params, new ParameterizedRowMapper<HOSRecord>() {
                @Override
                public HOSRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
                    HOSRecord hosRecord = new HOSRecord();
                    hosRecord.setEmployeeID(rs.getString("empid"));
                    hosRecord.setTzID(rs.getInt("tzID"));
                    return hosRecord;
                }
            });
        }
        if (driverInfo != null) {
            hosRecord.setEmployeeID(driverInfo.getEmployeeID());
            hosRecord.setTzID(driverInfo.getTzID());
        }
    }

    private final static String EDIT_USER_SELECT = "SELECT username FROM user WHERE userID = :editUserID";

    private void populateEditUser(HOSRecord hosRecord) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("editUserID", hosRecord.getEditUserID());
        String editUserName = (String) getNamedParameterJdbcTemplate().queryForObject(EDIT_USER_SELECT, params, String.class);
        hosRecord.setEditUserName(editUserName);
    }

    private final static String INSERT_SQL = "INSERT INTO hoslog ("+
            "driverID,vehicleID,noteID,logTime,tzID,status,driverDOTType,employeeId,editUserID,editUserName,editCount,editedFlag,"+
            "vehicleIsDOTFlag,vehicleLicense,vehicleOdometer,origin,latitude,longitude,location,userEnteredLocationFlag,state,truckGallons,trailerGallons,serviceID,trailerID,tripReportFlag,tripInspectionFlag,timeAdded,timeLastUpdated,mobileUnitId,inspectionType,reason,approvedBy,editor,timeStamp"+
            ") VALUES ("+
            ":driverID,:vehicleID,:noteID,:logTime,:tzID,:status,:driverDOTType,:employeeId," +
            ":editUserID,:editUserName,:editCount,:editedFlag," +
            ":vehicleIsDOTFlag,:vehicleLicense,:vehicleOdometer,:origin,:latitude,:longitude,:location,:userEnteredLocationFlag,:state,:truckGallons,:trailerGallons,:serviceID,:trailerID,:tripReportFlag,:tripInspectionFlag," +
            ":timeAdded,:timeLastUpdated,:mobileUnitId,:inspectionType,:reason,:approvedBy,:editor,:timeStamp"+
            ")";

    private Long create(HOSRecord hosRecord) {
        Map<String, Object> params = paramMapFromHOSRecord(hosRecord);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        getNamedParameterJdbcTemplate().update(INSERT_SQL, new MapSqlParameterSource(params), keyHolder);
        return keyHolder.getKey().longValue();
    }

    private final static String VEHICLE_LICENSE_SELECT = "SELECT license from vehicle where vehicleID=:vehicleID";
    private final static String VEHICLE_IS_DOT_DESIRED = "SELECT value from desiredVSet where settingID = " + SettingType.DOT_VEHICLE_TYPE.getSettingID() + " AND vehicleID = :vehicleID"; 
    private final static String VEHICLE_IS_DOT_ACTUAL = "SELECT value from actualVSet where settingID = " + SettingType.DOT_VEHICLE_TYPE.getSettingID() + " AND vehicleID = :vehicleID AND deviceID = :deviceID ORDER BY modified DESC LIMIT 1"; 

    private void populateVehicle(HOSRecord hosRecord) {
        String license = null;
        Integer vehicleIsDOTFlag = null;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("vehicleID", hosRecord.getVehicleID());
        params.put("deviceID", hosRecord.getDeviceID());
        if (hosRecord.getVehicleID() != null && hosRecord.getVehicleID() > 0) {
            license = queryForNullableObject(VEHICLE_LICENSE_SELECT, String.class, params);
            vehicleIsDOTFlag = queryForNullableObject(VEHICLE_IS_DOT_DESIRED, Integer.class, params);
            if (vehicleIsDOTFlag == null) {
                vehicleIsDOTFlag = queryForNullableObject(VEHICLE_IS_DOT_ACTUAL, Integer.class, params);
            }
        }
        hosRecord.setVehicleLicense(license);
        
        hosRecord.setNoteFlags(hosRecord.getNoteFlags()!=null?hosRecord.getNoteFlags():0);
        
        if (vehicleIsDOTFlag == null || (hosRecord.getNoteFlags() & vehicleIsDOTFlag) == vehicleIsDOTFlag) {
            vehicleIsDOTFlag = 0;
        }
        hosRecord.setVehicleIsDOT(vehicleIsDOTFlag == 1);
    }
    
    private static final String FETCH_DRIVERID_FOR_ADDRESS = "SELECT driverID FROM vddlog WHERE deviceID in (SELECT deviceID FROM device WHERE (imei = :address OR mcmid = :address) AND status = 1) AND stop IS NULL ORDER BY start DESC LIMIT 1";

    private Integer getDriverIDForCommAddress(String address) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("address", address);
        return queryForNullableObject(FETCH_DRIVERID_FOR_ADDRESS, Integer.class, params);
    }
    
    private final static String INSERT_CHANGE_LOG_SQL = "INSERT INTO hoslog_changelog( " +
            "hoslogID,driverID,vehicleID,status,logTime,location,driverDOTType,trailerID,serviceID,editUserID,truckGallons,trailerGallons,deletedFlag,timeLastUpdated,mobileUnitID) "+
            "SELECT hoslogID, driverID, vehicleID, status, logTime, location, driverDOTType, trailerID, serviceID, editUserID, truckGallons, trailerGallons, deletedFlag, timeLastUpdated, mobileUnitId " +
            "FROM hoslog WHERE hoslogID = :id";

    private Integer createChangeLog(Long hosLogID) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", hosLogID);
        return getNamedParameterJdbcTemplate().update(INSERT_CHANGE_LOG_SQL, params);
    }

    private final static String PREVIOUS_LOG_STARTTIME_SQL = "SELECT logTime FROM hoslog WHERE driverID = :driverID AND logTime < :startTime AND status IN (0,1,2,3,4,7,8,24,29,30,32) ORDER by logtime desc LIMIT 1";    

    private Interval getAdjustedInterval(Integer driverID, Interval interval) {
        // this is necessary to insure that a record that overlaps interval start is included in the query
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("driverID", driverID);
        params.put("startTime", getDateString(interval.getStart().toDate()));
        String previousLogDate = queryForNullableObject(PREVIOUS_LOG_STARTTIME_SQL, String.class, params);
        if (previousLogDate == null) {
            return interval;
        }
        return new Interval(new DateTime(getDateFromString(previousLogDate), DateTimeZone.UTC), interval.getEnd().withZone(DateTimeZone.UTC));
    }


    // --- DateTime Helper Methods ==
    private static final String DB_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(DB_DATE_FORMAT).withZone(DateTimeZone.UTC);
    private static final DateTimeFormatter dayDateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd").withZone(DateTimeZone.UTC);

    private Date getDateFromString(String strDate) {
        try {
            return (strDate == null || strDate.isEmpty()) ? null : dateTimeFormatter.parseDateTime(strDate.substring(0, 19)).toDate();
        } catch (Exception e) {
            logger.error("getDateFromString Parse Exception:", e);
            return null;
        }
    }

    private String getDateString(Date date) {
        return dateTimeFormatter.print(date.getTime());
    }

    private String getDayDateString(Date day) {
        return dayDateFormatter.print(day.getTime());
    }

    // --- Miscellaneous Helper Methods ==
    private boolean nonNullValue(Boolean value) {
        return value == null ? false : value.booleanValue();
    }

    private float nonNullValue(Float value) {
        return value == null ? 0f : value.floatValue();
    }

    private Interval getMinMaxInterval(List<HOSRecord> paramList) {
        Date startDate = new Date();
        Date endDate = new Date(0L);
        for (HOSRecord record : paramList) {
            if (record.getLogTime().before(startDate))
                startDate = record.getLogTime();

            if (record.getLogTime().after(endDate))
                endDate = record.getLogTime();
        }

        return new Interval(startDate.getTime(), endDate.getTime(), DateTimeZone.UTC);
    }

}
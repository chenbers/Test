package com.inthinc.pro.dao.jdbc;

import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.dao.LocationDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.hessian.DriverHessianDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.*;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.configurator.ProductType;
import com.mysql.jdbc.Statement;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
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

/**
 * JDBC Vehicle DAO.
 */
public class VehicleJDBCDAO extends SimpleJdbcDaoSupport implements VehicleDAO {
    private static final Logger logger = Logger.getLogger(DriverHessianDAO.class);

    private LocationDAO locationDAO;

    public LocationDAO getLocationDAO() {
        return locationDAO;
    }

    public void setLocationDAO(LocationDAO locationDAO) {
        this.locationDAO = locationDAO;
    }

    private static String VEHICLE_COLUMNS_STRING =
            " v.vehicleID, v.groupID, v.status, v.name, v.make, v.model, v.year, v.color, v.vtype, v.vin, v.weight, v.license, v.stateID, v.odometer, v.ifta, v.absOdometer, " +
                    " d.deviceID, d.acctID, d.status, d.name, d.imei, d.sim, d.serialNum, d.phone, d.activated, d.baseID, d.productVer, d.firmVer, d.witnessVer, d.emuMd5, d.mcmid, d.altImei," +
                    " vdd.deviceID, vdd.driverID, concat(p.first, ' ', p.last) driverName, g.name groupName ";

    private static final String VEHICLE_SUFFIX =
            "FROM vehicle v " +
                    "LEFT JOIN groups g USING (groupID) " +
                    "LEFT OUTER JOIN vddlog vdd ON (v.vehicleID = vdd.vehicleID and vdd.stop is null) " +
                    "LEFT OUTER JOIN device d ON (d.deviceID = vdd.deviceID and vdd.stop is null) " +
                    "LEFT OUTER JOIN driver dr ON (dr.driverID = vdd.driverID and vdd.stop is null) " +
                    "LEFT OUTER JOIN person p ON (dr.personID = p.personID) " +
                    "WHERE v.groupID in (:group_list) and v.status != 3";

    private static final String DRIVER_PERSON = "Select t.tzName,p.personID, p.acctID, p.tzID, p.modified, p.status, p.measureType, p.fuelEffType, p.addrID, p.locale, p.reportsTo," +
            "    p.title,p.dept,p.empid, p.first, p.middle, p.last,p.suffix, p.gender, p.height, p.weight, p.dob, p.info, p.warn, p.crit, p.priEmail, p.secEmail, p.priPhone," +
            "    p.secPhone, p.priText, p.secText, d.personID, d.driverid, d.groupid, d.barcode, d.rfid1, d.rfid2, d.fobID, d.license, d.stateid, d.expiration,  d.certs,d.dot," +
            "    d.grouppath, d.class from driver d, person p,timezone t where d.driverID = :driverID and p.personID = d.personID and p.tzID=t.tzID; ";

    private static final String VEHICLE_SELECT =
            "SELECT " + VEHICLE_COLUMNS_STRING + " " + VEHICLE_SUFFIX;

    private static final String MILES_DRIVEN =
            "SELECT MAX(vs.endingOdometer) milesDriven FROM vehicleScoreByDay vs where vs.vehicleID = :vehicleID";

    private static final String FIND_BY = "SELECT * FROM vehicle v " +
            "LEFT JOIN groups g USING (groupID) " +
            "LEFT OUTER JOIN vddlog vdd ON (v.vehicleID = vdd.vehicleID and vdd.stop is null) " +
            "LEFT OUTER JOIN device d ON (d.deviceID = vdd.deviceID and vdd.stop is null) " +
            "LEFT OUTER JOIN driver dr ON (dr.driverID = vdd.driverID and vdd.stop is null) " +
            "LEFT OUTER JOIN person p ON (dr.personID = p.personID) " +
            "LEFT OUTER JOIN driverPerformance dp ON (dr.driverID = dp.driverID)";

    private static final String GET_VEHICLE_NAME="select vehicleID, name from vehicle where groupID = :groupID";

    private static final String FIND_BY_D = "SELECT * FROM vehicle v " +
            "LEFT JOIN groups g USING (groupID) " +
            "LEFT OUTER JOIN vddlog vdd ON (v.vehicleID = vdd.vehicleID) " +
            "LEFT OUTER JOIN device d ON (d.deviceID = vdd.deviceID) " +
            "LEFT OUTER JOIN driver dr ON (dr.driverID = vdd.driverID) " +
            "LEFT OUTER JOIN person p ON (dr.personID = p.personID) " +
            "LEFT OUTER JOIN driverPerformance dp ON (dr.driverID = dp.driverID)";

    private static final String GET_VEHICLE = FIND_BY_D + " where v.groupID= :groupID";

    private static final String FIND_BY_DRIVER_ID = FIND_BY_D + " where dr.driverID = :driverID order by vdd.stop is null limit 1";

    private static final String FIND_BY_DRIVER_IN_GROUP = FIND_BY_D + " where dr.driverID = :driverID and v.groupID = :groupID";
    private static final String FIND_BY_VIN = FIND_BY_D +" where v.vin like :vin";
    private static final String FIND_BY_VEHICLEID ="select * from vehicle where vehicleID=:vehicleID";
    private static final String GROUP_ID_DEEP = FIND_BY +" where v.groupID in (select groupID from groups where groupPath like :groupID) and v.status <> 3";
    private static final String DEL_VEHICLE_BY_ID = "DELETE FROM vehicle WHERE vehicleID = ?";

    private static final String GET_GROUP_PATH = "select g.groupPath from groups g where g.groupID= :groupID";

    private static final String INSERT_VEHICLE = "insert into vehicle (VIN, color, groupID, groupPath, modified, license, make, model, name, stateID, status, weight, year, ifta, odometer, aggDate)" +
                                                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_VEHICLE = "UPDATE vehicle SET VIN= ?, color=?, groupID=?, groupPath=?, modified=?, license=?, make=?, model=?, name=?, stateID=?, status=?, weight=?, year=?, ifta=?, odometer=?, newAggDate=? where vehicleID= ?";

    private static final String VEHICLE_SET_DEVICE = "select count(*) from vddlog where deviceID=:deviceID and stop is null";
    private static final String SET_DRIVER = "select count(*) from vddlog where vehicleID=:vehicleID and stop is null";

    private static final String UPDATE_DEVICE_VEHICLE = "UPDATE vddlog set stop=? where deviceID= ? and stop is null";

    private static final String UPDATE_DEVICE_DRIVER = "UPDATE vddlog set stop=? where vehicleID= ? and stop is null";

    private static final String UPDATE_DEVICE_VEHICLE_NEW = "INSERT into vddlog (start, deviceID, vehicleID, imei, acctID, baseID, emuFeatureMask, vgroupID, vtype, driverID, dgroupID, tzID)" +
                                                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String NEWDEVICE_VEHICLE = "INSERT into vddlog (start, deviceID, vehicleID, imei, acctID, driverID, tzID)" +
                                                            "VALUES (?, ?, ?, ?, ?, ?, ?)";


     //imei, acctID, baseID, emuFeatureMask, vgroupID, vtype, driverID, dgroupID, tzID
    private static final String GET_IMEI = "select imei from vddlog where deviceID=:deviceID and stop is null";
    private static final String GET_ACCTID = "select acctID from vddlog where deviceID=:deviceID and stop is null";
    private static final String GET_BASEID = "select baseID from vddlog where deviceID=:deviceID and stop is null";
    private static final String GET_EMUMASK  = "select emuFeatureMask from vddlog where deviceID=:deviceID and stop is null";
    private static final String GET_VGROUPID = "select vgroupID from vddlog where deviceID=:deviceID and stop is null";
    private static final String GET_VTYPE = "select vtype from vddlog where deviceID=:deviceID and stop is null";
    private static final String GET_DRIVERID = "select driverID from vddlog where deviceID=:deviceID and stop is null";
    private static final String GET_DGROUPID = "select dgroupID from vddlog where deviceID=:deviceID and stop is null";
    private static final String GET_TZID = "select tzID from vddlog where deviceID=:deviceID and stop is null";

    //select device imei and acctID when count is 0
    private static final String GET_MAX_IMEI = "SELECT imei FROM device WHERE deviceID=:deviceID";
    private static final String GET_MAX_ACCTID = "SELECT acctID FROM device WHERE deviceID=:deviceID";
    //select max
    private static final String GET_MAX_TZID = "SELECT tzID FROM vddlog WHERE vddlogid=(SELECT max(vddlogid) FROM vddlog)";
    //select max for vehicle
    private static final String GET_MAX_IMEI_VEHICLE = "SELECT imei FROM vddlog WHERE vddlogid=(SELECT max(vddlogid) FROM vddlog)";
    private static final String GET_MAX_ACCTID_VEHICLE = "SELECT acctID FROM vddlog WHERE vddlogid=(SELECT max(vddlogid) FROM vddlog)";

    //select for driver
    private static final String GET_IMEI_DRIVER = "select imei from vddlog where vehicleID=:vehicleID and stop is null";
    private static final String GET_ACCTID_DRIVER = "select acctID from vddlog where vehicleID=:vehicleID and stop is null";
    private static final String GET_BASEID_DRIVER = "select baseID from vddlog where vehicleID=:vehicleID and stop is null";
    private static final String GET_EMUMASK_DRIVER  = "select emuFeatureMask from vddlog where vehicleID=:vehicleID and stop is null";
    private static final String GET_VGROUPID_DRIVER = "select vgroupID from vddlog where vehicleID=:vehicleID and stop is null";
    private static final String GET_VTYPE_DRIVER = "select vtype from vddlog where vehicleID=:vehicleID and stop is null";
    private static final String GET_DGROUPID_DRIVER= "select dgroupID from vddlog where vehicleID=:vehicleID and stop is null";
    private static final String GET_TZID_DRIVER = "select tzID from vddlog where vehicleID=:vehicleID and stop is null";
    private static final String GET_DEVICE_ID = "select deviceID from vddlog where vehicleID=:vehicleID and stop is null";

    private ParameterizedRowMapper<Vehicle> pagedVehicleRowMapper = new ParameterizedRowMapper<Vehicle>() {
        @Override
        public Vehicle mapRow(ResultSet rs, int rowNum) throws SQLException {
            //Vehicle ID
            Vehicle vehicle = new Vehicle();
            vehicle.setCreated(null);
            vehicle.setVehicleID(rs.getInt("v.vehicleID"));
            vehicle.setDeviceID(rs.getObject("vdd.deviceID") == null ? null : rs.getInt("vdd.deviceID"));
            vehicle.setDriverID(rs.getObject("vdd.driverID") == null ? null : rs.getInt("vdd.driverID"));
            vehicle.setGroupID(rs.getObject("v.groupID") == null ? null : rs.getInt("v.groupID"));
            vehicle.setName(rs.getString("v.name"));
            vehicle.setFullName(vehicle.getFullName());
            vehicle.setMake(rs.getString("v.make"));
            vehicle.setModel(rs.getString("v.model"));
            vehicle.setDriverName(rs.getString("driverName"));
            vehicle.setGroupName(rs.getString("groupName"));
            vehicle.setVIN(rs.getString("v.vin"));
            vehicle.setYear(rs.getObject("v.year") == null ? null : rs.getInt("v.year"));
            vehicle.setState(States.getStateById(rs.getInt("v.stateID")));
            vehicle.setStatus(Status.valueOf(rs.getInt("v.status")));
            vehicle.setVtype(VehicleType.valueOf(rs.getInt("v.vtype")));
            Long absOdometer = rs.getObject("v.absOdometer") == null ? null : (rs.getLong("v.absOdometer"));
            Long odometer = rs.getObject("v.odometer") == null ? null : rs.getLong("v.odometer");
            if (absOdometer != null) {
                vehicle.setOdometer(Long.valueOf(absOdometer/100l).intValue());
            }
            else if (odometer != null) {
                Integer milesDriven = getMilesDriven(vehicle.getVehicleID());
                vehicle.setOdometer(Long.valueOf((odometer + milesDriven)/100).intValue());
            }
            vehicle.setWeight(rs.getObject("v.weight") == null ? null : rs.getInt("v.weight"));

            if (vehicle.getDeviceID() != null) {
                Device device = new Device();
                device.setAccountID(rs.getInt("d.acctID"));
                device.setDeviceID(rs.getInt("d.deviceID"));
                device.setVehicleID(vehicle.getVehicleID());
                device.setStatus(rs.getObject("d.status") == null ? null : DeviceStatus.valueOf(rs.getInt("d.status")));
                device.setName(rs.getString("d.name"));
                device.setImei(rs.getString("d.imei"));
                device.setSim(rs.getString("d.sim"));
                device.setSerialNum(rs.getString("d.serialNum"));
                device.setPhone(rs.getString("d.phone"));
                device.setActivated(rs.getObject("d.activated") == null ? null : rs.getDate("d.activated"));
                device.setBaseID(rs.getObject("d.baseID") == null ? null : rs.getInt("d.baseID"));
                device.setProductVer(rs.getObject("d.productVer") == null ? null : rs.getInt("d.productVer"));
                device.setFirmwareVersion(rs.getObject("d.firmVer") == null ? null : Long.valueOf(rs.getLong("d.firmVer")).intValue());
                device.setWitnessVersion(rs.getObject("d.witnessVer") == null ? null : Long.valueOf(rs.getLong("d.witnessVer")).intValue());
                device.setEmuMd5(rs.getString("d.emuMd5"));
                device.setMcmid(rs.getString("d.mcmid"));
                device.setAltimei(rs.getString("d.altImei"));
                device.setProductVersion(device.getProductVer() == null ? ProductType.UNKNOWN : ProductType.getProductTypeFromVersion(device.getProductVer()));
                vehicle.setDevice(device);

                vehicle.setDevice(device);
            }

            return vehicle;
        }
    };

    public Integer getMilesDriven(Integer vehicleID) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("vehicleID", vehicleID);

        Integer miles = getSimpleJdbcTemplate().queryForInt(MILES_DRIVEN, params);
        return miles == null ? 0 : miles;
    }

    @Override
    public List<Vehicle> getVehiclesInGroupIDList(List<Integer> groupIDList) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("group_list", groupIDList);

        StringBuilder vehicleSelect = new StringBuilder();
        vehicleSelect.append(VEHICLE_SELECT);
        List<Vehicle> vehicleList = getSimpleJdbcTemplate().query(vehicleSelect.toString(), pagedVehicleRowMapper, params);
        return vehicleList;
    }

    @Override
    public List<Vehicle> getVehiclesInGroupHierarchy(Integer groupID) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("groupID", groupID);
            StringBuilder vehiclesIn = new StringBuilder(GROUP_ID_DEEP);

            List<Vehicle> vehicleList = getVehiclesByGroupIDDeep(groupID);

            return vehicleList;

        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Vehicle> getVehiclesInGroup(Integer groupID) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID", groupID);
        StringBuilder vehicleSelectAcct = new StringBuilder(GET_VEHICLE);

        List<Vehicle> vehiclesIn = getSimpleJdbcTemplate().query(vehicleSelectAcct.toString(), pagedVehicleRowMapper, params);
        return vehiclesIn;
    }

    @Override
    public List<VehicleName> getVehicleNames(Integer groupID) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID", groupID);
        StringBuilder vehicleSelectName = new StringBuilder(GET_VEHICLE_NAME);

        List<VehicleName> vehiclesName = getSimpleJdbcTemplate().query(vehicleSelectName.toString(), vehicleNameParameterizedRowMapper, params);
        return vehiclesName;
    }

    @Override
    public void setVehicleDriver(Integer vehicleID, Integer driverID) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        String stopDate = df.format(toUTC(new Date()));
        String startDate = df.format(toUTC(new Date()));
        String device = getDeviceID(vehicleID);

        if(!getDriverCount(vehicleID).equals("0")){

            String imei = getImeiDriver(vehicleID);
            String acctID = getAcctidDriver(vehicleID);
            String baseID = getBaseIdDriver(vehicleID);
            String emuMask = getEmuFeatureDriver(vehicleID);
            String vGroupID = getVgroupIdDriver(vehicleID);
            String vtype = getGetVtypeDriver(vehicleID);
            String dgroupID = getGetDgroupidDriver(vehicleID);
            String tzID = getGetTzidDriver(vehicleID);

            getJdbcTemplate().update(UPDATE_DEVICE_DRIVER, new Object[]{stopDate, vehicleID});

            getJdbcTemplate().update(UPDATE_DEVICE_VEHICLE_NEW, new Object[]{startDate, device, vehicleID, imei, acctID, baseID, emuMask, vGroupID, vtype, driverID, dgroupID, tzID});

        }

        if(getDriverCount(vehicleID).equals("0")){

            String maxImei = getMaxImeiVehicle();
            String maxAcctID = getMaxAcctIDVehicle();
            String maxTz = getGetMaxTzid();

            getJdbcTemplate().update(NEWDEVICE_VEHICLE, new Object[]{startDate, device, vehicleID, maxImei, maxAcctID, 0, maxTz});

        }
    }

    @Override
    public void setVehicleDriver(Integer vehicleID, Integer driverID, Date assignDate) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        String stopDate = df.format(toUTC(new Date()));
        String startDate = df.format(toUTC(new Date()));
        String device = getDeviceID(vehicleID);

        if(!getDriverCount(vehicleID).equals("0")){

            String imei = getImeiDriver(vehicleID);
            String acctID = getAcctidDriver(vehicleID);
            String baseID = getBaseIdDriver(vehicleID);
            String emuMask = getEmuFeatureDriver(vehicleID);
            String vGroupID = getVgroupIdDriver(vehicleID);
            String vtype = getGetVtypeDriver(vehicleID);
            String dgroupID = getGetDgroupidDriver(vehicleID);
            String tzID = getGetTzidDriver(vehicleID);

            getJdbcTemplate().update(UPDATE_DEVICE_DRIVER, new Object[]{stopDate, vehicleID});

            getJdbcTemplate().update(UPDATE_DEVICE_VEHICLE_NEW, new Object[]{assignDate, device, vehicleID, imei, acctID, baseID, emuMask, vGroupID, vtype, driverID, dgroupID, tzID});

        }

        if(getDriverCount(vehicleID).equals("0")){

            String maxImei = getMaxImeiVehicle();
            String maxAcctID = getMaxAcctIDVehicle();
            String maxTz = getGetMaxTzid();

            getJdbcTemplate().update(NEWDEVICE_VEHICLE, new Object[]{assignDate, device, vehicleID, maxImei, maxAcctID, 0, maxTz});

        }
    }

    @Override
    public void setVehicleDevice(Integer vehicleID, Integer deviceID, Date assignDate) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        String stopDate = df.format(toUTC(new Date()));
        String startDate = df.format(toUTC(new Date()));

        if (getVegicleCount(deviceID).equals("1")) {

            String imei = getImei(deviceID);
            String acctID = getAcctid(deviceID);
            String baseID = getBaseId(deviceID);
            String emuMask = getEmuFeature(deviceID);
            String vGroupID = getVgroupID(deviceID);
            String vtype = getvtype(deviceID);
            String driverID = geDriverID(deviceID);
            String dgroupID = getDgroupID(deviceID);
            String tzID = getTzID(deviceID);

            getJdbcTemplate().update(UPDATE_DEVICE_VEHICLE, new Object[]{stopDate, deviceID});

            getJdbcTemplate().update(UPDATE_DEVICE_VEHICLE_NEW, new Object[]{assignDate, deviceID, vehicleID, imei, acctID, baseID, emuMask, vGroupID, vtype, driverID, dgroupID, tzID});
        }

        if(getVegicleCount(deviceID).equals("0")){

            String maxImei = getMaxImei(deviceID);
            String maxAcctID = getMaxAcctID(deviceID);
            String maxTz = getGetMaxTzid();

            getJdbcTemplate().update(NEWDEVICE_VEHICLE, new Object[]{assignDate, deviceID, vehicleID, maxImei, maxAcctID, 0, maxTz});

        }
    }

    @Override
    public void setVehicleDevice(Integer vehicleID, Integer deviceID) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        String stopDate = df.format(toUTC(new Date()));
        String startDate = df.format(toUTC(new Date()));

        if (!getVegicleCount(deviceID).equals("0")) {

            String imei = getImei(deviceID);
            String acctID = getAcctid(deviceID);
            String baseID = getBaseId(deviceID);
            String emuMask = getEmuFeature(deviceID);
            String vGroupID = getVgroupID(deviceID);
            String vtype = getvtype(deviceID);
            String driverID = geDriverID(deviceID);
            String dgroupID = getDgroupID(deviceID);
            String tzID = getTzID(deviceID);

            getJdbcTemplate().update(UPDATE_DEVICE_VEHICLE, new Object[]{stopDate, deviceID});

            getJdbcTemplate().update(UPDATE_DEVICE_VEHICLE_NEW, new Object[]{startDate, deviceID, vehicleID, imei, acctID, baseID, emuMask, vGroupID, vtype, driverID, dgroupID, tzID});

            }

        if(getVegicleCount(deviceID).equals("0")){

            String deviceImei = getMaxImei(deviceID);
            String acctIDdev = getMaxAcctID(deviceID);
            String maxTz = getGetMaxTzid();

            getJdbcTemplate().update(NEWDEVICE_VEHICLE, new Object[]{startDate, deviceID, vehicleID, deviceImei, acctIDdev, 0, maxTz});

        }
    }

    @Override
    public void clearVehicleDevice(Integer vehicleID, Integer deviceID) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        String stopDate = df.format(toUTC(new Date()));

        if (getVegicleCount(deviceID).equals("1")) {

            getJdbcTemplate().update(UPDATE_DEVICE_VEHICLE, new Object[]{stopDate, deviceID});

        }
    }

    @Override
    public Vehicle findByVIN(String vin) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("vin", "" + vin + "");
            StringBuilder vehicleFindByVin = new StringBuilder(FIND_BY_VIN);
            Vehicle veh = null;
            List<Vehicle> vehic = getSimpleJdbcTemplate().query(vehicleFindByVin.toString(), pagedVehicleRowMapper, params);
            if (vehic.isEmpty()) {
            } else {
                veh = vehic.get(0);
            }
            return veh;

        } catch (EmptyResultSetException e) {
            return null;
        }
    }

    @Override
    public Vehicle findByDriverID(Integer driverID) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("driverID", driverID);
            StringBuilder vehicleFindByDriver = new StringBuilder(FIND_BY_DRIVER_ID);
            Vehicle veh = getSimpleJdbcTemplate().queryForObject(vehicleFindByDriver.toString(), pagedVehicleRowMapper, params);

            return veh;
    }

    @Override
    public Vehicle findByDriverInGroup(Integer driverID, Integer groupID) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("driverID", driverID);
        params.put("groupID", groupID);
        StringBuilder findByDriverIn = new StringBuilder(FIND_BY_DRIVER_IN_GROUP);

        return getSimpleJdbcTemplate().queryForObject(findByDriverIn.toString(), pagedVehicleRowMapper, params);
    }

    @Override
    public LastLocation getLastLocation(Integer vehicleID) {
        return locationDAO.getLastLocationForVehicle(vehicleID);
    }

    @Override
    public List<Trip> getTrips(Integer vehicleID, Date startDate, Date endDate) {
        return locationDAO.getTripsForVehicle(vehicleID, startDate, endDate);
    }

    @Override
    public Trip getLastTrip(Integer driverID) {
        return locationDAO.getLastTripForVehicle(driverID);
    }

    @Override
    public List<LatLng> getLocationsForTrip(Integer vehicleID, Date startTime, Date endTime) {
        return locationDAO.getLocationsForVehicleTrip(vehicleID, startTime, endTime);
    }

    @Override
    public List<LatLng> getLocationsForTrip(Integer vehicleID, Interval interval) {
        java.util.Date startTime = interval.getStart().toDate();
        java.util.Date endTime = interval.getEnd().toDate();
        return locationDAO.getLocationsForVehicleTrip(vehicleID, startTime, endTime);
    }

    @Override
    public List<DriverLocation> getVehiclesNearLoc(Integer groupID, Integer numof, Double lat, Double lng) {
        List <Vehicle> veh = getVehiclesByGroupIDDeep(groupID);
        List<DriverLocation> driverLocations = new ArrayList<DriverLocation>();

        for(int i = 0; i < veh.size(); i++) {
            Integer vehicleID = veh.get(i).getVehicleID();
            Double latitude = lastLatVehicle(vehicleID);
            Double longitude = lastLngVehicle(vehicleID);
            if (latitude==null) continue;
            if (longitude==null) continue;

            Double lat1 = Math.round(latitude * 100.0) / 100.0;
            Double lng1 = Math.round(longitude * 100.0) / 100.0;

            LastLocation loc = locationDAO.getLastLocationForVehicle(vehicleID);

            Double dist = latLngDistance(lat, lng, lat1, lng1);

            DriverLocation driverLoc = new DriverLocation();
            driverLoc.setLoc(loc.getLoc());
            driverLoc.setVehicle(veh.get(i));
            driverLoc.setTime(loc.getTime());
            driverLoc.setDist(dist);

            driverLoc.setDriver(getDriver(loc.getDriverID()));

            driverLocations.add(driverLoc);

        }

        Collections.sort(driverLocations, new Comparator<DriverLocation>() {
            @Override
            public int compare(DriverLocation l1, DriverLocation l2) {
                return (int)(l1.getDist() - l2.getDist());
            }
        });

        for (int i = numof ; i < driverLocations.size(); i++){
            driverLocations.remove(i);
        }

        return driverLocations;
    }


    public List<DriverLocation> getDriverLocations(Integer groupID) {
        return locationDAO.getDriverLocations(groupID);
    }

    @Override
    public Vehicle findByID(Integer vehicleID) {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("vehicleID", vehicleID);
        StringBuilder vehicleFindByID = new StringBuilder(FIND_BY_VEHICLEID);
        return getSimpleJdbcTemplate().queryForObject(vehicleFindByID.toString(), vehicleParameterizedRowMapper, args);

    }

    @Override
    public Integer create(Integer integer, final Vehicle entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_VEHICLE, Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, entity.getVIN());

                if (entity.getColor() == null) {
                    ps.setNull(2, Types.NULL);
                } else {
                    ps.setString(2, entity.getColor());
                }

                ps.setInt(3, entity.getGroupID());

                ps.setString(4, getPathByGroupId(entity.getGroupID()));

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                df.setTimeZone(TimeZone.getTimeZone("UTC"));
                String modified = df.format(toUTC(new Date()));

                ps.setString(5, modified);

                if (entity.getLicense() == null) {
                    ps.setNull(6, Types.NULL);
                } else {
                    ps.setString(6, entity.getLicense());
                }
                ps.setString(7, entity.getMake());
                ps.setString(8, entity.getModel());
                ps.setString(9,entity.getName());

                if (entity.getState() == null) {
                    ps.setNull(10, Types.NULL);
                } else {
                    ps.setInt(10, entity.getState().getStateID());
                }

                ps.setInt(11, entity.getStatus().getCode());

                if (entity.getWeight() == null) {
                    ps.setNull(12, Types.NULL);
                } else {
                    ps.setInt(12, entity.getWeight());
                }

                ps.setInt(13, entity.getYear());

                if (entity.getIfta() == null) {
                    ps.setNull(14, Types.NULL);
                } else {
                    ps.setBoolean(14, entity.getIfta());
                }

                if (entity.getOdometer() == null) {
                    ps.setNull(15, Types.NULL);
                } else {
                    ps.setInt(15, entity.getOdometer());
                }

                DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd");
                df.setTimeZone(TimeZone.getTimeZone("UTC"));
                String aggDate = dfm.format(toUTC(new Date()));

                ps.setString(16, aggDate);

                logger.debug(ps.toString());
                return ps;
            }
        };
        jdbcTemplate.update(psc, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public Integer update(final Vehicle entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                if (entity.getVehicleID() == null)
                    throw new SQLException("Cannot update vehicle with null id.");
                PreparedStatement ps = con.prepareStatement(UPDATE_VEHICLE);

                ps.setString(1, entity.getVIN());

                if (entity.getColor() == null) {
                    ps.setNull(2, Types.NULL);
                } else {
                    ps.setString(2, entity.getColor());
                }

                ps.setInt(3, entity.getGroupID());

                ps.setString(4, getPathByGroupId(entity.getGroupID()));

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                df.setTimeZone(TimeZone.getTimeZone("UTC"));
                String modified = df.format(toUTC(new Date()));

                ps.setString(5, modified);

                if (entity.getLicense() == null) {
                    ps.setNull(6, Types.NULL);
                } else {
                    ps.setString(6, entity.getLicense());
                }
                ps.setString(7, entity.getMake());
                ps.setString(8, entity.getModel());
                ps.setString(9,entity.getName());

                if (entity.getState() == null) {
                    ps.setNull(10, Types.NULL);
                } else {
                    ps.setInt(10, entity.getState().getStateID());
                }

                ps.setInt(11, entity.getStatus().getCode());

                if (entity.getWeight() == null) {
                    ps.setNull(12, Types.NULL);
                } else {
                    ps.setInt(12, entity.getWeight());
                }

                ps.setInt(13, entity.getYear());

                if (entity.getIfta() == null) {
                    ps.setNull(14, Types.NULL);
                } else {
                    ps.setBoolean(14, entity.getIfta());
                }

                if (entity.getOdometer() == null) {
                    ps.setNull(15, Types.NULL);
                } else {
                    ps.setInt(15, entity.getOdometer());
                }

                DateFormat dfNew = new SimpleDateFormat("yyyy-MM-dd");
                df.setTimeZone(TimeZone.getTimeZone("UTC"));
                String newAggDate = dfNew.format(toUTC(new Date()));

                ps.setString(16, newAggDate);

                ps.setInt(17, entity.getVehicleID());

                logger.debug(ps.toString());
                return ps;
            };

        };
        jdbcTemplate.update(psc);
        return entity.getVehicleID();
    }

    @Override
    public Integer deleteByID(Integer vehicleID) {
        return getJdbcTemplate().update(DEL_VEHICLE_BY_ID, new Object[]{vehicleID});
    }

    private ParameterizedRowMapper<VehicleName> vehicleNameParameterizedRowMapper = new ParameterizedRowMapper<VehicleName>() {
        @Override
        public VehicleName mapRow(ResultSet rs, int rowNum) throws SQLException {
            VehicleName vehicle = new VehicleName();
            vehicle.setVehicleID(rs.getInt("vehicleID"));
            vehicle.setVehicleName(rs.getString("name"));
            return vehicle;
        }
    };

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
            driver.setLicense(rs.getString("d.license"));
            driver.setLicenseClass(rs.getString("d.class"));
            driver.setState(States.getStateById(rs.getInt("d.stateID")));
            driver.setCertifications(rs.getString("d.certs"));
            driver.setDot(RuleSetType.valueOf(rs.getInt("d.dot")));
            driver.setBarcode(rs.getString("d.barcode"));
            driver.setRfid1(rs.getObject("d.rfid1") == null ? null : rs.getLong("d.rfid1"));
            driver.setRfid2(rs.getObject("d.rfid2")== null ? null : rs.getLong("d.rfid2"));
            driver.setFobID(rs.getString("d.fobID"));
            driver.setPerson(person);

            return driver;
        }
    };

    private ParameterizedRowMapper<Vehicle> vehicleParameterizedRowMapper = new ParameterizedRowMapper<Vehicle>() {
        @Override
        public Vehicle mapRow(ResultSet rs, int rowNum) throws SQLException {
            Vehicle veh = new Vehicle();
            veh.setVehicleID(rs.getInt("vehicleID"));
            veh.setGroupID(rs.getObject("groupID") == null ? null : rs.getInt("groupID"));
            veh.setFullName(rs.getString("name"));
            veh.setMake(rs.getString("make"));
            veh.setModel(rs.getString("model"));
            veh.setVIN(rs.getString("vin"));
            veh.setYear(rs.getObject("year") == null ? null : rs.getInt("year"));
            veh.setColor(rs.getObject("color") == null ? null : rs.getString("color"));
            veh.setIfta(rs.getObject("ifta") == null ? null : rs.getBoolean("ifta"));
            veh.setState(States.getStateById(rs.getInt("stateID")));
            veh.setStatus(Status.valueOf(rs.getInt("status")));
            veh.setVtype(VehicleType.valueOf(rs.getInt("vtype")));
            return veh;
        }
    };

    private ParameterizedRowMapper<Trip> tripParameterizedRowMapper = new ParameterizedRowMapper<Trip>() {
        @Override
        public Trip mapRow(ResultSet rs, int rowNum) throws SQLException {
            Trip trip = new Trip();

            trip.setTripID(rs.getLong("tripID"));
            trip.setDriverID(rs.getInt("driverID"));
            trip.setVehicleID(rs.getInt("vehicleID"));
            trip.setModified(rs.getDate("modified"));
            trip.setStartTime(rs.getDate("startTime"));
            trip.setEndTime(rs.getDate("endTime"));
            trip.setMileage(rs.getInt("mileage"));
            trip.setStatus(rs.getObject("status") == null ? null : TripStatus.valueOf(rs.getInt("status")));
            trip.setQuality(rs.getObject("quality") == null ? null : TripQuality.valueOf(rs.getInt("quality")));

            return trip;
        }
    };

    private List <Vehicle> getVehiclesByGroupIDDeep (Integer groupID) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID", "%/" + groupID + "/%");
        StringBuilder groupIdDeep = new StringBuilder(GROUP_ID_DEEP);
        List<Vehicle> vehiclesIn = getSimpleJdbcTemplate().query(groupIdDeep.toString(), pagedVehicleRowMapper, params);

        return  vehiclesIn;
    }

    private Date toUTC(Date date){
        DateTime dt = new DateTime(date.getTime()).toDateTime(DateTimeZone.UTC);
        return dt.toDate();
    }

    private String getPathByGroupId(Integer groupID){
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("groupID", groupID);
        String groupPath = new String (GET_GROUP_PATH);
        String grPath = getSimpleJdbcTemplate().queryForObject(groupPath, String.class, args);

        return grPath;
    }

    private String getVegicleCount(Integer deviceID){
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("deviceID", deviceID);
        String getCount = new String (VEHICLE_SET_DEVICE);
        String getCounting = getSimpleJdbcTemplate().queryForObject(getCount, String.class, args);

        return getCounting;
    }

    private String getDriverCount(Integer vehicleID){
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("vehicleID", vehicleID);
        String getCountDriver = new String (SET_DRIVER);
        String getCountingDriver = getSimpleJdbcTemplate().queryForObject(getCountDriver, String.class, args);

        return getCountingDriver;
    }

    //imei, acctID, baseID, emuFeatureMask, vgroupID, vtype, driverID, dgroupID, tzID
    private String getImei(Integer deviceID){
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("deviceID", deviceID);
        String getImei = new String (GET_IMEI);
        String getImeiColumns = getSimpleJdbcTemplate().queryForObject(getImei, String.class, args);
        return getImeiColumns;
    }

    private String getAcctid(Integer deviceID){
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("deviceID", deviceID);
        String getAcct = new String (GET_ACCTID);
        String getImeiColumn = getSimpleJdbcTemplate().queryForObject(getAcct, String.class, args);
        return getImeiColumn;

    }

    private String getBaseId(Integer deviceID){
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("deviceID", deviceID);
        String getbaseID = new String (GET_BASEID);
        String getBaseColumn = getSimpleJdbcTemplate().queryForObject(getbaseID, String.class, args);
        return getBaseColumn;

    }

    private String getEmuFeature(Integer deviceID){
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("deviceID", deviceID);
        String getEmuID = new String (GET_EMUMASK);
        String getEmuColumn = getSimpleJdbcTemplate().queryForObject(getEmuID, String.class, args);
        return getEmuColumn;
    }

    private String getVgroupID(Integer deviceID){
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("deviceID", deviceID);
        String getVgroupID = new String (GET_VGROUPID);
        String getVgroupColumn = getSimpleJdbcTemplate().queryForObject(getVgroupID, String.class, args);
        return getVgroupColumn;
    }

    private String getvtype(Integer deviceID){
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("deviceID", deviceID);
        String getVtype = new String (GET_VTYPE);
        String getVtypeColumn = getSimpleJdbcTemplate().queryForObject(getVtype, String.class, args);
        return getVtypeColumn;
    }

    private String geDriverID(Integer deviceID){
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("deviceID", deviceID);
        String getDriverId = new String (GET_DRIVERID);
        String getDriverIDColumn = getSimpleJdbcTemplate().queryForObject(getDriverId, String.class, args);
        return getDriverIDColumn;
    }

    private String getDgroupID(Integer deviceID){
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("deviceID", deviceID);
        String getDgroupID = new String (GET_DGROUPID);
        String getDgroupIDColumn = getSimpleJdbcTemplate().queryForObject(getDgroupID, String.class, args);
        return getDgroupIDColumn;
    }

    private String getTzID(Integer deviceID){
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("deviceID", deviceID);
        String getTz = new String (GET_TZID);
        String getTzColumn = getSimpleJdbcTemplate().queryForObject(getTz, String.class, args);
        return getTzColumn;
    }

    private String getDeviceID(Integer vehicleID){
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("vehicleID", vehicleID);
        String getDeviceId = new String (GET_DEVICE_ID);
        String getDeviceColumn = getSimpleJdbcTemplate().queryForObject(getDeviceId, String.class, args);
        return getDeviceColumn;
    }

    //For Max Values
    private String getMaxImei(Integer deviceID){
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("deviceID", deviceID);
        String getMaxImeiV = new String (GET_MAX_IMEI);
        String getMaxImeiColum = getSimpleJdbcTemplate().queryForObject(getMaxImeiV, String.class, args);
        return getMaxImeiColum;
    }

    private String getMaxAcctID(Integer deviceID){
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("deviceID", deviceID);
        String getMaxAcctIDV = new String (GET_MAX_ACCTID);
        String getMaxAcctIDColum = getSimpleJdbcTemplate().queryForObject(getMaxAcctIDV, String.class, args);
        return getMaxAcctIDColum;
    }

    private String getMaxImeiVehicle(){
        String getMaxImei = new String (GET_MAX_IMEI_VEHICLE);
        String getMaxImeiColumns = getSimpleJdbcTemplate().queryForObject(getMaxImei, String.class);
        return getMaxImeiColumns;
    }

    private String getMaxAcctIDVehicle(){
        String getMaxAcctID = new String (GET_MAX_ACCTID_VEHICLE);
        String getMaxAcctIDColumns = getSimpleJdbcTemplate().queryForObject(getMaxAcctID, String.class);
        return getMaxAcctIDColumns;
    }

    private String getGetMaxTzid(){
        String getMaxTzid = new String (GET_MAX_TZID);
        String getMaxTzidColumns = getSimpleJdbcTemplate().queryForObject(getMaxTzid, String.class);
        return getMaxTzidColumns;
    }

    //imei, acctID, baseID, emuFeatureMask, vgroupID, vtype, driverID, dgroupID, tzID
    private String getImeiDriver (Integer vehicleID){
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("vehicleID", vehicleID);
        String getImei = new String (GET_IMEI_DRIVER);
        String getImeiColumnD = getSimpleJdbcTemplate().queryForObject(getImei, String.class, args);
        return getImeiColumnD;
    }

    private String getAcctidDriver(Integer vehicleID){
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("vehicleID", vehicleID);
        String getAcctD = new String (GET_ACCTID_DRIVER);
        String getAcctColumnD = getSimpleJdbcTemplate().queryForObject(getAcctD, String.class, args);
        return getAcctColumnD;

    }

    private String getBaseIdDriver(Integer vehicleID){
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("vehicleID", vehicleID);
        String getbaseIdDriver = new String (GET_BASEID_DRIVER);
        String getBaseIdColumnD = getSimpleJdbcTemplate().queryForObject(getbaseIdDriver, String.class, args);
        return getBaseIdColumnD;

    }

    private String getEmuFeatureDriver(Integer vehicleID){
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("vehicleID", vehicleID);
        String getEmuIdDriver = new String (GET_EMUMASK_DRIVER);
        String getEmuColumnD = getSimpleJdbcTemplate().queryForObject(getEmuIdDriver, String.class, args);
        return getEmuColumnD;
    }

    private String getVgroupIdDriver(Integer vehicleID){
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("vehicleID", vehicleID);
        String getVgroupIdDriver = new String (GET_VGROUPID_DRIVER);
        String getVgroupColumnD = getSimpleJdbcTemplate().queryForObject(getVgroupIdDriver, String.class, args);
        return getVgroupColumnD;
    }

    private String getGetVtypeDriver(Integer vehicleID){
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("vehicleID", vehicleID);
        String getVtypeD = new String (GET_VTYPE_DRIVER);
        String getVtypeColumnD = getSimpleJdbcTemplate().queryForObject(getVtypeD, String.class, args);
        return getVtypeColumnD;
    }


    private String getGetDgroupidDriver(Integer vehicleID){
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("vehicleID", vehicleID);
        String getDgroupIdDr = new String (GET_DGROUPID_DRIVER);
        String getDgroupIDColumnD = getSimpleJdbcTemplate().queryForObject(getDgroupIdDr, String.class, args);
        return getDgroupIDColumnD;
    }

    private String getGetTzidDriver(Integer vehicleID){
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("vehicleID", vehicleID);
        String getTzD = new String (GET_TZID_DRIVER);
        String getTzColumnD = getSimpleJdbcTemplate().queryForObject(getTzD, String.class, args);
        return getTzColumnD;
    }

    public double latLngDistance(double latA, double lngA, double latB, double lngB){

        double rLatA = latA / 57.2957795786;
        double rLngA = lngA / 57.2957795786;
        double rLatB = latB / 57.2957795786;
        double rLngB = lngB / 57.2957795786;
        double dlat = rLatB - rLatA;
        double dlng = rLngB - rLngA;
        double aa = (Math.sin(dlat/2) * Math.sin(dlat/2)) + Math.cos(rLatA) * Math.cos(rLatB) * (Math.sin(dlng/2) * Math.sin(dlng/2));
        double cc = 2 * Math.atan2(Math.sqrt(aa), Math.sqrt(1-aa));
        // Approximate radius of earth in miles
        double d= cc * 3956.0;
     return d;
    }

    private Double lastLatVehicle(Integer vehicleID) {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("vehicleID", vehicleID);
        String getLastLat = new String("SELECT latitude FROM lastLocVehicle where vehicleID=:vehicleID");
        try {
            return getSimpleJdbcTemplate().queryForObject(getLastLat, Double.class, args);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    private Double lastLngVehicle(Integer vehicleID) {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("vehicleID", vehicleID);
        String getLastLng = new String("SELECT longitude FROM lastLocVehicle where vehicleID=:vehicleID");
        try {
            return getSimpleJdbcTemplate().queryForObject(getLastLng, Double.class, args);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    private Driver getDriver(Integer driverID){
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("driverID", driverID);
        String getDrv = new String (DRIVER_PERSON);
        List<Driver>  getDrvCol = getSimpleJdbcTemplate().query(getDrv, pagedDriverRowMapper, args);
        return getDrvCol.get(0);
    }
}
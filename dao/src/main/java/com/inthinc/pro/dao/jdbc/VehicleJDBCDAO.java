package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.LocationDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.cassandra.LocationCassandraDAO;
import com.inthinc.pro.dao.hessian.DriverHessianDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.*;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.configurator.ProductType;
import com.mysql.jdbc.Statement;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.text.DateFormatter;
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

    //2014-03-17 Use LocationCassandraDAO instead of LocationDAO
    private LocationCassandraDAO locationDAO;

    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter dateFormatterAgg = DateTimeFormat.forPattern("yyyy-MM-dd");

    private static String VEHICLE_COLUMNS_STRING =
            " v.vehicleID, v.groupID, v.status, v.name, v.make, v.model, v.year, v.color, v.vtype, v.vin, v.weight, v.license, v.stateID, v.odometer, v.ifta, v.absOdometer, v.country, " +
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

    private static final String GET_VEHICLE = FIND_BY + "where v.groupID= :groupID";

    private static final String GET_VEHICLE_NAME="select vehicleID, name from vehicle where groupID = :groupID";

    private static final String FIND_BY_DRIVER_ID = FIND_BY + "where dr.driverID = :driverID";
    private static final String FIND_BY_DRIVER_IN_GROUP = FIND_BY + "where dr.driverID = :driverID and v.groupID = :groupID";
    private static final String FIND_BY_VIN = FIND_BY +"where v.vin like :vin";
    private static final String FIND_BY_VEHICLEID = FIND_BY +"where v.vehicleID=:vehicleID";
    private static final String GROUP_ID_DEEP = FIND_BY +"where v.groupID=:groupID and v.status <> 3";
    private static final String DEL_VEHICLE_BY_ID = "DELETE FROM vehicle WHERE vehicleID = ?";

    private static final String GET_GROUP_PATH = "select g.groupPath from groups g where g.groupID= :groupID";

    private static final String TRIP_LIST = "SELECT * FROM trip where vehicleID=:vehicleID and startTime like :startTime and endTime like :endTime";

    private static final String INSERT_VEHICLE = "insert into vehicle (VIN, color, groupID, groupPath, modified, license, make, model, name, stateID, status, weight, year, ifta)" +
                                                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_VEHICLE = "UPDATE vehicle SET VIN= ?, color=?, groupID=?, groupPath=?, modified=?, license=?, make=?, model=?, name=?, stateID=?, status=?, weight=?, year=?, ifta=? where vehicleID= ?";


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
            vehicle.setFullName(rs.getString("v.name"));
            vehicle.setName(vehicle.getFullName());
            vehicle.setMake(rs.getString("v.make"));
            vehicle.setModel(rs.getString("v.model"));
            vehicle.setDriverName(rs.getString("driverName"));
            vehicle.setGroupName(rs.getString("groupName"));
            vehicle.setVIN(rs.getString("v.vin"));
            vehicle.setYear(rs.getObject("v.year") == null ? null : rs.getInt("v.year"));
            vehicle.setState(States.getStateById(rs.getInt("v.stateID")));
            vehicle.setStatus(Status.valueOf(rs.getInt("v.status")));
            vehicle.setVtype(VehicleType.valueOf(rs.getInt("v.vtype")));
            vehicle.setCountry(rs.getString("v.country"));

//            vehicle.setWeight(rs.getObject("v.weight") == null ? null : rs.getInt("v.weight"));
//            vehicle.setColor(rs.getString("v.color"));
//            vehicle.setIfta(rs.getObject("v.ifta") == null ? null : rs.getBoolean("v.ifta"));
//            vehicle.setLicense(rs.getString("v.license"));
//            Long absOdometer = rs.getObject("v.absOdometer") == null ? null : (rs.getLong("v.absOdometer"));
//            Long odometer = rs.getObject("v.odometer") == null ? null : rs.getLong("v.odometer");
//            if (absOdometer != null) {
//                vehicle.setOdometer(Long.valueOf(absOdometer / 100l).intValue());
//            } else if (odometer != null) {
//                Integer milesDriven = getMilesDriven(vehicle.getVehicleID());
//                vehicle.setOdometer(Long.valueOf((odometer + milesDriven) / 100).intValue());
//            }

            if (vehicle.getDeviceID() != null) {
                Device device = new Device();
                device.setDeviceID(rs.getInt("d.deviceID"));
                device.setName(rs.getString("d.name"));
                device.setStatus(rs.getObject("d.status") == null ? null : DeviceStatus.valueOf(rs.getInt("d.status")));
                device.setProductVer(rs.getObject("d.productVer") == null ? null : rs.getInt("d.productVer"));
                device.setProductVersion(device.getProductVer() == null ? ProductType.UNKNOWN : ProductType.getProductTypeFromVersion(device.getProductVer()));

//              device.setAccountID(rs.getInt("d.acctID"));
//              device.setVehicleID(vehicle.getVehicleID());
//              device.setImei(rs.getString("d.imei"));
//              device.setSim(rs.getString("d.sim"));
//              device.setSerialNum(rs.getString("d.serialNum"));
//              device.setPhone(rs.getString("d.phone"));
//              device.setActivated(rs.getObject("d.activated") == null ? null : rs.getDate("d.activated"));
//              device.setBaseID(rs.getObject("d.baseID") == null ? null : rs.getInt("d.baseID"));
//              device.setFirmwareVersion(rs.getObject("d.firmVer") == null ? null : Long.valueOf(rs.getLong("d.firmVer")).intValue());
//              device.setWitnessVersion(rs.getObject("d.witnessVer") == null ? null : Long.valueOf(rs.getLong("d.witnessVer")).intValue());
//              device.setEmuMd5(rs.getString("d.emuMd5"));
//              device.setMcmid(rs.getString("d.mcmid"));
//              device.setAltimei(rs.getString("d.altImei"));
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

            List <Vehicle> vehicleList = getVehiclesByGroupIDDeep(groupID);

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
        throw new NotImplementedException();
    }

    @Override
    public void setVehicleDriver(Integer vehicleID, Integer driverID, Date assignDate) {
        throw new NotImplementedException();
    }

    @Override
    public void setVehicleDevice(Integer vehicleID, Integer deviceID, Date assignDate) {
        throw new NotImplementedException();
    }

    @Override
    public void setVehicleDevice(Integer vehicleID, Integer deviceID) {
        throw new NotImplementedException();
    }

    @Override
    public void clearVehicleDevice(Integer vehicleID, Integer deviceID) {
        throw new NotImplementedException();
    }

    @Override
    public Vehicle findByVIN(String vin) {

        try{
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("vin", "" + vin + "");
        StringBuilder vehicleFindByVin = new StringBuilder(FIND_BY_VIN);
            Vehicle veh=null;
        List<Vehicle> vehic =  getSimpleJdbcTemplate().query(vehicleFindByVin.toString(), pagedVehicleRowMapper, params);
            if(vehic.isEmpty()){
            }else{
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

        return getSimpleJdbcTemplate().queryForObject(vehicleFindByDriver.toString(), pagedVehicleRowMapper, params);
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
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("vehicleID", vehicleID);

        LastLocation lastLocation = new LastLocation();

        return  lastLocation;
    }

    @Override
    public List<Trip> getTrips(Integer vehicleID, Date startTime, Date endTime) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("vehicleID", vehicleID);
        params.put("startTime", "" + startTime + "%");
        params.put("endTime", "" + endTime + "%");

        StringBuilder vehicleSelectAcct = new StringBuilder(TRIP_LIST);

        List<Trip> trips = getSimpleJdbcTemplate().query(vehicleSelectAcct.toString(), tripParameterizedRowMapper, params);

        return trips;
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
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("vehicleID", vehicleID);
//        locationDAO.getLocationsForVehicleTrip()

        throw new NotImplementedException();
    }


    @Override
    public List<DriverLocation> getVehiclesNearLoc(Integer groupID, Integer numof, Double lat, Double lng) {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("groupID", groupID);
        params.put("numof",  10 );
        params.put("latitude", lat );
        params.put("longitude", lng );

//        locationDAO.getLastLocationForVehicle()

        StringBuilder vehicleNearLocation = new StringBuilder(TRIP_LIST);


        throw new NotImplementedException();
    }

    @Override
    public Vehicle findByID(Integer vehicleID) {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("vehicleID", vehicleID);
        StringBuilder vehicleFindByID = new StringBuilder(FIND_BY_VEHICLEID);
        return getSimpleJdbcTemplate().queryForObject(vehicleFindByID.toString(), pagedVehicleRowMapper, args);

    }

    @Override
    public Integer create(Integer integer, final Vehicle entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_VEHICLE, Statement.RETURN_GENERATED_KEYS);
//                VIN*, color, deviceID, driverID, team groupID*, license, make*, model*, name* - vehicleID, state, status*, vtype, weight, year*, ifta
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

                ps.setInt(15, entity.getVehicleID());

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
        params.put("groupID", groupID);
        StringBuilder groupIdDeep = new StringBuilder(GROUP_ID_DEEP);
        List<Vehicle> vehiclesIn = getSimpleJdbcTemplate().query(groupIdDeep.toString(), pagedVehicleRowMapper, params);

        return  vehiclesIn;
    }

    protected Integer getCentralId(Map<String, Object> map) {
        return (Integer) map.get("id");
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

}

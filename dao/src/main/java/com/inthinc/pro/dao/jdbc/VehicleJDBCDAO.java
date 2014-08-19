package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleName;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.configurator.ProductType;
import org.joda.time.Interval;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JDBC Vehicle DAO.
 */
public class VehicleJDBCDAO extends SimpleJdbcDaoSupport implements VehicleDAO {
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
            vehicle.setName(rs.getString("v.name"));
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
        throw new NotImplementedException();
    }

    @Override
    public List<Vehicle> getVehiclesInGroup(Integer groupID) {
        throw new NotImplementedException();
    }

    @Override
    public List<VehicleName> getVehicleNames(Integer groupID) {
        throw new NotImplementedException();
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
        throw new NotImplementedException();
    }

    @Override
    public Vehicle findByDriverID(Integer driverID) {
        throw new NotImplementedException();
    }

    @Override
    public Vehicle findByDriverInGroup(Integer driverID, Integer groupID) {
        throw new NotImplementedException();
    }

    @Override
    public LastLocation getLastLocation(Integer vehicleID) {
        throw new NotImplementedException();
    }

    @Override
    public List<Trip> getTrips(Integer vehicleID, Date startDate, Date endDate) {
        throw new NotImplementedException();
    }

    @Override
    public Trip getLastTrip(Integer driverID) {
        throw new NotImplementedException();
    }

    @Override
    public List<LatLng> getLocationsForTrip(Integer vehicleID, Date startTime, Date endTime) {
        throw new NotImplementedException();
    }

    @Override
    public List<LatLng> getLocationsForTrip(Integer vehicleD, Interval interval) {
        throw new NotImplementedException();
    }

    @Override
    public List<DriverLocation> getVehiclesNearLoc(Integer groupID, Integer numof, Double lat, Double lng) {
        throw new NotImplementedException();
    }

    @Override
    public Vehicle findByID(Integer integer) {
        throw new NotImplementedException();
    }

    @Override
    public Integer create(Integer integer, Vehicle entity) {
        throw new NotImplementedException();
    }

    @Override
    public Integer update(Vehicle entity) {
        throw new NotImplementedException();
    }

    @Override
    public Integer deleteByID(Integer integer) {
        throw new NotImplementedException();
    }
}

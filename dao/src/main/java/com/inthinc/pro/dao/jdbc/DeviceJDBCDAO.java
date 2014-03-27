package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.model.ForwardCommand;
import com.inthinc.pro.model.ForwardCommandStatus;
import com.inthinc.pro.model.pagination.SortOrder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Device jdbc dao.
 */
public class DeviceJDBCDAO extends SimpleJdbcDaoSupport implements DeviceDAO{
    private static final String DEVICE_COLUMNS_STRING = "d.deviceID, d.acctID, d.baseID, d.status, d.autoLogoff," +
            " d.productVer, d.firmVer, d.witnessVer, d.emuFeatureMask, d.serialNum, d.name, d.imei, d.mcmid, d.altImei, " +
            " d.sim, d.phone, d.ephone, d.emuMd5, d.speedSet, d.accel, d.brake, d.turn, d.vert, d.modified, d.activated, " +
            " d.vehicleID, d.vehicleName ";

    private static final String DEVICE_SUFFIX = "FROM (select d.*, vdd.vehicleID, veh.name vehicleName from device d " +
            " LEFT OUTER JOIN vddlog vdd ON (d.deviceID = vdd.deviceID and vdd.stop is null)" +
            " LEFT OUTER JOIN vehicle veh on (veh.vehicleID = vdd.vehicleID)" +
            " ) d where d.deviceID = :deviceID ";

    private static final String DEVICE_SECOND = "FROM (select d.*, vdd.vehicleID, veh.name vehicleName from device d " +
            " LEFT OUTER JOIN vddlog vdd ON (d.deviceID = vdd.deviceID and vdd.stop is null)" +
            " LEFT OUTER JOIN vehicle veh on (veh.vehicleID = vdd.vehicleID)" +
            " ) d ";

    private static final String GET_DEVICE_IN = "select " + DEVICE_COLUMNS_STRING + " " + DEVICE_SECOND + "where d.acctID=:acctID" ;

    private static final String FIND_BY_IMEI = "select " + DEVICE_COLUMNS_STRING + " " + DEVICE_SECOND + "where imei like :imei";

    private static final String FIND_BY_SERIALNUM = "select " + DEVICE_COLUMNS_STRING + " " + DEVICE_SECOND + " where serialNum like :serialNum";

    private static final String DEL_DEVICE_BY_ID = "DELETE FROM device WHERE deviceID = ?";

    //get ForwardCommand
    private static final String GET_FWD = "select * from fwd ";
    private static final String GET_FWD_LIST = GET_FWD + "where deviceID=:deviceID and status=:status";

    private VehicleDAO vehicleDAO;

    private ParameterizedRowMapper<Device> deviceMapper = new ParameterizedRowMapper<Device>() {
        @Override
        public Device mapRow(ResultSet rs, int rowNum) throws SQLException {
            Device device = new Device();

            device.setDeviceID(getIntOrNullFromRS(rs, "deviceID"));

            Integer vehicleId = getIntOrNullFromRS(rs, "vehicleID");
            if (vehicleId != null && vehicleId.intValue() == 0)
                vehicleId = null;
            device.setVehicleID(vehicleId);

            device.setVehicleName(getStringOrNullFromRS(rs, "vehicleName"));
            device.setAccountID(getIntOrNullFromRS(rs, "acctID"));
            device.setStatus(rs.getObject("status") == null ? null : DeviceStatus.valueOf(rs.getInt("status")));
            device.setName(getStringOrNullFromRS(rs, "name"));
            device.setSim(getStringOrNullFromRS(rs, "sim"));
            device.setPhone(getStringOrNullFromRS(rs, "phone"));
            device.setActivated(getDateOrNullFromRS(rs, "activated"));
            device.setImei(getStringOrNullFromRS(rs, "imei"));
            device.setSerialNum(getStringOrNullFromRS(rs, "serialNum"));
            device.setBaseID(getIntOrNullFromRS(rs, "baseID"));
            device.setFirmwareVersion(getIntOrNullFromRS(rs, "firmVer"));
            device.setAltimei(getStringOrNullFromRS(rs, "altImei"));
            device.setProductVer(getIntOrNullFromRS(rs, "productVer"));
            device.setMcmid(getStringOrNullFromRS(rs, "mcmid"));
            device.setWitnessVersion(getIntOrNullFromRS(rs, "witnessVer"));
            device.setEmuMd5(getStringOrNullFromRS(rs, "emuMd5"));
            device.setAltimei(getStringOrNullFromRS(rs, "altImei"));

            return device;
        }
    };

    private String getStringOrNullFromRS(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) == null ? null : rs.getString(columnName);
    }

    private Integer getIntOrNullFromRS(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) == null ? null : (int) rs.getLong(columnName);
    }

    private Date getDateOrNullFromRS(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) == null ? null : rs.getDate(columnName);
    }


    @Override
    public Device findByID(Integer deviceID) {
        String deviceSelect =  "select "+ DEVICE_COLUMNS_STRING +" " + DEVICE_SUFFIX;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("deviceID", deviceID);

        return getSimpleJdbcTemplate().queryForObject(deviceSelect, deviceMapper, params);
    }

    @Override
    public List<Device> getDevicesByAcctID(Integer accountID) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("acctID", accountID);
            StringBuilder deviceSelect = new StringBuilder(GET_DEVICE_IN);
            List<Device> deviceList = getSimpleJdbcTemplate().query(deviceSelect.toString(), deviceMapper, params);

            return  deviceList;
        }
        catch (EmptyResultDataAccessException e){
         return Collections.emptyList();
        }
    }

    @Override
    public Device findByIMEI(String imei) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("imei", ""+ imei +"");
            StringBuilder findbyIMEI = new StringBuilder(FIND_BY_IMEI);
            Device finByImei = getSimpleJdbcTemplate().queryForObject(findbyIMEI.toString(), deviceMapper, params);

            return  finByImei;
        }   catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Device findBySerialNum(String serialNum) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("serialNum", ""+ serialNum +"");
            StringBuilder serialNumber = new StringBuilder(FIND_BY_SERIALNUM);
            Device findBySerialNum = getSimpleJdbcTemplate().queryForObject(serialNumber.toString(), deviceMapper, params);

            return  findBySerialNum;
        }   catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<ForwardCommand> getForwardCommands(Integer deviceID, ForwardCommandStatus status) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("deviceID", deviceID);
            params.put("status", status.getCode());
            StringBuilder fwdCommandList = new StringBuilder(GET_FWD_LIST);
            List<ForwardCommand> fwdList = getSimpleJdbcTemplate().query(fwdCommandList.toString(), forwardCommandParameterizedRowMapper, params) ;

            return  fwdList;
        }
        catch (EmptyResultDataAccessException e){
            return Collections.emptyList();
        }
    }

    @Override
    public Integer queueForwardCommand(Integer deviceID, ForwardCommand forwardCommand) {
        throw new NotImplementedException();
    }

    @Override
    public Integer create(Integer integer, Device entity) {
        throw new NotImplementedException();
    }

    @Override
    public Integer update(Device entity) {
        throw new NotImplementedException();
    }

    @Override
    public Integer deleteByID(Integer deviceID) {
        Device device = findByID(deviceID);
        if(device.getVehicleID() != null)
            vehicleDAO.clearVehicleDevice(device.getVehicleID(), deviceID);

        return getJdbcTemplate().update(DEL_DEVICE_BY_ID, new Object[]{deviceID});
    }

    private ParameterizedRowMapper<ForwardCommand> forwardCommandParameterizedRowMapper = new ParameterizedRowMapper<ForwardCommand>() {
        @Override
        public ForwardCommand mapRow(ResultSet rs, int rowNum) throws SQLException {

            ForwardCommand fwdCommand = new ForwardCommand();
            fwdCommand.setFwdID(rs.getInt("fwdID"));
            fwdCommand.setCmd(rs.getInt("fwdCmd"));
            fwdCommand.setStatus(rs.getObject("status") == null ? null : ForwardCommandStatus.valueOf(rs.getInt("status")));
            fwdCommand.setPersonID(rs.getObject("personID") == null ? null : rs.getInt("personID"));
            fwdCommand.setDriverID(rs.getObject("driverID") == null ? null : rs.getInt("driverID"));
            fwdCommand.setVehicleID(rs.getObject("vehicleID") == null ? null : rs.getInt("vehicleID"));
            fwdCommand.setCreated(rs.getObject("created") == null ? null : rs.getDate("created"));
            fwdCommand.setModified(rs.getObject("modified") == null ? null : rs.getDate("modified"));

            return fwdCommand;
        }
    };

    public VehicleDAO getVehicleDAO() {
        return vehicleDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }
}

package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.model.ForwardCommand;
import com.inthinc.pro.model.ForwardCommandStatus;
import com.inthinc.pro.model.pagination.SortOrder;
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

    public void createTestDevice(int testAccountId, int testDeviceId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("deviceID", String.valueOf(testDeviceId));
        params.put("acctID", String.valueOf(testAccountId));
        getSimpleJdbcTemplate().update("insert into device (deviceID, acctID, imei, name, modified, activated, serialNum) values (:deviceID, :acctID, 'test-imei', 'test-name', NOW(), NOW(), 1234)", params);
    }

    public void deleteTestDevice(int testDeviceId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("deviceID", String.valueOf(testDeviceId));
        getSimpleJdbcTemplate().update("delete from device where deviceID = :deviceID", params);
    }

    @Override
    public List<Device> getDevicesByAcctID(Integer accountID) {
        throw new NotImplementedException();
    }

    @Override
    public Device findByIMEI(String imei) {
        throw new NotImplementedException();
    }

    @Override
    public Device findBySerialNum(String serialNum) {
        throw new NotImplementedException();
    }

    @Override
    public List<ForwardCommand> getForwardCommands(Integer deviceID, ForwardCommandStatus status) {
        throw new NotImplementedException();
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
    public Integer deleteByID(Integer integer) {
        throw new NotImplementedException();
    }
}

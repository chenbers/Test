package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.AddressDAO;
import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.SensitivitySliderValues;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.configurator.DeviceSettingDefinition;
import com.inthinc.pro.model.configurator.SettingValue;
import com.inthinc.pro.model.configurator.VehicleSetting;
import com.inthinc.pro.model.configurator.VehicleSettingHistory;
import com.mysql.jdbc.Statement;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.sql.Date;
import java.util.*;


public class ConfiguratorJDBCDAO extends SimpleJdbcDaoSupport implements ConfiguratorDAO {

    private static final String GET_DATA = "select veh.vehicleID, d.deviceID FROM vehicle veh" +
            " LEFT OUTER JOIN vddlog vdd ON (veh.vehicleID = vdd.vehicleID and vdd.stop is null)" +
            " LEFT OUTER JOIN device d on (vdd.deviceID = d.deviceID)" +
            " where veh.vehicleID = :vehicleID";

    private static final String GET_DATA_DESIRED = "select a.settingID, a.value  FROM siloDB.desiredVSet a WHERE a.vehicleID =:vehicleID";
    private static final String GET_DATA_ACTUAL = "select a.settingID ,a.value  FROM siloDB.actualVSet a WHERE a.vehicleID =:vehicleID";

    private ParameterizedRowMapper<VehicleSetting> vehicleSettingParameterizedRowMapper = new ParameterizedRowMapper<VehicleSetting>() {
        @Override
        public VehicleSetting mapRow(ResultSet rs, int rowNum) throws SQLException {
            VehicleSetting vehiclesetting = new VehicleSetting();
            vehiclesetting.setVehicleID(rs.getInt("veh.vehicleID"));
            vehiclesetting.setDeviceID(rs.getInt("d.deviceID"));
            vehiclesetting.setActual(getSettingsMapFromList(getActualSettings(rs.getInt("veh.vehicleID"))));
            vehiclesetting.setDesired(getSettingsMapFromList(getDesiredSettings(rs.getInt("veh.vehicleID"))));

            return vehiclesetting;
        }
    };

    private ParameterizedRowMapper<SettingValue> vehicleSettingValueParameterizedRowMapper = new ParameterizedRowMapper<SettingValue>() {
        @Override
        public SettingValue mapRow(ResultSet rs, int rowNum) throws SQLException {
            SettingValue vehicleSettingValue = new SettingValue();
            vehicleSettingValue.setSettingID(rs.getInt("a.settingID"));
            vehicleSettingValue.setSettingValue(rs.getString("a.value"));
            return vehicleSettingValue;
        }
    };


    @Override
    public List<DeviceSettingDefinition> getDeviceSettingDefinitions() {
        return null;
    }

    @Override
    public VehicleSetting getVehicleSettings(Integer vehicleID) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("vehicleID", vehicleID);
            return getSimpleJdbcTemplate().queryForObject(GET_DATA, vehicleSettingParameterizedRowMapper, params);
        } catch (EmptyResultSetException e) {
            return null;
        }
    }

    public List<SettingValue> getActualSettings(Integer vehicleID) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("vehicleID", vehicleID);
            return getSimpleJdbcTemplate().query(GET_DATA_ACTUAL, vehicleSettingValueParameterizedRowMapper, params);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

    public List<SettingValue> getDesiredSettings(Integer vehicleID) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("vehicleID", vehicleID);
            return getSimpleJdbcTemplate().query(GET_DATA_DESIRED, vehicleSettingValueParameterizedRowMapper, params);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

    public Map<Integer, String> getSettingsMapFromList(List<SettingValue> settingValueList){
        Map<Integer, String> actualSettingsMap = new HashMap<Integer, String>();
        for(SettingValue settingValue : settingValueList){
            actualSettingsMap.put(settingValue.getSettingID(),settingValue.getSettingValue());
        }
        return actualSettingsMap;
    }

    @Override
    public List<VehicleSetting> getVehicleSettingsByGroupIDDeep(Integer groupID) {
        return null;
    }

    @Override
    public List<Integer> getVehicleIDsByGroupIDDeep(Integer groupID) {
        return null;
    }

    @Override
    public void setVehicleSettings(Integer vehicleID, Map<Integer, String> setMap, Integer userID, String reason) {

    }

    @Override
    public List<VehicleSettingHistory> getVehicleSettingsHistory(Integer vehicleID, java.util.Date startTime, java.util.Date endTime) {
        return null;
    }

    @Override
    public void updateVehicleSettings(Integer vehicleID, Map<Integer, String> setMap, Integer userID, String reason) {

    }

    @Override
    public List<SensitivitySliderValues> getSensitivitySliderValues() {
        return null;
    }

    @Override
    public DeviceSettingDefinition findByID(Integer integer) {
        return null;
    }

    @Override
    public Integer create(Integer integer, DeviceSettingDefinition entity) {
        return null;
    }

    @Override
    public Integer update(DeviceSettingDefinition entity) {
        return null;
    }

    @Override
    public Integer deleteByID(Integer integer) {
        return null;
    }
}

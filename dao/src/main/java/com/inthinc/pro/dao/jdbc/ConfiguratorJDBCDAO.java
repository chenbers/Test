package com.inthinc.pro.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.SensitivitySliderValues;
import com.inthinc.pro.model.configurator.DeviceSettingDefinition;
import com.inthinc.pro.model.configurator.SettingValue;
import com.inthinc.pro.model.configurator.VehicleSetting;
import com.inthinc.pro.model.configurator.VehicleSettingHistory;


public class ConfiguratorJDBCDAO extends SimpleJdbcDaoSupport implements ConfiguratorDAO {

    private static Logger logger = Logger.getLogger(ConfiguratorJDBCDAO.class);

    private static final String GET_DATA = "select veh.vehicleID, d.deviceID FROM vehicle veh" +
            " LEFT OUTER JOIN vddlog vdd ON (veh.vehicleID = vdd.vehicleID and vdd.stop is null)" +
            " LEFT OUTER JOIN device d on (vdd.deviceID = d.deviceID)" +
            " where veh.vehicleID = :vehicleID";

    private static final String GET_DATA_ALL = "select veh.vehicleID, d.deviceID FROM vehicle veh" +
            " LEFT OUTER JOIN vddlog vdd ON (veh.vehicleID = vdd.vehicleID and vdd.stop is null)" +
            " LEFT OUTER JOIN device d on (vdd.deviceID = d.deviceID)" +
            " where veh.vehicleID in (:vehicleIDs)";

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
        throw new NotImplementedException();
    }

    @Override
    public VehicleSetting getVehicleSettings(Integer vehicleID) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("vehicleID", vehicleID);
            return getSimpleJdbcTemplate().queryForObject(GET_DATA, vehicleSettingParameterizedRowMapper, params);
        } catch (EmptyResultSetException e) {
            return null;
        } catch (IncorrectResultSizeDataAccessException e) {
            logger.error("Vehicle " + vehicleID + " appears to have too many open entries in the vddlog table.");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("vehicleID", vehicleID);
            return getSimpleJdbcTemplate().query(GET_DATA, vehicleSettingParameterizedRowMapper, params).get(0);
        }
    }

    @Override
    public Map<Integer, VehicleSetting> getVehicleSettingsForAll(List<Integer> vehicleIDs) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("vehicleIDs", vehicleIDs);
        List<VehicleSetting> rawVehicleSettings = getSimpleJdbcTemplate().query(GET_DATA_ALL, vehicleSettingParameterizedRowMapper, params);
        Map<Integer, VehicleSetting> vehicleSettingMap = new HashMap<Integer, VehicleSetting>(vehicleIDs.size());
        for (VehicleSetting vehicleSetting : rawVehicleSettings) {
            vehicleSettingMap.put(vehicleSetting.getVehicleID(), vehicleSetting);
        }

        return vehicleSettingMap;
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
        throw new NotImplementedException();
    }

    @Override
    public List<Integer> getVehicleIDsByGroupIDDeep(Integer groupID) {
        throw new NotImplementedException();
    }

    @Override
    public void setVehicleSettings(Integer vehicleID, Map<Integer, String> setMap, Integer userID, String reason) {
        throw new NotImplementedException();
    }

    @Override
    public List<VehicleSettingHistory> getVehicleSettingsHistory(Integer vehicleID, java.util.Date startTime, java.util.Date endTime) {
        throw new NotImplementedException();
    }

    @Override
    public void updateVehicleSettings(Integer vehicleID, Map<Integer, String> setMap, Integer userID, String reason) {
        throw new NotImplementedException();
    }

    @Override
    public List<SensitivitySliderValues> getSensitivitySliderValues() {
        throw new NotImplementedException();
    }

    @Override
    public DeviceSettingDefinition findByID(Integer integer) {
        throw new NotImplementedException();
    }

    @Override
    public Integer create(Integer integer, DeviceSettingDefinition entity) {
        throw new NotImplementedException();
    }

    @Override
    public Integer update(DeviceSettingDefinition entity) {
        throw new NotImplementedException();
    }

    @Override
    public Integer deleteByID(Integer integer) {
        throw new NotImplementedException();
    }
}

package com.inthinc.pro.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.inthinc.pro.dao.report.MaintenanceReportsDAO;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.model.MaintenanceReportItem;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.SettingType;

public class MaintenanceReportsJDBCDAO extends SimpleJdbcDaoSupport implements MaintenanceReportsDAO {
    
    private static final DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
    
    private static final String VEHICLES_WITH_THRESHOLDS = "SELECT avs.vehicleID vehicleID, v.name vehicleName, v.odometer odometer, v.absOdometer absOdometer, v.groupID vehicleGroupID, settingID, value, g.name vehicleGroupName, acctID, v.year year, v.make make, v.model model " +
                    "from actualVSet avs " +
                    "join vehicle v on (avs.vehicleID = v.vehicleID) " +
                    "join groups g on (v.groupID = g.groupID) " +
                    "where v.groupID in (:groupID_list) " +
                    "  and settingID in ("+SettingType.MAINT_THRESHOLD_ENGINE_HOURS.getSettingID()+" , "+SettingType.MAINT_THRESHOLD_ODOMETER.getSettingID()+" , "+SettingType.MAINT_THRESHOLD_ODOMETER_START.getSettingID()+" ) " +
                    "order by avs.vehicleID, settingID asc ";
    
    private static final String BASE_ODOMETER_MULT_VEHICLE = "select vehicleID, deviceID, settingID, value " +
                    "from actualVSet avs " +
                    "where vehicleID in ( :vehicleID_list ) " +
                    "and settingID = "+SettingType.MAINT_THRESHOLD_ODOMETER_START.getSettingID()+" and value > 0";

    private static final String MILES_DRIVEN =
            "SELECT MAX(vs.endingOdometer) milesDriven FROM vehicleScoreByDay vs where vs.vehicleID = :vehicleID";
    private static final String MILES_DRIVEN_MULT_VEHICLE =
                    "SELECT MAX(vs.endingOdometer) milesDriven, vs.vehicleID vehicleID FROM vehicleScoreByDay vs where vs.vehicleID in ( :vehicleID_list ) group by vehicleID";
    private static final String ENGINE_HOURS_MULT_VEHICLE =
                    "select " +
                    "   max(time) " + 
                    "   ,noteID, driverID, cnv.vehicleID, type, aggType, time, cnv.groupID, vehicleGroupID, driverGroupID, personID " +  
                    "   ,substring(attribs, locate(';218=', attribs )+5, (locate(';', attribs, locate(';218=', attribs )+1) - locate(';218=', attribs )-5)) as mileage218 " + 
                    "   ,substring(attribs, locate(';233=', attribs )+5, (locate(';', attribs, locate(';233=', attribs )+1) - locate(';233=', attribs )-5)) as mileage233  " +
                    "  ,substring(attribs, locate(';240=', attribs )+5, (locate(';', attribs, locate(';240=', attribs )+1) - locate(';240=', attribs )-5)) as engineHours  " +
                    "  , v.odometer, v.absOdometer " +
                    "from cachedNoteView cnv "  +
                    "left outer join vehicle v on (cnv.vehicleID = v.vehicleID) " +
                    "where cnv.vehicleID in (:vehicleID_list) " + //select vehicleID from vehicle where groupID in (select groupId from groups where acctID = 438)
                    "  and type = 20 " +
                    "group by vehicleID";

    @Override
    public Integer getMilesDriven(Integer vehicleID) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("vehicleID", vehicleID);
        
        Integer miles = getSimpleJdbcTemplate().queryForInt(MILES_DRIVEN, params);
        
        return miles == null ? 0 : miles;
    }
    
    @Override
    public Map<Integer , Integer > getMilesDriven(Set<Integer> vehicleIDs){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("vehicleID_list", vehicleIDs);
        
        List<Map.Entry<Integer, Integer>> resultEntries = getSimpleJdbcTemplate().query(MILES_DRIVEN_MULT_VEHICLE, new ParameterizedRowMapper<Map.Entry<Integer, Integer>>() {
            @Override
            public Map.Entry<Integer, Integer> mapRow(ResultSet rs, int rowNum) throws SQLException {
                Integer miles = rs.getInt("milesDriven");
                Map.Entry<Integer, Integer> resultsMap = new AbstractMap.SimpleEntry<Integer, Integer>(rs.getInt("vehicleID"), (miles!=null)?miles:0);
                return resultsMap;
            }           
        } , params);
        Map<Integer, Integer> resultsMap = new HashMap<Integer, Integer>();
        for(Map.Entry<Integer, Integer> entry: resultEntries) {
            resultsMap.put(entry.getKey(), entry.getValue());
        }
        return resultsMap;
    }
    
    @Override
    public Map<Integer, Integer> getEngineHours(List<Integer> vehicleIDs){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("vehicleID_list", vehicleIDs);
        
        List<Map.Entry<Integer, Integer>> resultEntries = getSimpleJdbcTemplate().query(ENGINE_HOURS_MULT_VEHICLE, new ParameterizedRowMapper<Map.Entry<Integer, Integer>>() {
            @Override
            public Map.Entry<Integer, Integer> mapRow(ResultSet rs, int rowNum) throws SQLException {
                Integer hours = rs.getInt("engineHours");
                Map.Entry<Integer, Integer> resultsMap = new AbstractMap.SimpleEntry<Integer, Integer>(rs.getInt("vehicleID"), hours);
                return resultsMap;
            }           
        } , params);
        Map<Integer, Integer> resultsMap = new HashMap<Integer, Integer>();
        for(Map.Entry<Integer, Integer> entry: resultEntries) {
            resultsMap.put(entry.getKey(), entry.getValue());
        }
        return resultsMap;
    }
    
    @Override
    public Map<Integer, Integer> getBaseOdometer(List<Integer> vehicleIDs){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("vehicleID_list", vehicleIDs);
        List<Map.Entry<Integer, Integer>> resultEntries = getSimpleJdbcTemplate().query(BASE_ODOMETER_MULT_VEHICLE, new ParameterizedRowMapper<Map.Entry<Integer, Integer>>() {
            @Override
            public Map.Entry<Integer, Integer> mapRow(ResultSet rs, int rowNum) throws SQLException {
                Double baseOdometer = rs.getDouble("value");
                Map.Entry<Integer, Integer> resultsMap = new AbstractMap.SimpleEntry<Integer, Integer>(rs.getInt("vehicleID"), baseOdometer.intValue());
                return resultsMap;
            }           
        } , params);
        Map<Integer, Integer> resultsMap = new HashMap<Integer, Integer>();
        for(Map.Entry<Integer, Integer> entry: resultEntries) {
            resultsMap.put(entry.getKey(), entry.getValue());
        }
        return resultsMap;
       
    }


    @Override
    public List<MaintenanceReportItem> getVehiclesWithThreshold(List<Integer> groupIDList) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID_list", groupIDList);
        
        List<MaintenanceReportItem> results = getSimpleJdbcTemplate().query(VEHICLES_WITH_THRESHOLDS, new ParameterizedRowMapper<MaintenanceReportItem>() {
            @Override
            public MaintenanceReportItem mapRow(ResultSet rs, int rowNum) throws SQLException {
                MaintenanceReportItem item = new MaintenanceReportItem();
                SettingType settingType = SettingType.getBySettingID(rs.getInt("settingID"));
                Integer vehicleID = rs.getInt("vehicleID");

                item.setVehicleName(rs.getString("vehicleName"));
                item.setGroupID(rs.getInt("vehicleGroupID"));
                item.setGroupName(rs.getString("vehicleGroupName"));
                item.setSettingType(settingType);
                Integer value = ((Double)rs.getDouble("value")).intValue();
                if(SettingType.MAINT_THRESHOLD_ENGINE_HOURS.equals(settingType)) {
                    item.setThresholdHours(value);
                } else if(SettingType.MAINT_THRESHOLD_ODOMETER.equals(settingType)) {
                    item.setThresholdOdo(value);
                } else if(SettingType.MAINT_THRESHOLD_ODOMETER_START.equals(settingType)) {
                    item.setThresholdBase(value);
                }
                item.setVehicleName(rs.getString("value"));
                item.setVehicleID(vehicleID);
                StringBuffer ymmString = new StringBuffer();
                ymmString.append(rs.getString("year") + " ");
                ymmString.append(rs.getString("make") + " ");
                ymmString.append(rs.getString("model") + " ");
                item.setYmmString(ymmString.toString());
                
                Long absOdometer = rs.getObject("absOdometer") == null ? null : (rs.getLong("absOdometer"));
                Long odometer = rs.getObject("odometer") == null ? null : rs.getLong("odometer");
                if (absOdometer != null) {
                    item.setVehicleOdometer(Long.valueOf(absOdometer / 100l).intValue());
                } else if (odometer != null) {
                    Integer milesDriven = getMilesDriven(item.getVehicleID());
                    item.setVehicleOdometer(Long.valueOf((odometer + milesDriven) / 100).intValue());
                }
                
                return item;
            }
        }, params);
        // TODO Auto-generated method stub
        Map<Integer, MaintenanceReportItem> reportItemMap = new HashMap<Integer, MaintenanceReportItem>();
        for(MaintenanceReportItem item: results) {
            if(reportItemMap.containsKey(item.getVehicleID())) {
                //merge vehicle settings!
                //non shared values are : 
                //item.getThreshold()
                if(item.getThresholdOdo() != null) {
                    reportItemMap.get(item.getVehicleID()).setThresholdOdo(item.getThresholdOdo());
                }
                if(item.getThresholdHours() != null) {
                    reportItemMap.get(item.getVehicleID()).setThresholdHours(item.getThresholdHours());
                }
                if(item.getThresholdOdo() != null) {
                    reportItemMap.get(item.getVehicleID()).setThresholdOdo(item.getThresholdOdo());
                }
                //item.getSettingType()
                //item.getThresholdBase()
                
            }else {
                reportItemMap.put(item.getVehicleID(), item);
            }
        }
        return results;
    }
    
    
    private ParameterizedRowMapper<Vehicle> pagedVehicleRowMapper = new ParameterizedRowMapper<Vehicle>() {
        @Override
        public Vehicle mapRow(ResultSet rs, int rowNum) throws SQLException {
            Vehicle vehicle = new Vehicle();
            vehicle.setVehicleID(rs.getInt("v.vehicleID"));
            vehicle.setColor(rs.getString("v.color"));
            vehicle.setCreated(null); 
            vehicle.setDeviceID(rs.getObject("vdd.deviceID") == null ? null : rs.getInt("vdd.deviceID"));
            vehicle.setDriverID(rs.getObject("vdd.driverID") == null ? null : rs.getInt("vdd.driverID"));
            vehicle.setFullName(rs.getString("v.name"));
            vehicle.setGroupID(rs.getObject("v.groupID") == null ? null : rs.getInt("v.groupID"));
            vehicle.setIfta(rs.getObject("v.ifta") == null ? null : rs.getBoolean("v.ifta"));
            vehicle.setLicense(rs.getString("v.license"));
            vehicle.setMake(rs.getString("v.make"));
            vehicle.setModel(rs.getString("v.model"));
//            vehicle.setModified(rs.getDate("v.modified"));
            vehicle.setName(rs.getString("v.name"));
            //vehicle.setDriverName(getDriverName(rs));

            Long absOdometer = rs.getObject("v.absOdometer") == null ? null : (rs.getLong("v.absOdometer"));
            Long odometer = rs.getObject("v.odometer") == null ? null : rs.getLong("v.odometer");
            if (absOdometer != null) {
                vehicle.setOdometer(Long.valueOf(absOdometer/100l).intValue());
            }
            else if (odometer != null) {
                //Integer milesDriven = getMilesDriven(vehicle.getVehicleID());
                vehicle.setOdometer(Long.valueOf((odometer)/100).intValue());
            }
            vehicle.setState(States.getStateById(rs.getInt("v.stateID")));
            vehicle.setStatus(Status.valueOf(rs.getInt("v.status")));
            vehicle.setVIN(rs.getString("v.vin"));
            vehicle.setVtype(VehicleType.valueOf(rs.getInt("v.vtype")));
            vehicle.setWeight(rs.getObject("v.weight") == null ? null : rs.getInt("v.weight"));
            vehicle.setYear(rs.getObject("v.year") == null ? null : rs.getInt("v.year"));

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
                
            }
            
            return vehicle;
        }
    };

}

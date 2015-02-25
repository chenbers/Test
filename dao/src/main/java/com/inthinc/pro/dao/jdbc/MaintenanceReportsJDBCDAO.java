package com.inthinc.pro.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
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
import com.inthinc.pro.model.event.NoteType;

public class MaintenanceReportsJDBCDAO extends SimpleJdbcDaoSupport implements MaintenanceReportsDAO {
    
    private static final DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
    
    private static final String VEHICLES_WITH_THRESHOLDS = "SELECT avs.vehicleID vehicleID, v.name vehicleName, v.odometer odometer, v.absOdometer absOdometer, agg.vehicleEndingOdometer aggOdo, v.groupID vehicleGroupID, settingID, value, g.name vehicleGroupName, acctID, v.year year, v.make make, v.model model " +
                    "from actualVSet avs " +
                    "join vehicle v on (avs.vehicleID = v.vehicleID) " +
                    "join groups g on (v.groupID = g.groupID) " +
                    "join agg on (v.vehicleID = agg.vehicleID) "+
                    "join (select vehicleID, max(aggDate) as max_aggDate from agg where aggDate <= now() group by vehicleID) vid_maxAggDate on vid_maxAggDate.vehicleID = agg.vehicleID and vid_maxAggDate.max_aggDate = agg.aggDate "+ 
                    "where v.groupID in (:groupID_list) " +
                    "  and settingID in ("+SettingType.MAINT_THRESHOLD_ENGINE_HOURS.getSettingID()+" , "+SettingType.MAINT_THRESHOLD_ODOMETER.getSettingID()+" , "+SettingType.MAINT_THRESHOLD_ODOMETER_START.getSettingID()+" ) " +
                    "order by avs.vehicleID, settingID asc ";
    
    private static final String BASE_ODOMETER_MULT_VEHICLE = "select vehicleID, deviceID, settingID, value " +
                    "from actualVSet avs " +
                    "where vehicleID in ( :vehicleID_list ) " +
                    "and settingID = "+SettingType.MAINT_THRESHOLD_ODOMETER_START.getSettingID()+" and value > 0";
    
    private static final String ODOMETER_TIWI_MULTI_VEHICLE = 
                    "select agg.aggID, agg.vehicleID, agg.vehicleEndingOdometer, agg.aggDate "+
                    "from agg "+
                    "join (select vehicleID, max(aggDate) as max_aggDate from agg where aggDate <= now() group by vehicleID)vid_maxAggDate on vid_maxAggDate.vehicleID = agg.vehicleID and vid_maxAggDate.max_aggDate = agg.aggDate "+
                    "where agg.vehicleID in ( :vehicleID_list );";

    private static final String MILES_DRIVEN =
                    "SELECT MAX(vs.endingOdometer) milesDriven, vs.vehicleID vehicleID FROM vehicleScoreByDay vs where vs.vehicleID = :vehicleID";
    private static final String MILES_DRIVEN_MULT_VEHICLE =
                    "SELECT MAX(vs.endingOdometer) milesDriven, vs.vehicleID vehicleID FROM vehicleScoreByDay vs where vs.vehicleID in ( :vehicleID_list ) group by vehicleID";
    private static final String ENGINE_HOURS_MULT_VEHICLE =
                    "select " +
                    "   time  " +
                    "   ,noteID, driverID, cnv.vehicleID, type, aggType, time, cnv.groupID, vehicleGroupID, driverGroupID, personID " + 
                    "   ,substring(attribs, locate(';218=', attribs )+5, (locate(';', attribs, locate(';218=', attribs )+1) - locate(';218=', attribs )-5)) as mileage218 " + // attr218 = ATTR_ODOMETER
                    "   ,substring(attribs, locate(';233=', attribs )+5, (locate(';', attribs, locate(';233=', attribs )+1) - locate(';233=', attribs )-5)) as mileage233   " + // attr233 = ATTR_VEH_ODO
                    "  ,substring(attribs, locate(';240=', attribs )+5, (locate(';', attribs, locate(';240=', attribs )+1) - locate(';240=', attribs )-5)) as engineHours   " + // attr240 = ATTR_ENGINE_HOURS_X100
                    "  , v.odometer, v.absOdometer  " +
                    "from cachedNoteView cnv  " +
                    "JOIN (select max(time) as max_time, vehicleID from cachedNoteView where cachedNoteView.vehicleID in (:vehicleID_list) and type = "+NoteType.IGNITION_OFF.getCode() +" group by vehicleID)vid_maxTime " + 
                    "        on vid_maxTime.vehicleID = cnv.vehicleID and vid_maxTime.max_time = cnv.time " +
                    "left outer join vehicle v on (cnv.vehicleID = v.vehicleID)  " +
                    "where cnv.vehicleID in (:vehicleID_list) " + 
                    "  and type = "+NoteType.IGNITION_OFF.getCode() +" " + 
                    "group by vehicleID;";
    
    private static final String MAINTENANCE_EVENTS_BY_GROUPIDS_BY_DATE_RANGE = "select " +
                    "   substring(attribs, locate(';218=', attribs )+5, (locate(';', concat(attribs, ';'), locate(';218=', attribs )+1) - locate(';218=', attribs )-5)) as mileage218       " +     // attr218 = ATTR_ODOMETER
                    " , substring(attribs, locate(';233=', attribs )+5, (locate(';', concat(attribs, ';'), locate(';233=', attribs )+1) - locate(';233=', attribs )-5)) as mileage233       " +     // attr233 = ATTR_VEH_ODO
                    " , substring(attribs, locate(';81=', attribs )+4, (locate(';',  concat(attribs, ';'), locate(';81=', attribs )+1) - locate(';81=', attribs )-4)) as voltage            " +     // attr81  = ATTR_BATTERY_VOLTAGE (volts X 10)
                    " , substring(attribs, locate(';171=', attribs )+5, (locate(';', concat(attribs, ';'), locate(';171=', attribs )+1) - locate(';171=', attribs )-5)) as engineTemp       " +     // attr171 = ATTR_ENGINE_TEMP (celsius)
                    " , substring(attribs, locate(';172=', attribs )+5, (locate(';', concat(attribs, ';'), locate(';172=', attribs )+1) - locate(';172=', attribs )-5)) as transmissionTemp " +     // attr172 = ATTR_TRANSMISSION_TEMP (celsius)
                    " , substring(attribs, locate(';173=', attribs )+5, (locate(';', concat(attribs, ';'), locate(';173=', attribs )+1) - locate(';173=', attribs )-5)) as dpfFlowRate      " +     // attr173 = ATTR_DPF_FLOW_RATE (kiloPascals)
                    " , substring(attribs, locate(';174=', attribs )+5, (locate(';', concat(attribs, ';'), locate(';174=', attribs )+1) - locate(';174=', attribs )-5)) as oilPressure      " +     // attr174 = ATTR_OIL_PRESSURE (kiloPascals)
                    " , substring(attribs, locate(';240=', attribs )+5, (locate(';', concat(attribs, ';'), locate(';240=', attribs )+1) - locate(';240=', attribs )-5)) as engineHoursX100  " +     // attr240 = ATTR_ENGINE_HOURS_X100
                    " , v.name vehicleName, year, make, model, cnv.type, cnv.aggType, time, v.vehicleID, v.groupID vehicleGroupID, g.name vehicleGroupName, avs.settingID as settingID      " +
                    " , avs.value as threshold " +
                    " , cnv.noteID as noteID " +
                    " , attribs " +
                    " from cachedNoteView cnv " + 
                    " left outer join actualVSet avs on (cnv.vehicleID = avs.vehicleID) " +
                    " left outer join vehicle v on (cnv.vehicleID = v.vehicleID) " +
                    " join groups g on (v.groupID = g.groupID) " +
                    " where cnv.time between :startDate and :endDate " +
                    "   and cnv.type in (238, 20 , 66, 1, 209, 202)  " + //maintenance note types
                    "   and avs.settingID in (190, 191, 192, 193, 194, 195, 196, 197) " + //maintenance settings
                    "   and cnv.attribs is not null " +
                    "   and (attribs like '%;81=%' or attribs like '%;171=%' or attribs like '%;172=%' or attribs like '%;173=%' or attribs like '%;174=%' or attribs like '%;240=%'  or attribs like '%;218=%' ) " +
                    "   and cnv.groupID in ( :groupID_list ) " + 
                    " order by groupID , vehicleID, time"
                    ;

    //    List<Event> getEventsForGroupFromVehicles(Integer groupID, List<NoteType> eventTypes, Date startDate, Date endDate);

    @Override
    public List<MaintenanceReportItem> findMaintenanceEventsByGroupIDs(List<Integer> groupIDs, Date startDate, Date endDate ){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID_list", groupIDs);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        
        
        List<MaintenanceReportItem> queryResults = getSimpleJdbcTemplate().query(MAINTENANCE_EVENTS_BY_GROUPIDS_BY_DATE_RANGE, new ParameterizedRowMapper<MaintenanceReportItem>() {
            @Override
            public MaintenanceReportItem mapRow(ResultSet rs, int rowNum) throws SQLException {
                MaintenanceReportItem item = new MaintenanceReportItem();
                SettingType settingType = SettingType.getBySettingID(rs.getInt("settingID"));
                Integer vehicleID = rs.getInt("vehicleID");
                item.setNoteID(rs.getLong("noteID"));

                item.setVehicleName(rs.getString("vehicleName"));
                item.setGroupID(rs.getInt("vehicleGroupID"));
                item.setGroupName(rs.getString("vehicleGroupName"));
                item.setSettingType(settingType);
                item.setEventTime(rs.getTime("time"));
                Double value = rs.getDouble("threshold");
                if(SettingType.MAINT_THRESHOLD_ENGINE_HOURS.equals(settingType)) {
                    item.setThresholdHours(value.intValue());
                } else if (SettingType.MAINT_THRESHOLD_ODOMETER.equals(settingType)) {
                    item.setThresholdOdo(value.intValue());
                } else if (SettingType.MAINT_THRESHOLD_ODOMETER_START.equals(settingType)) {
                    item.setThresholdBase(value.intValue());
                } else if (SettingType.MAINT_THRESHOLD_BATTERY_VOLTAGE.equals(settingType)) {
                    item.setThresholdVoltage(value);
                } else if (SettingType.MAINT_THRESHOLD_DPF_FLOW_RATE.equals(settingType)) {
                    item.setThresholdDpfFlowRate(value);
                } else if (SettingType.MAINT_THRESHOLD_ENGINE_TEMP.equals(settingType)) {
                    item.setThresholdEngineTemp(value);
                } else if (SettingType.MAINT_THRESHOLD_OIL_PRESSURE.equals(settingType)) {
                    item.setThresholdOilPressure(value);
                } else if (SettingType.MAINT_THRESHOLD_TRANSMISSION_TEMP.equals(settingType)) {
                    item.setThresholdTransmissionTemp(value);
                }
                
                item.setEventDpfFlowRate(rs.getDouble("dpfFlowRate"));
                item.setEventEngineHours(rs.getInt("engineHoursX100")/100);
                item.setEventEngineTemp(rs.getDouble("engineTemp"));
                item.setEventOilPressure(rs.getDouble("oilPressure"));
                item.setEventTransmissionTemp(rs.getDouble("transmissionTemp"));
                item.setEventVoltage(rs.getDouble("voltage"));
                
                item.setVehicleID(vehicleID);
                StringBuffer ymmString = new StringBuffer();
                ymmString.append(rs.getString("year") + " ");
                ymmString.append(rs.getString("make") + " ");
                ymmString.append(rs.getString("model") + " ");
                item.setYmmString(ymmString.toString());
                item.setEventOdometer(rs.getInt("mileage218"));
                item.setVehicleOdometer(rs.getInt("mileage218"));
//                
//                Long absOdometer = rs.getObject("absOdometer") == null ? null : (rs.getLong("absOdometer"));
//                Long odometer = rs.getObject("odometer") == null ? null : rs.getLong("odometer");
//                if (absOdometer != null) {
//                    item.setVehicleOdometer(Long.valueOf(absOdometer / 100l).intValue());
//                } else if (odometer != null) {
//                    Integer milesDriven = findMilesDriven(item.getVehicleID());
//                    item.setVehicleOdometer(Long.valueOf((odometer + milesDriven) / 100).intValue());
//                }
                
                return item;
            }
        }, params);
        Map<Long, MaintenanceReportItem> reportItemMapByNoteID = new HashMap<Long, MaintenanceReportItem>();
        for(MaintenanceReportItem item: queryResults) { //TODO: note that this merge might NOT be correct???? we DO want to see one line PER EVENT??? I believe
//            if(reportItemMap.containsKey(item.getVehicleID())) {
//                if(item.getThresholdOdo() != null) {
//                    reportItemMap.get(item.getVehicleID()).setThresholdOdo(item.getThresholdOdo());
//                }
//                if(item.getThresholdHours() != null) {
//                    reportItemMap.get(item.getVehicleID()).setThresholdHours(item.getThresholdHours());
//                }
//                if(item.getThresholdBase() != null) {
//                    reportItemMap.get(item.getVehicleID()).setThresholdBase(item.getThresholdBase());
//                }
//                if(item.getThresholdVoltage() != null) {
//                    reportItemMap.get(item.getVehicleID()).setThresholdVoltage(item.getThresholdVoltage());
//                }
//                if(item.getThresholdDpfFlowRate() != null) {
//                    reportItemMap.get(item.getVehicleID()).setThresholdDpfFlowRate(item.getThresholdDpfFlowRate());
//                }
//                if(item.getThresholdEngineTemp() != null) {
//                    reportItemMap.get(item.getVehicleID()).setThresholdEngineTemp(item.getThresholdEngineTemp());
//                }
//                if(item.getThresholdOilPressure() != null) {
//                    reportItemMap.get(item.getVehicleID()).setThresholdOilPressure(item.getThresholdOilPressure());   
//                }
//                if(item.getThresholdTransmissionTemp() != null) {
//                    reportItemMap.get(item.getVehicleID()).setThresholdTransmissionTemp(item.getThresholdTransmissionTemp());
//                }
//            }else {
                reportItemMapByNoteID.put(item.getNoteID(), item);
            //}
        }
        List<MaintenanceReportItem> odoHoursItems = findVehiclesWithThreshold(groupIDs);
        Map<Integer, MaintenanceReportItem> odoHoursMapByVehicleID = new HashMap<Integer, MaintenanceReportItem>();
        for(MaintenanceReportItem item : odoHoursItems) {
            odoHoursMapByVehicleID.put(item.getVehicleID(), item);
        }
        
        List<MaintenanceReportItem> resultsItems = new ArrayList<MaintenanceReportItem>();
        for(Long noteID : reportItemMapByNoteID.keySet()) {
            Integer vehicleID = reportItemMapByNoteID.get(noteID).getVehicleID();
            if(odoHoursMapByVehicleID.containsKey(vehicleID)) {
                if(odoHoursMapByVehicleID.get(vehicleID) != null) {
                    if(odoHoursMapByVehicleID.get(vehicleID).getVehicleEngineHours() != null) {
                        reportItemMapByNoteID.get(noteID).setVehicleEngineHours(odoHoursMapByVehicleID.get(vehicleID).getVehicleEngineHours());
                    }
                    if(odoHoursMapByVehicleID.get(vehicleID).getVehicleOdometer() != null) {
                        reportItemMapByNoteID.get(noteID).setVehicleOdometer(odoHoursMapByVehicleID.get(vehicleID).getVehicleOdometer());
                    }
                }
            }
            resultsItems.add(reportItemMapByNoteID.get(noteID));
        }
        return resultsItems;
    }
    @Override
    public Integer findMilesDriven(Integer vehicleID) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("vehicleID", vehicleID);
        
        Integer miles = getSimpleJdbcTemplate().queryForInt(MILES_DRIVEN, params);
        
        return miles == null ? 0 : miles;
    }
    
    @Override
    public Map<Integer , Integer > findMilesDriven(Set<Integer> vehicleIDs){
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
    public Map<Integer, MaintenanceReportItem> findEngineHours(List<Integer> vehicleIDs){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("vehicleID_list", vehicleIDs);
        
        List<Map.Entry<Integer, MaintenanceReportItem>> resultEntries = getSimpleJdbcTemplate().query(ENGINE_HOURS_MULT_VEHICLE, new ParameterizedRowMapper<Map.Entry<Integer, MaintenanceReportItem>>() {
            @Override
            public Map.Entry<Integer, MaintenanceReportItem> mapRow(ResultSet rs, int rowNum) throws SQLException {
                Integer hours = rs.getInt("engineHours");
                Integer vehicleOdo = rs.getInt("mileage218");
                MaintenanceReportItem item = new MaintenanceReportItem();
                item.setVehicleEngineHours(hours);
                item.setVehicleOdometer(vehicleOdo);
                Map.Entry<Integer, MaintenanceReportItem> resultsMap = new AbstractMap.SimpleEntry<Integer, MaintenanceReportItem>(rs.getInt("vehicleID"), item);
                return resultsMap;
            }           
        } , params);
        Map<Integer, MaintenanceReportItem> resultsMap = new HashMap<Integer, MaintenanceReportItem>();
        for(Map.Entry<Integer, MaintenanceReportItem> entry: resultEntries) {
            resultsMap.put(entry.getKey(), entry.getValue());
        }
        return resultsMap;
    }
    
    @Override
    public Map<Integer, Integer> findBaseOdometer(List<Integer> vehicleIDs){
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
    public List<MaintenanceReportItem> findVehiclesWithThreshold(List<Integer> groupIDList) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID_list", groupIDList);
        
        List<MaintenanceReportItem> queryResults = getSimpleJdbcTemplate().query(VEHICLES_WITH_THRESHOLDS, new ParameterizedRowMapper<MaintenanceReportItem>() {
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
                
                item.setVehicleID(vehicleID);
                StringBuffer ymmString = new StringBuffer();
                ymmString.append(rs.getString("year") + " ");
                ymmString.append(rs.getString("make") + " ");
                ymmString.append(rs.getString("model") + " ");
                item.setYmmString(ymmString.toString());
                
                item.setVehicleOdometer(rs.getInt("aggOdo"));
//
//                if (absOdometer != null) {
//                    item.setVehicleOdometer(Long.valueOf(absOdometer / 100l).intValue());
//                } else if (odometer != null) {
//                    Integer milesDriven = getMilesDriven(item.getVehicleID());
//                    item.setVehicleOdometer(Long.valueOf((odometer + milesDriven) / 100).intValue());
//                }
                
                return item;
            }
        }, params);
        Map<Integer, MaintenanceReportItem> reportItemMap = new HashMap<Integer, MaintenanceReportItem>();
        for(MaintenanceReportItem item: queryResults) {
            if(reportItemMap.containsKey(item.getVehicleID())) {
                //merge vehicle settings!
                if(item.getThresholdOdo() != null) {
                    reportItemMap.get(item.getVehicleID()).setThresholdOdo(item.getThresholdOdo());
                }
                if(item.getThresholdHours() != null) {
                    reportItemMap.get(item.getVehicleID()).setThresholdHours(item.getThresholdHours());
                }
                if(item.getThresholdOdo() != null) {
                    reportItemMap.get(item.getVehicleID()).setThresholdOdo(item.getThresholdOdo());
                }
                
            }else {
                reportItemMap.put(item.getVehicleID(), item);
            }
        }
        List<MaintenanceReportItem> resultsItems = new ArrayList<MaintenanceReportItem>();
        for(Integer vehicleID : reportItemMap.keySet()) {
            resultsItems.add(reportItemMap.get(vehicleID));
        }
        return resultsItems;
        
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

    @Override
    public Map<Integer, Integer> findTiwiOdometer(Set<Integer> vehicleIDs) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("vehicleID_list", vehicleIDs);
        List<Map.Entry<Integer, Integer>> resultEntries = getSimpleJdbcTemplate().query(ODOMETER_TIWI_MULTI_VEHICLE, new ParameterizedRowMapper<Map.Entry<Integer, Integer>>() {
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

}

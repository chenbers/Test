package com.inthinc.pro.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.inthinc.pro.dao.util.GeoUtil;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.pagination.FilterOp;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableFilterField;

public class AdminVehicleJDBCDAO extends SimpleJdbcDaoSupport{
    private static String VEHICLE_COLUMNS_STRING = "";// " vehicleID, groupID, status, groupPath, modified, vtype, hos, dot, ifta, zonetype, odometer, absOdometer, weight, year, name, make, model, color, vin, license, stateID, warrantyStart, warranteStop, aggDate, newAggDate ";
    private static String PAGED_VEHICLE_COLUMNS_STRING = "";
    /**
     * Ordered map of columns where <String columnNameInDB, String messageKeyForDisplayOnSite?>
     */
    private static final LinkedHashMap<String, String> columnMap = new LinkedHashMap<String, String>();
    private static final Map<String, String> pagedColumnMap = new HashMap<String, String>();

    static {
        columnMap.put("vehicle.vehicleID", "vehicleID");
        columnMap.put("vehicle.groupID", "groupID");
        columnMap.put("vehicle.status", "status");
        columnMap.put("vehicle.groupPath ", "groupPath");
        columnMap.put("vehicle.modified", "modified");
        columnMap.put("vehicle.vtype", "vtype");
        columnMap.put("vehicle.hos", "hos");    //?
        columnMap.put("vehicle.dot", "dot");    //?
        columnMap.put("vehicle.ifta", "ifta");
        columnMap.put("vehicle.zonetype", "zonetype");  //?
        columnMap.put("vehicle.odometer", "odometer");
        columnMap.put("vehicle.absOdometer", "absOdometer");
        columnMap.put("vehicle.weight", "weight");
        columnMap.put("vehicle.year", "year");
        columnMap.put("vehicle.name", "name");
        columnMap.put("vehicle.make", "make");
        columnMap.put("vehicle.model", "model");
        columnMap.put("vehicle.color", "color");
        columnMap.put("vehicle.vin", "vin");
        columnMap.put("vehicle.license", "license");
        columnMap.put("vehicle.stateID", "state");
        columnMap.put("vehicle.warrantyStart", "warrantyStart");
        columnMap.put("vehicle.warrantyStop", "warranteStop");
        columnMap.put("vehicle.aggDate", "aggDate");
        columnMap.put("vehicle.newAggDate", "newAggDate");
        
        columnMap.put("vddlog.deviceID","deviceID");
        columnMap.put("vddlog.driverID","driverID");
        columnMap.put("lastLocVehicle.latitude","latitude");
        columnMap.put("lastLocVehicle.longitude","longitude");
        for(String key: columnMap.keySet()){
            VEHICLE_COLUMNS_STRING += " "+key+" ,";
        }
        //remove trailing comma if necessary
        if(VEHICLE_COLUMNS_STRING.endsWith(",")){ 
            VEHICLE_COLUMNS_STRING = VEHICLE_COLUMNS_STRING.substring(0, VEHICLE_COLUMNS_STRING.length()-2);
        }
        
        System.out.println("VEHICLE_COLUMNS_STRING: "+VEHICLE_COLUMNS_STRING);
        
        // these match the columns displayed in admin/vehicles
        pagedColumnMap.put("name", "v.name");
        pagedColumnMap.put("fullName", "CONCAT(p.first, ' ', p.last)");
        pagedColumnMap.put("driverID", "vdd.driverID");
        pagedColumnMap.put("groupID", "g.name");
        pagedColumnMap.put("year", "v.year");
        pagedColumnMap.put("make", "v.make");
        pagedColumnMap.put("model", "v.model");
        pagedColumnMap.put("color", "v.color");
        pagedColumnMap.put("vtype", "v.vType");
        pagedColumnMap.put("VIN", "v.vin");
        pagedColumnMap.put("weight", "v.weight");
        pagedColumnMap.put("license", "v.license");
        pagedColumnMap.put("state", "v.stateID");
        pagedColumnMap.put("status", "v.status");
        pagedColumnMap.put("deviceID", "d.name");
        pagedColumnMap.put("odometer", "v.odometer");
//        pagedColumnMap.put("ephone", "dv.ephone");    // comes from configurator so can't sort
        pagedColumnMap.put("DOT", "v.dot");    //?
        pagedColumnMap.put("IFTA", "v.ifta");
        pagedColumnMap.put("productType", "d.productVer");

        PAGED_VEHICLE_COLUMNS_STRING = 
                "v.vehicleID, v.groupID, v.status, v.name, v.make, v.model, v.year, v.color, v.vtype, v.vin, v.weight, v.license, v.stateID, v.odometer, v.ifta, v.absOdometer, "+
                "d.deviceID, d.acctID, d.status, d.name, d.imei, d.sim, d.serialNum, d.phone, d.activated, d.baseID, d.productVer, d.firmVer, d.witnessVer, d.emuMd5, d.mcmid, d.altImei," +
                "vdd.deviceID, vdd.driverID, CONCAT(p.first, ' ', p.last), g.name";
                
    };
    private static final String VEHICLE_PLUS_LASTLOC_SELECT_BY_ACCOUNT = //
            "SELECT " + VEHICLE_COLUMNS_STRING + " "+//
                    "FROM vehicle " + //
                    "LEFT OUTER JOIN lastLocVehicle ON (vehicle.vehicleID = lastLocVehicle.vehicleID) " + //
                    "LEFT OUTER JOIN groups ON (vehicle.groupID = groups.groupID) " + //
                    "LEFT OUTER JOIN vddlog ON (vehicle.vehicleID = vddlog.vehicleID) " + //
                    "WHERE groups.acctID = :acctID "; //

    private static final String PAGED_VEHICLE_SUFFIX = 
            "FROM vehicle v " + 
            "LEFT JOIN groups g USING (groupID) " + 
            "LEFT OUTER JOIN vddlog vdd ON (v.vehicleID = vdd.vehicleID and vdd.stop is null) " + 
            "LEFT OUTER JOIN device d ON (d.deviceID = vdd.deviceID and vdd.stop is null) " + 
            "LEFT OUTER JOIN driver dr ON (dr.driverID = vdd.driverID and vdd.stop is null) " + 
            "LEFT OUTER JOIN person p ON (dr.personID = p.personID) " + 
            "WHERE v.groupID in (:group_list) and v.status != 3"; 
    
    private static final String PAGED_VEHICLE_SELECT = 
            "SELECT " + PAGED_VEHICLE_COLUMNS_STRING + " "+ PAGED_VEHICLE_SUFFIX;

    private static final String PAGED_VEHICLE_COUNT = 
            "SELECT COUNT(*)  "+ PAGED_VEHICLE_SUFFIX;

    private static final String MILES_DRIVEN =
            "SELECT MAX(vs.endingOdometer) milesDriven FROM vehicleScoreByDay vs where vs.vehicleID = :vehicleID";


    public Integer getCount(List<Integer> groupIDs, List<TableFilterField> filters) {
        String vehicleCount = PAGED_VEHICLE_COUNT;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("group_list", groupIDs);
        vehicleCount = addFiltersToQuery(filters, vehicleCount, params);
        Integer cnt = getSimpleJdbcTemplate().queryForInt(vehicleCount, params);
        return cnt;
    }
    
    public Integer getMilesDriven(Integer vehicleID) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("vehicleID", vehicleID);
        
        Integer miles = getSimpleJdbcTemplate().queryForInt(MILES_DRIVEN, params);
        
        return miles == null ? 0 : miles;
    }
    
    private String addFiltersToQuery(final List<TableFilterField> filters,
            String queryStr, Map<String, Object> params) {
        if(filters != null && !filters.isEmpty()) {
            StringBuilder countFilter = new StringBuilder();
            for(TableFilterField filter : filters) {
                if(filter.getField() != null && pagedColumnMap.containsKey(filter.getField()) && filter.getFilter() != null ) {
                    String paramName = "filter_"+filter.getField();
                    if (filter.getFilter().toString().isEmpty())
                        continue;
                    if (filter.getFilterOp() == FilterOp.IN) {
                        countFilter.append(" AND " + pagedColumnMap.get(filter.getField()) + " in (:" + paramName + ")");
                        params.put(paramName, filter.getFilter());
                        
                    }
                    else if (filter.getFilterOp() == FilterOp.IN_OR_NULL) {
                        countFilter.append(" AND (" + pagedColumnMap.get(filter.getField()) + " in (:" + paramName + ") OR " + pagedColumnMap.get(filter.getField()) + " IS NULL)");
                        params.put(paramName, filter.getFilter());
                        
                    }
                    else {
                        countFilter.append(" AND " + pagedColumnMap.get(filter.getField()) + " LIKE :" + paramName);
                        params.put(paramName, "%"+filter.getFilter()+"%");
                    }
                    
                }
            }
            queryStr = queryStr + countFilter.toString();
        }
        return queryStr;
    }
    

    public List<Vehicle> getVehicles(List<Integer> groupIDs, PageParams pageParams) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("group_list", groupIDs);
        StringBuilder vehicleSelect = new StringBuilder();
        vehicleSelect.append(PAGED_VEHICLE_SELECT);
        
        /***FILTERING***/
        vehicleSelect = new StringBuilder(addFiltersToQuery(pageParams.getFilterList(), vehicleSelect.toString(), params));

        /***SORTING***/
        if(pageParams.getSort() != null && !pageParams.getSort().getField().isEmpty())
            vehicleSelect.append(" ORDER BY " + pagedColumnMap.get(pageParams.getSort().getField()) + " " + (pageParams.getSort().getOrder() == SortOrder.ASCENDING ? "ASC" : "DESC"));
        
        /***PAGING***/
        if(pageParams.getStartRow() != null && pageParams.getEndRow() != null)
            vehicleSelect.append(" LIMIT " + pageParams.getStartRow() + ", " + ((pageParams.getEndRow() - pageParams.getStartRow())+1) );
        
                
        List<Vehicle> vehicleList = getSimpleJdbcTemplate().query(vehicleSelect.toString(), pagedVehicleRowMapper, params);
        

        return vehicleList;
    }
    public List<Vehicle> findVehiclesByAccountWithinDistance(Integer accountID, Long distanceInMiles, LatLng location){
        List<Vehicle> results = new ArrayList<Vehicle>();
        List<VehiclePlusLastLoc> vehiclesPlusLastLoc = new ArrayList<VehiclePlusLastLoc>();

        Map<String, Object> args = new HashMap<String, Object>();
        args.put("acctID", accountID);
        System.out.println("simpleJdbcTemplate: " + getSimpleJdbcTemplate());
        for (String key : args.keySet()) {
            System.out.println(key + " " + args.get(key));
        }
        System.out.println(VEHICLE_PLUS_LASTLOC_SELECT_BY_ACCOUNT);
        vehiclesPlusLastLoc = getSimpleJdbcTemplate().query(VEHICLE_PLUS_LASTLOC_SELECT_BY_ACCOUNT, vehiclePlusLastLocRowMapper, args);
        
        for(VehiclePlusLastLoc vehicle: vehiclesPlusLastLoc){
            if(GeoUtil.distBetween(location, vehicle.getLastLoc(), MeasurementType.ENGLISH) <= distanceInMiles){
                results.add(vehicle);
            }
        }
        
        System.out.println("results" + results);
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
            Integer absOdometer = rs.getObject("v.absOdometer") == null ? null : (rs.getInt("v.absOdometer")/100);
            Integer odometer = rs.getObject("v.odometer") == null ? null : rs.getInt("v.odometer");
            if (absOdometer != null) {
                vehicle.setOdometer(absOdometer); 
            }
            else if (odometer != null) {
                Integer milesDriven = getMilesDriven(vehicle.getVehicleID()); 
                vehicle.setOdometer((odometer + milesDriven)/100);
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
                device.setFirmwareVersion(rs.getObject("d.firmVer") == null ? null : rs.getInt("d.firmVer"));
                device.setWitnessVersion(rs.getObject("d.witnessVer") == null ? null : rs.getInt("d.witnessVer"));
                device.setEmuMd5(rs.getString("d.emuMd5"));
                device.setMcmid(rs.getString("d.mcmid"));
                device.setAltimei(rs.getString("d.altImei"));
                device.setProductVersion(device.getProductVer() == null ? null : ProductType.getProductTypeFromVersion(device.getProductVer()));
                vehicle.setDevice(device);
                
            }
            
            return vehicle;
        }
    };


//    //TODO: used to be "static" make sure this is not a problem
    private static ParameterizedRowMapper<Vehicle> vehicleRowMapper = new ParameterizedRowMapper<Vehicle>() {
        @Override
        public Vehicle mapRow(ResultSet rs, int rowNum) throws SQLException {
            Vehicle vehicle = new Vehicle();
            //hazard.setHazardID(rs.getInt("hazardID"));
            vehicle.setColor(rs.getString("vehicle.color"));
            vehicle.setCreated(null); //TODO: not stored in DB?
            vehicle.setDeviceID(rs.getInt("vddlog.deviceID"));
            vehicle.setDriverID(rs.getInt("vddlog.driverID"));
            vehicle.setFullName(rs.getString("vehicle.name"));
            vehicle.setGroupID(rs.getInt("vehicle.groupID"));
            vehicle.setIfta(rs.getObject("vehicle.ifta") == null ? null : rs.getBoolean("vehicle.ifta"));  // review with jw
            vehicle.setLicense(rs.getString("vehicle.license"));
            vehicle.setMake(rs.getString("vehicle.make"));
            vehicle.setModel(rs.getString("vehicle.model"));
            vehicle.setModified(rs.getDate("vehicle.modified"));
            vehicle.setName(rs.getString("vehicle.name"));
            vehicle.setOdometer(rs.getObject("vehicle.odometer") == null ? null : rs.getInt("vehicle.odometer"));  // review with jw
            vehicle.setState(States.getStateById(rs.getInt("vehicle.stateID")));
            vehicle.setStatus(Status.valueOf(rs.getInt("vehicle.status")));
            vehicle.setVehicleID(rs.getInt("vehicle.vehicleID"));
            vehicle.setVIN(rs.getString("vehicle.vin"));
            vehicle.setVtype(VehicleType.valueOf(rs.getInt("vehicle.vtype")));
            vehicle.setWeight(rs.getInt("vehicle.weight"));
            vehicle.setYear(rs.getInt("vehicle.year"));
            return vehicle;
        }
    };
    private ParameterizedRowMapper<VehiclePlusLastLoc> vehiclePlusLastLocRowMapper = new ParameterizedRowMapper<VehiclePlusLastLoc>(){

        @Override
        public VehiclePlusLastLoc mapRow(ResultSet rs, int rowNum) throws SQLException {
            // TODO: try to do it like THIS (DRY)
            //VehiclePlusLastLoc vehicle = (VehiclePlusLastLoc) vehicleRowMapper.mapRow(rs, rowNum);
            
            VehiclePlusLastLoc vehicle = new VehiclePlusLastLoc();
            //hazard.setHazardID(rs.getInt("hazardID"));
            vehicle.setColor(rs.getString("vehicle.color"));
            vehicle.setCreated(null); //TODO: not stored in DB?
            vehicle.setDeviceID(rs.getInt("vddlog.deviceID"));
            vehicle.setDriverID(rs.getInt("vddlog.driverID"));
            vehicle.setFullName(rs.getString("vehicle.name"));
            vehicle.setGroupID(rs.getInt("vehicle.groupID"));
            vehicle.setIfta(rs.getBoolean("vehicle.ifta"));
            vehicle.setLicense(rs.getString("vehicle.license"));
            vehicle.setMake(rs.getString("vehicle.make"));
            vehicle.setModel(rs.getString("vehicle.model"));
            vehicle.setModified(rs.getDate("vehicle.modified"));
            vehicle.setName(rs.getString("vehicle.name"));
            vehicle.setOdometer(rs.getInt("vehicle.odometer"));
            vehicle.setState(States.getStateById(rs.getInt("vehicle.stateID")));
            vehicle.setStatus(Status.valueOf(rs.getInt("vehicle.status")));
            vehicle.setVehicleID(rs.getInt("vehicle.vehicleID"));
            vehicle.setVIN(rs.getString("vehicle.vin"));
            vehicle.setVtype(VehicleType.valueOf(rs.getInt("vehicle.vtype")));
            vehicle.setWeight(rs.getInt("vehicle.weight"));
            vehicle.setYear(rs.getInt("vehicle.year"));
            vehicle.setLastLoc(new LatLng(rs.getDouble("lastLocVehicle.latitude"), rs.getDouble("lastLocVehicle.longitude")));
            return vehicle;
        }
        
    };
    
    
    private class VehiclePlusLastLoc extends Vehicle {
        private LatLng lastLoc;

        public LatLng getLastLoc() {
            return lastLoc;
        }

        public void setLastLoc(LatLng lastLoc) {
            this.lastLoc = lastLoc;
        }
        
    }
}

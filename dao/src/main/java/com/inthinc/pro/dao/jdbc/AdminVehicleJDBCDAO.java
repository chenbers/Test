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
import com.inthinc.pro.model.Hazard;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;

public class AdminVehicleJDBCDAO extends SimpleJdbcDaoSupport{
    private static String VEHICLE_COLUMNS_STRING = " vehicleID, groupID, status, groupPath, modified, vtype, hos, dot, ifta, zonetype, odometer, absOdometer weight, year, name, make, model, color, vin, license, stateID, warrantyStart, warranteStop, aggDate, newAggDate ";
    /**
     * Ordered map of columns where <String columnNameInDB, String messageKeyForDisplayOnSite?>
     */
    private static final LinkedHashMap<String, String> columnMap = new LinkedHashMap<String, String>();

    static {
        columnMap.put("vehicle.vehicleID", "vehicleID");
        columnMap.put("vehicle.groupID", "groupID");
        columnMap.put("vehicle.status", "status");
        columnMap.put("vehicle.groupPath ", "groupPath");
        columnMap.put("vehicle.modified", "modified");
        columnMap.put("vehicle.vtype", "vtype");
        columnMap.put("vehicle.hos", "hos");
        columnMap.put("vehicle.dot", "dot");
        columnMap.put("vehicle.ifta", "ifta");
        columnMap.put("vehicle.zonetype", "zonetype");
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
        columnMap.put("vehicle.warranteStop", "warranteStop");
        columnMap.put("vehicle.aggDate", "aggDate");
        columnMap.put("vehicle.newAggDate", "newAggDate");
        
        columnMap.put("vddlog.deviceID","deviceID");
        columnMap.put("vddlog.driverID","driverID");
        columnMap.put("lastLocVehicle.latitude","latitude");
        columnMap.put("lastLocVehicle.longitude","longitude");
        
        for(String key: columnMap.keySet()){
            VEHICLE_COLUMNS_STRING += " "+key+" , ";
        }
        //remove trailing comma if necessary
        if(VEHICLE_COLUMNS_STRING.endsWith(",")){ 
            VEHICLE_COLUMNS_STRING = VEHICLE_COLUMNS_STRING.substring(0, VEHICLE_COLUMNS_STRING.length()-2);
        }
        
        System.out.println("VEHICLE_COLUMNS_STRING: "+VEHICLE_COLUMNS_STRING);
    };
    private static final String VEHICLE_PLUS_LASTLOC_SELECT_BY_ACCOUNT = //
            "SELECT " + VEHICLE_COLUMNS_STRING + " "+//
                    "FROM vehicle " + //
                    "LEFT OUTER JOIN lastLocVehicle ON (vehicle.vehicleID = lastLocVehicle.vehicleID) " + //
                    "LEFT OUTER JOIN groups ON (vehicle.groupID = groups.groupID) " + //
                    "LEFT OUTER JOIN vddlog ON (vehicle.vehicleID = vddlog.vehicleID) " + //
                    "WHERE groups.acctID = :acctID "; //
    public Integer getCount(List<Integer> groupIDs, List<TableFilterField> filters) {
        return 0;
    }
    
    
    public List<Vehicle> getVehicles(List<Integer> groupIDs, PageParams pageParams) {
        
        return new ArrayList<Vehicle>();
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


//    //TODO: used to be "static" make sure this is not a problem
    private static ParameterizedRowMapper<Vehicle> vehicleRowMapper = new ParameterizedRowMapper<Vehicle>() {
        @Override
        public Vehicle mapRow(ResultSet rs, int rowNum) throws SQLException {
            Vehicle vehicle = new Vehicle();
            //hazard.setHazardID(rs.getInt("hazardID"));
            vehicle.setColor(rs.getString(columnMap.get("vehicle.color")));
            vehicle.setCreated(null); //TODO: not stored in DB?
            vehicle.setDeviceID(rs.getInt(columnMap.get("vddlog.deviceID")));
            vehicle.setDriverID(rs.getInt(columnMap.get("vddlog.driverID")));
            vehicle.setFullName(rs.getString(columnMap.get("vehicle.name")));
            vehicle.setGroupID(rs.getInt(columnMap.get("vehicle.groupID")));
            vehicle.setIfta(rs.getBoolean(columnMap.get("vehicle.ifta")));
            vehicle.setLicense(rs.getString(columnMap.get("vehicle.license")));
            vehicle.setMake(rs.getString(columnMap.get("vehicle.make")));
            vehicle.setModel(rs.getString(columnMap.get("vehicle.model")));
            vehicle.setModified(rs.getDate(columnMap.get("vehicle.modified")));
            vehicle.setName(rs.getString(columnMap.get("vehicle.name")));
            vehicle.setOdometer(rs.getInt(columnMap.get("vehicle.odometer")));
            vehicle.setState(States.getStateById(rs.getInt(columnMap.get("vehicle.stateID"))));
            vehicle.setStatus(Status.valueOf(rs.getInt(columnMap.get("vehicle.status"))));
            vehicle.setVehicleID(rs.getInt(columnMap.get("vehicle.vehicleID")));
            vehicle.setVIN(rs.getString(columnMap.get("vehicle.vin")));
            vehicle.setVtype(VehicleType.valueOf(rs.getInt(columnMap.get("vehicle.vtype"))));
            vehicle.setWeight(rs.getInt(columnMap.get("vehicle.weight")));
            vehicle.setYear(rs.getInt(columnMap.get("vehicle.year")));
            return vehicle;
        }
    };
    private ParameterizedRowMapper<VehiclePlusLastLoc> vehiclePlusLastLocRowMapper = new ParameterizedRowMapper<VehiclePlusLastLoc>(){

        @Override
        public VehiclePlusLastLoc mapRow(ResultSet rs, int rowNum) throws SQLException {
            // TODO Auto-generated method stub
            VehiclePlusLastLoc vehicle = (VehiclePlusLastLoc) vehicleRowMapper.mapRow(rs, rowNum);
            vehicle.setLastLoc(new LatLng(rs.getDouble(columnMap.get("lastLocVehicle.latitude")), rs.getDouble(columnMap.get("lastLocVehicle.longitude"))));
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

package com.inthinc.pro.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.inthinc.pro.model.LatLng;
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
        columnMap.put("hazardID", "hazardID");
        columnMap.put("vehicleID", "vehicleID");
        columnMap.put("groupID", "groupID");
        columnMap.put("status", "status");
        columnMap.put("groupPath ", "groupPath");
        columnMap.put("modified", "modified");
        columnMap.put("vtype", "vtype");
        columnMap.put("hos", "hos");
        columnMap.put("dot", "dot");
        columnMap.put("ifta", "ifta");
        columnMap.put("zonetype", "zonetype");
        columnMap.put("odometer", "odometer");
        columnMap.put("absOdometer", "absOdometer");
        columnMap.put("weight", "weight");
        columnMap.put("year", "year");
        columnMap.put("name", "name");
        columnMap.put("make", "make");
        columnMap.put("model", "model");
        columnMap.put("color", "color");
        columnMap.put("vin", "vin");
        columnMap.put("license", "license");
        columnMap.put("stateID", "state");
        columnMap.put("warrantyStart", "warrantyStart");
        columnMap.put("warranteStop", "warranteStop");
        columnMap.put("aggDate", "aggDate");
        columnMap.put("newAggDate", "newAggDate");
        
        
        
        for(String key: columnMap.keySet()){
            VEHICLE_COLUMNS_STRING += " "+key+" , ";
        }
        //remove trailing comma if necessary
        if(VEHICLE_COLUMNS_STRING.endsWith(",")){ 
            VEHICLE_COLUMNS_STRING = VEHICLE_COLUMNS_STRING.substring(0, VEHICLE_COLUMNS_STRING.length()-2);
        }
        
        System.out.println("VEHICLE_COLUMNS_STRING: "+VEHICLE_COLUMNS_STRING);
    };
    public Integer getCount(List<Integer> groupIDs, List<TableFilterField> filters) {
        return 0;
    }
    
    
    public List<Vehicle> getVehicles(List<Integer> groupIDs, PageParams pageParams) {
        
        return new ArrayList<Vehicle>();
    }
    public List<Vehicle> getVehiclesByAccountWithinDistance(Integer accountID, Long distance, LatLng location){
        List<Vehicle> results = new ArrayList<Vehicle>();
        
        return results;
    }
    
    private static ParameterizedRowMapper<Vehicle> vehicleRowMapper = new ParameterizedRowMapper<Vehicle>() {
        @Override
        public Vehicle mapRow(ResultSet rs, int rowNum) throws SQLException {
            Vehicle vehicle = new Vehicle();
            //hazard.setHazardID(rs.getInt("hazardID"));
            vehicle.setColor(rs.getString(columnMap.get("color")));
            vehicle.setCreated(null); //TODO: not stored in DB?
            vehicle.setDeviceID(rs.getInt(""));//TODO: not available by only querying vehicle table
            vehicle.setDriverID(rs.getInt(""));//TODO: not available by only querying vehicle table
            vehicle.setFullName(rs.getString(columnMap.get("name")));
            vehicle.setGroupID(rs.getInt(columnMap.get("groupID")));
            vehicle.setIfta(rs.getBoolean(columnMap.get("ifta")));
            vehicle.setLicense(rs.getString(columnMap.get("license")));
            vehicle.setMake(rs.getString(columnMap.get("make")));
            vehicle.setModel(rs.getString(columnMap.get("model")));
            vehicle.setModified(rs.getDate(columnMap.get("modified")));
            vehicle.setName(rs.getString(columnMap.get("name")));
            vehicle.setOdometer(rs.getInt(columnMap.get("odometer")));
            vehicle.setState(States.getStateById(rs.getInt(columnMap.get(""))));//TODO: not available by only querying vehicle table
            vehicle.setStatus(Status.valueOf(rs.getInt(columnMap.get("status"))));
            vehicle.setVehicleID(rs.getInt(columnMap.get("vehicleID")));
            vehicle.setVIN(rs.getString(columnMap.get("vin")));
            vehicle.setVtype(VehicleType.valueOf(rs.getInt(columnMap.get("vtype"))));
            vehicle.setWeight(rs.getInt(columnMap.get("weight")));
            vehicle.setYear(rs.getInt(columnMap.get("year")));
            return vehicle;
        }
    };
}

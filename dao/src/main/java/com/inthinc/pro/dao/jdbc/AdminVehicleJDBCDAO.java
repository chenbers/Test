package com.inthinc.pro.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;

public class AdminVehicleJDBCDAO extends SimpleJdbcDaoSupport{
    
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
            vehicle.setColor(rs.getString("color"));
            //vehicle.setCreated(created);
            return vehicle;
        }
    };
}

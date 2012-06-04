package com.inthinc.pro.dao.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

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

}

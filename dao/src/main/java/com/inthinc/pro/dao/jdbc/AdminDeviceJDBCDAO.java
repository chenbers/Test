package com.inthinc.pro.dao.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;

public class AdminDeviceJDBCDAO extends SimpleJdbcDaoSupport{
    
    public Integer getCount(Integer acctID, List<TableFilterField> filters) {
        return 0;
    }
    
    
    public List<Device> getDevices(Integer acctID, PageParams pageParams) {
        
        return new ArrayList<Device>();
    }
}

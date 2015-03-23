package com.inthinc.pro.dao.jdbc;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.inthinc.pro.dao.FipsDAO;
import com.inthinc.pro.model.LatLng;

public class FipsJDBCDAO extends SimpleJdbcDaoSupport implements FipsDAO {
    
    @Override
    public String getClosestTown(LatLng latLng) {
        String query = "select getClosestTown(?, ?)";
        Object result = getJdbcTemplate().queryForObject(query, new Object[] { latLng.getLng(), latLng.getLat() }, java.lang.String.class);
        return result == null ? null : result.toString();
    }

    
}

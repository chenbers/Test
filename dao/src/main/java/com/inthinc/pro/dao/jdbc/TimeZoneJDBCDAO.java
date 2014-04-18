package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.TimeZoneDAO;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.SupportedTimeZone;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Infrasoft02 on 4/18/2014.
 */
public class TimeZoneJDBCDAO extends SimpleJdbcDaoSupport implements TimeZoneDAO {

    private static final String FIND_TIMEZONE_BY_ID =" SELECT * FROM timezone where tzID = :tzID";

    private static final String DEL_TIMEZONE_BY_ID = "DELETE FROM timezone WHERE tzID = ?";


    private ParameterizedRowMapper<SupportedTimeZone> timeZoneParameterizedRowMapper = new ParameterizedRowMapper<SupportedTimeZone>() {
        @Override
        public SupportedTimeZone mapRow(ResultSet rs, int rowNum) throws SQLException {
            SupportedTimeZone supportTimeZone = new SupportedTimeZone();


            supportTimeZone.setTzID(rs.getInt("tzID"));
            supportTimeZone.setTzName(rs.getString("tzName"));


            return supportTimeZone;
        }
    };


    @Override
    public List<SupportedTimeZone> getSupportedTimeZones() {
        return null;
    }

    @Override
    public SupportedTimeZone findByID(Integer tzID) {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("tzID", tzID);
        StringBuilder findAddress = new StringBuilder(FIND_TIMEZONE_BY_ID);

        return getSimpleJdbcTemplate().queryForObject(findAddress.toString(), timeZoneParameterizedRowMapper, args);
    }

    @Override
    public Integer create(Integer integer, SupportedTimeZone entity) {
        return null;
    }

    @Override
    public Integer update(SupportedTimeZone entity) {
        return null;
    }

    @Override
    public Integer deleteByID(Integer tzID) {
        return getJdbcTemplate().update(DEL_TIMEZONE_BY_ID, new Object[]{tzID});
    }
}

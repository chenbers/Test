package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.TimeZoneDAO;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.SupportedTimeZone;
import com.inthinc.pro.model.silo.SiloDef;
import com.mysql.jdbc.Statement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Infrasoft02 on 4/18/2014.
 */
public class TimeZoneJDBCDAO extends SimpleJdbcDaoSupport implements TimeZoneDAO {

    private static final String FIND_TIMEZONE_BY_ID =" SELECT * FROM timezone where tzID = :tzID";

    private static final String DEL_TIMEZONE_BY_ID = " DELETE FROM timezone WHERE tzID = ?";

    private static final String INSERT_TIMEZONE_ADDRESS = " INSERT INTO timezone (tzName, enable) VALUES (?, ?);";

    private static final String UPDATE_TIMEZONE_ADDRESS = " UPDATE timezone set tzName=?, enable=? where tzID = ?";


    private ParameterizedRowMapper<SupportedTimeZone> timeZoneParameterizedRowMapper = new ParameterizedRowMapper<SupportedTimeZone>() {
        @Override
        public SupportedTimeZone mapRow(ResultSet rs, int rowNum) throws SQLException {
            SupportedTimeZone supportTimeZone = new SupportedTimeZone();

            supportTimeZone.setTzID(rs.getInt("tzID"));
            supportTimeZone.setTzName(rs.getString("tzName"));
            supportTimeZone.setEnabled(rs.getInt("enabled"));

            return supportTimeZone;
        }
    };


    @Override
    public List<SupportedTimeZone> getSupportedTimeZones() {
        List<SupportedTimeZone> suportedTimeZone = new ArrayList<SupportedTimeZone>();

        suportedTimeZone.add(new SupportedTimeZone(535, "US/Alaska"));
        suportedTimeZone.add(new SupportedTimeZone(536, "US/Aleutian"));
        suportedTimeZone.add(new SupportedTimeZone(537, "US/Arizona"));
        suportedTimeZone.add(new SupportedTimeZone(538, "US/Central"));
        suportedTimeZone.add(new SupportedTimeZone(539, "US/East-Indiana"));
        suportedTimeZone.add(new SupportedTimeZone(540, "US/Eastern"));
        suportedTimeZone.add(new SupportedTimeZone(541, "US/Hawaii"));
        suportedTimeZone.add(new SupportedTimeZone(542, "US/Indiana-Starke"));
        suportedTimeZone.add(new SupportedTimeZone(543, "US/Michigan"));
        suportedTimeZone.add(new SupportedTimeZone(544, "US/Mountain"));
        suportedTimeZone.add(new SupportedTimeZone(545, "US/Pacific"));
        suportedTimeZone.add(new SupportedTimeZone(546, "US/Samoa"));
        suportedTimeZone.add(new SupportedTimeZone(547, "UTC"));

        return suportedTimeZone;
    }

    @Override
    public SupportedTimeZone findByID(Integer tzID) {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("tzID", tzID);
        StringBuilder findAddress = new StringBuilder(FIND_TIMEZONE_BY_ID);

        return getSimpleJdbcTemplate().queryForObject(findAddress.toString(), timeZoneParameterizedRowMapper, args);
    }

    @Override
    public Integer create(Integer integer, final SupportedTimeZone entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_TIMEZONE_ADDRESS, Statement.RETURN_GENERATED_KEYS);

                ps.setString(1,entity.getTzName());

                if (entity.getEnabled() == null) {
                    ps.setNull(2, Types.NULL);
                } else {
                    ps.setInt(2, entity.getEnabled());
                }

                logger.debug(ps.toString());
                return ps;
            }

        };
        jdbcTemplate.update(psc, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public Integer update(final SupportedTimeZone entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(UPDATE_TIMEZONE_ADDRESS);

                ps.setString(1, entity.getTzName());

                if (entity.getEnabled() == null) {
                    ps.setNull(2, Types.NULL);
                } else {
                    ps.setInt(2, entity.getEnabled());
                }

                logger.debug(ps.toString());
                return ps;
            }
        };
        jdbcTemplate.update(psc);
        return entity.getTzID();
    }

    @Override
    public Integer deleteByID(Integer tzID) {
        return getJdbcTemplate().update(DEL_TIMEZONE_BY_ID, new Object[]{tzID});
    }
}

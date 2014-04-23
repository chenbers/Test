package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.TimeZoneDAO;
import com.inthinc.pro.model.SupportedTimeZone;
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

import static com.inthinc.pro.dao.jdbc.GenericJDBCDAO.close;


public class TimeZoneJDBCDAO extends SimpleJdbcDaoSupport implements TimeZoneDAO {

    private static final String FIND_TIMEZONE_BY_ID =" SELECT * FROM timezone where tzID = :tzID";

    private static final String SUPORTED_TIMEZONE = " SELECT * FROM timezone WHERE enabled=1";

    private static final String DEL_TIMEZONE_BY_ID = " DELETE FROM timezone WHERE tzID = ?";

    private static final String INSERT_TIMEZONE = " INSERT INTO timezone (tzName, enable) VALUES (?, ?);";

    private static final String UPDATE_TIMEZONE = " UPDATE timezone set tzName=?, enable=? where tzID = ?";


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

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(SUPORTED_TIMEZONE);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                SupportedTimeZone stz = new SupportedTimeZone();
                stz.setTzID(resultSet.getInt("tzID"));
                stz.setTzName(resultSet.getString("tzName"));
                stz.setEnabled(resultSet.getInt("enabled"));
                suportedTimeZone.add(stz);
            }

        }   // end try
        catch (SQLException e) {
            throw new ProDAOException(statement.toString(), e);
        }
        finally {
            close(resultSet);
            close(statement);
            close(conn);
        }

        return suportedTimeZone;
    }

    @Override
    public SupportedTimeZone findByID(Integer tzID) {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("tzID", tzID);
        StringBuilder findTimeZone = new StringBuilder(FIND_TIMEZONE_BY_ID);

        return getSimpleJdbcTemplate().queryForObject(findTimeZone.toString(), timeZoneParameterizedRowMapper, args);
    }

    @Override
    public Integer create(Integer integer, final SupportedTimeZone entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_TIMEZONE, Statement.RETURN_GENERATED_KEYS);

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
                PreparedStatement ps = con.prepareStatement(UPDATE_TIMEZONE);

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

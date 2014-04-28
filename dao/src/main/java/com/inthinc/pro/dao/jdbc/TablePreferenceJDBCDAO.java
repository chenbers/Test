package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.TablePreferenceDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.TablePreference;
import com.inthinc.pro.model.TableType;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dao for table preferences
 */
public class TablePreferenceJDBCDAO extends SimpleJdbcDaoSupport implements TablePreferenceDAO {

    private static final String TABLE_PREF = "SELECT * FROM tablePref ";
    private static final String FIND_TABLE_PREF_BY_ID = TABLE_PREF + " WHERE tablePrefID=:tablePrefID";
    private static final String DELETE_TABLE_PREF_BY_ID = "DELETE FROM tablePref WHERE tablePrefID=?";
    private static final String FIND_TABLE_PREF_BY_USER_ID = TABLE_PREF + "WHERE userID=:userID";
    private static final String INSERT_INTO_TABLE_PREF = "INSERT INTO tablePref(userID,tableType,flags)VALUES(?,?,?)";
    private static final String UPDATE_TABLE_PREF = "UPDATE tablePref SET userID = ?, tableType = ?, flags = ? WHERE tablePrefID = ?";


    private ParameterizedRowMapper<TablePreference> tablePreferenceParameterizedRowMapper = new ParameterizedRowMapper<TablePreference>() {
        @Override
        public TablePreference mapRow(ResultSet rs, int rowNum) throws SQLException {
            TablePreference tablePreference = new TablePreference();

            tablePreference.setTablePrefID(rs.getInt("tablePrefId"));
            tablePreference.setUserID(rs.getInt("userID"));
            tablePreference.setFlags(rs.getString("flags"));
            tablePreference.setTableType(TableType.valueOf(rs.getInt("tableType")));
            List<Boolean> visible = new ArrayList<Boolean>();

            String list = rs.getString("flags");
            for (int i = 0; i < list.length(); i++)
            {
                visible.add(list.charAt(i) == '1');
            }

            tablePreference.setVisible(visible);

            return tablePreference;
        }
    };

    @Override
    public List<TablePreference> getTablePreferencesByUserID(Integer userID) {
        try {
            Map<String, Object> args = new HashMap<String, Object>();
            args.put("userID", userID);
            StringBuilder findTablePrefByUserId = new StringBuilder(FIND_TABLE_PREF_BY_USER_ID);

            List<TablePreference> result = getSimpleJdbcTemplate().query(findTablePrefByUserId.toString(), tablePreferenceParameterizedRowMapper, args);

            return result;

        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public TablePreference findByID(Integer tablePrefID) {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("tablePrefID", tablePrefID);
        StringBuilder findTablePref = new StringBuilder(FIND_TABLE_PREF_BY_ID);

        return getSimpleJdbcTemplate().queryForObject(findTablePref.toString(), tablePreferenceParameterizedRowMapper, args);
    }

    @Override
    public Integer create(Integer integer, final TablePreference entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_INTO_TABLE_PREF, Statement.RETURN_GENERATED_KEYS);

                ps.setInt(1, entity.getUserID());

                if (entity.getTableType() == null) {
                    ps.setNull(2, Types.NULL);
                } else {
                    ps.setInt(2, entity.getTableType().getCode());
                }

                if (entity.getFlags() == null) {
                    ps.setNull(3, Types.NULL);
                } else {
                    ps.setString(3, entity.getFlags());
                }


                logger.debug(ps.toString());
                return ps;
            }

        };
        jdbcTemplate.update(psc, keyHolder);
        return keyHolder.getKey().intValue();

    }

    @Override
    public Integer update(final TablePreference entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(UPDATE_TABLE_PREF);
                ps.setInt(1, entity.getTablePrefID());
                ps.setInt(2, entity.getUserID());

                if (entity.getTableType() == null) {
                    ps.setNull(3, Types.NULL);
                } else {
                    ps.setInt(3, entity.getTableType().getCode());
                }

                if (entity.getFlags() == null) {
                    ps.setNull(4, Types.NULL);
                } else {
                    ps.setString(4, entity.getFlags());
                }

                logger.debug(ps.toString());
                return ps;
            }
        };
        jdbcTemplate.update(psc);
        return entity.getTablePrefID();
    }

    @Override
    public Integer deleteByID(Integer tablePrefID) {
        return getJdbcTemplate().update(DELETE_TABLE_PREF_BY_ID, new Object[]{tablePrefID});
    }
}

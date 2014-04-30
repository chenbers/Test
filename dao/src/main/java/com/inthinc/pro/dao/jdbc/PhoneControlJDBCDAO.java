package com.inthinc.pro.dao.jdbc;


import com.inthinc.pro.dao.PhoneControlDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.Cellblock;
import com.inthinc.pro.model.phone.CellProviderType;
import com.inthinc.pro.model.phone.CellStatusType;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhoneControlJDBCDAO extends SimpleJdbcDaoSupport implements PhoneControlDAO {

    private static final String PHONE_CONTROL = "SELECT * FROM cellblock";
    private static final String FIND_PHONE_CONTROL_BY_ID = PHONE_CONTROL + " WHERE driverID=:driverID";
    private static final String FIND_BY_PHONE_NUMBER = PHONE_CONTROL + " WHERE cellPhone like ?";
    private static final String FIND_DRIVERS_WITH_DISABLED_PHONES = PHONE_CONTROL + " WHERE cellStatus=0";
    private static final String FIND_PHONE_CONTROL_BY_ACC_ID = PHONE_CONTROL + " WHERE acctID=:acctID";
    private static final String INSERT_INTO_PHONE_CONTROL = "INSERT INTO cellblock(driverID, acctID, cellStatus, providerType, providerUser, providerPass, cellPhone)VALUES(?, ?, ?, ?, ? , ?, ?)";
    private static final String UPDATE_PHONE_CONTROL = "UPDATE cellblock SET driverID = ?, cellStatus = ?, providerType = ?, providerUser = ?, providerPass = ?, cellPhone = ? WHERE driverID = ?";
    private static final String DELETE_PHONE_CONTROL_BY_ID = "DELETE FROM cellblock WHERE driverID=?";

    private ParameterizedRowMapper<Cellblock> phoneControlParameterizedRowMapper = new ParameterizedRowMapper<Cellblock>() {
        @Override
        public Cellblock mapRow(ResultSet rs, int rowNum) throws SQLException {

            Cellblock tablePreference = new Cellblock();

            tablePreference.setDriverID(rs.getInt("driverID"));
            tablePreference.setAcctID(rs.getInt("acctID"));
            tablePreference.setCellStatus(CellStatusType.valueOf(rs.getInt("cellStatus")));
            tablePreference.setProvider(CellProviderType.valueOf(rs.getInt("providerType")));
            tablePreference.setProviderUser(rs.getString("providerUser"));
            tablePreference.setProviderPassword(rs.getString("providerPass"));
            tablePreference.setCellPhone(rs.getString("cellPhone"));

            return tablePreference;
        }
    };

    @Override
    public Cellblock findByPhoneNumber(String cellPhone) {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("cellPhone", cellPhone);
        StringBuilder findByPhoneNumber = new StringBuilder(FIND_BY_PHONE_NUMBER);

        return getSimpleJdbcTemplate().queryForObject(findByPhoneNumber.toString(), phoneControlParameterizedRowMapper, args);
    }

    @Override
    public List<Cellblock> getDriversWithDisabledPhones() {
        try {
            StringBuilder findDriversWithDisabledPhones = new StringBuilder(FIND_DRIVERS_WITH_DISABLED_PHONES);

            List<Cellblock> result = getSimpleJdbcTemplate().query(findDriversWithDisabledPhones.toString(), phoneControlParameterizedRowMapper);

            return result;

        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Cellblock> getCellblocksByAcctID(Integer acctID) {

        try {
            Map<String, Object> args = new HashMap<String, Object>();
            args.put("acctID", acctID);
            StringBuilder findPhoneControlByAcctId = new StringBuilder(FIND_PHONE_CONTROL_BY_ACC_ID);

            List<Cellblock> result = getSimpleJdbcTemplate().query(findPhoneControlByAcctId.toString(), phoneControlParameterizedRowMapper, args);

            return result;

        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }

    }

    @Override
    public Cellblock findByID(Integer driverID) {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("driverID", driverID);
        StringBuilder findByDriverID = new StringBuilder(FIND_PHONE_CONTROL_BY_ID);

        return getSimpleJdbcTemplate().queryForObject(findByDriverID.toString(), phoneControlParameterizedRowMapper, args);
    }

    @Override
    public Integer create(Integer integer, final Cellblock entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final int id;

        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_INTO_PHONE_CONTROL, Statement.RETURN_GENERATED_KEYS);

                ps.setInt(1, entity.getDriverID());
                ps.setInt(2, entity.getAcctID());

                if (entity.getCellStatus() == null || entity.getCellStatus().getCode() == null) {
                    ps.setNull(3, Types.NULL);
                } else {
                    ps.setInt(3, entity.getCellStatus().getCode());
                }

                if (entity.getProvider() == null || entity.getProvider().getCode() == null) {
                    ps.setNull(4, Types.NULL);
                } else {
                    ps.setInt(4, entity.getProvider().getCode());
                }

                if (entity.getProviderUser() == null ||entity.getProviderUser().trim().isEmpty() ) {
                    ps.setNull(5, Types.NULL);
                } else {
                    ps.setString(5, entity.getProviderUser());
                }
                if (entity.getProviderPassword() == null || entity.getProviderPassword().trim().isEmpty()) {
                    ps.setNull(6, Types.NULL);
                } else {
                    ps.setString(6, entity.getProviderPassword());
                }
                if (entity.getCellPhone() == null ||entity.getCellPhone().trim().isEmpty()) {
                    ps.setNull(7, Types.NULL);
                } else {
                    ps.setString(7, entity.getCellPhone());
                }
                logger.debug(ps.toString());
                return ps;
            }

        };
        jdbcTemplate.update(psc, keyHolder);
        return entity.getDriverID();

    }

    @Override
    public Integer update(final Cellblock entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(UPDATE_PHONE_CONTROL, Statement.RETURN_GENERATED_KEYS);

                ps.setInt(1, entity.getDriverID());
                ps.setInt(2, entity.getAcctID());

                if (entity.getCellStatus() == null || entity.getCellStatus().getCode() == null) {
                    ps.setNull(3, Types.NULL);
                } else {
                    ps.setInt(3, entity.getCellStatus().getCode());
                }

                if (entity.getProvider() == null || entity.getProvider().getCode() == null) {
                    ps.setNull(4, Types.NULL);
                } else {
                    ps.setInt(4, entity.getProvider().getCode());
                }

                if (entity.getProviderUser() == null ||entity.getProviderUser().trim().isEmpty() ) {
                    ps.setNull(5, Types.NULL);
                } else {
                    ps.setString(5, entity.getProviderUser());
                }
                if (entity.getProviderPassword() == null || entity.getProviderPassword().trim().isEmpty()) {
                    ps.setNull(6, Types.NULL);
                } else {
                    ps.setString(6, entity.getProviderPassword());
                }
                if (entity.getCellPhone() == null ||entity.getCellPhone().trim().isEmpty()) {
                    ps.setNull(7, Types.NULL);
                } else {
                    ps.setString(7, entity.getCellPhone());
                }

                logger.debug(ps.toString());
                return ps;
            }

        };
        jdbcTemplate.update(psc);
        return entity.getDriverID();

    }

    @Override
    public Integer deleteByID(Integer driverID) {
        return getJdbcTemplate().update(DELETE_PHONE_CONTROL_BY_ID, new Object[]{driverID});
    }
}

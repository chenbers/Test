package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.AddressDAO;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.State;
import com.mysql.jdbc.Statement;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;


public class AddressJDBCDAO extends SimpleJdbcDaoSupport implements AddressDAO {

    private static final String GET_ADDRESS = "SELECT a.addrID, a.acctID, a.addr1, a.addr2, a.city, a.stateID, zip FROM address a";
    private static final String FIND_ADDRESS_BY_ID = GET_ADDRESS + " where a.addrID=:addrID";
    private static final String DEL_ADDRESS_BY_ID = "DELETE FROM address WHERE addrID = ?";

    private static final String INSERT_ADDRESS = "INSERT INTO address (acctID, addr1, addr2, city, stateID, zip) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_ADDRESS = "UPDATE address set acctID=?, addr1=?, addr2=?, city=?, stateID=?, zip=? where addrID = ?";

    private static final DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");

    private ParameterizedRowMapper<Address> addressParameterizedRowMapper = new ParameterizedRowMapper<Address>() {
        @Override
        public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
            Address addressItem = new Address();
            addressItem.setAccountID(rs.getInt("a.acctID"));
            addressItem.setAddr1(rs.getString("a.addr1"));
            addressItem.setAddr2(rs.getString("a.addr2"));
            addressItem.setCity(rs.getString("a.city"));
            addressItem.setZip(rs.getString("a.zip"));
            State state = new State();
            addressItem.setState(state);


            return addressItem;
        }
    };

    @Override
    public Address findByID(Integer addrID) {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("addrID", addrID);
        StringBuilder findAddress = new StringBuilder(FIND_ADDRESS_BY_ID);

        return getSimpleJdbcTemplate().queryForObject(findAddress.toString(), addressParameterizedRowMapper, args);
    }

    @Override
    public Integer create(Integer integer, final Address address) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_ADDRESS, Statement.RETURN_GENERATED_KEYS);

                ps.setInt(1, address.getAccountID());

                if (address.getAddr1() == null) {
                    ps.setNull(2, Types.NULL);
                } else {
                    ps.setString(2, address.getAddr1());
                }

                if (address.getAddr2() == null) {
                    ps.setNull(3, Types.NULL);
                } else {
                    ps.setString(3, address.getAddr2());
                }

                if (address.getCity() == null) {
                    ps.setNull(4, Types.NULL);
                } else {
                    ps.setString(4, address.getCity());
                }

                if (address.getState() == null) {
                    ps.setNull(5, Types.NULL);
                } else {
                    ps.setInt(5, address.getState().getStateID());
                }

                if (address.getZip() == null) {
                    ps.setNull(6, Types.NULL);
                } else {
                    ps.setString(6, address.getZip());
                }

                logger.debug(ps.toString());
                return ps;
            }

            };
        jdbcTemplate.update(psc, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public Integer update(final Address address) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(UPDATE_ADDRESS);

                ps.setInt(1, address.getAccountID());

                if (address.getAddr1() == null) {
                    ps.setNull(2, Types.NULL);
                } else {
                    ps.setString(2, address.getAddr1());
                }

                if (address.getAddr2() == null) {
                    ps.setNull(3, Types.NULL);
                } else {
                    ps.setString(3, address.getAddr2());
                }

                if (address.getCity() == null) {
                    ps.setNull(4, Types.NULL);
                } else {
                    ps.setString(4, address.getCity());
                }

                if (address.getState() == null) {
                    ps.setNull(5, Types.NULL);
                } else {
                    ps.setInt(5, address.getState().getStateID());
                }

                if (address.getZip() == null) {
                    ps.setNull(6, Types.NULL);
                } else {
                    ps.setString(6, address.getZip());
                }

                ps.setInt(7, address.getAddrID());

                logger.debug(ps.toString());
                return ps;
            }
        };
        jdbcTemplate.update(psc);
        return address.getAddrID();
    }

    @Override
    public Integer deleteByID(Integer addrID) {
        return getJdbcTemplate().update(DEL_ADDRESS_BY_ID, new Object[]{addrID});
    }
}

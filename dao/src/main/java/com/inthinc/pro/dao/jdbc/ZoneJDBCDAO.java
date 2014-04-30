package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.ZoneDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Zone;
import com.mysql.jdbc.Statement;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ZoneJDBCDAO extends SimpleJdbcDaoSupport implements ZoneDAO {

    private static final String ZONE_SELECT = "SELECT * FROM zone ";
    private static final String FIND_BY_ID = ZONE_SELECT + " where zoneID = :zoneID";

    private static final String DEL_ZONE_BY_ID = "DELETE FROM zone WHERE zoneID = ?";
    private static final String UPDATE_ZONE = "UPDATE zone set acctID = ?, groupID = ?, status = ?, modified = ?, name = ?, address = ?, latLng = ? where zoneID = ?";

    private static final String INSERT_ZONE = "INSERT INTO zone (acctID, groupID, status, created, modified, name, address, latLng) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String FIND_ZONE_BY_ACCTID = ZONE_SELECT + " where acctID = :acctID";

    private static final String FIND_PUBLISHZONES = " SELECT zoneType FROM zonePublish where acctID = :acctID";


    private ParameterizedRowMapper<Zone> zoneParameterizedRowMapper = new ParameterizedRowMapper<Zone>() {
        @Override
        public Zone mapRow(ResultSet rs, int rowNum) throws SQLException {
            Zone zoneItem = new Zone();

            zoneItem.setZoneID(rs.getInt("zoneID"));
            zoneItem.setAccountID(rs.getInt("acctID"));
            zoneItem.setGroupID(rs.getInt("groupID"));
            zoneItem.setStatus(Status.valueOf(rs.getInt("status")));
            zoneItem.setCreated(rs.getDate("created"));
            zoneItem.setModified(rs.getDate("modified"));
            zoneItem.setName(rs.getString("name"));
            zoneItem.setAddress(rs.getString("address"));

            Blob blob = rs.getBlob("latLng");
            byte[] bdata = blob.getBytes(1, (int) blob.length());
            String s = new String(bdata);
            stringToHex(s);
            zoneItem.setName(s);

            List<LatLng> latLngs = new ArrayList<LatLng>();
            zoneItem.setPoints(latLngs);

            return zoneItem;
        }
    };


    @Override
    public List<Zone> getZones(Integer accountID) {
        {
            try {
                Map<String, Object> args = new HashMap<String, Object>();
                args.put("accountID", accountID);
                StringBuilder findZoneByAcctId = new StringBuilder(FIND_ZONE_BY_ACCTID);

                List<Zone> zoneList = getSimpleJdbcTemplate().query(findZoneByAcctId.toString(), zoneParameterizedRowMapper, args);

                return zoneList;
            } catch (EmptyResultSetException e) {
                return Collections.emptyList();
            }

        }
    }

    @Override
    public Integer publishZones(Integer acctID) {

        Map<String, Object> args = new HashMap<String, Object>();
        args.put("acctID", acctID);
        StringBuilder findPublishZones = new StringBuilder(ZONE_SELECT);

        return getSimpleJdbcTemplate().queryForInt(findPublishZones.toString(), zoneParameterizedRowMapper, args);
    }

    @Override
    public Zone findByID(Integer zoneID) {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("zoneID", zoneID);
        StringBuilder findAddress = new StringBuilder(FIND_BY_ID);

        return getSimpleJdbcTemplate().queryForObject(findAddress.toString(), zoneParameterizedRowMapper, args);
    }

    @Override
    public Integer create(Integer integer, final Zone entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_ZONE, Statement.RETURN_GENERATED_KEYS);

                ps.setInt(1, entity.getAccountID());

                ps.setInt(2, entity.getGroupID());

                if (entity.getStatus() == null) {
                    ps.setNull(3, Types.NULL);
                } else {
                    ps.setInt(3, entity.getStatus().getCode());
                }
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                ps.setDate(4, new java.sql.Date(toUTC(new Date()).getTime()));

                ps.setDate(5, new java.sql.Date(toUTC(new Date()).getTime()));


                if (entity.getName() == null) {
                    ps.setNull(6, Types.NULL);
                } else {
                    ps.setString(6, entity.getName());
                }

                if (entity.getAddress() == null) {
                    ps.setNull(7, Types.NULL);
                } else {
                    ps.setString(7, entity.getAddress());
                }

                if (entity.getPoints() == null) {
                    ps.setNull(8, Types.NULL);
                } else {
                    ps.setString(8, entity.getPoints().toString());
                }


                logger.debug(ps.toString());
                return ps;
            }

        };
        jdbcTemplate.update(psc, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public Integer update(final Zone entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(UPDATE_ZONE);

                ps.setInt(1, entity.getAccountID());

                ps.setInt(2, entity.getGroupID());

                if (entity.getStatus() == null || entity.getStatus().toString().isEmpty()) {
                    ps.setNull(3, Types.NULL);
                } else {
                    ps.setInt(3, entity.getStatus().getCode());
                }

                ps.setDate(4, new java.sql.Date(toUTC(new Date()).getTime()));


                if (entity.getName() == null || entity.getName().isEmpty()) {
                    ps.setNull(5, Types.NULL);
                } else {
                    ps.setString(5, entity.getName());
                }

                if (entity.getAddress() == null || entity.getName().isEmpty()) {
                    ps.setNull(6, Types.NULL);
                } else {
                    ps.setString(6, entity.getAddress());
                }

                if (entity.getPoints() == null) {
                    ps.setNull(7, Types.NULL);
                } else {
                    ps.setString(7, entity.getPoints().toString());
                }

                ps.setInt(8, entity.getZoneID());

                logger.debug(ps.toString());
                return ps;
            }
        };
        jdbcTemplate.update(psc);
        return entity.getZoneID();
    }

    @Override
    public Integer deleteByID(Integer zoneID) {
        return getJdbcTemplate().update(DEL_ZONE_BY_ID, new Object[]{zoneID});
    }

    private Date toUTC(Date date) {
        DateTime dt = new DateTime(date.getTime()).toDateTime(DateTimeZone.UTC);
        return dt.toDate();
    }

    static String stringToHex(String string) {
        StringBuilder buf = new StringBuilder(200);
        for (char ch : string.toCharArray()) {
            if (buf.length() > 0)
                buf.append(' ');
            buf.append(String.format("%04x", (int) ch));
        }
        return buf.toString();
    }

}


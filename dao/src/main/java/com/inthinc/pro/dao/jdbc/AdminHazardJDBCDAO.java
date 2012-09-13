package com.inthinc.pro.dao.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.inthinc.pro.model.BoundingBox;
import com.inthinc.pro.model.Hazard;
import com.inthinc.pro.model.HazardStatus;
import com.inthinc.pro.model.HazardType;
import com.inthinc.pro.model.User;
import com.mysql.jdbc.Statement;

public class AdminHazardJDBCDAO extends SimpleJdbcDaoSupport {

    private static final Logger logger = Logger.getLogger(AdminHazardJDBCDAO.class);
    private static String HAZARD_COLUMNS_STRING="";// = " acctID, driverID, userID, vehicleID, deviceID, latitude, longitude, radius, startTime, endTime, type, description, status, location, stateID, created, modified ";
    private static final String HAZARD_UPDATE_PRE = "UPDATE hazard set"; //
    private static final String HAZARD_UPDATE_POST = " WHERE hazardID = :hazardID";
    private static String HAZARD_UPDATE;
    /**
     * Ordered map of columns where <String columnNameInDB, String messageKeyForDisplayOnSite?>
     */
    private static final LinkedHashMap<String, String> columnMap = new LinkedHashMap<String, String>();

    static {
        //columnMap.put("hazardID", "hazardID");
        columnMap.put("acctID", "acctID");
        columnMap.put("driverID", "driverID");// TODO: should be name
        columnMap.put("userID","userID");
        columnMap.put("vehicleID", "vehicleID");// TODO: should be name
        columnMap.put("deviceID", "deviceID");// TODO: should be name?
        columnMap.put("latitude", "latitude");
        columnMap.put("longitude", "longitude");
        columnMap.put("radius", "radius");
        columnMap.put("startTime", "startTime");
        columnMap.put("endTime", "endTime");
        columnMap.put("type", "type");// TODO: should be name
        columnMap.put("description", "description");
        columnMap.put("status", "status");// TODO: should be name
        columnMap.put("location", "location");
        columnMap.put("stateID", "stateID");
        columnMap.put("created", "created");
        columnMap.put("modified", "modified");
        
        HAZARD_UPDATE = HAZARD_UPDATE_PRE;
        for(String key: columnMap.keySet()){
            HAZARD_UPDATE += " "+key+" = ? , ";
            HAZARD_COLUMNS_STRING+=" "+key+" ,";
        }
        //remove trailing comma if necessary
        if(HAZARD_UPDATE.endsWith(",")){ 
            HAZARD_UPDATE = HAZARD_UPDATE.substring(0, HAZARD_UPDATE.length()-2);
        }
        //remove trailing comma if necessary
        if(HAZARD_COLUMNS_STRING.endsWith(",")){ 
            HAZARD_COLUMNS_STRING = HAZARD_COLUMNS_STRING.substring(0, HAZARD_COLUMNS_STRING.length()-2);
        }
        HAZARD_UPDATE += HAZARD_UPDATE_POST;
    };
    private static final String HAZARD_SELECT_BY_ID = //
    "SELECT hazardID, " + HAZARD_COLUMNS_STRING + " "+//
            "FROM hazard " + //
            "WHERE hazardID = :hazardID"; //
    private static final String HAZARD_SELECT_BY_ACCOUNT_AND_BOUNDS = //
    "SELECT hazardID, " + HAZARD_COLUMNS_STRING + " "+//
            "FROM hazard " + //
            "WHERE acctID = :acctID " + //
            "  AND latitude  BETWEEN :lat1 AND :lat2 " + //
            "  AND longitude BETWEEN :lng1 AND :lng2 " + //
            "  AND endTime > now() "; //
    private static final String HAZARD_INSERT = //
    "INSERT INTO hazard ( " + HAZARD_COLUMNS_STRING + " ) " + //
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "; //
    private static final String HAZARD_DELETE_BY_ID = //
    "DELETE FROM hazard WHERE hazardID = ?"; // 

    

    private static ParameterizedRowMapper<Hazard> hazardRowMapper = new ParameterizedRowMapper<Hazard>() {
        @Override
        public Hazard mapRow(ResultSet rs, int rowNum) throws SQLException {
            Hazard hazard = new Hazard();
            hazard.setHazardID(longToInteger((Long)rs.getObject("hazardID")));
            hazard.setAcctID(longToInteger((Long)rs.getObject("acctID")));
            hazard.setDriverID(longToInteger((Long)rs.getObject("driverID")));
            hazard.setUserID(longToInteger((Long)rs.getObject("userID")));
            hazard.setVehicleID(longToInteger((Long)rs.getObject("vehicleID")));
            hazard.setDeviceID(longToInteger((Long)rs.getObject("deviceID")));
            hazard.setType(HazardType.valueOf((Integer)rs.getObject("type")));
            hazard.setRadiusMeters(rs.getInt("radius"));
            hazard.setStartTime(rs.getDate("startTime"));
            hazard.setEndTime(rs.getDate("endTime"));
            hazard.setDescription(rs.getString("description"));
            hazard.setStatus(HazardStatus.valueOf(rs.getInt("status")));
            hazard.setLocation(rs.getString("location"));
            hazard.setStateID(longToInteger((Long)rs.getObject("stateID")));
            hazard.setLatitude(rs.getDouble("latitude"));
            hazard.setLongitude(rs.getDouble("longitude"));
            return hazard;
        }
    };
    private static Integer longToInteger(Long theLong){
        Integer theInteger = theLong != null ? theLong.intValue() : null;
        return theInteger;
    }

    /**
     * @param user
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    public List<Hazard> findHazardsByUserAcct(User user, Double lat1, Double lng1, Double lat2, Double lng2) {
        System.out.println("public List<Hazard> findHazardsByUserAcct(User " + user + ", Double " + lat1 + ", Double " + lng1 + ", Double " + lat2 + ", Double " + lng2 + ")");
        List<Hazard> results;
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("acctID", user.getPerson().getAcctID());
        args.put("lat1", lat1);
        args.put("lng1", lng1);
        args.put("lat2", lat2);
        args.put("lng2", lng2);
        System.out.println("simpleJdbcTemplate: " + getSimpleJdbcTemplate());
        for (String key : args.keySet()) {
            System.out.println(key + " " + args.get(key));
        }
        System.out.println(HAZARD_SELECT_BY_ACCOUNT_AND_BOUNDS);
        results = getSimpleJdbcTemplate().query(HAZARD_SELECT_BY_ACCOUNT_AND_BOUNDS, hazardRowMapper, args);
        System.out.println("results" + results);
        return results;
    }

    public List<Hazard> findHazardsByUserAcct(User user, BoundingBox box) {
        return findHazardsByUserAcct(user, box.getSw().getLat(), box.getSw().getLng(), box.getNe().getLat(), box.getNe().getLng());
    }

    public void update(Hazard hazard) {
        // TODO: implement
    }

    public Integer deleteByID(Integer hazardID) {
        return getJdbcTemplate().update(HAZARD_DELETE_BY_ID, new Object[] { hazardID });
    }

    public Hazard findByID(Integer hazardID) {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("hazardID", hazardID);
        return getSimpleJdbcTemplate().queryForObject(HAZARD_SELECT_BY_ID, hazardRowMapper, args);
    }

    // @Override
    public Integer create(final Hazard hazard) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(HAZARD_INSERT, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, hazard.getAcctID());
                ps.setObject(2, hazard.getDriverID(), Types.INTEGER);
                ps.setObject(3, hazard.getUserID(), Types.INTEGER);
                ps.setObject(4, hazard.getVehicleID(), Types.INTEGER);
                ps.setObject(5, hazard.getDeviceID(), Types.INTEGER);
                ps.setDouble(6, hazard.getLatitude());
                ps.setDouble(7, hazard.getLongitude());
                ps.setInt(8, hazard.getRadiusMeters());
                ps.setDate(9, new Date(hazard.getStartTime().getTime()));
                ps.setDate(10, new Date(hazard.getEndTime().getTime()));
                ps.setInt(11, hazard.getType().getCode());
                ps.setString(12, hazard.getDescription());
                ps.setInt(13, hazard.getStatus().getCode());
                ps.setString(14, hazard.getLocation());
                ps.setObject(15, hazard.getStateID(), Types.INTEGER);
                ps.setDate(16, new Date(System.currentTimeMillis()));
                ps.setDate(17, new Date(System.currentTimeMillis()));
                System.out.println(ps.toString());
                return ps;
            }
        };
        
        jdbcTemplate.update(psc, keyHolder);
        return keyHolder.getKey().intValue();
    }
}

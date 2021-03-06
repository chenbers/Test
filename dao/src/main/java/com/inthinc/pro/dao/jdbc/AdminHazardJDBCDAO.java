package com.inthinc.pro.dao.jdbc;

import static com.inthinc.pro.dao.util.NumberUtil.objectToInteger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.inthinc.pro.dao.RoadHazardDAO;
import com.inthinc.pro.dao.util.GeoUtil;
import com.inthinc.pro.model.BoundingBox;
import com.inthinc.pro.model.Hazard;
import com.inthinc.pro.model.HazardStatus;
import com.inthinc.pro.model.HazardType;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.User;
import com.mysql.jdbc.Statement;

public class AdminHazardJDBCDAO extends SimpleJdbcDaoSupport implements RoadHazardDAO{
    private static final Logger logger = Logger.getLogger(AdminHazardJDBCDAO.class);
    private static String HAZARD_COLUMNS_STRING="";// = " acctID, driverID, userID, vehicleID, deviceID, latitude, longitude, radius, startTime, endTime, type, description, status, location, stateID, created, modified ";
    private static final String HAZARD_UPDATE_PRE = "UPDATE hazard set"; //
    private static final String HAZARD_UPDATE_POST = " WHERE hazardID = ?";
    private static String HAZARD_UPDATE;
    /**
     * Ordered map of columns where <String columnNameInDB, String messageKeyForDisplayOnSite?>
     */
    private static final LinkedHashMap<String, String> columnMap = new LinkedHashMap<String, String>();

    static {
        //columnMap.put("hazardID", "hazardID");
        columnMap.put("acctID", "acctID");
        columnMap.put("driverID", "driverID");
        columnMap.put("userID","userID");
        columnMap.put("vehicleID", "vehicleID");
        columnMap.put("deviceID", "deviceID");
        columnMap.put("latitude", "latitude");
        columnMap.put("longitude", "longitude");
        columnMap.put("radius", "radius");
        columnMap.put("startTime", "startTime");
        columnMap.put("endTime", "endTime");
        columnMap.put("type", "type");
        columnMap.put("description", "description");
        columnMap.put("status", "status");
        columnMap.put("location", "location");
        columnMap.put("stateID", "stateID");
        columnMap.put("created", "created");
        columnMap.put("modified", "modified");

        HAZARD_UPDATE = HAZARD_UPDATE_PRE;
        for(String key: columnMap.keySet()){
            HAZARD_UPDATE += " "+key+" = ? , ";
            HAZARD_COLUMNS_STRING+=" "+key+" ,";
        }
        HAZARD_UPDATE = HAZARD_UPDATE.trim();
        HAZARD_COLUMNS_STRING = HAZARD_COLUMNS_STRING.trim();

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
    private static final String ACCOUNT_ID_FROM_MCMID = "SELECT acctID FROM device WHERE mcmid = :mcmID ";
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
            hazard.setHazardID(objectToInteger(rs.getObject("hazardID")));
            hazard.setAcctID(objectToInteger(rs.getObject("acctID")));
            hazard.setDriverID(objectToInteger(rs.getObject("driverID")));
            hazard.setUserID(objectToInteger(rs.getObject("userID")));
            hazard.setVehicleID(objectToInteger(rs.getObject("vehicleID")));
            hazard.setDeviceID(objectToInteger(rs.getObject("deviceID")));
            hazard.setType(HazardType.valueOf((Integer)rs.getObject("type")));
            hazard.setRadiusMeters(rs.getInt("radius"));
            String strStartDate = rs.getString("startTime");
            String strEndDate = rs.getString("endTime");
            String strCreated = rs.getString("created");
            String strModified = rs.getString("modified");

            SimpleDateFormat dateFormat = getDateFormat(TimeZone.getTimeZone("UTC"));
            java.util.Date startDate = null;
            java.util.Date endDate = null;
            java.util.Date created = null;
            java.util.Date modified = null;
            try{
                 startDate = dateFormat.parse(strStartDate);
                 endDate = dateFormat.parse(strEndDate);
                 created = dateFormat.parse(strCreated);
                 modified = dateFormat.parse(strModified);
            } catch (Exception e){
                logger.error(e);
            }

            hazard.setStartTime(startDate);
            hazard.setEndTime(endDate);
            hazard.setCreated(created);
            hazard.setModified(modified);
            hazard.setDescription(rs.getString("description"));
            hazard.setStatus(HazardStatus.valueOf(rs.getInt("status")));
            hazard.setLocation(rs.getString("location"));
            hazard.setStateID(objectToInteger(rs.getObject("stateID")));
            hazard.setLatitude(rs.getDouble("latitude"));
            hazard.setLongitude(rs.getDouble("longitude"));
            return hazard;
        }
    };
    public static SimpleDateFormat getDateFormat(TimeZone timeZone){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(timeZone);
        return dateFormat;
    };

    public List<Hazard> findAllInAccount(Integer acctID) {
        return findHazardsByUserAcct(acctID, LatLng.MIN_LAT, LatLng.MIN_LNG, LatLng.MAX_LAT, LatLng.MAX_LNG);
    }
    public List<Hazard> findInAccount(Integer acctID, BoundingBox box){
    	return findHazardsByUserAcct(acctID, box.getSw().getLat(), box.getSw().getLng(), box.getNe().getLat(), box.getNe().getLng());
    }
    public List<Hazard> findHazardsByUserAcct(Integer acctID, Double lat1, Double lng1, Double lat2, Double lng2){
        List<Hazard> results;
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("acctID", acctID);
        args.put("lat1", lat1);
        args.put("lng1", lng1);
        args.put("lat2", lat2);
        args.put("lng2", lng2);
        logger.debug("simpleJdbcTemplate: " + getSimpleJdbcTemplate());
        for (String key : args.keySet()) {
            logger.debug(key + " " + args.get(key));
        }
        logger.debug(HAZARD_SELECT_BY_ACCOUNT_AND_BOUNDS);
        results = getSimpleJdbcTemplate().query(HAZARD_SELECT_BY_ACCOUNT_AND_BOUNDS, hazardRowMapper, args);
        logger.debug("results" + results);
        return results;
    }
    @Override
    public List<Hazard> findHazardsByUserAcct(User user, Double lat1, Double lng1, Double lat2, Double lng2) {
        logger.debug("public List<Hazard> findHazardsByUserAcct(User " + user + ", Double " + lat1 + ", Double " + lng1 + ", Double " + lat2 + ", Double " + lng2 + ")");
        return findHazardsByUserAcct(user.getPerson().getAcctID(), lat1, lng1, lat2, lng2);
    }

    @Override
    public List<Hazard> findHazardsByUserAcct(User user, BoundingBox box) {
        return findHazardsByUserAcct(user, box.getSw().getLat(), box.getSw().getLng(), box.getNe().getLat(), box.getNe().getLng());
    }

    @Override
    public List<Hazard> findAllInAccountWithinDistance(Integer accountID, LatLng location, Integer meters) {
        logger.debug("public List<Hazard> findAllInAccountWithinDistance(Integer "+accountID+", LatLng "+location+", Integer "+meters+")");
        Double kilometers = meters / 1000.0;
        List<Hazard> allInAccount = findAllInAccount(accountID);
        List<Hazard> results = new ArrayList<Hazard>();
        Date rightNow = new Date();
        for(Hazard hazard: allInAccount) {
            LatLng hazardLocation = new LatLng(hazard.getLat(), hazard.getLng());
            float dist = GeoUtil.distBetween(location, hazardLocation, MeasurementType.METRIC);
            logger.debug("dist: "+dist);

            if(dist < kilometers && hazard.getStatus().equals(HazardStatus.ACTIVE) && hazard.getEndTime().after(rightNow)) {
                results.add(hazard);
            }
        }
        return results;
    }
    private Integer findAccountID(String mcmID) throws EmptyResultDataAccessException {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("mcmID",mcmID);
        return getSimpleJdbcTemplate().queryForInt(ACCOUNT_ID_FROM_MCMID, args);
    }
    @Override
    public List<Hazard> findByDeviceLocationRadius(String mcmID, LatLng location, Integer meters) throws EmptyResultDataAccessException {
        List<Hazard> results;
        //TODO: firmware WANTS to only send down NEW road hazards ?
        return findAllInAccountWithinDistance(findAccountID(mcmID), location, meters);
    }

    @Override
    public Integer update(final Hazard hazard) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(HAZARD_UPDATE);
                ps.setInt(1, hazard.getAcctID());
                ps.setObject(2, hazard.getDriverID(), Types.INTEGER);
                ps.setObject(3, hazard.getUserID(), Types.INTEGER);
                ps.setObject(4, hazard.getVehicleID(), Types.INTEGER);
                ps.setObject(5, hazard.getDeviceID(), Types.INTEGER);
                ps.setDouble(6, hazard.getLatitude());
                ps.setDouble(7, hazard.getLongitude());
                ps.setInt(8, hazard.getRadiusMeters());

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                df.setTimeZone(TimeZone.getTimeZone("UTC"));
                String strStart = null;
                String strEnd = null;

                if (hazard.getStartTime()!=null){
                    strStart = df.format(toUTC(hazard.getStartTime()));
                }

                if (hazard.getEndTime()!=null){
                    strEnd = df.format(toUTC(hazard.getEndTime()));
                }

                ps.setString(9, strStart);
                ps.setString(10, strEnd);
                ps.setInt(11, hazard.getType() != null ? hazard.getType().getCode() : null);
                ps.setString(12, hazard.getDescription());
                ps.setInt(13, hazard.getStatus() != null ? hazard.getStatus().getCode() : null);
                ps.setString(14, hazard.getLocation());
                ps.setObject(15, hazard.getStateID(), Types.INTEGER);
                ps.setTimestamp(16, hazard.getCreated() != null ? new Timestamp(hazard.getCreated().getTime()) : new Timestamp(System.currentTimeMillis()));
                ps.setTimestamp(17, new Timestamp(System.currentTimeMillis()));
                ps.setInt(18, hazard.getHazardID());
                logger.debug(ps.toString());
                return ps;
            }
        };

        jdbcTemplate.update(psc);
        return hazard.getHazardID();
    }

    @Override
    public Integer deleteByID(Integer hazardID) {
        return getJdbcTemplate().update(HAZARD_DELETE_BY_ID, new Object[] { hazardID });
    }

    @Override
    public Hazard findByID(Integer hazardID) {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("hazardID", hazardID);
        return getSimpleJdbcTemplate().queryForObject(HAZARD_SELECT_BY_ID, hazardRowMapper, args);
    }

    @Override
    public Integer create(Integer id, final Hazard hazard) {
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

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                df.setTimeZone(TimeZone.getTimeZone("UTC"));
                String strStart = df.format(toUTC(hazard.getStartTime()));
                String strEnd = df.format(toUTC(hazard.getEndTime()));
                ps.setString(9, strStart);
                ps.setString(10, strEnd);
                ps.setInt(11, hazard.getType().getCode());
                ps.setString(12, hazard.getDescription());
                ps.setInt(13, hazard.getStatus().getCode());
                ps.setString(14, hazard.getLocation());
                ps.setObject(15, hazard.getStateID(), Types.INTEGER);
                ps.setTimestamp(16, new Timestamp(System.currentTimeMillis()));
                ps.setTimestamp(17, new Timestamp(System.currentTimeMillis()));
                logger.debug(ps.toString());
                return ps;
            }
        };

        jdbcTemplate.update(psc, keyHolder);
        return keyHolder.getKey().intValue();
    }

    private Date toUTC(Date date){
        DateTime dt = new DateTime(date.getTime()).toDateTime(DateTimeZone.UTC);
        return dt.toDate();
    }
}

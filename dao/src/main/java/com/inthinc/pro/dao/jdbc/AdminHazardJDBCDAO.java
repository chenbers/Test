package com.inthinc.pro.dao.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.model.BoundingBox;
import com.inthinc.pro.model.CustomMap;
import com.inthinc.pro.model.Hazard;
import com.inthinc.pro.model.HazardStatus;
import com.inthinc.pro.model.HazardType;
import com.inthinc.pro.model.User;
import com.mysql.jdbc.Statement;

public class AdminHazardJDBCDAO extends SimpleJdbcDaoSupport{
    
    private static final Logger logger = Logger.getLogger(AdminHazardJDBCDAO.class);

    private static final String HAZARD_SELECT_BY_ID= 
            "select acctID, driverID, vehicleID, deviceID, latitude, longitude, radius, startTime, endTime, type, description, status, location, stateID, created, modified, hazardID " +
            "from hazard "+
            "where hazardID = :hazardID";
    private static final String HAZARD_SELECT_BY_ACCOUNT_AND_BOUNDS = 
            "SELECT acctID, driverID, vehicleID, deviceID, latitude, longitude, radius, startTime, endTime, type, description, status, location, stateID, created, modified, hazardID " +
            "from hazard " +
            "where acctID = :acctID " +
            "  and latitude  between :lat1 and :lat2 " +
            "  and longitude between :lng1 and :lng2 " +
            "  and endTime > now() ";
    private static final String HAZARD_INSERT = 
            "INSERT into hazard ( acctID,  driverID, userID,  vehicleID,  deviceID,  latitude,  longitude,  radius,  startTime,  endTime,  type,  description,  status,  location,  stateID,  created, modified) " +
            "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
    private static final String HAZARD_DELETE_BY_ID =
            "DELETE from hazard where hazardID = ?";
    
	private static final Map<String,String> columnMap = new HashMap<String, String>();

	static {
	    columnMap.put("hazardID", "hazardID");
	    columnMap.put("acctID", "acctID");
		columnMap.put("driverID", "driverID");//TODO: should be name
		columnMap.put("vehicleID", "vehicleID");//TODO: should be name
		columnMap.put("deviceID", "deviceID");//TODO: should be name?
		columnMap.put("type", "");//TODO: should be name
		columnMap.put("radius", "");
		columnMap.put("state", "");
		columnMap.put("startTime", "");
		columnMap.put("endTime", "");
		columnMap.put("description", "");
		columnMap.put("status", "");//TODO: should be name
		columnMap.put("location", "");
		columnMap.put("stateID", "stateID");
		columnMap.put("created", "created");
		columnMap.put("modified", "modified");
		
	};
	
	private static ParameterizedRowMapper<Hazard> hazardRowMapper = new ParameterizedRowMapper<Hazard>() {
	    @Override
        public Hazard mapRow(ResultSet rs, int rowNum) throws SQLException {
            Hazard hazard = new Hazard();
            hazard.setHazardID(rs.getInt("hazardID"));
            hazard.setAcctID(rs.getInt("acctID"));
            hazard.setDriverID(rs.getInt("driverID"));
            hazard.setVehicleID(rs.getInt("vehicleID"));
            hazard.setDeviceID(rs.getInt("deviceID"));
            hazard.setType(HazardType.valueOf(rs.getInt("type")));
            hazard.setRadiusMeters(rs.getInt("radius"));
            hazard.setStartTime(rs.getDate("startTime"));
            hazard.setEndTime(rs.getDate("endTime"));
            hazard.setDescription(rs.getString("description"));
            hazard.setStatus(HazardStatus.valueOf(rs.getInt("status")));
            hazard.setLocation(rs.getString("location"));
            hazard.setStateID(rs.getInt("stateID"));
            hazard.setLatitude(rs.getDouble("latitude"));
            hazard.setLongitude(rs.getDouble("longitude"));
            return hazard;
	    }
	};
	
	/**
	 * @param user
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return
	 */
	public List<Hazard> findHazardsByUserAcct(User user, Double lat1, Double lng1, Double lat2, Double lng2){
	    System.out.println("public List<Hazard> findHazardsByUserAcct(User "+user+", Double "+lat1+", Double "+lng1+", Double "+lat2+", Double "+lng2+")");
	    List<Hazard> results;
	    Map<String, Object> args = new HashMap<String, Object>();
	    args.put("acctID", user.getPerson().getAcctID());
	    args.put("lat1", lat1);
	    args.put("lng1", lng1);
	    args.put("lat2", lat2);
	    args.put("lng2", lng2);
	    System.out.println("simpleJdbcTemplate: "+getSimpleJdbcTemplate());
	    for(String key: args.keySet()){
	        System.out.println(key+" "+args.get(key));
	    }
	    System.out.println(HAZARD_SELECT_BY_ACCOUNT_AND_BOUNDS);
	    results = getSimpleJdbcTemplate().query(HAZARD_SELECT_BY_ACCOUNT_AND_BOUNDS, hazardRowMapper, args);
	    System.out.println("results"+results);
	    return results;
	}
	public List<Hazard> findHazardsByUserAcct(User user, BoundingBox box){
	    return findHazardsByUserAcct(user, box.getSw().getLat(), box.getSw().getLng(), box.getNe().getLat(), box.getNe().getLng());
	}
	
	public void update(Hazard hazard){
	    //TODO: implement
	}
	
	public Integer deleteByID(Integer hazardID){
        return getJdbcTemplate().update(HAZARD_DELETE_BY_ID, new Object[] {hazardID});
	}
	
	public Hazard findByID(Integer hazardID) {
	    Map<String, Object> args = new HashMap<String, Object>();
        args.put("hazardID", hazardID);
	    return getSimpleJdbcTemplate().queryForObject(HAZARD_SELECT_BY_ID, hazardRowMapper, args);
	}
	
    //@Override
    public Integer create(final Hazard hazard) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(HAZARD_INSERT, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, hazard.getAcctID());
                ps.setObject(2, hazard.getDriverID());
                ps.setObject(3, hazard.getUserID());
                ps.setObject(4, hazard.getVehicleID());
                ps.setObject(5, hazard.getDeviceID());
                ps.setDouble(6, hazard.getLatitude());
                ps.setDouble(7, hazard.getLongitude());
                ps.setInt(8, hazard.getRadiusMeters());
                ps.setDate(9, new Date(hazard.getStartTime().getTime()));
                ps.setDate(10, new Date(hazard.getEndTime().getTime()));
                ps.setInt(11, hazard.getType().getCode());
                ps.setString(12, hazard.getDescription());
                ps.setInt(13, hazard.getStatus().getCode());
                ps.setString(14, hazard.getLocation());
                ps.setObject(15, hazard.getStateID());
                ps.setDate(16, new Date(System.currentTimeMillis()));
                ps.setDate(17, new Date(System.currentTimeMillis()));
                
                return ps;
            }
        };
        jdbcTemplate.update(psc, keyHolder);
        return keyHolder.getKey().intValue();
    }
}


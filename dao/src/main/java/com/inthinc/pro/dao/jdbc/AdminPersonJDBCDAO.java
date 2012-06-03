package com.inthinc.pro.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.Gender;
import com.inthinc.pro.model.GoogleMapType;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.app.SupportedTimeZones;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableFilterField;
import com.inthinc.pro.model.security.AccessPoint;

public class AdminPersonJDBCDAO extends SimpleJdbcDaoSupport{
    
    private static final Logger logger = Logger.getLogger(AdminPersonJDBCDAO.class);
    
    // these are created as application level beans in spring context (need to do that for tests too)
    //SupportedTimeZones;
    //States;
    final Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));

    private static final String ROLE_ACCESS_SELECT = "SELECT DISTINCT roleID, accessPtID, mode FROM roleAccess WHERE roleID IN (:rlist)";
    private static final String ROLE_SELECT = "SELECT u.userID, u.roleID FROM role as r JOIN userRole as u USING (roleID) WHERE u.userID IN (:ulist)";
    private static final String PERSON_COUNT = "SELECT COUNT(*) FROM person AS p LEFT JOIN user as u USING (personID) LEFT JOIN driver as d USING (personID) WHERE (d.groupID IN (:dglist) OR u.groupID IN (:uglist)) AND (p.status != 3 OR d.status != 3 OR u.status !=3)";
	
	private static final Map<String,String> columnMap = new HashMap<String, String>();

	static {
		columnMap.put("fullName", "p.first");
		columnMap.put("priPhone", "p.priPhone");
		columnMap.put("secPhone", "p.secPhone");
		columnMap.put("priEmail", "p.priEmail");
		columnMap.put("secEmail", "p.secEmail");
		columnMap.put("priText", "p.priText");
		columnMap.put("secText", "p.secText");
		columnMap.put("info", "p.info");
		columnMap.put("warn", "p.warn");
		columnMap.put("crit", "p.crit");
		columnMap.put("timeZone", "p,tzID");
		columnMap.put("empid", "p.empID");
		columnMap.put("reportsTo", "p.reportsTo");
		columnMap.put("title", "p.title");
		columnMap.put("dob", "p.dob");
		columnMap.put("gender", "p.gender");
		columnMap.put("locale", "p.locale");
		columnMap.put("measurementType", "p.measureType");
		columnMap.put("fuelEfficiencyType", "p.fuelEffType");
		columnMap.put("user_status", "u.status");
		columnMap.put("user_username", "u.username");
		columnMap.put("user_groupID", "u.groupID");
		columnMap.put("user_role", "u.role");
		columnMap.put("driver_status", "d.status");
		columnMap.put("driver_license", "d.license");
		columnMap.put("driver_licenseClass", "d.class");
		columnMap.put("driver_state", "d.stateID");
		columnMap.put("driver_expiration", "d.expiration");
		columnMap.put("driver_certifications", "d.certs");
		columnMap.put("driver_dot", "d.dot");
		columnMap.put("barcode", "d.barcode");
		columnMap.put("rfid1", "d.rfid1");
		columnMap.put("rfid2", "d.rfid2");
		columnMap.put("driver_groupID", "d.groupID");
	}
	
	public Integer getCount(List<Integer> groupIDs, List<TableFilterField> filters) {
	    Map<String,Object>  params = new HashMap<String, Object>();
        params.put("dglist",groupIDs);
        params.put("uglist",groupIDs);

        Integer cnt =  getSimpleJdbcTemplate().queryForInt(PERSON_COUNT, params);
        logger.info("getCount " + cnt + " " + groupIDs + " " + PERSON_COUNT);
		
		return cnt;
	}
	
	
	public List<Person> getPeople(List<Integer> groupIDs, PageParams pageParams) {
				
		StringBuilder personSelect = new StringBuilder();
		personSelect
				.append("SELECT p.personID, p.acctID, p.priPhone, p.secPhone, p.priEmail, p.secEmail, p.priText, p.secText, p.info, p.warn, p.crit, p.tzID, p.empID, p.reportsTo, p.title, p.dob, p.gender, p.locale, p.measureType, p.fuelEffType, p.first, p.middle, p.last, p.suffix, p.status, ")
				.append("u.userID, u.status, u.username, u.groupID, u.mapType, u.password,  u.lastLogin, u.passwordDT, ")
				.append("d.driverID, d.groupID, d.status, d.license, d.class, d.stateID, d.expiration, d.certs, d.dot, d.barcode, d.rfid1, d.rfid2 ")
				.append("FROM person AS p LEFT JOIN user as u USING (personID) LEFT JOIN driver as d USING (personID) WHERE (d.groupID IN (:dglist) OR u.groupID IN (:uglist)) AND (p.status != 3 OR d.status != 3 OR u.status !=3)");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dglist", groupIDs);
		params.put("uglist", groupIDs);
		
		if(pageParams.getSort() == null || pageParams.getSort().getField().isEmpty() || pageParams.getSort().getOrder() == null)
			personSelect.append(" ORDER BY p.first ASC");
		else
			personSelect.append(" ORDER BY " + columnMap.get(pageParams.getSort().getField()) + " " + (pageParams.getSort().getOrder() == SortOrder.ASCENDING ? "ASC" : "DESC"));
		
		if(pageParams.getStartRow() != null && pageParams.getEndRow() != null)
			personSelect.append(" LIMIT " + pageParams.getStartRow() + ", " + (pageParams.getEndRow() - pageParams.getStartRow()) );
		
		
		List<Person> personList = getSimpleJdbcTemplate().query(personSelect.toString(), new ParameterizedRowMapper<Person>() {
			
			
			@Override
			public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
				Person person = new Person();
				person.setPersonID(rs.getInt("p.personID"));
				person.setAcctID(rs.getInt("p.acctID"));
				person.setPriPhone(rs.getString("p.priPhone"));
				person.setSecPhone(rs.getString("p.secPhone"));
				person.setPriEmail(rs.getString("p.priEmail"));
				person.setSecEmail(rs.getString("p.secEmail"));
				person.setPriText(rs.getString("p.priText"));
				person.setSecText(rs.getString("p.secText"));
				person.setInfo(rs.getObject("p.info") == null ? null : rs.getInt("p.info"));
				person.setWarn(rs.getObject("p.warn") == null ? null : rs.getInt("p.warn"));
				person.setCrit(rs.getObject("p.crit") == null ? null : rs.getInt("p.crit"));
				Integer tzID = rs.getInt("p.tzID");
				person.setTimeZone(TimeZone.getTimeZone(SupportedTimeZones.lookup(tzID)));
				person.setEmpid(rs.getString("p.empID"));
				person.setReportsTo(rs.getString("p.reportsTo"));
				person.setTitle(rs.getString("p.title"));
				person.setDob(rs.getDate("p.dob", calendar));
				person.setGender(Gender.valueOf(rs.getInt("p.gender")));
				
                String[] locale = rs.getString("p.locale").split("_");
                if (locale.length == 1)
                    person.setLocale(new Locale(locale[0]));
                else if (locale.length == 2)
                    person.setLocale(new Locale(locale[0], locale[1]));

                person.setMeasurementType(MeasurementType.valueOf(rs.getInt("p.measureType")));
                person.setFuelEfficiencyType(FuelEfficiencyType.valueOf(rs.getInt("p.fuelEffType")));
                person.setFirst(rs.getString("p.first"));
                person.setMiddle(rs.getString("p.middle"));
                person.setLast(rs.getString("p.last"));
                person.setSuffix(rs.getString("p.suffix"));
                person.setStatus(Status.valueOf(rs.getInt("p.status")));
                
                Integer userID= rs.getInt("u.userID");
                if (userID != 0) {
                    User user = new User();
                    user.setUserID(userID);
                    user.setStatus(Status.valueOf(rs.getInt("u.status")));
                    user.setUsername(rs.getString("u.username"));
                    user.setGroupID(rs.getInt("u.groupID"));
                    user.setMapType(GoogleMapType.valueOf(rs.getInt("u.mapType")));
                    user.setPassword(rs.getString("u.password"));
                    user.setLastLogin(rs.getTimestamp("u.lastLogin"));
                    user.setPasswordDT(rs.getTimestamp("u.passwordDT"));
                    user.setPerson(person);
                    user.setPersonID(person.getPersonID());
                    person.setUser(user);
                    user.setPerson(person);
                }
                else {
                    person.setUser(null);
                }

                
                Integer driverID = rs.getInt("d.driverID");
                if (driverID != 0) {
                    Driver driver = new Driver();
                    driver.setDriverID(driverID);
                    driver.setGroupID(rs.getInt("d.groupID"));
                    driver.setStatus(Status.valueOf(rs.getInt("d.status")));
                    driver.setLicense(rs.getString("d.license"));
                    driver.setLicenseClass(rs.getString("d.class"));
                    driver.setState(States.getStateById(rs.getInt("d.stateID")));
                    driver.setExpiration(rs.getDate("d.expiration"));
                    driver.setCertifications(rs.getString("d.certs"));
                    driver.setDot(RuleSetType.valueOf(rs.getInt("d.dot")));
                    driver.setBarcode(rs.getString("d.barcode"));
                    driver.setRfid1(rs.getObject("d.rfid1") == null ? null : rs.getLong("d.rfid1"));
                    driver.setRfid2(rs.getObject("d.rfid2")== null ? null : rs.getLong("d.rfid2"));
                    person.setDriver(driver);
                    driver.setPerson(person);
                }
                else {
                    person.setDriver(null);
                }
                
				return person;
			}
		}, params); 
		
		addUserRoles(personList);
		return personList;
	}


    private void addUserRoles(List<Person> personList) {
        
        final Map<Integer, User> users = new HashMap<Integer, User>();
        final Set<Integer> roleSet = new HashSet<Integer>();
        final Map<Integer, List<AccessPoint>> roleAccessPointMap = new HashMap<Integer, List<AccessPoint>>();
        
        for (Person person : personList) {
        	if (person.getUserID() != null)
        		users.put(person.getUserID(), person.getUser());
        }
        if (users.size() == 0)
        	return;
        
        List<List<Integer>> roleList = getSimpleJdbcTemplate().query(ROLE_SELECT, new ParameterizedRowMapper<List<Integer>>() {
			@Override
			public List<Integer> mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = users.get(rs.getInt("u.userID"));
				if(user.getRoles() == null)
					user.setRoles(new ArrayList<Integer>());
				user.getRoles().add(rs.getInt("u.roleID"));
				roleSet.add(rs.getInt("u.roleID"));
        		return null;
        	}
        }, new HashMap<String, Object>(){{put("ulist", users.keySet());}});
        
//        List<List<AccessPoint>> accessList = getSimpleJdbcTemplate().query(ROLE_ACCESS_SELECT, new ParameterizedRowMapper<List<AccessPoint>>() {
//
//			@Override
//			public List<AccessPoint> mapRow(ResultSet rs, int rowNum) throws SQLException {
//				int roleID = rs.getInt("roleID");
//				if(!roleAccessPointMap.containsKey(roleID)) 
//					roleAccessPointMap.put(roleID, new ArrayList<AccessPoint>());
//				roleAccessPointMap.get(roleID).add(new AccessPoint(rs.getInt("accessPtID"), rs.getInt("mode")));
//				return null;
//			}
//        	
//		}, new HashMap<String, Object>(){{put("rlist", roleSet);}});
//        
//        for(User user : users.values()) {
//        	for(Integer roleID : user.getRoles()) {
//        		if(user.getAccessPoints() == null)
//        			user.setAccessPoints(new ArrayList<AccessPoint>());
//        		user.getAccessPoints().addAll(roleAccessPointMap.get(roleID));
//        	}
//        }
        
    }
    
}

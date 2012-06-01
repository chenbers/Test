package com.inthinc.pro.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.Gender;
import com.inthinc.pro.model.GoogleMapType;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.app.SupportedTimeZones;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableFilterField;

public class AdminPersonJDBCDAO extends SimpleJdbcDaoSupport{
    
    private static final Logger logger = Logger.getLogger(AdminPersonJDBCDAO.class);
    
    // these are created as application level beans in spring context (need to do that for tests too)
    //SupportedTimeZones;
    //States;
    final Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));

    //	private static final String PERSON_COUNT = "SELECT COUNT(*) FROM person AS p LEFT JOIN user as u USING (personID) LEFT JOIN driver as d USING (personID) WHERE d.groupID IN (?) OR u.groupID IN (?) AND (p.status != 3 OR d.status != 3 OR u.status !=3)";
    private static final String PERSON_COUNT = "SELECT COUNT(*) FROM person AS p LEFT JOIN user as u USING (personID) LEFT JOIN driver as d USING (personID) WHERE d.groupID IN (:dglist) OR u.groupID IN (:uglist) AND (p.status != 3 OR d.status != 3 OR u.status !=3)";
	
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
		
//		String groupIDsForSQL = groupIDs.toString().substring(1, groupIDs.toString().length() - 1);
//		Integer cnt =  getSimpleJdbcTemplate().queryForInt(PERSON_COUNT, groupIDsForSQL, groupIDsForSQL);
	    
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
				.append("u.userID, u.status, u.username, u.groupID, u.mapType, u.password,  ")
		         .append("d.driverID, d.groupID, d.status, d.license, d.class, d.stateID, d.expiration, d.certs, d.dot, d.barcode, d.rfid1, d.rfid2 ")
				.append("FROM person AS p LEFT JOIN user as u USING (personID) LEFT JOIN driver as d USING (personID) WHERE d.groupID IN (:dglist) OR u.groupID IN (:uglist) AND (p.status != 3 OR d.status != 3 OR u.status !=3)");
        Map<String,Object>  params = new HashMap<String, Object>();
        params.put("dglist",groupIDs);
        params.put("uglist",groupIDs);
		
		if(pageParams.getSort() == null || pageParams.getSort().getField().isEmpty() || pageParams.getSort().getOrder() == null)
			personSelect.append(" ORDER BY p.first ASC");
		else
			personSelect.append(" ORDER BY " + columnMap.get(pageParams.getSort().getField()) + " " + (pageParams.getSort().getOrder() == SortOrder.ASCENDING ? "ASC" : "DESC"));
		
		if(pageParams.getStartRow() != null && pageParams.getEndRow() != null)
			personSelect.append(" LIMIT " + pageParams.getStartRow() + ", " + (pageParams.getEndRow() - pageParams.getStartRow()) );
		
		
//		String groupIDsForSQL = groupIDs.toString().substring(1, groupIDs.toString().length() - 1);
		
       
		
		
		List<Person> personList = getSimpleJdbcTemplate().query(personSelect.toString(), new ParameterizedRowMapper<Person>() {
			@Override
			public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
				Person person = new Person();
				person.setPersonID(rs.getInt(1));
				person.setAcctID(rs.getInt(2));
				person.setPriPhone(rs.getString(3));
				person.setSecPhone(rs.getString(4));
				person.setPriEmail(rs.getString(5));
				person.setSecEmail(rs.getString(6));
				person.setPriText(rs.getString(7));
				person.setSecText(rs.getString(8));
				person.setInfo(rs.getInt(9));
				person.setWarn(rs.getInt(10));
				person.setCrit(rs.getInt(11));
				Integer tzID = rs.getInt(12);
				person.setTimeZone(TimeZone.getTimeZone(SupportedTimeZones.lookup(tzID)));
				person.setEmpid(rs.getString(13));
				person.setReportsTo(rs.getString(14));
				person.setTitle(rs.getString(15));
				person.setDob(rs.getDate(16, calendar));
				person.setGender(Gender.valueOf(rs.getInt(17)));
				
                String[] locale = rs.getString(18).split("_");
                if (locale.length == 1)
                    person.setLocale(new Locale(locale[0]));
                else if (locale.length == 2)
                    person.setLocale(new Locale(locale[0], locale[1]));

                person.setMeasurementType(MeasurementType.valueOf(rs.getInt(19)));
                person.setFuelEfficiencyType(FuelEfficiencyType.valueOf(rs.getInt(20)));
                person.setFirst(rs.getString(21));
                person.setMiddle(rs.getString(22));
                person.setLast(rs.getString(23));
                person.setSuffix(rs.getString(24));
                person.setStatus(Status.valueOf(rs.getInt(25)));
                
                Integer userID= rs.getInt(26);
                if (userID != 0) {
                    User user = new User();
                    user.setUserID(userID);
                    user.setStatus(Status.valueOf(rs.getInt(27)));
                    user.setUsername(rs.getString(28));
                    user.setGroupID(rs.getInt(29));
                    user.setMapType(GoogleMapType.valueOf(rs.getInt(30)));
                    user.setPassword(rs.getString(31));
                    user.setPerson(person);
                    user.setPersonID(person.getPersonID());
                    person.setUser(user);
                    user.setPerson(person);
                }
                else {
                    person.setUser(null);
                }

                Integer driverID = rs.getInt(32);
                if (driverID != 0) {
                    Driver driver = new Driver();
                    driver.setDriverID(driverID);
                    driver.setGroupID(rs.getInt(33));
                    driver.setStatus(Status.valueOf(rs.getInt(34)));
                    driver.setLicense(rs.getString(35));
                    driver.setLicenseClass(rs.getString(36));
                    driver.setState(States.getStateById(rs.getInt(37)));
                    driver.setExpiration(rs.getDate(38, calendar));
                    driver.setCertifications(rs.getString(39));
                    driver.setDot(RuleSetType.valueOf(rs.getInt(40)));
                    driver.setBarcode(rs.getString(41));
                    driver.setRfid1(rs.getLong(42) == 0l ? null : rs.getLong(42));
                    driver.setRfid2(rs.getLong(43)== 0l ? null : rs.getLong(43));
                    person.setDriver(driver);
                    driver.setPerson(person);
                }
                else {
                    person.setDriver(null);
                }
                
				return person;
			}
		}, params); //groupIDsForSQL, groupIDsForSQL);
		
		
		addUserRoles(personList);
		
		
		
		return personList;
	}


	// TODO: cj = couldn't figure out how to do this without a separate query (also did the jdbc template wrong I'm sure)
    private void addUserRoles(List<Person> personList) {
        
        List<Integer> userIDs = new ArrayList<Integer>();
        for (Person person : personList) {
            if (person.getUserID() != null)
                userIDs.add(person.getUserID());
        }
        if (userIDs.size() == 0)
            return;
        
        StringBuilder roleSelect = new StringBuilder();

        roleSelect
                .append("SELECT r.userID, r.roleID ")
                .append("FROM userRole AS r LEFT JOIN user as u USING (userID) WHERE r.userID IN (:ulist) order by r.userID");
        Map<String,Object>  params = new HashMap<String, Object>();
        params.put("ulist",userIDs);
        
        RoleMapper mapper = new RoleMapper(personList);
        getSimpleJdbcTemplate().query(roleSelect.toString(), (ParameterizedRowMapper<List<Integer>>) mapper, params); 
        
        
    }
	
    class RoleMapper implements ParameterizedRowMapper<List<Integer>>
    {
        List<Person> personList;
        
        public RoleMapper(List<Person> personList) {
            this.personList = personList;
        }

        @Override
        public List<Integer> mapRow(ResultSet rs, int rowNum) throws SQLException {
            Integer userID = rs.getInt(1);
            Integer roleID = rs.getInt(2);
            for (Person person : personList) {
                if (person.getUserID() != null && person.getUserID().equals(userID)) {
                    
                    if (person.getUser().getRoles() == null) {
                        person.getUser().setRoles(new ArrayList<Integer>());
                    }
                    person.getUser().getRoles().add(roleID);
                    person.getUser().getRoles();
                }
            }
            return null;
        }
        
    }


}

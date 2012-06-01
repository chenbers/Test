package com.inthinc.pro.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.Gender;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.SortOrder;

public class AdminPersonJDBCDAO extends SimpleJdbcDaoSupport{

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
	
	
	public List<Person> getPeople(List<Integer> groupIDList, PageParams pageParams) {
				
		StringBuilder personSelect = new StringBuilder();
		
		personSelect
				.append("SELECT p.personID, p.acctID, p.priPhone, p.secPhone, p.priEmail, p.secEmail, p.priText, p.secText, p.info, p.warn, p.crit, p.tzID, p.empID, p.reportsTo, p.title, p.dob, p.gender, p.locale, p.measureType, p.fuelEffType, p.first, p.middle, p.last, ")
				.append("u.userID, u.status, u.username, u.groupID, d.driverID, d.groupID, d.status, d.license, d.class, d.stateID, d.expiration, d.certs, d.dot, d.barcode, d.rfid1, d.rfid2 ")
				.append("FROM person AS p LEFT JOIN user as u USING (personID) LEFT JOIN driver as d USING (personID) WHERE d.groupID IN (?) OR u.groupID IN (?) AND (p.status != 3 OR d.status != 3 OR u.status !=3)");
		
		if(!pageParams.getSort().getField().isEmpty() && pageParams.getSort().getOrder() != null)
			personSelect.append(" ORDER BY " + columnMap.get(pageParams.getSort().getField()) + " " + (pageParams.getSort().getOrder() == SortOrder.ASCENDING ? "asc" : "desc"));
		
		if(pageParams.getStartRow() != null && pageParams.getEndRow() != null)
			personSelect.append(" LIMIT " + pageParams.getStartRow() + ", " + (pageParams.getEndRow() - pageParams.getStartRow()) );
		
		
		String groupIDs = groupIDList.toString().substring(1, groupIDList.toString().length() - 1);
		
		
		
		
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
				person.setTimeZone(TimeZone.getTimeZone(Integer.toString(rs.getInt(12))));
				person.setEmpid(rs.getString(13));
				person.setReportsTo(rs.getString(14));
				person.setTitle(rs.getString(15));
				person.setDob(rs.getDate(16));
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
                
                User user = new User();
                user.setUserID(rs.getInt(24));
                user.setStatus(Status.valueOf(rs.getInt(25)));
                user.setUsername(rs.getString(26));
                user.setGroupID(rs.getInt(27));
                user.setPerson(person);
                user.setPersonID(person.getPersonID());
                person.setUser(user);
                
                Driver driver = new Driver();
                driver.setDriverID(rs.getInt(28));
                driver.setGroupID(rs.getInt(29));
                driver.setStatus(Status.valueOf(rs.getInt(30)));
                driver.setLicense(rs.getString(31));
                driver.setLicenseClass(rs.getString(32));
                driver.setState(State.valueOf(rs.getInt(33)));
                driver.setExpiration(rs.getDate(34));
                driver.setCertifications(rs.getString(35));
                driver.setDot(RuleSetType.valueOf(rs.getInt(36)));
                driver.setBarcode(rs.getString(37));
                driver.setRfid1(rs.getLong(38));
                driver.setRfid2(rs.getLong(39));
                person.setDriver(driver);
                
				return person;
			}
		}, groupIDs, groupIDs);
		return personList;
	}
	
}

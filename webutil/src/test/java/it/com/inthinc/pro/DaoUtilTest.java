package it.com.inthinc.pro;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import it.com.inthinc.pro.dao.model.GroupData;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.IntegrationConfig;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.context.SecurityContextImpl;
import org.springframework.security.providers.TestingAuthenticationToken;

import com.inthinc.pro.backing.dao.DaoUtilBean;
import com.inthinc.pro.backing.dao.DateFormatBean;
import com.inthinc.pro.backing.dao.MockSuperuserDAO;
import com.inthinc.pro.backing.dao.model.CrudType;
import com.inthinc.pro.backing.dao.model.DaoMethod;
import com.inthinc.pro.backing.dao.model.Param;
import com.inthinc.pro.backing.dao.model.Result;
import com.inthinc.pro.backing.dao.ui.UIInputType;
import com.inthinc.pro.backing.dao.validator.ValidatorType;
import com.inthinc.pro.dao.hessian.PersonHessianDAO;
import com.inthinc.pro.dao.hessian.ReportScheduleHessianDAO;
import com.inthinc.pro.dao.hessian.RoleHessianDAO;
import com.inthinc.pro.dao.hessian.TablePreferenceHessianDAO;
import com.inthinc.pro.dao.hessian.proserver.ReportServiceCreator;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.Gender;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.ReportSchedule;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.TablePreference;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.security.Role;
import com.inthinc.pro.security.userdetails.ProUser;


public class DaoUtilTest extends BaseSpringTest {
	
    // Warning: this test uses the same data as the alert messages test in the dao project -- If the alert data is regenerated
    // copy the AlertTest.xml from that project's test/resources to this one.
    // UtilText.xml is also  AlertTest.xml but for a different account
    private static final String XML_DATA_FILE = "AlertTest.xml";
    private static final String OTHER_XML_DATA_FILE = "UtilTest.xml";

    private static ReportServiceCreator reportServiceCreator;
    private static SiloServiceCreator siloServiceCreator;

    MockSuperuserDAO superuserDAO;
    
    private static Map<ValidatorType, String> validValueMap;
    private static Map<ValidatorType, String> invalidValueMap;
    private static List<Date> dateList;
    private static List<String> expectedNoResults;
    private static List<String> expectedNoAttempt;
    private static User fleetUser;
    private static Integer fleetAccountID; 
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        IntegrationConfig config = new IntegrationConfig();

        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());

        reportServiceCreator = new ReportServiceCreator(host, port);
        siloServiceCreator = new SiloServiceCreator(host, port);
        
        validValueMap = initValueMap(XML_DATA_FILE, true);
        invalidValueMap = initValueMap(OTHER_XML_DATA_FILE, false);
	    DateTimeZone dateTimeZone = DateTimeZone.forID(TimeZone.getDefault().getID());

        dateList = new ArrayList<Date>();
      	dateList.add(new DateTime(dateTimeZone).minusDays(7).toDate());
        dateList.add(new DateTime(dateTimeZone).minusDays(0).toDate());
        expectedNoResults = new ArrayList<String>();
        expectedNoResults.add("getCrashes");
        expectedNoResults.add("getDriverByPersonID");
        expectedNoResults.add("getRedFlagAlertsByUserIDDeep"); 
        expectedNoResults.add("getSDScoresByGT");
        expectedNoResults.add("getSDTrendsByGTC");
        expectedNoResults.add("getStops");
        expectedNoResults.add("getZoneAlertsByUserIDDeep");
        
        
        expectedNoAttempt = new ArrayList<String>();
        expectedNoAttempt.add("getCrash");
        expectedNoAttempt.add("getID");
        expectedNoAttempt.add("getNote");
        expectedNoAttempt.add("getRfidsForBarcode");
   }
    
    private static Map<ValidatorType, String> initValueMap(String filename, boolean initUser) throws Exception {
    	
    	ITData itData = new ITData();
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
        if (!itData.parseTestData(stream, siloServiceCreator.getService(), true, true)) {
            throw new Exception("Error parsing Test data xml file");
        }
        if (initUser) {
        	fleetUser = itData.fleetUser;
        	fleetAccountID = itData.fleetGroup.getAccountID();
        }
        GroupData groupData = itData.teamGroupData.get(0);
        Integer userID = groupData.user.getUserID();
        Person person = getPerson(groupData.user.getPersonID());
        Role role = getRole(itData.account.getAccountID());
        ReportSchedule reportSchedule = getReportSchedule(itData.account.getAccountID(), userID, groupData.group.getGroupID());
        TablePreference tablePreference = getTablePreference(userID);
        
        Map<ValidatorType, String> valueMap = new HashMap<ValidatorType, String>();
        valueMap.put(ValidatorType.ADDRESS, itData.address.getAddrID().toString());
//        valueMap(ValidatorType.CRASH_REPORT, itData.address.getAddrID().toString());
        valueMap.put(ValidatorType.DEVICE, groupData.device.getDeviceID().toString());
        valueMap.put(ValidatorType.DRIVER, groupData.driver.getDriverID().toString());
        valueMap.put(ValidatorType.DRIVER_OR_VEHICLE, groupData.driver.getDriverID().toString());
        valueMap.put(ValidatorType.GROUP, groupData.group.getGroupID().toString());
//        valueMap(ValidatorType.NOTE, itData.address.getAddrID().toString());
        valueMap.put(ValidatorType.PERSON, person.getPersonID().toString());

//TODO: following line is causing null pointer on getAlertID()
//        valueMap.put(ValidatorType.RED_FLAG_ALERT, itData.redFlagAlertList.get(0).getAlertID().toString());
        valueMap.put(ValidatorType.REPORT_PREF, reportSchedule.getReportScheduleID().toString());
        valueMap.put(ValidatorType.ROLE, role.getRoleID().toString());
        valueMap.put(ValidatorType.TABLE_PREF, tablePreference.getTablePrefID().toString());
        valueMap.put(ValidatorType.USER, userID.toString());
        valueMap.put(ValidatorType.VEHICLE, groupData.vehicle.getVehicleID().toString());
        valueMap.put(ValidatorType.ZONE, itData.zone.getZoneID().toString());
        System.out.println("4: "+itData.zoneAlert.getAlertID());
        //TODO: following line is causing null pointer exception on getAlertID()
        //valueMap.put(ValidatorType.ZONE_ALERT, itData.zoneAlert.getAlertID().toString());

		return valueMap;
	}

	private static TablePreference getTablePreference(Integer userID) {
    	TablePreferenceHessianDAO tablePreferenceDAO = new TablePreferenceHessianDAO();
    	tablePreferenceDAO.setSiloService(siloServiceCreator.getService());
    	
    	List<TablePreference> list = tablePreferenceDAO.getTablePreferencesByUserID(userID);
    	if (list == null || list.size() == 0) {
            List<Boolean> booleanList = new ArrayList<Boolean>();
            for (int i = 0; i < 7; i++)
            	booleanList.add(Boolean.TRUE);
    		TablePreference tablePreference = new TablePreference(null, userID, TableType.ADMIN_CUSTOMROLE, booleanList);
    		
    		Integer id = tablePreferenceDAO.create(userID, tablePreference);
    		tablePreference.setTablePrefID(id);
    		
    		list = new ArrayList<TablePreference>();
    		list.add(tablePreference);
    	}
    		
		return list.get(0);
	}

	private static ReportSchedule getReportSchedule(Integer acctID, Integer userID, Integer groupID) {
    	ReportScheduleHessianDAO reportHessianDAO = new ReportScheduleHessianDAO();
    	reportHessianDAO.setSiloService(siloServiceCreator.getService());
    	
    	List<ReportSchedule> reportScheduleList = reportHessianDAO.getReportSchedulesByUserID(userID);
    	if (reportScheduleList == null || reportScheduleList.size() == 0) {
            List<Boolean> booleanList = new ArrayList<Boolean>();
            for (int i = 0; i < 7; i++)
            	booleanList.add(Boolean.FALSE);

    		ReportSchedule reportSchedule = new ReportSchedule(null, acctID, 10, userID, null, "test", new Date(), new Date(),
    	            booleanList, null, null, groupID, Status.INACTIVE, null, null);
    		
    		Integer id = reportHessianDAO.create(userID, reportSchedule);
    		reportSchedule.setReportScheduleID(id);
    		
    		
    		reportScheduleList = new ArrayList<ReportSchedule>(); 
    		reportScheduleList.add(reportSchedule);
    		
    	}
		return reportScheduleList.get(0);
	}

	private static Role getRole(Integer acctID) {
    	RoleHessianDAO roleDAO = new RoleHessianDAO();
    	roleDAO.setSiloService(siloServiceCreator.getService());
    	List<Role> roles = roleDAO.getRoles(acctID);
		return roles.get(0);
	}

	private static Person getPerson(Integer personID) {
    	
    	PersonHessianDAO personDAO = new PersonHessianDAO();
    	personDAO.setSiloService(siloServiceCreator.getService());
    	
    	return personDAO.findByID(personID);
	}

	protected void mockLoginUser(Boolean isSuperuser)
    {
    	fleetUser.setPerson(new Person(1, fleetAccountID, TimeZone.getDefault(), 0, "pri@email.com", null, null, null,
                null, null, 0,0,0, null, null, null, null, "first", "m",
                "last", null, Gender.FEMALE, null, null, null, Status.ACTIVE, MeasurementType.ENGLISH,
                FuelEfficiencyType.MPG_US, Locale.US));
    	ProUser proUser = new ProUser(fleetUser, new GrantedAuthority[] {new GrantedAuthorityImpl("ROLE_ADMIN")});
        TestingAuthenticationToken testToken = new TestingAuthenticationToken(
                proUser, proUser.getPassword(),
                proUser.getAuthorities());

        SecurityContext securityContext = new SecurityContextImpl();
        testToken.setAuthenticated(true);
        securityContext.setAuthentication(testToken);
        SecurityContextHolder.setContext(securityContext);
        
    }

    
    @Test
    @Ignore
    public void daoUtilNonSuperuser()
    {
System.out.println("test");    	
		mockLoginUser(false);
		
    	DaoUtilBean dao = new DaoUtilBean();
    	dao.setSiloServiceCreator( siloServiceCreator);
    	dao.setReportServiceCreator( reportServiceCreator);
    	
		superuserDAO = new MockSuperuserDAO();
		((MockSuperuserDAO)superuserDAO).setSuperuser(Boolean.FALSE);
		dao.setSuperuserDAO(superuserDAO);
		dao.setDateFormatBean(new DateFormatBean());
		dao.init();
		dao.setFormatResults(false);

    	
    	Map<String, DaoMethod> methodMap = dao.getMethodMap();
    	
    	for (String methodName: methodMap.keySet()) {
    		DaoMethod daoMethod = methodMap.get(methodName);
    		assertEquals("expected read methods only", CrudType.READ, daoMethod.getCrudType());
    		
//    		System.out.print(methodName);
    		boolean attempted = true;
    		
    		dao.setSelectedMethod(methodName);
    		
    		
    		List<Param> paramList = dao.getParamList();
    		if (paramList != null) {
        		int datecnt = 0;
	    		for (Param param : paramList) {
	    			if (param.getIsAccountID()) {
	    				assertEquals("expected pre-populated account ID", fleetAccountID.toString(), param.getParamValue().toString());
	    			}
	    			else if (param.getUiInputType().equals(UIInputType.DEFAULT)) {
		    			for (ValidatorType validatorType : EnumSet.allOf(ValidatorType.class)) {
		    				if (param.getValidatorType().equals(validatorType)) {
			    				if (validatorType.equals(ValidatorType.DEFAULT)) {
			    					if (param.getParamType().equals(Integer.class)) {
			    						param.setParamValue(Integer.valueOf(10));
			    					}
			    					else if (param.getParamType().equals(Double.class)) {
			    						param.setParamValue(Double.valueOf(0d));
			    					}
			    				}
			    				else {
			    					param.setParamValue(validValueMap.get(validatorType));
			    				}
		    					if (param.getParamValue() != null && 
		    							!param.getValidatorType().equals(ValidatorType.DEFAULT) && 
		    							!param.getValidatorType().equals(ValidatorType.DRIVER_OR_VEHICLE)) {
		    						try {
		    							param.validate(null, null, "XYZ");
		    							fail(methodName + " " + param.getParamName() + " expected validatorException");
		    						}
		    						catch (ValidatorException ve) {
		    						}
		    						try {
		    							param.validate(null, null, "0");
		    							fail(methodName + " " + param.getParamName() + " expected validatorException");
		    						}
		    						catch (ValidatorException ve) {
		    						}
		    						try {
		    							param.validate(null, null, invalidValueMap.get(validatorType));
		    							fail(methodName + " " + param.getParamName() + " expected validatorException for invalid value");
		    						}
		    						catch (ValidatorException ve) {
		    						}
		    						try {
		    							param.validate(null, null, param.getParamValue());
		    						}
		    						catch (ValidatorException ve) {
		    							fail(methodName + " " + param + " Unexpected validatorException");
		    						}
		    					}
		    				}
		    			}
	    			}
	    			else if (param.getUiInputType().equals(UIInputType.CALENDAR)) {
	    				param.setParamValue(dateList.get(datecnt++));
	    			}
	    			else if (param.getUiInputType().equals(UIInputType.BOOLEAN)) {
	    				param.setParamValue(Integer.valueOf(1));
	    			}
	    			else if (param.getUiInputType().equals(UIInputType.SELECT_LIST)) {
	    				param.setParamValue(param.getSelectList().get(0).getValue());
	    			}
	    			else if (param.getUiInputType().equals(UIInputType.PICK_LIST)) {
	    				String[] paramValues = new String[param.getSelectList().size()];
	    				int c = 0;
	    				for (SelectItem item : param.getSelectList()) {
	    					paramValues[c++] = item.getValue().toString();
	    				}
	    				param.setParamValueList(Arrays.asList(paramValues));
	    			}
	    		}
	    		for (Param param : paramList) {
	    			if (param.getParamValue() == null && param.getParamValueList() == null) {
	    	    		attempted = false;
	    	    		break;
	    			}
	    		}
    		}
    		if (!attempted) {
    			assertTrue(methodName + " not attempted ", expectedNoAttempt.contains(methodName));
    		}
    		else {
       			dao.resultsAction();
        		
    			List<List<Result>> results = dao.getRecords();
    			if (expectedNoResults.contains(methodName)) {
        			assertNotNull(methodName + " error msg should be not be null", dao.getErrorMsg());
        			assertTrue(methodName + " error msg should be 304", dao.getErrorMsg().contains("304"));
    				
    			}
    			else if (dao.getErrorMsg() != null && dao.getErrorMsg().equals(DaoUtilBean.NO_RESULTS)) {
    			    continue;
    			}
    			else {
    				assertNotNull(methodName + " results should not be null", results);
//            		System.out.println(" - ATTEMPTED [" + dao.getErrorMsg() + "]");
    				assertNull(methodName + " error msg should be null", dao.getErrorMsg());
    			}
    			

    			Method method = daoMethod.getMethod();
    			boolean isList = method.getReturnType().isArray();
    			if (isList) 
    				assertTrue("expected one or fewer results for non list return type", results.size() < 2);

    			
    		}
    	}
    	
    	
    }
}

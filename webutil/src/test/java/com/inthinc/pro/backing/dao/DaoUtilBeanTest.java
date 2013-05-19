package com.inthinc.pro.backing.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.faces.model.SelectItem;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.context.SecurityContextImpl;
import org.springframework.security.providers.TestingAuthenticationToken;

import com.inthinc.pro.backing.dao.impl.ReportServiceImpl;
import com.inthinc.pro.backing.dao.impl.SiloServiceImpl;
import com.inthinc.pro.backing.dao.model.CrudType;
import com.inthinc.pro.backing.dao.model.DaoMethod;
import com.inthinc.pro.backing.dao.model.Param;
import com.inthinc.pro.backing.dao.ui.UIInputType;
import com.inthinc.pro.dao.SuperuserDAO;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.Gender;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;
import com.inthinc.pro.security.userdetails.ProUser;


public class DaoUtilBeanTest {

	private DaoUtilBean dab;
	private SuperuserDAO superuserDAO;
	
	Param getDriverNoteExpectedParams[] = {
		new Param("driverID", Integer.class, 0, Integer.class, "10"),	
		new Param("startDate", Long.class, 1, java.util.Date.class, new Date()),	
		new Param("endDate", Long.class, 2, java.util.Date.class, new Date()),	
		new Param("showExcluded", Integer.class, 3, Boolean.class, "1"),	
		new Param("types", Integer[].class, 4, com.inthinc.pro.backing.dao.ui.EventTypeList.class, "1,2,3"),	
	};
	UIInputType getDriverNoteExpectedUIType[] = {
			UIInputType.DEFAULT,	
			UIInputType.CALENDAR,	
			UIInputType.CALENDAR,	
			UIInputType.BOOLEAN,	
			UIInputType.PICK_LIST
	};
	
	Param getVehicleReportPageExpectedParams[] = {
		new Param("groupID", Integer.class, 0, Integer.class, Integer.valueOf(10)),	
		new Param("startRow", Integer.class, 1, com.inthinc.pro.model.pagination.PageParams.class, "0"),	
		new Param("endRow", Integer.class, 1, com.inthinc.pro.model.pagination.PageParams.class, "10"),	
	};

	Param updateAccountExpectedParams[] = {
		new Param("accountID", Integer.class, 0, Integer.class, Integer.valueOf(10)),	
		new Param("acctID", Integer.class, 1, com.inthinc.pro.model.Account.class, Integer.valueOf(10)),	
		new Param("name", String.class, 1, com.inthinc.pro.model.Account.class, "testAccount"),	
		new Param("status", com.inthinc.pro.model.Status.class, 1, com.inthinc.pro.model.Account.class, Integer.valueOf(10)),	
		new Param("unkDriverID", Integer.class, 1, com.inthinc.pro.model.Account.class, Integer.valueOf(10)),	
        new Param("mailID", Integer.class, 1, com.inthinc.pro.model.Account.class, Integer.valueOf(10)),   
        new Param("billID", Integer.class, 1, com.inthinc.pro.model.Account.class, Integer.valueOf(10)),   
        new Param("hos", com.inthinc.pro.model.AccountHOSType.class, 1, com.inthinc.pro.model.Account.class, Integer.valueOf(0)),   
        new Param("zonePublishDate", Long.class, 1, com.inthinc.pro.model.Account.class, Long.valueOf(0)),   
	};
	
	List<String>modelClassExcludeList = Arrays.asList(
			"getDeviceReportCount",
			"getDriverReportCount",
			"getVehicleReportCount",
			"getIdlingReportCount",
			"getIdlingVehicleReportCount",
			"getDriverEventCount",
			"getRedFlagsCount",
			"getRfidsForBarcode",
			"getSensitivityMaps",	// TODO
			"getID",
			"getTextMsgCount"
	);

	@Before
	public void setUp() throws Exception {
		mockLoginUser(true);
		dab = new DaoUtilBean();
		superuserDAO = new MockSuperuserDAO();
		((MockSuperuserDAO)superuserDAO).setSuperuser(Boolean.TRUE);
		dab.setSuperuserDAO(superuserDAO);
		dab.setDateFormatBean(new DateFormatBean());
	}
	
	@Test
	public void initForSuperuser() {
		((MockSuperuserDAO)superuserDAO).setSuperuser(Boolean.TRUE);
		dab.init();

		// for superuser, all methods are available
		Map<String, DaoMethod> methodMap = dab.getMethodMap();
		Method[] siloMethods = new SiloServiceImpl().getClass().getMethods();
		for (Method method : siloMethods) {
			if (dab.getExcludedMethods().contains(method.getName()))
				continue;
			assertTrue(method.getName(), methodMap.get(method.getName()) != null);
		}
			
		
		Method[] reportMethods = new ReportServiceImpl().getClass().getMethods();
		for (Method method : reportMethods) {
			if (dab.getExcludedMethods().contains(method.getName()))
				continue;
			assertTrue(method.getName(), methodMap.get(method.getName()) != null);
		}
			
		
		List<CrudType> excludedTypes = dab.getExcludedTypes();
		assertTrue(excludedTypes.size() == 1);
		assertEquals(CrudType.NOT_AVAILABLE, excludedTypes.get(0));
		

	}
    @Test
    public void initForNonSuperuser() {
        ((MockSuperuserDAO)superuserDAO).setSuperuser(Boolean.FALSE);
        dab.init();
        Map<String, DaoMethod> methodMap = dab.getMethodMap();
        for (DaoMethod daoMethod : methodMap.values()) {
            assertEquals("only read methods available " + daoMethod.getMethod().getName(), CrudType.READ, daoMethod.getCrudType());
        }
        
        dab.setSelectedMethod("getAcct");
        List<Param> paramList = dab.getParamList();
        assertEquals("first param should be account id", Boolean.TRUE, paramList.get(0).getIsAccountID());
        assertEquals("first param should be account id", "1", paramList.get(0).getParamValue());
    }

	@Test
	public void methodList() {
		dab.setExcludedTypes(Arrays.asList(CrudType.NOT_AVAILABLE, CrudType.CREATE, CrudType.DELETE, CrudType.UPDATE, CrudType.READ_RESTRICTED));
    	dab.initMethodMap();
    	
		Map<String, DaoMethod> methodMap = dab.getMethodMap();
		
		for (DaoMethod daoMethod : methodMap.values()) {
			assertEquals("only read methods available " + daoMethod.getMethod().getName(), CrudType.READ, daoMethod.getCrudType());
			if (modelClassExcludeList.contains(daoMethod.getMethod().getName()))
				continue;
			assertTrue("model class should be specified " + daoMethod.getMethod().getName(), daoMethod.getModelClass() != java.util.Map.class);
		}

		List<SelectItem> selectList = dab.getMethodSelectList();
		assertEquals("selectList is not correct", methodMap.size(), selectList.size()-1);
	}
	
	@Test
	public void testArgsFromParams() throws Exception {
		
		dab.setExcludedTypes(Arrays.asList(CrudType.NOT_AVAILABLE));
    	dab.initMethodMap();
		Map<String, DaoMethod> methodMap = dab.getMethodMap();
		assertTrue(methodMap.size() >0);
		
		dab.setSelectedMethod("getDriverNote");
		
		List<Param> plist = dab.getParamList();
		// set the param values
		int index = 0;
		for (Param param : plist) {
			Param expectedParam = getDriverNoteExpectedParams[index++];
			param.setParamValue(expectedParam.getParamValue());
			
		}
		
		Object args[] = dab.getArgsFromParamList();
		
		assertEquals(dab.getSelectedMethod() + " num args" , getDriverNoteExpectedParams.length, args.length);

	
	
		dab.setSelectedMethod("updateAcct");
		
		plist = dab.getParamList();
		// set the param values
		index = 0;
		for (Param param : plist) {
			Param expectedParam = updateAccountExpectedParams[index++];
			param.setParamValue(expectedParam.getParamValue());
		}
		
		args = dab.getArgsFromParamList();
		assertEquals(dab.getSelectedMethod() + " num args" , 2, args.length);
	
	}

	
	@Test
	public void testParamList() {
		
		dab.setExcludedTypes(Arrays.asList(CrudType.NOT_AVAILABLE));
    	dab.initMethodMap();
		Map<String, DaoMethod> methodMap = dab.getMethodMap();
		assertTrue(methodMap.size() >0);
		
		dab.setSelectedMethod("getDriverNote");
		List<Param> plist = dab.getParamList();
		assertEquals(dab.getSelectedMethod() + " num params" , getDriverNoteExpectedParams.length, plist.size());
		
		int index = 0;
		for (Param param : plist) {
			Param expectedParam = getDriverNoteExpectedParams[index];
			assertEquals("param name", expectedParam.getParamName(), param.getParamName());
			assertEquals(expectedParam.getParamName() + " param type", expectedParam.getParamType(), param.getParamType());
			assertEquals(expectedParam.getParamName() + " param index", expectedParam.getIndex(), param.getIndex());
			assertEquals(expectedParam.getParamName() + " param actual type", expectedParam.getInputType(), param.getInputType());
			
			UIInputType expectedUIInputType = getDriverNoteExpectedUIType[index++];
			assertEquals(expectedParam.getParamName() + " UIInputType", expectedUIInputType, param.getUiInputType());
			
		}

		dab.setSelectedMethod("getVehicleReportPage");
		plist = dab.getParamList();
		assertEquals(dab.getSelectedMethod() + " num params" , getVehicleReportPageExpectedParams.length, plist.size());
		
		index = 0;
		for (Param param : plist) {
			Param expectedParam = getVehicleReportPageExpectedParams[index++];
			assertEquals("param name", expectedParam.getParamName(), param.getParamName());
			assertEquals("param type", expectedParam.getParamType(), param.getParamType());
			assertEquals("param index", expectedParam.getIndex(), param.getIndex());
//			assertEquals("param actual type", expectedParam.getInputType(), param.getInputType());
		}
		

		dab.setSelectedMethod("updateAcct");
		plist = dab.getParamList();
		assertEquals(dab.getSelectedMethod() + " num params" , updateAccountExpectedParams.length, plist.size());
		
		index = 0;
		for (Param param : plist) {
			Param expectedParam = updateAccountExpectedParams[index++];
			assertEquals("param name", expectedParam.getParamName(), param.getParamName());
			assertEquals("param type", expectedParam.getParamType(), param.getParamType());
			assertEquals("param index", expectedParam.getIndex(), param.getIndex());
//			assertEquals("param actual type", expectedParam.getInputType(), param.getInputType());
		}
		



	}
	
	@Test
	public void nullParam() throws Exception {
		
		dab.setExcludedTypes(Arrays.asList(CrudType.NOT_AVAILABLE));
    	dab.initMethodMap();
		Map<String, DaoMethod> methodMap = dab.getMethodMap();
		assertTrue(methodMap.size() >0);
		
		dab.setSelectedMethod("getDeviceReportCount");
		List<Param> plist = dab.getParamList();
		assertTrue(plist.size() == 1);

		plist.get(0).setParamValue("1"); // groupID
		
		Object args[] = dab.getArgsFromParamList();
		
		assertTrue(args != null);
		
	}
	
    protected void mockLoginUser(Boolean isSuperuser)
    {
    	User user = new User(1, 1, null, Status.ACTIVE, "test", "testpassword", 1);
    	user.setPerson(new Person(1, 1, TimeZone.getDefault(),  0, "pri@example.com", null, null, null,
                null, null, 0,0,0, null, null, null, null, "first", "m",
                "last", null, Gender.FEMALE, null, null, null, Status.ACTIVE, MeasurementType.ENGLISH,
                FuelEfficiencyType.MPG_US, Locale.US));
    	ProUser proUser = new ProUser(user, new GrantedAuthority[] {new GrantedAuthorityImpl("ROLE_ADMIN")});
        TestingAuthenticationToken testToken = new TestingAuthenticationToken(
                proUser, proUser.getPassword(),
                proUser.getAuthorities());

        SecurityContext securityContext = new SecurityContextImpl();
        testToken.setAuthenticated(true);
        securityContext.setAuthentication(testToken);
        SecurityContextHolder.setContext(securityContext);
        
    }

}

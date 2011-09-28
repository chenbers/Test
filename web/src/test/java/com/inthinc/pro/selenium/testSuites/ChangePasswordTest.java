package com.inthinc.pro.selenium.testSuites;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.automation.AutomationPropertiesBean;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.utils.AutomationHessianFactory;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.pageObjects.PageNotificationsRedFlags;

public class ChangePasswordTest extends WebTest {
	private static final Integer day = 60 * 60 * 24;
	private static Long _120DaysAgo;
	private static Long _90DaysAgo;
	private static Long _15DaysAgo;
	private Integer userID = 31613;
	private static final Long now = System.currentTimeMillis()/1000;
	private Map<String, Object> user;
	private PageLogin login;
	private PageNotificationsRedFlags redFlags;
	
	private static String weakPassword = "123456";
	private static String strongPassword = ")(*098poiPOI";
	private static String initialPassword = "password";
	private static String hashOfStringPASSWORD = "JcxjA4COcypWBgdB1+v4VXPdbUf9Nzv4QQ9a8uL3IuCI1JZpLy2J08SO2A9rsHGI";
	
	
	public void updateUser(){
	    AutomationPropertiesBean apb = getAutomationPropertiesBean();
	    Addresses address = Addresses.getSilo(apb.getSilo());
		SiloService portalProxy = (new AutomationHessianFactory()).getPortalProxy(address);
		portalProxy.updateUser(userID, user);
	}
	
	public void resetLoginExpiry(Long daysAgo){
		user.put("lastLogin", daysAgo);
		updateUser();
	}
	
	public void resetPasswordExpiry(Long daysAgo){
		user.put("passwordDT", daysAgo);
		updateUser();
	}
	
	public void resetUser(){
		user.put("password", hashOfStringPASSWORD);
		user.put("lastLogin", now);
		user.put("passwordDT", now);
		user.put("status", 1);
		updateUser();
	}
	
	
	
	@Before
	public void beforeStuff(){
		user = new HashMap<String, Object>();
		_120DaysAgo = now - day * 119;
		_90DaysAgo = now - day * 89;
		_15DaysAgo = now - day * 14;
		resetUser();
		login = new PageLogin();
		redFlags = new PageNotificationsRedFlags();
	}
	
	@Ignore //TODO: dTanner: hessian calls cannot be made from cloudshare ip's
	@Test
	public void passwordStrengthTest(){
		resetPasswordExpiry(_90DaysAgo);
		resetLoginExpiry(_15DaysAgo);
		pause(5, "");
		login.loginProcess("passwords", initialPassword);
		redFlags._popUp().updatePasswordReminder()._link().changePassword().click();
		assertTrue(redFlags._popUp().changePassword()._textField().currentPassword().isPresent()==true,"The popup didn't appear");
		redFlags._popUp().changePassword()._textField().currentPassword().type(initialPassword);
		redFlags._popUp().changePassword()._textField().newPassword().type(weakPassword);
		redFlags._popUp().changePassword()._textField().confirmNewPassword().type(weakPassword);
		redFlags._popUp().changePassword()._text().passwordStrength().compare("Minimum Password Strength Not Met");
		redFlags._popUp().changePassword()._button().change().click();
		pause(30, "");
		redFlags._popUp().changePassword()._textField().currentPassword().assertVisibility(true);
		redFlags._popUp().changePassword()._text().newPasswordError().compare("Minimum Password Strength Not Met");
	}
	
	@Ignore //TODO: dTanner: hessian calls cannot be made from cloudshare ip's
	@Test
	public void onlyHaveToChangeOnce(){
		resetPasswordExpiry(_90DaysAgo);
		resetLoginExpiry(_15DaysAgo);
		pause(5, "");
		login.loginProcess("passwords", initialPassword);
		redFlags._popUp().updatePasswordReminder()._link().changePassword().click();
		assertTrue(redFlags._popUp().changePassword()._textField().currentPassword().isPresent()==true,"The popup didn't appear");
		redFlags._popUp().changePassword()._textField().currentPassword().type(initialPassword);
		redFlags._popUp().changePassword()._textField().newPassword().type(strongPassword);
		redFlags._popUp().changePassword()._textField().confirmNewPassword().type(strongPassword);
		redFlags._popUp().changePassword()._button().change().click();
		redFlags._link().notifications().click();
		pause(30, "");
		redFlags._popUp().updatePasswordReminder()._link().changePassword().assertVisibility(false);
	}

}

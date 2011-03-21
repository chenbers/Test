/****************************************************************************************
 * Purpose: Used to process links selected on the Master Heading screen 
 * most functions are used regardless of what Main menu item is selected 
 * as Master Heading screen does not change. 
 * @author larringt , dtanner
 * Last Update:  11/18/Added comments and made changes to adhere to Java Coding Standards
 */

package com.inthinc.pro.web.selenium.portal.Masthead;

import static org.junit.Assert.*;

import com.inthinc.pro.web.selenium.CoreMethodLib;
import com.inthinc.pro.web.selenium.SeleniumServerLib;
import com.inthinc.pro.web.selenium.GlobalSelenium;
import com.inthinc.pro.web.selenium.Debug.ErrorCatcher;
import com.inthinc.pro.web.selenium.portal.Login.Login;


public class Masthead extends SeleniumServerLib{	
	
	
	
	private String version_text;
	private String copyright_text_actual;
	
	protected static CoreMethodLib selenium;
	
	public Masthead(){
			this(GlobalSelenium.getYourOwn());
		}
	
	public Masthead(GlobalSelenium tvar ){
			this(tvar.getSelenium());
		}
	
	public Masthead( CoreMethodLib sel ){
			selenium = sel;
		}
	
	
	public String get_version(){
			version_text = selenium.getText(MastheadEnum.VERSION, "Version return");
			return version_text;	
		}
	
	public String get_copyright(){
			copyright_text_actual = selenium.getText(MastheadEnum.COPYRIGHT, "Copyright text");
			return copyright_text_actual;
		}
	
	public void click_my_account(){
			selenium.click(MastheadEnum.MY_ACCOUNT, "My Account click");
			selenium.waitForPageToLoad("30000", "My Account click");
			selenium.getLocation("tiwipro/app/account","My Account click");		
		}
	
	public void click_my_messages(){
			selenium.click(MastheadEnum.MY_MESSAGES, "My Messages click");
			selenium.waitForPageToLoad("30000", "My Messages click");
			selenium.getLocation("tiwipro/app/messages/", "My Messages click");
		}
	
	public void click_logout(){
			selenium.click(MastheadEnum.LOGOUT, "Logout click");
			selenium.waitForPageToLoad("30000", "Logout click");
			selenium.getLocation("tiwipro/login", "Logout click");
		}
	
	public void click_support(){
			selenium.click(MastheadEnum.SUPPORT, "Support click");
			selenium.selectWindow(null);
		}
	
	public void click_legal(){
			selenium.click(MastheadEnum.LEGAL, "Legal Notice click");
			selenium.waitForPopUp("popup", "30000");
			selenium.selectPopUp("");
			selenium.getText(MastheadEnum.LEGAL_NOTICE, "Legal Notice click");
			selenium.close();
			selenium.selectWindow(null);
		}
	
	public void click_privacy(){
			selenium.click(MastheadEnum.PRIVACY, "Privacy Policy click");
			selenium.waitForPopUp("popup", "30000");
			selenium.selectPopUp("");
			selenium.getText(MastheadEnum.PRIVACY_POLICY, "Privacy Policy text");
			selenium.close();
			selenium.selectWindow(null);
		}
	
	public void click_help(String help_page){
			if (help_page.indexOf(".htm")== -1){help_page += ".htm";}
			selenium.click(MastheadEnum.HELP, "Help click");
			selenium.waitForPageToLoad("30000", "Help click");
		}

	public void ck_header(){
			selenium.isElementPresent(MastheadEnum.LOGO, "Logo element present");
			selenium.isElementPresent(MastheadEnum.HELP, "Help link present");
			selenium.isElementPresent(MastheadEnum.MY_MESSAGES, "My Messages element present");
			selenium.isElementPresent(MastheadEnum.MY_ACCOUNT, "My Account element present");
			selenium.isElementPresent(MastheadEnum.LOGOUT, "Log Out link present");
			
			selenium.getText(MastheadEnum.HELP, "Help link text");
			selenium.getText(MastheadEnum.MY_MESSAGES, "My Messages link text");
			selenium.getText(MastheadEnum.MY_ACCOUNT, "My Account text");
			selenium.getText(MastheadEnum.LOGOUT, "Log Out text");
		}
	
	public void ck_footer(){
			selenium.isElementPresent(MastheadEnum.COPYRIGHT, "Copyright element present");
			selenium.isElementPresent(MastheadEnum.PRIVACY, "Privacy Policy element present");
			selenium.isElementPresent(MastheadEnum.LEGAL, "Legal Notice element present");
			selenium.isElementPresent(MastheadEnum.SUPPORT, "Support element present");
			selenium.isElementPresent(MastheadEnum.VERSION, "Version element present");
			
			selenium.getText(MastheadEnum.COPYRIGHT, "Copyright text");
			selenium.getText(MastheadEnum.PRIVACY, "Privacy Policy text");
			selenium.getText(MastheadEnum.LEGAL, "Legal Notice text");
			selenium.getText(MastheadEnum.SUPPORT, "Support text");
		}
	
	public void test_self_before_login(){
			if (selenium.getLocation().indexOf("/tiwipro/login")==-1){selenium.open("login", "Open login page");}
			ck_footer();
			click_privacy();
			click_legal();
		}
	
	public void test_self_after_login(){
			ck_footer();
			ck_header();
			click_logout();
		}
	

	public ErrorCatcher get_errors(){
			return selenium.getErrors();
		}
		
	public CoreMethodLib get_selenium(){
			return selenium;
		}
	
	public static void main( String[] args ){
		String errors = "";
//		try {
//			Masthead.setUp();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		try{
			Masthead masthead = new Masthead();
			Login login = new Login(masthead.get_selenium());
			Masthead.selenium.start();
			masthead.test_self_before_login();
			login.portal_log_in_process("0001", "password");
			masthead.test_self_after_login();
			
			errors = masthead.get_errors().get_errors().toString();
			System.out.println(errors);
			Masthead.selenium.close();
			Masthead.selenium.stop();
			
		}catch(Exception e){
			e.printStackTrace();
		}
//		try{
//			Masthead.tearDown();
//		}catch(Exception e){
//			e.printStackTrace();
//		}
		
		assertTrue(errors=="{}");
	}
}



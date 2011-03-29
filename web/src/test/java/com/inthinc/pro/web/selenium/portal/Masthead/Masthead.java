/****************************************************************************************
 * Purpose: Used to process links selected on the Master Heading screen 
 * most functions are used regardless of what Main menu item is selected 
 * as Master Heading screen does not change. 
 * @author larringt , dtanner
 * Last Update:  11/18/Added comments and made changes to adhere to Java Coding Standards
 */

package com.inthinc.pro.web.selenium.portal.Masthead;

import com.inthinc.pro.web.selenium.CoreMethodLib;
import com.inthinc.pro.web.selenium.SeleniumServerLib;
import com.inthinc.pro.web.selenium.GlobalSelenium;
import com.inthinc.pro.web.selenium.Debug.ErrorCatcher;

public class Masthead 
//extends SeleniumServerLib
{	
	
	private String version_text;
	private String copyright_text_actual;
	
	protected static CoreMethodLib selenium;
	
	public Masthead(){
		selenium = GlobalSelenium.getSelenium();
	}
	
	public String get_version(){
		version_text = selenium.getText(MastheadEnum.VERSION);
		return version_text;	
	}
	
	public String get_copyright(){
		copyright_text_actual = selenium.getText(MastheadEnum.COPYRIGHT);
		return copyright_text_actual;
	}

	public void click_my_account(){
		selenium.click(MastheadEnum.MY_ACCOUNT);
		selenium.waitForPageToLoad(MastheadEnum.MY_ACCOUNT);
		selenium.getLocation(MastheadEnum.URL);		
	}
	
	public void click_my_messages(){
		selenium.click(MastheadEnum.MY_MESSAGES);
		selenium.waitForPageToLoad(MastheadEnum.MY_MESSAGES);
		selenium.getLocation("tiwipro/app/messages/", "My Messages click");//TODO: jwimmer: DTanner: no hard coded Strings in the FRAMEWORK code
	}
	
	public void click_logout(){
		selenium.click(MastheadEnum.LOGOUT);
		selenium.waitForPageToLoad(MastheadEnum.LOGOUT);
		selenium.getLocation("tiwipro/login", "Logout click");//TODO: jwimmer: DTanner: no hard coded Strings in the FRAMEWORK code
	}
	
	public void click_support(){
		selenium.click(MastheadEnum.SUPPORT);
		selenium.selectWindow(null);
	}
	
	public void click_legal(){
		selenium.click(MastheadEnum.LEGAL);
		selenium.waitForPopUp("popup", CoreMethodLib.PAGE_TIMEOUT.toString());
		selenium.selectPopUp("");
		selenium.getText(MastheadEnum.LEGAL_NOTICE);
		selenium.close();
		selenium.selectWindow(null);
	}
	
	public void click_privacy(){
		selenium.click(MastheadEnum.PRIVACY);
		selenium.waitForPopUp("popup", CoreMethodLib.PAGE_TIMEOUT.toString());
		selenium.selectPopUp("");
		selenium.getText(MastheadEnum.PRIVACY_POLICY);
		selenium.close();
		selenium.selectWindow(null);
	}
	
	public void click_help(String help_page){
		if (help_page.indexOf(".htm")== -1){help_page += ".htm";}//TODO: jwimmer: DTanner: no hard coded Strings in the FRAMEWORK code... I'm not sure this line is doing anything (affective) anyway?
		selenium.click(MastheadEnum.HELP);
		selenium.waitForPageToLoad(MastheadEnum.HELP);
	}

	public void ck_header(){
		selenium.isElementPresent(MastheadEnum.LOGO);
		selenium.isElementPresent(MastheadEnum.HELP);
		selenium.isElementPresent(MastheadEnum.MY_MESSAGES);
		selenium.isElementPresent(MastheadEnum.MY_ACCOUNT);
		selenium.isElementPresent(MastheadEnum.LOGOUT);
		
		selenium.getText(MastheadEnum.HELP);
		selenium.getText(MastheadEnum.MY_MESSAGES);
		selenium.getText(MastheadEnum.MY_ACCOUNT);
		selenium.getText(MastheadEnum.LOGOUT);
	}
	
	public void ck_footer(){
		selenium.isElementPresent(MastheadEnum.COPYRIGHT);
		selenium.isElementPresent(MastheadEnum.PRIVACY);
		selenium.isElementPresent(MastheadEnum.LEGAL);
		selenium.isElementPresent(MastheadEnum.SUPPORT);
		selenium.isElementPresent(MastheadEnum.VERSION);
		
		selenium.getText(MastheadEnum.COPYRIGHT);
		selenium.getText(MastheadEnum.PRIVACY);
		selenium.getText(MastheadEnum.LEGAL);
		selenium.getText(MastheadEnum.SUPPORT);
	}
	public void page_validate() {
	    this.ck_footer();
	    this.ck_header();
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
}



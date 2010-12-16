/****************************************************************************************
 * Purpose: Used to process links selected on the Master Heading screen 
 * most functions are used regardless of what Main menu item is selected 
 * as Master Heading screen does not change. 
 * @author larringt , dtanner
 * Last Update:  11/18/Added comments and made changes to adhere to Java Coding Standards
 */

package com.inthinc.pro.web.selenium.portal.Masthead;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.apache.commons.lang.StringEscapeUtils;

import com.inthinc.pro.web.selenium.Core;
import com.inthinc.pro.web.selenium.Selenium_Server;
import com.inthinc.pro.web.selenium.Singleton;
import com.inthinc.pro.web.selenium.Debug.Error_Catcher;
import com.inthinc.pro.web.selenium.portal.Login.Login;


public class Masthead extends Selenium_Server{	
	

	private final String footer_form = "//form[@id='footerForm']";
	private final String header_form = "//form[@id='headerForm']";
	private final String header_nav = "/div[@id='horz_nav']";
	
	private final String help_loc="1", messages_loc="2", account_loc="3", logout_loc="4";
	private final String copyright_loc="1", privacy_loc="3", legal_loc="5", support_loc="7", version_loc="2"; 
	
	
	private final String account_id = "headerForm:headerMyAccount";
	private final String account_class = "tb-account";
	private final String account_href = "/tiwipro/app/account";
	private final String account_xpath = header_form+header_nav+"/ul/li["+account_loc+"]/a";
	private final String account_xpath_direct = "//a[@href='"+account_href+"']";
	private final String account_link = "link=My Account";
		
	private final Calendar cal = Calendar.getInstance();
	private final String copyright_class = "first";
	private final String copyright_xpath = footer_form+"/ul/li["+copyright_loc+"]";
	private final String copyright_xpath_direct = "//li[@class='"+copyright_class+"']";
	private final String copyright_text = StringEscapeUtils.unescapeHtml("&#169;" + String.valueOf(cal.get(Calendar.YEAR)) + " inthinc");
//	private final String copyright_text = StringEscapeUtils.unescapeHtml("&copy;2009 inthinc");
	
	private final String help_class = "tb-help";
	private final String help_href = "/tiwipro/secured/lochelp/en/en/";
	private final String help_xpath = header_form+header_nav+"/ul/li["+help_loc+"]/a";
	private final String help_xpath_partial = "//a[@href='"+help_href;
	private final String help_link = "link=Help";
		
	private final String legal_id = "footerForm:legal";
	private final String legal_title = "Legal Notice";
	private final String legal_link = "link="+legal_title;
	private final String legal_xpath = footer_form+"/ul/li["+legal_loc+"]/a";
	private final String legal_xpath_direct = "//a[@id='"+legal_id+"']";
	private final String legal_notice = "THE MATERIAL AND INFORMATION ON THIS WEBSITE ARE BEING PROVIDED FOR YOUR INFORMATION ONLY, \"AS IS, WHERE IS,\" WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION ANY WARRANTY FOR INFORMATION, SERVICES OR PRODUCTS PROVIDED BY OR THROUGH THIS WEBSITE. INTHINC EXPRESSLY DISCLAIMS ANY IMPLIED WARRANTY FOR MERCHANTABILITY, SATISFACTORY QUALITY, FITNESS FOR A PARTICULAR PURPOSE, EXPECTATION OF PRIVACY OR NON-INFRINGEMENT.  YOU MAY USE THIS WEBSITE AT YOUR OWN RISK. EXCEPT AS REQUIRED BY LAW, INTHINC WILL NOT BE RESPONSIBLE OR LIABLE FOR ANY DAMAGE OR INJURY CAUSED BY, AMONG OTHER THINGS, ANY FAILURE OF PERFORMANCE; ERROR, OMISSION, INTERRUPTION, DELETION, DEFECT OR DELAY IN OPERATION OR TRANSMISSION; COMPUTER VIRUS; COMMUNICATION LINE FAILURE; THEFT, DESTRUCTION OR UNAUTHORIZED ACCESS TO, ALTERATION OF OR USE OF ANY DATA. YOU, NOT INTHINC, ASSUME THE ENTIRE COST OF ALL NECESSARY SERVICING, REPAIR OR CORRECTION DUE TO YOUR USE OF THIS WEBSITE.  INTHINC WILL NOT BE LIABLE FOR THE ACTIONS OF THIRD PARTIES.";
	
	private final String login_logo_id = "login_logo";
	private final String login_logo_xpath = "//body/div[1]/div/img";
	private final String login_logo_xpath_direct = "//div[@id='"+login_logo_id+"']";
	
	private final String logo_id = "headerForm:headerInitDashboard";
	private final String logo_href = "/tiwipro/app/dashboard/";
	private final String logo_xpath = header_form+"/div[@id='logo']/a/img";
	private final String logo_xpath_direct = "//a[@id='"+logo_id+"']/img";
	
	private final String logout_class = "tb-logout";
	private final String logout_href = "/tiwipro/logout";
	private final String logout_xpath = header_form+header_nav+"/ul/li["+logout_loc+"]/a";
	private final String logout_xpath_direct = "//a[@href='"+logout_href+"']";
	private final String logout_link = "link=Log Out";
	
	private final String messages_id = "headerForm:headerMyMessages";
	private final String messages_class = "tb-messages";
	private final String messages_href = "/tiwipro/app/messages/";
	private final String messages_xpath = header_form+header_nav+"/ul/li["+messages_loc+"]/a";
	private final String messages_xpath_direct = "//a[@href='"+messages_href+"']";
	private final String messages_link = "link=My Messages";
	
	private final String privacy_id = "footerForm:privacy";
	private final String privacy_title = "Privacy Policy";
	private final String privacy_link = "link="+privacy_title;
	private final String privacy_xpath = footer_form+"/ul/li["+privacy_loc+"]/a";
	private final String privacy_xpath_direct = "//a[@title='"+privacy_title+"']";
	private final String privacy_policy = StringEscapeUtils.unescapeHtml("We at inthinc take your privacy very seriously. This Privacy Policy describes how we handle personally identifiable information (&8220;Personal Information&8221;) and other information that we collect or receive through the operation of inthinc products and services, any websites, portals, telecommunications, technical or customer service support or information and as part of any of our other business activities.  &8220;Personal Information&8221; in this context is information that is identifiable to a particular person, including when the information is combined with other information about that individual.  We endeavor to carefully guard and protect the privacy of any Personal Information that we collect or otherwise receive.");
	
	private final String support_id = "footerForm:customerSupport";
	private final String support_title = "Support";
	private final String support_link = "link="+support_title;
	private final String support_xpath = footer_form+"/ul/li["+support_loc+"]";
	private final String support_xpath_direct = "//a[@id='"+support_id+"']";
	
	private final String version_id = "footerForm:version";
	private final String version_class = "last";
	private final String version_xpath = footer_form+"/ul/li["+version_loc+"']";
	private final String version_xpath_class = footer_form+"/ul/li[@class='"+version_class+"']";
	private final String version_xpath_direct = "//span[@id='"+version_id+"";
	private final String pagelistid = "//td[@onclick=\"Event.fire(this, 'rich:datascroller:onscroll'";
	
	
	private String version_text;
	private String copyright_text_actual;
	
	protected static Core selenium;
	
	public Masthead(){
			this(Singleton.getSingleton().getSelenium());
		}
	
	public Masthead(Singleton tvar ){
			this(tvar.getSelenium());
		}
	
	public Masthead( Core sel ){
			selenium = sel;
		}
	
	
	public String get_version(){
			version_text = selenium.getText(version_id, "Version return");
			return version_text;	
		}
	
	public String get_copyright(){
			copyright_text_actual = selenium.getText(copyright_xpath, copyright_text, "Copyright text");
			return copyright_text_actual;
		}
	
	public void click_my_account(){
			selenium.click(account_link, "My Account click");
			selenium.waitForPageToLoad("30000", "My Account click");
			selenium.getLocation("tiwipro/app/account","My Account click");		
		}
	
	public void click_my_messages(){
			selenium.click(messages_link, "My Messages click");
			selenium.waitForPageToLoad("30000", "My Messages click");
			selenium.getLocation("tiwipro/app/messages/", "My Messages click");
		}
	
	public void click_logout(){
			selenium.click(logout_link, "Logout click");
			selenium.waitForPageToLoad("30000", "Logout click");
			selenium.getLocation("tiwipro/login", "Logout click");
		}
	
	public void click_support(){
			selenium.click(support_link, "Support click");
			selenium.selectWindow(null);
		}
	
	public void click_legal(){
			selenium.click(legal_link, "Legal Notice click");
			selenium.waitForPopUp("popup", "30000");
			selenium.selectPopUp("");
			selenium.getText("//p[8]", legal_notice, "Legal Notice click");
			selenium.close();
			selenium.selectWindow(null);
		}
	
	public void click_privacy(){
			selenium.click(privacy_link, "Privacy Policy click");
			selenium.waitForPopUp("popup", "30000");
			selenium.selectPopUp("");
			selenium.getText("//p[4]", privacy_policy, "Privacy Policy text");
			selenium.close();
			selenium.selectWindow(null);
		}
	
	public void click_help(String help_page){
			if (help_page.indexOf(".htm")== -1){help_page += ".htm";}
			selenium.click(help_link+help_page, "Help click");
			selenium.waitForPageToLoad("30000", "Help click");
		}
	
	

	public void ck_header(){
			selenium.isElementPresent(logo_id, "Logo element present");
			selenium.isElementPresent(help_link, "Help link present");
			selenium.isElementPresent(messages_id, "My Messages element present");
			selenium.isElementPresent(account_id, "My Account element present");
			selenium.isElementPresent(logout_link, "Log Out link present");
			
			selenium.getText(help_link, "Help", "Help link text");
			selenium.getText(messages_id, "My Messages", "My Messages link text");
			selenium.getText(account_id, "My Account", "My Account text");
			selenium.getText(logout_link, "Log Out", "Log Out text");
		}
	
	public void ck_footer(){
			selenium.isElementPresent(copyright_xpath, "Copyright element present");
			selenium.isElementPresent(privacy_id, "Privacy Policy element present");
			selenium.isElementPresent(legal_id, "Legal Notice element present");
			selenium.isElementPresent(support_id, "Support element present");
			selenium.isElementPresent(version_id, "Version element present");
			
			selenium.getText(copyright_xpath, copyright_text, "Copyright text");
			selenium.getText(privacy_id, privacy_title, "Privacy Policy text");
			selenium.getText(legal_id, legal_title, "Legal Notice text");
			selenium.getText(support_id, support_title, "Support text");
		}
	
	public void test_self_before_login(){
			if (selenium.getLocation().indexOf("/tiwipro/login")==-1){selenium.open("login", "Open login page");}
			ck_footer();
		}
	
	public void test_self_after_login(){
			ck_footer();
			ck_header();
			click_logout();
		}
	

	public Error_Catcher get_errors(){
			return selenium.getErrors();
		}
		
	public Core get_selenium(){
			return selenium;
		}
	
	public static void main( String[] args ){
		String errors = "";
		try {
			Masthead.setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try{
			Masthead masthead = new Masthead();
			Login login = new Login(masthead.get_selenium());
			Masthead.selenium.start();
			masthead.test_self_before_login();
			login.login_to_portal("Automation1", "password");
			masthead.test_self_after_login();
			
			errors = masthead.get_errors().get_errors().toString();
			System.out.println(errors);
			Masthead.selenium.close();
			Masthead.selenium.stop();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			Masthead.tearDown();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		assertTrue(errors=="{}");
	}
}



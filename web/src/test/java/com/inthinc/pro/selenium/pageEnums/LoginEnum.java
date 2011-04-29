package com.inthinc.pro.selenium.pageEnums;

import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;

import com.inthinc.pro.automation.enums.SeleniumEnumUtil;
import com.inthinc.pro.automation.enums.SeleniumEnums;

// Enums have format NAME( Text, ID, X-Path, X-Path-Alternate )

public enum LoginEnum implements SeleniumEnums {
	
    LOGIN_URL("login"),
    LOGOUT_URL("logout"),
    
	/* Main Login Page Elements*/
	
	USERNAME_LABEL("User Name:", null, "//form[@id='loginForm']/table/tbody/tr[1]/td[1]", null),
	USERNAME_FIELD(null, "j_username",  "//form[@id='loginForm']/table/tbody/tr[1]/td[2]/input", null),
	PASSWORD_FIELD(null, "j_password", "//form[@id='loginForm']/table/tbody/tr[2]/td[2]/input", null),
	PASSWORD_LABEL("Password:", null, "//form[@id='loginForm']/table/tbody/tr[2]/td[1]", null),
	LOGIN_BUTTON("Login", "loginLogin", "form[@id='loginForm']/table/tbody/tr[3]/td/button", "//button[@type='submit']"),
	LOGIN_TEXT("Log In", null, "//form[@id='loginForm']/table/tbody/tr[3]/td/button/span", "//span[@class='login']"),
	FORGOT_USERNAME_LINK( "Forgot your user name or password?", "link=Forgot your user name or password?", "//div/form/a", null),
	
	/* Forgot Username/Password pop-up */
	FORGOT_ERROR_EMAIL_FORMAT("Incorrect format (jdoe@tiwipro.com)",null,"//form[@id='changePasswordForm']/div[1]/div/span/span", "//span[@class='rich-message field-error field-msg']"),
	FORGOT_ERROR_EMAIL_REQUIRED("Required",null,"//form[@id='changePasswordForm']/div[1]/div/span/span", null),
	FORGOT_ERROR_EMAIL_UNKNOWN("Incorrect e-mail address",null,"//form[@id='changePasswordForm']/div[1]/div/span/span", null),
	FORGOT_TITLE("Forgot User Name or Password?", null, "//div[@id='forgotPasswordPanelHeader']", null),
	FORGOT_TEXT("Forgot your user name or password?", null, "//a[@title='Forgot your user name or password?']", null),
	FORGOT_EMAIL_FIELD(null, "changePasswordForm:email", "//form[@id='changePasswordForm']/div[1]/input", "//input[@id='changePasswordForm:email']"),
	FORGOT_EMAIL_LABEL("E-mail Address:", null, "//form[@id='changePasswordForm']/div[1]", null),
	FORGOT_CANCEL_BUTTON("Cancel", "changePasswordForm:PasswordCancelButton","//div[@class='popupactions']/buton[@type='button']/span", "//span[@class='cancel']" ),
	FORGOT_SEND("Send", "changePasswordForm:PasswordSubmitButton", "//div[@class='popupactions']/buton[@type='submit']/span", "//span[@class='retrieve_password']"),
	FORGOT_CLOSE("Cancel",null, "//div[@id='forgotPasswordPanelContentDiv']/div/img", "//img[@onclick=\"Richfaces.hideModalPanel('forgotPasswordPanel')\"]"),
	
	FORGOT_MESSAGE("Enter the e-mail address from your tiwiPro account. Information pertaining to your user name and password will be sent to this e-mail account.", 
			null, "//table[@id='forgotPasswordPanelContentTable']/tbody/tr[2]/td/div", null),	
	
	
	/* Error pop-up for the Forgot Username/Password pop-up */
	ERROR_BUTTON_OK("OK","loginErrorForm:loginOk", "//form[@id='loginErrorForm']/div/button", "//form[@id='loginErrorForm']/div/button/span"),
	ERROR_CLOSE("","Richfaces.hideModalPanel('errorPanel')","//div[@id='errorPanelContentDiv']/div","//div[@id='errorPanelContentDiv']/div/img"),//"" rather than null? on image???
	ERROR_HEADER("Log In Error", "errorPanelHeader", "//table[@id='errorPanelContentTable']/tbody/tr[1]/td/div",null),
	ERROR_MESSAGE(StringEscapeUtils.unescapeHtml("Incorrect user name or password<br/><br/>Please try again."), "//p",null,null),
	
	
	/* Success with forgot Username/Password */
	MESSAGE_SENT_BULLET_1("Be patient, it may take a few minutes for the message to arrive in your Inbox.", null, "//table[@id='fd-table-1']/tbody/tr/td/div/ul/li[1]", null),
	MESSAGE_SENT_BULLET_2("Check your Junk folder.",null,"//table[@id='fd-table-1']/tbody/tr/td/div/ul/li[2]", null ),
	MESSAGE_SENT_BULLET_3("If problems still exist, perform the process again.",null,"//table[@id='fd-table-1']/tbody/tr/td/div/ul/li[3]", null ),
	MESSAGE_SENT_HEADER("Didn't get the message?",null,"//table[@id='fd-table-1']/tbody/tr/td/div/h2",null),
	MESSAGE_SENT_TITLE("Message Sent Successfully", null, "//li[@class='l grid_title']", null),
	
	MESSAGE_SENT_FIRST_PARAGRAPH("A message containing infromation about your user name and password has been sent to the e-mail address provided.", 
			null, "//table[@id='fd-table-1']/tbody/tr/td/div/div", null),
	
	/* Update Credentials Page */
			
	CHANGE_TITLE("Change Password",	null, "//li[@class='l grid_title']", null),
	
	SECOND_PARAGRAPH("Your user name is indicated below. To change your password, enter a new password in the fields below.",
			null,"//form[@id='updateCredentialsForm']/p",null),
	
	CONFIRM_CHANGE("Your password has been changed.", null, "//table[@id='successPanelContentTable']/tbody/tr[2]/td/p",null),

	USERNAME_TEXT_LABEL("User Name:", null, "//form[@id='updateCredentialsForm']/table/tbody/tr/td/table/tbody/tr[1]/td[1]", null),
	USERNAME_TEXT_FIELD(null, null, "//form[@id='updateCredentialsForm']/table/tbody/tr/td/table/tbody/tr[1]/td[2]", null),
	
	NEW_PASSWORD_LABEL("New Password:",null,"//form[@id='updateCredentialsForm']/table/tbody/tr/td/table/tbody/tr[2]/td[1]",null),
	NEW_PASSWORD(null,"updateCredentialsForm:newPassword","//form[@id='updateCredentialsForm']/table/tbody/tr/td/table/tbody/tr[2]/td[2]",null),
	NEW_PASSWORD_SHORT("Must be 6 to 12 characters","//span[@class='rich-message field-error field-msg']/span",null,null),
	
	CONFIRM_PASSWORD_LABEL("Confirm New Password:",null,"//form[@id='updateCredentialsForm']/table/tbody/tr/td/table/tbody/tr[3]/td[1]",null),
	CONFIRM_PASSWORD(null,"updateCredentialsForm:newPassword","//form[@id='updateCredentialsForm']/table/tbody/tr/td/table/tbody/tr[3]/td[2]",null),
	CONFIRM_PASSWORD_NO_MATCH("Does not match password", null, "//span[@class='rich-message field-error field-msg']/span", null),
	
	CHANGE_PASSWORD_BUTTON("Change", "updateCredentialsForm:updateCredentialsSubmit", "//button[@type='submit']", "//span[@class='save']"),
	CANCEL_CHANGE("Cancel", "updateCredentialsCancel", "//button[@type='button']", "//span[@class='cancel']" );
	
	
	private String text, ID, xpath, xpath_alt, url;
	
	private LoginEnum( String text, String ID, String xpath, String xpath_alt) {
		this.text=text;
		this.ID=ID;
		this.xpath=xpath;
		this.xpath_alt=xpath_alt;
		this.url=null;
	}
	
	private LoginEnum(String url){
	    this.url = url;
	    this.text=null;
	    this.ID=null;
	    this.xpath=null;
	    this.xpath_alt=null;
	}
	
	public String getURL(){
	    return url;
	}

	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text=text;
	}

	public String getID() {
		return ID;
	}

	public String getXpath() {
		return xpath;
	}

	public String getXpath_alt() {
		return xpath_alt;
	}
	
	public String getError() {
	    return this.name();
	}
    @Override
    public List<String> getLocators() {        
        return SeleniumEnumUtil.getLocators(this);
    }
}

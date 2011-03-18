package com.inthinc.pro.web.selenium.portal.Login;

import org.apache.commons.lang.StringEscapeUtils;

import com.inthinc.pro.web.selenium.SeleniumEnums;

// Enums have format NAME( Text, ID, X-Path, X-Path-Alternate )

public enum LoginEnum implements SeleniumEnums {
	
	/* Main Login Page Elements*/
	
	USERNAME_LABEL("User Name:", null, "//form[@id='loginForm']/table/tbody/tr[1]/td[1]", null),
	USERNAME_FIELD(null, "j_username",  "//form[@id='loginForm']/table/tbody/tr[1]/td[2]/input", null),
	PASSWORD_FIELD(null, "j_password", "//form[@id='loginForm']/table/tbody/tr[2]/td[2]/input", null),
	PASSWORD_LABEL("Password:", null, "//form[@id='loginForm']/table/tbody/tr[2]/td[1]", null),
	LOGIN_BUTTON("Login", "loginLogin", "form[@id='loginForm']/table/tbody/tr[3]/td/button", "//button[@type='submit']"),
	LOGIN_TEXT("Log In", null, "//form[@id='loginForm']/table/tbody/tr[3]/td/button/span", "//span[@class='login']"),
	FORGOT_USERNAME_LINK( "Forgot your user name or password?", "link=Forgot your user name or password", "//div/form/a", null),
	
	/* Forgot Username/Password pop-up */
	FORGOT_ERROR("Incorrect format (jdoe@tiwipro.com)",null,"//form[@id='changePasswordForm']/div[1]/div/span/span", null),
	FORGOT_TITLE("Forgot User Name or Password?", null, "//div[@id='forgotPasswordPanelHeader']", null),
	FORGOT_TEXT("Forgot your user name or password?", null, "//a[@title='Forgot your user name or password?']", null),
	FORGOT_EMAIL_FIELD(null, "changePasswordForm:email", "//form[@id='changePasswordForm']/div[1]/input", "//input[@id='changePasswordForm:email']"),
	FORGOT_EMAIL_LABEL("E-mail Address:", null, "//form[@id='changePasswordForm']/div[1]", null),
	FORGOT_CANCEL_BUTTON("Cancel", "changePasswordForm:PasswordCancelButton","//div[@class='popupactions']/buton[@type='button']/span", "//span[@class='cancel']" ),
	FORGOT_SEND("Send", "changePasswordForm:PasswordSubmitButton", "//div[@class='popupactions']/buton[@type='submit']/span", "//span[@class='retrieve_password']"),
	FORGOT_CLOSE("Cancel",null, "//div[@id='forgotPasswordPanelContentDiv']/div/img", "//img[@onclick=\"Richfaces.hideModalPanel('forgotPasswordPanel')\"]"),
	
	FORGOT_MESSAGE("Enter the e-mail address from your tiwiPRO account. Information pertaining to your user name and password will be sent to this e-mail account.", 
			null, "//table[@id='forgotPasswordPanelContentTable']/tbody/tr[2]/td/div", null),	
	
	
	/* Error pop-up for the Forgot Username/Password pop-up */
	ERROR_BUTTON(null,"loginErrorForm:loginOk", "//form[@id='loginErrorForm']/div/button", "//form[@id='loginErrorForm']/div/button/span"),
	ERROR_CLOSE(null,"Richfaces.hideModalPanel('errorPanel')","//div[@id='errorPanelContentDiv']/div","//div[@id='errorPanelContentDiv']/div/img"),
	ERROR_HEADER(null, "errorPanelHeader", "//table[@id='errorPanelContentTable']/tbody/tr[1]/td/div",null),
	ERROR_MESSAGE(StringEscapeUtils.unescapeHtml("Incorrect user name or password<br/><br/>Please try again."), "//p",null,null),
	
	
	/* Success with forgot Username/Password */
	BULLET_1("Be patient, it may take a few minutes for the message to arrive in your Inbox.", null, "//table[@id='fd-table-1']/tbody/tr/td/div/ul/li[1]", null),
	BULLET_2("Check your Junk folder.",null,"//table[@id='fd-table-1']/tbody/tr/td/div/ul/li[2]", null ),
	BULLET_3("If problems still exist, perform the process again.",null,"//table[@id='fd-table-1']/tbody/tr/td/div/ul/li[3]", null ),
	HEADER("Didn't get the message?",null,"//table[@id='fd-table-1']/tbody/tr/td/div/h2",null),
	MESSAGE_SENT_TITLE("Message Sent Successfully", null, "//li[@class='l grid_title']", null),
	
	FIRST_PARAGRAPH("A message containing infromation about your user name and password has been sent to the e-mail address provided.", 
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
	
	
	private String text, ID, xpath, xpath_alt;
	
	private LoginEnum( String text, String ID, String xpath, String xpath_alt) {
		this.text=text;
		this.ID=ID;
		this.xpath=xpath;
		this.xpath_alt=xpath_alt;
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
}

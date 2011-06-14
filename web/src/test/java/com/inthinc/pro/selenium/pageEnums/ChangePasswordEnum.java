package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum ChangePasswordEnum implements SeleniumEnums {

    /* Update Credentials Page */
    CHANGE_TITLE("Change Password", "//li[@class='l grid_title']"),
    MESSAGE("Your user name is indicated below. To change your password, enter a new password in the fields below.", "//form[@id='updateCredentialsForm']/p"),
    CONFIRM_CHANGE("Your password has been changed.", "//table[@id='successPanelContentTable']/tbody/tr[2]/td/p"),
   
    USERNAME_TEXT("User Name:", "//form[@id='updateCredentialsForm']/table/tbody/tr/td/table/tbody/tr[1]/td[2]/strong"),

    NEW_PASSWORD("New Password:", "updateCredentialsForm:newPassword", "//form[@id='updateCredentialsForm']/table/tbody/tr/td/table/tbody/tr[2]/td[2]"),

    CONFIRM_PASSWORD("Confirm New Password:", "updateCredentialsForm:newPassword", "//form[@id='updateCredentialsForm']/table/tbody/tr/td/table/tbody/tr[3]/td[2]/input"),
    
    PASSWORD_STRENGTH("Password Strength:", "//tr[4]/td[1]"),
    PASSWORD_STRENGTH_MESSAGE(null, "updateCredentialsForm_strengthMsg"),

    CHANGE_PASSWORD_BUTTON("Change", "updateCredentialsForm:updateCredentialsSubmit", "//button[@type='submit']", "//span[@class='save']"),
    CANCEL_CHANGE(cancel, "updateCredentialsCancel", "//button[@type='button']", "//span[@class='cancel']");

    
	
	;

	private String text, url;
	private String[] IDs;
	
	private ChangePasswordEnum(String url){
		this.url = url;
	}
	private ChangePasswordEnum(String text, String ...IDs){
	    this.text=text;
		this.IDs = IDs;
	}
	
	@Override
	public String[] getIDs() {
	    return IDs;
	}
	
	@Override
	public String getText() {
	    return text;
	}
	
	@Override
	public String getURL() {
	    return url;
	}
}

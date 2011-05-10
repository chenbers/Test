package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum LoginEnum implements SeleniumEnums {

    LOGIN_URL("login"),
    LOGOUT_URL("logout"),

    /* Main Login Page Elements */

    USERNAME_LABEL("User Name:", "//form[@id='loginForm']/table/tbody/tr[1]/td[1]"),
    USERNAME_FIELD(null, "j_username", "//form[@id='loginForm']/table/tbody/tr[1]/td[2]/input"),
    PASSWORD_FIELD(null, "j_password", "//form[@id='loginForm']/table/tbody/tr[2]/td[2]/input"),
    PASSWORD_LABEL("Password:", "//form[@id='loginForm']/table/tbody/tr[2]/td[1]"),
    LOGIN_BUTTON("Log In", "loginLogin", "form[@id='loginForm']/table/tbody/tr[3]/td/button", "//button[@type='submit']"),
    LOGIN_TEXT("Log In", "//div[@class='panel_title']/span", "//span[@class='login']"),
    FORGOT_USERNAME_LINK("Forgot your user name or password?", "link=Forgot your user name or password?", "//div/form/a"),

    /* Forgot Username/Password pop-up */
    FORGOT_ERROR_EMAIL_FORMAT("Incorrect format (jdoe@tiwipro.com)", "//form[@id='changePasswordForm']/div[1]/div/span/span", "//span[@class='rich-message field-error field-msg']"),
    FORGOT_ERROR_EMAIL_REQUIRED("Required", "//form[@id='changePasswordForm']/div[1]/div/span/span"),
    FORGOT_ERROR_EMAIL_UNKNOWN("Incorrect e-mail address", "//form[@id='changePasswordForm']/div[1]/div/span/span"),
    FORGOT_TITLE("Forgot User Name or Password?", "//div[@id='forgotPasswordPanelHeader']"),
    FORGOT_TEXT("Forgot your user name or password?", "//a[@title='Forgot your user name or password?']"),
    FORGOT_EMAIL_FIELD(null, "changePasswordForm:email", "//form[@id='changePasswordForm']/div[1]/input", "//input[@id='changePasswordForm:email']"),
    FORGOT_EMAIL_LABEL("E-mail Address:", "//form[@id='changePasswordForm']/div[1]/text()[3]"),
    FORGOT_CANCEL_BUTTON("Cancel", "changePasswordForm:PasswordCancelButton", "//div[@class='popupactions']/buton[@type='button']/span", "//span[@class='cancel']"),
    FORGOT_SEND("Send", "changePasswordForm:PasswordSubmitButton", "//div[@class='popupactions']/buton[@type='submit']/span", "//span[@class='retrieve_password']"),
    FORGOT_CLOSE("Cancel", "//div[@id='forgotPasswordPanelContentDiv']/div/img", "//img[@onclick=\"Richfaces.hideModalPanel('forgotPasswordPanel')\"]"),

    FORGOT_MESSAGE("Enter the e-mail address from your tiwiPro account. Information pertaining to your user name and password will be sent to this e-mail account.",
            "//table[@id='forgotPasswordPanelContentTable']/tbody/tr[2]/td/div"),

    /* Error pop-up for the Forgot Username/Password pop-up */
    ERROR_BUTTON_OK("OK", "loginErrorForm:loginOk", "//form[@id='loginErrorForm']/div/button", "//form[@id='loginErrorForm']/div/button/span"),
    ERROR_CLOSE("", "Richfaces.hideModalPanel('errorPanel')", "//div[@id='errorPanelContentDiv']/div", "//div[@id='errorPanelContentDiv']/div/img"),//id('forgotPasswordPanelContentDiv')/x:div/x:img
    ERROR_HEADER("Log In Error", "errorPanelHeader", "//table[@id='errorPanelContentTable']/tbody/tr[1]/td/div"),
    ERROR_MESSAGE("Incorrect user name or password.\n\nPlease try again.", "//p"),

    /* Success with forgot Username/Password */
    MESSAGE_SENT_BULLET_1("Be patient, it may take a few minutes for the message to arrive in your Inbox.", "//table[@id='fd-table-1']/tbody/tr/td/div/ul/li[1]"),
    MESSAGE_SENT_BULLET_2("Check your Junk folder.", "//table[@id='fd-table-1']/tbody/tr/td/div/ul/li[2]"),
    MESSAGE_SENT_BULLET_3("If problems still exist, perform the process again.", "//table[@id='fd-table-1']/tbody/tr/td/div/ul/li[3]"),
    MESSAGE_SENT_HEADER("Didn't get the message?", "//table[@id='fd-table-1']/tbody/tr/td/div/h2"),
    MESSAGE_SENT_TITLE("Message Sent Successfully", "//li[@class='l grid_title']"),

    MESSAGE_SENT_FIRST_PARAGRAPH("A message containing information about your user name and password has been sent to the e-mail address provided.",
            "//table[@id='fd-table-1']/tbody/tr/td/div/div"),

    /* Update Credentials Page */

    CHANGE_TITLE("Change Password", "//li[@class='l grid_title']"),

    SECOND_PARAGRAPH("Your user name is indicated below. To change your password, enter a new password in the fields below.", "//form[@id='updateCredentialsForm']/p"),

    CONFIRM_CHANGE("Your password has been changed.", "//table[@id='successPanelContentTable']/tbody/tr[2]/td/p"),

    USERNAME_TEXT_LABEL("User Name:", "//form[@id='updateCredentialsForm']/table/tbody/tr/td/table/tbody/tr[1]/td[1]"),
    USERNAME_TEXT_FIELD(null, "//form[@id='updateCredentialsForm']/table/tbody/tr/td/table/tbody/tr[1]/td[2]"),

    NEW_PASSWORD_LABEL("New Password:", "//form[@id='updateCredentialsForm']/table/tbody/tr/td/table/tbody/tr[2]/td[1]"),
    NEW_PASSWORD(null, "updateCredentialsForm:newPassword", "//form[@id='updateCredentialsForm']/table/tbody/tr/td/table/tbody/tr[2]/td[2]"),
    NEW_PASSWORD_SHORT("Must be 6 to 12 characters", "//span[@class='rich-message field-error field-msg']/span"),

    CONFIRM_PASSWORD_LABEL("Confirm New Password:", "//form[@id='updateCredentialsForm']/table/tbody/tr/td/table/tbody/tr[3]/td[1]"),
    CONFIRM_PASSWORD(null, "updateCredentialsForm:newPassword", "//form[@id='updateCredentialsForm']/table/tbody/tr/td/table/tbody/tr[3]/td[2]"),
    CONFIRM_PASSWORD_NO_MATCH("Does not match password", "//span[@class='rich-message field-error field-msg']/span"),

    CHANGE_PASSWORD_BUTTON("Change", "updateCredentialsForm:updateCredentialsSubmit", "//button[@type='submit']", "//span[@class='save']"),
    CANCEL_CHANGE("Cancel", "updateCredentialsCancel", "//button[@type='button']", "//span[@class='cancel']");

    private String text, url;
    private String[] IDs;
    
    private LoginEnum(String url){
    	this.url = url;
    }
    private LoginEnum(String text, String ...IDs){
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

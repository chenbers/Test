package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.utils.Xpath;

// Enums have format NAME( Text, ID, X-Path, X-Path-Alternate )

public enum LoginEnum implements SeleniumEnums {

    LOGIN_URL("login"),
    LOGOUT_URL("logout"),

    /* Main Login Page Elements */

    USERNAME_LABEL("User Name:", null, "//form[@id='loginForm']/table/tbody/tr[1]/td[1]"),
    USERNAME_FIELD(null, "j_username", "//form[@id='loginForm']/table/tbody/tr[1]/td[2]/input"),
    PASSWORD_FIELD(null, "j_password", "//form[@id='loginForm']/table/tbody/tr[2]/td[2]/input"),
    PASSWORD_LABEL("Password:", null, "//form[@id='loginForm']/table/tbody/tr[2]/td[1]"),
    LOGIN_BUTTON("Log In", "loginLogin", "form[@id='loginForm']/table/tbody/tr[3]/td/button", "//button[@type='submit']"),
    LOGIN_TEXT("Log In", null, "//div[@class='panel_title']/span", "//span[@class='login']"),
    FORGOT_USERNAME_LINK("Forgot your user name or password?", "link=Forgot your user name or password?", "//div/form/a"),

    /* Forgot Username/Password pop-up */
    FORGOT_ERROR_EMAIL_FORMAT("Incorrect format (jdoe@tiwipro.com)", null, "//form[@id='changePasswordForm']/div[1]/div/span/span", "//span[@class='rich-message field-error field-msg']"),
    FORGOT_ERROR_EMAIL_REQUIRED("Required", null, "//form[@id='changePasswordForm']/div[1]/div/span/span"),
    FORGOT_ERROR_EMAIL_UNKNOWN("Incorrect e-mail address", null, "//form[@id='changePasswordForm']/div[1]/div/span/span"),
    FORGOT_TITLE("Forgot User Name or Password?", null, "//div[@id='forgotPasswordPanelHeader']"),
    FORGOT_TEXT("Forgot your user name or password?", null, "//a[@title='Forgot your user name or password?']"),
    FORGOT_EMAIL_FIELD(null, "changePasswordForm:email", "//form[@id='changePasswordForm']/div[1]/input", "//input[@id='changePasswordForm:email']"),
    FORGOT_EMAIL_LABEL("E-mail Address:", null, "//form[@id='changePasswordForm']/div[1]/text()[3]"),
    FORGOT_CANCEL_BUTTON("Cancel", "changePasswordForm:PasswordCancelButton", "//div[@class='popupactions']/buton[@type='button']/span", "//span[@class='cancel']"),
    FORGOT_SEND("Send", "changePasswordForm:PasswordSubmitButton", "//div[@class='popupactions']/buton[@type='submit']/span", "//span[@class='retrieve_password']"),
    FORGOT_CLOSE("Cancel", null, "//div[@id='forgotPasswordPanelContentDiv']/div/img", "//img[@onclick=\"Richfaces.hideModalPanel('forgotPasswordPanel')\"]"),

    FORGOT_MESSAGE("Enter the e-mail address from your tiwiPro account. Information pertaining to your user name and password will be sent to this e-mail account.", null,
            "//table[@id='forgotPasswordPanelContentTable']/tbody/tr[2]/td/div"),

    /* Error pop-up for the Forgot Username/Password pop-up */
    ERROR_BUTTON_OK("OK", "loginErrorForm:loginOk", "//form[@id='loginErrorForm']/div/button", "//form[@id='loginErrorForm']/div/button/span"),
    ERROR_CLOSE("", "Richfaces.hideModalPanel('errorPanel')", "//div[@id='errorPanelContentDiv']/div", "//div[@id='errorPanelContentDiv']/div/img"),//id('forgotPasswordPanelContentDiv')/x:div/x:img
    ERROR_HEADER("Log In Error", "errorPanelHeader", "//table[@id='errorPanelContentTable']/tbody/tr[1]/td/div"),
    ERROR_MESSAGE("Incorrect user name or password.\n\nPlease try again.", null, "//p"),

    /* Success with forgot Username/Password */
    MESSAGE_SENT_BULLET_1("Be patient, it may take a few minutes for the message to arrive in your Inbox.", null, "//table[@id='fd-table-1']/tbody/tr/td/div/ul/li[1]"),
    MESSAGE_SENT_BULLET_2("Check your Junk folder.", null, "//table[@id='fd-table-1']/tbody/tr/td/div/ul/li[2]"),
    MESSAGE_SENT_BULLET_3("If problems still exist, perform the process again.", null, "//table[@id='fd-table-1']/tbody/tr/td/div/ul/li[3]"),
    MESSAGE_SENT_HEADER("Didn't get the message?", null, "//table[@id='fd-table-1']/tbody/tr/td/div/h2"),
    MESSAGE_SENT_TITLE("Message Sent Successfully", null, "//li[@class='l grid_title']"),

    MESSAGE_SENT_FIRST_PARAGRAPH("A message containing information about your user name and password has been sent to the e-mail address provided.", null,
            "//table[@id='fd-table-1']/tbody/tr/td/div/div"),

    /* Update Credentials Page */

    CHANGE_TITLE("Change Password", null, "//li[@class='l grid_title']"),

    SECOND_PARAGRAPH("Your user name is indicated below. To change your password, enter a new password in the fields below.", null, "//form[@id='updateCredentialsForm']/p"),

    CONFIRM_CHANGE("Your password has been changed.", null, "//table[@id='successPanelContentTable']/tbody/tr[2]/td/p"),

    USERNAME_TEXT_LABEL("User Name:", null, "//form[@id='updateCredentialsForm']/table/tbody/tr/td/table/tbody/tr[1]/td[1]"),
    USERNAME_TEXT_FIELD(null, null, "//form[@id='updateCredentialsForm']/table/tbody/tr/td/table/tbody/tr[1]/td[2]"),

    NEW_PASSWORD_LABEL("New Password:", null, "//form[@id='updateCredentialsForm']/table/tbody/tr/td/table/tbody/tr[2]/td[1]"),
    NEW_PASSWORD(null, "updateCredentialsForm:newPassword", "//form[@id='updateCredentialsForm']/table/tbody/tr/td/table/tbody/tr[2]/td[2]"),
    NEW_PASSWORD_SHORT("Must be 6 to 12 characters", "//span[@class='rich-message field-error field-msg']/span"),

    CONFIRM_PASSWORD_LABEL("Confirm New Password:", null, "//form[@id='updateCredentialsForm']/table/tbody/tr/td/table/tbody/tr[3]/td[1]"),
    CONFIRM_PASSWORD(null, "updateCredentialsForm:newPassword", "//form[@id='updateCredentialsForm']/table/tbody/tr/td/table/tbody/tr[3]/td[2]"),
    CONFIRM_PASSWORD_NO_MATCH("Does not match password", null, "//span[@class='rich-message field-error field-msg']/span"),

    CHANGE_PASSWORD_BUTTON("Change", "updateCredentialsForm:updateCredentialsSubmit", "//button[@type='submit']", "//span[@class='save']"),
    CANCEL_CHANGE("Cancel", "updateCredentialsCancel", "//button[@type='button']", "//span[@class='cancel']");

    private String text, ID, xpath, xpath_alt, url;

    private LoginEnum(String text, String ID, String xpath, String xpath_alt) {
        this.text = text;
        this.ID = ID;
        this.xpath = xpath;
        this.xpath_alt = xpath_alt;
    }

    private LoginEnum(String url) {
        this.url = url;
    }

    private LoginEnum(String text, String ID) {
        this(text, ID, "", null);
    }

    private LoginEnum(String text, String ID, String xpath) {
        this(text, ID, xpath, null);
    }

    private LoginEnum(String text, String ID, Xpath xpath, Xpath xpath_alt) {
        this(text, ID, xpath.toString(), xpath_alt.toString());
    }

    private LoginEnum(String text, String ID, Xpath xpath) {
        this(text, ID, xpath.toString(), null);
    }

    private LoginEnum(String text, Xpath xpath) {
        this(text, null, xpath.toString(), null);
    }

    private LoginEnum(Xpath xpath, Xpath xpath_alt) {
        this(null, null, xpath.toString(), xpath_alt.toString());
    }

    private LoginEnum(Xpath xpath) {
        this(null, null, xpath.toString(), null);
    }

    public String getURL() {
        return url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

}

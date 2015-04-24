package com.inthinc.pro.selenium.pageEnums;

import java.util.Calendar;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum LoginEnum implements SeleniumEnums {

    LOGIN_URL("login"),
    LOGOUT_URL("logout"),

    /* Main Login Page Elements */

    USERNAME_FIELD("Username:", "//input[@id='username']"),
    PASSWORD_FIELD("Password:", "//input[@id='password']"),
    LOGIN_BUTTON("LOGIN", "//button[@class='btn btn-primary btn-lg']"),
    RETURN_BUTTON("LOGIN", "//button[@class='btn btn-lg btn-primary']"),
    //FORGOT_USERNAME_LINK("Forgot your username or password?", "//span[5]/a"),
    //FORGOT_USERNAME_LINK("Forgot your username or password?", "//span[1]/a"),
    FORGOT_USERNAME_LINK("Forgot your username or password?", "//a[@href='https://qa.inthinc.com/tiwipro/resetPassword']"),
    //TERM_OF_SERVICE_LINK("Forgot your username or password?", "//span[1]/a"),
    //TERMS_OF_SERVICE_LINK("Terms of Service", "//span[1]/a"),
    TERMS_OF_SERVICE_LINK("Terms of Service", "//a[@href='html/inthincCustomerTermsOfService.jsp']"), 
    //PRIVACY_POLICY_LINK("Privacy Policy", "//span[1]/a"), 
    PRIVACY_POLICY_LINK("Privacy Policy", "//a[@href='html/inthincPrivacyPolicy.jsp']"), 

//    PRIVACY_POLICY_LINK("Privacy Policy", "//span[3]/a"),
    //LOGO_TEXT("inthinc", "//div[@id='content']/div"),
    LOGO_TEXT("", "//div[@class='logo']/div"),
    COPYRIGHT("&#169;" + String.valueOf(Calendar.getInstance().get(Calendar.YEAR)) + " inthinc", "//li[@class='first copyright_info']"),
    COPYRIGHT_TEXT("©2009-2015 inthinc", "//span[@class='footer-info copyright pull-right']"),
    
    ERROR_TEXT(null, "//p[@id='credentials.errors']")
    ;
    
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

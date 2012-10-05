package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum LoginEnum implements SeleniumEnums {

    LOGIN_URL("login"),
    LOGOUT_URL("logout"),

    /* Main Login Page Elements */

    USERNAME_FIELD("Username:", "//input[@id='username']"),
    PASSWORD_FIELD("Password:", "//input[@id='password']"),
    LOGIN_BUTTON("LOGIN", "//button[@class='btn btn-large btn-block btn-inthinc']"),
    
    FORGOT_USERNAME_LINK("Forgot your user name or password?", "//span[5]/a"),
    LOGO_TEXT("©2012 inthinc", "//span[2]/a"),
    
    ERROR_TEXT(null, "//span[@id='credentials.errors']")
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

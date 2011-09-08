package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum LoginEnum implements SeleniumEnums {

    LOGIN_URL("login"),
    LOGOUT_URL("logout"),

    /* Main Login Page Elements */

    USERNAME_FIELD("User Name:", "j_username", "//form[@id='loginForm']/table/tbody/tr[1]/td[2]/input"),
    PASSWORD_FIELD("Password:", "j_password", "//form[@id='loginForm']/table/tbody/tr[2]/td[2]/input"),
    LOGIN_BUTTON("Log In", "loginLogin", "form[@id='loginForm']/table/tbody/tr[3]/td/button", "//button[@type='submit']"),
    LOGIN_HEADER("Log In", "//div[@class='panel_title']/span", "//span[@class='login']"),
    
    FORGOT_USERNAME_LINK("Forgot your user name or password?", "//a[contains(@id,':loginForgotPassword')]", "//div/form/a"),

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

package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum LogoutEnum implements SeleniumEnums {

    LOGOUT_URL("cas/logout"),

    /* Main Login Page Elements */

    RETURN_BUTTON("LOGIN", "//input[@class='btn btn-large btn-inthinc']"),
    
    SUCCESSFUL_TEXT("Logout successful", "//div[@class='well']/h3"),//not currently on new login page
    EXIT_BROWSER_TEXT("For security reasons, exit your web browser.", "//div[@class='well']/p")
    ;
    
    private String text, url;
    private String[] IDs;
    
    private LogoutEnum(String url){
    	this.url = url;
    }
    private LogoutEnum(String text, String ...IDs){
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

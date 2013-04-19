package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum UtilLoginEnum implements SeleniumEnums {

    MY_ACCOUNT_URL(appUrl + "/tiwiproutil/login.faces"),

    USERNAME("Username:", "j_username"),
    PASSWORD("Password:", "j_password"),
    LOGIN("Login", "login_btn")
    ;

    private String text, url;
    private String[] IDs;
    
    private UtilLoginEnum(String url){
    	this.url = url;
    }
    private UtilLoginEnum(String text, String ...IDs){
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

package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;


public enum FormsBarEnum implements SeleniumEnums {

    MANAGE("Forms", "//a[@href='/forms/']"),
    PUBLISHED("Published", "//a[@href='/forms/published']"),
    SUBMISSIONS("Submissions", "//a[@href='/forms/submissions']"),
    CUSTOMERS("Customers", "//a[@href='/forms/customerlocations']"),
    ;

    private String text, url;
    private String[] IDs;
    
    private FormsBarEnum(String url){
    	this.url = url;
    }
    private FormsBarEnum(String text, String ...IDs){
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

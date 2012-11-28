package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;


public enum FormsBarEnum implements SeleniumEnums {

    MANAGE("Forms", "//div[2]/div[1]/div/ul/li[1]/a"),
    PUBLISHED("Published", "//div[2]/div[1]/div/ul/li[2]/a"),
    SUBMISSIONS("Submissions", "//div[2]/div[1]/div/ul/li[3]/a"),
    CUSTOMERS("Customers", "//div[2]/div[1]/div/ul/li[4]/a"),

    //THESE FIVE CAN BE MOVED UP TO THE MASTHEAD ONCE WE MERGE THE FORMS AND CURRENT PORTAL INTERFACES     
    ACCOUNT_IMAGE(null, "//div[1]/div/div/ul[2]/li/a"),
    MESSAGES("Messages", "//a[@id='headerForm:headerMyMessages']"),
    HELP("Help", "//a[@id='tb-help']"),
    ACCOUNT("Account", "//a[@id='headerForm:headerMyAccount']"),
    LOGOUT("Logout", "//a[@id='template-settings-logout']")
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

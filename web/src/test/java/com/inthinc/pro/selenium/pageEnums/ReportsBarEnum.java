package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum ReportsBarEnum implements SeleniumEnums {
    DRIVERS("Drivers", "//div[@class='sub_nav-bg']/ul/li[1]/a"),
    VEHICLES("Vehicles", "//div[@class='sub_nav-bg']/ul/li[2]/a"),
    IDLING("Idling", "//div[@class='sub_nav-bg']/ul/li[3]/a"),
    DEVICES("Devices", "//div[@class='sub_nav-bg']/ul/li[4]/a"),
    WAYSMART("waySmart", "//div[@class='sub_nav-bg']/ul/li[5]/a"),

    ;

    private String text, url;
    private String[] IDs;
    
    private ReportsBarEnum(String url){
    	this.url = url;
    }
    private ReportsBarEnum(String text, String ...IDs){
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

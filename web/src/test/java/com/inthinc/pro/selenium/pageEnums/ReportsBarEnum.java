package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.selenium.SeleniumEnums;

public enum ReportsBarEnum implements SeleniumEnums {
    DRIVERS("Drivers", null, "//div[@class='sub_nav-bg']/ul/li[1]/a", null),
    VEHICLES("Vehicles", null, "//div[@class='sub_nav-bg']/ul/li[2]/a", null),
    IDLING("Idling", null, "//div[@class='sub_nav-bg']/ul/li[3]/a", null),
    DEVICES("Devices", null, "//div[@class='sub_nav-bg']/ul/li[4]/a", null),
    WAYSMART("waySmart", null, "//div[@class='sub_nav-bg']/ul/li[5]/a", null),
    
    ;
    
    private String text, ID, xpath, xpath_alt;
    
    private ReportsBarEnum( String text, String ID, String xpath, String xpath_alt) {
        this.text=text;
        this.ID=ID;
        this.xpath=xpath;
        this.xpath_alt=xpath_alt;
    }
    
    

    public String getID() {
        return this.ID;
    }

    public String getText() {
        return this.text;
    }

    public String getXpath() {
        return this.xpath;
    }

    public String getXpath_alt() {
        return this.xpath_alt;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getURL() {
        return null;
    }
}

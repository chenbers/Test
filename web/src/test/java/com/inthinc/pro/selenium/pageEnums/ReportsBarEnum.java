package com.inthinc.pro.selenium.pageEnums;



import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.utils.Xpath;



public enum ReportsBarEnum implements SeleniumEnums {
    DRIVERS("Drivers", null, "//div[@class='sub_nav-bg']/ul/li[1]/a"),
    VEHICLES("Vehicles", null, "//div[@class='sub_nav-bg']/ul/li[2]/a"),
    IDLING("Idling", null, "//div[@class='sub_nav-bg']/ul/li[3]/a"),
    DEVICES("Devices", null, "//div[@class='sub_nav-bg']/ul/li[4]/a"),
    WAYSMART("waySmart", null, "//div[@class='sub_nav-bg']/ul/li[5]/a"),
    
    ;
    
    private String text, ID, xpath, xpath_alt, url;
    
    private ReportsBarEnum(String text, String ID, String xpath, String xpath_alt) {
        this.text = text;
        this.ID = ID;
        this.xpath = xpath;
        this.xpath_alt = xpath_alt;
    }

    private ReportsBarEnum(String url) {
        this.url = url;
    }

    private ReportsBarEnum(String text, String ID) {
        this(text, ID, "", null);
    }

    private ReportsBarEnum(String text, String ID, String xpath) {
        this(text, ID, xpath, null);
    }

    private ReportsBarEnum(String text, String ID, Xpath xpath, Xpath xpath_alt) {
        this(text, ID, xpath.toString(), xpath_alt.toString());
    }

    private ReportsBarEnum(String text, String ID, Xpath xpath) {
        this(text, ID, xpath.toString(), null);
    }

    private ReportsBarEnum(String text, Xpath xpath) {
        this(text, null, xpath.toString(), null);
    }

    private ReportsBarEnum(Xpath xpath, Xpath xpath_alt) {
        this(null, null, xpath.toString(), xpath_alt.toString());
    }

    private ReportsBarEnum(Xpath xpath) {
        this(null, null, xpath.toString(), null);
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

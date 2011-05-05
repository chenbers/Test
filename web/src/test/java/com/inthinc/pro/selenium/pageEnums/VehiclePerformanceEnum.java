package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.utils.Xpath;

public enum VehiclePerformanceEnum implements SeleniumEnums {
    VIEW_ALL_TRIPS(null, "vehiclePerformanceTrips");

    private String text, ID, xpath, xpath_alt, url;

    private VehiclePerformanceEnum(String text, String ID, String xpath, String xpath_alt) {
        this.text = text;
        this.ID = ID;
        this.xpath = xpath;
        this.xpath_alt = xpath_alt;
    }

    private VehiclePerformanceEnum(String url) {
        this.url = url;
    }

    private VehiclePerformanceEnum(String text, String ID) {
        this(text, ID, "", null);
    }

    private VehiclePerformanceEnum(String text, String ID, String xpath) {
        this(text, ID, xpath, null);
    }

    private VehiclePerformanceEnum(String text, String ID, Xpath xpath, Xpath xpath_alt) {
        this(text, ID, xpath.toString(), xpath_alt.toString());
    }

    private VehiclePerformanceEnum(String text, String ID, Xpath xpath) {
        this(text, ID, xpath.toString(), null);
    }

    private VehiclePerformanceEnum(String text, Xpath xpath) {
        this(text, null, xpath.toString(), null);
    }

    private VehiclePerformanceEnum(Xpath xpath, Xpath xpath_alt) {
        this(null, null, xpath.toString(), xpath_alt.toString());
    }

    private VehiclePerformanceEnum(Xpath xpath) {
        this(null, null, xpath.toString(), null);
    }

    @Override
    public String getID() {
        return this.ID;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public String getXpath() {
        return this.xpath;
    }

    @Override
    public String getXpath_alt() {
        return this.xpath_alt;
    }

    @Override
    public void setText(String text) {
    // TODO Auto-generated method stub jwimmer: to dtanner: is there a reason this doesn't SET?

    }

    @Override
    public String getURL() {
        return url;
    }

}

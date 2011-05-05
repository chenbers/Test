package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum IdlingReportEnum implements SeleniumEnums {
    TEAM_SEARCH(null, "idling-form:idling:groupfsp", null, null),
    DRIVER_SEARCH(null, "idling-form:idling:fullNamefsp", null, null),
    IDLING_FORM(null, "idling-form", null, null),
    TEAM(null, "idling-form:idling:0:idlingsDashboard", null, null),
    DRIVER(null, "idling-form:idling:0:idlingDriverPerformance", null, null),
    TRIPS(null, "idling-form:idling:0:idlingDriverTrips", null, null),
    START_DATE(null, "idling-form:startCalendarInputDate", null, null),
    END_DATE(null, "idling-form:endCalendarInputDate", null, null),
    REFRESH(null, "idling-form:idling_refresh", null, null);

    private String text, ID, xpath, xpath_alt, url;

    private IdlingReportEnum(String text, String ID, String xpath, String xpath_alt) {
        this.text = text;
        this.ID = ID;
        this.xpath = xpath;
        this.xpath_alt = xpath_alt;
    }

    private IdlingReportEnum(String url) {
        this.url = url;
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
        this.text = text;
    }

    @Override
    public String getURL() {
        return this.url;
    }

}

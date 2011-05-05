package com.inthinc.pro.selenium.pageEnums;



import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.utils.Xpath;



public enum IdlingReportEnum implements SeleniumEnums {
    TEAM_SEARCH(null,"idling-form:idling:groupfsp"),
    DRIVER_SEARCH(null,"idling-form:idling:fullNamefsp"),
    IDLING_FORM(null,"idling-form"),
    TEAM(null,"idling-form:idling:0:idlingsDashboard"),
    DRIVER(null,"idling-form:idling:0:idlingDriverPerformance"),
    TRIPS(null,"idling-form:idling:0:idlingDriverTrips"),
    START_DATE(null,"idling-form:startCalendarInputDate"),
    END_DATE(null,"idling-form:endCalendarInputDate"),
    REFRESH(null,"idling-form:idling_refresh")
    ;
    
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

    private IdlingReportEnum(String text, String ID) {
        this(text, ID, "", null);
    }

    private IdlingReportEnum(String text, String ID, String xpath) {
        this(text, ID, xpath, null);
    }

    private IdlingReportEnum(String text, String ID, Xpath xpath, Xpath xpath_alt) {
        this(text, ID, xpath.toString(), xpath_alt.toString());
    }

    private IdlingReportEnum(String text, String ID, Xpath xpath) {
        this(text, ID, xpath.toString(), null);
    }

    private IdlingReportEnum(String text, Xpath xpath) {
        this(text, null, xpath.toString(), null);
    }

    private IdlingReportEnum(Xpath xpath, Xpath xpath_alt) {
        this(null, null, xpath.toString(), xpath_alt.toString());
    }

    private IdlingReportEnum(Xpath xpath) {
        this(null, null, xpath.toString(), null);
    }

    @Override
    public String getID() {
        // TODO Auto-generated method stub
        return this.ID;
    }

    @Override
    public String getText() {
        // TODO Auto-generated method stub
        return this.text;
    }

    @Override
    public String getXpath() {
        // TODO Auto-generated method stub
        return this.xpath;
    }

    @Override
    public String getXpath_alt() {
        // TODO Auto-generated method stub
        return this.xpath_alt;
    }

    @Override
    public void setText(String text) {
        // TODO Auto-generated method stub
        this.text=text;
    }

    @Override
    public String getURL() {
        // TODO Auto-generated method stub
        return this.url;
    }

}

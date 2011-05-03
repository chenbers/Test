package com.inthinc.pro.selenium.pageEnums;

import java.util.List;

import com.inthinc.pro.automation.enums.SeleniumEnum;
import com.inthinc.pro.automation.enums.SeleniumEnum.SeleniumEnums;


public enum IdlingReportEnum implements SeleniumEnums {
    TEAM_SEARCH(null,"idling-form:idling:groupfsp",null,null,null),
    DRIVER_SEARCH(null,"idling-form:idling:fullNamefsp",null,null,null),
    IDLING_FORM(null,"idling-form",null,null,null),
    TEAM(null,"idling-form:idling:0:idlingsDashboard",null,null,null),
    DRIVER(null,"idling-form:idling:0:idlingDriverPerformance",null,null,null),
    TRIPS(null,"idling-form:idling:0:idlingDriverTrips",null,null,null),
    START_DATE(null,"idling-form:startCalendarInputDate",null,null,null),
    END_DATE(null,"idling-form:endCalendarInputDate",null,null,null),
    REFRESH(null,"idling-form:idling_refresh",null,null,null)
    ;
    
    private String text, ID, xpath, xpath_alt, url;
    
    private IdlingReportEnum( String text, String ID, String xpath, String xpath_alt, String url) {
        this.text=text;
        this.ID=ID;
        this.xpath=xpath;
        this.xpath_alt=xpath_alt;
        this.url=url;
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


    @Override
    public List<String> getLocators() {        
        return SeleniumEnum.locators(this);
    }
    
    @Override
    public  IdlingReportEnum replaceNumber(String number) {
        ID = ID.replace("###", number);
        xpath = xpath.replace("###", number);
        xpath_alt = xpath_alt.replace("###", number);
        return this;
    }

    @Override
    public  IdlingReportEnum replaceWord(String word) {
        ID = ID.replace("***", word);
        xpath = xpath.replace("***", word);
        xpath_alt = xpath_alt.replace("***", word);
        return this;
    }
}

package com.inthinc.pro.selenium.pageEnums;

import java.util.List;

import com.inthinc.pro.automation.enums.SeleniumEnum;
import com.inthinc.pro.automation.enums.SeleniumEnum.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum TeamBarEnum implements SeleniumEnums {
    TEAM_TITLE(Xpath.start().li(Id.clazz("l grid_title max-width"))),
    TEAM_SCORE(Xpath.start().li(Id.clazz("l")).span().table().tbody().tr().td()),
    
    CRASHES_PER_MILLION_NUMBER(Xpath.start().li("5", Id.clazz("l crash-item")).span("2")),
    CRASHES_PER_MILLION_TEXT("Crashes per million miles", Xpath.start().li("5", Id.clazz("l crash-item")).text("2")),
    
    DAYS_SINCE_NUMBER(Xpath.start().li("6", Id.clazz("l crash-item")).span()),
    DAYS_SINCE_TEXT("Days since last crash", Xpath.start().li("6", Id.clazz("l crash-item")).text()),
    
    MILES_SINCE_NUMBER(Xpath.start().li("7", Id.clazz("l crash-item")).span()),
    MILES_SINCE_TEXT("Miles since last crash", Xpath.start().li("7", Id.clazz("l crash-item")).text()),
    
    SUB_TITLE("What Happened...", Xpath.start().div(Id.clazz("panel_title")).text("1")),
    
    DRIVER_STATISTICS("Driver Statistics", "teamStatistics_lbl"),
    TRIPS("Trips", "teamTrips_lbl"),
    STOPS("Stops", "teamStops_lbl"),
    LIVE_TEAM("Live Team", "teamLive_lbl"),
    OVERALL_SCORE("Overall Score", "teamOverall_lbl"),
    DRIVING_STYLE("Driving Style", "teamStyle_lbl"),
    SPEED("Speed", "teamSpeeding_lbl"),  
    
    ;

    private String text, ID, xpath, xpath_alt, url;

    private TeamBarEnum(String text, String ID, String xpath, String xpath_alt) {
        this.text = text;
        this.ID = ID;
        this.xpath = xpath;
        this.xpath_alt = xpath_alt;
    }

    private TeamBarEnum(String url) {
        this.url = url;
    }
    
    private TeamBarEnum( String text, String ID) {
        this(text, ID, "", null);
    }
    private TeamBarEnum( String text, String ID, String xpath) {
        this(text, ID, xpath, null);
    }
    
    private TeamBarEnum(String text, String ID, Xpath xpath, Xpath xpath_alt){
        this(text, ID, xpath.toString(), xpath_alt.toString());
    }
    
    private TeamBarEnum(String text, String ID, Xpath xpath){
        this(text, ID, xpath.toString(), null);
    }
    
    private TeamBarEnum(String text, Xpath xpath){
        this(text, null, xpath.toString(), null);
    }
    
    private TeamBarEnum( Xpath xpath, Xpath xpath_alt) {
        this(null, null, xpath.toString(), xpath_alt.toString());
    }
    
    private TeamBarEnum( Xpath xpath) {
        this(null, null, xpath.toString(), null);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getID() {
        return ID;
    }

    public String getXpath() {
        return xpath;
    }

    public String getXpath_alt() {
        return xpath_alt;
    }

    public String getURL() {
        return url;
    }
    @Override
    public List<String> getLocators() {        
        return SeleniumEnum.locators(this);
    }
    
    @Override
    public  TeamBarEnum replaceNumber(String number) {
        ID = ID.replace("###", number);
        xpath = xpath.replace("###", number);
        xpath_alt = xpath_alt.replace("###", number);
        return this;
    }

    @Override
    public  TeamBarEnum replaceWord(String word) {
        ID = ID.replace("***", word);
        xpath = xpath.replace("***", word);
        xpath_alt = xpath_alt.replace("***", word);
        return this;
    }
}

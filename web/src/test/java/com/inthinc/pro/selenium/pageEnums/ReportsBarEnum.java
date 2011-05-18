package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum ReportsBarEnum implements SeleniumEnums {
    DRIVERS("Drivers", "//div[@class='sub_nav-bg']/ul/li[1]/a"),
    VEHICLES("Vehicles", "//div[@class='sub_nav-bg']/ul/li[2]/a"),
    IDLING("Idling", "//div[@class='sub_nav-bg']/ul/li[3]/a"),
    DEVICES("Devices", "//div[@class='sub_nav-bg']/ul/li[4]/a"),
    WAYSMART("waySmart", "//div[@class='sub_nav-bg']/ul/li[5]/a"),
    
    OVERALL_SCORE_DHX(null, "//input[@name='***-form:***:overallScoreFilter']"),
    SPEED_SCORE_DHX(null, "//input[@name='***-form:***:speedScoreFilter']"),
    STYLE_SCORE_DHX(null, "//input[@name='***-form:***:styleScoreFilter']"),
    SEATBELT_SCORE_DHX(null, "//input[@name='***-form:***:seatbeltScoreFilter']"),

    OVERALL_SCORE_SORT(null, "***-form:***:overallScoreheader:sortDiv"),
    SPEED_SCORE_SORT(null, "***-form:***:speedScoreheader:sortDiv"),
    STYLE_SCORE_SORT(null, "***-form:***:styleScoreheader:sortDiv"),
    SEATBELT_SCORE_SORT(null, "***-form:***:seatbeltScoreheader:sortDiv"),
    
    TOOL_EMAIL("E-mail This Report", "***-form:***-emailMenuItem"),
    TOOL_PDF("Export To PDF", "***-form:***-export_menu_item:anchor"),
    TOOL_EXCEL("Export To Excel", "***-form:***-exportExcelMEnuItem:icon"),
    
    COUNTER("Showing XXX to YYY of ZZZ records", "***-form:header"),

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

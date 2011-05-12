package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum ReportsBarEnum implements SeleniumEnums {
    DRIVERS("Drivers", "//div[@class='sub_nav-bg']/ul/li[1]/a"),
    VEHICLES("Vehicles", "//div[@class='sub_nav-bg']/ul/li[2]/a"),
    IDLING("Idling", "//div[@class='sub_nav-bg']/ul/li[3]/a"),
    DEVICES("Devices", "//div[@class='sub_nav-bg']/ul/li[4]/a"),
    WAYSMART("waySmart", "//div[@class='sub_nav-bg']/ul/li[5]/a"),
    
    
    OVERALL_SCORE_DHX(null, "***-form:***:overallScoreheader:sortDiv"),
    SPEED_SCORE_DHX(null, "***-form:***:speedScoreheader:sortDiv"),
    STYLE_SCORE_DHX(null, "***-form:***:styleScoreheader:sortDiv"),
    SEATBELT_SCORE_DHX(null, "***-form:***:seatbeltScoreheader:sortDiv"),

    OVERALL_SCORE_ARROW(null, "//div[@id='***-form:***:overallScoreheader:sortDiv']/span/span/span/div/img"),
    SPEED_SCORE_ARROW(null, "//div[@id='***-form:***:speedScoreheader:sortDiv']/span/span/span/div/img"),
    STYLE_SCORE_ARROW(null, "//div[@id='***-form:***:styleScoreheader:sortDiv']/span/span/span/div/img"),
    SEATBELT_SCORE_ARROW(null, "//div[@id='***-form:***:seatbeltScoreheader:sortDiv']/span/span/span/div/img"),
    
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

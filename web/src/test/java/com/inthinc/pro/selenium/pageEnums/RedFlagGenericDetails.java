package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum RedFlagGenericDetails implements SeleniumEnums {
    
    
    /* Generic Red Flag */    
    SETTING_HEADER("Setting", "//form[@id='redFlagForm']/div/div[2]/div/table[2]/tbody/tr/td/table/tbody/tr[1]/th[1]"),
    SETTING_ENTRY(null, "//form[@id='redFlagForm']/div/div[2]/div/table[2]/tbody/tr/td/table/tbody/tr[2]/td/ul/li[###]"),

    
    /* Vehicle Red Flag */
    VEHICLE_SETTING_TAMPERING("Tampering", "redFlagForm:vehicTamp"),
    VEHICLE_SETTING_IGNITION_ON("Vehicle ignition ON", "redFlagForm:vehicIgOn"),
    VEHICLE_SETTING_LOW_BATTERY("Vehicle battery is low", "redFlagForm:vehicLoBat"),
    VEHICLE_SETTING_IDLING_LABEL("Idling Limit (minutes)", "//table[@id='redFlagForm:idlingThreshold']/../../td[1]"),
    VEHICLE_SETTING_IDLING_SLIDER("Idling Limit (minutes)", "redFlagForm:idlingThresholdTip"),
    VEHICLE_SETTING_IDLING_VALUE("Idling Limit (minutes)", "redFlagForm:idlingThresholdInput"),
    
    /* Speeding Red Flag */
    SPEEDING_LIMIT_HEADER("Limit", "//tr[@class='datagrid']/th[1]"),
    SPEEDING_EXCEEDING_BY_HEADER("When exceeding posted limit by", "//tr[@class='datagrid']/th[2]"),
    SPEEDING_NOTIFICATION_HEADER("Notification:", "//tr[@class='datagrid']/th[3]"),
    
    SPEEDING_LIMIT_LABEL(null, "//table[@id='redFlagForm:speed###']/../../td[1]"),
    SPEEDING_LIMIT_SLIDER(null, "redFlagForm:speed###Tip"),
    SPEEDING_LIMIT_VALUE(null, "redFlagForm:speed###MPH"),
    
    /* Driving Style Red Flag */
    
    STYLE_SETTING_HEADER("Setting", "//tr[@class='datagrid']/th[1]"),
    STYLE_SEVERITY_HEADER("Severity", "//tr[@class='datagrid']/th[2]"),
    
    STYLE_HARD_ACCELERATE("Hard Accelerate", "//tr[@class='datagrid']/../tr[2]/td[1]"),
    STYLE_HARD_BRAKE("Hard Brake", "//tr[@class='datagrid']/../tr[3]/td[1]"),
    STYLE_UNSAFE_TURN("Unsafe Turn", "//tr[@class='datagrid']/../tr[4]/td[1]"),
    STYLE_HARD_BUMP("Hard Bump", "//tr[@class='datagrid']/../tr[5]/td[1]"),
    
    STYLE_HARD_ACCELERATE_SLIDER_POSITION(null, "redFlagForm:hardAccelerationTip"),
    STYLE_HARD_BRAKE_SLIDER_POSITION(null, "redFlagForm:hardBrakeTip"),
    STYLE_UNSAFE_TURN_SLIDER_POSITION(null, "redFlagForm:hardTurnTip"),
    STYLE_HARD_BUMP_SLIDER_POSITION(null, "redFlagForm:hardVerticalTip"),
    
    STYLE_HARD_ACCELERATE_SEVERITY(null, "redFlagForm:hardAccelerationLevel"),
    STYLE_HARD_BRAKE_SEVERITY(null, "redFlagForm:hardBrakeLevel"),
    STYLE_UNSAFE_TURN_SEVERITY(null, "redFlagForm:hardTurnLevel"),
    STYLE_HARD_BUMP_SEVERITY(null, "redFlagForm:hardVerticalLevel"),
    
    ;
    private String text, url;
    private String[] IDs;
    
    private RedFlagGenericDetails(String url){
        this.url = url;
    }
    private RedFlagGenericDetails(String text, String ...IDs){
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

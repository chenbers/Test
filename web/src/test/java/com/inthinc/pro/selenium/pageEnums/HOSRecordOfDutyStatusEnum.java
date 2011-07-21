package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum HOSRecordOfDutyStatusEnum implements SeleniumEnums {

    
    ;
    
    private String text, url;
    private String[] IDs;
    
    private HOSRecordOfDutyStatusEnum(String url){
        this.url = url;
    }
    private HOSRecordOfDutyStatusEnum(String text, String ...IDs){
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

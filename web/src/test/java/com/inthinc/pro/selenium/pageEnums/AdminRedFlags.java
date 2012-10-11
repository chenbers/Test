package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum AdminRedFlags implements SeleniumEnums {
    
    DEFAULT_URL(appUrl + "/admin/redFlags"),
    TITLE("Admin - Red Flags", "//span[@class='admin']"),
    
    
    ;
    private String text, url;
    private String[] IDs;
    
    private AdminRedFlags(String url){
        this.url = url;
    }
    private AdminRedFlags(String text, String ...IDs){
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

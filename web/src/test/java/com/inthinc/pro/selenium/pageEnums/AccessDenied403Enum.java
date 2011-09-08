package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum AccessDenied403Enum implements SeleniumEnums {

    TITLE("                   Access Denied                ", "//div[@class='panel_title']"),
    
    P1(null, "//div[@class='panel_content']/p/span"),
    
    LINE(null, "//ul/li[###]/span"),
    
    RETURN_TO_HOMEPAGE("return to home page", "error403")
    ;

    private String text, url;
    private String[] IDs;

    private AccessDenied403Enum(String url) {
        this.url = url;
    }

    private AccessDenied403Enum(String text, String... IDs) {
        this.text = text;
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

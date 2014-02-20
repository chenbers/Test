package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum ErrorPageEnum implements SeleniumEnums {

    ERROR_PAGE_URL(""),

    /* Main Login Page Elements */

    TRY_AGAIN_BUTTON("Try Again", "//*[@id='errorTryAgain']"),
    ;
    
    private String text, url;
    private String[] IDs;
    
    private ErrorPageEnum(String url){
    	this.url = url;
    }
    private ErrorPageEnum(String text, String ...IDs){
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

package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum TeamStyleEnum implements SeleniumEnums {

    STYLE_SCORE(null, Xpath.start().div(Id.clazz("middle")).table().tbody().tr().toString()),
    STYLE_SCORE_LABEL(null, Xpath.start().div(Id.clazz("middle")).text().toString())

    ;

    private String text, url;
    private String[] IDs;
    
    private TeamStyleEnum(String url){
    	this.url = url;
    }
    private TeamStyleEnum(String text, String ...IDs){
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

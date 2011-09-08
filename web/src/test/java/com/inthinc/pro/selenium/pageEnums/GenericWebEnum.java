package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum GenericWebEnum implements SeleniumEnums {
	

	FIND_ANCHOR_BY_CONTAINS_TEXT(null, Xpath.start().a(Id.contains(Id.text(), "***")).toString()),
    
    ;
    
    private String text, url;
    private String[] IDs;
    
    private GenericWebEnum(String url){
    	this.url = url;
    }
    private GenericWebEnum(String text, String ...IDs){
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

package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.utils.Xpath;

public enum GenericWebEnum implements SeleniumEnums {
    
    ;
    
    private String ID, xpath, xpath_alt, text, url;
    
    private GenericWebEnum(String ID){
        this.ID=ID;
    }
    
    private GenericWebEnum(Xpath xpath){
        this.xpath = xpath.toString();
    }
    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getURL() {
        return url;
    }

    @Override
    public String getXpath() {
        return xpath;
    }

    @Override
    public String getXpath_alt() {
        return xpath_alt;
    }
    
    @Override
    public String getID() {
        return ID;
    }
    
    @Override
    public void setText(String text) {
        this.text = text;
    }

}

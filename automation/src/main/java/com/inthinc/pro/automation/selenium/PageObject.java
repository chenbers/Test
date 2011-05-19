package com.inthinc.pro.automation.selenium;

import java.util.HashMap;

import com.inthinc.pro.automation.enums.AutomationEnum;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.selenium.CoreMethodLib;

public class PageObject {

    protected CoreMethodLib selenium;
    private volatile static HashMap<Long, String> currentLocation = new HashMap<Long, String>();

    public PageObject() {
        selenium = GlobalSelenium.getSelenium();
    }

    protected CoreMethodLib getSelenium() {
        return selenium;
    }
    
    protected static void setCurrentPage(){
        Long currentThread = Thread.currentThread().getId();
        String[] location = GlobalSelenium.getSelenium().getLocation().split("/");
        String page = location[location.length-1];
        currentLocation.put(currentThread, page);
    }
    
    protected static String getCurrentPage(){
        Long currentThread = Thread.currentThread().getId();
        return currentLocation.get(currentThread);
    }
    
    public void addErrorWithExpected(String errorName, String error, String expected){
        selenium.getErrors().addError(errorName, error);
        selenium.getErrors().addExpected(errorName, expected);
    }
    
    public void addError(String errorName, String error){
        selenium.getErrors().addError(errorName, error);
    }
    
    public void addError(AutomationEnum errorName, String error){
        selenium.getErrors().addError(selenium.getLocator(errorName), error);
    }
}

package com.inthinc.pro.automation.selenium;

import org.openqa.selenium.WebDriver;

public interface Page {
    public void load();
    
    public void isLoaded();
    
    public WebDriver getDriver();
    
    public String getPath();
}

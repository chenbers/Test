package com.inthinc.pro.web.selenium;

import org.openqa.selenium.WebDriver;

public interface Page {
    public void load();
    
    public void isLoaded();
    
    public WebDriver getDriver();
    
    public String getPath();
}

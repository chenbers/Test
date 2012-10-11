package com.inthinc.pro.automation.enums;

import java.util.EnumSet;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.iphone.IPhoneDriver;

public enum Browsers {
    
    ANDROID("android", AndroidDriver.class),
    CHROME("chrome", ChromeDriver.class),
    FIREFOX("ff", FirefoxDriver.class),
    INTERNET_EXPLORER("ie", InternetExplorerDriver.class),
    IPHONE("iphone", IPhoneDriver.class),
    
    ;
    
    private Class<? extends WebDriver> baseDriver;
    private String name;

    private volatile static HashMap<Long, WebDriver> webDriverThread = new HashMap<Long, WebDriver>();
    
    private Browsers(String shortName, Class<? extends WebDriver> baseDriver){
        this.baseDriver = baseDriver;
        name = shortName;
    }
    
    public WebDriver getDriver(){
        long thread = Thread.currentThread().getId();
        WebDriver driver = webDriverThread.get(thread);
        
        try {
            if (driver == null){
                driver = baseDriver.newInstance();
            } else {
                driver.getCurrentUrl();
            }
        } catch (Exception e){
            try {
                driver = baseDriver.newInstance();
            } catch (InstantiationException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        }
        return driver;
    }
    
    
    private static HashMap<String, Browsers> lookupByName = new HashMap<String, Browsers>();

    static {
        for (Browsers b : EnumSet.allOf(Browsers.class)) {
            lookupByName.put(b.getName(), b);
        }
    }

    public String getName(){
        return name;
    }
    
    public static Browsers getBrowserByName(String name){
        String nameWithoutVersion = name.replaceAll("[0-9._]", "");
        return lookupByName.get(nameWithoutVersion);
    }
    
}

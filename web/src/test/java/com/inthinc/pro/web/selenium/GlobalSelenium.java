package com.inthinc.pro.web.selenium;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class GlobalSelenium {
	 
    // volatile is needed so that multiple thread can reconcile the instance
    // semantics for volatile changed in Java 5.
//    private volatile static GlobalSelenium globalSelenium;
    private volatile static HashMap<Long, CoreMethodLib> multiplicative = new HashMap<Long, CoreMethodLib>();
    

    public static CoreMethodLib getSelenium(){
    	Long currentThread = Thread.currentThread().getId();
    	return multiplicative.get(currentThread);
    }
    
    
    public static CoreMethodLib getYourOwn(WebDriver driver) {
    	CoreMethodLib selenium;
    	Long currentThread = Thread.currentThread().getId();
    	
    	if (!multiplicative.containsKey(currentThread) || multiplicative.get(currentThread)==null) {
    		String url = "https://qa.tiwipro.com:8423/tiwipro/";
        	selenium = new CoreMethodLib(driver, url);
    		multiplicative.put(currentThread, selenium);
    	}
    	return multiplicative.get(currentThread);
    }
    
    public static CoreMethodLib getYourOwn() {
    	return getYourOwn(new FirefoxDriver());
    }
    
    public static void dieSeleniumDie() {
    	Long currentThread = Thread.currentThread().getId();
    	((CoreMethodLib) multiplicative.get(currentThread)).getUnderlyingWebDriver();
    	multiplicative.remove(currentThread);
    }
}

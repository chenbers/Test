package com.inthinc.pro.web.selenium;

import org.apache.commons.lang.NullArgumentException;

public class Singleton {
	 
    // volatile is needed so that multiple thread can reconcile the instance
    // semantics for volatile changed in Java 5.
    private volatile static Singleton singleton;

    private Core selenium;
  
    public Core getSelenium(){
    	
    	return selenium;
    }
    private Singleton() {
    	
    }
    
  //overloaded singleton used by the start_selenium method to pass information
    public static Singleton getSingleton(String host, String browser, String url) {
    	if(singleton==null) {
            synchronized(Singleton.class){
            // this is needed if two threads are waiting at the monitor at the
            // time when singleton was getting instantiated
              if(singleton == null) {
                    try {
                    	singleton = new Singleton();
                    	singleton.selenium = new Core(host, 4444, browser, url);
					} catch (Exception e) {
						e.printStackTrace();
					}
					}
            	} 
    		}
    return singleton;
}
    
    public static Singleton getSingleton() {
        // needed because once there is singleton available no need to acquire
        // monitor again & again as it is costly
        if(singleton==null) {
            synchronized(Singleton.class){
            // this is needed if two threads are waiting at the monitor at the
            // time when singleton was getting instantiated
                if(singleton == null) {
            		String host = "localhost";
            		String browser = "*iexplore";
            		String url = "https://qa.tiwipro.com:8423/tiwipro/";
                	try{
                		host = System.getenv("Selenium_host");
                		browser = System.getenv("Selenium_Browser");
                		url = System.getenv("Selenium_Url");
                		if (host.isEmpty()||browser.isEmpty()||url.isEmpty()){
                			throw new NullArgumentException("No environment Variables");
                		}
                	}catch(Exception e){
                		host = "localhost";
                		browser = "*iexplore";
                		url = "https://qa.tiwipro.com:8423/tiwipro/";
                	}
                    try {
                    	singleton = new Singleton();
                    	singleton.selenium = new Core(host, 4444, browser, url);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
            }
        }
        return singleton;
    }   
}

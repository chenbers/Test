package com.inthinc.pro.web.selenium;





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
    // synchronized keyword has been removed from here
    public static Singleton getSingleton() {
        // needed because once there is singleton available no need to acquire
        // monitor again & again as it is costly
        if(singleton==null) {
            synchronized(Singleton.class){
            // this is needed if two threads are waiting at the monitor at the
            // time when singleton was getting instantiated
                if(singleton == null) {
                    try {
                    	singleton = new Singleton();
						//singleton.selenium = (Selenium) new SeleniumServer();
						singleton.selenium = new Core("localhost", 4444, "*firefox", "https://qa.tiwipro.com:8423/tiwipro/");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
                    
                }
            }
        return singleton;
    }
}

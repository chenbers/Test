package com.inthinc.pro.web.selenium;
import com.inthinc.pro.web.selenium.InthincTest;

public class Singleton extends InthincTest {
	 
    // volatile is needed so that multiple thread can reconcile the instance
    // semantics for volatile changed in Java 5.
    private volatile static Singleton singleton;

    private Core selenium;
  
    public Core getSelenium(){
    	
    	return selenium;
    }
    private Singleton() {
    	
    }
    
  //overloaded singleton used by the start_selenium method to pass data file information
    public static Singleton getSingleton(String file_name, String test_case) {
    	if(singleton==null) {
            synchronized(Singleton.class){
            // this is needed if two threads are waiting at the monitor at the
            // time when singleton was getting instantiated
              if(singleton == null) {
                    try {
                    	singleton = new Singleton();
                    	set_test_case(file_name, test_case);
                    	singleton.selenium = new Core((get_data("Settings","Host")), 4444, (get_data("Settings","Browser")), (get_data("Settings","URL")));
					} catch (Exception e) {
						// TODO Auto-generated catch block
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
                    try {
                    	singleton = new Singleton();
                    	singleton.selenium = new Core(System.getenv("Selenium_host"), 4444, System.getenv("Selenium_Browser"), System.getenv("Selenium_Url"));
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

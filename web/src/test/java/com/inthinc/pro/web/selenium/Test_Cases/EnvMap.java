package com.inthinc.pro.web.selenium.Test_Cases;

import org.junit.Ignore;
import org.junit.Test;
import com.inthinc.pro.web.selenium.portal.Login.Login;
import com.inthinc.pro.web.selenium.DataReaderLib;
import com.inthinc.pro.web.selenium.InthincTest;
import com.inthinc.pro.web.selenium.portal.Masthead.*;


import java.util.Map;

public class EnvMap {
	
    public static void main (String[] args) {
 
//    	String host = System.getenv("Selenium_host");
//    	String browser = System.getenv("Selenium_Browser");
//    	String url = System.getenv("Selenium_Url");
//    	System.out.println(host);
//    	System.out.println(browser);
//    	System.out.println(url);
    	
    	// String s = "fred";    // use this if you want to test the exception below
        String s = "0";

        try
        {
          // the String to int conversion happens here
          int i = Integer.parseInt(s.trim());

          // print out the value after the conversion
          System.out.println("int i = " + i);
        }
        catch (NumberFormatException nfe)
        {
          System.out.println("NumberFormatException: " + nfe.getMessage());
        }

    	
    	
//        Map<String, String> env = System.getenv();
//        for (String envName : env.keySet()) {
//        	//
//        	if (envName =="NotThere");{
////            System.out.format("%s=%s%n",envName, env.get(envName));
//        	  //get heading
//          System.out.println(envName);
//        	// get value
//           System.out.println(env.get(envName));
//        	}
//          
//        }
    }
}












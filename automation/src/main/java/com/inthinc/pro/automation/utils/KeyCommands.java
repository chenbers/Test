package com.inthinc.pro.automation.utils;

import java.awt.AWTException;
import java.awt.Robot;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class KeyCommands {

    public static void typeKey(Integer keyCode){
    	try {
    	    Robot r = new Robot();
    	    r.keyPress(keyCode);
    	    r.keyRelease(keyCode);
    	} catch (AWTException e) {
    	    e.printStackTrace();
    	}
    }
}

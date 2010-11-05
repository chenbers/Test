package com.inthinc.pro.web.selenium;

import com.inthinc.pro.web.selenium.Debug.Error_Catcher;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleniumException;

import static org.junit.Assert.*;

public class Try extends DefaultSelenium{
	
	private Error_Catcher errors = new Error_Catcher();

	public Try(String serverHost, int serverPort, String browserStartCommand,
			String browserURL) {
		super(serverHost, serverPort, browserStartCommand, browserURL);
		// TODO Auto-generated constructor stub
	}
	
	
	public void click(String locator, String name) {
		try{
			click(locator);
		}catch(SeleniumException e){
			errors.Error(name, e);
		}catch(Exception e){
			errors.Error(name, e);
		}
	}
	
	public void isElementPresent(String element, String name){
		try{
			assertTrue( isElementPresent(element));
		}catch(AssertionError e){
			errors.Error(name, e);
		}catch(SeleniumException e){
			errors.Error(name, e);
		}catch(Exception e){
			errors.Error(name, e);
		}
	}
	
	public void isElementNotPresent(String element, String name){
		try{
			assertFalse( isElementPresent(element));
		}catch(AssertionError e){
			errors.Error(name, e);
		}catch(SeleniumException e){
			errors.Error(name, e);
		}catch(Exception e){
			errors.Error(name, e);
		}
	}
	
	public void getText(String locator, String expected, String name){
		try{
			assertFalse( getText(locator)==expected);
		}catch(AssertionError e){
			errors.Error(name, getText(locator));
			errors.Expected(name, expected);
		}catch(SeleniumException e){
			errors.Error(name, e);
		}catch(Exception e){
			errors.Error(name, e);
		}
	}
	
	public void getTable(String locator, String expected, String name){
		try{
			assertFalse( getTable(locator)==expected);
		}catch(AssertionError e){
			errors.Error(name, getText(locator));
			errors.Expected(name, expected);
		}catch(SeleniumException e){
			errors.Error(name, e);
		}catch(Exception e){
			errors.Error(name, e);
		}
	}
	
}
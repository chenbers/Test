package com.inthinc.pro.web.selenium;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.inthinc.pro.web.selenium.Debug.Error_Catcher;
import com.thoughtworks.selenium.CommandProcessor;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleniumException;

public class Core extends DefaultSelenium{

	private Error_Catcher errors = new Error_Catcher();
	
	public Core(CommandProcessor processor) {
		super(processor);
	}

	public Core(String serverHost, int serverPort, String browserStartCommand,
			String browserURL) {
		super(serverHost, serverPort, browserStartCommand, browserURL);
	}
	
	public void open(String locator,String error_name){
		try{
			open(locator);
		}catch(SeleniumException e){
			errors.Error(error_name, e);
		}catch(Exception e){
			errors.Error(error_name, e);
		}
	}

	
	public void click(String locator, String error_name) {
		try{
			click(locator);
		}catch(SeleniumException e){
			errors.Error(error_name, e);
		}catch(Exception e){
			errors.Error(error_name, e);
		}
	}
	
	public void isElementPresent(String element, String error_name){
		try{
			assertTrue( isElementPresent(element));
		}catch(AssertionError e){
			errors.Error(error_name, e);
		}catch(SeleniumException e){
			errors.Error(error_name, e);
		}catch(Exception e){
			errors.Error(error_name, e);
		}
	}
	
	public void isElementNotPresent(String element, String error_name){
		try{
			assertFalse( isElementPresent(element));
		}catch(AssertionError e){
			errors.Error(error_name, e);
		}catch(SeleniumException e){
			errors.Error(error_name, e);
		}catch(Exception e){
			errors.Error(error_name, e);
		}
	}
	
	public void getText(String locator, String expected, String error_name){
		try{
			assertFalse( getText(locator)==expected);
		}catch(AssertionError e){
			errors.Error(error_name, getText(locator));
			errors.Expected(error_name, expected);
		}catch(SeleniumException e){
			errors.Error(error_name, e);
		}catch(Exception e){
			errors.Error(error_name, e);
		}
	}
	
	public void getTable(String locator, String expected, String error_name){
		try{
			assertFalse( getTable(locator)==expected);
		}catch(AssertionError e){
			errors.Error(error_name, getText(locator));
			errors.Expected(error_name, expected);
		}catch(SeleniumException e){
			errors.Error(error_name, e);
		}catch(Exception e){
			errors.Error(error_name, e);
		}
	}
	
	public void type(String locator, String text, String error_name){
		try{
			type(locator, text);
		}catch(SeleniumException e){
			errors.Error(error_name, e);
		}catch(Exception e){
			errors.Error(error_name, e);
		}
	}
	
	
	public void isTextPresent(String text, String error_name){
		try{
			assertTrue( isTextPresent(text));
		}catch(AssertionError e){
			errors.Error(error_name, e);
		}catch(SeleniumException e){
			errors.Error(error_name, e);
		}catch(Exception e){
			errors.Error(error_name, e);
		}
	}
	
	public void isTextNotPresent(String text, String error_name){
		try{
			assertFalse( isTextPresent(text));
		}catch(AssertionError e){
			errors.Error(error_name, e);
		}catch(SeleniumException e){
			errors.Error(error_name, e);
		}catch(Exception e){
			errors.Error(error_name, e);
		}
	}
	
	public Error_Catcher getErrors(){
		return errors;
	}

	public void TextPresent( String text){
		try {
			assertTrue(isTextPresent(text));
			}catch (AssertionError e){
				
			}
	}
	
	public void ElementPresent( String element){
		try {
			assertTrue(isElementPresent(element));
			}catch (AssertionError e){
				
			}
	}
	
	public void GetText( String location, String text){
	try {
		AssertEquals(getText(location), text);
		}catch (AssertionError e){
		}
	}
	
	public void AssertEquals( String local, String text){
		try {
			assertTrue(local==text);
			}catch (AssertionError e){
			}
		}
	
}

package com.inthinc.pro.web.selenium;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.inthinc.pro.web.selenium.Debug.Error_Catcher;
import com.thoughtworks.selenium.CommandProcessor;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleniumException;

public class Core extends DefaultSelenium{

	private static Error_Catcher errors = new Error_Catcher();
	
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
			errors.Error(error_name, "Failed");
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
			errors.Error(error_name, "Failed");
		}catch(SeleniumException e){
			errors.Error(error_name, e);
		}catch(Exception e){
			errors.Error(error_name, e);
		}
	}
	
	public void getLocation(String expected, String error_name){
		Integer truefalse = -1;
		try{
			truefalse = getLocation().indexOf(expected);
			AssertNotEqual(truefalse, -1, error_name);
		}catch(SeleniumException e){
			errors.Error(error_name, e);
		}catch(Exception e){
			errors.Error(error_name, e);
		}
	}
	
	public String getText(String locator, String expected, String error_name){
		String text = "";
		try{
			text = getText(locator);
			assertTrue(text.compareTo(expected)==0);
		}catch(AssertionError e){
			errors.Error(error_name, getText(locator));
			errors.Expected(error_name, expected);
		}catch(SeleniumException e){
			errors.Error(error_name, e);
		}catch(Exception e){
			errors.Error(error_name, e);
		}finally{			
			if (text==""){
				text = "Failed";
			}
		}
		return text;
	}
	
	public String getText(String locator, String error_name){
		String text = "";
		try{
			text = getText(locator);
		}catch(SeleniumException e){
			errors.Error(error_name, e);
		}catch(Exception e){
			errors.Error(error_name, e);
		}finally{			
			if (text==""){
				text = "Failed";
			}
		}
		return text;
	}
	
	public String getTable(String locator, String expected, String error_name){
		String text = "";
		try{
			assertFalse( getTable(locator)==expected);
		}catch(AssertionError e){
			errors.Error(error_name, getTable(locator));
			errors.Expected(error_name, expected);
		}catch(SeleniumException e){
			errors.Error(error_name, e);
		}catch(Exception e){
			errors.Error(error_name, e);
		}finally{
			if (text==""){
				text = "Failed";
			}
		}		
		return text;
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
			assertTrue(isTextPresent(text));
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
			assertFalse(isTextPresent(text));
		}catch(AssertionError e){
			errors.Error(error_name, e);
		}catch(SeleniumException e){
			errors.Error(error_name, e);
		}catch(Exception e){
			errors.Error(error_name, e);
		}
	}
	
	public void waitForPageToLoad(String timeout, String error_name){
		try{
			waitForPageToLoad(timeout);
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
		AssertEquals(getText(location), text, "error");
		}catch (AssertionError e){
		}
	}
	
	public void AssertEquals( String actual, String expected, String error_name){
		try {
			assertTrue(actual.compareTo(expected)==0);
		}catch(AssertionError e){
			errors.Error(error_name, actual);
			errors.Expected(error_name, expected);
		}catch(Exception e){
			errors.Error(error_name, e);
		}
	}
	
	public void AssertEquals( Integer actual, Integer expected, String error_name){
		try {
			assertTrue(actual==expected);
		}catch(AssertionError e){
			errors.Error(error_name, actual.toString());
			errors.Expected(error_name, expected.toString());
		}catch(Exception e){
			errors.Error(error_name, e);
		}
	}
	
	public void AssertNotEqual( String actual, String not_expected, String error_name){
		try {
			assertFalse(actual.compareTo(not_expected)==0);
		}catch(AssertionError e){
			errors.Error(error_name, actual);
			errors.Expected(error_name, "not"+not_expected);
		}catch(Exception e){
			errors.Error(error_name, e);
		}
	}
	
	public void AssertNotEqual( Integer actual, Integer not_expected, String error_name){
		try {
			assertFalse(actual==not_expected);
		}catch(AssertionError e){
			errors.Error(error_name, "False");
		}catch(Exception e){
			errors.Error(error_name, e);
		}
	}
}

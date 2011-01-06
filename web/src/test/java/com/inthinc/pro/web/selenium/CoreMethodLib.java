/****************************************************************************************
 * Extend the functionality of DefaultSelenium, but add some error handling around it
 * 
 * try{}
 * catch(AssertionError e){ errors.Error(NameOfError, e)}
 * catch(SeleniumException e){ errors.Error(NameOfError, e)}
 * catch(Exception e){ errors.Error(NameOfError, e)}
 * 
 * @see DefaultSelenium
 * @see ErrorCatcher
 * @author dtanner
 *
 */


package com.inthinc.pro.web.selenium;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import com.inthinc.pro.web.selenium.Debug.ErrorCatcher;
import com.thoughtworks.selenium.CommandProcessor;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleniumException;


public class CoreMethodLib extends DefaultSelenium{

	private static ErrorCatcher errors;
	
	public CoreMethodLib(CommandProcessor processor) {
		super(processor);
	}

	public CoreMethodLib(String serverHost, int serverPort, String browserStartCommand,
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
	
	
	public int isChecked(String element,String condition, String error_name){
		try{
			assertTrue( isChecked(element));
		}catch(SeleniumException e){
			errors.Error(error_name, e);
			return 0;
		}catch(Exception e){
			errors.Error(error_name, e);
			return 0;
		}
		//returns 1 if checked 0 if not checked
		return 1;
	}
		
	public String isnotChecked(String element, String error_name){
			try{
				assertFalse( isChecked(element));
			}catch(SeleniumException e){
				errors.Error(error_name, e);
				return "no";
			}catch(Exception e){
				errors.Error(error_name, e);
				return "no";
			}
			//returns 1 is not checked 0 if checked
			return "yes";
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
	
	public String getLocation(String expected, String error_name){
		Integer truefalse = -1;
		String location = "Could not get location";
		try{
			location = getLocation();
			truefalse = location.indexOf(expected);
			AssertNotEqual(truefalse, -1, error_name);
		}catch(SeleniumException e){
			errors.Error(error_name, e);
		}catch(Exception e){
			errors.Error(error_name, e);
		}
		return location;
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
	
	
	public String getTable(String locator, int row, int col, String error_name){
		String text = "";
		try{
			getTable(locator + "." + row + "." + col);
		}catch(AssertionError e){
			errors.Error(error_name, getTable(locator));
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
	
	public void isVisible( String element, String error_name ){
		try{
			assertTrue( isVisible(element));
		}catch(AssertionError e){
			errors.Error(error_name, e);
		}catch(SeleniumException e){
			errors.Error(error_name, e);
		}catch(Exception e){
			errors.Error(error_name, e);
		}
	}
	
	public void isNotVisible( String element, String error_name ){
		try{
			assertFalse( isVisible(element) );
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
	
	public ErrorCatcher getErrors(){
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
	
	public void Pause( Integer timeout_in_secs ){
		try{
			Thread.currentThread();
			Thread.sleep((long)(timeout_in_secs * 1000));
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public void select(String locator, String label, String error_name){
		try{
			select(locator, label);
			Pause(5);
		}catch(SeleniumException e){
			errors.Error(error_name, e);
		}catch(Exception e){
			errors.Error(error_name, e);
		}
	}
	
	
	public void selectDhxCombo(String entry_name, String error_name){
		entry_name = "//div[text()=\""+entry_name+"\"]";
		click(entry_name, error_name);
		Pause(5);
	}
	public void addtoPanel(String locator, String itemtomove, String error_name){
			addSelection(locator + "-from", itemtomove);
			click(locator + "-move_right");
			Pause(2);
		}
	public void removefromPanel (String locator, String itemtomove, String error_name){
			addSelection(locator + "-picked", itemtomove);
			click(locator + "-move_left");
			Pause(2);
		}
	public void moveallPanel (String locator, String moveoption, String error_name){
			if (moveoption.contentEquals("Right")){
				click(locator + "-move_all_right",error_name);
			}else if (moveoption.contentEquals("Left")){
				click(locator + "-move_all_left", error_name);
			}else{
				System.out.printf("ERROR - invald data in moveallPanel.");
			}
			Pause(2);
		}
	
	public void wait_for_element_present(String watch_for){
		wait_for_element_present(watch_for, "link");
	}
	public void wait_for_element_present(String watch_for, String type){
		Integer x = 0;
		while (true){
			x++;
			try{
				if (type.compareToIgnoreCase("link")==0){
					if ( isElementPresent("link="+watch_for)) break;
				}else if(type.compareToIgnoreCase("text")==0){
					if ( isTextPresent(watch_for))break;
				}else if (type.compareToIgnoreCase("element")==0){
					if ( isElementPresent(watch_for))break;
				}
			}catch(Exception e){
				continue;
			}
			Pause(2);
			if (x==60)break;
		}
	}
	
	@Override
	public void start(){
		errors = new ErrorCatcher();
		super.start();
	}
	
	public Boolean inSession(){
		try{
			getAllWindowNames();
		}catch(NullPointerException e){
			return false;
		}
		return true;
	}
}

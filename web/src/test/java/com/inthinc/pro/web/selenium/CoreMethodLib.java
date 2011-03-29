package com.inthinc.pro.web.selenium;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;

import com.google.common.base.Supplier;
import com.inthinc.pro.web.selenium.Debug.ErrorCatcher;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleniumException;

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
public class CoreMethodLib extends WebDriverBackedSelenium{
    public static Integer PAGE_TIMEOUT = 3000;

public CoreMethodLib(WebDriver baseDriver, String baseUrl) {
		super(baseDriver, baseUrl);
	}

	public CoreMethodLib(Supplier<WebDriver> maker, String baseUrl) {
		super(maker, baseUrl);
	}

	private static ErrorCatcher errors = new ErrorCatcher();
	
	private String getLocator(SeleniumEnums checkIt) {
		if (checkIt.getID()!=null)return checkIt.getID();
		else if (checkIt.getXpath()!=null)return checkIt.getXpath();
		else if (checkIt.getXpath_alt()!=null)return checkIt.getXpath_alt();
		return null;
	}

	public void open(String locator,String error_name){
		try{
			open(locator);
		}catch(SeleniumException e){
			errors.addError(error_name, e);
		}catch(RuntimeException e) {
			throw new RuntimeException(e);
		}catch(Exception e){
			errors.addError(error_name, e);
		}
	}

    private void process(Method method, String locator, String error_name) {
        try {
            method.invoke(this, locator, error_name);
        } catch (Exception e) {
            if(e instanceof RuntimeException)
                throw new RuntimeException(e);
            errors.addError(error_name, e);
        }
    }
	public void click(String locator, String error_name) {
		try{
			click(locator);
		}catch(SeleniumException e){
			errors.addError(error_name, e);
		}catch(RuntimeException e) {
			throw new RuntimeException(e);
		}catch(Exception e){
			errors.addError(error_name, e);
		}
	}
	
	public int isChecked(String element,String condition, String error_name){
		try{
			assertTrue( isChecked(element));
		}catch(SeleniumException e){
			errors.addError(error_name, e);
			return 0;
		}catch(RuntimeException e) {
			throw new RuntimeException(e);
		}catch(Exception e){
			errors.addError(error_name, e);
			return 0;
		}
		//returns 1 if checked 0 if not checked
		return 1;
	}
		
	public String isnotChecked(String element, String error_name){
			try{
				assertFalse( isChecked(element));
			}catch(SeleniumException e){
				errors.addError(error_name, e);
				return "no";
			}catch(RuntimeException e) {
				throw new RuntimeException(e);
			}catch(Exception e){
				errors.addError(error_name, e);
				return "no";
			}
			//returns 1 is not checked 0 if checked
			return "yes";
		}
	
	public void isElementPresent(String element, String error_name){
		try{
			assertTrue( isElementPresent(element));
		}catch(AssertionError e){
			errors.addError(error_name, "Failed");
		}catch(SeleniumException e){
			errors.addError(error_name, e);
		}catch(RuntimeException e) {
			throw new RuntimeException(e);
		}catch(Exception e){
			errors.addError(error_name, e);
		}
	}
	
	public void isElementNotPresent(String element, String error_name){
		try{
			assertFalse( isElementPresent(element));
		}catch(AssertionError e){
			errors.addError(error_name, "Failed");
		}catch(SeleniumException e){
			errors.addError(error_name, e);
		}catch(RuntimeException e) {
			throw new RuntimeException(e);
		}catch(Exception e){
			errors.addError(error_name, e);
		}
	}
	
   public void getLocation(SeleniumEnums checkIt) {
        getLocation(getLocator(checkIt), "getLocation: "+checkIt.toString());     
    }
   
	public String getLocation(String expected, String error_name){//TODO: jwimmer: question for DTanner: what does "getLocation(...)" do?  should it be changed/renamed to validate location???
		Integer truefalse = -1;
		String location = "Could not get location";
		try{
			location = getLocation();
			truefalse = location.indexOf(expected);
			AssertNotEqual(truefalse, -1, error_name);
		}catch(SeleniumException e){
			errors.addError(error_name, e);
		}catch(RuntimeException e) {
			throw new RuntimeException(e);
		}catch(Exception e){
			errors.addError(error_name, e);
		}
		return location;
	}
	
	public String getText(String locator, String expected, String error_name){//TODO: jwimmer: quesiton for DTanner: same as getLocation? should this be verify or getandverify ???
		String text = "";
		try{
			text = getText(locator);
			assertTrue(text.compareTo(expected)==0);
		}catch(AssertionError e){
			errors.addError(error_name, getText(locator));
			errors.addExpected(error_name, expected);
		}catch(SeleniumException e){
			errors.addError(error_name, e);
		}catch(RuntimeException e) {
			throw new RuntimeException(e);
		}catch(Exception e){
			errors.addError(error_name, e);
		}finally{			
			if (text==""){
				text = "Failed";//TODO: question for DavidTanner: what if the text really is FAILED
			}
		}
		return text;
	}
	
	public String getText(String locator, String error_name){
		String text = "";
		try{
			text = getText(locator);
		}catch(SeleniumException e){
			errors.addError(error_name, e);
		}catch(RuntimeException e) {
			throw new RuntimeException(e);
		}catch(Exception e){
			errors.addError(error_name, e);
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
			errors.addError(error_name, getTable(locator));
		}catch(SeleniumException e){
			errors.addError(error_name, e);
		}catch(RuntimeException e) {
			throw new RuntimeException(e);
		}catch(Exception e){
			errors.addError(error_name, e);
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
			errors.addError(error_name, getTable(locator));
			errors.addExpected(error_name, expected);
		}catch(SeleniumException e){
			errors.addError(error_name, e);
		}catch(RuntimeException e) {
			throw new RuntimeException(e);
		}catch(Exception e){
			errors.addError(error_name, e);
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
			errors.addError(error_name, e);
		}catch(RuntimeException e) {
			throw new RuntimeException(e);
		}catch(Exception e){
			errors.addError(error_name, e);
		}
	}
	
	
	public void isTextPresent(String text, String error_name){
		try{
			assertTrue(isTextPresent(text));
		}catch(AssertionError e){
			errors.addError(error_name, e);
		}catch(SeleniumException e){
			errors.addError(error_name, e);
		}catch(RuntimeException e) {
			throw new RuntimeException(e);
		}catch(Exception e){
			errors.addError(error_name, e);
		}
	}
	
	
	public void isTextNotPresent(String text, String error_name){
		try{
			assertFalse(isTextPresent(text));
		}catch(AssertionError e){
			errors.addError(error_name, e);
		}catch(SeleniumException e){
			errors.addError(error_name, e);
		}catch(RuntimeException e) {
			throw new RuntimeException(e);
		}catch(Exception e){
			errors.addError(error_name, e);
		}
	}
	
	public void isVisible( String element, String error_name ){
		try{
			assertTrue( isVisible(element));
		}catch(AssertionError e){
			errors.addError(error_name, e);
		}catch(SeleniumException e){
			errors.addError(error_name, e);
		}catch(RuntimeException e) {
			throw new RuntimeException(e);
		}catch(Exception e){
			errors.addError(error_name, e);
		}
	}
	
	public void isNotVisible( String element, String error_name ){
		try{
			assertFalse( isVisible(element) );
		}catch(AssertionError e){
			errors.addError(error_name, e);
		}catch(SeleniumException e){
			errors.addError(error_name, e);
		}catch(RuntimeException e) {
			throw new RuntimeException(e);
		}catch(Exception e){
			errors.addError(error_name, e);
		}
	}
	
	public void waitForPageToLoad(String timeout, String error_name){
		try{
			waitForPageToLoad(timeout);
		}catch(SeleniumException e){
			errors.addError(error_name, e);
		}catch(RuntimeException e) {
			throw new RuntimeException(e);
		}catch(Exception e){
			errors.addError(error_name, e);
		}
	}
	
	public ErrorCatcher getErrors(){
		return errors;
	}

	
	public void AssertEquals( String actual, String expected, String error_name){
		try {
			assertTrue(actual.compareTo(expected)==0);
		}catch(AssertionError e){
			errors.addError(error_name, actual);
			errors.addExpected(error_name, expected);
		}catch(RuntimeException e) {
			throw new RuntimeException(e);
		}catch(Exception e){
			errors.addError(error_name, e);
		}
	}
	
	public void AssertEquals( Integer actual, Integer expected, String error_name){
		try {
			assertTrue(actual==expected);
		}catch(AssertionError e){
			errors.addError(error_name, actual.toString());
			errors.addExpected(error_name, expected.toString());
		}catch(RuntimeException e) {
			throw new RuntimeException(e);
		}catch(Exception e){
			errors.addError(error_name, e);
		}
	}
	
	public void AssertNotEqual( String actual, String not_expected, String error_name){
		try {
			assertFalse(actual.compareTo(not_expected)==0);
		}catch(AssertionError e){
			errors.addError(error_name, actual);
			errors.addExpected(error_name, "not"+not_expected);
		}catch(RuntimeException e) {
			throw new RuntimeException(e);
		}catch(Exception e){
			errors.addError(error_name, e);
		}
	}
	
	public void AssertNotEqual( Integer actual, Integer not_expected, String error_name){
		try {
			assertFalse(actual==not_expected);
		}catch(AssertionError e){
			errors.addError(error_name, "False");
		}catch(RuntimeException e) {
			throw new RuntimeException(e);
		}catch(Exception e){
			errors.addError(error_name, e);
		}
	}
	
	public void Pause( Integer timeout_in_secs ){
		try{
			Thread.currentThread();
			Thread.sleep((long)(timeout_in_secs * 1000));
		}catch(InterruptedException e){
			e.printStackTrace();
		}catch(RuntimeException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void select(String locator, String label, String error_name){
		try{
			select(locator, label);
			Pause(5);
		}catch(SeleniumException e){
			errors.addError(error_name, e);
		}catch(RuntimeException e) {
			throw new RuntimeException(e);
		}catch(Exception e){
			errors.addError(error_name, e);
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

	public void wait_for_element_present(String watch_for, String type) {
	    wait_for_element_present(watch_for, type, 180);
	}
	
    public void wait_for_element_present(String watch_for, String type, Integer secondsToWait) {
        Integer x = 0;
        boolean found = false;
        boolean doneWaiting = false;
        while (!found || !doneWaiting) {
            try {
                if (type.compareToIgnoreCase("link") == 0) {
                    found = isElementPresent("link=" + watch_for);
                } else if (type.compareToIgnoreCase("text") == 0) {
                    found = isTextPresent(watch_for);
                } else if (type.compareToIgnoreCase("element") == 0) {
                    found = isElementPresent(watch_for); 
                } else {
                    doneWaiting = true; //no need to wait if the type is not recognized
                }
            } catch (Exception e) {
                e.printStackTrace(); //TODO: jwimmer: logging for debugging/learning purposes... should remove before in use 
                continue;
            } finally {
                Pause(1); //second
                x++;
                doneWaiting = x > secondsToWait;
            }
        }

    }
	
	@Override
	public void start(){
		errors = new ErrorCatcher();
		super.start();
	}
	
    public Boolean inSession() {
        try {
            getAllWindowNames();
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

	public void click(SeleniumEnums checkIt) {
		click(getLocator(checkIt), "click: "+checkIt.toString());		
	}
	
	public void waitForPageToLoad(SeleniumEnums pageURL) {
	    waitForPageToLoad(pageURL, PAGE_TIMEOUT);
	}
	public void waitForPageToLoad(SeleniumEnums pageURL, Integer wait ) {
	    waitForPageToLoad(wait.toString(), "waitForPageToLoad: "+pageURL.toString());
	}
	
	public void isNotVisible(SeleniumEnums checkIt) {
		isNotVisible(getLocator(checkIt),"isNotVisible: "+checkIt.toString());		
	}


	public void isVisible(SeleniumEnums checkIt) {
		isVisible(getLocator(checkIt),"isVisible: "+checkIt.toString());
	}


	public String getText(SeleniumEnums checkIt, String expected) {
		return getText(checkIt.getText(), expected, "getText: "+checkIt.toString());
	}


	public void type(SeleniumEnums checkIt, String expected) {
		type(getLocator(checkIt), expected, "type: "+checkIt.toString());
	}


	public void isElementPresent(SeleniumEnums checkIt) {
		isElementPresent(getLocator(checkIt), "isElementPresent: "+checkIt.toString());		
	}
	
	public void isElementNotPresent(SeleniumEnums checkIt) {
		isElementNotPresent(getLocator(checkIt), "isElementNotPresent: "+checkIt.toString());		
	}


	public String getText(SeleniumEnums checkIt) {
		return getText(getLocator(checkIt), checkIt.getText(), "getText: "+checkIt.toString());
	}
}

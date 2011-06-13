package com.inthinc.pro.automation.utils;

import com.inthinc.pro.automation.enums.AutomationEnum;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;
import com.inthinc.pro.automation.selenium.CoreMethodLib;
import com.inthinc.pro.automation.selenium.ErrorCatcher;
import com.inthinc.pro.automation.selenium.GlobalSelenium;

public class MasterTest {
	
	public static enum ErrorLevel{
		FAIL,
		ERROR,
		WARN,
		COMPARE;
	}
	
	private String savedPage;

	private CoreMethodLib selenium;

	protected void addError(String errorName) {
		selenium.getErrors().addError(errorName,
				Thread.currentThread().getStackTrace());
	}
	
	protected void addError(String errorName, String error) {
		selenium.getErrors().addError(errorName, error);
	}

	protected void addErrorWithExpected(String errorName, String error,
			String expected) {
		selenium.getErrors().addError(errorName, error);
		selenium.getErrors().addExpected(errorName, expected);
	}


	protected void assertEquals(AutomationEnum anEnum) {
		assertEquals(selenium.getText(anEnum), anEnum.getText());
	}


	protected void assertEquals(Object expected, AutomationEnum actual) {
		assertEquals(expected, selenium.getText(actual));
	}



	protected void assertEquals(Object expected, Object actual) {
		String string;
		if (actual instanceof TextEnum) {
			string = ((TextEnum) actual).getText();
			if (!string.equals(expected)) {
				addError("'" + string + "'" + " != '" + expected + "'");
			}
		} else {
			if (!actual.equals(expected)) {
				addError("'" + actual + "'" + " != '" + expected + "'");
			}
		}
	}

	protected void assertEquals(Object expected, Object actual, AutomationEnum myEnum) {
		if (!actual.equals(expected)) {
			addError(myEnum.toString() + "\n" + myEnum.getLocatorsAsString(),
					"\t\tExpected = " + expected + "\n\t\tActual = " + actual);
		}
	}

	protected void assertStringContains(String partialString, String fullString) {
		if (!fullString.contains(partialString)) {
			addError(partialString + " not in " + fullString);
		}
	}

	protected Boolean assertTrue(Boolean test, String error) {
		if (!test) {
			addError(error);
			return false;
		}
		return true;
	}

	protected Boolean compare (TextEnum expected, String actual){
		return (actual == expected.getText());
	}

	protected ErrorCatcher get_errors() {
		return selenium.getErrors();
	}

	public String getCurrentLocation() {
        return selenium.getLocation();
    }

	protected CoreMethodLib getSelenium() {
		if (selenium == null){
			selenium = GlobalSelenium.getSelenium();
		}
		return selenium;
	}
	
    protected void open(SeleniumEnums pageToOpen){
    	selenium.open(AutomationEnum.CORE_ONLY.setEnum(pageToOpen));
    }
    
    protected void open(String url){
    	selenium.open(url);
    }
    
    protected void openSavedPage(){
    	open(savedPage);
    }
    
    protected void savePageLink(){
    	savedPage = getCurrentLocation();
    }
    
    protected void setSelenium() {
		this.selenium = GlobalSelenium.getYourOwn();
	}
}

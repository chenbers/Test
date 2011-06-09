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

	private CoreMethodLib selenium;

	protected void addError(String errorName) {
		selenium.getErrors().addError(errorName,
				Thread.currentThread().getStackTrace());
	}
	
	protected Boolean compare (TextEnum expected, String actual){
		return (actual == expected.getText());
	}

	protected void addError(String errorName, String error) {
		selenium.getErrors().addError(errorName, error);
	}


	protected void addErrorWithExpected(String errorName, String error,
			String expected) {
		selenium.getErrors().addError(errorName, error);
		selenium.getErrors().addExpected(errorName, expected);
	}


	protected void assertStringContains(String fullString, String partialString) {
		if (!fullString.contains(partialString)) {
			addError(partialString + " not in " + fullString);
		}
	}



	protected void assertEquals(AutomationEnum anEnum) {
		assertEquals(selenium.getText(anEnum), anEnum.getText());
	}

	protected void assertEquals(Object expected, AutomationEnum actual) {
		assertEquals(expected, selenium.getText(actual));
	}

	protected void assertEquals(Object actual, Object expected) {
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

	protected void assertEquals(Object actual, Object expected, AutomationEnum myEnum) {
		if (!actual.equals(expected)) {
			addError(myEnum.toString() + "\n" + myEnum.getLocatorsAsString(),
					"\t\tExpected = " + expected + "\n\t\tActual = " + actual);
		}
	}


	// TODO: jwimmer: dtanner: asserts should not be made available to the test
	// writers. test writers should compare/validate/? Elements, where the
	// assert methods do not necessarily operate on the element. note: pulling
	// certain methods up one layer to an object that parents AutomatedTest and
	// ElementBase would reveal them in both places... IF that is done make sure
	// we are only revealing methods to the test writers that we think they need
	// and/or will use
	protected void assertNotEquals(Object actual, Object expected) {
		if (actual.equals(expected)) {
			addError(actual + " == " + expected);
		}
	}

	protected void assertNotEquals(Object expected, SeleniumEnums actual) {
		assertNotEquals(expected, actual.getText());
	}

	protected Boolean assertTrue(Boolean test, String error) {
		if (!test) {
			addError(error);
			return false;
		}
		return true;
	}

	protected ErrorCatcher get_errors() {
		return selenium.getErrors();
	}

	protected CoreMethodLib getSelenium() {
		if (selenium == null){
			selenium = GlobalSelenium.getSelenium();
		}
		return selenium;
	}

	protected void setSelenium() {
		this.selenium = GlobalSelenium.getYourOwn();
	}
}

package com.inthinc.pro.automation.utils;

import com.inthinc.pro.automation.enums.AutomationEnum;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;
import com.inthinc.pro.automation.selenium.CoreMethodLib;
import com.inthinc.pro.automation.selenium.ErrorCatcher;
import com.inthinc.pro.automation.selenium.GlobalSelenium;

public class MasterTest {

	public static enum ErrorLevel {
		FAIL, 
		ERROR, 
		WARN, 
		COMPARE;
	}

	private String savedPage;

	private CoreMethodLib selenium;

	protected void addError(String errorName, ErrorLevel level) {
		getErrors().addError(errorName, Thread.currentThread().getStackTrace(),
				level);
	}

	protected void addError(String errorName, String error, ErrorLevel level) {
		getErrors().addError(errorName, error, level);
	}

	protected void addError(String errorName, Throwable stackTrace,
			ErrorLevel level) {
		getErrors().addError(errorName, stackTrace, level);
	}

	protected void addErrorWithExpected(String errorName, String error,
			String expected, ErrorLevel level) {
		getErrors().addExpected(errorName, expected);
		getErrors().addError(errorName, error, level);
	}

	protected Boolean assertEquals(AutomationEnum anEnum) {
		return assertEquals(anEnum.getText(), selenium.getText(anEnum), anEnum);
	}

	protected Boolean assertEquals(Object expected, AutomationEnum actual) {
		return assertEquals(expected, selenium.getText(actual));
	}

	protected Boolean assertEquals(Object expected, Object actual) {
		return assertEquals(expected, actual, true);
	}

	protected Boolean assertEquals(Object expected, Object actual,
			AutomationEnum myEnum) {
		if (!compare(expected, actual)) {
			addError(myEnum.toString() + "\n" + myEnum.getLocatorsAsString(),
					"\t\tExpected = " + expected + "\n\t\tActual = " + actual,
					ErrorLevel.FAIL);
			return false;
		}
		return true;
	}

	private Boolean assertEquals(Object expected, Object actual,
			Boolean areObjectsEqual) {
		if (!compare(expected, actual)) {
			addError("your expected: '" + actual + "'" + " does not equal: '"
					+ expected + "'", ErrorLevel.FAIL);
			return false;
		}
		return true;
	}

	protected Boolean assertFalse(Boolean test, String error) {
		if (test) {
			addError(error, ErrorLevel.FAIL);
			return false;
		}
		return true;
	}

	protected Boolean assertNotEquals(Object expected, Object actual) {
		return assertEquals(expected, actual, false);
	}

	protected Boolean assertStringContains(String partialString,
			String fullString) {
		if (!fullString.contains(partialString)) {
			addError(partialString + " not in " + fullString, ErrorLevel.FAIL);
			return false;
		}
		return true;
	}

	protected Boolean assertTrue(Boolean test, String error) {
		if (!test) {
			addError(error, ErrorLevel.FAIL);
			return false;
		}
		return true;
	}

	protected Boolean compare(Object expected, Object actual) {
		Boolean results = false;
		if (actual instanceof TextEnum) {
			results = compare(expected, ((TextEnum) actual).getText());
		} else if (expected instanceof TextEnum) {
			results = compare(((TextEnum) expected).getText(), actual);
		} else {
			results = actual.equals(expected);
		}
		return results;
	}

	public String getCurrentLocation() {
		return selenium.getLocation();
	}

	protected ErrorCatcher getErrors() {
		return selenium.getErrors();
	}

	protected CoreMethodLib getSelenium() {
		if (selenium == null) {
			selenium = GlobalSelenium.getSelenium();
		}
		return selenium;
	}

	protected void open(SeleniumEnums pageToOpen) {
		selenium.open(AutomationEnum.CORE_ONLY.setEnum(pageToOpen));
	}

	protected void open(String url) {
		selenium.open(url);
	}

	protected void openSavedPage() {
		open(savedPage);
	}

	protected void savePageLink() {
		savedPage = getCurrentLocation();
	}

	protected void setSelenium() {
		this.selenium = GlobalSelenium.getYourOwn();
	}

	protected Boolean validateEquals(Object expected, Object actual) {
		return validateEquals(expected, actual, true);
	}

	private Boolean validateEquals(Object expected, Object actual,
			Boolean areObjectsEqual) {
		Boolean result = compare(expected, actual);
		if (areObjectsEqual != result) {
			String match = "shouldn't match";
			if (areObjectsEqual) {
				match = "should match";
			}
			addError("your expected: '" + actual + "'" + " and actual is: '"
					+ expected + "' they " + match, ErrorLevel.ERROR);
		}
		return result;
	}

	protected Boolean validateNotEquals(Object expected, Object actual) {
		return validateEquals(expected, actual, false);
	}
}

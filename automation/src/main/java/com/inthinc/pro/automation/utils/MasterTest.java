package com.inthinc.pro.automation.utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringEscapeUtils;
import org.openqa.selenium.WebDriver;

import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;
import com.inthinc.pro.automation.selenium.CoreMethodInterface;
import com.inthinc.pro.automation.selenium.ErrorCatcher;
import com.inthinc.pro.automation.selenium.GlobalSelenium;

public class MasterTest {

    public static enum ErrorLevel {
	FAIL,
	ERROR,
	WARN,
	COMPARE;
    }

    protected static void enterKey() {
	try {
	    Robot r = new Robot();
	    r.keyPress(KeyEvent.VK_ENTER);
	    r.keyRelease(KeyEvent.VK_ENTER);
	} catch (AWTException e) {
	    e.printStackTrace();
	}
    }

    protected static String escapeHtml(String original) {
	return StringEscapeUtils.escapeHtml(original);
    }

    public static void print(Object printToScreen) {
	StackTraceElement element = Thread.currentThread().getStackTrace()[2];
	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	String time = sdf.format(GregorianCalendar.getInstance().getTime());
	String className = element.getFileName().replace(".java", "");
	System.out.printf("%s, %s.%s:%3d - %s\n", time, className,
		element.getMethodName(), element.getLineNumber(),
		printToScreen.toString());
    }

    protected static void tabKey() {
	try {
	    Robot r = new Robot();
	    r.keyPress(KeyEvent.VK_TAB);
	    r.keyRelease(KeyEvent.VK_TAB);
	} catch (AWTException e) {
	    e.printStackTrace();
	}
    }

    protected static String unescapeHtml(String original) {
	return StringEscapeUtils.unescapeHtml(original);
    }

    private String savedPage;

    private CoreMethodInterface selenium;

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

    protected Boolean assertEquals(Object expected, Object actual) {
	return assertEquals(expected, actual, true);
    }

    private Boolean assertEquals(Object expected, Object actual,
	    Boolean areObjectsEqual) {
	if (compare(expected, actual) != areObjectsEqual) {
	    addError("your expected: '" + expected + "'" + " does not equal: '"
		    + actual + "'", ErrorLevel.FAIL);
	    return false;
	}
	return true;
    }

    protected Boolean assertEquals(Object expected, Object actual,
	    SeleniumEnumWrapper myEnum) {
	if (!compare(expected, actual)) {
	    addError(myEnum.toString() + "\n" + myEnum.getLocatorsAsString(),
		    "\t\tExpected = " + expected + "\n\t\tActual = " + actual,
		    ErrorLevel.FAIL);
	    return false;
	}
	return true;
    }

    protected Boolean assertEquals(Object expected, SeleniumEnumWrapper actual) {
	return assertEquals(expected, selenium.getText(actual));
    }

    protected Boolean assertEquals(SeleniumEnumWrapper anEnum) {
	return assertEquals(anEnum.getText(), selenium.getText(anEnum), anEnum);
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

    protected Boolean assertNotEquals(Object expected, Object actual,
	    SeleniumEnumWrapper myEnum) {
	if (compare(expected, actual)) {
	    addError(myEnum.toString() + "\n" + myEnum.getLocatorsAsString(),
		    "\t\tExpected = " + expected + "\n\t\tActual = " + actual,
		    ErrorLevel.FAIL);
	    return false;
	}
	return true;
    }

    protected Boolean assertNotEquals(Object expected,
	    SeleniumEnumWrapper anEnum) {
	return assertNotEquals(anEnum.getText(), selenium.getText(anEnum),
		anEnum);
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
	if (actual instanceof SeleniumEnumWrapper) {
	    results = compare(expected,
		    selenium.getText((SeleniumEnumWrapper) actual));
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

    protected CoreMethodInterface getSelenium() {
	if (selenium == null) {
	    selenium = GlobalSelenium.getSelenium();
	}
	return selenium;
    }

    protected String getTextFromElementWithFocus() {
	WebDriver web = selenium.getWrappedDriver();
	return web.switchTo().activeElement().getText();
    }

    protected void open(SeleniumEnums pageToOpen) {
	selenium.open(new SeleniumEnumWrapper(pageToOpen));
    }

    protected void open(SeleniumEnums pageToOpen, Integer replaceNumber) {
	SeleniumEnumWrapper urlWithNumber = new SeleniumEnumWrapper(pageToOpen);
	urlWithNumber.updateURL(replaceNumber);
	selenium.open(urlWithNumber);
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

    protected void typeToElementWithFocus(String type) {
	WebDriver web = selenium.getWrappedDriver();
	web.switchTo().activeElement().sendKeys(type);
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

    protected Boolean validateStringContains(String partialString,
	    String fullString) {
	if (!fullString.contains(partialString)) {
	    addError(partialString + " not in " + fullString, ErrorLevel.ERROR);
	    return false;
	}
	return true;
    }
}

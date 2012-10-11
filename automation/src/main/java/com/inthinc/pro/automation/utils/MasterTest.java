package com.inthinc.pro.automation.utils;

import com.inthinc.pro.automation.enums.ErrorLevel;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.interfaces.TextEnum;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.selenium.CoreMethodInterface;
import com.inthinc.pro.automation.selenium.CoreMethodLib;
import com.inthinc.pro.automation.selenium.ErrorCatcher;
import com.inthinc.pro.automation.selenium.Page;

public abstract class MasterTest {
    

    /**
     * Adds an error for this test, WARNING: default ErrorLevel is set to FAIL.
     * @param errorName
     */
    public void addError(String errorName){
        addError(errorName, ErrorLevel.FAIL);
    }

    public void addError(String errorName, ErrorLevel level) {
        getSelenium().getErrorCatcher().addError(errorName, Thread.currentThread().getStackTrace(), level);
    }
    
    public void addError(String errorName, String error, ErrorLevel level) {
        getSelenium().getErrorCatcher().addError(errorName, error, level);
    }
    
    public void addError(String errorName, Throwable stackTrace, ErrorLevel level) {
        getSelenium().getErrorCatcher().addError(errorName, stackTrace, level);
    }

    public Boolean assertEquals(Object expected, Object actual) {
        return assertEquals(expected, actual, true);
    }

    private Boolean assertEquals(Object expected, Object actual, Boolean areObjectsEqual) {
        Boolean result = compare(expected, actual);
        if (areObjectsEqual != result) {
            String match = "shouldn't match";
            if (areObjectsEqual) {
                match = "should match";
            }
            Log.info("your expected: '" + expected + "'" + " and actual is: '" + actual + "' they " + match);
            addError("your expected: '" + expected + "'" + " and actual is: '" + actual + "' they " + match, ErrorLevel.FATAL);
        }
        return result == areObjectsEqual;
    }

    public Boolean assertEquals(Object expected, Object actual, SeleniumEnumWrapper myEnum) {
        if (!compare(expected, actual)) {
            addError(myEnum.toString() + "\n" + myEnum.getLocatorsAsString(), "\t\tExpected = " + expected + "\n\t\tActual = " + actual, ErrorLevel.FATAL);
            return false;
        }
        return true;
    }
    public Boolean assertEquals(Object expected, SeleniumEnumWrapper actual) {
        return assertEquals(expected, getSelenium().getText(actual));
    }

    public Boolean assertEquals(SeleniumEnumWrapper anEnum) {
        return assertEquals(anEnum.getText(), getSelenium().getText(anEnum), anEnum);
    }

    public Boolean assertFalse(Boolean test, String error) {
        if (test) {
            addError(error, ErrorLevel.FATAL);
            return false;
        }
        return true;
    }


    public Boolean assertNotEquals(Object expected, Object actual) {
        return assertEquals(expected, actual, false);
    }

    public Boolean assertNotEquals(Object expected, Object actual, SeleniumEnumWrapper myEnum) {
        if (compare(expected, actual)) {
            addError(myEnum.toString() + "\n" + myEnum.getLocatorsAsString(), "\t\tExpected = " + expected + "\n\t\tActual = " + actual, ErrorLevel.FATAL);
            return false;
        }
        return true;
    }

    public Boolean assertNotEquals(Object expected, SeleniumEnumWrapper anEnum) {
        return assertNotEquals(anEnum.getText(), getSelenium().getText(anEnum), anEnum);
    }

    public Boolean assertStringContains(String partialString, String fullString) {
        if (!fullString.contains(partialString)) {
            addError(partialString + " not in " + fullString, ErrorLevel.FATAL);
            return false;
        }
        return true;
    }

    public Boolean assertTrue(Boolean test, String error) {
        return assertTrue(test, error, ErrorLevel.FATAL);
    }

    public Boolean assertTrue(Boolean test, String error, ErrorLevel level) {
        if (!test) {
            addError(error, level);
            return false;
        }
        return true;   
    }

    public Boolean compare(Object expected, Object actual) {
        Boolean results = false;
        if (actual instanceof SeleniumEnumWrapper) {
            results = compare(expected, getSelenium().getText((SeleniumEnumWrapper) actual));
        } else if (expected instanceof TextEnum) {
            results = compare(((TextEnum) expected).getText(), actual);
        } else {
            results = actual.equals(expected);
        }
        Log.info("Expected: " + expected + " == Actual: " + actual + " is " + results);
        return results;
    }

    public String getCurrentLocation() {
        return getSelenium().getLocation();
    }

    public ErrorCatcher getErrors() {
        return getSelenium().getErrorCatcher();
    }


    public CoreMethodInterface getSelenium() {
        return CoreMethodLib.getSeleniumThread();
    }

    public String getTextFromElementWithFocus() {// TODO: jwimmer please check this again against new code.
        return getSelenium().getTextFromElementWithFocus();
    }
    
    public AutoServers getServer(){
        return new AutoServers();
    }

    public void killSelenium() {
    	CoreMethodLib.closeSeleniumThread();
    }

    public void open(Page page){
        getSelenium().open(page.getExpectedPath());
    }
    
    public void open(SeleniumEnums pageToOpen) {
        getSelenium().open(new SeleniumEnumWrapper(pageToOpen));
    }

    public void open(SeleniumEnums pageToOpen, Integer replaceNumber) {
        SeleniumEnumWrapper urlWithNumber = new SeleniumEnumWrapper(pageToOpen);
        urlWithNumber.updateURL(replaceNumber);
        getSelenium().open(urlWithNumber);
    }

    public void open(String url) {
        getSelenium().open(url);
    }
    
    public Boolean validateEquals(Object expected, Object actual) {
        return validateEquals(expected, actual, true);
    }
    
    private Boolean validateEquals(Object expected, Object actual, Boolean areObjectsEqual) {
        Boolean result = compare(expected, actual);
        if (areObjectsEqual != result) {
            String match = "shouldn't match";
            if (areObjectsEqual) {
                match = "should match";
            }
            Log.info("your expected: '" + expected + "'" + " and actual is: '" + actual + "' they " + match);
            addError("your expected: '" + expected + "'" + " and actual is: '" + actual + "' they " + match, ErrorLevel.FAIL);
        }
        return result == areObjectsEqual;
    }
    
    public Boolean validateFalse(Boolean test, String error) {
        if (test) {
            addError(error, ErrorLevel.FAIL);
            return false;
        }
        return true;
    }
    
    public Boolean validateNotEquals(Object expected, Object actual) {
        return validateEquals(expected, actual, false);
    }
    
    public Boolean validateStringContains(String partialString, String fullString) {
        if (!fullString.contains(partialString)) {
            addError(partialString + " not in " + fullString, ErrorLevel.FAIL);
            return false;
        }
        return true;
    }
    
    public Boolean validateStringDoesNotContain(String partialString, String fullString){
        if (fullString.contains(partialString)) {
            addError(partialString + " is, but should not be, in " + fullString, ErrorLevel.FAIL);
            return false;
        }
        return true;
    }
    
    public Boolean validateTrue(Boolean test, String error) {
        if (!test) {
            addError(error, ErrorLevel.FAIL);
            return false;
        }
        return true;
    }
}

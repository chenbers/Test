package com.inthinc.pro.automation.selenium;

import java.util.HashMap;

import com.inthinc.pro.automation.utils.StackToString;
import com.thoughtworks.selenium.SeleniumException;

/****************************************************************************************
 * Purpose: To catch the errors raised by Selenium, and format them into a nice HashMap<br />
 * <p>
 * The idea is to take a stack trace, or string, and tie it to an error name.<br />
 * Then associate that error with a name for easy reading to see what broke.<br />
 * 
 * @author dtanner
 * @see HashMap
 */
public class ErrorCatcher {

    private static HashMap<String, HashMap<String, String>> errors = new HashMap<String, HashMap<String, String>>();
    private static HashMap<String, String> errorList;

    /**
     * Helper method for other error(name, error) methods. <br />
     * 
     * @param name
     * @param type of error
     * @param error text
     */
    private void addError(String name, String type, String error) {
        if (!errors.containsKey(name)) {
            add_error(name);
        }
        errors.get(name).put(type, error);
        throw new SeleniumException(name + "  " + error);
    }

    /**
     * Adds the error to the error list<br />
     * 
     * @param name
     * @param error
     * @throws Throwable 
     */
    public void addError(String name, Object error) {
        String errorStr = null;
        String type = error.getClass().getSimpleName();
        if (error instanceof String) {
            errorStr = (String) error;
            type = "Actual text or Message";
        } else if (error instanceof Throwable)
            errorStr = StackToString.toString((Throwable) error);
        addError(name, type, errorStr);
    }

    /**
     * Take the expected string for comparison against the actual<br />
     * 
     * @param errorName
     * @param expected
     */
    public void addExpected(String name, String expected) {

        if (!errors.containsKey(name)) {
            add_error(name);
        }

        errors.get(name).put("Expected", expected);
    }

    /**
     * Add an error by Name<br />
     * 
     * @param errorName
     */
    private void add_error(String name) {
        errorList = new HashMap<String, String>();
        errors.put(name, errorList);
    }

    /**
     * @return the errors we have stored in a double HashMap
     * @see HashMap
     */
    public HashMap<String, HashMap<String, String>> get_errors() {
        return errors;
    }
}

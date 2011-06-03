package com.inthinc.pro.automation.selenium;

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.openqa.selenium.WebDriver;

import com.inthinc.pro.automation.enums.AutomationEnum;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;

public abstract class AbstractPage implements Page {
    protected CoreMethodLib selenium;
    protected WebDriver webDriver;
    protected String currentPage;
    public static ArrayList<Class<? extends AbstractPage>> instantiatedPages = new ArrayList<Class<? extends AbstractPage>>(); //TODO: jwimmer: should not stay public

    public AbstractPage() {
        selenium = GlobalSelenium.getSelenium(); System.out.println("selenium: "+selenium);
        webDriver = selenium.getWrappedDriver();
        
        Class<? extends AbstractPage> derivedClass = this.getClass();
        if(!instantiatedPages.contains(derivedClass)) {
            instantiatedPages.add(derivedClass);
        }
    }

    public void addError(String errorName) {
        selenium.getErrors().addError(errorName, Thread.currentThread().getStackTrace());
    }

    public void addError(String errorName, String error) {
        selenium.getErrors().addError(errorName, error);
    }

    public void addErrorWithExpected(String errorName, String error, String expected) {
        selenium.getErrors().addError(errorName, error);
        selenium.getErrors().addExpected(errorName, expected);
    }

    public void assertEquals(Object actual, Object expected) {
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

    public void assertEquals(Object expected, SeleniumEnums actual) {
        assertEquals(expected, actual.getText());
    }

    public void assertEquals(AutomationEnum anEnum) {
        assertEquals(selenium.getText(anEnum), anEnum.getText());
    }

    public void assertNotEquals(Object actual, Object expected) {
        if (actual.equals(expected)) {
            addError(actual + " == " + expected);
        }
    }

    public void assertNotEquals(Object expected, SeleniumEnums actual) {
        assertNotEquals(expected, actual.getText());
    }

    public void assertContains(String fullString, String partialString) {
        if (!fullString.contains(partialString)) {
            addError(partialString + " not in " + fullString);
        }
    }
    
    public ErrorCatcher get_errors() {
        return selenium.getErrors();
    }

    @Override
    public String getCurrentLocation() {
        return selenium.getLocation();
    }

    @Override
    public String getExpectedPath() {
        // TODO Auto-generated method stub
        return null;
    }

    public CoreMethodLib getSelenium() {
        return selenium;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.web.selenium.Page#validateURL()
     */
    public AbstractPage validateURL() {
        boolean results = getCurrentLocation().contains(getExpectedPath());
        if (!results)
            addError("validateURL", getCurrentLocation() + " does not contain " + getExpectedPath() + " ?"); 
        return this;
    }

    @Override
    public Page load() {
        selenium.open(this.getExpectedPath());
        return this;// TODO: jwimmer: remove load()... jwimmer is the only one using it.
    }

    public AbstractPage page_bareMinimum_validate() {
        return validate();
    }

    @Override
    public AbstractPage validate() {
        addError("no validate method", "automation cannot validate AbstractPage OR there is no validate() method for the concrete page being tested.");
        // TODO Auto-generated method stub
        return this;
    }
}

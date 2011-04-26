package com.inthinc.pro.automation.elements;

import org.openqa.selenium.WebDriver;

import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.selenium.CoreMethodLib;
import com.inthinc.pro.automation.selenium.GlobalSelenium;

public class ElementBase implements ElementInterface {
    protected CoreMethodLib selenium;
    protected WebDriver webDriver;

    protected String element;

    protected SeleniumEnums myEnum;
    protected String text;

    public ElementBase(SeleniumEnums anEnum) {
        this(anEnum, null, null);
    }

    public ElementBase(SeleniumEnums anEnum, String replaceWord) {
        this(anEnum, replaceWord, null);
    }
    
    public ElementBase(SeleniumEnums anEnum, Integer replaceNumber) {
        this(anEnum, null, replaceNumber);
    }

    public ElementBase(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        selenium = GlobalSelenium.getSelenium();
        webDriver = selenium.getWrappedDriver();
        element = getLocator(replaceWord, replaceNumber);
        this.text = anEnum.getText();
        this.myEnum = anEnum;
    }


    @Override
    public boolean isVisible() {
        return selenium.isVisible(myEnum);
    }

    @Override
    public ElementInterface validate() {
        assertEquals(myEnum);
        return this;
    }

    public String getLocator() {
        return getLocator(null, null);
    }
    
    public String getLocator(String replaceWord) {
        return getLocator(replaceWord, null);
    }
    
    public String getLocator(Integer replaceNumber) {
        return getLocator(null, replaceNumber);
    }

    public String getLocator(String replaceName, Integer replaceNumber) {
        String id = null;
        boolean ID=true,xpath=true,xpathAlt=true;
        while (id == null) {
            if (myEnum.getID() != null && ID){
                id = myEnum.getID().replace("***", replaceName).replace("###", replaceNumber.toString());
                if (!selenium.isElementPresent(id)) {
                    id = null;
                    ID=false;
                }
            } else if (myEnum.getXpath() != null && xpath) {
                id = myEnum.getXpath().replace("***", replaceName).replace("###", replaceNumber.toString());
                if (!selenium.isElementPresent(id)) {
                    id = null;
                    xpath=false;
                }
            } else if (myEnum.getXpath_alt() != null && xpathAlt) {
                id = myEnum.getXpath_alt().replace("***", replaceName).replace("###", replaceNumber.toString());
                if (!selenium.isElementPresent(id)) {
                    id = null;
                    xpathAlt=false;
                }
            }
            if (!ID && !xpath && !xpathAlt){
                break;
            }
        }
        return id;
    }

    @Override
    public ElementInterface focus() {
        // selenium.focus(locator);
        String error_name = "focus: " + element;

        try {
            selenium.focus(element);
            error_name = "focus: " + element;
        }catch (Exception e) {
            if (e instanceof RuntimeException)
                throw new RuntimeException(e);
            else
                selenium.getErrors().addError(error_name, e);
        }
        return this;
    }

    public ElementBase addError(String errorName) {
        selenium.getErrors().addError(errorName, Thread.currentThread().getStackTrace());
        return this;
    }

    public ElementBase addError(String errorName, String error) {
        selenium.getErrors().addError(errorName, error);
        return this;
    }

    public void addErrorWithExpected(String errorName, String error, String expected) {
        selenium.getErrors().addError(errorName, error);
        selenium.getErrors().addExpected(errorName, expected);
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

    public void assertEquals(Object actual, Object expected) {
        if (!actual.equals(expected)) {
            addError(actual + " != " + expected);
        }
    }

    public void assertEquals(Object expected, SeleniumEnums actual) {
        assertEquals(expected, actual.getText());
    }

    public void assertEquals(SeleniumEnums anEnum) {
        assertEquals(selenium.getText(anEnum), anEnum.getText());
    }

    public SeleniumEnums getMyEnum() {
        return myEnum;
    }

    public void setMyEnum(SeleniumEnums myEnum) {
        this.myEnum = myEnum;
    }

    public CoreMethodLib getselenium() {
        return selenium;
    }
}

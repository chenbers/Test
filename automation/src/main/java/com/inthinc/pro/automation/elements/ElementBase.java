package com.inthinc.pro.automation.elements;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.selenium.CoreMethodLib;
import com.inthinc.pro.automation.selenium.GlobalSelenium;

public class ElementBase implements ElementInterface {
    private final static Logger logger = Logger.getLogger(ElementBase.class);
    protected CoreMethodLib selenium;
    protected WebDriver webDriver;

    protected SeleniumEnums myEnum;
    protected String text;
    protected String replaceWord;
    protected Integer replaceNumber;

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
        this.text = anEnum.getText();
        this.myEnum = anEnum;
        this.replaceWord = replaceWord;
        this.replaceNumber = replaceNumber;
        selenium = GlobalSelenium.getSelenium();
        webDriver = selenium.getWrappedDriver();
    }

    @Override
    public boolean isVisible() {
        return selenium.isVisible(myEnum, replaceWord, replaceNumber);
    }

    @Override
    public ElementInterface validate() {
        assertEquals(myEnum);
        return this;
    }

    @Override
    public ElementInterface focus() {
        selenium.focus(myEnum, replaceWord, replaceNumber);
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
    
    protected void setCurrentLocation(){
        String[] address = getCurrentLocation().split("/"); //TODO: jwimmer: doesn't seem very robust on pages where MORE than the last portion of the URL is significant?
        String[] longUrl = {"protocol", "", "urlandport", "appPath", "label", "section", "page"};
        String[] shortUrl = {"protocol", "", "urlandport", "appPath", "page"};
        HashMap<String, String> current = new HashMap<String, String>();
        current.put("protocol", address[0]);
        String[] url = address[2].split(":");
        current.put("url", url[0]);
        current.put("port", url[1]);
        current.put("appPath", address[3]);
        
        if (address.length == longUrl.length){
            current.put("label", address[4]);
            current.put("section", address[5]);
            current.put("page", address[6]);    
        }else if (address.length == shortUrl.length){
            current.put("page", address[4]);
        }
    }
    
    public String getCurrentLocation() {
        return selenium.getLocation();
    }
}

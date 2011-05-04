package com.inthinc.pro.automation.elements;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.inthinc.pro.automation.enums.AutomationEnum;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.selenium.CoreMethodLib;
import com.inthinc.pro.automation.selenium.GlobalSelenium;

public class ElementBase implements ElementInterface {
    private final static Logger logger = Logger.getLogger(ElementBase.class);
    protected CoreMethodLib selenium;
    protected WebDriver webDriver;

    protected AutomationEnum myEnum;
    protected String text;
    
    protected HashMap<String, String> current;
    
    public ElementBase(){
        this(AutomationEnum.PLACE_HOLDER, null, null);
    }

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
        setEnum(anEnum);
        if (replaceWord!=null){
            myEnum.replaceNumber(replaceNumber.toString());
        }
        if (replaceWord!=null){
            myEnum.replaceWord(replaceWord);
        }
        selenium = GlobalSelenium.getSelenium();
        webDriver = selenium.getWrappedDriver();
    }
    
    private void setEnum(SeleniumEnums anEnum){
        this.myEnum = AutomationEnum.PLACE_HOLDER.setEnum(anEnum);
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
    
    protected Boolean isElementPresent(){
        return selenium.isElementPresent(myEnum);
    }

    @Override
    public ElementInterface focus() {
        selenium.focus(myEnum);
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
    
    public void assertTrue(Boolean test) {
        if (!test) {
            addError(myEnum.toString());
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
        assertEquals(expected, selenium.getText(AutomationEnum.PLACE_HOLDER.setEnum(actual)));
    }

    public void assertEquals(SeleniumEnums anEnum) {
        assertEquals(selenium.getText(AutomationEnum.PLACE_HOLDER.setEnum(anEnum)), anEnum.getText());
    }
    
    public void assertEquals(){
        assertEquals(myEnum);
    }

    public SeleniumEnums getMyEnum() {
        return myEnum;
    }

    public void setMyEnum(SeleniumEnums anEnum) {
        this.myEnum = AutomationEnum.PLACE_HOLDER.setEnum(anEnum);
    }

    public CoreMethodLib getselenium() {
        return selenium;
    }
    
    protected void setCurrentLocation(){
        String uri = getCurrentLocation();
        logger.info(uri);
        String[] address = uri.split("/"); //TODO: jwimmer: doesn't seem very robust on pages where MORE than the last portion of the URL is significant?
        current = new HashMap<String, String>();
        current.put("protocol", address[0]);
        String[] url = address[2].split(":");
        current.put("url", url[0]);
        current.put("port", url[1]);
        current.put("appPath", address[3]);
        current.put("page", address[address.length-1]);
        
        if (address.length == 7){
            current.put("label", address[4]);
            current.put("section", address[5]);
        }
    }
    
    public String getCurrentLocation() {
        return selenium.getLocation();
    }
    
    public void validateElementsPresent(SeleniumEnums ...enums){
        for (SeleniumEnums enumerated: enums){
            setEnum(enumerated);
            assertTrue(isElementPresent());
        }
    }
    
    public void validateTextMatches(SeleniumEnums ...enums){
        for (SeleniumEnums enumerated: enums){
            assertEquals(enumerated);
        }
    }
    
}

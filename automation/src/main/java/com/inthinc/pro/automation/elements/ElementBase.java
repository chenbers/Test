package com.inthinc.pro.automation.elements;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;
import com.inthinc.pro.automation.selenium.CoreMethodInterface;
import com.inthinc.pro.automation.utils.MasterTest;

public class ElementBase extends MasterTest implements ElementInterface {

    protected final static String parentXpath = "/..";

    protected final static Logger logger = Logger.getLogger(ElementBase.class);
    protected CoreMethodInterface selenium;
    protected WebDriver webDriver;

    protected SeleniumEnumWrapper myEnum;

    protected HashMap<String, String> current;

    public ElementBase() {
        selenium = super.getSelenium();
    }

    public ElementBase(SeleniumEnums anEnum) {
        this(anEnum, null, null);
    }

    public ElementBase(SeleniumEnums anEnum, String replaceWord) {
        this(anEnum, replaceWord, null);
    }

    public ElementBase(SeleniumEnums anEnum, TextEnum replaceWord) {
        this(anEnum, replaceWord.getText(), null);
    }

    public ElementBase(SeleniumEnums anEnum, Integer replaceNumber) {
        this(anEnum, null, replaceNumber);
    }

    public ElementBase(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        setMyEnum(anEnum);
        if (replaceNumber != null) {
            myEnum.replaceNumber(replaceNumber);
        }
        if (replaceWord != null) {
            myEnum.replaceWord(replaceWord);
        }
        selenium = super.getSelenium();
        webDriver = selenium.getWrappedDriver();
    }

    @Override
    public Boolean isPresent() {
        return selenium.isElementPresent(myEnum);
    }

    @Override
    public Boolean isVisible() {
        return selenium.isVisible(myEnum);
    }

    @Override
    public Boolean assertVisibility(Boolean visible) {
        return assertTrue(visible == selenium.isVisible(myEnum), myEnum.toString());
    }

    @Override
    public ElementInterface focus() {
        selenium.focus(myEnum);
        return this;
    }

    @Override
    public Boolean hasFocus() {
        return selenium.hasFocus(myEnum);
    }

    @Override
    public SeleniumEnums getMyEnum() {
        return myEnum;
    }

    @Override
    public void setMyEnum(SeleniumEnums anEnum) {
        this.myEnum = new SeleniumEnumWrapper(anEnum);
    }

    protected void setCurrentLocation() {
        String uri = getCurrentLocation();
        logger.debug(uri);
        String[] address = uri.split("/");
        current = new HashMap<String, String>();
        current.put("protocol", address[0]);
        String[] url = address[2].split(":");
        current.put("url", url[0]);
        current.put("port", url[1]);
        current.put("appPath", address[3]);
        current.put("page", address[address.length - 1]);

        if (address.length == 7) {
            current.put("label", address[4]);
            current.put("section", address[5]);
        }
    }

    @Override
    public String getCurrentLocation() {
        return selenium.getLocation();
    }

    @Override
    public Boolean validateElementsPresent(Object... enums) {
        SeleniumEnumWrapper temp = myEnum;
        for (Object enumerated : enums) {
            setMyEnum((SeleniumEnums) enumerated);
            assertTrue(isPresent(), myEnum.toString());
        }
        myEnum = temp;
        return true;
    }

    @Override
    public Boolean validateElementsPresent(ArrayList<SeleniumEnums> enums) {
        return validateElementsPresent(enums.toArray());
    }

    protected ElementBase replaceNumber(Integer number) {
        myEnum.replaceNumber(number);
        return this;
    }

    protected ElementBase replaceWord(String word) {
        myEnum.replaceWord(word);
        return this;
    }

    /**
     * This method should only be used by the page objects to make a replacement.<br />
     * If you have to use it, we accept no responsibility for the outcome.<br />
     * You have been warned.<br />
     * 
     * @param replaceOld
     * @param withNew
     * @return
     */
    public ElementBase replaceSubStringInMyEnum(String replaceOld, String withNew) {
        myEnum.replaceOldWithNew(replaceOld, withNew);
        return this;
    }

    @Override
    public Boolean validateVisibility(Boolean visible) {
        return validateEquals(visible, isVisible());
    }
    
    @Override
    public void waitForElement(){
        waitForElement(60);
    }
    
    @Override
    public void waitForElement(int i){
        selenium.waitForElementPresent(myEnum, i);
    }

    @Override
    public Boolean validatePresence(Boolean present) {
        return validateEquals(present, isPresent());
    }

    @Override
    public Boolean assertPresence(Boolean present) {
        return assertEquals(present, isPresent());
    }
}

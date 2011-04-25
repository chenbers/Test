package com.inthinc.pro.automation.elements;

import org.openqa.selenium.NoSuchElementException;

import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.selenium.AbstractPage;
import com.inthinc.pro.automation.selenium.CoreMethodLib;
import com.thoughtworks.selenium.SeleniumException;
import com.thoughtworks.selenium.DefaultSelenium;


public class ElementBase implements ElementInterface {
    
    protected String text;
    protected String ID;
    protected String URL;
    protected String xpath;
    protected String xpath_alt;
    protected SeleniumEnums myEnum;
    protected CoreMethodLib mySelenium;
    
    public ElementBase(CoreMethodLib pageSelenium, SeleniumEnums anEnum) {
        this.text = anEnum.getText();
        this.ID = anEnum.getID();
        this.URL = anEnum.getURL();
        this.xpath = anEnum.getXpath();
        this.xpath_alt = anEnum.getXpath_alt();
        mySelenium = pageSelenium;
    }
    
    @Override
    public String getXPath() {
        System.out.println("abstractElement.getXPath() returns "+myEnum.getXpath());//TODO: jwimmer: remove before checkin
        return myEnum.getXpath();
    }

    @Override
    public boolean isVisible() {
        System.out.println("abstractElement.isVisible()");//TODO: jwimmer: remove before checkin
        return mySelenium.isVisible(myEnum);
    }

    @Override
    public ElementInterface validate() {
        System.out.println("abstractElement.validate()");//TODO: jwimmer: remove before checkin
        AbstractPage.assertEquals(myEnum);
        return this;
    }
    public String getLocator(SeleniumEnums checkIt) {//TODO: jwimmer: maybe THIS is the best place for failover...  
        if (checkIt.getID() != null)
            return checkIt.getID();
        else if (checkIt.getXpath() != null)
            return checkIt.getXpath();
        else if (checkIt.getXpath_alt() != null)
            return checkIt.getXpath_alt();
        return null;
    }
    @Override
    public ElementInterface focus() {
        System.out.println("abstractElement.focus()");//TODO: jwimmer: remove before checkin
        //mySelenium.focus(locator);
        String element = getLocator(myEnum);
        String error_name = "focus: "+element;
        
        try {
            mySelenium.focus(myEnum.getID());
            error_name = "focus: "+myEnum.getID();
        } catch (NoSuchElementException e1) {
            try {
                mySelenium.focus(myEnum.getXpath());
                error_name = "focus: "+myEnum.getXpath();
            } catch (NoSuchElementException e2) {
                mySelenium.focus(myEnum.getXpath_alt());
                error_name = "focus: "+myEnum.getXpath_alt();
            }
        } catch(Exception e) {
            if(e instanceof RuntimeException)
                throw new RuntimeException(e);
            else
                mySelenium.getErrors().addError(error_name, e);
        }
        return this;
    }
    
    public ElementBase addError(String errorName) {
        mySelenium.getErrors().addError(errorName, Thread.currentThread().getStackTrace());
        return this;
    }
    public ElementBase addError(String errorName, String error) {
        mySelenium.getErrors().addError(errorName, error);
        return this;
    }
    
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String uRL) {
        URL = uRL;
    }

    public String getXpath() {
        return xpath;
    }

    public void setXpath(String xpath) {
        this.xpath = xpath;
    }

    public String getXpath_alt() {
        return xpath_alt;
    }

    public void setXpath_alt(String xpathAlt) {
        xpath_alt = xpathAlt;
    }

    public SeleniumEnums getMyEnum() {
        return myEnum;
    }

    public void setMyEnum(SeleniumEnums myEnum) {
        this.myEnum = myEnum;
    }

    public CoreMethodLib getMySelenium() {
        return mySelenium;
    }

    public void setMySelenium(CoreMethodLib mySelenium) {
        this.mySelenium = mySelenium;
    }
}

package com.inthinc.pro.automation.elements;

import java.lang.reflect.Method;

import org.openqa.selenium.NoSuchElementException;

import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.elements.ElementInterface.Typeable;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.selenium.CoreMethodLib;
import com.thoughtworks.selenium.SeleniumException;

public class TextField extends ElementBase implements Typeable {
    
    public TextField(CoreMethodLib pageSelenium, SeleniumEnums anEnum) {
        super(pageSelenium, anEnum);
        myEnum = anEnum;
        mySelenium = pageSelenium;
        // TODO Auto-generated constructor stub
    }
    
    //TODO: jwimmer: work in process... trying to build a more elegant way to have failover contained in one place... not ready for public consumption yet... recursion would be better if there were a good/easy way to increment/decrement/walkthrough the id,xpath,xpath_alt options...
    private void process(Method method, String... params ) {
        String locator = params[0];
        String error_name = params[1];
        try {
            method.invoke(this, locator, error_name);
        } catch (Exception e) {
            if(e instanceof RuntimeException)
                throw new RuntimeException(e);
            mySelenium.getErrors().addError(error_name, e);
        }
    }

    public TextField clear() {
        type(null);
        return this;
    }
    
    public TextField type(String inputText) {
        //mySelenium.type(myEnum, inputText);
        String element = getLocator(myEnum);
        String error_name = "type: " + element;
        try {
            try{
                mySelenium.type(myEnum.getID(), inputText);
            } catch(NoSuchElementException e1) {
                try{
                    mySelenium.type(myEnum.getXpath(), inputText);
                } catch(NoSuchElementException e2){
                    mySelenium.type(myEnum.getXpath_alt(), inputText);
                }
            }
        } catch (Exception e) {
            if(e instanceof RuntimeException)
                throw new RuntimeException(e);
            mySelenium.getErrors().addError(error_name, e);
        }
        return this;
    }

    //TODO: jwimmer: push the next two methods up into Text (the abstract? implementation of TextBased interface?)
    @Override
    public ElementInterface compareText(String expected) {
        String actual = getText();
        if (!expected.equals(actual)) {
            addError(this.myEnum.toString(), "Expected = " + expected + "\nActual = " + actual);
        }
        return this;
    }

    @Override
    public ElementInterface compareText() {
        return compareText(myEnum.getText());
    }

}

package com.inthinc.pro.automation.elements;

import java.lang.reflect.Method;

import com.inthinc.pro.automation.elements.ElementInterface.Typeable;
import com.inthinc.pro.automation.enums.SeleniumEnums;

public class TextField extends ElementBase implements Typeable {
    
    public TextField(SeleniumEnums anEnum) {
        super(anEnum);
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
            selenium.getErrors().addError(error_name, e);
        }
    }

    public TextField clear() {
        type(null);
        return this;
    }
    
    public TextField type(String inputText) {
        //selenium.type(myEnum, inputText);
        String error_name = "type: " + element;
        try {
            selenium.type(element, inputText);
        } catch (Exception e) {
            if(e instanceof RuntimeException)
                throw new RuntimeException(e);
            selenium.getErrors().addError(error_name, e);
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

    @Override
    public String getText() {
        return selenium.getText(element);
    }

}

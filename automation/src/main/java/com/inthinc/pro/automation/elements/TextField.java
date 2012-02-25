package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Typeable;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.interfaces.TextEnum;

public class TextField extends TextObject implements Typeable {
    
    public TextField(SeleniumEnums anEnum, Object ...objects) {
        super(anEnum, objects);
    }
    
    public TextField(SeleniumEnums anEnum, String prefix, TextEnum replacement) {
        super(anEnum, prefix + replacement.getText());
    }

    @Override
    public TextField clear() {
        type("");
        return this;
    }

    @Override
    public TextField type(Object inputText) {
        getSelenium().type(myEnum, inputText.toString());
        return this;
    }

    @Override
    public String getText(){
        return getSelenium().getValue(myEnum);
    }
    @Override
    public Boolean assertEquals() {
        return assertEquals(myEnum, getText(), myEnum);
    }

    @Override
    public Boolean assertEquals(String compareAgainst) {
        return assertEquals(compareAgainst, getText());
    }

    @Override
    public Boolean assertNotEquals(String compareAgainst) {
        return assertNotEquals(compareAgainst, getText());
    }
}

package com.inthinc.pro.automation.elements;

import com.inthinc.device.emulation.utils.DeviceUtil.TextEnum;
import com.inthinc.pro.automation.elements.ElementInterface.Typeable;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;

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
        selenium.type(myEnum, inputText.toString());
        return this;
    }

    @Override
    public String getText(){
        return selenium.getValue(myEnum);
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

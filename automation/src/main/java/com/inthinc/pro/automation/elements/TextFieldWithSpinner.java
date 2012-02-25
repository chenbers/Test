package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public class TextFieldWithSpinner extends TextField {

    private SeleniumEnumWrapper spinnerEnum;

    public TextFieldWithSpinner(SeleniumEnums anEnum) {
        super(anEnum, ("Edit"));
        spinnerEnum = new SeleniumEnumWrapper(anEnum);
        spinnerEnum.replaceWord("Buttons");
    }

    public TextFieldWithSpinner(SeleniumEnums anEnum, String type) {
        super(anEnum, (type + "Edit"));
        spinnerEnum = new SeleniumEnumWrapper(anEnum);
        spinnerEnum.replaceWord(type + "Buttons");
    }

    private SeleniumEnumWrapper setIds(Integer upOrDown) {
        String[] newIDs = spinnerEnum.getIDs();
        int last = newIDs.length - 1;
        return new SeleniumEnumWrapper(spinnerEnum).setID("//table[@id='"
                + newIDs[last] + "']/tbody/tr[" + upOrDown.toString()
                + "]/td/input");
    }

    public Button up() {
        return new Button(setIds(1)) {
            @Override
            public Button click() {
                getSelenium().mouseDown(myEnum);
                getSelenium().mouseUp(myEnum);
                return this;
            }
        };
    }

    public Button down() {
        return new Button(setIds(2)) {
            @Override
            public Button click() {
                getSelenium().mouseDown(myEnum);
                getSelenium().mouseUp(myEnum);
                return this;
            }
        };
    }

}

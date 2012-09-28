package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public class TextFieldWithSpinner extends TextField {

    private SeleniumEnumWrapper spinnerEnum;

    public TextFieldWithSpinner(SeleniumEnums anEnum) {
        super(anEnum, "Edit");
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
        String ID;
        if (!newIDs[last].startsWith("//")){
            ID = "//table[@id='" + newIDs[last] + "']";
        } else {
            ID = newIDs[last];
        }
        return new SeleniumEnumWrapper(spinnerEnum).setID(ID + "/tbody/tr[" + upOrDown.toString() + "]/td/input");
    }

    public SpinnerButton up() {
        return new SpinnerButton(setIds(1));
    }
    
    public ClickableObject clickTheUp(){
        return up().click();
    }

    public SpinnerButton down() {
        return new SpinnerButton(setIds(2));
    }
    
    public ClickableObject clickTheDown(){
        return down().click();
    }
}

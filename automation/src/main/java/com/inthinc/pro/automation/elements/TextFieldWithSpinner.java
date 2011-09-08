package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public class TextFieldWithSpinner extends TextField {
    
    private SeleniumEnumWrapper spinnerEnum;
    
    public TextFieldWithSpinner(SeleniumEnums anEnum){
        super(anEnum, ("Edit"));
        spinnerEnum = new SeleniumEnumWrapper(anEnum);
        spinnerEnum.replaceWord("Buttons");
    }
    
    public TextFieldWithSpinner(SeleniumEnums anEnum, String type){
        super(anEnum, (type+"Edit"));
        spinnerEnum = new SeleniumEnumWrapper(anEnum);
        spinnerEnum.replaceWord(type + "Buttons");
    }
    
    private void setIds(Integer upOrDown){
        String[] newIDs = spinnerEnum.getIDs();
        int last = newIDs.length - 1;
        newIDs[last] = Xpath.start().table(Id.id(newIDs[last])).tbody().tr(upOrDown.toString()).td().input().toString();
    }
    
    public Button up(){
        setIds(1);
        return new Button(spinnerEnum){
            @Override
            public Button click(){
                selenium.mouseDown(spinnerEnum);
                selenium.mouseUp(spinnerEnum);
                return this;
            }
        };
    }
    
    public Button down(){
        setIds(2);
        return new Button(spinnerEnum){
            @Override
            public Button click(){
                selenium.mouseDown(spinnerEnum);
                selenium.mouseUp(spinnerEnum);
                return this;
            }
        };
    }

}

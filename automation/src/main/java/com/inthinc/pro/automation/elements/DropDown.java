package com.inthinc.pro.automation.elements;


import com.inthinc.pro.automation.elements.ElementInterface.Selectable;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;

public class DropDown extends SelectableObject implements Selectable {
    
    public DropDown(SeleniumEnums anEnum) {
        super(anEnum);
    }
    public DropDown(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
    }
    public DropDown(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
    }
    
    public DropDown(SeleniumEnums anEnum, TextEnum replaceWord){
    	super(anEnum, replaceWord.getText());
    }
    public DropDown(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
    }
    
}

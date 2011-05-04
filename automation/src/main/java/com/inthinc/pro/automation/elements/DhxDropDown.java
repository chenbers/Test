package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.enums.SeleniumEnums;

public class DhxDropDown extends DropDown implements Clickable, TextBased {
    
    public DhxDropDown(SeleniumEnums anEnum) {
        super(anEnum);
    }
    public DhxDropDown(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
    }
    public DhxDropDown(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
    }
    public DhxDropDown(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
    }
    

    @Override
    public ElementInterface click() {
        // TODO: dtanner Auto-generated method stub
        return null;
    }

}

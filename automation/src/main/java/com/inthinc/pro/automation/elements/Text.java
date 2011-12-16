package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public class Text extends TextObject implements TextBased {
    
    public Text(SeleniumEnums anEnum, Object ...objects) {
        super(anEnum, objects);
    }

}

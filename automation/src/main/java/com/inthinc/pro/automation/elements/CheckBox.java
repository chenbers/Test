package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Checkable;
import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.enums.SeleniumEnums;

public class CheckBox extends CheckableObject implements Checkable, Clickable {
	public CheckBox(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
    }
	
	
}

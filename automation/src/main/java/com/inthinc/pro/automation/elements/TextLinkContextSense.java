package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public class TextLinkContextSense extends TextLink implements TextBased, Clickable {
    public TextLinkContextSense(SeleniumEnums anEnum, Object ...objects) {
        super(anEnum, objects);
    }
    
    @Override
    public TextLinkContextSense click() {
    	setCurrentLocation();
        String page = current.get("page");
        if (!page.equals("")){
            myEnum.replaceWord(page);
        }
        super.click();
        return this;
    }
}

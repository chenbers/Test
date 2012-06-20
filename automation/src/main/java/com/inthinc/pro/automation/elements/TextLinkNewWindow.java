package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public class TextLinkNewWindow extends TextLink implements Clickable, TextBased {
	
	public TextLinkNewWindow(SeleniumEnums anEnum, Object ...objects) {
        super(anEnum, objects);
    }
    
    @Override
    public TextLinkNewWindow click(){ 
        super.click();
        String[] handles = getSelenium().getWrappedDriver().getWindowHandles().toArray(new String[2]);
        getSelenium().getWrappedDriver().switchTo().window(handles[handles.length-1]);
        getSelenium().close();
        getSelenium().getWrappedDriver().switchTo().window(handles[0]);
        return this;
    }
}

package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.selenium.CoreMethodLib;

public class Link extends ElementBase implements Clickable {
    
    public Link(CoreMethodLib pageSelenium, SeleniumEnums anEnum) {
        super(pageSelenium, anEnum);
        myEnum = anEnum;
        mySelenium = pageSelenium;
    }

    @Override
    public ElementInterface click() {
        System.out.println("Clickable.click(...)");//TODO: jwimmer: remove before checkin
        mySelenium.click(myEnum);//TODO: jwimmer: WARNING: no failover!!!
        return null;
    }

}

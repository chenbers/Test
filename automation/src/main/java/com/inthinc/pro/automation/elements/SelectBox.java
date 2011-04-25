package com.inthinc.pro.automation.elements;

import org.openqa.selenium.By;

import com.inthinc.pro.automation.elements.ElementInterface.Selectable;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.selenium.CoreMethodLib;

public class SelectBox extends Text implements Selectable {
    
    public SelectBox(CoreMethodLib pageSelenium, SeleniumEnums anEnum) {
        super(pageSelenium, anEnum);
        myEnum = anEnum;
        mySelenium = pageSelenium;
    }

    @Override
    public ElementInterface select(String fullMatch) {
        System.out.println("Selectable.select(...)");//TODO: jwimmer: remove before checkin
        mySelenium.select(myEnum, fullMatch);
        return this;
    }

    @Override
    public ElementInterface select(Integer optionNumber) {
        System.out.println("Selectable.select(...)");//TODO: jwimmer: remove before checkin
        optionNumber--;
        mySelenium.select(myEnum, "index="+optionNumber);
        return this;
    }

    @Override
    public ElementInterface selectFullMatch(String fullMatch, Integer matchNumber) {
        System.out.println("Selectable.selectFullMatch(...)");//TODO: jwimmer: remove before checkin
        matchNumber--;
        if (myEnum.getID()!=null){
            String id = myEnum.getID();
            xpath = "//select[@id='"+id+"']/option[text(),'"+fullMatch+"']";
        }else {
            xpath = myEnum.getXpath() + "/option[text(),'"+fullMatch+"']";
        }
        mySelenium.getWrappedDriver().findElements(By.xpath(xpath)).get(matchNumber).setSelected();
        return this;
    }

    @Override
    public ElementInterface selectPartMatch(String partialMatch, Integer matchNumber) {
        System.out.println("Selectable.selectPartMatch(...)");//TODO: jwimmer: remove before checkin
        matchNumber--;
           
        if (myEnum.getID()!=null){
            String id = myEnum.getID();
            xpath = "//select[@id='"+id+"']/option[contains(text(),'"+partialMatch+"')]";
        }else {
            xpath = myEnum.getXpath() + "/option[contains(text(),'"+partialMatch+"')]";
        }
        mySelenium.getWrappedDriver().findElements(By.xpath(xpath)).get(matchNumber).setSelected();
        return this;
    }

    @Override
    public ElementInterface selectPartMatch(String partialMatch) {
        System.out.println("Selectable.selectPartMatch(...)");//TODO: jwimmer: remove before checkin
        return selectPartMatch(partialMatch, 0);
    }
}

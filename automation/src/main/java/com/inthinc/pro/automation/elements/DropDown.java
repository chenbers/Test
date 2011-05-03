package com.inthinc.pro.automation.elements;

import org.openqa.selenium.By;

import com.inthinc.pro.automation.elements.ElementInterface.Selectable;
import com.inthinc.pro.automation.enums.SeleniumEnum.SeleniumEnums;

public class DropDown extends Text implements Selectable {
    
    public DropDown(SeleniumEnums anEnum) {
        super(anEnum);
    }
    public DropDown(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
    }
    public DropDown(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
    }
    public DropDown(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
    }
    

    @Override
    public ElementInterface select(String fullMatch) {
        selenium.select(myEnum, fullMatch);
        String selected = selenium.getSelectedLabel(myEnum);
        assertEquals(selected, fullMatch);
        return this;
    }

    @Override
    public ElementInterface select(Integer optionNumber) {
        optionNumber--;
        selenium.select(myEnum, "index="+optionNumber);
        String selected = selenium.getSelectedIndex(myEnum);
        assertEquals(selected, optionNumber.toString());
        return this;
    }

    @Override
    public ElementInterface selectFullMatch(String fullMatch, Integer matchNumber) {
        matchNumber--;
        String xpath;
        if (myEnum.getID()!=null){
            String id = myEnum.getID();
            xpath = "//select[@id='"+id+"']/option[text()='"+fullMatch+"']";
        }else {
            xpath = myEnum.getXpath() + "/option[text()='"+fullMatch+"']";
        }
        webDriver.findElements(By.xpath(xpath)).get(matchNumber).setSelected();
        return this;
    }

    @Override
    public ElementInterface selectPartMatch(String partialMatch, Integer matchNumber) {
        matchNumber--;
        String xpath;
        if (myEnum.getID()!=null){
            String id = myEnum.getID();
            xpath = "//select[@id='"+id+"']/option[contains(text(),'"+partialMatch+"')]";
        }else {
            xpath = myEnum.getXpath() + "/option[contains(text(),'"+partialMatch+"')]";
        }
        webDriver.findElements(By.xpath(xpath)).get(matchNumber).setSelected();
        return this;
    }

    @Override
    public ElementInterface selectPartMatch(String partialMatch) {
        return selectPartMatch(partialMatch, 1);
    }
}

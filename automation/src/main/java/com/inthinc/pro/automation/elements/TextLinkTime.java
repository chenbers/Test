package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.enums.TextEnum;
import com.inthinc.pro.automation.enums.SeleniumEnums;

public class TextLinkTime extends TextLink implements Clickable, TextBased {
    public TextLinkTime(SeleniumEnums anEnum) {
        super(anEnum);
    }
    public TextLinkTime(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
    }
    public TextLinkTime(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
    }
    public TextLinkTime(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
    }
    
    @Override
    @Deprecated
    /**
     * Use click(TextEnum duration) instead of click
     */
    public TextLinkTime click(){
        return null;
    }
    
    public TextLinkTime click(TextEnum duration){
        myEnum.replaceWord(duration.getText());
        super.click();
        selenium.pause(8); // Give the page time to update
        return this;
    }
    
    @Override
    @Deprecated
    public ElementInterface compareText(String expected) {
        return null;
    }

    @Override
    @Deprecated
    public ElementInterface compareText() {
        return null;
    }

    @Override
    @Deprecated
    public String getText() {
       return null;
    }
    
    public TextLinkTime compareText(TextEnum duration, String expected) {
        myEnum.replaceWord(duration.getText());
        super.compareText(expected);
        return this;
    }

    public TextLinkTime compareText(TextEnum duration) {
        myEnum.replaceWord(duration.getText());
        super.compareText();
        return this;
    }

    public String getText(TextEnum duration) {
        myEnum.replaceWord(duration.getText());     
        return super.getText();
    }
}

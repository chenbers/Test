package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.enums.SeleniumEnum.SeleniumEnums;

public abstract class ClickableText extends ClickableObject implements TextBased {
    private TextObject textStuff = new Text(myEnum);
    
    public ClickableText(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
    }
    public ClickableText(SeleniumEnums anEnum) {
        super(anEnum);
    }
    public ClickableText(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
    }
    public ClickableText(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
    }
    
    
    @Override
    public ElementInterface compareText(String expected) {
        return textStuff.compareText(expected);
    }

    @Override
    public ElementInterface compareText() {
        return textStuff.compareText();
    }

    @Override
    public String getText() {
       return textStuff.getText();
    }
}

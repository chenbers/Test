package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.enums.SeleniumEnums;

public abstract class ClickableText extends ClickableObject implements TextBased {
    private TextObject textStuff;
    
    public ClickableText(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
        textStuff = new Text(anEnum);
    }
    public ClickableText(SeleniumEnums anEnum) {
        super(anEnum);
        textStuff = new Text(anEnum);
    }
    public ClickableText(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
        textStuff = new Text(anEnum);
    }
    public ClickableText(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
        textStuff = new Text(anEnum);
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

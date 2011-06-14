package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;

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
    public ClickableText(SeleniumEnums anEnum, TextEnum replaceWord){
    	super(anEnum, replaceWord);
    }
    
    @Override
    public Boolean compare(String expected) {
        return textStuff.compare(expected);
    }

    @Override
    public Boolean compare() {
        return textStuff.compare();
    }

    @Override
    public String getText() {
       return textStuff.getText();
    }
	@Override
	public Boolean assertEquals(String compareAgainst) {
		return textStuff.assertEquals(compareAgainst);
	}
}

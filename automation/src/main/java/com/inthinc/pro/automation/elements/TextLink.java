package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;

public class TextLink extends ClickableText implements TextBased, Clickable {
    private TextObject text;
	
	
    public TextLink(SeleniumEnums anEnum) {
        super(anEnum);
        text = new TextObject(anEnum);
    }
    public TextLink(SeleniumEnums anEnum, TextEnum replaceWord){
    	super(anEnum, replaceWord);
        text = new TextObject(anEnum, replaceWord);
    }
    public TextLink(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
        text = new TextObject(anEnum, replaceNumber);
    }
    public TextLink(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
        text = new TextObject(anEnum, replaceWord);
    }
    public TextLink(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
        text = new TextObject(anEnum, replaceWord, replaceNumber);
    }
	@Override
	public Boolean validateContains(String expectedPart) {
		return text.validateContains(expectedPart);
	}
	@Override
	public Boolean validate(String expected) {
		return text.validate(expected);
	}
	@Override
	public Boolean validate(TextEnum expected) {
		return text.validate(expected);
	}
	@Override
	public Boolean validate(TextEnum expected, String replaceOld, String withNew) {
		return text.validate(expected, replaceOld, withNew);
	}
	@Override
	public Boolean validate() {
		return text.validate();
	}
}

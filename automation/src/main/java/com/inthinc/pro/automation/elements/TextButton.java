package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;

public class TextButton extends ClickableText implements TextBased, Clickable {
	private TextObject text;
    
    public TextButton(SeleniumEnums anEnum) {
        super(anEnum);
        text = new TextObject(anEnum);
    }
    public TextButton(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
        text = new TextObject(anEnum, replaceNumber);
    }
    public TextButton(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
        text = new TextObject(anEnum, replaceWord);
    }
    public TextButton(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
        text = new TextObject(anEnum, replaceNumber);
    }
	public TextButton(SeleniumEnums anEnum, TextEnum replaceWord) {
        super(anEnum, replaceWord);
        text = new TextObject(anEnum, replaceWord);
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
		return validate(expected);
	}
	@Override
	public Boolean validate(TextEnum expected, String replaceOld, String withNew) {
		return validate(expected, replaceOld, withNew);
	}
	@Override
	public Boolean validate() {
		return super.validate();
	}
}
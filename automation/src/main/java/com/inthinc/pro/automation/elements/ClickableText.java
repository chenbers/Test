package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.ClickableTextBased;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;

public class ClickableText extends ClickableObject implements ClickableTextBased {

    private TextObject textStuff;

    public ClickableText(SeleniumEnums anEnum, String replaceWord,
	    Integer replaceNumber) {
	super(anEnum, replaceWord, replaceNumber);
	textStuff = new Text(anEnum);
    }

    public ClickableText(SeleniumEnums anEnum) {
	super(anEnum);
	textStuff = new Text(anEnum);
    }

    public ClickableText(SeleniumEnums anEnum, Integer replaceNumber) {
	super(anEnum, replaceNumber);
	textStuff = new Text(anEnum, replaceNumber);
    }

    public ClickableText(SeleniumEnums anEnum, String replaceWord) {
	super(anEnum, replaceWord);
	textStuff = new Text(anEnum, replaceWord);
    }

    public ClickableText(SeleniumEnums anEnum, TextEnum replaceWord) {
	super(anEnum, replaceWord);
    }
    
    @Override
    public Boolean assertEquals(){
	return textStuff.assertEquals();
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

    @Override
    public Boolean assertNotEquals(String compareAgainst) {
	return textStuff.assertNotEquals(compareAgainst);
    }

    @Override
    public Boolean validateContains(String expectedPart) {
	return textStuff.validateContains(expectedPart);
    }

    @Override
    public Boolean validate(String expected) {
	return textStuff.validate(expected);
    }

    @Override
    public Boolean validate(TextEnum expected) {
	return textStuff.validate(expected);
    }

    @Override
    public Boolean validate(TextEnum expected, String replaceOld, String withNew) {
	return textStuff.validate(expected, replaceOld, withNew);
    }

    @Override
    public Boolean validate() {
	return textStuff.validate();
    }

}

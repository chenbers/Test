package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.ClickableTextBased;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.interfaces.TextEnum;

public class ClickableText extends ClickableObject implements ClickableTextBased {

    private TextObject textStuff;

    public ClickableText(SeleniumEnums anEnum, Object... objects) {
        super(anEnum, objects);
        textStuff = new Text(anEnum, objects);
    }

    @Override
    public Boolean assertContains(String compareAgainst) {
        return this.textStuff.assertContains(compareAgainst);
    }

    @Override
    public Boolean assertDoesNotContain(String compareAgainst) {
        return this.textStuff.assertDoesNotContain(compareAgainst);
    }

    @Override
    public Boolean assertTheDefaultValue() {
        return textStuff.assertTheDefaultValue();
    }

    @Override
    public Boolean assertTheSameAs(String compareAgainst) {
        return textStuff.assertTheSameAs(compareAgainst);
    }

    @Override
    public Boolean assertIsNot(String compareAgainst) {
        return textStuff.assertIsNot(compareAgainst);
    }

    @Override
    public Boolean compareDefault() {
        return textStuff.compareDefault();
    }

    @Override
    public Boolean compare(String expected) {
        return textStuff.compare(expected);
    }

    @Override
    public String getText() {
        return textStuff.getText();
    }

    @Override
    public Boolean validateTheDefaultValue() {
        return textStuff.validateTheDefaultValue();
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
    public Boolean validateContains(String expectedPart) {
        return textStuff.validateContains(expectedPart);
    }

    @Override
    public Boolean validateDoesNotContain(String expectedPart) {
        return this.textStuff.validateDoesNotContain(expectedPart);
    }

    @Override
    public Boolean validateIsNot(String expected) {
        return this.textStuff.validateIsNot(expected);
    }

}

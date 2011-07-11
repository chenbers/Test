package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;

public class TextObject extends ElementBase implements TextBased {

    public TextObject(SeleniumEnums anEnum) {
        super(anEnum);
    }

    public TextObject(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
    }

    public TextObject(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
    }

    public TextObject(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
    }

    public TextObject(SeleniumEnums anEnum, TextEnum replaceWord) {
        super(anEnum, replaceWord);
    }

    @Override
    public Boolean assertEquals() {
        return assertEquals(myEnum, myEnum, myEnum);
    }

    @Override
    public Boolean assertEquals(String compareAgainst) {
        return assertEquals(compareAgainst, myEnum);
    }

    @Override
    public Boolean assertNotEquals(String compareAgainst) {
        return assertNotEquals(compareAgainst, myEnum);
    }

    @Override
    public Boolean compare() {
        return compare(myEnum.getText());
    }

    @Override
    public Boolean compare(String expected) {
        return compare(expected, getText());
    }

    @Override
    public String getText() {
        return selenium.getText(myEnum);
    }

    @Override
    public String toString() {
        return getText();
    }

    @Override
    public Boolean validate() {
        return validateEquals(myEnum.getText(), getText());
    }

    @Override
    public Boolean validate(String expected) {
        return compare(expected);
    }

    @Override
    public Boolean validate(TextEnum expected) {
        return compare(expected.getText());
    }

    @Override
    public Boolean validate(TextEnum expected, String replaceOld, String withNew) {
        return compare(expected.getText().replace(replaceOld, withNew));
    }

    @Override
    public Boolean validateContains(String expectedPart) {
        String actual = getText();
        return validateStringContains(actual, expectedPart);
    }
}

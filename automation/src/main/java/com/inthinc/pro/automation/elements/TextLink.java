package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.ClickableTextBased;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.interfaces.TextEnum;

public class TextLink extends ClickableText implements ClickableTextBased {

    public TextLink(SeleniumEnums anEnum) {
        super(anEnum);
    }

    public TextLink(SeleniumEnums anEnum, TextEnum replaceWord) {
        super(anEnum, replaceWord);
    }

    public TextLink(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
    }

    public TextLink(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
    }

    public TextLink(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
    }

    public TextLink(SeleniumEnums anEnum, String replaceWord, TextEnum column) {
        super(anEnum, replaceWord);
        myEnum.replaceOldWithNew("*column*", column.getText());
    }

}

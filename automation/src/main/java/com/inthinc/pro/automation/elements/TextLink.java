package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.ClickableTextBased;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.interfaces.TextEnum;

public class TextLink extends ClickableText implements ClickableTextBased {

    public TextLink(SeleniumEnums anEnum, Object ...objects) {
        super(anEnum, objects);
    }

    public TextLink(SeleniumEnums anEnum, String replaceWord, TextEnum column) {
        super(anEnum, replaceWord);
        myEnum.replaceOldWithNew("*column*", column.getText());
    }

}

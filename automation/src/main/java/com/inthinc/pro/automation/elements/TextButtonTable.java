package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.interfaces.TextEnum;

public class TextButtonTable extends TextTableLink {

    public TextButtonTable(SeleniumEnums anEnum, String replaceWord,
            Integer replaceNumber) {
        super(anEnum, replaceWord, replaceNumber);
    }

    public TextButtonTable(SeleniumEnums anEnum) {
        super(anEnum);
    }

    public TextButtonTable(SeleniumEnums anEnum, Integer replaceNumber) {
        super(anEnum, replaceNumber);
    }

    public TextButtonTable(SeleniumEnums anEnum, String replaceWord) {
        super(anEnum, replaceWord);
    }

    public TextButtonTable(SeleniumEnums anEnum, TextEnum replaceWord) {
        super(anEnum, replaceWord);
    }

    public TextButtonTable(SeleniumEnums anEnum, String page, TextEnum column) {
        super(anEnum, page, column);
    }

}

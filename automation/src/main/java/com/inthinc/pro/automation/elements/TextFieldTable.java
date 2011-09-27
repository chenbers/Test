package com.inthinc.pro.automation.elements;

import java.util.Iterator;

import com.inthinc.pro.automation.elements.ElementInterface.TextFieldTableBased;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.interfaces.TextEnum;

public class TextFieldTable implements TextFieldTableBased{
    protected SeleniumEnumWrapper myEnum;

    public TextFieldTable(SeleniumEnums anEnum, String replaceWord,
            Integer replaceNumber) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.replaceNumber(replaceNumber);
        myEnum.replaceWord(replaceWord);
    }

    public TextFieldTable(SeleniumEnums anEnum) {
        myEnum = new SeleniumEnumWrapper(anEnum);
    }

    public TextFieldTable(SeleniumEnums anEnum, Integer replaceNumber) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.replaceNumber(replaceNumber);
    }

    public TextFieldTable(SeleniumEnums anEnum, String replaceWord) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.replaceWord(replaceWord);
    }

    public TextFieldTable(SeleniumEnums anEnum, TextEnum replaceWord) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.replaceWord(replaceWord.getText());
    }

    public TextFieldTable(SeleniumEnums anEnum, String page, TextEnum column) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.replaceWord(page);
        myEnum.replaceOldWithNew("*column*", column.getText());
    }
    
    @Override
    public Iterator<TextField> iterator() {
        return new TableIterator<TextField>(this);
    }

    @Override
    public TextField row(int rowNumber) {
        return new TextField(myEnum, rowNumber);
    }

}

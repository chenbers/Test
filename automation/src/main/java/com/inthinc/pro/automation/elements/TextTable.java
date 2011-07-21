package com.inthinc.pro.automation.elements;

import java.util.Iterator;

import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.elements.ElementInterface.TextTableBased;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;

public class TextTable implements TextTableBased {
    
    protected SeleniumEnumWrapper myEnum;
    private int rowNumber;
    
    public TextTable(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.replaceNumber(replaceNumber);
        myEnum.replaceWord(replaceWord);
    }

    public TextTable(SeleniumEnums anEnum) {
        myEnum = new SeleniumEnumWrapper(anEnum);
    }

    public TextTable(SeleniumEnums anEnum, Integer replaceNumber) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.replaceNumber(replaceNumber);
    }

    public TextTable(SeleniumEnums anEnum, String replaceWord) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.replaceWord(replaceWord);
    }

    public TextTable(SeleniumEnums anEnum, TextEnum replaceWord) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.replaceWord(replaceWord.getText());
    }
    
    public TextTable(SeleniumEnums anEnum, String page, TextEnum column) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.replaceWord(page);
        myEnum.replaceOldWithNew("*column*", column.getText());
    }

    @Override
    public boolean hasNext() {
        return row(rowNumber).isPresent();
    }

    @Override
    public Text next() {
        return row(rowNumber++);
    }

    @Override
    @Deprecated
    public void remove() {
        throw new UnsupportedOperationException("There is nothing to remove");
    }

    @Override
    public Iterator<TextBased> iterator() {
        rowNumber = 0;
        return this;
    }

    @Override
    public Text row(int rowNumber) {
        return new Text(myEnum, rowNumber);
    }
    
}

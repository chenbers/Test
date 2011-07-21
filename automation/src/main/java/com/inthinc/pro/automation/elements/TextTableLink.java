package com.inthinc.pro.automation.elements;

import java.util.Iterator;

import com.inthinc.pro.automation.elements.ElementInterface.ClickableTextBased;
import com.inthinc.pro.automation.elements.ElementInterface.ClickableTextTableBased;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;

public class TextTableLink implements ClickableTextTableBased {

    protected SeleniumEnumWrapper myEnum;
    private int rowNumber;
    
    public TextTableLink(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.replaceNumber(replaceNumber);
        myEnum.replaceWord(replaceWord);
    }

    public TextTableLink(SeleniumEnums anEnum) {
        myEnum = new SeleniumEnumWrapper(anEnum);
    }

    public TextTableLink(SeleniumEnums anEnum, Integer replaceNumber) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.replaceNumber(replaceNumber);
    }

    public TextTableLink(SeleniumEnums anEnum, String replaceWord) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.replaceWord(replaceWord);
    }

    public TextTableLink(SeleniumEnums anEnum, TextEnum replaceWord) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.replaceWord(replaceWord.getText());
    }

    public TextTableLink(SeleniumEnums anEnum, String page, TextEnum column) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.replaceWord(page);
        myEnum.replaceOldWithNew("*column*", column.getText());
    }

    @Override
    public boolean hasNext() {
        return row(rowNumber).isPresent();
    }

    @Override
    public TextLink next() {
        return row(rowNumber++);
    }

    @Override
    @Deprecated
    public void remove() {
        throw new UnsupportedOperationException("There is nothing to remove");
    }

    @Override
    public Iterator<ClickableTextBased> iterator() {
        rowNumber = 0;
        return this;
    }

    @Override
    public TextLink row(int rowNumber) {
        return new TextLink(myEnum, rowNumber);
    }


}

package com.inthinc.pro.automation.elements;

import java.util.Iterator;

import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.elements.ElementInterface.ClickableTableBased;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;

public class ClickableTableObject implements ClickableTableBased {
    private SeleniumEnumWrapper myEnum;
    private int rowNumber=1;

    public ClickableTableObject(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.replaceNumber(replaceNumber);
        myEnum.replaceWord(replaceWord);
    }

    public ClickableTableObject(SeleniumEnums anEnum) {
        myEnum = new SeleniumEnumWrapper(anEnum);
    }

    public ClickableTableObject(SeleniumEnums anEnum, Integer replaceNumber) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.replaceNumber(replaceNumber);
    }

    public ClickableTableObject(SeleniumEnums anEnum, String replaceWord) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.replaceWord(replaceWord);
    }

    public ClickableTableObject(SeleniumEnums anEnum, TextEnum replaceWord) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.replaceWord(replaceWord.getText());
    }

    @Override
    public boolean hasNext() {
        return row(rowNumber).isPresent();
    }

    @Override
    public Clickable next() {
        return row(rowNumber++);
    }

    @Override
    @Deprecated
    public void remove() {
        throw new UnsupportedOperationException("There is nothing to remove");
    }

    @Override
    public Iterator<Clickable> iterator() {
        rowNumber = 1;
        return this;
    }

    @Override
    public Clickable row(int rowNumber) {
        return new ClickableObject(myEnum, rowNumber);
    }


}

package com.inthinc.pro.automation.elements;

import java.util.Iterator;

import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.elements.ElementInterface.TableBased;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.IndexEnum;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.interfaces.TextEnum;

public class LinkTable implements TableBased<Clickable> {

    private SeleniumEnumWrapper myEnum;

    public LinkTable(SeleniumEnums anEnum, String replaceWord,
            Integer replaceNumber) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.replaceNumber(replaceNumber);
        myEnum.replaceWord(replaceWord);
    }

    public LinkTable(SeleniumEnums anEnum) {
        myEnum = new SeleniumEnumWrapper(anEnum);
    }

    public LinkTable(SeleniumEnums anEnum, Integer replaceNumber) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.replaceNumber(replaceNumber);
    }

    public LinkTable(SeleniumEnums anEnum, String replaceWord) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.replaceWord(replaceWord);
    }

    public LinkTable(SeleniumEnums anEnum, TextEnum replaceWord) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.replaceWord(replaceWord.getText());
    }


    @Override
    public Iterator<Clickable> iterator() {
        return new TableIterator<Clickable>(this);
    }

    @Override
    public Clickable row(int rowNumber) {
        return new ClickableObject(myEnum, rowNumber);
    }

    @Override
    public Clickable row(IndexEnum indexByName) {
        return row(indexByName.getIndex());
    }
}

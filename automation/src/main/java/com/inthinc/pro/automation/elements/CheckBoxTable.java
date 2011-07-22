package com.inthinc.pro.automation.elements;

import java.util.Iterator;

import com.inthinc.pro.automation.elements.ElementInterface.Checkable;
import com.inthinc.pro.automation.elements.ElementInterface.CheckableTable;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.enums.SeleniumEnums;

public class CheckBoxTable implements CheckableTable {

    private final SeleniumEnumWrapper myEnum;
    
    public CheckBoxTable(SeleniumEnums anEnum) {
        myEnum = new SeleniumEnumWrapper(anEnum);
    }

    public CheckBoxTable(SeleniumEnums anEnum, String replaceWord) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.replaceWord(replaceWord);
    }

    @Override
    public Iterator<Checkable> iterator() {
        return new TableIterator<Checkable>(new CheckBoxTable(myEnum));
    }


    @Override
    public Checkable row(int rowNumber) {
        return new CheckBox(myEnum, rowNumber);
    }

}

package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.TableBased;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.IndexEnum;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.interfaces.TextEnum;

public class TextFieldTable implements TableBased<TextField>{
    protected SeleniumEnumWrapper myEnum;

    public TextFieldTable(SeleniumEnums anEnum, Object ...objects) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.makeReplacements(objects);
    }


    public TextFieldTable(SeleniumEnums anEnum, String page, TextEnum column, Object ...objects) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.makeReplacements(objects);
        myEnum.replaceOldWithNew("*column*", column.getText());
    }
    
    @Override
    public TableIterator<TextField> iterator() {
        return new TableIterator<TextField>(this);
    }

    @Override
    public TextField row(int rowNumber) {
        return new TextField(myEnum, rowNumber);
    }

    @Override
    public TextField row(IndexEnum indexByName) {
        return row(indexByName.getIndex());
    }

}

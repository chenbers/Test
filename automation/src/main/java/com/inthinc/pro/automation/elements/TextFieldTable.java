package com.inthinc.pro.automation.elements;

import java.util.Iterator;

import com.inthinc.device.emulation.utils.DeviceUtil.IndexEnum;
import com.inthinc.device.emulation.utils.DeviceUtil.TextEnum;
import com.inthinc.pro.automation.elements.ElementInterface.TableBased;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;

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
    public Iterator<TextField> iterator() {
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

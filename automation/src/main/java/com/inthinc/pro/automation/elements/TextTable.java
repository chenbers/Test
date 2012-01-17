package com.inthinc.pro.automation.elements;

import java.util.Iterator;

import com.inthinc.device.emulation.utils.DeviceUtil.IndexEnum;
import com.inthinc.device.emulation.utils.DeviceUtil.TextEnum;
import com.inthinc.pro.automation.elements.ElementInterface.TableBased;
import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public class TextTable implements TableBased<TextBased> {

    protected SeleniumEnumWrapper myEnum;

    public TextTable(SeleniumEnums anEnum, Object ...objects) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.makeReplacements(objects);
    }


    public TextTable(SeleniumEnums anEnum, String page, TextEnum column) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.replaceWord(page);
        myEnum.replaceOldWithNew("*column*", column.getText());
    }


    @Override
    public Iterator<TextBased> iterator() {
        return new TableIterator<TextBased>(this);
    }

    @Override
    public Text row(int rowNumber) {
        return new Text(myEnum, rowNumber);
    }

    @Override
    public TextBased row(IndexEnum indexByName) {
        return row(indexByName.getIndex());
    }

}

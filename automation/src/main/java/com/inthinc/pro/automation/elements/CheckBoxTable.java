package com.inthinc.pro.automation.elements;

import java.util.Iterator;

import com.inthinc.device.emulation.utils.DeviceUtil.IndexEnum;
import com.inthinc.pro.automation.elements.ElementInterface.Checkable;
import com.inthinc.pro.automation.elements.ElementInterface.TableBased;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public class CheckBoxTable implements TableBased<Checkable> {

    private final SeleniumEnumWrapper myEnum;
    
    public CheckBoxTable(SeleniumEnums anEnum, Object ...objects) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.makeReplacements(objects);
    }

    @Override
    public Iterator<Checkable> iterator() {
        return new TableIterator<Checkable>(new CheckBoxTable(myEnum));
    }


    @Override
    public Checkable row(int rowNumber) {
        return new CheckBox(myEnum, rowNumber);
    }
    
    @Override
    public Checkable row(IndexEnum index) {
        return row(index.getIndex());
    }

}

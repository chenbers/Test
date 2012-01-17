package com.inthinc.pro.automation.elements;

import java.util.Iterator;

import com.inthinc.device.emulation.utils.DeviceUtil.IndexEnum;
import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.elements.ElementInterface.TableBased;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public class ClickableTableObject implements TableBased<Clickable> {
    private SeleniumEnumWrapper myEnum;

    public ClickableTableObject(SeleniumEnums anEnum, Object ...objects) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.makeReplacements(objects);
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

package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.elements.ElementInterface.ClickableTableBased;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.IndexEnum;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public class ClickableTableObject implements ClickableTableBased<Clickable> {
    private SeleniumEnumWrapper myEnum;

    public ClickableTableObject(SeleniumEnums anEnum, Object ...objects) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.makeReplacements(objects);
    }

    @Override
    public TableIterator<Clickable> iterator() {
        return new TableIterator<Clickable>(this);
    }

    @SuppressWarnings("unchecked")
	@Override
    public Clickable row(int rowNumber) {
        return new ClickableObject(myEnum, rowNumber);
    }

    @SuppressWarnings("unchecked")
	@Override
    public Clickable row(IndexEnum indexByName) {
        return row(indexByName.getIndex());
    }

}

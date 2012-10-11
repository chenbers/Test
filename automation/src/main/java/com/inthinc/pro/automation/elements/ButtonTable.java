package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.elements.ElementInterface.TableBased;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public class ButtonTable extends ClickableTableObject implements TableBased<Clickable> {

	
	public ButtonTable(SeleniumEnums anEnum, Object ...objects) {
        super(anEnum, objects);
    }
}

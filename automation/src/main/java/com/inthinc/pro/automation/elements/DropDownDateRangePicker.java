package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public class DropDownDateRangePicker extends DropDown {

	public DropDownDateRangePicker(SeleniumEnums anEnum, Object... objects) {
		super(anEnum, objects);
		
	}
	
	@Override
	public DropDownDateRangePicker select(String selection) {
		new Link(myEnum).click();
		return this;
	}

}

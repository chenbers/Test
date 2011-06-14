package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.enums.AutomationEnum;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.elements.ElementInterface.Checkable;
import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public class CheckableObject extends ClickableObject implements Checkable,
		Clickable {
	
	public CheckableObject(SeleniumEnums anEnum) {
		super(anEnum);
	}
	public CheckableObject(SeleniumEnums anEnum, String replaceWord) {
		super(anEnum, replaceWord);
	}

	@Override
	public CheckableObject check(Integer number) {
		replaceNumber(number);
		selenium.check(myEnum);
		return null;
	}

	@Override
	public CheckableObject uncheck(Integer number) {
		replaceNumber(number);
		selenium.check(myEnum);
		return null;
	}

	@Override
	public Boolean isChecked(Integer number) {
		replaceNumber(number);
		return selenium.isChecked(myEnum);
	}

	@Override
	@Deprecated 
	/**
	 * Use click(Integer number) to specify which item you are clicking
	 * 
	 * @deprecated use {@link com.inthinc.pro.automation.elements.CheckableObject#click(Integer)}
	 */
	public ClickableObject click() {
	    addError("CheckableObject.click()", "more information is required to determine WHICH item to click.  please supply either an (Integer number) or a (String label)", ErrorLevel.FAIL);
		return null;
	}

	public ClickableObject click(Integer number) {
		replaceNumber(number);
		return super.click();
	}

	public ClickableObject click(String label) {
		String xpath = Xpath.start().label(Id.text(label)).input().toString();
		myEnum = AutomationEnum.PLACE_HOLDER.setID(xpath);
		return super.click();
	}

}

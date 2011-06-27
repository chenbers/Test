package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.Checkable;
import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.elements.ElementInterface.TableBased;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public class CheckableObject extends LinkTable implements Checkable, TableBased,
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
		return isChecked();
	}
	
	private Boolean isChecked(){
	    return selenium.isChecked(myEnum);
	}

	@Override
	@Deprecated 
	/**
	 * Use click(Integer number) to specify which item you are clicking
	 * 
	 * @deprecated use {@link com.inthinc.pro.automation.elements.CheckableObject#click(Integer)}
	 */
	public CheckableObject click() {
    	addError(
				".click()",
				"please supply an Integer number for the row on the table)",
				ErrorLevel.FAIL);
		return null;
	}

	public CheckableObject click(Integer row) {
		super.click(row);
		return this;
	}

	public CheckableObject click(String label) {
		String xpath = Xpath.start().label(Id.text(label)).input().toString();
		myEnum.setID(xpath);
		super.click(1);
		return this;
	}
	@Override
	public Boolean validateChecked(Integer number, Boolean checked) {
	    return validateEquals(checked, isChecked());
	}
	@Override
	public Boolean assertChecked(Integer number, Boolean checked) {
	    return assertEquals(checked, isChecked());
	}
	@Override
	public Boolean hasFocus(Integer number) {
	    replaceNumber(number);
	    return super.hasFocus();
	}
}

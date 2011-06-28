package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.CheckableTable;
import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.elements.ElementInterface.TableBased;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public class CheckBoxTable extends LinkTable implements CheckableTable,
	TableBased, Clickable {

    private CheckBox checkBox;
    
    public CheckBoxTable(SeleniumEnums anEnum) {
	super(anEnum);
	checkBox = new CheckBox(anEnum);
    }

    public CheckBoxTable(SeleniumEnums anEnum, String replaceWord) {
	super(anEnum, replaceWord);
	checkBox = new CheckBox(anEnum);
    }

    @Override
    public CheckableTable check(Integer number) {
	checkBox.replaceNumber(number);
	checkBox.check();
	return null;
    }

    @Override
    public CheckableTable uncheck(Integer number) {
	checkBox.replaceNumber(number);
	checkBox.check();
	return null;
    }

    @Override
    public Boolean isChecked(Integer number) {
	checkBox.replaceNumber(number);
	return checkBox.isChecked();
    }

    @Override
    @Deprecated
    /**
     * Use click(Integer number) to specify which item you are clicking
     * 
     * @deprecated use {@link com.inthinc.pro.automation.elements.CheckableObject#click(Integer)}
     */
    public ClickableTableObject click() {
	addError(
				".click()",
				"please supply an Integer number for the row on the table)",
				ErrorLevel.FAIL);
	return null;
    }

    public ClickableTableObject click(Integer row) {
	super.click(row);
	return this;
    }

    public CheckableTable click(String label) {
	String xpath = Xpath.start().label(Id.text(label)).input().toString();
	myEnum.setID(xpath);
	super.click(1);
	return this;
    }

    @Override
    public Boolean validateChecked(Integer number, Boolean checked) {
	return validateEquals(checked, isChecked(number));
    }

    @Override
    public Boolean assertChecked(Integer number, Boolean checked) {
	return assertEquals(checked, isChecked(number));
    }

    @Override
    public Boolean hasFocus(Integer number) {
	replaceNumber(number);
	return super.hasFocus();
    }
}

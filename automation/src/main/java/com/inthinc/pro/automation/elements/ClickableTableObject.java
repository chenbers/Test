package com.inthinc.pro.automation.elements;

import com.inthinc.pro.automation.elements.ElementInterface.ClickableTableBased;
import com.inthinc.pro.automation.elements.ElementInterface.TableBased;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;

public class ClickableTableObject extends ClickableObject implements
	ClickableTableBased,
	TableBased {

    public ClickableTableObject(SeleniumEnums anEnum, String replaceWord,
	    Integer replaceNumber) {
	super(anEnum, replaceWord, replaceNumber);
    }

    public ClickableTableObject(SeleniumEnums anEnum) {
	super(anEnum);
    }

    public ClickableTableObject(SeleniumEnums anEnum, Integer replaceNumber) {
	super(anEnum, replaceNumber);
    }

    public ClickableTableObject(SeleniumEnums anEnum, String replaceWord) {
	super(anEnum, replaceWord);
    }

    public ClickableTableObject(SeleniumEnums anEnum, TextEnum replaceWord) {
	super(anEnum, replaceWord);
    }

    @Override
    @Deprecated
    public ClickableTableObject click() {
	addError(
				".click()",
				"please supply an Integer number for the row on the table)",
				ErrorLevel.FAIL);
	return null;
    }

    /**
     * Choose the row, or Item number, that you want to click.<br />
     * If you are selecting the item from a 2D table<br />
     * then you want to count row by row left to right.<br />
     * 
     * @param row
     * @return
     */
    public ClickableTableObject click(Integer row) {
	replaceNumber(row);
	super.click();
	return this;
    }

    @Override
    public Boolean isVisible(Integer row) {
	replaceNumber(row);
	return super.isVisible();
    }

    @Override
    public Boolean isPresent(Integer row) {
	replaceNumber(row);
	return super.isPresent();
    }

    @Override
    public ElementInterface focus(Integer row) {
	replaceNumber(row);
	return super.focus();
    }

    @Override
    public Boolean assertVisibility(Integer row, Boolean visible) {
	replaceNumber(row);
	return super.assertVisibility(visible);
    }

    @Override
    @Deprecated
    public Boolean isVisible() {
	addError(
				".isVisible()",
				"please supply an Integer number for the row on the table)",
				ErrorLevel.FAIL);
	return null;
    }

    @Override
    @Deprecated
    public Boolean isPresent() {
	addError(
				".isPresent()",
				"please supply an Integer number for the row on the table)",
				ErrorLevel.FAIL);
	return null;
    }

    @Override
    @Deprecated
    public ElementInterface focus() {
	addError(
				".focus()",
				"please supply an Integer number for the row on the table)",
				ErrorLevel.FAIL);
	return null;
    }

    @Override
    @Deprecated
    public Boolean assertVisibility(Boolean visible) {
	addError(
			".assertVisibility()",
			"please supply an Integer number for the row on the table)",
			ErrorLevel.FAIL);
	return null;
    }

    @Override
    public Boolean validateVisibility(Integer row, Boolean visible) {
	replaceNumber(row);
	return super.validateVisibility(visible);
    }

    @Override
    public Boolean validatePresence(Integer row, Boolean present) {
	replaceNumber(row);
	return super.validatePresence(present);
    }

    @Override
    public Boolean assertPresence(Integer row, Boolean present) {
	replaceNumber(row);
	return super.assertPresence(present);
    }

    @Override
    public Boolean isClickable(Integer row) {
	replaceNumber(row);
	return super.isClickable();
    }

    @Override
    public Boolean validateClickable(Integer row, Boolean clickable) {
	replaceNumber(row);
	return super.validateClickable(clickable);
    }

    @Override
    public Boolean assertClickable(Integer row, Boolean clickable) {
	replaceNumber(row);
	return super.assertClickable(clickable);
    }

    @Override
    @Deprecated
    public Boolean isClickable() {
	addError(
		".isClickable()",
		"please supply an Integer number for the row on the table)",
		ErrorLevel.FAIL);
	return null;
    }

    @Override
    @Deprecated
    public Boolean validateClickable(Boolean clickable) {
	addError(
		".validateClickable()",
		"please supply an Integer number for the row on the table)",
		ErrorLevel.FAIL);
	return null;
    }

    @Override
    @Deprecated
    public Boolean assertClickable(Boolean clickable) {
	addError(
		".assertClickable()",
		"please supply an Integer number for the row on the table)",
		ErrorLevel.FAIL);
	return null;
    }

}

package com.inthinc.pro.automation.selenium;

import org.openqa.selenium.WebDriver;

import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.thoughtworks.selenium.Selenium;

public interface CoreMethodInterface extends Selenium {
	
	public CoreMethodLib addSelection(SeleniumEnumWrapper anEnum,String optionLocator);
	
	public CoreMethodLib check(SeleniumEnumWrapper anEnum);
	public CoreMethodLib click(SeleniumEnumWrapper anEnum);
	public CoreMethodLib clickAt(SeleniumEnumWrapper anEnum,String coordString);
	public boolean compareLocation(String expected);
	public CoreMethodLib contextMenu(SeleniumEnumWrapper anEnum);
	public CoreMethodLib contextMenuAt(SeleniumEnumWrapper anEnum,String coordString);
	public CoreMethodLib doubleClick(SeleniumEnumWrapper anEnum);
	public CoreMethodLib doubleClickAt(SeleniumEnumWrapper anEnum,String coordString);
	public CoreMethodLib fireEvent(SeleniumEnumWrapper anEnum,String eventName);
	public CoreMethodLib focus(SeleniumEnumWrapper anEnum);
	public String getCurrentMonth();
	public SeleniumEnumWrapper getEnum();
	public ErrorCatcher getErrors();
	public String[] getFiveDayPeriodLong();
	public String[] getFiveDayPeriodShort();
	public String getLocator(SeleniumEnumWrapper myEnum);
	public String getSelectedIndex(SeleniumEnumWrapper myEnum);
	public String getSelectedLabel(SeleniumEnumWrapper myEnum);
	public String getTable(SeleniumEnumWrapper myEnum);
	public String getTable(SeleniumEnumWrapper myEnum, Integer row, Integer col);
	public String getText(SeleniumEnumWrapper anEnum);
	public String[] getTimeFrameOptions();
	public String getValue(SeleniumEnumWrapper anEnum);
	public WebDriver getWrappedDriver();
	public boolean hasFocus(SeleniumEnumWrapper anEnum);
	public boolean isChecked(SeleniumEnumWrapper anEnum);
	public boolean isEditable(SeleniumEnumWrapper anEnum);
	public boolean isElementPresent(SeleniumEnumWrapper anEnum);
	public boolean isVisible(SeleniumEnumWrapper anEnum);
	public CoreMethodLib keyDown(SeleniumEnumWrapper anEnum,String keySequence);
	public CoreMethodLib keyPress(SeleniumEnumWrapper anEnum,String keySequence);
	public CoreMethodLib keyUp(SeleniumEnumWrapper anEnum,String keySequence);
	public CoreMethodLib mouseDown(SeleniumEnumWrapper anEnum);
	public CoreMethodLib mouseDownAt(SeleniumEnumWrapper anEnum,String coordString);
	public CoreMethodLib mouseDownRight(SeleniumEnumWrapper anEnum);
	public CoreMethodLib mouseDownRightAt(SeleniumEnumWrapper anEnum,String coordString);
	public CoreMethodLib mouseMove(SeleniumEnumWrapper anEnum);
	public CoreMethodLib mouseMoveAt(SeleniumEnumWrapper anEnum,String coordString);
	public CoreMethodLib mouseOut(SeleniumEnumWrapper anEnum);
	public CoreMethodLib mouseOver(SeleniumEnumWrapper anEnum);
	public CoreMethodLib mouseUp(SeleniumEnumWrapper anEnum);
	public CoreMethodLib mouseUpAt(SeleniumEnumWrapper anEnum,String coordString);
	public CoreMethodLib mouseUpRight(SeleniumEnumWrapper anEnum);
	public CoreMethodLib mouseUpRightAt(SeleniumEnumWrapper anEnum,String coordString);
	public CoreMethodLib open(SeleniumEnumWrapper anEnum);
	public CoreMethodLib pause(Integer timeout_in_secs, String reasonForPause);
	public CoreMethodLib removeAllSelections(SeleniumEnumWrapper anEnum);
	public CoreMethodLib removeSelection(SeleniumEnumWrapper anEnum,String optionLocator);
	public CoreMethodLib select(SeleniumEnumWrapper anEnum,String optionLocator);
	public CoreMethodLib selectDhx(SeleniumEnumWrapper myEnum, String option);
	public CoreMethodLib selectWindowByID(String ID);
	public CoreMethodLib selectWindowByName(String name);
	public CoreMethodLib selectWindowByTitle(String title);
	public CoreMethodLib type(SeleniumEnumWrapper anEnum,String value);
	public CoreMethodLib typeKeys(SeleniumEnumWrapper anEnum,String value);
	public CoreMethodLib uncheck(SeleniumEnumWrapper anEnum);
	public boolean verifyLocation(SeleniumEnumWrapper myEnum);
	public CoreMethodLib waitForElementPresent(Object element, Integer secondsToWait);
	public CoreMethodLib waitForPageToLoad();

	public CoreMethodLib waitForPageToLoad(Integer timeout);
}

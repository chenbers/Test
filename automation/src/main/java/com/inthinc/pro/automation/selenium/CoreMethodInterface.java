package com.inthinc.pro.automation.selenium;

import org.openqa.selenium.WebDriver;

import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.thoughtworks.selenium.Selenium;

public interface CoreMethodInterface extends Selenium {
	
	public CoreMethodLib addSelection(SeleniumEnumWrapper myEnum,String optionLocator);
	
	public CoreMethodLib check(SeleniumEnumWrapper myEnum);
	public CoreMethodLib click(SeleniumEnumWrapper myEnum);
	public CoreMethodLib clickAt(SeleniumEnumWrapper myEnum,String coordString);
	public boolean compareLocation(String expected);
	public CoreMethodLib contextMenu(SeleniumEnumWrapper myEnum);
	public CoreMethodLib contextMenuAt(SeleniumEnumWrapper myEnum,String coordString);
	public CoreMethodLib doubleClick(SeleniumEnumWrapper myEnum);
	public CoreMethodLib doubleClickAt(SeleniumEnumWrapper myEnum,String coordString);
	public CoreMethodLib fireEvent(SeleniumEnumWrapper myEnum,String eventName);
	public CoreMethodLib focus(SeleniumEnumWrapper myEnum);
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
	public String getText(SeleniumEnumWrapper myEnum);
	public String[] getTimeFrameOptions();
	public String getValue(SeleniumEnumWrapper myEnum);
	public WebDriver getWrappedDriver();
	public boolean hasFocus(SeleniumEnumWrapper myEnum);
	public boolean isChecked(SeleniumEnumWrapper myEnum);
	public boolean isEditable(SeleniumEnumWrapper myEnum);
	public boolean isElementPresent(SeleniumEnumWrapper myEnum);
	public boolean isVisible(SeleniumEnumWrapper myEnum);
	public CoreMethodLib keyDown(SeleniumEnumWrapper myEnum,String keySequence);
	public CoreMethodLib keyPress(SeleniumEnumWrapper myEnum,String keySequence);
	public CoreMethodLib keyUp(SeleniumEnumWrapper myEnum,String keySequence);
	public CoreMethodLib mouseDown(SeleniumEnumWrapper myEnum);
	public CoreMethodLib mouseDownAt(SeleniumEnumWrapper myEnum,String coordString);
	public CoreMethodLib mouseDownRight(SeleniumEnumWrapper myEnum);
	public CoreMethodLib mouseDownRightAt(SeleniumEnumWrapper myEnum,String coordString);
	public CoreMethodLib mouseMove(SeleniumEnumWrapper myEnum);
	public CoreMethodLib mouseMoveAt(SeleniumEnumWrapper myEnum,String coordString);
	public CoreMethodLib mouseOut(SeleniumEnumWrapper myEnum);
	public CoreMethodLib mouseOver(SeleniumEnumWrapper myEnum);
	public CoreMethodLib mouseUp(SeleniumEnumWrapper myEnum);
	public CoreMethodLib mouseUpAt(SeleniumEnumWrapper myEnum,String coordString);
	public CoreMethodLib mouseUpRight(SeleniumEnumWrapper myEnum);
	public CoreMethodLib mouseUpRightAt(SeleniumEnumWrapper myEnum,String coordString);
	public CoreMethodLib open(SeleniumEnumWrapper myEnum);
	public CoreMethodLib pause(Integer timeout_in_secs, String reasonForPause);
	public CoreMethodLib removeAllSelections(SeleniumEnumWrapper myEnum);
	public CoreMethodLib removeSelection(SeleniumEnumWrapper myEnum,String optionLocator);
	public CoreMethodLib select(SeleniumEnumWrapper myEnum,String optionLocator);
	public CoreMethodLib selectDhx(SeleniumEnumWrapper myEnum, String option);
	public CoreMethodLib selectWindowByID(String ID);
	public CoreMethodLib selectWindowByName(String name);
	public CoreMethodLib selectWindowByTitle(String title);
	public CoreMethodLib type(SeleniumEnumWrapper myEnum,String value);
	public CoreMethodLib typeKeys(SeleniumEnumWrapper myEnum,String value);
	public CoreMethodLib uncheck(SeleniumEnumWrapper myEnum);
	public boolean verifyLocation(SeleniumEnumWrapper myEnum);
	public CoreMethodLib waitForElementPresent(Object element, Integer secondsToWait);
	public CoreMethodLib waitForPageToLoad();

	public CoreMethodLib waitForPageToLoad(Integer timeout);

	public Boolean isClickable(SeleniumEnumWrapper myEnum);
}

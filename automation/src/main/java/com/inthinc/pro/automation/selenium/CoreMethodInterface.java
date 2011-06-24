package com.inthinc.pro.automation.selenium;

import org.openqa.selenium.WebDriver;

import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.thoughtworks.selenium.Selenium;

public interface CoreMethodInterface extends Selenium {
	
	public WebDriver getWrappedDriver();
	
	public CoreMethodLib click(SeleniumEnumWrapper anEnum);
	public CoreMethodLib doubleClick(SeleniumEnumWrapper anEnum);
	public CoreMethodLib contextMenu(SeleniumEnumWrapper anEnum);
	public CoreMethodLib clickAt(SeleniumEnumWrapper anEnum,String coordString);
	public CoreMethodLib doubleClickAt(SeleniumEnumWrapper anEnum,String coordString);
	public CoreMethodLib contextMenuAt(SeleniumEnumWrapper anEnum,String coordString);
	public CoreMethodLib fireEvent(SeleniumEnumWrapper anEnum,String eventName);
	public CoreMethodLib focus(SeleniumEnumWrapper anEnum);
	public CoreMethodLib keyPress(SeleniumEnumWrapper anEnum,String keySequence);
	public CoreMethodLib keyDown(SeleniumEnumWrapper anEnum,String keySequence);
	public CoreMethodLib keyUp(SeleniumEnumWrapper anEnum,String keySequence);
	public CoreMethodLib mouseOver(SeleniumEnumWrapper anEnum);
	public CoreMethodLib mouseOut(SeleniumEnumWrapper anEnum);
	public CoreMethodLib mouseDown(SeleniumEnumWrapper anEnum);
	public CoreMethodLib mouseDownRight(SeleniumEnumWrapper anEnum);
	public CoreMethodLib mouseDownAt(SeleniumEnumWrapper anEnum,String coordString);
	public CoreMethodLib mouseDownRightAt(SeleniumEnumWrapper anEnum,String coordString);
	public CoreMethodLib mouseUp(SeleniumEnumWrapper anEnum);
	public CoreMethodLib mouseUpRight(SeleniumEnumWrapper anEnum);
	public CoreMethodLib mouseUpAt(SeleniumEnumWrapper anEnum,String coordString);
	public CoreMethodLib mouseUpRightAt(SeleniumEnumWrapper anEnum,String coordString);
	public CoreMethodLib mouseMove(SeleniumEnumWrapper anEnum);
	public CoreMethodLib mouseMoveAt(SeleniumEnumWrapper anEnum,String coordString);
	public CoreMethodLib type(SeleniumEnumWrapper anEnum,String value);
	public CoreMethodLib typeKeys(SeleniumEnumWrapper anEnum,String value);
	public CoreMethodLib check(SeleniumEnumWrapper anEnum);
	public CoreMethodLib uncheck(SeleniumEnumWrapper anEnum);
	public CoreMethodLib select(SeleniumEnumWrapper anEnum,String optionLocator);
	public CoreMethodLib addSelection(SeleniumEnumWrapper anEnum,String optionLocator);
	public CoreMethodLib removeSelection(SeleniumEnumWrapper anEnum,String optionLocator);
	public CoreMethodLib removeAllSelections(SeleniumEnumWrapper anEnum);
	public CoreMethodLib open(SeleniumEnumWrapper anEnum);
	public String getValue(SeleniumEnumWrapper anEnum);
	public String getText(SeleniumEnumWrapper anEnum);
	public boolean isChecked(SeleniumEnumWrapper anEnum);
	public boolean isElementPresent(SeleniumEnumWrapper anEnum);
	public boolean isVisible(SeleniumEnumWrapper anEnum);
	public boolean isEditable(SeleniumEnumWrapper anEnum);
	public CoreMethodLib pause(Integer timeout_in_secs, String reasonForPause);
	public boolean isTextPresentOnPage(String text);
	public String getSelectedLabel(SeleniumEnumWrapper myEnum);
	public String getLocator(SeleniumEnumWrapper myEnum);
	public 	String getLocation(String expected);
	public 	String getCurrentMonth();
	public 	String[] getFiveDayPeriodLong();
	public String[] getFiveDayPeriodShort();
	public String getSelectedIndex(SeleniumEnumWrapper myEnum);
	public String[] getTimeFrameOptions();
	public ErrorCatcher getErrors();
	public String getTable(SeleniumEnumWrapper myEnum, Integer row, Integer col);
	public String getTable(SeleniumEnumWrapper myEnum);
	public CoreMethodLib selectWindowByID(String ID);
	public CoreMethodLib selectWindowByName(String name);
	public CoreMethodLib selectWindowByTitle(String title);
	public boolean verifyLocation(SeleniumEnumWrapper myEnum);
	public boolean verifyLocation(String expected);
	public CoreMethodLib waitForElementPresent(Object element, Integer secondsToWait);
	public CoreMethodLib waitForPageToLoad();
	public CoreMethodLib waitForPageToLoad(Integer timeout);
	public CoreMethodLib selectDhx(SeleniumEnumWrapper myEnum, String option);
	public String getErrorName();

	public SeleniumEnumWrapper getEnum();
}

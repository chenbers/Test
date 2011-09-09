package com.inthinc.pro.automation.selenium;

import org.openqa.selenium.WebDriver;

import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.enums.Browsers;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.thoughtworks.selenium.Selenium;

public interface CoreMethodInterface extends Selenium {
	
	public CoreMethodInterface addSelection(SeleniumEnumWrapper myEnum,String optionLocator);
	
	public CoreMethodInterface check(SeleniumEnumWrapper myEnum);
	public CoreMethodInterface click(SeleniumEnumWrapper myEnum);
	public CoreMethodInterface clickAt(SeleniumEnumWrapper myEnum,String coordString);
	public boolean compareLocation(String expected);
	public CoreMethodInterface contextMenu(SeleniumEnumWrapper myEnum);
	public CoreMethodInterface contextMenuAt(SeleniumEnumWrapper myEnum,String coordString);
	public CoreMethodInterface doubleClick(SeleniumEnumWrapper myEnum);
	public CoreMethodInterface doubleClickAt(SeleniumEnumWrapper myEnum,String coordString);
	public CoreMethodInterface fireEvent(SeleniumEnumWrapper myEnum,String eventName);
	public CoreMethodInterface focus(SeleniumEnumWrapper myEnum);
	public String getAttribute(SeleniumEnumWrapper myEnum, String attributeToGet);
	public SeleniumEnumWrapper getEnum();
	public ErrorCatcher getErrors();
	public String getLocator(SeleniumEnumWrapper myEnum);
	public String getSelectedIndex(SeleniumEnumWrapper myEnum);
	public String getSelectedLabel(SeleniumEnumWrapper myEnum);
	public String getTable(SeleniumEnumWrapper myEnum);
	public String getTable(SeleniumEnumWrapper myEnum, Integer row, Integer col);
	public String getText(SeleniumEnumWrapper myEnum);
	public String getValue(SeleniumEnumWrapper myEnum);
	public WebDriver getWrappedDriver();
	public boolean hasFocus(SeleniumEnumWrapper myEnum);
	public boolean isChecked(SeleniumEnumWrapper myEnum);
	public boolean isEditable(SeleniumEnumWrapper myEnum);
	public boolean isElementPresent(SeleniumEnumWrapper myEnum);
	public boolean isVisible(SeleniumEnumWrapper myEnum);
	public CoreMethodInterface keyDown(SeleniumEnumWrapper myEnum,String keySequence);
	public CoreMethodInterface keyPress(SeleniumEnumWrapper myEnum,String keySequence);
	public CoreMethodInterface keyUp(SeleniumEnumWrapper myEnum,String keySequence);
	public CoreMethodInterface mouseDown(SeleniumEnumWrapper myEnum);
	public CoreMethodInterface mouseDownAt(SeleniumEnumWrapper myEnum,String coordString);
	public CoreMethodInterface mouseDownRight(SeleniumEnumWrapper myEnum);
	public CoreMethodInterface mouseDownRightAt(SeleniumEnumWrapper myEnum,String coordString);
	public CoreMethodInterface mouseMove(SeleniumEnumWrapper myEnum);
	public CoreMethodInterface mouseMoveAt(SeleniumEnumWrapper myEnum,String coordString);
	public CoreMethodInterface mouseOut(SeleniumEnumWrapper myEnum);
	public CoreMethodInterface mouseOver(SeleniumEnumWrapper myEnum);
	public CoreMethodInterface mouseUp(SeleniumEnumWrapper myEnum);
	public CoreMethodInterface mouseUpAt(SeleniumEnumWrapper myEnum,String coordString);
	public CoreMethodInterface mouseUpRight(SeleniumEnumWrapper myEnum);
	public CoreMethodInterface mouseUpRightAt(SeleniumEnumWrapper myEnum,String coordString);
	public CoreMethodInterface open(SeleniumEnumWrapper myEnum);
	public CoreMethodInterface removeAllSelections(SeleniumEnumWrapper myEnum);
	public CoreMethodInterface removeSelection(SeleniumEnumWrapper myEnum,String optionLocator);
	public CoreMethodInterface select(SeleniumEnumWrapper myEnum,String optionLocator);
	public CoreMethodInterface selectDhx(SeleniumEnumWrapper myEnum, String option);
	public CoreMethodInterface selectWindowByID(String ID);
	public CoreMethodInterface selectWindowByName(String name);
	public CoreMethodInterface selectWindowByTitle(String title);
	public CoreMethodInterface type(SeleniumEnumWrapper myEnum,String value);
	public CoreMethodInterface typeKeys(SeleniumEnumWrapper myEnum,String value);
	public CoreMethodInterface uncheck(SeleniumEnumWrapper myEnum);
	public boolean verifyLocation(SeleniumEnumWrapper myEnum);
	public CoreMethodInterface waitForElementPresent(Object element, Integer secondsToWait);
	public CoreMethodInterface waitForPageToLoad();

	public CoreMethodInterface waitForPageToLoad(Integer timeout);

	public Boolean isClickable(SeleniumEnumWrapper myEnum);

    public String[] getSelectOptions(SeleniumEnumWrapper myEnum);

    public String getDHXText(SeleniumEnumWrapper myEnum, String option);

    public CoreMethodInterface click(String xpath, Integer matchNumber);
    public CoreMethodInterface click(String xpath, String desiredOption, Integer matchNumber);
    
    public String getTextFromElementWithFocus();
    public CoreMethodInterface tabKey();
    public CoreMethodInterface enterKey();

    Addresses getSilo();

    Browsers getBrowser();
    
}

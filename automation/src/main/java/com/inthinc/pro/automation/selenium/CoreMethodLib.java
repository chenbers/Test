package com.inthinc.pro.automation.selenium;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;

import com.google.common.base.Supplier;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;
import com.thoughtworks.selenium.DefaultSelenium;

/****************************************************************************************
 * Extend the functionality of DefaultSelenium, but add some error handling
 * around it
 * 
 * try{<br />
 * <br />
 * catch(Exception e){<br />
 * errors.Error(NameOfError, e)<br />
 * 
 * @see DefaultSelenium
 * @see ErrorCatcher
 * @author dtanner
 * 
 */
public class CoreMethodLib extends WebDriverBackedSelenium implements
	CoreMethodInterface {

    public static Integer PAGE_TIMEOUT = 30000;
    private final static Logger logger = Logger.getLogger(CoreMethodLib.class);
    private ErrorCatcher errors;
    private SeleniumEnumWrapper myEnum;

    public CoreMethodLib(Supplier<WebDriver> maker, String baseUrl) {
	super(maker, baseUrl);
	errors = new ErrorCatcher(this);
    }

    public CoreMethodLib(WebDriver baseDriver, String baseUrl) {
	super(baseDriver, baseUrl);
	errors = new ErrorCatcher(this);
    }

    @Override
    public CoreMethodLib addSelection(SeleniumEnumWrapper myEnum, String item) {
	String element = getLocator(myEnum);
	addSelection(element, item);
	return this;
    }

    @Override
    public CoreMethodLib check(SeleniumEnumWrapper myEnum) {
	String element = getLocator(myEnum);
	check(element);
	return this;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#click(String)}
     * @param myEnum
     * @param replacement
     * @return
     */
    @Override
    public CoreMethodLib click(SeleniumEnumWrapper myEnum) {
	String element = getLocator(myEnum);
	click(element);
	pause(2, "click(" + myEnum + ")");
	return this;
    }

    @Override
    public CoreMethodLib clickAt(SeleniumEnumWrapper anEnum, String coordString) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public CoreMethodLib contextMenu(SeleniumEnumWrapper anEnum) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public CoreMethodLib contextMenuAt(SeleniumEnumWrapper anEnum,
		String coordString) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public CoreMethodLib doubleClick(SeleniumEnumWrapper anEnum) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public CoreMethodLib doubleClickAt(SeleniumEnumWrapper anEnum,
			String coordString) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public CoreMethodLib fireEvent(SeleniumEnumWrapper myEnum, String eventName) {
	String element = getLocator(myEnum);
	fireEvent(element, eventName);
	return this;
    }

    /**
     * @param myEnum
     * @return
     */
    @Override
    public CoreMethodLib focus(SeleniumEnumWrapper myEnum) {

	do {
	    selectWindow("");
	    tabKey();
	} while (hasFocus(myEnum));

	return this;
    }

    @Override
    public boolean hasFocus(SeleniumEnumWrapper anEnum) {
	String element = getLocator(anEnum);
	WebElement item = null;
	if (element.startsWith("//")) {
	    item = getWrappedDriver().findElement(By.xpath(element));
	} else if (!element.contains("=")) {
	    item = getWrappedDriver().findElement(By.id(element));
	} else {
	    return false;
	}

	WebElement hasFocus = getWrappedDriver().switchTo().activeElement();

	return hasFocus.equals(item);

	// if (item.getX()!=hasFocus.getX()){
	// return false;
	// }else if (item.getY()!=item.getY()){
	// return false;
	// }else {
	// return true;
	// }
    }

    private void tabKey() {
	try {
	    Robot r = new Robot();
	    r.keyPress(KeyEvent.VK_TAB);
	    r.keyRelease(KeyEvent.VK_TAB);
	} catch (AWTException e) {
	    e.printStackTrace();
	}
    }

    @Deprecated
    @Override
    public String[] getAllWindowIds() {
	return null;
    }

    @Deprecated
    @Override
    public String[] getAllWindowNames() {
	return null;
    }

    @Override
    public String getCurrentMonth() {
	Calendar today = GregorianCalendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("MMMMM");
	String month = sdf.format(today.getTime());
	return month;
    }

    @Override
    public SeleniumEnumWrapper getEnum() {
	return myEnum;
    }

    @Override
    public ErrorCatcher getErrors() {
	return errors;
    }

    @Override
    public String[] getFiveDayPeriodLong() {
	String[] timeFrame = new String[5];
	Calendar today = GregorianCalendar.getInstance();
	today.add(Calendar.DATE, -2);
	SimpleDateFormat sdf = new SimpleDateFormat("EEEE");

	for (int i = 0; i < 5; i++) {
	    timeFrame[i] = sdf.format(today.getTime());
	    today.add(Calendar.DATE, -1);
	}

	return timeFrame;
    }

    @Override
    public String[] getFiveDayPeriodShort() {
	String[] timeFrame = new String[5];
	Calendar today = GregorianCalendar.getInstance();
	today.add(Calendar.DATE, -2);
	SimpleDateFormat sdf = new SimpleDateFormat("EEE");

	for (int i = 0; i < 5; i++) {
	    timeFrame[i] = sdf.format(today.getTime());
	    today.add(Calendar.DATE, -1);
	}

	return timeFrame;
    }

    /**
     * Returns the best locator string to use for this element.
     * 
     * @param myEnum
     * @param replaceName
     * @param replaceNumber
     * @return the best locator string to use for this element, null if none are
     *         found in page
     */
    @Override
    public String getLocator(SeleniumEnumWrapper myEnum) {
	this.myEnum = myEnum;
	String last = "";
	for (String s : myEnum.getLocators()) {
	    if (isElementPresent(s)) {
		return s;
	    }
	    last = s;
	}
	return last;
    }

    @Override
    public String getSelectedIndex(SeleniumEnumWrapper myEnum) {
	String element = getLocator(myEnum);
	return getSelectedIndex(element);
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#getSelectedLabel(String)}
     * @param myEnum
     * @return
     */
    @Override
    public String getSelectedLabel(SeleniumEnumWrapper myEnum) {
	String element = getLocator(myEnum);
	return getSelectedLabel(element);
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#getTable(String)}
     * @param myEnum
     * @return
     */
    @Override
    public String getTable(SeleniumEnumWrapper myEnum) {
	return getTable(myEnum, null, null);
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#getTable(String)}
     * @param myEnum
     * @param row
     * @param col
     * @return
     */
    @Override
    public String getTable(SeleniumEnumWrapper myEnum, Integer row, Integer col) {
	StringBuffer element = new StringBuffer(getLocator(myEnum));
	String text = "";

	if (row != null && col != null)
	    element.append("." + row + "." + col);
	text = getTable(element.toString());

	return text;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#getText(String)}
     * @param myEnum
     * @param replacement
     * @param replacmentNumber
     * @return
     */
    @Override
    public String getText(SeleniumEnumWrapper myEnum) {
	logger.debug(" getText(" + myEnum.toString() + "\n"
		+ myEnum.getLocatorsAsString() + ")");
	String element = getLocator(myEnum);
	String text = "";
	text = getText(element);
	return text;
    }

    @Override
    public String[] getTimeFrameOptions() {
	String[] timeFrame = new String[11];
	String[] fiveDays = getFiveDayPeriodLong();

	timeFrame[0] = "Today";
	timeFrame[1] = "Yesterday";

	for (int i = 2; i < 7; i++) {
	    timeFrame[i] = fiveDays[i - 2];
	}

	timeFrame[7] = "Past Week";
	timeFrame[8] = getCurrentMonth();
	timeFrame[9] = "Past 30 Days";
	timeFrame[10] = "Past Year";

	return timeFrame;
    }

    @Override
    public String getValue(SeleniumEnumWrapper anEnum) {
	// TODO Auto-generated method stub
	return null;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#isChecked(String)}
     * @param myEnum
     * @param condition
     * @return
     */
    @Override
    public boolean isChecked(SeleniumEnumWrapper myEnum) {
	String element = getLocator(myEnum);
	return isChecked(element);
    }

    @Override
    public boolean isEditable(SeleniumEnumWrapper myEnum) {
	String element = getLocator(myEnum);
	return isEditable(element);
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#isElementPresent(String)}
     * @param myEnum
     * @param replacementWord
     * @param replacementNumber
     * @return
     */
    @Override
    public boolean isElementPresent(SeleniumEnumWrapper myEnum) {
	String element = getLocator(myEnum);
	return isElementPresent(element);
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#isVisible(String)}
     * @param myEnum
     * @param replacementWord
     * @param replacementNumber
     * @return
     */
    @Override
    public boolean isVisible(SeleniumEnumWrapper myEnum) {
	String element = getLocator(myEnum);
	return isVisible(element);
    }

    @Override
    public CoreMethodLib keyDown(SeleniumEnumWrapper anEnum, String keySequence) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public CoreMethodLib keyPress(SeleniumEnumWrapper anEnum, String keySequence) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public CoreMethodLib keyUp(SeleniumEnumWrapper anEnum, String keySequence) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public CoreMethodLib mouseDown(SeleniumEnumWrapper anEnum) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public CoreMethodLib mouseDownAt(SeleniumEnumWrapper anEnum,
			String coordString) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public CoreMethodLib mouseDownRight(SeleniumEnumWrapper anEnum) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public CoreMethodLib mouseDownRightAt(SeleniumEnumWrapper anEnum,
			String coordString) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public CoreMethodLib mouseMove(SeleniumEnumWrapper anEnum) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public CoreMethodLib mouseMoveAt(SeleniumEnumWrapper anEnum,
			String coordString) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public CoreMethodLib mouseOut(SeleniumEnumWrapper anEnum) {
	// TODO Auto-generated method stub
	return null;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#mouseDown(String)}
     * @param myEnum
     */
    @Override
    public CoreMethodLib mouseOver(SeleniumEnumWrapper myEnum) {
	String element = getLocator(myEnum);
	mouseOver(element);
	return this;
    }

    @Override
    public CoreMethodLib mouseUp(SeleniumEnumWrapper anEnum) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public CoreMethodLib mouseUpAt(SeleniumEnumWrapper anEnum,
			String coordString) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public CoreMethodLib mouseUpRight(SeleniumEnumWrapper anEnum) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public CoreMethodLib mouseUpRightAt(SeleniumEnumWrapper anEnum,
			String coordString) {
	// TODO Auto-generated method stub
	return null;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#open(String)}
     * @param myEnum
     * @return
     */
    @Override
    public CoreMethodLib open(SeleniumEnumWrapper myEnum) {
	String element = myEnum.getURL();
	open(element);
	return this;
    }

    @Override
    public CoreMethodLib pause(Integer timeout_in_secs, String reasonForPause) {
	try {
	    logger.debug("pausing for " + timeout_in_secs
		    + " seconds because: " + reasonForPause);
	    // Thread.currentThread();
	    Thread.sleep((long) (timeout_in_secs * 1000));
	} catch (InterruptedException e) {
	    e.printStackTrace();
	} catch (RuntimeException e) {
	    throw e;
	}
	return this;
    }

    @Override
    public CoreMethodLib removeAllSelections(SeleniumEnumWrapper myEnum) {
	String element = getLocator(myEnum);
	removeAllSelections(element);
	return this;
    }

    @Override
    public CoreMethodLib removeSelection(SeleniumEnumWrapper myEnum, String item) {
	String element = getLocator(myEnum);
	removeSelection(element, item);
	return this;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#select(String, String)}
     * @param myEnum
     * @param label
     * @return
     */
    @Override
    public CoreMethodLib select(SeleniumEnumWrapper myEnum, String label) {
	String element = getLocator(myEnum);

	select(element, label);
	pause(5, "Pausing so browser has a chance to catch up");

	return this;
    }

    @Override
    public CoreMethodLib selectDhx(SeleniumEnumWrapper myEnum, String option) {
	String element = getLocator(myEnum);
	String xpath = Xpath.start().body().div(Id.id(element)).div(option)
		.toString();
	click(xpath);
	pause(3, "Pause so the browser has a chance to catch up");
	return this;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#selectWindow(String)}
     * @param ID
     * @return
     */
    @Override
    public CoreMethodLib selectWindowByID(String ID) {
	if (ID.equals("")) {
	    ID = "null";
	}
	selectWindow(ID);
	return this;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#selectWindow(String)}
     * @param name
     * @return
     */
    @Override
    public CoreMethodLib selectWindowByName(String name) {
	selectWindow("name=" + name);
	return this;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#selectWindow(String)}
     * @param title
     * @return
     */
    @Override
    public CoreMethodLib selectWindowByTitle(String title) {
	selectWindow("title=" + title);
	return this;
    }

    @Override
    @Deprecated
    public void start() {
	throw new IllegalArgumentException("This method cannot be called.");
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#type(String, String)}
     * @param myEnum
     * @param text
     * @return
     */
    @Override
    public CoreMethodLib type(SeleniumEnumWrapper myEnum, String text) {
	String element = getLocator(myEnum);

	fireEvent(element, "keydown");
	type(element, text);
	fireEvent(element, "keyup");
	return this;
    }

    @Override
    public CoreMethodLib typeKeys(SeleniumEnumWrapper anEnum, String value) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public CoreMethodLib uncheck(SeleniumEnumWrapper myEnum) {
	String element = getLocator(myEnum);
	uncheck(element);
	return this;
    }

    @Override
    public boolean verifyLocation(SeleniumEnumWrapper myEnum) {
	return myEnum.getURL().equals(getLocator(myEnum));
    }

    @Override
    public boolean compareLocation(String expected) {
	return expected.equals(getLocation());
    }

    @Override
    public CoreMethodLib waitForElementPresent(Object element,
	    Integer secondsToWait) {
	Integer x = 0;
	boolean found = false;
	boolean doneWaiting = false;
	while (!found && !doneWaiting) {
	    boolean foundByString = ((element instanceof String) && (isElementPresent((String) element)));
	    boolean foundByEnum = ((element instanceof SeleniumEnumWrapper) && (isElementPresent((SeleniumEnumWrapper) element)));
	    found = foundByString || foundByEnum;
	    pause(1, "waitForElementPresent: " + element); // second
	    x++;
	    doneWaiting = x > secondsToWait;
	}
	if (!found)
	    errors.addError("waitForElementPresent TIMEOUT",
		    "while waiting for " + element);
	return this;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#waitForPageToLoad(String)}
     * @return
     */
    @Override
    public CoreMethodLib waitForPageToLoad() {
	waitForPageToLoad(PAGE_TIMEOUT);
	return this;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#waitForPageToLoad(String)}
     * @param timeout
     * @return
     */
    @Override
    public CoreMethodLib waitForPageToLoad(Integer timeout) {
	waitForPageToLoad(timeout.toString());
	return this;
    }

    @Override
    public Boolean isClickable(SeleniumEnumWrapper myEnum) {
	String element = getLocator(myEnum);
	WebElement item = getWrappedDriver().findElement(getLocator(element));
	String tag = item.getTagName();
	boolean results = false;
	if (tag.equals("button")||tag.equals("a")){
	    results = true;
	}else if ( element.startsWith("//")){
	    results = isElementPresent(element+"/a");
	}else if ( !element.contains("=")){
	    results = isElementPresent("//"+tag+"[@id='"+element+"']/a");
	}
	return results;
    }

    private By getLocator(String locator) {
	if (locator.startsWith("//")) {
	    return By.xpath(locator);
	} else if (!locator.contains("=")) {
	    return By.id(locator);
	} else {
	    return null;
	}
    }

}

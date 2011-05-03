package com.inthinc.pro.automation.selenium;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;

import com.google.common.base.Supplier;
import com.inthinc.pro.automation.enums.SeleniumEnum.SeleniumEnums;
import com.thoughtworks.selenium.DefaultSelenium;

/****************************************************************************************
 * Extend the functionality of DefaultSelenium, but add some error handling around it
 * 
 * try{} catch(AssertionError e){ errors.Error(NameOfError, e)} catch(SeleniumException e){ errors.Error(NameOfError, e)} catch(Exception e){ errors.Error(NameOfError, e)}
 * 
 * @see DefaultSelenium
 * @see ErrorCatcher
 * @author dtanner
 * 
 */
public class CoreMethodLib extends WebDriverBackedSelenium {
    public static Integer PAGE_TIMEOUT = 30000;

    private ErrorCatcher errors;

    public CoreMethodLib(Supplier<WebDriver> maker, String baseUrl) {
        super(maker, baseUrl);
        errors = new ErrorCatcher();
    }

    public CoreMethodLib(WebDriver baseDriver, String baseUrl) {
        super(baseDriver, baseUrl);
        errors = new ErrorCatcher();
    }

    public CoreMethodLib addtoPanel(SeleniumEnums myEnum, String itemtomove) {
        addSelection(getLocator(myEnum) + "-from", itemtomove);
        click(getLocator(myEnum) + "-move_right");
        pause(2);
        return this;
    }
    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#click(String)}
     * @param myEnum
     * @param replacement
     * @return
     */
    public CoreMethodLib click(SeleniumEnums myEnum) {
        return click(myEnum, null, null);
    }
    
    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#click(String)}
     * @param myEnum
     * @param replacement
     * @return
     */
    public CoreMethodLib click(SeleniumEnums myEnum, String replaceString, Integer replaceNumber) {
        String element = getLocator(myEnum, replaceString, replaceNumber);
        String error_name = "click: " + element;
        try {
            click(element);
            pause(2);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return this;
    }

    public ErrorCatcher getErrors() {
        return errors;
    }

    @Deprecated
    public String[] getAllWindowIds() {
        return null;
    }

    @Deprecated
    public String[] getAllWindowNames() {
        return null;
    }

    public static String[] getTimeFrameOptions() {
        String[] timeFrame = new String[11];
        String[] fiveDays = getFiveDayPeriodLong();

        timeFrame[0] = "Today";
        timeFrame[1] = "Yesterday";

        for (int i = 2; i < 7; i++) {
            timeFrame[i] = fiveDays[i-2];
        }

        timeFrame[7] = "Past Week";
        timeFrame[8] = getCurrentMonth();
        timeFrame[9] = "Past 30 Days";
        timeFrame[10] = "Past Year";

        return timeFrame;
    }
    
    public String getSelectedIndex(SeleniumEnums myEnum){
        String element = getLocator(myEnum);
        String error_name = "select: " + element;
        try {
            return getSelectedIndex(element);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }return null;
    }

    public static String[] getFiveDayPeriodLong() {
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
    
    public static String[] getFiveDayPeriodShort() {
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
    
    public static String getCurrentMonth() {
        Calendar today = GregorianCalendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMMM");
        String month = sdf.format(today.getTime());
        return month;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#getLocation()}
     * @param expected
     * @return
     */
    public String getLocation(String expected) {
        String error_name = "verifyLocation: " + expected;
        String location = "Could not get location";
        try {
            location = getLocation();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return location;
    }

    /**
     * Returns the best locator string to use for this element.
     * @param myEnum
     * @return
     */
    public String getLocator(SeleniumEnums myEnum) {
        return getLocator(myEnum, null, null);
    }
    
    /**
     * Returns the best locator string to use for this element.
     * 
     * @param myEnum
     * @param replaceWord
     * @return
     */
    public String getLocator(SeleniumEnums myEnum, String replaceWord) {
        return getLocator(myEnum, replaceWord, null);
    }
    
    /**
     * Returns the best locator string to use for this element.
     * @param myEnum
     * @param replaceNumber
     * @return
     */
    public String getLocator(SeleniumEnums myEnum, Integer replaceNumber) {
        return getLocator(myEnum, null, replaceNumber);
    }
    
    /**
     * Returns the best locator string to use for this element.
     * @param myEnum
     * @param replaceName
     * @param replaceNumber
     * @return the best locator string to use for this element, null if none are found in page
     */
    public String getLocator(SeleniumEnums myEnum, String replaceName, Integer replaceNumber) {
        String id = null;
        String number="";
        if (replaceName==null){
            replaceName = "";
        }
        if (replaceNumber!=null){
            number = replaceNumber.toString();
        } 

        for(String s: myEnum.getLocators()){
            id = s.replace("***", replaceName).replace("###", number);
            if(isElementPresent(id)){
                return id;
            }
        }
        return null;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#getSelectedLabel(String)}
     * @param myEnum
     * @return
     */
    public String getSelectedLabel(SeleniumEnums myEnum) {
        String element = getLocator(myEnum);
        String error_name = "select: " + element;
        try {
            return getSelectedLabel(element);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return null;
    }
    
    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#focus(String)}
     * @param myEnum
     * @return
     */
    public CoreMethodLib focus(SeleniumEnums myEnum) {
        return focus(myEnum, null, null);
    }
    
    /**
     * @param myEnum
     * @param replaceWord
     * @param replaceNumber
     * @return
     */
    public CoreMethodLib focus(SeleniumEnums myEnum, String replaceWord, Integer replaceNumber) {
        String element = getLocator(myEnum, replaceWord, replaceNumber);
        String error_name = "focus: " + element;
        try {
            focus(element);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return this;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#getTable(String)}
     * @param myEnum
     * @return
     */
    public String getTable(SeleniumEnums myEnum) {
        return getTable(myEnum, null, null);
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#getTable(String)}
     * @param myEnum
     * @param row
     * @param col
     * @return
     */
    public String getTable(SeleniumEnums myEnum, Integer row, Integer col) {
        StringBuffer element = new StringBuffer(getLocator(myEnum));
        String error_name = "Click: " + element;
        String text = "";
        try {
            if(row != null && col != null)
                element.append("."+row+"."+col);
            text = getTable(element.toString());
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return text;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#getText(String)}
     * @param myEnum
     * @return
     */
    public String getText(SeleniumEnums myEnum) {
        return getText(myEnum, null, null);
    }
    
    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#getText(String)}
     * @param myEnum
     * @param replacement
     * @param replacmentNumber
     * @return
     */
    public String getText(SeleniumEnums myEnum, String replacement, Integer replaceNumber) {
        String element = getLocator(myEnum, replacement, replaceNumber);
        String error_name = "getText: " + element;
        String text = "";
        try {
            text = getText(element);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return text;
    }

    public Boolean inSession() {//TODO: jwimmer: question for dTanner: if you deprecated getAllWindowNames() why are we then using it here?
        try {
            getAllWindowNames();
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#isChecked(String)}
     * @param myEnum
     * @param condition
     * @return
     */
    public Boolean isChecked(SeleniumEnums myEnum, String condition) {
        String element = getLocator(myEnum);
        String error_name = "isChecked: " + element;
        try {
            return isChecked(element);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return null;
    }
    //TODO: jwimmer: dTanner: determine why we need/want this if we already have isChecked
    //error_name will be isNotChecked (vs isChecked)
    public Boolean isNotChecked(SeleniumEnums myEnum) {
        String element = getLocator(myEnum);
        String error_name = "isNotChecked: " + element;
        try {
            return !isChecked(element);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }

        return null;
    }

    //TODO: jwimmer: determine why we need/want this if we already have isElementPresent
  //error_name will be isElementNotPresent (vs isElementPresent)
    public Boolean isElementNotPresent(SeleniumEnums myEnum) {
        String element = getLocator(myEnum);
        String error_name = "isElementNotPresent: " + element;
        try {
            return !isElementPresent(element);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return null;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#isElementPresent(String)}
     * @param myEnum
     * @return
     */
    public Boolean isElementPresent(SeleniumEnums myEnum) {
        return isElementPresent(myEnum, null, null);
    }
    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#isElementPresent(String)}
     * @param myEnum
     * @param replacementWord
     * @param replacementNumber
     * @return
     */
    public Boolean isElementPresent(SeleniumEnums myEnum, String replacementWord, Integer replacementNumber) {
        String element = getLocator(myEnum, replacementWord, replacementNumber);
        String error_name = "isElementPresent: " + element;
        try {
            return isElementPresent(element);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return null;
    }

    /**
     * Override to the DefaultSelenium method. This provides a way to handle errors.
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#isTextPresent(String)}
     * 
     * @param text
     * @return Boolean
     */
    public Boolean isTextPresentOnPage(String text) {
        String error_name = "isTextPresent: " + text;
        try {
            return isTextPresent(text);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return null;
    }

    //TODO: jwimmer: determine why we need/want this if we already have isTextPresentOnPage
    //error_name will be isTextNotPresentOnPage (vs isTextPresentOnPage)
    public Boolean isTextNotPresentOnPage(String text) {
        String error_name = "isTextNotPresent: " + text;
        try {
            return !isTextPresentOnPage(text);  
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return null;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#isVisible(String)}
     * @param myEnum
     * @return
     */
    public Boolean isVisible(SeleniumEnums myEnum) {
        return isVisible(myEnum, null, null);
    }
    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#isVisible(String)}
     * @param myEnum
     * @param replacementWord
     * @param replacementNumber
     * @return
     */
    public Boolean isVisible(SeleniumEnums myEnum, String replacementWord, Integer replacementNumber) {
        String element = getLocator(myEnum, replacementWord, replacementNumber);
        String error_name = "isVisible: " + element;
        try {
            return isVisible(element);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return null;
    }
    //TODO: jwimmer: determine why we need/want this if we already have isVisible
    //error_name will be isNotVisisble (vs isVisible)
    public Boolean isNotVisible(SeleniumEnums myEnum) {
        String element = getLocator(myEnum);
        String error_name = "isNotVisible: " + element;
        try {
            return !isVisible(element);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return null;
    }

    public CoreMethodLib moveallPanel(SeleniumEnums myEnum, String moveoption) {
        String element = getLocator(myEnum);
        if (moveoption.contentEquals("Right")) {
            click(element + "-move_all_right");
        } else if (moveoption.contentEquals("Left")) {
            click(element + "-move_all_left");
        }
        pause(2);
        return this;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#open(String)}
     * @param myEnum
     * @return
     */
    public CoreMethodLib open(SeleniumEnums myEnum) {
        String element = myEnum.getURL();
        String error_name = "open: " + element;
        try {
            open(element);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return this;
    }

    public CoreMethodLib pause(Integer timeout_in_secs) {
        try {
            Thread.currentThread();
            Thread.sleep((long) (timeout_in_secs * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            throw e;
        }
        return this;
    }

    public CoreMethodLib removefromPanel(SeleniumEnums myEnum, String itemtomove) {
        addSelection(getLocator(myEnum) + "-picked", itemtomove);
        click(getLocator(myEnum) + "-move_left");
        pause(2);
        return this;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#select(String, String)}
     * @param myEnum
     * @param label
     * @return
     */
    public CoreMethodLib select(SeleniumEnums myEnum, String label) {
        String element = getLocator(myEnum);
        String error_name = "select: " + element + " : Label = " + label;

        try {
            select(element, label);
            pause(5);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return this;
    }
    
    public CoreMethodLib selectDhxCombo(Integer divPosition, String entry_name) {
        String element = "//div[" + divPosition + "]/div[text()='" + entry_name + "']";
        click(element);
        return this;
    }

    public CoreMethodLib selectDhxCombo(String entry_name) {
        String element = "//div[text()='" + entry_name + "']";
        click(element);
        return this;
    }

    public CoreMethodLib selectDhxCombo(String[] entry_name) {
        StringWriter aStringAString = new StringWriter();
        for (int i = 0; i < entry_name.length; i++) {
            aStringAString.write(entry_name[i]);
            if (i != (entry_name.length - 1)) {
                aStringAString.write(" - ");
            }
        }
        selectDhxCombo(aStringAString.toString());
        return this;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#selectWindow(String)}
     * @param ID
     * @return
     */
    public CoreMethodLib selectWindowByID(String ID) {
        if (ID.equals("")) {
            ID = "null";
        }
        String error_name = "selectWindowByID: " + ID;
        try {
            selectWindow(ID);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return this;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#selectWindow(String)}
     * @param name
     * @return
     */
    public CoreMethodLib selectWindowByName(String name) {
        String error_name = "selectWindowByTitle: " + name;
        try {
            selectWindow("name=" + name);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return this;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#selectWindow(String)}
     * @param title
     * @return
     */
    public CoreMethodLib selectWindowByTitle(String title) {
        String error_name = "selectWindowByTitle: " + title;
        try {
            selectWindow("title=" + title);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return this;
    }

    @Override
    @Deprecated
    public void start() {
        errors = new ErrorCatcher();
        super.start();
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#type(String, String)}
     * @param myEnum
     * @param text
     * @return
     */
    public CoreMethodLib type(SeleniumEnums myEnum, String text) {
        String element = getLocator(myEnum);
        String error_name = "type: " + element;
        try {
            type(element, text);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return this;
    }

    public Boolean verifyLocation(SeleniumEnums myEnum) {
        return myEnum.getURL().equals(getLocator(myEnum));
    }

    public Boolean verifyLocation(String expected) {
        return expected.equals(getLocation(expected));
    }

    //TODO: jwimmer: question for dTanner: it looks like we are NOT using waitForElementPresent(...) if we are NOT going to use this, lets axe it.  if we are going to use it, it needs to work in conjunction with the new getLocator
    public CoreMethodLib waitForElementPresent(String watch_for) {
        waitForElementPresent(watch_for, "link");
        return this;
    }

    public CoreMethodLib waitForElementPresent(String watch_for, String type) {
        waitForElementPresent(watch_for, type, 180);
        return this;
    }

    public CoreMethodLib waitForElementPresent(String watch_for, String type, Integer secondsToWait) {
        Integer x = 0;
        boolean found = false;
        boolean doneWaiting = false;
        while (!found || !doneWaiting) {
            try {
                if (type.equals("link")) {
                    found = isElementPresent("link=" + watch_for);
                } else if (type.compareToIgnoreCase("text") == 0) {
                    found = isTextPresentOnPage(watch_for);
                } else if (type.compareToIgnoreCase("element") == 0) {
                    found = isElementPresent(watch_for);
                } else {
                    doneWaiting = true; // no need to wait if the type is not recognized
                }
            } catch (Exception e) {
                continue;
            } finally {
                pause(1); // second
                x++;
                doneWaiting = x > secondsToWait;
            }
        }
        return this;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#waitForPageToLoad(String)}
     * @return
     */
    public CoreMethodLib waitForPageToLoad() {
        waitForPageToLoad(PAGE_TIMEOUT);
        return this;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#waitForPageToLoad(String)}
     * @param timeout
     * @return
     */
    public CoreMethodLib waitForPageToLoad(Integer timeout) {
        String error_name = "waitForPageToLoad: Timeout = " + timeout;
        try {
            waitForPageToLoad(timeout.toString());
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return this;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#mouseDown(String)}
     * @param myEnum
     */
    public void mouseOver(SeleniumEnums myEnum) {
        String element = getLocator(myEnum);
        String error_name = "mouseOver: " + element;
        try {
            mouseOver(element);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
    }

}

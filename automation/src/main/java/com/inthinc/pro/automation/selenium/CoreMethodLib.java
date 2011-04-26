package com.inthinc.pro.automation.selenium;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;

import com.google.common.base.Supplier;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleniumException;

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

    public CoreMethodLib addtoPanel(SeleniumEnums checkIt, String itemtomove) {
        addSelection(getLocator(checkIt) + "-from", itemtomove);
        click(getLocator(checkIt) + "-move_right");
        Pause(2);
        return this;
    }

    public CoreMethodLib click(SeleniumEnums checkIt) {
        String element = getLocator(checkIt);
        String error_name = "click: " + element;
        try {
            click(element);
            Pause(2);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return this;
    }
    
    public CoreMethodLib click(SeleniumEnums checkIt, String replacement) {
        String element = getLocator(checkIt, replacement);
        String error_name = "click: " + element;
        try {
            click(element);
            Pause(2);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
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

    public String getLocation(String expected) {
        String error_name = "verifyLocation: " + expected;
        String location = "Could not get location";
        try {
            location = getLocation();
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return location;
    }

    public String getLocator(SeleniumEnums checkIt) {
        if (checkIt.getID() != null)
            return checkIt.getID();
        else if (checkIt.getXpath() != null)
            return checkIt.getXpath();
        else if (checkIt.getXpath_alt() != null)
            return checkIt.getXpath_alt();
        return null;
    }
    
    public String getLocator(SeleniumEnums checkIt, String replaceme){
        if (checkIt.getID() != null)
            return checkIt.getID().replace("***", replaceme);
        else if (checkIt.getXpath() != null)
            return checkIt.getXpath().replace("***", replaceme);
        else if (checkIt.getXpath_alt() != null)
            return checkIt.getXpath_alt().replace("***", replaceme);
        return null;
    }

    public String getSelectedLabel(SeleniumEnums checkIt) {
        String element = getLocator(checkIt);
        String error_name = "select: " + element;
        try {
            return getSelectedLabel(element);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return null;
    }

    public String getTable(SeleniumEnums checkIt) {
        String element = getLocator(checkIt);
        String error_name = "getTable: " + element;
        String text = "";
        try {
            text = getTable(element);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return text;
    }

    public String getTable(SeleniumEnums checkIt, int row, int col) {
        String element = getLocator(checkIt);
        String error_name = "Click: " + element;
        String text = "";
        try {
            text = getTable(element + "." + row + "." + col);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return text;
    }

    public String getText(SeleniumEnums checkIt) {
        String element = getLocator(checkIt);
        String error_name = "getText: " + element;
        String text = "";
        try {
            text = getText(element);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return text;
    }
    
    public String getText(SeleniumEnums checkIt, String replacement) {
        String element = getLocator(checkIt, replacement);
        String error_name = "getText: " + element;
        String text = "";
        try {
            text = getText(element);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return text;
    }

    public Boolean inSession() {
        try {
            getAllWindowNames();
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    public Boolean isChecked(SeleniumEnums checkIt, String condition) {
        String element = getLocator(checkIt);
        String error_name = "isChecked: " + element;
        try {
            return isChecked(element);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return null;
    }

    public Boolean isElementNotPresent(SeleniumEnums checkIt) {
        String element = getLocator(checkIt);
        String error_name = "isElementNotPresent: " + element;
        try {
            return !isElementPresent(element);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return null;
    }

    public Boolean isElementPresent(SeleniumEnums checkIt) {
        String element = getLocator(checkIt);
        String error_name = "isElementPresent: " + element;
        try {
            return isElementPresent(element);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return null;
    }

    public Boolean isNotChecked(SeleniumEnums checkIt) {
        String element = getLocator(checkIt);
        String error_name = "isNotChecked: " + element;
        try {
            return !isChecked(element);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errors.addError(error_name, e);
        }

        return null;
    }

    public Boolean isNotVisible(SeleniumEnums checkIt) {
        String element = getLocator(checkIt);
        String error_name = "isNotVisible: " + element;
        try {
            return !isVisible(element);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return null;
    }

    public Boolean isTextNotPresentOnPage(String text) {
        String error_name = "isTextNotPresent: " + text;
        try {
            return !isTextPresentOnPage(text);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return null;
    }

    /**
     * Override to the DefaultSelenium method. This provides a way to handle errors
     * 
     * @param text
     * @return Boolean
     */
    public Boolean isTextPresentOnPage(String text) {
        String error_name = "isTextPresent: " + text;
        try {
            return isTextPresent(text);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return null;
    }

    public Boolean isVisible(SeleniumEnums checkIt) {
        String element = getLocator(checkIt);
        String error_name = "isVisible: " + element;
        try {
            return isVisible(element);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return null;
    }

    public CoreMethodLib moveallPanel(SeleniumEnums checkIt, String moveoption) {
        String element = getLocator(checkIt);
        if (moveoption.contentEquals("Right")) {
            click(element + "-move_all_right");
        } else if (moveoption.contentEquals("Left")) {
            click(element + "-move_all_left");
        }
        Pause(2);
        return this;
    }

    public CoreMethodLib open(SeleniumEnums checkIt) {
        String element = checkIt.getURL();
        String error_name = "open: " + element;
        try {
            open(element);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return this;
    }

    public CoreMethodLib Pause(Integer timeout_in_secs) {
        try {
            Thread.currentThread();
            Thread.sleep((long) (timeout_in_secs * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public CoreMethodLib removefromPanel(SeleniumEnums checkIt, String itemtomove) {
        addSelection(getLocator(checkIt) + "-picked", itemtomove);
        click(getLocator(checkIt) + "-move_left");
        Pause(2);
        return this;
    }

    public CoreMethodLib select(SeleniumEnums checkIt, String label) {
        String element = getLocator(checkIt);
        String error_name = "select: " + element + " : Label = " + label;

        try {
            select(element, label);
            Pause(5);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
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

    public CoreMethodLib selectWindowByID(String ID) {
        if (ID.equals("")) {
            ID = "null";
        }
        String error_name = "selectWindowByID: " + ID;
        try {
            selectWindow(ID);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return this;
    }

    public CoreMethodLib selectWindowByName(String name) {
        String error_name = "selectWindowByTitle: " + name;
        try {
            selectWindow("name=" + name);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return this;
    }

    public CoreMethodLib selectWindowByTitle(String title) {
        String error_name = "selectWindowByTitle: " + title;
        try {
            selectWindow("title=" + title);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
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

    public CoreMethodLib type(SeleniumEnums checkIt, String text) {
        String element = getLocator(checkIt);
        String error_name = "type: " + element;
        try {
            type(element, text);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return this;
    }

    public Boolean verifyLocation(SeleniumEnums checkIt) {
        return checkIt.getURL().equals(getLocator(checkIt));
    }

    public Boolean verifyLocation(String expected) {
        return expected.equals(getLocation(expected));
    }

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
                Pause(1); // second
                x++;
                doneWaiting = x > secondsToWait;
            }
        }
        return this;
    }

    public CoreMethodLib waitForPageToLoad() {
        waitForPageToLoad(PAGE_TIMEOUT);
        return this;
    }

    public CoreMethodLib waitForPageToLoad(Integer timeout) {
        String error_name = "waitForPageToLoad: Timeout = " + timeout;
        try {
            waitForPageToLoad(timeout.toString());
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return this;
    }

    public void mouseOver(SeleniumEnums checkIt) {
        String element = getLocator(checkIt);
        String error_name = "mouseOver: " + element;
        try {
            mouseOver(element);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
    }

}

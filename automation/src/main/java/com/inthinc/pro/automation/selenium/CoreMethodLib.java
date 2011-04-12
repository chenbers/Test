package com.inthinc.pro.automation.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;

import com.google.common.base.Supplier;
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

    public void addtoPanel(SeleniumEnums checkIt, String itemtomove) {
        addSelection(getLocator(checkIt) + "-from", itemtomove);
        click(getLocator(checkIt) + "-move_right");
        Pause(2);
    }

    public void click(SeleniumEnums checkIt) {
        String element = getLocator(checkIt);
        String error_name = "click: " + element;
        try {
            click(element);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
    }

    public ErrorCatcher getErrors() {
        return errors;
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

    public void moveallPanel(SeleniumEnums checkIt, String moveoption) {
        String element = getLocator(checkIt);
        if (moveoption.contentEquals("Right")) {
            click(element + "-move_all_right");
        } else if (moveoption.contentEquals("Left")) {
            click(element + "-move_all_left");
        }
        Pause(2);
    }

    public void open(SeleniumEnums checkIt) {
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
    }
    
    public void Pause(Integer timeout_in_secs) {
        try {
            Thread.currentThread();
            Thread.sleep((long) (timeout_in_secs * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public void removefromPanel(SeleniumEnums checkIt, String itemtomove) {
        addSelection(getLocator(checkIt) + "-picked", itemtomove);
        click(getLocator(checkIt) + "-move_left");
        Pause(2);
    }

    public void select(SeleniumEnums checkIt, String label) {
        String element = getLocator(checkIt);
        String error_name = "select: " + element;
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
    }

    public void selectDhxCombo(String entry_name) {
        String element = entry_name = "//div[text()=\"" + entry_name + "\"]";
        click(element);
        Pause(5);
    }

    @Override
    public void start() {
        errors = new ErrorCatcher();
        super.start();
    }

    public void type(SeleniumEnums checkIt, String text) {
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
    }

    public Boolean verifyLocation(SeleniumEnums checkIt){
        return checkIt.getURL().equals(getLocation());
    }
    
    public Boolean verifyLocation(String expected){
        return expected.equals(getLocation());
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

    public void waitForElementPresent(String watch_for) {
        waitForElementPresent(watch_for, "link");
    }

    public void waitForElementPresent(String watch_for, String type) {
        waitForElementPresent(watch_for, type, 180);
    }

    public void waitForElementPresent(String watch_for, String type, Integer secondsToWait) {
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
    }

    public void waitForPageToLoad() {
        waitForPageToLoad(PAGE_TIMEOUT);
    }

    public void waitForPageToLoad(Integer timeout) {
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
    }

}

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

    public CoreMethodLib(WebDriver baseDriver, String baseUrl) {
        super(baseDriver, baseUrl);
        errors = new ErrorCatcher();
    }

    public CoreMethodLib(Supplier<WebDriver> maker, String baseUrl) {
        super(maker, baseUrl);
        errors = new ErrorCatcher();
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

    public void open(String locator, String error_name) {
        try {
            open(locator);
            AbstractPage.setCurrentPage();
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
    }

    public void click(String locator, String error_name) {
        try {
            click(locator);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
    }

    public Boolean isChecked(String element, String condition, String error_name) {
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

    public Boolean isnotChecked(String element, String error_name) {
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

    public Boolean isElementPresent(String element, String error_name) {
        try {
            return isElementPresent(element);
        } catch (AssertionError e) {
            errors.addError(error_name, "Failed");
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return null;
    }

    public Boolean isElementNotPresent(String element, String error_name) {
        try {
            return !isElementPresent(element);
        } catch (AssertionError e) {
            errors.addError(error_name, "Failed");
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return null;
    }

    public Boolean verifyLocation(SeleniumEnums checkIt) {
        return verifyLocation(getLocator(checkIt), "getLocation: " + checkIt.toString());
    }

    public Boolean verifyLocation(String expected, String error_name) {
        String location = "Could not get location";
        try {
            location = getLocation();
            return location.contains(expected);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return null;
    }

    public String getText(String locator, String error_name) {
        String text = "";
        try {
            text = getText(locator);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return text;
    }

    public String getTable(String locator, int row, int col, String error_name) {
        String text = "";
        try {
            text = getTable(locator + "." + row + "." + col);
        } catch (AssertionError e) {
            errors.addError(error_name, getTable(locator));
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return text;
    }

    public String getTable(String locator, String error_name) {
        String text = "";
        try {
            text = getTable(locator);
        } catch (AssertionError e) {
            errors.addError(error_name, getTable(locator));
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return text;
    }

    public void type(String locator, String text, String error_name) {
        try {
            type(locator, text);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
    }

    public Boolean isTextPresent(String text, String error_name) {
        try {
            return isTextPresent(text);
        } catch (AssertionError e) {
            errors.addError(error_name, e);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return null;
    }

    public Boolean isTextNotPresent(String text, String error_name) {
        try {
            return !isTextPresent(text);
        } catch (AssertionError e) {
            errors.addError(error_name, e);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return null;
    }

    public Boolean isVisible(String element, String error_name) {
        try {
            return isVisible(element);
        } catch (AssertionError e) {
            errors.addError(error_name, e);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return null;
    }

    public Boolean isNotVisible(String element, String error_name) {
        try {
            return !isVisible(element);
        } catch (AssertionError e) {
            errors.addError(error_name, e);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return null;
    }

    public void waitForPageToLoad(String timeout, String error_name) {
        try {
            waitForPageToLoad(timeout);
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

    public void select(String locator, String label, String error_name) {
        try {
            select(locator, label);
            Pause(5);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
    }

    public void selectDhxCombo(String entry_name, String error_name) {
        entry_name = "//div[text()=\"" + entry_name + "\"]";
        click(entry_name, error_name);
        Pause(5);
    }

    public void addtoPanel(String locator, String itemtomove, String error_name) {
        addSelection(locator + "-from", itemtomove);
        click(locator + "-move_right");
        Pause(2);
    }

    public void removefromPanel(String locator, String itemtomove, String error_name) {
        addSelection(locator + "-picked", itemtomove);
        click(locator + "-move_left");
        Pause(2);
    }

    public void moveallPanel(String locator, String moveoption, String error_name) {
        if (moveoption.contentEquals("Right")) {
            click(locator + "-move_all_right", error_name);
        } else if (moveoption.contentEquals("Left")) {
            click(locator + "-move_all_left", error_name);
        } else {
            System.out.printf("ERROR - invald data in moveallPanel.");
        }
        Pause(2);
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
                    found = isTextPresent(watch_for);
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

    @Override
    public void start() {
        errors = new ErrorCatcher();
        super.start();
    }

    public Boolean inSession() {
        try {
            getAllWindowNames();
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    public void click(SeleniumEnums checkIt) {
        click(getLocator(checkIt), "click: " + checkIt.toString());
        Pause(2);
    }

    public void waitForPageToLoad(SeleniumEnums pageURL) {
        waitForPageToLoad(pageURL, PAGE_TIMEOUT);
    }

    public void waitForPageToLoad(SeleniumEnums pageURL, Integer wait) {
        waitForPageToLoad(wait.toString(), "waitForPageToLoad: " + pageURL.toString());
    }

    public Boolean isNotVisible(SeleniumEnums checkIt) {
        return isNotVisible(getLocator(checkIt), "isNotVisible: " + checkIt.toString());
    }

    public Boolean isVisible(SeleniumEnums checkIt) {
        return isVisible(getLocator(checkIt), "isVisible: " + checkIt.toString());
    }

    public String getText(SeleniumEnums checkIt) {
        return getText(checkIt.getText(), "getText: " + checkIt.toString());
    }

    public void type(SeleniumEnums checkIt, String expected) {
        type(getLocator(checkIt), expected, "type: " + checkIt.toString());
    }

    public Boolean isElementPresent(SeleniumEnums checkIt) {
        return isElementPresent(getLocator(checkIt), "isElementPresent: " + checkIt.toString());
    }

    public Boolean isElementNotPresent(SeleniumEnums checkIt) {
        return isElementNotPresent(getLocator(checkIt), "isElementNotPresent: " + checkIt.toString());
    }

    public String verifyText(SeleniumEnums checkIt) {
        isVisible(checkIt);
        return getText(getLocator(checkIt), "getText: " + checkIt.toString());
    }

    public void open(SeleniumEnums url) {
        this.open(url.getURL());
        AbstractPage.setCurrentPage();
    }
}

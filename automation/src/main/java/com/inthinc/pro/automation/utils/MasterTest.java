package com.inthinc.pro.automation.utils;

import java.awt.event.KeyEvent;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;
import com.inthinc.pro.automation.selenium.CoreMethodInterface;
import com.inthinc.pro.automation.selenium.ErrorCatcher;
import com.inthinc.pro.automation.selenium.GlobalSelenium;
import com.inthinc.pro.rally.TestCaseResult.Verdicts;

public class MasterTest {
    private final static Logger logger = Logger.getLogger(MasterTest.class);
    

    protected static ErrorCatcher errors = GlobalSelenium.getErrorCatcher();

    public static enum ErrorLevel {
        FATAL_ERROR(Verdicts.INCONCLUSIVE),
        FATAL(Verdicts.FAIL),
        FAIL(Verdicts.FAIL),
        ERROR(Verdicts.ERROR),
        INCONCLUSIVE(Verdicts.INCONCLUSIVE),
        WARN,
        COMPARE,
        PASS,
        ;
        
        private Verdicts verdict;
        
        private ErrorLevel(){
            verdict = Verdicts.PASS;
        }
        
        private ErrorLevel(Verdicts verdict){
            this.verdict = verdict;
        }
        
        public Verdicts getVerdict(){
            return verdict;
        }
    }
    
    protected static void enterKey() {
        KeyCommands.typeKey(KeyEvent.VK_ENTER);
    }

    protected static String escapeHtml(String original) {
        return StringEscapeUtils.escapeHtml(original);
    }

    public static void print(Object printToScreen) {
        StackTraceElement element = Thread.currentThread().getStackTrace()[2];
        String print = String.format("%3d - %s\n", element.getLineNumber(), printToScreen.toString());
        Logger.getLogger(element.getClassName()).info(print);
    }

    protected static void tabKey() {
        KeyCommands.typeKey(KeyEvent.VK_TAB);
    }

    protected static void spaceBar() {
        KeyCommands.typeKey(KeyEvent.VK_SPACE);
    }
    protected static void keyPeriod() {
        KeyCommands.typeKey(KeyEvent.VK_PERIOD);
    }

    protected static String unescapeHtml(String original) {
        return StringEscapeUtils.unescapeHtml(original);
    }

    private String savedPage;

    private CoreMethodInterface selenium;

    protected void addError(String errorName, ErrorLevel level) {
        errors.addError(errorName, Thread.currentThread().getStackTrace(), level);
    }

    protected void addError(String errorName, String error, ErrorLevel level) {
        errors.addError(errorName, error, level);
    }

    protected void addError(String errorName, Throwable stackTrace, ErrorLevel level) {
        errors.addError(errorName, stackTrace, level);
    }

    protected Boolean assertEquals(Object expected, Object actual) {
        return assertEquals(expected, actual, true);
    }

    private Boolean assertEquals(Object expected, Object actual, Boolean areObjectsEqual) {
        if (compare(expected, actual) != areObjectsEqual) {
            logger.debug("your expected: '" + expected + "'" + " does not equal: '" + actual + "'");
            addError("your expected: '" + expected + "'" + " does not equal: '" + actual + "'", ErrorLevel.FATAL);
            return false;
        }
        return true;
    }

    protected Boolean assertEquals(Object expected, Object actual, SeleniumEnumWrapper myEnum) {
        if (!compare(expected, actual)) {
            addError(myEnum.toString() + "\n" + myEnum.getLocatorsAsString(), "\t\tExpected = " + expected + "\n\t\tActual = " + actual, ErrorLevel.FATAL);
            return false;
        }
        return true;
    }

    protected Boolean assertEquals(Object expected, SeleniumEnumWrapper actual) {
        return assertEquals(expected, selenium.getText(actual));
    }

    protected Boolean assertEquals(SeleniumEnumWrapper anEnum) {
        return assertEquals(anEnum.getText(), selenium.getText(anEnum), anEnum);
    }

    protected Boolean assertFalse(Boolean test, String error) {
        if (test) {
            addError(error, ErrorLevel.FATAL);
            return false;
        }
        return true;
    }

    protected Boolean assertNotEquals(Object expected, Object actual) {
        return assertEquals(expected, actual, false);
    }

    protected Boolean assertNotEquals(Object expected, Object actual, SeleniumEnumWrapper myEnum) {
        if (compare(expected, actual)) {
            addError(myEnum.toString() + "\n" + myEnum.getLocatorsAsString(), "\t\tExpected = " + expected + "\n\t\tActual = " + actual, ErrorLevel.FATAL);
            return false;
        }
        return true;
    }

    protected Boolean assertNotEquals(Object expected, SeleniumEnumWrapper anEnum) {
        return assertNotEquals(anEnum.getText(), selenium.getText(anEnum), anEnum);
    }

    protected Boolean assertStringContains(String partialString, String fullString) {
        if (!fullString.contains(partialString)) {
            addError(partialString + " not in " + fullString, ErrorLevel.FATAL);
            return false;
        }
        return true;
    }

    protected Boolean assertTrue(Boolean test, String error) {
        if (!test) {
            addError(error, ErrorLevel.FATAL);
            return false;
        }
        return true;
    }

    protected Boolean compare(Object expected, Object actual) {
        Boolean results = false;
        if (actual instanceof SeleniumEnumWrapper) {
            results = compare(expected, selenium.getText((SeleniumEnumWrapper) actual));
        } else if (expected instanceof TextEnum) {
            results = compare(((TextEnum) expected).getText(), actual);
        } else {
            results = actual.equals(expected);
        }
        logger.debug("Expected: " + expected + " == Actual: " + actual + " is " + results);
        return results;
    }

    public String getCurrentLocation() {
        return selenium.getLocation();
    }

    protected ErrorCatcher getErrors() {
        errors = GlobalSelenium.getErrorCatcher();
        return errors;
    }

    protected CoreMethodInterface getSelenium() {
        if (selenium == null) {
            selenium = GlobalSelenium.getSelenium();
        }
        errors = GlobalSelenium.getErrorCatcher();
        return selenium;
    }

    protected String getTextFromElementWithFocus() {//TODO: dtanner: would you give this a second look. I can't get it to return expected values for _link, _text, or _textField
        WebDriver web = selenium.getWrappedDriver();
        return web.switchTo().activeElement().getText();
    }

    protected void open(SeleniumEnums pageToOpen) {
        selenium.open(new SeleniumEnumWrapper(pageToOpen));
    }

    protected void open(SeleniumEnums pageToOpen, Integer replaceNumber) {
        SeleniumEnumWrapper urlWithNumber = new SeleniumEnumWrapper(pageToOpen);
        urlWithNumber.updateURL(replaceNumber);
        selenium.open(urlWithNumber);
    }

    protected void open(String url) {
        selenium.open(url);
    }

    protected void openSavedPage() {
        open(savedPage);
    }
    
    public MasterTest pause(Integer timeout_in_secs, String reasonForPause) {
        StackTraceElement element = Thread.currentThread().getStackTrace()[2];
        AutomationThread.pause(timeout_in_secs, reasonForPause, element);
        return this;
    }

    protected void savePageLink() {
        savedPage = getCurrentLocation();
    }

    protected void setSelenium() {
        this.selenium = GlobalSelenium.getYourOwn();
    }

    protected void typeToElementWithFocus(String type) {
        WebDriver web = selenium.getWrappedDriver();
        web.switchTo().activeElement().sendKeys(type);
    }

    protected Boolean validateEquals(Object expected, Object actual) {
        return validateEquals(expected, actual, true);
    }

    private Boolean validateEquals(Object expected, Object actual, Boolean areObjectsEqual) {
        Boolean result = compare(expected, actual);
        if (areObjectsEqual != result) {
            String match = "shouldn't match";
            if (areObjectsEqual) {
                match = "should match";
            }
            addError("your expected: '" + expected + "'" + " and actual is: '" + actual + "' they " + match, ErrorLevel.FAIL);
        }
        return result;
    }
    
    

    protected Boolean validateNotEquals(Object expected, Object actual) {
        return validateEquals(expected, actual, false);
    }

    protected Boolean validateStringContains(String partialString, String fullString) {
        if (!fullString.contains(partialString)) {
            addError(partialString + " not in " + fullString, ErrorLevel.FAIL);
            return false;
        }
        return true;
    }
    
    protected Boolean validateTrue(Boolean test, String error) {
        if (!test) {
            addError(error, ErrorLevel.FAIL);
            return false;
        }
        return true;
    }
    
    protected Boolean validateFalse(Boolean test, String error) {
        if (test) {
            addError(error, ErrorLevel.FAIL);
            return false;
        }
        return true;
    }


}

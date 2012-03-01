package com.inthinc.pro.automation.utils;

import java.awt.event.KeyEvent;
import java.io.StringWriter;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Aliases;
import org.jbehave.core.annotations.When;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;

import com.inthinc.pro.automation.enums.ErrorLevel;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.interfaces.TextEnum;
import com.inthinc.pro.automation.selenium.CoreMethodInterface;
import com.inthinc.pro.automation.selenium.CoreMethodLib;
import com.inthinc.pro.automation.selenium.ErrorCatcher;
import com.inthinc.pro.automation.selenium.Page;
import com.inthinc.pro.rally.PrettyJSON;

public class MasterTest {
    private final static Logger logger = Logger.getLogger(MasterTest.class);
    
    @When("I press the enter key on my keyboard")
    public void enterKey() {
        getSelenium().enterKey();
    }

    public static String escapeHtml(String original) {
        return StringEscapeUtils.escapeHtml(original);
    }

    public static void print(Object printToScreen) {
        print(printToScreen, Level.INFO, 2);
    }
    
    public static void print(Object printToScreen, Level level, int stepsBack){
        String printStringToScreen = "";
        stepsBack ++;
        if (printToScreen == null){
            printToScreen = "error";
        } else if (printToScreen instanceof Throwable){
            printStringToScreen = StackToString.toString((Throwable) printToScreen);
        } else if (printToScreen instanceof JSONObject){
            printStringToScreen = PrettyJSON.toString(printToScreen);
        } else if (printToScreen instanceof StackTraceElement[]){
        	printStringToScreen = StackToString.toString((StackTraceElement[]) printToScreen);
        } else {
            printStringToScreen = printToScreen.toString();
        }
        StackTraceElement element = Thread.currentThread().getStackTrace()[3];
        String print = String.format("line:%3d - %s\n", element.getLineNumber(), printStringToScreen);
        Logger temp = Logger.getLogger(element.getClassName());
        if (level.equals(Level.INFO)){
            temp.info(print);
        } else if (level.equals(Level.DEBUG)){
            temp.debug(print);
        } else if (level.equals(Level.ERROR)){
            temp.error(print);
        } else if (level.equals(Level.FATAL)){
            temp.fatal(print);
        } else {
            temp.trace(print);
        }
    }
    
    public static void print(Object printToScreen, Level level){
        print(printToScreen, level, 3);
    }
    
    public static void print(String printToScreen, Level level, Object ...objects){
        print(String.format(printToScreen, objects), level, 3);
    }
    
    public static void print(String printToScreen, Object ...objects){
        print(String.format(printToScreen, objects), Level.INFO, 3);
    }

    @When("I hit the Tab Key")
    public void tabKey() {
        getSelenium().tabKey();
    }

    @When("I hit the Spacebar")
    public static void spaceBar() {
        KeyCommands.typeKey(KeyEvent.VK_SPACE);
    }
    
    @When("I hit the Period key")
    public static void keyPeriod() {
        KeyCommands.typeKey(KeyEvent.VK_PERIOD);
    }

    public static String unescapeHtml(String original) {
        return StringEscapeUtils.unescapeHtml(original);
    }

    private String savedPage;

    /**
     * Adds an error for this test, WARNING: default ErrorLevel is set to FAIL.
     * @param errorName
     */
    public void addError(String errorName){
        addError(errorName, ErrorLevel.FAIL);
    }
    public void addError(String errorName, ErrorLevel level) {
        getSelenium().getErrorCatcher().addError(errorName, Thread.currentThread().getStackTrace(), level);
    }

    public void addError(String errorName, String error, ErrorLevel level) {
        getSelenium().getErrorCatcher().addError(errorName, error, level);
    }

    public void addError(String errorName, Throwable stackTrace, ErrorLevel level) {
        getSelenium().getErrorCatcher().addError(errorName, stackTrace, level);
    }

    public Boolean assertEquals(Object expected, Object actual) {
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

    public Boolean assertEquals(Object expected, Object actual, SeleniumEnumWrapper myEnum) {
        if (!compare(expected, actual)) {
            addError(myEnum.toString() + "\n" + myEnum.getLocatorsAsString(), "\t\tExpected = " + expected + "\n\t\tActual = " + actual, ErrorLevel.FATAL);
            return false;
        }
        return true;
    }

    public Boolean assertEquals(Object expected, SeleniumEnumWrapper actual) {
        return assertEquals(expected, getSelenium().getText(actual));
    }

    public Boolean assertEquals(SeleniumEnumWrapper anEnum) {
        return assertEquals(anEnum.getText(), getSelenium().getText(anEnum), anEnum);
    }

    public Boolean assertFalse(Boolean test, String error) {
        if (test) {
            addError(error, ErrorLevel.FATAL);
            return false;
        }
        return true;
    }

    public Boolean assertNotEquals(Object expected, Object actual) {
        return assertEquals(expected, actual, false);
    }

    public Boolean assertNotEquals(Object expected, Object actual, SeleniumEnumWrapper myEnum) {
        if (compare(expected, actual)) {
            addError(myEnum.toString() + "\n" + myEnum.getLocatorsAsString(), "\t\tExpected = " + expected + "\n\t\tActual = " + actual, ErrorLevel.FATAL);
            return false;
        }
        return true;
    }

    public Boolean assertNotEquals(Object expected, SeleniumEnumWrapper anEnum) {
        return assertNotEquals(anEnum.getText(), getSelenium().getText(anEnum), anEnum);
    }

    public Boolean assertStringContains(String partialString, String fullString) {
        if (!fullString.contains(partialString)) {
            addError(partialString + " not in " + fullString, ErrorLevel.FATAL);
            return false;
        }
        return true;
    }
    
    public Boolean assertTrue(Boolean test, String error, ErrorLevel level) {
        if (!test) {
            addError(error, level);
            return false;
        }
        return true;   
    }

    public Boolean assertTrue(Boolean test, String error) {
        return assertTrue(test, error, ErrorLevel.FATAL);
    }

    public Boolean compare(Object expected, Object actual) {
        Boolean results = false;
        if (actual instanceof SeleniumEnumWrapper) {
            results = compare(expected, getSelenium().getText((SeleniumEnumWrapper) actual));
        } else if (expected instanceof TextEnum) {
            results = compare(((TextEnum) expected).getText(), actual);
        } else {
            results = actual.equals(expected);
        }
        logger.debug("Expected: " + expected + " == Actual: " + actual + " is " + results);
        return results;
    }

    public String getCurrentLocation() {
        return getSelenium().getLocation();
    }

    public ErrorCatcher getErrors() {
        return getSelenium().getErrorCatcher();
    }

    public CoreMethodInterface getSelenium() {
        return CoreMethodLib.getSeleniumThread();
    }

    public String getTextFromElementWithFocus() {// TODO: jwimmer please check this again against new code.
        return getSelenium().getTextFromElementWithFocus();
    }

    public void open(SeleniumEnums pageToOpen) {
        getSelenium().open(new SeleniumEnumWrapper(pageToOpen));
    }
    
    public void open(Page page){
        getSelenium().open(page.getExpectedPath());
    }

    public void open(SeleniumEnums pageToOpen, Integer replaceNumber) {
        SeleniumEnumWrapper urlWithNumber = new SeleniumEnumWrapper(pageToOpen);
        urlWithNumber.updateURL(replaceNumber);
        getSelenium().open(urlWithNumber);
    }

    public void open(String url) {
        getSelenium().open(url);
    }

    public void openSavedPage() {
        open(savedPage);
    }
    
    public MasterTest pause(Integer timeout_in_secs, String reasonForPause) {
        System.out.println("pausing for: "+reasonForPause);
        StackTraceElement element = Thread.currentThread().getStackTrace()[2];
        AutomationThread.pause(timeout_in_secs, reasonForPause, element);
        return this;
    }

    public void savePageLink() {
        savedPage = getCurrentLocation();
    }

    public void killSelenium() {
    	CoreMethodLib.closeSeleniumThread();
    }

    @When("I type to the active field")
    @Aliases(values={"I type to the element with focus"})
    public void typeToElementWithFocus(String type) {
        WebDriver web = getSelenium().getWrappedDriver();
        web.switchTo().activeElement().sendKeys(type);
    }

    public Boolean validateEquals(Object expected, Object actual) {
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
    
    

    public Boolean validateNotEquals(Object expected, Object actual) {
        return validateEquals(expected, actual, false);
    }

    public Boolean validateStringContains(String partialString, String fullString) {
        if (!fullString.contains(partialString)) {
            addError(partialString + " not in " + fullString, ErrorLevel.FAIL);
            return false;
        }
        return true;
    }
    
    public Boolean validateTrue(Boolean test, String error) {
        if (!test) {
            addError(error, ErrorLevel.FAIL);
            return false;
        }
        return true;
    }
    
    public Boolean validateFalse(Boolean test, String error) {
        if (test) {
            addError(error, ErrorLevel.FAIL);
            return false;
        }
        return true;
    }
    
    public static String capitalizeFirstLettersTokenizer ( final String s ) {
        return capitalizeString(s, " ");
    }
    
    public static String capitalizeString(final String s, final String split){
        final StringTokenizer st = new StringTokenizer( s, split, true );
        final StringBuilder sb = new StringBuilder();
         
        while ( st.hasMoreTokens() ) {
            String token = st.nextToken();
            token = String.format( "%s%s",
                                    Character.toUpperCase(token.charAt(0)),
                                    token.substring(1).toLowerCase() );
            sb.append( token );
        }
            
        return sb.toString();
    }
    
    public static String captalizeEnumName(final String s){
        String formatted = capitalizeString(s, "_").replace("_", "");
        return Character.toLowerCase(formatted.charAt(0)) + formatted.substring(1);
        
    }
    
    public static String switchCase(final String s){
        StringWriter writer = new StringWriter();
        for (Character c : s.toCharArray()){
            if(Character.isUpperCase(c)){
                writer.write(Character.toLowerCase(c));
            } else {
                writer.write(Character.toUpperCase(c));
            }
        }
        return writer.toString();
    }


}

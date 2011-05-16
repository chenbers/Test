package com.inthinc.pro.automation.selenium;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;

import com.google.common.base.Supplier;
import com.inthinc.pro.automation.enums.AutomationEnum;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.thoughtworks.selenium.DefaultSelenium;

/****************************************************************************************
 * Extend the functionality of DefaultSelenium, but add some error handling around it
 * 
 * try{<br />}<br />catch(Exception e){<br />errors.Error(NameOfError, e)<br />}
 * 
 * @see DefaultSelenium
 * @see ErrorCatcher
 * @author dtanner
 * 
 */
public class CoreMethodLib extends WebDriverBackedSelenium {
    public static Integer PAGE_TIMEOUT = 30000;
    private final static Logger logger = Logger.getLogger(CoreMethodLib.class);
    private ErrorCatcher errors;

    public CoreMethodLib(Supplier<WebDriver> maker, String baseUrl) {
        super(maker, baseUrl);
        errors = new ErrorCatcher();
    }

    public CoreMethodLib(WebDriver baseDriver, String baseUrl) {
        super(baseDriver, baseUrl);
        errors = new ErrorCatcher();
    }

    public CoreMethodLib addToPanel(SeleniumEnums myEnum, String itemtomove) {
        addSelection(getLocator(myEnum) + "-from", itemtomove);
        click(getLocator(myEnum) + "-move_right");
        pause(2, "addToPanel");
        return this;
    }
    
    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#click(String)}
     * @param myEnum
     * @param replacement
     * @return
     */
    public CoreMethodLib click(SeleniumEnums myEnum) {
        String element = getLocator(myEnum);
        String error_name = "click: " + element;
        try {
            click(element);
            pause(2, "click("+myEnum+")");
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
     * @param replaceName
     * @param replaceNumber
     * @return the best locator string to use for this element, null if none are found in page
     */
    public String getLocator(SeleniumEnums myEnum) {
        AutomationEnum blah = AutomationEnum.CORE_ONLY.setEnum(myEnum);
        String last="";
        for(String s: blah.getLocators()){
            if(isElementPresent(s)){
                return s;
            }
            last = s;
        }
        return last;
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
     * @param myEnum
     * @param replaceWord
     * @param replaceNumber
     * @return
     */
    public CoreMethodLib focus(SeleniumEnums myEnum) {
        String element = getLocator(myEnum);
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
     * @param replacement
     * @param replacmentNumber
     * @return
     */
    public String getText(SeleniumEnums myEnum) {
        logger.debug(" getText("+myEnum.toString()+")");
        String element = getLocator(myEnum);
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


    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#isChecked(String)}
     * @param myEnum
     * @param condition
     * @return
     */    public Boolean isChecked(SeleniumEnums myEnum) {
        String element = getLocator(myEnum);
        String error_name = "isChecked: " + element;
        try {
            return isChecked(element);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return false;
    }


    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#isElementPresent(String)}
     * @param myEnum
     * @param replacementWord
     * @param replacementNumber
     * @return
     */
    public Boolean isElementPresent(SeleniumEnums myEnum) {
        String element = getLocator(myEnum);
        return isElementPresent(element);
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
        return false;
    }


    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#isVisible(String)}
     * @param myEnum
     * @param replacementWord
     * @param replacementNumber
     * @return
     */
    public Boolean isVisible(SeleniumEnums myEnum) {
        String element = getLocator(myEnum);
        String error_name = "isVisible: " + element;
        try {
            return isVisible(element);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return false;
    }

    public CoreMethodLib moveAllPanel(SeleniumEnums myEnum, String moveoption) {
        String element = getLocator(myEnum);
        if (moveoption.contentEquals("Right")) {
            click(element + "-move_all_right");
        } else if (moveoption.contentEquals("Left")) {
            click(element + "-move_all_left");
        }
        pause(2, "moveAllPanel");
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

    public CoreMethodLib pause(Integer timeout_in_secs, String reasonForPause) {
        try {
            logger.trace("pausing for "+timeout_in_secs+" seconds because: "+reasonForPause);//TODO: jwimmer: change to trace/debug
            Thread.currentThread();
            Thread.sleep((long) (timeout_in_secs * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            throw e;
        }
        return this;
    }

    public CoreMethodLib removeFromPanel(SeleniumEnums myEnum, String itemtomove) {
        addSelection(getLocator(myEnum) + "-picked", itemtomove);
        click(getLocator(myEnum) + "-move_left");
        pause(2, "removeFromPanel");
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
            pause(5, error_name);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return this;
    }
    
//    public CoreMethodLib selectDhxCombo(Integer divPosition, String entry_name) {
//        String element = "//div[" + divPosition + "]/div[text()='" + entry_name + "']";
//        click(element);
//        return this;
//    }
//
//    public CoreMethodLib selectDhxCombo(String entry_name) {
//        String element = "//div[text()='" + entry_name + "']";
//        click(element);
//        return this;
//    }
//
//    public CoreMethodLib selectDhxCombo(String[] entry_name) {
//        StringWriter aStringAString = new StringWriter();
//        for (int i = 0; i < entry_name.length; i++) {
//            aStringAString.write(entry_name[i]);
//            if (i != (entry_name.length - 1)) {
//                aStringAString.write(" - ");
//            }
//        }
//        selectDhxCombo(aStringAString.toString());
//        return this;
//    }

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

    public CoreMethodLib waitForElementPresent(SeleniumEnums myEnum, Integer secondsToWait) {
        Integer x = 0;
        boolean found = false;
        boolean doneWaiting = false;
        while (!found || !doneWaiting) {
            if (isElementPresent(myEnum)){
            	break;
            }
            pause(1, "waitForElementPresent"); // second
            x++;
            doneWaiting = x > secondsToWait;
        }
        return this;
    }
    
    public CoreMethodLib waitForElementPresent(String element, Integer secondsToWait) {
        Integer x = 0;
        boolean found = false;
        boolean doneWaiting = false;
        while (!found || !doneWaiting) {
            if (isElementPresent(element)){
            	break;
            }
            pause(1, "waitForElementPresent: " + element); // second
            x++;
            doneWaiting = x > secondsToWait;
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
    public CoreMethodLib mouseOver(SeleniumEnums myEnum) {
        String element = getLocator(myEnum);
        String error_name = "mouseOver: " + element;
        try {
            mouseOver(element);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
		return this;
    }

	public CoreMethodLib check(AutomationEnum myEnum) {
		String element = getLocator(myEnum);
        String error_name = "mouseOver: " + element;
        try {
            check(element);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
		return this;
	}
	
	public CoreMethodLib uncheck(AutomationEnum myEnum) {
		String element = getLocator(myEnum);
        String error_name = "mouseOver: " + element;
        try {
            uncheck(element);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
		return this;
	}

}

package com.inthinc.pro.automation.selenium;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;

import com.google.common.base.Supplier;
import com.inthinc.pro.automation.enums.AutomationEnum;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleniumException;

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
    
    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#click(String)}
     * @param myEnum
     * @param replacement
     * @return
     */
    public CoreMethodLib click(AutomationEnum myEnum) {
        String element = getLocator(myEnum);
        String error_name = "click: " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString();
        try {
            click(element);
            pause(2, "click("+myEnum+")");
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
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
    
    public String getSelectedIndex(AutomationEnum myEnum){
        String element = getLocator(myEnum);
        String error_name = "select: " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString();
        try {
            return getSelectedIndex(element);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
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
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
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
    public String getLocator(AutomationEnum myEnum) {
        String last="";
        for(String s: myEnum.getLocators()){
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
    public String getSelectedLabel(AutomationEnum myEnum) {
        String element = getLocator(myEnum);
        String error_name = "select: " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString();
        try {
            return getSelectedLabel(element);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
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
    public CoreMethodLib focus(AutomationEnum myEnum) {
        String element = getLocator(myEnum);
        String error_name = "focus: " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString();
        try {
            focus(element);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
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
    public String getTable(AutomationEnum myEnum) {
        return getTable(myEnum, null, null);
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#getTable(String)}
     * @param myEnum
     * @param row
     * @param col
     * @return
     */
    public String getTable(AutomationEnum myEnum, Integer row, Integer col) {
        StringBuffer element = new StringBuffer(getLocator(myEnum));
        String error_name = "Click: " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString();
        String text = "";
        try {
            if(row != null && col != null)
                element.append("."+row+"."+col);
            text = getTable(element.toString());
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
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
    public String getText(AutomationEnum myEnum) {
        logger.debug(" getText("+myEnum.toString() +"\n"+ myEnum.getLocatorsAsString()+")");
        String element = getLocator(myEnum);
        String error_name = "getText: " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString();
        String text = "";
        try {
            text = getText(element);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
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
     */    public Boolean isChecked(AutomationEnum myEnum) {
        String element = getLocator(myEnum);
        String error_name = "isChecked: " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString();
        try {
            return isChecked(element);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
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
    public Boolean isElementPresent(AutomationEnum myEnum) {
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
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
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
    public Boolean isVisible(AutomationEnum myEnum) {
        String element = getLocator(myEnum);
        String error_name = "isVisible: " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString();
        try {
            return isVisible(element);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return false;
    }


    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#open(String)}
     * @param myEnum
     * @return
     */
    public CoreMethodLib open(AutomationEnum myEnum) {
        String element = myEnum.getURL();
        String error_name = "open: " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString();
        try {
            open(element);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return this;
    }

    public CoreMethodLib pause(Integer timeout_in_secs, String reasonForPause) {
        try {
            logger.debug("pausing for "+timeout_in_secs+" seconds because: "+reasonForPause);
            //Thread.currentThread();
            Thread.sleep((long) (timeout_in_secs * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            throw e;
        }
        return this;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#select(String, String)}
     * @param myEnum
     * @param label
     * @return
     */
    public CoreMethodLib select(AutomationEnum myEnum, String label) {
        String element = getLocator(myEnum);
        String error_name = "select: " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString() + " : Label = " + label;
        
        try {
            select(element, label);
            pause(5, error_name);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
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
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
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
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
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
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
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
    public CoreMethodLib type(AutomationEnum myEnum, String text) {
        String element = getLocator(myEnum);
        String error_name = "type: " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString();
        try {
            type(element, text);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return this;
    }

    public Boolean verifyLocation(AutomationEnum myEnum) {
        return myEnum.getURL().equals(getLocator(myEnum));
    }

    public Boolean verifyLocation(String expected) {
        return expected.equals(getLocation(expected));
    }
    
    public CoreMethodLib waitForElementPresent(Object element, Integer secondsToWait) {
        Integer x = 0;
        boolean found = false;
        boolean doneWaiting = false;
        while (!found && !doneWaiting) {
            boolean foundByString = ((element instanceof String) && (isElementPresent((String) element)));
            boolean foundByEnum = ((element instanceof AutomationEnum) && (isElementPresent((AutomationEnum) element))); 
            found =  foundByString || foundByEnum;
            pause(1, "waitForElementPresent: " + element); // second
            x++;
            doneWaiting = x > secondsToWait;
        }
        if (!found)
            errors.addError("waitForElementPresent TIMEOUT", "while waiting for " + element);
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
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
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
    public CoreMethodLib mouseOver(AutomationEnum myEnum) {
        String element = getLocator(myEnum);
        String error_name = "mouseOver: " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString();
        try {
            mouseOver(element);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
		return this;
    }

	public CoreMethodLib check(AutomationEnum myEnum) {
		String element = getLocator(myEnum);
        String error_name = "mouseOver: " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString();
        try {
            check(element);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
		return this;
	}
	
	public CoreMethodLib uncheck(AutomationEnum myEnum) {
		String element = getLocator(myEnum);
        String error_name = "mouseOver: " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString();
        try {
            uncheck(element);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
		return this;
	}

	public CoreMethodLib addSelection(AutomationEnum myEnum, String item) {
		String element = getLocator(myEnum);
        String error_name = "addSelection: " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString();
        try{
        	addSelection(element, item);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return this;
	}
	
	public CoreMethodLib removeSelection(AutomationEnum myEnum, String item) {
		String element = getLocator(myEnum);
        String error_name = "removeSelection: " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString();
        try{
        	removeSelection(element, item);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return this;
	}
	
	public CoreMethodLib removeAllSelections(AutomationEnum myEnum) {
		String element = getLocator(myEnum);
        String error_name = "removeAllSelections: " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString();
        try{
        	removeAllSelections(element);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return this;
	}

	public CoreMethodLib selectDhx(AutomationEnum myEnum, String option) {
		String element = getLocator(myEnum);
        String error_name = "removeAllSelections: " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString();
		String xpath = Xpath.start().body().div(Id.id(element)).div(option).toString();
		try{
        	click(xpath);
        	pause(3, "Need a pause");
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return this;
	}

	public CoreMethodLib fireEvent(AutomationEnum myEnum, String eventName) {
		String element = getLocator(myEnum);
        String error_name = "fireEvent: "+eventName+": " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString();
        try{
        	fireEvent(element, eventName);
        } catch (SeleniumException e) {
            errors.addError(error_name, e);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            errors.addError(error_name, e);
        }
        return this;
	}


}

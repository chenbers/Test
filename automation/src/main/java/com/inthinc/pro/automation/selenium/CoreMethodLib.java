package com.inthinc.pro.automation.selenium;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.interactions.Actions;

import com.google.common.base.Supplier;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;
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
public class CoreMethodLib extends WebDriverBackedSelenium implements CoreMethodInterface {
    public static Integer PAGE_TIMEOUT = 30000;
    private final static Logger logger = Logger.getLogger(CoreMethodLib.class);
    private ErrorCatcher errors;
    private String error_name;
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
    public String getErrorName(){
    	return error_name;
    }
    
    @Override
    public SeleniumEnumWrapper getEnum(){
    	return myEnum;
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
        error_name = "click: " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString();
        click(element);
        pause(2, "click("+myEnum+")");
        return this;
    }

	@Override
    public ErrorCatcher getErrors() {
        return errors;
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
    public String[] getTimeFrameOptions() {
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

	@Override
    public String getSelectedIndex(SeleniumEnumWrapper myEnum){
        String element = getLocator(myEnum);
        error_name = "select: " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString();
        return getSelectedIndex(element);
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

	@Override
    public String getCurrentMonth() {
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
	@Override
    public String getLocation(String expected) {
        error_name = "verifyLocation: " + expected;
        String location = "Could not get location";
        location = getLocation();
        return location;
    }

    
    /**
     * Returns the best locator string to use for this element.
     * @param myEnum
     * @param replaceName
     * @param replaceNumber
     * @return the best locator string to use for this element, null if none are found in page
     */
	@Override
    public String getLocator(SeleniumEnumWrapper myEnum) {
		this.myEnum = myEnum;
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
	@Override
    public String getSelectedLabel(SeleniumEnumWrapper myEnum) {
        String element = getLocator(myEnum);
        error_name = "select: " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString();
        return getSelectedLabel(element);
    }
    
    
    /**
     * @param myEnum
     * @return
     */
	@Override
    public CoreMethodLib focus(SeleniumEnumWrapper myEnum) {
        String element = getLocator(myEnum);
        error_name = "focus: " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString();
        WebElement item=null;
        if (element.startsWith("//")){
        	item = getWrappedDriver().findElement(By.xpath(element));
        }else if (!element.contains("=")){
        	item = getWrappedDriver().findElement(By.id(element));
        	System.out.println(element);
        }else return this;

//        new Actions(getWrappedDriver()).moveToElement(item).build().perform();
        
        return this;
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
        error_name = "Click: " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString();
        String text = "";
        
        if(row != null && col != null)
            element.append("."+row+"."+col);
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
        logger.debug(" getText("+myEnum.toString() +"\n"+ myEnum.getLocatorsAsString()+")");
        String element = getLocator(myEnum);
        error_name = "getText: " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString();
        String text = "";
        text = getText(element);
        return text;
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
        error_name = "isChecked: " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString();
        return isChecked(element);
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
     * Override to the DefaultSelenium method. This provides a way to handle errors.
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#isTextPresent(String)}
     * 
     * @param text
     * @return boolean
     */
	@Override
    public boolean isTextPresentOnPage(String text) {
        error_name = "isTextPresent: " + text;
        return isTextPresent(text);
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
        error_name = "isVisible: " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString();
        return isVisible(element);
    }


    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#open(String)}
     * @param myEnum
     * @return
     */
	@Override
    public CoreMethodLib open(SeleniumEnumWrapper myEnum) {
        String element = myEnum.getURL();
        error_name = "open: " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString();
        open(element);
        return this;
    }

	@Override
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
	@Override
    public CoreMethodLib select(SeleniumEnumWrapper myEnum, String label) {
        String element = getLocator(myEnum);
        error_name = "select: " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString() + " : Label = " + label;
        
        select(element, label);
        pause(5, error_name);
        
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
        error_name = "selectWindowByID: " + ID;
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
        error_name = "selectWindowByTitle: " + name;
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
        error_name = "selectWindowByTitle: " + title;
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
        error_name = "type: " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString();
        type(element, text);
        return this;
    }

	@Override
    public boolean verifyLocation(SeleniumEnumWrapper myEnum) {
        return myEnum.getURL().equals(getLocator(myEnum));
    }

	@Override
    public boolean verifyLocation(String expected) {
        return expected.equals(getLocation(expected));
    }

	@Override
    public CoreMethodLib waitForElementPresent(Object element, Integer secondsToWait) {
        Integer x = 0;
        boolean found = false;
        boolean doneWaiting = false;
        while (!found && !doneWaiting) {
            boolean foundByString = ((element instanceof String) && (isElementPresent((String) element)));
            boolean foundByEnum = ((element instanceof SeleniumEnumWrapper) && (isElementPresent((SeleniumEnumWrapper) element))); 
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
        error_name = "waitForPageToLoad: Timeout = " + timeout;
        waitForPageToLoad(timeout.toString());
        return this;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#mouseDown(String)}
     * @param myEnum
     */
	@Override
    public CoreMethodLib mouseOver(SeleniumEnumWrapper myEnum) {
        String element = getLocator(myEnum);
        error_name = "mouseOver: " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString();
        mouseOver(element);
		return this;
    }

	@Override
	public CoreMethodLib check(SeleniumEnumWrapper myEnum) {
		String element = getLocator(myEnum);
        error_name = "mouseOver: " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString();
        check(element);
		return this;
	}

	@Override
	public CoreMethodLib uncheck(SeleniumEnumWrapper myEnum) {
		String element = getLocator(myEnum);
        error_name = "mouseOver: " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString();
        uncheck(element);
		return this;
	}

	@Override
	public CoreMethodLib addSelection(SeleniumEnumWrapper myEnum, String item) {
		String element = getLocator(myEnum);
        error_name = "addSelection: " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString();
    	addSelection(element, item);
        return this;
	}

	@Override
	public CoreMethodLib removeSelection(SeleniumEnumWrapper myEnum, String item) {
		String element = getLocator(myEnum);
        error_name = "removeSelection: " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString();
    	removeSelection(element, item);
        return this;
	}

	@Override
	public CoreMethodLib removeAllSelections(SeleniumEnumWrapper myEnum) {
		String element = getLocator(myEnum);
        error_name = "removeAllSelections: " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString();
    	removeAllSelections(element);
        return this;
	}

	@Override
	public CoreMethodLib selectDhx(SeleniumEnumWrapper myEnum, String option) {
		String element = getLocator(myEnum);
        error_name = "removeAllSelections: " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString();
		String xpath = Xpath.start().body().div(Id.id(element)).div(option).toString();
    	click(xpath);
    	pause(3, "Need a pause");
        return this;
	}

	@Override
	public CoreMethodLib fireEvent(SeleniumEnumWrapper myEnum, String eventName) {
		String element = getLocator(myEnum);
        error_name = "fireEvent: "+eventName+": " + myEnum.toString() +"\n"+ myEnum.getLocatorsAsString();
    	fireEvent(element, eventName);
        return this;
	}

	@Override
	public CoreMethodLib doubleClick(SeleniumEnumWrapper anEnum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CoreMethodLib contextMenu(SeleniumEnumWrapper anEnum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CoreMethodLib clickAt(SeleniumEnumWrapper anEnum, String coordString) {
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
	public CoreMethodLib contextMenuAt(SeleniumEnumWrapper anEnum,
			String coordString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CoreMethodLib keyPress(SeleniumEnumWrapper anEnum, String keySequence) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CoreMethodLib keyDown(SeleniumEnumWrapper anEnum, String keySequence) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CoreMethodLib keyUp(SeleniumEnumWrapper anEnum, String keySequence) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CoreMethodLib mouseOut(SeleniumEnumWrapper anEnum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CoreMethodLib mouseDown(SeleniumEnumWrapper anEnum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CoreMethodLib mouseDownRight(SeleniumEnumWrapper anEnum) {
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
	public CoreMethodLib mouseDownRightAt(SeleniumEnumWrapper anEnum,
			String coordString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CoreMethodLib mouseUp(SeleniumEnumWrapper anEnum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CoreMethodLib mouseUpRight(SeleniumEnumWrapper anEnum) {
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
	public CoreMethodLib mouseUpRightAt(SeleniumEnumWrapper anEnum,
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
	public CoreMethodLib typeKeys(SeleniumEnumWrapper anEnum, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValue(SeleniumEnumWrapper anEnum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEditable(SeleniumEnumWrapper anEnum) {
		// TODO Auto-generated method stub
		return false;
	}
	

}

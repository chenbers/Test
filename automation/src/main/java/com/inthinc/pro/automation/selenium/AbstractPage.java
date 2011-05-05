package com.inthinc.pro.automation.selenium;

import org.openqa.selenium.WebDriver;

import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;


public abstract class AbstractPage implements Page {
    protected CoreMethodLib selenium;
    protected WebDriver webDriver;
    protected String currentPage;

    public AbstractPage() {
        selenium = GlobalSelenium.getSelenium();
        webDriver = selenium.getWrappedDriver();
    }

    public void addError(String errorName) {
        selenium.getErrors().addError(errorName, Thread.currentThread().getStackTrace());
    }

    public void addError(String errorName, String error) {
        selenium.getErrors().addError(errorName, error);
    }

    public void addErrorWithExpected(String errorName, String error, String expected) {
        selenium.getErrors().addError(errorName, error);
        selenium.getErrors().addExpected(errorName, expected);
    }

    public void assertEquals(Object actual, Object expected) {
    	String string;
    	if (actual instanceof TextEnum){
    		string = ((TextEnum) actual).getText();
    		if (!string.equals(expected)) {
                addError("'"+string+"'" + " != '" + expected+"'");
            }
    	}else{
	        if (!actual.equals(expected)) {
	            addError("'"+actual+"'" + " != '" + expected+"'");
	        }
    	}
    }

    public void assertEquals(Object expected, SeleniumEnums actual) {
        assertEquals(expected, actual.getText());
    }
    
    public void assertEquals(SeleniumEnums anEnum) {
        assertEquals(selenium.getText(anEnum), anEnum.getText());
    }
    
//    /*
//     * (non-Javadoc)
//     * 
//     * @see com.inthinc.pro.web.selenium.Page#page_loginLoad()
//     */
//    public AbstractPage page_directURL_loginThenLoad() { //TODO: jwimmer: to dTanner: this was the method I was talking about 20110502 vs. putting loginProcess in Masthead  FYI
//        return (AbstractPage) loginLoad()
//    }
    
    public void assertNotEquals(Object actual, Object expected) {
        if (actual.equals(expected)) {
            addError(actual + " == " + expected);
        }
    }

    public void assertNotEquals(Object expected, SeleniumEnums actual) {
        assertNotEquals(expected, actual.getText());
    }
    
    public void assertContains(String fullString, String partialString){
        if(!fullString.contains(partialString)){
            addError(partialString + " not in " + fullString);
        }
    }

    public String browser_location_getCurrent() {
        String[] url = webDriver.getCurrentUrl().split("/");//TODO: jwimmer: seems like this doesn't capture ALL of the pertinent location info for some pages? i.e. https://my.inthinc.com/tiwipro/app/driver/214
        return url[url.length-1];
    }


    

    public ErrorCatcher get_errors() {
        return selenium.getErrors();
    }
    
    @Override
    public String getCurrentLocation() {
        return selenium.getLocation();
    }
    
    @Override
    public String getExpectedPath() {
        // TODO Auto-generated method stub
        return null;
    }

    public CoreMethodLib getSelenium() {
        return selenium;
    }
    

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.web.selenium.Page#validateURL()
     */
    public AbstractPage validateURL() {
        boolean results = getCurrentLocation().contains(getExpectedPath());
        //TODO: jwimmer: this should fail test if called and results = false
        // System.out.println("about to return: "+results);
        return this;
    }
    
    @Override
    public Page load() {
        selenium.open(this.getExpectedPath());
        return null;
    }

    public AbstractPage page_bareMinimum_validate() {
        return validate();
    }

    @Override
    public AbstractPage validate() {
        // TODO Auto-generated method stub
        return this;
    }
    
    


}

package com.inthinc.pro.automation.selenium;

import org.openqa.selenium.WebDriver;


public abstract class AbstractPage implements VerbosePage {
    protected static CoreMethodLib selenium;
    protected static WebDriver webDriver;
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
        if (!actual.equals(expected)) {
            addError(actual + " != " + expected);
        }
    }

    public void assertEquals(Object expected, SeleniumEnums actual) {
        assertEquals(expected, actual.getText());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.web.selenium.Page#load()
     */
    public AbstractPage page_directURL_load() {
        return (AbstractPage) load();
    }
    
    public void assertEquals(SeleniumEnums actual) {
        assertEquals(selenium.getText(actual), actual.getText());
    }
    
//    /*
//     * (non-Javadoc)
//     * 
//     * @see com.inthinc.pro.web.selenium.Page#page_loginLoad()
//     */
//    public AbstractPage page_directURL_loginThenLoad() {
//        return (AbstractPage) loginLoad()
//    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.web.selenium.Page#validateURL()
     */
    public AbstractPage page_URL_validate() {
        return validateURL();
    }
    
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
        String[] url = webDriver.getCurrentUrl().split("/");
        return url[url.length-1];
    }


    protected void clickNewWindowLink(SeleniumEnums link, SeleniumEnums text){
        selenium.click(link);
        String[] handles = webDriver.getWindowHandles().toArray(new String[2]);
        webDriver.switchTo().window(handles[handles.length-1]);
        selenium.getText(text);
        selenium.close();
        webDriver.switchTo().window(handles[0]);
    }

    public ErrorCatcher get_errors() {
        return selenium.getErrors();
    }
    
    @Override
    public String getCurrentLocation() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public String getExpectedPath() {
        // TODO Auto-generated method stub
        return null;
    }

    public CoreMethodLib getSelenium() {
        return selenium;
    }
    
    protected String setCurrentLocation(){
        String[] address = getCurrentLocation().split("/");
        currentPage = address[address.length-1];
        return currentPage;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.web.selenium.Page#validateURL()
     */
    public AbstractPage validateURL() {
        boolean results = getCurrentLocation().contains(getExpectedPath());
        // System.out.println("about to return: "+results);
        return this;
    }
    
    @Override
    public Page load() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.web.selenium.Page#page_getExpectedPath()
     */
    public String page_path_getExpected() {
        return getExpectedPath();
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

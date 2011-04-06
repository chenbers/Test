package com.inthinc.pro.automation.selenium;

import java.util.HashMap;

public abstract class AbstractPage implements VerbosePage {
    protected static CoreMethodLib selenium;
    private volatile static HashMap<Long, String> currentLocation = new HashMap<Long, String>();

    public AbstractPage() {
        selenium = GlobalSelenium.getSelenium();
    }

    protected CoreMethodLib getSelenium() {
        return selenium;
    }
    
    public void addErrorWithExpected(String errorName, String error, String expected){
        selenium.getErrors().addError(errorName, error);
        selenium.getErrors().addExpected(errorName, expected);
    }
    
    public void addError(String errorName, String error){
        selenium.getErrors().addError(errorName, error);
    }
    /* (non-Javadoc)
     * @see com.inthinc.pro.web.selenium.ScripterPage#page_load()
     */
    public AbstractPage page_load(){
        return (AbstractPage) load();
    }
    
    /* (non-Javadoc)
     * @see com.inthinc.pro.web.selenium.ScripterPage#page_isLoaded()
     */
    public boolean page_isLoaded() {
        return isLoaded();
    }
    /* (non-Javadoc)
     * @see com.inthinc.pro.web.selenium.Page#isLoaded()
     */
    public boolean isLoaded() {
        return selenium.getUnderlyingWebDriver().getCurrentUrl().contains(getPath());
    }
    
    /* (non-Javadoc)
     * @see com.inthinc.pro.web.selenium.ScripterPage#page_getPath()
     */
    public String page_getPath(){
        return getPath();
    }
    /* (non-Javadoc)
     * @see com.inthinc.pro.web.selenium.Page#getPath()
     */
    public String getPath() {
        return selenium.getLocation();
    }
    //TODO: jwimmer: question for dTanner: when you get a minute, explain to me the difference between getPath() and getCurrentPage???
    protected static void setCurrentPage(){
        Long currentThread = Thread.currentThread().getId();
        currentLocation.put(currentThread, GlobalSelenium.getSelenium().getLocation());
    }
    
    protected static String getCurrentPage(){
        Long currentThread = Thread.currentThread().getId();
        return currentLocation.get(currentThread);
    }
    
    /* (non-Javadoc)
     * @see com.inthinc.pro.web.selenium.ScripterPage#page_validate()
     */
    public boolean page_validate(){
        return validate();
    }
}

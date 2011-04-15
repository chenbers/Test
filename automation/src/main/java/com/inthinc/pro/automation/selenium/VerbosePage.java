package com.inthinc.pro.automation.selenium;

/**
 * VerbosePage interface ensures that all pageObjects must implement a "minimum" functionality similar to com.inthinc.pro.web.selenium.Page,
 * but these methods are named to be familiar to the non-Programmers (TestCase writers) who will interact with the automation classes.
 *
 */
public interface VerbosePage extends Page {
    /**
     * Verbose version of load().
     * @see com.inthinc.pro.web.selenium.Page#load()
     * @return the Page object after it has been loaded.
     */
    public VerbosePage page_directURL_load();
    
    /**
     * Verbose version of validateURL().
     * @see com.inthinc.pro.web.selenium.Page#validateURL()
     * @return true if Page object is loaded, otherwise false.
     */
    public VerbosePage page_URL_validate();
    
    /**
     * Verbose version of getExpectedPath().
     * @see com.inthinc.pro.web.selenium.Page#getExpectedPath()
     * @return a String representation of the intended path.
     */
    public String page_path_getExpected();
    
    /**
     * Verbose version of getActualPath().
     * @see com.inthinc.pro.web.selenium.Page#getCurrentLocation()
     * @return a String representation of the actual browser location path.
     */
    public String browser_location_getCurrent();
    
    /**
     * Verbose version of validate().
     * @see com.inthinc.pro.web.selenium.Page#validateURL()
     * @return true if all validation checks pass, otherwise false
     */
    public VerbosePage page_bareMinimum_validate();
    
    //public VerbosePage page_directURL_loginThenLoad();
}

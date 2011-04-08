package com.inthinc.pro.automation.selenium;

public interface VerbosePage extends Page {
    /**
     * Verbose version of load().
     * @see com.inthinc.pro.web.selenium.Page#load()
     * @return the Page object after it has been loaded.
     */
    public VerbosePage page_directURL_load();
    
    /**
     * Verbose version of isLoaded().
     * @see com.inthinc.pro.web.selenium.Page#isLoaded()
     * @return true if Page object is loaded, otherwise false.
     */
    public boolean page_URL_validate();
    
    /**
     * Verbose version of getExpectedPath().
     * @see com.inthinc.pro.web.selenium.Page#getExpectedPath()
     * @return a String representation of the intended path.
     */
    public String page_path_getExpected();
    
    /**
     * Verbose version of getActualPath().
     * @see com.inthinc.pro.web.selenium.Page#getActualPath()
     * @return a String representation of the actual browser location path.
     */
    public String browser_path_getActual();
    
    /**
     * Verbose version of validate().
     * @see com.inthinc.pro.web.selenium.Page#isLoaded()
     * @return true if all validation checks pass, otherwise false
     */
    public boolean page_bareMinimum_validate();
}

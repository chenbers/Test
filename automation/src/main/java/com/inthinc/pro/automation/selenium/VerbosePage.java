package com.inthinc.pro.automation.selenium;

public interface VerbosePage extends Page {
    /**
     * Verbose version of load().
     * @see com.inthinc.pro.web.selenium.Page#load()
     * @return the Page object after it has been loaded.
     */
    public VerbosePage page_load();
    
    /**
     * Verbose version of isLoaded().
     * @see com.inthinc.pro.web.selenium.Page#isLoaded()
     * @return true if Page object is loaded, otherwise false.
     */
    public boolean page_isLoaded();
    
    /**
     * Verbose version of getPath().
     * @see com.inthinc.pro.web.selenium.Page#getPath()
     * @return a String representation of the intended path.
     */
    public String page_getPath();
    
    /**
     * Verbose version of validate().
     * @see com.inthinc.pro.web.selenium.Page#isLoaded()
     * @return true if all validation checks pass, otherwise false
     */
    public boolean page_validate();
}

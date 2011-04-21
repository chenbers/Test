package com.inthinc.pro.automation.selenium;

/**
 * Page interface ensures that all pageObjects must implement a "minimum" functionality.
 * These methods are named to be familiar to the Programmers who will interact with the automation classes.
 *
 */
public interface Page {
    /**
     * Load the Page object by navigating directly to it's URL.
     * The majority of pageObjects (concrete) are reachable by direct URL, those that are not should implement load() to fail accordingly.
     * 
     *                  
     * @return the Page object after it has been loaded.
     */
    public Page load();
    
    /**
     * Check to make sure the browser has loaded the correct URL.
     * @return this, fail test if URL is not correct
     */
    public Page validateURL();
    
    /**
     * Return the EXPECTED path for this Page object.
     * This method provides standardized helper method for validateURL();
     *  
     * @return a String representation of the intended path.
     */
    public String getExpectedPath();
    
    /**
     * Return the ACTUAL path of the browser.
     * @return a String representation of the actual browser location path.
     */
    public String getCurrentLocation();
    
    /**
     * Validates the Page object for all REQUIRED page elements.
     * @return true if all validation checks pass, otherwise false
     */
    public Page validate();
    
    //public Page loginLoad();
}

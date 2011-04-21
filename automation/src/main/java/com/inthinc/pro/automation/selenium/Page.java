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
     * //TODO: dtanner: question to jwimmer: What does this gain us??? 
     *         jwimmer: answer above in method javadoc
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
     * //TODO: dtanner: question to jwimmer: What does this accomplish us other than a common method name???
     *         jwimmer: answer in method javadoc
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
     * //TODO: dtanner: question for jwimmer: Why do we need this class?  for function name reuse???
     *         jwimmer: answer in class javadoc
     * //TODO: dtanner: question for jwimmer: Why do we return true or false, if we fail the test should end as a fail
     *         jwimmer: answer: if we never use validate methods for flow control then returning Page is preferable
     * @return true if all validation checks pass, otherwise false
     */
    public Page validate();
    
    //public Page loginLoad();
}

package com.inthinc.pro.automation.selenium;

public interface Page {
    /**
     * Load the Page object by navigating directly to it's URL.
     * //TODO: dtanner: question to jwimmer: What does this gain us??? 
     * @return the Page object after it has been loaded.
     */
    public Page load();
    
    /**
     * Check to make sure the browser has loaded the correct URL.
     *  //TODO: jwimmer: question for dTanner: can we use selenium to detect that the page is "done" loading???
     *  //TODO: dTanner: answer to jwimmer: no.
     * @return true if loaded, otherwise false
     */
    public boolean isLoaded();
    
    /**
     * Return the EXPECTED path for this Page object.
     * //TODO: dtanner: question to jwimmer: What does this accomplish us other than a common method name???
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
     * //TODO: dtanner: question for jwimmer: Why do we return true or false, if we fail the test should end as a fail
     * @return true if all validation checks pass, otherwise false
     */
    public boolean validate();
 
}

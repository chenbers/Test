package com.inthinc.pro.automation.selenium;

public interface Page {
    /**
     * Load the Page object by navigating directly to it's URL.
     * @return the Page object after it has been loaded.
     */
    public Page load();
    
    /**
     * Check to make sure the browser has loaded the correct URL.
     *  //TODO: jwimmer: question for dTanner: can we use selenium to detect that the page is "done" loading???
     * @return true if loaded, otherwise false
     */
    public boolean isLoaded();
    
    /**
     * Return the EXPECTED path for this Page object.
     * @return a String representation of the intended path.
     */
    public String getExpectedPath();
    
    /**
     * Return the ACTUAL path of the browser.
     * @return a String representation of the actual browser location path.
     */
    public String getActualPath();
    
    /**
     * Validates the Page object for all REQUIRED page elements.
     * @return true if all validation checks pass, otherwise false
     */
    public boolean validate();
 
    //TODO: jwimmer: IF there is a way to do this ... possibly by returning the Class instead? it could be helpfull... then getExpectedPath could be implemented in AbstractPage
    //public SeleniumEnum getPageEnum();
}

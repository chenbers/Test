package com.inthinc.pro.automation.selenium;

public abstract class AbstractPage implements VerbosePage {
    protected static CoreMethodLib selenium;
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
    
    /* (non-Javadoc)
     * @see com.inthinc.pro.web.selenium.ScripterPage#page_validate()
     */
    public boolean page_validate(){
        return validate();
    }
}

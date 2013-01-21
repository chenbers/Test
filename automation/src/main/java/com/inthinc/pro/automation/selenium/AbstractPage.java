package com.inthinc.pro.automation.selenium;

import java.util.ArrayList;

import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.enums.ErrorLevel;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.MasterTest;

public abstract class AbstractPage extends MasterTest implements Page {

    protected SeleniumEnums url;
    protected ArrayList<SeleniumEnums> checkMe;
    protected String currentPage;
    public static ArrayList<Class<? extends AbstractPage>> instantiatedPages = new ArrayList<Class<? extends AbstractPage>>();
    
    public String getUrl(){
        return url.getURL();
    }
    
    public abstract SeleniumEnums setUrl();

    public TextLink getLinkByText(String text) {
        return new TextLink(new SeleniumEnumWrapper("GET_LINK_BY_TEXT", "link="
                + text));
    }

    public AbstractPage() {
        checkMe = new ArrayList<SeleniumEnums>();
        url = setUrl();

        Class<? extends AbstractPage> derivedClass = this.getClass();
        if (!instantiatedPages.contains(derivedClass)) {
            instantiatedPages.add(derivedClass);
        }
    }

    public Boolean verifyOnPage() {
        return validateTrue(isOnPage(), "Could not validate on " + getSimpleName());
    }
    
    private String getSimpleName(){
        return this.getClass().getSimpleName();
    }
    
    public Boolean verifyNotOnPage(){
        return validateFalse(isOnPage(), "We are still on " + getSimpleName());
    }
    
    public Boolean assertOnPage(){
        return assertTrue(isOnPage(), "Could not assert on " + getSimpleName());
    }
    
    public Boolean assertNotOnPage(){
        return assertFalse(isOnPage(), "We are still on " + getSimpleName());
    }
    
    
    /**
     * Page method to see what page the portal is currently on.
     * @return true if on page
     */
    public Boolean isOnPage() {
    	//added 'waitForPageToLoad()' pause because sometimes elements on the page weren't loaded before a test was running
    	// DAB: adjusted 'waitForPageToLoad()' to 2 seconds, rather than the default 30 seconds, in a try/catch block
    	//      in case 2 seconds isn't long enough
    	try {
    		getSelenium().waitForPageToLoad(2000); 
    	} catch(Exception n) {
    		getSelenium().waitForPageToLoad(7000);
    	}
        return checkIsOnPage(); 
    }
    
    protected abstract boolean checkIsOnPage();

    @Override
    public String getExpectedPath() {
        return url.getURL();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.web.selenium.Page#validateURL()
     */
    public AbstractPage assertURL() {
        boolean results = getCurrentLocation().contains(getExpectedPath());
        if (!results)
            addError("validateURL", getCurrentLocation() + " does not contain "
                    + getExpectedPath() + " ?", ErrorLevel.FATAL);
        return this;
    }

    @Override
    public Page load() {
        open(url);
        return this;
    }

    @Override
    public AbstractPage validate() {
        addError(
                "AbstractPage.validate()",
                "automation cannot validate AbstractPage OR there is no validate() method for the concrete page being tested.",
                ErrorLevel.FATAL);
        return this;
    }
}

package com.inthinc.pro.automation.selenium;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;

import com.inthinc.pro.automation.elements.ElementBase;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.enums.ErrorLevel;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.MasterTest;

public abstract class AbstractPage extends MasterTest implements Page {

    protected SeleniumEnums url;
    protected ArrayList<SeleniumEnums> checkMe;
    private CoreMethodInterface selenium;
    protected WebDriver webDriver;
    protected String currentPage;
    public static ArrayList<Class<? extends AbstractPage>> instantiatedPages = new ArrayList<Class<? extends AbstractPage>>();

    public TextLink getLinkByText(String text) {
        return new TextLink(new SeleniumEnumWrapper("GET_LINK_BY_TEXT", "link="
                + text));
    }

    public AbstractPage() {
        selenium = super.getSelenium();
        webDriver = selenium.getWrappedDriver();
        checkMe = new ArrayList<SeleniumEnums>();

        Class<? extends AbstractPage> derivedClass = this.getClass();
        if (!instantiatedPages.contains(derivedClass)) {
            instantiatedPages.add(derivedClass);
        }
    }

    public Boolean verifyOnPage() {
        ElementBase test = new ElementBase() {};
        return test.validateElementsPresent(checkMe);
    }
    
    
    /**
     * Same as {@link #verifyOnPage()}, only does not add errors if NOT on page.
     * @return true if on page
     */
    public Boolean isOnPage() {
        ElementBase test = new ElementBase() {};
        return test.isElementsPresent(checkMe);
    }

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

package com.inthinc.pro.automation.selenium;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;

import com.inthinc.pro.automation.enums.AutomationEnum;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.utils.MasterTest;

public abstract class AbstractPage extends MasterTest implements Page {
	protected SeleniumEnums url;
	protected SeleniumEnums[] checkMe;
    private CoreMethodLib selenium;
    protected WebDriver webDriver;
    protected String currentPage;
    public static ArrayList<Class<? extends AbstractPage>> instantiatedPages = new ArrayList<Class<? extends AbstractPage>>(); //TODO: jwimmer: should not stay public

    public AbstractPage() {
        selenium = super.getSelenium(); System.out.println("selenium: "+selenium);
        webDriver = selenium.getWrappedDriver();
        
        Class<? extends AbstractPage> derivedClass = this.getClass();
        if(!instantiatedPages.contains(derivedClass)) {
            instantiatedPages.add(derivedClass);
        }
    }

   
    @Override
    public String getCurrentLocation() {
        return selenium.getLocation();
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
    public AbstractPage validateURL() {
        boolean results = getCurrentLocation().contains(getExpectedPath());
        if (!results)
            addError("validateURL", getCurrentLocation() + " does not contain " + getExpectedPath() + " ?"); 
        return this;
    }
    
    protected void open(SeleniumEnums pageToOpen){
    	selenium.open(AutomationEnum.CORE_ONLY.setEnum(pageToOpen));
    }
    
    protected void open(String url){
    	selenium.open(url);
    }

    @Override
    public Page load() {
        open(url);
        return this;
    }

    @Override
    public AbstractPage validate() {
        addError("no validate method", "automation cannot validate AbstractPage OR there is no validate() method for the concrete page being tested.");
        // TODO Auto-generated method stub
        return this;
    }
}

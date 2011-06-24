package com.inthinc.pro.automation.selenium;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;

import com.inthinc.pro.automation.elements.ElementBase;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.utils.MasterTest;

public abstract class AbstractPage extends MasterTest implements Page {
	protected SeleniumEnums url;
	protected ArrayList<SeleniumEnums> checkMe;
	private CoreMethodInterface selenium;
	protected WebDriver webDriver;
	protected String currentPage;
	private static ArrayList<Class<? extends AbstractPage>> instantiatedPages = new ArrayList<Class<? extends AbstractPage>>(); 
	
	public AbstractPage() {
		selenium = super.getSelenium();
		webDriver = selenium.getWrappedDriver();
		checkMe = new ArrayList<SeleniumEnums>();

		Class<? extends AbstractPage> derivedClass = this.getClass();
		if (!instantiatedPages.contains(derivedClass)) {
			instantiatedPages.add(derivedClass);
		}
	}

	public Boolean verifyOnPage() { // TODO: dtanner or jwimmer: come up with a
									// better name
		ElementBase test = new ElementBase() {};
		return test.validateElementsPresent(checkMe);
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
			addError("validateURL", getCurrentLocation() + " does not contain "
					+ getExpectedPath() + " ?", ErrorLevel.FAIL);
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
				ErrorLevel.FAIL);
		return this;
	}
}

package com.inthinc.pro.automation.selenium;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class GlobalSelenium {

	// volatile is needed so that multiple thread can reconcile the instance
	// semantics for volatile changed in Java 5.
	// private volatile static GlobalSelenium globalSelenium;
	private volatile static HashMap<Long, CoreMethodLib> multiplicative = new HashMap<Long, CoreMethodLib>();

	public static CoreMethodLib getSelenium() {
		Long currentThread = Thread.currentThread().getId();
		return multiplicative.get(currentThread);
	}

	public static CoreMethodLib getYourOwn(WebDriver driver, String appURL) {
	    System.out.println("getYourOwn("+driver+", "+appURL+")");
		CoreMethodLib selenium;
		Long currentThread = Thread.currentThread().getId();

		selenium = new CoreMethodLib(driver, appURL);
		multiplicative.put(currentThread, selenium);
		
		return multiplicative.get(currentThread);
	}

	public static CoreMethodLib getYourOwn() {
		return getYourOwn(new FirefoxDriver() ," http://localhost:8080/web/");
	}

	public static void dieSeleniumDie() {
		Long currentThread = Thread.currentThread().getId();
		//((CoreMethodLib) multiplicative.get(currentThread)).getWrappedDriver();
		multiplicative.remove(currentThread);
	}
}

package com.inthinc.pro.web.selenium;

import org.junit.*;
import org.openqa.selenium.server.SeleniumServer;

import com.inthinc.pro.web.selenium.portal.Singleton;


public class Selenium_Server {
	
	protected static Core selenium;
	private static SeleniumServer seleniumServer;
	
	@BeforeClass
	public static void setUp() throws Exception {
		startSeleniumServer();
		startSeleniumClient();
	}

	public static void startSeleniumClient() throws Exception {
		selenium = Singleton.getSingleton().getSelenium();
	}

	public static void startSeleniumServer() throws Exception {
		seleniumServer = new SeleniumServer();
		seleniumServer.start();
	}
	
	public static void stopSeleniumClient() throws Exception {
		if (selenium != null) {
			selenium.close();
			selenium.stop();
			selenium = null;
		}
	}
	public static void stopSeleniumServer() throws Exception {
		if (seleniumServer != null) {
			seleniumServer.stop();
			seleniumServer = null;
		}
	}
	@AfterClass
	public static void tearDown() throws Exception {
		stopSeleniumClient();
		stopSeleniumServer();
	}
}

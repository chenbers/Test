package com.inthinc.pro.automation.selenium;

import org.junit.*;
import org.openqa.selenium.server.SeleniumServer;



public class SeleniumServerLib {
	
	protected static CoreMethodLib selenium;
	private static SeleniumServer seleniumServer;
	
	@BeforeClass
	public static void setUp() throws Exception {
		startSeleniumServer();
		startSeleniumClient();
	}

	public static void startSeleniumClient() throws Exception {
		selenium = GlobalSelenium.getYourOwn();
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

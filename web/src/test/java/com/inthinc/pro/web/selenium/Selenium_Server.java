package com.inthinc.pro.web.selenium;

import org.junit.*;
import org.openqa.selenium.server.SeleniumServer;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class Selenium_Server {
	
	protected static Selenium selenium;
	private static SeleniumServer seleniumServer;
	
	@BeforeClass
	public static void setUp() throws Exception {
		startSeleniumServer();
		startSeleniumClient();
	}

	public static void startSeleniumClient() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*iexplore", "https://qa.tiwipro.com:8423/tiwipro/");
		selenium.start();
	}

	public static void startSeleniumServer() throws Exception {
		seleniumServer = new SeleniumServer();
		seleniumServer.start();
	}
	
	public static void stopSeleniumClient() throws Exception {
		if (selenium != null) {
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

package com.inthinc.pro.web.selenium.portal.Login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.inthinc.pro.automation.selenium.AbstractPage;
import com.inthinc.pro.automation.selenium.CoreMethodLib;
import com.inthinc.pro.automation.selenium.GlobalSelenium;

public class AppErrPage extends AbstractPage {

	protected CoreMethodLib selenium; // TODO: jwimmer: should? this be static?
	private final WebDriver driver;

	// TODO: jwimmer: these need to be in .properties or someplace they can be
	// dynamically populated/injected
	private String server = "qa-pro.inthinc.com";
	private Integer port = 8082;
	private String appName = "tiwipro";
	private String pagePath = "/login";

	/*
	 * Constructors
	 */
	public AppErrPage(WebDriver driver) {
		this.driver = driver;
		this.selenium = GlobalSelenium.getYourOwn();
		load();
		PageFactory.initElements(driver, this);
	}

	@Override
	public AppErrPage load() {
		// TODO Auto-generated method stub
		driver.get("http://" + server + ":" + port + "/" + appName + pagePath);
		return this;
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}
}

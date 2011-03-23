package com.inthinc.pro.web.selenium.Test_Cases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.thoughtworks.selenium.Selenium;


public class GeneralTest {
	public static void main(String[] args) throws InterruptedException {
		WebDriver driver = new FirefoxDriver();
		
//		driver.get("https://qa.tiwipro.com:8423/tiwipro/");
//		driver.wait(2000);
//		WebElement element;
//		element = driver.findElement( By.id("j_username"));
//		element.sendKeys("darth");
//		element = driver.findElement( By.id("j_password"));
//		element.sendKeys("password");
//		element = driver.findElement( By.id("loginLogin"));
//		element.click();
//		driver.wait(30000);
		
		Selenium selenium = new WebDriverBackedSelenium(driver, "https://qa.tiwipro.com:8423/tiwipro/");
		
//		selenium.open("logout");
//		selenium.type("j_username", "0001");
//		selenium.type("j_password", "password");
//		selenium.click("loginLogin");
		WebDriver driverInstance = ((WebDriverBackedSelenium) selenium).getUnderlyingWebDriver();
		selenium.stop();
	}
}

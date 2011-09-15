package com.inthinc.pro.selenium.testSuites;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.automation.utils.RandomValues;
import com.inthinc.pro.selenium.pageEnums.TAE.Fuel_Ratio;
import com.inthinc.pro.selenium.pageEnums.TAE.Locale;
import com.inthinc.pro.selenium.pageEnums.TAE.Measurement;
import com.inthinc.pro.selenium.pageObjects.PageMyAccount;

/*My Account test*/
public class MyAccountTest extends WebRallyTest {
	
	private PageMyAccount myAccountPage;
	private RandomValues random;
	private String USERNAME = "tinaauto";
	private String PASSWORD = "password";
	
	@Before
	public void setupPage() {
		random = new RandomValues();
		myAccountPage = new PageMyAccount();
	}
	
	@Test
	public void AccountInformation(){
	    //TODO: dtanner: to tNilson: This test needs to be compared to the user's account on the Admin > Users page
		set_test_case("TC1266");
		
		
		PageMyAccount my = new PageMyAccount();
		
		myAccountPage.loginProcess(USERNAME, PASSWORD);
		my._link().myAccount().click();
		
	/*Login Info*/
		my._text().userName().validate("tinaauto");
		my._text().locale().validate(Locale.ENGLISH.getText());
		my._text().measurement().validate(Measurement.ENGLISH.getText());
		my._text().fuelEfficiency().validate(Fuel_Ratio.ENGLISH_MILES_US.getText());
		
	/*Account Info*/
		my._text().name().validate("Tina Automation");
		my._text().group().validate("Tina's Auto Team");
		my._text().team().validate("Tina's Auto Team");
	
	/*Red Flags*/
		my._text().redFlagInfo().validate("E-mail 1");
		my._text().redFlagWarn().validate("Phone 2");
		my._text().redFlagCritical().validate("Text Message 1");
		
	/*Contact Info*/
		my._text().email1().validate("tinaauto@test.com");
		my._text().email2().validate("712@test.com");
		my._text().phone1().validate("111-222-3333");
		my._text().phone2().validate("444-555-6666");
		my._text().textMessage1().validate("2223334444@tmomail.com");
		my._text().textMessage2().validate("5556667777@tmomail.com");
	}

}

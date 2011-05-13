package com.inthinc.pro.selenium.testSuites;

import org.junit.Test;

import com.inthinc.pro.selenium.pageEnums.TAE.Fuel_Ratio;
import com.inthinc.pro.selenium.pageEnums.TAE.Locale;
import com.inthinc.pro.selenium.pageEnums.TAE.Measurement;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.pageObjects.PageMyAccount;

/*My Account test*/
public class MyAccountTest extends WebRallyTest {
	
	@Test
	public void AccountInformation(){
	    //TODO: jwimmer: to tNilson: this test fails occasionally because it is testing values that get changed (possibly by OTHER tests...) like textMessage1?
		set_test_case("TC1266");
		PageMyAccount my = new PageMyAccount();
		PageLogin login = new PageLogin();
		
		login.loginProcess("tnilson", "password");
		my._link().myAccount().click();
		
	/*Login Info*/
		my._text().userName().validateText("tnilson");
		my._text().locale().validateText("English (United States)");
		my._text().locale().validateText(Locale.ENGLISH.getText());
		my._text().measurement().validateText(Measurement.ENGLISH.getText());
		my._text().fuelEfficiency().validateText(Fuel_Ratio.ENGLISH_MILES_UK.getText());
		
	/*Account Info*/
		my._text().name().validateText("Tina L Nilson");
		my._text().group().validateText("Top");
		my._text().team().validateText("Skip's Team");
	
	/*Red Flags*/
		my._text().redFlagInfo().validateText("Text Message 1");
		my._text().redFlagWarn().validateText("E-mail 1");
		my._text().redFlagCritical().validateText("Phone 1");
		
	/*Contact Info*/
		my._text().email1().validateText("tina1965@test.com");
		my._text().email2().validateText("tlc1965@test.com");
		my._text().phone1().validateText("801-777-7777");
		my._text().phone2().validateText("801-999-9999");
		my._text().textMessage1().validateText("8017779999@tmomail.net");
		my._text().textMessage2().validateText("8019997777@tmomail.net");
	}

}

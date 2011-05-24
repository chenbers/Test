package com.inthinc.pro.selenium.testSuites;

import org.junit.Test;

import com.inthinc.pro.selenium.pageEnums.TAE.Fuel_Ratio;
import com.inthinc.pro.selenium.pageEnums.TAE.Locale;
import com.inthinc.pro.selenium.pageEnums.TAE.Measurement;
import com.inthinc.pro.selenium.pageObjects.PageMyAccount;

/*My Account test*/
public class MyAccountTest extends WebRallyTest {
	
	@Test
	public void AccountInformation(){
	    //TODO: jwimmer: to tNilson: This test needs to be corrected using the Admin > Users page
		set_test_case("TC1266");
		PageMyAccount my = new PageMyAccount();
		
		my.loginProcess("tnilson", "password");
		my._link().myAccount().click();
		
	/*Login Info*/
		my._text().userName().validate("tnilson");
		my._text().locale().validate(Locale.ENGLISH.getText());
		my._text().measurement().validate(Measurement.ENGLISH.getText());
		my._text().fuelEfficiency().validate(Fuel_Ratio.ENGLISH_MILES_UK.getText());
		
	/*Account Info*/
		my._text().name().validate("Tina L Nilson");
		my._text().group().validate("Top");
		my._text().team().validate("Skip's Team");
	
	/*Red Flags*/
		my._text().redFlagInfo().validate("Text Message 1");
		my._text().redFlagWarn().validate("E-mail 1");
		my._text().redFlagCritical().validate("Phone 1");
		
	/*Contact Info*/
		my._text().email1().validate("tina1965@test.com");
		my._text().email2().validate("tlc1965@test.com");
		my._text().phone1().validate("801-777-7777");
		my._text().phone2().validate("801-999-9999");
		my._text().textMessage1().validate("8017779999@tmomail.net");
		my._text().textMessage2().validate("8019997777@tmomail.net");
	}

}
